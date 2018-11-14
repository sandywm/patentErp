package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajLcInfoTb;

public interface ZlajLcInfoDao {
	/**
	 * 根据主键加载专利案件流程信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件流程信息的主键值
	 * @return 加载的专利案件流程信息PO
	 */
	ZlajLcInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件流程信息实体，新增一条专利案件流程信息记录
	 * @param lcInfo 保存的专利案件流程信息实例
	 */
	void save(Session sess,ZlajLcInfoTb lcInfo);
	
	/**
	 * 根据主键删除专利案件流程信息实体，删除一条专利案件流程信息记录
	 * @param id 需要删除专利案件流程信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件流程信息记录
	 * @param lcInfo 需要更新的专利案件流程信息
	 */
	void update(Session sess,ZlajLcInfoTb lcInfo);
	
	/**
	* 获取专利额外要求信息列表(按照流程号降序排列)
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:14:17
	*  @param sess
	*  @param yqType 类型（fm,sy,wg）""表示全部
	*  @return
	 */
	List<ZlajLcInfoTb> findInfo(Session sess,Integer ajId);
	
	/**
	 * 根据主键编号获取额外要求信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:59:06
	*  @param sess
	*  @param id 主键
	*  @return
	 */
	List<ZlajLcInfoTb> findInfoById(Session sess,Integer id);
	
	/**
	 * 根据流程名字获取流程详细信息
	 * @description
	 * @author wm
	 * @date 2018-9-13 下午05:51:01
	 * @param sess
	 * @param ajId 专利编号
	 * @param lcMz 流程名字
	 * @return
	 */
	List<ZlajLcInfoTb> findInfoByLcMz(Session sess,Integer ajId,String lcMz);
	
	/**
	 * 获取最后一个未完成的流程任务列表(lcNo降序)
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-9-18 下午09:35:29
	 * @param sess
	 * @param ajId 专利编号
	 * @return
	 */
	List<ZlajLcInfoTb> findLastInfo(Session sess,Integer ajId);
	
	/**
	 * 获取指定专利的专利流程（id降序排列）
	 * @description
	 * @author Administrator
	 * @date 2018-11-14 下午04:16:56
	 * @param sess
	 * @param zlId 专利编号
	 * @return
	 */
	List<ZlajLcInfoTb> findInfoByZlId(Session sess,Integer zlId);
	
}
