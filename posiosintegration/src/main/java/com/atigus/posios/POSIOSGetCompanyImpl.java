package com.atigus.posios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.atigus.posios.dto.POSIOSToken;

public class POSIOSGetCompanyImpl implements POSIOSGetCompany{
	
	private POSIOSLoginToken pOSIOSLoginToken;

	@Override
	public String getCompany(String input) {
		String token = pOSIOSLoginToken.getToken();
		
		System.out.println("getting token.......");
		String param2 ="{id:\"2\",method:\"posiosApi.getCompanies\", params:[\""+token+"\",1,10]}";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new RestConnection().postToREST(param2)));
		String line = "";
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println("result "+line);
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
