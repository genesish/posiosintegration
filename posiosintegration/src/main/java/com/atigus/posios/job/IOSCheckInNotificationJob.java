package com.atigus.posios.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.sql.DataSource;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;


public class IOSCheckInNotificationJob {
	private static final String SQL ="SELECT id,operation,transactionid FROM integration_table WHERE operation=?";
	private static final String SQLFOLIO ="SELECT g.fullname,g.email,g.identification_no,g.pos_customer_no,gf.room_reservation_id,g.id,g.gender FROM guest_folio gf,guest g WHERE gf.id=? AND g.id = gf.guest_id";
	private static final String SQLROOMRESERVATION ="SELECT a.id,a.room_id,b.room_no FROM room_reservation a, room b WHERE b.id = a.room_id AND a.id=?";
	private static final String SQLDELETE ="DELETE FROM integration_table WHERE id=?";
	private static final String SQLDEVICE = "SELECT token,keystore,keystore_password FROM iosdevice_token";
	
	
	private DataSource frontDeskDS;
	
	public void sendCheckInNotification(String input){
		System.out.println("send check-in notification");
		Connection conn = null;
		Vector<String> ids = new Vector<String>();
		
		try {
			conn = frontDeskDS.getConnection();
			PreparedStatement prep = conn.prepareStatement(SQL);
			prep.setString(1, "ios/checkInNotification");
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
					String guestName = rs2.getString("g.fullname");
					String gender =  rs2.getString("g.gender");

					String notificationMessage = guestName +" already checked in";
					sendNotification(notificationMessage);
					
				}
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
					e.printStackTrace();
				}
		}
		
		
	}
	
	private void sendNotification(String message){
		Connection conn = null;
		
		try {
			conn = frontDeskDS.getConnection();
			PreparedStatement prep = conn.prepareStatement(SQLDEVICE);
			ResultSet rs = prep.executeQuery();
			//int size = rs.last() ? rs.getRow() : 0;
			System.out.println("device size "+1);
			String devices [] = new String[1];
			int i = 0;
			String keystore = null;
			String password = null;
			while(rs.next()){
				String token = rs.getString("token");
				keystore = rs.getString("keystore");
				System.out.println("keystore found "+keystore);
				password = rs.getString("keystore_password");
				devices[i] = token;
				i++;
			}
			System.out.println(devices.length+" devices found");
			rs.close();
			prep.close();
			conn.close();
			//javaPNSSend(devices, message,keystore,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void javaPNSSend(String[] devices,String message,String keystore,String password){
		try {
			System.out.println("keystore "+keystore);
			//Push.test(keystore, password, false, devices);
			//System.out.println("done push");
			List<PushedNotification> notifications = Push.alert(message, keystore, password, false, devices);
			for (PushedNotification notification : notifications) {
                if (notification.isSuccessful()) {
                        /* Apple accepted the notification and should deliver it */  
                        System.out.println("Push notification sent successfully to: " +
                                                        notification.getDevice().getToken());
                        /* Still need to query the Feedback Service regularly */  
                } else {
                        String invalidToken = notification.getDevice().getToken();
                        /* Add code here to remove invalidToken from your database */  
                        System.out.println("invalidToken "+invalidToken);

                        /* Find out more about what the problem was */  
                        Exception theProblem = notification.getException();
                        theProblem.printStackTrace();

                        /* If the problem was an error-response packet returned by Apple, get it */  
                        ResponsePacket theErrorResponse = notification.getResponse();
                        if (theErrorResponse != null) {
                                System.out.println(theErrorResponse.getMessage());
                        }
                }
        }
			 List<Device> inactiveDevices = Push.feedback(keystore, password, false);
			 //System.out.println("babi");
			 for(Device in :inactiveDevices){
				System.out.println("get device id "+in.getDeviceId());
				System.out.println("get last register "+in.getLastRegister().toString());
			 }
			
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeystoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DataSource getFrontDeskDS() {
		return frontDeskDS;
	}

	public void setFrontDeskDS(DataSource frontDeskDS) {
		this.frontDeskDS = frontDeskDS;
	}
	
	
}
