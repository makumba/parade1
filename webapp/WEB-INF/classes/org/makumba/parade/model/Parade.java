package org.makumba.parade.model;

import java.util.HashMap;
import java.util.Map;

public class Parade {
	
	private Map rows = new HashMap();
			
	public Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Parade() {
		
	}

	public Map getRows() {
		return rows;
	}

	public void setRows(Map rows) {
		this.rows = rows;
	}

	

}
