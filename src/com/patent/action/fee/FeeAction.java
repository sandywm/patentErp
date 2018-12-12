/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.fee;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.patent.factory.AppFactory;
import com.patent.module.FeeExportRecordInfo;
import com.patent.module.ZlajFeeInfoTb;
import com.patent.module.ZlajMainInfoTb;
import com.patent.page.PageConst;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.FeeExportRecordInfoManager;
import com.patent.service.ZlajFeeInfoManager;
import com.patent.tools.CommonTools;
import com.patent.tools.Convert;
import com.patent.tools.CurrentTime;
import com.patent.util.Constants;
import com.patent.util.WebUrl;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 12-10-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class FeeAction extends DispatchAction {
	
	/**
	 * 获取session中的用户ID
	 * @param request
	 * @return
	 */
	private Integer getLoginUserId(HttpServletRequest request){
        Integer userId = (Integer)request.getSession(false).getAttribute(Constants.LOGIN_USER_ID);
        return userId;
	}
	
	/**
	 * 获取session中的用户角色编号
	 * @param request
	 * @return
	 */
	private Integer getLoginRoleId(HttpServletRequest request){
        Integer userId = (Integer)request.getSession(false).getAttribute(Constants.LOGIN_USER_ROLE_ID);
        return userId;
	}
	
	/**
	 * 获取session中的用户角色名称
	 * @param request
	 * @return
	 */
	private String getLoginRoleName(HttpServletRequest request){
        String roleName = (String)request.getSession(false).getAttribute(Constants.LOGIN_USER_ROLE_NAME);
        return roleName;
	}
	
	/**
	 * 获取session中的登录类型
	 * @author Administrator
	 * @date 2018-7-31 下午09:39:57
	 * @ModifiedBy
	 * @param request
	 * @return
	 */
	private String getLoginType(HttpServletRequest request){
        String loginType = (String)request.getSession(false).getAttribute(Constants.LOGIN_TYPE);
        return loginType;
	}
	
	/**
	 * 封装json
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午10:17:05
	*  @param obj
	*  @param response
	*  @throws IOException
	 */
	private void getJsonPkg(Object obj,HttpServletResponse response) throws IOException{
		String json = JSON.toJSONString(obj);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
	}
	
	/**
	 * 封装添加单元格数据内容方法
	 * @description
	 * @author Administrator
	 * @date 2018-11-28 上午09:40:37
	 * @param column 列名（,隔开）
	 * @param num 列数
	 * @param row
	 * @param style
	 */
	private static void addCellData(Integer num,String column,HSSFRow row,HSSFCellStyle style){
		HSSFCell cell = row.createCell(0); 
		String[] columnArr = column.split(":");
		for(Integer i = 0 ; i < num ; i++){
			cell = row.createCell(i); 
	        cell.setCellStyle(style);  
	        if(columnArr[i].equals("noData")){
	        	cell.setCellValue(""); 
	        }else{
	        	cell.setCellValue(columnArr[i]); 
	        }
		}
	}
	
	/**
	 * 导向费用页面
	 * @description
	 * @author Administrator
	 * @date 2018-12-10 上午09:05:53
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goFeePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("feePage");
	}
	
	/**
	 * 获取当前代理机构的费用情况
	 * @description
	 * @author Administrator
	 * @date 2018-12-5 上午09:28:33
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllFeeInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ZlajFeeInfoManager fm = (ZlajFeeInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_FEE_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		String roleName = this.getLoginRoleName(request);
		Integer currUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		if(this.getLoginType(request).equals("cpyUser")){
			Integer cpyId = cum.getEntityById(currUserId).getCpyInfoTb().getId();
			boolean abilityFlag = false;
			if(roleName.equals("管理员")){
				abilityFlag = true;
			}else{//只获取自己的任务流程
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "listFee");//只有具有浏览权限的人员
			}
			if(abilityFlag){
				Integer feeStatus = CommonTools.getFinalInteger("feeStatus", request);//费用缴纳状态（0未交，1：已交）
				Integer diffDays = CommonTools.getFinalInteger("diffDays", request);//代理机构缴费截止日期距当前日期天数小于等于指定的天数
				String zlNo = CommonTools.getFinalStr("zlNo", request);
				String ajNo = CommonTools.getFinalStr("ajNo", request);
				Integer cusId = CommonTools.getFinalInteger("cusId", request);
				String sDate = CommonTools.getFinalStr("sDate", request);//缴费开始时间
				String eDate = CommonTools.getFinalStr("eDate", request);//缴费结束时间
				
				List<ZlajFeeInfoTb> zlfList = new ArrayList<ZlajFeeInfoTb>();
				Integer count = 0;
				if(feeStatus.equals(0)){
					zlfList = fm.listInfoByOpt(cpyId, feeStatus, diffDays, zlNo, ajNo, cusId, "", "", 0, 0);
				}else{
					Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("limit")), 10);//等同于pageSize
					Integer pageNo = CommonTools.getFinalInteger("page", request);//等同于pageNo
					count = fm.getCountByOpt(cpyId, zlNo, ajNo, cusId,sDate,eDate);
					if(count > 0){
						zlfList = fm.listInfoByOpt(cpyId, feeStatus, diffDays, zlNo, ajNo, cusId,sDate,eDate, pageNo, pageSize);
					}
				}
				if(zlfList.size() > 0){
					List<Object> list_d = new ArrayList<Object>();
					String currDate = CurrentTime.getStringDate();
					Double feeTotal = 0d;
					for(Iterator<ZlajFeeInfoTb> it = zlfList.iterator() ; it.hasNext();){
						ZlajFeeInfoTb zlf = it.next();
						Map<String,Object> map_d = new HashMap<String,Object>();
						ZlajMainInfoTb zl = zlf.getZlajMainInfoTb();
						map_d.put("zlNo", zl.getAjNoGf());
						map_d.put("ajNo", zl.getAjNo());
						map_d.put("zlName", zl.getAjTitle());
						map_d.put("sqrName", zl.getAjSqrName());
						map_d.put("feeName", zlf.getFeeTypeInfoTb().getFeeName());
						String feeEndDateJj = zlf.getFeeEndDateJj();
						String feeEndDateGf = zlf.getFeeEndDateGf();
						Double feePrice = zlf.getFeePrice();
						map_d.put("feeEndDateJj", feeEndDateJj);
						map_d.put("feeEndDateGf", feeEndDateGf);
						map_d.put("feePrice", feePrice);
						if(feeStatus.equals(0)){//未交费用
							Integer diffDays_jj = CurrentTime.compareDate(currDate,feeEndDateJj);
							Integer diffDays_gf = CurrentTime.compareDate(currDate,feeEndDateGf);
							map_d.put("diffDays_jj", diffDays_jj);
							map_d.put("diffDays_Gf", diffDays_gf);
						}else{//已缴费
							map_d.put("jfDate", zlf.getFeeJnDate());
							map_d.put("backFee", zlf.getBackFee());//客户退还的费用
							map_d.put("backDate", zlf.getBackDate());//客户退还时间
							feeTotal = Convert.convertInputNumber_2(feeTotal + feePrice);
							map_d.put("feeBatchNo", zlf.getFeeBatchNo());
							map_d.put("bankSerialNo", zlf.getBankSerialNo());
							map_d.put("fpDate", zlf.getFpDate());
							map_d.put("fpNo", zlf.getFpNo());
						}
						list_d.add(map_d);
					}
					if(feeStatus.equals(1)){
						List<Object> fmObj = fm.getTjFeeInfoByOpt(cpyId, zlNo, ajNo, cusId, sDate, eDate);
						Object[] obj = (Object[]) fmObj.get(0);
						Double yjFeeTotal = (Double)obj[0];//已交费用总计
						Double backFeeTotal = (Double)obj[1];//实收费用总计
						String noBackFeeTotal = Convert.convertInputNumber_3(yjFeeTotal - backFeeTotal);//未收费用总计
						map.put("yjFeeTotal", Convert.convertInputNumber_3(yjFeeTotal));
						map.put("backFeeTotal", Convert.convertInputNumber_3(backFeeTotal));
						map.put("noBackFeeTotal", noBackFeeTotal);
					}else{
						map.put("feeTotal", feeTotal);//应缴费总计--未交费用模式下使用
					}
					map.put("msg", "success");
					map.put("data", list_d);
					map.put("count", count);
					map.put("code", 0);
				}else{
					map.put("msg", "noInfo");
				}
			}
		}
		this.getJsonPkg(map, response);
		return null;
	}

	
	/**
	 * 导出未缴费清单到excel(上交国家局/客户)
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-7 上午12:01:40
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportFeeInfoToExcel_1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		ZlajFeeInfoManager fm = (ZlajFeeInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_FEE_INFO);
		FeeExportRecordInfoManager ferm = (FeeExportRecordInfoManager) AppFactory.instance(null).getApp(Constants.WEB_FEE_EXPORT_RECORD_INFO);
		String roleName = this.getLoginRoleName(request);
		Integer currUserId = this.getLoginUserId(request);
		if(this.getLoginType(request).equals("cpyUser")){
			Integer cpyId = cum.getEntityById(currUserId).getCpyInfoTb().getId();
			boolean abilityFlag = false;
			if(roleName.equals("管理员")){
				abilityFlag = true;
			}else{//只获取自己的任务流程
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "listFee");//只有具有浏览权限的人员
			}
			if(abilityFlag){
				String idStr = CommonTools.getFinalStr("idStr", request);//所有选择的专利编号的拼接
				List<ZlajFeeInfoTb> zlfList = new ArrayList<ZlajFeeInfoTb>();
				if(!idStr.equals("")){
					zlfList = fm.listUnJfInfoByOpt(cpyId, idStr);
				}
//				Integer feeStatus = CommonTools.getFinalInteger("feeStatus", request);//费用缴纳状态（0未交，1：已交）
//				Integer diffDays = CommonTools.getFinalInteger("diffDays", request);//代理机构缴费截止日期距当前日期天数小于等于指定的天数
//				String zlNo = CommonTools.getFinalStr("zlNo", request);
//				String ajNo = CommonTools.getFinalStr("ajNo", request);
//				Integer cusId = CommonTools.getFinalInteger("cusId", request);
//				zlfList = fm.listInfoByOpt(cpyId, feeStatus, diffDays, zlNo, ajNo, cusId, "", "", 0, 0);
				if(zlfList.size() > 0){
					Double feePrice_total = 0d;
					for(Iterator<ZlajFeeInfoTb> it = zlfList.iterator() ; it.hasNext();){
						ZlajFeeInfoTb zlf = it.next();
						feePrice_total += zlf.getFeePrice();
					}
					// 第一步，创建一个webbook，对应一个Excel文件  
			        HSSFWorkbook wb = new HSSFWorkbook();  
			        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
			        HSSFSheet sheet = wb.createSheet("费用清单");  
			        //设置横向打印
			        sheet.getPrintSetup().setLandscape(true);
			        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
			        HSSFRow row = sheet.createRow(0);  
			        // 第四步，创建单元格，并设置值表头 设置表头居中  
			        HSSFCellStyle style = wb.createCellStyle();  
			        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
		            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		            
		            HSSFFont font_1 = wb.createFont();    
		            font_1.setFontName("宋体");    
		            font_1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
		            font_1.setFontHeightInPoints((short) 16);//设置字体大小  (备注)
		            
		            
		            HSSFFont font_2 = wb.createFont();    
		            font_2.setFontName("宋体");    
		            font_2.setFontHeightInPoints((short) 16);//设置字体大小  (备注)
		            
		            style.setFont(font_1);
		            
		            FeeAction.addCellData(6, "序列号:申请号:缴费人姓名:费用名称:金额:备注", row, style);
		            
		            row = sheet.createRow(1);//创建行
		        	// 第四步，创建单元格，并设置值  
		            FeeAction.addCellData(6,"1:noData:noData:noData:"+Convert.convertInputNumber_3(feePrice_total)+":noData", row, style);
					Integer i = 2;
					for(Iterator<ZlajFeeInfoTb> it = zlfList.iterator() ; it.hasNext();){
						ZlajFeeInfoTb zlf = it.next();
						row = sheet.createRow(i);//创建行
						ZlajMainInfoTb zl = zlf.getZlajMainInfoTb();
						String feeRemark = zlf.getFeeRemark().equals("") ? "noData" : zlf.getFeeRemark();
						FeeAction.addCellData(6,""+i+":"+zl.getAjNoGf()+":"+zl.getAjSqrName()+":"+zlf.getFeeTypeInfoTb().getFeeName()+":"+Convert.convertInputNumber_3(zlf.getFeePrice())+":"+feeRemark+"", row, style);
						i++;
					}
		        	// 第六步，将文件存到指定位置
			    	String absoFilePath = "";//绝对地址
			    	try  {  
			    		String currTime = CurrentTime.getCurrentTime();
			        	String fileName = "费用清单_"+CurrentTime.getStringTime()+".xls";
			        	String filePath_pre = "Module\\excelTemp\\"+cpyId+"\\";
			        	String folder = WebUrl.DATA_URL_PRO + filePath_pre;//通过代理机构把excel分开
			        	absoFilePath = folder +fileName;
			        	File file = new File(folder);
						if(!file.exists()){
							file.mkdirs();
						}
			            FileOutputStream fout = new FileOutputStream(absoFilePath);//存到服务器
			            wb.write(fout);  
			            fout.close();  
			            //生成记录
			            ferm.addFER(fileName, currTime, currUserId, filePath_pre+fileName, cpyId);
				        //第七步 下载文件到客户端
				        OutputStream fos = null;
				        BufferedOutputStream bos = null;
				        InputStream fis = null;
				        BufferedInputStream bis = null;
				        fis = new FileInputStream(new File(absoFilePath));
						bis = new BufferedInputStream(fis);
						fos = response.getOutputStream();
						bos = new BufferedOutputStream(fos);
						fileName = URLEncoder.encode(fileName,"UTF-8");
						//这个就就是弹出下载对话框的关键代码
						response.setHeader("Pragma", "No-cache");
						response.setHeader("Cache-Control", "No-cache");
						response.setDateHeader("Expires", 0); 
				        response.setHeader("Content-disposition","attachment;filename=" +fileName);
				        response.setContentType("application/x-download");
				        int bytesRead = 0;
				        byte[] buffer = new byte[8192];
				        while ((bytesRead = bis.read(buffer,0,8192)) != -1) {
				        	fos.write(buffer, 0, bytesRead);
				        }
				        fos.flush();
				        fis.close();
				        bis.close();
				        fos.close();
				        bos.close();
			        }  
			        catch (IOException e){  
			            //e.printStackTrace();  
			        }
				}
			}
		}
		return null;
	}
	
	/**
	 * 分页获取未交费用清单列表(用户导出的未交费清单)
	 * @description
	 * @author Administrator
	 * @date 2018-12-10 下午04:22:14
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPageFER(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		FeeExportRecordInfoManager ferm = (FeeExportRecordInfoManager) AppFactory.instance(null).getApp(Constants.WEB_FEE_EXPORT_RECORD_INFO);
		String roleName = this.getLoginRoleName(request);
		Integer currUserId = this.getLoginUserId(request);
		String msg = "error";
		Map<String,Object> map = new HashMap<String,Object>();
		if(this.getLoginType(request).equals("cpyUser")){
			Integer cpyId = cum.getEntityById(currUserId).getCpyInfoTb().getId();
			boolean abilityFlag = false;
			if(roleName.equals("管理员")){
				abilityFlag = true;
			}else{//只获取自己的任务流程
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "listFee");//只有具有浏览权限的人员
			}
			if(abilityFlag){
				String addDateS = CommonTools.getFinalStr("sDate", request);
				String addDateE = CommonTools.getFinalStr("eDate", request);
				Integer count = ferm.getCountByOpt(addDateS, addDateE, cpyId);
				if(count > 0){
					Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("limit")), 10);//等同于pageSize
					Integer pageNo = CommonTools.getFinalInteger("page", request);//等同于pageNo
					List<FeeExportRecordInfo> ferList = ferm.listPageInfoByOpt(addDateS, addDateE, cpyId, pageNo, pageSize);
					if(ferList.size() > 0){
						msg = "success";
						List<Object> list_d = new ArrayList<Object>();
						for(Iterator<FeeExportRecordInfo> it = ferList.iterator() ; it.hasNext();){
							FeeExportRecordInfo fer = it.next();
							Map<String,Object> map_d = new HashMap<String,Object>();
							map_d.put("ferId", fer.getId());
							map_d.put("ferName", fer.getExcelName());
							map_d.put("addTime", fer.getAddTime());
							map_d.put("userName", fer.getUser().getUserName());
							map_d.put("excelPath", fer.getExcelPath());
							list_d.add(map_d);
						}
						map.put("ferList", list_d);
					}else{
						msg = "noInfo";
					}
				}
			}else{
				msg = "noAbility";
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 导入已缴费清单并进行平账（代理机构平账）
	 * @description
	 * @author Administrator
	 * @date 2018-12-11 下午03:45:27
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dealYjFeeExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		FeeExportRecordInfoManager ferm = (FeeExportRecordInfoManager) AppFactory.instance(null).getApp(Constants.WEB_FEE_EXPORT_RECORD_INFO);
//		String filePath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" + CommonTools.getFinalStr("filePath", request);
		String filePath = CommonTools.getFinalStr("filePath", request);
		//读取excel内容
		Sheet sheet;  
        Workbook book;  
        Cell cell1;
        HSSFWorkbook wb;
        WorkbookSettings wbs = new WorkbookSettings();
        wbs.setEncoding("GBK"); // 解决中文乱码
        wbs.setSuppressWarnings(true); 
        book= Workbook.getWorkbook(new File(filePath),wbs);
		//获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)  
		sheet=book.getSheet(0); 
		Integer i = 2;
        Integer maxRow = sheet.getRows();
        while(i < maxRow){
        	//获取每一行的单元格   
            cell1=sheet.getCell(0,i);//（列，行）  
            if("".equals(cell1.getContents())==true)    //如果读取的数据为空  
                break;  
            String zlNo = sheet.getCell(1,i).getContents().replace(" ", "").replace("\t", "");//专利号
            String feeName = sheet.getCell(3,i).getContents().replace(" ", "").replace("\t", "");//费用名称
            String feePrice = sheet.getCell(4,i).getContents().replace(" ", "").replace("\t", "");//费用金额
            System.out.println(zlNo + " " + feeName + " " + feePrice);
            i++;
        }
		return null;
	}
	
	/**
	 * 增加客户还款动作
	 * @description
	 * @author Administrator
	 * @date 2018-12-11 下午03:48:34
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBackFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		FeeExportRecordInfoManager ferm = (FeeExportRecordInfoManager) AppFactory.instance(null).getApp(Constants.WEB_FEE_EXPORT_RECORD_INFO);
		
		return null;
	}
}