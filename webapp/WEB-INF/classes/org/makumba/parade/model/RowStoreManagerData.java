package org.makumba.parade.model;

import java.util.Map;

import org.makumba.parade.Manager;
import org.makumba.parade.RowProperties;

public class RowStoreManagerData extends Manager {

	private Long id = new Long(1);
	
	private Map rowstore;
	
	private RowProperties rowDefs = new RowProperties();
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RowProperties getRowDefs() {
		return rowDefs;
	}

	public void setRowDefs(RowProperties rowDefs) {
		this.rowDefs = rowDefs;
	}

	public Map getRowstore() {
		return rowstore;
	}

	public void setRowstore(Map rowstore) {
		this.rowstore = rowstore;
	}
}
