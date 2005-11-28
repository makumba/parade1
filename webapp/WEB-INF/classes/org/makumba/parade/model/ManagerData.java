package org.makumba.parade.model;

import java.util.HashMap;
import java.util.Map;

import org.makumba.parade.Manager;

public class ManagerData extends Manager {
	
	private Long id;
	
	private Map common;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map getCommon() {
		return common;
	}

	public void setCommon(Map common) {
		this.common = common;
	}

}
