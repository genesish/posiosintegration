package com.atigus.posios.dto;

import java.io.Serializable;

public class POSIOSRoom implements Serializable {

	private String lastname;
	private String guestname;
	private String roomInfo;
	private Integer customerId;
	private Integer companyId;
	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getGuestname() {
		return guestname;
	}
	public void setGuestname(String guestname) {
		this.guestname = guestname;
	}
	public String getRoomInfo() {
		return roomInfo;
	}
	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	
}
