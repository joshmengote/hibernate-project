package ecc.hibernate.xml.app;

import java.util.Scanner;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;


import ecc.hibernate.xml.util.*;
import ecc.hibernate.xml.model.*;
import ecc.hibernate.xml.service.*;

public class Application {
    private static final String dashes = new String(new char[247]).replace("\0", "-");
    private static final String columnNames = String.format("| %-3s| %-50s| %-50s| %-10s | %-5s| %-10s | %-18s | %-30s| %-49s|", 
                                                            "ID", "NAME","ADDRESS","BIRTHDATE", "GWA", "DATE HIRED", "CURRENTLY EMPLOYED", "ROLES", "CONTACTS");
    
    private static Scanner scan = new Scanner(System.in);
    private static boolean valid;
    private static int selectedOption;

    public static void main(String[] args) {
        HibernateUtils.getSessionFactory();
        System.out.println();
        System.out.println("Welcome to ECC Exercise 7: Hibernate ORM");
        System.out.print("Press Enter to continue...");
        scan.nextLine();
        mainMenu();
    }

    private static void mainMenu() {
        System.out.println("::MAIN MENU::");
        System.out.println(" [1] PERSON MANAGEMENT"
                        + "\n [2] ROLE MANAGEMENT"
                        + "\n [3] EXIT");
        selectedOption = UserInputUtils.numberWithLimit(" Select option",3);
        switch(selectedOption) {
            case 1:
                personManagementMenu();
                break;
            case 2:
                roleManagementMenu();
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

    private static void personManagementMenu() {
        System.out.println();
        System.out.println("::PERSON MANAGEMENT::");
        System.out.println("  [1] CREATE PERSON ENTRY"
                        + "\n  [2] UPDATE PERSON ENTRY"
                        + "\n  [3] LIST PERSON ENTRIES"
                        + "\n  [4] DELETE PERSON ENTRY"
                        + "\n  [5] ADD ROLE TO A PERSON"
                        + "\n  [6] DELETE ROLE OF A PERSON"
                        + "\n  [7] ADD CONTACT"
                        + "\n  [8] UPDATE CONTACT"
                        + "\n  [9] DELETE CONTACT"
                        + "\n  [10] RETURN");
        selectedOption = UserInputUtils.numberWithLimit(" Select option",10);
        System.out.println();
        switch(selectedOption) {
            case 1: 
                System.out.println(" >> ADD PERSON <<");
                addPerson();
                break;
            case 2: 
                System.out.println(" >> UPDATE PERSON <<");
                updatePerson();
                break;
            case 3: 
                System.out.println(" >> LIST PERSON <<");
                printPersonListBy();
                break;
            case 4: 
                System.out.println(" >> DELETE PERSON <<");
                deletePerson();
                break;
            case 5: 
                System.out.println(" >> ADD ROLE TO PERSON <<");
                addPersonRole();
                break;
            case 6: 
                System.out.println(" >> REMOVE ROLE FROM PERSON <<");
                deletePersonRole();
                break;
            case 7: 
                System.out.println(" >> ADD CONTACT <<");
                addContact();
                break;
            case 8: 
                System.out.println(" >> UPDATE CONTACT <<");
                updateContact();
                break;
            case 9: 
                System.out.println(" >> DELETE CONTACT <<");
                deleteContact();
                break;
            case 10:
                mainMenu();
                break;
        }
        personManagementMenu();
    }

    private static void roleManagementMenu() {
        System.out.println();
        System.out.println("::ROLE MANAGEMENT::");
        System.out.println(" [1] ADD ROLE"
                        + "\n [2] UPDATE ROLE"
                        + "\n [3] DELETE ROLE"
                        + "\n [4] LIST ROLE"
                        + "\n [5] RETURN");
        selectedOption = UserInputUtils.numberWithLimit(" Select option",5);
        System.out.println();
        switch(selectedOption) {
            case 1:
                System.out.println(" >> ADD NEW ROLE <<");
                addNewRole();
                break;
            case 2:
                System.out.println(" >> UPDATE AN EXISTING ROLE <<");
                updateExistingRole();
                break;
            case 3:
                System.out.println(" >> DELETE AN EXISTING ROLE <<<");
                deleteExistingRole();
                break;
            case 4:
                System.out.println(" >> LIST EXISTING ROLES <<<");
                listRoles();
                roleManagementMenu();
                break;
            case 5:
                mainMenu();
                break;
        }
    }

    private static void addNewRole() {
        listRoles();
        Role role = new Role();
        valid = false;
        while(!valid) {
            role.setRoleName(UserInputUtils.string("  Enter New Role: "));
            boolean roleExists = RoleService.roleExist(role);
            if (roleExists){
                valid = true;
            } else {
                System.out.println("\t Role already exist. Please try again. ");
            }
        }
        RoleService.saveOrUpdate(role);
        roleManagementMenu();
    }

    private static void updateExistingRole() {
        if (RoleService.isEmpty()) {
            System.out.println(" No Role added yet.");
            roleManagementMenu();
        }
        listRoles();
        Role role = selectRole("Update Role");
        String newRoleName = "";
        valid = false;
        while(!valid) {
            newRoleName = UserInputUtils.string("   Updated Role Entry: ");
            role.setRoleName(newRoleName);
            boolean roleExists = RoleService.roleExist(role);
            if (roleExists){
                    valid = true;
            } else {
                System.out.println("\t Role already exist. Please try again. ");
            }
        }
        RoleService.saveOrUpdate(role);
        roleManagementMenu();
    }

    private static void deleteExistingRole() {
        if (RoleService.isEmpty()) {
            System.out.println(" No Role added yet.");
            roleManagementMenu();
        }
        listRoles();
        valid = false;
        Role role = new Role();
        while(!valid) {
            role = selectRole("   Delete Role");
            if (role.getPerson().size() == 0) {
                RoleService.delete(role);
                valid = true;
            } else {
                System.out.println("\t Can't delete, role is in used!");
            }
        }
        roleManagementMenu();
    }

    private static void listRoles() {
        List roles = RoleService.findAll();
        String rolesString = RoleService.convertListToString(roles);
        System.out.println();
        System.out.println("  AVAILABLE ROLES\n");
        System.out.println("   ID  ROLE\n" + rolesString);
    }

    private static void addPerson() {
        if (RoleService.isEmpty()) {
            System.out.println(" Add roles before adding people.");
            personManagementMenu();
        }
        Person person = new Person();
        person = updatePersonProperties(person);
        System.out.println();

        System.out.println("  ROLES");
        boolean isFinishedAdding = false;
        while(!isFinishedAdding) {
            if(!PersonService.hasAvailableRolesFor(person)) {
                System.out.println("    No more available roles to add\n");
                break;
            }
            printAvailableRolesFor(person);
            Role role = selectPersonRole(person, "   Add Role");
            person.getRoles().add(role);
            System.out.println("   ADD ANOTHER ROLE? \n     [1]YES\n     [2]NO");
            selectedOption = UserInputUtils.numberWithLimit("   Enter option", 2);
            switch(selectedOption) {
                case 1:
                    System.out.println();
                    System.out.println("   Added Person Roles: " + RoleService.convertSetToString(person.getRoles()));
                    break;
                case 2:
                    isFinishedAdding = true;
                    break;
            }
        }  

        System.out.println("\n  CONTACT DETAILS");
        System.out.println("   ADD PERSONAL CONTACT INFORMATION? \n     [1]YES\n     [2]NO");
        selectedOption = UserInputUtils.numberWithLimit("   Enter option", 2);
        isFinishedAdding = false;
        while(!isFinishedAdding) {
            System.out.println("   ADD CONTACT");
            Contact newContact = new Contact();
            newContact = updateContactInformation(newContact, selectContactType());
            person.getContacts().add(newContact);
            System.out.println("   ADD ANOTHER CONTACT? \n     [1]YES\n     [2]NO");
            selectedOption = UserInputUtils.numberWithLimit("   Enter option", 2);
            if (selectedOption == 2) {
                isFinishedAdding = true;
            } else {
                System.out.println();
                System.out.println("  Added Person Contacts: " + ContactService.convertSetToString(person.getContacts()));
                System.out.println();
            }
        }
        PersonService.saveOrUpdate(person); 
    }

    private static void updatePerson() {
        if(PersonService.isEmpty()) {
            System.out.println(" No person added yet");
            return;
        }
        Person person = selectPersonFromList();
        System.out.println("  Current Data:");        
        String personInfo = PersonService.getPersonInfoAsString(person);
        System.out.println(personInfo);
        System.out.println();

        System.out.println("  UPDATE - Enter New Data:");
        person = updatePersonProperties(person);

        PersonService.saveOrUpdate(person);
    }

    private static void printPersonListBy() {
        List list = new ArrayList();
        System.out.println("  LIST BY:\n   [1] LAST NAME"
                                   + "\n   [2] DATE HIRED"
                                   + "\n   [3] GWA");
        int parameter = UserInputUtils.numberWithLimit("  Select option",3);

        System.out.println("  ORDER:\n   [1] ASCENDING"
                                 + "\n   [2] DESCENDING");
        int order = UserInputUtils.numberWithLimit("  Select option",2);
        if(parameter != 3) {
            list = PersonService.sort(parameter, order);
        } else {
            list = PersonService.findAll();
            if (order == 1) {
                Collections.sort(list, Person.gwaAscending);
            } else if (order == 2) {
                Collections.sort(list, Person.gwaDescending);
            }
        }
        String stringList = PersonService.convertListToString(list);
        System.out.println();
        System.out.println(columnNames);
        System.out.println(dashes);
        System.out.println(stringList);
        System.out.println();
    }
    
    private static void deletePerson() {
        if(PersonService.isEmpty()) {
            System.out.println(" No person added yet");
            return;
        }
        Person person = selectPersonFromList();
        PersonService.delete(person);
    }

    private static void addPersonRole() {
        if(PersonService.isEmpty()) {
            System.out.println(" No person added yet");
            return;
        }
        Person person = selectPersonFromList();
        if(!PersonService.hasAvailableRolesFor(person)) {
            System.out.println("    No more available roles to add");
            return;
        }
        printAvailableRolesFor(person);
        Role role = selectPersonRole(person, "   Add person role");
        person.getRoles().add(role);
        PersonService.saveOrUpdate(person);
    }

    private static void deletePersonRole() {
        if(PersonService.isEmpty()) {
            System.out.println(" No person added yet");
            return;
        }
        Person person = selectPersonFromList();
        List<Role> roles = new ArrayList(person.getRoles());
        if (roles.size() == 0) {
            System.out.println("    No more available roles to remove");
            return;
        }
        System.out.println();
        System.out.println("  PERSON CURRENT ROLES");
        System.out.println("   ID  ROLE\n" + RoleService.convertListToString(roles));
        int count = 0;
        boolean valid = false;
        while(!valid) {
            Long roleId = (long) UserInputUtils.number("  Remove Role(Enter ID): ");
            for (Role role : roles) {
                if (role.getId() == roleId) {
                    person.getRoles().remove(role);
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("   Person doesn't have role with such ID");
            } else {
                valid = true;
            }
        }
        PersonService.saveOrUpdate(person);
    }
    
    private static void addContact() {
        if(PersonService.isEmpty()) {
            System.out.println(" No person added yet");
            return;
        }
        Person person = selectPersonFromList();
        printPersonContacts(person);
        System.out.println("   ADD CONTACT");
        Contact contact = new Contact();
        contact = updateContactInformation(contact, selectContactType());
        person.getContacts().add(contact);
        PersonService.saveOrUpdate(person);
    }

    private static void updateContact() {
        if(PersonService.isEmpty()) {
            System.out.println(" No person added yet");
            return;
        }
        Person person = selectPersonFromList();
        if(person.getContacts().size() == 0) {
            System.out.println("    No contacts to update.");
            return;
        }
        printPersonContacts(person);
        Contact contact = selectContact(person, "   Update contact");
        contact = updateContactInformation(contact, contact.getType());
        ContactService.saveOrUpdate(contact);
    }

    private static void deleteContact() {
        if(PersonService.isEmpty()) {
            System.out.println(" No person added yet");
            return;
        }
        Person person = selectPersonFromList();
        if(person.getContacts().size() == 0) {
            System.out.println("    No contacts to delete.");
            return;
        }
        printPersonContacts(person);
        Contact contact = selectContact(person, "   Delete contact");
        ContactService.delete(contact);
    }

    private static void printPersonList() {
        List people = PersonService.findAll();
        String peopleString = PersonService.convertListToString(people);
        System.out.println(columnNames);    
        System.out.println(dashes);    
        System.out.println(peopleString);    
    }

    private static void printAvailableRolesFor(Person person) {
        List availableRoles = PersonService.getAvailableRolesFor(person);
        String rolesString = RoleService.convertListToString(availableRoles);
        System.out.println();
        System.out.println("  AVAILABLE ROLES");
        System.out.println("   ID   ROLE\n" + rolesString);
    }

    private static void printPersonContacts(Person person) {
        List<Contact> contacts = new ArrayList(person.getContacts());
        String contactString = ContactService.convertListToString(contacts);
        System.out.println("\n  PERSON ID #: " + person.getId());
        System.out.println("   CONTACTS:");
        System.out.println("    ID   TYPE   INFORMATION");
        System.out.println(contactString);
    }

    private static Person selectPersonFromList() {
        Person person = new Person();
        valid = false;
        printPersonList();
        while(!valid) {
            try {
                Long personId = (long) UserInputUtils.number("  Select Person(Enter ID): ");
                person = PersonService.find(personId);
                valid = true;
            } catch (NullPointerException e) {
                System.out.println("\t There is no Person Entry with such ID ");
            }
        }
        return person;
    }

    private static Role selectPersonRole(Person person, String header) {
        Role role = new Role();
        valid = false;
        while(!valid) {
            try {
                role = RoleService.find((long) UserInputUtils.number(header + "(Enter ID): "));
                if (PersonService.roleIsAvailable(person,role)) {
                    System.out.println("    Role not available to add. Please try again.");
                } else {
                    valid = true;
                }
            } catch (NullPointerException e) {
                System.out.println("\t There is no Role Entry with such ID. Please try again");
            }
        }
        return role;
    }

    private static Role selectRole(String header) {
        Role role = new Role();
        valid = false;
        while(!valid) {
            try {
                role = RoleService.find((long) UserInputUtils.number(header + " (Enter ID): "));
                System.out.println("   Role: " + role.getRoleName());
                valid = true;
            } catch (NullPointerException e) {
                System.out.println("\t There is no Role Entry with such ID. Please try again.");
            }
        }
        return role;
    }

    private static Contact selectContact(Person person, String header) {
        Contact contact = new Contact();
        valid = false;
        while(!valid) {
            try {
                contact = ContactService.find((long) UserInputUtils.number(header + "(Enter ID): "));
                int count = 0;
                for (Contact personContacts : person.getContacts()) {
                    if (contact.getInformation().equals(personContacts.getInformation())) {
                        count++;
                    }
                }
                if (count != 0) {
                    valid = true;
                } else {
                    System.out.println("\t Person does not have a contact record with such ID. Please try again.");
                }
            } catch (NullPointerException e) {
                System.out.println("\t There is no Contact Entry with such ID. Please try again.");
            }
        }
        return contact;
    }

    private static Person updatePersonProperties(Person person) {
        System.out.println("  NAME");
        Name name = new Name();
        name.setTitle(UserInputUtils.stringWithNull("   Title: "));
        name.setFirstName(UserInputUtils.string("   First Name: "));
        name.setMiddleName(UserInputUtils.string("   Middle Name: "));
        name.setLastName(UserInputUtils.string("   Last Name: "));
        name.setSuffix(UserInputUtils.stringWithNull("   Suffix: "));
        person.setName(name);
        System.out.println();

        System.out.println("  ADDRESS");
        Address address = new Address();
        address.setStreet(UserInputUtils.string("   Street: "));
        address.setBarangay(UserInputUtils.string("   Barangay: "));
        address.setCity(UserInputUtils.string("   City: "));
        address.setZipCode(UserInputUtils.number("   Zip Code: "));
        person.setAddress(address);
        System.out.println();

        System.out.println("  BIRTHDATE (MM/DD/YYYY)");
        Date birthdate = UserInputUtils.date("   Birthdate: ");
        person.setBirthdate(birthdate);
        System.out.println();

        System.out.println("  DATE HIRED (MM/DD/YYYY)");
        boolean isDateValid = false;
        Date dateHired = new Date();
        while(!isDateValid) {
            dateHired = UserInputUtils.date("   Date Hired: ");
            if (birthdate.compareTo(dateHired) > 0) {
                System.out.println("    Date hired can't be before being born. Please try again");
            } else if(birthdate.compareTo(dateHired) <= 0){
                isDateValid = true;
            } else {
                System.out.println("Not possible");
            }
        }
        person.setDateHired(dateHired);
        System.out.println();

        System.out.println("  GRADE WEIGHTED AVERAGE");
        Float gwa = 0f;
        while (gwa <= 0f || gwa > 5f) {
            gwa = UserInputUtils.decimal("   GWA: ");
            if (gwa <= 0 || gwa > 5) {
                System.out.println("    Invalid GWA (Range: 1~5, [1=Highest, 5=Lowest]). Please try again.");
            }
        }
        person.setGwa(gwa);
        System.out.println();

        System.out.println("  CURRENTLY EMPLOYED?");
        System.out.println("   [1] YES" + "\n   [2] NO");
        int employedOption = UserInputUtils.numberWithLimit("   Select option",2);
        if (employedOption == 1) {
            person.setCurrentlyEmployed(true);
        } else {
            person.setCurrentlyEmployed(false);
        }

        return person;
    }

    private static Contact updateContactInformation(Contact contact, String type) {
        String information = "";
        boolean validContact = false;
        while(!validContact) {
            String header = "   Enter " + type + ": ";
            if(type.equals("LANDLINE")) {
                information = UserInputUtils.landline(header);
            } else if (type.equals("MOBILE")) {
                information = UserInputUtils.mobileNumber("   Enter mobile number: ");
            } else if (type.equals("EMAIL")) {
                information = UserInputUtils.string(header);
            }
            contact.setInformation(information);
            if (!ContactService.contactExist(contact)) {
                System.out.println("    Contact details already being used. Please enter another.");
            } else {
                validContact = true;
            }
        }
        return contact;
    }

    private static String selectContactType() {
        String type = "";
        System.out.println("   TYPE:"
                                + "\n    [1] LANDLINE" 
                                + "\n    [2] MOBILE"
                                + "\n    [3] EMAIL");
        selectedOption = UserInputUtils.numberWithLimit("   Select option",3);
        switch(selectedOption) {
            case 1:
                type = "LANDLINE";
            case 2:
                type = "MOBILE";
                break;
            case 3:
                type = "EMAIL";
                break;
        }
        return type;
    }

}