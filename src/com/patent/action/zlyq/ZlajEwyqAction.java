/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.zlyq;

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
import com.patent.module.ZlajEwyqInfoTb;
import com.patent.service.ZlajEwyqInfoManager;
import com.patent.tools.CommonTools;
import com.patent.util.Constants;

/** 
 * MyEclipse Struts
 * Creation date: 08-21-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class ZlajEwyqAction extends DispatchAction {
	
	
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
	*  导向专利案件页面
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午10:04:31
	*  @param mapping
	*  @param form
	*  @param request
	*  @param response
	*  @return
	 */
	public ActionForward goZlyqPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return mapping.findForward("yqPage");
	}
	
	/**
	 * 增加要求记录
	 * @description
	 * @author wm
	 * @date 2018-8-22 上午08:53:25
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addZlyq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajEwyqInfoManager yqm = (ZlajEwyqInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_EWYQ_INFO);
		String msg = "error";
		if(this.getLoginRoleName(request).equals("super")){
			String yqContent = Transcode.unescape(request.getParameter("yqContent"), request);
			String yqType = CommonTools.getFinalStr(request.getParameter("yqType"));
			if(yqm.listInfoByCnt(yqContent).size() > 0){
				msg = "exist";
			}else{
//				if(yqType.indexOf("fm") >= 0 && yqType.indexOf("syxx") >= 0){//如果同时是发明、实用新型时动态增加发明+新型
//					yqType += ",fmxx";
//				}
				Integer yqId = yqm.addYq(yqContent, yqType);
				if(yqId > 0){
					msg = "success";
				}
			}
		}else{
			msg = "noAbility";
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 修改指定的额外要求信息
	 * @description
	 * @author wm
	 * @date 2018-8-22 上午09:40:13
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upZlyq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajEwyqInfoManager yqm = (ZlajEwyqInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_EWYQ_INFO);
		String msg = "error";
		boolean existFlag = false;//不存在
		if(this.getLoginRoleName(request).equals("super")){
			Integer yqId = CommonTools.getFinalInteger(request.getParameter("yqId"));
			String yqContent = Transcode.unescape(request.getParameter("yqContent"), request);
			String yqType = CommonTools.getFinalStr(request.getParameter("yqType"));
			ZlajEwyqInfoTb yq = yqm.getEntityById(yqId);
			if(!yq.getYqContent().equals(yqContent)){//不相同，判断重复
				existFlag = (yqm.listInfoByCnt(yqContent).size() > 0);
			}
			if(existFlag){
				msg = "exist";
			}else{
//				if(yqType.indexOf("fm") >= 0 && yqType.indexOf("syxx") >= 0){//如果同时是发明、实用新型时动态增加发明+新型
//					yqType += ",fmxx";
//				}
				boolean flag = yqm.updateYq(yqId, yqContent, yqType);
				if(flag){
					msg = "success";
				}
			}
		}else{
			msg = "noAbility";
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取指定主键的要求信息
	 * @description
	 * @author wm
	 * @date 2018-8-22 下午04:37:38
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSpecZlyqData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajEwyqInfoManager yqm = (ZlajEwyqInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_EWYQ_INFO);
		String msg = "error";
		Map<String,Object> map  = new HashMap<String,Object>();
		List<Object> list_d = new ArrayList<Object>();
		if(this.getLoginRoleName(request).equals("super")){
			Integer yqId = CommonTools.getFinalInteger(request.getParameter("yqId"));
			ZlajEwyqInfoTb yq = yqm.getEntityById(yqId);
			if(yq != null){
				msg = "success";
				map.put("id", yq.getId());
				map.put("yqContent", yq.getYqContent());
				String yqType = yq.getYqType();
				
				Map<String,Object> map_d1  = new HashMap<String,Object>();
				map_d1.put("typeNameValue", "fm");
				map_d1.put("typeNameChi", "发明");
				if(yqType.indexOf("fm") >= 0){
					map_d1.put("checked", true);
				}else{
					map_d1.put("checked", false);
				}
				list_d.add(map_d1);
				Map<String,Object> map_d2  = new HashMap<String,Object>();
				map_d2.put("typeNameValue", "syxx");
				map_d2.put("typeNameChi", "实用新型");
				if(yqType.indexOf("syxx") >= 0){
					map_d2.put("checked", true);
				}else{
					map_d2.put("checked", false);
				}
				list_d.add(map_d2);
				Map<String,Object> map_d3  = new HashMap<String,Object>();
				map_d3.put("typeNameValue", "wg");
				map_d3.put("typeNameChi", "外观");
				if(yqType.indexOf("wg") >= 0){
					map_d3.put("checked", true);
				}else{
					map_d3.put("checked", false);
				}
				list_d.add(map_d3);
				map.put("yqType", list_d);
			}
			
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
	
	/**
	 * 获取额外要求记录列表
	 * @description
	 * @author wm
	 * @date 2018-8-22 上午09:43:42
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getZlyqData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ZlajEwyqInfoManager yqm = (ZlajEwyqInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_EWYQ_INFO);
		String msg = "noInfo";
		Map<String,Object> map  = new HashMap<String,Object>();
		List<Object> list_d = new ArrayList<Object>();
		String roleName = this.getLoginRoleName(request);
		if(roleName.equals("super") || this.getLoginType(request).equals("cpyUser")){
			String yqType = CommonTools.getFinalStr(request.getParameter("yqType"));
			if(yqType.equals("fmxx")){
				List<Object> list_fm = new ArrayList<Object>();
				List<Object> list_xx = new ArrayList<Object>();
				List<ZlajEwyqInfoTb> yqList_fm = yqm.listInfoByType("fm");
				if(yqList_fm.size() > 0){
					msg = "success";
					for(Iterator<ZlajEwyqInfoTb> it = yqList_fm.iterator() ; it.hasNext();){
						ZlajEwyqInfoTb yq = it.next();
						Map<String,Object> map_d  = new HashMap<String,Object>();
						map_d.put("id", yq.getId());
						map_d.put("yqContent", yq.getYqContent());
						String yqType_db = yq.getYqType();
						String yqTypeChi = "";
						if(yqType_db.indexOf("fm") >= 0){
							yqTypeChi = "发明,";
						}
						if(yqType_db.indexOf("syxx") >= 0){
							yqTypeChi += "实用新型,";
						}
						if(yqType_db.indexOf("wg") >= 0){
							yqTypeChi += "外观,";
						}
						if(!yqTypeChi.equals("")){
							yqTypeChi = yqTypeChi.substring(0, yqTypeChi.length() - 1);
						}
						map_d.put("yqTypeChi", yqTypeChi);
						list_fm.add(map_d);
					}
					map.put("fmyqInfo", list_fm);
				}
				List<ZlajEwyqInfoTb> yqList_xx = yqm.listInfoByType("syxx");
				if(yqList_xx.size() > 0){
					msg = "success";
					for(Iterator<ZlajEwyqInfoTb> it = yqList_xx.iterator() ; it.hasNext();){
						ZlajEwyqInfoTb yq = it.next();
						Map<String,Object> map_d  = new HashMap<String,Object>();
						map_d.put("id", yq.getId());
						map_d.put("yqContent", yq.getYqContent());
						String yqType_db = yq.getYqType();
						String yqTypeChi = "";
						if(yqType_db.indexOf("fm") >= 0){
							yqTypeChi = "发明,";
						}
						if(yqType_db.indexOf("syxx") >= 0){
							yqTypeChi += "实用新型,";
						}
						if(yqType_db.indexOf("wg") >= 0){
							yqTypeChi += "外观,";
						}
						if(!yqTypeChi.equals("")){
							yqTypeChi = yqTypeChi.substring(0, yqTypeChi.length() - 1);
						}
						map_d.put("yqTypeChi", yqTypeChi);
						list_xx.add(map_d);
					}
					map.put("xxyqInfo", list_xx);
				}
			}else{
				List<ZlajEwyqInfoTb> yqList = yqm.listInfoByType(yqType);
				if(yqList.size() > 0){
					msg = "success";
					for(Iterator<ZlajEwyqInfoTb> it = yqList.iterator() ; it.hasNext();){
						ZlajEwyqInfoTb yq = it.next();
						Map<String,Object> map_d  = new HashMap<String,Object>();
						map_d.put("id", yq.getId());
						map_d.put("yqContent", yq.getYqContent());
						String yqType_db = yq.getYqType();
						String yqTypeChi = "";
						if(yqType_db.indexOf("fm") >= 0){
							yqTypeChi = "发明,";
						}
						if(yqType_db.indexOf("syxx") >= 0){
							yqTypeChi += "实用新型,";
						}
						if(yqType_db.indexOf("wg") >= 0){
							yqTypeChi += "外观,";
						}
						if(!yqTypeChi.equals("")){
							yqTypeChi = yqTypeChi.substring(0, yqTypeChi.length() - 1);
						}
						map_d.put("yqTypeChi", yqTypeChi);
						list_d.add(map_d);
					}
					map.put("yqInfo", list_d);
				}
			}
		}else{
			msg = "noAbility";
		}
		map.put("result", msg);
		this.getJsonPkg(map, response);
		return null;
	}
}