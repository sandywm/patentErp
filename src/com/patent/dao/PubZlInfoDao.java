package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.PubZlCzRecordTb;
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
	 * @param ajIdS 案件编号
	 * @return
	 */
	List<PubZlInfoTb> findSpecInfoByOpt_1(Session sess,Integer lqCpyId,Integer ajId);
	
	/**
	 * 获取指定主键、指定发布人编号的专利发布信息
	 * @description
	 * @author wm
	 * @date 2018-8-13 上午11:35:41
	 * @param sess
	 * @param id 主键
	 * @param pubId 发布人编号（0时不查询）
	 * @return
	 */
	List<PubZlInfoTb> findSpecInfoByOpt(Session sess,Integer id,Integer pubId);
	
	/**
	 * 根据条件获取领取人所属公司的领取记录列表（可分页）
	 * @description
	 * @author wm
	 * @date 2018-8-28 下午05:10:35
	 * @param sess
	 * @param addStatus 增加标记(1：已增加，0：未增加)
	 * @param lqCpyId 申请公司
	 * @param lqUserId 领取人编号（0表示全部）
	 * @param pageFlag 是否分页（true：分页,false：不分页）
	 * @return
	 */
	List<PubZlInfoTb> findSpecInfoByOpt_2(Session sess, Integer lqCpyId,Integer lqUserId, Integer addStatus,boolean pageFlag,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取领取人所属公司的领取记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-29 上午11:37:59
	 * @param sess
	 * @param addStatus 增加标记(1：已增加，0：未增加)
	 * @param lqCpyId 申请公司
	 * @param lqUserId 领取人编号（0表示全部）
	 * @return
	 */
	Integer getCountByOpt_2(Session sess, Integer lqCpyId,Integer lqUserId, Integer addStatus);
	
	/**
	 * 获取指定发布专利任务的领取/撤销记录
	 * @description
	 * @author wm
	 * @date 2018-9-10 上午09:21:46
	 * @param sess
	 * @param pubId 专利任务编号
	 * @return
	 */
	List<PubZlCzRecordTb> findInfoByPubId(Session sess,Integer pubId);
	
	/**
	 * 保存发布专利领取/撤销信息实体，新增一条专利领取/撤销信息记录
	 * @param pzCzInfo 保存的专利领取/撤销信息实例
	 */
	void saveCz(Session sess,PubZlCzRecordTb pzCzInfo);
}
