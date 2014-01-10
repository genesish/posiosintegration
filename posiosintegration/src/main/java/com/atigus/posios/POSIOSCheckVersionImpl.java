package com.atigus.posios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class POSIOSCheckVersionImpl {
	
	private POSIOSLoginToken pOSIOSLoginToken;

	public String getVersion(String s) {
		if(pOSIOSLoginToken == null){
			System.err.println("pOSIOSLoginToken is null");
		}
		String token = pOSIOSLoginToken.getToken();
	
		return token;
	}

	public POSIOSLoginToken getpOSIOSLoginToken() {
		return pOSIOSLoginToken;
	}

	public void setpOSIOSLoginToken(POSIOSLoginToken pOSIOSLoginToken) {
		this.pOSIOSLoginToken = pOSIOSLoginToken;
	}
	
	

}
