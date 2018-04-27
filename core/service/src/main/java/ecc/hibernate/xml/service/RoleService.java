package ecc.hibernate.xml.service;

import java.util.Set;
import java.util.List;

import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.dao.RoleDao;

public class RoleService{

    private RoleDao roleDao;

    public RoleService() {
        roleDao = new RoleDao();
    }

    public void saveOrUpdate(Role role ) {
        roleDao.saveOrUpdate(role);
    }

    public Role find(Long id) {
        return roleDao.find(id);
    }

    public List findAll() {
        return roleDao.findAll();
    }

    public void delete(Role role) {
        roleDao.delete(role);
    }

    public boolean isEmpty() {
        return (roleDao.findAll().size() == 0);
    }


    public boolean roleExist(Role role) {
        return roleDao.roleExist(role.getRoleName());
    }

    public String convertListToString(List<Role> roles) {
        String rolesString = "";
        for(Role role : roles) {
            rolesString += "   " + role.getId() + "   " + role.getRoleName() + "\n";
        }
        return rolesString;
    }

    public String convertSetToString(Set<Role> roles) {
        String rolesString = "";
        int count = 0;
        for(Role role : roles) {
            rolesString += role.getRoleName();
            count++;
            if (count < roles.size()) {
                rolesString += ",";
            }
        }
        return rolesString;
    }


}
