package com.jspservletcookbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.MySqlConnection;
import com.jspservletcookbook.bean.AcountBean;

public class CheckAccount extends HttpServlet {
	final String  login_jsp ="logincookie.jsp";
	final String error_notification ="Username or Password Error!";
	final String Title="title";
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = null;
   		Cookie[] mCookies = request.getCookies();
		for(Cookie mCookie:mCookies){
			if(mCookie.getName().equals("username"))
				username= mCookie.getValue();
		}
		
		/*******************************************************************************/
//		response.setContentType("text/html;charset=utf-8"); 
//		response.setStatus(HttpServletResponse.SC_OK);
//		PrintWriter out2 = response.getWriter(); 
// 
//	         out2.print("Everything is OK"); 
//	         return;
		/******************************************************************************/
	   
		MySqlConnection sqlConn= new MySqlConnection();
		boolean connect = sqlConn.init();
		if(!connect){
			response.setContentType("text/html;charset=utf-8"); 
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			PrintWriter out = response.getWriter(); 
	 
		         out.print(sqlConn.getError());
		         return;
		}
		
		{
		// the title of the person is manager
			String title = sqlConn.getTitle(username);			
			String selection = sqlConn.getSelection(Constant.IT);
			Cookie mCookie = new Cookie(Title,Constant.EMPLOYEE);
			if(title.equals(Constant.MANAGER)){
				mCookie.setValue(Constant.MANAGER);
			}
			response.setContentType("text/html;charset=utf-8"); 
			response.addCookie(mCookie);		
			selection= selection.trim().substring(0,selection.length()-1);
			session.setAttribute("selections", selection);

			String check_page = "subclean.jsp";
			response.setStatus(HttpServletResponse.SC_OK);
//			response.sendRedirect(check_page);
//			sqlConn.closeDataBase();
			PrintWriter out = response.getWriter(); 
		    out.print(check_page); 
			sqlConn.closeDataBase();
		         
		     
			
		}
//		sqlConn.closeDataBase();
		    /* if ((username != null) && (username.trim().equals("fhlkm"))) {
		      if ((pwd != null) && (pwd.trim().equals("123456"))) {
		       System.out.println("session");
		       session.setAttribute("account", account);
		      // String logon_suc = "session.jsp";
		       String check_page = "dynamic.jsp";
		       response.sendRedirect(check_page);
		       return;
		      }
		     }*/
		
/*		response.setContentType("text/html;charset=utf-8"); 
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		PrintWriter out = response.getWriter(); 
 
	         out.print(error_notification); */
	     
		
//		session.setAttribute("fail",error_notification);
//		response.sendRedirect(login_jsp);
//		     String logon_fail = "fail.jsp";
//		     response.sendRedirect(logon_fail);

		     
		}

		public void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {

		     doGet(request, response);
		
			
		}
		
}
