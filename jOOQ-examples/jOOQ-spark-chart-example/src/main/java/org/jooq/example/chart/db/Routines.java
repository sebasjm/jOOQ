/**
 * This class is generated by jOOQ
 */
package org.jooq.example.chart.db;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.AggregateFunction;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.example.chart.db.routines.GetCustomerBalance;
import org.jooq.example.chart.db.routines.GroupConcat;
import org.jooq.example.chart.db.routines.InventoryHeldByCustomer;
import org.jooq.example.chart.db.routines.InventoryInStock;
import org.jooq.example.chart.db.routines.LastDay;
import org.jooq.example.chart.db.routines.LastUpdated;
import org.jooq.example.chart.db.routines._GroupConcat;
import org.jooq.example.chart.db.tables.FilmInStock;
import org.jooq.example.chart.db.tables.FilmNotInStock;
import org.jooq.example.chart.db.tables.RewardsReport;
import org.jooq.example.chart.db.tables.records.FilmInStockRecord;
import org.jooq.example.chart.db.tables.records.FilmNotInStockRecord;
import org.jooq.example.chart.db.tables.records.RewardsReportRecord;
import org.jooq.impl.DSL;


/**
 * Convenience access to all stored procedures and functions in public
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
public class Routines {

    /**
     * Call <code>public._group_concat</code>
     */
    public static String _GroupConcat(Configuration configuration, String __1, String __2) {
        _GroupConcat f = new _GroupConcat();
        f.set__1(__1);
        f.set__2(__2);

        f.execute(configuration);
        return f.getReturnValue();
    }

    /**
     * Get <code>public._group_concat</code> as a field.
     */
    public static Field<String> _GroupConcat(String __1, String __2) {
        _GroupConcat f = new _GroupConcat();
        f.set__1(__1);
        f.set__2(__2);

        return f.asField();
    }

    /**
     * Get <code>public._group_concat</code> as a field.
     */
    public static Field<String> _GroupConcat(Field<String> __1, Field<String> __2) {
        _GroupConcat f = new _GroupConcat();
        f.set__1(__1);
        f.set__2(__2);

        return f.asField();
    }

    /**
     * Call <code>public.get_customer_balance</code>
     */
    public static BigDecimal getCustomerBalance(Configuration configuration, Integer pCustomerId, Timestamp pEffectiveDate) {
        GetCustomerBalance f = new GetCustomerBalance();
        f.setPCustomerId(pCustomerId);
        f.setPEffectiveDate(pEffectiveDate);

        f.execute(configuration);
        return f.getReturnValue();
    }

    /**
     * Get <code>public.get_customer_balance</code> as a field.
     */
    public static Field<BigDecimal> getCustomerBalance(Integer pCustomerId, Timestamp pEffectiveDate) {
        GetCustomerBalance f = new GetCustomerBalance();
        f.setPCustomerId(pCustomerId);
        f.setPEffectiveDate(pEffectiveDate);

        return f.asField();
    }

    /**
     * Get <code>public.get_customer_balance</code> as a field.
     */
    public static Field<BigDecimal> getCustomerBalance(Field<Integer> pCustomerId, Field<Timestamp> pEffectiveDate) {
        GetCustomerBalance f = new GetCustomerBalance();
        f.setPCustomerId(pCustomerId);
        f.setPEffectiveDate(pEffectiveDate);

        return f.asField();
    }

    /**
     * Get <code>public.group_concat</code> as a field.
     */
    public static AggregateFunction<String> groupConcat(String __1) {
        GroupConcat f = new GroupConcat();
        f.set__1(__1);

        return f.asAggregateFunction();
    }

    /**
     * Get <code>public.group_concat</code> as a field.
     */
    public static AggregateFunction<String> groupConcat(Field<String> __1) {
        GroupConcat f = new GroupConcat();
        f.set__1(__1);

        return f.asAggregateFunction();
    }

    /**
     * Call <code>public.inventory_held_by_customer</code>
     */
    public static Integer inventoryHeldByCustomer(Configuration configuration, Integer pInventoryId) {
        InventoryHeldByCustomer f = new InventoryHeldByCustomer();
        f.setPInventoryId(pInventoryId);

        f.execute(configuration);
        return f.getReturnValue();
    }

    /**
     * Get <code>public.inventory_held_by_customer</code> as a field.
     */
    public static Field<Integer> inventoryHeldByCustomer(Integer pInventoryId) {
        InventoryHeldByCustomer f = new InventoryHeldByCustomer();
        f.setPInventoryId(pInventoryId);

        return f.asField();
    }

    /**
     * Get <code>public.inventory_held_by_customer</code> as a field.
     */
    public static Field<Integer> inventoryHeldByCustomer(Field<Integer> pInventoryId) {
        InventoryHeldByCustomer f = new InventoryHeldByCustomer();
        f.setPInventoryId(pInventoryId);

        return f.asField();
    }

    /**
     * Call <code>public.inventory_in_stock</code>
     */
    public static Boolean inventoryInStock(Configuration configuration, Integer pInventoryId) {
        InventoryInStock f = new InventoryInStock();
        f.setPInventoryId(pInventoryId);

        f.execute(configuration);
        return f.getReturnValue();
    }

    /**
     * Get <code>public.inventory_in_stock</code> as a field.
     */
    public static Field<Boolean> inventoryInStock(Integer pInventoryId) {
        InventoryInStock f = new InventoryInStock();
        f.setPInventoryId(pInventoryId);

        return f.asField();
    }

    /**
     * Get <code>public.inventory_in_stock</code> as a field.
     */
    public static Field<Boolean> inventoryInStock(Field<Integer> pInventoryId) {
        InventoryInStock f = new InventoryInStock();
        f.setPInventoryId(pInventoryId);

        return f.asField();
    }

    /**
     * Call <code>public.last_day</code>
     */
    public static Date lastDay(Configuration configuration, Timestamp __1) {
        LastDay f = new LastDay();
        f.set__1(__1);

        f.execute(configuration);
        return f.getReturnValue();
    }

    /**
     * Get <code>public.last_day</code> as a field.
     */
    public static Field<Date> lastDay(Timestamp __1) {
        LastDay f = new LastDay();
        f.set__1(__1);

        return f.asField();
    }

    /**
     * Get <code>public.last_day</code> as a field.
     */
    public static Field<Date> lastDay(Field<Timestamp> __1) {
        LastDay f = new LastDay();
        f.set__1(__1);

        return f.asField();
    }

    /**
     * Call <code>public.last_updated</code>
     */
    public static Object lastUpdated(Configuration configuration) {
        LastUpdated f = new LastUpdated();

        f.execute(configuration);
        return f.getReturnValue();
    }

    /**
     * Get <code>public.last_updated</code> as a field.
     */
    public static Field<Object> lastUpdated() {
        LastUpdated f = new LastUpdated();

        return f.asField();
    }

    /**
     * Call <code>public.film_in_stock</code>.
     */
    public static Result<FilmInStockRecord> filmInStock(Configuration configuration, Integer pFilmId, Integer pStoreId) {
        return DSL.using(configuration).selectFrom(FilmInStock.FILM_IN_STOCK.call(pFilmId, pStoreId)).fetch();
    }

    /**
     * Get <code>public.film_in_stock</code> as a table.
     */
    public static FilmInStock filmInStock(Integer pFilmId, Integer pStoreId) {
        return FilmInStock.FILM_IN_STOCK.call(pFilmId, pStoreId);
    }

    /**
     * Get <code>public.film_in_stock</code> as a table.
     */
    public static FilmInStock filmInStock(Field<Integer> pFilmId, Field<Integer> pStoreId) {
        return FilmInStock.FILM_IN_STOCK.call(pFilmId, pStoreId);
    }

    /**
     * Call <code>public.film_not_in_stock</code>.
     */
    public static Result<FilmNotInStockRecord> filmNotInStock(Configuration configuration, Integer pFilmId, Integer pStoreId) {
        return DSL.using(configuration).selectFrom(FilmNotInStock.FILM_NOT_IN_STOCK.call(pFilmId, pStoreId)).fetch();
    }

    /**
     * Get <code>public.film_not_in_stock</code> as a table.
     */
    public static FilmNotInStock filmNotInStock(Integer pFilmId, Integer pStoreId) {
        return FilmNotInStock.FILM_NOT_IN_STOCK.call(pFilmId, pStoreId);
    }

    /**
     * Get <code>public.film_not_in_stock</code> as a table.
     */
    public static FilmNotInStock filmNotInStock(Field<Integer> pFilmId, Field<Integer> pStoreId) {
        return FilmNotInStock.FILM_NOT_IN_STOCK.call(pFilmId, pStoreId);
    }

    /**
     * Call <code>public.rewards_report</code>.
     */
    public static Result<RewardsReportRecord> rewardsReport(Configuration configuration, Integer minMonthlyPurchases, BigDecimal minDollarAmountPurchased) {
        return DSL.using(configuration).selectFrom(RewardsReport.REWARDS_REPORT.call(minMonthlyPurchases, minDollarAmountPurchased)).fetch();
    }

    /**
     * Get <code>public.rewards_report</code> as a table.
     */
    public static RewardsReport rewardsReport(Integer minMonthlyPurchases, BigDecimal minDollarAmountPurchased) {
        return RewardsReport.REWARDS_REPORT.call(minMonthlyPurchases, minDollarAmountPurchased);
    }

    /**
     * Get <code>public.rewards_report</code> as a table.
     */
    public static RewardsReport rewardsReport(Field<Integer> minMonthlyPurchases, Field<BigDecimal> minDollarAmountPurchased) {
        return RewardsReport.REWARDS_REPORT.call(minMonthlyPurchases, minDollarAmountPurchased);
    }
}
