package com.jspservletcookbook.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmkcode.vo.CreateFileUtil;

public class ReadFileTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		

		    //Use Any Environmental Variable , here i have used CATALINA_HOME
		test();
		    String propertyHome = System.getenv("CATALINA_HOME");           
		    if(null == propertyHome){

		        //This is a system property that is  passed
		        // using the -D option in the Tomcat startup script
		        propertyHome  =  System.getProperty("PROPERTY_HOME");
		    

		    }
		    CreateFileUtil.createDir("/home/cm360/public_html/"+"pictures");
	}
	
	
	private void  test(){
	      Map m = System.getenv();

          for ( Iterator it = m.keySet().iterator(); it.hasNext(); )

          {

                 String key = (String ) it.next();

                 String value = (String )  m.get(key);

                 System.out.println(key +":" +value);

          }

          System.out.println( "--------------------------------------" );

          Properties p = System.getProperties();

         

          for ( Iterator it = p.keySet().iterator(); it.hasNext(); )

          {

                 String key = (String ) it.next();

                 String value = (String )  p.get(key);

                 System.out.println(key +":" +value);

          }
          
          System.out.println( "--------------------------------------" );
          System.out.println(System.getenv("CATALINA_HOME"));

		
	}

}
