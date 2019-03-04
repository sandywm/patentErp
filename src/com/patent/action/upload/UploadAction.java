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
import com.patent.factory.AppFactory;
import com.patent.service.CpyUserInfoManager;
import com.patent.service.ZlajMainInfoManager;
import com.patent.tools.CheckImage;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;
import com.patent.util.Constants;
import com.patent.util.WebUrl;
import com.patent.web.Ability;

/** 
 * MyEclipse Struts
 * Creation date: 09-03-2018
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class UploadAction extends DispatchAction {
	
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
	 * 获取session中的用户角色编号
	 * @param request
	 * @return
	 */
	private Integer getLoginRoleId(HttpServletRequest request){
        Integer userId = (Integer)request.getSession(false).getAttribute(Constants.LOGIN_USER_ROLE_ID);
        return userId;
	}
	
	/** 
	 * 上传文件
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 */
	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String msg = "";
		boolean upFlag = false;
		String fileUrl = "";
		Integer ajId = CommonTools.getFinalInteger("ajId", request);
		String fileType = CommonTools.getFinalStr("fileType", request);//tzs(通知书),fj(附件),pj(票据),dg(底稿),fee(已缴费清单)
		String loginType = this.getLoginType(request);
		Integer currLoginUserId = this.getLoginUserId(request);
		Map<String,Object> map = new HashMap<String,Object>();
		ZlajMainInfoManager zlm = (ZlajMainInfoManager) AppFactory.instance(null).getApp(Constants.WEB_ZLAJ_MAIN_INFO);
		Integer cpyId = 0;
		CpyUserInfoManager cum = (CpyUserInfoManager) AppFactory.instance(null).getApp(Constants.WEB_CPY_USER_INFO);
		//通知书、票据、附件需要具有专利流程处理权限的才能上传
		boolean abilityFlag = false;
		if(loginType.equals("cpyUser")){
			cpyId = cum.getEntityById(currLoginUserId).getCpyInfoTb().getId();
			if(this.getLoginRoleName(request).equals("管理员")){
				abilityFlag = true;
			}else{
				if(fileType.equals("dg")){//导入专利的技术底稿、定稿文件、合同文件权限
					boolean abilityFlag_1 = Ability.checkAuthorization(this.getLoginRoleId(request), "addZl");//增加专利的能上传
					boolean abilityFlag_2 = Ability.checkAuthorization(this.getLoginRoleId(request), "upZl");//修改专利的能上传
					abilityFlag = abilityFlag_1 || abilityFlag_2;
				}else if(fileType.equals("pj")){//上传发票
					abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "impPj");//上传发票权限
				}else if(fileType.equals("fee")){//上传已缴费清单
					abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "impFee");//导入已缴费清单权限
				}else if(fileType.equals("tzs")){//导入通知书
					if(zlm.listInfoByOpt("tzs", currLoginUserId, cpyId).size() > 0){
						abilityFlag = true;
					}
				}else{
					//撰稿人员、专利审核人员、客户确认人员、定稿提交、补正提交、补正审核人员上传的都是附件类
					abilityFlag = Ability.checkAuthorization(this.getLoginRoleId(request), "dealZl");//专利流程处理
				}
			}
		}else{
			if(fileType.equals("")){
				abilityFlag = true;
			}else{
				abilityFlag = true;
			}
		}
		
		if(abilityFlag){
			if (ServletFileUpload.isMultipartContent(request)){// 判断是否是上传文件
				DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();// 创建工厂对象
				ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory); // 创建上传对象
				try {
					List<FileItem> filelist = fileUpload.parseRequest(request);
					ListIterator<FileItem> iterator = filelist.listIterator();
					String userPath = WebUrl.DATA_URL_UP_FILE_UPLOAD + "\\" + loginType + "\\";
					String absolutPath = loginType + "\\";
					if(loginType.equals("appUser")){
						userPath += currLoginUserId;
						absolutPath += currLoginUserId;
						if(ajId > 0){
							userPath +=  "\\" + ajId;
							absolutPath +=  "\\" + ajId;
						}
					}else if(loginType.equals("cpyUser")){
						if(fileType.equals("fee")){
							absolutPath = "Module\\excelTemp\\"+cpyId+"\\feeImport\\";
							userPath = WebUrl.DATA_URL_PRO + absolutPath;
						}else{
							if(ajId > 0){
								userPath += ajId + "\\" + fileType;
								absolutPath += ajId + "\\" + fileType;
							}else{
								//技术底稿、批量上传的通知书
								//之前放在外层，等待专利增加后，剪切到ajId下
								userPath += "u_"+currLoginUserId;//怕引起文件名重复，暂时将代理机构下人员上传的文件存在u_id下
								absolutPath += "u_"+currLoginUserId;
							}
						}
					}
					while (iterator.hasNext()) {
						FileItem fileItem = iterator.next();// 获取文件对象
						// 处理文件上传
						String filename = fileItem.getName();// 获取名字
						Integer lastIndex = filename.lastIndexOf(".");
						String suffix = filename.substring(lastIndex+1);
						String filePre = filename.substring(0, lastIndex);
						filename = filePre + "_" + CurrentTime.getRadomTime() + "." + suffix;
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
							fileUrl +=  absolutPath  + "\\" + filename + ",";
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
		}else{
			map.put("msg", "noAbility");
			this.getJsonPkg(map, response);
		}
		
		return null;
	}
}