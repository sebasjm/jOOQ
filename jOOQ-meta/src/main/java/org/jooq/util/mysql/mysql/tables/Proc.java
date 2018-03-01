/**
 * This class is generated by jOOQ
 */
package org.jooq.util.mysql.mysql.tables;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.mysql.Mysql;
import org.jooq.util.mysql.mysql.enums.ProcIsDeterministic;
import org.jooq.util.mysql.mysql.enums.ProcLanguage;
import org.jooq.util.mysql.mysql.enums.ProcSecurityType;
import org.jooq.util.mysql.mysql.enums.ProcSqlDataAccess;
import org.jooq.util.mysql.mysql.enums.ProcType;


/**
 * Stored Procedures
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Proc extends TableImpl<Record> {

    private static final long serialVersionUID = -765641597;

    /**
     * The reference instance of <code>mysql.proc</code>
     */
    public static final Proc PROC = new Proc();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>mysql.proc.db</code>.
     */
    public static final TableField<Record, String> DB = createField("db", org.jooq.impl.SQLDataType.CHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.CHAR)), PROC, "");

    /**
     * The column <code>mysql.proc.name</code>.
     */
    public static final TableField<Record, String> NAME = createField("name", org.jooq.impl.SQLDataType.CHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.CHAR)), PROC, "");

    /**
     * The column <code>mysql.proc.type</code>.
     */
    public static final TableField<Record, ProcType> TYPE = createField("type", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(org.jooq.util.mysql.mysql.enums.ProcType.class), PROC, "");

    /**
     * The column <code>mysql.proc.specific_name</code>.
     */
    public static final TableField<Record, String> SPECIFIC_NAME = createField("specific_name", org.jooq.impl.SQLDataType.CHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.CHAR)), PROC, "");

    /**
     * The column <code>mysql.proc.language</code>.
     */
    public static final TableField<Record, ProcLanguage> LANGUAGE = createField("language", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(org.jooq.util.mysql.mysql.enums.ProcLanguage.class), PROC, "");

    /**
     * The column <code>mysql.proc.sql_data_access</code>.
     */
    public static final TableField<Record, ProcSqlDataAccess> SQL_DATA_ACCESS = createField("sql_data_access", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(org.jooq.util.mysql.mysql.enums.ProcSqlDataAccess.class), PROC, "");

    /**
     * The column <code>mysql.proc.is_deterministic</code>.
     */
    public static final TableField<Record, ProcIsDeterministic> IS_DETERMINISTIC = createField("is_deterministic", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(org.jooq.util.mysql.mysql.enums.ProcIsDeterministic.class), PROC, "");

    /**
     * The column <code>mysql.proc.security_type</code>.
     */
    public static final TableField<Record, ProcSecurityType> SECURITY_TYPE = createField("security_type", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(org.jooq.util.mysql.mysql.enums.ProcSecurityType.class), PROC, "");

    /**
     * The column <code>mysql.proc.param_list</code>.
     */
    public static final TableField<Record, byte[]> PARAM_LIST = createField("param_list", org.jooq.impl.SQLDataType.BLOB.nullable(false), PROC, "");

    /**
     * The column <code>mysql.proc.returns</code>.
     */
    public static final TableField<Record, byte[]> RETURNS = createField("returns", org.jooq.impl.SQLDataType.BLOB.nullable(false), PROC, "");

    /**
     * The column <code>mysql.proc.body</code>.
     */
    public static final TableField<Record, byte[]> BODY = createField("body", org.jooq.impl.SQLDataType.BLOB.nullable(false), PROC, "");

    /**
     * The column <code>mysql.proc.definer</code>.
     */
    public static final TableField<Record, String> DEFINER = createField("definer", org.jooq.impl.SQLDataType.CHAR.length(77).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.CHAR)), PROC, "");

    /**
     * The column <code>mysql.proc.created</code>.
     */
    public static final TableField<Record, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), PROC, "");

    /**
     * The column <code>mysql.proc.modified</code>.
     */
    public static final TableField<Record, Timestamp> MODIFIED = createField("modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), PROC, "");

    /**
     * The column <code>mysql.proc.sql_mode</code>.
     */
    public static final TableField<Record, String> SQL_MODE = createField("sql_mode", org.jooq.impl.SQLDataType.VARCHAR.length(478).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), PROC, "");

    /**
     * The column <code>mysql.proc.comment</code>.
     */
    public static final TableField<Record, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.CLOB.nullable(false), PROC, "");

    /**
     * The column <code>mysql.proc.character_set_client</code>.
     */
    public static final TableField<Record, String> CHARACTER_SET_CLIENT = createField("character_set_client", org.jooq.impl.SQLDataType.CHAR.length(32), PROC, "");

    /**
     * The column <code>mysql.proc.collation_connection</code>.
     */
    public static final TableField<Record, String> COLLATION_CONNECTION = createField("collation_connection", org.jooq.impl.SQLDataType.CHAR.length(32), PROC, "");

    /**
     * The column <code>mysql.proc.db_collation</code>.
     */
    public static final TableField<Record, String> DB_COLLATION = createField("db_collation", org.jooq.impl.SQLDataType.CHAR.length(32), PROC, "");

    /**
     * The column <code>mysql.proc.body_utf8</code>.
     */
    public static final TableField<Record, byte[]> BODY_UTF8 = createField("body_utf8", org.jooq.impl.SQLDataType.BLOB, PROC, "");

    /**
     * No further instances allowed
     */
    private Proc() {
        this("proc", null);
    }

    private Proc(String alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Proc(String alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Stored Procedures");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Mysql.MYSQL;
    }
}
