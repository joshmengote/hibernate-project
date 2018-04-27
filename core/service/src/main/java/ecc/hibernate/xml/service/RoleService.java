package ecc.hibernate.xml.service;

import java.util.Set;
import java.util.List;

import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.dao.RoleDao;

public class RoleService{

    public static void saveOrUpdate(Role role ) {
        RoleDao.saveOrUpdate(role);
    }

    public static Role find(Long id) {
        return RoleDao.find(id);
    }

    public static List findAll() {
        return RoleDao.findAll();
    }

    public static void delete(Role role) {
        RoleDao.delete(role);
    }

    public static boolean isEmpty() {
        return (RoleDao.findAll().size() == 0);
    }


    public static boolean roleExist(Role role) {
        return RoleDao.roleExist(role.getRoleName());
    }

    public static String convertListToString(List<Role> roles) {
        String rolesString = "";
        for(Role role : roles) {
            rolesString += "   " + role.getId() + "   " + role.getRoleName() + "\n";
        }
        return rolesString;
    }

    public static String convertSetToString(Set<Role> roles) {
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
