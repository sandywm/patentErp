/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.zl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
import com.patent.util.WebUrl;
import com.patent.action.base.Transcode;
import com.patent.factory.AppFactory;
import com.patent.module.CpyInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.module.CustomerFmrInfoTb;
import com.patent.module.CustomerInfoTb;
import com.patent.module.CustomerLxrInfoTb;
import com.patent.module.JsFiledInfoTb;
import com.patent.module.ZlajEwyqInfoTb;
import com.patent.module.ZlajFeeInfoTb;
import com.patent.module.ZlajFjInfoTb;
import com.patent.module.ZlajLcInfoTb;
import com.patent.module.ZlajLcMxInfoTb;
import com.patent.module.ZlajMainInfoTb;
import com.patent.module.ZlajTzsInfoTb;
import com.patent.page.PageConst;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.CustomerInfoManager;
import com.patent.service.JsFiledInfoManager;
import com.patent.service.MailInfoManager;
import com.patent.service.ZlajEwyqInfoManager;
import com.patent.service.ZlajFeeInfoManager;
import com.patent.service.ZlajFjInfoManager;
import com.patent.service.ZlajLcInfoManager;
import com.patent.service.ZlajLcMxInfoManager;
import com.patent.service.ZlajMainInfoManager;
import com.patent.service.ZlajTzsInfoManager;
import com.patent.tools.CommonTools;
import com.patent.tools.Convert;
import com.patent.tools.CurrentTime;
import com.patent.util.Constants;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 08-25-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class ZlMainAction extends DispatchAction {
	
	
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
	 * 导向专利页面
	 * @description
	 * @author wm
	 * @date 2018-8-25 下午05:34:01
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward goZlPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String[] myAbility = Ability.getAbilityInfo("addZl,upZl,delZl", this.getLoginType(request), this.getLoginRoleName(request), this.getLoginRoleId(request)).split(",");
		request.setAttribute("delFlag", myAbility[0]);
		request.setAttribute("upFlag", myAbility[1]);
		request.setAttribute("addFlag", myAbility[2]);
		return mapping.findForward("zlPage");
	}
	
	/**
	 * 根据条件分页获取专利信息列表(只对代理机构和平台用户开放)
	 * @description
	 * @author wm
	 * @date 2018-8-25 下午05:50:50
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPageZlData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		Integer cpyId = CommonTools.getFinalInteger("cpyId",request);
		Integer stopStatus = CommonTools.getFinalInteger("stopStatus",request);
		String ajNoQt = CommonTools.getFinalStr("ajNoQt",request);
		String sqAddress = Transcode.unescape_new("sqAddress", request);
		String zlNo = CommonTools.getFinalStr("zlNo", request);
		String ajTitle = Transcode.unescape_new("ajTitle", request);
		String ajType = CommonTools.getFinalStr("ajType", request);
		String lxr = CommonTools.getFinalStr("lxr", request);
		String sDate = CommonTools.getFinalStr("sDate", request);
		String eDate = CommonTools.getFinalStr("eDate", request);
		Map<String,Object> map = new HashMap<String,Object>();
		String loginType = this.getLoginType(request);
		boolean abilityFlag = false;
		if(loginType.equals("cpyUser")){//代理机构下
			CpyUserInfo cpyUser = cum.getEntityById(this.getLoginUserId(request));
			if(cpyUser != null){
				cpyId = cpyUser.getCpyInfoTb().getId();
				abilityFlag = true;
			}
		}else if(loginType.equals("spUser")){//平台用户
			abilityFlag = true;
		}
		if(abilityFlag){
			Integer count = zlm.getCountByOpt(cpyId, stopStatus, sqAddress, ajNoQt, zlNo, ajTitle, ajType, lxr, sDate, eDate);
			if(count > 0){
				Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("limit")), 10);//等同于pageSize
				Integer pageNo = CommonTools.getFinalInteger("page", request);//等同于pageNo
				List<ZlajMainInfoTb> zlList = zlm.listPageInfoByOpt(cpyId, stopStatus, sqAddress, ajNoQt, zlNo, ajTitle, ajType, lxr, sDate, eDate, pageNo, pageSize);
				List<Object> list_d = new ArrayList<Object>();
				for(Iterator<ZlajMainInfoTb> it = zlList.iterator() ; it.hasNext();){
					ZlajMainInfoTb zl = it.next();
					Map<String,Object> map_d = new HashMap<String,Object>();
					map_d.put("id", zl.getId());
					map_d.put("ajNo", zl.getAjNoQt());
					map_d.put("ajNoGf", zl.getAjNoGf());
					map_d.put("ajTitle", zl.getAjTitle());
					String ajType_db = zl.getAjType();
					String ajType_new = "";
					if(ajType_db.equals("fm")){
						ajType_new = "发明";
					}else if(ajType_db.equals("syxx")){
						ajType_new = "实用新型";
					}else if(ajType_db.equals("wg")){
						ajType_new = "外观";
					}else if(ajType_db.equals("fmxx")){
						ajType_new = "发明+新型";
					}
					map_d.put("ajType", ajType_new);
					String ajFieldIdStr = zl.getAjFieldId();
					String ajFieldName = "";
					if(!ajFieldIdStr.equals("")){
						List<JsFiledInfoTb> jsList = jsm.listInfoByOpt(cpyId, ajFieldIdStr);
						for(Iterator<JsFiledInfoTb> it_js = jsList.iterator(); it_js.hasNext();){
							JsFiledInfoTb js = it_js.next();
							ajFieldName += js.getZyName() + ",";
						}
						if(!ajFieldName.equals("")){
							ajFieldName = ajFieldName.substring(0, ajFieldName.length() - 1);
						}
					}				
					map_d.put("ajFieldName", ajFieldName);
					String sqrId = zl.getAjSqrId();//可以是公司也可以是个人
					String sqrName = "";
					if(!sqrId.equals("")){
						String[] sqrIdArr = sqrId.split(",");
						for(Integer k = 0 ; k < sqrIdArr.length ; k++){
							List<CustomerInfoTb> cList = cm.listInfoById(cpyId, Integer.parseInt(sqrIdArr[k]));
							if(cList.size() > 0){
								sqrName += cList.get(0).getCusName() + ",";
							}
						}
						if(!sqrName.equals("")){
							sqrName = sqrName.substring(0, sqrName.length() - 1);
						}
					}
					map_d.put("sqrInfo", sqrName);
					String fmrId = zl.getAjFmrId();
					String fmrName = "";
					if(!fmrId.equals("")){
						String[] fmrIdArr = fmrId.split(",");
						for(Integer i = 0 ; i < fmrIdArr.length ; i++){
							List<CustomerFmrInfoTb> cList = cm.listFmrInfoByFmrId(Integer.parseInt(fmrIdArr[i]), cpyId);
							if(cList.size() > 0){
								fmrName += cList.get(0).getCusFmrName() + ",";
							}
						}
						if(!fmrName.equals("")){
							fmrName = fmrName.substring(0, fmrName.length() - 1);
						}
					}
					map_d.put("fmrInfo", fmrName);
					String lxrId = zl.getAjLxrId();
					String lxrName = "";
					if(!lxrId.equals("")){
						String[] lxrIdArr = lxrId.split(",");
						for(Integer j = 0 ; j < lxrIdArr.length ; j++){
							List<CustomerLxrInfoTb> clList = cm.listLxrInfoByCusId(Integer.parseInt(lxrIdArr[j]), cpyId);
							if(clList.size() > 0){
								lxrName += clList.get(0).getCusLxrName() + ",";
							}
						}
						if(!lxrName.equals("")){
							lxrName = lxrName.substring(0, lxrName.length() - 1);
						}
					}
					map_d.put("lxrInfo", lxrName);
					map_d.put("ajAddress", zl.getAjSqAddress());
					map_d.put("applyDate", zl.getAjApplyDate());
					map_d.put("ajStatus", zl.getAjStatus());
					map_d.put("ajStopStatus", zl.getAjStopStatus().equals(0) ? "正常":"终止");
					map_d.put("ajStopDate", zl.getAjStopDate());
					map_d.put("ajStopUser", zl.getAjStopUser());
					String soptUserType = zl.getAjStopUserType();
					if(soptUserType.equals("cpyUser")){
						soptUserType = "机构员工";
					}else{
						soptUserType = "发布人员";
					}
					map_d.put("ajStopUserType", soptUserType);
					map_d.put("ajAddDate", zl.getAjAddDate());
					Integer zxUserId = zl.getZxUserId();
					String zxUserName = "暂无";
					CpyUserInfo zxUser = cum.getEntityById(zxUserId);
					if(zxUser != null){
						zxUserName = zxUser.getUserName();
					}
					map_d.put("zxUserName", zxUserName);
					list_d.add(map_d);
				}
				map.put("msg", "success");
				map.put("data", list_d);
				map.put("count", count);
				map.put("code", 0);
			}else{
				map.put("msg", "noInfo");
			}
		}

		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取指定专利详情
	 * @description
	 * @author wm
	 * @date 2018-8-30 上午11:38:03
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getZlDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		ZlajEwyqInfoManager yqm = (ZlajEwyqInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_EWYQ_INFO);
		CustomerInfoManager cm = (CustomerInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CUSTOMER_INFO);
		JsFiledInfoManager jsm = (JsFiledInfoManager) AppFactory.instance(null).getApp(Constants.WEB_JS_FIELD_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO); 
		ZlajLcInfoManager lcm = (ZlajLcInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_LC_INFO); 
		ZlajLcMxInfoManager mxm = (ZlajLcMxInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_LC_MX_INFO);
		ZlajTzsInfoManager tzsm = (ZlajTzsInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_TZS_INFO);
		ZlajFjInfoManager fjm = (ZlajFjInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_FJ_INFO);
		ZlajFeeInfoManager zfm = (ZlajFeeInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_FEE_INFO);
		Integer zlId = CommonTools.getFinalInteger("zlId", request);
		String opt = CommonTools.getFinalStr("opt", request);//basic(基本信息),lc(流程),tzs(通知书),fj(附件),fy(费用)-后续有的再加
		String msg = "error";
		boolean abilityFlag = false;
		Integer cpyId = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		if(this.getLoginType(request).equals("cpyUser")){
			abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "listZl");
			if(abilityFlag){
				CpyUserInfo cpyUser = cum.getEntityById(this.getLoginUserId(request));
				if(cpyUser != null){
					cpyId = cpyUser.getCpyInfoTb().getId();
				}
				List<ZlajMainInfoTb> zlList = zlm.listSpecInfoById(zlId, cpyId);
				if(zlList.size() > 0){
					if(opt.equals("basic")){//基本信息
						msg = "success";
						ZlajMainInfoTb zl = zlList.get(0);
						map.put("ajId", zlId);
						map.put("ajTitle", zl.getAjTitle());
						map.put("ajNo", zl.getAjNoQt());
						map.put("ajNoGf", zl.getAjNoGf());
						map.put("ajAddress", zl.getAjSqAddress());
						map.put("ajType", zl.getAjType());
						String sqrId = zl.getAjSqrId();//可以是公司也可以是个人
						String sqrName = "";
						if(!sqrId.equals("")){
							String[] sqrIdArr = sqrId.split(",");
							for(Integer k = 0 ; k < sqrIdArr.length ; k++){
								List<CustomerInfoTb> cList = cm.listInfoById(cpyId, Integer.parseInt(sqrIdArr[k]));
								if(cList.size() > 0){
									sqrName += cList.get(0).getCusName() + ",";
								}
							}
							if(!sqrName.equals("")){
								sqrName = sqrName.substring(0, sqrName.length() - 1);
							}
						}
						map.put("sqrInfo", sqrName);
						String fmrId = zl.getAjFmrId();
						String fmrName = "";
						if(!fmrId.equals("")){
							String[] fmrIdArr = fmrId.split(",");
							for(Integer i = 0 ; i < fmrIdArr.length ; i++){
								List<CustomerFmrInfoTb> cList = cm.listFmrInfoByFmrId(Integer.parseInt(fmrIdArr[i]), cpyId);
								if(cList.size() > 0){
									fmrName += cList.get(0).getCusFmrName() + ",";
								}
							}
							if(!fmrName.equals("")){
								fmrName = fmrName.substring(0, fmrName.length() - 1);
							}
						}
						map.put("fmrInfo", fmrName);
						String lxrId = zl.getAjLxrId();
						String lxrName = "";
						if(!lxrId.equals("")){
							String[] lxrIdArr = lxrId.split(",");
							for(Integer j = 0 ; j < lxrIdArr.length ; j++){
								List<CustomerLxrInfoTb> clList = cm.listLxrInfoByCusId(Integer.parseInt(lxrIdArr[j]), cpyId);
								if(clList.size() > 0){
									lxrName += clList.get(0).getCusLxrName() + ",";
								}
							}
							if(!lxrName.equals("")){
								lxrName = lxrName.substring(0, lxrName.length() - 1);
							}
						}
						map.put("lxrInfo", lxrName);
						map.put("ajYxqDetail", zl.getAjYxqDetail());//格式为申请专利号,申请地区,申请日期:申请专利号,申请地区,申请日期........
						//获取当前专利类型的额外要求
						String yqIdStr = zl.getAjEwyqId();
						String[] yqIdArr = yqIdStr.split(",");
						List<ZlajEwyqInfoTb> yqList = yqm.listInfoByType(zl.getAjType());
						List<Object> list_d = new ArrayList<Object>();
						if(yqList.size() > 0){
							for(Iterator<ZlajEwyqInfoTb> it = yqList.iterator() ; it.hasNext();){
								ZlajEwyqInfoTb yq = it.next();
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("yqId", yq.getId());
								map_d.put("yaContent", yq.getYqContent());
								if(yqIdStr.equals("")){
									map_d.put("checked", false);
								}else{
									for(Integer i = 0 ; i < yqIdArr.length ; i++){
										if(String.valueOf(yq.getId()).equals(yqIdArr[i])){
											map_d.put("checked", true);
											break;
										}else{
											map_d.put("checked", false);
										}
									}
								}
								list_d.add(map_d);
							}
						}
						map.put("yqInfo", list_d);
						map.put("upFile", zl.getAjUpload());//附件
						map.put("stopStatus", zl.getAjStopStatus());//案件在终止状态下基本信息不能被修改
						String selJsFieldStr = zl.getAjFieldId();
						String[] selJsFieldArr = selJsFieldStr.split(",");
						List<JsFiledInfoTb> jsList = jsm.listInfoByOpt(cpyId, "");
						List<Object> list_j = new ArrayList<Object>();
						for(Iterator<JsFiledInfoTb> it = jsList.iterator() ; it.hasNext();){
							JsFiledInfoTb js = it.next();
							Map<String,Object> map_j = new HashMap<String,Object>();
							map_j.put("jsId", js.getId());
							map_j.put("jsName", js.getZyName());
							if(selJsFieldStr.equals("")){
								map_j.put("checked", false);
							}else{
								for(Integer i = 0 ; i < selJsFieldArr.length ; i++){
									if(String.valueOf(js.getId()).equals(selJsFieldArr[i])){
										map_j.put("checked", true);
										break;
									}else{
										map_j.put("checked", false);
									}
								}
							}
							list_j.add(map_j);
						}
						map.put("jsFieldInfo", list_j);
						map.put("checkUserId", zl.getCheckUserId());
						map.put("zxUserId", zl.getZxUserId());
						map.put("tjUserId", zl.getTzsUserId());
						map.put("tzsUserId", zl.getTzsUserId());
						map.put("feeUserId", zl.getFeeUserId());
						map.put("bzUserId", zl.getBzUserId());
						map.put("bzshUserId", zl.getBzshUserId());
						map.put("bhUserId", zl.getBhUserId());
						//获取当前代理机构所有人员
						List<CpyUserInfo> uList = cum.listValidInfoByOpt(cpyId, 0);
						List<Object> list_u = new ArrayList<Object>();
						for(Iterator<CpyUserInfo> it = uList.iterator() ; it.hasNext();){
							CpyUserInfo user = it.next();
							Map<String,Object> map_d = new HashMap<String,Object>();
							map_d.put("userId", user.getId());
							map_d.put("userName", user.getUserName());
							list_u.add(map_d);
						}
						map.put("allUserInfo", list_u);
					}else if(opt.equals("lc")){//流程
						List<ZlajLcInfoTb> lcList = lcm.listLcInfoByAjId(zlId);
						List<Object> list_lc = new ArrayList<Object>();
						List<Object> list_mx = new ArrayList<Object>();
						if(lcList.size() > 0){
							msg = "success";
							for(Iterator<ZlajLcInfoTb> it = lcList.iterator() ; it.hasNext();){
								ZlajLcInfoTb lc = it.next();
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("lcId", lc.getId());
								map_d.put("lcName", lc.getLcMz());
								map_d.put("cpyDate", lc.getLcCpyDate());
								map_d.put("sDate", lc.getLcSDate());
								map_d.put("comDate", lc.getLcEDate());
								map_d.put("gfDate", lc.getLcGfDate());
								list_lc.add(map_d);
							}
							map.put("lcInfo", list_lc);
							//默认获取最后一个流程的流程明细
							Integer lastLcId = lcList.get(0).getId();
							List<ZlajLcMxInfoTb> mxList = mxm.listDetailInfoByLcId(lastLcId);
							if(mxList.size() > 0){
								for(Iterator<ZlajLcMxInfoTb> it = mxList.iterator() ; it.hasNext();){
									ZlajLcMxInfoTb mx = it.next();
									Map<String,Object> map_d = new HashMap<String,Object>();
									map_d.put("mxId", mx.getId());
									map_d.put("mxName", mx.getLcMxName());
									map_d.put("fzUserName", mx.getCpyUserInfo().getUserName());
									map_d.put("mxSDate", mx.getLcMxSDate());
									map_d.put("mxEDate", mx.getLcMxEDate());
									String upFile = mx.getLcMxUpFile();
									String upFileName = "";
									if(!upFile.equals("")){
										upFileName = upFile.substring(upFile.lastIndexOf("/")+1,upFile.length());
									}
									map_d.put("upFile", upFile);
									map_d.put("upFileName", upFileName);
									map_d.put("mxRemark", mx.getLcMxRemark());
									list_mx.add(map_d);
								}
							}
							map.put("mxInfo", list_mx);
						}else{
							msg = "noInfo";
						}
					}else if(opt.equals("tzs")){//通知书
						List<ZlajTzsInfoTb> tzsList = tzsm.listInfoByZlId(zlId);
						if(tzsList.size() > 0){
							msg = "success";
							List<Object> list_tzs = new ArrayList<Object>();
							for(Iterator<ZlajTzsInfoTb> it = tzsList.iterator() ; it.hasNext();){
								ZlajTzsInfoTb tzs = it.next();
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("tzsId", tzs.getId());
								map_d.put("tzsName", tzs.getTzsName());
								map_d.put("fwrDate", tzs.getTzsFwr());
								map_d.put("gfrDate", tzs.getTzsGfr());
								list_tzs.add(map_d);
							}
							map.put("tzsInfo", list_tzs);
						}else{
							msg = "noInfo";
						}
					}else if(opt.equals("fj")){//附件
						List<ZlajFjInfoTb> fjList = fjm.listInfoByAjId(zlId);
						if(fjList.size() > 0){
							msg = "success";
							List<Object> list_fj = new ArrayList<Object>();
							for(Iterator<ZlajFjInfoTb> it = fjList.iterator() ; it.hasNext();){
								ZlajFjInfoTb fj = it.next();
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("fjId", fj.getId());
								map_d.put("fjName", fj.getFjName());
								map_d.put("fjType", fj.getFjType());
								map_d.put("fjVersion", fj.getFjVersion());
								map_d.put("fjGs", fj.getFjGs());
								map_d.put("fjDx", fj.getFjDx());
								map_d.put("upUserName", fj.getCpyUserInfo().getUserName());
								map_d.put("upDate", fj.getFjUpDate());
								list_fj.add(map_d);
							}
							map.put("fjInfo", list_fj);
						}else{
							msg = "noInfo";
						}
					}else if(opt.equals("fy")){//费用
						msg = "success";
						List<ZlajFeeInfoTb> zfList_gf = zfm.listInfoByOpt(zlId, "gf");//官费
						List<ZlajFeeInfoTb> zfList_dlf = zfm.listInfoByOpt(zlId, "dlf");//代理费
						List<ZlajFeeInfoTb> zfList_nf = zfm.listInfoByOpt(zlId, "nf");//年费
						List<ZlajFeeInfoTb> zfList_jlj = zfm.listInfoByOpt(zlId, "jlj");//奖励金
						List<Object> list_gf = new ArrayList<Object>();
						List<Object> list_dlf = new ArrayList<Object>();
						List<Object> list_nf = new ArrayList<Object>();
						List<Object> list_jlj = new ArrayList<Object>();
						Double gfTotal = 0.00,dlfTotal = 0.00,nfTotal = 0.00,jljTotal = 0.00;
						if(zfList_gf.size() > 0){
							for(Iterator<ZlajFeeInfoTb> it = zfList_gf.iterator() ; it.hasNext();){
								ZlajFeeInfoTb gf = it.next();
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("feeName", gf.getFeeTypeInfoTb().getFeeName());
								map_d.put("applyUserName", gf.getCpyUserInfo().getUserName());
								map_d.put("feePrice", gf.getFeePrice());
								map_d.put("jnDate", gf.getFeeJnDate());
								map_d.put("jnStatus", gf.getFeeStatus().equals(0) ? "未交" : "已交");
								map_d.put("djStatus", gf.getDjStatus().equals(0) ? "自交" : "代交");
								map_d.put("gfDate", gf.getFeeEndDateGf());
								map_d.put("cpyDate", gf.getFeeEndDateJj());
								map_d.put("feeZd", gf.getFeeUpZd());
								map_d.put("feeRemark", gf.getFeeRemark());
								list_gf.add(map_d);
								gfTotal += gf.getFeePrice();
							}
							if(gfTotal > 0){
								gfTotal = Convert.convertInputNumber_2(gfTotal);
							}
							map.put("gfResult", "success");
						}else{
							map.put("gfResult", "noInfo");
						}
						map.put("gfInfo", list_gf);
						map.put("gfTotal", gfTotal);
						
						if(zfList_dlf.size() > 0){
							for(Iterator<ZlajFeeInfoTb> it = zfList_dlf.iterator() ; it.hasNext();){
								ZlajFeeInfoTb gf = it.next();
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("feeName", gf.getFeeTypeInfoTb().getFeeName());
								map_d.put("applyUserName", gf.getCpyUserInfo().getUserName());
								map_d.put("feePrice", gf.getFeePrice());
								map_d.put("jnDate", gf.getFeeJnDate());
								map_d.put("jnStatus", gf.getFeeStatus().equals(0) ? "未交" : "已交");
								map_d.put("djStatus", gf.getDjStatus().equals(0) ? "自交" : "代交");
								map_d.put("gfDate", gf.getFeeEndDateGf());
								map_d.put("cpyDate", gf.getFeeEndDateJj());
								map_d.put("feeZd", gf.getFeeUpZd());
								map_d.put("feeRemark", gf.getFeeRemark());
								list_dlf.add(map_d);
								dlfTotal += gf.getFeePrice();
							}
							if(dlfTotal > 0){
								dlfTotal = Convert.convertInputNumber_2(dlfTotal);
							}
							map.put("dlfResult", "success");
						}else{
							map.put("dlfResult", "noInfo");
						}
						
						map.put("dlfInfo", list_dlf);
						map.put("dlfTotal", dlfTotal);
						
						if(zfList_nf.size() > 0){
							for(Iterator<ZlajFeeInfoTb> it = zfList_nf.iterator() ; it.hasNext();){
								ZlajFeeInfoTb gf = it.next();
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("feeName", gf.getFeeTypeInfoTb().getFeeName());
								map_d.put("applyUserName", gf.getCpyUserInfo().getUserName());
								map_d.put("feePrice", gf.getFeePrice());
								map_d.put("jnDate", gf.getFeeJnDate());
								map_d.put("jnStatus", gf.getFeeStatus().equals(0) ? "未交" : "已交");
								map_d.put("djStatus", gf.getDjStatus().equals(0) ? "自交" : "代交");
								map_d.put("gfDate", gf.getFeeEndDateGf());
								map_d.put("cpyDate", gf.getFeeEndDateJj());
								map_d.put("feeZd", gf.getFeeUpZd());
								map_d.put("feeRemark", gf.getFeeRemark());
								list_nf.add(map_d);
								nfTotal += gf.getFeePrice();;
							}
							if(nfTotal > 0){
								nfTotal = Convert.convertInputNumber_2(nfTotal);
							}
							map.put("nfResult", "success");
						}else{
							map.put("nfResult", "noInfo");
						}
						
						map.put("nfInfo", list_nf);
						map.put("nfTotal", nfTotal);

						if(zfList_jlj.size() > 0){
							for(Iterator<ZlajFeeInfoTb> it = zfList_jlj.iterator() ; it.hasNext();){
								ZlajFeeInfoTb gf = it.next();
								Map<String,Object> map_d = new HashMap<String,Object>();
								map_d.put("feeName", gf.getFeeTypeInfoTb().getFeeName());
								map_d.put("applyUserName", gf.getCpyUserInfo().getUserName());
								map_d.put("feePrice", gf.getFeePrice());
								map_d.put("jnDate", "");
								map_d.put("jnStatus", "");
								map_d.put("djStatus", "");
								map_d.put("gfDate", gf.getFeeEndDateGf());
								map_d.put("cpyDate", "");
								map_d.put("feeZd", "");
								map_d.put("feeRemark", gf.getFeeRemark());
								list_jlj.add(map_d);
								jljTotal += gf.getFeePrice();;
							}
							if(jljTotal > 0){
								jljTotal = Convert.convertInputNumber_2(jljTotal);
							}
							map.put("jljResult", "success");
						}else{
							map.put("jljResult", "noInfo");
						}
						
						map.put("jljInfo", list_jlj);
						map.put("jljTotal", jljTotal);
					}
					
				}
			}else{
				msg = "noAbility";
			}
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 修改专利操作人员
	 * @description
	 * @author wm
	 * @date 2018-9-6 上午08:20:32
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateOperatorUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Map<String,String> map = new HashMap<String,String>();
		boolean abilityFlag = false;
		Integer zlId = CommonTools.getFinalInteger("zlId", request);
		Integer zxUserId = CommonTools.getFinalInteger("zxUserId", request);
		Integer tjUserId = CommonTools.getFinalInteger("tjUserId", request);
		Integer tzsUserId = CommonTools.getFinalInteger("tzsUserId", request);
		Integer feeUserId = CommonTools.getFinalInteger("feeUserId", request);
		Integer bzUserId = CommonTools.getFinalInteger("bzUserId", request);
		Integer bzshUserId = CommonTools.getFinalInteger("bzshUserId", request);
		Integer bhUserId = CommonTools.getFinalInteger("bhUserId", request);
		Integer checkUserId = CommonTools.getFinalInteger("checkUserId", request);
		Integer cpyId = -1;
		Integer zxUserId_db = 0;
		if(this.getLoginType(request).equals("cpyUser")){
			CpyUserInfo user = cum.getEntityById(this.getLoginUserId(request));
			if(user != null){
				cpyId = user.getCpyInfoTb().getId();
			}
			if(this.getLoginRoleName(request).equals("管理员")){
				abilityFlag = true;
			}else{
				//获取当前用户是否拥有增加的权限--将修改专利操作人员的权限归类为增加权限
				abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addZl");
				if(!abilityFlag && Ability.checkAuthorization(this.getLoginRoleId(request), "upZl")){//没有增加权限但是有修改权限
					//如果是只修改领取人--比如说之前没有领取人的时候，这个时候有修改权限的员工就可以修改
					if(cpyId > 0){
						List<ZlajMainInfoTb> zlList = zlm.listSpecInfoById(zlId, cpyId);
						if(zlList.size() > 0){
							zxUserId_db = zlList.get(0).getZxUserId();
							abilityFlag = true;
						}
					}
				}
			}
		}
		
		
		return null;
	}
	
	/**
	 * 获取当前的案件号（增加时前台显示--参考，解决可能出现几个人在同时增加就会出现问题）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-26 下午10:10:49
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCurrAjNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		Integer cpyId = 0;
		String currNextAjNo = "";
		String msg = "error";
		String nextNumStr = "";
		Map<String,String> map = new HashMap<String,String>();
		if(this.getLoginType(request).equals("cpyUser")){
			cpyId = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb().getId();
			String currYear = CurrentTime.getYear();
			String ajType = CommonTools.getFinalStr("ajType", request);
			if(cpyId > 0 && !ajType.equals("")){
				List<ZlajMainInfoTb> zlList = zlm.listFirstInfoByOpt(cpyId,ajType,currYear);
				String varCon = "";
				if(ajType.equals("fm")){
					varCon = "01";
				}else if(ajType.equals("syxx")){
					varCon = "02";
				}else if(ajType.equals("wg")){
					varCon = "03";
				}
				msg = "success";
				if(zlList.size() > 0){
					String ajNo = zlList.get(0).getAjNo();//20180100011--201802
					String str1 = ajNo.substring(0,6);
					String str2 = ajNo.substring(6, 10);
					Integer nextNum = Integer.parseInt(str2) + 1;
					if(nextNum > 1000){
						nextNumStr = nextNum + "";
					}else if(nextNum > 100){
						nextNumStr = "0" + nextNum;
					}else if(nextNum > 10){
						nextNumStr = "00" + nextNum;
					}else if(nextNum > 1){
						nextNumStr = "000" + nextNum;
					}
					currNextAjNo = str1 + nextNumStr + "." + cpyId;
				}else{
					currNextAjNo = currYear + varCon + "0001" + "." + cpyId;
				}
				map.put("currNextAjNo", currNextAjNo);
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 增加专利
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-26 下午09:14:12
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addZlData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
		Integer cpyId = 0;
		String nextNumStr = "";
		String ajNoQt = "";
		String ajNo = "";
		String msg = "error";
		Map<String,String> map = new HashMap<String,String>();
		if(this.getLoginType(request).equals("cpyUser")){
			//判断权限
			//获取当前用户是否有修改权限
			boolean abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "addZl");
			if(abilityFlag){
				cpyId = cum.getEntityById(this.getLoginUserId(request)).getCpyInfoTb().getId();
				//获取当前的案例号--实时
				String currYear = CurrentTime.getYear();
				String ajType = CommonTools.getFinalStr("ajType", request);
				String varCon = "";
				if(!ajType.equals("")){
					if(ajType.equals("fmxx")){
						ajType = "fm,syxx";
					}
					String[] ajTypeArr = ajType.split(",");
					for(Integer i = 0 ; i < ajTypeArr.length ; i++){
						ajType = ajTypeArr[i];
						if(cpyId > 0 && !ajType.equals("")){
							if(ajType.equals("fm")){
								varCon = "01";
							}else if(ajType.equals("syxx")){
								varCon = "02";
							}else if(ajType.equals("wg")){
								varCon = "03";
							}
							List<ZlajMainInfoTb> zlList = zlm.listFirstInfoByOpt(cpyId,ajType,currYear);
							if(zlList.size() > 0){
								String ajNo_prev = zlList.get(0).getAjNo();//20180100011--201802
								String str1 = ajNo_prev.substring(0,6);
								String str2 = ajNo_prev.substring(6, 10);
								Integer nextNum = Integer.parseInt(str2) + 1;
								if(nextNum > 1000){
									nextNumStr = nextNum + "";
								}else if(nextNum > 100){
									nextNumStr = "0" + nextNum;
								}else if(nextNum > 10){
									nextNumStr = "00" + nextNum;
								}else if(nextNum > 1){
									nextNumStr = "000" + nextNum;
								}
								ajNoQt = str1 + nextNumStr + "." + cpyId;
								ajNo =  str1 + nextNumStr + cpyId;
							}else{
								ajNoQt = currYear + varCon + "0001" + "." + cpyId;
								ajNo = currYear + varCon + "0001" + cpyId;
							}
							
							String zlNoGf = "";
							String ajTitle = Transcode.unescape_new("ajTitle", request);
							String ajFieldId = CommonTools.getFinalStr("ajFieldId", request);
							String ajSqrId  = CommonTools.getFinalStr("ajSqrId", request);
							String ajFmrId  = CommonTools.getFinalStr("ajFmrId", request);
							String ajLxrId = CommonTools.getFinalStr("ajLxrId", request);
							String ajSqAddress = Transcode.unescape_new("ajSqAddress", request);
							String ajYxqId = CommonTools.getFinalStr("ajYxqId", request);
							String ajUpload = CommonTools.getFinalStr("ajUpload", request);
							String ajRemark = CommonTools.getFinalStr("ajRemark", request);
							String ajEwyqId = CommonTools.getFinalStr("ajEwyqId", request);
							Integer pubZlId = CommonTools.getFinalInteger("pubZlId", request);//发布专利的编号
							Integer zxUserId = CommonTools.getFinalInteger("zxUserId", request);
							Integer tjUserId = CommonTools.getFinalInteger("tjUserId", request);
							Integer tzsUserId = CommonTools.getFinalInteger("tzsUserId", request);
							Integer feeUserId = CommonTools.getFinalInteger("feeUserId", request);
							Integer bzUserId = CommonTools.getFinalInteger("bzUserId", request);
							Integer bzshUserId = CommonTools.getFinalInteger("bzshUserId", request);
							Integer bhUserId = CommonTools.getFinalInteger("bhUserId", request);
							
							String ajApplyDate = "";
							String ajStatus = CommonTools.getFinalStr("ajStatus", request);
							Integer checkUserId = CommonTools.getFinalInteger("checkUserId", request);//审查人员编号
							Integer zlId = zlm.addZL(ajNo, ajNoQt, zlNoGf, ajTitle, ajType, ajFieldId, ajSqrId, ajFmrId, ajLxrId, ajSqAddress, 
									ajYxqId, ajUpload, ajRemark, ajEwyqId, ajApplyDate, ajStatus, pubZlId,cpyId,checkUserId,zxUserId,
									tjUserId,tzsUserId,feeUserId,bzUserId,bzshUserId,bhUserId);
							if(zlId > 0){
								msg = "success";
//								if(zxUserId.equals(0)){
									//给全体员工（对专利具有修改操作的人）发送邮件
//									List<CpyUserInfo> uList = cum.listValidInfoByOpt(cpyId, 0);
//									for(Iterator<CpyUserInfo> it = uList.iterator() ; it.hasNext();){
//										CpyUserInfo user = it.next();
//										
//									}
//									mm.addMail("taskM", Constants.SYSTEM_EMAIL_ACCOUNT, acceptUserId, "cpyUser", "新的专利发布啦","新的专利发布了，快来领取撰写任务了！");
//								}
							}
						}
					}
				}
			}else{
				msg = "noAbility";
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 修改指定专利的终止状态信息
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-27 下午09:49:04
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStopStatusInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		String msg = "error";
		Map<String,String> map = new HashMap<String,String>();
		if(this.getLoginType(request).equals("cpyUser")){
			//判断权限
			//获取当前用户是否有修改权限
			boolean abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "upZl");
			if(abilityFlag){
				String zlIdStr = CommonTools.getFinalStr("zlId", request);//可能是发明+新型（发布人可同时取消）
				if(!zlIdStr.equals("")){
					String[] zlIdStrArr = zlIdStr.split(",");
					Integer stopStatus = CommonTools.getFinalInteger("stopStatus", request);
					String stopUser = "";
					String stopDate = "";
					String stopUserType = "";
					if(stopStatus.equals(1)){
						stopUser = CommonTools.getFinalStr("stopUser", request);
						stopDate = CurrentTime.getCurrentTime();
						stopUserType = this.getLoginType(request);
					}
					zlm.updateStopStatusById(Integer.parseInt(zlIdStrArr[0]), stopStatus, stopDate, stopUser, stopUserType);
					if(zlIdStrArr.length == 2){
						zlm.updateStopStatusById(Integer.parseInt(zlIdStrArr[1]), stopStatus, stopDate, stopUser, stopUserType);
					}
					msg = "success";
				}
			}else{
				msg = "noAbility";
			}
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 下载文件
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午10:05:26
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String fileUrl = CommonTools.getFinalStr("fileUrl", request);
		String absoFilePath = "";//绝对地址
		String fileName = "";
		if(!fileUrl.equals("")){
			fileName = fileUrl.substring(fileUrl.lastIndexOf("\\")+1,fileUrl.length());
			absoFilePath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" +fileUrl;
			try  {  
		        //第七步 下载文件到客户端
		        OutputStream fos = null;
		        BufferedOutputStream bos = null;
		        InputStream fis = null;
		        BufferedInputStream bis = null;
		        fis = new FileInputStream(new File(absoFilePath));
				bis = new BufferedInputStream(fis);
				fos = response.getOutputStream();
				bos = new BufferedOutputStream(fos);
				fileName = URLEncoder.encode(fileName,"UTF-8");
				//这个就就是弹出下载对话框的关键代码
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "No-cache");
				response.setDateHeader("Expires", 0); 
		        response.setHeader("Content-disposition","attachment;filename=" +fileName);
		        response.setContentType("application/x-download");
		        int bytesRead = 0;
		        byte[] buffer = new byte[8192];
		        while ((bytesRead = bis.read(buffer,0,8192)) != -1) {
		        	fos.write(buffer, 0, bytesRead);
		        }
		        fos.flush();
		        fis.close();
		        bis.close();
		        fos.close();
		        bos.close();
		    }  
		    catch (IOException e){  
		        e.printStackTrace();  
		    }
		}
		return null;
	}
}