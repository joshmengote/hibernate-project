package ecc.hibernate.xml.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import java.util.List;
import java.util.ArrayList;
import ecc.hibernate.xml.util.HibernateUtil;
import ecc.hibernate.xml.model.Person;

public class PersonDAO {
    private static Session session;

    public static List<Person> retrieveList() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Person> personList = new ArrayList();
        String query = "FROM Person";

        try {
            transaction = session.beginTransaction();
            personList = session.createQuery(query).list();
            for (Person person : personList) {
            Hibernate.initialize(person.getContacts());
            Hibernate.initialize(person.getRoles());
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return personList;
    }

    public static void saveEntry(Person person) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public static void deleteEntry(long id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Person WHERE id = :ID";
            Query query = session.createQuery(hql);
            query.setParameter("ID", id);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    public static Person findById(long id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Person person = new Person();
        try {
            transaction = session.beginTransaction();
            person = (Person) session.get(Person.class,id);
            Hibernate.initialize(person.getContacts());
            Hibernate.initialize(person.getRoles());
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return person;
    }

    public static void updateEntryOfId(long id, Person person) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Person findPersonObj = null;
        try {
            transaction = session.beginTransaction();
            findPersonObj = (Person) session.get(Person.class, id);
            findPersonObj = person;
            transaction.commit();
        } catch (HibernateException e) {
            if( null != transaction) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }
}