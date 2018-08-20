/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.pubZl.PubZl;

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
import com.patent.page.PageConst;
import com.patent.service.CpyInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.MailInfoManager;
import com.patent.service.PubZlInfoManager;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;
import com.patent.util.Constants;

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
				map_d.put("pubDate", CurrentTime.dateConvertToString(pz.getZlNewDate()));
				map_d.put("lqrName", pz.getLqUserName());
				map_d.put("lqrCpyName", pz.getLqCpyName());
				if(pz.getLqUserId() > 0){
					map_d.put("lqDate", CurrentTime.dateConvertToString(pz.getLqDate()));
				}else{
					map_d.put("lqDate", "");
				}
				map_d.put("pubInfo", pz.getApplyInfoTb().getAppName());
//				map_d.put("ajIdStr", pz.getAjIdStr());
				list_d.add(map_d);
			}
			map.put("data", list_d);
			map.put("count", count);
			map.put("code", 0);
		}else{
			map.put("msg", "noInfo");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
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
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
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
				if(pubZlList.get(0).getZlStatus().equals(0)){
					String zlTitle = Transcode.unescape(request.getParameter("zlTitle"), request);
					String zlContent =  Transcode.unescape(request.getParameter("zlContent"), request);
					String zlType = request.getParameter("zlType");
					String zlUpCl = request.getParameter("zlUpCl");
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
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
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
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO);
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		Integer pubId = CommonTools.getFinalInteger(request.getParameter("pubId"));
		Integer zlStatus = 0;
		Integer lqUserId = this.getLoginUserId(request);;
		String lqUserName = "";
		Integer lqCpyId = 0;
		String lqCpyName = "";
		Date lqDate = null;
		String ajIdStr = "";
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
				lqDate = CurrentTime.stringToDate_1(CurrentTime.getStringDate());
				ajIdStr = request.getParameter("ajIdStr");
				
				pzList = pzm.listSpecInfoByOpt(pubId, 0);
				if(pzList.size() > 0){//存在信息&& pzList.get(0).getZlStatus().equals(zlStatus)
					if(pzList.get(0).getZlStatus().equals(0)){//未领取，设置为成领取
						Integer diffDays = CurrentTime.compareDate(CurrentTime.getStringDate(), CurrentTime.dateConvertToString(cpy.getEndDate()));
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
				flag = pzm.updatePubZlById(pubId, zlStatus, lqUserId, lqUserName, lqCpyId, lqCpyName, lqDate, ajIdStr);
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
						}
					}
					
				}else{
					msg = "error";
				}
			}else{
				msg = "lowLevel";//会员等级不够或者会员已到期
			}
		}

		map.put("result", msg);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
}