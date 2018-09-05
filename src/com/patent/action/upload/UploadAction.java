/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.patent.action.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.patent.tools.CheckImage;
import com.patent.tools.CommonTools;
import com.patent.util.Constants;
import com.patent.util.WebUrl;

/** 
 * MyEclipse Struts
 * Creation date: 09-03-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class UploadAction extends DispatchAction {
	
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
	 * 获取session中的用户ID
	 * @param request
	 * @return
	 */
	private Integer getLoginUserId(HttpServletRequest request){
        Integer userId = (Integer)request.getSession(false).getAttribute(Constants.LOGIN_USER_ID);
        return userId;
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
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String msg = "";
		boolean upFlag = false;
		String fileUrl = "";
		Integer ajId = CommonTools.getFinalInteger("ajId", request);
		String fileType = CommonTools.getFinalStr("fileType", request);//tzs(通知书),fj(附件),pj(票据),dg(底稿)
		String loginType = this.getLoginType(request);
		Map<String,Object> map = new HashMap<String,Object>();
		if (ServletFileUpload.isMultipartContent(request)){// 判断是否是上传文件
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();// 创建工厂对象
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory); // 创建上传对象
			try {
				List<FileItem> filelist = fileUpload.parseRequest(request);
				ListIterator<FileItem> iterator = filelist.listIterator();
				String userPath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "/" + loginType + "/";
				if(loginType.equals("appUser")){
					userPath += this.getLoginUserId(request);
					if(ajId > 0){
						userPath +=  "/" + ajId;
					}
				}else if(loginType.equals("cpyUser")){
					if(ajId > 0){
						userPath += ajId + "/" + fileType;
					}else{
						//技术底稿
						//之前放在外层，等待专利增加后，剪切到ajId下
						userPath += "dg";
					}
					
				}
				
				while (iterator.hasNext()) {
					FileItem fileItem = iterator.next();// 获取文件对象
					// 处理文件上传
					String filename = fileItem.getName();// 获取名字
					Integer lastIndex = filename.lastIndexOf(".");
					String suffix = filename.substring(lastIndex+1);
					CheckImage ci = new CheckImage();
					//doc,docx,wps,xls,xlsx,txt,pdf,pptx,ppt,zip,rar,dwg,eml,jpg,png,bmp,gif,vsd,vsdx如果文件格式不在上述范围内请压缩成zip格式后上传
					String checkFileSuffixInfo = ci.getUpFileStuffix(suffix);
					if(checkFileSuffixInfo.equals("img")){//图片限制5M
						upFlag = ci.checkItemSize(fileItem, 5 * 1024 * 1024);
						if(!upFlag){
							msg = "outSize";
						}
					}else if(checkFileSuffixInfo.equals("file")){//文件限制10M
						upFlag = ci.checkItemSize(fileItem, 10 * 1024 * 1024);
						if(!upFlag){
							msg = "outSize";
						}
					}else{
						msg = "suffixError";
					}
					if(upFlag){
						byte[] data = fileItem.get();// 获取数据
						//没有该文件夹先创建文件夹
			    		File file = new File(userPath);
			    		if(!file.exists()){
			    			file.mkdirs();
			    		}
			    		FileOutputStream fileOutputStream = new FileOutputStream(userPath + "/" + filename);
						fileOutputStream.write(data);// 写入文件
						fileOutputStream.close();// 关闭文件流
						msg = "success";
						fileUrl += "/" + userPath + "/" + filename + ",";
					}
				}
				map.put("code", 0);
				map.put("msg", msg);
				if(!fileUrl.equals("")){
					fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
				}
				List<Object> list_f = new ArrayList<Object>();
				Map<String,String> map_f = new HashMap<String,String>();
				map_f.put("src", fileUrl);
				list_f.add(map_f);
				map.put("data", list_f);
				this.getJsonPkg(map, response);
			}catch (FileUploadException e) {
				e.printStackTrace();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}