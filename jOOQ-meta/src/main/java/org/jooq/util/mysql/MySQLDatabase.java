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

package org.jooq.util.mysql;

import static org.jooq.impl.DSL.falseCondition;
import static org.jooq.impl.DSL.inline;
import static org.jooq.util.mysql.information_schema.Tables.COLUMNS;
import static org.jooq.util.mysql.information_schema.Tables.KEY_COLUMN_USAGE;
import static org.jooq.util.mysql.information_schema.Tables.REFERENTIAL_CONSTRAINTS;
import static org.jooq.util.mysql.information_schema.Tables.ROUTINES;
import static org.jooq.util.mysql.information_schema.Tables.SCHEMATA;
import static org.jooq.util.mysql.information_schema.Tables.STATISTICS;
import static org.jooq.util.mysql.information_schema.Tables.TABLES;
import static org.jooq.util.mysql.mysql.Tables.PROC;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SortOrder;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.csv.CSVReader;
import org.jooq.util.AbstractDatabase;
import org.jooq.util.AbstractIndexDefinition;
import org.jooq.util.ArrayDefinition;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DefaultEnumDefinition;
import org.jooq.util.DefaultIndexColumnDefinition;
import org.jooq.util.DefaultRelations;
import org.jooq.util.DomainDefinition;
import org.jooq.util.EnumDefinition;
import org.jooq.util.IndexColumnDefinition;
import org.jooq.util.IndexDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.RoutineDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.SequenceDefinition;
import org.jooq.util.TableDefinition;
import org.jooq.util.UDTDefinition;
import org.jooq.util.mysql.information_schema.tables.Columns;
import org.jooq.util.mysql.information_schema.tables.KeyColumnUsage;
import org.jooq.util.mysql.information_schema.tables.ReferentialConstraints;
import org.jooq.util.mysql.information_schema.tables.Routines;
import org.jooq.util.mysql.information_schema.tables.Schemata;
import org.jooq.util.mysql.information_schema.tables.Statistics;
import org.jooq.util.mysql.information_schema.tables.Tables;
import org.jooq.util.mysql.mysql.enums.ProcType;
import org.jooq.util.mysql.mysql.tables.Proc;

/**
 * @author Lukas Eder
 */
public class MySQLDatabase extends AbstractDatabase {

    private static final JooqLogger log = JooqLogger.getLogger(MySQLDatabase.class);
    private static Boolean          is8;

    @Override
    protected List<IndexDefinition> getIndexes0() throws SQLException {
        List<IndexDefinition> result = new ArrayList<IndexDefinition>();

        // Same implementation as in H2Database and HSQLDBDatabase
        Map<Record, Result<Record>> indexes = create()
            .select(
                Statistics.TABLE_SCHEMA,
                Statistics.TABLE_NAME,
                Statistics.INDEX_NAME,
                Statistics.NON_UNIQUE,
                Statistics.COLUMN_NAME,
                Statistics.SEQ_IN_INDEX)
            .from(STATISTICS)
            // [#5213] Duplicate schema value to work around MySQL issue https://bugs.mysql.com/bug.php?id=86022
            .where(Statistics.TABLE_SCHEMA.in(getInputSchemata()).or(
                  getInputSchemata().size() == 1
                ? Statistics.TABLE_SCHEMA.in(getInputSchemata())
                : falseCondition()))
            .orderBy(
                Statistics.TABLE_SCHEMA,
                Statistics.TABLE_NAME,
                Statistics.INDEX_NAME,
                Statistics.SEQ_IN_INDEX)
            .fetchGroups(
                new Field[] {
                    Statistics.TABLE_SCHEMA,
                    Statistics.TABLE_NAME,
                    Statistics.INDEX_NAME,
                    Statistics.NON_UNIQUE
                },
                new Field[] {
                    Statistics.COLUMN_NAME,
                    Statistics.SEQ_IN_INDEX
                });

        indexLoop:
        for (Entry<Record, Result<Record>> entry : indexes.entrySet()) {
            final Record index = entry.getKey();
            final Result<Record> columns = entry.getValue();

            final SchemaDefinition tableSchema = getSchema(index.get(Statistics.TABLE_SCHEMA));
            if (tableSchema == null)
                continue indexLoop;

            final String indexName = index.get(Statistics.INDEX_NAME);
            final String tableName = index.get(Statistics.TABLE_NAME);
            final TableDefinition table = getTable(tableSchema, tableName);
            if (table == null)
                continue indexLoop;

            final boolean unique = !index.get(Statistics.NON_UNIQUE, boolean.class);

            // [#6310] [#6620] Function-based indexes are not yet supported
            for (Record column : columns)
                if (table.getColumn(column.get(Statistics.COLUMN_NAME)) == null)
                    continue indexLoop;

            result.add(new AbstractIndexDefinition(tableSchema, indexName, table, unique) {
                List<IndexColumnDefinition> indexColumns = new ArrayList<IndexColumnDefinition>();

                {
                    for (Record column : columns) {
                        indexColumns.add(new DefaultIndexColumnDefinition(
                            this,
                            table.getColumn(column.get(Statistics.COLUMN_NAME)),
                            SortOrder.ASC,
                            column.get(Statistics.SEQ_IN_INDEX, int.class)
                        ));
                    }
                }

                @Override
                protected List<IndexColumnDefinition> getIndexColumns0() {
                    return indexColumns;
                }
            });
        }

        return result;
    }

    @Override
    protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
        for (Record record : fetchKeys(true)) {
            SchemaDefinition schema = getSchema(record.get(Statistics.TABLE_SCHEMA));
            String constraintName = record.get(Statistics.INDEX_NAME);
            String tableName = record.get(Statistics.TABLE_NAME);
            String columnName = record.get(Statistics.COLUMN_NAME);

            String key = getKeyName(tableName, constraintName);
            TableDefinition table = getTable(schema, tableName);

            if (table != null) {
                relations.addPrimaryKey(key, table.getColumn(columnName));
            }
        }
    }

    @Override
    protected void loadUniqueKeys(DefaultRelations relations) throws SQLException {
        for (Record record : fetchKeys(false)) {
            SchemaDefinition schema = getSchema(record.get(Statistics.TABLE_SCHEMA));
            String constraintName = record.get(Statistics.INDEX_NAME);
            String tableName = record.get(Statistics.TABLE_NAME);
            String columnName = record.get(Statistics.COLUMN_NAME);

            String key = getKeyName(tableName, constraintName);
            TableDefinition table = getTable(schema, tableName);

            if (table != null) {
                relations.addUniqueKey(key, table.getColumn(columnName));
            }
        }
    }

    private String getKeyName(String tableName, String keyName) {
        return "KEY_" + tableName + "_" + keyName;
    }

    private boolean is8() {
        if (is8 == null) {

            // [#6602] The mysql.proc table got removed in MySQL 8.0
            try {
                create(true).fetchExists(PROC);
                is8 = false;
            }
            catch (DataAccessException ignore) {
                is8 = true;
            }
        }

        return is8;
    }

    private Result<Record4<String, String, String, String>> fetchKeys(boolean primary) {

        // [#3560] It has been shown that querying the STATISTICS table is much faster on
        // very large databases than going through TABLE_CONSTRAINTS and KEY_COLUMN_USAGE
        return create().select(
                           Statistics.TABLE_SCHEMA,
                           Statistics.TABLE_NAME,
                           Statistics.COLUMN_NAME,
                           Statistics.INDEX_NAME)
                       .from(STATISTICS)
                       // [#5213] Duplicate schema value to work around MySQL issue https://bugs.mysql.com/bug.php?id=86022
                       .where(Statistics.TABLE_SCHEMA.in(getInputSchemata()).or(
                             getInputSchemata().size() == 1
                           ? Statistics.TABLE_SCHEMA.in(getInputSchemata())
                           : falseCondition()))
                       .and(primary
                           ? Statistics.INDEX_NAME.eq(inline("PRIMARY"))
                           : Statistics.INDEX_NAME.ne(inline("PRIMARY")).and(Statistics.NON_UNIQUE.eq(inline("0"))))
                       .orderBy(
                           Statistics.TABLE_SCHEMA,
                           Statistics.TABLE_NAME,
                           Statistics.INDEX_NAME,
                           Statistics.SEQ_IN_INDEX)
                       .fetch();
    }

    @Override
    protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
        for (Record record : create().select(
                    ReferentialConstraints.CONSTRAINT_SCHEMA,
                    ReferentialConstraints.CONSTRAINT_NAME,
                    ReferentialConstraints.TABLE_NAME,
                    ReferentialConstraints.REFERENCED_TABLE_NAME,
                    ReferentialConstraints.UNIQUE_CONSTRAINT_NAME,
                    ReferentialConstraints.UNIQUE_CONSTRAINT_SCHEMA,
                    KeyColumnUsage.COLUMN_NAME)
                .from(REFERENTIAL_CONSTRAINTS)
                .join(KEY_COLUMN_USAGE)
                .on(ReferentialConstraints.CONSTRAINT_SCHEMA.equal(KeyColumnUsage.CONSTRAINT_SCHEMA))
                .and(ReferentialConstraints.CONSTRAINT_NAME.equal(KeyColumnUsage.CONSTRAINT_NAME))
                // [#5213] Duplicate schema value to work around MySQL issue https://bugs.mysql.com/bug.php?id=86022
                .where(ReferentialConstraints.CONSTRAINT_SCHEMA.in(getInputSchemata()).or(
                      getInputSchemata().size() == 1
                    ? ReferentialConstraints.CONSTRAINT_SCHEMA.in(getInputSchemata())
                    : falseCondition()))
                .orderBy(
                    KeyColumnUsage.CONSTRAINT_SCHEMA.asc(),
                    KeyColumnUsage.CONSTRAINT_NAME.asc(),
                    KeyColumnUsage.ORDINAL_POSITION.asc())
                .fetch()) {

            SchemaDefinition foreignKeySchema = getSchema(record.get(ReferentialConstraints.CONSTRAINT_SCHEMA));
            SchemaDefinition uniqueKeySchema = getSchema(record.get(ReferentialConstraints.UNIQUE_CONSTRAINT_SCHEMA));

            String foreignKey = record.get(ReferentialConstraints.CONSTRAINT_NAME);
            String foreignKeyColumn = record.get(KeyColumnUsage.COLUMN_NAME);
            String foreignKeyTableName = record.get(ReferentialConstraints.TABLE_NAME);
            String referencedKey = record.get(ReferentialConstraints.UNIQUE_CONSTRAINT_NAME);
            String referencedTableName = record.get(ReferentialConstraints.REFERENCED_TABLE_NAME);

            TableDefinition foreignKeyTable = getTable(foreignKeySchema, foreignKeyTableName);

            if (foreignKeyTable != null) {
                ColumnDefinition column = foreignKeyTable.getColumn(foreignKeyColumn);

                String key = getKeyName(referencedTableName, referencedKey);
                relations.addForeignKey(foreignKey, key, column, uniqueKeySchema);
            }
        }
    }

    @Override
    protected void loadCheckConstraints(DefaultRelations r) throws SQLException {
        // Currently not supported
    }

    @Override
    protected List<CatalogDefinition> getCatalogs0() throws SQLException {
        List<CatalogDefinition> result = new ArrayList<CatalogDefinition>();
        result.add(new CatalogDefinition(this, "", ""));
        return result;
    }

    @Override
    protected List<SchemaDefinition> getSchemata0() throws SQLException {
        List<SchemaDefinition> result = new ArrayList<SchemaDefinition>();

        for (String name : create()
                .select(Schemata.SCHEMA_NAME)
                .from(SCHEMATA)
                .fetch(Schemata.SCHEMA_NAME)) {

            result.add(new SchemaDefinition(this, name, ""));
        }

        return result;
    }

    @Override
    protected List<SequenceDefinition> getSequences0() throws SQLException {
        List<SequenceDefinition> result = new ArrayList<SequenceDefinition>();
        return result;
    }

    @Override
    protected List<TableDefinition> getTables0() throws SQLException {
        List<TableDefinition> result = new ArrayList<TableDefinition>();

        for (Record record : create().select(
                Tables.TABLE_SCHEMA,
                Tables.TABLE_NAME,
                Tables.TABLE_COMMENT)
            .from(TABLES)
            // [#5213] Duplicate schema value to work around MySQL issue https://bugs.mysql.com/bug.php?id=86022
            .where(Tables.TABLE_SCHEMA.in(getInputSchemata()).or(
                  getInputSchemata().size() == 1
                ? Tables.TABLE_SCHEMA.in(getInputSchemata())
                : falseCondition()))
            .orderBy(
                Tables.TABLE_SCHEMA,
                Tables.TABLE_NAME)
            .fetch()) {

            SchemaDefinition schema = getSchema(record.get(Tables.TABLE_SCHEMA));
            String name = record.get(Tables.TABLE_NAME);
            String comment = record.get(Tables.TABLE_COMMENT);

            MySQLTableDefinition table = new MySQLTableDefinition(schema, name, comment);
            result.add(table);
        }

        return result;
    }

    @Override
    protected List<EnumDefinition> getEnums0() throws SQLException {
        List<EnumDefinition> result = new ArrayList<EnumDefinition>();

        Result<Record5<String, String, String, String, String>> records = create()
            .select(
                Columns.TABLE_SCHEMA,
                Columns.COLUMN_COMMENT,
                Columns.TABLE_NAME,
                Columns.COLUMN_NAME,
                Columns.COLUMN_TYPE)
            .from(COLUMNS)
            .where(
                Columns.COLUMN_TYPE.like("enum(%)").and(
                // [#5213] Duplicate schema value to work around MySQL issue https://bugs.mysql.com/bug.php?id=86022
                Columns.TABLE_SCHEMA.in(getInputSchemata()).or(
                      getInputSchemata().size() == 1
                    ? Columns.TABLE_SCHEMA.in(getInputSchemata())
                    : falseCondition())))
            .orderBy(
                Columns.TABLE_SCHEMA.asc(),
                Columns.TABLE_NAME.asc(),
                Columns.COLUMN_NAME.asc())
            .fetch();

        for (Record record : records) {
            SchemaDefinition schema = getSchema(record.get(Columns.TABLE_SCHEMA));

            String comment = record.get(Columns.COLUMN_COMMENT);
            String table = record.get(Columns.TABLE_NAME);
            String column = record.get(Columns.COLUMN_NAME);
            String name = table + "_" + column;
            String columnType = record.get(Columns.COLUMN_TYPE);

            // [#1237] Don't generate enum classes for columns in MySQL tables
            // that are excluded from code generation
            TableDefinition tableDefinition = getTable(schema, table);
            if (tableDefinition != null) {
                ColumnDefinition columnDefinition = tableDefinition.getColumn(column);

                if (columnDefinition != null) {

                    // [#1137] Avoid generating enum classes for enum types that
                    // are explicitly forced to another type
                    if (getConfiguredForcedType(columnDefinition, columnDefinition.getType()) == null) {
                        DefaultEnumDefinition definition = new DefaultEnumDefinition(schema, name, comment);

                        CSVReader reader = new CSVReader(
                            new StringReader(columnType.replaceAll("(^enum\\()|(\\)$)", ""))
                           ,','  // Separator
                           ,'\'' // Quote character
                           ,true // Strict quotes
                        );

                        for (String string : reader.next()) {
                            definition.addLiteral(string);
                        }

                        result.add(definition);
                    }
                }
            }
        }

        return result;
    }

    @Override
    protected List<DomainDefinition> getDomains0() throws SQLException {
        List<DomainDefinition> result = new ArrayList<DomainDefinition>();
        return result;
    }

    @Override
    protected List<UDTDefinition> getUDTs0() throws SQLException {
        List<UDTDefinition> result = new ArrayList<UDTDefinition>();
        return result;
    }

    @Override
    protected List<ArrayDefinition> getArrays0() throws SQLException {
        List<ArrayDefinition> result = new ArrayList<ArrayDefinition>();
        return result;
    }

    @Override
    protected List<RoutineDefinition> getRoutines0() throws SQLException {
        List<RoutineDefinition> result = new ArrayList<RoutineDefinition>();

        Result<Record6<String, String, String, byte[], byte[], ProcType>> records = is8()

            ? create().select(
                    Routines.ROUTINE_SCHEMA,
                    Routines.ROUTINE_NAME,
                    Routines.ROUTINE_COMMENT,
                    inline(new byte[0]).as(Proc.PARAM_LIST),
                    inline(new byte[0]).as(Proc.RETURNS),
                    Routines.ROUTINE_TYPE.coerce(Proc.TYPE).as(Routines.ROUTINE_TYPE))
                .from(ROUTINES)
                .where(Routines.ROUTINE_SCHEMA.in(getInputSchemata()))
                .orderBy(1, 2, 6)
                .fetch()

            : create().select(
                    Proc.DB.as(Routines.ROUTINE_SCHEMA),
                    Proc.NAME.as(Routines.ROUTINE_NAME),
                    Proc.COMMENT.as(Routines.ROUTINE_COMMENT),
                    Proc.PARAM_LIST,
                    Proc.RETURNS,
                    Proc.TYPE.as(Routines.ROUTINE_TYPE))
                .from(PROC)
                .where(Proc.DB.in(getInputSchemata()))
                .orderBy(1, 2, 6)
                .fetch();

        Map<Record, Result<Record6<String, String, String, byte[], byte[], ProcType>>> groups =
            records.intoGroups(new Field[] { Routines.ROUTINE_SCHEMA, Routines.ROUTINE_NAME });

        // [#1908] This indirection is necessary as MySQL allows for overloading
        // procedures and functions with the same signature.
        for (Entry<Record, Result<Record6<String, String, String, byte[], byte[], ProcType>>> entry : groups.entrySet()) {
            Result<?> overloads = entry.getValue();

            for (int i = 0; i < overloads.size(); i++) {
                Record record = overloads.get(i);

                SchemaDefinition schema = getSchema(record.get(Routines.ROUTINE_SCHEMA));
                String name = record.get(Routines.ROUTINE_NAME);
                String comment = record.get(Routines.ROUTINE_COMMENT);
                String params = is8() ? "" : new String(record.get(Proc.PARAM_LIST));
                String returns = is8() ? "" : new String(record.get(Proc.RETURNS));
                ProcType type = record.get(Routines.ROUTINE_TYPE.coerce(Proc.TYPE).as(Routines.ROUTINE_TYPE));

                if (overloads.size() > 1)
                    result.add(new MySQLRoutineDefinition(schema, name, comment, params, returns, type, "_" + type.name()));
                else
                    result.add(new MySQLRoutineDefinition(schema, name, comment, params, returns, type, null));
            }
        }

        return result;
    }

    @Override
    protected List<PackageDefinition> getPackages0() throws SQLException {
        List<PackageDefinition> result = new ArrayList<PackageDefinition>();
        return result;
    }

    @Override
    protected DSLContext create0() {
        return DSL.using(getConnection(), SQLDialect.MYSQL);
    }
}
