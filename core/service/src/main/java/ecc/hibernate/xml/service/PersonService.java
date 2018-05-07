package ecc.hibernate.xml.service;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import ecc.hibernate.xml.model.*;
import ecc.hibernate.xml.dto.*;
import ecc.hibernate.xml.dao.PersonDao;
import ecc.hibernate.xml.dao.RoleDao;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
public class PersonService {
    private static final int ASCENDING = 1;
    private static final int DESCENDING = 2;
    private static PersonDao personDao = new PersonDao();
    private static RoleDao roleDao = new RoleDao();
    private static RoleService roleService = new RoleService();
    private static ContactService contactService = new ContactService();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private static MapperFacade mapper = mapperFactory.getMapperFacade();

    public PersonService() {
        mapperFactory.classMap(Person.class, PersonDTO.class).byDefault()
                        .field("name.firstName", "firstName")
                        .field("name.middleName", "middleName")
                        .field("name.lastName", "lastName")
                        .field("name.title", "title")
                        .field("name.suffix", "suffix")
                        .field("address.street", "street")
                        .field("address.barangay", "barangay")
                        .field("address.city", "city")
                        .field("address.zipCode", "zipCode")
                        .register();
    }

    public void saveOrUpdate(PersonDTO personDTO) {
        personDao.saveOrUpdate(dtoToEntity(personDTO));
    }

    public List findAllByLastName(int direction) {
        List<Person> people = personDao.findAllByLastName(direction);
        List<PersonDTO> peopleDTO = new ArrayList();
        for (Person person : people) {
            peopleDTO.add(entityToDTO(person));
        }
        return peopleDTO;
    }

    public List findAllByDateHired(int direction) {
        List<Person> people = personDao.findAllByDateHired(direction);
        List<PersonDTO> peopleDTO = new ArrayList();
        for (Person person : people) {
            peopleDTO.add(entityToDTO(person));
        }
        return peopleDTO;
    }

    public List sortByGwa (List<PersonDTO> peopleDTO, int order) {
        List<Person> people = new ArrayList();
        for (PersonDTO personDTO : peopleDTO) {
            people.add(dtoToEntity(personDTO));
        }
        Collections.sort(people, ((order == ASCENDING) ? Person.gwaAscending : Person.gwaDescending));
        peopleDTO.clear();
        for (Person person : people) {
            peopleDTO.add(entityToDTO(person));
        }
        return peopleDTO;
    }
    public List findAll() {
        List<Person> people = personDao.findAll();
        List<PersonDTO> peopleDTO = new ArrayList();
        for (Person person : people) {
            peopleDTO.add(entityToDTO(person));
        }
        return peopleDTO;
    }

    public PersonDTO find(Long id) {
        return entityToDTO(personDao.find(id));
    }
    public void delete(PersonDTO personDTO) {
        personDao.delete(dtoToEntity(personDTO));
    }

    public void removeContact(PersonDTO personDTO, ContactDTO contactDTO) {
        ContactDTO contactToBeRemoved = new ContactDTO();
        for (ContactDTO contact : personDTO.getContacts()) {
            if( contact.getInformation().equals(contactDTO.getInformation())) {
                contactToBeRemoved = contact;
            }
        }
        personDTO.getContacts().remove(contactToBeRemoved);
        personDao.saveOrUpdate(dtoToEntity(personDTO));
    }

    private String personObjectToString(PersonDTO personDTO) {

        String idString = String.format("| %-3s|", personDTO.getId());
        String nameString = String.format(" %-50s|", personDTO.getFullName());
        String addressString = String.format(" %-50s|", personDTO.getFullAddress());
        String bdayString = String.format(" %-10s |", dateFormat.format(personDTO.getBirthdate()));
        String dateHiredString = String.format(" %-10s |", dateFormat.format(personDTO.getDateHired()));
        String gwaString = String.format(" %-4s |", personDTO.getGwa());
        String currentlyEmployed = (personDTO.getCurrentlyEmployed() ? "YES" : "NO"); 
        String currentlyEmployedString = String.format("%8s%-12s|", "", currentlyEmployed);
        String roleString = String.format(" %-30s|", roleService.convertSetToString(personDTO.getRoles()));
        String contactString = String.format(" %-49s|", contactService.convertSetToString(personDTO.getContacts()));

        String personString = idString + nameString + addressString + bdayString + gwaString + dateHiredString + currentlyEmployedString + roleString + contactString;
        return personString;
    }

    public String convertListToString(List<PersonDTO> peopleDTO) {
        String peopleString = "";
        for(PersonDTO personDTO : peopleDTO) {
            peopleString += personObjectToString(personDTO) + "\n";
        }
        return peopleString;
    }

    public String getPersonInfoAsString(PersonDTO personDTO) {
        String currentlyEmployed = ((personDTO.getCurrentlyEmployed()) ? "YES" : "NO");
        String personInfo = "   Person ID #: " + personDTO.getId() 
            + "\n   Name: " + personDTO.getFullName() 
            + "\n   Address: " + personDTO.getFullAddress()  
            + "\n   Birthdate: " + dateFormat.format(personDTO.getBirthdate()) 
            + "\n   GWA: " + personDTO.getGwa() 
            + "\n   Date Hired: " + dateFormat.format(personDTO.getDateHired())  
            + "\n   Employment Status:  Currently Employed = " + currentlyEmployed;
        return personInfo;
    }

    public List getAvailableRolesFor(Long id) {
        Person person = personDao.find(id);
        List<Role> roles = roleDao.findAll(Role.class);
        List<Role> personRolesList = new ArrayList(person.getRoles());
        List<Role> intersection = new ArrayList();
        for (Role role : roles) {
            for(int i=0; i<personRolesList.size(); i++) {
                if (role.getRoleName().equals(personRolesList.get(i).getRoleName())) {
                    intersection.add(role);
                }
            }
        }
        roles.removeAll(intersection);
        List<RoleDTO> rolesDTO = new ArrayList();
        for (Role role : roles) {
            rolesDTO.add(roleService.entityToDTO(role));
        }
        return rolesDTO;
    }

    public boolean hasAvailableRolesFor(Long id) {
        return (getAvailableRolesFor(id).size() == 0) ? false : true;
    }

    public boolean roleIsAvailable(Long id, String roleDtoName) {
        List<RoleDTO> availableRoles = getAvailableRolesFor(id);
        boolean isAvailable = true;
        for (RoleDTO personRole : availableRoles) {
            if (personRole.getRoleName().equals(roleDtoName)) {
                isAvailable = false;
            }
        }
        return isAvailable;
    }

    public boolean isEmpty() {
        return (personDao.findAll().size() == 0);
    }

    private PersonDTO entityToDTO(Person person) {
        return mapper.map(person, PersonDTO.class);

    }
    private Person dtoToEntity(PersonDTO personDTO) {
        return mapper.map(personDTO, Person.class);
    }   
}
