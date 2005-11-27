package org.makumba.parade;

import java.io.File;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.makumba.parade.model.Parade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class RootSpring extends HibernateDaoSupport {

	static final Long one = new Long(1);

	public ApplicationContext ctx = null;

	private SessionFactory sessionFactory;

	public void init()  {
		
		HibernateTemplate ht = new HibernateTemplate();

		System.out.println("INFO: Session ...");
		//Parade parade = (Parade) ctx.getBean("RootDAO");
		//parade.id = one;
		
		Iterator i = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("FROM Parade p").iterate();
        if (!i.hasNext()) {
            Parade p = new Parade();
            p.setId(one);
            getHibernateTemplate().getSessionFactory().getCurrentSession().save(p);
        }

	}

	public RootSpring() {
		// TODO make private constructor to protect from mult instances
		/*
		String[] paths = { "applicationContext.xml" };
		ctx = new ClassPathXmlApplicationContext(paths);
		*/
	}
/*
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	*/
/*
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
*/
}
