/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.zd;

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
import com.patent.factory.AppFactory;
import com.patent.module.CpyUserInfo;
import com.patent.module.FeeTypeInfoTb;
import com.patent.module.ZlajQqsInfoTb;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.ZlajFeeInfoManager;
import com.patent.service.ZlajQqsInfoManager;
import com.patent.service.ZlajZdSubmitManager;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;
import com.patent.util.Constants;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 03-14-2019
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class ZdSubmitQqsAction extends DispatchAction {
	
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
	 * 获取请求书数据列表
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午11:51:45
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getQqsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajQqsInfoManager zqm = (ZlajQqsInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_QQS_INFO);
		String typeEng = CommonTools.getFinalStr("typeEng", request);
		String msg = "error";
		Map<String,Object> map = new HashMap<String,Object>();
		if(!typeEng.equals("")){
			List<ZlajQqsInfoTb> zqList = zqm.listInfoByTypeEng(typeEng);
			if(zqList.size() > 0){
				msg = "success";
				List<Object> list_d = new ArrayList<Object>();
				for(Iterator<ZlajQqsInfoTb> it = zqList.iterator() ; it.hasNext();){
					ZlajQqsInfoTb zq = it.next();
					Map<String,Object> map_d = new HashMap<String,Object>();
					map_d.put("qqsId", zq.getId());
					map_d.put("qqsName", zq.getZlQqs());
					map_d.put("feePrice", zq.getFeePrice());
					list_d.add(map_d);
				}
				map.put("qqsInfo", list_d);
			}else{
				msg = "noInfo";
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取指定请求书的费用
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 下午03:43:43
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getQqsPrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajQqsInfoManager zqm = (ZlajQqsInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_QQS_INFO);
		Integer qqsId = CommonTools.getFinalInteger("qqsId", request);
		String msg = "error";
		Map<String,Object> map = new HashMap<String,Object>();
		if(qqsId > 0){
			ZlajQqsInfoTb zq = zqm.getSpecInfoById(qqsId);
			if(zq != null){
				msg = "success";
				map.put("qqsPrice", zq.getFeePrice());
			}else{
				msg = "noInfo";
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 增加主动提交
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 下午03:44:54
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addZdSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajQqsInfoManager zqm = (ZlajQqsInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_QQS_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		ZlajZdSubmitManager zzsm = (ZlajZdSubmitManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_ZD_SUBMIT_INFO);
		ZlajFeeInfoManager fm = (ZlajFeeInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_FEE_INFO);
		Integer zlId = CommonTools.getFinalInteger("zlId", request);
		Integer qqsId = CommonTools.getFinalInteger("qqsId", request);
		String upFilePath = CommonTools.getFinalStr("upFilePath", request);
		
		Integer upUserId = this.getLoginUserId(request);
		Integer cpyId = 0;
		boolean abilityFlag = false;
		if(this.getLoginType(request).equals("cpyUser")){//代理机构下
			CpyUserInfo cpyUser = cum.getEntityById(upUserId);
			if(cpyUser != null){
				cpyId = cpyUser.getCpyInfoTb().getId();
				if(this.getLoginRoleName(request).equals("管理员")){
					abilityFlag = true;
				}else{
					////获取当前用户是否有主动提交的权限
					abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addZdSubmit");
				}
				if(abilityFlag){
					ZlajQqsInfoTb qqs = zqm.getSpecInfoById(qqsId);
					if(qqs != null){
						String feeTypeName = qqs.getFeeTypeName();
						Double feePrice = qqs.getFeePrice();
						if(feePrice > 0){
							List<FeeTypeInfoTb> ftList = fm.listInfoByName(feeTypeName);
							if(ftList.size() > 0){
								Integer feeTypeId = ftList.get(0).getId();
							}
						}
					}
					zzsm.addZZS(qqsId, upUserId, zlId, upFilePath, CurrentTime.getStringDate(), cpyId);
//					fm.addZLFee(zlId, appUserId, geeTypeId, feePrice, feeRate, feeEndDateCpy, feeEndDateGf, feeRemark, feeStatus, cpyId, 
//							djStatus, feeJnDate, feeUpZd, tzsArea, yearFeeNo, feeRange, addStatus, backDate, feeBatchNo, bankSerialNo, fpDate, fpNo, feeTxType, tzsTx)
				}
			}
		}
		String msg = "error";
		Map<String,Object> map = new HashMap<String,Object>();
		if(qqsId > 0){
			ZlajQqsInfoTb zq = zqm.getSpecInfoById(qqsId);
			if(zq != null){
				msg = "success";
				map.put("qqsPrice", zq.getFeePrice());
			}else{
				msg = "noInfo";
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
}