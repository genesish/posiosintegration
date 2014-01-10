package com.atigus.posios.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class POSIOSJson implements Serializable {

	private String id;
	private String method;
	private List<String> params  = new ArrayList<String>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	
	
}
