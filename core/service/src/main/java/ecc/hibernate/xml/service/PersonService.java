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
    private static final int DESCENDING = 1;
    private PersonDao personDao;
    private RoleDao roleDao;
    private NameService nameService;
    private AddressService addressService;
    private RoleService roleService;
    private ContactService contactService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper;             

    public PersonService() {
        personDao = new PersonDao();
        roleDao = new RoleDao();
        nameService = new NameService();
        addressService = new AddressService();
        roleService = new RoleService();
        contactService = new ContactService();
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
        mapper = mapperFactory.getMapperFacade();
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
        for (ContactDTO contact : personDTO.getContacts()) {
            if( contact.getInformation().equals(contactDTO.getInformation())) {
                personDTO.getContacts().remove(contact);
            }
        }
        personDao.saveOrUpdate(dtoToEntity(personDTO));
    }

    private String personObjectToString(PersonDTO personDTO) {
        Person person = dtoToEntity(personDTO);
        Name name = person.getName();
        Address address = person.getAddress();
        Set<RoleDTO> roles = personDTO.getRoles();
        Set<ContactDTO> contacts = personDTO.getContacts();
        String currentlyEmployed;
        currentlyEmployed = (person.getCurrentlyEmployed() ? "YES" : "NO"); 

        String idString = String.format("| %-3s|",person.getId());
        String nameString = String.format(" %-50s|",nameService.nameToString(name));
        String addressString = String.format(" %-50s|",addressService.addressToString(address));
        String bdayString = String.format(" %-10s |", dateFormat.format(person.getBirthdate()));
        String dateHiredString = String.format(" %-10s |", dateFormat.format(person.getDateHired()));
        String gwaString = String.format(" %-4s |", person.getGwa());
        String currentlyEmployedString = String.format("%8s%-12s|", "", currentlyEmployed);
        String roleString = String.format(" %-30s|", roleService.convertSetToString(roles));
        String contactString = String.format(" %-49s|", contactService.convertSetToString(contacts));

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
        Person person = dtoToEntity(personDTO);
        String currentlyEmployed = ((person.getCurrentlyEmployed()) ? "YES" : "NO");
        String personInfo = "   Person ID #: " + person.getId() 
            + "\n   Name: " + nameService.nameToString(person.getName()) 
            + "\n   Address: " + addressService.addressToString(person.getAddress())  
            + "\n   Birthdate: " + dateFormat.format(person.getBirthdate()) 
            + "\n   GWA: " + person.getGwa() 
            + "\n   Date Hired: " + dateFormat.format(person.getDateHired())  
            + "\n   Employment Status:  Currently Employed = " + currentlyEmployed;
        return personInfo;
    }

    public List getAvailableRolesFor(PersonDTO personDTO) {
        Person person = dtoToEntity(personDTO);
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

    public boolean hasAvailableRolesFor(PersonDTO personDTO) {
        return (getAvailableRolesFor(personDTO).size() == 0) ? false : true;
    }

    public boolean roleIsAvailable(PersonDTO personDTO, RoleDTO roleDTO) {
        Role role = roleService.dtoToEntity(roleDTO);
        List<RoleDTO> availableRoles = getAvailableRolesFor(personDTO);
        boolean isAvailable = true;
        for (RoleDTO personRole : availableRoles) {
            if (personRole.getRoleName().equals(role.getRoleName())){
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
