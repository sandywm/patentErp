
package com.patent.action.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.patent.util.WebUrl;

public class InitServlet extends HttpServlet
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InitServlet()
    {
    }

    public void init()throws ServletException{
    	WebUrl.DATA_URL_PRO = getServletContext().getRealPath("/");
    	
    	WebUrl.DATA_URL_WEB_INFO = getServletContext().getRealPath("/WEB-INF/");
    	
    	WebUrl.DATA_URL_YYZZ_UPLOAD = getServletContext().getRealPath("/Module/uploadFile/yyzz");
    	
    	WebUrl.LOG_URL = getServletContext().getRealPath("/Module/sendLog");
    	
    	WebUrl.DATA_URL_UP_FILE_UPLOAD = getServletContext().getRealPath("/Module/uploadFile");
    	
    	WebUrl.NEW_DATA_URL_UP_FILE_UPLOAD = "Module/uploadFile/";
    	
    	WebUrl.DATA_URL_TZS_IMG = getServletContext().getRealPath("/Module/tzsImg");
    	
    	WebUrl.NEW_DATA_URL_TZS_IMG = "/Module/tzsImg";
    	
    	WebUrl.DATA_URL_QRS_ZIP = getServletContext().getRealPath("/Module/qrsZip");
    	
    	WebUrl.NEW_DATA_URL_QRS_ZIP = "Module\\qrsZip";
    }
}
