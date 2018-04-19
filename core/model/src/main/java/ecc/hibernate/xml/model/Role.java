package ecc.hibernate.xml.model;

import java.util.Set;
import java.util.HashSet;

public class Role {
    private long id;
    private String roleName;

    private Set<Person> person = new HashSet<Person>();

    public Role() {}
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public long getId() {
        return id;
    }
    private void setId(long id) {
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