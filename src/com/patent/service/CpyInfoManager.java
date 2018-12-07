package com.patent.service;

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
			String cpyUrl, String cpyProfile, String signDate, String endDate,
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
	 * 当cpyParId大于0时，cpySubId必须为0，id表示的是子公司的编号--增加子公司的主公司编号
	 * 当cpyParId等于0时，cpySubId必须大于0，id表示的是主公司的编号--增加主公司的子公司编号
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
	boolean updateCpyInfoById(Integer id,String endDate,Integer hotStatus,Integer cpyLevel)throws WEBException;
	
	/**
	 * 分页获取代理公司信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:16:07
	 * @param cpyNamePy 代理机构名称拼音(""表示全部)--模糊查询
	 * @param cpyProv 代理机构所在身份(""表示全部)
	 * @param cpyCity 代理机构所在城市(""表示全部)
	 * @param cpyFr 代理机构法人(""表示全部)
	 * @param cpyLxr 代理机构联系人(""表示全部)
	 * @param cpyLevel 代理机构会员级别(-1表示全部)
	 * @param yxStatus 有效状态--会员是否过期(-1[全部],0[未过期],1[已过期])
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<CpyInfoTb> listPageInfoByOpt(String cpyNamePy,String cpyProv, String cpyCity, String cpyFr, String cpyLxr,
			Integer cpyLevel,Integer yxStatus, Integer pageNo, Integer pageSize)throws WEBException;
	
	/**
	 * 根据条件获取代理公司信息记录条数
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:16:37
	 * @param cpyNamePy 代理机构名称拼音(""表示全部)--模糊查询
	 * @param cpyProv 代理机构所在身份(""表示全部)
	 * @param cpyCity 代理机构所在城市(""表示全部)
	 * @param cpyFr 代理机构法人(""表示全部)
	 * @param cpyLxr 代理机构联系人(""表示全部)
	 * @param cpyLevel 代理机构会员级别(-1表示全部)
	 * @param yxStatus 有效状态--会员是否过期(-1[全部],0[未过期],1[已过期])
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(String cpyNamePy,String cpyProv, String cpyCity, String cpyFr, String cpyLxr,
			Integer cpyLevel,Integer yxStatus)throws WEBException;
	
	/**
	 * 获取所有即将到期或者已到期的代理机构（即将到期5、1天内/已到期0,1天内进行邮件提醒）
	 * @description
	 * @author wm
	 * @date 2018-8-6 下午04:57:44
	 * @return
	 * @throws WEBException
	 */
	List<CpyInfoTb> listEndDateCpyInfo()throws WEBException;
	
	/**
	 * 根据代理公司编号、公司类型获取主/子公司信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-14 下午05:01:11
	 * @param cpyIdStr 代理公司编号
	 * @param cpyType 代理公司类型（par-表示子公司查看主公司,sub--表示主公司查看子公司）
	 * @return
	 * @throws WEBException
	 */
	List<CpyInfoTb> listParSubCpyInfo(String cpyIdStr,String cpyType)throws WEBException;
	
	/**
	 * 根据主键获取代理公司信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-16 下午04:31:59
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	List<CpyInfoTb> listInfoById(Integer id)throws WEBException;
	
	/**
	 * 根据主键修改代理机构增加的专利数（newNum为1是增加1，-1时减少1）
	 * @description
	 * @author wm
	 * @date 2018-8-17 下午05:16:16
	 * @param id
	 * @param newNum
	 * @return
	 * @throws WEBException
	 */
	boolean updateZlNumById(Integer id,Integer newNum)throws WEBException;
	
	/**
	 * 根据主键修改代理机构银行信息
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-6 下午11:27:57
	 * @param id 主键
	 * @param bankAccountName 银行账户名
	 * @param bankName 开户行
	 * @param bankNo 账号
	 * @return
	 * @throws WEBException
	 */
	boolean updateCpyBankInfoById(Integer id,String bankAccountName,String bankName,String bankNo)throws WEBException;
	
	/**
	 * 根据主键修改销售提成比例
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-6 下午11:30:21
	 * @param id 主键
	 * @param saleBonus 提成比例
	 * @return
	 * @throws WEBException
	 */
	boolean updateCpySaleBonusById(Integer id,String saleBonus)throws WEBException;
	
	/**
	 * 根据主键修改代理机构代理费
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-6 下午11:31:27
	 * @param id 主键
	 * @param dlFeeFm 发明专利代理费
	 * @param dlFeeXx 实用新型专利代理费
	 * @param dlFeeWg 外观专利代理费
	 * @return
	 * @throws WEBException
	 */
	boolean updateCpyDlFeeById(Integer id,String dlFeeFm,String dlFeeXx,String dlFeeWg)throws WEBException;
	
}
