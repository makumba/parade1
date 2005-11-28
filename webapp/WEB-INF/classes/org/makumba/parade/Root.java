package org.makumba.parade;

import java.util.*;

import javax.servlet.jsp.PageContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.makumba.parade.model.ManagerData;
import org.makumba.parade.model.ParadeData;
import org.makumba.parade.model.RowData;

import org.apache.log4j.Logger;


public class Root {

	private static Logger logger = Logger.getLogger(Root.class.getName());
    
	private static Configuration cfg;
    
    static final Long one = new Long(1);
    
    private static Root root = null;
    
    private static SessionFactory sessionFactory;

    private static Session session = null;
    
    private static ParadeData p;
    
    private static ManagerData m;
    
    private static RowData r;
    
    private Manager mgr = new Manager();
    
    public void initRoot() {
        	
       	getParadeData();
       	//getRowData();
       	
        mgr.init();
        mgr.readManagers();
        mgr.initManagers();
        
        close();

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
	
	public static ManagerData getManager() {
		Transaction tx = session.beginTransaction();
		
		Iterator i = session.createQuery("FROM ManagerData m").iterate();
		
		if (!i.hasNext()) {
			
			try {
				m = new ManagerData();
				m.setId(new Long(1));
				session.saveOrUpdate(m);
			} catch (Throwable t) {
				logger.error(t); t.printStackTrace();
			}
			tx.commit();
		} else {
			m = (ManagerData) i.next();
		}
			
			return m;
	}
	
	public static void setManagerData(ManagerData m) {
		Transaction tx = session.beginTransaction();
		
		m.setCommon(m.getCommon());
		
		session.saveOrUpdate(m);
		
		tx.commit();
	}
	
	public static void getParadeData() {
		Transaction tx = session.beginTransaction();

        try {     	
       	
        	Iterator i = session.createQuery("FROM ParadeData p").iterate();
            
            if (!i.hasNext()) {
                p = new ParadeData();
                p.setId(one);
                session.saveOrUpdate(p);
                tx.commit();
            } else {
            	p = (ParadeData) i.next();
            }
            
        } catch (Throwable t) {
            logger.error(t); t.printStackTrace();
        }
        
	}
	
	public static void setParadeData(ParadeData data) {
		Transaction tx = session.beginTransaction();
		
		p.setRows(data.getRows());
		
		session.saveOrUpdate(p);
		
		tx.commit();
	}
	
	/* Return Parade rows
	 * TODO: implement it
	 */
	public static Map getParadeRows() {
		return null;
	}
	
	/* Set Parade rows */
	public static void setParadeRows(Map rows) {
		Transaction tx = session.beginTransaction();
		
		p.setRows(rows);
		
		session.saveOrUpdate(p);
		
		tx.commit();
		
	}
	
	public static void getRowData() {
		Transaction tx = session.beginTransaction();

        try {     	
       	
        	Iterator i = session.createQuery("FROM RowData r").iterate();
            
            if (!i.hasNext()) {
                r = new RowData();
                r.setId(one);
                session.saveOrUpdate(r);
                tx.commit();
            } else {
            	r = (RowData) i.next();
            }
            
        } catch (Throwable t) {
            logger.error(t); t.printStackTrace();
        }
	}
	
	public static void setRowData(RowData data) {
		Transaction tx = session.beginTransaction();
		
		//do something useful
		
		session.saveOrUpdate(p);
		
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
            cfg.addResource("../conf/ParadeData.hbm.xml");
            cfg.addResource("../conf/RowData.hbm.xml");
            cfg.addResource("../conf/ManagerData.hbm.xml");
            cfg.addResource("../conf/ManagerDataHierarchy.hbm.xml");
            cfg.addResource("../conf/File.hbm.xml");
            cfg.addResource("../conf/FileHierarchy.hbm.xml");
            cfg.addResource("../conf/FileDataHierarchy.hbm.xml");
            
            sessionFactory = cfg.buildSessionFactory();
            
            // making hibernate creating the tables
            SchemaUpdate schemaUpdate = new SchemaUpdate(cfg);
            schemaUpdate.execute(true, true);
            
            session = sessionFactory.openSession();
            

        } catch (Throwable t) {
            logger.error(t); t.printStackTrace();
        }
		
	}
}

