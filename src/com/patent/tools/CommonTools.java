package com.patent.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.ListIterator;
//import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.patent.util.Constants;

public class CommonTools {
	
	/**
	 * null转换为""
	 * @description
	 * @author wm
	 * @date 2017-5-2 上午10:32:59
	 * @param inputStr
	 * @return
	 */
	public static String getFinalStr(String inputStr){
		inputStr = String.valueOf(inputStr);
		if(inputStr == "null"){
			return "";
		}else{
			return inputStr;
		}
	}
	
	/**
	 * null转换为""
	 * @description
	 * @author wm
	 * @date 2018-8-25 下午06:01:28
	 * @param inputStr
	 * @param request
	 * @return
	 */
	public static String getFinalStr(String inputStr,HttpServletRequest request){
		inputStr = String.valueOf(request.getParameter(inputStr));
		if(inputStr == "null"){
			return "";
		}else{
			return inputStr;
		}
	}
	
	/**
	 * 输入的数字类型转换成数据类型
	 * @description
	 * @author wm
	 * @date 2016-9-6 下午03:09:46
	 * @param inputData
	 * @return
	 */
	public static Integer getFinalInteger(String inputData){
		inputData = String.valueOf(inputData);
		if(inputData.equals("") || inputData.equals("null")){
			return 0;
		}else{
			return Integer.parseInt(inputData);
		}
	}
	
	/**
	 * 输入的数字类型转换成数据类型
	 * @description
	 * @author wm
	 * @date 2016-9-6 下午03:09:46
	 * @param inputData
	 * @return
	 */
	public static Integer getFinalInteger(String inputData,HttpServletRequest request){
		inputData = String.valueOf(request.getParameter(inputData));
		if(inputData.equals("") || inputData.equals("null")){
			return 0;
		}else{
			return Integer.parseInt(inputData);
		}
	}
	
	/**
	 * 输入的数字类型转换成数据类型
	 * @description
	 * @author wm
	 * @date 2017-4-21 上午11:16:34
	 * @param inputData
	 * @return
	 */
	public static Float getFinalFloat(String inputData){
		inputData = String.valueOf(inputData);
		if(inputData.equals("") || inputData.equals("null")){
			return 0f;
		}else{
			return Float.parseFloat(inputData);
		}
	}

	/**
	 * 输入的数字类型转换成数据类型
	 * @description
	 * @author wm
	 * @date 2017-4-21 上午11:16:34
	 * @param inputData
	 * @return
	 */
	public static Float getFinalFloat(String inputData,HttpServletRequest request){
		inputData = String.valueOf(request.getParameter(inputData));
		if(inputData.equals("") || inputData.equals("null")){
			return 0f;
		}else{
			return Float.parseFloat(inputData);
		}
	}
	
	/**
	 * 输入的数字类型转换成数据类型
	 * @description
	 * @author wm
	 * @date 2017-4-21 上午11:16:34
	 * @param inputData
	 * @return
	 */
	public static Double getFinalDouble(String inputData,HttpServletRequest request){
		inputData = String.valueOf(request.getParameter(inputData));
		if(inputData.equals("") || inputData.equals("null")){
			return 0.0;
		}else{
			return Double.parseDouble(inputData);
		}
	}
	
	/**
     * 写入xss/sql注入攻击报告
     * @description
     * @author wm
     * @date 2016-4-29 上午08:34:23
     * @param subject
     * @param request
     */
    public static void sendAttackReport(String filePath,String content,HttpServletRequest request,String attackUrl){
		String attackInfo = "      攻击时间："+CurrentTime.getCurrentTime() + "      攻击IP："+CommonTools.getIpAddress(request) + "     攻击URL："+attackUrl;
		File oldfile = new File(filePath);  
		if(!oldfile.exists()){
			 try {
				oldfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileWriter fw;
		try {
			fw = new FileWriter(oldfile, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("  "+content+attackInfo);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
  //获取真实IP地址
	public static String getIpAddress(HttpServletRequest request){
		String ipAddress = "";
		ipAddress = request.getHeader("x-forwarded-for");    
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
			ipAddress = request.getHeader("Proxy-Client-IP");    
	    }
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
	        ipAddress = request.getHeader("WL-Proxy-Client-IP");    
	    }  
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	        ipAddress = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	        ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
			ipAddress = request.getRemoteAddr();    
			if(ipAddress.equals("127.0.0.1") ||ipAddress.equals("0:0:0:0:0:0:0:1")){    
				//根据网卡取本机配置的IP    
				InetAddress inet=null;    
			    try {    
			    	inet = InetAddress.getLocalHost();    
			    } catch (UnknownHostException e) {    
			    	e.printStackTrace();    
			    }    
			    ipAddress= inet.getHostAddress();
			}          
	    }   
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割    
	    if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15    
	    	if(ipAddress.indexOf(",")>0){    
	    		ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));    
	    	}    
	    }    
		return ipAddress;
	}
	
	/**
	 * 根据IP地址获取当前省、市
	 * @description
	 * @author wm
	 * @date 2018-8-2 下午04:18:06
	 * @param ip
	 * @return
	 */
	public static String getSelfArea(String ip) {
		String address="";
		String prov = "",city = "";
		String strUrl="https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query="+ip+"&co=&resource_id=6006&t=1444747793291&ie=utf8&oe=utf8&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110207215902183078953_1444747767470&_=1444747767472";
		try { 
			URL url = new URL(strUrl);
			
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
	        //add reuqest header
	        con.setRequestMethod("GET");
	        // Send post request
	        con.setDoOutput(true);
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	 
	        int idx=response.indexOf("{\"location\":\"");
	        if(idx != -1){
		        String response2 = response.substring(response.indexOf("{\"location\":\"")+13,response.length());
		        address = response2.substring(0,response2.indexOf("\""));
		        String[] zzq = {"内蒙古","新疆","西藏","广西","宁夏"};
				String[] zxs = {"北京","天津","上海","重庆"};
				String[] xzq = {"香港","澳门"};
				if(address.contains("自治区")){//自治区
					for(Integer i = 0 ; i < zzq.length ; i++){
						if(address.contains(zzq[i])){
							prov = zzq[i];
							Integer startIndex = address.indexOf("自治区");
							city = address.substring(startIndex+3, address.indexOf("市"));
							break;
						}
					}
				}else if(address.contains("省")){//省、市
					Integer startIndex = address.indexOf("省");
					Integer endIndex = address.indexOf("市");
					prov = address.substring(0,startIndex);
					city = address.substring(startIndex+1, endIndex);
				}else if(address.contains("行政区")){//特别行政区
					for(Integer i = 0 ; i < xzq.length ; i++){
						if(address.contains(xzq[i])){
							prov = city = xzq[i];
							break;
						}
					}
				}else{//直辖市
					for(Integer i = 0 ; i < zxs.length ; i++){
						if(address.contains(zxs[i])){
							prov = city = zxs[i];
							break;
						}
					}
				}
				address = prov + ":" + city;
	        }
	        else
	        	address = "un-know";
		} catch (Exception e) {
			e.printStackTrace();
			address = "un-know";
		}
		return address;
	}
	
	/**
	 * 获取客户端信息（上述2种方法的整合）分清安卓、ios、pc、移动浏览器
	 * @description
	 * @author wm
	 * @date 2016-7-14 上午08:38:13
	 * @param request
	 * @return
	 */
	public static String getCilentInfo_new(HttpServletRequest request){
		String clientInfo = request.getHeader("User-agent");
		String cilentQuip = "";
		if(clientInfo != null){
			if(clientInfo.indexOf("Android") >= 0 || clientInfo.indexOf("iPad") >= 0 || clientInfo.indexOf("iPhone") >= 0){
				if(clientInfo.indexOf("AppleWebKit") > 0){
					cilentQuip = "mobileBrowser";//移动端浏览器
				}else{
					if(clientInfo.indexOf("Android") >= 0){//移动端APP
						cilentQuip = "andriodApp";
					}else if(clientInfo.indexOf("iPad") >= 0 || clientInfo.indexOf("iPhone") >= 0){
						cilentQuip = "iphoneApp";
					}
				}
			}else{
				cilentQuip = "pc";//PC端
			}
		}else{
			cilentQuip = "";//无法获取客户端信息
		}
		return cilentQuip;
	}
	
	/**
	 * 根据传递的入库单编号自动获取下一个
	 * @description
	 * @author wm
	 * @date 2017-5-11 上午09:30:26
	 * @param insNoStr 数据库中最后一个入库单编号
	 * @param preSuffix 前缀字母代号
	 * @return
	 */
	public static String getInStoreNo(String insNoStr){
		String insNo_base = insNoStr.split("_")[1];
		String preStr = insNoStr.split("_")[0];
		Integer insNoLength = insNo_base.length();
		String insNo_curr_str = String.valueOf((Integer.parseInt(insNo_base) + 1));
		Integer insNo_curr_length = insNo_curr_str.length();
		Integer diff = insNoLength - insNo_curr_length;
		preStr += "_"; 
		for(Integer i = 0 ; i < diff ; i++){
			preStr += "0";
		}
		return preStr + insNo_curr_str;
	}
	
	public static boolean checkMobile(String mobile) {
		if (mobile.equals("")) {
			return false;
		} else {
			String regex = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(mobile);

			return m.find();
		}

	}

	/**
	 * 长整形对象转成整形（用于获取记录数时使用）
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午10:07:53
	 * @param count_obj
	 * @return
	 */
	public static Integer longToInt(Object count_obj){
		long count = 0;
		if(count_obj != null){
			count =  Long.parseLong(String.valueOf( count_obj));
		}
		return (int)count;
	}
	
	/**
	 * 检查邮箱格式
	 * @description
	 * @author wm
	 * @date 2018-7-30 下午04:14:40
	 * @param inputEmail
	 * @return 证成功返回true，验证失败返回false 
	 */
	public static boolean checkEmail(String inputEmail){
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return Pattern.matches(regex, inputEmail);   
	}
	
	/**
	 * 根据年度、专利类型获取年费
	 * @description
	 * @author Administrator
	 * @date 2018-10-28 上午09:25:21
	 * @param yearNum
	 * @param zlType
	 * @return
	 */
	public static Double getYearFee(Integer yearNum,String zlType){
		Double yearFee = 0d;
		if(zlType.equals("fm")){
			if(yearNum >= 1 && yearNum <= 3){
				yearFee = Constants.FM_YEAR_FEE_1_3;
			}else if(yearNum >= 4 && yearNum <= 6){
				yearFee = Constants.FM_YEAR_FEE_4_6;
			}else if(yearNum >= 7 && yearNum <= 9){
				yearFee = Constants.FM_YEAR_FEE_7_9;
			}else if(yearNum >= 10 && yearNum <= 12){
				yearFee = Constants.FM_YEAR_FEE_10_12;
			}else if(yearNum >= 13 && yearNum <= 15){
				yearFee = Constants.FM_YEAR_FEE_13_15;
			}else if(yearNum >= 16 && yearNum <= 20 ){
				yearFee = Constants.FM_YEAR_FEE_16_20;
			}
		}else{//新型+外观
			if(yearNum >= 1 && yearNum <= 3){
				yearFee = Constants.SY_WG_YEAR_FEE_1_3;
			}else if(yearNum >= 4 && yearNum <= 6){
				yearFee = Constants.SY_WG_YEAR_FEE_4_5;
			}else if(yearNum >= 7 && yearNum <= 9){
				yearFee = Constants.SY_WG_YEAR_FEE_6_8;
			}else if(yearNum >= 10 && yearNum <= 12){
				yearFee = Constants.SY_WG_YEAR_FEE_9_10;
			}
		}
		return yearFee;
	}

	/**
	 * 检查用户登录结果
	 * @author wm
	 * @date 2019-5-6 下午04:36:00
	 * @param account
	 * @param password
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	public static String checkLoginUser(String account,String password) throws Exception{
		String result = "";//格式为登录状态:管理科室：模块(1:油综调配见效率,2:水综合调配方案,3:水综层段合格率,4:作业注水合格率)
		if(!account.equals("") && !password.equals("")){
			String s = null;
			File file = new File("e:/loginUser.json");;
			InputStreamReader br = new InputStreamReader(new FileInputStream(file),"utf-8");//读取文件,同时指定编码
			StringBuffer sb = new StringBuffer();
	        char[] ch = new char[128];  //一次读取128个字符
	        int len = 0;
	        while((len = br.read(ch,0, ch.length)) != -1){
	            sb.append(ch, 0, len);
	        }
	        s = sb.toString();
	        String account_json = "";
	        String password_json = "";
	        
	        JSONObject dataJson = JSON.parseObject(s); 
	        JSONArray features = dataJson.getJSONArray("userList");// 找到features json数组
	        for(Integer i = 0 ; i < features.size() ; i++){
	        	JSONObject stuInfo = features.getJSONObject(i);// 获取features数组的第i个json对象
	        	account_json = stuInfo.getString("account");//获取账号
	        	password_json = stuInfo.getString("password");
	        	if(account_json.equals(account) && password_json.equals(password)){//账号和密码都要相同
	        		result = "succ";
	        		result += ":"+stuInfo.getString("groupName") + ":" + stuInfo.getString("moduleId");
	        		break;
	        	}else{
	        		result = "fail";
	        	}
	        }
		}
		return result;
	}
	
	//jsong格式
	/**{
		"userList": [{
			"account": "wulei",
			"password": "123456",
			"groupName": "管理一室",
			"moduleId": "4"
		}, {
			"account": "wmk",
			"password": "123456",
			"groupName": "管理二室",
			"moduleId": "1,2"
		}, {
			"account": "sandy",
			"password": "123456",
			"groupName": "管理二室",
			"moduleId": "3"
		}]
	}**/
	
	public static void main(String[] args) throws Exception{
		System.out.print(CommonTools.checkLoginUser("wmk", "123456"));
	}
}
