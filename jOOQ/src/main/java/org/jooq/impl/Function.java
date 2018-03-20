/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.jooq.impl;

import static org.jooq.SQLDialect.CUBRID;
// ...
import static org.jooq.SQLDialect.H2;
import static org.jooq.SQLDialect.HSQLDB;
import static org.jooq.SQLDialect.MARIADB;
import static org.jooq.SQLDialect.MYSQL;
import static org.jooq.SQLDialect.POSTGRES;
import static org.jooq.SQLDialect.POSTGRES_9_4;
import static org.jooq.SQLDialect.SQLITE;
// ...
import static org.jooq.impl.DSL.condition;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.one;
import static org.jooq.impl.DSL.percentileCont;
import static org.jooq.impl.Keywords.K_AS;
import static org.jooq.impl.Keywords.K_DENSE_RANK;
import static org.jooq.impl.Keywords.K_DISTINCT;
import static org.jooq.impl.Keywords.K_FILTER;
import static org.jooq.impl.Keywords.K_FIRST;
import static org.jooq.impl.Keywords.K_IGNORE_NULLS;
import static org.jooq.impl.Keywords.K_KEEP;
import static org.jooq.impl.Keywords.K_LAST;
import static org.jooq.impl.Keywords.K_NULL;
import static org.jooq.impl.Keywords.K_ORDER_BY;
import static org.jooq.impl.Keywords.K_OVER;
import static org.jooq.impl.Keywords.K_RESPECT_NULLS;
import static org.jooq.impl.Keywords.K_SEPARATOR;
import static org.jooq.impl.Keywords.K_WHERE;
import static org.jooq.impl.Keywords.K_WITHIN_GROUP;
import static org.jooq.impl.Term.ARRAY_AGG;
import static org.jooq.impl.Term.LIST_AGG;
import static org.jooq.impl.Term.MEDIAN;
import static org.jooq.impl.Term.ROW_NUMBER;
import static org.jooq.impl.Tools.DataKey.DATA_WINDOW_DEFINITIONS;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import org.jooq.AggregateFilterStep;
import org.jooq.AggregateFunction;
import org.jooq.ArrayAggOrderByStep;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.OrderedAggregateFunction;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.WindowBeforeOverStep;
import org.jooq.WindowDefinition;
import org.jooq.WindowFinalStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOrderByStep;
import org.jooq.WindowOverStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.WindowRowsAndStep;
import org.jooq.WindowRowsStep;
import org.jooq.WindowSpecification;
// ...

/**
 * A field that handles built-in functions, aggregate functions, and window
 * functions.
 *
 * @author Lukas Eder
 */
class Function<T> extends AbstractField<T> implements

    // Cascading interface implementations for aggregate function behaviour
    OrderedAggregateFunction<T>,
    ArrayAggOrderByStep<T>,
    AggregateFunction<T>,
    // and for window function behaviour
    WindowIgnoreNullsStep<T>,
    WindowPartitionByStep<T>,
    WindowRowsStep<T>,
    WindowRowsAndStep<T>
    {


    private static final long                serialVersionUID      = 347252741712134044L;
    private static final EnumSet<SQLDialect> SUPPORT_ARRAY_AGG     = EnumSet.of(HSQLDB, POSTGRES);
    private static final EnumSet<SQLDialect> SUPPORT_GROUP_CONCAT  = EnumSet.of(CUBRID, H2, HSQLDB, MARIADB, MYSQL, SQLITE);
    private static final EnumSet<SQLDialect> SUPPORT_STRING_AGG    = EnumSet.of(POSTGRES);
    private static final EnumSet<SQLDialect> SUPPORT_WINDOW_CLAUSE = EnumSet.of(MYSQL, POSTGRES);

    static final Field<Integer>              ASTERISK              = DSL.field("*", Integer.class);

    // Mutually exclusive attributes: super.getName(), this.name, this.term
    private final Name                       name;
    private final Term                       term;

    // Other attributes
    private final QueryPartList<QueryPart>   arguments;
    private final boolean                    distinct;
    private SortFieldList                    withinGroupOrderBy;
    private SortFieldList                    keepDenseRankOrderBy;
    private Condition                        filter;
    private WindowSpecificationImpl          windowSpecification;
    private WindowDefinitionImpl             windowDefinition;
    private Name                             windowName;

    private boolean                          first;
    private boolean                          ignoreNulls;
    private boolean                          respectNulls;

    // -------------------------------------------------------------------------
    // XXX Constructors
    // -------------------------------------------------------------------------

    Function(String name, DataType<T> type, QueryPart... arguments) {
        this(name, false, type, arguments);
    }

    Function(Term term, DataType<T> type, QueryPart... arguments) {
        this(term, false, type, arguments);
    }

    Function(Name name, DataType<T> type, QueryPart... arguments) {
        this(name, false, type, arguments);
    }

    Function(String name, boolean distinct, DataType<T> type, QueryPart... arguments) {
        super(DSL.name(name), type);

        this.term = null;
        this.name = null;
        this.distinct = distinct;
        this.arguments = new QueryPartList<QueryPart>(arguments);
    }

    Function(Term term, boolean distinct, DataType<T> type, QueryPart... arguments) {
        super(term.toName(), type);

        this.term = term;
        this.name = null;
        this.distinct = distinct;
        this.arguments = new QueryPartList<QueryPart>(arguments);
    }

    Function(Name name, boolean distinct, DataType<T> type, QueryPart... arguments) {
        super(name, type);

        this.term = null;
        this.name = name;
        this.distinct = distinct;
        this.arguments = new QueryPartList<QueryPart>(arguments);
    }

    // -------------------------------------------------------------------------
    // XXX QueryPart API
    // -------------------------------------------------------------------------

    @Override
    public /* final */ void accept(Context<?> ctx) {
        if (term == ARRAY_AGG && SUPPORT_ARRAY_AGG.contains(ctx.family())) {
            toSQLGroupConcat(ctx);
            toSQLFilterClause(ctx);
            toSQLOverClause(ctx);
        }
        else if (term == LIST_AGG && SUPPORT_GROUP_CONCAT.contains(ctx.family())) {
            toSQLGroupConcat(ctx);
        }
        else if (term == LIST_AGG && SUPPORT_STRING_AGG  .contains(ctx.family())) {
            toSQLStringAgg(ctx);
            toSQLFilterClause(ctx);
            toSQLOverClause(ctx);
        }





        else if (term == MEDIAN && ctx.family() == POSTGRES) {
            Field<?>[] fields = new Field[arguments.size()];
            for (int i = 0; i < fields.length; i++)
                fields[i] = DSL.field("{0}", arguments.get(i));

            ctx.visit(percentileCont(new BigDecimal("0.5")).withinGroupOrderBy(fields));
        }
        else {
            toSQLArguments(ctx);
            toSQLKeepDenseRankOrderByClause(ctx);
            toSQLWithinGroupClause(ctx);
            toSQLFilterClause(ctx);
            toSQLOverClause(ctx);
        }
    }














































    /**
     * [#1275] <code>LIST_AGG</code> emulation for Postgres, Sybase
     */
    final void toSQLStringAgg(Context<?> ctx) {
        toSQLFunctionName(ctx);
        ctx.sql('(');

        if (distinct)
            ctx.visit(K_DISTINCT).sql(' ');

        // The explicit cast is needed in Postgres
        ctx.visit(((Field<?>) arguments.get(0)).cast(String.class));

        if (arguments.size() > 1)
            ctx.sql(", ").visit(arguments.get(1));
        else
            ctx.sql(", ''");

        if (!Tools.isEmpty(withinGroupOrderBy))
            ctx.sql(' ').visit(K_ORDER_BY).sql(' ')
               .visit(withinGroupOrderBy);

        ctx.sql(')');
    }

    /**
     * [#1273] <code>LIST_AGG</code> emulation for MySQL and CUBRID
     */
    final void toSQLGroupConcat(Context<?> ctx) {
        toSQLFunctionName(ctx);
        ctx.sql('(');
        toSQLArguments1(ctx, new QueryPartList<QueryPart>(Arrays.asList(arguments.get(0))));

        if (!Tools.isEmpty(withinGroupOrderBy))
            ctx.sql(' ').visit(K_ORDER_BY).sql(' ')
               .visit(withinGroupOrderBy);

        if (arguments.size() > 1)
            if (ctx.family() == SQLITE)
                ctx.sql(", ").visit(arguments.get(1));
            else
                ctx.sql(' ').visit(K_SEPARATOR).sql(' ')
                   .visit(arguments.get(1));

        ctx.sql(')');
    }

    final void toSQLFilterClause(Context<?> ctx) {
        if (filter != null && (HSQLDB == ctx.family() || POSTGRES_9_4.precedes(ctx.dialect()))) {
            ctx.sql(' ')
               .visit(K_FILTER)
               .sql(" (")
               .visit(K_WHERE)
               .sql(' ')
               .visit(filter)
               .sql(')');
        }
    }

    final void toSQLOverClause(Context<?> ctx) {
        QueryPart window = window(ctx);

        // Render this clause only if needed
        if (window == null)
            return;

        // [#1524] Don't render this clause where it is not supported
        if (term == ROW_NUMBER && ctx.configuration().dialect() == HSQLDB)
            return;

        ctx.sql(' ')
           .visit(K_OVER)
           .sql(' ')
           .visit(window);
    }

    @SuppressWarnings("unchecked")
    final QueryPart window(Context<?> ctx) {
        if (windowSpecification != null)
            return DSL.sql("({0})", windowSpecification);

        // [#3727] Referenced WindowDefinitions that contain a frame clause
        // shouldn't be referenced from within parentheses (in PostgreSQL)
        if (windowDefinition != null)
            if (POSTGRES == ctx.family())
                return windowDefinition;
            else
                return DSL.sql("({0})", windowDefinition);

        // [#531] Inline window specifications if the WINDOW clause is not supported
        if (windowName != null) {
            if (SUPPORT_WINDOW_CLAUSE.contains(ctx.family()))
                return windowName;

            QueryPartList<WindowDefinition> windows = (QueryPartList<WindowDefinition>) ctx.data(DATA_WINDOW_DEFINITIONS);

            if (windows != null) {
                for (WindowDefinition window : windows)
                    if (((WindowDefinitionImpl) window).getName().equals(windowName))
                        return DSL.sql("({0})", window);
            }

            // [#3162] If a window specification is missing from the query's WINDOW clause,
            // jOOQ should just render the window name regardless of the SQL dialect
            else {
                return windowName;
            }
        }

        return null;
    }

    /**
     * Render <code>KEEP (DENSE_RANK [FIRST | LAST] ORDER BY {...})</code> clause
     */
    final void toSQLKeepDenseRankOrderByClause(Context<?> ctx) {
        if (!Tools.isEmpty(keepDenseRankOrderBy)) {
            ctx.sql(' ').visit(K_KEEP)
               .sql(" (").visit(K_DENSE_RANK)
               .sql(' ').visit(first ? K_FIRST : K_LAST)
               .sql(' ').visit(K_ORDER_BY)
               .sql(' ').visit(keepDenseRankOrderBy)
               .sql(')');
        }
    }

    /**
     * Render <code>WITHIN GROUP (ORDER BY ..)</code> clause
     */
    final void toSQLWithinGroupClause(Context<?> ctx) {
        if (withinGroupOrderBy != null) {
            ctx.sql(' ').visit(K_WITHIN_GROUP)
               .sql(" (").visit(K_ORDER_BY).sql(' ');

            if (withinGroupOrderBy.isEmpty())
                ctx.visit(K_NULL);
            else
                ctx.visit(withinGroupOrderBy);

            ctx.sql(')');
        }
    }

    /**
     * Render function arguments and argument modifiers
     */
    final void toSQLArguments(Context<?> ctx) {
        toSQLFunctionName(ctx);
        ctx.sql('(');
        toSQLArguments0(ctx);
        ctx.sql(')');
    }

    final void toSQLArguments0(Context<?> ctx) {
        toSQLArguments1(ctx, arguments);
    }

    final void toSQLArguments1(Context<?> ctx, QueryPartList<QueryPart> args) {
        if (distinct) {
            ctx.visit(K_DISTINCT);

            // [#2883] PostgreSQL can use the DISTINCT keyword with formal row value expressions.
            if (ctx.family() == POSTGRES && args.size() > 1) {
                ctx.sql('(');
            }
            else {
                ctx.sql(' ');
            }
        }

        if (!args.isEmpty()) {
            if (filter == null || HSQLDB == ctx.family() || POSTGRES_9_4.precedes(ctx.dialect())) {
                ctx.visit(args);
            }
            else {
                QueryPartList<Field<?>> expressions = new QueryPartList<Field<?>>();

                for (QueryPart argument : args)
                    expressions.add(DSL.when(filter, argument == ASTERISK ? one() : argument));

                ctx.visit(expressions);
            }
        }

        if (distinct)
            if (ctx.family() == POSTGRES && args.size() > 1)
                ctx.sql(')');

        if (ignoreNulls) {





                ctx.sql(' ').visit(K_IGNORE_NULLS);
        }
        else if (respectNulls) {





                ctx.sql(' ').visit(K_RESPECT_NULLS);
        }
    }

    final void toSQLFunctionName(Context<?> ctx) {
        if (name != null)
            ctx.visit(name);
        else if (term != null)
            ctx.sql(term.translate(ctx.configuration().dialect()));
        else
            ctx.sql(getName());
    }

    // -------------------------------------------------------------------------
    // XXX aggregate and window function fluent API methods
    // -------------------------------------------------------------------------

    final QueryPartList<QueryPart> getArguments() {
        return arguments;
    }

    @Override
    public final AggregateFunction<T> withinGroupOrderBy(OrderField<?>... fields) {
        return withinGroupOrderBy(Arrays.asList(fields));
    }

    @Override
    public final AggregateFunction<T> withinGroupOrderBy(Collection<? extends OrderField<?>> fields) {
        if (withinGroupOrderBy == null)
            withinGroupOrderBy = new SortFieldList();

        withinGroupOrderBy.addAll(Tools.sortFields(fields));
        return this;
    }














































    @Override
    public final WindowBeforeOverStep<T> filterWhere(Condition c) {
        filter = c;
        return this;
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(Condition... conditions) {
        return filterWhere(Arrays.asList(conditions));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(Collection<? extends Condition> conditions) {
        ConditionProviderImpl c = new ConditionProviderImpl();
        c.addConditions(conditions);
        return filterWhere(c);
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(Field<Boolean> field) {
        return filterWhere(condition(field));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(Boolean field) {
        return filterWhere(condition(field));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(SQL sql) {
        return filterWhere(condition(sql));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(String sql) {
        return filterWhere(condition(sql));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(String sql, Object... bindings) {
        return filterWhere(condition(sql, bindings));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(String sql, QueryPart... parts) {
        return filterWhere(condition(sql, parts));
    }

    @Override
    public final WindowPartitionByStep<T> over() {
        windowSpecification = new WindowSpecificationImpl();
        return this;
    }

    @Override
    public final WindowFinalStep<T> over(WindowSpecification specification) {
        this.windowSpecification = (WindowSpecificationImpl) specification;
        return this;
    }

    @Override
    public final WindowFinalStep<T> over(WindowDefinition definition) {
        this.windowDefinition = (WindowDefinitionImpl) definition;
        return this;
    }

    @Override
    public final WindowFinalStep<T> over(String n) {
        return over(name(n));
    }

    @Override
    public final WindowFinalStep<T> over(Name n) {
        this.windowName = n;
        return this;
    }

    @Override
    public final WindowOrderByStep<T> partitionBy(Field<?>... fields) {
        windowSpecification.partitionBy(fields);
        return this;
    }

    @Override
    public final WindowOrderByStep<T> partitionBy(Collection<? extends Field<?>> fields) {
        windowSpecification.partitionBy(fields);
        return this;
    }

    @Override
    @Deprecated
    public final WindowOrderByStep<T> partitionByOne() {
        windowSpecification.partitionByOne();
        return this;
    }

    @Override
    public final Function<T> orderBy(OrderField<?>... fields) {
        if (windowSpecification != null)
            windowSpecification.orderBy(fields);
        else
            withinGroupOrderBy(fields);

        return this;
    }

    @Override
    public final Function<T> orderBy(Collection<? extends OrderField<?>> fields) {
        if (windowSpecification != null)
            windowSpecification.orderBy(fields);
        else
            withinGroupOrderBy(fields);

        return this;
    }

    @Override
    public final WindowFinalStep<T> rowsUnboundedPreceding() {
        windowSpecification.rowsUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowFinalStep<T> rowsPreceding(int number) {
        windowSpecification.rowsPreceding(number);
        return this;
    }

    @Override
    public final WindowFinalStep<T> rowsCurrentRow() {
        windowSpecification.rowsCurrentRow();
        return this;
    }

    @Override
    public final WindowFinalStep<T> rowsUnboundedFollowing() {
        windowSpecification.rowsUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowFinalStep<T> rowsFollowing(int number) {
        windowSpecification.rowsFollowing(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenUnboundedPreceding() {
        windowSpecification.rowsBetweenUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenPreceding(int number) {
        windowSpecification.rowsBetweenPreceding(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenCurrentRow() {
        windowSpecification.rowsBetweenCurrentRow();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenUnboundedFollowing() {
        windowSpecification.rowsBetweenUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenFollowing(int number) {
        windowSpecification.rowsBetweenFollowing(number);
        return this;
    }

    @Override
    public final WindowFinalStep<T> rangeUnboundedPreceding() {
        windowSpecification.rangeUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowFinalStep<T> rangePreceding(int number) {
        windowSpecification.rangePreceding(number);
        return this;
    }

    @Override
    public final WindowFinalStep<T> rangeCurrentRow() {
        windowSpecification.rangeCurrentRow();
        return this;
    }

    @Override
    public final WindowFinalStep<T> rangeUnboundedFollowing() {
        windowSpecification.rangeUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowFinalStep<T> rangeFollowing(int number) {
        windowSpecification.rangeFollowing(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenUnboundedPreceding() {
        windowSpecification.rangeBetweenUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenPreceding(int number) {
        windowSpecification.rangeBetweenPreceding(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenCurrentRow() {
        windowSpecification.rangeBetweenCurrentRow();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenUnboundedFollowing() {
        windowSpecification.rangeBetweenUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenFollowing(int number) {
        windowSpecification.rangeBetweenFollowing(number);
        return this;
    }

    @Override
    public final WindowFinalStep<T> andUnboundedPreceding() {
        windowSpecification.andUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowFinalStep<T> andPreceding(int number) {
        windowSpecification.andPreceding(number);
        return this;
    }

    @Override
    public final WindowFinalStep<T> andCurrentRow() {
        windowSpecification.andCurrentRow();
        return this;
    }

    @Override
    public final WindowFinalStep<T> andUnboundedFollowing() {
        windowSpecification.andUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowFinalStep<T> andFollowing(int number) {
        windowSpecification.andFollowing(number);
        return this;
    }
}
