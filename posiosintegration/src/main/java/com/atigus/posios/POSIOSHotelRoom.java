package com.atigus.posios;

import java.util.Vector;

import com.atigus.posios.dto.POSIOSRoom;

public interface POSIOSHotelRoom {
	public Vector<String> list();
	public Vector<POSIOSRoom> list(String token,Integer companyID);
}
