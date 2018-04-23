package ecc.hibernate.xml.dao;

import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.util.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;


public class RoleDao {
    private static Session session;
    private static Transaction transaction;

    private static void startOperation() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public static void saveOrUpdate(Role role) {
        try {
            startOperation();
            session.saveOrUpdate(role);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public static Role find(long id) {
        Role role = null;
        try {
            startOperation();
            role = (Role) session.get(Role.class, id);
            Hibernate.initialize(role.getPerson());
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return role;
    }

    public static List findAll(){
        List<Role> objects = null;
        try {
            startOperation();
            Query query = session.createQuery("FROM Role");
            objects = query.list();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return objects;
    }   


    public static void delete(Role role) {
        try {
            startOperation();
            long id = role.getId();
            String query = "DELETE FROM Role "  + 
                         "WHERE id = :role_id";
            Query queryObject = session.createQuery(query);
            queryObject.setParameter("role_id", id);
            queryObject.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    
}