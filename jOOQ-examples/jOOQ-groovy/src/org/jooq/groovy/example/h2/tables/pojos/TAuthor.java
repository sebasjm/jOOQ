/**
 * This class is generated by jOOQ
 */
package org.jooq.groovy.example.h2.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.2.1" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TAuthor implements java.io.Serializable {

	private static final long serialVersionUID = 1333576897;

	private java.lang.Integer  id;
	private java.lang.String   firstName;
	private java.lang.String   lastName;
	private java.sql.Timestamp dateOfBirth;
	private java.lang.Integer  yearOfBirth;
	private java.lang.String   address;

	public TAuthor() {}

	public TAuthor(
		java.lang.Integer  id,
		java.lang.String   firstName,
		java.lang.String   lastName,
		java.sql.Timestamp dateOfBirth,
		java.lang.Integer  yearOfBirth,
		java.lang.String   address
	) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.yearOfBirth = yearOfBirth;
		this.address = address;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}

	public java.lang.String getLastName() {
		return this.lastName;
	}

	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}

	public java.sql.Timestamp getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(java.sql.Timestamp dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public java.lang.Integer getYearOfBirth() {
		return this.yearOfBirth;
	}

	public void setYearOfBirth(java.lang.Integer yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public java.lang.String getAddress() {
		return this.address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}
}
