package com.patent.service;

import java.util.Date;
import java.util.List;
import com.patent.exception.WEBException;
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
			Date zlNewDate) throws WEBException; 

	/**
	 * 根据主键修改发布的专利的基本信息
	 * @author Administrator
	 * @date 2018-8-12 下午09:25:10
	 * @ModifiedBy
	 * @param id 主键
	 * @param zlTitle 专利标题(""不修改)
	 * @param zlContent 专利内容简介
	 * @param zlType 专利类型
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
	 * @param ajIdStr 对应的案件编号
	 * @return
	 * @throws WEBException
	 */
	boolean updatePubZlById(Integer id,Integer zlStatus, Integer lqUserId,
			String lqUserName, Integer lqCpyId, String lqCpyName, Date lqDate,
			String ajIdStr) throws WEBException; 
	
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
	 * @param ajIdStr 案件编号
	 * @return
	 */
	List<PubZlInfoTb> listSpecInfoByOpt(Integer lqCpyId,String ajIdStr)throws WEBException ;
	
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
}
