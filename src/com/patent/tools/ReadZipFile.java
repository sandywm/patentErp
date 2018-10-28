package com.patent.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.patent.action.base.IgnoreDTDEntityResolver;
import com.patent.factory.AppFactory;
import com.patent.module.FeeTypeInfoTb;
import com.patent.module.ZlajFeeInfoTb;
import com.patent.module.ZlajLcInfoTb;
import com.patent.module.ZlajLcMxInfoTb;
import com.patent.module.ZlajMainInfoTb;
import com.patent.service.MailInfoManager;
import com.patent.service.ZlajFeeInfoManager;
import com.patent.service.ZlajFjInfoManager;
import com.patent.service.ZlajLcInfoManager;
import com.patent.service.ZlajLcMxInfoManager;
import com.patent.service.ZlajMainInfoManager;
import com.patent.service.ZlajTzsInfoManager;
import com.patent.util.Constants;
import com.patent.util.WebUrl;

public class ReadZipFile {

	
	/**
	 * 读取上传的通知书内容(格式必须是zip)
	 * @description
	 * @author Administrator
	 * @date 2018-9-29 下午04:44:44
	 * @param pathPre 文件路径默认
	 * @param upZipPath 上传的文件路径
	 * @param currUserId 当前操作人员
	 * @param cpyId 当前操作人员公司编号
	 * @param specZlId 专利编号（统一上传时为0，大于0时表示对指定的专利上传）
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> readZipFile_new(String pathPre,String upZipPath,Integer currUserId,Integer cpyId,Integer specZlId) throws Exception{
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		ZlajLcInfoManager lcm = (ZlajLcInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_LC_INFO);
		ZlajLcMxInfoManager mxm = (ZlajLcMxInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_LC_MX_INFO);
		ZlajFjInfoManager fjm = (ZlajFjInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_FJ_INFO);
		ZlajTzsInfoManager tzsm = (ZlajTzsInfoManager)  AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_TZS_INFO);
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
		ZlajFeeInfoManager fm = (ZlajFeeInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_FEE_INFO);
		Charset gbk = Charset.forName("gbk");
		String finalPath = pathPre + "\\" + upZipPath;
		List<Object> list_d = new ArrayList<Object>();
		String msg = "";
		String currDate = CurrentTime.getStringDate();
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
		String jfDetail = "";//缴费详情
		Double jfTotal = 0.0;//缴费总计
		String yearNo = "";//年度数字
		Map<String,Object> map = new HashMap<String,Object>();
		msg = "";
        try {
			ZipFile zf = new ZipFile(finalPath,gbk);
			FileInputStream fileInputStream = new FileInputStream(finalPath);
			CheckedInputStream check = new CheckedInputStream(fileInputStream, new CRC32());
	        ZipInputStream zin = new ZipInputStream(check,gbk);
	        //ZipEntry 类用于表示 ZIP 文件条目。
	        ZipEntry ze;
	        
	        while((ze=zin.getNextEntry())!=null){
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
								fwDate =  CurrentTime.convertFormatDate(l5.element("notice_sent_date").getTextTrim());
							}
							Element l6 = root.element("invention_title");
							if(l6 != null){
								zlName = l6.getTextTrim();
							}
							map_d.put("zlName", zlName);
							map_d.put("tzsName", tzsName);
			            	map_d.put("ajNoGf", ajNoGf);
			            	map_d.put("fwSerial", fwSerial);
			            	map_d.put("fwDate", fwDate);
			            	map_d.put("applyDate", applyDate);
			            	map_d.put("sqrName", sqrName);
			            	if(tzsName.equals("专利申请受理通知书")){
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
			            	}else if(tzsName.equals("费用减缓审批通知书") || tzsName.equals("缴纳申请费通知书")){
			            		if(tzsName.equals("费用减缓审批通知书")){
			            			fjApplyDate = CurrentTime.convertFormatDate(root.element("cost_slow_req_date").getTextTrim());
				            		fjRecord = root.element("cost_slow_mes").getTextTrim();
				            		fjRate = root.element("cost_slow_rate_annul").getTextTrim();
				            		fjRate = "0."+fjRate.substring(0, fjRate.length() - 1);
			            		}
			            		feeEdate = CurrentTime.convertFormatDate(root.element("pay_deadline_date").getTextTrim());//缴费截止日期-通知书
			            		fwDate = CurrentTime.convertFormatDate(root.element("notice_sent").elementText("notice_sent_date"));//发文日
			            		Element fee = root.element("fee_info_all");
			            		Element feeDetail = null;
			            		List<Object> list_f = new ArrayList<Object>();
			            		if(fee != null){
			            			String feeTotalTxt = fee.element("fee_total").getTextTrim();
			            			map_d.put("feeTotal", feeTotalTxt);
			            			jfTotal = Double.parseDouble(feeTotalTxt);
			            			fee = fee.element("fee_info");
			            			for(@SuppressWarnings("unchecked")
		    							Iterator<Element> it = fee.elementIterator("fee") ; it.hasNext();){
			            				feeDetail = it.next();
			            				Map<String,String> map_f = new HashMap<String,String>();
			            				String feeName = feeDetail.element("fee_name").getTextTrim();
			            				String feeAmount = feeDetail.element("fee_amount").getTextTrim();
			            				if(feeAmount.equals("0") || feeAmount.equals((""))){//费用不为0的记录上
			            					
			            				}else{
			            					map_f.put("feeName", feeName);
				            				map_f.put("feeAmount", feeAmount);
				            				list_f.add(map_f);
				            				jfDetail += feeName + "," + feeAmount + ":";
			            				}
			            			}
			            			map_d.put("feeDetail", list_f);
			            		}
			            		map_d.put("fjApplyDate", fjApplyDate);
			            		map_d.put("fjRecord", fjRecord);
			            		map_d.put("feeEdate", feeEdate);
			            		map_d.put("fjRate", fjRate);
			            	}else if(tzsName.equals("办理登记手续通知书")){
			            		feeEdate = CurrentTime.convertFormatDate(root.element("pay_deadline_date").getTextTrim());//缴费截止日期-通知书
			            		fwDate = CurrentTime.convertFormatDate(root.element("notice_sent").elementText("notice_sent_date"));//发文日
			            		Element fee = root.element("fee_info_all");
			            		Element feeDetail = null;
			            		List<Object> list_f = new ArrayList<Object>();
			            		if(fee != null){
			            			Element fee_sub = fee.element("fee_info");
			            			for(@SuppressWarnings("unchecked")
		    							Iterator<Element> it = fee_sub.elementIterator("fee") ; it.hasNext();){
			            				feeDetail = it.next();
			            				Map<String,String> map_f = new HashMap<String,String>();
			            				String feeName = feeDetail.element("fee_name").getTextTrim();
			            				String feeAmount = feeDetail.elementText("fee_amount");
			            				if(feeAmount.equals("0") || feeAmount.equals((""))){//费用不为0的记录上
			            					
			            				}else{
			            					map_f.put("feeName", feeName);
				            				map_f.put("feeAmount", feeAmount);
				            				list_f.add(map_f);
				            				jfDetail += feeName + "," + feeAmount + ":";
			            				}
			            			}
			            			yearNo = fee.element("annual_year").getTextTrim();//年度数字
			            			String feeRateSign = fee.element("cost_slow_flag").getTextTrim();
			            			if(!feeRateSign.contains("无费减")){
			            				fjRate = "0."+feeRateSign.substring(0, feeRateSign.length() - 1);
			            			}
			            			map_d.put("feeDetail", list_f);
			            			map_d.put("feeEdate", feeEdate);
			            			map_d.put("fwDate", fwDate);
			            			map_d.put("fjRate", fjRate);
			            			map_d.put("yearNo", yearNo);
			            		}
			            	}else if(tzsName.equals("发明专利申请公布及进入实质审查通知书")){
			            		//获取公用的就可以了
			            	}
			            	list_d.add(map_d);
			            	break;
	        			}else{//里面不存在数据文件，需要从list.xml中获取
	        				l1 = root.element("TONGZHISXJ");
	        				if(l1 != null){
	        					Element l2 = l1.element("SHUXINGXX");
	        					if(l2 != null){
	        						tzsName = l2.elementText("TONGZHISMC");
	        						zlName = l2.elementText("FAMINGMC");
	        						ajNoGf = l2.elementText("SHENQINGH");
	        						fwSerial = root.element("FAWENXLH").getTextTrim();
	        						fwDate = CurrentTime.convertFormatDate(l2.elementText("FAWENR"));
	        						feeEdate = CurrentTime.getFinalDate(fwDate, Constants.TD_RECEIVE_DAYS);
	        						map_d.put("zlName", zlName);
	    							map_d.put("tzsName", tzsName);
	    			            	map_d.put("ajNoGf", ajNoGf);
	    			            	map_d.put("feeEdate", feeEdate);
	    			            	map_d.put("fwSerial", fwSerial);
	    			            	map_d.put("fwDate", fwDate);
	    			            	list_d.add(map_d);
	        					}
	        					break;
	        				}
	        			}
	            	}
	            }
	        }
	        
	        if(list_d.size() > 0){//存在内容
            	List<ZlajMainInfoTb> zlList = new ArrayList<ZlajMainInfoTb>();
            	if(specZlId.equals(0)){//统一导入
            		zlList = zlm.listSpecInfoByZlNo(ajNoGf);
	            	//第一次导入，需要根据专利名称、申请人、专利类型获取系统专利
	            	if(zlList.size() == 0){//说明系统中还没有该专利号的专利
	            		if(zlType.equals("")){
            				//需要通过专利号进行判定（专利号的规范####-#[专利类型编号]）（1：发明,2：实用新型，3：外观）--国内。（8：发明,9：实用新型，10：外观）--国外
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
            			}
            			zlList = zlm.listSpecInfoByOpt(zlName, sqrName, zlType,cpyId);
	            	}
            	}else if(specZlId > 0){//针对指定的专利导入
            		zlList = zlm.listSpecInfoById(specZlId, cpyId);
            	}
            	
            	if(zlList.size() > 0){
    				if(zlList.size() == 1){
    					ZlajMainInfoTb zl = zlList.get(0);
    					Integer zlId = zl.getId();
    					zlType = zl.getAjType();
//    					String zlInfo = "该专利["+zl.getAjNoGf()+"] ["+ zl.getAjTitle() +"]";
    					//只有在案件状态正常时（0）
						if(zl.getAjStopStatus().equals(0)){
							
							//-------------------------新修改S-----------------------------//
							if(currUserId.equals(zl.getTzsUserId())){//只有导入通知书的人员才能导入通知书
								double lcNo = Double.parseDouble(zl.getAjStatus());
								//查询是否已导入过此通知书
								if(tzsm.listInfoByOpt(zlId, fwSerial).size() > 0){//有此通知书
									//无需再增加
									msg = "uploadExist";//之前已经上传过，无需再次上传
									map.put("tzsName", tzsName);
									map.put("zlId", zlId);
									map.put("ajNoGf", zl.getAjNoGf());
									map.put("ajTitle", zl.getAjTitle());
									map.put("result", msg);
									//删除当前通知书压缩包
									FileOpration.deleteFile(pathPre+upZipPath);
								}else{//未增加
									if(tzsName.equals("专利申请受理通知书")){
										msg = "success";
										Integer currLcId = lcm.addLcInfo(zlId, "导入通知书", "导入受理通知书", currDate, CurrentTime.getFinalDate(currDate, 30), currDate, "",7.1);//导入通知书期限1个月
										if(currLcId > 0){
											mxm.addLcMx(currLcId, zl.getTzsUserId(), "导入受理通知书", 7.1, currDate, currDate, upZipPath, currUserId, currDate, "",  0.0, "成功导入"+tzsName);
											//发送邮件
											mm.addMail("taslM", Constants.SYSTEM_EMAIL_ACCOUNT, currUserId, "cpyUser", "新任务通知：导入费用减缓审批/缴纳申请费通知书", "专利["+zl.getAjTitle()+"]已完成受理通知书导入，请及时完成导入费用减缓审批/缴纳申请费通知书工作");
										}
										zlm.updateAjNoGfById(zlId, applyDate);//修改专利申请日
										if(zlType.equals("fm")){
											//去除费用催缴流程
//											List<ZlajLcMxInfoTb> mxList = mxm.listSpecInfoInfoByOpt(zlId, "实质审查费催缴-无申请日");
//											if(mxList.size() > 0){//之前因为不知道申请日出现的记录，需要进行修改
//												ZlajLcMxInfoTb lcmx = mxList.get(0);
//												Integer lcId_temp = lcmx.getZlajLcInfoTb().getId();
//												Integer lcmxId = lcmx.getId();
//												mxm.updateEdateById(lcmxId, "实质审查费催缴");
//												String feeEndDate_gf = CurrentTime.getFinalDate(applyDate, Constants.JF_SC_END_DATE_GF);
//												String feeEndDate_cpy = CurrentTime.getFinalDate(applyDate, Constants.JF_SC_END_DATE_CPY);
//												lcm.updateLcBasicInfoById(lcId_temp, "", "", "", feeEndDate_cpy,feeEndDate_gf);
//												List<ZlajFeeInfoTb> feeList = fm.listInfoByOpt(zlId, 3);
//												if(feeList.size() > 0){
//													fm.updateFeeInfoById(feeList.get(0).getId(), feeEndDate_cpy, feeEndDate_gf);
//												}
//											}
											
											List<ZlajFeeInfoTb> feeList = fm.listInfoByOpt(zlId, 3);
											if(feeList.size() > 0){
												ZlajFeeInfoTb fee = feeList.get(0);
												if(fee.getFeeEndDateGf().equals("noDate")){
													String finalDate = CurrentTime.getFinalDate_2(applyDate, 3);//申请人3年后的时间
													String feeEndDate_gf = CurrentTime.getFinalDate(finalDate, -1);
													String feeEndDate_cpy = CurrentTime.getFinalDate(feeEndDate_gf, Constants.TD_RECEIVE_DAYS);//代理机构比官方绝限提前天数
													fm.updateFeeInfoById(feeList.get(0).getId(), feeEndDate_cpy, feeEndDate_gf);
												}
												
											}
										}
										if(lcNo == 7.0){//说明是正常顺序
											zlm.updateZlStatusById(zlId, "7.1", "等待导入费用减缓审批/缴纳申请费通知书");
										}
										
									}else if(tzsName.equals("费用减缓审批通知书") || tzsName.equals("缴纳申请费通知书")){
										msg = "success";
										Integer currLcId = lcm.addLcInfo(zlId, "导入通知书", "导入费用减缓审批/缴纳申请费通知书", currDate, CurrentTime.getFinalDate(currDate, 30), currDate, "",7.2);//导入通知书期限1个月
										if(currLcId > 0){
											mxm.addLcMx(currLcId, zl.getTzsUserId(), "导入费用减缓审批/缴纳申请费通知书", 7.2, currDate, currDate, upZipPath, currUserId, currDate, "",  0.0, "成功导入"+tzsName);
											//发送邮件
											mm.addMail("taslM", Constants.SYSTEM_EMAIL_ACCOUNT, zl.getFeeUserId(), "cpyUser", "新任务通知：费用催缴", "专利["+zl.getAjTitle()+"]已完成费用减缓审批/缴纳申请费通知书导入，请及时完成费用催缴工作");
											
											if(!fjRate.equals("0.0") && zl.getAjFjInfo() == 0.0){//通知书存在费减并且系统中不存在费减
												//存在费减，修改
												zlm.updateZlFjInfo(zlId, Double.parseDouble(fjRate));
											}
											//取消掉增加费用催缴流程（只在缴费记录表里面体现）
//											String lcName_next = "受理费";
//											Integer nextLcId = lcm.addLcInfo(zlId, "费用催缴", lcName_next+"催缴", currDate, CurrentTime.getFinalDate(feeEdate, Constants.JF_SL_END_DATE_CPY), "", feeEdate,8.1);//代理机构缴费期限为官方期限提前15天
//											if(nextLcId > 0){
//												Integer lcMxId = mxm.addLcMx(nextLcId, zl.getFeeUserId(), lcName_next+"催缴", 8.1, currDate, "", "", 0, "", "", jfTotal,"催缴受理费:"+jfDetail);
//												if(!jfDetail.equals("") && lcMxId > 0){
//													//增加未缴纳受理费的清单
//													String[] jfDetailArr = jfDetail.split(":");
//													for(Integer i = 0 ; i < jfDetailArr.length ; i++){
//														Double fjRate_temp = 0.0;
//														Integer feeTypeId = 0;
//														String[] jfDetailArr1 = jfDetailArr[i].split(",");
//														if(jfDetailArr1[0].equals("申请费")){
//															if(zlType.equals("fm")){
//																feeTypeId = 1;//对应的是发明专利申请费
//															}else if(zlType.equals("syxx")){
//																feeTypeId = 4;//对应的是实用新型专利申请费
//															}else if(zlType.equals("wg")){
//																feeTypeId = 5;//对应的是外观设计专利申请费
//															}
//															fjRate_temp = Double.parseDouble(fjRate);
//														}else{
//															feeTypeId = fm.listInfoByName(jfDetailArr1[0]).get(0).getId();
//															fjRate_temp = 0.0;//其他费用没有费减
//														}
//														
//														if(fm.listInfoByOpt(zlId, feeTypeId).size() == 0){//不存在该费用才增加
//															fm.addZLFee(zlId, zl.getFeeUserId(), feeTypeId, Double.parseDouble(jfDetailArr1[1]), fjRate_temp, CurrentTime.getFinalDate(feeEdate, Constants.JF_SL_END_DATE_CPY), feeEdate, "", 0, cpyId, 0, "", "",tzsName,0);
//														}
//													}
//												}
//											}
											if(!jfDetail.equals("")){
												//增加未缴纳受理费的清单
												String[] jfDetailArr = jfDetail.split(":");
												for(Integer i = 0 ; i < jfDetailArr.length ; i++){
													Double fjRate_temp = 0.0;
													Integer feeTypeId = 0;
													String[] jfDetailArr1 = jfDetailArr[i].split(",");
													if(jfDetailArr1[0].equals("申请费")){
														if(zlType.equals("fm")){
															feeTypeId = 1;//对应的是发明专利申请费
														}else if(zlType.equals("syxx")){
															feeTypeId = 4;//对应的是实用新型专利申请费
														}else if(zlType.equals("wg")){
															feeTypeId = 5;//对应的是外观设计专利申请费
														}
														fjRate_temp = Double.parseDouble(fjRate);
													}else{
														feeTypeId = fm.listInfoByName(jfDetailArr1[0]).get(0).getId();
														fjRate_temp = 0.0;//其他费用没有费减
													}
													
													if(fm.listInfoByOpt(zlId, feeTypeId).size() == 0){//不存在该费用才增加
														fm.addZLFee(zlId, zl.getFeeUserId(), feeTypeId, Double.parseDouble(jfDetailArr1[1]), fjRate_temp, 
																CurrentTime.getFinalDate(feeEdate, Constants.JF_SL_END_DATE_CPY), feeEdate, "", 0, cpyId, 0, "", "",tzsName,0,"",0);
													}
												}
											}
//											不再增加实质审查费催缴流程，等申请日出现后自动结算实质审查费剩余期限（如果在缴申请费流程中一并交了实质审查费，这时候需要增加实质审查费缴费流程）
											if(zlType.equals("fm")){//如果是受理费和实审费
//												lcName_next = "受理、实质审查费";
												//实质审查费为申请日开始3年内为缴费期限--存在申请日期时才增加实质审查费催缴和
												String applyDate_sys = zl.getAjApplyDate();
												String feeEndDate_gf = "noDate";
												String feeEndDate_cpy = "noDate";
												String lcmxName = "实质审查费催缴";
												//等有了专利申请日后再修改过来
												if(!applyDate_sys.equals("")){//存在申请日期
													String finalDate = CurrentTime.getFinalDate_2(applyDate_sys, 3);//申请人3年后的时间
													feeEndDate_gf = CurrentTime.getFinalDate(finalDate, -1);
													feeEndDate_cpy = CurrentTime.getFinalDate(feeEndDate_gf, Constants.TD_RECEIVE_DAYS);//代理机构比官方绝限提前天数
												}else{
													lcmxName = "实质审查费催缴-无申请日";
													//等待导入受理通知书或者手动修改申请日的时候自动修改实质审查费的期限时间
												}
												//取消掉增加费用催缴流程（只在缴费记录表里面体现）
//												Integer nextLcId_1 = lcm.addLcInfo(zlId, "费用催缴", "实质审查费催缴", currDate, feeEndDate_cpy, "", feeEndDate_gf,8.2);
//												if(nextLcId_1 > 0){
//													double scFee_final  = Double.parseDouble(fjRate) * Constants.SC_FEE;
//													Integer lcMxId = mxm.addLcMx(nextLcId_1, currUserId, lcmxName, 8.2, currDate, "", "", 0, "", "", scFee_final,"催缴实质审查费:"+scFee_final);
//													//增加未缴纳实质审查费的清单3--发明专利申请实质审查费（实质审查费在申请日三年之内缴纳）
//													if(fm.listInfoByOpt(zlId, 3).size() == 0){//不存在该费用才增加
//														fm.addZLFee(zlId, zl.getFeeUserId(), 3, scFee_final, Double.parseDouble(fjRate),feeEndDate_cpy, feeEndDate_gf, "", 0, cpyId, 0, "", "",tzsName,lcMxId);
//													}
//												}
												double scFee_final  = Double.parseDouble(fjRate) * Constants.SC_FEE;
												//增加未缴纳实质审查费的清单3--发明专利申请实质审查费（实质审查费在申请日三年之内缴纳）
												if(fm.listInfoByOpt(zlId, 3).size() == 0){//不存在该费用才增加
													fm.addZLFee(zlId, zl.getFeeUserId(), 3, scFee_final, Double.parseDouble(fjRate),feeEndDate_cpy, 
															feeEndDate_gf, "", 0, cpyId, 0, "", "",tzsName,0,"",0);
												}
											}
//											if(lcNo == 7.1){//说明是正常顺序
//												zlm.updateZlStatusById(zlId, "8.0", "等待缴纳"+lcName_next);
//											}
										}
									}else if(tzsName.equals("补正通知书") || tzsName.contains("审查意见通知书") || tzsName.contains("初步审查合格通知书")){
										msg = "success";
										if(tzsName.contains("初步审查合格通知书")){//初审合格
    										Integer currLcId = lcm.addLcInfo(zlId, "导入通知书", "导入初步审查合格通知书", currDate, CurrentTime.getFinalDate(currDate, 30), currDate, "",9.1);//导入通知书期限1个月
											if(currLcId > 0){
												mxm.addLcMx(currLcId, zl.getTzsUserId(), "导入初步审查合格通知书", 9.1, currDate, currDate, upZipPath, currUserId, currDate, "",  0.0, "成功导入"+tzsName);
											}
											if(zlType.equals("fm")){
												if(lcNo < 13){//实审之前导入初步审查合格通知书
													List<ZlajLcMxInfoTb> mxList = mxm.listSpecInfoInfoByOpt(zlId, "实质审查费催缴");
													if(mxList.size() > 0){
														ZlajLcMxInfoTb mx = mxList.get(0);
														boolean feeFlag = (mx.getLcMxEDate().equals("") || mx.getLcMxEDate().equals("noDate"));
														if(!feeFlag){//已缴纳了实审费
															//状态进入实审中
															zlm.updateZlStatusById(zlId, "13.0", "实审中");
														}else{//需要在三年内提交实审费才能进行
															zlm.updateZlStatusById(zlId, "12.0", "等待缴纳实审费");
														}
													}
												}
											}else{//其他类型专利没有实审
												zlm.updateZlStatusById(zlId, "14.0", "等待导入授权、办理登记手续通知书");
											}
										}else{//说明需要进行补正或者审查答复（可能是初审的补正/审查答复，也可能是实审的补正/审查答复）
											String finalDate = CurrentTime.getFinalDate(fwDate, Constants.TD_RECEIVE_DAYS);//推定收到日
											String finalDate_cpy = "";//官方绝限提前15天
											Integer addMonthes = 2;
											if(lcNo >= 9.0 && lcNo < 10){//说明当前处于初审阶段（所有专利）
												if(tzsName.equals("第一次审查意见通知书")){
													//发明专利的第一次审查意见通知书的答复期限是下发日+15天+4个月，其余都是2个月
													if(zlType.equals("fm")){
														addMonthes = 4;
													}
												}
												if(lcNo < 9.9){
													lcNo += 0.1;
												}
												finalDate = CurrentTime.getFinalDate_1(finalDate, addMonthes);
												finalDate_cpy = CurrentTime.getFinalDate(finalDate,-Constants.JF_SL_END_DATE_CPY);
												Integer currLcId = lcm.addLcInfo(zlId, "导入通知书", "导入"+tzsName, currDate, CurrentTime.getFinalDate(fwDate, 30), currDate, "",lcNo);//导入通知书期限1个月
												if(currLcId > 0){
													mxm.addLcMx(currLcId, zl.getTzsUserId(), "导入"+tzsName, lcNo, currDate, currDate, upZipPath, currUserId, currDate, "",  0.0, "成功导入"+tzsName);
												}
												Double newLcNo = 0.0;
												if(lcNo < 9.9){
													newLcNo = lcNo + 0.1;
												}
												String lcName = "";
												if(tzsName.contains("审查意见通知书")){//审查答复
													lcName = "案件审查答复";
												}else{//补正
													lcName = "案件补正";
												}
												zlm.updateZlStatusById(zlId, String.valueOf(newLcNo), "等待"+lcName);
												//增加案件补正/案件审查答复的环节（当前环节的下一个环节）
												Integer nextLcId = lcm.addLcInfo(zlId, lcName, lcName, currDate, finalDate_cpy, "", finalDate,newLcNo);
												if(nextLcId > 0){
													mxm.addLcMx(nextLcId, zl.getBzUserId(), lcName, newLcNo, currDate, "", "", 0, "", "",  0.0, "等待"+lcName);
												}
    										}else if(lcNo >= 13.0 && lcNo < 14 && zlType.equals("fm")){//说明当前处于实审阶段（发明专利）
    											if(tzsName.equals("第一次审查意见通知书")){
													//发明专利的第一次审查意见通知书的答复期限是下发日+15天+4个月，其余都是2个月
													if(zlType.equals("fm")){
														addMonthes = 4;  
													}
												}
    											finalDate = CurrentTime.getFinalDate_1(finalDate, addMonthes);
    											finalDate_cpy = CurrentTime.getFinalDate(finalDate,-Constants.JF_SL_END_DATE_CPY);
    											if(lcNo < 13.9){
													lcNo += 0.1;
												}
    											Integer currLcId = lcm.addLcInfo(zlId, "导入通知书", "导入"+tzsName, currDate, CurrentTime.getFinalDate(fwDate, 30), currDate, "",lcNo);//导入通知书期限1个月
												if(currLcId > 0){
													mxm.addLcMx(currLcId, zl.getTzsUserId(), "导入"+tzsName, lcNo, currDate, currDate, upZipPath, currUserId, currDate, "",  0.0, "成功导入"+tzsName);
												}
												Double newLcNo = 0.0;
												if(lcNo < 13.9){
													newLcNo = lcNo + 0.1;
												}
												String lcName = "";
												if(tzsName.contains("审查意见通知书")){//审查答复
													lcName = "案件审查答复";
												}else{//补正
													lcName = "案件补正";
												}
												zlm.updateZlStatusById(zlId, String.valueOf(newLcNo), "等待"+lcName);
												//增加案件补正/案件审查答复的环节（当前环节的下一个环节）
												Integer nextLcId = lcm.addLcInfo(zlId, lcName, lcName, currDate, finalDate_cpy, "", finalDate,newLcNo);
												if(nextLcId > 0){
													mxm.addLcMx(nextLcId, zl.getBzUserId(), lcName, newLcNo, currDate, "", "", 0, "", "",  0.0, "等待"+lcName);
												}
    										}
										}
									}else if(tzsName.equals("驳回决定")){//专利被驳回，需要在收到该通知书后3个月内向专利复审委员会请求复审
										msg = "success";
										String finalDate = CurrentTime.getFinalDate(fwDate, Constants.TD_RECEIVE_DAYS);//推定收到日
										finalDate = CurrentTime.getFinalDate_1(finalDate, 3);//3个月内进行请求复审
										if(lcNo >= 9.0 && lcNo < 10.0){//初审补正/审查答复中被驳回
											if(lcNo < 9.9){
												lcNo += 0.1;
											}
											//正常顺序下-修改案件状态任务为驳回
										}else if(lcNo >= 13.0 && lcNo < 14.0){//实审补正/审查答复中被驳回
											if(lcNo < 13.9){
												lcNo += 0.1;
											}
											//正常顺序下-修改案件状态任务为驳回
											zlm.updateZlStatusById(zlId, String.valueOf(lcNo), "案件被驳回");
										}
										Integer currLcId = lcm.addLcInfo(zlId, "导入通知书", "导入"+tzsName, currDate, CurrentTime.getFinalDate(fwDate, 30), currDate, "",lcNo);//导入通知书期限1个月
										if(currLcId > 0){
											mxm.addLcMx(currLcId, zl.getTzsUserId(), "导入"+tzsName, lcNo, currDate, currDate, upZipPath, currUserId, currDate, "",  0.0, "成功导入"+tzsName);
										}
									}else if(tzsName.equals("办理登记手续通知书")){//授权和办理登记手续通知书
										msg = "success";
										Integer currLcId = lcm.addLcInfo(zlId, "导入通知书", "导入"+tzsName, currDate, CurrentTime.getFinalDate(fwDate, 30), currDate, "",14.0);//导入通知书期限1个月
										if(currLcId > 0){
											mxm.addLcMx(currLcId, zl.getTzsUserId(), "导入"+tzsName, 14.0, currDate, currDate, upZipPath, currUserId, currDate, "",  0.0, "成功导入"+tzsName);
											//取消掉增加费用催缴流程（只在缴费记录表里面体现）
//											Integer nextLcId = lcm.addLcInfo(zlId, "费用催缴", "授权登记费催缴", fwDate, CurrentTime.getFinalDate(feeEdate, -Constants.JF_SL_END_DATE_CPY), "", feeEdate,15.0);//导入通知书期限1个月
//											if(nextLcId > 0){
//												Integer lcMxId = mxm.addLcMx(nextLcId, zl.getTzsUserId(), "授权登记费催缴", 15.0, currDate, currDate, upZipPath, currUserId, currDate, "",  0.0, "成功导入"+tzsName);
//												if(lcNo <= 14.0){//正常顺序
//													zlm.updateZlStatusById(zlId, String.valueOf(15.0), "等待缴纳授权登记费");
//												}
												//增加未缴纳费用的清单
												String[] jfDetailArr = jfDetail.split(":");
												for(Integer i = 0 ; i < jfDetailArr.length ; i++){
													Integer feeTypeId = 0;
													String feeName = "";
													String[] jfDetailArr1 = jfDetailArr[i].split(",");
													Double fjRate_temp = 0.0;
													Integer yearNo_1 = 0;
													if(jfDetailArr1[0].equals("年费")){
														Integer yearNum = 10;//发明专利前10年度费用由费减，其他专利前6年度都费减
														yearNo_1 = Integer.parseInt(yearNo);
														String feeNamePre = "";
														if(zlType.equals("fm")){
															feeNamePre = "发明专利";
															yearNum = 20;
														}else if(zlType.equals("syxx")){
															feeNamePre = "实用新型专利";
														}else if(zlType.equals("wg")){
															feeNamePre = "外观设计专利";
														}
														fjRate_temp = Double.parseDouble(fjRate);
														//年费为申请日+1年-一天
														String applyDate_sys = zl.getAjApplyDate();//申请日
														if(!applyDate_sys.equals("")){
															//自动增加10/20年度的年费
															for(Integer j = yearNo_1 ; j <= yearNum ; j++){
																feeName = feeNamePre + "第" + j + "年"+jfDetailArr1[0];
																String yearFee_sDate = CurrentTime.getFinalDate_2(applyDate_sys, j);//第一次年费开始日期（可能不是第一年度）
																String yearFee_eDate = CurrentTime.getFinalDate(CurrentTime.getFinalDate_2(applyDate_sys, j+1), -1);//第一交年费结束日期（可能不是第一年度）
																String feeRange = yearFee_sDate+":"+yearFee_eDate;
																Double yearFee = 0d;
																List<FeeTypeInfoTb> feeTList = fm.listInfoByName(feeName);
																if(feeTList.size() > 0){//存在该费用类型
																	feeTypeId = feeTList.get(0).getId();
																	String feeCpyDate = "";//代理机构期限
																	String feeGfDate = "";//官方期限
																	if(j == yearNo_1){//第一次存在截止日期
																		feeCpyDate = CurrentTime.getFinalDate(feeEdate, Constants.JF_SL_END_DATE_CPY);
																		feeGfDate = feeEdate;
																		yearFee = Double.parseDouble(jfDetailArr1[1]);
																	}else{
																		yearFee = CommonTools.getYearFee(j, zlType);
																		feeCpyDate = CurrentTime.getFinalDate(yearFee_sDate, Constants.JF_SL_END_DATE_CPY);;
																		feeGfDate = yearFee_sDate;
																		if(fjRate_temp > 0){//存在费减
																			if(zlType.equals("fm")){//发明--头10次年费有费减
																				if(j < (yearNo_1 + 10)){//可计算费减
																					yearFee *= fjRate_temp;
																				}else{
																					fjRate_temp = 0.0;
																				}
																			}else{//新型、外观头6次有费减
																				if(j < (yearNo_1 + 6)){//可计算费减
																					yearFee *= fjRate_temp;
																				}else{
																					fjRate_temp = 0.0;
																				}
																			}
																		}
																	}
																	fm.addZLFee(zlId, zl.getFeeUserId(), feeTypeId, yearFee, fjRate_temp,feeCpyDate, 
																			feeGfDate, "", 0, cpyId, 0, "", "",tzsName,j,feeRange,0);
																}
															}
															//只增加通知书一个年度的年费，后续年费在上个年度的结束日提前45天自动增加
															/**feeName = feeNamePre + "第" + yearNo_1 + "年"+jfDetailArr1[0];
															String yearFee_sDate = CurrentTime.getFinalDate_2(applyDate_sys, yearNo_1);//第一次年费开始日期（可能不是第一年度）
															String yearFee_eDate = CurrentTime.getFinalDate(CurrentTime.getFinalDate_2(applyDate_sys, yearNo_1+1), -1);//第一交年费结束日期（可能不是第一年度）
															String feeRange = yearFee_sDate+":"+yearFee_eDate;
															Double yearFee = 0d;
															List<FeeTypeInfoTb> feeTList = fm.listInfoByName(feeName);
															if(feeTList.size() > 0){//存在该费用类型
																feeTypeId = feeTList.get(0).getId();
																String feeCpyDate = CurrentTime.getFinalDate(feeEdate, Constants.JF_SL_END_DATE_CPY);//代理机构期限
																String feeGfDate = feeEdate;//官方期限
																yearFee = Double.parseDouble(jfDetailArr1[1]);
																fm.addZLFee(zlId, zl.getFeeUserId(), feeTypeId, yearFee, fjRate_temp,feeCpyDate, 
																		feeGfDate, "", 0, cpyId, 0, "", "",tzsName,yearNo_1,feeRange,0);
															}**/
														}
													}else{
														feeName = jfDetailArr1[0];
														fjRate_temp = 0.0;
														List<FeeTypeInfoTb> feeTList = fm.listInfoByName(feeName);
														if(feeTList.size() > 0){//存在该费用类型
															feeTypeId = feeTList.get(0).getId();
															if(fm.listInfoByOpt(zlId, feeTypeId).size() == 0){
																fm.addZLFee(zlId, zl.getFeeUserId(), feeTypeId, Double.parseDouble(jfDetailArr1[1]), fjRate_temp,CurrentTime.getFinalDate(feeEdate, Constants.JF_SL_END_DATE_CPY), 
																		feeEdate, "", 0, cpyId, 0, "", "",tzsName,yearNo_1,"",0);
															}
														}
													}
												}
//											}
										}
									}
									map.put("tzsName", tzsName);
									map.put("zlId", zlId);
									map.put("ajNoGf", zl.getAjNoGf());
									map.put("ajTitle", zl.getAjTitle());
									map.put("result", msg);//已成功导入通知书
//									2018年10月24日
									String upZipPath_new = upZipPath;
									if(specZlId == 0){//没有针对指定的专利导入，需要修改上传文件路径
										String oldPath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" + upZipPath;//上传通知书的原绝对路径
										upZipPath_new = "cpyUser\\"+zlId+"\\tzs\\"+upZipPath.substring((upZipPath.lastIndexOf("\\")+1));
										String newPath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" + upZipPath_new;
										//也需要挪动到新的正确位置
										File file = new File(newPath);
										//复制上传通知书到正确位置
										 if(!file.exists()){//不存在改文件，进行复制
											 FileOpration.copyFile(oldPath, newPath);
											 FileOpration.deleteFile(oldPath);
										 }
									}
									tzsm.addTzs(zlId, tzsName, fwDate, "", fwSerial,upZipPath_new);
								}
								
							}else{
								map.put("tzsName", tzsName);
								map.put("zlId", zl.getId());
								map.put("ajNoGf", zl.getAjNoGf());
								map.put("ajTitle", zl.getAjTitle());
								map.put("result", "tzsUserError");//当前导入人员不是系统指定人员,请更换导入人员重新导入当前通知书
								//删除当前通知书压缩包
								FileOpration.deleteFile(pathPre+upZipPath);
							}
							//-------------------------新修改E-----------------------------//

						}else{
							msg =  "zlStop";//专利终止条件下不能进行导入
							map.put("tzsName", tzsName);
							map.put("zlId", zl.getId());
							map.put("ajNoGf", zl.getAjNoGf());
							map.put("ajTitle", zl.getAjTitle());
							map.put("result", msg);//已终止，不能进行导入通知书操作
							//删除当前通知书压缩包
							FileOpration.deleteFile(pathPre+upZipPath);
						}
    				}else{//获取到一个以上的专利信息(需要用户判断选择一个，然后再进行操作)
    					List<Object> list_r = new ArrayList<Object>();
    					for(Iterator<ZlajMainInfoTb> it = zlList.iterator(); it.hasNext();){
    						ZlajMainInfoTb zl = it.next();
    						Map<String,Object> map_1 = new HashMap<String,Object>();
    						map_1.put("zlId", zl.getId());
    						map_1.put("zlTitle", zl.getAjTitle());
    						map_1.put("zlSqrInfo", zl.getAjSqrName());
    						String zlTypeChi = "";
    						if(zlType.equals("fm")){
    							zlTypeChi = "发明";
    						}else if(zlType.equals("syxx")){
    							zlTypeChi = "实用新型";
    						}else if(zlType.equals("wg")){
    							zlTypeChi = "外观";
    						}
    						map_1.put("zlType", zlTypeChi);
    						list_r.add(map);
    					}
    					map.put("result", "repeatInfo");//该通知书匹配到两个以上的专利，请选择一个专利进行重新匹配
    					map.put("tzsName", tzsName);
    					map.put("zlList", list_r);
    					//把通知书内容保存
    					map.put("tzsInfo", list_d);
    					map.put("zipPath", upZipPath);
    				}
    			}else{//不存在
    				map.put("result", "noInfo");//该通知书没有匹配到专利
    				map.put("tzsName", tzsName);
					map.put("zipPath", upZipPath);
					//删除当前通知书压缩包
					FileOpration.deleteFile(pathPre+upZipPath);
    			}
	        }else{
	        	map.put("result", "tzsError");//请导入正确的通知书
				map.put("zipPath", upZipPath);
				//删除当前通知书压缩包
				FileOpration.deleteFile(pathPre+upZipPath);
	        }
	        
        }catch (Exception e) {
			// TODO Auto-generated catch block
        	map.put("result", "typeError");//只支持ZIP压缩格式的通知书
			map.put("zipPath", upZipPath);
			//删除当前通知书压缩包
			FileOpration.deleteFile(pathPre+upZipPath);
		}
        System.out.println(list_d);
        return map;
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
	 * @description
	 * @author Administrator
	 * @date 2018-9-21 下午05:23:40
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ReadZipFile.readZipFile_new("E:\\","发明-补正通知书.zip",0,0,0);
//		for(Iterator<Object> it = objList.iterator() ; it.hasNext();){
//			Object obj = it.next();
//			System.out.println(obj);
//		}
//		Double aa = 5.0;
//		System.out.println(aa.equals(5.00));
//		System.out.println(aa == 5.00);
	}

}
