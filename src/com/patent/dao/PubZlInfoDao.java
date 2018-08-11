package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.PubZlInfoTb;

public interface PubZlInfoDao {
	/**
	 * 根据主键加载发布专利信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的发布专利信息的主键值
	 * @return 加载的发布专利信息PO
	 */
	PubZlInfoTb get(Session sess,int id);
	
	/**
	 * 保存发布专利信息实体，新增一条发布专利信息记录
	 * @param pzInfo 保存的发布专利信息实例
	 */
	void save(Session sess,PubZlInfoTb pzInfo);
	
	/**
	 * 删除发布专利信息实体，删除一条发布专利信息记录
	 * @param pzInfo 删除的发布专利信息实体
	 */
	void delete(Session sess,PubZlInfoTb pzInfo);
	
	/**
	 * 根据主键删除发布专利信息实体，删除一条发布专利信息记录
	 * @param id 需要删除发布专利信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 根据主键修改发布专利信息实体，修改一条发布专利信息记录
	 * @param id 需要修改发布专利信息的主键
	 */
	void update(Session sess,PubZlInfoTb pzInfo);
	
	/**
	 * 根据条件分页获取发布的专利信息列表
	 * @author Administrator
	 * @date 2018-8-11 下午09:47:50
	 * @ModifiedBy
	 * @param sess
	 * @param pubId 发布者编号（-1时表示全部）
	 * @param zlTitle 专利标题 （""时表示全部）
	 * @param zlNo 专利号 （""时表示全部）
	 * @param zlType 专利类型 （""时表示全部）
	 * @param pubDate 发布日期 （""时表示全部）
	 * @param zlStatus 专利领取状态 （-1时表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<PubZlInfoTb> findPageInfoByOpt(Session sess,Integer pubId,String zlTitle,String zlNo,String zlType,
			String pubDate,Integer zlStatus,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取发布的专利信息记录条数
	 * @author Administrator
	 * @date 2018-8-11 下午09:55:35
	 * @ModifiedBy
	 * @param sess
	 * @param pubId 发布者编号（-1时表示全部）
	 * @param zlTitle 专利标题 （""时表示全部）
	 * @param zlNo 专利号 （""时表示全部）
	 * @param zlType 专利类型 （""时表示全部）
	 * @param pubDate 发布日期 （""时表示全部）
	 * @param zlStatus 专利领取状态 （-1时表示全部）
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer pubId,String zlTitle,String zlNo,String zlType,
			String pubDate,Integer zlStatus);
	
	/**
	 * 获取指定领取公司指定案件编号的发布专利信息
	 * @author Administrator
	 * @date 2018-8-11 下午10:01:17
	 * @ModifiedBy
	 * @param sess
	 * @param lqCpyId 领取公司编号
	 * @param ajIdStr 案件编号
	 * @return
	 */
	List<PubZlInfoTb> findSpecInfoByOpt(Session sess,Integer lqCpyId,String ajIdStr);
}
