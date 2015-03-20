package com.jspservletcookbook.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DBOP {

	public static void main(String[] args) {
//		TimeZone time = TimeZone.getTimeZone("GMT-5"); //设置为东八区
		TimeZone time = TimeZone.getTimeZone("America/Chicago");
		System.out.println(time);
		TimeZone.setDefault(time);// 设置时区
		Calendar calendar = Calendar.getInstance();// 获取实例
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//构造格式化模板
		Date date = calendar.getTime(); //获取Date对象
		String str = new String();
		str = format1.format(date);//对象进行格式化，获取字符串格式的输出
		System.out.println(str);
		/////////////////Chicago
		
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(str);  
        System.out.println(ts);  

	}

}
