package ecc.hibernate.xml.service;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import ecc.hibernate.xml.model.Name;
import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.model.Address;
import ecc.hibernate.xml.model.Person;
import ecc.hibernate.xml.model.Contact;

import ecc.hibernate.xml.dao.PersonDao;
import ecc.hibernate.xml.dao.RoleDao;


public class PersonService{

    private PersonDao personDao;
    private RoleDao roleDao;
    private NameService nameService;
    private AddressService addressService;
    private RoleService roleService;
    private ContactService contactService;

    public PersonService() {
        personDao = new PersonDao();
        roleDao = new RoleDao();
        nameService = new NameService();
        addressService = new AddressService();
        roleService = new RoleService();
        contactService = new ContactService();
    }

    public void saveOrUpdate(Person person) {
        personDao.saveOrUpdate(person);
    }

    public List sort(int parameter, int order) {
        return personDao.sort(parameter,order);
    }

    public List findAll() {
        return personDao.findAll();
    }
    public Person find(Long id) {
        return personDao.find(id);
    }
    public void delete(Person person) {
        personDao.delete(person);
    }

    private String personObjectToString(Person person) {
        Name name = person.getName();
        Address address = person.getAddress();
        Set<Role> roles = person.getRoles();
        Set<Contact> contacts = person.getContacts();
        String currentlyEmployed;
        if (person.getCurrentlyEmployed()) {
            currentlyEmployed = "YES";
        } else {
            currentlyEmployed = "NO";
        }

        String idString = String.format("| %-3s|",person.getId());
        String nameString = String.format(" %-50s|",nameService.nameToString(name));
        String addressString = String.format(" %-50s|",addressService.addressToString(address));
        String bdayString = String.format(" %-10s |", person.getBirthdate());
        String dateHiredString = String.format(" %-10s |", person.getDateHired());
        String gwaString = String.format(" %-4s |", person.getGwa());
        String currentlyEmployedString = String.format("%8s%-12s|", "", currentlyEmployed);
        String roleString = String.format(" %-30s|", roleService.convertSetToString(roles));
        String contactString = String.format(" %-49s|", contactService.convertSetToString(contacts));

        String personString = idString + nameString + addressString + bdayString + gwaString + dateHiredString + currentlyEmployedString + roleString + contactString;
        return personString;
    }

    public String convertListToString(List<Person> people) {
        String peopleString = "";
        for(Person person : people) {
            peopleString += personObjectToString(person) + "\n";
        }
        return peopleString;
    }

    public String getPersonInfoAsString(Person person) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String personInfo = "   Person ID #: " + person.getId() 
            + "\n   Name: " + nameService.nameToString(person.getName()) 
            + "\n   Address: " + addressService.addressToString(person.getAddress())  
            + "\n   Birthdate: " + dateFormat.format(person.getBirthdate()) 
            + "\n   GWA: " + person.getGwa() 
            + "\n   Date Hired: " + dateFormat.format(person.getBirthdate())  
            + "\n   Employment Status:  Currently Employed = " + person.getCurrentlyEmployed();
        return personInfo;
    }

    public List getAvailableRolesFor(Person person) {
        List<Role> roles = roleDao.findAll();
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
        return roles;
    }

    public boolean hasAvailableRolesFor(Person person) {
        if (getAvailableRolesFor(person).size() == 0){
            return false;
        }
        return true;
    }

    public boolean roleIsAvailable(Person person, Role role) {
        List<Role> availableRoles = getAvailableRolesFor(person);
        boolean isAvailable = true;
        for (Role personRole : availableRoles) {
            if (personRole.getRoleName().equals(role.getRoleName())){
                isAvailable = false;
            }
        }
        return isAvailable;
    }

    public boolean isEmpty() {
        return (personDao.findAll().size() == 0);
    }
}
