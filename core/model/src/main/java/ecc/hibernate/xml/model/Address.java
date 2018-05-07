package ecc.hibernate.xml.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "street", nullable = false, length = 255)
    private String street;
    
    @Column(name = "barangay", nullable = false, length = 255)
    private String barangay;

    @Column(name = "city", nullable = false, length = 255)
    private String city;
    
    @Column(name = "zip", nullable = false)
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