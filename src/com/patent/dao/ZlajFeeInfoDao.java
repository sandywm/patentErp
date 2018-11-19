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
	 * 根据专利获取指定年度的年费信息
	 * @description
	 * @author Administrator
	 * @date 2018-11-19 下午04:06:24
	 * @param sess
	 * @param zlId 专利编号
	 * @param yearNo 年度
	 * @return
	 */
	List<ZlajFeeInfoTb> findYearFeeInfoByOpt(Session sess,Integer zlId,Integer yearNo);
}
