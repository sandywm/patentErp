package com.patent.service;

import java.util.List;
import com.patent.exception.WEBException;
import com.patent.module.PubZlCzRecordTb;
import com.patent.module.PubZlInfoTb;

public interface PubZlInfoManager {

	/**
	 * 增加发布案件初始信息
	 * @author Administrator
	 * @date 2018-8-12 下午09:16:46
	 * @ModifiedBy
	 * @param pubUserId 发布人/公司编号
	 * @param zlTitle 专利标题
	 * @param zlContent 专利内容简介
	 * @param zlType 专利类型
	 * @param zlUpCl 专利技术交底材料
	 * @param zlNewDate 发布专利日期
	 * @return
	 * @throws WEBException
	 */
	Integer addPubZl(Integer pubUserId,String zlTitle, String zlContent,String zlType,String zlUpCl, 
			String zlNewDate) throws WEBException; 

	/**
	 * 根据主键修改发布的专利的基本信息
	 * @author Administrator
	 * @date 2018-8-12 下午09:25:10
	 * @ModifiedBy
	 * @param id 主键
	 * @param zlTitle 专利标题(""不修改)
	 * @param zlContent 专利内容简介(""不修改)
	 * @param zlType 专利类型(""不修改)
	 * @param zlUpCl 专利交底材料
	 * @return
	 * @throws WEBException
	 */
	boolean updateBasicInfoById(Integer id,String zlTitle,String zlContent,String zlType,String zlUpCl) throws WEBException; 
	
	/**
	 * 根据主键修改专利的高级信息
	 * 专利状态(-1时修改案件号，0时为修改为未领取状态并重置其他信息，1时为修改为已领取状态，并修改下列信息)
	 * @author Administrator
	 * @date 2018-8-12 下午09:16:50
	 * @ModifiedBy
	 * @param id 主键
	 * @param zlStatus 专利状态(-1时为修改案件号)
	 * @param lqUserId 领取人 编号
	 * @param lqUserName 领取人姓名
	 * @param lqCpyId 领取人所在公司
	 * @param lqCpyName 领取人所在公司
	 * @param lqDate 领取时间
	 * @param ajId 对应的案件编号
	 * @return
	 * @throws WEBException
	 */
	boolean updatePubZlById(Integer id,Integer zlStatus, Integer lqUserId,
			String lqUserName, Integer lqCpyId, String lqCpyName, String lqDate,
			Integer ajId) throws WEBException; 
	
	/**
	 * 删除指定的发布专利任务
	 * @author Administrator
	 * @date 2018-8-12 下午09:16:57
	 * @ModifiedBy
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	boolean delPubZlById(Integer id) throws WEBException; 
	
	/**
	 * 根据条件分页获取发布专利任务列表
	 * @author Administrator
	 * @date 2018-8-12 下午09:31:46
	 * @ModifiedBy
	 * @param pubId 发布者编号（-1时表示全部）
	 * @param zlTitle 专利标题 （""时表示全部）
	 * @param zlNo 专利号 （""时表示全部）
	 * @param zlType 专利类型 （""时表示全部）
	 * @param pubDate 发布日期 （""时表示全部）
	 * @param zlStatus 专利领取状态 （-1时表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<PubZlInfoTb> listPageInfoByOpt(Integer pubId,String zlTitle,String zlNo,String zlType,
			String pubDate,Integer zlStatus,Integer pageNo,Integer pageSize) throws WEBException; 
	
	/**
	 * 根据条件获取发布专利任务记录条数
	 * @author Administrator
	 * @date 2018-8-12 下午09:17:02
	 * @ModifiedBy
	 * @param pubId 发布者编号（-1时表示全部）
	 * @param zlTitle 专利标题 （""时表示全部）
	 * @param zlNo 专利号 （""时表示全部）
	 * @param zlType 专利类型 （""时表示全部）
	 * @param pubDate 发布日期 （""时表示全部）
	 * @param zlStatus 专利领取状态 （-1时表示全部）
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer pubId,String zlTitle,String zlNo,String zlType,
			String pubDate,Integer zlStatus) throws WEBException; 
	
	/**
	 * 获取指定领取公司指定案件编号的发布专利信息
	 * @author Administrator
	 * @date 2018-8-12 下午09:17:45
	 * @ModifiedBy
	 * @param lqCpyId 领取公司编号
	 * @param ajId 案件编号
	 * @return
	 */
	List<PubZlInfoTb> listSpecInfoByOpt_1(Integer lqCpyId,Integer ajId)throws WEBException ;
	
	/**
	 * 获取指定主键、指定发布人编号的专利发布信息
	 * @description
	 * @author wm
	 * @date 2018-8-13 上午11:37:42
	 * @param id 主键
	 * @param pubId 发布人编号（0时不查询）
	 * @return
	 * @throws WEBException
	 */
	List<PubZlInfoTb> listSpecInfoByOpt(Integer id,Integer pubId)throws WEBException ;
	
	/**
	 * 修改指定发布编号的案件编号
	 * @description
	 * @author wm
	 * @date 2018-8-28 下午05:07:19
	 * @param id 主键
	 * @param ajId 案件编号
	 * @return
	 * @throws WEBException
	 */
	boolean updateAjIdById(Integer id,Integer ajId)throws WEBException ;
	
	/**
	 * 根据条件获取领取人所属公司的领取记录列表（可分页）
	 * @description
	 * @author wm
	 * @date 2018-8-28 下午05:31:31
	 * @param addStatus 增加标记(1：已增加，0：未增加)
	 * @param lqCpyId 申请公司
	 * @param pageFlag 是否分页（true：分页,false：不分页）
	 * @return
	 * @throws WEBException
	 */
	List<PubZlInfoTb> listSpecInfoByOpt_2(Integer lqCpyId,Integer addStatus,boolean pageFlag, Integer pageNo, Integer pageSize)throws WEBException ;
	
	/**
	 * 根据条件获取领取人所属公司的领取记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-29 下午05:08:56
	 * @param addStatus 增加标记(1：已增加，0：未增加)
	 * @param lqCpyId 申请公司
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt_2(Integer lqCpyId,Integer addStatus)throws WEBException ;
	
	/**
	 * 根据专利任务编号获取领取/撤销记录
	 * @description
	 * @author wm
	 * @date 2018-9-10 上午09:24:35
	 * @param pubId 专利任务编号
	 * @return
	 * @throws WEBException
	 */
	List<PubZlCzRecordTb> listInfoByPubId(Integer pubId)throws WEBException ;
	
	/**
	 * 增加指定专利任务下的领取/撤销记录
	 * @description
	 * @author wm
	 * @date 2018-9-10 上午09:38:10
	 * @param pubId 专利编号
	 * @param addContent 领取/撤销记录
	 * @return
	 * @throws WEBException
	 */
	Integer addPzCzInfo(Integer pubId,String addContent)throws WEBException ;
}
