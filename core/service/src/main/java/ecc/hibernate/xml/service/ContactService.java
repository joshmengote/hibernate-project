package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Contact;
import ecc.hibernate.xml.dao.ContactDao;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;

public class ContactService {
    private ContactDao contactDao;

    public ContactService() {
        contactDao = new ContactDao();
    }

    public void saveOrUpdate(Contact contact) {
        contactDao.saveOrUpdate(contact);
    }

    public void delete(Contact contact) {
        contactDao.delete(contact);
    }

    public Contact find(Long id) {
        return contactDao.find(id);
    }

    public boolean contactExist(Contact contact) {
        return contactDao.contactExist(contact.getInformation());
    }

    public String convertSetToString(Set<Contact> contacts) {
        String contactsString = "";
        int count = 0;
        for(Contact contact : contacts) {
            contactsString += contact.getInformation();
            count++;
            if (count < contacts.size()) {
                contactsString += ",";
            }
        }
        return contactsString;
    }

    public String convertListToString(List<Contact> contacts) {
        String contactString = "";
        for(Contact contact : contacts) {
            contactString += "    " + contact.getId() + "   " + contact.getType() + "   " + contact.getInformation() + "\n";
        }
        return contactString;
    }

}