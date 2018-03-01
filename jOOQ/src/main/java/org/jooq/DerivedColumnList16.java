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
import static org.jooq.SQLDialect.H2;
import static org.jooq.SQLDialect.HSQLDB;
import static org.jooq.SQLDialect.MYSQL_8_0;
// ...
import static org.jooq.SQLDialect.POSTGRES;
// ...
// ...
// ...

import javax.annotation.Generated;

/**
 * A <code>DerivedColumnList</code> is a name of a table expression with
 * optional derived column list.
 * <p>
 * An example of a correlation name with derived column list is:
 * <code>table(column1, column2)</code>
 *
 * @author Lukas Eder
 */
@Generated("This class was generated using jOOQ-tools")
public interface DerivedColumnList16 extends QueryPart {

    /**
     * Specify a subselect to refer to by the <code>DerivedColumnList</code> to
     * form a common table expression.
     */
    @Support({ FIREBIRD, H2, HSQLDB, MYSQL_8_0, POSTGRES })
    <R extends Record16<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> CommonTableExpression<R> as(Select<R> select);

}
