
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
    
    /**
     * 打开页面时获取当前用户权限（增加、修改、删除）
     * @description
     * @author wm
     * @date 2018-8-13 下午05:03:10
     * @param actNameEngArr
     * @param loginType
     * @param loginRoleName
     * @return
     * @throws Exception 
     */
    public static String getAbilityInfo(String actNameEngArr,String loginType,String loginRoleName,Integer roleId) throws Exception{
    	String abilityStr = "";
    	if(actNameEngArr.split(",").length == 2){
    		abilityStr = "false,false";
        	if(loginType.equals("cpyUser")){
        		if(loginRoleName.equals("管理员")){
        			abilityStr = "true,true";
        		}else{
        			boolean addFlag = Ability.checkAuthorization(roleId, actNameEngArr.split(",")[0]);
        			boolean upFlag = Ability.checkAuthorization(roleId, actNameEngArr.split(",")[1]);
        			abilityStr = addFlag + "," + upFlag;
        		}
        	}
    	}else{//3种
    		abilityStr = "false,false,false";
        	if(loginType.equals("cpyUser")){
        		if(loginRoleName.equals("管理员")){
        			abilityStr = "true,true,true";
        		}else{
        			boolean addFlag = Ability.checkAuthorization(roleId, actNameEngArr.split(",")[0]);
        			boolean upFlag = Ability.checkAuthorization(roleId, actNameEngArr.split(",")[1]);
        			boolean delFlag = Ability.checkAuthorization(roleId, actNameEngArr.split(",")[2]);
        			abilityStr = addFlag + "," + upFlag + "," + delFlag;
        		}
        	}
    	}
    	
    	return abilityStr;
    }
}
