package ecc.hibernate.xml.app;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import ecc.hibernate.xml.dao.*;
import ecc.hibernate.xml.util.*;
import ecc.hibernate.xml.model.*;
import ecc.hibernate.xml.service.*;

public class Application {
    private static int selectedOption;
    private static boolean valid;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
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
        selectedOption = UserInputUtil.numberWithLimit(" Select option",3);
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

    private static void roleManagementMenu() {
        System.out.println();
        System.out.println("::ROLE MANAGEMENT::");
        System.out.println(" [1] ADD ROLE"
                        + "\n [2] UPDATE ROLE"
                        + "\n [3] DELETE ROLE"
                        + "\n [4] LIST ROLE"
                        + "\n [5] RETURN");
        selectedOption = UserInputUtil.numberWithLimit(" Select option",5);
        System.out.println();
        switch(selectedOption) {
            case 1:
                addNewRole();
                break;
            case 2:
                updateExistingRole();
                break;
            case 3:
                deleteExistingRole();
                break;
            case 4:
                System.out.println(" >> LIST EXISTING ROLES <<<");
                printRoleList();
                roleManagementMenu();
                break;
            case 5:
                mainMenu();
                break;
        }
    }

    private static void printRoleList() {
        List roles = null;
        String rolesString = null;

        roles = RoleDao.findAll();
        rolesString = RoleService.convertListToString(roles);
        System.out.println("   ID   ROLE\n" + rolesString);
    }

    private static void addNewRole() {
        Role role = null;
        String roleName;
        System.out.println(" >> ADD NEW ROLE <<");
        printRoleList();
        roleName = UserInputUtil.string("  Enter New Role: ");
        role = new Role(roleName);
        RoleDao.saveOrUpdate(role);
        roleManagementMenu();
    }

    private static void updateExistingRole() {
        Role role = null;
        valid = false;
        long idOfRoleToUpdate = 0;
        System.out.println(" >> UPDATE EXISTING ROLE <<");
        printRoleList();
        while(!valid) {
            try {
                idOfRoleToUpdate = (long) UserInputUtil.number(" Update Role (Enter ID): ");
                role = RoleDao.find(idOfRoleToUpdate);
                System.out.println("   Role: " + role.getRoleName());
                valid = true;
            } catch (NullPointerException e) {
                System.out.println("  !!! There is no Role Entry with such ID !!!");
            }
        }
        String updatedRoleName = UserInputUtil.string("   Updated Role Entry: ");
        role.setRoleName(updatedRoleName);
        RoleDao.saveOrUpdate(role);
        roleManagementMenu();
    }

    private static void deleteExistingRole() {
        Role role = null;
        long idOfRoleToDelete = 0;
        valid = false;
        System.out.println(" >> DELETE EXISTING ROLE <<<");
        printRoleList();
        while(!valid) {
            try {
                idOfRoleToDelete = (long) UserInputUtil.number(" Delete Role (Enter ID): ");
                role = RoleDao.find(idOfRoleToDelete);
                RoleDao.delete(role);
                valid = true;
            } catch (NullPointerException e) {
                System.out.println("  !!! There is no Role Entry with such ID !!!");
            }
        }
        roleManagementMenu();
    }
////////////////////////////////////////////////////////PERSON
    private static void personManagementMenu() {
        Person person = null;
        Role roleObject = null;
        List<Contact> contacts = null;
        Contact contactObject = null;
        String contactString = null;
        long roleId = 0;
        long contactId = 0;

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
        selectedOption = UserInputUtil.numberWithLimit(" Select option",10);
        System.out.println();
        switch(selectedOption) {
            case 1: 
                createPerson();
                break;
            case 2: 
                updatePerson();
                break;
            case 3: 
                printPersonListBy();
                break;
            case 4: 
                System.out.println(" >> DELETE PERSON <<");
                person = selectPersonFromList();
                PersonDao.delete(person);
                personManagementMenu();
                break;
            case 5: 
                System.out.println(" >> ADD ROLE TO PERSON <<");
                person = selectPersonFromList();
                printRoleList();
                roleId = UserInputUtil.number("  Add Role(Enter ID): ");
                roleObject = RoleDao.find(roleId);
                person.getRoles().add(roleObject);
                PersonDao.saveOrUpdate(person);
                personManagementMenu();
                break;
            case 6: 
                System.out.println(" >> REMOVE ROLE FROM PERSON <<");
                person = selectPersonFromList();
                List<Role> roles = new ArrayList();
                roles.addAll(person.getRoles());
                String rolesString = RoleService.convertListToString(roles);
                System.out.println(rolesString);
                roleId = UserInputUtil.number("  Remove Role(Enter ID): ");
                for (Role role : roles) {
                    if (role.getId() == roleId) {
                        person.getRoles().remove(role);
                    }
                }
                PersonDao.saveOrUpdate(person);
                personManagementMenu();
                break;
            case 7: 
                System.out.println(" >> ADD CONTACT <<");
                person = selectPersonFromList();
                contacts = new ArrayList();
                contacts.addAll(person.getContacts());
                contactString = ContactService.convertListToString(contacts);
                System.out.println(contactString);
                Set<Contact> contactsSet = createContacts();
                for (Contact contact : contactsSet) {
                    person.getContacts().add(contact);
                }
                PersonDao.saveOrUpdate(person);
                personManagementMenu();
                break;
            case 8: 
                System.out.println(" >> UPDATE CONTACT <<");
                person = selectPersonFromList();
                contacts = new ArrayList();
                contacts.addAll(person.getContacts());
                contactString = ContactService.convertListToString(contacts);
                System.out.println(contactString);
                contactId = UserInputUtil.number("  Update Contact(Enter ID): ");
                for (Contact contact : contacts) {
                    if (contact.getId() == contactId) {
                        String newInformation = UserInputUtil.string("  Enter " + contact.getType() + ": ");
                        contact.setInformation(newInformation);
                    }
                }
                PersonDao.saveOrUpdate(person);
                personManagementMenu();
                break;
            case 9: 
                System.out.println(" >> DELETE CONTACT <<");
                person = selectPersonFromList();
                contacts = new ArrayList();
                contacts.addAll(person.getContacts());
                contactString = ContactService.convertListToString(contacts);
                System.out.println(contactString);
                contactId = UserInputUtil.number("  Remove Contact(Enter ID): ");
                for (Contact contact : contacts) {
                    if (contact.getId() == contactId) {
                        person.getContacts().remove(contact);
                    }
                }
                PersonDao.saveOrUpdate(person);
                personManagementMenu();
                break;
            case 10:
                mainMenu();
                break;
        }
    }

    private static void createPerson() {
        String name = "";
        String address = ""; 
        String roles = "";
        String birthdate;
        String dateHired;

        int employedOption;
        boolean employmentStatus;
        float gwa;

        List<Long> roleIds = null;
        Set<Contact> contacts = null;

        System.out.println(" >> ADD PERSON <<");
        System.out.println("  NAME");
        name += UserInputUtil.stringWithNull("   Title: ") + "\n";
        name += UserInputUtil.string("   First Name: ") + "\n";
        name += UserInputUtil.string("   Middle Name: ") + "\n";
        name += UserInputUtil.string("   Last Name: ") + "\n";
        name += UserInputUtil.stringWithNull("   Suffix: ");
        System.out.println();
        System.out.println("  ADDRESS");
        address += UserInputUtil.string("   Street: ") + "\n";
        address += UserInputUtil.string("   Barangay: ") + "\n";
        address += UserInputUtil.string("   City: ") + "\n";
        address += UserInputUtil.number("   Zip Code: ");
        System.out.println();
        System.out.println("  BIRTHDATE (MM/DD/YYYY)");
        birthdate = UserInputUtil.date("   Birthdate: ");
        System.out.println();
        System.out.println("  DATE HIRED (MM/DD/YYYY)");
        dateHired = UserInputUtil.date("   Date hired: ");
        System.out.println();
        System.out.println("  GRADE WEIGHTED AVERAGE");
        gwa = UserInputUtil.decimal("   GWA: ");
        System.out.println();
        System.out.println("  CURRENTLY EMPLOYED?");
        System.out.println("   [1] YES" + "\n   [2] NO");
        employedOption = UserInputUtil.numberWithLimit("   Select option",2);
        if (employedOption == 1) {
            employmentStatus = true;
        } else {
            employmentStatus = false;
        }
        System.out.println();

        Person person = PersonService.createPersonEntry(NameService.stringToName(name), 
                                        AddressService.stringToAddress(address), 
                                        PersonService.stringToDate(birthdate), 
                                        PersonService.stringToDate(dateHired), 
                                        gwa, employmentStatus); 
        
        System.out.println("  ROLES");
        roleIds = addPersonRole();
        for (long id : roleIds) {
            Role roleObject = RoleDao.find(id);
            person.getRoles().add(roleObject);
        }

        System.out.println();
        System.out.println("  CONTACT DETAILS");
        contacts = createContacts();
        person.setContacts(contacts);

        PersonDao.saveOrUpdate(person);
        System.out.println();
        personManagementMenu();
    }

    private static List<Long> addPersonRole() {
        Role role = null;
        List<Long> roleIds = new ArrayList();
        valid = false;
        long roleId = 0;
        boolean doneAddingRole = false;
        while(!doneAddingRole) {
            while(!valid) {
                try {
                    printRoleList();
                    roleId = (long) UserInputUtil.number("   Add role (Enter ID): ");
                    role = RoleDao.find(roleId);
                    System.out.println("   Added to Person - Role: " + role.getRoleName());
                    roleIds.add(roleId);
                    valid = true;
                } catch (NullPointerException e) {
                    System.out.println("  !!! There is no Role Entry with such ID !!!");
                }
            }
            System.out.println("   ADD ANOTHER ROLE?"
                                + "\n    [1] YES"
                                + "\n    [2] NO");
            selectedOption = UserInputUtil.numberWithLimit("   Select option", 2);
            if(selectedOption == 2) {
                doneAddingRole =true;
            } else {
                valid = false;
            }
        }
        return roleIds;
    }

    private static Set<Contact> createContacts() {
        boolean doneAddContact = false;
        String type = "";
        String header;
        String information;

        Contact contactObject = null;
        Set<Contact> contacts = new HashSet();

        do {
            System.out.println("   ADD CONTACT");
            System.out.println("   TYPE:"
                                    + "\n    [1] LANDLINE" 
                                    + "\n    [2] MOBILE"
                                    + "\n    [3] EMAIL");
            selectedOption = UserInputUtil.numberWithLimit("   Select option",3);
            switch(selectedOption) {
                case 1:
                    type = "LANDLINE";
                    break;
                case 2:
                    type = "MOBILE";
                    break;
                case 3:
                    type = "EMAIL";
                    break;
            }
            header = "   Enter " + type + ": ";
            information = UserInputUtil.string(header);
            contactObject = new Contact(type, information);
            contacts.add(contactObject);
            System.out.println("   ADD ANOTHER:"
                                    + "\n    [1] YES" 
                                    + "\n    [2] NO");
            selectedOption = UserInputUtil.numberWithLimit("   Select option",2);
            if (selectedOption == 2) {
                doneAddContact = true;
            }
            
        } while (!doneAddContact);
        return contacts;
    }

    private static void printPersonList() {
        List people = null;
        String peopleString = null;

        people = PersonDao.findAll();
        peopleString = PersonService.convertListToString(people);
        System.out.println("   ID   \t\t\tNAME \t\t\tADDRESS \t\tBIRTHDATE \t GWA \t DATE HIRED \t CURRENTLY EMPLOYED \t ROLES \t CONTACT\n" + peopleString);
    
    }

    private static Person selectPersonFromList() {
        Person person = null;
        long personId = 0;
        valid = false;
        printPersonList();
        while(!valid) {
            try {
                personId = (long) UserInputUtil.number("  Select Person(Enter ID):");
                person = PersonDao.find(personId);
                valid = true;
            } catch (NullPointerException e) {
                System.out.println("  !!! There is no Person Entry with such ID !!!");
            }
        }
        return person;
    }

    private static void printPersonListBy() {
        String stringList = null;
        List list = null;
        int parameter;
        int order;
        System.out.println(" >> LIST PERSON <<");        
        System.out.println("  LIST BY:\n   [1] LAST NAME"
                                   + "\n   [2] DATE HIRED"
                                   + "\n   [3] GWA");
        parameter = UserInputUtil.numberWithLimit("  Select option",3);

        System.out.println("  ORDER:\n   [1] ASCENDING"
                                 + "\n   [2] DESCENDING");
        order = UserInputUtil.numberWithLimit("  Select option",2);
        if(parameter != 3) {
            list = PersonDao.sort(parameter, order);
        } else {
            list = PersonDao.findAll();
            if (order == 1) {
                Collections.sort(list, Person.gwaAscending);
            } else if (order == 2) {
                Collections.sort(list, Person.gwaDescending);
            }
        }
        stringList = PersonService.convertListToString(list);
        System.out.println();
        System.out.println(stringList);
        System.out.println();
        personManagementMenu();
    }

    private static void updatePerson() {
        String name = "";
        String address = ""; 
        String roles = "";
        String birthdate;
        String dateHired;

        int employedOption;
        boolean employmentStatus;
        float gwa;

        System.out.println(" >> UPDATE PERSON <<");        
        Person person = selectPersonFromList();
        System.out.println("  Current Data:");        
        String personInfo = PersonService.getPersonInfo(person);
        System.out.println(personInfo);
        System.out.println();

        System.out.println("  UPDATE - Enter New Data:");
        System.out.println("  NAME");
        name += UserInputUtil.stringWithNull("   Title: ") + "\n";
        name += UserInputUtil.string("   First Name: ") + "\n";
        name += UserInputUtil.string("   Middle Name: ") + "\n";
        name += UserInputUtil.string("   Last Name: ") + "\n";
        name += UserInputUtil.stringWithNull("   Suffix: ");
        System.out.println();
        System.out.println("  ADDRESS");
        address += UserInputUtil.string("   Street: ") + "\n";
        address += UserInputUtil.string("   Barangay: ") + "\n";
        address += UserInputUtil.string("   City: ") + "\n";
        address += UserInputUtil.number("   Zip Code: ");
        System.out.println();
        System.out.println("  BIRTHDATE (MM/DD/YYYY)");
        birthdate = UserInputUtil.date("   Birthdate: ");
        System.out.println();
        System.out.println("  DATE HIRED (MM/DD/YYYY)");
        dateHired = UserInputUtil.date("   Date hired: ");
        System.out.println();
        System.out.println("  GRADE WEIGHTED AVERAGE");
        gwa = UserInputUtil.decimal("   GWA: ");
        System.out.println();
        System.out.println("  CURRENTLY EMPLOYED?");
        System.out.println("   [1] YES" + "\n   [2] NO");
        employedOption = UserInputUtil.numberWithLimit("   Select option",2);
        if (employedOption == 1) {
            employmentStatus = true;
        } else {
            employmentStatus = false;
        }

        person.setName(NameService.stringToName(name));
        person.setAddress(AddressService.stringToAddress(address));
        person.setBirthdate(PersonService.stringToDate(birthdate));
        person.setDateHired(PersonService.stringToDate(dateHired));
        person.setGwa(gwa);
        person.setCurrentlyEmployed(employmentStatus);
        PersonDao.saveOrUpdate(person);
        personManagementMenu();
    }


}