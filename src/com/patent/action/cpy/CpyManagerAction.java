/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.cpy;

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
import com.patent.module.CpyJoinInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.page.PageConst;
import com.patent.service.ApplyInfoManager;
import com.patent.service.CpyInfoManager;
import com.patent.service.CpyJoinInfoManager;
import com.patent.service.CpyRoleInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.MailInfoManager;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;
import com.patent.tools.MD5;
import com.patent.util.Constants;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 08-06-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class CpyManagerAction extends DispatchAction {
	
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
	 * 导向所有代理机构列表页面
	 * @description
	 * @author wm
	 * @date 2018-8-6 上午09:45:20
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward goCpyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String[] myAbility = Ability.getAbilityInfo("addCpy,upCpy,delCpy", this.getLoginType(request), this.getLoginRoleName(request), this.getLoginRoleId(request)).split(",");
		request.setAttribute("delFlag", myAbility[0]);
		request.setAttribute("upFlag", myAbility[1]);
		request.setAttribute("addFlag", myAbility[2]);
		return mapping.findForward("cpyPage");
	}
	
	/**
	 * 获取代理机构记录列表
	 * @description
	 * @author wm
	 * @date 2018-8-6 上午09:49:10
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getCpyCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO);
		String cpyName = Transcode.unescape(request.getParameter("cpyName"), request);
		String cpyProv = Transcode.unescape(request.getParameter("cpyProv"), request);
		String cpyCity = Transcode.unescape(request.getParameter("cpyCity"), request);
		String cpyLxr = Transcode.unescape(request.getParameter("cpyLxr"), request);
		String cpyFr = Transcode.unescape(request.getParameter("cpyFr"), request);
		Integer cpyLevel = Integer.parseInt(request.getParameter("cpyLevel"));
		Integer yxStatus = Integer.parseInt(request.getParameter("yxStatus"));//有效状态--会员是否过期(-1[全部],0[已过期],1[未过期])
		Integer count = cm.getCountByOpt(cpyName, cpyProv, cpyCity, cpyFr, cpyLxr, cpyLevel, yxStatus);
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("result", count);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 分页获取代理机构列表(会员等级、公司热度降序排列)
	 * @description
	 * @author wm
	 * @date 2018-8-6 上午10:21:48
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCpyPageInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO);
		String cpyName = Transcode.unescape(request.getParameter("cpyName"), request);
		String cpyProv = Transcode.unescape(request.getParameter("cpyProv"), request);
		String cpyCity = Transcode.unescape(request.getParameter("cpyCity"), request);
		String cpyLxr = Transcode.unescape(request.getParameter("cpyLxr"), request);
		String cpyFr = Transcode.unescape(request.getParameter("cpyFr"), request);
		Integer cpyLevel = CommonTools.getFinalInteger(request.getParameter("cpyLevel"));
		Integer yxStatus = CommonTools.getFinalInteger(request.getParameter("yxStatus"));//有效状态--会员是否过期(-1[全部],0[已过期],1[未过期])
		Integer count = cm.getCountByOpt(cpyName, cpyProv, cpyCity, cpyFr, cpyLxr, cpyLevel, yxStatus);
		Map<String,Object> map = new HashMap<String,Object>();
		if(count > 0){
			Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("limit")), 10);//等同于pageSize
			Integer pageNo = CommonTools.getFinalInteger(request.getParameter("page"));//等同于pageNo
			List<CpyInfoTb> cpyList = cm.listPageInfoByOpt(cpyName, cpyProv, cpyCity, cpyFr, cpyLxr, cpyLevel, yxStatus, pageNo, pageSize);
			List<Object> list_d = new ArrayList<Object>();
			for(Iterator<CpyInfoTb> it = cpyList.iterator() ; it.hasNext();){
				CpyInfoTb cpy = it.next();
				Map<String,Object> map_d = new HashMap<String,Object>();
				map_d.put("id", cpy.getId());
				map_d.put("cpyName", cpy.getCpyName());
				map_d.put("cpyNamePy", cpy.getCpyNamePy());
				map_d.put("cpyAddress", cpy.getCpyAddress());
				map_d.put("cpyProv", cpy.getCpyProv());
				map_d.put("cpyCity", cpy.getCpyCity());
				map_d.put("cpyFr", cpy.getCpyFr());
				map_d.put("cpyYyzz", cpy.getCpyYyzz());
				map_d.put("cpyLxr", cpy.getCpyLxr());
				map_d.put("lxrTel", cpy.getLxrTel());
				map_d.put("lxrEmail", cpy.getLxrEmail());
				map_d.put("cpyUrl", cpy.getCpyUrl());
				map_d.put("cpyProfile", cpy.getCpyProfile());
				map_d.put("signDate", cpy.getSignDate());
				map_d.put("endDate", cpy.getEndDate());
				map_d.put("hotStatus", cpy.getHotStatus());
				map_d.put("levelNum", cpy.getCpyLevel());
				if(cpy.getCpyLevel().equals(0)){
					map_d.put("levelChi", "铜牌");
				}else if(cpy.getCpyLevel().equals(1)){
					map_d.put("levelChi", "银牌");
				}else if(cpy.getCpyLevel().equals(2)){
					map_d.put("levelChi", "金牌");
				}else if(cpy.getCpyLevel().equals(3)){
					map_d.put("levelChi", "钻石牌");
				}
				list_d.add(map_d);
			}
			map.put("msg", "success");
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
	 * 修改代理机构到期时间、公司会员等级(超级管理员手动修改,代理机构管理员在购买的时候成功后自动修改)
	 * @description
	 * @author wm
	 * @date 2018-8-7 下午04:18:08
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCpyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO);
//		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		String msg = "";
		String endDate = CommonTools.getFinalStr("enDate", request);
		Integer hotStatus = CommonTools.getFinalInteger(request.getParameter("hotStatus"));
		Integer cpyLevel = Integer.parseInt(request.getParameter("cpyLevel"));
		Integer cpyId = 0;
		if(this.getLoginRoleName(request).equals("super")){//平台管理员--可以修改任何代理机构
			cpyId = Integer.parseInt(request.getParameter("cpyId"));
			boolean flag = cm.updateCpyInfoById(cpyId, endDate, hotStatus, cpyLevel);
			if(flag){
				msg = "success";
			}else{
				msg = "error";
			}
//		}else if(this.getLoginRoleName(request).equals("管理员")){//代理机构管理员只能通过购买会员的形式修改到期时间、公司会员等级
			//暂时去掉管理员修改代理机构期限、会员等级
//			CpyUserInfo cUser = cum.getEntityById(this.getLoginUserId(request));
//			if(cUser != null){
//				cpyId = cUser.getCpyInfoTb().getId();
//				boolean flag = cm.updateCpyInfoById(cpyId, endDate, hotStatus, cpyLevel);
//				if(flag){
//					msg = "success";
//				}else{
//					msg = "error";
//				}
//			}else{
//				msg = "fail";
//			}
		}else{
			msg = "noAbility";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", msg);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 获取代理机构详细信息(超管、申请人/公司用)
	 * @description
	 * @author wm
	 * @date 2018-8-25 下午03:27:07
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCpyDetailData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO); 
		Map<String,Object> map = new HashMap<String,Object>();
		if(this.getLoginRoleName(request).equals("super") || this.getLoginType(request).equals("appUser")){//只有超管和申请人/公司才能获取
			Integer cpyId = CommonTools.getFinalInteger(request.getParameter("cpyId"));
			List<CpyInfoTb> cpyList = cm.listInfoById(cpyId);
			if(cpyList.size() > 0){
				CpyInfoTb cpy = cpyList.get(0);
				map.put("result", "success");
				map.put("cpyId", cpy.getId());
				map.put("cpyName", cpy.getCpyName());
				map.put("cpyProv", cpy.getCpyProv());
				map.put("cpyCity", cpy.getCpyCity());
				map.put("cpyAddress", cpy.getCpyAddress());
				map.put("cpyFr", cpy.getCpyFr());
				map.put("cpyYyzz", cpy.getCpyYyzz());
				map.put("cpyLxr", cpy.getCpyLxr());
				map.put("lxrTel", cpy.getLxrTel());
				map.put("lxrEmail", cpy.getLxrEmail());
				map.put("cpyUrl", cpy.getCpyUrl());
				map.put("cpyProfile", cpy.getCpyProfile());
				map.put("signDate", cpy.getSignDate());
				map.put("hotStatus", cpy.getHotStatus());
				String cpySubIdStr = cpy.getCpySubId();
				Integer cpyParId = cpy.getCpyParId();
				String cpySubName = "";
				String cpyParName = "";
				if(!cpySubIdStr.equals("")){
					String[] cpySubIdArr = cpySubIdStr.split(",");
					for(Integer i = 0 ; i < cpySubIdArr.length ; i++){
						List<CpyInfoTb> subList = cm.listInfoById(Integer.parseInt(cpySubIdArr[i]));
						if(subList.size() > 0){
							cpySubName += subList.get(0).getCpyName() + ",";
						}
					}
					if(!cpySubName.equals("")){
						cpySubName = cpySubName.substring(0, cpySubName.length() - 1);
					}
				}else{
					cpySubName = "";
				}
				if(cpyParId > 0){
					List<CpyInfoTb> parList = cm.listInfoById(cpyParId);
					if(parList.size() > 0){
						cpyParName = parList.get(0).getCpyName();
					}
				}
				map.put("cpySubInfo", cpySubName);
				map.put("cpyParInfo", cpyParName);
				Integer cpyLevel = cpy.getCpyLevel();
				if(cpyLevel > 0){//收费会员
					String endDate = cpy.getEndDate();
					if(CurrentTime.compareDate(CurrentTime.getStringDate(), endDate) > 0){
						map.put("endFlag", false);//未到期
					}else{
						map.put("endFlag", true);//已到期
					}
					map.put("endDate", endDate);
				}
				String cpyLevelChi = "铜牌";
				if(cpyLevel.equals(0)){
					cpyLevelChi = "铜牌";
				}else if(cpyLevel.equals(1)){
					cpyLevelChi = "银牌";
				}else if(cpyLevel.equals(2)){
					cpyLevelChi = "金牌";
				}else if(cpyLevel.equals(3)){
					cpyLevelChi = "钻石";
				}
				map.put("cpyLevel", cpyLevelChi);
			}else{
				map.put("result", "noInfo");
			}
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 导向代理机构信息修改页面
	 * @description
	 * @author wm
	 * @date 2018-8-4 下午04:27:59
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goCpyDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean abilityFlag = false;//修改权限
		if(this.getLoginType(request).equals("cpyUser")){
			if(this.getLoginRoleName(request).equals("管理员")){
				abilityFlag = true;
			}else{
				//获取当前用户是否有修改权限
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upCpy");
			}
		}else{
			abilityFlag = false;
		}
		request.setAttribute("abilityFlag", abilityFlag);
		return mapping.findForward("cpyDetailPage");
	}
	
	/**
	 * 获取用户所在的代理机构信息
	 * @description
	 * @author wm
	 * @date 2018-8-2 上午08:37:49
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCpyDetailInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Integer userId = this.getLoginUserId(request);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyUserInfo cpyUser = cum.getEntityById(userId);
		Map<String,Object> map = new HashMap<String,Object>();
		if(cpyUser != null){
			CpyInfoTb cpy = cpyUser.getCpyInfoTb();
			map.put("result", "success");
			map.put("cpyId", cpy.getId());
			map.put("cpyName", cpy.getCpyName());
			map.put("cpyProv", cpy.getCpyProv());
			map.put("cpyCity", cpy.getCpyCity());
			map.put("cpyAddress", cpy.getCpyAddress());
			map.put("cpyFr", cpy.getCpyFr());
			map.put("cpyYyzz", cpy.getCpyYyzz());
			map.put("cpyLxr", cpy.getCpyLxr());
			map.put("lxrTel", cpy.getLxrTel());
			map.put("lxrEmail", cpy.getLxrEmail());
			map.put("cpyUrl", cpy.getCpyUrl());
			map.put("cpyProfile", cpy.getCpyProfile());
			map.put("signDate", cpy.getSignDate());
			map.put("hotStatus", cpy.getHotStatus());
			Integer cpyLevel = cpy.getCpyLevel();
			if(cpyLevel > 0){//收费会员
				String endDate = cpy.getEndDate();
				if(CurrentTime.compareDate(CurrentTime.getStringDate(), endDate) > 0){
					map.put("endFlag", false);//未到期
				}else{
					map.put("endFlag", true);
				}
				map.put("endDate", endDate);
			}
			String cpyLevelChi = "铜牌";
			if(cpyLevel.equals(0)){
				cpyLevelChi = "铜牌";
			}else if(cpyLevel.equals(1)){
				cpyLevelChi = "银牌";
			}else if(cpyLevel.equals(2)){
				cpyLevelChi = "金牌";
			}else if(cpyLevel.equals(3)){
				cpyLevelChi = "钻石";
			}
			map.put("cpyLevel", cpyLevelChi);
		}else{
			map.put("result", "error");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 修改代理机构基本信息
	 * @description
	 * @author wm
	 * @date 2018-8-2 上午09:02:58
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCpyBasicInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO); 
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		boolean abilityFlag = false;
		if(this.getLoginType(request).equals("cpyUser")){
			if(this.getLoginRoleName(request).equals("管理员")){
				abilityFlag = true;
			}else{
				//获取当前用户是否有修改权限
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upCpy");
			}
		}else{
			abilityFlag = false;
		}
		if(abilityFlag){
			CpyUserInfo cpyUser = cum.getEntityById(this.getLoginUserId(request));
			if(cpyUser != null){
				Integer cpyId = cpyUser.getCpyInfoTb().getId();
				if(cpyId > 0){
					String cpyName = Transcode.unescape(request.getParameter("cpyName"), request);
					String cpyProv = Transcode.unescape(request.getParameter("cpyProv"), request);
					String cpyCity = Transcode.unescape(request.getParameter("cpyCity"), request);
					String cpyAddress = Transcode.unescape(request.getParameter("cpyAddress"), request);
					String cpyFr = Transcode.unescape(request.getParameter("cpyFr"), request);
					String cpyYyzz = Transcode.unescape(request.getParameter("cpyYyzz"), request);
					String cpyLxr = Transcode.unescape(request.getParameter("cpyLxr"), request);
					String lxrTel = Transcode.unescape(request.getParameter("lxrTel"), request);
					String lxrEmail = Transcode.unescape(request.getParameter("lxrEmail"), request);
					String cpyUrl = Transcode.unescape(request.getParameter("cpyUrl"), request);
					String cpyProfile = Transcode.unescape(request.getParameter("cpyProfile"), request);
					boolean flag = cm.updateBasicCpyInfoById(cpyId, cpyName,cpyAddress, cpyProv, cpyCity, cpyFr, cpyLxr, lxrTel, lxrEmail, cpyYyzz,cpyUrl, cpyProfile);
					if(flag){
						msg = "success";
					}else{
						msg = "error";
					}
				}
			}
		}else{
			msg = "noAbility";//没有权限
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
	 * 导向子/主公司列表页面
	 * @description
	 * @author wm
	 * @date 2018-8-14 上午11:39:01
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goSubParCpyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] myAbility = Ability.getAbilityInfo("addCpy,upCpy,delCpy", this.getLoginType(request), this.getLoginRoleName(request), this.getLoginRoleId(request)).split(",");
		request.setAttribute("delFlag", myAbility[0]);
		request.setAttribute("upFlag", myAbility[1]);
		request.setAttribute("addFlag", myAbility[2]);
		return mapping.findForward("subCpyPage");
	}
	
	/**
	 * 获取子/主公司信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-14 下午05:39:24
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubParCpyData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO); 
		CpyInfoTb cpy = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb();
		Integer parId = cpy.getCpyParId();
		String subIdStr = cpy.getCpySubId();
		List<CpyInfoTb> cpyList = new ArrayList<CpyInfoTb>();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list_d = new ArrayList<Object>();
		if(parId.equals(0)){//没有主公司
			if(!subIdStr.equals("")){//存在子公司
				cpyList = cm.listParSubCpyInfo(subIdStr, "sub");
				map.put("result", "existInfo");
				map.put("psInfo", "sub");
			}else{
				map.put("result", "noInfo");
			}
		}else{//存在主公司-说明自己是子公司
			cpyList = cm.listParSubCpyInfo(parId+"", "par");
			map.put("result", "existInfo");
			map.put("psInfo", "par");
		}
		for(Iterator<CpyInfoTb> it = cpyList.iterator() ; it.hasNext();){
			CpyInfoTb cpy_t = it.next();
			Map<String,Object> map_d = new HashMap<String,Object>();
			map_d.put("cpyId", cpy_t.getId());
			map_d.put("cpyName", cpy_t.getCpyName());
			map_d.put("cpyProv", cpy_t.getCpyProv());
			map_d.put("cpyCity", cpy_t.getCpyCity());
			map_d.put("cpyAddress", cpy_t.getCpyAddress());
			map_d.put("cpyFr", cpy_t.getCpyFr());
			map_d.put("cpyYyzz", cpy_t.getCpyYyzz());
			map_d.put("cpyLxr", cpy_t.getCpyLxr());
			map_d.put("lxrTel", cpy_t.getLxrTel());
			map_d.put("lxrEmail", cpy_t.getLxrEmail());
			map_d.put("cpyUrl", cpy_t.getCpyUrl());
			map_d.put("cpyProfile", cpy_t.getCpyProfile());
			map_d.put("signDate", cpy_t.getSignDate());
			map_d.put("endDate", cpy_t.getEndDate());
			map_d.put("hotStatus", cpy_t.getHotStatus());
			Integer cpyLevel = cpy_t.getCpyLevel();
			String cpyLevelChi = "铜牌";
			if(cpyLevel.equals(0)){
				cpyLevelChi = "铜牌";
			}else if(cpyLevel.equals(1)){
				cpyLevelChi = "银牌";
			}else if(cpyLevel.equals(2)){
				cpyLevelChi = "金牌";
			}else if(cpyLevel.equals(3)){
				cpyLevelChi = "钻石";
			}
			map_d.put("cpyLevel", cpyLevelChi);
			list_d.add(map_d);
		}
		map.put("cpyInfo", list_d);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 获取子公司详情
	 * @description
	 * @author wm
	 * @date 2018-8-28 上午11:01:43
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubCpyDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO); 
		CpyInfoTb cpy = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb();
		Integer parId = cpy.getCpyParId();
		String msg = "error";
		Map<String,Object> map = new HashMap<String,Object>();
		if(parId.equals(0)){
			Integer cpySubId = CommonTools.getFinalInteger("cpySubId", request);
			List<CpyInfoTb> cpyList = cm.listInfoById(cpySubId);
			if(cpyList.size() > 0){
				CpyInfoTb cpy_sub = cpyList.get(0);
				if(cpy_sub.getCpyParId().equals(cpy.getId())){
					msg = "success";
					map.put("cpyId", cpy_sub.getId());
					map.put("cpyName", cpy_sub.getCpyName());
					map.put("cpyProv", cpy_sub.getCpyProv());
					map.put("cpyCity", cpy_sub.getCpyCity());
					map.put("cpyAddress", cpy_sub.getCpyAddress());
					map.put("cpyFr", cpy_sub.getCpyFr());
					map.put("cpyYyzz", cpy_sub.getCpyYyzz());
					map.put("cpyLxr", cpy_sub.getCpyLxr());
					map.put("lxrTel", cpy_sub.getLxrTel());
					map.put("lxrEmail", cpy_sub.getLxrEmail());
					map.put("cpyUrl", cpy_sub.getCpyUrl());
					map.put("cpyProfile", cpy_sub.getCpyProfile());
					map.put("signDate", cpy_sub.getSignDate());
					map.put("hotStatus", cpy_sub.getHotStatus());
					Integer cpyLevel = cpy_sub.getCpyLevel();
					if(cpyLevel > 0){
						map.put("endDate", cpy_sub.getEndDate());
					}else{
						map.put("endDate", "");
					}
					String cpyLevelChi = "铜牌";
					if(cpyLevel.equals(0)){
						cpyLevelChi = "铜牌";
					}else if(cpyLevel.equals(1)){
						cpyLevelChi = "银牌";
					}else if(cpyLevel.equals(2)){
						cpyLevelChi = "金牌";
					}else if(cpyLevel.equals(3)){
						cpyLevelChi = "钻石";
					}
					map.put("cpyLevel", cpyLevelChi);
				}
			}else{
				msg = "noInfo";
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
	
	
	/**
	 * 申请合并记录（暂时取消该功能）
	 * @description
	 * @author wm
	 * @date 2018-8-16 下午05:21:00
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward applyJoinInfo(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
//		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO); 
//		CpyJoinInfoManager cjm = (CpyJoinInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_JOIN_INFO);
//		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
//		boolean abilityFlag = false;
//		Map<String,String> map = new HashMap<String,String>();
//		String msg = "";
//		boolean flag = false;
//		if(this.getLoginType(request).equals("cpyUser")){
//			if(this.getLoginRoleName(request).equals("管理员")){
//				abilityFlag = true;
//			}else{
//				//获取当前用户是否有修改权限
//				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "bindCpy");
//			}
//		}else{
//			abilityFlag = false;
//		}
//		if(abilityFlag){
////			1:申请让代理公司B成为当前用户所在公司的子公司（发起申请时需要查看当前公司的子公司余额以及子公司没有主公司）
//			CpyInfoTb cpy = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb();
//			Integer cpyLevel  = cpy.getCpyLevel();
//			Integer currCpyId = cpy.getId();
//			String currCpyName = cpy.getCpyName();
//			Integer selCpyId = CommonTools.getFinalInteger(request.getParameter("selCpyId"));
//			String applyOpt = request.getParameter("applyOpt");//sub(申请成为子公司),par(申请成为主公司)
//			String subCpyName = "";
//			if(applyOpt.equals("par")){
//				//获取当前公司的是否是别人的子公司
//				if(cpy.getCpyParId().equals(0)){
//					if(cpyLevel.equals(0)){
//						msg = "noApply";//免费会员不能发出让别人成为自己的子公司信息
//					}else{
//						//获取是否到期
//						String endDate = cpy.getEndDate();
//						if(CurrentTime.compareDate(CurrentTime.getStringDate(), endDate) > 0){//未过期
//							//获取自己名下的子公司记录
//							String selfSubInfo = cpy.getCpySubId();
//							//获取子公司是否成为别人的子公司
//							List<CpyInfoTb> subCpyList = cm.listInfoById(selCpyId);
//							if(subCpyList.size() > 0){
//								CpyInfoTb subCpy = subCpyList.get(0);
//								subCpyName = subCpy.getCpyName();
//								if(subCpy.getCpyParId().equals(0) && subCpy.getCpySubId().equals("")){
//									msg = "error";
//								}else{
//									if(selfSubInfo.equals("")){
//										flag = true;
//									}else{
//										Integer subCpyCount = selfSubInfo.split(",").length;
//										if(cpyLevel.equals(1)){
//											flag = true;
//										}else if(cpyLevel.equals(2) && Constants.SUB_CPY_NUM_JP > subCpyCount){
//											flag = true;
//										}else if(cpyLevel.equals(3) && Constants.SUB_CPY_NUM_ZS > subCpyCount){
//											flag = true;
//										}else{
//											msg = "noNum";//子公司余额不足
//										}
//									}
//									if(flag){
//										//发送申请记录
//										Integer keyId = cjm.addCJ(currCpyId, currCpyId, selCpyId, currCpyName, subCpyName, 0, "代理机构["+currCpyName+"]发起对代理机构["+subCpyName+"]成为代理机构["+currCpyName+"]的子公司申请", CurrentTime.getStringDate());
//										if(keyId > 0){
//											//向子公司发送邮件通知
//											List<CpyUserInfo> cuList = cum.listManagerInfoByOpt(selCpyId, "管理员");//子公司的管理员列表
//											//向申请公司管理员发送邮件通知
//											for(Iterator<CpyUserInfo> it = cuList.iterator() ; it.hasNext();){
//												CpyUserInfo cUser = it.next();
//												mm.addMail("joinM", Constants.SYSTEM_EMAIL_ACCOUNT, cUser.getId(), "cpyUser", "申请通过", "代理机构["+currCpyName+"]发起对贵公司成为代理机构["+currCpyName+"]的子公司申请");
//											}
//											msg = "success";
//										}
//									}
//								}
//							}else{
//								msg = "error";
//							}
//							
//						}else{
//							msg = "outDate";//会员已过期，不能进行发送申请别公司成为自己的子公司信息
//						}
//					}
//				}else{
//					msg = "currSubCpy";//当前公司时别公司的子公司，不能再申请子公司
//				}
//			}else{//申请成为子公司
//				List<CpyInfoTb> parCpyList = cm.listInfoById(selCpyId);
//				if(parCpyList.size() > 0){
//					cpy = parCpyList.get(0);
//					cpyLevel = cpy.getCpyLevel();
//					currCpyId = cpy.getId();
//					currCpyName = cpy.getCpyName();
//					
//					//子公司信息
//					CpyInfoTb subCpyInfo = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb();
//					Integer subCpyId = subCpyInfo.getId();
//					subCpyName = subCpyInfo.getCpyName();
//					
//					
//				}
//			}
//		}else{
//			msg = "noAbility";
//		}
//		return null;
//	}
	
	/**
	 * 添加子公司（手动增加、通过申请合并记录增加）
	 * @description
	 * @author wm
	 * @date 2018-8-14 上午11:34:37
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSubCpyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO); 
		ApplyInfoManager am = (ApplyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		boolean abilityFlag = false;
		if(this.getLoginType(request).equals("cpyUser")){
			if(this.getLoginRoleName(request).equals("管理员")){
				abilityFlag = true;
			}else{
				//获取当前用户是否有修改权限
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addCpy");
			}
		}else{
			abilityFlag = false;
		}
		if(abilityFlag){
			CpyInfoTb cpy = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb();
			Integer cpyParId = cpy.getId();
			Integer cpyJoinId = CommonTools.getFinalInteger(request.getParameter("cpyJoinId"));//合并信息主键编号
			if(cpyJoinId.equals(0)){//2：通过手动添加
				String comName = Transcode.unescape(request.getParameter("name"), request);//子公司名字
				String comAddress = "";//公司地址
				String comProv = Transcode.unescape(request.getParameter("prov"), request);//子公司所在省份
				String comCity = Transcode.unescape(request.getParameter("city"), request);//子公司所在城市
				String comLxr = Transcode.unescape(request.getParameter("lxr"), request);//子公司联系人
				String email = request.getParameter("email");//个人邮箱--用于找回密码
				String comTel = Transcode.unescape(request.getParameter("tel"), request);//子公司联系电话
				
				String account = request.getParameter("account");
				String password = request.getParameter("password");
				boolean flag = false;
				//先检查主公司是否是高级会员
				if(cpy.getCpyLevel() > 0){
					Integer diffDays = CurrentTime.compareDate(CurrentTime.getStringDate(), cpy.getEndDate());
					if(diffDays > 0){//判断是否到期
						//检查账号不能重复(两张表中账号不能相同)
						if(cum.listSpecInfoByAccount(account).size() > 0 || am.listInfoByAccount(account).size() > 0){
							msg = "exist";
						}else{
							//获取主代理机构下目前拥有的子公司个数
							if(cpy.getCpySubId().equals("")){//没有子公司
								flag = true;
							}else{//存在有子公司
								Integer subCpyLen = cpy.getCpySubId().split(",").length;
								if(cpy.getCpyLevel().equals(1)){//银牌
									flag = false;
								}else if(cpy.getCpyLevel().equals(2)){//金牌
									if(subCpyLen < Constants.SUB_CPY_NUM_JP){
										flag = true;
									}else{
										flag = false;
									}
								}else if(cpy.getCpyLevel().equals(3)){//钻石
									if(subCpyLen < Constants.SUB_CPY_NUM_ZS){
										flag = true;
									}else{
										flag = false;
									}
								}
							}
							if(flag){
								Integer cpyId = cm.addCpy(comName, comAddress, comProv, comCity, cpy.getCpyFr(), cpy.getCpyYyzz(), comLxr, comTel, "", 
										"", cpyParId, cpy.getCpyUrl(), cpy.getCpyProfile(), cpy.getSignDate(), cpy.getEndDate(), 
										0, 0);
								if(cpyId > 0){
									//自动为每个代理机构初始一个管理员身份
									Integer roleId = crm.addRole("管理员", "管理机构基本信息", cpyId);
									//增加代理机构管理员
									Integer cpyUserId = cum.addCpyUser(cpyId, "", "", account, new MD5().calcMD5(password), "m", 
											email, "", CurrentTime.getStringDate(), "", "");
									//增加身份绑定
									Integer ruId = crm.addRoleUser(roleId, cpyUserId);
									if(ruId > 0){
										msg = "success";//成功
										//修改主公司的子公司信息
										cm.updateJoinInfoById(cpyParId, 0, cpyId);
									}else{
										msg = "fail";//失败
									}
								}else{
									msg = "fail";//失败
								}
							}else{
								msg = "outNum";//超过当前会员最大子公司数量
							}
						}
					}else{
						msg = "outDate";
					}
				}else{
					msg = "lowerLevel";
				}
				
			}else{//1：通过分/主公司合并信息进行添加
//				Integer joinStatus = CommonTools.getFinalInteger(request.getParameter("joinStatus"));//合并状态
//				//查询此编号是否在未完成的状态下，并且主公司是否是当前公司
//				List<CpyJoinInfoTb> cjList = cjm.listInfoById(cpyJoinId);
//				boolean flag = false;
//				if(cjList.size() > 0){
//					CpyJoinInfoTb cj = cjList.get(0);
//					Integer subCpyId = cj.getSubCpyId();
//					if(cj.getParCpyId().equals(cpyParId) && cj.getJoinStatus().equals(0)){//必须自己是主公司，并且还没处理
//						List<CpyInfoTb> cpyList = cm.listInfoById(subCpyId);
//						if(cpyList.size() > 0){
//							if(cpyList.get(0).getCpyParId().equals(0)){//说明当前公司还没有主公司
//								String subCpyName = cpyList.get(0).getCpyName();
//								String czContent = "";
//								List<CpyUserInfo> cuList = cum.listManagerInfoByOpt(subCpyId, "管理员");//子公司的管理员列表
//								if(joinStatus.equals(1)){
//									czContent = "主公司["+parCpyName+"]已经同意子公司["+subCpyName+"]的申请";
//									//向申请公司管理员发送邮件通知
//									for(Iterator<CpyUserInfo> it = cuList.iterator() ; it.hasNext();){
//										CpyUserInfo cUser = it.next();
//										mm.addMail("joinM", Constants.SYSTEM_EMAIL_ACCOUNT, cUser.getId(), "cpyUser", "申请通过", "您的成为["+parCpyName+"]的子公司申请已被通过");
//									}
//									flag = true;
//								}else if(joinStatus.equals(2)){
//									czContent = "主公司["+parCpyName+"]已经拒绝子公司["+subCpyName+"]的申请";
//									//向申请公司管理员发送邮件通知
//									for(Iterator<CpyUserInfo> it = cuList.iterator() ; it.hasNext();){
//										CpyUserInfo cUser = it.next();
//										mm.addMail("joinM", Constants.SYSTEM_EMAIL_ACCOUNT, cUser.getId(), "cpyUser", "申请通过", "您的成为["+parCpyName+"]的子公司申请已被拒绝");
//									}
//									flag = true;
//								}else{
//									msg = "error";
//								}
//								if(flag){
//									cjm.updateCJById(cpyJoinId, joinStatus, CurrentTime.getStringDate(), czContent);
//									msg = "success";
//								}
//							}else{
//								msg = "bind";//当前子公司已成为别的代理机构的子公司
//							}
//						}else{
//							msg = "error";
//						}
//					}else{
//						msg = "error";
//					}
//				}else{
//					msg = "noBindInfo";//无此合并申请记录
//				}
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
	 * 解除主\子公司关系（暂时取消该功能）
	 * @description
	 * @author wm
	 * @date 2018-8-15 上午09:47:08
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward unBindCpyInfo(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
//		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO); 
//		ApplyInfoManager am = (ApplyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
//		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
//		Map<String,String> map = new HashMap<String,String>();
//		String msg = "";
//		boolean abilityFlag = false;
//		if(this.getLoginType(request).equals("cpyUser")){
//			if(this.getLoginRoleName(request).equals("管理员")){
//				abilityFlag = true;
//			}else{
//				//获取当前用户是否有修改权限
//				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "delCpy");//解除子公司
//			}
//		}else{
//			abilityFlag = false;
//		}
//		if(abilityFlag){
//			
//		}else{
//			msg = "noAbility";
//		}
//		return null;
//	}
	
	/**
	 * 获取当前用户所在代理机构的会员状态
	 * @author Administrator
	 * @date 2018-8-16 下午11:07:59
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCpyHyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		boolean hyEndFlag = true;//已到期
		Map<String,Object> map = new HashMap<String,Object>();
		if(this.getLoginType(request).equals("cpyUser")){
			CpyInfoTb cpy = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb();
			Integer cpyLevel = cpy.getCpyLevel();
			map.put("cpyLevel", cpyLevel);
			String cpyEndDate = cpy.getEndDate();
			Integer diffDays = CurrentTime.compareDate(CurrentTime.getStringDate(), cpyEndDate);
			if(diffDays > 0){
				hyEndFlag = false;
			}else{
				hyEndFlag = true;
			}
		}else{
			map.put("cpyLevel", 0);
			hyEndFlag = false;
		}
		map.put("hyEndFlag", hyEndFlag);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
}