package org.makumba.parade;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;


import org.apache.log4j.Logger;


public class Root {

	static Logger logger = Logger.getLogger(Root.class.getName());

    private static Configuration cfg;
    
    static final Long one = new Long(1);
    
    private static Root root =null;

    private SessionFactory sessionFactory;
    
    private Session session = null;
    
    public void init() {

        try {

            session = sessionFactory.openSession();
            
            Transaction tx = session.beginTransaction();

            Iterator i = session.createQuery("FROM Parade p").iterate();
            if (!i.hasNext()) {
                Parade p = new Parade();
                p.setId(one);
                session.save(p);
            }
            
            tx.commit();
            
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
	
	private Root getParade() {
        return (Root)session.get(Parade.class, one);
    }
	
	private void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	/* For later use, once pluggable session managment works fine
	
	private Session session() {
		//return this.sessionFactory.getCurrentSession();
		
	}
	
	*/
	
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
            
            
            
            
            
            setSessionFactory(cfg.buildSessionFactory());
            
            // making hibernate creating the tables
            SchemaUpdate schemaUpdate = new SchemaUpdate(cfg);
            schemaUpdate.execute(true, true);
            
        } catch (Throwable t) {
            logger.error(t); t.printStackTrace();
        }
		
	}
}

