/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.role;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.patent.action.base.Transcode;
import com.patent.factory.AppFactory;
import com.patent.module.CpyRoleInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.service.CpyRoleInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.util.Constants;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 08-04-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class RoleAction extends DispatchAction {
	
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
	 * 导向代理机构角色管理界面
	 * @description
	 * @author wm
	 * @date 2018-8-4 下午04:32:41
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward goRolePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
//		boolean delFlag = false;//删除权限
//		boolean upFlag = false;//修改权限
//		boolean addFlag = false;//增加权限
//		if(this.getLoginType(request).equals("cpyUser")){
//			if(this.getLoginRoleName(request).equals("管理员")){
//				delFlag = upFlag = addFlag = true;
//			}else{
//				//获取当前用户有无增加角色的权限，如果是管理员直接跳过（管理员直接拥有权限）
//				delFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "delRole");
//				upFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upRole");
//				addFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addRole");
//			}
//		}
		String[] myAbility = Ability.getAbilityInfo("addRole,upRole,delRole", this.getLoginType(request), this.getLoginRoleName(request), this.getLoginRoleId(request)).split(",");
		request.setAttribute("delFlag", Boolean.parseBoolean(myAbility[0]));
		request.setAttribute("upFlag", Boolean.parseBoolean(myAbility[1]));
		request.setAttribute("addFlag", Boolean.parseBoolean(myAbility[2]));
		System.out.println(myAbility[0] + "," + myAbility[1] + "," + myAbility[2]);
		return mapping.findForward("rolePage");
	}
	
	/**
	 * 增加角色
	 * @description
	 * @author wm
	 * @date 2018-8-4 下午04:33:36
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward addRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO); 
		Integer userId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(userId);
		boolean abilityFlag = false;
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		if(cUser != null){
			Integer cpyId = cUser.getCpyInfoTb().getId();
			if(this.getLoginRoleName(request).equals("管理员")){
				abilityFlag = true;
			}else{
				//获取当前用户有无增加角色的权限，如果是管理员直接跳过（管理员直接拥有权限）
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addRole");
			}
			if(abilityFlag){
				String roleName = Transcode.unescape(request.getParameter("inpRoleName"), request);
				String roleProfile = Transcode.unescape(request.getParameter("roleProfile"), request);
				//检查当前输入的角色名字是否存在
				if(crm.listInfoByOpt(cpyId, roleName).size() > 0){
					msg = "exist";//已存在
				}else{
					//增加角色
					Integer roleId = crm.addRole(roleName, roleProfile, cpyId);
					if(roleId > 0){
						msg = "success";
					}else{
						msg = "error";
					}
				}
			}else{
				msg = "noAbility";
			}
		}else{
			msg = "fail";
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
	 * 修改角色
	 * @description
	 * @author wm
	 * @date 2018-8-4 下午05:35:54
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO); 
		Integer userId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(userId);
		boolean abilityFlag = false;
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		if(cUser != null){
			Integer cpyId = cUser.getCpyInfoTb().getId();
			if(this.getLoginRoleName(request).equals("管理员")){
				abilityFlag = true;
			}else{
				//获取当前用户有无增加角色的权限，如果是管理员直接跳过（管理员直接拥有权限）
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upRole");
			}
			if(abilityFlag){
				Integer roleId = Integer.parseInt(request.getParameter("roleId"));
				String roleName = Transcode.unescape(request.getParameter("inpRoleName"), request);
				String roleProfile = Transcode.unescape(request.getParameter("roleProfile"), request);
				//修改前获取当前roleId的数据库中的名字
				List<CpyRoleInfoTb> rList = crm.listInfoById(roleId);
				if(rList.size() > 0){
					String roleName_db = rList.get(0).getRoleName();
					if(roleName.equals("管理员")){//管理员字段不能进行修改
						msg = "error";
					}else if(roleName_db.equals(roleName)){
						//相同说明没修改，不进行重名检查
						boolean flag = crm.updateRoleById(roleId, roleName, roleProfile);
						if(flag){
							msg = "success";
						}else{
							msg = "error";
						}
					}else{
						if(crm.listInfoByOpt(cpyId, roleName).size() > 0){
							msg = "exist";//已存在
						}else{
							boolean flag = crm.updateRoleById(roleId, roleName, roleProfile);
							if(flag){
								msg = "success";
							}else{
								msg = "error";
							}
						}
					}
				}else{
					msg = "fail";
				}
			}else{
				msg = "noAbility";
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
	 * 获取代理机构下的角色列表
	 * @author Administrator
	 * @date 2018-8-4 下午10:03:46
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRoleList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO); 
		Integer userId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(userId);
		Map<String,Object> map = new HashMap<String,Object>();
		if(cUser != null){
			Integer cpyId = cUser.getCpyInfoTb().getId();
			List<CpyRoleInfoTb> crList = crm.listInfoByCpyId(cpyId);
			List<Object> list_d = new ArrayList<Object>();
			for(Iterator<CpyRoleInfoTb> it = crList.iterator() ; it.hasNext();){
				CpyRoleInfoTb cr = it.next();
				Map<String,Object> map_d = new HashMap<String,Object>();
				map_d.put("id", cr.getId());
				map_d.put("roleName", cr.getRoleName());
				map_d.put("roleProfile", cr.getRoleProfile());
				list_d.add(map_d);
			}
			map.put("roleList", list_d);
		}else{
			map.put("roleList", new ArrayList<Object>());
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	public ActionForward delRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub 
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO); 
		Map<String,Object> map = new HashMap<String,Object>();
		String msg = "";
		boolean abilityFlag = false;
		boolean flag = false;
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//检查用户有无删除的权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "delRole");
		}
		if(abilityFlag){
			Integer roleId = Integer.parseInt(request.getParameter("roleId"));
			//获取当前代理机构有无该角色
			CpyUserInfo cUser = cum.getEntityById(this.getLoginUserId(request));
			List<CpyRoleInfoTb> crList = crm.listInfoByCpyId(cUser.getCpyInfoTb().getId());
			for(Iterator<CpyRoleInfoTb> it = crList.iterator() ; it.hasNext();){
				CpyRoleInfoTb cr = it.next();
				if(cr.getId().equals(roleId)){
					flag = true;
					break;
				}else{
					flag = false;
				}
			}
			if(flag){
				if(crm.listInfoById(roleId).get(0).getRoleName().equals("管理员")){
					//管理员不能删除
					msg = "noDel";
				}else{
					//检查有无绑定该角色的用户
					if(crm.listInfoByRoleId(roleId).size() > 0){
						msg = "existUser";
					}else{
						flag = crm.delRoleById(roleId);
						if(flag){
							msg = "success";
						}else{
							msg = "error";
						}
					}
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
}