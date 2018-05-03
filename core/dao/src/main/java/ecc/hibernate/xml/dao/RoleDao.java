package ecc.hibernate.xml.dao;

import ecc.hibernate.xml.model.Role;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;


public class RoleDao extends GenericDao {
    public RoleDao() {
        super();
    }
    
    public Role find(Long id) {
        Role role = new Role();
        try {
            startOperation();
            role = (Role) session.get(Role.class, id);
            Hibernate.initialize(role.getPerson());
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
        return role;
    }        
    
}
