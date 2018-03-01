/**
 * This class is generated by jOOQ
 */
package org.jooq.example.chart.db.routines;


import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Parameter;
import org.jooq.example.chart.db.Public;
import org.jooq.impl.AbstractRoutine;


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
public class GetCustomerBalance extends AbstractRoutine<BigDecimal> {

    private static final long serialVersionUID = -1878366530;

    /**
     * The parameter <code>public.get_customer_balance.RETURN_VALUE</code>.
     */
    public static final Parameter<BigDecimal> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.NUMERIC, false, false);

    /**
     * The parameter <code>public.get_customer_balance.p_customer_id</code>.
     */
    public static final Parameter<Integer> P_CUSTOMER_ID = createParameter("p_customer_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.get_customer_balance.p_effective_date</code>.
     */
    public static final Parameter<Timestamp> P_EFFECTIVE_DATE = createParameter("p_effective_date", org.jooq.impl.SQLDataType.TIMESTAMP, false, false);

    /**
     * Create a new routine call instance
     */
    public GetCustomerBalance() {
        super("get_customer_balance", Public.PUBLIC, org.jooq.impl.SQLDataType.NUMERIC);

        setReturnParameter(RETURN_VALUE);
        addInParameter(P_CUSTOMER_ID);
        addInParameter(P_EFFECTIVE_DATE);
    }

    /**
     * Set the <code>p_customer_id</code> parameter IN value to the routine
     */
    public void setPCustomerId(Integer value) {
        setValue(P_CUSTOMER_ID, value);
    }

    /**
     * Set the <code>p_customer_id</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPCustomerId(Field<Integer> field) {
        setField(P_CUSTOMER_ID, field);
    }

    /**
     * Set the <code>p_effective_date</code> parameter IN value to the routine
     */
    public void setPEffectiveDate(Timestamp value) {
        setValue(P_EFFECTIVE_DATE, value);
    }

    /**
     * Set the <code>p_effective_date</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPEffectiveDate(Field<Timestamp> field) {
        setField(P_EFFECTIVE_DATE, field);
    }
}
