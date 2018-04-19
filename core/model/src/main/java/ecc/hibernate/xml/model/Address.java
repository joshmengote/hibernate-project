package ecc.hibernate.xml.model;

import java.util.Set;
import java.util.HashSet;

public class Address {
	private String street;
	private String barangay;
	private String city;
	private int zipCode;
	
	private Set<Person> person = new HashSet<Person>();

	public Address() {}
	public Address(String street, String barangay, String city, int zipCode) {
		this.street = street;
		this.barangay = barangay;
		this.city = city;
		this.zipCode = zipCode;
	}

    public Set<Person> getPerson() {
        return person;
    }

    public void setPerson(Set<Person> person) {
    	this.person = person;
    }

	public String getStreet(){
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}

	public String getBarangay() {
		return barangay;
	}
	public void setBarangay(String barangay) {
		this.barangay = barangay;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
}