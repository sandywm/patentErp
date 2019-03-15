package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajZdSubmitTb;

public interface ZlajZdSubmitDao {
	/**
	 * 根据主键加载主动提交信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的主动提交信息的主键值
	 * @return 加载的主动提交信息PO
	 */
	ZlajZdSubmitTb get(Session sess,int id);
	
	/**
	 * 保存主动提交信息实体，新增一条主动提交信息记录
	 * @param arInfo 保存的主动提交信息实例
	 */
	void save(Session sess,ZlajZdSubmitTb zdInfo);
	
	/**
	 * 分页获取指定条件下的主动提交列表
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午09:34:06
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param zlTitle 专利名称
	 * @param zlNo 专利号
	 * @param signStatus 签收状态(0:全部,1:未签收，2：已签收)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<ZlajZdSubmitTb> findPageInfoByOpt(Session sess,Integer cpyId,String zlTitle,String zlNo,Integer signStatus,Integer pageNo,Integer pageSize);
	
	/**
	 * 获取指定条件下的主动提交记录条数
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午09:34:22
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param zlTitle 专利名称
	 * @param zlNo 专利号
	 * @param signStatus 签收状态(0:全部,1:未签收，2：已签收)
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer cpyId,String zlTitle,String zlNo,Integer signStatus);
	
	/**
	 * 根据专利号、请求书名称、签收状态获取主动提交信息列表
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午09:37:46
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param zlNo 专利编号
	 * @param qqsName 请求书名称
	 * @param signStatus 签收状态(0:全部,1:未签收，2：已签收)
	 * @return
	 */
	List<ZlajZdSubmitTb> findInfoByZlNo(Session sess,Integer cpyId,String zlNo,String qqsName,Integer signStatus);
}
