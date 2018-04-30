package ecc.hibernate.xml.dao;

import ecc.hibernate.xml.model.Role;
import ecc.hibernate.xml.util.HibernateUtils;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;


public class RoleDao {
    private static Session session;
    private static Transaction transaction;
    private static HibernateUtils hibernateUtil = new HibernateUtils();
    private void startOperation() throws HibernateException {
        session = hibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public void saveOrUpdate(Role role) {
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

    public Role find(Long id) {
        Role role = new Role();
        try {
            startOperation();
            role = (Role) session.get(Role.class, id);
            Hibernate.initialize(role.getPerson());
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return role;
    }

    public List findAll(){
        List<Role> objects = new ArrayList();
        try {
            startOperation();
            Criteria criteria = session.createCriteria(Role.class);
            objects = criteria.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return objects;
    }   

    public void delete(Role role) {
        try {
            startOperation();
            session.delete(role);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public boolean roleExist(String roleName) {
        boolean exist = false;
        try {
            startOperation();
            Criteria criteria = session.createCriteria(Role.class);
            criteria.add(Restrictions.eq("role", roleName));
            List list = criteria.list();
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
