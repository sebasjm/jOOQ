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
package org.jooq;

import static org.jooq.SQLDialect.CUBRID;
// ...
import static org.jooq.SQLDialect.FIREBIRD_3_0;
import static org.jooq.SQLDialect.HSQLDB;
// ...
// ...
// ...

import java.util.Map;

/**
 * This type is used for the {@link Merge}'s DSL API.
 * <p>
 * Example: <code><pre>
 * DSLContext create = DSL.using(configuration);
 *
 * create.mergeInto(table)
 *       .using(select)
 *       .on(condition)
 *       .whenMatchedThenUpdate()
 *       .set(field1, value1)
 *       .set(field2, value2)
 *       .whenNotMatchedThenInsert(field1, field2)
 *       .values(value1, value2)
 *       .execute();
 * </pre></code>
 *
 * @author Lukas Eder
 */
public interface MergeMatchedSetStep<R extends Record> {

    /**
     * Set values for <code>UPDATE</code> in the <code>MERGE</code> statement's
     * <code>WHEN MATCHED</code> clause.
     */
    @Support({ CUBRID, FIREBIRD_3_0, HSQLDB })
    <T> MergeMatchedSetMoreStep<R> set(Field<T> field, T value);

    /**
     * Set values for <code>UPDATE</code> in the <code>MERGE</code> statement's
     * <code>WHEN MATCHED</code> clause.
     */
    @Support({ CUBRID, FIREBIRD_3_0, HSQLDB })
    <T> MergeMatchedSetMoreStep<R> set(Field<T> field, Field<T> value);

    /**
     * Set values for <code>UPDATE</code> in the <code>MERGE</code> statement's
     * <code>WHEN MATCHED</code> clause.
     */
    @Support({ CUBRID, FIREBIRD_3_0, HSQLDB })
    <T> MergeMatchedSetMoreStep<R> set(Field<T> field, Select<? extends Record1<T>> value);

    /**
     * Set multiple values for <code>UPDATE</code> in the <code>MERGE</code>
     * statement's <code>WHEN MATCHED</code> clause.
     * <p>
     * Keys can either be of type {@link String}, {@link Name}, or
     * {@link Field}.
     * <p>
     * Values can either be of type <code>&lt;T&gt;</code> or
     * <code>Field&lt;T&gt;</code>. jOOQ will attempt to convert values to their
     * corresponding field's type.
     */
    @Support({ CUBRID, FIREBIRD_3_0, HSQLDB })
    MergeMatchedSetMoreStep<R> set(Map<?, ?> map);

    /**
     * Set multiple values for <code>UPDATE</code> in the <code>MERGE</code>
     * statement's <code>WHEN MATCHED</code> clause.
     * <p>
     * This is the same as calling {@link #set(Map)} with the argument record
     * treated as a <code>Map&lt;Field&lt;?>, Object></code>, except that the
     * {@link Record#changed()} flags are taken into consideration in order to
     * update only changed values.
     *
     * @see #set(Map)
     */
    @Support({ CUBRID, FIREBIRD_3_0, HSQLDB })
    MergeMatchedSetMoreStep<R> set(Record record);
}
