package com.patent.service;

import java.util.Date;
import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.CpyInfoTb;

public interface CpyInfoManager {

	/**
	 * 增加专业代理公司（注册、总公司增加分公司时用）
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:02:48
	 * @param cpyName 公司名字
	 * @param cpyAddress 公司地址
	 * @param cpyProv 公司所在省份
	 * @param cpyCity 公司所在城市
	 * @param cpyFr 公司法人
	 * @param cpyYyzz 公司营业执照
	 * @param cpyLxr 公司联系人
	 * @param lxrTel 联系人电话
	 * @param lxrEmail 联系人邮箱
	 * @param cpySubId 子公司
	 * @param cpyParId 主公司
	 * @param cpyUrl 公司网址
	 * @param cpyProfile 公司简介
	 * @param signDate 注册时间
	 * @param endDate 到期时间
	 * @param hotStatus 公司热度
	 * @param cpyLevel 公司会员等级
	 * @return
	 * @throws WEBException
	 */
	Integer addCpy(String cpyName, String cpyAddress, String cpyProv,
			String cpyCity, String cpyFr, String cpyYyzz, String cpyLxr,
			String lxrTel, String lxrEmail, String cpySubId, Integer cpyParId,
			String cpyUrl, String cpyProfile, String signDate, Date endDate,
			Integer hotStatus, Integer cpyLevel) throws WEBException;
	
	/**
	 * 修改代理机构基本信息
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:11:42
	 * @param id
	 * @param cpyName 公司名字
	 * @param cpyAddress 公司地址
	 * @param cpyProv 公司所在省份
	 * @param cpyCity 公司所在城市
	 * @param cpyFr 公司法人
	 * @param cpyLxr 公司联系人
	 * @param lxrTel 联系人电话
	 * @param lxrEmail 联系人邮箱
	 * @param cpyYyzz 营业执照
	 * @param cpyUrl 公司网址
	 * @param cpyProfile 公司简介
	 * @return
	 * @throws WEBException
	 */
	boolean updateBasicCpyInfoById(Integer id,String cpyName,String cpyAddress,String cpyProv,String cpyCity,String cpyFr,
			String cpyLxr,String lxrTel,String lxrEmail,String cpyYyzz,String cpyUrl,String cpyProfile) throws WEBException;
	
	/**
	 * 修改主/分公司信息编号
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:12:25
	 * @param id
	 * @param cpyParId 主公司信息编号
	 * @param cpySubId 分公司信息编号
	 * @return
	 * @throws WEBException
	 */
	boolean updateJoinInfoById(Integer id,Integer cpyParId,Integer cpySubId) throws WEBException;
	
	/**
	 * 修改代理机构结束日期、公司热度、会员等级
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:12:57
	 * @param id
	 * @param endDate 结束日期（null不修改）
	 * @param hotStatus 公司热度（0时不修改）
	 * @param cpyLevel 公司会员等级（0时不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateCpyInfoById(Integer id,Date endDate,Integer hotStatus,Integer cpyLevel)throws WEBException;
	
	/**
	 * 分页获取代理公司信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:16:07
	 * @param cpyName 代理机构名称(""表示全部)--模糊查询
	 * @param cpyProv 代理机构所在身份(""表示全部)
	 * @param cpyCity 代理机构所在城市(""表示全部)
	 * @param cpyFr 代理机构法人(""表示全部)
	 * @param cpyLxr 代理机构联系人(""表示全部)
	 * @param cpyLevel 代理机构会员级别(-1表示全部)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<CpyInfoTb> listPageInfoByOpt(String cpyName,String cpyProv, String cpyCity, String cpyFr, String cpyLxr,
			Integer cpyLevel,Integer yxStatus, Integer pageNo, Integer pageSize)throws WEBException;
	
	/**
	 * 根据条件获取代理公司信息记录条数
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:16:37
	 * @param cpyName 代理机构名称(""表示全部)--模糊查询
	 * @param cpyProv 代理机构所在身份(""表示全部)
	 * @param cpyCity 代理机构所在城市(""表示全部)
	 * @param cpyFr 代理机构法人(""表示全部)
	 * @param cpyLxr 代理机构联系人(""表示全部)
	 * @param cpyLevel 代理机构会员级别(-1表示全部)
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(String cpyName,String cpyProv, String cpyCity, String cpyFr, String cpyLxr,
			Integer cpyLevel,Integer yxStatus)throws WEBException;
}
