package ecc.hibernate.xml.dao;

import ecc.hibernate.xml.util.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.ArrayList;

public class GenericDao {

    protected Session session;
    protected Transaction tx;
    private HibernateUtils hibernateUtil = new HibernateUtils();
    public GenericDao() {
    }

    protected void startOperation() throws HibernateException {
        session = hibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    public void saveOrUpdate(Object object) {
        try {
            startOperation();
            session.saveOrUpdate(object);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }

    public void delete(Object object) {
        try {
            startOperation();
            session.delete(object);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }

    public Object find(Class clazz, Long id) {
        Object object = new Object();
        try {
            startOperation();
            object = session.get(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
        return object;
    }

    public List findAll(Class clazz) {
        List objects = new ArrayList();
        try {
            startOperation();
            Criteria criteria = session.createCriteria(clazz);
            criteria.setCacheable(true);
            objects = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
        return objects;
    }

    public boolean exists(Class clazz, String field, String value) {
        boolean exist = false;
        try {
            startOperation();
            Criteria criteria = session.createCriteria(clazz);
            criteria.add(Restrictions.eq(field, value));
            criteria.setCacheable(true);
            List list = criteria.list();
            if (list.size() == 0) {
                exist = true;
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
        return exist;
    }

}