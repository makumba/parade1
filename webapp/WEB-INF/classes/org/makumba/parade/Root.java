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
	
	
	public static ManagerIfc getManagerData(String className, Class type) {
		
		Transaction tx = session.beginTransaction();
		
		ManagerIfc data = (ManagerIfc) session.get(className, new Long(1));
		if (data == null) {
			try {
				Object o = Class.forName(className).asSubclass(Manager.class).newInstance();
				data = (Manager) o;
				
				data.setId(new Long(1));
				session.save(data,new Long(1));
				
			} catch (Throwable t) {
				logger.error("Cannot create persistend manager");
				logger.error(t); t.printStackTrace();
			}
			tx.commit();
			
			
		}
		
		return data;
	}
	
	public static void setManagerData(ManagerIfc data) {
		Transaction tx = session.beginTransaction();
		
		session.merge(data);
		
		tx.commit();
	}
	
	public static ManagerData getManager() {
		Transaction tx = session.beginTransaction();
		
		m = (ManagerData) session.get(ManagerData.class,new Long(1));
		
		if (m == null) {
			
			try {
				m = new ManagerData();
				m.setId(new Long(1));
				session.save(m,new Long(1));
			} catch (Throwable t) {
				logger.error(t); t.printStackTrace();
			}
			tx.commit();
			
		} 
			return m;
	}
	
	public static void setManagerDataData (ManagerData m) {
		Transaction tx = session.beginTransaction();
		
		m.setCommon(m.getCommon());
		
		session.merge(m);
		
		tx.commit();
	}
	
	public static void getParadeData() {
		Transaction tx = session.beginTransaction();
		
		p = (ParadeData) session.get(ParadeData.class, new Long(1));
		
		if (p == null) {
			
			try {     	
	        	p = new ParadeData();
	        	p.setId(one);
	        	session.save(p,new Long(1));
	        	tx.commit();
	        	
	        } catch (Throwable t) {
	            logger.error(t); t.printStackTrace();
	        }
		}
	}
	
	public static void setParadeData(ParadeData data) {
		Transaction tx = session.beginTransaction();
		
		p.setRows(data.getRows());
		session.merge(p);
		
		tx.commit();
		
	}
	
	/* Return Parade rows */
	public static Map getParadeRows() {
		Transaction tx = session.beginTransaction();
		
		Map rows = p.getRows();
		tx.commit();
		
		return rows;
	}
	
	/* Set Parade rows */
	public static void setParadeRows(Map rows) {
		Transaction tx = session.beginTransaction();
		
		//p = (ParadeData) session.get(ParadeData.class,new Long(1));
		p.setRows(rows);
		session.merge(p);
		
		tx.commit();

		
	}
	
	/* Returns data of one row
	 * TODO: make it select the row
	 */
	public static void getRowData() {
		Transaction tx = session.beginTransaction();

        try {     	
       	
        	r = (RowData) session.get(RowData.class, new Long(1));
            
            if (r == null) {
                r = new RowData();
                r.setId(one);
                session.save(r,new Long(1));
                tx.commit();
            }
            
        } catch (Throwable t) {
            logger.error(t); t.printStackTrace();
        }
	}
	
	/* Sets the data of one Row
	 * TODO: make it select the row
	 */
	public static void setRowData(RowData data) {
		Transaction tx = session.beginTransaction();
		
		//do something useful
		
		session.save(r,new Long(1));
		
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

