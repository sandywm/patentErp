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
import com.patent.module.ZlajLcInfoTb;
import com.patent.module.ZlajLcMxInfoTb;
import com.patent.module.ZlajMainInfoTb;
import com.patent.service.ZlajFjInfoManager;
import com.patent.service.ZlajLcInfoManager;
import com.patent.service.ZlajLcMxInfoManager;
import com.patent.service.ZlajMainInfoManager;
import com.patent.util.Constants;

public class ReadZipFile {

	
	/**
	 * 读取上传的通知书内容
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
	public static List<Object> readZipFile_new(String pathPre,String upZipPath,Integer currUserId,Integer cpyId,Integer specZlId) throws Exception{
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		ZlajLcInfoManager lcm = (ZlajLcInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_LC_INFO);
		ZlajLcMxInfoManager mxm = (ZlajLcMxInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_LC_MX_INFO);
		ZlajFjInfoManager fjm = (ZlajFjInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_FJ_INFO);
		Charset gbk = Charset.forName("gbk");
		String finalPath = pathPre + "\\" + upZipPath;
		List<Object> list_d = new ArrayList<Object>();
		String msg = "";
		String currDate = CurrentTime.getStringDate();
        try {
			ZipFile zf = new ZipFile(finalPath,gbk);
			FileInputStream fileInputStream = new FileInputStream(finalPath);
	        
			CheckedInputStream check = new CheckedInputStream(fileInputStream, new CRC32());
			
	        ZipInputStream zin = new ZipInputStream(check,gbk);

	        //ZipEntry 类用于表示 ZIP 文件条目。
	        ZipEntry ze;
	        
	        while((ze=zin.getNextEntry())!=null){
	        	msg = "";
	        	String tzsName = "";//通知书名称
	        	String zlName = "";//专利名称
	    		String ajNoGf = "";//申请号或专利号
	    		String fwSerial = "";//发文序列号--通过这个确定那个为先（小的为先）
	    		String sqrName = "";//申请人
	    		String applyDate = "";//申请日
	    		String zlType = "";
	    		String fjApplyDate = "";//费减请求日期
	    		String fjRecord = "";//费减记录
	    		String feeEdate = "";//缴费截止日期/补正截止日期
	    		String fjRate = "0.0";//费减比率
	    		String jfDetail = "";//缴费详情
	    		Double jfTotal = 0.0;//缴费总计
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
							Element l6 = root.element("invention_title");
							if(l6 != null){
								zlName = l6.getTextTrim();
							}
							map_d.put("zlName", zlName);
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
			            	}else if(tzsName.equals("费用减缓审批通知书") || tzsName.equals("缴纳申请费通知书")){
			            		if(tzsName.equals("费用减缓审批通知书")){
			            			fjApplyDate = CurrentTime.convertFormatDate(root.element("cost_slow_req_date").getTextTrim());
				            		fjRecord = root.element("cost_slow_mes").getTextTrim();
				            		fjRate = root.element("cost_slow_rate_annul").getTextTrim();
				            		fjRate = "0."+fjRate.substring(0, fjRate.length() - 1);
			            		}
			            		feeEdate = CurrentTime.convertFormatDate(root.element("pay_deadline_date").getTextTrim());
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
			            				map_f.put("feeName", feeName);
			            				map_f.put("feeAmount", feeAmount);
			            				list_f.add(map_f);
			            				jfDetail += feeName + "," + feeAmount + ":";
			            			}
			            			map_d.put("feeDetail", list_f);
			            			jfDetail += feeTotalTxt;//最后是费用总计
			            		}
			            		map_d.put("fjApplyDate", fjApplyDate);
			            		map_d.put("fjRecord", fjRecord);
			            		map_d.put("feeEdate", feeEdate);
			            		map_d.put("fjRate", fjRate);
			            	}else if(tzsName.equals("发明专利申请公布及进入实质审查通知书")){
			            		
			            	}
	        			}else{//里面不存在数据文件，需要从list.xml中获取
	        				l1 = root.element("TONGZHISXJ");
	        				if(l1 != null){
	        					Element l2 = l1.element("SHUXINGXX");
	        					if(l2 != null){
	        						tzsName = l2.elementText("TONGZHISMC");
	        						zlName = l2.elementText("FAMINGMC");
	        						ajNoGf = l2.elementText("SHENQINGH");
	        						Integer qx = Integer.parseInt(l2.elementText("QIXIAN") + Constants.TD_RECEIVE_DAYS);//补正期限+推定接收期限
	        						String fwr = CurrentTime.convertFormatDate(l2.elementText("FAWENR"));
	        						feeEdate = CurrentTime.getFinalDate(fwr, qx);
	        						map_d.put("zlName", zlName);
	    							map_d.put("tzsName", tzsName);
	    			            	map_d.put("ajNoGf", ajNoGf);
	    			            	map_d.put("feeEdate", feeEdate);
	        					}
	        				}
	        			}
	        			list_d.add(map_d);
		            	List<ZlajMainInfoTb> zlList = new ArrayList<ZlajMainInfoTb>();
		            	if(specZlId.equals(0)){//统一导入
		            		zlList = zlm.listSpecInfoByZlNo(ajNoGf);
			            	//第一次导入，需要根据专利名称、申请人、专利类型获取系统专利
			            	if(zlList.size() == 0){//说明系统中还没有该专利号的专利
			            		if(tzsName.equals("专利申请受理通知书")){
			            			zlList = zlm.listSpecInfoByOpt(zlName, sqrName, zlType,cpyId);
			            		}else{
			            			msg = "noInfo";//没有这个专利
			            		}
			            	}else{//不是第一次导入
			            		if(tzsName.equals("专利申请受理通知书")){
			            			msg = "noInput";//该专利已被受理过，不需要再导入受理通知书
			            		}
			            	}
		            	}else if(specZlId > 0){//针对指定的专利导入
		            		zlList = zlm.listSpecInfoById(specZlId, cpyId);
		            	}
		            	
		            	if(zlList.size() > 0){
            				if(zlList.size() == 1){
            					ZlajMainInfoTb zl = zlList.get(0);
            					Integer zlId = zl.getId();
            					//只有在案件状态正常时（0）
        						if(zl.getAjStopStatus().equals(0)){
        							//获取当前最后一个流程
        							List<ZlajLcInfoTb> lcList = lcm.listLastInfoByAjId(zlId);
        							if(lcList.size() > 0){
        								Integer lcId = lcList.get(0).getId();
        								List<ZlajLcMxInfoTb> mxList = mxm.listLastInfoByLcId(lcId);
        								if(mxList.size() > 0){
        									ZlajLcMxInfoTb lcmx = mxList.get(0);
        									double lcNo = lcmx.getLcMxNo();//流程号
        									if(lcmx.getLcMxEDate().equals("")){
        										if(currUserId.equals(lcmx.getLcFzUserId())){
        											if(tzsName.equals("专利申请受理通知书")){
        												if(lcNo == 7.0){
	        												msg = "success";
	        												lcm.updateComInfoById(lcId, currDate);//修改流程完成时间
	        												mxm.updateEdateById(lcmx.getId(), currUserId, currUserId, upZipPath, currDate, "", currDate, "成功导入"+tzsName);
	        												zlm.updateZlStatusById(zlId, "7.1", "导入费用减缓审批/缴纳申请费通知书");
	        												Integer nextLcId = lcm.addLcInfo(zlId, "导入通知书", "导入费用减缓审批/缴纳申请费通知书", currDate, lcList.get(0).getLcCpyDate(), "", "");
	        												if(nextLcId > 0){
	        													mxm.addLcMx(nextLcId, currUserId, "导入费用减缓审批/缴纳申请费通知书", 7.1, currDate, "", "", 0, "", "", "");
	        												}
	        												zlm.updateAjNoGfById(zlId, applyDate);//修改专利申请日
	        											}else{
	        												msg = "error";//当前只能导入受理通知书
	        												map_d.put("currLcInfo", "当前任务环节为：["+lcmx.getLcMxName()+"],不能导入受理通知书");
	        											}
        											}else if(tzsName.equals("费用减缓审批通知书") || tzsName.equals("缴纳申请费通知书")){
        												if(lcNo == 7.1){
        													msg = "success";
        													lcm.updateComInfoById(lcId, currDate);
        													mxm.updateEdateById(lcmx.getId(), currUserId, currUserId, upZipPath, currDate, "", currDate, "成功导入"+tzsName);
        													String lcName_next = "";
        													if(zl.getAjType().equals("fm")){
        														lcName_next = "受理、实质审查费催缴";
        													}else{
        														lcName_next = "受理费催缴";
        													}
        													zlm.updateZlStatusById(zlId, "8.0", "等待"+lcName_next);
        													//费用缴纳为8.0，缴纳过后变为9.0，增加导入补正/审查意见通知书流程，等待导入补正通知书或者审查意见通知书
        													Integer nextLcId = lcm.addLcInfo(zlId, "费用催缴", lcName_next, currDate, CurrentTime.getFinalDate(zl.getAjApplyDate(), Constants.JF_SL_END_DATE_CPY), "", feeEdate);
	        												if(nextLcId > 0){
	        													//将缴费明细计入流程明细备注
	        													mxm.addLcMx(nextLcId, currUserId, "受理费催缴", 8.1, currDate, "", "", 0, "", "", jfDetail);
	        													if(zl.getAjType().equals("fm")){
	        														double scFee_final  = Double.parseDouble(fjRate) * Constants.SC_FEE;
		        													mxm.addLcMx(nextLcId, currUserId, "实质审查费催缴", 8.2, currDate, "", "", 0, "", "", "催缴实质审查费:"+scFee_final);
		        													//如果是发明专利，先交了受理费，如果此时没有交实质审查费，则修改流程最后期限和官方绝限，等到缴纳了实质审查费后才能修改完成时间
	        													}
	        												}
	        												//修改专利费减明细
	        												zlm.updateZlFjInfo(zlId, Double.parseDouble(fjRate));
        												}else{
        													msg = "error";//当前只能导入费用减缓审批/缴纳申请费通知书
	        												map_d.put("currLcInfo", "当前任务环节为：["+lcmx.getLcMxName()+"],不能导入缴费/费用减缓通知书");
        												}
        											}else if(tzsName.equals("补正通知书") || tzsName.contains("审查意见通知书") || tzsName.contains("初步审查合格通知书")){
        												//补正/审查意见通知书可能是初审中的，也可能是实审中的,都是审核不通过下发的通知书
        												String nextAjStatusChi = "";
        												if(lcNo >= 9.0 && lcNo < 10){//说明当前处于初审阶段
        													if(tzsName.contains("初步审查合格通知书")){//初审合格
        														if(zl.getAjType().equals("fm")){
        															//先判断之前有没有进行缴纳实质审查费
            														List<ZlajLcMxInfoTb> mxList_sz = mxm.listUnComInfoByOpt("实质审查费催缴", 8.2, lcId);
        															if(mxList_sz.size() > 0){//之前没缴纳
        																lcNo = 12.0;//发明专利进入费用催缴（缴纳实质审查费）
        																nextAjStatusChi = "等待缴纳实质审查费";
        															}else{
        																lcNo = 13.0;//进入实审阶段
        																nextAjStatusChi = "实审中";
        															}
        														}else{//其他专利
        															lcNo = 14.0;//其他专利需要等待导入授权和办理登记手续阶段
        															nextAjStatusChi = "等待导入授权和办理登记手续";
        														}
        													}else{//初审不合格
        														//进入案件提交(补正/审查答复阶段)
        														if(lcNo < 9.9){//如果流程到9.9，不再往上累加
        															lcNo += 0.1;
        														}
        														nextAjStatusChi = "等待补正/审查答复";
        													}
        												}else if(lcNo >= 13.0 && lcNo < 14){//当前处于实审补正中
        													if(tzsName.equals("补正通知书") || tzsName.contains("审查意见通知书")){
        														if(lcNo < 13.9){
        															lcNo += 0.1;
        														}
        														nextAjStatusChi = "等待补正/审查答复";
        													}
        												}else{
        													msg = "error";//当前只能导入通知书错误
	        												map_d.put("currLcInfo", "当前任务环节为：["+lcmx.getLcMxName()+"],不能导入"+tzsName);
        												}
        											}else if(tzsName){
        												
        											}
        											
        										}else{
        											msg = "noAbility";
        										}
        									}
        								}
        							}
        						}else{
        							msg =  "zlStop";//专利终止条件下不能进行导入
        						}
            				}else{//获取到一个以上的专利信息
//            					for(Iterator<ZlajMainInfoTb> it = zlList.iterator(); it.hasNext();){
//            						ZlajMainInfoTb zl = it.next();
//            						Map<String,Object> map_1 = new HashMap<String,Object>();
//            						map_1.put("zlId", zl.getId());
//            						map_1.put("zlTitle", zl.getAjTitle());
//            						map_1.put("zlSqrInfo", zl.getAjSqrName());
//            						map_1.put("", zl);
//            					}
            					map_d.put("result", "existInfo");
            				}
            			}else{//不存在
            				map_d.put("result", "noInfo");
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
//		ReadZipFile.readZipFile_new("E:\\","实用新型-受理+交纳申请费通知书.zip",0,0,0);
//		for(Iterator<Object> it = objList.iterator() ; it.hasNext();){
//			Object obj = it.next();
//			System.out.println(obj);
//		}
		Double aa = 5.0;
		System.out.println(aa.equals(5.00));
		System.out.println(aa == 5.00);
	}

}
