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
import com.patent.module.CpyRoleInfoTb;
import com.patent.module.CpyRoleUserInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.module.JsFiledInfoTb;
import com.patent.module.SuperUser;
import com.patent.page.PageConst;
import com.patent.service.ApplyInfoManager;
import com.patent.service.CpyRoleInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.JsFiledInfoManager;
import com.patent.service.SuperUserManager;
import com.patent.tools.CommonTools;
import com.patent.tools.Convert;
import com.patent.tools.MD5;
import com.patent.util.Constants;
import com.patent.web.Ability;

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
	 * 根据所选身份跳转页面
	 * @description
	 * @author wm
	 * @date 2018-7-25 下午04:07:32
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		Integer roleId = CommonTools.getFinalInteger(request.getParameter("roleId"));
		String roleName = "";
		String loginType = String.valueOf(request.getParameter("loginType"));//cpyUser:代理机构员工登录,appUser:申请人/公司账号登录
		String urlPage = "";
		if(loginType.equals("cpyUser")){
			//判断当前用户是否真的有该身份(用户破坏)
			boolean flag = false;
			List<CpyRoleUserInfoTb> crList = crm.listInfoByUserId(this.getLoginUserId(request));
			if(crList.size() > 0){
				for(Iterator<CpyRoleUserInfoTb> it = crList.iterator() ; it.hasNext() ;){
					CpyRoleUserInfoTb cru = it.next();
					if(cru.getCpyRoleInfoTb().getId().equals(roleId)){
						flag = true;
						roleName = cru.getCpyRoleInfoTb().getRoleName();
						break;
					}
				}
			}
			if(flag){
				request.getSession(false).setAttribute(Constants.LOGIN_USER_ROLE_ID, roleId);
				request.getSession(false).setAttribute(Constants.LOGIN_USER_ROLE_NAME, roleName);
				urlPage = "welcome";//管理机构其他角色主界面
			}else{
				urlPage = "loginException";//异常界面
			}
		}else if(loginType.equals("appUser")){
			request.getSession(false).setAttribute(Constants.LOGIN_USER_ROLE_NAME, "申请人/公司");
			urlPage = "welcome";//管理机构其他角色主界面
		}else{
			urlPage = "loginException";//异常界面
		}
		return mapping.findForward(urlPage);
	}
	
	
	/**
	 * 平台管理人员导向页面
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午09:13:05
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward spGoPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("welcome");
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
		return mapping.findForward("index");
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
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		if(loginType.equals("appUser")){//申请人/公司身份
			ApplyInfoTb appUser = am.getEntityById(userId);
			if(appUser != null){
				map.put("result", "success");
				map.put("id", appUser.getId());
				map.put("name", appUser.getAppName());
				map.put("roleName", appUser.getAppType());
				map.put("appICard", appUser.getAppICard());
				map.put("appAddress", appUser.getAppAddress());
				map.put("appAccount", appUser.getAppAccount());
				map.put("appLxr", appUser.getAppLxr());
				map.put("tel", appUser.getAppTel());
				map.put("email", appUser.getAppEmail());
				map.put("qq", appUser.getAppQq());
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
				map.put("yxStatus", cpyUser.getUserYxStatus());
				//获取用户角色
				List<CpyRoleUserInfoTb> ruList = crm.listInfoByUserId(cpyUser.getId());
				String roleName = "";
				for(Iterator<CpyRoleUserInfoTb> it_1 = ruList.iterator() ; it_1.hasNext();){
					CpyRoleUserInfoTb ru = it_1.next();
					roleName += ru.getCpyRoleInfoTb().getRoleName() + ",";
				}
				if(!roleName.equals("")){
					roleName = roleName.substring(0, roleName.length() - 1);
				}
				map.put("roleName", roleName);
				//-----------------------------------------//
				if(!this.getLoginRoleName(request).equals("管理员")){
					//下面数据为代理机构其他用户所有
					map.put("zxNum", cpyUser.getUserZxNum());
					String scFiledIdStr = cpyUser.getUserScFiledId();
					map.put("scFiled", scFiledIdStr);
					Integer cpyId = cum.getEntityById(userId).getCpyInfoTb().getId();
//					List<JsFiledInfoTb> jsList = jsm.listInfoByOpt(cpyId, cpyUser.getUserScFiledId());
//					String scFiledName = "";
//					for(Iterator<JsFiledInfoTb> it = jsList.iterator() ; it.hasNext();){
//						JsFiledInfoTb js = it.next();
//						scFiledName += js.getZyName() + ",";
//					}
//					if(!scFiledName.equals("")){
//						scFiledName = scFiledName.substring(0, scFiledName.length() - 1);
//					}
//					map.put("scFiledName", scFiledName);
					//获取该代理机构所有的专业列表
					List<JsFiledInfoTb> jsList_all = jsm.listInfoByOpt(cpyId, "");
					List<Object> list_d = new ArrayList<Object>();
					for(Iterator<JsFiledInfoTb> it_1 = jsList_all.iterator() ; it_1.hasNext();){
						JsFiledInfoTb js = it_1.next();
						Map<String,Object> map_d = new HashMap<String,Object>();
						map_d.put("jsId", js.getId());
						map_d.put("zyName", js.getZyName());
						boolean checkFlag = false;
						if(!scFiledIdStr.equals("")){
							String[] scFiledIdArr = scFiledIdStr.split(",");
							for(Integer i = 0 ; i < scFiledIdArr.length ; i++){
								if(scFiledIdArr[i].equals(String.valueOf(js.getId()))){
									checkFlag = true;
									break;
								}
							}
						}
						map_d.put("checkFlag", checkFlag);
						list_d.add(map_d);
					}
					map.put("jsInfo", list_d);
					map.put("useExp", cpyUser.getUserExper());
				}
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
				map.put("roleName", spUser.getUserType());
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
			if(flag && !this.getLoginRoleName(request).equals("管理员")){
				String userScFiledIdStr = String.valueOf(request.getParameter("userScFiledIdStr"));
				flag = cum.updateInfoById(userId, 0, userScFiledIdStr, "", 0);
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
		String[] myAbility = Ability.getAbilityInfo("addUser,upUser,delUser", this.getLoginType(request), this.getLoginRoleName(request), this.getLoginRoleId(request)).split(",");
		request.setAttribute("delFlag", myAbility[0]);
		request.setAttribute("upFlag", myAbility[1]);
		request.setAttribute("addFlag", myAbility[2]);
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
		ApplyInfoManager am = (ApplyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
		String roleName = this.getLoginRoleName(request);
		Integer userId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(userId);
		Integer cpyId = 0;
		String msg = "";
		boolean abilityFlag = false;
		if(cUser != null){
			cpyId = cUser.getCpyInfoTb().getId();
			if(roleName.equals("管理员")){
				abilityFlag = true;
			}else{//不是管理员，就需要查看是否有增加的权限
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addUser");
			}
			if(abilityFlag){
				String userName = Transcode.unescape(request.getParameter("name"), request);
				String userNamePy =  Convert.getFirstSpell(userName);
				String account = CommonTools.getFinalStr(request.getParameter("account"));
				String userSex = CommonTools.getFinalStr(request.getParameter("sex"));
				String userEmail = CommonTools.getFinalStr(request.getParameter("email"));
				String userTel = CommonTools.getFinalStr(request.getParameter("tel"));
				String inDate = CommonTools.getFinalStr(request.getParameter("inDate"));
				String userScFiledIdStr =CommonTools.getFinalStr(request.getParameter("userScFiledIdStr"));
				Integer roleId = 0;
				String roleIdStr = CommonTools.getFinalStr(request.getParameter("roleId"));
				boolean flag_cpy_user = cum.listSpecInfoByAccount(account).size() > 0;
				boolean flag_app_user = am.listInfoByAccount(account).size() > 0;
				if(flag_cpy_user || flag_app_user){
					msg = "exist";
				}else{
					Integer cpyUserId = cum.addCpyUser(cpyId, userName, userNamePy, account, new MD5().calcMD5("123456"), userSex, 
							userEmail, userTel, inDate, userScFiledIdStr, "");
					if(cpyUserId > 0 && !roleIdStr.equals("")){
						//绑定员工角色
						String[] roleIdArr = roleIdStr.split(",");
						Integer roleIdLen = roleIdArr.length;
						for(Integer i = 0 ; i < roleIdLen ; i++){
							roleId = Integer.parseInt(roleIdArr[i]);
							crm.addRoleUser(roleId, cpyUserId);
						}
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
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = 0;
			if(cUser != null){
				cpyId = cUser.getCpyInfoTb().getId();
				Integer userLzStatus = Integer.parseInt(request.getParameter("userLzStatus"));
				Integer userYxStatus = Integer.parseInt(request.getParameter("userYxStatus"));
				Integer roleId = Integer.parseInt(request.getParameter("roleId"));
				String userNamePy = request.getParameter("userNamePy");
				Integer count = cum.getCountByOpt(cpyId, userLzStatus, userYxStatus, roleId, userNamePy);
				if(count > 0){
					Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("limit")), 10);//等同于pageSize
					Integer pageNo = CommonTools.getFinalInteger(request.getParameter("page"));//等同于pageNo
					List<CpyUserInfo> cUserList = cum.listPageInfoByOpt(cpyId, userLzStatus, userYxStatus, roleId, userNamePy, pageNo, pageSize);
					List<Object> list_u = new ArrayList<Object>();
					for(Iterator<CpyUserInfo> it = cUserList.iterator() ; it.hasNext();){
						CpyUserInfo cUser_a = it.next();
						Map<String,Object> map_u = new HashMap<String,Object>();
						map_u.put("userId", cUser_a.getId());
						map_u.put("name", cUser_a.getUserName());
						map_u.put("namePy", cUser_a.getUserNamePy());
						map_u.put("account", cUser_a.getUserAccount());
						map_u.put("sex", cUser_a.getUserSex());
						map_u.put("email", cUser_a.getUserEmail());
						map_u.put("inDate", cUser_a.getUserInDate());
						map_u.put("outDate", cUser_a.getUserOutDate());
						if(cUser_a.getId().equals(currLoginUserId)){
							map_u.put("selfFlag",true);
						}else{
							map_u.put("selfFlag",false);
						}
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
						String userScField = cUser_a.getUserScFiledId() == null ? "" : cUser_a.getUserScFiledId();
						String scFiledName = "";
						if(!userScField.equals("")){
							List<JsFiledInfoTb> jsList = jsm.listInfoByOpt(cpyId, userScField);
							for(Iterator<JsFiledInfoTb> it_1 = jsList.iterator() ; it_1.hasNext();){
								JsFiledInfoTb js = it_1.next();
								scFiledName += js.getZyName() + ",";
							}
							if(!scFiledName.equals("")){
								scFiledName = scFiledName.substring(0, scFiledName.length() - 1);
							}
						}
						map_u.put("scFiledName", scFiledName);
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
//					map.put("result", "success");
//					map.put("uInfo", list_u);
					map.put("msg", "success");
					map.put("data", list_u);
					map.put("count", count);
					map.put("code", 0);
				}else{
//					map.put("result", "noInfo");
					map.put("msg", "noInfo");
				}
			}else{
//				map.put("result", "fail");
				map.put("msg", "fail");
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
		Map<String,Integer> map = new HashMap<String,Integer>();
		Integer count = 0;
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = 0;
			if(cUser != null){
				cpyId = cUser.getCpyInfoTb().getId();
				Integer userLzStatus = Integer.parseInt(request.getParameter("userLzStatus"));
				Integer userYxStatus = Integer.parseInt(request.getParameter("userYxStatus"));
				Integer roleId = Integer.parseInt(request.getParameter("roleId"));
				String userNamePy = request.getParameter("userNamePy");
				count = cum.getCountByOpt(cpyId, userLzStatus, userYxStatus, roleId, userNamePy);
			}
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
		boolean abilityFlag = false;
		String msg = "";
		if(this.getLoginRoleName(request).equals("管理员")){//如果是管理员直接跳过（管理员直接拥有权限）
			abilityFlag = true;
		}else{
			//获取当前用户是否有修改权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upUser");
		}
		if(abilityFlag){
			Integer cpyUserId = Integer.parseInt(request.getParameter("userId"));
			Integer lzSatatus = Integer.parseInt(request.getParameter("lzSatatus"));
			String outDate = request.getParameter("outDate");
			Integer yxStatus = Integer.parseInt(request.getParameter("yxStatus"));
			if(cpyUserId.equals(this.getLoginUserId(request))){
				msg = "error";
			}else{
				boolean flag = cum.updateUserInfoById(cpyUserId, outDate, lzSatatus, yxStatus);
				if(flag){
					msg = "success";
				}else{
					msg = "error";
				}
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
	 * @date 2018-8-4 下午11:09:22
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
			if(sum.listInfoByAccount(account).size() == 0){
				Integer sUserId = sum.addSUser(account, new MD5().calcMD5("123456"), userName, userType);
				if(sUserId > 0){
					msg = "success";
				}else{
					msg = "error";
				}
			}else{
				msg = "exist";
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
	 * 根据用户编号获取用户详情（适合代理机构查询内部员工、平台用户查询平台员工）
	 * @description
	 * @author wm
	 * @date 2018-8-16 上午10:45:19
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getUserDetailData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String loginType = this.getLoginType(request);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		SuperUserManager sum = (SuperUserManager)  AppFactory.instance(null).getApp(Constants.WEB_SUPER_USER_INFO);
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		Integer userId = CommonTools.getFinalInteger(request.getParameter("userId"));
		if(loginType.equals("cpyUser")){//代理机构
			CpyUserInfo cpyUser = cum.getEntityById(userId);
			if(cpyUser != null){
				Integer specUserCpyId = cpyUser.getCpyInfoTb().getId();
				Integer currLoginUserCpyId = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb().getId();
				if(specUserCpyId.equals(currLoginUserCpyId)){//只能查询自己代理机构的员工
					//获取该代理机构下所有的角色列表
					List<CpyRoleInfoTb> crList = crm.listInfoByCpyId(currLoginUserCpyId);
					List<Object> list_r = new ArrayList<Object>();
					//获取用户角色
					List<CpyRoleUserInfoTb> ruList = crm.listInfoByUserId(cpyUser.getId());
					for(Iterator<CpyRoleInfoTb> it = crList.iterator() ; it.hasNext();){
						CpyRoleInfoTb cr = it.next();
						Map<String,Object> map_r = new HashMap<String,Object>();
						map_r.put("roleId", cr.getId());
						map_r.put("roleName", cr.getRoleName());
						if(ruList.size() == 0){
							map_r.put("checked", false);
						}else{
							for(Iterator<CpyRoleUserInfoTb> it_1 = ruList.iterator() ; it_1.hasNext();){
								CpyRoleUserInfoTb cru = it_1.next();
								if(cru.getCpyRoleInfoTb().getId().equals(cr.getId())){
									map_r.put("checked", true);
									break;
								}else{
									map_r.put("checked", false);
								}
							}
						}
						list_r.add(map_r);
					}
					map.put("result", "success");
					map.put("id", cpyUser.getId());
					map.put("name", cpyUser.getUserName());
					map.put("namePy", cpyUser.getUserNamePy());
					map.put("account", cpyUser.getUserAccount());
					map.put("sex", cpyUser.getUserSex());
					map.put("email", cpyUser.getUserEmail());
					map.put("tel", cpyUser.getUserTel());
					map.put("inDate", cpyUser.getUserInDate());
					map.put("outDate", cpyUser.getUserOutDate());
					map.put("lzStatus", cpyUser.getUserLzStatus());
					map.put("yxStatus", cpyUser.getUserYxStatus());
					map.put("roleNameInfo", list_r);
					map.put("zxNum", cpyUser.getUserZxNum());
					map.put("scFiled", cpyUser.getUserScFiledId());
					Integer cpyId = cum.getEntityById(userId).getCpyInfoTb().getId();
					String userScFieldId = cpyUser.getUserScFiledId();
					String scFiledName = "";
					if(!userScFieldId.equals("")){
						List<JsFiledInfoTb> jsList = jsm.listInfoByOpt(cpyId, userScFieldId);
						for(Iterator<JsFiledInfoTb> it = jsList.iterator() ; it.hasNext();){
							JsFiledInfoTb js = it.next();
							scFiledName += js.getZyName() + ",";
						}
						if(!scFiledName.equals("")){
							scFiledName = scFiledName.substring(0, scFiledName.length() - 1);
						}
					}
					map.put("scFiledName", scFiledName);
					map.put("useExp", cpyUser.getUserExper());
					map.put("lastLoginDate", cpyUser.getLastLoginDate());
				}else{
					map.put("result", "noUser");//查无此人
				}
				
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
				map.put("roleName", spUser.getUserType());
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
	 * 重置用户密码
	 * @author Administrator
	 * @date 2018-8-16 下午10:36:57
	 * @ModifiedBy
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUserPass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String loginType = this.getLoginType(request);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		SuperUserManager sum = (SuperUserManager)  AppFactory.instance(null).getApp(Constants.WEB_SUPER_USER_INFO);
		Map<String,String> map = new HashMap<String,String>();
		boolean abilityFlag = false;
		String msg = "";
		if(this.getLoginRoleName(request).equals("管理员")){//如果是管理员直接跳过（管理员直接拥有权限）
			abilityFlag = true;
		}else{
			//获取当前用户是否有修改权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upUser");
		}
		if(abilityFlag){
			Integer selUserId = CommonTools.getFinalInteger(request.getParameter("userId"));
			if(loginType.equals("cpyUser")){
				//需要修改的用户必须和自己是同一代理机构
				CpyUserInfo cUser = cum.getEntityById(selUserId);
				if(cUser != null){
					CpyUserInfo currCuser = cum.getEntityById(this.getLoginUserId(request));
					if(cUser.getCpyInfoTb().getId().equals(currCuser.getCpyInfoTb().getId())){
						cum.updatePassById(selUserId, "123456");
						msg = "success";
					}else{
						msg = "error";
					}
				}else{
					msg = "error";
				}
			}else if(loginType.equals("spUser")){
				sum.updateSUserById(selUserId, "123456", "");
				msg = "success";
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
	*  修改用户身份(管理员修改)
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-18 下午05:54:46
	*  @param mapping
	*  @param form
	*  @param request
	*  @param response
	*  @return
	*  @throws Exception
	 */
	public ActionForward updateUserRoleInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String loginType = this.getLoginType(request);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		CpyRoleInfoManager crm = (CpyRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_ROLE_INFO);
		Integer selUserId = CommonTools.getFinalInteger(request.getParameter("userId"));
		String selRoleIdStr = CommonTools.getFinalStr(request.getParameter("selRoleId"));
		String msg = "";
		boolean abilityFlag = false;
		if(selUserId.equals(0) || selRoleIdStr.equals("")){
			msg = "error";
		}else if(loginType.equals("cpyUser")){
			if(this.getLoginRoleName(request).equals("管理员")){//如果是管理员直接跳过（管理员直接拥有权限）
				abilityFlag = true;
			}else{
				//获取当前用户是否有修改权限
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upUser");
			}
			if(abilityFlag){
				Integer currLoginUserCpyId = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb().getId();
				Integer selUserCpyId = cum.getEntityById(selUserId).getCpyInfoTb().getId();
				if(currLoginUserCpyId.equals(selUserCpyId)){
					String[] roleIdArr = selRoleIdStr.split(",");
					//先删除之前绑定的关系
					List<CpyRoleUserInfoTb> cruList = crm.listInfoByUserId(selUserId);
					for(Iterator<CpyRoleUserInfoTb> it = cruList.iterator() ; it.hasNext();){
						CpyRoleUserInfoTb cru = it.next();
						crm.delRoleUserByOpt(0, cru.getCpyUserInfo().getId());
					}
					//重新增加
					for(Integer i = 0 ; i < roleIdArr.length ; i++){
						Integer roleId = Integer.parseInt(roleIdArr[i]);
						crm.addRoleUser(roleId, selUserId);
					}
					msg = "success";
				}else{
					msg = "error";
				}
			}else{
				msg = "noAbility";
			}
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
	 * 检查代理机构员工存在状态
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-18 下午10:17:33
	*  @param mapping
	*  @param form
	*  @param request
	*  @param response
	*  @return
	*  @throws Exception
	 */
	public ActionForward checkExistInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		ApplyInfoManager am = (ApplyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
		boolean existFlag = false;
		String account = request.getParameter("account");
		boolean flag_cpy_user = cum.listSpecInfoByAccount(account).size() > 0;
		boolean flag_app_user = am.listInfoByAccount(account).size() > 0;
		if(flag_cpy_user || flag_app_user){
			existFlag = true;
		}
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		map.put("result", existFlag);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
}