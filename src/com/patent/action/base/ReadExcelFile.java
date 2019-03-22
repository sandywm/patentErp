package com.patent.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import com.alibaba.fastjson.JSON;
import com.patent.action.fee.FeeAction;
import com.patent.json.LxrJson;
import com.patent.json.QrhJson;
import com.patent.json.SqrJson;
import com.patent.tools.CurrentTime;
import com.patent.tools.FileOpration;
import com.patent.util.WebUrl;
import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
//import org.apache.commons.lang3.StringUtils;
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
	
	/**
	 * @Description: 文字页脚
	 * @see: http://www.coderanch.com/t/525626/java/java/Adding-Header-Footer-Word-Document
	 */
	public void simpleFooter(String savePath) throws Exception {
		XWPFDocument document = new XWPFDocument();
		CTP ctp = CTP.Factory.newInstance();
		CTR ctr = ctp.addNewR();
		CTText textt = ctr.addNewT();
		textt.setStringValue( "测试" );
		XWPFParagraph codePara = new XWPFParagraph( ctp, document );
		codePara.setAlignment(ParagraphAlignment.CENTER);
		codePara.setVerticalAlignment(TextAlignment.CENTER);
		XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
		newparagraphs[0] = codePara;
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new  XWPFHeaderFooterPolicy( document, sectPr );
		headerFooterPolicy.createFooter( STHdrFtr.DEFAULT, newparagraphs );
		headerFooterPolicy.createHeader(STHdrFtr.DEFAULT, newparagraphs );
		FileOutputStream fos = new FileOutputStream(savePath);
		document.write(fos);
		fos.close();
	}
	
	//页脚:显示页码信息
	public void simpleNumberFooter(XWPFDocument document) throws Exception {
		CTP ctp = CTP.Factory.newInstance();
		XWPFParagraph codePara = new XWPFParagraph(ctp, document);
		XWPFRun r1 = codePara.createRun();
		r1.setText("第");
		r1.setFontSize(11);
		CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");
 
		r1 = codePara.createRun();
		CTFldChar fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
 
		r1 = codePara.createRun();
		CTText ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("PAGE  \\* MERGEFORMAT");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");
 
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
 
		r1 = codePara.createRun();
		r1.setText("页 总共");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");
		
		r1 = codePara.createRun();
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
 
		r1 = codePara.createRun();
		ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");
 
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
		
		r1 = codePara.createRun();
		r1.setText("页");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");
		
		codePara.setAlignment(ParagraphAlignment.CENTER);
		codePara.setVerticalAlignment(TextAlignment.CENTER);
		codePara.setBorderTop(Borders.THICK);
		XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
		newparagraphs[0] = codePara;
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
		headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, newparagraphs);
	}
	
	
	public void simpleDateHeader(XWPFDocument document) throws Exception {
		CTP ctp = CTP.Factory.newInstance();
		XWPFParagraph codePara = new XWPFParagraph(ctp, document);
		
		XWPFRun r1 = codePara.createRun();
		CTFldChar fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
 
		r1 = codePara.createRun();
		CTText ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("TIME \\@ \"EEEE\"");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");
 
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
 
		r1 = codePara.createRun();
		r1.setText("年");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");
		
		r1 = codePara.createRun();
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
 
		r1 = codePara.createRun();
		ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("TIME \\@ \"O\"");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");
 
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
		
		r1 = codePara.createRun();
		r1.setText("月");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");
		
		r1 = codePara.createRun();
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
 
		r1 = codePara.createRun();
		ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("TIME \\@ \"A\"");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");
 
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
		
		r1 = codePara.createRun();
		r1.setText("日");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");
		
		codePara.setAlignment(ParagraphAlignment.CENTER);
		codePara.setVerticalAlignment(TextAlignment.CENTER);
		codePara.setBorderBottom(Borders.THICK);
		XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
		newparagraphs[0] = codePara;
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
		headerFooterPolicy.createHeader(STHdrFtr.DEFAULT, newparagraphs);
	}
	
	/**
	 * 增加新页
	 * @description
	 * @author Administrator
	 * @date 2019-3-19 上午11:06:59
	 * @param document
	 * @param breakType
	 */
	public void addNewPage(XWPFDocument document,BreakType breakType){
		XWPFParagraph xp = document.createParagraph();
		xp.createRun().addBreak(breakType);
	}
	
	public void addBreakClear(XWPFDocument document,BreakClear breakClear){
		XWPFParagraph xp = document.createParagraph();
		xp.createRun().addBreak(breakClear);
	}
 
	//TODO 写的时候遇到过一次数组越界,测试几次都没法重现
	public void addSimpleParagraph(XWPFDocument document,String text,String fontName,int fontSize,String color,boolean isBold,boolean isItalic){
		XWPFParagraph xp = document.createParagraph();
		XWPFRun r1 = xp.createRun();
		r1.setText(text);
		r1.setFontSize(fontSize);
		r1.setBold(isBold);
		r1.setItalic(isItalic);
		r1.setColor(color);
		CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii(fontName);
		fonts.setEastAsia(fontName);
		fonts.setHAnsi(fontName);
		xp.setAlignment(ParagraphAlignment.CENTER);
		xp.setVerticalAlignment(TextAlignment.CENTER);
	}
	
	
	//注意: 代码采用的是先写数据再写表头
	public  void createSimpleTable(XWPFDocument doc) throws Exception {
		List<String> columnList = new ArrayList<String>();
		columnList.add("序号");
		columnList.add("姓名信息|姓甚|名谁");
		columnList.add("名刺信息|籍贯|营生");
		XWPFTable table = doc.createTable(2,5);
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		CTJc cTJc=tblPr.addNewJc();
		cTJc.setVal(STJc.Enum.forString("center"));
		tblWidth.setW(new BigInteger("8000"));
		tblWidth.setType(STTblWidth.DXA);
		
		XWPFTableRow firstRow=null;
		XWPFTableRow secondRow=null;
		XWPFTableCell firstCell=null;
		XWPFTableCell secondCell=null;
		
		for(int i=0;i<2;i++){
			firstRow=table.getRow(i);
			firstRow.setHeight(380);
			for(int j=0;j<5;j++){
				firstCell=firstRow.getCell(j);
				setCellText(firstCell, "测试", null, 1600);
			}
		}
		
		firstRow=table.insertNewTableRow(0);
	    secondRow=table.insertNewTableRow(1);
		firstRow.setHeight(380);
		secondRow.setHeight(380);
		for(String str:columnList){
			if(str.indexOf("|") == -1){
				firstCell=firstRow.addNewTableCell();
				secondCell=secondRow.addNewTableCell();
				createVSpanCell(firstCell, str,"CCCCCC",1600,STMerge.RESTART);
				createVSpanCell(secondCell, "", "CCCCCC", 1600,null);
			}else{
				String[] strArr=str.split("\\|");
				firstCell=firstRow.addNewTableCell();
				createHSpanCell(firstCell, strArr[0],"CCCCCC",1600,STMerge.RESTART);
				for(int i=1;i<strArr.length-1;i++){
					firstCell=firstRow.addNewTableCell();
					createHSpanCell(firstCell, "","CCCCCC",1600,null);
				}
				for(int i=1;i<strArr.length;i++){
					secondCell=secondRow.addNewTableCell();
					setCellText(secondCell, strArr[i], "CCCCCC", 1600);
				}
			}
		}
		
	}
	
	public  void setCellText(XWPFTableCell cell,String text, String bgcolor, int width) {
		CTTc cttc = cell.getCTTc();
		CTTcPr cellPr = cttc.addNewTcPr();
		cellPr.addNewTcW().setW(BigInteger.valueOf(width));
		cell.setColor(bgcolor);
		cell.setVerticalAlignment(XWPFVertAlign.CENTER);
		CTTcPr ctPr = cttc.addNewTcPr();
		ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
		cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
		cell.setText(text);
	}
	public void createHSpanCell(XWPFTableCell cell,String value, String bgcolor, int width,STMerge.Enum stMerge){
		CTTc cttc = cell.getCTTc();
		CTTcPr cellPr = cttc.addNewTcPr();
		cellPr.addNewTcW().setW(BigInteger.valueOf(width));
		cell.setColor(bgcolor);
		cellPr.addNewHMerge().setVal(stMerge);
		cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
		cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
		cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
	}
	
	public void createVSpanCell(XWPFTableCell cell,String value, String bgcolor, int width,STMerge.Enum stMerge){
		CTTc cttc = cell.getCTTc();
		CTTcPr cellPr = cttc.addNewTcPr();
		cellPr.addNewTcW().setW(BigInteger.valueOf(width));
		cell.setColor(bgcolor);
		cellPr.addNewVMerge().setVal(stMerge);
		cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
		cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
		cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
	}
	
	
	public void saveDocument(XWPFDocument document,String savePath) throws Exception{
		FileOutputStream fos = new FileOutputStream(savePath);
		document.write(fos);
		fos.close();
	}

	/**
	 * 设置字体样式
	 * @description
	 * @author Administrator
	 * @date 2019-3-19 上午11:44:42
	 * @param docxDocument
	 * @param fontSize
	 * @param font
	 * @param boldFlag
	 * @param textCon
	 */
	private void setFontStyle(XWPFDocument  docxDocument,Integer fontSize,String font,boolean boldFlag,ParagraphAlignment pa,String textCon,String color){
		XWPFParagraph paragraph  = docxDocument.createParagraph();
		XWPFRun run_txt = paragraph.createRun();
		run_txt.setTextPosition(15);//设置行间距
		paragraph.setAlignment(pa);//对齐方式
//		paragraph.setIndentationFirstLine(567);//首行缩进：567==1厘米
		run_txt.setText(textCon);
        run_txt.setFontFamily(font);
        run_txt.setFontSize(fontSize);
        run_txt.setBold(boldFlag);
        run_txt.setColor(color);//
	}
	
	
	/**
	 * 导出word文档
	 * @description
	 * @author Administrator
	 * @date 2019-3-19 上午09:47:34
	 * @throws Exception
	 */
	private void exportWord() throws Exception{
		//创建文本对象
		List<QrhJson> list_qrh = new ArrayList<QrhJson>();
		List<SqrJson> list_sqr = new ArrayList<SqrJson>();
		List<LxrJson> list_lxr = new ArrayList<LxrJson>();
		List<LxrJson> list_lxr1 = new ArrayList<LxrJson>();
		list_lxr.add(new LxrJson("曹新新","15269029629","519513253@qq.com"));
		list_lxr.add(new LxrJson("王心怡","15269029629","519513253@qq.com"));
		list_sqr.add(new SqrJson("濮阳博瑞特石油工程技术有限公司", "91410902558315925T", "濮阳市濮台路与新东路交叉口向北800米路西","18103939769",list_lxr));
		list_lxr1.add(new LxrJson("周新新","15269029629","519513253@qq.com"));
		list_lxr1.add(new LxrJson("李连杰","15269029629","519513253@qq.com"));
		list_sqr.add(new SqrJson("濮阳隆特科技有限公司", "91410902558315925T", "濮阳市濮台路与新东路交叉口向北800米路西","18103939769",list_lxr1));
		list_qrh.add(new QrhJson("0000120191000001","C环滑套喷砂器","发明","是","是","是","贾志强、曹新新、宋健、于传霖、刘明、李新凯","410928196508159637",list_sqr,"电子"));
		for(int j = 0; j < list_qrh.size(); j++){
			QrhJson qrh = list_qrh.get(j);
			XWPFDocument  docxDocument = new XWPFDocument();
			setFontStyle(docxDocument,18,"宋体",true,ParagraphAlignment.CENTER,"专利申请委托相关事项确认函","000000");
	        setFontStyle(docxDocument,16,"宋体",true,ParagraphAlignment.BOTH,"一、基本信息","000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"发明名称："+qrh.getZlTitle(),"000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"申请类型："+qrh.getSqlx(),"000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"是否提实审："+qrh.getSsStatusChi(),"000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"是否提前公开："+qrh.getTqgkStatucChi(),"000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"是否已做费减："+qrh.getFjStatucChi(),"000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"发明/设计人："+qrh.getFmrInfo(),"000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"第一发明人身份证号："+qrh.getFmrICard(),"000000");
	        List<SqrJson> sqrList = qrh.getSqrList();
	        for(int i = 0 ; i < sqrList.size() ; i++){
	        	SqrJson sqr = sqrList.get(i);
	        	setFontStyle(docxDocument,14,"宋体",true,ParagraphAlignment.BOTH,"申  请  人："+sqr.getSqrName(),"DC143C");
		        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"身份证号或社会统一信用代码证号："+sqr.getSqrICard(),"000000");
		        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"申请人地址："+sqr.getSqrAddress(),"000000");
		        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"申请人电话："+sqr.getSqrMobile(),"000000");
		        List<LxrJson> lxrList = sqr.getLxrList();
		        for(int k = 0 ; k < lxrList.size() ; k++){
		        	LxrJson lxr = lxrList.get(k);
		        	setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系人："+lxr.getLxrName(),"8B008B");
			        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系电话："+lxr.getLxrMobile(),"000000");
			        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系人电子邮箱："+lxr.getLxrEmail(),"000000");
		        }
	        }
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"代理委托书：电子","000000");
	        setFontStyle(docxDocument,16,"宋体",true,ParagraphAlignment.BOTH,"二、注意事项","000000");
	        setFontStyle(docxDocument,12,"宋体",false,ParagraphAlignment.BOTH,"请申请人明确本件专利的注意事项或具体要求。","000000");
	        String content = "本专利的申请文本是基于代理人"+CurrentTime.getStringDate()+"发送的确认稿，文件名为《"+qrh.getZlTitle()+"》，该文件经申请人/发明人代表通过确认，同意向国家知识产权局递交。";
	        setFontStyle(docxDocument,14,"宋体",true,ParagraphAlignment.BOTH,content,"000000");
	        setFontStyle(docxDocument,12,"宋体",false,ParagraphAlignment.RIGHT,"申请单位专利主管部门或发明人代表","000000");
	        setFontStyle(docxDocument,12,"宋体",false,ParagraphAlignment.RIGHT,"签   名:________________________","000000");
	        setFontStyle(docxDocument,12,"宋体",false,ParagraphAlignment.RIGHT,"日   期:________________________","000000");
	        String filePath = "D:\\"+qrh.getZlTitle()+"_"+qrh.getZlSerialNo();
	        File file = new File(filePath);
	        if(!file.exists()){
				file.mkdirs();
			}
	        FileOutputStream fout = new FileOutputStream(filePath + "\\客户确认函_"+qrh.getZlSerialNo()+".doc");  
			docxDocument.write(fout);
	        fout.close();
		}
	}
	
	public static void main(String[] args) throws Exception{
		ReadExcelFile t = new ReadExcelFile();
//		t.exportWord();
		String aa = "1,2,3:";
		String bb = ":1,2,3";
		System.out.println(aa.split(":").length + "  " + bb.split(":").length);
//		System.out.println("------------------------简单文字页脚-----------------");
////		t.simpleFooter("D:\\word测试_"+ System.currentTimeMillis() + ".docx");
//		System.out.println("------------------------简单文字页眉页脚-----------------");
//		XWPFDocument document = new XWPFDocument();
//		t.simpleDateHeader(document);
//		t.createSimpleTable(document);
//		t.addNewPage(document, BreakType.PAGE);
//		String str="测试测试测试测试测试文本测试测试测试测试测试文本测试\r\n测试测试测试测试文本测试测试测试测试测试文本测试";
//		t.addSimpleParagraph(document, str, "宋体",11,"FF0000", true, false);
//		t.addNewPage(document, BreakType.COLUMN);
//		t.addSimpleParagraph(document, str,"微软雅黑",12, "00FF00", false, true);
//		t.addNewPage(document, BreakType.TEXT_WRAPPING);
//		t.addSimpleParagraph(document, str,"楷体",13, "0000FF", true, true);
//		t.addBreakClear(document, BreakClear.ALL);
//		t.addSimpleParagraph(document, str,"黑体",14,"000000", false, false);
//		t.simpleNumberFooter(document);
//		t.saveDocument(document, "D:\\word测试.docx");

//		String aa = "2018-09-01";
//		System.out.println(CurrentTime.getFinalDate_2(aa, 2));
//		String aa  = "sl:100";
//		String[] aaArr = aa.split(":");
//		String[] aa1Arr = aaArr[0].split(",");
//		for(int i = 0 ; i < aa1Arr.length ; i++){
//			System.out.println(aa1Arr[i]+"   "+aaArr[1].split(",")[i]);
//		}
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
		
		
//		String oldExcel = "d:\\feeOk.xls";
		
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
//    	HSSFSheet sheet1 = xssfWorkbook.getSheetAt(4);
//    	System.out.println(sheet1.getLastRowNum());
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
//    	for (int i = 0; i <= sheet1.getLastRowNum(); i++) {
//    		HSSFRow row = sheet1.getRow(i);
//    		if (null == row) {
//    			continue;
//			}else{
//				System.out.println(row.getCell(0));
//			}
//    	}
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
		
		
//		// 第一步，创建一个webbook，对应一个Excel文件  
//        HSSFWorkbook wb = new HSSFWorkbook();  
//        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
//        HSSFSheet sheet = wb.createSheet("费用清单");  
//        //设置横向打印
//        sheet.getPrintSetup().setLandscape(true);
//        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
//        HSSFRow row = sheet.createRow(0);  
//        // 第四步，创建单元格，并设置值表头 设置表头居中  
//        HSSFCellStyle style = wb.createCellStyle();  
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
//        
//        row = sheet.createRow(0);
//        HSSFCell cell = row.createCell(0); 
//        cell.setCellStyle(style);  
//        cell.setCellValue("账户名"); 
//        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 0, 0, 0, 1, sheet, wb);
//        
//        cell = row.createCell(2); 
//        cell.setCellStyle(style);  
//        cell.setCellValue("王传明"); 
//        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 0, 0, 2, 7, sheet, wb);
//       
//        
//        row = sheet.createRow(1);
//        cell = row.createCell(0); 
//        cell.setCellStyle(style);  
//        cell.setCellValue("开户行"); 
//        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 1, 1, 0, 1, sheet, wb);
//        
//        cell = row.createCell(2); 
//        cell.setCellStyle(style);  
//        cell.setCellValue("濮阳市工商银行"); 
//        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 1, 1, 2, 7, sheet, wb);
//        
//        row = sheet.createRow(2);
//        cell = row.createCell(0); 
//        cell.setCellStyle(style);  
//        cell.setCellValue("账号"); 
//        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 2, 2, 0, 1, sheet, wb);
//        
//        cell = row.createCell(2); 
//        cell.setCellStyle(style);  
//        cell.setCellValue("2321321321321321321"); 
//        ReadExcelFile.setJoinBorderStyle(HSSFCellStyle.BORDER_THIN, 2, 2, 2, 7, sheet, wb);
//        
//		row = sheet.createRow(3);
//		
//        List<String> list_head = new ArrayList<String>();
//        list_head.add("序号");
//        list_head.add("申请号");
//        list_head.add("专利名称");
//        list_head.add("申请日");
//        list_head.add("申请人");
//        list_head.add("官方费用");
//        list_head.add("缴费截止日");
//        list_head.add("服务费");
//        ReadExcelFile fa = new ReadExcelFile();
//        fa.addCellData(list_head, row, style);
//        FileOutputStream fout = new FileOutputStream("d:\\feeCus.xls");  
//        try {
//			wb.write(fout);
//			 fout.close();  
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
	}
}
