package org.makumba.parade;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.makumba.parade.model.Parade;

import org.apache.log4j.Logger;



public class Root {

	private static Logger logger = Logger.getLogger(Root.class.getName());
    
	private static Configuration cfg;
    
    static final Long one = new Long(1);
    
    private static Root root = null;
    
    private static SessionFactory sessionFactory;

    private static Session session = null;
    
    private Manager mgr = new Manager();
    
    public void initRoot() {

        try {
        	
        	session = sessionFactory.openSession();
        	
        	Transaction tx = session.beginTransaction();

            Iterator i = session.createQuery("FROM Parade p").iterate();
            
            if (!i.hasNext()) {
                Parade p = new Parade();
                p.setId(one);
                session.saveOrUpdate(p);
            }
            
            tx.commit();
            
            mgr.init();
            
            
            
        } catch (Throwable t) {
            logger.error(t); t.printStackTrace();
        }

    }
    
    public void close() {
    	try {
    		session.flush();
            session.close();
    	} catch(Throwable t) {
    		logger.error(t); t.printStackTrace();
    	}
    	
    }
	
	private static void createPersistentManager(String className, Class type) {
		
		Transaction tx = session.beginTransaction();
		
		Iterator i = session.createQuery("FROM "+className+" c").iterate();
		if (!i.hasNext()) {
			
			try {
				
				Object o = Class.forName(className).asSubclass(Manager.class).newInstance();
				Manager c = (Manager) o;
				
				c.setId(new Long(1));
				session.saveOrUpdate(c);
			} catch (Throwable t) {
				logger.error(t); t.printStackTrace();
			}
			tx.commit();
			
        }
	}
	
	public static ManagerIfc getManagerData(String className, Class type) {
		
		createPersistentManager(className,type);
		
		Transaction tx = session.beginTransaction();
		
		ManagerIfc data = (ManagerIfc) session.get(className, new Long(1));
		if (data == null) {
            logger.error("Cannot get manager data");
		}
		
		tx.commit();
		
		return data;
	}
	
	public static void setManagerData(ManagerIfc data) {
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(data);
		
		tx.commit();
	}
	
	
	public static Root getInstance() throws Exception {
	    if (root==null){
	      root=new Root();
	    }
	    return root;
	  }
	
	private Root() {
		
        try {
            
            cfg = new Configuration().configure("../conf/hibernate.cfg.xml");
            cfg.addResource("../conf/Parade.hbm.xml");
            cfg.addResource("../conf/Row.hbm.xml");
            cfg.addResource("../conf/ManagerData.hbm.xml");
            cfg.addResource("../conf/ManagerDataHierarchy.hbm.xml");
            cfg.addResource("../conf/File.hbm.xml");
            cfg.addResource("../conf/FileHierarchy.hbm.xml");
            cfg.addResource("../conf/FileDataHierarchy.hbm.xml");
            
            sessionFactory = cfg.buildSessionFactory();
            
            // making hibernate creating the tables
            SchemaUpdate schemaUpdate = new SchemaUpdate(cfg);
            schemaUpdate.execute(true, true);
            

        } catch (Throwable t) {
            logger.error(t); t.printStackTrace();
        }
		
	}
}

