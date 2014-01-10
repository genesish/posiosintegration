package com.atigus.posios.dto;

import java.io.Serializable;

public class POSIOSReceiptObj implements Serializable {
	
	private double total;
	private double vat;
	private int customerId;
	private String status;
	private String receiptId;
	private int paymentTypeId;
	private String paymentType;
	private String paymentTypeType;
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public int getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentTypeType() {
		return paymentTypeType;
	}
	public void setPaymentTypeType(String paymentTypeType) {
		this.paymentTypeType = paymentTypeType;
	}
	
	
}
