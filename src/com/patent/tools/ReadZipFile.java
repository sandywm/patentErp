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
	public static List<Object> readZipFile_new(String zipPath){
		Charset gbk = Charset.forName("gbk");
		zipPath = "E:\\实用新型-受理+费用减缓通知书.zip";
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
	    		String fwSerial = "";//发文序列号--通过这个确定那个为先（小的为先）
	    		String sqrName = "";//申请人
	    		String applyDate = "";//申请日
	    		String zlType = "";
	    		String fjApplyDate = "";//费减请求日期
	    		String fjRecord = "";//费减记录
	    		String feeEdate = "";//缴费截止日期
	    		String fjRate = "";//费减比率
	            if(ze.isDirectory()){
	                //为空的文件夹什么都不做
	            }else{
	            	Map<String,Object> map_d = new HashMap<String,Object>();
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
	        				tzsName = l1.getTextTrim();//通知书名称
	        				ajNoGf = root.element("application_number").getTextTrim();//申请号或专利号
	        				Element l2 = root.element("application_date");
	        				if(l2 != null){
	        					applyDate = CurrentTime.convertFormatDate(l2.getTextTrim());//申请日
	        				}
	        				Element l3 = root.element("applicant_info");
	        				Element l4 = null;
							for(@SuppressWarnings("unchecked")
							Iterator<Element> it = l3.elementIterator("applicant_name") ; it.hasNext();){
								l4 = it.next();
								sqrName += l4.getTextTrim() + ",";//申请人
	        				}
							if(!sqrName.equals("")){
								sqrName = sqrName.substring(0, sqrName.length() - 1);
							}
							Element l5 = root.element("notice_sent");
							if(l5 != null){
								fwSerial = l5.element("notice_sent_serial").getTextTrim();
							}
							map_d.put("tzsName", tzsName);
			            	map_d.put("ajNoGf", ajNoGf);
			            	map_d.put("fwSerial", fwSerial);
			            	map_d.put("applyDate", applyDate);
			            	if(tzsName.equals("专利申请受理通知书")){
			            		map_d.put("sqrName", sqrName);
			            		Element lType = root.element("file_list");
			            		if(lType != null){
			            			lType = lType.element("file_info");
			            			if(lType != null){
			            				for(@SuppressWarnings("unchecked")
	        							Iterator<Element> it = lType.elementIterator("file"); it.hasNext();){
			            					lType = it.next();
			            					String zlTypeChi = lType.element("file_name").getTextTrim();
			            					if(zlTypeChi.indexOf("实用新型") >= 0){
			            						zlType = "syxx";
			            					}else if(zlTypeChi.indexOf("发明专利") >= 0){
			            						zlType = "fm";
			            					}else if(zlTypeChi.indexOf("外观") >= 0){
			            						zlType = "wg";
			            					}
	        								map_d.put("zlType", zlType);
	        								break;
	        	        				}
			            			}
			            		}
			            	}
			            	if(tzsName.equals("费用减缓审批通知书")){
			            		fjApplyDate = CurrentTime.convertFormatDate(root.element("cost_slow_req_date").getTextTrim());
			            		fjRecord = root.element("cost_slow_mes").getTextTrim();
			            		feeEdate = CurrentTime.convertFormatDate(root.element("pay_deadline_date").getTextTrim());
			            		fjRate = root.element("cost_slow_rate_annul").getTextTrim();
			            		Element fee = root.element("fee_info_all");
			            		Element feeDetail = null;
			            		List<Object> list_f = new ArrayList<Object>();
			            		if(fee != null){
			            			map_d.put("feeTotal", fee.element("fee_total").getTextTrim());
			            			fee = fee.element("fee_info");
			            			for(@SuppressWarnings("unchecked")
		    							Iterator<Element> it = fee.elementIterator("fee") ; it.hasNext();){
			            				feeDetail = it.next();
			            				Map<String,String> map_f = new HashMap<String,String>();
			            				map_f.put("feeName", feeDetail.element("fee_name").getTextTrim());
			            				map_f.put("feeAmount", feeDetail.element("fee_amount").getTextTrim());
			            				list_f.add(map_f);
			            			}
			            			map_d.put("feeDetail", list_f);
			            		}
			            		map_d.put("fjApplyDate", fjApplyDate);
			            		map_d.put("fjRecord", fjRecord);
			            		map_d.put("feeEdate", feeEdate);
			            		map_d.put("fjRate", fjRate);
			            	}
			            	list_d.add(map_d);
	        			}
	            	}
	            }
	        }
        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(list_d);
        return list_d;
	}
	
	
	/**
	 * @description
	 * @author Administrator
	 * @date 2018-9-21 下午05:23:40
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Object> objList = ReadZipFile.readZipFile_new("");
		for(Iterator<Object> it = objList.iterator() ; it.hasNext();){
			Object obj = it.next();
			System.out.println(obj);
		}
	}

}
