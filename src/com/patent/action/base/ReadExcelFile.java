package com.patent.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.patent.tools.CurrentTime;

import jxl.*;
import jxl.read.biff.BiffException;
public class ReadExcelFile {
	
	public static void readExcel(){
		int i;  
        Sheet sheet;  
        Workbook book;  
        Cell cell1,cell2,cell3; 
        try {
			book= Workbook.getWorkbook(new File("D://1234.xls"));
			//获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)  
			sheet=book.getSheet(0);   
            i = 1;
            Integer maxRow = sheet.getRows();
            while(i < maxRow){
            	//获取每一行的单元格   
                cell1=sheet.getCell(0,i);//（列，行）  
                cell2=sheet.getCell(1,i);  
                cell3=sheet.getCell(2,i);  
                if("".equals(cell1.getContents())==true)    //如果读取的数据为空  
                    break;  
                System.out.println(cell1.getContents()+"\t"+cell2.getContents()+"\t"+cell3.getContents());   
                i++;  
            }
            book.close();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		//ReadExcelFile.readExcel();
//		String aa = "2017-04-01 00:00:01";
//		System.out.print(CurrentTime.stringConvertToTimestamp(aa));
		
//		List<Object> list_d = new ArrayList<Object>();
//		for(Integer i = 1 ; i < 3 ; i ++){
//			Map<String,Object> map_d = new HashMap<String,Object>();
//			map_d.put("id", i);
//			map_d.put("bcName", "加工单位"+i);//加工单位
//			map_d.put("madeUserName", "加工人员"+i);//加工人员
//			List<Object> list_d_1 = new ArrayList<Object>();
//			for(Integer j = 1 ; j < 2 ; j++){
//				Map<String,Object> map_d_1 = new HashMap<String,Object>();
//				map_d_1.put("madeProName", "加工产品"+j);//加工的产品
//				map_d_1.put("rlDepName", "报损工序"+j);//报损、退回所在的工序
//				list_d_1.add(map_d_1);
//			}
//			map_d.put("isscInfo", list_d_1);
//			list_d.add(map_d);
//		}
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("result", list_d);
//		String json = JSON.toJSONString(map);
//		System.out.println(json);
//		Double aa = 0.9796;
//		System.out.println(aa * 100);
		//删除的是appUser/1/headBanner1.jpg
//		String oldFile = "appUser/1/4D数字化校园可行性报告.doc,appUser/1/headBanner.jpg,appUser/1/headBanner1.jpg";
//		String newFile = "appUser/1/4D数字化校园可行性报告.doc,appUser/1/headBanner2.jpg";
//		System.out.println(CurrentTime.getCurrentTime());
//		String[] oldFileArr =  oldFile.split(",");
//		String[] newFileArr =  newFile.split(",");
//		for(int i = 0 ; i < oldFileArr.length ; i++){
//			boolean existFlag = false;
//			for(int j = 0 ; j < newFileArr.length ; j++){
//				if(oldFileArr[i].equals(newFileArr[j])){
//					existFlag = true;
//					break;
//				}else{
//					existFlag = false;
//				}
//			}
//			if(!existFlag){
//				System.out.println("需要删除的："+oldFileArr[i]);
//			}
//		}
//		System.out.println(CurrentTime.getCurrentTime());
//		String bb = "aa_V1.jpg";
//		Integer lastIndex = bb.lastIndexOf("_");
//		String last_1 = bb.substring(lastIndex+1,bb.length());
//		Integer lastIndex_1 = last_1.indexOf(".");
//		String version = last_1.substring(0, lastIndex_1);
//		String suffix = last_1.substring(lastIndex_1+1, last_1.length());
//		System.out.println(version + "       "+suffix);
		Double aa = 1.5;
		Double bb = 1.50;
//		File file = new File("e:\\发明-补正通知书1.zip");
//		 if (file.exists()) { //文件存在时          
//			 System.out.println(true);
//		 }else{
//			 System.out.println(false);
//		 }
//		 System.out.println(CurrentTime.compareDate("2018-09-19", "2018-11-19"));
//		 File file = new File("D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\patentErp\\Module\\uploadFile\\appUser\\1\\1\\外观设计-受理+费用减缓你通知书.zip");
//		 System.out.println(file.getName());
//		 new FileInputStream(file);
		 String aaa = "2017301654572";
		 Integer c = 3;
		 Integer d = 3;
		 Double a = 5.0;
		 String bbb = "tongguo:";
		 String ccc = ":kehuFail";
		 System.out.println("1: "+bbb.split(":")[0]);
		 System.out.println("2: "+ccc.split(":")[1]);
	}
}
