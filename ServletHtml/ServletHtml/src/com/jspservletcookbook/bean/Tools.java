package com.jspservletcookbook.bean;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import com.google.gson.JsonObject;

public class Tools {
	public static  Timestamp getCurrentTime(){
		Timestamp timeStamp= new java.sql.Timestamp(System.currentTimeMillis());
		return timeStamp;
	}
	
	public static Calendar getCalander(){
		TimeZone tz = TimeZone.getTimeZone("America/Chicago"); 
//		TimeZone tz = TimeZone.getTimeZone("GMT+8"); 
		TimeZone.setDefault(tz);
		Calendar calendar = Calendar.getInstance(tz);
		return calendar;
	}

	public static JsonObject getJsonObject(Map<String, String[]> Info){
		JsonObject mJson = new JsonObject();
		Iterator iter = Info.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Map.Entry entry = (Map.Entry) iter.next(); 
			Object key = entry.getKey(); 
			String[] val = (String[])entry.getValue(); 
			mJson.addProperty(key.toString(), val[0]);
		} 
		return mJson;		
	}

}
