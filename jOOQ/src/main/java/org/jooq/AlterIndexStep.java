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
import static org.jooq.SQLDialect.DERBY;
import static org.jooq.SQLDialect.H2;
import static org.jooq.SQLDialect.HSQLDB;
// ...
import static org.jooq.SQLDialect.POSTGRES;

/**
 * The step in the <code>ALTER INDEX</code> where the action can be decided.
 *
 * @author Lukas Eder
 */
public interface AlterIndexStep {

    /**
     * Add a <code>RENAME TO</code> clause to the <code>ALTER INDEX</code>
     * statement.
     * <p>
     * Note that in some databases, including MySQL and SQL Server, the index
     * namespace is tied to a table, not a schema. In those databases, it is
     * recommended to call {@link DSLContext#alterTable(String)} with
     * {@link AlterTableStep#renameIndex(String)} instead.
     */
    @Support({ DERBY, H2, HSQLDB, POSTGRES })
    AlterIndexFinalStep renameTo(String newName);

    /**
     * Add a <code>RENAME TO</code> clause to the <code>ALTER INDEX</code>
     * statement.
     * <p>
     * Note that in some databases, including MySQL and SQL Server, the index
     * namespace is tied to a table, not a schema. In those databases, it is
     * recommended to call {@link DSLContext#alterTable(Name)} with
     * {@link AlterTableStep#renameIndex(Name)} instead.
     */
    @Support({ DERBY, H2, HSQLDB, POSTGRES })
    AlterIndexFinalStep renameTo(Name newName);

    /**
     * Add a <code>RENAME TO</code> clause to the <code>ALTER INDEX</code>
     * statement.
     * <p>
     * Note that in some databases, including MySQL and SQL Server, the index
     * namespace is tied to a table, not a schema. In those databases, it is
     * recommended to call {@link DSLContext#alterTable(Name)} with
     * {@link AlterTableStep#renameIndex(Index)} instead.
     */
    @Support({ DERBY, H2, HSQLDB, POSTGRES })
    AlterIndexFinalStep renameTo(Index newName);
}
