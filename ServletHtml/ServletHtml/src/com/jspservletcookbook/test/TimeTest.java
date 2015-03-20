package com.jspservletcookbook.test;

import java.io.File;

import com.hmkcode.vo.CreateFileUtil;


public class TimeTest {

	public static void main(String[] args) {
		String directory= "C:\\Users\\hanlu Feng\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\ServletHtml\\pictures\\Temp\\Tom";
		File mFile = new File(directory);
		CreateFileUtil.deleteTempFolder(directory);
  

	}

}
