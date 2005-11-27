package org.makumba.parade.model;

import java.util.HashMap;
import java.util.Map;

public class Row {
	
	public Long id;
	
	public String rowname;
	
	private Map managers = new HashMap();
	
	private Map files = new HashMap();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Row() {
		
	}

	public String getRowname() {
		return rowname;
	}

	public void setRowname(String rowname) {
		this.rowname = rowname;
	}
	
	public Map getManagers() {
		return managers;
	}

	public void setManagers(Map managers) {
		this.managers = managers;
	}

	public Map getFiles() {
		return files;
	}

	public void setFiles(Map files) {
		this.files = files;
	}

}
