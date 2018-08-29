package com.patent.spring.config.timer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import com.patent.factory.AppFactory;
import com.patent.module.CpyInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.service.CpyInfoManager;
import com.patent.service.CpyRoleInfoManager;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.MailInfoManager;
import com.patent.tools.CurrentTime;
import com.patent.tools.sendMail.MailSendInfo;
import com.patent.tools.sendMail.SimpleMailSender;
import com.patent.util.Constants;
import com.patent.util.WebUrl;

public class SendEndDateMsg {

	/**
	 * 发送代理机构会员到期提醒
	 * @description
	 * @author wm
	 * @date 2018-8-6 上午11:12:15
	 * @return
	 * @throws Exception 
	 */
	public static void sendEDMsg() throws Exception{
		CpyInfoManager cm = (CpyInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_INFO);
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		MailInfoManager mm = (MailInfoManager) AppFactory.instance(null).getApp(Constants.WEB_MAIL_INFO);
	      //获取需要发送邮件提醒的代理机构的邮箱(5,1,0,-1)四天时发送邮件
	      List<CpyInfoTb> cpyList = cm.listEndDateCpyInfo();
	      if(cpyList.size() > 0){
	    	  for(Iterator<CpyInfoTb> it = cpyList.iterator() ; it.hasNext();){
	    		  CpyInfoTb cpy = it.next();
	    		  Integer cpyId = cpy.getId();
	    		  String cpyLevelChi = "";
	    		  if(cpy.getCpyLevel().equals(0)){
	    			  cpyLevelChi = "铜牌";
				  }else if(cpy.getCpyLevel().equals(1)){
					  cpyLevelChi = "银牌";
				  }else if(cpy.getCpyLevel().equals(2)){
					  cpyLevelChi = "金牌";
				  }else if(cpy.getCpyLevel().equals(3)){
					  cpyLevelChi = "钻石";
				  }
	    		  //获取该管理机构所有管理员的邮箱
	    		  List<CpyUserInfo> cUserList = cum.listManagerInfoByOpt(cpyId, "管理员");
	    		  if(cUserList.size() > 0){
	    			  for(Iterator<CpyUserInfo> it_1 = cUserList.iterator() ; it_1.hasNext();){
	    				  CpyUserInfo cUser = it_1.next();
	    				  String userEmail_manager = cUser.getUserEmail();
	    				  if(!userEmail_manager.equals("")){
	    					  Integer days_diff = CurrentTime.compareDate(CurrentTime.getStringDate(), cpy.getEndDate());
	    					  MailSendInfo mailInfo = new MailSendInfo();    
	    				      mailInfo.setMailServerHost(Constants.MAIL_SERVER_HOST);    
	    				      mailInfo.setMailServerPort(Constants.MAIL_SERVER_PORT);    
	    				      mailInfo.setValidate(Constants.VALIDATE_FLAG);    
	    				      mailInfo.setUserName(Constants.SYSTEM_EMAIL_ACCOUNT);//邮箱账号    
	    				      mailInfo.setPassword(Constants.SYSTEM_EMAIL_PASS);//您的邮箱授权码 
	    				      mailInfo.setFromAddress(Constants.SYSTEM_EMAIL_ACCOUNT);//邮箱地址（同账号）  
	    					  mailInfo.setToAddress(userEmail_manager);//邮件接收人地址 
	    				      mailInfo.setSubject("代理机构会员到期提醒"); 
	    				      String endChi = "";
	    				      if(days_diff > 0){
	    				    	  endChi = "将于";
	    				      }else{
	    				    	  endChi = "已于";
	    				      }
	    				      endChi += cpy.getEndDate()+"到期,为了不影响您的使用，请及时续订";;
	    				      mailInfo.setContent("尊敬的"+cpyLevelChi+"用户,您的会员"+endChi);  
	    				      //发送系统平台邮件
	    				      mm.addMail("endM", Constants.SYSTEM_EMAIL_ACCOUNT, cUser.getId(), "cpyUser", "代理机构会员到期提醒", "尊敬的"+cpyLevelChi+"用户,您的会员"+endChi);
	    				      boolean flag = SimpleMailSender.sendTextMail(mailInfo);
	    				      String logPath = WebUrl.LOG_URL + "/" + CurrentTime.getStringDate() + ".txt";
    				    	  File file = new File(logPath);
    				    	  if(!file.exists()){
	    				    		try {
	    				    			  file.createNewFile();
	    				  			} catch (IOException e) {
	    				  				// TODO Auto-generated catch block
	    				  				e.printStackTrace();
	    				  			}
    				    	  }
    				    	  FileWriter fw;
    				  		try {
    				  			fw = new FileWriter(file, true);
    				  			PrintWriter pw = new PrintWriter(fw);
    				  			if(flag){
    				  				pw.println("发送成功-----已向代理机构["+cpy.getCpyName()+"]发送会员到期提醒!");
    				  			}else{
    				  				pw.println("发送失败-----已向代理机构["+cpy.getCpyName()+"]发送会员到期提醒!");
    				  			}
    				  			pw.flush();
    				  			fw.flush();
    				  			pw.close();
    				  			fw.close();
    				  		} catch (IOException e1) {
    				  			// TODO Auto-generated catch block
    				  			e1.printStackTrace();
    				  		}
	    				  }else{
	    					  break;
	    				  }
	    			  }
	    		  }
	    	  }
	      }
	}
}
