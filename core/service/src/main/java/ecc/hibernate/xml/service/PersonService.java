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

    public static void saveOrUpdate(Person person) {
        PersonDao.saveOrUpdate(person);
    }

    public static List sort(int parameter, int order) {
        return PersonDao.sort(parameter,order);
    }

    public static List findAll() {
        return PersonDao.findAll();
    }
    public static Person find(Long id) {
        return PersonDao.find(id);
    }
    public static void delete(Person person) {
        PersonDao.delete(person);
    }

    private static String personObjectToString(Person person) {
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
        String nameString = String.format(" %-50s|",NameService.nameToString(name));
        String addressString = String.format(" %-50s|",AddressService.addressToString(address));
        String bdayString = String.format(" %-10s |", person.getBirthdate());
        String dateHiredString = String.format(" %-10s |", person.getDateHired());
        String gwaString = String.format(" %-4s |", person.getGwa());
        String currentlyEmployedString = String.format("%8s%-12s|", "", currentlyEmployed);
        String roleString = String.format(" %-30s|", RoleService.convertSetToString(roles));
        String contactString = String.format(" %-49s|", ContactService.convertSetToString(contacts));

        String personString = idString + nameString + addressString + bdayString + gwaString + dateHiredString + currentlyEmployedString + roleString + contactString;
        return personString;
    }

    public static String convertListToString(List<Person> people) {
        String peopleString = "";
        for(Person person : people) {
            peopleString += personObjectToString(person) + "\n";
        }
        return peopleString;
    }

    public static String getPersonInfoAsString(Person person) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String personInfo = "   Person ID #: " + person.getId() 
            + "\n   Name: " + NameService.nameToString(person.getName()) 
            + "\n   Address: " + AddressService.addressToString(person.getAddress())  
            + "\n   Birthdate: " + dateFormat.format(person.getBirthdate()) 
            + "\n   GWA: " + person.getGwa() 
            + "\n   Date Hired: " + dateFormat.format(person.getBirthdate())  
            + "\n   Employment Status:  Currently Employed = " + person.getCurrentlyEmployed();
        return personInfo;
    }

    public static List getAvailableRolesFor(Person person) {
        List<Role> roles = RoleDao.findAll();
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

    public static boolean hasAvailableRolesFor(Person person) {
        if (getAvailableRolesFor(person).size() == 0){
            return false;
        }
        return true;
    }

    public static boolean roleIsAvailable(Person person, Role role) {
        List<Role> availableRoles = getAvailableRolesFor(person);
        boolean isAvailable = true;
        for (Role personRole : availableRoles) {
            if (personRole.getRoleName().equals(role.getRoleName())){
                isAvailable = false;
            }
        }
        return isAvailable;
    }

    public static boolean isEmpty() {
        return (PersonDao.findAll().size() == 0);
    }
}
