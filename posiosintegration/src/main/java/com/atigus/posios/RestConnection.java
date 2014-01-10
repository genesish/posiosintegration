package com.atigus.posios;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class RestConnection {
	private static final String REST_URL = "http://dev.posios.com:8080/PosServer/JSON-RPC";
	public InputStream postToREST(String param){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(REST_URL);
		StringEntity entity = new StringEntity(param,ContentType.APPLICATION_JSON);
		httpPost.setEntity(entity);
		
		try {
			HttpResponse r = httpClient.execute(httpPost);
			return r.getEntity().getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
