package com.atigus.posios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import com.sun.jersey.json.impl.reader.JsonReaderXmlEvent;

public class POSIOSTodayReceiptImpl {
	private POSIOSLoginToken pOSIOSLoginToken;
	
	public String todayReceipt(String input){
		String token = pOSIOSLoginToken.getToken();
		System.out.println("todayReceipt.......");
		Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.HOUR, 0);
		yesterday.set(Calendar.MINUTE,0);
		yesterday.set(Calendar.SECOND, 0);
		
		System.out.println("yesterday "+yesterday.getTime());
		long f = yesterday.getTimeInMillis();
		
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR, 23);
		today.set(Calendar.MINUTE,59);
		today.set(Calendar.SECOND, 0);
		
		System.out.println("yesterday "+today.getTime());
		long to = today.getTimeInMillis();
		
		String param2 ="{id:\"2\",method:\"posiosApi.getReceiptsByDate\", params:[\""+token+"\",8207,"+f+","+to+"]}";
		System.out.println(param2);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new RestConnection().postToREST(param2)));
		JsonFactory jfactory = new JsonFactory();
		String line = "";
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println("result "+line);
			}
			JsonParser jParser = jfactory.createJsonParser(new RestConnection().postToREST(param2));
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jParser.getCurrentName();
				System.out.println(fieldname);
				if("result".equals(fieldname)){
					jParser.nextToken(); // current token is "[", move next
					 
					  // messages is array, loop until token equal to "]"
					  while (jParser.nextToken() != JsonToken.END_ARRAY) {
			 
			                     // display msg1, msg2, msg3
					     System.out.println(jParser.getText()); 
			 
					  }
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "success";
	}

	public POSIOSLoginToken getpOSIOSLoginToken() {
		return pOSIOSLoginToken;
	}

	public void setpOSIOSLoginToken(POSIOSLoginToken pOSIOSLoginToken) {
		this.pOSIOSLoginToken = pOSIOSLoginToken;
	}
	
	
}
