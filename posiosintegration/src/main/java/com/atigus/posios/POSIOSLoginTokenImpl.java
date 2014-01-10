package com.atigus.posios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.atigus.posios.dto.POSIOSToken;

public class POSIOSLoginTokenImpl implements POSIOSLoginToken {

	@Override
	public String getToken() {
		System.out.println("getting token.......");
		String param2 ="{id:\"2\",method:\"posiosApi.getApiToken\", params:[\"ghitPartner\",\"ghitPartner\",2,\"\",\"\",\"\",\"\",false,\"\",\"\"]}";
		POSIOSToken token = new POSIOSToken();
		ObjectMapper objectMapper = new ObjectMapper();
		
		RestConnection toRest = new RestConnection();
		try {
			token = objectMapper.readValue(toRest.postToREST(param2), POSIOSToken.class);
			//System.out.println("token :"+token.getResult());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return token.getResult();
	}

	@Override
	public String getToken(String username, String password) {
		System.out.println("getting token.......");
		String param2 ="{id:\"2\",method:\"posiosApi.getApiToken\", params:[\""+username+"\",\""+password+"\",2,\"\",\"\",\"\",\"\",false,\"\",\"\"]}";
		POSIOSToken token = new POSIOSToken();
		ObjectMapper objectMapper = new ObjectMapper();
		
		RestConnection toRest = new RestConnection();
		try {
			token = objectMapper.readValue(toRest.postToREST(param2), POSIOSToken.class);
			//System.out.println("token :"+token.getResult());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return token.getResult();
	}

}
