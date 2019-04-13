package com.patent.json;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知书详情封装JSON
 * @author Administrator
 * @createDate 2018-11-4
 */
public class TzsJson implements Comparable<TzsJson>{

	private String fwSerial;//发文序号
	private String ajNoGf;//专利/申请号
	private String tzsName;//通知书名称
	private String zlName = "";//专利名称
	private String fwDate = "";//发文日
	private String sqrName = "";//申请人
	private String applyDate = "";//申请日
	private String zlType = "";//专利类型
	private String fjApplyDate = "";//费减请求日期
	private String fjRecord = "";//费减记录
	private String feeEdate = "";//缴费截止日期/补正截止日期
	private String fjRate = "0.0";//费减比率
	private List<FeeDetailJson> fdList = new ArrayList<FeeDetailJson>();//缴费详情
	private String yearNo = "";//年度数字
	private List<LateFeeJson> lfList = new ArrayList<LateFeeJson>();//年费缴纳时间段
	private List<FileListJson> flList = new ArrayList<FileListJson>();//电子申请回执清单
	private String zipPath = "";//通知书路径
	private String tifPath = "";//通知书图片路径
	private String readFile = "";//读取的文件(dataXml--数据文件,listXml--通用文件)
	
	public TzsJson(String fwSerial,String ajNoGf){
		this.fwSerial = fwSerial;
		this.ajNoGf = ajNoGf;
	}
	
	public TzsJson(String fwSerial, String ajNoGf, String tzsName,
			String zlName, String fwDate, String sqrName, String applyDate,
			String zlType, String fjApplyDate, String fjRecord,
			String feeEdate, String fjRate, String yearNo,List<FeeDetailJson> fdList,List<LateFeeJson> lfList,
			List<FileListJson> flList,String zipPath,String tifPath,String readFile) {
		this.fwSerial = fwSerial;
		this.ajNoGf = ajNoGf;
		this.tzsName = tzsName;
		this.zlName = zlName;
		this.fwDate = fwDate;
		this.sqrName = sqrName;
		this.applyDate = applyDate;
		this.zlType = zlType;
		this.fjApplyDate = fjApplyDate;
		this.fjRecord = fjRecord;
		this.feeEdate = feeEdate;
		this.fjRate = fjRate;
		this.yearNo = yearNo;
		this.fdList = fdList;
		this.lfList = lfList;
		this.flList = flList;
		this.zipPath = zipPath;
		this.tifPath = tifPath;
		this.readFile = readFile;
	}

	/**
	 * 对比排序
	 */
	public int compareTo(TzsJson o) {
		// TODO Auto-generated method stub
		int i = Integer.parseInt(this.getFwDate().replaceAll("-", "")) - Integer.parseInt(o.getFwDate().replaceAll("-", ""));//按照发文日排序
		return (int)i;
	}
	
	
	public String getFwSerial() {
		return fwSerial;
	}
	public void setFwSerial(String fwSerial) {
		this.fwSerial = fwSerial;
	}
	public String getAjNoGf() {
		return ajNoGf;
	}
	public void setAjNoGf(String ajNoGf) {
		this.ajNoGf = ajNoGf;
	}

	public String getTzsName() {
		return tzsName;
	}

	public void setTzsName(String tzsName) {
		this.tzsName = tzsName;
	}

	public String getZlName() {
		return zlName;
	}

	public void setZlName(String zlName) {
		this.zlName = zlName;
	}

	public String getFwDate() {
		return fwDate;
	}

	public void setFwDate(String fwDate) {
		this.fwDate = fwDate;
	}

	public String getSqrName() {
		return sqrName;
	}

	public void setSqrName(String sqrName) {
		this.sqrName = sqrName;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getZlType() {
		return zlType;
	}

	public void setZlType(String zlType) {
		this.zlType = zlType;
	}

	public String getFjApplyDate() {
		return fjApplyDate;
	}

	public void setFjApplyDate(String fjApplyDate) {
		this.fjApplyDate = fjApplyDate;
	}

	public String getFjRecord() {
		return fjRecord;
	}

	public void setFjRecord(String fjRecord) {
		this.fjRecord = fjRecord;
	}

	public String getFeeEdate() {
		return feeEdate;
	}

	public void setFeeEdate(String feeEdate) {
		this.feeEdate = feeEdate;
	}

	public String getFjRate() {
		return fjRate;
	}

	public void setFjRate(String fjRate) {
		this.fjRate = fjRate;
	}

	public String getYearNo() {
		return yearNo;
	}

	public void setYearNo(String yearNo) {
		this.yearNo = yearNo;
	}

	public List<FeeDetailJson> getFdList() {
		return fdList;
	}

	public void setFdList(List<FeeDetailJson> fdList) {
		this.fdList = fdList;
	}

	public List<LateFeeJson> getLfList() {
		return lfList;
	}

	public void setLfList(List<LateFeeJson> lfList) {
		this.lfList = lfList;
	}

	public String getZipPath() {
		return zipPath;
	}

	public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}

	public List<FileListJson> getFlList() {
		return flList;
	}

	public void setFlList(List<FileListJson> flList) {
		this.flList = flList;
	}

	public String getReadFile() {
		return readFile;
	}

	public void setReadFile(String readFile) {
		this.readFile = readFile;
	}

	public String getTifPath() {
		return tifPath;
	}

	public void setTifPath(String tifPath) {
		this.tifPath = tifPath;
	}

	
}
