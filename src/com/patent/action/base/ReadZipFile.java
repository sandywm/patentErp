package com.patent.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.patent.factory.AppFactory;
import com.patent.tools.Convert;
import com.patent.tools.CurrentTime;
import com.patent.util.Constants;
import com.patent.util.WebUrl;


public class ReadZipFile {
	
	//将所有HSS替换成XSS，HSS最大支持excel65536行，XSS可以超过65536行限制
//	private static String writeTxtPath = "e:\\readXml.txt";	
	private static Integer sheetNum = 1;//每个sheet的最大行数
	private static Integer rowNum = 0;//多少条记录数
	// 第一步，创建一个webbook，对应一个Excel文件  
    static HSSFWorkbook wb = new HSSFWorkbook();  
//    static XSSFWorkbook wb = new XSSFWorkbook();  
    // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
    static HSSFSheet sheet = wb.createSheet("专利检索---"+CurrentTime.getStringTime()+"_"+sheetNum);  
//    static XSSFSheet sheet = wb.createSheet("专利检索---"+CurrentTime.getStringTime()+"_2");
    // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
    static HSSFRow row = sheet.createRow(0);  
//    static XSSFRow row = sheet.createRow(0);
    // 第四步，创建单元格，并设置值表头 设置表头居中  
    static HSSFCellStyle style = wb.createCellStyle();  
//    static XSSFCellStyle style = wb.createCellStyle();
    
	public static void writeReport(String filePath,String var1,String var2,String var3,String var4,String var5){
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
			String content = "读取开始-----------------------------------"+com.patent.tools.CurrentTime.getCurrentTime() + "\r\n";
			content += "申请号:"+var1 + "\r\n";
			content += "发明名称: "+var2 + "\r\n";
			content += "申请日: "+var3 + "\r\n";
			content += "授权公告日: "+var4 + "\r\n";
			content += "对比文件: "+var5 + "\r\n";
			if(var1.equals("")){
				content = "读取完成-----------------------------------"+com.patent.tools.CurrentTime.getCurrentTime() + "\r\n";
			}
			pw.println(content);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	
	
	public static void writeReportToExcel(String filePath,String var1,String var2,String var3,String var4,String var5,String var6){
		if(rowNum.equals(60000)){//当记录为60000条数时,重新开启个sheet
			sheetNum++;
			rowNum = 0;
			sheet = wb.createSheet("专利检索---"+"_"+sheetNum);  
			row = sheet.createRow(0);  
		}
		File oldfile = new File(filePath);  
		if(!oldfile.exists()){
			 try {
				oldfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			if(rowNum.equals(0)){//第一次
				row = sheet.createRow(0);  
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
		        HSSFCell cell = row.createCell(0);  
		        cell.setCellStyle(style);  
		        cell.setCellValue("申请号");  
		        cell = row.createCell(1);  
		        cell.setCellStyle(style);  
		        cell.setCellValue("发明名称");  
		        cell = row.createCell(2);  
		        cell.setCellStyle(style);  
		        cell.setCellValue("申请日");  
		        cell = row.createCell(3);  
		        cell.setCellStyle(style);  
		        cell.setCellValue("授权公告日");  
		        cell = row.createCell(4);  
		        cell.setCellStyle(style);  
		        cell.setCellValue("对比文件");  
		        cell = row.createCell(5);  
		        cell.setCellStyle(style);  
		        cell.setCellValue("分类表");  
		        rowNum++;
		        row = sheet.createRow(rowNum);
	        	// 第四步，创建单元格，并设置值  
	        	HSSFCell cell_data = row.createCell(0); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var1);
	        	
	        	cell_data = row.createCell(1); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var2);
	        	
	        	cell_data = row.createCell(2); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var3);
	        	
	        	cell_data = row.createCell(3); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var4);
	        	
	        	cell_data = row.createCell(4); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var5);
	        	
	        	cell_data = row.createCell(5); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var6);
		        
			}else{
				//写入数据
				rowNum++;
				row = sheet.createRow(rowNum);
	        	// 第四步，创建单元格，并设置值  
	        	HSSFCell cell_data = row.createCell(0); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var1);
	        	
	        	cell_data = row.createCell(1); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var2);
	        	
	        	cell_data = row.createCell(2); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var3);
	        	
	        	cell_data = row.createCell(3); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var4);
	        	
	        	cell_data = row.createCell(4); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var5);
	        	
	        	cell_data = row.createCell(5); 
	        	cell_data.setCellStyle(style);
	        	cell_data.setCellValue(var6);
			}
			
	        // 第五步，写入实体数据 实际应用中这些数据
			 FileOutputStream fout = new FileOutputStream(filePath);  
	         wb.write(fout);  
	         fout.close();  
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	
	/**
	 * 读取压缩包内容
	 * @description
	 * @author wm
	 * @date 2017-9-15 上午09:15:40
	 * @param zipPath 压缩包路径
	 * @param fName 文件夹中文名(CN-BIBS-ABSS-10-B 中国发明专利授权公告标准化著录项目数据)
	 * @param fName 文件夹中文名1(20170301)
	 */
	public static void readZipFile_new(String zipPath,String fName,String fName_1){
//		String zipPath1 = "d:/CN-TXTS-10-B.zip";
//		zipPath = "F:\\CN-TXTO-10-B 中国发明专利授权公告全文文本数据\\20160406\\20160406-001.ZIP";
		Charset gbk = Charset.forName("gbk");
        try {
			ZipFile zf = new ZipFile(zipPath,gbk);
			FileInputStream fileInputStream = new FileInputStream(zipPath);
	        
			CheckedInputStream check = new CheckedInputStream(fileInputStream, new CRC32());
			
	        ZipInputStream zin = new ZipInputStream(check,gbk);

	        //ZipEntry 类用于表示 ZIP 文件条目。
	        ZipEntry ze;
	        
	        while((ze=zin.getNextEntry())!=null){
	            if(ze.isDirectory()){
	                //为空的文件夹什么都不做
	            }else{
	            	String fileName = ze.getName();
	            	if(fileName.endsWith("TXT") || fileName.endsWith("txt")){//文本文件
	            		
	            	}else if(fileName.endsWith("XML") || fileName.endsWith("xml")){
	                    ZipEntry zip = zf.getEntry(ze.getName());
	                    InputStream inputstream = null;
	            		inputstream = zf.getInputStream(zip);
	            		SAXReader reader = new SAXReader();  
	            		reader.setEntityResolver(new IgnoreDTDEntityResolver());//忽略dtd验证
	                    Document doc = reader.read(inputstream);
	        			Element root = doc.getRootElement();  
	        			String sqNo = "";//申请号
	        			String fmName = "";//发明名称
	        			String sqDate = "";//申请日
	        			String sqDate_1 = "";//授权公告日
	        			String dbFile = "";//对比文件
	        			String typeStr = "";//分类表
	        			//第一次版本数据---start
//	        			Element l1 = root.element("cn-bibliographic-data");//发明名称
//	        			fmName = l1.elementText("invention-title");
//	        			Element l2 = l1.element("cn-publication-reference");//公布/公告信息（公布/公告号、日期、数据种类、公报卷期号）
//	        			sqDate_1 = l2.element("document-id").elementText("date");
//	        			Element l3 = l1.element("application-reference");//申请信息（申请号、申请日、申请种类）
//	        			sqNo = l3.element("document-id").elementText("country") +l3.element("document-id").elementText("doc-number");
//        				sqDate = l3.element("document-id").elementText("date");
//        				Element l4 = l1.element("references-cited");//引证文献信息（对比文件）
//        				dbFile = l4.element("citation").element("patcit").elementText("text");
//        				Element l5 = l1.element("classifications-ipcr");//分类表
//        				for(@SuppressWarnings("rawtypes")
//    						Iterator it = l5.elementIterator("classification-ipcr") ; it.hasNext();){
//    	        	        	Element l5_sub = (Element) it.next();
//    	        	        	String typeStr_temp = l5_sub.elementText("text").trim();
//    	        	        	if(!typeStr_temp.equals("")){
//    	        	        		typeStr += typeStr_temp + ",";
//    	        	        	}
//    	        	        }
//    	        	        if(!typeStr.equals("")){
//    	        	        	typeStr = typeStr.substring(0, typeStr.length() - 1);
//    	        	        }
//	            		}
	            		//第一次版本数据---end
	        			
	        			//第二次版本数据---start
	        			Element l1 = root.element("BibliographicData");//发明名称
	        			fmName = l1.elementText("InventionTitle");
	        			Element l2 = l1.element("PublicationReference");//公布/公告信息（公布/公告号、日期、数据种类、公报卷期号）
        				sqDate_1 = l2.element("DocumentID").elementText("Date");
        				Element l3 = null;//申请信息（申请号、申请日、申请种类）
        				for(@SuppressWarnings("unchecked")
						Iterator<Element> it = l1.elementIterator("ApplicationReference") ; it.hasNext();){
							l3 = it.next();//获取最后一组真实数据
							String code =  l3.element("DocumentID").elementText("DocNumber");
							if(code.indexOf(".") > 0){
								break;
							}
						}
        				sqNo = l3.element("DocumentID").elementText("WIPOST3Code") + l3.element("DocumentID").elementText("DocNumber");
        				sqDate = l3.element("DocumentID").elementText("Date");
        				dbFile = "无";//对比文件
        				Element l5 = l1.element("ClassificationIPCRDetails");//分类表
            	        for(@SuppressWarnings("rawtypes")
        				Iterator it = l5.elementIterator("ClassificationIPCR") ; it.hasNext();){
            	        	Element l5_sub = (Element) it.next();
            	        	String typeStr_temp = l5_sub.elementText("Text").trim();
            	        	if(!typeStr_temp.equals("")){
            	        		typeStr += typeStr_temp + ",";
            	        	}
            	        }
            	        if(!typeStr.equals("")){
            	        	typeStr = typeStr.substring(0, typeStr.length() - 1);
            	        }
        				
	        			//第二次版本数据---end
            	        
//	        			if(l1 == null){
//	        				l1 = root.element("BibliographicData");
//	        				fmName = l1.elementText("InventionTitle");
//	        			}else{
//	        				fmName = l1.elementText("invention-title");
//	        			}
//	        			Element l2 = l1.element("cn-publication-reference");//公布/公告信息（公布/公告号、日期、数据种类、公报卷期号）
//	        			if(l2 == null){
//	        				l2 = l1.element("PublicationReference");
//	        				sqDate_1 = l2.element("DocumentID").elementText("Date");
//	        			}else{
//	        				sqDate_1 = l2.element("document-id").elementText("date");
//	        			}
//	        			Element l3 = l1.element("application-reference");//申请信息（申请号、申请日、申请种类）
//	        			if(l3 == null){
//	        				for(@SuppressWarnings("unchecked")
//    						Iterator<Element> it = l1.elementIterator("ApplicationReference") ; it.hasNext();){
//    							l3 = it.next();//获取最后一组真实数据
//    							String code =  l3.element("DocumentID").elementText("DocNumber");
//    							if(code.indexOf(".") > 0){
//    								break;
//    							}
//    						}
//	        				sqNo = l3.element("DocumentID").elementText("WIPOST3Code") + l3.element("DocumentID").elementText("DocNumber");
//	        				sqDate = l3.element("DocumentID").elementText("Date");
//	        			}else{
//	        				sqNo = l3.element("document-id").elementText("country") +l3.element("document-id").elementText("doc-number");
//	        				sqDate = l3.element("document-id").elementText("date");
//	        			}
//	        			Element l4 = l1.element("references-cited");//引证文献信息（对比文件）
//	        			
//	        			if(l4 == null){
//	        				dbFile = "无";
//	        			}else{
//	        				dbFile = l4.element("citation").element("patcit").elementText("text");
//	        			}
//	        			Element l5 = l1.element("classifications-ipcr");//分类表
////	        			System.out.println("读取开始--------------------------------------------");
////	        			System.out.println("申请号: "+l3.element("document-id").elementText("country") + l3.element("document-id").elementText("doc-number"));
////	        	        System.out.println("发明名称: "+l1.elementText("invention-title"));
////	        	        System.out.println("申请日: "+l3.element("document-id").elementText("date"));
////	        	        System.out.println("授权公告日: "+l2.element("document-id").elementText("date"));
////	        	        System.out.println("对比文件: "+l4.element("citation").element("patcit").elementText("text").trim());
//	        			if(l5 == null){
//	        				l5 = l1.element("ClassificationIPCRDetails");
//	            	        for(@SuppressWarnings("rawtypes")
//	        				Iterator it = l5.elementIterator("ClassificationIPCR") ; it.hasNext();){
//	            	        	Element l5_sub = (Element) it.next();
//	            	        	String typeStr_temp = l5_sub.elementText("Text").trim();
//	            	        	if(!typeStr_temp.equals("")){
//	            	        		typeStr += typeStr_temp + ",";
//	            	        	}
//	            	        }
//	            	        if(!typeStr.equals("")){
//	            	        	typeStr = typeStr.substring(0, typeStr.length() - 1);
//	            	        }
////	            	        System.out.println("分类表: "+typeStr);
//	        			}else{
//	        				for(@SuppressWarnings("rawtypes")
//    						Iterator it = l5.elementIterator("classification-ipcr") ; it.hasNext();){
//    	        	        	Element l5_sub = (Element) it.next();
//    	        	        	String typeStr_temp = l5_sub.elementText("text").trim();
//    	        	        	if(!typeStr_temp.equals("")){
//    	        	        		typeStr += typeStr_temp + ",";
//    	        	        	}
//    	        	        }
//    	        	        if(!typeStr.equals("")){
//    	        	        	typeStr = typeStr.substring(0, typeStr.length() - 1);
//    	        	        }
//	        			}
	        	        
//	        	        System.out.println("分类表: "+typeStr);
//	        	        ReadZipFile.writeReportToExcel("e:\\zl_"+CurrentTime.getStringDate()+"_2.xls", 
//	        					(l3.element("document-id").elementText("country") + l3.element("document-id").elementText("doc-number")),
//	        					l1.elementText("invention-title"), l3.element("document-id").elementText("date"),
//	        					l2.element("document-id").elementText("date"),dbFile,
//	        					typeStr, flag);    
	        	        ReadZipFile.writeReportToExcel("e:\\zl_"+CurrentTime.getStringDate()+".xls", 
	        	        		sqNo,fmName, sqDate,sqDate_1,dbFile,typeStr.replaceAll(" ", ""));    
//	        	        System.out.println("读取开始--------------------------------------------");
//	        			System.out.println("申请号: "+sqNo);
//	        	        System.out.println("发明名称: "+fmName);
//	        	        System.out.println("申请日: "+sqDate);
//	        	        System.out.println("授权公告日: "+sqDate_1);
//	        	        System.out.println("对比文件: "+dbFile);
//	        	        System.out.println("分类表: "+typeStr);
	        	        System.out.println("正在读取："+fName+"\\"+fName_1+"\\"+ze.getName()+":: "+rowNum);
	            	}
	            }
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
	}
	
	/**
	 * @description
	 * @author wm
	 * @date 2017-7-4 下午02:29:08
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		//step1:浏览文件夹
		String filePath = "F:\\SIPO\\1\\CN-BIBS-ABSS-10-B 中国发明专利授权公告标准化著录项目数据_2";
		File f = new File(filePath);
		if(f.isDirectory()){
			File[] fArray = f.listFiles();
			for(Integer i = 0 ; i < fArray.length ; i++){
				File f_sub = new File(fArray[i].getPath());
				File[] fArray_sub = f_sub.listFiles();
				for(Integer j = 0 ; j < fArray_sub.length ; j++){
					if(fArray_sub[j].getName().endsWith(".ZIP")){
						readZipFile_new(fArray_sub[j].getPath(),fArray[i].getPath().split("\\\\")[3],fArray[i].getPath().split("\\\\")[4]);
					}
				}
			}
			System.out.println("解析完成"+CurrentTime.getCurrentTime());
		}
//		ReadZipFile.exportExcel();
//		ReadZipFile.readXml("");
	}

	public static void readXml(String filePath){
		File f = new File("E:\\CN102017000394552CN00001071711380BBIAZH20180119CN00B.XML");
		SAXReader reader = new SAXReader();  
		reader.setEntityResolver(new IgnoreDTDEntityResolver());//忽略dtd验证
        Document doc;
		try {
			doc = reader.read(f);
			Element root = doc.getRootElement();  	        
			//新xml格式
			Element l1 = root.element("cn-bibliographic-data");
			if(l1 == null){
				l1 = root.element("BibliographicData");
				System.out.println("发明名称: "+l1.elementText("InventionTitle"));
			}else{
				System.out.println("发明名称: "+l1.elementText("invention-title"));
			}
			Element l2 = l1.element("cn-publication-reference");//公布/公告信息（公布/公告号、日期、数据种类、公报卷期号）
			if(l2 == null){
				l2 = l1.element("PublicationReference");
				System.out.println("授权公告日: "+l2.element("DocumentID").elementText("Date"));
			}else{
				System.out.println("授权公告日: "+l2.element("document-id").elementText("date"));
			}
			Element l3 = l1.element("application-reference");//申请信息（申请号、申请日、申请种类）
			if(l3 == null){
				for(@SuppressWarnings("unchecked")
				Iterator<Element> it = l1.elementIterator("ApplicationReference") ; it.hasNext();){
					l3 = it.next();//获取最后一组真实数据
					String code =  l3.element("DocumentID").elementText("DocNumber");
					if(code.indexOf(".") > 0){
						break;
					}
				}
				System.out.println("申请号: "+l3.element("DocumentID").elementText("WIPOST3Code") + l3.element("DocumentID").elementText("DocNumber"));
				System.out.println("申请日: "+l3.element("DocumentID").elementText("Date"));
			}else{
				System.out.println("申请号: "+l3.element("document-id").elementText("country") + l3.element("document-id").elementText("doc-number"));
				System.out.println("申请日: "+l3.element("document-id").elementText("date"));
			}
//			System.out.println(l3);
			Element l4 = l1.element("references-cited");//引证文献信息（对比文件）
			if(l4 == null){
				 System.out.println("对比文件: 无");
			}else{
				 System.out.println("对比文件: "+l4.element("citation").element("patcit").elementText("text"));
			}
			Element l5 = l1.element("classification-ipcr");
			String typeStr = "";
			if(l5 == null){
				l5 = l1.element("ClassificationIPCRDetails");
				
    	        for(@SuppressWarnings("rawtypes")
				Iterator it = l5.elementIterator("ClassificationIPCR") ; it.hasNext();){
    	        	Element l5_sub = (Element) it.next();
    	        	String typeStr_temp = l5_sub.elementText("Text").trim();
    	        	if(!typeStr_temp.equals("")){
    	        		typeStr += typeStr_temp + ",";
    	        	}
    	        }
    	        if(!typeStr.equals("")){
    	        	typeStr = typeStr.substring(0, typeStr.length() - 1);
    	        }
    	        System.out.println("分类表: "+typeStr);
			}
//			System.out.println("申请号: "+l3.element("document-id").elementText("country") + l3.element("document-id").elementText("doc-number"));
//	        System.out.println("发明名称: "+l1.elementText("invention-title"));
//	        System.out.println("申请日: "+l3.element("document-id").elementText("date"));
//	        System.out.println("授权公告日: "+l2.element("document-id").elementText("date"));
//	        System.out.println("对比文件: "+l4.element("citation").element("patcit").elementText("text"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	/**
	 * ERP导出专用
	 * @description
	 * @author wm
	 * @date 2017-9-15 上午08:58:37
	 * @throws Exception
	 */
	public static void exportExcel() throws Exception{
		
		HSSFWorkbook wb = new HSSFWorkbook();     
      HSSFSheet sheet = wb.createSheet("new sheet");  
      sheet.getPrintSetup().setLandscape(true);
      //2.生成样式对象  
      HSSFCellStyle style = wb.createCellStyle();  
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
      style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
      style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
      style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
      
      
      HSSFCellStyle style_con = wb.createCellStyle();  
      style_con.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
      style_con.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
      style_con.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
      style_con.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
      style_con.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
      style_con.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
      
      HSSFCellStyle style_remark = wb.createCellStyle();  
      style_remark.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
      style_remark.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
      style_remark.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
      style_remark.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
      style_remark.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
      style_remark.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
      
      //3:设置字体
      HSSFFont font = wb.createFont();    
      font.setFontName("宋体");    
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
      font.setFontHeightInPoints((short) 18);//设置字体大小  (标题)
      
      HSSFFont font_con = wb.createFont();    
      font_con.setFontName("宋体");    
      font_con.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
      font_con.setFontHeightInPoints((short) 16);//设置字体大小  (内容)
      
      HSSFFont font_remark = wb.createFont();    
      font_remark.setFontName("宋体");    
      font_remark.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
      font_remark.setFontHeightInPoints((short) 14);//设置字体大小  (备注)
      
      style.setFont(font);
      HSSFRow row = sheet.createRow(0);    
      
      HSSFCell cell = row.createCell(0); 
      cell.setCellValue("采购单Purchasing Order");
      cell.setCellStyle(style);  
      sheet.setColumnWidth(0, (int)6.05*256);
      sheet.setColumnWidth(1, (int)22.06*256);
      sheet.setColumnWidth(2, (int)18.86*256);
      sheet.setColumnWidth(3, (int)21.74*256);
      sheet.setColumnWidth(4, (int)9*256);
      sheet.setColumnWidth(5, (int)12.96*256);
      sheet.setColumnWidth(6, (int)17.65*256);
      sheet.setColumnWidth(7, (int)13.35*256);
      sheet.addMergedRegion(new CellRangeAddress(     
  			0, //first row (0-based)  from 行     
  			0, //last row  (0-based)  to 行     
            0, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      ));
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(style); 
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(style); 
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(style); 
      cell = row.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(style); 
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(style); 
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(style); 
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style);  

      style_con.setFont(font_con);
      row = sheet.createRow(1);
      cell = row.createCell(0); 
      cell.setCellValue("TO: 天腾赖总");
      cell.setCellStyle(style_con);  
      sheet.addMergedRegion(new CellRangeAddress(     
    			1, //first row (0-based)  from 行     
    			1, //last row  (0-based)  to 行     
              0, //first column (0-based) from 列     
              1  //last column  (0-based)  to 列     
        ));   
      
      cell = row.createCell(2); 
      cell.setCellValue("FROM：亮宇");
      cell.setCellStyle(style_con);  
      sheet.addMergedRegion(new CellRangeAddress(     
    			1, //first row (0-based)  from 行     
    			1, //last row  (0-based)  to 行     
              2, //first column (0-based) from 列     
              4  //last column  (0-based)  to 列     
        ));   
      
      cell = row.createCell(5); 
      cell.setCellValue(" 编号：L-C20170803-03");
      cell.setCellStyle(style_con);
      sheet.addMergedRegion(new CellRangeAddress(     
  			1, //first row (0-based)  from 行     
  			1, //last row  (0-based)  to 行     
            5, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      )); 
      
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style_con);  
      
      row = sheet.createRow(2);
      cell = row.createCell(0); 
      cell.setCellValue("编号");
      cell.setCellStyle(style_con);  
      
      cell = row.createCell(1); 
      cell.setCellValue("物料名称");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(2); 
      cell.setCellValue("材质");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(3); 
      cell.setCellValue("规格");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(4); 
      cell.setCellValue("数量");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(5); 
      cell.setCellValue("单价");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(6); 
      cell.setCellValue("总价");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(7); 
      cell.setCellValue("交货日期");
      cell.setCellStyle(style_con); 
      
      
      
      row = sheet.createRow(3);
      cell = row.createCell(0); 
      cell.setCellValue("1");
      cell.setCellStyle(style_con);  
      
      cell = row.createCell(1); 
      cell.setCellValue("芯取夹具");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(2); 
      cell.setCellValue("钢");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(3); 
      cell.setCellValue("5*2");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(4); 
      cell.setCellValue("10");
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(5); 
      cell.setCellValue(Convert.convertMoney_1(10000));
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(6); 
      cell.setCellValue(Convert.convertMoney_1(10000));
      cell.setCellStyle(style_con); 
      
      cell = row.createCell(7); 
      cell.setCellValue("");
      cell.setCellStyle(style_con); 
      
      row = sheet.createRow(4);
      cell = row.createCell(0); 
      cell.setCellValue("合计总额元:"+Convert.convertMoney_1(543513213));
      cell.setCellStyle(style_con);
      sheet.addMergedRegion(new CellRangeAddress(     
  			4, //first row (0-based)  from 行     
  			4, //last row  (0-based)  to 行     
            0, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      )); 
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style_con);  
      
      style_remark.setFont(font_remark);
      row = sheet.createRow(5);
      cell = row.createCell(0); 
      cell.setCellValue("注：1.以上物料含包装运输费含17%税。");
      cell.setCellStyle(style_remark);
      sheet.addMergedRegion(new CellRangeAddress(     
  			5, //first row (0-based)  from 行     
  			5, //last row  (0-based)  to 行     
            0, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      )); 
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      
      row = sheet.createRow(6);
      cell = row.createCell(0); 
      cell.setCellValue("    2.产品交货时需提交送货单及相关检验报告。");
      cell.setCellStyle(style_remark);
      sheet.addMergedRegion(new CellRangeAddress(     
  			6, //first row (0-based)  from 行     
  			6, //last row  (0-based)  to 行     
            0, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      )); 
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      
      row = sheet.createRow(7);
      cell = row.createCell(0); 
      cell.setCellValue("采购方确认：王高敏");
      cell.setCellStyle(style_remark);
      sheet.addMergedRegion(new CellRangeAddress(     
  			7, //first row (0-based)  from 行     
  			7, //last row  (0-based)  to 行     
            0, //first column (0-based) from 列     
            3  //last column  (0-based)  to 列     
      )); 
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      
      cell = row.createCell(4); 
      cell.setCellValue("供货方确认：");
      cell.setCellStyle(style_remark);
      sheet.addMergedRegion(new CellRangeAddress(     
  			7, //first row (0-based)  from 行     
  			7, //last row  (0-based)  to 行     
            4, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      )); 
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      
      row = sheet.createRow(8);
      cell = row.createCell(0); 
      cell.setCellValue("采购方名称：濮阳市亮宇实业有限公司");
      cell.setCellStyle(style_remark);
      sheet.addMergedRegion(new CellRangeAddress(     
  			8, //first row (0-based)  from 行     
  			8, //last row  (0-based)  to 行     
            0, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      )); 
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      
      row = sheet.createRow(9);
      cell = row.createCell(0); 
      cell.setCellValue("联系方式：15083287930/8997209");
      cell.setCellStyle(style_remark);
      sheet.addMergedRegion(new CellRangeAddress(     
  			9, //first row (0-based)  from 行     
  			9, //last row  (0-based)  to 行     
            0, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      )); 
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      
      row = sheet.createRow(10);
      cell = row.createCell(0); 
      cell.setCellValue("公司地址：濮阳市濮东产业集聚区华龙科技创业园（新东路与锦田路交叉口向南200米路西）");
      cell.setCellStyle(style_remark);
      sheet.addMergedRegion(new CellRangeAddress(     
  			10, //first row (0-based)  from 行     
  			10, //last row  (0-based)  to 行     
            0, //first column (0-based) from 列     
            7  //last column  (0-based)  to 列     
      )); 
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(style_remark); 
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(style_remark);  
      
      String absoFilePath = "";//绝对地址
      try  {  
      	String fileName = "test_"+CurrentTime.getStringTime()+".xls";
      	String folder = "D:\\excelTemp\\";
      	absoFilePath = folder +fileName;
      	File file = new File(folder);
			if(!file.exists()){
				file.mkdirs();
			}
          FileOutputStream fout = new FileOutputStream(absoFilePath);  
          wb.write(fout);  
          fout.close();  
      } catch (IOException e){  
          //e.printStackTrace();  
      }
	}
}
