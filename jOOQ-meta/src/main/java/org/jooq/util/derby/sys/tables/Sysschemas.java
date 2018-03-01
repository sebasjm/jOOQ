/**
 * This class is generated by jOOQ
 */
package org.jooq.util.derby.sys.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "3.0.0"},
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings("all")
public class Sysschemas extends org.jooq.impl.TableImpl<org.jooq.Record> {

	private static final long serialVersionUID = 1872180098;

	/**
	 * The singleton instance of <code>SYS.SYSSCHEMAS</code>
	 */
	public static final org.jooq.util.derby.sys.tables.Sysschemas SYSSCHEMAS = new org.jooq.util.derby.sys.tables.Sysschemas();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.Record> getRecordType() {
		return org.jooq.Record.class;
	}

	/**
	 * The column <code>SYS.SYSSCHEMAS.SCHEMAID</code>.
	 */
	public static final org.jooq.TableField<org.jooq.Record, java.lang.String> SCHEMAID = createField("SCHEMAID", org.jooq.impl.SQLDataType.CHAR, SYSSCHEMAS);

	/**
	 * The column <code>SYS.SYSSCHEMAS.SCHEMANAME</code>.
	 */
	public static final org.jooq.TableField<org.jooq.Record, java.lang.String> SCHEMANAME = createField("SCHEMANAME", org.jooq.impl.SQLDataType.VARCHAR, SYSSCHEMAS);

	/**
	 * The column <code>SYS.SYSSCHEMAS.AUTHORIZATIONID</code>.
	 */
	public static final org.jooq.TableField<org.jooq.Record, java.lang.String> AUTHORIZATIONID = createField("AUTHORIZATIONID", org.jooq.impl.SQLDataType.VARCHAR, SYSSCHEMAS);

	/**
	 * No further instances allowed
	 */
	private Sysschemas() {
		super("SYSSCHEMAS", org.jooq.util.derby.sys.Sys.SYS);
	}
}
