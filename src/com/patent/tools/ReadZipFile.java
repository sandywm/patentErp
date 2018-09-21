package com.patent.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.patent.action.base.IgnoreDTDEntityResolver;

public class ReadZipFile {

	
	/**
	 * 读取压缩包内容
	 * @description
	 * @author Administrator
	 * @date 2018-9-21 下午05:24:59
	 * @param zipPath
	 */
	public static void readZipFile_new(String zipPath){
		Charset gbk = Charset.forName("gbk");
		zipPath = "E:\\实用新型-受理+费用减缓通知书.zip";
        try {
			ZipFile zf = new ZipFile(zipPath,gbk);
			FileInputStream fileInputStream = new FileInputStream(zipPath);
	        
			CheckedInputStream check = new CheckedInputStream(fileInputStream, new CRC32());
			
	        ZipInputStream zin = new ZipInputStream(check,gbk);

	        //ZipEntry 类用于表示 ZIP 文件条目。
	        ZipEntry ze;
	        
	        while((ze=zin.getNextEntry())!=null){
	            if(ze.isDirectory()){
	                //为空的文件夹什么都不做
	            }else{
	            	String fileName = ze.getName();
	            	if(fileName.endsWith("TXT") || fileName.endsWith("txt")){//文本文件
	            		
	            	}else if(fileName.endsWith("XML") || fileName.endsWith("xml")){
	                    ZipEntry zip = zf.getEntry(ze.getName());
	                    InputStream inputstream = null;
	            		inputstream = zf.getInputStream(zip);
	            		SAXReader reader = new SAXReader();  
	            		reader.setEntityResolver(new IgnoreDTDEntityResolver());//忽略dtd验证
	                    Document doc = reader.read(inputstream);
	        			Element root = doc.getRootElement();  
	        			Element l1 = root.element("notice_name");//发明名称
	        			if(l1 != null){
	        				String tzsName = l1.getData().toString();
	        				String ajNoGf = root.element("application_number").getData().toString();
	        				System.out.println(ajNoGf);
	        			}
	        			
	            	}
	            }
	        }
        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @description
	 * @author Administrator
	 * @date 2018-9-21 下午05:23:40
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadZipFile.readZipFile_new("");
	}

}
