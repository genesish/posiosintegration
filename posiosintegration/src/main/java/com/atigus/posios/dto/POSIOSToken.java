package com.atigus.posios.dto;

import java.io.Serializable;

public class POSIOSToken implements Serializable {
	private String id;
	private String result;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
