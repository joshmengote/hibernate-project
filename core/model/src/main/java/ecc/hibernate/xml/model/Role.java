package ecc.hibernate.xml.model;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;

@Entity
@Table(name = "Roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String roleName;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="roles") 
    private Set<Person> person = new HashSet<Person>();

    public Role() {}
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }
    private void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Person> getPerson() {
        return person;
    }
    public void setPerson(Set<Person> person) {
        this.person = person;
    }
}