package ecc.hibernate.xml.model;

import ecc.hibernate.xml.util.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.ArrayList;

public abstract class GenericDao {

    private Session session;
    private Transaction tx;
    private HibernateUtils hibernateUtil = new HibernateUtils();
    public GenericDao() {
        HibernateFactory.buildIfNeeded();
    }

    protected void startOperation() throws HibernateException {
        session = hibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    protected void saveOrUpdate(Object object) {
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

    protected void delete(Object object) {
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

    protected Object find(Class clazz, Long id) {
        Object object = new Object();
        try {
            startOperation();
            object = session.load(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
        return object;
    }

    protected List findAll(Class clazz) {
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

}