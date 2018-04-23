package ecc.hibernate.xml.service;

import java.util.List;
import java.util.Set;

import ecc.hibernate.xml.model.Role;

public class RoleService{

    public static String convertListToString(List<Role> roles) {
        String rolesString = "";
        for(Role role : roles) {
            rolesString += "   " + role.getId() + "   " + role.getRoleName() + "\n";
        }
        return rolesString;
    }

    public static String roleSetToString(Set<Role> roles) {
        String rolesString = "";
        for(Role role : roles) {
            rolesString += role.getRoleName();
            if (roles.size() != 1) {
                rolesString += ",";
            }
        }
        return rolesString;
    }

}
