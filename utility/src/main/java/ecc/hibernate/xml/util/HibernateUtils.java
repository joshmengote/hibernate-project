package ecc.hibernate.xml.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory() {
		try{
			Configuration configuration = new AnnotationConfiguration().configure();
			return configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}