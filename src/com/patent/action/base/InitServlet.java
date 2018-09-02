
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
    	
    	WebUrl.DATA_URL_JS_FILE_UPLOAD = getServletContext().getRealPath("/Module/uploadFile/jsFile");
    	
    	WebUrl.DATA_URL_TZS_FILE_UPLOAD = getServletContext().getRealPath("/Module/uploadFile/tzsFile");
    }
}
