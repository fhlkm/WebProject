package com.jspservletcookbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;

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


public class Parameters extends HttpServlet {

	public final static String PARAMETERS="Parameters";
	public final static String COMMAND="command";
	public final static String PARAMETERS_SHOW="PARAMETERS_SHOW";
	public final static String MANAGER="MANAGER";
	public final static String EMPLOYEE="EMPLOYEE";
	private String username;
	private String pwd;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doGet(req, resp);
		//System.out.println(req.toString());
		
		Cookie[] mCookies = req.getCookies();
		for(Cookie mCookie:mCookies){
			if(mCookie.getName().equals("username"))
				username= mCookie.getValue();
		}
		if(null != req.getParameter(COMMAND)&&req.getParameter(COMMAND).equals(PARAMETERS)){
			resp.setContentType("text/html;charset=utf-8"); 
			resp.setStatus(HttpServletResponse.SC_OK);
			MySqlConnection conn = new MySqlConnection();
			conn.init();
			String title = conn.getParameterTitle();
			PrintWriter out = resp.getWriter(); 
			out.print(title); 
			conn.closeDataBase();
		}else if(null != req.getParameter(COMMAND)&&req.getParameter(COMMAND).equals(PARAMETERS_SHOW)){
			resp.setContentType("text/html;charset=utf-8"); 
			resp.setStatus(HttpServletResponse.SC_OK);
			MySqlConnection conn = new MySqlConnection();
			conn.init();
			String title = conn.getParameterTitle();
			JsonArray parameters = conn.getParameters();
			JsonObject mObject = new JsonObject();// INFO SEND TO HTML
			mObject.addProperty(MANAGER, title);
			mObject.add(EMPLOYEE, parameters);
			PrintWriter out = resp.getWriter(); 
			out.print(mObject.toString()); 
			conn.closeDataBase();
		}else{// insert parameters into database
			Timestamp mTime = Tools.getCurrentTime();
			JsonObject mJson =Tools.getJsonObject(req.getParameterMap());
			MySqlConnection conn = new MySqlConnection();
			conn.init();
			boolean action =conn.insertDeviceParameters(username, mJson.toString(), null);
//			System.out.println("insert DEVICESPARAMETERS:  "+action);
			conn.closeDataBase();
			resp.setContentType("text/html;charset=utf-8"); 
			resp.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = resp.getWriter(); 
			if(action){
				String text = "Hi, \n "+username+ " just report a new data about the water tempatrue, please check.";
				SendEmail.autoSend(username, text);
			out.print("Sucess");
			}
			else
			out.print("Failure");

		}


	}




	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doPost(req, resp);
		doGet(req,resp);

	}


}
