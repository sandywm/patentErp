
package com.patent.web;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.patent.factory.AppFactory;
import com.patent.service.ActRoleInfoManager;
import com.patent.util.Constants;

public class Ability
{

    public Ability()
    {
    }
  //打印出没有权限对话框（带导向地址）
    public static void PrintAuthorizeScript(String url, String authorizeScript, HttpServletResponse response)
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String script = "";
        script += "<script language=\"Javascript\">\n";
        script += url + "\n";
        script += "alert('" + authorizeScript+ "')\n";
        script += "</script>\n";
        try
        {
            response.getWriter().print(script);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取指定角色有无指定动作的权限(查看不计)
     * @description
     * @author wm
     * @date 2018-8-10 上午10:14:51
     * @param roleId 角色编号
     * @param actNameEng 动作
     * @return
     * @throws Exception 
     */
    public static boolean checkAuthorization(Integer roleId,String actNameEng) throws Exception{
    	ActRoleInfoManager arm = (ActRoleInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ACT_ROLE_INFO);
		if(arm.listInfoByOpt(roleId, actNameEng).size() > 0){
			return true;
		}
		return false;
    }
}
