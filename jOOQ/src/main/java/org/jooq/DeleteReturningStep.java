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

// ...
import static org.jooq.SQLDialect.FIREBIRD;
// ...
import static org.jooq.SQLDialect.POSTGRES;

import java.util.Collection;

/**
 * This type is used for the {@link Delete}'s DSL API.
 * <p>
 * Example: <code><pre>
 * DSLContext create = DSL.using(configuration);
 *
 * create.delete(table)
 *       .where(field1.greaterThan(100))
 *       .execute();
 * </pre></code>
 * <p>
 * This implemented differently for every dialect:
 * <ul>
 * <li>Firebird and Postgres have native support for
 * <code>UPDATE .. RETURNING</code> clauses</li>
 * <li>DB2 allows to execute
 * <code>SELECT .. FROM FINAL TABLE (DELETE ...)</code></li>
 * </ul>
 *
 * @author Lukas Eder
 */
public interface DeleteReturningStep<R extends Record> extends DeleteFinalStep<R> {

    /**
     * Configure the <code>DELETE</code> statement to return all fields in
     * <code>R</code>.
     *
     * @see UpdateResultStep
     */
    @Support({ FIREBIRD, POSTGRES })
    DeleteResultStep<R> returning();

    /**
     * Configure the <code>DELETE</code> statement to return a list of fields in
     * <code>R</code>.
     *
     * @param fields Fields to be returned
     * @see UpdateResultStep
     */
    @Support({ FIREBIRD, POSTGRES })
    DeleteResultStep<R> returning(SelectFieldOrAsterisk... fields);

    /**
     * Configure the <code>DELETE</code> statement to return a list of fields in
     * <code>R</code>.
     *
     * @param fields Fields to be returned
     * @see UpdateResultStep
     */
    @Support({ FIREBIRD, POSTGRES })
    DeleteResultStep<R> returning(Collection<? extends SelectFieldOrAsterisk> fields);
}
