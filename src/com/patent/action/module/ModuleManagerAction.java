/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.module;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.patent.module.ActRoleInfoTb;
import com.patent.module.CpyInfoTb;
import com.patent.module.CpyRoleInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.module.ModActInfoTb;
import com.patent.module.ModuleInfoTb;
import com.patent.service.ActRoleInfoManager;
import com.patent.service.CpyRoleInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.ModActInfoManager;
import com.patent.service.ModuleInfoManager;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;
import com.patent.util.Constants;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 08-07-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class ModuleManagerAction extends DispatchAction {
	
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
	 * 导向模块管理界面
	 * @description
	 * @author wm
	 * @date 2018-8-7 下午05:02:19
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goModulePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return mapping.findForward("modulePage");
	}
	
	/**
	 * 获取平台所有模块(超级管理员)，当是代理机构管理员时，获取同等级的平台及其以下模块并需要传递selRoleId参数
	 * @description
	 * @author wm
	 * @date 2018-8-7 下午05:03:31
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getModuleDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModuleInfoManager mm = (ModuleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MODULE_INFO);
		ModActInfoManager mam = (ModActInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MOD_ACT_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		ActRoleInfoManager arm = (ActRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ACT_ROLE_INFO);
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		String loginRoleName = this.getLoginRoleName(request);
		List<ModuleInfoTb> mList = new ArrayList<ModuleInfoTb>();
		Map<String,Object> map = new HashMap<String,Object>();
		boolean endFlag = true;//未过期
		Integer selRoleId = 0;
		Integer allModCheckStatus = 1;//所有主模块选中状态
		String loginType = this.getLoginType(request);
		CpyInfoTb cpy = null;
		boolean abilityFlag = false;
		if(loginRoleName.equals("super")){//超管获取所有模块列表
			mList = mm.listInfoByLevel(-1,-1);
		}else if(loginType.equals("cpyUser")){//代理机构员工获取和代理机构级别相同的模块列表
			//先判断用户是否具有权限
			if(loginRoleName.equals("管理员")){//管理员固有权限
				abilityFlag = true;
			}else{//其他身份需要判断是否具有该权限
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "listMa");
			}
			if(abilityFlag){
				//获取当前代理机构是否到期
				selRoleId = CommonTools.getFinalInteger(request.getParameter("selRoleId"));
				cpy = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb();
				String cpyEndDate = cpy.getEndDate();
				if(cpy.getCpyLevel() > 0){
					if(CurrentTime.compareDate(CurrentTime.getStringDate(), cpyEndDate) <= 0){
						endFlag = false;//已过期(免费会员不存在过期)
					}
				}
				mList = mm.listInfoByLevel(cpy.getCpyLevel(),0);
				if(selRoleId.equals(0)){//当获取指定角色的模块列表情况时，不用再获取角色列表了
					//获取该代理机构下的用户角色列表
					List<CpyRoleInfoTb> crList = crm.listInfoByCpyId(cpy.getId());
					List<Object> list_d = new ArrayList<Object>(); 
					if(crList.size() > 0){
						for(Iterator<CpyRoleInfoTb> it = crList.iterator() ; it.hasNext();){
							CpyRoleInfoTb cr = it.next();
							if(cr.getRoleName().equals("管理员")){
								continue;//不显示出管理员
							}else{
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("roleId", cr.getId());
								map_d.put("roleName", cr.getRoleName());
								list_d.add(map_d);
							}
						}
					}
					map.put("roleInfo", list_d);
				}
				map.put("endFlag", endFlag);//代理机构增加过期状态
			}
		}
		List<Object> list_d = new ArrayList<Object>();
		for(Iterator<ModuleInfoTb> it = mList.iterator() ; it.hasNext();){
			ModuleInfoTb mod = it.next();
			Map<String,Object> map_1 = new HashMap<String,Object>();
			map_1.put("modId", mod.getId());
			map_1.put("modName", mod.getModName());
			map_1.put("modUrl", mod.getResUrl());
			map_1.put("modLevel", mod.getModLevel());
			if(endFlag){//未过期
				if(loginType.equals("cpyUser") && cpy != null){
					if(cpy.getCpyLevel() >= mod.getModLevel()){
						map_1.put("useFlag", true);
					}else{
						map_1.put("useFlag", false);
					}
				}
			}else{//已过期
				if(mod.getModLevel() > 0){//铜牌以上的模块--全部不能设置
					map_1.put("useFlag", false);
				}else{//铜牌的模块一直免费使用
					map_1.put("useFlag", true);
				}
			}
			String modLevelChi = "";
			Integer modLevel = mod.getModLevel();
			if(modLevel.equals(0)){
				modLevelChi = "铜牌";
			}else if(modLevel.equals(1)){
				modLevelChi = "银牌";
			}else if(modLevel.equals(2)){
				modLevelChi = "金牌";
			}else if(modLevel.equals(3)){
				modLevelChi = "钻石";
			}
			map_1.put("modLevelChi", modLevelChi);
			map_1.put("orderNo", mod.getOrderNo());
			map_1.put("showStatus", mod.getShowStatus());
			Integer mainModCheckStatus = 1;
			//获取该模块下的模块动作列表
			List<ModActInfoTb> maList = mam.listInfoByModId(mod.getId());
			List<Object> list_ma = new ArrayList<Object>();
			for(Iterator<ModActInfoTb> it_1 = maList.iterator() ; it_1.hasNext();){
				ModActInfoTb ma = it_1.next();
				Map<String,Object> map_2 = new HashMap<String,Object>();
				map_2.put("maId", ma.getId());
				map_2.put("actNameChi", ma.getActNameChi());
				map_2.put("actNameEng", ma.getActNameEng());
				map_2.put("orderNo", ma.getOrderNo());
				map_2.put("modId",mod.getId());
				if(selRoleId > 0){
					if(arm.listInfoByOpt(selRoleId, ma.getId()).size() == 0){
						map_2.put("bindFlag",false);
						mainModCheckStatus *= 0;
					}else{
						map_2.put("bindFlag",true);
						mainModCheckStatus *= 1;
					}
				}else{
					map_2.put("bindFlag",false);
				}
				list_ma.add(map_2);
			}
			map_1.put("modActInfo", list_ma);
			if(loginType.equals("cpyUser")){
				if(mainModCheckStatus.equals(1)){
					map_1.put("mainBindFlag", true);
				}else{
					map_1.put("mainBindFlag", false);
				}
				allModCheckStatus *= mainModCheckStatus;
			}
			
			list_d.add(map_1);
		}
		map.put("result", list_d);
		if(loginType.equals("cpyUser")){
			if(allModCheckStatus.equals(1)){
				map.put("allBindFlag", true);
			}else{
				map.put("allBindFlag", false);
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
	 * 增加平台模块
	 * @description
	 * @author wm
	 * @date 2018-8-7 下午05:04:00
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward addModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModuleInfoManager mm = (ModuleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MODULE_INFO);
		ModActInfoManager mam = (ModActInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MOD_ACT_INFO);
		Map<String,String> map = new HashMap<String,String>();
		if(this.getLoginRoleName(request).equals("super")){//只有超管才能增加
			String modName = Transcode.unescape(request.getParameter("modName"), request);
			String modPic = "";
			String resUrl = request.getParameter("resUrl");
			Integer orderNo = 1;
			Integer modLevel = Integer.parseInt(request.getParameter("modLevel"));
			Integer showStatus = CommonTools.getFinalInteger(request.getParameter("showStatus"));
			String actNameEng = CommonTools.getFinalStr(request.getParameter("actNameEng"));
			if(mm.listInfoByName(modName).size() > 0){
				map.put("result", "exist");
			}else{
				//自动获取最大序列号
				List<ModuleInfoTb> mList = mm.listMaxOrderInfo();
				if(mList.size() > 0){
					orderNo = mList.get(0).getOrderNo() + 1;
				}
				Integer modId = mm.addModule(modName, modPic, resUrl, orderNo, showStatus, modLevel,actNameEng);
				if(modId > 0){
					//增加子模块
					mam.addMAct("增加", "add"+actNameEng, 1, modId);
					mam.addMAct("删除", "del"+actNameEng, 2, modId);
					mam.addMAct("修改", "up"+actNameEng, 3, modId);
					mam.addMAct("查看", "list"+actNameEng, 4, modId);
					map.put("result", "success");
				}else{
					map.put("result", "error");
				}
			}
		}else{
			map.put("result", "noAbility");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 获取指定模块信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 下午03:53:05
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSpecModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModuleInfoManager mm = (ModuleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MODULE_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		if(this.getLoginRoleName(request).equals("super")){//只有超管才能增加
			Integer modId = CommonTools.getFinalInteger(request.getParameter("modId"));
			ModuleInfoTb mod = mm.getEntityById(modId);
			if(mod != null){
				map.put("modId", mod.getId());
				map.put("modName", mod.getModName());
				map.put("resUrl", mod.getResUrl());
				map.put("orderNo", mod.getOrderNo());
				map.put("modLevel", mod.getModLevel());
				map.put("showStatus", mod.getShowStatus());
				map.put("actNameEng", mod.getActNameEng());
				map.put("result", "success");
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
	 * 修改模块信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午11:33:28
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModuleInfoManager mm = (ModuleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MODULE_INFO);
		ModActInfoManager mam = (ModActInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MOD_ACT_INFO);
		Map<String,String> map = new HashMap<String,String>();
		boolean existFlag = true;
		if(this.getLoginRoleName(request).equals("super")){//只有超管才能增加
			Integer modId = CommonTools.getFinalInteger(request.getParameter("modId"));
			String modName = Transcode.unescape(request.getParameter("modName"), request);
			String modPic = "";
			String resUrl = request.getParameter("resUrl");
			Integer orderNo = CommonTools.getFinalInteger(request.getParameter("orderNo"));
			Integer modLevel = CommonTools.getFinalInteger(request.getParameter("modLevel"));
			Integer showStatus = CommonTools.getFinalInteger(request.getParameter("showStatus"));
			String actNameEng = CommonTools.getFinalStr(request.getParameter("actNameEng"));
			ModuleInfoTb mod = mm.getEntityById(modId);
			if(!mod.getModName().equals(modName)){//模块名字变动
				//检查是否重名
				if(mm.listInfoByName(modName).size() > 0){
					map.put("result", "exist");
				}else{
					existFlag = false;
				}
			}else{//模块名字不变动
				existFlag = false;
			}
			if(!existFlag){
				boolean flag = mm.upModule(modId, modName, modPic, resUrl, orderNo, showStatus,modLevel,actNameEng);
				if(flag){
					if(!mm.getEntityById(modId).getActNameEng().equals(actNameEng)){//发变化，修改子模块
						List<ModActInfoTb> maList = mam.listInfoByModId(modId);
						Integer maId_add = 0,maId_del = 0,maId_up = 0,maId_list = 0;
						if(maList.size() > 0){
							for(Iterator<ModActInfoTb> it = maList.iterator() ; it.hasNext();){
								ModActInfoTb ma = it.next();
								if(ma.getActNameEng().startsWith("add")){
									maId_add = ma.getId();
								}else if(ma.getActNameEng().startsWith("del")){
									maId_del = ma.getId();
								}else if(ma.getActNameEng().startsWith("up")){
									maId_up = ma.getId();
								}else if(ma.getActNameEng().startsWith("list")){
									maId_list = ma.getId();
								}else{
									flag = false;
									break;
								}
							}
							if(flag){
								mam.upMActById(maId_add, "增加", "add"+actNameEng, 1);
								mam.upMActById(maId_del, "删除", "del"+actNameEng, 2);
								mam.upMActById(maId_up, "修改", "up"+actNameEng, 3);
								mam.upMActById(maId_list, "查看", "list"+actNameEng, 4);
								map.put("result", "success");
							}else{
								map.put("result", "startError");
							}
						}
					}else{
						map.put("result", "success");
					}
				}else{
					map.put("result", "error");
				}
			}
		}else{
			map.put("result", "noAbility");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**\
	 * 删除模块
	 * @description
	 * @author wm
	 * @date 2018-8-21 下午03:39:20
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModuleInfoManager mm = (ModuleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MODULE_INFO);
		ModActInfoManager mam = (ModActInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MOD_ACT_INFO);
		ActRoleInfoManager arm = (ActRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ACT_ROLE_INFO);
		Map<String,String> map = new HashMap<String,String>();
		if(this.getLoginRoleName(request).equals("super")){//只有超管才能增加
			Integer modId = CommonTools.getFinalInteger(request.getParameter("modId"));
			List<ModActInfoTb> mList = mam.listInfoByModId(modId);
			boolean delFlag = false;
			if(mList.size() > 0){
				for(Iterator<ModActInfoTb> it = mList.iterator() ; it.hasNext();){
					ModActInfoTb ma = it.next();
					Integer maId = ma.getId();
					//查看有无角色绑定这个模块动作
					if(arm.listInfoByOpt(0, maId).size() > 0){
						delFlag = false;
						break;
					}else{
						delFlag = true;
					}
				}
				if(delFlag){
					Integer status = 1;
					for(Iterator<ModActInfoTb> it = mList.iterator() ; it.hasNext();){
						ModActInfoTb ma = it.next();
						Integer maId = ma.getId();
						delFlag = mam.delMActById(maId);
						if(delFlag){
							status *= 1;
						}else{
							status *= 0;
						}
					}
					if(status.equals(1)){
						mm.delModuleById(modId);
						map.put("result", "success");
					}else{
						map.put("result", "error");
					}
				}else{
					map.put("result", "exist");//子模块存在角色权限绑定信息
				}
			}else{
				mm.delModuleById(modId);
				map.put("result", "success");
			}
		}else{
			map.put("result", "noAbility");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 增加指定模块的模块动作(暂不使用)
	 * @author Administrator
	 * @date 2018-8-8 下午10:32:44
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward addModAct(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		// TODO Auto-generated method stub
//		ModActInfoManager mam = (ModActInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MOD_ACT_INFO);
//		Map<String,String> map = new HashMap<String,String>();
//		if(this.getLoginRoleName(request).equals("super")){//只有超管才能增加
//			Integer modId = Integer.parseInt(request.getParameter("modId"));
//			String actNameChi = Transcode.unescape(request.getParameter("actNameChi"), request);
//			String actNameEng = request.getParameter("actNameEng");
//			Integer orderNo = Integer.parseInt(request.getParameter("orderNo"));
//			if(mam.listSpecInfoByOpt(modId, actNameChi, actNameEng).size() > 0){
//				map.put("result", "exist");
//			}else{
//				Integer maId = mam.addMAct(actNameChi, actNameEng, orderNo, modId);
//				if(maId > 0){
//					map.put("result", "success");
//				}else{
//					map.put("result", "error");
//				}
//			}
//		}else{
//			map.put("result", "noAbility");
//		}
//		String json = JSON.toJSONString(map);
//        PrintWriter pw = response.getWriter();  
//        pw.write(json); 
//        pw.flush();  
//        pw.close();
//		return null;
//	}
	
	/**
	 * 删除模块动作(暂不使用)
	 * @description
	 * @author wm
	 * @date 2018-8-9 上午09:52:23
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward delModAct(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		// TODO Auto-generated method stub
//		ModActInfoManager mam = (ModActInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MOD_ACT_INFO);
//		ActRoleInfoManager arm = (ActRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ACT_ROLE_INFO);
//		Map<String,String> map = new HashMap<String,String>();
//		if(this.getLoginRoleName(request).equals("super")){//只有超管才能增加
//			Integer maId = Integer.parseInt(request.getParameter("maId"));
//			//查看有无角色绑定这个模块动作
//			if(arm.listInfoByOpt(0, maId).size() > 0){
//				map.put("result", "exist");
//			}else{
//				//删除动作
//				boolean flag = mam.delMActById(maId);
//				if(flag){
//					map.put("result", "success");
//				}else{
//					map.put("result", "error");
//				}
//			}
//		}else{
//			map.put("result", "noAbility");
//		}
//		String json = JSON.toJSONString(map);
//        PrintWriter pw = response.getWriter();  
//        pw.write(json); 
//        pw.flush();  
//        pw.close();
//		return null;
//	}
	
	/**
	 * 获取指定模块动作信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 下午04:01:58
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSpecModAct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModActInfoManager mam = (ModActInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MOD_ACT_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		if(this.getLoginRoleName(request).equals("super")){//只有超管才能增加
			Integer maId = Integer.parseInt(request.getParameter("maId"));
			List<ModActInfoTb> maList = mam.listSpecInfoById(maId);
			if(maList.size() > 0){
				ModActInfoTb ma = maList.get(0);
				map.put("maId", ma.getId());
				map.put("actNameChi", ma.getActNameChi());
				map.put("actNameEng", ma.getActNameEng());
				map.put("orderNo", ma.getOrderNo());
				map.put("result", "success");
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
	 * 修改指定模块动作
	 * @description
	 * @author wm
	 * @date 2018-8-21 下午04:05:04
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upModAct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModActInfoManager mam = (ModActInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MOD_ACT_INFO);
		Map<String,String> map = new HashMap<String,String>();
		if(this.getLoginRoleName(request).equals("super")){//只有超管才能增加
			Integer maId = Integer.parseInt(request.getParameter("maId"));
			//查看有无角色绑定这个模块动作
			String actNameEng = request.getParameter("actNameEng");
			List<ModActInfoTb> maList = mam.listSpecInfoById(maId);
			ModActInfoTb ma = maList.get(0);
			Integer modId = ma.getModuleInfoTb().getId();
			String actNameEng_db = ma.getActNameEng();
			if(actNameEng.startsWith("add") || actNameEng.startsWith("del") || actNameEng.startsWith("up") || actNameEng.startsWith("list")){
				if(actNameEng_db.equals(actNameEng)){//相同
					map.put("result", "success");//不修改
				}else{//英文不同
					if(mam.listSpecInfoByOpt(modId, "", actNameEng).size() > 0){
						map.put("result", "exist");
					}else{
						mam.upMActById(maId, "", actNameEng, -1);
						map.put("result", "success");
					}
				}
			}else{
				map.put("result", "startError");//英文名开始必须固定
			}
			
		}else{
			map.put("result", "noAbility");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 代理机构管理员为机构角色绑定模块动作(绑定归类到修改里面)
	 * @description
	 * @author wm
	 * @date 2018-8-9 下午04:37:39
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward bindMod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		ActRoleInfoManager arm = (ActRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ACT_ROLE_INFO);
		String loginRoleName = this.getLoginRoleName(request);
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		boolean flag = false,abilityFlag = false;
		if(this.getLoginType(request).equals("cpyUser")){//只有代理机构才有绑定的权限
			if(loginRoleName.equals("管理员")){//管理员固有的权限
				abilityFlag = true;
			}else{
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upMa");
			}
		}
		if(abilityFlag){
			Integer selRoleId = Integer.parseInt(request.getParameter("selRoleId"));
			String selMaIdStr = request.getParameter("selMaIdStr");
			CpyUserInfo cUser = cum.getEntityById(this.getLoginUserId(request));
			Integer cpyId = cUser.getCpyInfoTb().getId();
			//获取该代理机构下所有的角色
			List<CpyRoleInfoTb> crList = crm.listInfoByCpyId(cpyId);
			if(crList.size() > 0){
				for(Iterator<CpyRoleInfoTb> it = crList.iterator() ; it.hasNext();){
					CpyRoleInfoTb cr = it.next();
					if(cr.getId().equals(selRoleId)){
						flag = true;
						break;
					}
				}
			}else{
				msg = "fail";
			}
			if(!selMaIdStr.equals("") && flag){
				//先后去指定角色存在的绑定关系
				List<ActRoleInfoTb> arList = arm.listInfoByOpt(selRoleId, 0);
				if(arList.size() > 0){
					//全部删除，然后重新增加
					String idStr = "";
					for(Iterator<ActRoleInfoTb> it = arList.iterator() ; it.hasNext();){
						ActRoleInfoTb ar = it.next();
						idStr += ar.getId() + ",";
					}
					idStr = idStr.substring(0, idStr.length() - 1);
					arm.delBatchInfoById(idStr);
					//批量增加绑定关系
					if(!selMaIdStr.equals("")){
						arm.addBatchARole(selRoleId, selMaIdStr);
					}
				}else{
					//直接增加绑定关系
					if(!selMaIdStr.equals("")){
						arm.addBatchARole(selRoleId, selMaIdStr);
					}
				}
				msg = "success";
			}else{
				msg = "error";
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
	 * 获取个人模块列表
	 * @description
	 * @author wm
	 * @date 2018-8-10 下午04:09:53
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSelfModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActRoleInfoManager arm = (ActRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ACT_ROLE_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		String loginRoleName = this.getLoginRoleName(request);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean endFlag = true;//未过期
		if(this.getLoginType(request).equals("cpyUser")){//代理机构员工
			if(!loginRoleName.equals("管理员")){//代理机构中管理员以外的其他身份
				List<ActRoleInfoTb> arList = arm.listInfoByOpt(this.getLoginRoleId(request), 0);
				if(arList.size() > 0){
					List<Object> list_d = new ArrayList<Object>();
					List<ModuleInfoTb> list_m = new ArrayList<ModuleInfoTb>();
					CpyInfoTb cpy = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb();
					String cpyEndDate = cpy.getEndDate();
					if(CurrentTime.compareDate(CurrentTime.getStringDate(), cpyEndDate) <= 0){//已过期
						endFlag = false;//已过期
					}
					for(Iterator<ActRoleInfoTb> it = arList.iterator() ; it.hasNext();){
						ActRoleInfoTb ar = it.next();
						ModuleInfoTb module = ar.getModActInfoTb().getModuleInfoTb();
						Map<String,Object> map_d = new HashMap<String,Object>();
						if(module.getShowStatus().equals(0)){//只获取可见
							if(list_d.size() == 0){//首次不判断
								map_d.put("modId", module.getId());
								map_d.put("modName", module.getModName());
								map_d.put("modUrl", module.getResUrl());
								if(endFlag){//未过期
									map_d.put("useFlag", true);
								}else{
									if(module.getModLevel() > 0){//铜牌以上的模块
										map_d.put("useFlag", false);
									}else{//铜牌的模块一直免费使用
										map_d.put("useFlag", true);
									}
								}
								list_d.add(map_d);
								list_m.add(module);
							}else{
								//判断list_m中有无相同的modId
								Integer exist_status = 1;
								for(Iterator<ModuleInfoTb> it_1 = list_m.iterator() ; it_1.hasNext();){
									ModuleInfoTb mod_exist = it_1.next();
									if(mod_exist.getId().equals(module.getId())){
										exist_status = 0;
										break;
									}else{
										exist_status = 1;//不存在
									}
								}
								if(exist_status.equals(1)){//不存在
									map_d.put("modId", module.getId());
									map_d.put("modName", module.getModName());
									map_d.put("modUrl", module.getResUrl());
									if(endFlag){//未过期
										map_d.put("useFlag", true);
									}else{
										if(module.getModLevel() > 0){//铜牌以上的模块
											map_d.put("useFlag", false);
										}else{//铜牌的模块一直免费使用
											map_d.put("useFlag", true);
										}
									}
									list_d.add(map_d);
									list_m.add(module);
								}
							}
						}
						
					}
					map.put("modInfo", list_d);
				}else{
					map.put("result", new ArrayList<Object>());
				}
			}else{
				map.put("result", new ArrayList<Object>());
			}
		}else{//除了代理机构以外其他用户不获取模块列表
			map.put("result", new ArrayList<Object>());
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
}