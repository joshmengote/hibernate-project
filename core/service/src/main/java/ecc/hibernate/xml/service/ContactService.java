package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Person;
import ecc.hibernate.xml.model.Name;
import ecc.hibernate.xml.model.Contact;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;

public class ContactService {

    public static Contact stringToContact(String contactString) {
        String[] contactArray;
        String type;
        String information;

        contactArray = StringUtils.split(contactString, ':');
        type = contactArray[0];
        information = contactArray[1];
        Contact contact = new Contact(type, information);
        return contact;
    }

    public static Set<Contact> stringToContactSet(String contactString) {
        String[] contactArray;
        Set<Contact> contacts = new HashSet<Contact>();
        contactArray = StringUtils.split(contactString, '\n');
        for (String singleContact : contactArray) {
            contacts.add(stringToContact(singleContact));
        }
        return contacts;
    }

    public static String contactSetToString(Set<Contact> contacts) {
        String contactsString = "";
        for(Contact contact : contacts) {
            contactsString += contact.getInformation();
            if(contacts.size() != 1) {
                contactsString += ",";
            }
        }
        return contactsString;
    }

    public static String convertListToString(List<Contact> contacts) {
        String contactString = "";
        for(Contact contact : contacts) {
            contactString += "   " + contact.getId() + "   " + contact.getType() + "   " + contact.getInformation() + "\n";
        }
        return contactString;
    }

}