package com.patent.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.patent.action.base.IgnoreDTDEntityResolver;
import com.patent.json.FeeDetailJson;
import com.patent.json.FileListJson;
import com.patent.json.LateFeeJson;
import com.patent.json.TzsJson;
import com.patent.util.Constants;
import com.patent.util.WebUrl;

public class ReadZipFile {

	
	/**
	 * 读取上传的通知书内容(格式必须是zip)--通知书都存在通知书数据表里面
	 * @description
	 * @author Administrator
	 * @date 2018-9-29 下午04:44:44
	 * @param upZipPath 上传的文件路径(cpyUser/u_id/****.zip)
	 * @param currUserId 当前操作人员
	 * @param cpyId 当前操作人员公司编号
	 * @param specZlId 专利编号（统一上传时为0，大于0时表示对指定的专利上传）
	 * @return
	 * @throws Exception
	 */
	public static List<TzsJson> readZipFile_new(String upZipPath,Integer currUserId,Integer cpyId,Integer specZlId){
		Charset gbk = Charset.forName("gbk");
		List<TzsJson> list_sub_d = new ArrayList<TzsJson>();
		List<TzsJson> list_all = new ArrayList<TzsJson>();
		
		Map<String,Object> map = new HashMap<String,Object>();
		String finalPath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" + upZipPath;//上传文件的绝对路径
        try {
			ZipFile zf = new ZipFile(finalPath,gbk);
			FileInputStream fileInputStream = new FileInputStream(finalPath);
			CheckedInputStream check = new CheckedInputStream(fileInputStream, new CRC32());
	        ZipInputStream zin = new ZipInputStream(check,gbk);
	        //ZipEntry 类用于表示 ZIP 文件条目。
	        ZipEntry ze;
	        while((ze=zin.getNextEntry())!=null){
	        	String tzsName = "";//通知书名称
	        	String zlName = "";//专利名称
	    		String ajNoGf = "";//申请号或专利号
	    		String fwSerial = "";//发文序列号--通过这个确定那个为先（小的为先）
	    		String fwDate = "";//发文日
	    		String sqrName = "";//申请人
	    		String applyDate = "";//申请日
	    		String zlType = "";
	    		String fjApplyDate = "";//费减请求日期
	    		String fjRecord = "";//费减记录
	    		String feeEdate = "";//缴费截止日期/补正截止日期
	    		String fjRate = "0.0";//费减比率
//	    		String jfDetail = "";//缴费详情
//	    		Double jfTotal = 0.0;//缴费总计
	    		List<LateFeeJson> list_lf = new ArrayList<LateFeeJson>();//年费滞纳金
	    		List<FeeDetailJson> list_fd = new ArrayList<FeeDetailJson>();//费用详情
	    		List<FileListJson> list_fl = new ArrayList<FileListJson>();//电子申请回执清单
	    		String yearNo = "";//年度数字
	    		//---------------年费滞纳金-----------------//
	            if(ze.isDirectory()){
	                //为空的文件夹什么都不做
	            }else{
	            	String fileName = ze.getName();
	            	if(fileName.endsWith("XML") || fileName.endsWith("xml")){
	                    ZipEntry zip = zf.getEntry(ze.getName());
	                    InputStream inputstream = null;
	            		inputstream = zf.getInputStream(zip);
	            		SAXReader reader = new SAXReader();  
	            		reader.setEntityResolver(new IgnoreDTDEntityResolver());//忽略dtd验证
	                    Document doc = reader.read(inputstream);
	        			Element root = doc.getRootElement();  
	        			Element l1 = root.element("notice_name");//通知书存在的内容
	        			Element l11 = root.element("ANJIANBH");//电子回单存在的内容
	        			if(l1 != null){//读取到数据文件（不判断直接存）
	        				tzsName = l1.getTextTrim();//通知书名称
	        				ajNoGf = root.element("application_number").getTextTrim();//申请号或专利号
	        				ajNoGf = ajNoGf.replace(".", "");//统一去掉专利号带点的
	        				if(ajNoGf.length() == 13){
            					String zlTypeNo = ajNoGf.substring(4, 5);
            					if(zlTypeNo.equals("1")){
            						zlType = "fm";
            					}else if(zlTypeNo.equals("2") || zlTypeNo.equals("8")){
            						zlType = "syxx";
            					}else if(zlTypeNo.equals("3") || zlTypeNo.equals("9")){
            						zlType = "wg";
            					}
            				}else if(ajNoGf.length() == 14){
            					zlType = "wg";
            				}
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
								fwDate =  CurrentTime.convertFormatDate(l5.element("notice_sent_date").getTextTrim());
							}
							Element l6 = root.element("invention_title");
							if(l6 != null){
								zlName = l6.getTextTrim();
							}
			            	if(tzsName.equals("专利申请受理通知书")){
			            		
			            	}else if(tzsName.equals("费用减缓审批通知书") || tzsName.equals("缴纳申请费通知书")){
			            		if(tzsName.equals("费用减缓审批通知书")){
			            			fjApplyDate = CurrentTime.convertFormatDate(root.element("cost_slow_req_date").getTextTrim());
				            		fjRecord = root.element("cost_slow_mes").getTextTrim();
				            		fjRate = root.element("cost_slow_rate_annul").getTextTrim();
				            		fjRate = "0."+fjRate.substring(0, fjRate.length() - 1);
			            		}
			            		feeEdate = CurrentTime.convertFormatDate(root.element("pay_deadline_date").getTextTrim());//缴费截止日期-通知书
			            		Element fee = root.element("fee_info_all");
			            		Element feeDetail = null;
			            		if(fee != null){
			            			fee = fee.element("fee_info");
			            			for(@SuppressWarnings("unchecked")
		    							Iterator<Element> it = fee.elementIterator("fee") ; it.hasNext();){
			            				feeDetail = it.next();
			            				String feeName = feeDetail.element("fee_name").getTextTrim();
			            				String feeAmount = feeDetail.element("fee_amount").getTextTrim();
			            				if(feeAmount.equals("0") || feeAmount.equals((""))){//费用不为0的记录上
			            					
			            				}else{
			            					list_fd.add(new FeeDetailJson(feeName,Double.parseDouble(feeAmount)));
			            				}
			            			}
			            		}
			            	}else if(tzsName.equals("办理登记手续通知书")){
			            		feeEdate = CurrentTime.convertFormatDate(root.element("pay_deadline_date").getTextTrim());//缴费截止日期-通知书
			            		Element fee = root.element("fee_info_all");
			            		Element feeDetail = null;
			            		if(fee != null){
			            			Element fee_sub = fee.element("fee_info");
			            			for(@SuppressWarnings("unchecked")
		    							Iterator<Element> it = fee_sub.elementIterator("fee") ; it.hasNext();){
			            				feeDetail = it.next();
			            				String feeName = feeDetail.element("fee_name").getTextTrim();
			            				String feeAmount = feeDetail.elementText("fee_amount");
			            				if(feeAmount.equals("0") || feeAmount.equals((""))){//费用不为0的记录上
			            					
			            				}else{
			            					list_fd.add(new FeeDetailJson(feeName,Double.parseDouble(feeAmount)));
			            				}
			            			}
			            			yearNo = fee.element("annual_year").getTextTrim();//年度数字
			            			String feeRateSign = fee.element("cost_slow_flag").getTextTrim();
			            			if(!feeRateSign.contains("无费减")){
			            				fjRate = "0."+feeRateSign.substring(0, feeRateSign.length() - 1);
			            			}
			            		}
			            	}else if(tzsName.equals("发明专利申请公布及进入实质审查通知书")){
			            		//获取公用的就可以了
			            	}else if(tzsName.equals("缴费通知书")){
			            		feeEdate = CurrentTime.convertFormatDate(root.element("pay_deadline_date").getTextTrim());//缴费截止日期-通知书
			            		yearNo = root.element("annual_year").getTextTrim();//年度
			            		Element fee_info = root.element("fee_info");
			            		Element feeDetail = null;
			            		if(fee_info != null){
			            			for(@SuppressWarnings("unchecked")
		    							Iterator<Element> it = fee_info.elementIterator("fee") ; it.hasNext();){
			            				feeDetail = it.next();
			            				//缴费开始日期：缴费结束日期
			            				String feeRange_s = CurrentTime.convertFormatDate(feeDetail.elementTextTrim("pay_start_time"));
			            				String feeRange_e = CurrentTime.convertFormatDate(feeDetail.elementTextTrim("pay_end_time"));
			            				String lateFee_temp = feeDetail.elementTextTrim("late_fee");//滞纳金
			            				list_lf.add(new LateFeeJson(Double.parseDouble(lateFee_temp),feeRange_s,feeRange_e));
			            			}
			            		}
			            	}
			            	list_sub_d.add(new TzsJson(fwSerial,ajNoGf));//存在数据文件，无需判断
			            	list_all.add(new TzsJson(fwSerial, ajNoGf, tzsName,zlName, fwDate, sqrName, applyDate,
			            			zlType, fjApplyDate, fjRecord,feeEdate, fjRate,yearNo,list_fd,list_lf,list_fl,upZipPath,"dataXml"));
	        			}else if(l11 != null){//电子回单（没有图片）
	        				tzsName = "电子申请回执";
	        				zlName = root.element("FAMINGCZMC").getTextTrim();
	        				fwSerial = root.element("ANJIANBH").getTextTrim();//电子回单的回执编号-唯一的（类似于通知书的发文序号）
	        				Element l_sqh = root.element("SHENQINGH");
	        				if(l_sqh != null){//存在申请号
	        					ajNoGf = l_sqh.getTextTrim().replace("申请号：", "");
	        					ajNoGf = ajNoGf.replace(".", "");//统一去掉专利号带点的
	        					fwDate = root.element("QIANMINGSJC").getTextTrim().substring(0, 10);
	        					for(@SuppressWarnings("unchecked")
	        						Iterator<Element> it = root.elementIterator("SHOUDAOWJ") ; it.hasNext();){
	        						Element l = it.next().element("WENJIANLB");
	        						list_fl.add(new FileListJson(l.elementText("WENJIANMC"),l.elementText("WENJIANGS"),l.elementText("WENJIANDX")));
	        					}
				            	list_all.add(new TzsJson(fwSerial, ajNoGf, tzsName,zlName, fwDate, "", "",
				            			"", "", "","", fjRate,yearNo,list_fd,list_lf,list_fl,upZipPath,"dataXml"));
	        				}
	        				//不存在申请号的不读取
	        			}else{//里面不存在数据文件，需要从list.xml中获取(比如补正通知书)
	        				l1 = root.element("TONGZHISXJ");
	        				if(l1 != null){
	        					Element l2 = l1.element("SHUXINGXX");
	        					if(l2 != null){
	        						tzsName = l2.elementText("TONGZHISMC");
	        						zlName = l2.elementText("FAMINGMC");
	        						ajNoGf = l2.elementText("SHENQINGH");
	        						Element l3 = root.element("FAWENXLH");
	        						if(l3 != null){
	        							ajNoGf = ajNoGf.replace(".", "");//统一去掉专利号带点的
	        							fwSerial = l3.getTextTrim();
	        							boolean readFlag = false;
		        						//需要判断之前的list_sub_d中是否已经读取过
		        						for(int j = 0; j < list_sub_d.size(); j++){
		        							TzsJson tJson = list_sub_d.get(j);
	        					        	if(tJson.getAjNoGf().equals(ajNoGf) && tJson.getFwSerial().equals(fwSerial)){
	        					        		readFlag = true;
	        					        		break;
	        					        	}
	        					        }
		        						if(!readFlag){//没读取过
		        							if(ajNoGf.length() == 13){
		                    					String zlTypeNo = ajNoGf.substring(4, 5);
		                    					if(zlTypeNo.equals("1")){
		                    						zlType = "fm";
		                    					}else if(zlTypeNo.equals("2") || zlTypeNo.equals("8")){
		                    						zlType = "syxx";
		                    					}else if(zlTypeNo.equals("3") || zlTypeNo.equals("9")){
		                    						zlType = "wg";
		                    					}
		                    				}else if(ajNoGf.length() == 14){
		                    					zlType = "wg";
		                    				}
		        							fwDate = CurrentTime.convertFormatDate(l2.elementText("FAWENR"));
		        							feeEdate = CurrentTime.getFinalDate(fwDate, Constants.TD_RECEIVE_DAYS);
		        							if(zlType.equals("fm") && tzsName.equals("第一次审查意见通知书")){
		        								feeEdate = CurrentTime.getFinalDate_1(feeEdate, 4);
		        							}else{
		        								feeEdate = CurrentTime.getFinalDate_1(feeEdate, 2);
		        							}
//			        						feeEdate = CurrentTime.getFinalDate(fwDate, (60+Constants.TD_RECEIVE_DAYS));
			    			            	list_all.add(new TzsJson(fwSerial, ajNoGf, tzsName,zlName, fwDate, sqrName, applyDate,
			    			            			zlType, fjApplyDate, fjRecord,feeEdate, fjRate,yearNo,list_fd,list_lf,list_fl,upZipPath,"listXml"));
		        						}
	        						}
	        					}
	        				}
	        			}
	        			inputstream.close();
	            	}
	            }
	        }
	        zin.close();
	        check.close();
	        fileInputStream.close();
	        zf.close();
        }catch (Exception e) {
			// TODO Auto-generated catch block
        	map.put("readInfo", "typeError");//只支持ZIP压缩格式的通知书
			//删除当前通知书压缩包
			FileOpration.deleteFile(finalPath);
		}
        return list_all;
	}
	
	/**
	 * 读取系统配置文件（实质审查费、到期警报天数）
	 * @description
	 * @author Administrator
	 * @date 2018-9-29 上午10:31:11
	 * @param optVal
	 * @return
	 * @throws IOException
	 */
	public static String readConfigDetail(String optVal) throws IOException{
		String configPath = Constants.SYS_CONFIG_WJ;
		File file = new File(configPath);
		if(file != null){
			InputStreamReader br = new InputStreamReader(new FileInputStream(file),"utf-8");//读取文件,同时指定编码
			StringBuffer sb = new StringBuffer();
	        char[] ch = new char[128];  //一次读取128个字符
	        int len = 0;
	        while((len = br.read(ch,0, ch.length)) != -1){
	            sb.append(ch, 0, len);
	        }
	        String s = sb.toString();
	        JSONObject dataJson = JSON.parseObject(s); 
	        JSONArray features = dataJson.getJSONArray("opt");// 找到features json数组
	        JSONObject json = features.getJSONObject(0);// 获取features数组的首个json对象
	        if(optVal.equals("scFee")){
	        	return json.getString("scFee");
	        }else if(optVal.equals("alert")){
	        	//截止日期比当前日期>=20天-绿色警告，10-20天内为黄色警告，<10天内为红色警告
	        	return json.getString("yellowAlert") + "," + json.getString("greenAlert");//格式红,黄，绿
	        }
		}
		return "";
	}
	
	/**
	 * 复制zip压缩包中的指定文件
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2019-1-7 下午11:16:48
	 * @param file
	 */
	public void copyZipFile(File file){
		ZipFile zipFile = null;
		ZipInputStream zipInput = null;
		ZipEntry zipEntry = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			zipFile = new ZipFile(file);
			zipInput = new ZipInputStream(new FileInputStream(file),Charset.forName("utf-8"));
			while((zipEntry = zipInput.getNextEntry()) != null){
				String currFileName = zipEntry.getName();
				if(currFileName.endsWith(".tif")){
					int indexLen = currFileName.indexOf("\\");
					String fileNamePre = "";//文件夹名称
					if(indexLen > -1){
						fileNamePre = currFileName.substring(0, indexLen);
						int indexLastLen = currFileName.lastIndexOf("\\");
						if((fileNamePre+"\\"+fileNamePre+"\\"+fileNamePre).equals(currFileName.substring(0, indexLastLen))){
							File file_c = new File(file.getParent() + fileNamePre);
							String tifName = currFileName.substring(indexLastLen);
							if(!file_c.exists()){
								file_c.mkdirs();
							}
							try {
								File mainfestFile = new File(file.getParent() + fileNamePre + tifName);
								mainfestFile.createNewFile();
								os = new FileOutputStream(mainfestFile);
								is = zipFile.getInputStream(zipEntry);
								int len;
								while((len = is.read()) != -1){
									os.write(len);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}
			}
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				is.close();
				os.close();
				zipInput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * @description
	 * @author Administrator
	 * @date 2018-9-21 下午05:23:40
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ReadZipFile rzf = new ReadZipFile();
		rzf.copyZipFile(new File("e:\\1122.zip"));
//		List<TzsJson> tjList = new ArrayList<TzsJson>();
////		for(int i = 222 ; i <= 223; i++){
////			List<TzsJson> tj = ReadZipFile.readZipFile_new("E:\\"+i+".zip",0,0,0);
////			tjList.addAll(tj);
////		}
//		List<TzsJson> tj = ReadZipFile.readZipFile_new("E:\\tzs_411050434.zip",0,0,0);
//		tjList.addAll(tj);
//		System.out.println(tj.size());
////		Collections.sort(tjList);
//		for(int j = 0; j < tjList.size(); j++){
//        	TzsJson tJson = tjList.get(j);
//        	System.out.println("---------------------------------"+j);
//        	System.out.println("发文序号 : "+tJson.getFwSerial());
//        	System.out.println("专利/申请号 : "+tJson.getAjNoGf());
//        	System.out.println("通知书名称 : "+tJson.getTzsName());
//        	System.out.println("专利名称 : "+tJson.getZlName());
//        	System.out.println("发文日 : "+tJson.getFwDate());
//        	System.out.println("申请人 : "+tJson.getSqrName());
//        	System.out.println("申请日 : "+tJson.getApplyDate());
//        	System.out.println("专利类型 : "+tJson.getZlType());
//        	System.out.println("费减请求日期 : "+tJson.getFjApplyDate());
//        	System.out.println("费减记录: "+tJson.getFjRecord());
//        	System.out.println("缴费截止日期/补正截止日期 :  "+tJson.getFeeEdate());
//        	System.out.println("费减比率 : "+tJson.getFjRate());
//        	List<FeeDetailJson> fdList = tJson.getFdList();
//        	if(fdList.size() > 0){
//        		for(Integer k = 0 ; k < fdList.size() ; k++){
//        			FeeDetailJson fdJson = fdList.get(k);
//        			System.out.println(fdJson.getFeeName()+" :"+fdJson.getFeeAmount());
//        		}
//        	}
//        	System.out.println("年度数字:  "+tJson.getYearNo());
//        	List<LateFeeJson> lfList = tJson.getLfList();
//        	if(lfList.size() > 0){
//        		for(Integer k = 0 ; k < lfList.size() ; k++){
//        			LateFeeJson lfJson = lfList.get(k);
//        			System.out.println("缴费时间段："+lfJson.getFeeSDate()+"至"+lfJson.getFeeEDate() + " 滞纳金 " + lfJson.getLateFee());
//        		}
//        	}
//        	List<FileListJson> flList = tJson.getFlList();
//        	if(flList.size() > 0){
//        		for(Integer k = 0 ; k < flList.size() ; k++){
//        			FileListJson lfJson = flList.get(k);
//        			System.out.println("文件名："+lfJson.getFileName() + "   ,文件格式:" + lfJson.getFileType() + "   ,文件大小 :"+lfJson.getFileSize());
//        		}
//        	}
//        	System.out.println("通知书路径 : "+tJson.getZipPath());
//        }
	}

}
