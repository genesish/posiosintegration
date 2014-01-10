package com.atigus.posios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class POSIOSCompanyImpl implements POSIOSCompany {

	@Override
	public Vector<Integer> getCompanyIDs(String token) {
		System.out.println("getting company......");
		Vector<Integer> v = new Vector<Integer>();
		String param="{id:\"2\",method:\"posiosApi.getCompanies\", params:[\""+token+"\",0,1000]}";
		//System.out.println(param);
		//BufferedReader reader = new BufferedReader(new InputStreamReader(new RestConnection().postToREST(param)));
		//String line = "";
		try {
			
			JsonFactory jfactory = new JsonFactory();
			JsonParser jParser = jfactory.createJsonParser(new RestConnection().postToREST(param));
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jParser.getCurrentName();
				//System.out.println(fieldname);
				if("result".equals(fieldname)){
					jParser.nextToken();
					while (jParser.nextToken() != JsonToken.END_ARRAY) {
						//System.out.println(jParser.getText());
						if("oid".equals(jParser.getText())){
							jParser.nextToken();
							v.addElement(new Integer(jParser.getText()));
						}
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return v;
	}

}
