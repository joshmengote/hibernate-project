package ecc.hibernate.xml.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Name {
    @Column(name = "first_name", nullable = false, length =255)
    private String firstName;

    @Column(name = "middle_name", nullable = false, length =255)
    private String middleName;
    
    @Column(name = "last_name", nullable = false, length =255)
    private String lastName;
    
    @Column(name = "suffix", nullable = false, length =255)
    private String suffix;
    
    @Column(name = "title", nullable = false, length =255)
    private String title;
    
    public Name() {}
    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
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
    
}