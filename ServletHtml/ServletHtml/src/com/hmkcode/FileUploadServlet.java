package com.hmkcode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.connection.MySqlConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmkcode.vo.CreateFileUtil;
import com.hmkcode.vo.FileMeta;

//this to be used with Java Servlet 3.0 API
@MultipartConfig 
public class FileUploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String imagFolder="pictures";
	private static final String WebContent="WebContent";
	private static final String Separator=java.io.File.separator;
	final String  SUCCESS="Success!";
	final String  FAILURE ="Please select pictures!";

	// this will store uploaded files
	private List<FileMeta> files = new LinkedList<FileMeta>();
	private String userName="Tom";
	private String directory=null;
	private String description = null;
	
	  private File fileUploadPath;
	
	/***************************************************
	 * URL: /upload
	 * doPost(): upload the files and other parameters
	 ****************************************************/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException{
//		System.out.println("********Get one Picture********************");
		// we didn't write the data into database
		if(request.getParameter("save")==null){
		files.clear();
		files.addAll(MultipartRequestHandler.uploadByApacheFileUpload(request));
		
/*		Cookie[] mCookies = request.getCookies();
		for(Cookie mCookie:mCookies){
			if(mCookie.getName().equals("username"))
				userName= mCookie.getValue();
		}*/
	    String directory=  request.getServletContext().getRealPath("/")+Separator+imagFolder+Separator+"Temp";
	    CreateFileUtil.createDir(directory);
	    CreateFileUtil mCreateFileUtil = new CreateFileUtil();
	    String temp = directory+Separator+userName;// file name
	    CreateFileUtil.createDir(temp);
	    
		// 1. Upload File Using Java Servlet API
		//files.addAll(MultipartRequestHandler.uploadByJavaServletAPI(request));			
		
		// 1. Upload File Using Apache FileUpload

//		 System.out.println("files size............"+files.size());
		// save picture using the time as the file name
		for(int i =0;i<files.size();i++){		
//			 System.out.println("Pictures  saving............");
			mCreateFileUtil.saveImage(files.get(i).getContent(),temp,files.get(i).getFileName());
		}
		
		
	
		
		// 2. Set response type to json
		response.setContentType("application/json");
		
		// 3. Convert List<FileMeta> into JSON format
    	ObjectMapper mapper = new ObjectMapper();
    	
    	// 4. Send resutl to client
    	 JSONArray json = new JSONArray();
    	   for (FileMeta item : files) {
              
//    		   url: "http://url.to/file/or/page",
//    	        thumbnail_url: "http://url.to/thumnail.jpg ",
//    	        name: "thumb2.jpg",
//    	        type: "image/jpeg",
//    	        size: 46353,
//    	        delete_url: "http://url.to/delete /file/",
//    	        delete_type: "DELETE"
    		      JSONObject jsono = new JSONObject();
                  try {
					jsono.put("name", item.getFileName());
	                //  jsono.put("size", Integer.parseInt(item.getFileSize().replace("Kb", "")));
					jsono.put("size", 237382);
	                  jsono.put("url", "UploadServlet?getfile=" + item.getFileName());
	                  jsono.put("thumbnail_url", "UploadServlet?getthumb=" + item.getFileName());
	                  jsono.put("delete_url", "UploadServlet?delfile=" + item.getFileName());
	                  jsono.put("delete_type", "GET");
	                  json.put(jsono);
//	                  System.out.println(json.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


                       
               
           }
    	   PrintWriter writer = response.getWriter();
    	   response.setContentType("application/json");
    	   if(null != json)
    	   writer.write(json.toString());
           writer.close();
    	   
    	//mapper.writeValue(response.getOutputStream(), files);
		}else{// write the data into database
		/*	Cookie[] mCookies = request.getCookies();
			for(Cookie mCookie:mCookies){
				if(mCookie.getName().equals("username"))
					userName= mCookie.getValue();
			}*/
			setDescription(request.getParameter("description"));
		    String directory=  request.getServletContext().getRealPath("/")+Separator+imagFolder;
		    CreateFileUtil.createDir(directory);
		    DateTime dt = new DateTime();
		    DateTime dtChicago =dt.withZone(DateTimeZone.forID("America/Chicago"));
			SimpleDateFormat dateFormat =  new java.text.SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			String formatDate = dateFormat.format(dtChicago.getMillis());
		    String saveFolder = directory+Separator+userName;// file name
		    CreateFileUtil.createDir(saveFolder);
		    saveFolder= saveFolder+Separator+formatDate;		
		    CreateFileUtil.createDir(saveFolder);
		    String tempFolder = request.getServletContext().getRealPath("/")+Separator+imagFolder+Separator+"Temp"+Separator+userName;
		    boolean isCopySuccess =  copyFiles(new File(tempFolder),new File(saveFolder));
		    
			PrintWriter out = response.getWriter(); 
			if(isCopySuccess){
				setDirectory(saveFolder);
				response.setStatus(HttpServletResponse.SC_OK);
				out.print(SUCCESS); 
				CreateFileUtil.deleteTempFolder(request);
				MySqlConnection myConnection = new MySqlConnection();
				myConnection.init();
				myConnection.insertProblem(getDescription(), getDirectory(), userName);

			}else{
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				out.print(FAILURE);
			}
		    
		}
	
	}
	
	
   
	
	/**
	 * Copy the files from srcFolder to destFolder
	 * @param srcFolder
	 * @param destFolder
	 */
	private boolean copyFiles(File srcFolder, File destFolder){
		boolean isCopy= true;
		File[] file = srcFolder.listFiles();
		if(null == file){
			return false;
		}
		try {
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// copy file
					String type = file[i].getName().substring(file[i].getName().lastIndexOf(".") + 1);
					copyFile(file[i],new File(destFolder.getAbsolutePath()+Separator+file[i].getName()));
				}
			}
		} catch (IOException e) {
			isCopy = false;
			e.printStackTrace();
		}
		return isCopy;
	}
        
        /**
         * 
         * @param sourceFile
         * @param targetFile
         * @throws IOException
         */
        public static void copyFile(File sourceFile, File targetFile) throws IOException  {
            BufferedInputStream inBuff = null;
            BufferedOutputStream outBuff = null;
            try {
            	FileInputStream mFileInputStream;
			
			   mFileInputStream = new FileInputStream(sourceFile);			
            	inBuff = new BufferedInputStream(mFileInputStream);
            	
                FileOutputStream outStream = new FileOutputStream(targetFile);
                outBuff = new BufferedOutputStream(outStream);

                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = inBuff.read(b)) != -1) {
                    outBuff.write(b, 0, len);
                }
                outBuff.flush();
            }catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
                if (inBuff != null)
                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            }
        }
	/***************************************************
	 * URL: /upload?f=value
	 * doGet(): get file of index "f" from List<FileMeta> as an attachment
	 ****************************************************/
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
        		throws ServletException, IOException{

        	// 1. Get f from URL upload?f="?"
        	String value = request.getParameter("f");

        	// 2. Get the file of index "f" from the list "files"
        	FileMeta getFile = files.get(Integer.parseInt(value));
        	InputStream input=null;
        	OutputStream output=null;

        	try {		
        		// 3. Set the response content type = file content type 
        		response.setContentType(getFile.getFileType());

        		// 4. Set header Content-disposition
        		response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");

        		// 5. Copy file inputstream to response outputstream
        		input = getFile.getContent();
        		output = response.getOutputStream();
        		byte[] buffer = new byte[1024*10];

        		for (int length = 0; (length = input.read(buffer)) > 0;) {
        			output.write(buffer, 0, length);
        		}


        	}catch (IOException e) {
        		e.printStackTrace();
        	}finally{
        		output.close();
        		input.close();
        	}

        }

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
