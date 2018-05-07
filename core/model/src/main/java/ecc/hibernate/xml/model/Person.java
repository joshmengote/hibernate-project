package ecc.hibernate.xml.model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "gwa")
    private Float gwa;
   
    @Column(name = "birthdate")
    private Date birthdate;
   
    @Column(name = "date_hired")
    private Date dateHired;
   
    @Column(name = "currently_employed")
    private Boolean currentlyEmployed;
    
    @Embedded
    private Name name;
   
    @Embedded
    private Address address;
    
    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE})  
    @JoinTable(name="person_role", joinColumns=@JoinColumn(name="person_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Role> roles = new HashSet<Role>();

    @OneToMany
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "person_id") 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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