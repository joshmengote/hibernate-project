package ecc.hibernate.xml.dao;

import ecc.hibernate.xml.model.Person;
import ecc.hibernate.xml.model.Contact;
import ecc.hibernate.xml.model.Role;

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


public class PersonDao extends GenericDao {
    private static final int ASCENDING = 1;
    private static final int DESCENDING = 2;

    public Person find(Long id) {
        Person person = new Person();
        try {
            startOperation();
            person = (Person) session.get(Person.class, id);
            Hibernate.initialize(person.getContacts());
            Hibernate.initialize(person.getRoles());
        } catch (HibernateException e) {
            tx.rollback();
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
            tx.rollback();
        } finally {
            session.close();
        }
        return objects;
    }   

    public List<Person> findAllByLastName(int sortOrder) {
        List<Person> personList = new ArrayList();;
        String queryString = "FROM Person Order BY last_name ";
        queryString += ((sortOrder == ASCENDING) ? "ASC" : "DESC");
        try {
            startOperation();
            Query query = session.createQuery(queryString); 
            query.setCacheable(true);
            personList = query.list();
            for (Person person : personList) {
                Hibernate.initialize(person.getContacts());
                Hibernate.initialize(person.getRoles());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
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
            tx.rollback();
        } finally {
            session.close();
        }
        return personList;
    }
    
}
