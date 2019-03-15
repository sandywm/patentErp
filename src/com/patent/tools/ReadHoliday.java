package com.patent.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


public class ReadHoliday {

	/**
	 * 获取指定日期是否是节假日
	 * @description
	 * @author Administrator
	 * @date 2019-3-11 下午04:03:14
	 * @param httpArg
	 * @return  0 上班 1周末 2节假日
	 */
	public static String getHoliday(String httpArg){
//		String httpUrl="http://www.easybots.cn/api/holiday.php";
		String httpUrl = "http://api.goseek.cn/Tools/holiday";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
//        httpUrl = httpUrl + "?d=" + httpArg;
        httpUrl = httpUrl + "?date=" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}
	
	/**
	 * @description
	 * @author Administrator
	 * @date 2019-3-11 下午03:54:52
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd");
//        String specDate=f.format(new Date());
//        System.out.println(specDate);
		String specDate = "20190307";
		String jsonResult = getHoliday(specDate);
		System.out.println(jsonResult);
		JSONObject  jasonObject = JSONObject.parseObject(jsonResult);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String,String> map = (Map)jasonObject;
		System.out.println(map.get(specDate.replaceAll("-", "")));
	}

}
