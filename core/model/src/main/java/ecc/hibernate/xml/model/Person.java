package ecc.hibernate.xml.model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class Person {
	private long id;
	private float gwa;
	private Date birthdate;
	private Date dateHired;
	private boolean currentlyEmployed;
	
	private Name name;
	private Address address;
	private Set<Role> roles = new HashSet<Role>();
	private Set<Contact> contacts = new HashSet<Contact>();

	public Person() {}
	public Person(Name name, Address address, float gwa, boolean currentlyEmployed,Date birthdate, Date dateHired) {
		this.name = name;
		this.address = address;
		this.gwa = gwa;
		this.currentlyEmployed = currentlyEmployed;
		this.birthdate = birthdate;
		this.dateHired = dateHired;
	}
	
	public long getId() {
		return id;
	}
	private void setId(long id) {
		this.id = id;
	}

	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getDateHired() {
		return dateHired;
	}
	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public float getGwa() {
		return gwa;
	}
	public void setGwa(float gwa) {
		this.gwa = gwa;
	}

	public boolean getCurrentlyEmployed() {
		return currentlyEmployed;
	}
	public void setCurrentlyEmployed(boolean currentlyEmployed) {
		this.currentlyEmployed = currentlyEmployed;
	}

	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}
    public void setContacts(Set<Contact> contacts) {
    	this.contacts = contacts;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
    	this.roles = roles;
    }
}