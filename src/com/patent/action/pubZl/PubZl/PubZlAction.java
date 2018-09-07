/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.pubZl.PubZl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.patent.action.base.Transcode;
import com.patent.factory.AppFactory;
import com.patent.module.CpyInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.module.PubZlInfoTb;
import com.patent.module.ZlajMainInfoTb;
import com.patent.page.PageConst;
import com.patent.service.ApplyInfoManager;
import com.patent.service.CpyInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.MailInfoManager;
import com.patent.service.PubZlInfoManager;
import com.patent.service.ZlajMainInfoManager;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;
import com.patent.tools.FileOpration;
import com.patent.util.Constants;
import com.patent.util.WebUrl;

/** 
 * MyEclipse Struts
 * Creation date: 08-13-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class PubZlAction extends DispatchAction {
	
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
	 * 专利列表管理界面
	 * @description
	 * @author wm
	 * @date 2018-8-13 上午09:00:29
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goPubZlPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return mapping.findForward("zlPage");
	}
	
	/**
	 * 分页获取专利列表(申请人/公司，平台用户查看)
	 * @description
	 * @author wm
	 * @date 2018-8-13 上午09:03:06
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getPageInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PubZlInfoManager pzm = (PubZlInfoManager) AppFactory.instance(null).getApp(Constants.WEB_PUB_ZL_INFO);
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		String zlTitle = Transcode.unescape(request.getParameter("zlTitle"), request);
		String zlNo = request.getParameter("zlNo");
		String zlType = request.getParameter("zlType");
		String pubDate = request.getParameter("pubDate");
		Integer zlStatus = Integer.parseInt(request.getParameter("zlStatus"));
		Integer currUserId = 0;
		if(this.getLoginType(request).equals("appUser")){//申请人/公司查看
			currUserId = this.getLoginUserId(request);
		}
		//平台管理人员、代理机构查看全部
		Integer count = pzm.getCountByOpt(currUserId, zlTitle, zlNo, zlType, pubDate, zlStatus);
		if(count > 0){
			map.put("msg", "success");
			Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("limit")), 10);//等同于pageSize
			Integer pageNo = CommonTools.getFinalInteger(request.getParameter("page"));//等同于pageNo
			List<PubZlInfoTb> pzList = pzm.listPageInfoByOpt(currUserId, zlTitle, zlNo, zlType, pubDate, zlStatus, pageNo, pageSize);
			List<Object> list_d = new ArrayList<Object>();
			for(Iterator<PubZlInfoTb> it = pzList.iterator() ; it.hasNext();){
				PubZlInfoTb pz = it.next();
				Map<String,Object> map_d = new HashMap<String,Object>();
				map_d.put("id", pz.getId());
				map_d.put("title", pz.getZlTitle());
				if(pz.getZlType().equals("fm")){
					map_d.put("type", "发明");
				}else if(pz.getZlType().equals("syxx")){
					map_d.put("type", "实用新型");
				}else if(pz.getZlType().equals("wg")){
					map_d.put("type", "外观");
				}else if(pz.getZlType().equals("fmxx")){
					map_d.put("type", "发明、实用新型");
				}
				if(pz.getZlUpCl().equals("")){
					map_d.put("clFlag", false);
				}else{
					map_d.put("clFlag", true);
				}
				map_d.put("pubDate", pz.getZlNewDate());
				map_d.put("lqrName", pz.getLqUserName());
				map_d.put("lqrCpyName", pz.getLqCpyName());
				if(pz.getLqUserId() > 0){
					map_d.put("lqDate", pz.getLqDate());
				}else{
					map_d.put("lqDate", "");
				}
				map_d.put("pubInfo", pz.getApplyInfoTb().getAppName());
				Integer ajId = pz.getAjId();
				String ajNoQt = "";
				map_d.put("ajId", ajId);
				if(ajId > 0){
					List<ZlajMainInfoTb> zlList = zlm.listSpecInfoById(ajId, 0);
					if(zlList.size() > 0){
						ajNoQt = zlList.get(0).getAjNoQt();
					}
				}
				map_d.put("ajNoQt", ajNoQt);
				map_d.put("ajLqStatus", pz.getZlStatus());
				list_d.add(map_d);
			}
			map.put("data", list_d);
			map.put("count", count);
			map.put("code", 0);
		}else{
			map.put("msg", "noInfo");
		}
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 根据条件获取发布专利列表记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-13 上午11:27:28
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PubZlInfoManager pzm = (PubZlInfoManager) AppFactory.instance(null).getApp(Constants.WEB_PUB_ZL_INFO);
		Map<String,Integer> map = new HashMap<String,Integer>();
		String zlTitle = Transcode.unescape(request.getParameter("zlTitle"), request);
		String zlNo = request.getParameter("zlNo");
		String zlType = request.getParameter("zlType");
		String pubDate = request.getParameter("pubDate");
		Integer zlStatus = Integer.parseInt(request.getParameter("zlStatus"));
		Integer currUserId = 0;
		if(this.getLoginType(request).equals("appUser")){//申请人/公司查看
			currUserId = this.getLoginUserId(request);
		}
		Integer count = pzm.getCountByOpt(currUserId, zlTitle, zlNo, zlType, pubDate, zlStatus);
		map.put("result", count);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取专利任务详情（只有收费会员和平台可以查看详情，发布人可以查看自己的发布任务详情）
	 * @description
	 * @author wm
	 * @date 2018-9-4 上午11:19:13
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetailInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PubZlInfoManager pzm = (PubZlInfoManager) AppFactory.instance(null).getApp(Constants.WEB_PUB_ZL_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO);
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		Integer pubId = CommonTools.getFinalInteger("pubId", request);
		Integer userId = 0;
		String msg = "error";
		String loginType = this.getLoginType(request);
		if(loginType.equals("appuser")){
			userId = this.getLoginUserId(request);
			msg = "success";
		}else{
			if(loginType.equals("cpyUser")){
				//需要判断当前用户所在的代理机构是否是收费会员
				CpyUserInfo user = cum.getEntityById(this.getLoginUserId(request));
				if(user != null){
					Integer cpyId = user.getCpyInfoTb().getId();
					List<CpyInfoTb> cpyList = cm.listInfoById(cpyId);
					if(cpyList.size() > 0){
						CpyInfoTb cpy = cpyList.get(0);
						if(cpy.getCpyLevel() > 0){//收费会员
							Integer diffDays = CurrentTime.compareDate(CurrentTime.getStringDate(), cpy.getEndDate());
							if(diffDays > 0){
								msg = "success";
							}else{
								msg = "outDate";//过期会员不能使用
							}
						}else{
							msg = "lowLevel";//免费会员不能使用
						}
					}
				}
			}else{
				msg = "success";
			}
		}
		if(msg.equals("success")){
			List<PubZlInfoTb> pbZlList = pzm.listSpecInfoByOpt(pubId, userId);
			if(pbZlList.size() > 0){
				PubZlInfoTb pz = pbZlList.get(0);
				map.put("zlId", pz.getId());
				map.put("zlTitle", pz.getZlTitle());
				map.put("zlContent", pz.getZlContent());
				String zlType = pz.getZlType();
				if(zlType.equals("fm")){
					map.put("typeChi", "发明");
				}else if(zlType.equals("syxx")){
					map.put("typeChi", "实用新型");
				}else if(zlType.equals("wg")){
					map.put("typeChi", "外观");
				}
				map.put("zlType", zlType);
				String zlUpCl = pz.getZlUpCl();
				String zlUpClSize = "";
				String zlUpClName = "";
				List<Object> list_file = new ArrayList<Object>();
				if(!zlUpCl.equals("")){
					String[] zlUpClArr = zlUpCl.split(",");
					for(Integer i = 0  ; i < zlUpClArr.length ; i++){
						zlUpClName = zlUpClArr[i].substring((zlUpClArr[i].lastIndexOf("\\") + 1));
						zlUpClSize = FileOpration.getFileSize(WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" + zlUpClArr[i]);
						Map<String,String> map_file = new HashMap<String,String>();
						map_file.put("zlUpClPath", zlUpClArr[i]);
						map_file.put("fileName", zlUpClName);
						map_file.put("fileSize", zlUpClSize);
						map_file.put("downFilePath", zlUpClArr[i].replaceAll("\\\\", "\\\\\\\\"));
						list_file.add(map_file);
					}
				}
				map.put("zlUpCl", list_file);
				
				map.put("zlStatus", pz.getZlStatus());
				map.put("zlStatusChi", pz.getZlStatus().equals(0) ? "待领取" : "已领取");
				map.put("pubDate", pz.getZlNewDate());
				map.put("lqrName", pz.getLqUserName());
				map.put("lqrCpyName", pz.getLqCpyName());
				if(pz.getLqUserId() > 0){
					map.put("lqDate", pz.getLqDate());
				}else{
					map.put("lqDate", "");
				}
				map.put("pubInfo", pz.getApplyInfoTb().getAppName());
				Integer ajId = pz.getAjId();
				String ajNoQt = "";
				map.put("ajId", ajId);
				if(ajId > 0){
					List<ZlajMainInfoTb> zlList = zlm.listSpecInfoById(ajId, 0);
					if(zlList.size() > 0){
						ajNoQt = zlList.get(0).getAjNoQt();
					}
				}
				map.put("ajNoQt", ajNoQt);
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 修改自己发布的专利(只有在没领取的时候才能修改)
	 * @description
	 * @author wm
	 * @date 2018-8-13 上午11:30:29
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSelfPzInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PubZlInfoManager pzm = (PubZlInfoManager) AppFactory.instance(null).getApp(Constants.WEB_PUB_ZL_INFO);
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		if(this.getLoginType(request).equals("appUser")){//申请人/公司查看
			Integer currUserId = this.getLoginUserId(request);
			Integer pubId = Integer.parseInt(request.getParameter("pubId"));
			List<PubZlInfoTb> pubZlList = pzm.listSpecInfoByOpt(pubId, currUserId);
			if(pubZlList.size() > 0){
				PubZlInfoTb zl = pubZlList.get(0);
				if(zl.getZlStatus().equals(0)){
					String zlTitle = Transcode.unescape(request.getParameter("zlTitle"), request);
					String zlContent =  Transcode.unescape(request.getParameter("zlContent"), request);
					String zlType = request.getParameter("zlType");
					String zlUpCl = request.getParameter("zlUpCl");//页面传递过来
					String zlUpCl_db = zl.getZlUpCl();//数据库中
					String zlUpCl_final = "";//需要删除的文件路径
					if(zlUpCl.equals("")){//删除该文件夹里面所有文件
						if(!zlUpCl_db.equals("")){
							String firstFilePath = zlUpCl_db.split(",")[0];
							FileOpration.deleteAllFile(WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" + firstFilePath.substring(0,firstFilePath.lastIndexOf("\\")));
						}
					}else{
						if(zlUpCl_db.equals("")){//数据库为空
							//不执行删除
						}else{
							if(zlUpCl.equals(zlUpCl_db)){//数据库和页面传递相同
								//不执行删除
							}else{
								String[] zlUpClArr = zlUpCl.split(",");
								String[] zlUpClArr_db = zlUpCl_db.split(",");
								for(Integer i = 0 ; i < zlUpClArr_db.length ; i++){
									boolean existFlag = false;
									for(Integer j = 0 ; j < zlUpClArr.length ; j++){
										if(zlUpClArr_db[i].equals(zlUpClArr[j])){
											existFlag = true;
											break;
										}else{
											existFlag = false;
										}
									}
									if(!existFlag){
										zlUpCl_final += zlUpClArr_db[i] + ",";
									}
								}
								if(!zlUpCl_final.equals("")){
									zlUpCl_final = zlUpCl_final.substring(0, zlUpCl_final.length() - 1);
									//删除文件
									String[] zlUpClArr_final = zlUpCl_final.split(",");
									for(Integer k = 0 ; k < zlUpClArr_final.length ; k++){
										FileOpration.deleteFile(WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" + zlUpClArr_final[k]);
									}
								}
							}
						}
					}
					
					boolean flag = pzm.updateBasicInfoById(pubId, zlTitle, zlContent, zlType, zlUpCl);
					if(flag){
						msg = "success";
					}else{
						msg = "error";
					}
				}else{
					msg = "notUpdate";//专利在领取后不能进行修改
				}
			}else{
				msg = "fail";
			}
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 修改发布专利的高级信息（专利状态、领取信息、案件编号）
	 * @description
	 * @author wm
	 * @date 2018-8-13 下午03:59:26
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHignPzInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PubZlInfoManager pzm = (PubZlInfoManager) AppFactory.instance(null).getApp(Constants.WEB_PUB_ZL_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO);
		ApplyInfoManager am = (ApplyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		Integer pubId = CommonTools.getFinalInteger(request.getParameter("pubId"));
		Integer zlStatus = 0;
		Integer lqUserId = this.getLoginUserId(request);;
		String lqUserName = "";
		Integer lqCpyId = 0;
		String lqCpyName = "";
		String lqDate = "";
		Integer ajId = 0;
		boolean flag = false;
		List<PubZlInfoTb> pzList = new ArrayList<PubZlInfoTb>();
		if(pubId > 0){
			if(this.getLoginType(request).equals("appUser")){//申请人/公司修改专利状态
				Integer currUserId = this.getLoginUserId(request);
				pzList = pzm.listSpecInfoByOpt(pubId, currUserId);
				if(pzList.size() > 0 && pzList.get(0).getZlStatus().equals(1)){//只有当专利领取状态为已领取的时候才能进行撤销
					flag = true;
				}
			}else if(this.getLoginType(request).equals("cpyUser")){//当是代理机构员工时
				//必须是银牌以上会员并且没到期才能接任务
				CpyUserInfo cUser = cum.getEntityById(lqUserId);
				CpyInfoTb cpy = cUser.getCpyInfoTb();
				Integer cpyLevel = cpy.getCpyLevel();
				lqUserName = cUser.getUserName();
				lqCpyId = cUser.getCpyInfoTb().getId();
				lqCpyName = cUser.getCpyInfoTb().getCpyName();
				lqDate = CurrentTime.getCurrentTime();
				ajId = CommonTools.getFinalInteger("ajId", request);
				
				pzList = pzm.listSpecInfoByOpt(pubId, 0);
				if(pzList.size() > 0){//存在信息&& pzList.get(0).getZlStatus().equals(zlStatus)
					if(pzList.get(0).getZlStatus().equals(0)){//未领取，设置为成领取
						Integer diffDays = CurrentTime.compareDate(CurrentTime.getStringDate(), cpy.getEndDate());
						if(cpyLevel > 0 &&  diffDays > 0){
							//获取当前代理机构已增加的专利个数
							Integer totalNum = cpy.getZlNum();
							if(cpyLevel.equals(1) && totalNum < Constants.ADD_ZL_NUM_YP){
								flag = true;
							}else if(cpyLevel.equals(2) && totalNum < Constants.ADD_ZL_NUM_JP){
								flag = true;
							}else if(cpyLevel.equals(3)){//钻石无限制
								flag = true;
							}else{
								flag = false;
							}
						}else if(cpyLevel.equals(0)){//免费会员
							//免费会员在试用期内部判断
							if(diffDays > 0){
								flag = true;
							}else{
								//获取当月有无增加的专利---免费会员每月有增加专利条数的限制
								Integer currMonthAddNum = 0;//从数据库获取
								if(currMonthAddNum < Constants.MONTH_MAX_ZL_NUM_TP){
									flag = true;
								}
							}
						}else{
							flag = false;
						}
						if(flag){
							zlStatus  = 1;
						}
					}else{//已领取，设置成撤销领取
						//先判断是不是自己领取的
						if(pzList.get(0).getLqCpyId().equals(cpy.getId())){
							zlStatus  = 0;
							flag = true;
						}
					}
				}
			}
			if(flag){
				msg = "success";
				String mailTitle = "";
				String mailCon = "";
				if(this.getLoginType(request).equals("appUser")){//申请人/公司撤销领取状态
					PubZlInfoTb pz = pzm.listSpecInfoByOpt(pubId, this.getLoginUserId(request)).get(0);
					mailTitle = "专利任务撤回通知";
					String zlTitle = pz.getZlTitle();
					//给发布人发送邮件
					mm.addMail("taskM", Constants.SYSTEM_EMAIL_ACCOUNT, lqUserId, "appType", mailTitle, "您已主动撤回专利["+zlTitle+"],之前的代理机构["+pz.getLqCpyName()+"]将不能再进行操作!");
					lqCpyId = pz.getLqCpyId();
					
					//给代理机构管理员发送邮件
					List<CpyUserInfo> cuList = cum.listManagerInfoByOpt(lqCpyId, "管理员");
					mailCon = "由于专利发布人员主动撤回专利["+zlTitle+"]。您无法再对该任务进行编辑，如有疑问，请联系专利发布人员["+pz.getApplyInfoTb().getAppName()+"]!";
					for(Iterator<CpyUserInfo> it = cuList.iterator() ; it.hasNext();){
						CpyUserInfo cUser = it.next();
						mm.addMail("taskM", Constants.SYSTEM_EMAIL_ACCOUNT, cUser.getId(), "cpyUser", mailTitle, mailCon);
					}
					//减少领取公司的领取数量
					cm.updateZlNumById(lqCpyId, -1);
					//需要修改对应的案件终止状态--------------------------------------------
					zlm.updateStopStatusById(ajId, 1, CurrentTime.getCurrentTime(), am.getEntityById(this.getLoginUserId(request)).getAppName(), this.getLoginType(request));
				}else if(this.getLoginType(request).equals("cpyUser")){//代理公司领取/撤销领取状态
					PubZlInfoTb pz = pzm.listSpecInfoByOpt(pubId, 0).get(0);
					lqCpyId = pz.getLqCpyId();
					if(zlStatus.equals(0)){//被代理机构员工撤销
						mailTitle = "专利任务撤回通知";
						mailCon = "由于代理机构人员["+pz.getLqUserName()+"]主动撤回。您无法再对该任务进行编辑!";
						//给发布人/公司发送邮件
						mm.addMail("taskM", Constants.SYSTEM_EMAIL_ACCOUNT, pz.getApplyInfoTb().getId(), "appType", mailTitle, "待机机构["+pz.getLqCpyName()+"]下的员工["+pz.getLqUserName()+"]主动撤回对您发布的专利任务["+pz.getZlTitle()+"]");
						//给代理机构所有管理员发送邮件
						List<CpyUserInfo> cuList = cum.listManagerInfoByOpt(lqCpyId, "管理员");
						for(Iterator<CpyUserInfo> it = cuList.iterator() ; it.hasNext();){
							CpyUserInfo cUser = it.next();
							mm.addMail("taskM", Constants.SYSTEM_EMAIL_ACCOUNT, cUser.getId(), "cpyUser", mailTitle, mailCon);
						}
						//减少领取公司的领取数量
						cm.updateZlNumById(lqCpyId, -1);
						//需要修改对应的案件终止状态---------------------------------------------
						zlm.updateStopStatusById(ajId, 1, "", "", "");
					}else{//被代理机构员工领取
						mailTitle = "专利任务领取通知";
						mailCon = "您发布的专利["+pz.getZlTitle()+"]已被代理机构["+lqCpyName+"]下员工["+lqUserName+"]领取!";
						//给发布人/公司发送邮件
						mm.addMail("taskM", Constants.SYSTEM_EMAIL_ACCOUNT, pz.getApplyInfoTb().getId(), "appType", mailTitle, mailCon);
						//给代理机构所有管理员发送邮件
						List<CpyUserInfo> cuList = cum.listManagerInfoByOpt(lqCpyId, "管理员");
						for(Iterator<CpyUserInfo> it = cuList.iterator() ; it.hasNext();){
							CpyUserInfo cUser = it.next();
							mm.addMail("taskM", Constants.SYSTEM_EMAIL_ACCOUNT, cUser.getId(), "cpyUser", mailTitle, "员工["+pz.getLqUserName()+"]已成功领取["+pz.getZlTitle()+"]专利任务!");
						}
						//增加领取公司的领取数量
						cm.updateZlNumById(lqCpyId, 1);
						//需要修改对应的案件终止状态---------------------------------------------
						zlm.updateStopStatusById(ajId, 0, CurrentTime.getCurrentTime(), lqUserName, this.getLoginType(request));
					}
				}
				//修改领取状态
				pzm.updatePubZlById(pubId, zlStatus, lqUserId, lqUserName, lqCpyId, lqCpyName, lqDate, 0);
			}else{
				msg = "lowLevel";//会员等级不够或者会员已到期
			}
		}

		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 发布专利
	 * @description
	 * @author wm
	 * @date 2018-8-28 下午04:09:57
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPzInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PubZlInfoManager pzm = (PubZlInfoManager) AppFactory.instance(null).getApp(Constants.WEB_PUB_ZL_INFO);
		Map<String,String> map = new HashMap<String,String>();
		String msg = "error";
		if(this.getLoginType(request).equals("appUser")){//只有发布个人、公司用户才能使用
			String zlTitle = Transcode.unescape_new("zlTitle", request);
			String zlContent = Transcode.unescape_new("zlContent", request);
			String zlType = CommonTools.getFinalStr("zlType", request);
			String zlUpCl = Transcode.unescape_new1("zlUpCl", request);
			if(zlType.equals("fm") || zlType.equals("syxx") || zlType.equals("wg")){
				Integer pzId = pzm.addPubZl(this.getLoginUserId(request), zlTitle, zlContent, zlType, zlUpCl, CurrentTime.getStringDate());
				if(pzId > 0){
					//如果存在上传的文件，需要移动
					if(!zlUpCl.equals("")){
						String[] upFileArr = zlUpCl.split(",");
						String newPath =  WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\appUser\\" + this.getLoginUserId(request) + "\\" + pzId;
						File file = new File(newPath);
						if(!file.exists()){
			    			file.mkdirs();
			    		}
						String newPath_db = "";
						String path_pre = upFileArr[0].substring(0,upFileArr[0].lastIndexOf("\\")) + "\\" + pzId + "\\";
						for(Integer i = 0 ; i < upFileArr.length ; i++){
							String oldPath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" +upFileArr[i];
							String fileName = upFileArr[i].substring((upFileArr[i].lastIndexOf("\\") + 1));
							String newPathFinal = newPath + "\\" + fileName;
							newPath_db +=  path_pre + fileName + ",";
							boolean flag = FileOpration.copyFile(oldPath, newPathFinal);
							if(flag){
								//删除之前文件
								flag = FileOpration.deleteFile(oldPath);
							}
						}
						//修改上传附件的真实路径
						if(!newPath_db.equals("")){
							newPath_db = newPath_db.substring(0, newPath_db.length() - 1);
							pzm.updateBasicInfoById(pzId, "", "", "", newPath_db);
						}
					}
				}
				msg = "success";
			}else if(zlType.equals("fmxx")){//发明+新型
				Integer pubId_1 = pzm.addPubZl(this.getLoginUserId(request), zlTitle, zlContent, "fm", zlUpCl, CurrentTime.getCurrentTime());
				Integer pubId_2 = pzm.addPubZl(this.getLoginUserId(request), zlTitle, zlContent, "syxx", zlUpCl, CurrentTime.getCurrentTime());
				if(pubId_1 > 0 && pubId_2 > 0){
					//如果存在上传的文件，需要移动
					if(!zlUpCl.equals("")){
						String[] upFileArr = zlUpCl.split(",");
						String newPath_1 =  WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\appUser\\" + this.getLoginUserId(request) + "\\" + pubId_1;
						String newPath_2 =  WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\appUser\\" + this.getLoginUserId(request) + "\\" + pubId_2;
						File file_1 = new File(newPath_1);
						if(!file_1.exists()){
							file_1.mkdirs();
			    		}
						File file_2 = new File(newPath_2);
						if(!file_2.exists()){
							file_2.mkdirs();
			    		}
						String newPath_db_1 = "",newPath_db_2 = "";
						String path_1_pre = upFileArr[0].substring(0,upFileArr[0].lastIndexOf("\\")) + "\\" + pubId_1 + "\\";
						String path_2_pre = upFileArr[0].substring(0,upFileArr[0].lastIndexOf("\\")) + "\\" + pubId_2 + "\\";
						for(Integer i = 0 ; i < upFileArr.length ; i++){
							String oldPath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" +upFileArr[i];
							String fileName = upFileArr[i].substring((upFileArr[i].lastIndexOf("\\") + 1));
							String newPathFinal_1 = newPath_1 + "\\" + fileName;
							String newPathFinal_2 = newPath_2 + "\\" + fileName;
							newPath_db_1 +=  path_1_pre + fileName + ",";
							newPath_db_2 +=  path_2_pre + fileName + ",";
							boolean flag = FileOpration.copyFile(oldPath, newPathFinal_1);
							if(flag){
								flag = FileOpration.copyFile(oldPath, newPathFinal_2);
								if(flag){
									//删除之前文件
									FileOpration.deleteFile(oldPath);
								}
							}
						}
						//修改上传附件的真实路径
						if(!newPath_db_1.equals("")){
							newPath_db_1 = newPath_db_1.substring(0, newPath_db_1.length() - 1);
							pzm.updateBasicInfoById(pubId_1, "", "", "", newPath_db_1);
						}
						if(!newPath_db_2.equals("")){
							newPath_db_2 = newPath_db_2.substring(0, newPath_db_2.length() - 1);
							pzm.updateBasicInfoById(pubId_2, "", "", "", newPath_db_2);
						}
					}
				}
				msg = "success";
			}
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 专利列表管理界面
	 * @description
	 * @author wm
	 * @date 2018-8-13 上午09:00:29
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goLqZlPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return mapping.findForward("lqzlPage");
	}
	
	/**
	 * 获取当前代理机构已领取的发布专利任务列表
	 * @description
	 * @author wm
	 * @date 2018-8-28 下午05:34:01
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLqPzListData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PubZlInfoManager pzm = (PubZlInfoManager) AppFactory.instance(null).getApp(Constants.WEB_PUB_ZL_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list_d = new ArrayList<Object>();
		String msg = "error";
		String zlNo = "";
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(this.getLoginUserId(request));
			if(cUser != null){
				Integer cpyId = cUser.getCpyInfoTb().getId();
				Integer addStatus = CommonTools.getFinalInteger("addStatus", request);
				String purpose = CommonTools.getFinalStr("purpose", request);
				boolean pageFlag = false;
				Integer pageNo = 0,pageSize = 0;
				Integer count = pzm.getCountByOpt_2(cpyId, addStatus);
				if(purpose.equals("allInfo")){//用于浏览单位全部领取记录用
					pageFlag = true;
					if(count > 0){
						pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("pageSize")), 10);
						Integer pageCount = PageConst.getPageCount(count, pageSize);
						pageNo = PageConst.getPageNo(String.valueOf(request.getParameter("pageNo")), pageCount);
						List<PubZlInfoTb> pzList = pzm.listSpecInfoByOpt_2(cpyId, addStatus, pageFlag, pageNo, pageSize);
						msg = "success";
						for(Iterator<PubZlInfoTb> it = pzList.iterator() ; it.hasNext();){
							PubZlInfoTb pz = it.next();
							Map<String,Object> map_d = new HashMap<String,Object>();
							map_d.put("pzId", pz.getId());
							map_d.put("pzTitle", pz.getZlTitle());
							map_d.put("pzContent", pz.getZlContent());
							map_d.put("pzType", pz.getZlType());
							String zlType = pz.getZlType();
							String zlTypeChi = "";
							if(zlType.equals("fm")){
								zlTypeChi = "发明";
							}else if(zlType.equals("syxx")){
								zlTypeChi = "实用新型";
							}else if(zlType.equals("wg")){
								zlTypeChi = "外观";
							}
							map_d.put("zlTypeChi", zlTypeChi);
							map_d.put("upCl", pz.getZlUpCl());
							map_d.put("addDate", pz.getZlNewDate());
							map_d.put("lqr", pz.getLqUserName());
							map_d.put("lqDate", pz.getLqDate());
							List<ZlajMainInfoTb> zlList = zlm.listSpecInfoById(pz.getAjId(), cpyId);
							if(zlList.size() > 0){
								zlNo = zlList.get(0).getAjNoQt();
							}
							map_d.put("zlNo", zlNo);
							list_d.add(map_d);
						}
						map.put("pzInfo", list_d);
					}else{
						msg = "noInfo";
					}
				}else if(purpose.equals("simpleInfo")){//用于增加专利时显示用
					List<PubZlInfoTb> pzList = pzm.listSpecInfoByOpt_2(cpyId, addStatus, pageFlag, pageNo, pageSize);
					if(pzList.size() > 0){
						msg = "success";
						for(Iterator<PubZlInfoTb> it = pzList.iterator() ; it.hasNext();){
							PubZlInfoTb pz = it.next();
							Map<String,Object> map_d = new HashMap<String,Object>();
							map_d.put("pzId", pz.getId());
							map_d.put("pzTitle", pz.getZlTitle());
							String zlType = pz.getZlType();
							map_d.put("pzType", pz.getZlType());
							String zlTypeChi = "";
							if(zlType.equals("fm")){
								zlTypeChi = "发明";
							}else if(zlType.equals("syxx")){
								zlTypeChi = "实用新型";
							}else if(zlType.equals("wg")){
								zlTypeChi = "外观";
							}
							map_d.put("zlTypeChi", zlTypeChi);
							list_d.add(map_d);
						}
						map.put("pzInfo", list_d);
					}else{
						msg = "noInfo";
					}
				}
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取当前代理机构已领取的发布专利任务列表
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-29 下午09:11:56
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLqPzCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PubZlInfoManager pzm = (PubZlInfoManager) AppFactory.instance(null).getApp(Constants.WEB_PUB_ZL_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Map<String,Integer> map = new HashMap<String,Integer>();
		Integer count = 0;
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(this.getLoginUserId(request));
			if(cUser != null){
				Integer cpyId = cUser.getCpyInfoTb().getId();
				Integer addStatus = CommonTools.getFinalInteger("addStatus", request);
				count = pzm.getCountByOpt_2(cpyId, addStatus);
			}
		}
		map.put("result", count);
		this.getJsonPkg(map, response);
		return null;
	}

}