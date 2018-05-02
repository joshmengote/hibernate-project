package ecc.hibernate.xml.dto;

import java.util.Set;
import java.util.HashSet;
import ecc.hibernate.xml.model.Person;

public class RoleDTO {
    private Long id;
    private String roleName;
    private Set<Person> person = new HashSet<Person>();

    public RoleDTO() {}
    public RoleDTO(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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