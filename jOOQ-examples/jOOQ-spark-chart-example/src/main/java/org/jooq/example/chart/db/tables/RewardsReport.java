/**
 * This class is generated by jOOQ
 */
package org.jooq.example.chart.db.tables;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.example.chart.db.Keys;
import org.jooq.example.chart.db.Public;
import org.jooq.example.chart.db.tables.records.RewardsReportRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0",
        "schema version:public_2",
    },
    date = "2016-06-30T15:44:15.143Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RewardsReport extends TableImpl<RewardsReportRecord> {

    private static final long serialVersionUID = -1408765210;

    /**
     * The reference instance of <code>public.rewards_report</code>
     */
    public static final RewardsReport REWARDS_REPORT = new RewardsReport();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RewardsReportRecord> getRecordType() {
        return RewardsReportRecord.class;
    }

    /**
     * The column <code>public.rewards_report.customer_id</code>.
     */
    public final TableField<RewardsReportRecord, Integer> CUSTOMER_ID = createField("customer_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('customer_customer_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.rewards_report.store_id</code>.
     */
    public final TableField<RewardsReportRecord, Integer> STORE_ID = createField("store_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.rewards_report.first_name</code>.
     */
    public final TableField<RewardsReportRecord, String> FIRST_NAME = createField("first_name", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

    /**
     * The column <code>public.rewards_report.last_name</code>.
     */
    public final TableField<RewardsReportRecord, String> LAST_NAME = createField("last_name", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

    /**
     * The column <code>public.rewards_report.email</code>.
     */
    public final TableField<RewardsReportRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

    /**
     * The column <code>public.rewards_report.address_id</code>.
     */
    public final TableField<RewardsReportRecord, Integer> ADDRESS_ID = createField("address_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.rewards_report.activebool</code>.
     */
    public final TableField<RewardsReportRecord, Boolean> ACTIVEBOOL = createField("activebool", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("true", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.rewards_report.create_date</code>.
     */
    public final TableField<RewardsReportRecord, Date> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.DATE.nullable(false).defaultValue(org.jooq.impl.DSL.field("('now'::text)::date", org.jooq.impl.SQLDataType.DATE)), this, "");

    /**
     * The column <code>public.rewards_report.last_update</code>.
     */
    public final TableField<RewardsReportRecord, Timestamp> LAST_UPDATE = createField("last_update", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.rewards_report.active</code>.
     */
    public final TableField<RewardsReportRecord, Integer> ACTIVE = createField("active", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.rewards_report</code> table reference
     */
    public RewardsReport() {
        this("rewards_report", null);
    }

    /**
     * Create an aliased <code>public.rewards_report</code> table reference
     */
    public RewardsReport(String alias) {
        this(alias, REWARDS_REPORT);
    }

    private RewardsReport(String alias, Table<RewardsReportRecord> aliased) {
        this(alias, aliased, new Field[2]);
    }

    private RewardsReport(String alias, Table<RewardsReportRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<RewardsReportRecord, Integer> getIdentity() {
        return Keys.IDENTITY_REWARDS_REPORT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RewardsReport as(String alias) {
        return new RewardsReport(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    public RewardsReport rename(String name) {
        return new RewardsReport(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public RewardsReport call(Integer minMonthlyPurchases, BigDecimal minDollarAmountPurchased) {
        return new RewardsReport(getName(), null, new Field[] { DSL.val(minMonthlyPurchases), DSL.val(minDollarAmountPurchased) });
    }

    /**
     * Call this table-valued function
     */
    public RewardsReport call(Field<Integer> minMonthlyPurchases, Field<BigDecimal> minDollarAmountPurchased) {
        return new RewardsReport(getName(), null, new Field[] { minMonthlyPurchases, minDollarAmountPurchased });
    }
}
