package com.patent.web;

import com.patent.factory.AppFactory;
import com.patent.module.ApplyInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.module.SuperUser;
import com.patent.service.ApplyInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.SuperUserManager;
import com.patent.tools.CommonTools;
import com.patent.tools.DataBaseSqlVerify;
import com.patent.util.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class UserLoginFilter implements Filter{
	
	private String forwardPath = null;
	private FilterConfig filterConfig = null;

	public void destroy()
	{
		this.forwardPath = null;
		this.filterConfig = null;
	}

	@SuppressWarnings("null")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String requestUrl[] = httpServletRequest.getRequestURI().split(";");
		//攻击检测增加代码---start
		String param =  httpServletRequest.getQueryString();//action动作地址
		String filePath = "e:\\attackReport.txt";
		String oldParam = param;
		CpyUserInfoManager cpym = null;
		ApplyInfoManager am = null;
		SuperUserManager sum = null;
		if(param == null || param.equals("")){
			
		}else{
			Integer indexNumber = param.indexOf("&");
			if(indexNumber >= 0){
				param = param.substring(indexNumber);
				boolean flag = DataBaseSqlVerify.checkSql(param);
    			if(flag){//sql注入攻击
    				String subject = "检测到sql攻击";
    				CommonTools.sendAttackReport(filePath,subject, httpServletRequest,oldParam);
    				return;
    			}else{//没攻击继续查询xss攻击
    				flag = DataBaseSqlVerify.checkXss(param);
    				if(flag){//存在攻击
    					String subject = "检测到xss攻击";
    					CommonTools.sendAttackReport(filePath,subject, httpServletRequest,oldParam);
    					return;
    				}
    			}
    		}
		}
		//攻击检测增加代码---end
	    String requesturi = requestUrl[0]; 
		// 通过检查session中的变量，过滤请求
		HttpSession session = httpServletRequest.getSession(false);
		Integer loginFlag = -1;
		String loginType = "";
		Integer userId = 0;
		if(session != null){
			//获取用户session中的loginFlag
			loginFlag = (Integer)session.getAttribute(Constants.LOGIN_TIMES);
			//获取用户session中的用户编号
			userId = (Integer)session.getAttribute(Constants.LOGIN_USER_ID);
			if(userId == null){
				userId = 0;
			}
		}
		Integer loginFlag_dataBase = -1;
		if(userId.equals(0)){
			if(!requesturi.endsWith("/login.do") 
					&& !requesturi.endsWith("/authImg")
					&& !requesturi.endsWith("jsp")
					&& !requesturi.endsWith("css") 
					&& !requesturi.endsWith("js")
					&& !requesturi.endsWith("png")
					&& !requesturi.endsWith("gif")
					&& !requesturi.endsWith("jpg")
					&& !requesturi.endsWith("jpeg")
					&& !requesturi.endsWith("ico")
					&& !requesturi.endsWith("ttf")
					&& !requesturi.endsWith(httpServletRequest.getContextPath()+ "/")){
                String url = "window.top.location.href='login.do?action=loginOut'";
				String authorizeScript = "由于您60分钟内没上线，系统已强制您下线，请重新登录！";
				Ability.PrintAuthorizeScript(url,authorizeScript, httpServletResponse);
                return;
            }
			chain.doFilter(request, response);
		}else{
			try {
				cpym = (CpyUserInfoManager)AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
				am = (ApplyInfoManager)AppFactory.instance(null).getApp(Constants.WEB_APPLY_INFO);
				sum = (SuperUserManager)AppFactory.instance(null).getApp(Constants.WEB_SUPER_USER_INFO);
				loginType = String.valueOf(session.getAttribute(Constants.LOGIN_TYPE));
				if(loginType.equals("cpyUser")){
					CpyUserInfo cpyUser = cpym.getEntityById(userId);
					if(cpyUser != null){
						loginFlag_dataBase = cpyUser.getUserLoginTimes();
					}
				}else if(loginType.equals("appUser")){
					ApplyInfoTb appUser = am.getEntityById(userId);
					if(appUser != null){
						loginFlag_dataBase = appUser.getUserLoginTimes();
					}
				}else if(loginType.equals("spUser")){
					List<SuperUser> suList = sum.listInfoById(userId);
					if(suList.size() > 0){
						loginFlag_dataBase = suList.get(0).getLoginTimes();
					}
				}else{
					
				}
				//获取数据库中指定currentUser的loginFlag
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!loginFlag.equals(loginFlag_dataBase)){
				session.invalidate();
				String url = "window.location.href='login.do?action=loginOut'";
				String authorizeScript = "该账号已经在别处登录，系统已强制您下线，请重新登录！";
				Ability.PrintAuthorizeScript(url,authorizeScript, httpServletResponse);
			}else{
				chain.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
		this.forwardPath = filterConfig.getInitParameter("forwardpath");
	}
}
