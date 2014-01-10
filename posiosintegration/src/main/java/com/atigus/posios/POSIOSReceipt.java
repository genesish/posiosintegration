package com.atigus.posios;

import java.util.Vector;

import com.atigus.posios.dto.POSIOSReceiptObj;

public interface POSIOSReceipt {
	public void getReceipts(String token, int companyID,long dateFrom,long dateTo);
	public Vector<POSIOSReceiptObj> getReceipts(String token);
	
}
