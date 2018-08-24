package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajMainInfoTb;

public interface ZlajMainInfoDao {
	/**
	 * 根据主键加载专利案件主管理信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件主管理信息的主键值
	 * @return 加载的专利案件主管理信息PO
	 */
	ZlajMainInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件主管理信息实体，新增一条专利案件主管理信息记录
	 * @param ajInfo 保存的专利案件主管理信息实例
	 */
	void save(Session sess,ZlajMainInfoTb ajInfo);
	
	/**
	 * 根据主键删除专利案件主管理信息实体，删除一条专利案件主管理信息记录
	 * @param id 需要删除专利案件主管理信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件主管理信息记录
	 * @param ajInfo 需要更新的专利案件主管理信息
	 */
	void update(Session sess,ZlajMainInfoTb ajInfo);
	
	/**
	 * 根据条件分页获取专利基本信息表列表（按照时间先后顺序降序排列）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-22 下午10:10:36
	 * @param sess
	 * @param cpyId 代理机构编号（-1表示全部）
	 * @param stopStatus 案件终止状态（当案件是通过任务获取时）（-1表示全部）
	 * @param sqAddress 案件申请地区（""表示全部）
	 * @param ajNoQt 案件编号（""表示全部）
	 * @param zlNo 案件专利号（""表示全部）
	 * @param ajTitle 案件名称（""表示全部）
	 * @param ajType 案件类型（""表示全部）
	 * @param lxr 案件联系人（""表示全部）
	 * @param sDate 开始时间(####-##)确定要月份（""表示全部）
	 * @param eDate 结束时间(####-##)确定要月份（""表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<ZlajMainInfoTb> findPageInfoByOpt(Session sess,Integer cpyId,Integer stopStatus,String sqAddress,
			String ajNoQt,String zlNo,String ajTitle,String ajType,String lxr,String sDate,String eDate,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取专利基本信息记录条数
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-22 下午10:22:56
	 * @param cpyId 代理机构编号（-1表示全部）
	 * @param stopStatus 案件终止状态（当案件是通过任务获取时）（-1表示全部）
	 * @param sqAddress 案件申请地区（""表示全部）
	 * @param ajNoQt 案件编号（""表示全部）
	 * @param zlNo 案件专利号（""表示全部）
	 * @param ajTitle 案件名称（""表示全部）
	 * @param ajType 案件类型（""表示全部）
	 * @param lxr 案件联系人（""表示全部）
	 * @param sDate 开始时间(####-##)确定要月份（""表示全部）
	 * @param eDate 结束时间(####-##)确定要月份（""表示全部）
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer cpyId,Integer stopStatus,String sqAddress,
			String ajNoQt,String zlNo,String ajTitle,String ajType,String lxr,String sDate,String eDate);
	
	/**
	 * 根据主键获取专利案件信息
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-22 下午10:29:19
	 * @param sess
	 * @param id 主键
	 * @param cpyId 代理机构编号（-1表示全部）
	 * @return
	 */
	List<ZlajMainInfoTb> findSpecInfoById(Session sess,Integer id,Integer cpyId);
	
	/**
	 * 获取第一条记录（按照案件编号降序排列）获取即将增加的案件号
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-22 下午10:38:24
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<ZlajMainInfoTb> findFirstInfoByCpyId(Session sess,Integer cpyId);
	
	/**
	 * 根据条件全网获取案件专利（填写优先权时使用）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-23 下午09:36:47
	 * @param sess
	 * @param ajNoGf 专利/申请号
	 * @param sqAddress 申请地区
	 * @param sqDate 申请日期
	 * @return
	 */
	List<ZlajMainInfoTb> findSpecInfoByOpt(Session sess,String ajNoGf,String sqAddress,String sqDate);
	
}
