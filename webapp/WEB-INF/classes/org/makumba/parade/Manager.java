package org.makumba.parade;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

public class Manager implements ManagerIfc {
	
	private static Logger logger = Logger.getLogger(Root.class.getName());
    
	public Long id;
	
	private ManagerData data;
	
	
	// managers
	
	private RowStoreManager rowStoreManager;
	
	
	
	/* Does the initialization */
	public void init() {
		
		data = (ManagerData) Root.getManagerData("org.makumba.parade.ManagerData", ManagerData.class);
		
		if(data.getCommon() == null) {
    		logger.warn("No common manager data found");
    		data.setCommon(new HashMap());
    	}
		
		Root.setManagerData((ManagerIfc)data);
    	
		
		createManagers();
		initManagers();
	}
	
	
	/* Initialize the managers
	 * goes through all the plugged managers and calls their init() method
	 * This will populate the commonManagerData map
	 * 
	 * TODO: do this with Spring
	 */
	public void initManagers() {
		
		this.rowStoreManager.init(getCommon());
		
	}
	
	/* Creates the manager instances
	 * 
	 * TODO: do it with Spring
	 */
	
	private void createManagers() {
		
		this.rowStoreManager = new RowStoreManager();
		
	}
	
	public Map getCommon() {
		return this.data.getCommon();
	}
	
	public void setCommon(Map common) {
		this.data.setCommon(common);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void display() {
		// some general code about how to display info, might be moved to view
	}

}
