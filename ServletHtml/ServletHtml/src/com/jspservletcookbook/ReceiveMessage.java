package com.jspservletcookbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.MySqlConnection;
import com.google.gson.JsonObject;
import com.jspservletcookbook.bean.Tools;
/**
 * Receive the message from customer
 * @author hanlu Feng
 *
 */
public class ReceiveMessage extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JsonObject mJson =Tools.getJsonObject(req.getParameterMap());
		resp.setContentType("text/html;charset=utf-8"); 
		MySqlConnection conn = new MySqlConnection();
		conn.init();
		boolean insertAction = conn.insertCustomerReport(mJson);
		conn.closeDataBase();
		resp.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = resp.getWriter(); 
		if(insertAction)
		out.print("Sucess");
		else
			out.print("false");
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);

		
	}
	
	

	
}
