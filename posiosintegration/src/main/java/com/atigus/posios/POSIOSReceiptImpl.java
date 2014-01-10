package com.atigus.posios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.atigus.posios.dto.POSIOSReceiptObj;

public class POSIOSReceiptImpl implements POSIOSReceipt {
	
	private POSIOSCompany pOSIOSCompany;

	@Override
	public void getReceipts(String token, int companyID, long dateFrom,
			long dateTo) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<POSIOSReceiptObj> getReceipts(String token) {
			
			Vector<POSIOSReceiptObj> v = new Vector<POSIOSReceiptObj>();
			Vector<Integer> companyIDs = pOSIOSCompany.getCompanyIDs(token);
			for(Integer i : companyIDs){
				String params = "{id:\"2\",method:\"posiosApi.getReceipts\", params:[\""+token+"\","+i+",-1]}";
				System.out.println(params);
				//BufferedReader reader = new BufferedReader(new InputStreamReader(new RestConnection().postToREST(params)));
				String line = "";
				JsonFactory jfactory = new JsonFactory();
				
				
				try {
					//JsonParser jParser = jfactory.createJsonParser(new RestConnection().postToREST(params));
					/*
					while ((line = reader.readLine()) != null) {
						System.out.println("result "+line);
					}
					*/
					
					ObjectMapper mapper = new ObjectMapper();
					JsonNode rootNode = mapper.readTree(new InputStreamReader(new RestConnection().postToREST(params)));
					//node = node.get("result");
					
					JsonNode idNode = rootNode.path("id");
					System.out.println("**"+idNode.getTextValue());
					
					JsonNode resultNode = rootNode.path("result");
					
					Iterator<JsonNode> ite = resultNode.getElements();
					while (ite.hasNext()) {
						POSIOSReceiptObj receipt = new POSIOSReceiptObj();
						
						JsonNode temp = ite.next();
						receipt.setTotal(temp.get("total").getDoubleValue());
						System.out.println("total "+temp.get("total"));
						
						System.out.println("vat "+temp.get("vat"));
						receipt.setVat(temp.get("vat").getDoubleValue());
						
						System.out.println("customerID "+temp.get("customerId"));
						receipt.setCustomerId(temp.get("customerId").getIntValue());
						
						System.out.println("status "+temp.get("status"));
						receipt.setStatus(temp.get("status").getTextValue());
						
						System.out.println("payment "+temp.get("payment"));
						
						JsonNode paymentNode = temp.path("payment");
						
						JsonNode receiptID = paymentNode.path("receiptId");
						System.out.println("receiptID "+receiptID.getTextValue());
						receipt.setReceiptId(receiptID.getTextValue());
						
						JsonNode paymentTypeID = paymentNode.path("paymentTypeId");
						System.out.println("paymentTypeId "+paymentTypeID.getIntValue());
						receipt.setPaymentTypeId(paymentTypeID.getIntValue());
						
						JsonNode paymentType = paymentNode.path("type");
						System.out.println("paymentType "+paymentType.getTextValue());
						receipt.setPaymentType(paymentType.getTextValue());
						
						JsonNode paymentTypeType = paymentNode.path("paymentTypeTypeId");
						System.out.println("paymentTypeType "+paymentTypeType.getIntValue());
						receipt.setPaymentTypeType(paymentTypeType.getIntValue()+"");
						
						v.addElement(receipt);				
					}
					
					/*
					while (jParser.nextToken() != JsonToken.END_OBJECT) {
						String fieldname = jParser.getCurrentName();
						if("result".equals(fieldname)){
							jParser.nextToken();
							while (jParser.nextToken() != JsonToken.END_ARRAY) {
								//System.out.println(jParser.getText());
								if("total".equals(jParser.getText())){
									jParser.nextToken();
									System.out.println("total "+jParser.getText());
								}
								
							}
						}
					}
					*/
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		
			return v;

	}

	public POSIOSCompany getpOSIOSCompany() {
		return pOSIOSCompany;
	}

	public void setpOSIOSCompany(POSIOSCompany pOSIOSCompany) {
		this.pOSIOSCompany = pOSIOSCompany;
	}
	
	

}
