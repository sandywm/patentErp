package com.patent.tools.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.patent.tools.CheckImage;
import com.patent.util.WebUrl;

@WebServlet(name = "UploadImgServlet", urlPatterns = { "/uploadImg" })
@MultipartConfig(fileSizeThreshold = 1024)
public class UploadImgServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Constructor of the object.
	 */
	public UploadImgServlet() {
		super();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		// 获取文件部件part
		Part part = request.getPart("fileToUpload"); // request.getParts();
		//自定义目录
		String ipthum=WebUrl.DATA_URL_YYZZ_UPLOAD;
		// 获取文件服务器头部信息
		String root = request.getServletContext().getRealPath(ipthum);
		File file = new File(root);
		  //判断文件夹是否存在,如果不存在则创建文件夹
		  if (!file.exists()) {
			  file.mkdir();
		  }
		  //上传图片原始名称
		String name = part.getHeader("content-disposition");
		//图片后缀
		String ext = name.substring(name.lastIndexOf("."), name.length() - 1);
		CheckImage ci = new CheckImage();
		if(ci.checkImageStuffix(ext)){
			//上传名称
			String imgName=UUID.randomUUID().toString() + ext;
			//上传完整路径
			String filename = root + "/" + imgName;
			part.write(filename);
			//图片上传后目录
			String imgURL = ipthum+"/"+imgName;
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(imgURL);
			out.flush();
			out.close();
		}else{
			System.out.println("请上传图片!");
		}
	}

}
