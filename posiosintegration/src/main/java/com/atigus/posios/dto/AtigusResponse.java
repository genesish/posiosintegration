package com.atigus.posios.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AtigusResponse implements Serializable{

	private String status = "F";
	private long transactionDate;
	
	List<Object> results = new ArrayList<Object>();
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(long transactionDate) {
		this.transactionDate = transactionDate;
	}
	public List<Object> getResults() {
		return results;
	}
	public void setResults(List<Object> results) {
		this.results = results;
	}
	public void addResults(Object obj){
		if(obj instanceof POSIOSRoom){
			if(this.results == null){
				results = new ArrayList<Object>();
			}
			results.add(obj);
		}
	}
	
}
