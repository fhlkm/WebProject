package com.hmkcode.vo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;

public class CreateFileUtil {
	private static final String Separator=java.io.File.separator;
	private static final String imagFolder="pictures";
	private static  String userName="Tom";
public  static boolean createFile(String destFileName) {
    File file = new File(destFileName);
    if(file.exists()) {
        System.out.println(  destFileName + "Exists");
        return false;
    }
    if (destFileName.endsWith(File.separator)) {
        System.out.println( destFileName + "endwith separator");
        return false;
    }
    if(!file.getParentFile().exists()) {
        System.out.println("file's doesn't parent exists");
        if(!file.getParentFile().mkdirs()) {
            System.out.println("create dir failed");
            return false;
        }
    }
    try {
        if (file.createNewFile()) {
            System.out.println("create " + destFileName +"success");
            return true;
        } else {
            System.out.println("create " + destFileName + "Failed");
            return false;
        }
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("create  " + destFileName + "Failed  I/O error" + e.getMessage());
        return false;
    }
}

/**
 * Create a dir
 * @param path
 * @param destDirName
 * @return
 */
public static  boolean createDir( String destDirName) {
    File dir = new File(destDirName);
    if (dir.exists()) {
        System.out.println("create directory" + destDirName + "   Failure, it already exists");
        return false;
    }
    if (!destDirName.endsWith(File.separator)) {
        destDirName = destDirName + File.separator;
    }
    //create
    if (dir.mkdirs()) {
        System.out.println("create directory" + destDirName + "   Success");
        return true;
    } else {
        System.out.println("create directory" + destDirName + "   Failure");
        return false;
    }
}

/**
 * 
 * @param prefix
 * @param suffix
 * @param dirName
 * @return
 */
public static  String createTempFile(String prefix, String suffix, String dirName) {
    File tempFile = null;
    if (dirName == null) {
        try{
            tempFile = File.createTempFile(prefix, suffix);
            return tempFile.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } else {
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (!CreateFileUtil.createDir(dirName)) {
                return null;
            }
        }
        try {
           
            tempFile = File.createTempFile(prefix, suffix, dir);
            return tempFile.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

/**
 * 
 * @param content pictures 
 * @param path the path where files will be saved
 * @param name pictures' name
 */
public void saveImage(InputStream content, String path,String name){


	try {
		BufferedInputStream   in = new BufferedInputStream(content);   

		//the name of the file
		File img = new File(path+File.separator+name);   

		BufferedOutputStream out;
		out = new BufferedOutputStream(new FileOutputStream(img));

		byte[] buf = new byte[2048];   

		while(in.read(buf) != -1)   
		{   
			out.write(buf);   
		}   
        System.out.println("save picture finished");
		in.close();   
		out.close();   
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   

}
public static void main(String[] args) {

}


public static void deleteFile(String sPath) {      
    File  file = new File(sPath);
     
     if (file.isFile() && file.exists()) {
    	 System.out.println(file.delete());
         
     }
   
 }
public static  void deleteTempFolder(HttpServletRequest request){
	String tempFolder = request.getServletContext().getRealPath("/")+Separator+imagFolder+Separator+"Temp"+Separator+userName;
	deleteTempFolder(tempFolder);
	
}

public static  void deleteTempFolder(String tempFolder){
	File mFolder = new File(tempFolder);
	if(null == mFolder)
		return;
	File[] files = mFolder.listFiles();
	 for (int i = 0; i < files.length; i++) {  
	        //delete children file
	        if (files[i].isFile()) {  
	            deleteFile(files[i].getAbsolutePath());  
	        } 
	    }
	new File(tempFolder).delete();
	
}



}
