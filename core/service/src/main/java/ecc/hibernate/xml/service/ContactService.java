package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Contact;
import ecc.hibernate.xml.dto.ContactDTO;
import ecc.hibernate.xml.dao.GenericDao;

import java.util.List;
import java.util.Set;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ContactService {
    private static GenericDao contactDao = new GenericDao();
    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private static MapperFacade mapper = mapperFactory.getMapperFacade();

    public ContactService() {
        mapperFactory.classMap(Contact.class, ContactDTO.class).byDefault();
    }

    public void saveOrUpdate(ContactDTO contactDTO) {
        contactDao.saveOrUpdate(dtoToEntity(contactDTO));
    }

    public void delete(ContactDTO contactDTO) {
        contactDao.delete(dtoToEntity(contactDTO));
    }

    public ContactDTO find(Long id) {
        return entityToDTO((Contact)contactDao.find(Contact.class, id));
    }

    public boolean contactExist(ContactDTO contactDTO) {
        Contact contact = dtoToEntity(contactDTO);
        return contactDao.exists(Contact.class, "information", contact.getInformation());
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

    public ContactDTO entityToDTO(Contact contact) {
        return mapper.map(contact, ContactDTO.class);

    }
    public Contact dtoToEntity(ContactDTO contactDTO) {
        return mapper.map(contactDTO, Contact.class);
    } 

}