/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.jfield;

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
import com.patent.module.CpyUserInfo;
import com.patent.module.JsFiledInfoTb;
import com.patent.page.PageConst;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.JsFiledInfoManager;
import com.patent.util.Constants;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class JsFieldManagerAction extends DispatchAction {
	
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
	 * 导向专业区域列表页面
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:20:11
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward goJfPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		String[] myAbility = Ability.getAbilityInfo("addJf,upJf,delJf", this.getLoginType(request), this.getLoginRoleName(request), this.getLoginRoleId(request)).split(",");
		request.setAttribute("delFlag", myAbility[0]);
		request.setAttribute("upFlag", myAbility[1]);
		request.setAttribute("addFlag", myAbility[2]);
		return mapping.findForward("jsPage");
	}
	
	/**
	 * 获取指定代理机构下的专业列表
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午10:43:58
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getJfData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer loginUserId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(loginUserId);
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		if(cUser != null){
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer count = jsm.getCountByCpyId(cpyId);
			if(count > 0){
				List<JsFiledInfoTb> jfList = jsm.listInfoByOpt(cpyId, "");
				List<Object> list_d = new ArrayList<Object>();
				for(Iterator<JsFiledInfoTb> it = jfList.iterator() ; it.hasNext();){
					JsFiledInfoTb jf = it.next();
					Map<String,Object> map_d = new HashMap<String,Object>();
					map_d.put("id", jf.getId());
					map_d.put("zyName", jf.getZyName());
					map_d.put("zyProfile", jf.getZyProfile());
					list_d.add(map_d);
				}
				msg = "success";
				map.put("jsInfo", list_d);
			}else{
				msg = "noInfo";
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
	 * 分页获取专业区域列表
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:20:44
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getPageJfData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer loginUserId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(loginUserId);
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		if(cUser != null){
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer count = jsm.getCountByCpyId(cpyId);
			if(count > 0){
				Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("pageSize")), 10);
				Integer pageCount = PageConst.getPageCount(count, pageSize);
				Integer pageNo = PageConst.getPageNo(String.valueOf(request.getParameter("pageNo")), pageCount);
				List<JsFiledInfoTb> jfList = jsm.listPageInfoByCpyId(cpyId, pageNo, pageSize);
				List<Object> list_d = new ArrayList<Object>();
				for(Iterator<JsFiledInfoTb> it = jfList.iterator() ; it.hasNext();){
					JsFiledInfoTb jf = it.next();
					Map<String,Object> map_d = new HashMap<String,Object>();
					map_d.put("id", jf.getId());
					map_d.put("zyName", jf.getZyName());
					map_d.put("zyProfile", jf.getZyProfile());
					list_d.add(map_d);
				}
				msg = "success";
				map.put("jsInfo", list_d);
			}else{
				msg = "noInfo";
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
	 * 获取专业区域记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:21:30
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getJfCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer loginUserId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(loginUserId);
		Integer count = 0;
		Map<String,Integer> map = new HashMap<String,Integer>();
		if(cUser != null){
			Integer cpyId = cUser.getCpyInfoTb().getId();
			count = jsm.getCountByCpyId(cpyId);
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
	 * 增加专业区域数据
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:21:46
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward addJf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer loginUserId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(loginUserId);
		Map<String,String> map = new HashMap<String,String>();
		boolean abilityFlag = false;
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addJf");
		}
		if(abilityFlag){
			if(cUser != null){
				Integer cpyId = cUser.getCpyInfoTb().getId();
				String zyName = Transcode.unescape(request.getParameter("zyName"), request);
				String zyProfile = Transcode.unescape(request.getParameter("zyProfile"), request);
				if(jsm.listInfoByOpt_1(cpyId, zyName).size() > 0){
					map.put("result", "exist");
				}else{
					Integer jfId = jsm.addJF(zyName, cpyId,zyProfile);
					if(jfId > 0){
						map.put("result", "success");
					}else{
						map.put("result", "error");
					}
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
	 * 删除专业区域数据
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:22:01
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward delJf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer loginUserId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(loginUserId);
		Map<String,String> map = new HashMap<String,String>();
		boolean abilityFlag = false;
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "delJf");
		}
		if(abilityFlag){
			if(cUser != null){
				Integer cpyId = cUser.getCpyInfoTb().getId();
				Integer jfId = Integer.parseInt(request.getParameter("jfId"));
				if(jsm.listInfoByOpt(cpyId, String.valueOf(jfId)).size() > 0){
					//查看需要删除的专业是否有人已绑定
					if(cum.listInfoByOpt(cpyId, jfId).size() > 0){
						map.put("result", "existBind");//已经被员工绑定
					}else{
						boolean flag = jsm.delJfById(jfId);
						if(flag){
							map.put("result", "success");
						}else{
							map.put("result", "error");
						}
					}
				}else{
					map.put("result", "fail");//捣乱
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
	 * 修改专业区域数据
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:22:23
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward updateJf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer loginUserId = this.getLoginUserId(request);
		CpyUserInfo cUser = cum.getEntityById(loginUserId);
		Map<String,String> map = new HashMap<String,String>();
		boolean abilityFlag = false;
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upJf");
		}
		if(abilityFlag){
			if(cUser != null){
				Integer cpyId = cUser.getCpyInfoTb().getId();
				Integer jfId = Integer.parseInt(request.getParameter("jfId"));
				String zyName = Transcode.unescape(request.getParameter("zyName"), request);
				String zyProfile = Transcode.unescape(request.getParameter("zyProfile"), request);
				List<JsFiledInfoTb> jfList = jsm.listInfoByOpt(cpyId, String.valueOf(jfId));
				if(jfList.size() > 0){
					String zyName_db = jfList.get(0).getZyName();
					if(zyName.equals(zyName_db)){
						map.put("result", "success");//不修改数据库
					}else{
						//需要判断是否重复
						if(jsm.listInfoByOpt_1(cpyId, zyName).size() > 0){
							map.put("result", "exist");//名字重名
						}else{
							boolean flag = jsm.updateJfById(jfId, zyName,zyProfile);
							if(flag){
								map.put("result", "success");
							}else{
								map.put("result", "error");
							}
						}
					}
				}else{
					map.put("result", "fail");//捣乱
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
}