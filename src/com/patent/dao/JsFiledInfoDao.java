package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.JsFiledInfoTb;

public interface JsFiledInfoDao {

	/**
	 * 根据主键加载专业技术区域信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专业技术区域信息的主键值
	 * @return 加载的专业技术区域信息PO
	 */
	JsFiledInfoTb get(Session sess,int id);
	
	/**
	 * 保存专业技术区域信息实体，新增一条专业技术区域信息记录
	 * @param jsInfo 保存的专业技术区域信息实例
	 */
	void save(Session sess,JsFiledInfoTb jsInfo);
	
	/**
	 * 删除专业技术区域信息实体，删除一条专业技术区域信息记录
	 * @param jsInfo 删除的专业技术区域信息实体
	 */
	void delete(Session sess,JsFiledInfoTb jsInfo);
	
	/**
	 * 根据主键删除专业技术区域信息实体，删除一条专业技术区域信息记录
	 * @param id 需要删除专业技术区域信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 根据主键修改专业技术区域信息记录
	 * @param jsInfo 需要修改的专业技术区域信息
	 */
	void update(Session sess,JsFiledInfoTb jsInfo);
	
	/**
	 * 根据条件获取专业技术区域信息列表
	 * @author Administrator
	 * @date 2018-8-7 下午10:32:18
	 * @ModifiedBy
	 * @param cpyId 代理机构编号
	 * @param jsIdStr 员工擅长专业编号组合(""时表示全部)
	 * @param sess
	 * @return
	 */
	List<JsFiledInfoTb> findInfoByOpt(Session sess,Integer cpyId,String jsIdStr);
	
	/**
	 * 分页获取指定代理机构下的专业技术区域列表
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:37:58
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<JsFiledInfoTb> findPageInfoByCpyId(Session sess,Integer cpyId,Integer pageNo,Integer pageSize);
	
	/**
	 * 获取指定代理机构下的专业技术区域信息记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:38:21
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @return
	 */
	Integer getCountByCpyId(Session sess,Integer cpyId);
	
	/**
	 * 获取指定代理机构下指定专业名字信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午10:15:31
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param zyName 专业名字
	 * @return
	 */
	List<JsFiledInfoTb> findInfoByOpt_1(Session sess,Integer cpyId,String zyName);
}
