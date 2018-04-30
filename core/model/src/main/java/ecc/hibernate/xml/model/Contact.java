package ecc.hibernate.xml.model;

import javax.persistence.*;

@Entity
@Table(name = "contact", uniqueConstraints = {
        @UniqueConstraint(columnNames = "information")})
public class Contact {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")    
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "information")
    private String information;
    
    public Contact() {}
    public Contact(String type, String information) {
        this.type = type;
        this.information = information;
    }

    public Long getId() {
        return id;
    }
    private void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }
    public void setInformation(String information) {
        this.information = information;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}