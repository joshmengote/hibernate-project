package ecc.hibernate.xml.model;

import java.util.Set;
import java.util.HashSet;

public class Address {
	private String street;
	private String barangay;
	private String city;
	private Integer zipCode;
	
	public Address() {}
	public Address(String street, String barangay, String city, Integer zipCode) {
		this.street = street;
		this.barangay = barangay;
		this.city = city;
		this.zipCode = zipCode;
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

	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
}