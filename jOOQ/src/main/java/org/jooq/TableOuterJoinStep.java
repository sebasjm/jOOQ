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
// ...
import static org.jooq.SQLDialect.CUBRID;
// ...
import static org.jooq.SQLDialect.DERBY;
import static org.jooq.SQLDialect.FIREBIRD;
import static org.jooq.SQLDialect.H2;
// ...
import static org.jooq.SQLDialect.HSQLDB;
// ...
// ...
import static org.jooq.SQLDialect.MARIADB;
import static org.jooq.SQLDialect.MYSQL;
// ...
import static org.jooq.SQLDialect.POSTGRES;
// ...
// ...
// ...

import org.jooq.impl.DSL;

/**
 * An intermediate type for the construction of a partitioned
 * {@link SQLDialect#ORACLE} <code>OUTER JOIN</code> clause.
 * <p>
 * This step allows for adding Oracle-specific <code>PARTITION BY</code> clauses
 * to the left of an <code>OUTER JOIN</code> keyword. See the Oracle
 * documentation for more details here: <a href=
 * "https://docs.oracle.com/database/121/SQLRF/statements_10002.htm#BABBCHJA"
 * >https://docs.oracle.com/database/121/SQLRF/statements_10002.htm#BABBCHJA</a>
 *
 * @author Lukas Eder
 */
public interface TableOuterJoinStep<R extends Record> {




























































































































































































































































































































































































}
