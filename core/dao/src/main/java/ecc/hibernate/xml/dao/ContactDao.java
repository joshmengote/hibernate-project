package ecc.hibernate.xml.dao;

import ecc.hibernate.xml.model.Contact;
import ecc.hibernate.xml.util.HibernateUtils;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;


public class ContactDao {
    private static Session session;
    private static Transaction transaction;
    private static HibernateUtils hibernateUtil = new HibernateUtils();
    private void startOperation() throws HibernateException {
        session = hibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public void saveOrUpdate(Contact contact) {
        try {
            startOperation();
            session.saveOrUpdate(contact);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public Contact find(Long id) {
        Contact contact = new Contact();
        try {
            startOperation();
            contact = (Contact) session.get(Contact.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return contact;
    }

    public void delete(Contact contact) {
        try {
            startOperation();
            session.delete(contact);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public boolean contactExist(String contactInfo) {
        boolean exist = false;
        try {
            startOperation();
            String query = "FROM Contact WHERE information = :INFO";
            Query queryObject = session.createQuery(query);
            queryObject.setParameter("INFO", contactInfo);
            List list = queryObject.list();
            if (list.size() == 0) {
                exist = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return exist;
    }
}
