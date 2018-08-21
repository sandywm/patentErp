package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ModActInfoTb;

public interface ModActInfoDao {
	/**
	 * 根据主键加载模块动作信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的模块动作信息的主键值
	 * @return 加载的模块动作信息PO
	 */
	ModActInfoTb get(Session sess,int id);
	
	/**
	 * 保存模块动作信息实体，新增一条模块动作信息记录
	 * @param maInfo 保存的模块动作信息实例
	 */
	void save(Session sess,ModActInfoTb maInfo);
	
	/**
	 * 删除模块动作信息实体，删除一条模块动作信息记录
	 * @param maInfo 删除的模块动作信息实体
	 */
	void delete(Session sess,ModActInfoTb maInfo);
	
	/**
	 * 根据主键删除模块动作信息实体，删除一条模块动作信息记录
	 * @param id 需要删除模块动作信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条模块动作信息记录
	 * @param maInfo 需要更新的模块动作信息
	 */
	void update(Session sess,ModActInfoTb maInfo);
	
	/**
	 * 列出指定模块的动作列表
	 * @description
	 * @author wm
	 * @date 2018-8-7 下午05:41:34
	 * @param sess
	 * @param modId 模块编号
	 * @return
	 */
	List<ModActInfoTb> findSpecInfo(Session sess,Integer modId);
	
	/**
	 * 列出指定模块下指定模块动作的模块动作列表
	 * @author Administrator
	 * @date 2018-8-8 下午10:44:22
	 * @ModifiedBy
	 * @param sess
	 * @param modId
	 * @param actNameChi 模块动作中文
	 * @param actNameEng 模块动作英文
	 * @return
	 */
	List<ModActInfoTb> findSpecInfoByOpt(Session sess,Integer modId,String actNameChi,String actNameEng);
	
	/**
	 * 根据主键获取模块动作信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 下午03:44:31
	 * @param sess
	 * @param id
	 * @return
	 */
	List<ModActInfoTb> findSpecInfoById(Session sess,Integer id);
}
