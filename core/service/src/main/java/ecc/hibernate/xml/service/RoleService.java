package ecc.hibernate.xml.service;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.dto.RoleDTO;
import ecc.hibernate.xml.dao.RoleDao;
import ecc.hibernate.xml.dao.GenericDao;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class RoleService{
    private RoleDao roleDao;
    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper;

    public RoleService() {
        roleDao = new RoleDao();
        mapperFactory.classMap(Role.class, RoleDTO.class).byDefault();
        mapper = mapperFactory.getMapperFacade();
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

    public boolean roleNotUsed(Long id) {
        return roleDao.find(id).getPerson().size() == 0;
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

    public RoleDTO entityToDTO(Role role) {
        return mapper.map(role, RoleDTO.class);

    }
    public Role dtoToEntity(RoleDTO roleDTO) {
        return mapper.map(roleDTO, Role.class);
    } 

}
