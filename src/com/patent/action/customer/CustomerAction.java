/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.customer;

import java.io.IOException;
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
import com.patent.module.CustomerFmrInfoTb;
import com.patent.module.CustomerInfoTb;
import com.patent.module.CustomerLxrInfoTb;
import com.patent.page.PageConst;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.CustomerInfoManager;
import com.patent.tools.CommonTools;
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
	
	/**
	 * 根据条件分页获取代理机构下客户列表
	 * @description
	 * @author wm
	 * @date 2018-8-20 下午04:11:49
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCusPageInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list_d = new ArrayList<Object>();
		boolean abilityFlag = false;
		if(this.getLoginType(request).equals("cpyUser")){
			if(this.getLoginRoleName(request).equals("管理员")){
				abilityFlag = true;
			}else{
				//需要查看当前用户有无增加权限111
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "listCus");
			}
			if(abilityFlag){
				CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
				Integer cpyId = cUser.getCpyInfoTb().getId();
				String cusName = Transcode.unescape(request.getParameter("cusName"), request);
				Integer count = cm.getCountByOpt(cpyId, cusName);
				if(count > 0){
					Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("limit")), 10);//等同于pageSize
					Integer pageNo = CommonTools.getFinalInteger(request.getParameter("page"));//等同于pageNo
					List<CustomerInfoTb> cList = cm.listPageInfoByOpt(cpyId, cusName, pageNo, pageSize);
					for(Iterator<CustomerInfoTb> it = cList.iterator() ; it.hasNext();){
						CustomerInfoTb cus = it.next();
						Map<String,Object> map_d = new HashMap<String,Object>();
						map_d.put("id", cus.getId());
						String cusType = cus.getCusType();
						String cusTypeChi = "gr";
						if(cusType.equals("dzyx")){
							cusTypeChi = "大专院校";
						}else if(cusType.equals("kydw")){
							cusTypeChi = "科研单位";
						}else if(cusType.equals("gkqy")){
							cusTypeChi = "工矿企业";
						}else if(cusType.equals("sydw")){
							cusTypeChi = "事业单位";
						}
						map_d.put("cusType", cusTypeChi);
						map_d.put("cusName", cus.getCusName());
						map_d.put("cusICard", cus.getCusICard());
						map_d.put("cusAddress", cus.getCusAddress());
						map_d.put("cusZip", cus.getCusZip());
						list_d.add(map_d);
					}
					map.put("msg", "success");
					map.put("data", list_d);
					map.put("count", count);
					map.put("code", 0);
				}else{
					map.put("msg", "暂无记录");
				}
			}else{
				map.put("msg", "noAbility");
			}
			
		}else{
			map.put("msg", "error");
		}
		this.getJsonPkg(map, response);
        return null;
	}
	
	/**
	 * 获取指定客户的详细信息(针对代理机构员工开放)
	 * @description
	 * @author wm
	 * @date 2018-9-12 上午09:38:47
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCusDetailInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		String opt = CommonTools.getFinalStr("opt", request);//cus,lxr,fmr
		String msg = "error";
		Integer cusId = CommonTools.getFinalInteger("cusId", request);
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			if(opt.equals("cus")){
				List<CustomerInfoTb> cList = cm.listInfoById(cpyId, cusId);
				if(cList.size() > 0){
					msg = "success";
					CustomerInfoTb cInfo = cList.get(0);
					map.put("cusId", cInfo.getId());
					map.put("cusType", cInfo.getCusType());
					map.put("cusName", cInfo.getCusName());
					map.put("cusiCard", cInfo.getCusICard());
					map.put("cusAddress", cInfo.getCusAddress());
					map.put("cusZip", cInfo.getCusZip());
				}else{
					msg = "noInfo";
				}
			}else if(opt.equals("lxr")){
				List<CustomerLxrInfoTb> clxrList = cm.listLxrInfoByOpt(cusId,cpyId);
				List<Object> list_lxr = new ArrayList<Object>();
				if(clxrList.size() > 0){
					msg = "success";
					for(Iterator<CustomerLxrInfoTb> it = clxrList.iterator() ; it.hasNext();){
						CustomerLxrInfoTb lxr = it.next();
						Map<String,Object> map_d = new HashMap<String,Object>();
						map_d.put("lxrId", lxr.getId());
						map_d.put("lxrName", lxr.getCusLxrName());
						map_d.put("lxrTel", lxr.getCusLxrTel());
						map_d.put("lxrEmail", lxr.getCusLxrEmail());
						list_lxr.add(map_d);
					}
					map.put("lxrInfo", list_lxr);
				}else{
					msg = "noInfo";
				}
			}else if(opt.equals("fmr")){
				List<CustomerFmrInfoTb> cfmrList = cm.listFmrInfoByCusId(cusId,cpyId);
				List<Object> list_fmr = new ArrayList<Object>();
				if(cfmrList.size() > 0){
					msg = "success";
					for(Iterator<CustomerFmrInfoTb> it = cfmrList.iterator() ; it.hasNext();){
						CustomerFmrInfoTb fmr = it.next();
						Map<String,Object> map_d = new HashMap<String,Object>();
						map_d.put("fmrId", fmr.getId());
						map_d.put("fmrName", fmr.getCusFmrName());
						map_d.put("fmrTel", fmr.getCusFmrTel());
						map_d.put("fmrEmail", fmr.getCusFxrEmail());
						map_d.put("fmriCard", fmr.getCusFmrICard());
						list_fmr.add(map_d);
					}
					map.put("fmrInfo", list_fmr);
				}else{
					msg = "noInfo";
				}
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取联系人数据
	 * @description
	 * @author wm
	 * @date 2018-8-20 下午04:25:33
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCusLxrInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list_d = new ArrayList<Object>();
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer cusId = CommonTools.getFinalInteger(request.getParameter("cusId"));
			List<CustomerInfoTb> cList = cm.listInfoById(cpyId, cusId);
			if(cList.size() > 0){
				List<CustomerLxrInfoTb> clxrList = cm.listLxrInfoByOpt(cusId,cpyId);
				if(clxrList.size() > 0){
					for(Iterator<CustomerLxrInfoTb> it = clxrList.iterator() ; it.hasNext();){
						CustomerLxrInfoTb lxr = it.next();
						Map<String,Object> map_d = new HashMap<String,Object>();
						map_d.put("lxrId", lxr.getId());
						map_d.put("lxrName", lxr.getCusLxrName());
						map_d.put("lxrTel", lxr.getCusLxrTel());
						map_d.put("lxrEmail", lxr.getCusLxrEmail());
						list_d.add(map_d);
					}
					map.put("result", "success");
					map.put("lxrInfo", list_d);
				}else{
					map.put("result", "noInfo");
				}
			}else{
				map.put("result", "error");
			}
		}else{
			map.put("result", "error");
		}
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取发明人数据
	 * @description
	 * @author wm
	 * @date 2018-8-20 下午04:25:57
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCusFmrInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list_d = new ArrayList<Object>();
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer cusId = CommonTools.getFinalInteger(request.getParameter("cusId"));
			List<CustomerInfoTb> cList = cm.listInfoById(cpyId, cusId);
			if(cList.size() > 0){
				List<CustomerFmrInfoTb> cfmrList = cm.listFmrInfoByCusId(cusId,cpyId);
				if(cfmrList.size() > 0){
					for(Iterator<CustomerFmrInfoTb> it = cfmrList.iterator() ; it.hasNext();){
						CustomerFmrInfoTb fmr = it.next();
						Map<String,Object> map_d = new HashMap<String,Object>();
						map_d.put("fmrId", fmr.getId());
						map_d.put("fmrName", fmr.getCusFmrName());
						map_d.put("fmrTel", fmr.getCusFmrTel());
						map_d.put("fmrEmail", fmr.getCusFxrEmail());
						map_d.put("fmriCard", fmr.getCusFmrICard());
						list_d.add(map_d);
					}
					map.put("result", "success");
					map.put("fmrInfo", list_d);
				}else{
					map.put("result", "noInfo");
				}
			}else{
				map.put("result", "error");
			}
		}else{
			map.put("result", "error");
		}
		this.getJsonPkg(map, response);
        return null;
	}
	
	/**
	 * 增加客户信息主信息
	 * @description
	 * @author wm
	 * @date 2018-8-20 下午05:18:19
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addCusData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean abilityFlag = false;
		String msg = "error";
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addCus");
		}
		if(abilityFlag){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			String cusName = Transcode.unescape(request.getParameter("cusName"), request);
			String cusType = CommonTools.getFinalStr(request.getParameter("cusType"));
			String cusiCard = CommonTools.getFinalStr(request.getParameter("cusiCard"));
			String cusAddress = Transcode.unescape(request.getParameter("cusAddress"), request);
			String cusZip = CommonTools.getFinalStr(request.getParameter("cusZip"));
			Integer cusId = cm.addCusInfo(cusType, cusName, cusiCard, cusAddress, cusZip, cpyId);
			if(cusId > 0){
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
	 * 增加客户联系人
	 * @description
	 * @author wm
	 * @date 2018-8-20 下午05:22:29
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addLxrData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean abilityFlag = false;
		String msg = "error";
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addCus");
		}
		if(abilityFlag){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer cusId = CommonTools.getFinalInteger(request.getParameter("cusId"));
			List<CustomerInfoTb> cusList = cm.listInfoById(cpyId, cusId);
			if(cusList.size() > 0){
				String lxrName = Transcode.unescape(request.getParameter("lxrName"), request);
				String lxrTel = CommonTools.getFinalStr(request.getParameter("lxrTel"));
				String lxrEmail = CommonTools.getFinalStr(request.getParameter("lxrEmail"));
				Integer lxrId = cm.addCusLxrInfo(cusId, lxrName, lxrTel, lxrEmail);
				if(lxrId > 0){
					msg = "success";
				}
			}
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 增加发明人
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午09:11:56
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFmrData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean abilityFlag = false;
		String msg = "error";
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addCus");
		}
		if(abilityFlag){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer cusId = CommonTools.getFinalInteger(request.getParameter("cusId"));
			List<CustomerInfoTb> cusList = cm.listInfoById(cpyId, cusId);
			if(cusList.size() > 0){
				String fmrName = Transcode.unescape(request.getParameter("fmrName"), request);
				String fmrTel = CommonTools.getFinalStr(request.getParameter("fmrTel"));
				String fmrEmail = CommonTools.getFinalStr(request.getParameter("fmrEmail"));
				String fmriCard = CommonTools.getFinalStr(request.getParameter("fmriCard"));
				Integer fmrId = cm.addCusFmrInfo(cusId, fmrName, fmriCard, fmrTel, fmrEmail);
				if(fmrId > 0){
					msg = "success";
				}
			}
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 根据客户编号获取联系人、发明人详细信息
	 * @description
	 * @author wm
	 * @date 2018-9-12 下午04:14:29
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCusDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		String opt = CommonTools.getFinalStr("opt", request);//cus,lxr,fmr
		String msg = "error";
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			if(opt.equals("lxr")){
				Integer lxrId = CommonTools.getFinalInteger("lxrId", request);
				List<CustomerLxrInfoTb> clxrList = cm.listLxrInfoByCusId(lxrId, cpyId);
				if(clxrList.size() > 0){
					msg = "success";
					CustomerLxrInfoTb lxr = clxrList.get(0);
					map.put("lxrId", lxr.getId());
					map.put("lxrName", lxr.getCusLxrName());
					map.put("lxrTel", lxr.getCusLxrTel());
					map.put("lxrEmail", lxr.getCusLxrEmail());
				}else{
					msg = "noInfo";
				}
			}else if(opt.equals("fmr")){
				Integer fmrId = CommonTools.getFinalInteger("fmrId", request);
				List<CustomerFmrInfoTb> cfmrList = cm.listFmrInfoByFmrId(fmrId, cpyId);
				if(cfmrList.size() > 0){
					msg = "success";
					CustomerFmrInfoTb fmr = cfmrList.get(0);
					map.put("fmrId", fmr.getId());
					map.put("fmrName", fmr.getCusFmrName());
					map.put("fmrTel", fmr.getCusFmrTel());
					map.put("fmrEmail", fmr.getCusFxrEmail());
					map.put("fmriCard", fmr.getCusFmrICard());
				}else{
					msg = "noInfo";
				}
			}
			
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 修改客户基本信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午09:12:42
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upCusData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean abilityFlag = false;
		String msg = "error";
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upCus");
		}
		if(abilityFlag){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer cusId = CommonTools.getFinalInteger(request.getParameter("cusId"));
			String cusName = Transcode.unescape(request.getParameter("cusName"), request);
			String cusType = CommonTools.getFinalStr(request.getParameter("cusType"));
			String cusiCard = CommonTools.getFinalStr(request.getParameter("cusiCard"));
			String cusAddress = Transcode.unescape(request.getParameter("cusAddress"), request);
			String cusZip = CommonTools.getFinalStr(request.getParameter("cusZip"));
			boolean falg  = cm.upCusInfo(cusId, cpyId, cusType, cusName, cusiCard, cusAddress, cusZip);
			if(falg){
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
	 * 修改客户的联系人信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午09:18:54
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upLxrData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean abilityFlag = false;
		String msg = "error";
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upCus");
		}
		if(abilityFlag){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer lxrId = CommonTools.getFinalInteger(request.getParameter("lxrId"));
			List<CustomerLxrInfoTb> lxrList = cm.listLxrInfoByCusId(lxrId, cpyId);
			if(lxrList.size() > 0){
				String lxrName = Transcode.unescape(request.getParameter("lxrName"), request);
				String lxrTel = CommonTools.getFinalStr(request.getParameter("lxrTel"));
				String lxrEmail = CommonTools.getFinalStr(request.getParameter("lxrEmail"));
				boolean flag = cm.upCusLxrById(lxrId, lxrName, lxrTel, lxrEmail);
				if(flag){
					msg = "success";
				}
			}
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 修改客户的发明人信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午09:19:04
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upFmrData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean abilityFlag = false;
		String msg = "error";
		if(this.getLoginRoleName(request).equals("管理员")){
			abilityFlag = true;
		}else{
			//需要查看当前用户有无增加权限
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upCus");
		}
		if(abilityFlag){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			Integer fmrId = CommonTools.getFinalInteger(request.getParameter("fmrId"));
			List<CustomerFmrInfoTb> fmrList = cm.listFmrInfoByFmrId(fmrId, cpyId);
			if(fmrList.size() > 0){
				String fmrName = Transcode.unescape(request.getParameter("fmrName"), request);
				String fmrTel = CommonTools.getFinalStr(request.getParameter("fmrTel"));
				String fmrEmail = CommonTools.getFinalStr(request.getParameter("fmrEmail"));
				String fmriCard = CommonTools.getFinalStr(request.getParameter("fmriCard"));
				boolean flag  = cm.upCusFmrById(fmrId, fmrName, fmriCard, fmrTel, fmrEmail);
				if(flag){
					msg = "success";
				}
			}
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取指定申请人（可以多个）下的发明人或者联系人
	 * @description
	 * @author Administrator
	 * @date 2018-9-28 下午05:48:16
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCusFmOrLxrInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo cUser = cum.getEntityById(currLoginUserId);
			Integer cpyId = cUser.getCpyInfoTb().getId();
			String opt = CommonTools.getFinalStr("opt", request);//add:增加专利时，edit：修改专利
			String option = CommonTools.getFinalStr("option", request);//fmr:发明人，lxr:联系人
			String cusIdStr = CommonTools.getFinalStr(request.getParameter("cusIdStr"));
			List<Object> list_final = new ArrayList<Object>();
			if(!cusIdStr.equals("")){
				String[] cusIdArr = cusIdStr.split(",");
				for(Integer i = 0 ; i < cusIdArr.length ; i++){
					Map<String,Object> map_a = new HashMap<String,Object>();
					List<Object> list_d = new ArrayList<Object>();
					Integer cusId = Integer.parseInt(cusIdArr[i]);
					List<CustomerInfoTb> cList = cm.listInfoById(cpyId, cusId);
					if(cList.size() > 0){
						CustomerInfoTb cus = cList.get(0);
						//申请人信息
						map_a.put("sqrId", cus.getId());
						map_a.put("sqrName", cus.getCusName());
						if(option.equals("fmr")){
							List<CustomerFmrInfoTb> cfmrList = cm.listFmrInfoByCusId(cusId,cpyId);
							//发明人信息
							if(cfmrList.size() > 0){
								for(Iterator<CustomerFmrInfoTb> it = cfmrList.iterator() ; it.hasNext();){
									CustomerFmrInfoTb fmr = it.next();
									Map<String,Object> map_d = new HashMap<String,Object>();
									map_d.put("fmrId", fmr.getId());
									map_d.put("fmrName", fmr.getCusFmrName());
									list_d.add(map_d);
								}
								map_a.put("result", "success");
								map_a.put("fmrInfo", list_d);
							}
						}else if(option.equals("lxr")){
							List<CustomerLxrInfoTb> lxrList = cm.listLxrInfoByOpt(cusId, cpyId);
							if(lxrList.size() > 0){
								for(Iterator<CustomerLxrInfoTb> it = lxrList.iterator() ; it.hasNext();){
									CustomerLxrInfoTb lxr = it.next();
									Map<String,Object> map_d = new HashMap<String,Object>();
									map_d.put("lxrId", lxr.getId());
									map_d.put("lxrName", lxr.getCusLxrName());
									list_d.add(map_d);
								}
								map_a.put("result", "success");
								map_a.put("lxrInfo", list_d);
							}
						}
						list_final.add(map_a);
					}
				}
				map.put("result", list_final);
			}
		}
		this.getJsonPkg(map, response);
        return null;
	}
}