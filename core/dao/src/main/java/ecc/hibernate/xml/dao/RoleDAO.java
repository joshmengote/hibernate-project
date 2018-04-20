package ecc.hibernate.xml.dao;


import java.util.List;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.model.Person;
import ecc.hibernate.xml.model.Name;
import ecc.hibernate.xml.util.HibernateUtil;

public class RoleDAO {
    private static Session session;

    public static List<Role> retrieveList(){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Role> roleList = new ArrayList<Role>();
        try {
            String query = "FROM Role";
            transaction = session.beginTransaction();
            roleList = session.createQuery(query).list();
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
        return roleList;
    }   

    public static void saveEntry(Role role) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(role);
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

    public static void deleteEntry(long id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Role "  + 
                         "WHERE id = :role_id";
            Query query = session.createQuery(hql);
            query.setParameter("role_id", id);
            query.executeUpdate();
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
    
    public static Role findById(long id) throws NullPointerException {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Role findRoleObj = null;
        try {
            transaction = session.beginTransaction();
            findRoleObj = (Role) session.get(Role.class, id);
            Hibernate.initialize(findRoleObj.getPerson());
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
        return findRoleObj;
    }

    public static void updateEntryOfId(long id, String name) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Role findRoleObj = null;
        try {
            transaction = session.beginTransaction();
            findRoleObj = (Role) session.get(Role.class, id);
            findRoleObj.setRoleName(name);
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
