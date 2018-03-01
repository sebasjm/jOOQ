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

import static org.jooq.SQLDialect.POSTGRES;
// ...
import static org.jooq.SQLDialect.SQLITE;
// ...

import java.util.EnumSet;
import java.util.Map;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Table;

/**
 * @author Lukas Eder
 */
final class FieldMapForUpdate extends AbstractQueryPartMap<Field<?>, Field<?>> {


    /**
     * Generated UID
     */
    private static final long                serialVersionUID   = -6139709404698673799L;
    private static final EnumSet<SQLDialect> NO_SUPPORT_QUALIFY = EnumSet.of(POSTGRES, SQLITE);

    private final Table<?>                   table;
    private final Clause                     assignmentClause;

    FieldMapForUpdate(Table<?> table, Clause assignmentClause) {
        this.table = table;
        this.assignmentClause = assignmentClause;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (size() > 0) {
            String separator = "";

            // [#989] Some dialects do not support qualified column references
            // in the UPDATE statement's SET clause

            // [#2055] Other dialects require qualified column references to
            // disambiguated columns in queries like
            // UPDATE t1 JOIN t2 .. SET t1.val = ..., t2.val = ...
            boolean restoreQualify = ctx.qualify();
            boolean supportsQualify = NO_SUPPORT_QUALIFY.contains(ctx.family()) ? false : restoreQualify;

            for (Entry<Field<?>, Field<?>> entry : entrySet()) {
                ctx.sql(separator);

                if (!"".equals(separator)) {
                    ctx.formatNewLine();
                }

                ctx.start(assignmentClause)
                   .qualify(supportsQualify)
                   .visit(entry.getKey())
                   .qualify(restoreQualify)
                   .sql(" = ")
                   .visit(entry.getValue())
                   .end(assignmentClause);

                separator = ", ";
            }
        }
        else {
            ctx.sql("[ no fields are updated ]");
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    final void set(Map<?, ?> map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            Field<?> field = Tools.tableField(table, entry.getKey());
            Object value = entry.getValue();

            put(field, Tools.field(value, field));
        }
    }
}
