package com.atigus.posios.job;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.sql.DataSource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.atigus.posios.POSIOSPostGuestImpl;
import com.atigus.posios.dto.AtigusResponse;
import com.atigus.posios.dto.Guest;
import com.atigus.posios.dto.POSIOSRoom;


public class POSIOSCheckInJob implements Job {
	
	private static final String SQL ="SELECT id,operation,transactionid FROM integration_table WHERE operation=?";
	private static final String SQLFOLIO ="SELECT g.fullname,g.email,g.identification_no,g.pos_customer_no,gf.room_reservation_id,g.id FROM guest_folio gf,guest g WHERE gf.id=? AND g.id = gf.guest_id";
	private static final String SQLROOMRESERVATION ="SELECT a.id,a.room_id,b.room_no FROM room_reservation a, room b WHERE b.id = a.room_id AND a.id=?";
	private static final String SQLDELETE ="DELETE FROM integration_table WHERE id=?";
	private static final String SQLUPDATEGUEST ="UPDATE guest SET pos_customer_no =? WHERE id=?";
	private static final String SQLUPDATEROOMRESERVATION = "UPDATE room_reservation SET pos_room_no =? WHERE id=?";
	
	private DataSource frontDeskDS;
	private POSIOSPostGuestImpl pOSIOSPostGuest;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("this is my job.....");
		Connection conn = null;
		Vector<String> ids = new Vector<String>();
		try {
			conn = frontDeskDS.getConnection();
			PreparedStatement prep = conn.prepareStatement(SQL);
			prep.setString(1, "posios/postGuest");
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				System.out.println(rs.getLong("transactionid"));
				ids.addElement(rs.getLong("id")+"|"+rs.getLong("transactionid"));
			}
			rs.close();
			prep.close();
			System.out.println("processing "+ids.size()+" records");
			for(String i : ids){
				PreparedStatement prep2 = conn.prepareStatement(SQLFOLIO);
				String integrationId = i.substring(0,i.indexOf("|"));
				System.out.println("integrationId "+integrationId);
				String transactionId = i.substring(i.indexOf("|")+1);
				System.out.println("transactionId "+ transactionId);
				prep2.setLong(1, Long.parseLong(transactionId));
				ResultSet rs2 = prep2.executeQuery();
				if(rs2.next()){
					//System.out.println();
					//tokenizeName(rs2.getString("g.fullname"));
					Guest guest = new Guest();
					guest.setFullname(tokenizeFirstName(rs2.getString("g.fullname")));
					guest.setEmail(rs2.getString("g.email"));
					guest.setPosCustomerNo(rs2.getLong("g.pos_customer_no"));
					guest.setFirstname(tokenizeFirstName(rs2.getString("g.fullname")));
					guest.setLastname(tokenizeLastName(rs2.getString("g.fullname")));
					guest.setRoomNo(getRoomNo(rs2.getLong("gf.room_reservation_id")));
					AtigusResponse response = pOSIOSPostGuest.postGuest(convertObjectToJSON(guest));
					
					long reservationId = getRoomReservationID(rs2.getLong("gf.room_reservation_id"));
					
					if(response.getStatus().equals("SUCCESS")){
						System.out.println("successful push data to posios");
						PreparedStatement prepDelete = conn.prepareStatement(SQLDELETE);
						prepDelete.setLong(1, Long.parseLong(integrationId));
						prepDelete.execute();
						prepDelete.close();
						
						for(Object temp : response.getResults()){
							if(temp instanceof POSIOSRoom){
								POSIOSRoom room = (POSIOSRoom)temp;
								System.out.println("POSIOS customer ID "+room.getCustomerId());
								PreparedStatement prepUpdate = conn.prepareStatement(SQLUPDATEGUEST);
								prepUpdate.setLong(1,room.getCustomerId());
								prepUpdate.setLong(2, rs2.getLong("g.id"));
								prepUpdate.execute();
								prepUpdate.close();
								
							}
						
						}
						
					}
					else{
						System.out.println("failed to push data to posioas");
					}
				}
				rs2.close();
				prep2.close();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}
	private String convertObjectToJSON(Guest guest){
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = writer.writeValueAsString(guest);
			System.out.println(json);
			return json;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private String getRoomNo(Long roomReservationId){
		Connection conn = null;
		
		try {
			conn = frontDeskDS.getConnection();
			PreparedStatement prep  = conn.prepareStatement(SQLROOMRESERVATION);
			prep.setLong(1, roomReservationId);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				return rs.getString("b.room_no");
			}
			rs.close();
			prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private long getRoomReservationID(Long roomReservationId){
		Connection conn = null;
		
		try {
			conn = frontDeskDS.getConnection();
			PreparedStatement prep  = conn.prepareStatement(SQLROOMRESERVATION);
			prep.setLong(1, roomReservationId);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				return rs.getLong("a.id");
			}
			rs.close();
			prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private String tokenizeFirstName(String fullname){
		
		String[] names = fullname.split(" ");
		//System.out.println(String.format("FirstName: %s : LastName: %s", names[0], names[names.length-1]));
		return names[0];
		
	}
	private String tokenizeLastName(String fullname){
		
		String[] names = fullname.split(" ");
		//System.out.println(String.format("FirstName: %s : LastName: %s", names[0], names[names.length-1]));
		return names[names.length-1];
		
	}
	public DataSource getFrontDeskDS() {
		return frontDeskDS;
	}
	public void setFrontDeskDS(DataSource frontDeskDS) {
		this.frontDeskDS = frontDeskDS;
	}
	public POSIOSPostGuestImpl getpOSIOSPostGuest() {
		return pOSIOSPostGuest;
	}
	public void setpOSIOSPostGuest(POSIOSPostGuestImpl pOSIOSPostGuest) {
		this.pOSIOSPostGuest = pOSIOSPostGuest;
	}
	
	
	

}
