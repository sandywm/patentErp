package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CpyJoinInfoTb;

public interface CpyJoinInfoDao {
	/**
	 * 根据主键加载分/主公司合并信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的分/主公司合并信息的主键值
	 * @return 加载的分/主公司合并信息PO
	 */
	CpyJoinInfoTb get(Session sess,int id);
	
	/**
	 * 保存分/主公司合并信息实体，新增一条分/主公司合并信息记录
	 * @param cjInfo 保存的分/主公司合并信息实例
	 */
	void save(Session sess,CpyJoinInfoTb cjInfo);
	
	/**
	 * 删除分/主公司合并信息实体，删除一条分/主公司合并信息记录
	 * @param cjInfo 删除的分/主公司合并信息实体
	 */
	void delete(Session sess,CpyJoinInfoTb cjInfo);
	
	/**
	 * 根据主键删除分/主公司合并信息实体，删除一条分/主公司合并信息记录
	 * @param id 需要删除分/主公司合并信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条分/主公司合并信息记录
	 * @param cjInfo 需要更新的分/主公司合并信息
	 */
	void update(Session sess,CpyJoinInfoTb cjInfo);
	
	/**
	 * 根据条件获取分/主公司合并信息记录列表
	 * @description
	 * @author wm
	 * @date 2018-8-15 下午05:28:55
	 * @param sess
	 * @param parCpyId 主公司编号（0时表示全部）
	 * @param subCpyId  子公司编号（0时表示全部）
	 * @param joinStatus 合并状态 （-1时表示全部）
	 * @param applyCpyId 申请公司编号 （0时表示全部）
	 * @param czDate 操作时间 （""时表示全部）
	 * @return
	 */
	List<CpyJoinInfoTb> findInfoByOpt(Session sess,Integer parCpyId,Integer subCpyId,Integer joinStatus,Integer applyCpyId,String czDate);
	
	/**
	 * 根据条件分页获取分/主公司合并信息记录列表
	 * @description
	 * @author wm
	 * @date 2018-8-15 下午05:31:37
	 * @param sess
	 * @param parCpyId 主公司编号（0时表示全部）
	 * @param subCpyId  子公司编号（0时表示全部）
	 * @param joinStatus 合并状态 （-1时表示全部）
	 * @param applyCpyId 申请公司编号 （0时表示全部）
	 * @param czDate 操作时间 （""时表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<CpyJoinInfoTb> findPageInfoByOpt(Session sess,Integer parCpyId,Integer subCpyId,Integer joinStatus,Integer applyCpyId,String czDate,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取分/主公司合并信息记录列表条数
	 * @description
	 * @author wm
	 * @date 2018-8-15 下午05:31:41
	 * @param sess
	 * @param parCpyId 主公司编号（0时表示全部）
	 * @param subCpyId  子公司编号（0时表示全部）
	 * @param joinStatus 合并状态 （-1时表示全部）
	 * @param applyCpyId 申请公司编号 （0时表示全部）
	 * @param czDate 操作时间 （""时表示全部）
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer parCpyId,Integer subCpyId,Integer joinStatus,Integer applyCpyId,String czDate);
}
