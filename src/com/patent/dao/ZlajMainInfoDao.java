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
	 * 根据条件分页获取专利基本信息表列表（Id降序排列）
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
	 * @param cusId 客户编号（0表示全部）
	 * @param sDate 开始时间(####-##)确定要月份（""表示全部）
	 * @param eDate 结束时间(####-##)确定要月份（""表示全部）
	 * @param lqStatus 任务条件（0：流程任务分配，1：专利任务，2：撰写任务领取，3：我的专利）
	 * @param ajAddUserId 案件录入人员
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<ZlajMainInfoTb> findPageInfoByOpt(Session sess,Integer cpyId,Integer stopStatus,String sqAddress,
			String ajNoQt,String zlNo,String ajTitle,String ajType,Integer cusId,String sDate,String eDate,Integer lqStatus,Integer ajAddUserId,Integer pageNo,Integer pageSize);
	
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
	 * @param cusId 客户编号（0表示全部）
	 * @param sDate 开始时间(####-##)确定要月份（""表示全部）
	 * @param eDate 结束时间(####-##)确定要月份（""表示全部）
	 * @param lqStatus 任务条件（0：流程任务分配，1：专利任务，2：撰写任务领取，3：我的专利）
	 * @param ajAddUserId 案件录入人员
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer cpyId,Integer stopStatus,String sqAddress,
			String ajNoQt,String zlNo,String ajTitle,String ajType,Integer cusId,String sDate,String eDate,Integer lqStatus,Integer ajAddUserId);
	
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
	 * @param ajType 案件类型
	 * @param currYear 当前年份
	 * @return
	 */
	List<ZlajMainInfoTb> findFirstInfoByOpt(Session sess,Integer cpyId,String ajType,String currYear);
	
	/**
	 * 根据专利号全网获取案件专利（填写优先权时使用）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-23 下午09:36:47
	 * @param sess
	 * @param ajNoGf 专利/申请号
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<ZlajMainInfoTb> findSpecInfoByOpt(Session sess,String ajNoGf,Integer cpyId);
	
	/**
	 * 通过专利标题、申请人、专利类型获取专利信息(导入受理通知书时使用)
	 * @description
	 * @author Administrator
	 * @date 2018-9-23 上午09:28:04
	 * @param sess
	 * @param zlTitle 专利标题
	 * @param sqrName 申请人
	 * @param zlType 专利类型
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<ZlajMainInfoTb> findSpecInfoByOpt(Session sess,String zlTitle,String sqrName,String zlType,Integer cpyId);
	
	/**
	 * 获取指定用户、指定代理机构、指定流程任务的专利列表（获取指定人是否具有通知书导入和费用催缴流程任务）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-29 下午10:04:03
	 * @param sess
	 * @param lcNameEng 流程任务（tzs,fycj）--(通知书导入、费用催缴)
	 * @param userId 指定员工
	 * @param cpyId 指定代理机构
	 * @return
	 */
	List<ZlajMainInfoTb> findInfoByOpt(Session sess,String lcNameEng,Integer userId,Integer cpyId);
	
	/**
	 * 通过专利号、客户编号获取专利列表
	 * @description
	 * @author Administrator
	 * @date 2019-1-2 上午11:49:25
	 * @param sess
	 * @param zlNo 专利号（""时为全部）
	 * @param cusId 客户编号（0时表示全部）
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<ZlajMainInfoTb> findInfoByOpt_1(Session sess,String zlNo,Integer cusId,Integer cpyId);
	
	/**
	 * 获取指定专利人员的添加的专利数量
	 * @description
	 * @author Administrator
	 * @date 2019-1-17 下午03:53:03
	 * @param sess
	 * @param addUserId 专利人员
	 * @return
	 */
	Integer getCountByAddUserId(Session sess,Integer addUserId);
	
	/**
	 * 获取指定专利名称的专利信息（案件正常状态下）
	 * @description
	 * @author Administrator
	 * @date 2019-4-1 上午10:20:53
	 * @param sess
	 * @param zlTitle 专利标题
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<ZlajMainInfoTb> findInfoByZlTitle(Session sess,String zlTitle,Integer cpyId);
}
