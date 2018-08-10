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
import com.patent.module.CpyUserInfo;
import com.patent.page.PageConst;
import com.patent.service.CpyInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;
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
	 * 导向所有代理机构列表页面
	 * @description
	 * @author wm
	 * @date 2018-8-6 上午09:45:20
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goCpyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
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
		Integer cpyLevel = Integer.parseInt(request.getParameter("cpyLevel"));
		Integer yxStatus = Integer.parseInt(request.getParameter("yxStatus"));//有效状态--会员是否过期(-1[全部],0[已过期],1[未过期])
		Integer count = cm.getCountByOpt(cpyName, cpyProv, cpyCity, cpyFr, cpyLxr, cpyLevel, yxStatus);
		Map<String,Object> map = new HashMap<String,Object>();
		if(count > 0){
			Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("pageSize")), 10);
			Integer pageCount = PageConst.getPageCount(count, pageSize);
			Integer pageNo = PageConst.getPageNo(String.valueOf(request.getParameter("pageNo")), pageCount);
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
				map_d.put("cpySubIdStr", cpy.getCpySubId());
				map_d.put("cpyParIdStr", cpy.getCpyParId());
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
			map.put("result", "success");
			map.put("cpyInfo", list_d);
		}else{
			map.put("result", "noInfo");
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
		Date endDate = null;
		String msg = "";
		String endDateStr = request.getParameter("enDate");
		if(!endDateStr.equals("")){
			endDate = CurrentTime.stringToDate_1(endDateStr);
		}
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
		}else if(this.getLoginRoleName(request).equals("管理员")){//代理机构管理员只能通过购买会员的形式修改到期时间、公司会员等级
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
		boolean abilityFlag = false;
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//获取当前用户是否有修改权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upCpy");
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
			map.put("signDate", CurrentTime.dateConvertToString(cpy.getSignDate()));
			map.put("endDate", CurrentTime.dateConvertToString(cpy.getEndDate()));
			map.put("hotStatus", cpy.getHotStatus());
			Integer cpyLevel = cpy.getCpyLevel();
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
	public ActionForward updateCpylInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO); 
		Map<String,String> map = new HashMap<String,String>();
		String roleName = this.getLoginRoleName(request);
		String msg = "";
		boolean abilityFlag = false;
		if(roleName.equals("管理员")){//只有管理员才能修改
			abilityFlag = true;
		}else{
			//获取当前用户是否有修改权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upCpy");
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
}