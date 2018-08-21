package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.CustomerFmrInfoTb;
import com.patent.module.CustomerInfoTb;
import com.patent.module.CustomerLxrInfoTb;

public interface CustomerInfoManager {

	/**
	 * 增加专利机构客户信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:07:38
	 * @param cusType 客户类型
	 * @param cusName 客户名称
	 * @param cusiCard 客户卡号（公司机构代码/个人身份证号）
	 * @param cusAddress 客户地址
	 * @param cusZip 客户邮编
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer addCusInfo(String cusType,String cusName,String cusiCard,String cusAddress,String cusZip,Integer cpyId) throws WEBException;
	
	/**
	 * 删除指定主键的客户信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:07:43
	 * @param id
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	boolean delCusInfo(Integer id,Integer cpyId) throws WEBException;
	
	/**
	 * 修改指定编号的客户信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:07:46
	 * @param id 主键
	 * @param cusType 客户类型（""不修改）
	 * @param cusName 客户名称（""不修改）
	 * @param cusiCard 客户卡号（公司机构代码/个人身份证号）（""不修改）
	 * @param cusAddress 客户地址（""不修改）
	 * @param cusZip 客户邮编（""不修改）
	 * @param cpyId 代理机构编号(必须存在)--存在的目的是为了不让捣乱删除
	 * @return
	 * @throws WEBException
	 */
	boolean upCusInfo(Integer id,Integer cpyId,String cusType,String cusName,String cusiCard,String cusAddress,String cusZip) throws WEBException;
	
	/**
	 * 分页获取指定代理机构下指定条件的客户记录列表
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:07:49
	 * @param cpyId 代理机构编号
	 * @param cusName 客户名称(""表示全部)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<CustomerInfoTb> listPageInfoByOpt(Integer cpyId,String cusName,Integer pageNo,Integer pageSize) throws WEBException;
	
	/**
	 * 
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:07:51
	 * @param cpyId 代理机构编号
	 * @param cusName 客户名称(""表示全部)
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer cpyId,String cusName) throws WEBException;
	
	/**
	 * 根据主键、代理机构编号获取客户信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-20 下午04:47:59
	 * @param cusId 客户编号
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<CustomerInfoTb> listInfoById(Integer cpyId,Integer cusId) throws WEBException;
	
	
	/**
	 * 增加指定客户的联系人信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:27:40
	 * @param cusId 客户编号
	 * @param lxrName 联系人姓名
	 * @param lxrTel 联系人电话
	 * @param lxrEmail 联系人email
	 * @return
	 * @throws WEBException
	 */
	Integer addCusLxrInfo(Integer cusId,String lxrName,String lxrTel,String lxrEmail) throws WEBException;
	
	/**
	 * 修改指定客户的联系人信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:27:42
	 * @param lxrId 主键
	 * @param lxrName 联系人姓名（""时不修改）
	 * @param lxrTel 联系人电话（""时不修改）
	 * @param lxrEmail 联系人email（""时不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean upCusLxrById(Integer lxrId,String lxrName,String lxrTel,String lxrEmail) throws WEBException;
	
	/**
	 * 删除指定联系人的信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:27:44
	 * @param lxrId 主键
	 * @return
	 * @throws WEBException
	 */
	boolean delCysLxrById(Integer lxrId) throws WEBException;
	
	/**
	 * 获取指定客户下的联系人
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:27:47
	 * @param cusId 客户编号
	 * @return
	 * @throws WEBException
	 */
	List<CustomerLxrInfoTb> listLxrInfoByCusId(Integer cusId) throws WEBException;
	
	/**
	 * 根据联系人编号、代理机构编号获取联系人信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午10:44:50
	 * @param lxrId 联系人编号
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<CustomerLxrInfoTb> listLxrInfoByCusId(Integer lxrId,Integer cpyId) throws WEBException;
	
	/**
	 * 增加发明人信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:33:59
	 * @param cusId
	 * @param fmrName
	 * @param fmriCard
	 * @param fmrTel
	 * @param fmrEmail
	 * @return
	 * @throws WEBException
	 */
	Integer addCusFmrInfo(Integer cusId,String fmrName,String fmriCard,String fmrTel,String fmrEmail) throws WEBException;
	
	/**
	 * 修改发明人信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:34:04
	 * @param fmrId
	 * @param fmrName
	 * @param fmriCard
	 * @param fmrTel
	 * @param fmrEmail
	 * @return
	 * @throws WEBException
	 */
	boolean upCusFmrById(Integer fmrId,String fmrName,String fmriCard,String fmrTel,String fmrEmail) throws WEBException;
	
	/**
	 * 删除发明人信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:34:09
	 * @param fmrId
	 * @return
	 * @throws WEBException
	 */
	boolean delCusFmrById(Integer fmrId) throws WEBException;
	
	/**
	 * 根据客户编号获取发明人信息
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:34:12
	 * @param cusId
	 * @return
	 * @throws WEBException
	 */
	List<CustomerFmrInfoTb> listFmrInfoByCusId(Integer cusId) throws WEBException;
	
	/**
	 * 根据发明人编号、代理机构编号获取发明人信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午10:29:05
	 * @param fmrId 发明人编号
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<CustomerFmrInfoTb> listFmrInfoByFmrId(Integer fmrId,Integer cpyId) throws WEBException;
}
