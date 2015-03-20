package com.hmkcode;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONObject;

import com.connection.MySqlConnection;
import com.hmkcode.vo.CreateFileUtil;
import com.mail.SendEmail;


public class UploadServlet extends HttpServlet {

//    private static final long serialVersionUID = 1L;
    private File fileUploadPath;
	private static final String imagFolder="pictures";
	private static final String WebContent="WebContent";
	private static final String Separator=java.io.File.separator;
	private String description = null;
	private String userName="Tom";
	final String  SUCCESS="Success!";
	private String directory=null;
	final String  FAILURE ="Please select pictures!";
	public static final String PROJECT="ServletHtml";
	
    @Override
    public void init(ServletConfig config) {
        fileUploadPath = new File(config.getInitParameter("upload_path"));
       
    }
        
    /**
        * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String directory=  request.getServletContext().getRealPath("/")+Separator+imagFolder;
    	fileUploadPath =new File(directory);
    	
        if (request.getParameter("getfile") != null 
                && !request.getParameter("getfile").isEmpty()) {
            File file = new File(fileUploadPath,
                    request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(fileUploadPath, request.getParameter("delfile"));
            if (file.exists()) {
                file.delete(); // TODO:check and report success
            } 
        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            File file = new File(fileUploadPath, request.getParameter("getthumb"));
                if (file.exists()) {
                    String mimetype = getMimeType(file);
                    if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                        BufferedImage im = ImageIO.read(file);
                        if (im != null) {
                            BufferedImage thumb = Scalr.resize(im, 75); 
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            if (mimetype.endsWith("png")) {
                                ImageIO.write(thumb, "PNG" , os);
                                response.setContentType("image/png");
                            } else if (mimetype.endsWith("jpeg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else {
                                ImageIO.write(thumb, "GIF" , os);
                                response.setContentType("image/gif");
                            }
                            ServletOutputStream srvos = response.getOutputStream();
                            response.setContentLength(os.size());
                            response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );
                            os.writeTo(srvos);
                            srvos.flush();
                            srvos.close();
                        }
                    }
            } // TODO: check and report success
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }
    
    /**
        * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(request.getParameter("save")==null){
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        CreateFileUtil mCreateFileUtil = new CreateFileUtil();
//    	String directory=  request.getServletContext().getRealPath("/")+Separator+imagFolder;
//    	mCreateFileUtil.createDir(directory);
//    	fileUploadPath =new File(directory);
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            
            		Cookie[] mCookies = request.getCookies();
    		for(Cookie mCookie:mCookies){
    			if(mCookie.getName().equals("username"))
    				userName= mCookie.getValue();
    		}
    	    String directory=  request.getServletContext().getRealPath("/")+Separator+imagFolder;
    	    CreateFileUtil.createDir(directory);
    	    String temp = directory+Separator+"Temp";
    	    CreateFileUtil.createDir(temp);
    	    temp = temp+Separator+userName;// file name
    	    CreateFileUtil.createDir(temp);
    		for(int i =0;i<items.size();i++){		
//   			 System.out.println("Pictures  saving............");
   			 if(null !=items.get(i).getName())
   			mCreateFileUtil.saveImage(items.get(i).getInputStream(),temp,items.get(i).getName());
   		}
   		
            for (FileItem item : items) {
                if (!item.isFormField()) {
                	
                	mCreateFileUtil.saveImage(item.getInputStream(), directory, item.getName());
//                       File file = new File(fileUploadPath, item.getName());
//                        item.write(file);
                        JSONObject jsono = new JSONObject();
                        jsono.put("name", item.getName());
                        jsono.put("size", item.getSize());
                        jsono.put("url", "upload?getfile=" + item.getName());
                        jsono.put("thumbnail_url", "upload?getthumb=" + item.getName());
                        jsono.put("delete_url", "upload?delfile=" + item.getName());
                        jsono.put("delete_type", "GET");
                        json.put(jsono);
                }
            }
        } catch (FileUploadException e) {
                throw new RuntimeException(e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
            writer.write(json.toString());
            writer.close();
        }
    	}else{//save the directory to database
    		
    			Cookie[] mCookies = request.getCookies();
			for(Cookie mCookie:mCookies){
				if(mCookie.getName().equals("username"))
					userName= mCookie.getValue();
			}
			setDescription(request.getParameter("description"));
//		    String directory=  request.getServletContext().getRealPath("/")+Separator+imagFolder;
			String directory="/home/cm360/public_html/pictures";// we put the pictures folder out of the project, we use it when publish in the server linux
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
				myConnection.closeDataBase();
				String text = "Hi, \n "+userName+ " just report a new problem, please check.";
				SendEmail.autoSend(userName, text);

			}else{
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				out.print(FAILURE);
			}
			
			
    		
    	}

    }

    
  /*  private String getParentDirectory(String curDirectory){
 
    	curDirectory = curDirectory.substring(0, curDirectory.length()-PROJECT.length()-1);
    	return curDirectory;
    	
    }*/
    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
//            URLConnection uc = new URL("file://" + file.getAbsolutePath()).openConnection();
//            String mimetype = uc.getContentType();
//            MimetypesFIleTypeMap gives PNG as application/octet-stream, but it seems so does URLConnection
//            have to make dirty workaround
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
            }
        }
//        System.out.println("mimetype: " + mimetype);
        return mimetype;
    }



    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
//        System.out.println("suffix: " + suffix);
        return suffix;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

		public String getDirectory() {
			return directory;
		}

		public void setDirectory(String directory) {
			this.directory = directory;
		}
        
        
    
}