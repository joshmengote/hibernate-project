package ecc.hibernate.xml.dao;

import ecc.hibernate.xml.model.Person;
import ecc.hibernate.xml.model.Contact;
import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.util.HibernateUtils;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;


public class PersonDao {
    private static final int ASCENDING = 1;
    private static final int DESCENDING = 2;
    private static Session session;
    private static Transaction transaction;
    private static HibernateUtils hibernateUtil = new HibernateUtils();
    private void startOperation() throws HibernateException {
        session = hibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public void saveOrUpdate(Person person) {
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

    public Person find(long id) {
        Person person = new Person();
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

    public List findAll(){
        List<Person> objects = new ArrayList();
        try {
            startOperation();
            Criteria criteria = session.createCriteria(Person.class);
            criteria.setCacheable(true);
            objects = criteria.list();
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

    public void delete(Person person) {
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

    public List<Person> findAllByLastName(int sortOrder) {
        List<Person> personList = new ArrayList();;
        String query = "FROM Person Order BY last_name ";
        query += ((sortOrder == ASCENDING) ? "ASC" : "DESC");
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

    public List<Person> findAllByDateHired(int sortOrder) {
        List<Person> personList = new ArrayList();
        try {
            startOperation();
            Criteria criteria = session.createCriteria(Person.class);
            criteria.addOrder((sortOrder == ASCENDING) ? Order.asc("dateHired") : Order.desc("dateHired"));
            criteria.setCacheable(true);
            personList = criteria.list();
            for (Person person : personList) {
                Hibernate.initialize(person.getContacts());
                Hibernate.initialize(person.getRoles());
            }
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return personList;
    }
    
}
