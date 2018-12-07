package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CpyBonusInfo;

public interface CpyBonusInfoDao {

	/**
	 * 根据主键加载提成信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的提成信息的主键值
	 * @return 加载的提成信息PO
	 */
	CpyBonusInfo get(Session sess,int id);
	
	/**
	 * 保存提成信息实体，新增一条提成信息记录
	 * @param cbInfo 保存的提成信息实例
	 */
	void save(Session sess,CpyBonusInfo cbInfo);
	
	/**
	 * 更新一条提成信息记录
	 * @param cbInfo 需要更新的提成信息
	 */
	void update(Session sess,CpyBonusInfo cbInfo);
	
	/**
	 * 根据条件分页获取提成信息记录列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午03:41:30
	 * @param sess
	 * @param workType 工作类型(""表示全部)
	 * @param zlType 专利类型(""表示全部)
	 * @param zlLevel 专利难易度(0表示全部)
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<CpyBonusInfo> findPageInfoByOpt(Session sess,String workType,String zlType,Integer zlLevel,Integer cpyId,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取提成信息记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午03:53:31
	 * @param sess
	 * @param workType 工作类型(""表示全部)
	 * @param zlType 专利类型(""表示全部)
	 * @param zlLevel 专利难易度(0表示全部)
	 * @param cpyId 代理机构编号
	 * @return
	 */
	Integer getCountByOpt(Session sess,String workType,String zlType,Integer zlLevel,Integer cpyId);
	
	/**
	 * 根据条件获取提成信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午03:53:55
	 * @param sess
	 * @param workType 工作类型
	 * @param zlType 专利类型
	 * @param zlLevel 专利难易度
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<CpyBonusInfo> findInfoByOpt(Session sess,String workType,String zlType,Integer zlLevel,Integer cpyId);
}
