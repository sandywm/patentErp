package com.patent.action.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.patent.tools.Convert;
import com.patent.tools.CurrentTime;
import com.patent.util.WebUrl;

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
	
	public static void main(String[] args){
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
		Double aa = 0.9796;
		System.out.println(aa * 100);
		String bb = "/Module/uploadFile/jsFile/wm1122.xls";
		System.out.println(bb.substring(bb.lastIndexOf("/")+1,bb.length()));
		
		String cc = "appUser/4/aa.txt,appUser/4/ab.txt,appUser/4/ac.txt";
		String newPath_db = "";
		if(!cc.equals("")){
			String[] upFileArr = cc.split(",");
			Integer pzId = 1;
			String gdPath = upFileArr[0].substring(0,upFileArr[0].lastIndexOf("/")) + "/" + pzId + "/";
			for(Integer i = 0 ; i < upFileArr.length ; i++){
				String fileName = upFileArr[i].substring((upFileArr[i].lastIndexOf("/") + 1));
				newPath_db +=  gdPath + fileName + ",";
			}
			if(!newPath_db.equals("")){
				newPath_db = newPath_db.substring(0, newPath_db.length() - 1);
			}
		}
		System.out.println(newPath_db);
	}
}
