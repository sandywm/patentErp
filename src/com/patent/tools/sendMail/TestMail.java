package com.patent.tools.sendMail;

import com.patent.tools.InviteCode;
import com.patent.tools.sendMail.MailSendInfo;
import com.patent.tools.sendMail.SimpleMailSender;

public class TestMail {

	/**
	 * @description
	 * @author wm
	 * @date 2018-7-30 上午09:36:10
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 MailSendInfo mailInfo = new MailSendInfo();    
	      mailInfo.setMailServerHost("smtp.163.com");    
	      mailInfo.setMailServerPort("25");    
	      mailInfo.setValidate(true);    
	      mailInfo.setUserName("service_cus@163.com");//邮箱账号    
	      mailInfo.setPassword("32011823wmk");//您的邮箱授权码 
	      mailInfo.setFromAddress("service_cus@163.com");//邮箱地址（同账号）  
	      mailInfo.setToAddress("13311089766@189.cn");//邮件接收人地址 
	      mailInfo.setSubject("重置密码验证码");    
	      String code = InviteCode.getRandomNumberCode();
	      mailInfo.setContent("你的验证码是："+code + " 该验证码30分钟内有效，请尽快使用!");    
//	                这个类主要来发送邮件   
	      boolean flag = false;
	      SimpleMailSender sms = new SimpleMailSender();   
	      flag = sms.sendTextMail(mailInfo);//发送文体格式    
//	      flag = SimpleMailSender.sendHtmlMail(mailInfo);//发送html格式   
	      System.out.println("发送邮件成功标志: "+flag);
	}

}
