package org.makumba.parade.model;

import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class RowData {
	
	public Long id;
	
	public String rowname;
	
	public String rowpath;
	
	public String description;
	
	//private Map managers = new HashMap();
	
	private ManagerData managerdata;
	
	private Map files = new HashMap();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public RowData() {
		
	}

	public String getRowname() {
		return rowname;
	}

	public void setRowname(String rowname) {
		this.rowname = rowname;
	}
	
	/*
	public Map getManagers() {
		return managers;
	}

	public void setManagers(Map managers) {
		this.managers = managers;
	}
	*/

	public Map getFiles() {
		return files;
	}

	public void setFiles(Map files) {
		this.files = files;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRowpath() {
		return rowpath;
	}

	public void setRowpath(String rowpath) {
		this.rowpath = rowpath;
	}

	public ManagerData getManagerdata() {
		return managerdata;
	}

	public void setManagerdata(ManagerData managerdata) {
		this.managerdata = managerdata;
	}

}
