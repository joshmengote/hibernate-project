package ecc.hibernate.xml.dto;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class PersonDTO {
	private Long id;
    private Float gwa;
    private Date birthdate;
    private Date dateHired;
	private Boolean currentlyEmployed;

    private String firstName;
    private String middleName;
    private String lastName;
    private String title;
    private String suffix;

    private String street;
    private String barangay;
    private String city;
    private Integer zipCode;

    private Set<RoleDTO> roles = new HashSet();
    private Set<ContactDTO> contacts = new HashSet();

	public PersonDTO() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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

	public Set<ContactDTO> getContacts() {
		return contacts;
	}
    public void setContacts(Set<ContactDTO> contacts) {
    	this.contacts = contacts;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }
    public void setRoles(Set<RoleDTO> roles) {
    	this.roles = roles;
    }

}