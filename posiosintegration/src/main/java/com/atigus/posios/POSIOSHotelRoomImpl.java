package com.atigus.posios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPasswordField;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import com.atigus.posios.dto.POSIOSRoom;

public class POSIOSHotelRoomImpl implements POSIOSHotelRoom {
	
	private POSIOSLoginToken pOSIOSLoginToken;

	@Override
	public Vector<String> list() {
		String token = pOSIOSLoginToken.getToken("ghitPartner","ghitPartner");
		String param ="{id:\"2\",method:\"posiosApi.getRooms\", params:[\""+token+"\",8207]}";
		//{id:"2",method:"posiosApi.getRooms", params:["8525b1d4-13b5-4490-bf0c-f793233eef75",8207]}
		System.out.println(param);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new RestConnection().postToREST(param)));
		String line = "";
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println("result "+line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Vector<POSIOSRoom> list(String token,Integer companyID) {
		System.out.println("getting room list..."+companyID);
		String param ="{id:\"2\",method:\"posiosApi.getRooms\", params:[\""+token+"\","+companyID+"]}";
		//System.out.println(param);
		Vector<POSIOSRoom> v = new Vector<POSIOSRoom>();
		//BufferedReader reader = new BufferedReader(new InputStreamReader(new RestConnection().postToREST(param)));
		String line = "";
		try {
			//while ((line = reader.readLine()) != null) {
				//System.out.println("result "+line);
			//}
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new InputStreamReader(new RestConnection().postToREST(param)));
			System.out.println("Room Result "+rootNode.toString());
			JsonNode idNode = rootNode.path("id");
			//System.out.println("**"+idNode.getTextValue());
			JsonNode resultNode = rootNode.path("result");
			Iterator<JsonNode> ite = resultNode.getElements();
			while (ite.hasNext()) {
				JsonNode temp = ite.next();
				
				POSIOSRoom room = new POSIOSRoom();
				room.setCustomerId(temp.path("customerId").getIntValue());
				room.setLastname(temp.path("lastname").getTextValue());
				room.setRoomInfo(temp.path("info").getTextValue());
				room.setGuestname(temp.path("name").getTextValue());
				room.setCompanyId(temp.path("companyId").getIntValue());
				v.add(room);
			}
			
			/*
			JsonFactory jfactory = new JsonFactory();
			JsonParser jParser = jfactory.createJsonParser(new RestConnection().postToREST(param));
			
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jParser.getCurrentName();
				System.out.println("fieldname "+fieldname);
				if("result".equals(fieldname)){
					jParser.nextToken();
					while (jParser.nextToken() != JsonToken.END_ARRAY) {
						//System.out.println("fieldname "+jParser.getText());
						POSIOSRoom room = new POSIOSRoom();
						
						if("lastName".equals(jParser.getText())){
							jParser.nextToken();
							room.setLastname(jParser.getText());
							//System.out.println(jParser.getText());
							
						}
						
						if("guestName".equals(jParser.getText())){
							jParser.nextToken();
							room.setGuestname(jParser.getText());
							//System.out.println(jParser.getText());						
						}
						if("info".equals(jParser.getText())){
							jParser.nextToken();
							if(jParser.getText()!= null){
								room.setRoomInfo(jParser.getText());
								//System.out.println(jParser.getText());
								v.addElement(room);
							}
								
						}
						
						
					}
				}
			}
			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return v;
	}

	public POSIOSLoginToken getpOSIOSLoginToken() {
		return pOSIOSLoginToken;
	}

	public void setpOSIOSLoginToken(POSIOSLoginToken pOSIOSLoginToken) {
		this.pOSIOSLoginToken = pOSIOSLoginToken;
	}

	
	
	

}
