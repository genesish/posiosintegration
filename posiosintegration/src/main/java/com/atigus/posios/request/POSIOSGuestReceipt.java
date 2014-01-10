package com.atigus.posios.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.atigus.posios.POSIOSCompany;
import com.atigus.posios.POSIOSLoginToken;
import com.atigus.posios.POSIOSReceipt;
import com.atigus.posios.dto.POSIOSReceiptObj;

public class POSIOSGuestReceipt {
	private POSIOSReceipt pOSIOSReceipt; 
	private POSIOSLoginToken pOSIOSLoginToken;
	private POSIOSCompany pOSIOSCompany;
	
	public List<POSIOSReceiptObj> getGuestReceipt(String input){
		String token = pOSIOSLoginToken.getToken();
		List<POSIOSReceiptObj> temp = new ArrayList<POSIOSReceiptObj>();
		Vector<POSIOSReceiptObj> v = pOSIOSReceipt.getReceipts(token);
		for(POSIOSReceiptObj i: v){
			if(i.getStatus().equals("payed")){
				temp.add(i);
			}
		}
		System.out.println(v.size());
		return temp;
	}
	public POSIOSReceipt getpOSIOSReceipt() {
		return pOSIOSReceipt;
	}
	public void setpOSIOSReceipt(POSIOSReceipt pOSIOSReceipt) {
		this.pOSIOSReceipt = pOSIOSReceipt;
	}
	public POSIOSLoginToken getpOSIOSLoginToken() {
		return pOSIOSLoginToken;
	}
	public void setpOSIOSLoginToken(POSIOSLoginToken pOSIOSLoginToken) {
		this.pOSIOSLoginToken = pOSIOSLoginToken;
	}
	public POSIOSCompany getpOSIOSCompany() {
		return pOSIOSCompany;
	}
	public void setpOSIOSCompany(POSIOSCompany pOSIOSCompany) {
		this.pOSIOSCompany = pOSIOSCompany;
	}
	
	
}
