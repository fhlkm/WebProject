package com.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jspservletcookbook.bean.Tools;
import com.jspservletcookbook.bean.WorkReportBean;



public class MySqlConnection {
	final static String driver = "com.mysql.jdbc.Driver";
	final static String CLASS_NOT_FOUND="Class Not Found";
	final static String Can_not_connect="Database can't be connected";
	String error = null;
	// create a database cm360_cm360

	final static String url = "jdbc:mysql://localhost:3306";
//	final static String url = "localhost/sqlexpress";
	
//	final static String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
//	final static String url ="jdbc:mysql://localhost/cm360_cm360?user=root&password=root&useUnicode=true&characterEncoding=8859_1";
//	final static String url = "jdbc:odbc:cm360_cm360";

	// username

//	final static String user = "root";
	final static String user = "cm360_cm360";//adminTariMnr

	// password

//	final static String  password = "root";
	final static String  password ="cm360_cm360";//adminTariMnr
	final static String createDatabase="CREATE database  if not exists cm360_cm360;";
	final static String createTableEmployee="CREATE TABLE IF NOT EXISTS EMPLOYEE(SNO VARCHAR(7) NOT NULL,  SNAME VARCHAR(8) NOT NULL,  SPASSWORD VARCHAR(11) NOT NULL,SEX VARCHAR(8) NOT NULL,  BDATE DATETIME NOT NULL,  HEIGHT DEC(5,2) DEFAULT 000.00,  PRIMARY KEY(SNO), UNIQUE KEY (SNAME))ENGINE=InnoDB";		
	final static String insertData="INSERT INTO EMPLOYEE VALUES (2014,'enlan','12345','male','2014-6-10 11:00:00',160.00)";
	final static String useDatabase="use cm360_cm360";
	final static String setUpdate="SET SQL_SAFE_UPDATES=0";
	
	final static String createTableReport="CREATE TABLE IF NOT EXISTS WORKREPORT(SNO VARCHAR(7) NOT NULL,  SNAME VARCHAR(8) NOT NULL,SDATA DATETIME ,  SCHECKLIST VARCHAR(150),SIMAGE VARCHAR(20),FOREIGN KEY (SNO) REFERENCES EMPLOYEE (SNO) ON UPDATE CASCADE,FOREIGN KEY(SNAME) REFERENCES EMPLOYEE (SNAME) ON UPDATE CASCADE ) ENGINE=InnoDB";
	final static String insertReportData ="INSERT INTO WORKREPORT VALUES (2014,'enlan',' 2014-6-10 14:38:59','abc','afdsfdfds')";
	static String name="";
	static String queryCount = "SELECT COUNT(*) from EMPLOYEE WHERE SNAME="+name;
    Statement statement= null;
    Connection conn;
    
    
	public MySqlConnection() {
	}

	public boolean init(){
		boolean connect = true;
		try {
				Class.forName(driver);

			// connect database

			

			conn = DriverManager.getConnection(url, user, password);
//				conn = DriverManager.getConnection(url);


			if(!conn.isClosed())

				System.out.println("Succeeded connecting to the Database!");

			// create sql sql

			statement = conn.createStatement();
//			statement.execute(createDatabase);
			statement.execute(useDatabase);
			statement.execute(createTableEmployee);
			statement.execute(createTableReport);
/*			statement.execute(createTableEmployee);
			statement.execute(insertData);
			statement.execute(setUpdate);*/
			// execute sql

			String sql = "select * from EMPLOYEE";
			ResultSet rs = statement.executeQuery(sql); 
			while(rs.next()) {
				String name = rs.getString("sname");
//				System.out.println(name);
			}
//			rs = statement.executeQuery("SELECT COUNT(*) from EMPLOYEE WHERE SNAME='fhlkm'");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			error = CLASS_NOT_FOUND;
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			error = Can_not_connect;
			connect = false;
			e.printStackTrace();
		} 
		return connect;
	}
	public String getError() {
		return error;
	}
	
	public void closeDataBase(){
		
			try {
				if(null != conn)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public  boolean checkUserAndPwd(String username,String pwd){
		if(null != statement)
		try {
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) from EMPLOYEE WHERE SNAME="+"'"+username+"'");
//			ResultSet rs = statement.executeQuery("SELECT COUNT(*) from EMPLOYEE WHERE SNAME='fhlkm'");
			int count=0;
			while(rs.next()) {
				count = rs.getInt(1);
				//				String name = rs.getString("sname");
				//				System.out.println(name);
			}
			if(count==0){
//				System.out.println("not exist username");
				return false;
			}else{
				rs = statement.executeQuery("SELECT SPASSWORD FROM EMPLOYEE WHERE SNAME='"+username+"'");
				while(rs.next()){
					if(pwd.equals(rs.getString("SPASSWORD"))){
						return true;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	public static void main(String[] args)
	{
		

		//add drive

		try {
			Class.forName(driver);

			// connect database

			Connection conn;

			conn = DriverManager.getConnection(url, user, password);


			if(!conn.isClosed())

				System.out.println("Succeeded connecting to the Database!");

			// create sql sql

			Statement statement = conn.createStatement();
			statement.execute(createDatabase);
			statement.execute(useDatabase);
			statement.execute(createTableEmployee);
			statement.execute(createTableReport);
/*			statement.execute(createTableEmployee);
			statement.execute(insertData);
			statement.execute(setUpdate);*/
			// execute sql

			String sql = "select * from EMPLOYEE";
			ResultSet rs = statement.executeQuery(sql); 
			rs = statement.executeQuery("SELECT COUNT(*) from EMPLOYEE WHERE SNAME='fhlkm'");
			int count=0;
			while(rs.next()) {
				count = rs.getInt(1);
//				String name = rs.getString("sname");
//				System.out.println(name);
			}
			if(count==0){
				System.out.println("not exist");
			}else{
				rs = statement.executeQuery("SELECT SPASSWORD FROM EMPLOYEE WHeRE SNAME='fhlkm'");
				while(rs.next()){
					String pwd = rs.getString("SPASSWORD");
//					System.out.println("password:"+pwd);
				}
			}
			
			
			// check
			
			rs = statement.executeQuery("SELECT COUNT(*) from EMPLOYEE WHERE SNAME='fhlkm'");
			int count2=0;
			while(rs.next()) {
				count2 = rs.getInt(1);
				//				String name = rs.getString("sname");
				//				System.out.println(name);
			}
			if(count2==0){
//				System.out.println("not exist username");
				
			}else{
				rs = statement.executeQuery("SELECT SPASSWORD FROM EMPLOYEE WHeRE SNAME='fhlkm'");
				while(rs.next()){
					
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * update the selections added by the manager
	 * @param selections the selection that will be updated
	 * @param name manager's name
	 */
	public boolean updateSelections(String selections, String name){
		boolean action = true;
		String sql = "UPDATE WORKREPORT SET SCHECKLIST='"+selections+"' WHERE SNAME='"+name+"'";
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			action = false;
			e.printStackTrace();
		}
		return action;
		
	}
	/**
	 * 
	 * Insert the new selection added by the employee
	 * @param selection
	 * @param name
	 */
	public boolean insertSelections(String selection, String name){
		String sql ="INSERT INTO WORKREPORT VALUES (?,?,?,?,?)";
		boolean action = true;
        try {
			PreparedStatement stmt = conn.prepareStatement(sql);
//			SimpleDateFormat dateFormat =  new java.text.SimpleDateFormat("yyyy：MM：dd hh:mm:ss");
//			String formatDate = dateFormat.format(new java.util.Date(System.currentTimeMillis())); 
//			java.util.Date mDate = dateFormat.parse(formatDate);
//		    DateTime dt = new DateTime();
//		    DateTime dtChicago =dt.withZone(DateTimeZone.forID("America/Chicago"));
//			java.sql.Timestamp timeStamp = new java.sql.Timestamp(dtChicago.getMillis());

			java.sql.Timestamp timeStamp= new java.sql.Timestamp(System.currentTimeMillis());
			stmt.setInt(1, Integer.parseInt(getNumberByName(name).trim()));
			stmt.setString(2, name);
			//			stmt.setDate(3, time);
			stmt.setTimestamp(3, timeStamp,Tools.getCalander());
			stmt.setString(4, selection);
			stmt.setString(5, "N/A");
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			action = false;
			e.printStackTrace();
		}	
		return action;
	}
	
	/**
	 * insert the data into table DEVICESPARAMETERS
	 * @param name
	 * @param parameters json data about the parameters into table DEVICESPARAMETERS
	 * @param description if it is null, we just insert "N/A"
	 * @return TRUE  if insert success
	 */
	public boolean insertDeviceParameters(String name, String parameters,String description){
		String sql ="INSERT INTO DEVICESPARAMETERS VALUES (?,?,?,?)";
		boolean action = true;
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);		
			stmt.setString(1, name);
			stmt.setTimestamp(2, Tools.getCurrentTime(), Tools.getCalander());
			stmt.setString(3, parameters);
			if(null == description){
				stmt.setString(4, "N/A");
			}else{
				stmt.setString(4, description);
			}
			stmt.execute();
		}catch (SQLException e){
			action = false;
			e.printStackTrace();
		}
		return action;
	}
	public String getNumberByName(String name){
		String sql = "SELECT SNO FROM EMPLOYEE WHERE SNAME='"+name+"'";
		String selections = null;
		try {
			ResultSet rs=	statement.executeQuery(sql);
			while(rs.next()) {
				selections = rs.getString("SNO");
				break;
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selections;
	}

	public String getParameterTitle(){
		String sql = "SELECT DEVICESPARAMETERS.*  FROM DEVICESPARAMETERS,EMPLOYEE WHERE  DEVICESPARAMETERS.SNAME=EMPLOYEE.SNAME AND EMPLOYEE.BTITLE ='Manager'" ;
		String parameters= null;
		boolean action = true;
		try{
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				parameters = rs.getString("SPARAMETERS");
			}
		}catch(SQLException e){
			action = false;
			e.printStackTrace();
		}
		return parameters;
		
	}
	
	public JsonArray getParameters(){
		String sql = "SELECT DEVICESPARAMETERS.*  FROM DEVICESPARAMETERS,EMPLOYEE WHERE  DEVICESPARAMETERS.SNAME=EMPLOYEE.SNAME AND EMPLOYEE.BTITLE ='Employee'" ;
		JsonArray mArray = new JsonArray();
		String parameters= null;
		JsonObject mObject;
		try{
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				parameters = rs.getString("SPARAMETERS");
			    mObject = new JsonObject();
			    mObject.addProperty("parameter", parameters);
			    mObject.addProperty(WorkReportBean.Name,rs.getString("SNAME"));
			    Timestamp mTimeStamp= rs.getTimestamp("SDATE", Tools.getCalander());
				mObject.addProperty(WorkReportBean.Date,mTimeStamp.toString().substring(0,mTimeStamp.toString().length()-2));
			    mArray.add(mObject);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return mArray;
		
	}
	public String getTitle(String name){
		String title= null;
		String sql="SELECT BTitle FROM EMPLOYEE WHERE SNAME = '"+name+"'";
		try {
			ResultSet rs=	statement.executeQuery(sql);
			while(rs.next()) {
				title = rs.getString("BTitle");
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return title;
	}
	public String getSelection(String name){
		String sql="SELECT SCHECKLIST FROM  WORKREPORT WHERE SNAME='"+name+"'";
		String selections = null;
		try {
			ResultSet rs=	statement.executeQuery(sql);
			while(rs.next()) {
				selections = rs.getString("SCHECKLIST");
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selections;
	}

	/*var employees = [{ "Number":"Bill" , "Name":"Gates" ,"Name":"Gates" ,"Time":"2014-06-10 13:00:00","Checlists":"elevators;floor;","Image":"N/A"},
	{ "Number":"hanlu" , "Name":"Gates" ,"Name":"Gates" ,"Time":"2014-06-10 13:00:00","Checlists":"elevators;floor;","Image":"N/A"},
	];*/
	public JsonArray getWorkReportJson(){
		String sql = "SELECT WORKREPORT.*  FROM WORKREPORT,EMPLOYEE WHERE  WORKREPORT.SNAME=EMPLOYEE.SNAME AND EMPLOYEE.BTITLE ='Manager'";
		JsonArray gsonArray = new JsonArray();
		JsonObject jsonObject = new JsonObject();
		try {
			ResultSet rs=	statement.executeQuery(sql);
			while(rs.next()) {
			jsonObject = new JsonObject();
			jsonObject.addProperty(WorkReportBean.SNO, rs.getString("SNO"));
			jsonObject.addProperty(WorkReportBean.Name,rs.getString("SNAME"));
		    Timestamp mTimeStamp= rs.getTimestamp("SDATA", Tools.getCalander());
			jsonObject.addProperty(WorkReportBean.Date,mTimeStamp.toString().substring(0,mTimeStamp.toString().length()-2));
			jsonObject.addProperty(WorkReportBean.SCHECKLIST,rs.getString("SCHECKLIST"));
			jsonObject.addProperty(WorkReportBean.Image, rs.getString("SIMAGE"));
			gsonArray.add(jsonObject);
			}
			sql ="SELECT WORKREPORT.*  FROM WORKREPORT,EMPLOYEE WHERE  WORKREPORT.SNAME=EMPLOYEE.SNAME AND EMPLOYEE.BTITLE ='Employee' order by SDATA DESC";
			rs=	statement.executeQuery(sql);
			while(rs.next()) {
			jsonObject = new JsonObject();
			jsonObject.addProperty(WorkReportBean.SNO, rs.getString("SNO"));
			jsonObject.addProperty(WorkReportBean.Name,rs.getString("SNAME"));
		    Timestamp mTimeStamp= rs.getTimestamp("SDATA", Tools.getCalander());
			jsonObject.addProperty(WorkReportBean.Date,mTimeStamp.toString().substring(0,mTimeStamp.toString().length()-2));
			jsonObject.addProperty(WorkReportBean.SCHECKLIST,rs.getString("SCHECKLIST"));
			jsonObject.addProperty(WorkReportBean.Image, rs.getString("SIMAGE"));
			gsonArray.add(jsonObject);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gsonArray;
		
	}
	
	public JsonArray getProblemsDetail(){
		JsonArray gsonArray = new JsonArray();
		String sql ="SELECT * FROM PROBLEMREPORT  order by SDATA DESC";
		JsonObject jsonObject = null;
		JsonObject mImageObject = null;
		JsonArray mPictures = null;
		
		try {
			ResultSet rs = statement.executeQuery(sql);
			String folder = null;
			File mFolder = null;
			while(rs.next()) {
				jsonObject = new JsonObject();
				jsonObject.addProperty(WorkReportBean.DESCRIPTION, rs.getString("SDESCRIPTION"));
				jsonObject.addProperty(WorkReportBean.Name, rs.getString("SNAME"));
			    Timestamp mTimeStamp= rs.getTimestamp("SDATA", Tools.getCalander());
				jsonObject.addProperty(WorkReportBean.Date,mTimeStamp.toString().substring(0,mTimeStamp.toString().length()-2));
				folder =  rs.getString("SIMAGE");
				mFolder = new File(folder);
				mPictures = new JsonArray();
				if(null != mFolder){
					File[] files = mFolder.listFiles();
					if(null != files)
					for(File mFile :files){
						mImageObject = new JsonObject();
						mImageObject.addProperty(WorkReportBean.PICTURE, mFile.getAbsolutePath());
						mPictures.add(mImageObject);
					}
				}
				
				jsonObject.add(WorkReportBean.PICTURES, mPictures);
				gsonArray.add(jsonObject);	
				
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
		
		return gsonArray;
		
	}
	
	public JsonObject getImagesDetail(String name, String time ){
		String sql ="SELECT * FROM PROBLEMREPORT WHERE SDATA='"+time+"' AND SNAME='"+name+"'";
		JsonObject mInfo = new JsonObject();
		
		try {
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				mInfo.addProperty(WorkReportBean.Name, rs.getString("SNAME"));
			    Timestamp mTimeStamp= rs.getTimestamp("SDATA", Tools.getCalander());
			    mInfo.addProperty(WorkReportBean.Date,mTimeStamp.toString().substring(0,mTimeStamp.toString().length()-2));
				mInfo.addProperty(WorkReportBean.DESCRIPTION,rs.getString("SDESCRIPTION"));
				File mFolder = new File(rs.getString("SIMAGE"));
				JsonArray mPictures = new JsonArray();
				JsonObject mImageObject =null;
				if(null != mFolder){
					File[] files = mFolder.listFiles();
					if(null != files)
					for(File mFile :files){
						mImageObject = new JsonObject();
						mImageObject.addProperty(WorkReportBean.PICTURE, mFile.getAbsolutePath());
						mPictures.add(mImageObject);
					}
				}
				mInfo.add(WorkReportBean.PICTURES, mPictures);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mInfo;
		
	}
	/**
	 * Insert the description and images' directory to database  WORKREPORT
	 * @param descriptions
	 * @param directory
	 * @param name
	 */
	public void insertProblem(String descriptions, String directory, String name){
		String sql ="INSERT INTO PROBLEMREPORT VALUES (?,?,?,?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);		
			java.sql.Timestamp timeStamp= new java.sql.Timestamp(System.currentTimeMillis());
			stmt.setString(1, name);
			//			stmt.setDate(3, time);
			stmt.setTimestamp(2, timeStamp,Tools.getCalander());
			stmt.setString(3, descriptions);
			stmt.setString(4, directory);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Insert the the information of equipment into "DEVICESMANUAL"
	 * @param mObject
	 */
	
	public boolean insertEquipmentParts(JsonObject mObject){
		String sql ="INSERT INTO DEVICESMANUAL VALUES (?,?,?,?)";
		boolean insert = true;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			java.sql.Timestamp timeStamp= new java.sql.Timestamp(System.currentTimeMillis());
			stmt.setString(1, mObject.get("person").toString());
			stmt.setTimestamp(2,timeStamp,Tools.getCalander());
			stmt.setString(3, mObject.get("part").toString());
			stmt.setString(4,  mObject.get("description").toString());
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			insert = false;
			e.printStackTrace();
		}		
		return insert;
		
	}
	
	public JsonArray getEquipmentParts(){
		String sql ="SELECT * FROM DEVICESMANUAL order by SDATE DESC";
		JsonArray mArray = new JsonArray();
		JsonObject mObject ;
		
		try {
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				mObject = new JsonObject();
				mObject.addProperty(WorkReportBean.Name, rs.getString("SNAME"));
				Timestamp mTimeStamp= rs.getTimestamp("SDATE", Tools.getCalander());
				mObject.addProperty(WorkReportBean.Date,mTimeStamp.toString().substring(0,mTimeStamp.toString().length()-2));
				mObject.addProperty(WorkReportBean.PART, rs.getString("SPART"));
				mObject.addProperty(WorkReportBean.DESCRIPTION, rs.getString("SDESCRIPTION"));
				mArray.add(mObject);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mArray;
	}
	
	public boolean insertCustomerReport(JsonObject mObject){
		String sql ="INSERT INTO CUSTOMERREPORT VALUES (?,?,?,?)";
		boolean insert = true;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			java.sql.Timestamp timeStamp= new java.sql.Timestamp(System.currentTimeMillis());
			stmt.setString(1, mObject.get("userName").toString());
			stmt.setTimestamp(2,timeStamp,Tools.getCalander());
			stmt.setString(3, mObject.get("userEmail").toString());
			stmt.setString(4,  mObject.get("userMsg").toString());
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			insert = false;
			e.printStackTrace();
		}		
		return insert;
		
	}
	
	
}
