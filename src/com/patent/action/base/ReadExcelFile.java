package com.patent.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

import com.alibaba.fastjson.JSON;
import com.patent.action.fee.FeeAction;
import com.patent.tools.CurrentTime;
import com.patent.tools.FileOpration;
import com.patent.util.WebUrl;
import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
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
	
	/**
	 * 设置单个单元格的边框
	 * @description
	 * @author Administrator
	 * @date 2018-11-21 上午09:49:59
	 * @param style
	 */
	public static void setBorderStyle(HSSFCellStyle style){
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
	}
	
	public static void setJoinBorderStyle(int border, Integer rowIndex, Integer lastRow, Integer firstColumn, Integer lastColumn, HSSFSheet sheet, HSSFWorkbook wb){
		CellRangeAddress region = new CellRangeAddress(rowIndex,lastRow,firstColumn,lastColumn);//first row (0-based)  from 行
		sheet.addMergedRegion(region);
		
        RegionUtil.setBorderBottom(border, region, sheet, wb);   //下边框
        RegionUtil.setBorderLeft(border, region, sheet, wb);     //左边框
        RegionUtil.setBorderRight(border, region, sheet, wb);    //右边框
        RegionUtil.setBorderTop(border, region, sheet, wb);      //上边框
    }
	
	private void addCellData(List<String> columnList,HSSFRow row,HSSFCellStyle style){
		HSSFCell cell = row.createCell(0); 
		Integer colLen = columnList.size();
		if(colLen > 0){
			for(Integer i = 0 ; i < colLen ; i++){
				cell = row.createCell(i); 
		        cell.setCellStyle(style);  
		        cell.setCellValue(columnList.get(i)); 
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
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
		 
		 
		// 第一步，创建一个webbook，对应一个Excel文件  
//	        HSSFWorkbook wb = new HSSFWorkbook();  
//	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
//	        HSSFSheet sheet = wb.createSheet("费用清单");  
//	        //设置横向打印
//	        sheet.getPrintSetup().setLandscape(true);
//	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
//	        HSSFRow row = sheet.createRow(0);  
//	        // 第四步，创建单元格，并设置值表头 设置表头居中  
//	        HSSFCellStyle style = wb.createCellStyle();  
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
//	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
//	        
//	        HSSFCellStyle style_error = wb.createCellStyle();  
//	        style_error.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
//	        style_error.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
//	        
//	        HSSFFont font_title = wb.createFont();    
//            font_title.setFontName("宋体");    
//            font_title.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
//            font_title.setFontHeightInPoints((short) 12);//设置字体大小  (备注)
//            font_title.setColor(HSSFColor.RED.index);
//            style_error.setFont(font_title);
//            
//            HSSFCell cell = row.createCell(0); 
//	        cell.setCellValue("费用清单");
//	        cell.setCellStyle(style); 
//	        
//	        cell = row.createCell(1); 
//	        cell.setCellValue("费用明细");
//	        cell.setCellStyle(style); 
//	        
//	        row = sheet.createRow(1);
//	        cell = row.createCell(0); 
//	        cell.setCellStyle(style_error);  
//	        cell.setCellValue("aaaa"); 
//	        
//	        cell = row.createCell(1); 
//	        cell.setCellStyle(style_error);  
//	        cell.setCellValue("aaaa"); 
//	        
//	        row = sheet.createRow(2);
//	        cell = row.createCell(0); 
//	        cell.setCellStyle(style);  
//	        cell.setCellValue("bbbb");
//	        
//	        cell = row.createCell(1); 
//	        cell.setCellStyle(style);  
//	        cell.setCellValue("aaaa"); 
//	        
//	        String fileName = "专利费用清单_"+CurrentTime.getStringTime()+".xls";
//	        String folder = "d:\\"+fileName;
////        	File file = new File(folder);
////			if(!file.exists()){
////				file.mkdirs();
////			}
//            FileOutputStream fout = new FileOutputStream(folder);  
//            try {
//				wb.write(fout);
//				 fout.close();  
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
           
//		String filePath = "D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\patentErp\\Module\\uploadFile\\cpyUser\\u_1\\实用新型-受理+费用减缓通知书_503313511.zip";
//		String filePath1 = "D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\patentErp\\Module\\uploadFile\\cpyUser\\u_1\\外观设计-办理登记手续通知书_3926524881.zip";
////		System.out.println("复制文件："+FileOpration.copyFile(filePath, filePath1));
//		System.out.println("删除文件："+FileOpration.deleteFile(filePath));
		
		
//		String oldExcel = "d:\\feeOk3.xls";
		
//		Sheet sheet;  
//        Workbook book;  
//        Cell cell1;
//        WorkbookSettings wbs = new WorkbookSettings();
//        wbs.setEncoding("GBK"); // 解决中文乱码
//        wbs.setSuppressWarnings(true); 
//        try {
//			book= Workbook.getWorkbook(new File(oldExcel),wbs);
//			sheet=book.getSheet(6); 
//			cell1 = sheet.getCell(5,2);
//	        System.out.println(cell1.getContents());
//		} catch (BiffException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
		
//    	String fileName = "费用清单_"+CurrentTime.getStringTime()+".xls";
//    	String absoFilePath = "d:\\" +fileName;
////    	FileOpration.copyFile(oldExcel, absoFilePath);
//    	
//    	File f = new File(oldExcel);
//    	InputStream inputStream = new FileInputStream(f);
//    	HSSFWorkbook xssfWorkbook = new HSSFWorkbook(inputStream);
//    	HSSFSheet sheet = xssfWorkbook.getSheetAt(6);
//    	System.out.println(sheet.getLastRowNum());
//    	HSSFCellStyle style = xssfWorkbook.createCellStyle();  
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
//		HSSFFont font_1 = xssfWorkbook.createFont();    
//        font_1.setFontName("宋体");    
//        font_1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
//        font_1.setFontHeightInPoints((short) 16);//设置字体大小  (备注)
//        style.setFont(font_1);
////    	HSSFRow row0 = sheet.getRow(0);
////    	HSSFCell cell_title = row0.createCell(9);//增加第8列
////    	cell_title.setCellStyle(style);
////    	cell_title.setCellValue("实际费用");//
////		
//        
//    	for (int i = 2; i < 4; i++) {
//    		HSSFRow row = sheet.getRow(i);
//    		if (null == row) {
//    			continue;
//			}else{
//				
////				HSSFCell cell0 = row.getCell(0);//读取第几列
////				if(cell0 == null){
////					continue;
////				}else{
//////					cell0.setCellValue(1);//
////					System.out.println(cell0.getStringCellValue());
////				}
////				
////				HSSFCell cell = row.getCell(5);//读取第几列
////				if(cell == null){
////					continue;
////				}else{
//////					cell.setCellValue("2017301654572");//
////					System.out.println(cell);
////				}
////				
////				HSSFCell cell2 = row.getCell(2);//读取第几列
////				if(cell2 == null){
////					continue;
////				}else{
////					cell2.setCellValue("濮阳天龙集团");//
////				}
//				
//				HSSFCell cell3 = row.getCell(3);//读取第几列
//				if(cell3 == null){
//					continue;
//				}else{
////					cell3.setCellValue("外观设计专利权评价报告请求费");//
//				}
//				
////				HSSFCell cell5 = row.getCell(5);//读取第几列
//				
////				System.out.println(cell5.getNumericCellValue());
//				
//				HSSFCell cell8 = row.createCell(9);//增加第8列
//				cell8.setCellStyle(style);
//				cell8.setCellValue("￥2,200.00");//
//				HSSFPatriarch draw = sheet.createDrawingPatriarch();
//		        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 3, 3, (short) 5, 6);
//		        HSSFComment comment = draw.createCellComment(anchor);
//				// 设置注释内容
//				comment.setString(new HSSFRichTextString(" 第2年度  缴费时间：2018-06-12:2018-07-09 滞纳金：30\r\n 第2年度  缴费时间：2018-06-12:2018-07-09 滞纳金：30"));
//				comment.setAuthor("system");//添加作者
//				cell8.setCellComment(comment);
//				
//				HSSFCell cell10 = row.getCell(10);//读取第几列
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//                Date date = HSSFDateUtil.getJavaDate(cell10.getNumericCellValue());
//
////				System.out.println(sdf.format(date));
//				
//                cell10 = row.getCell(11);//读取第几列
//                cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
//				System.out.println(cell10.toString());
//				
//				cell10 = row.getCell(12);//读取第几列
//                cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
//				System.out.println(cell10.toString());
//				
//				cell10 = row.getCell(13);//读取第几列
//				cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
//				System.out.println(cell10.toString());
//				
////				xssfWorkbook.setForceFormulaRecalculation(true);
//				sheet.autoSizeColumn(1);
//				sheet.autoSizeColumn(2);
//				sheet.autoSizeColumn(3);
//				sheet.autoSizeColumn(5);
//				sheet.autoSizeColumn(9);
//			}
//    	}
//    	Pattern pattern = Pattern.compile("^[+]?[\\*[1-9]]*$"); 
//    	System.out.println(pattern.matcher("0").matches());
////    	FileOutputStream fout = new FileOutputStream(absoFilePath);//存到服务器
////    	xssfWorkbook.write(fout);  
////        fout.close();     
//    	String aa = "2016-12-01 08:58:11";
//    	System.out.println(aa.substring(0, 10));
//    	
//    	List<String> list = new ArrayList<String>();
//    	list.add("asdfdsf");
//    	list.add("23423");
//    	list.add("12321");
//    	list.add("dsfsf131");
//    	for(int i = 0 ; i < list.size() ; i++){
//    		System.out.println(list.get(i));
//    	}
		
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("费用清单");  
        //设置横向打印
        sheet.getPrintSetup().setLandscape(true);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow(0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        
        row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0); 
        cell.setCellStyle(style);  
        cell.setCellValue("账户名"); 
        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 0, 0, 0, 1, sheet, wb);
        
        cell = row.createCell(2); 
        cell.setCellStyle(style);  
        cell.setCellValue("王传明"); 
        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 0, 0, 2, 7, sheet, wb);
       
        
        row = sheet.createRow(1);
        cell = row.createCell(0); 
        cell.setCellStyle(style);  
        cell.setCellValue("开户行"); 
        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 1, 1, 0, 1, sheet, wb);
        
        cell = row.createCell(2); 
        cell.setCellStyle(style);  
        cell.setCellValue("濮阳市工商银行"); 
        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 1, 1, 2, 7, sheet, wb);
        
        row = sheet.createRow(2);
        cell = row.createCell(0); 
        cell.setCellStyle(style);  
        cell.setCellValue("账号"); 
        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 2, 2, 0, 1, sheet, wb);
        
        cell = row.createCell(2); 
        cell.setCellStyle(style);  
        cell.setCellValue("2321321321321321321"); 
        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 2, 2, 2, 7, sheet, wb);
        
		row = sheet.createRow(3);
		
        List<String> list_head = new ArrayList<String>();
        list_head.add("序号");
        list_head.add("申请号");
        list_head.add("专利名称");
        list_head.add("申请日");
        list_head.add("申请人");
        list_head.add("官方费用");
        list_head.add("缴费截止日");
        list_head.add("服务费");
        ReadExcelFile fa = new ReadExcelFile();
        fa.addCellData(list_head, row, style);
        FileOutputStream fout = new FileOutputStream("d:\\feeCus.xls");  
        try {
			wb.write(fout);
			 fout.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
