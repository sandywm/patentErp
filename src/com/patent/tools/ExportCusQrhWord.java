package com.patent.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.patent.json.LxrJson;
import com.patent.json.QrhJson;
import com.patent.json.SqrJson;

public class ExportCusQrhWord {

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
	 * 生成客户确认函文件夹以及word文档
	 * @description
	 * @author Administrator
	 * @date 2019-3-20 下午04:14:21
	 * @param list_qrh
	 * @throws Exception
	 */
	public void exportWord(List<QrhJson> list_qrh) throws Exception{
		//创建文本对象
//		list_qrh = new ArrayList<QrhJson>();
//		List<SqrJson> list_sqr = new ArrayList<SqrJson>();
//		List<LxrJson> list_lxr = new ArrayList<LxrJson>();
//		List<LxrJson> list_lxr1 = new ArrayList<LxrJson>();
//		list_lxr.add(new LxrJson("曹新新","15269029629","519513253@qq.com"));
//		list_lxr.add(new LxrJson("王心怡","15269029629","519513253@qq.com"));
//		list_sqr.add(new SqrJson("濮阳博瑞特石油工程技术有限公司", "91410902558315925T", "濮阳市濮台路与新东路交叉口向北800米路西","18103939769",list_lxr));
//		list_lxr1.add(new LxrJson("周新新","15269029629","519513253@qq.com"));
//		list_lxr1.add(new LxrJson("李连杰","15269029629","519513253@qq.com"));
//		list_sqr.add(new SqrJson("濮阳隆特科技有限公司", "91410902558315925T", "濮阳市濮台路与新东路交叉口向北800米路西","18103939769",list_lxr1));
//		list_qrh.add(new QrhJson("0000120191000001","C环滑套喷砂器","发明","是","是","是","贾志强、曹新新、宋健、于传霖、刘明、李新凯","410928196508159637",list_sqr,"电子"));
		for(int j = 0; j < list_qrh.size(); j++){
			QrhJson qrh = list_qrh.get(j);
			XWPFDocument  docxDocument = new XWPFDocument();
			setFontStyle(docxDocument,18,"宋体",true,ParagraphAlignment.CENTER,"专利申请委托相关事项确认函","000000");
	        setFontStyle(docxDocument,16,"宋体",true,ParagraphAlignment.BOTH,"一、基本信息","000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"发明名称："+qrh.getZlTitle(),"000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"申请类型："+qrh.getSqlx(),"000000");
	        if(qrh.getSqlx().equals("发明")){
	        	setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"是否提实审："+qrh.getSsStatusChi(),"DC143C");
	        	setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"是否提前公开："+qrh.getTqgkStatucChi(),"DC143C");
	        }
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"是否已做费减："+qrh.getFjStatucChi(),"DC143C");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"发明/设计人："+qrh.getFmrInfo(),"000000");
	        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"第一发明人身份证号："+qrh.getFmrICard(),"000000");
	        List<SqrJson> sqrList = qrh.getSqrList();
	        for(int i = 0 ; i < sqrList.size() ; i++){
	        	SqrJson sqr = sqrList.get(i);
	        	setFontStyle(docxDocument,14,"宋体",true,ParagraphAlignment.BOTH,"申  请  人："+sqr.getSqrName(),"DC143C");
		        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"身份证号或社会统一信用代码证号："+sqr.getSqrICard(),"000000");
		        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"申请人地址："+sqr.getSqrAddress(),"000000");
		        List<LxrJson> lxrList = sqr.getLxrList();
		        if(lxrList.size() > 0){
		        	for(int k = 0 ; k < lxrList.size() ; k++){
			        	LxrJson lxr = lxrList.get(k);
			        	setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系人："+lxr.getLxrName(),"8B008B");
				        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系电话："+lxr.getLxrMobile(),"000000");
				        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系人电子邮箱："+lxr.getLxrEmail(),"000000");
			        }
		        }else{
		        	setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系人：","8B008B");
			        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系电话：","000000");
			        setFontStyle(docxDocument,14,"宋体",false,ParagraphAlignment.BOTH,"联系人电子邮箱：","000000");
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
}
