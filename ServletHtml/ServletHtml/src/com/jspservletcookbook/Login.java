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

public class Login extends HttpServlet {
	final String  login_jsp ="logincookie.jsp";
	final String error_notification ="Username or Password Error!";
	final String Title="title";
	private String employess_title = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		employess_title = null;
		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		HttpSession session = request.getSession();

		MySqlConnection sqlConn= new MySqlConnection();
		boolean connect = sqlConn.init();
		if(!connect){
			response.setContentType("text/html;charset=utf-8"); 
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			PrintWriter out = response.getWriter(); 

			out.print(sqlConn.getError());
			return;
		}

		if(sqlConn.checkUserAndPwd(username, pwd)){// user name and password are right
			Cookie[] mCookies = request.getCookies();
			for(Cookie cookie:mCookies){
				if(cookie.getName().equals("Title")){
					employess_title= cookie.getValue();
					if(employess_title.equals(Constant.MANAGER)){
						cookie.setValue(Constant.MANAGER);
					}else{
						cookie.setValue(Constant.EMPLOYEE);
					}
				}
			}
			
			if(employess_title== null){
				String title = sqlConn.getTitle(username);			
				Cookie mCookie = new Cookie(Title,Constant.EMPLOYEE);
				if(title.equals(Constant.MANAGER)){
					mCookie.setValue(Constant.MANAGER);
				}else{
					mCookie.setValue(Constant.EMPLOYEE);
				}
				response.addCookie(mCookie);
			}

					
			String check_page = "main.jsp";
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter(); 
			out.print(check_page); 
			sqlConn.closeDataBase();

			return;
		}


		response.setContentType("text/html;charset=utf-8"); 
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		PrintWriter out = response.getWriter(); 

		out.print(error_notification); 
		return;


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}


}
