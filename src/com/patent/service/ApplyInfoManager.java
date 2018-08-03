package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ApplyInfoTb;

public interface ApplyInfoManager {

	/**
	 * 增加申请专利(人/公司)信息记录
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午10:16:52
	 * @param appType 申请人/公司类型（申请人/公司类型中dzyx：大专院校、kydw：科研单位、gkqy：工矿企业，sydw：事业单位，gr：个人）
	 * @param appName 申请人/公司名字
	 * @param appNamePy 申请人/公司拼音
	 * @param appICard 申请人/公司卡号（公司机构代码/个人身份证号）
	 * @param appAddress 申请人/公司地址
	 * @param account 申请人/公司账号
	 * @param password 申请人/公司密码
	 * @param appLxr 申请人/公司联系人
	 * @param lxrTel 申请人/公司电话
	 * @param appEmail 申请人/公司邮箱
	 * @param appQQ 申请人/公司QQ
	 * @return
	 * @throws WEBException
	 */
	Integer addAppInfo(String appType, String appName, String appNamePy,String appICard,
			String appAddress, String appAccount, String appPass,
			String appLxr, String appTel, String appEmail, String appQq) throws WEBException;
	
	/**
	 * 根据条件分页获取申请专利(人/公司)信息记录列表
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午10:18:35
	 * @param appType 申请人/公司类型（""表示全部）
	 * @param appNamePy 申请人/公司拼音（""表示全部）
	 * @param appLxr 申请人/公司联系人（""表示全部）
	 * @param lxrTel 申请人/公司电话（""表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<ApplyInfoTb> findPageInfoByOpt(String appType,String appNamePy,String appLxr,String lxrTel,Integer pageNo,Integer pageSize) throws WEBException;
	
	/**
	 * 根据条件获取申请专利(人/公司)信息记录条数
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午10:21:26
	 * @param appType 申请人/公司类型（""表示全部）
	 * @param appNamePy 申请人/公司拼音（""表示全部）
	 * @param appLxr 申请人/公司联系人（""表示全部）
	 * @param lxrTel 申请人/公司电话（""表示全部）
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(String appType,String appNamePy,String appLxr,String lxrTel) throws WEBException;
	
	/**
	 * 根据主键获取申请专利(人/公司)信息
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午10:21:47
	 * @param appId 主键编号
	 * @return
	 * @throws WEBException
	 */
	ApplyInfoTb getEntityById(Integer appId) throws WEBException;
	
	/**
	 * 更新指定申请专利(人/公司)账号的登录信息
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午10:22:07
	 * @param id
	 * @param lastLoginDate  最后登录时间
	 * @param userLoginTimes 登录次数
	 * @return
	 * @throws WEBException
	 */
	boolean updateAppLoginInfoById(Integer id,String lastLoginDate,Integer userLoginTimes) throws WEBException;
	
	/**
	 * 根据账号获取申请人/公司信息
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午11:13:46
	 * @param account 账号
	 * @return
	 * @throws WEBException
	 */
	List<ApplyInfoTb> listInfoByAccount(String account) throws WEBException;
	
	/**
	 * 根据主键编号修改用户密码
	 * @author Administrator
	 * @date 2018-7-31 下午09:03:32
	 * @ModifiedBy
	 * @param userId 主键编号
	 * @param newPass 新密码
	 * @return
	 * @throws WEBException
	 */
	boolean updatePassById(Integer userId,String newPass) throws WEBException;
}
