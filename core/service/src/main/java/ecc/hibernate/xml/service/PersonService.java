package ecc.hibernate.xml.service;

import java.util.Date;
import java.util.Set;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import ecc.hibernate.xml.model.Name;
import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.model.Address;
import ecc.hibernate.xml.model.Person;
import ecc.hibernate.xml.model.Contact;
public class PersonService{

    public static Person createPersonEntry(Name name, Address address, Date birthdate, Date dateHired, float gwa, 
                                            boolean currentlyEmployed) {
        Role roleObject = null;
        Person person = new Person(name, address, gwa, currentlyEmployed, birthdate, dateHired);
        return person;
    }

    public static Date stringToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        try {
            date = format.parse(dateString); 
        } catch (ParseException e) { 
            System.out.println("Parse Exception");
        }
        return date;
    }

    public static String personObjectToString(Person person) {
        String personString;
        Name name = person.getName();
        Address address = person.getAddress();
        Set<Role> roles = person.getRoles();
        Set<Contact> contacts = person.getContacts();
        personString = "   " + person.getId() + " | " 
                        + String.format(NameService.nameToString(name), 50) + " | " 
                        + String.format(AddressService.addressToString(address), 50) + " | " 
                        + person.getBirthdate() + " | " 
                        + person.getGwa() + " | " 
                        + person.getDateHired() + " | " 
                        + person.getCurrentlyEmployed() + " | "
                        + RoleService.roleSetToString(roles) + " | "
                        + ContactService.contactSetToString(contacts) + " | "; 
        return personString;
    }

    public static String convertListToString(List<Person> people) {
        String peopleString = "";
        for(Person person : people) {
            peopleString += personObjectToString(person) + "\n";
        }
        return peopleString;
    }

    public static String getPersonInfo(Person person) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Name nameObject;
        Address addressObject;
        Date birthdateObject;
        Date dateHiredObject;
        float gwa;
        boolean currentlyEmployed;
        long personId;

        String nameString;
        String addressString;
        String birthdateString;
        String dateHiredString;

        String personInfo;

        personId = person.getId();

        nameObject = person.getName();
        nameString = NameService.nameToString(nameObject);

        addressObject = person.getAddress();
        addressString = AddressService.addressToString(addressObject);

        currentlyEmployed = person.getCurrentlyEmployed();

        birthdateObject = person.getBirthdate();
        birthdateString = dateFormat.format(birthdateObject);

        dateHiredObject = person.getBirthdate();
        dateHiredString = dateFormat.format(dateHiredObject);

        gwa = person.getGwa();

        personInfo = "   Person ID #: " + personId 
            + "\n   Name: " + nameString 
            + "\n   Address: " + addressString  
            + "\n   Birthdate: " + birthdateString 
            + "\n   GWA: " + gwa 
            + "\n   Date Hired: " + dateHiredString  
            + "\n   Employment Status:  Currently Employed = " + currentlyEmployed;

        return personInfo;
        }
}
