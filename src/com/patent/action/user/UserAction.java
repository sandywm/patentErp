/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.user;

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
import com.patent.module.ApplyInfoTb;
import com.patent.module.CpyInfoTb;
import com.patent.module.CpyRoleUserInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.module.SuperUser;
import com.patent.page.PageConst;
import com.patent.service.ApplyInfoManager;
import com.patent.service.CpyInfoManager;
import com.patent.service.CpyRoleInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.SuperUserManager;
import com.patent.tools.Convert;
import com.patent.tools.MD5;
import com.patent.util.Constants;

/** 
 * MyEclipse Struts
 * Creation date: 07-29-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class UserAction extends DispatchAction {
	
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
	 * 导向欢迎首页界面
	 * @author Administrator
	 * @date 2018-8-7 下午09:00:58
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goWelcomePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("welcome");
	}
	
	/**
	 * 导向个人信息修改页面
	 * @description
	 * @author wm
	 * @date 2018-8-4 下午04:24:43
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goUserDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("udPage");
	}
	
	/**
	 * 导向个人密码修改页面
	 * @author Administrator
	 * @date 2018-8-7 下午08:52:10
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goUpdatePassPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("uPassPage");
	}
	
	/**
	 * 获取个人用户详细记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getUserDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Integer userId = this.getLoginUserId(request);
		String loginType = this.getLoginType(request);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		ApplyInfoManager am = (ApplyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
		SuperUserManager sum = (SuperUserManager)  AppFactory.instance(null).getApp(Constants.WEB_SUPER_USER_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		if(loginType.equals("appUser")){//申请人/公司身份
			ApplyInfoTb appUser = am.getEntityById(userId);
			if(appUser != null){
				map.put("result", "success");
				map.put("id", appUser.getId());
				map.put("name", appUser.getAppName());
				map.put("type", appUser.getAppType());
				map.put("appICard", appUser.getAppICard());
				map.put("appAddress", appUser.getAppAddress());
				map.put("appAccount", appUser.getAppAccount());
				map.put("appLxr", appUser.getAppLxr());
				map.put("tel", appUser.getAppTel());
				map.put("email", appUser.getAppEmail());
			}else{
				map.put("result", "noUser");//查无此人
			}
		}else if(loginType.equals("cpyUser")){//代理机构
			CpyUserInfo cpyUser = cum.getEntityById(userId);
			if(cpyUser != null){
				map.put("result", "success");
				map.put("id", cpyUser.getId());
				map.put("name", cpyUser.getUserName());
				map.put("account", cpyUser.getUserAccount());
				map.put("sex", cpyUser.getUserSex());
				map.put("email", cpyUser.getUserEmail());
				map.put("tel", cpyUser.getUserTel());
				map.put("inDate", cpyUser.getUserInDate());
				map.put("outDate", cpyUser.getUserOutDate());
				map.put("lzStatus", cpyUser.getUserLzStatus());
				map.put("zxNum", cpyUser.getUserZxNum());
				map.put("scFiled", cpyUser.getUserScFiledId());
				map.put("scFiledName", cpyUser.getUserScFiledName());
				map.put("useExp", cpyUser.getUserExper());
			}else{
				map.put("result", "noUser");//查无此人
			}
		}else if(loginType.equals("spUser")){//平台用户
			List<SuperUser> suList = sum.listInfoById(userId);
			if(suList.size() > 0){
				SuperUser spUser = suList.get(0);
				map.put("result", "success");
				map.put("id", spUser.getId());
				map.put("name", spUser.getUserName());
				map.put("account", spUser.getAccount());
				map.put("type", spUser.getUserType());
			}else{
				map.put("result", "noUser");//查无此人
			}
		}else{
			map.put("result", "fail");//session失效或者捣乱
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 修改个人基本信息(代理机构和申请人/公司用)
	 * @description
	 * @author wm
	 * @date 2018-8-3 上午11:13:55
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateUserDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Integer userId = this.getLoginUserId(request);
		String loginType = this.getLoginType(request);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		ApplyInfoManager am = (ApplyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
		boolean flag = false;
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		if(loginType.equals("appUser")){//申请人/公司身份
			String appiCard = request.getParameter("appiCard");
			String address = Transcode.unescape(request.getParameter("address"), request);
			String lxr = Transcode.unescape(request.getParameter("lxr"), request);
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			String qq = request.getParameter("qq");
			flag = am.updateUserDetailById(userId, appiCard, address, lxr, tel, email, qq);
		}else if(loginType.equals("cpyUser")){//代理机构
			String userName = Transcode.unescape(request.getParameter("name"), request);
			String userNamePy =  Convert.getFirstSpell(userName);
			String userSex = request.getParameter("sex");
			String userEmail = request.getParameter("email");
			String userTel = request.getParameter("tel");
			flag = cum.updateBasicInfoById(userId, userName, userNamePy, userSex, userEmail, userTel);
			if(flag){
				String userScFiledIdStr = request.getParameter("userScFiledIdStr");
				String userScFiledNameStr = Transcode.unescape(request.getParameter("userScFiledNameStr"), request);
				flag = cum.updateInfoById(userId, 0, userScFiledIdStr, userScFiledNameStr, 0);
			}
		}else{
			
		}
		map.put("result", flag);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	
	/**
	 * 修改用户密码
	 * @author Administrator
	 * @date 2018-7-31 下午09:37:46
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateUserPass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Integer userId = this.getLoginUserId(request);
		String loginType = this.getLoginType(request);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		ApplyInfoManager am = (ApplyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
		SuperUserManager sum = (SuperUserManager)  AppFactory.instance(null).getApp(Constants.WEB_SUPER_USER_INFO);
		Map<String,String> map = new HashMap<String,String>();
		String inputPass_old = String.valueOf(request.getParameter("passOld"));
		String newPass = String.valueOf(request.getParameter("newPass"));
		MD5 md5 = new MD5();
		String msg = "";
		if(inputPass_old.equals("null") || inputPass_old.equals("")){
			msg = "oldNull";//输入原数据库密码为空
		}else{
			if(loginType.equals("cpyUser")){
				CpyUserInfo cUser = cum.getEntityById(userId);
				if(cUser != null){
					String pass_db = cUser.getUserPassword();
					if(pass_db.equalsIgnoreCase(md5.calcMD5(inputPass_old))){
						cum.updatePassById(userId, newPass);
						msg = "success";
					}else{
						msg = "noMatch";//不匹配
					}
				}
			}else if(loginType.equals("appUser")){
				ApplyInfoTb app = am.getEntityById(userId);
				if(app != null){
					String pass_db = app.getAppPass();
					if(pass_db.equalsIgnoreCase(md5.calcMD5(inputPass_old))){
						am.updatePassById(userId, newPass);
						msg = "success";
					}else{
						msg = "noMatch";//不匹配
					}
				}
			}else if(loginType.equals("spUser")){
				List<SuperUser> suList = sum.listInfoById(userId);
				if(suList.size() > 0){
					String pass_db = suList.get(0).getPassword();
					if(pass_db.equalsIgnoreCase(md5.calcMD5(inputPass_old))){
						sum.updateSUserById(userId, newPass, "");
						msg = "success";
					}else{
						msg = "noMatch";//不匹配
					}
				}
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
	 * 导向管理机构下员工列表页面
	 * @description
	 * @author wm
	 * @date 2018-8-4 下午04:29:04
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goUserPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("userPage");
	}
	
	/**
	 * 管理员增加代理机构员工
	 * @author Administrator
	 * @date 2018-8-3 下午10:45:42
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addCpyUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		String roleName = this.getLoginRoleName(request);
		Integer userId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(userId);
		Integer cpyId = 0;
		String msg = "";
		if(cUser != null){
			cpyId = cUser.getCpyInfoTb().getId();
			if(roleName.equals("管理员")){//只有管理员才能增加
				String userName = Transcode.unescape(request.getParameter("name"), request);
				String userNamePy =  Convert.getFirstSpell(userName);
				String account = request.getParameter("account");
				String userSex = request.getParameter("sex");
				String userEmail = request.getParameter("email");
				String userTel = request.getParameter("tel");
				String inDate = request.getParameter("inDate");
				String userScFiledIdStr = request.getParameter("userScFiledIdStr");
				String userScFiledNameStr = Transcode.unescape(request.getParameter("userScFiledNameStr"), request);
				Integer roleId = Integer.parseInt(request.getParameter("roleId"));
				Integer cpyUserId = cum.addCpyUser(cpyId, userName, userNamePy, account, new MD5().calcMD5("123456"), userSex, 
						userEmail, userTel, inDate, userScFiledIdStr, userScFiledNameStr);
				if(cpyUserId > 0){
					//绑定员工角色
					Integer cruId = crm.addRoleUser(roleId, cpyUserId);
					if(cruId > 0){
						msg = "success";
					}else{
						msg = "error";
					}
				}else{
					msg = "error";	
				}
			}else{
				msg = "noAbility";
			}
		}else{
			msg = "fail";
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", msg);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 根据条件分页获取当前代理机构的所有用户列表
	 * @author Administrator
	 * @date 2018-8-3 下午11:09:41
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCpyUserPageInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
		Integer cpyId = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		if(cUser != null){
			cpyId = cUser.getCpyInfoTb().getId();
			Integer userLzStatus = Integer.parseInt(request.getParameter("userLzStatus"));
			Integer userYxStatus = Integer.parseInt(request.getParameter("userYxStatus"));
			Integer roleId = Integer.parseInt(request.getParameter("roleId"));
			String userNamePy = request.getParameter("userNamePy");
			Integer count = cum.getCountByOpt(cpyId, userLzStatus, userYxStatus, roleId, userNamePy);
			if(count > 0){
				Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("pageSize")), 10);
				Integer pageCount = PageConst.getPageCount(count, pageSize);
				Integer pageNo = PageConst.getPageNo(String.valueOf(request.getParameter("pageNo")), pageCount);
				List<CpyUserInfo> cUserList = cum.listPageInfoByOpt(cpyId, userLzStatus, userYxStatus, roleId, userNamePy, pageNo, pageSize);
				List<Object> list_u = new ArrayList<Object>();
				for(Iterator<CpyUserInfo> it = cUserList.iterator() ; it.hasNext();){
					CpyUserInfo cUser_a = it.next();
					Map<String,Object> map_u = new HashMap<String,Object>();
					map_u.put("userId", cUser_a.getId());
					map_u.put("name", cUser_a.getUserName());
					map_u.put("account", cUser_a.getUserAccount());
					map_u.put("sex", cUser_a.getUserSex());
					map_u.put("email", cUser_a.getUserEmail());
					map_u.put("inDate", cUser_a.getUserInDate());
					map_u.put("outDate", cUser_a.getUserOutDate());
					Integer lzStatus = cUser_a.getUserLzStatus();
					String lzStatusChi = "在职";
					if(lzStatus.equals(0)){
						lzStatusChi = "离职";
					}
					map_u.put("lzStatus", lzStatusChi);
					Integer yxStatus = cUser_a.getUserYxStatus();
					String yxStatusChi = "有效";
					if(yxStatus.equals(0)){
						yxStatusChi = "无效";
					}
					map_u.put("yxStatus", yxStatusChi);
					map_u.put("zxNum", cUser_a.getUserZxNum());
					map_u.put("scFiledName", cUser_a.getUserScFiledName());
					map_u.put("exper", cUser_a.getUserExper());
					//获取用户角色
					List<CpyRoleUserInfoTb> ruList = crm.listInfoByUserId(cUser_a.getId());
					String roleName = "";
					for(Iterator<CpyRoleUserInfoTb> it_1 = ruList.iterator() ; it_1.hasNext();){
						CpyRoleUserInfoTb ru = it_1.next();
						roleName += ru.getCpyRoleInfoTb().getRoleName() + ",";
					}
					if(!roleName.equals("")){
						roleName = roleName.substring(0, roleName.length() - 1);
					}
					map_u.put("roleName", roleName);
					list_u.add(map_u);
				}
				map.put("result", "success");
				map.put("uInfo", list_u);
			}else{
				map.put("result", "noInfo");
			}
		}else{
			map.put("result", "fail");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 根据条件获取当前代理机构的所有用户记录条数
	 * @author Administrator
	 * @date 2018-8-3 下午11:10:41
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCpyUserCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
		Integer cpyId = 0;
		Integer count = 0;
		Map<String,Integer> map = new HashMap<String,Integer>();
		if(cUser != null){
			cpyId = cUser.getCpyInfoTb().getId();
			Integer userLzStatus = Integer.parseInt(request.getParameter("userLzStatus"));
			Integer userYxStatus = Integer.parseInt(request.getParameter("userYxStatus"));
			Integer roleId = Integer.parseInt(request.getParameter("roleId"));
			String userNamePy = request.getParameter("userNamePy");
			count = cum.getCountByOpt(cpyId, userLzStatus, userYxStatus, roleId, userNamePy);
		}
		map.put("result", count);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 设置员工离职、账号有效
	 * @description
	 * @author wm
	 * @date 2018-8-4 上午11:34:18
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		boolean ailityFlag = false;
		String msg = "";
		if(this.getLoginRoleName(request).equals("管理员")){//如果是管理员直接跳过（管理员直接拥有权限）
			ailityFlag = true;
		}else{
			//获取当前用户是否有修改权限
		}
		if(ailityFlag){
			Integer cpyUserId = Integer.parseInt(request.getParameter("userId"));
			Integer lzSatatus = Integer.parseInt(request.getParameter("lzSatatus"));
			String outDate = request.getParameter("outDate");
			Integer yxStatus = Integer.parseInt(request.getParameter("yxStatus"));
			boolean flag = cum.updateUserInfoById(cpyUserId, outDate, lzSatatus, yxStatus);
			if(flag){
				msg = "success";
			}else{
				msg = "error";
			}
		}else{
			msg = "noAbility";
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", msg);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 增加平台用户
	 * @author Administrator
	 * @date 2018-8-4 下午11:09:21
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSpUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		SuperUserManager sum = (SuperUserManager) AppFactory.instance(null).getApp(Constants.WEB_SUPER_USER_INFO);
		String roleName = this.getLoginRoleName(request);
		String msg = "";
		if(roleName.equals("super")){//超级管理员
			String account = request.getParameter("account");
			String userName = Transcode.unescape(request.getParameter("userName"), request);
			String userType = request.getParameter("userType");//cwu:财务，zjl:总经理
			Integer sUserId = sum.addSUser(account, new MD5().calcMD5("123456"), userName, userType);
			if(sUserId > 0){
				msg = "success";
			}else{
				msg = "error";
			}
		}else{
			msg = "noAbility";
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", msg);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
}