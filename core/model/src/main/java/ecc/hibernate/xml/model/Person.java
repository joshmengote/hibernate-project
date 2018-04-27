package ecc.hibernate.xml.model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

public class Person {
	private Long id;
	private Float gwa;
	private Date birthdate;
	private Date dateHired;
	private Boolean currentlyEmployed;
	
	private Name name;
	private Address address;
	private Set<Role> roles = new HashSet<Role>();
	private Set<Contact> contacts = new HashSet<Contact>();

	public Person() {}
	public Person(Name name, Address address, Float gwa, Boolean currentlyEmployed,Date birthdate, Date dateHired) {
		this.name = name;
		this.address = address;
		this.gwa = gwa;
		this.currentlyEmployed = currentlyEmployed;
		this.birthdate = birthdate;
		this.dateHired = dateHired;
	}
	
	public Long getId() {
		return id;
	}
	private void setId(Long id) {
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

	public Float getGwa() {
		return gwa;
	}
	public void setGwa(Float gwa) {
		this.gwa = gwa;
	}

	public Boolean getCurrentlyEmployed() {
		return currentlyEmployed;
	}
	public void setCurrentlyEmployed(Boolean currentlyEmployed) {
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

    public static Comparator<Person> gwaAscending = new Comparator<Person>() {
        public int compare(Person o1, Person o2) {
            return Float.compare(o2.getGwa(), o1.getGwa());
        }
    };

    public static Comparator<Person> gwaDescending = new Comparator<Person>() {
    	public int compare(Person o1, Person o2) {
    		return Float.compare(o1.getGwa(), o2.getGwa());
    	}
    };
}