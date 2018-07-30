package com.patent.tools.sendMail;
import javax.mail.*;  

/**
 * smtp认证
 * @author Administrator
 *
 */

public class MyAuthenticator extends Authenticator{
	String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}
