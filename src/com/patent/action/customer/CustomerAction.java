/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.patent.service.CustomerInfoManager;
import com.patent.util.Constants;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 08-20-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class CustomerAction extends DispatchAction {
	
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
	 * 导向代理机构下客户信息管理页面
	 * @description
	 * @author wm
	 * @date 2018-8-20 上午11:15:38
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward goCusPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String[] myAbility = Ability.getAbilityInfo("addCus,upCus,delCus", this.getLoginType(request), this.getLoginRoleName(request), this.getLoginRoleId(request)).split(",");
		request.setAttribute("delFlag", myAbility[0]);
		request.setAttribute("upFlag", myAbility[1]);
		request.setAttribute("addFlag", myAbility[2]);
		return mapping.findForward("cusPage");
	}
	
	public ActionForward addCusData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
//		CustomerInfoManager cm = 
		return null;
	}
}