package org.makumba.parade;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.makumba.parade.model.RowData;
import org.makumba.parade.model.RowStoreManagerData;


public class RowStoreManager {
	
	public Long id;
	
	static Logger logger = Logger.getLogger(RowStoreManager.class.getName());
	
	private RowStoreManagerData data;
		    
    
	public void read(Map common) {
    	data = (RowStoreManagerData) Root.getManagerData("org.makumba.parade.model.RowStoreManagerData", RowStoreManagerData.class);
    	readRowDefinitions(common);
    }
	
	
	public void init(Map common) {
    	
    }
    
    /* Read row definitions, stores them in rowstore and create rows in ParadeData */
    private void readRowDefinitions(Map common) {
    	
    	
    	Map rowstore = (new RowProperties()).getRowDefinitions();
    	Map stored_rowstore = data.getRowstore();
    	
    	/* TODO: Comparing the maps to see if there's a need to update */
    	
        
        if(true) {
        	Map rows = new HashMap();
        	if(rowstore.isEmpty()) {
        		logger.warn("No row definitions found, check RowProperties");
        	} else {
        	
    	    	Iterator i = rowstore.keySet().iterator();
    	    	int j =0;
    	    	while(i.hasNext()) {
    	    		Map row = (Map) rowstore.get((String) i.next());
    	    		
    	    		/* TODO in FileManager
    	    		try {
    	                row.put("path", new File(((String) row.get("path")).trim()).getCanonicalPath());
    	            } catch (Throwable t) {
    	            	logger.error(t); t.printStackTrace();
    	            }
    	            */
    	            
    	            // creating RowData objects and passing the information
    	            RowData rd = new RowData();
    	            rd.setId(new Long(++j));
    	            rd.setRowname((String)row.get("name"));
    	            rd.setRowpath((String) row.get("path"));
    	            rd.setDescription((String)row.get("desc"));
    	            
    	            rows.put((String)row.get("name"),rd);
    		    }
    	    	
    	    	data.setRowstore(rowstore);
    	    	Root.setManagerData(data);
    	    	
    	    	/* set Parade's rows
    	    	 * TODO: compare first the changes
    	    	 */
    	    	Root.setParadeRows(rows);
    	    	
        	}
        }
    	
    	
    
    }
    
    public String view(String rowname) {
    	Map row = (Map) data.getRowstore().get(rowname);
    	return ((String) row.get("name")+" "+((File)row.get("path")).getPath());
    }
    
    /*
    public synchronized void addParadeRow(java.util.Map data,
            javax.servlet.jsp.PageContext pc) throws ParadeException {
        String s[] = pc.getRequest().getParameterValues("parade.row");
        if (s == null || s.length != 1 || s[0].trim().indexOf(" ") != -1)
            throw new ParadeException(
                    "missing name, multiple names, or name containing spaces");
        String rowName = s[0].trim();
        Map row = new HashMap();
        data.put(rowName, row);
        row.put("parade.name", rowName);

        Config.invokeOperation(data, "parade", "findPath", pc);
        String path = (String) row.get("parade.path");
        if (path == null)
            throw new ParadeException("could not determine path");

        this.data.getRowDefs().addRowDefinition(rowName, path, "");
        
    }
    */

	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public RowStoreManager() {
		this.data = new RowStoreManagerData();
	}
	
	
	

}
