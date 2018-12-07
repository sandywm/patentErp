package com.patent.service;

import java.util.List;
import com.patent.exception.WEBException;
import com.patent.module.CpyBonusInfo;

public interface CpyBonusInfoManager {

	/**
	 * 增加项目提成信息
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午04:06:21
	 * @param workType 工作类型（zx,sc.....）
	 * @param zlType 专利类型(fm,syxx,wg)
	 * @param zlLevel 专利难易度(1,2,3)
	 * @param bonusFee 提成费用
	 * @param cpyId
	 * @return
	 * @throws WEBException
	 */
	Integer addCbInfo(String workType, String zlType,Integer zlLevel,String bonusFee,Integer cpyId) throws WEBException;
	
	/**
	 * 根据条件分页获取提成信息记录列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午04:07:23
	 * @param workType 工作类型(""表示全部)
	 * @param zlType 专利类型(""表示全部)
	 * @param zlLevel 专利难易度(0表示全部)
	 * @param cpyId 代理机构编号
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<CpyBonusInfo> listPageInfoByOpt(String workType,String zlType, Integer zlLevel, Integer cpyId, Integer pageNo,Integer pageSize) throws WEBException;
	
	/**
	 * 根据条件获取提成信息记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午04:07:47
	 * @param workType 工作类型(""表示全部)
	 * @param zlType 专利类型(""表示全部)
	 * @param zlLevel 专利难易度(0表示全部)
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(String workType,String zlType,Integer zlLevel,Integer cpyId) throws WEBException;
	
	/**
	 * 根据条件获取提成信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午04:08:07
	 * @param workType 工作类型
	 * @param zlType 专利类型
	 * @param zlLevel 专利难易度
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<CpyBonusInfo> listInfoByOpt(String workType,String zlType,Integer zlLevel,Integer cpyId) throws WEBException;
	
	/**
	 * 修改指定主键的提成费用
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午04:08:25
	 * @param id 主键
	 * @param bonusFee 费用
	 * @return
	 * @throws WEBException
	 */
	boolean updateCbInfoById(Integer id,String bonusFee) throws WEBException;
	
	/**
	 * 获取指定主键的项目提成信息实体
	 * @description
	 * @author Administrator
	 * @date 2018-12-7 下午04:28:36
	 * @param id 主键
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	CpyBonusInfo getEntityById(Integer id,Integer cpyId) throws WEBException;
}
