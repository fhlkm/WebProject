package com.jspservletcookbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.MySqlConnection;
import com.google.gson.JsonArray;

public class ShowSelections  extends HttpServlet{

	public final static String INFO ="info";
	public final static String REPORT ="problem report";
	public final static String IMAGE ="images";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
		
	      
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		super.doPost(req, resp);
//		doGet(req,resp);
		MySqlConnection sqlConn= new MySqlConnection();
		sqlConn.init();
		if(req.getParameter("command").equals(INFO)){
		
			JsonArray arrays = sqlConn.getWorkReportJson();
			resp.setContentType("text/html;charset=utf-8"); 
			resp.setStatus(resp.SC_OK);
			if(null !=resp.getWriter()){
				resp.getWriter().write(arrays.toString());
			}
		}else if(req.getParameter("command").equals(REPORT)){
			JsonArray arrays = sqlConn.getProblemsDetail();
			resp.setContentType("text/html;charset=utf-8"); 
			resp.setStatus(resp.SC_OK);
			if(null !=resp.getWriter()){
				resp.getWriter().write(arrays.toString());
			}
		}else if(req.getParameter("command").equals(IMAGE)){
			String selection = sqlConn.getImagesDetail(req.getParameter("name"), req.getParameter("time")).toString();
			resp.setContentType("text/html;charset=utf-8");
			resp.setStatus(resp.SC_OK);
			if(null!= resp.getWriter()){
				resp.getWriter().write(selection);
			}
			
		}
		
		sqlConn.closeDataBase();
	}
	
	

}
