package org.makumba.parade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.makumba.parade.model.ManagerData;
import org.makumba.parade.model.RowStoreManagerData;

public class Manager implements ManagerIfc {
	
	private static Logger logger = Logger.getLogger(Manager.class.getName());
    
	public Long id;
	
	private ManagerData data;
	
	
	// managers
	private static RowStoreManager rowStoreManager;
	private static FileManager fileManager;
	
	
	/* Does the initialization */
	public void init() {
		
		data = Root.getManager();
		
		if(data.getCommon() == null) {
    		logger.warn("No common manager data found");
    		data.setCommon(new HashMap());
    		Root.setManagerData(data);
    	}
		
		createManagers();
		readManagers();
	}
	
	/* Read initialization data for managers which need it */
	public void readManagers() {
		rowStoreManager.read(getCommon());
	}
	
	
	/* Initialize the managers
	 * goes through all the plugged managers and calls their init() method
	 *
	 * TODO: do this with Spring
	 */
	public void initManagers() {
		rowStoreManager.init(getCommon());
		fileManager.init(getCommon());
		
	}
	
	/* Initializes the given row with all the managers */
	public void initManagersOnRow(Map row) {
		fileManager.initRow(row,getCommon());
		
		
	}
	
	/* Creates the manager instances
	 * 
	 * TODO: do it with Spring
	 */
	
	private void createManagers() {
		
		rowStoreManager = new RowStoreManager();
		fileManager = new FileManager();
	}
	
	/* Displays row data
	 * 
	 */
	
	public static String view() {
		String view="<table><tr>";
		Map rows = ((RowStoreManagerData) Root.getManagerData("org.makumba.parade.model.RowStoreManagerData", RowStoreManagerData.class)).getRowstore();
    	
		Iterator i = rows.keySet().iterator();
		while(i.hasNext()) {
			//views from all the concerned managers
			view += "<td>"+rowStoreManager.view((String)rows.get(i.next()))+"</td>";
			
			view+="</tr><tr>";
		}
		view+="</tr></table>";
		
		logger.warn(view);
		return view;
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
