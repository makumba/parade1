package org.makumba.parade;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.makumba.parade.model.RowStoreManagerData;


public class RowStoreManager {
	
	public Long id;
	
	static Logger logger = Logger.getLogger(RowStoreManager.class.getName());
	
	private RowStoreManagerData data;
		    
    public void init(Map common) {
    	
    	data = (RowStoreManagerData) Root.getManagerData("org.makumba.parade.model.RowStoreManagerData", RowStoreManagerData.class);
    	readRowDefinitions(common);
    }
    
    /* Read row definitions and stores them in rowstore */
    private void readRowDefinitions(Map common) {
    	
    	data.setRowstore((new RowProperties()).getRowDefinitions());
    	
    	if(data.getRowstore().isEmpty()) {
    		logger.warn("No row definitions found, check RowProperties");
    	}
        
    	// we "convert" the path String to a real path
    	Iterator i = data.getRowstore().keySet().iterator();
    	while(i.hasNext()) {
    		String name = (String) i.next();
    		Map row = (Map) data.getRowstore().get(name);
    		
    		try {
                row.put("path", new File(((String) row.get("path")).trim()).getCanonicalPath());
            } catch (Throwable t) {
            	logger.error(t); t.printStackTrace();
            }
    	}
    	
    	/* we put rowstore in the common Map
    	
    	common.putAll(data.getRowstore());
    	
    	Root.setManagerData((ManagerIfc)data);
    	
    	*/
    	
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
