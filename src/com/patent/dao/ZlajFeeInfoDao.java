package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajFeeInfoTb;

public interface ZlajFeeInfoDao {
	
	/**
	 * 根据主键加载专利案件缴费信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件缴费信息的主键值
	 * @return 加载的专利案件缴费信息PO
	 */
	ZlajFeeInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件缴费信息实体，新增一条专利案件缴费信息记录
	 * @param feeInfo 保存的专利案件缴费信息实例
	 */
	void save(Session sess,ZlajFeeInfoTb feeInfo);
	
	/**
	 * 根据主键删除专利案件缴费信息实体，删除一条专利案件缴费信息记录
	 * @param id 需要删除专利案件缴费信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件缴费信息记录
	 * @param feeInfo 需要更新的专利案件缴费信息
	 */
	void update(Session sess,ZlajFeeInfoTb feeInfo);
	
	
	/**
	*  根据主键编号获取缴费信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:19:14
	*  @param sess
	*  @param id 主键
	*  @return
	 */
	ZlajFeeInfoTb getFeeEntityById(Session sess,Integer id);
	
	/**
	 * 根据条件获取费用缴纳信息表
	 * @description
	 * @author wm
	 * @date 2018-9-4 下午04:28:52
	 * @param sess
	 * @param zlId 专利编号
	 * @param feeTypeStatus 费用类型（""表示全部）
	 * @return
	 */
	List<ZlajFeeInfoTb> findInfoByOpt(Session sess,Integer zlId,String feeTypeStatus);
	
	/**
	 * 根据专利编号、费用类型编号获取专利案件缴费列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-10 上午11:16:24
	 * @param sess
	 * @param zlId 专利编号
	 * @param feeTypeId 费用类型
	 * @return
	 */
	List<ZlajFeeInfoTb> findInfoByOpt(Session sess,Integer zlId,Integer feeTypeId);
	
	/**
	 * 根据专利编号、通知书名称、缴费状态、代缴状态获取缴费记录列表（按专利分组）
	 * @description
	 * @author Administrator
	 * @date 2018-10-14 上午11:58:45
	 * @param sess
	 * @param zlId 专利编号 （-1为全部）
	 * @param tzsArea 通知书名称 （""为全部）
	 * @param feeStatus 缴费状态（-1为全部）
	 * @param djStatus 代缴状态（-1为全部）
	 * @return
	 */
	List<ZlajFeeInfoTb> findInfoByOpt(Session sess,Integer zlId,String tzsArea,Integer feeStatus,Integer djStatus);
	
	/**
	 * 获取系统中所有缴纳年费的记录（id降序）
	 * @description
	 * @author Administrator
	 * @date 2018-10-25 上午09:38:37
	 * @param sess
	 * @return
	 */
	List<ZlajFeeInfoTb> findYearFeeInfoByOpt(Session sess);
	
	/**
	 * 获取指定代理机构下指定专利的所有费用（按照官方期限升序排列）
	 * @description
	 * @author Administrator
	 * @date 2018-10-30 上午08:48:15
	 * @param sess
	 * @param zlId 专利编号
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<ZlajFeeInfoTb> findAllFeeByZlId(Session sess,Integer zlId,Integer cpyId);
	
	/**
	 * 根据条件获取缴纳费用清单
	 * @description
	 * @author Administrator
	 * @date 2018-11-12 上午09:52:13
	 * @param sess
	 * @param zlId 专利编号
	 * @param feeTypeStatus 费用类型（""表示全部）
	 * @param djStatus 代缴状态（-1为全部）
	 * @param feeStatus 缴费状态（-1为全部）
	 * @param backStatus 退换状态（-1为全部）--代缴的时候才出现
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<ZlajFeeInfoTb> findAllFeeByOpt(Session sess,Integer zlId,String feeTypeStatus,Integer djStatus,Integer feeStatus,Integer backStatus,Integer cpyId);
	
	/**
	 * 根据专利获取指定年度的年费/滞纳金信息
	 * @description
	 * @author Administrator
	 * @date 2018-11-19 下午04:06:24
	 * @param sess
	 * @param zlId 专利编号
	 * @param yearNo 年度
	 * @param feeName 费用名称简称(如果是znjFee时，获取的是滞纳金，否则就是年费)
	 * @return
	 */
	List<ZlajFeeInfoTb> findYearFeeInfoByOpt(Session sess,Integer zlId,Integer yearNo,String feeName);
	
	/**
	 * 根据条件分页/不分页获取费用列表（获取未缴费的费用列表时专利必须在正常状态下）
	 * @description 未交费状态下时按照代理机构期限升序排列
	 * @author Administrator
	 * @date 2018-12-5 上午10:49:41
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param feeStatus 费用缴纳状态--在未交费的状态下不分页
	 * @param diffDays 距离代理机构期限天数(-1表示全部)--只在未缴纳费用中使用
	 * @param zlNo 专利/申请号(""表示全部)
	 * @param ajNo 案件编号(""表示全部)
	 * @param cusId 客户/申请人编号(0表示全部)
	 * @param sDate 开始时间(缴费时间)--feeStatus=1的状态下使用
	 * @param eDate 结束时间(缴费时间)--feeStatus=1的状态下使用
	 * @param qdStatus 0：专利局缴费清单-国家，1：专利缴费清单-客户
	 * @param pageNo 页码
	 * @param pageSize 每页记录条数
	 * @return
	 */
	List<ZlajFeeInfoTb> findInfoByOpt(Session sess,Integer cpyId,Integer feeStatus,Integer diffDays,String zlNo,String ajNo,Integer cusId,String sDate,String eDate,Integer qdStatus,Integer pageNo,Integer pageSize);
	
	/**
	 * 获取指定代理机构下已缴费/全部的费用记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-12-5 上午10:50:06
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param feeStatus 费用缴纳状态--1:已缴费,2：全部
	 * @param zlNo 专利/申请号(""表示全部)
	 * @param ajNo 案件编号(""表示全部)
	 * @param cusId 客户/申请人编号(0表示全部)
	 * @param sDate 开始时间(缴费时间)--feeStatus=1的状态下使用
	 * @param eDate 结束时间(缴费时间)--feeStatus=1的状态下使用
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer cpyId,Integer feeStatus,String zlNo,String ajNo,Integer cusId,String sDate,String eDate);
	
	/**
	 * 获取指定代理机构下已交费用、实收费用、未收费用统计（在已交费用模式下）
	 * @description
	 * @author Administrator
	 * @date 2018-12-11 上午10:57:00
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param zlNo 专利/申请号(""表示全部)
	 * @param ajNo 案件编号(""表示全部)
	 * @param cusId 客户/申请人编号(0表示全部)
	 * @param sDate 开始时间(缴费时间)
	 * @param eDate 结束时间(缴费时间)
	 * @return
	 */
	List<Object> getTjFeeInfoByOpt(Session sess,Integer cpyId,String zlNo,String ajNo,Integer cusId,String sDate,String eDate);
	
	/**
	 * 根据拼接的专利费用编号获取指定代理机构下的未交费用信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-12 下午04:14:53
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param idStr 拼接的费用编号（###,###）
	 * @param feeStatus 缴费状态(0:未交费，1：已缴费)
	 * @return
	 */
	List<ZlajFeeInfoTb> findUnJfInfoByOpt(Session sess,Integer cpyId,String idStr,Integer feeStatus);
	
	/**
	 * 根据专利号、费用名称获取指定代理机构下的费用列表(导入excel缴费清单平账用)
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-12 下午09:14:18
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param feeName 费用名称
	 * @param zlNo 专利号
	 * @return
	 */
	List<ZlajFeeInfoTb> findInfoByOpt(Session sess,Integer cpyId,String zlNo,String feeName);
	
	/**
	 * 获取指定代理机构下指定客户的所有未平账已缴费的费用记录
	 * @description
	 * @author Administrator
	 * @date 2018-12-22 下午03:52:22
	 * @param sess
	 * @param cpyId 代理费编号
	 * @param cusId 客户编号
	 * @param feeType 费用类型标记(gf:官费，dlf:代理费)
	 * @return
	 */
	List<ZlajFeeInfoTb> findUnBackInfoByOpt(Session sess,Integer cpyId,Integer cusId,String feeType);
	
	/**
	 * 根据专利编号、客户编号获取专利费用列表（统计用）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2019-1-3 下午10:07:27
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param zlNo 专利编号
	 * @param cusId 客户编号
	 * @return
	 */
	List<ZlajFeeInfoTb> findAllInfoByOpt(Session sess,Integer cpyId,String zlNo,Integer cusId);
	
	/**
	 * 根据组合的id获取所有的费用列表
	 * @description
	 * @author Administrator
	 * @date 2019-1-15 下午03:32:36
	 * @param sess
	 * @param feeIdStr 费用编号
	 * @return
	 */
	List<ZlajFeeInfoTb> findSpecInfoByFeeIdArr(Session sess,String feeIdStr);
}
