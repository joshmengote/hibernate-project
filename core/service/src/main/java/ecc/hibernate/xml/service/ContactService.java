package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Contact;
import ecc.hibernate.xml.dto.ContactDTO;
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

    public void saveOrUpdate(ContactDTO contactDTO) {
        contactDao.saveOrUpdate(dtoToEntity(contactDTO));
    }

    public void delete(ContactDTO contactDTO) {
        contactDao.delete(dtoToEntity(contactDTO));
    }

    public ContactDTO find(Long id) {
        return entityToDTO(contactDao.find(id));
    }

    public boolean contactExist(ContactDTO contactDTO) {
        Contact contact = dtoToEntity(contactDTO);
        return contactDao.contactExist(contact.getInformation());
    }

    public String convertSetToString(Set<ContactDTO> contacts) {
        String contactsString = "";
        int count = 0;
        for(ContactDTO contact : contacts) {
            contactsString += contact.getInformation();
            count++;
            if (count < contacts.size()) {
                contactsString += ",";
            }
        }
        return contactsString;
    }

    public String convertListToString(List<ContactDTO> contacts) {
        String contactString = "";
        for(ContactDTO contact : contacts) {
            contactString += "    " + contact.getId() + "   " + contact.getType() + "   " + contact.getInformation() + "\n";
        }
        return contactString;
    }

    public Contact dtoToEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setType(contactDTO.getType());
        contact.setInformation(contactDTO.getInformation());
        return contact;
    }

    public ContactDTO entityToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setType(contact.getType());
        contactDTO.setInformation(contact.getInformation());
        return contactDTO;
    }
}