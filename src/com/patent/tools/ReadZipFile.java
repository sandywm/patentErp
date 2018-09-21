package com.patent.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	public static Map<String,Object> readZipFile_new(String zipPath){
		Charset gbk = Charset.forName("gbk");
		zipPath = "E:\\实用新型-受理+费用减缓通知书.zip";
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list_d = new ArrayList<Object>();
        try {
			ZipFile zf = new ZipFile(zipPath,gbk);
			FileInputStream fileInputStream = new FileInputStream(zipPath);
	        
			CheckedInputStream check = new CheckedInputStream(fileInputStream, new CRC32());
			
	        ZipInputStream zin = new ZipInputStream(check,gbk);

	        //ZipEntry 类用于表示 ZIP 文件条目。
	        ZipEntry ze;
	        
	        while((ze=zin.getNextEntry())!=null){
	        	String tzsName = "";//通知书名称
	    		String ajNoGf = "";//申请号或专利号
	    		String sqrName = "";//申请人
	    		String applyDate = "";//申请日
	            if(ze.isDirectory()){
	                //为空的文件夹什么都不做
	            }else{
	            	Map<String,String> map_d = new HashMap<String,String>();
	            	String fileName = ze.getName();
	            	if(fileName.endsWith("XML") || fileName.endsWith("xml")){
	                    ZipEntry zip = zf.getEntry(ze.getName());
	                    InputStream inputstream = null;
	            		inputstream = zf.getInputStream(zip);
	            		SAXReader reader = new SAXReader();  
	            		reader.setEntityResolver(new IgnoreDTDEntityResolver());//忽略dtd验证
	                    Document doc = reader.read(inputstream);
	        			Element root = doc.getRootElement();  
	        			Element l1 = root.element("notice_name");
	        			if(l1 != null){
	        				tzsName = l1.getData().toString();//通知书名称
	        				ajNoGf = root.element("application_number").getData().toString();//申请号或专利号
	        				Element l2 = root.element("application_date");
	        				if(l2 != null){
	        					applyDate = l2.getData().toString();//申请日
	        				}
	        				Element l3 = root.element("applicant_info");
	        				Element l4 = null;
							for(@SuppressWarnings("unchecked")
							Iterator<Element> it = l3.elementIterator("applicant_name") ; it.hasNext();){
								l4 = it.next();
								sqrName += l4.getData().toString() + ",";//申请人
	        				}
							if(!sqrName.equals("")){
								sqrName = sqrName.substring(0, sqrName.length() - 1);
							}
							map_d.put("tzsName", tzsName);
			            	map_d.put("ajNoGf", ajNoGf);
			            	map_d.put("applyDate", applyDate);
			            	map_d.put("sqrName", sqrName);
			            	list_d.add(map_d);
	        			}
	            	}
	            }
	        }
        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        map.put("result", list_d);
        System.out.println(map);
        return map;
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
