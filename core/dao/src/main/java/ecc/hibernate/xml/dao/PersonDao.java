package ecc.hibernate.xml.dao;

import ecc.hibernate.xml.model.Person;
import ecc.hibernate.xml.model.Contact;
import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.util.HibernateUtils;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;


public class PersonDao {
    private static Session session;
    private static Transaction transaction;

    private static void startOperation() throws HibernateException {
        session = HibernateUtils.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public static void saveOrUpdate(Person person) {
        try {
            startOperation();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public static Person find(long id) {
        Person person = null;
        try {
            startOperation();
            person = (Person) session.get(Person.class, id);
            Hibernate.initialize(person.getContacts());
            Hibernate.initialize(person.getRoles());
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return person;
    }

    public static List findAll(){
        List<Person> objects = null;
        try {
            startOperation();
            Query query = session.createQuery("FROM Person");
            objects = query.list();
            for (Person person : objects) {
            Hibernate.initialize(person.getContacts());
            Hibernate.initialize(person.getRoles());
            }
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return objects;
    }   

    public static void delete(Person person) {
        try {
            startOperation();
            session.delete(person);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public static List<Person> sort(int sortParamater, int sortOrder) {
        List<Person> personList = null;
        final int ASCENDING = 1;
        final int DESCENDING = 2;
        
        final int LAST_NAME = 1;
        final int DATE_HIRED = 2;
        String query = null;

        if (sortParamater == LAST_NAME && sortOrder == ASCENDING) {
            query = "FROM Person ORDER BY last_name ASC";
        } else if (sortParamater == LAST_NAME && sortOrder == DESCENDING) {
            query = "FROM Person ORDER BY last_name DESC";
        } else if (sortParamater == DATE_HIRED && sortOrder == ASCENDING) {
            query = "FROM Person ORDER BY date_hired ASC";
        } else if (sortParamater == DATE_HIRED && sortOrder == DESCENDING) {
            query = "FROM Person ORDER BY date_hired DESC";
        }
        try {
            startOperation();
            personList = session.createQuery(query).list();
            for (Person person : personList) {
            Hibernate.initialize(person.getContacts());
            Hibernate.initialize(person.getRoles());
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return personList;
    }
    
}
