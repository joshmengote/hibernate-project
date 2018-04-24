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


        String idString = String.format("| %-3s|",person.getId());
        String nameString = String.format(" %-50s|",NameService.nameToString(name));
        String addressString = String.format(" %-50s|",AddressService.addressToString(address));
        String bdayString = String.format(" %-10s |", person.getBirthdate());
        String dateHiredString = String.format(" %-10s |", person.getDateHired());
        String gwaString = String.format(" %-4s |", person.getGwa());
        String currentlyEmployedString = String.format("%8s%-12s|", "", person.getCurrentlyEmployed());
        String roleString = String.format(" %-30s|", RoleService.roleSetToString(roles));
        String contactString = String.format(" %-49s|", ContactService.contactSetToString(contacts));

        personString = idString + nameString + addressString + bdayString + gwaString + dateHiredString + currentlyEmployedString + roleString + contactString;
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
