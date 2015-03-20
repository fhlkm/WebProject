package com.jspservletcookbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.MySqlConnection;

public class CheckSelection extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MySqlConnection conn;
	String title =null;
//	final String manager ="fhlkm";
	final String  SUCCESS="Success!";
	final String  FAILURE ="Failure!";
	String username = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String selections = new String("");
		Map parameters= req.getParameterMap();
		for (Object key : parameters.keySet()) {
			if(key.toString().equals("title")){
				title =( (String[])(parameters.get("title")))[0];
			}else{
				selections= selections+key.toString();
				selections=selections+";";
			}
		}
		selections.trim().subSequence(0, selections.length()-1);
		conn = new MySqlConnection();
		conn.init();
		boolean action = false;
		Cookie[] user = req.getCookies();
		
		for(Cookie mCookie:user){
			if(mCookie.getName().equals("username"))
				username= mCookie.getValue();
		}
		if(title.equals(Constant.MANAGER)){// manager update, other empolyee insert		
			action =updateSelectionOption(selections,Constant.IT);			
		}else{
			action = insertSelectionOption(selections,username);
		}
		conn.closeDataBase();
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		PrintWriter out = resp.getWriter(); 
		if(action){

		         out.print(SUCCESS); 
		     
		}else{
			 out.print(FAILURE);
		}

	}
	private boolean updateSelectionOption(String selections, String name){
		boolean action = false;
		if(null != selections&& selections.length()>0){

			action = conn.updateSelections(selections, name);
		}
		return action;

	}
	private boolean  insertSelectionOption(String selections,String name){
		boolean action = false;
		
		action = conn.insertSelections(selections, name);
		
		return action;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}


}
