package ecc.hibernate.xml.service;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.dto.RoleDTO;
import ecc.hibernate.xml.dao.RoleDao;
import ecc.hibernate.xml.dao.GenericDao;

public class RoleService{
    private RoleDao roleDao;

    public RoleService() {
        roleDao = new RoleDao();
    }

    public void saveOrUpdate(RoleDTO roleDTO ) {
        roleDao.saveOrUpdate(dtoToEntity(roleDTO));
    }

    public RoleDTO find(Long id) {
        return entityToDTO(roleDao.find(id));
    }

    public List findAll() {
        List<RoleDTO> rolesDTO = new ArrayList();
        List<Role> roles = roleDao.findAll(Role.class);
        for (Role role : roles) {
            rolesDTO.add(entityToDTO(role));
        }
        return rolesDTO;
    }

    public void delete(RoleDTO roleDTO) {
        roleDao.delete(dtoToEntity(roleDTO));
    }

    public boolean isEmpty() {
        return (roleDao.findAll(Role.class).size() == 0);
    }


    public boolean roleExist(RoleDTO roleDTO) {
        Role role = dtoToEntity(roleDTO);
        return roleDao.exists(Role.class, "roleName", role.getRoleName());
    }

    public boolean roleNotUsed(RoleDTO roleDTO) {
        Role role = dtoToEntity(roleDTO);
        return (role.getPerson().size() == 0);
    }

    public String convertListToString(List<RoleDTO> roles) {
        String rolesString = "";
        for(RoleDTO role : roles) {
            rolesString += "   " + role.getId() + "   " + role.getRoleName() + "\n";
        }
        return rolesString;
    }

    public String convertSetToString(Set<RoleDTO> roles) {
        String rolesString = "";
        int count = 0;
        for(RoleDTO role : roles) {
            rolesString += role.getRoleName();
            count++;
            if (count < roles.size()) {
                rolesString += ",";
            }
        }
        return rolesString;
    }


    public Role dtoToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setRoleName(roleDTO.getRoleName());
        role.setPerson(roleDTO.getPerson());
        return role;
    }
    public RoleDTO entityToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setRoleName(role.getRoleName());
        roleDTO.setPerson(role.getPerson());
        return roleDTO;
    }


}
