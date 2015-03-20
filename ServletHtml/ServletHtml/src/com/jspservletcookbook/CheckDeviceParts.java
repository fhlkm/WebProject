package com.jspservletcookbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.MySqlConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jspservletcookbook.bean.Tools;
import com.mail.SendEmail;

public class CheckDeviceParts extends HttpServlet {
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getParameter("command")!= null){
			MySqlConnection conn = new MySqlConnection();
			conn.init();
			JsonArray mArray = conn.getEquipmentParts();
			JsonObject mObject = new JsonObject();
			mObject.add("Parts", mArray);
			resp.setContentType("text/html;charset=utf-8"); 
			resp.setStatus(resp.SC_OK);
			resp.getWriter().write(mArray.toString());
			conn.closeDataBase();
			
		}else{
		MySqlConnection conn = new MySqlConnection();
		conn.init();
		boolean insert = conn.insertEquipmentParts(Tools.getJsonObject(req.getParameterMap()));
		resp.setContentType("text/html;charset=utf-8"); 
		resp.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = resp.getWriter(); 
		if(insert){
		out.print("Sucess");
		}
		else
		out.print("Failure");
		conn.closeDataBase();
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req,resp);
	}
	

}
