package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CpyInfoTb;

public interface CpyInfoDao {
	/**
	 * 根据主键加载代理机构信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的代理机构信息的主键值
	 * @return 加载的代理机构信息PO
	 */
	CpyInfoTb get(Session sess,int id);
	
	/**
	 * 保存代理机构信息实体，新增一条代理机构信息记录
	 * @param cpyInfo 保存的代理机构信息实例
	 */
	void save(Session sess,CpyInfoTb cpyInfo);
	
	/**
	 * 删除代理机构信息实体，删除一条代理机构信息记录
	 * @param cpyInfo 删除的代理机构信息实体
	 */
	void delete(Session sess,CpyInfoTb cpyInfo);
	
	/**
	 * 根据主键删除代理机构信息实体，删除一条代理机构信息记录
	 * @param id 需要删除代理机构信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条代理机构信息记录
	 * @param cpyInfo 需要更新的代理机构信息
	 */
	void update(Session sess,CpyInfoTb cpyInfo);
	
	/**
	 * 根据条件分页获取代理机构信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:45:31
	 * @param sess
	 * @param cpyNamePy 代理机构名称拼音(""表示全部)--模糊查询
	 * @param cpyProv 代理机构所在身份(""表示全部)
	 * @param cpyCity 代理机构所在城市(""表示全部)
	 * @param cpyFr 代理机构法人(""表示全部)
	 * @param cpyLxr 代理机构联系人(""表示全部)
	 * @param cpyLevel 代理机构会员级别(-1表示全部)
	 * @param gqStatus 代理机构过期状态（-1表示全部,0：未过期，1：已过期）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<CpyInfoTb> findPageInfoByOpt(Session sess,String cpyNamePy,String cpyProv,String cpyCity,String cpyFr,
			String cpyLxr,Integer cpyLevel,Integer gqStatus,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取代理机构信息记录条数
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:49:54
	 * @param sess
	 * @param cpyNamePy 代理机构名称拼音(""表示全部)--模糊查询
	 * @param cpyProv 代理机构所在身份(""表示全部)
	 * @param cpyCity 代理机构所在城市(""表示全部)
	 * @param cpyFr 代理机构法人(""表示全部)
	 * @param cpyLxr 代理机构联系人(""表示全部)
	 * @param cpyLevel 代理机构会员级别(-1表示全部)
	 * @param gqStatus 代理机构过期状态（-1表示全部,0：未过期，1：已过期）
	 * @return
	 */
	Integer getCountByOpt(Session sess,String cpyNamePy,String cpyProv,String cpyCity,String cpyFr,
			String cpyLxr,Integer cpyLevel,Integer gqStatus);
	
	/**
	 * 根据主公司编号获取所有分公司信息
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:51:49
	 * @param sess
	 * @param parCpyId
	 * @return
	 */
	List<CpyInfoTb> findSubInfoByParCpyId(Session sess,Integer parCpyId);
	
	/**
	 * 根据分公司获取主公司信息
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午10:06:30
	 * @param sess
	 * @param subCpyId
	 * @return
	 */
	List<CpyInfoTb> findParInfoBySubCpyId(Session sess,Integer subCpyId);
	
	/**
	 * 修改指定代理机构的主公司编号
	 * @description 修改的同时也要修改主公司的分公司信息编号
	 * @author wm
	 * @date 2018-7-24 上午09:53:48
	 * @param sess
	 * @param id 指定代理机构编号
	 * @param cpyParId 主公司编号
	 * @return
	 */
	boolean updateCpyParInfoById(Session sess,Integer id,Integer cpyParId);
	
	/**
	 * 获取所有即将到期或者已到期的代理机构（即将到期5、1天内/已到期0,1天内进行邮件提醒）
	 * @description
	 * @author wm
	 * @date 2018-8-6 下午04:27:02
	 * @param sess
	 * @return
	 */
	List<CpyInfoTb> findEndDateCpyInfo(Session sess);
}
