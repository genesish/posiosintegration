package com.atigus.posios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.atigus.posios.dto.AtigusResponse;
import com.atigus.posios.dto.Guest;
import com.atigus.posios.dto.POSIOSRoom;

public class POSIOSPostGuestImpl {
	
	private POSIOSLoginToken pOSIOSLoginToken;
	private POSIOSHotelRoom pOSIOSHotelRoom;
	private POSIOSCompany pOSIOSCompany;
	
	public AtigusResponse postGuest(String input){
		//System.out.println("INPUT "+input);
		AtigusResponse resp = new AtigusResponse();
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			Guest guest = mapper.readValue(input, Guest.class);
		
			Long customerID = guest.getPosCustomerNo();
		
		String token = pOSIOSLoginToken.getToken();
		Vector<Integer> companyID =  pOSIOSCompany.getCompanyIDs(token);
		System.out.println(companyID);
		for(int i : companyID){
			
			
			String param2 ="{id:\"2\",method:\"posiosApi.setRooms\", params:[\""+token+"\","+i+",[{\"info\":\""+guest.getRoomNo()+"\",\"name\":\""+guest.getRoomNo()+"\",\"companyId\":"+i+",\"customerId\":"+customerID+",\"name\":\""+guest.getFullname()+"\",\"firstName\":\""+guest.getFirstname()+"\",\"lastName\":\""+guest.getLastname()+"\",\"email\":\""+guest.getEmail()+"\"}]]}";
			if(customerID > 0){
				param2 ="{id:\"2\",method:\"posiosApi.setRooms\", params:[\""+token+"\","+i+",[{\"info\":\""+guest.getRoomNo()+"\",\"name\":\""+guest.getRoomNo()+"\",\"companyId\":"+i+",\"customerId\":"+customerID+",\"name\":\""+guest.getFullname()+"\"}]]}";
			}
			
			System.out.println("params2 "+param2);
		
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new RestConnection().postToREST(param2)));
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					System.out.println("result "+line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("server response :"+line);
			
			Vector<POSIOSRoom> rooms = pOSIOSHotelRoom.list(token, i);
			for(POSIOSRoom j :rooms){
				System.out.println("ROOM No "+j.getRoomInfo()+"customerID "+j.getCompanyId()+" customer name "+j.getGuestname());
				
				if(guest.getRoomNo().equalsIgnoreCase(j.getRoomInfo()) && guest.getFullname().equals(j.getGuestname())){
					resp.setStatus("SUCCESS");
					resp.setTransactionDate(new Date().getTime());
					POSIOSRoom room = new POSIOSRoom();
					room.setCustomerId(j.getCustomerId());
					room.setRoomInfo(j.getRoomInfo());
					resp.addResults(room);
				}
				
			}
			
			
			
		}
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
		return resp;
	}

	public POSIOSLoginToken getpOSIOSLoginToken() {
		return pOSIOSLoginToken;
	}

	public void setpOSIOSLoginToken(POSIOSLoginToken pOSIOSLoginToken) {
		this.pOSIOSLoginToken = pOSIOSLoginToken;
	}

	public POSIOSHotelRoom getpOSIOSHotelRoom() {
		return pOSIOSHotelRoom;
	}

	public void setpOSIOSHotelRoom(POSIOSHotelRoom pOSIOSHotelRoom) {
		this.pOSIOSHotelRoom = pOSIOSHotelRoom;
	}

	public POSIOSCompany getpOSIOSCompany() {
		return pOSIOSCompany;
	}

	public void setpOSIOSCompany(POSIOSCompany pOSIOSCompany) {
		this.pOSIOSCompany = pOSIOSCompany;
	}
	
	
	
	
}
