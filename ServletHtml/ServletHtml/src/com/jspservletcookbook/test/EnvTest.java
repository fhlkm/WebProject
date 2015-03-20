package com.jspservletcookbook.test;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class EnvTest {
	
	public static void main(String [] args)

    {

           Map m = System.getenv();

           for ( Iterator it = m.keySet().iterator(); it.hasNext(); )

           {

                  String key = (String ) it.next();

                  String value = (String )  m.get(key);

                  System.out.println(key +":" +value);

           }

           System.out.println( "--------------------------------------" );
          // String ="/home/cm360/public_html";

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
