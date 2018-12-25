/**
 * 
 */
package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CusBackFeeInfo;

public interface CusBackFeeInfoDao {

	/**
	 * 获取客户汇款信息实体
	 * @param id 主键
	 */
	CusBackFeeInfo get(Session sess,int id);
	
	/**
	 * 保存客户汇款信息实体，新增一条客户汇款信息记录
	 * @param cbfInfo 保存的客户汇款信息实例
	 */
	void save(Session sess,CusBackFeeInfo cbfInfo);
	
	/**
	 * 删除客户汇款信息实体，删除一条客户汇款信息记录
	 * @param cbfInfo 删除的客户汇款信息实体
	 */
	void delete(Session sess,CusBackFeeInfo cbfInfo);
	
	/**
	 * 根据主键删除客户汇款信息实体，删除一条客户汇款信息记录
	 * @param id 需要删除客户汇款信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条客户汇款信息记录
	 * @param cbfInfo 需要更新的客户汇款信息
	 */
	void update(Session sess,CusBackFeeInfo cbfInfo);
	
	/**
	 * 根据条件分页获取客户汇款信息列表
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-15 下午09:50:18
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param cusId 客户编号(0表示全部)
	 * @param sDate 汇款开始时间（""表示全部）
	 * @param eDate 汇款结束时间（""表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<CusBackFeeInfo> findPageInfoByOpt(Session sess,Integer cpyId,
			Integer cusId,String sDate,String eDate,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取客户汇款信息记录条数
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-15 下午09:51:22
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param cusId 客户编号(0表示全部)
	 * @param sDate 汇款开始时间（""表示全部）
	 * @param eDate 汇款结束时间（""表示全部）
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer cpyId,Integer cusId,String sDate,String eDate);
}
