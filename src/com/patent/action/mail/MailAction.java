/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.mail;

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
import com.patent.module.MailInfoTb;
import com.patent.page.PageConst;
import com.patent.service.MailInfoManager;
import com.patent.tools.CommonTools;
import com.patent.util.Constants;

/** 
 * MyEclipse Struts
 * Creation date: 08-17-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class MailAction extends DispatchAction {
	
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
	 * 导向邮件页面
	 * @description
	 * @author wm
	 * @date 2018-8-17 上午08:28:04
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goMailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return mapping.findForward("mailPage");
	}
	
	/**
	 * 修改邮件读取状态
	 * @description
	 * @author wm
	 * @date 2018-8-17 上午08:30:13
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward updateMailStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
		Integer mailId = CommonTools.getFinalInteger(request.getParameter("mailId"));
		Map<String,String> map = new HashMap<String,String>();
		if(mailId > 0){
			List<MailInfoTb> mList = mm.listInfoByOpt(this.getLoginUserId(request), this.getLoginType(request), mailId);
			if(mList.size() > 0){
				mm.updateReadStatusById(mailId, 1);
				map.put("result", "success");
			}else{
				map.put("result", "error");
			}
		}else{
			map.put("result", "error");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 删除指定邮件
	 * @description
	 * @author wm
	 * @date 2018-8-17 上午08:30:16
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward delMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
		Integer mailId = CommonTools.getFinalInteger(request.getParameter("mailId"));
		Map<String,String> map = new HashMap<String,String>();
		if(mailId > 0){
			List<MailInfoTb> mList = mm.listInfoByOpt(this.getLoginUserId(request), this.getLoginType(request), mailId);
			if(mList.size() > 0){
				mm.delMailById(mailId);
				map.put("result", "success");
			}else{
				map.put("result", "error");
			}
		}else{
			map.put("result", "error");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 根据条件分页获取邮件列表
	 * @description
	 * @author wm
	 * @date 2018-8-17 上午08:30:19
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getMailPageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
		String mailType = request.getParameter("mailType");
		String mailTitle = Transcode.unescape(request.getParameter("mailTitle"), request);
		Integer readStatus = Integer.parseInt(request.getParameter("readStatus"));
		Integer count = mm.getCountByOpt(this.getLoginUserId(request), this.getLoginType(request), mailType, mailTitle, readStatus);
		Map<String,Object> map = new HashMap<String,Object>();
		if(count > 0){
//			Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("pageSize")), 10);
//			Integer pageCount = PageConst.getPageCount(count, pageSize);
//			Integer pageNo = PageConst.getPageNo(String.valueOf(request.getParameter("pageNo")), pageCount);
			
			Integer pageSize = PageConst.getPageSize(String.valueOf(request.getParameter("limit")), 10);//等同于pageSize
			Integer pageNo = CommonTools.getFinalInteger(request.getParameter("page"));//等同于pageNo
			
			List<MailInfoTb> mList = mm.listPageInfoByOpt(this.getLoginUserId(request), this.getLoginType(request), mailType, mailTitle, readStatus, pageNo, pageSize);
			List<Object> list_d = new ArrayList<Object>();
			for(Iterator<MailInfoTb> it = mList.iterator() ; it.hasNext();){
				MailInfoTb mail = it.next();
				Map<String,Object> map_d = new HashMap<String,Object>();
				map_d.put("id", mail.getId());
				map_d.put("mailType", mail.getMailType());
				map_d.put("sendInfo", mail.getSendInfo());
				map_d.put("mailTitle", mail.getMailTitle());
				map_d.put("mailContent", mail.getMailContent());
				map_d.put("sendTime", mail.getSendTime());
				if(mail.getReadStatus().equals(0)){
					map_d.put("readStatus", "未读");
				}else{
					map_d.put("readStatus", "已读");
				}
				list_d.add(map_d);
			}
			map.put("data", list_d);
			map.put("msg", "success");
			map.put("count", count);
			map.put("code", 0);
		}else{
			map.put("msg", "noInfo");
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 根据条件获取邮件记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-17 上午08:30:24
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward getMailCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
		String mailType = request.getParameter("mailType");
		String mailTitle = Transcode.unescape(request.getParameter("mailTitle"), request);
		Integer readStatus = Integer.parseInt(request.getParameter("readStatus"));
		Integer count = mm.getCountByOpt(this.getLoginUserId(request), this.getLoginType(request), mailType, mailTitle, readStatus);
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("result", count);
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
	/**
	 * 获取指定邮件的详细记录
	 * @description
	 * @author wm
	 * @date 2018-8-17 上午09:48:29
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMailDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
		Integer mailId = CommonTools.getFinalInteger(request.getParameter("mailId"));
		Map<String,Object> map = new HashMap<String,Object>();
		if(mailId > 0){
			List<MailInfoTb> mList = mm.listInfoByOpt(this.getLoginUserId(request), this.getLoginType(request), mailId);
			if(mList.size() > 0){
				MailInfoTb mail = mList.get(0);
				map.put("id", mail.getId());
				map.put("mailType", mail.getMailType());
				map.put("sendInfo", mail.getSendInfo());
				map.put("mailTitle", mail.getMailTitle());
				map.put("mailContent", mail.getMailContent());
				map.put("sendTime", mail.getSendTime());
				mm.updateReadStatusById(mailId, 1);//点击打开后修改邮件的已读状态
			}
		}
		String json = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();  
        pw.write(json); 
        pw.flush();  
        pw.close();
		return null;
	}
	
}