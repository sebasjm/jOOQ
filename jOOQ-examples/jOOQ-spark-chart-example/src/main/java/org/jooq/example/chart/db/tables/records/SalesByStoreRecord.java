/**
 * This class is generated by jOOQ
 */
package org.jooq.example.chart.db.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.example.chart.db.tables.SalesByStore;
import org.jooq.impl.TableRecordImpl;


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
public class SalesByStoreRecord extends TableRecordImpl<SalesByStoreRecord> implements Record3<String, String, BigDecimal> {

    private static final long serialVersionUID = 696884587;

    /**
     * Setter for <code>public.sales_by_store.store</code>.
     */
    public void setStore(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.sales_by_store.store</code>.
     */
    public String getStore() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.sales_by_store.manager</code>.
     */
    public void setManager(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.sales_by_store.manager</code>.
     */
    public String getManager() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.sales_by_store.total_sales</code>.
     */
    public void setTotalSales(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.sales_by_store.total_sales</code>.
     */
    public BigDecimal getTotalSales() {
        return (BigDecimal) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, BigDecimal> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, BigDecimal> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return SalesByStore.SALES_BY_STORE.STORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return SalesByStore.SALES_BY_STORE.MANAGER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return SalesByStore.SALES_BY_STORE.TOTAL_SALES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getStore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getManager();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value3() {
        return getTotalSales();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesByStoreRecord value1(String value) {
        setStore(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesByStoreRecord value2(String value) {
        setManager(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesByStoreRecord value3(BigDecimal value) {
        setTotalSales(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesByStoreRecord values(String value1, String value2, BigDecimal value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SalesByStoreRecord
     */
    public SalesByStoreRecord() {
        super(SalesByStore.SALES_BY_STORE);
    }

    /**
     * Create a detached, initialised SalesByStoreRecord
     */
    public SalesByStoreRecord(String store, String manager, BigDecimal totalSales) {
        super(SalesByStore.SALES_BY_STORE);

        set(0, store);
        set(1, manager);
        set(2, totalSales);
    }
}
