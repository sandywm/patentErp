package com.patent.service;

import java.util.List;

import org.hibernate.Session;

import com.patent.exception.WEBException;
import com.patent.module.ZlajMainInfoTb;

public interface ZlajMainInfoManager {

	/**
	 * 增加专利
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-24 下午10:20:49
	 * @param ajNo 案件编号（系统定义）
	 * @param ajNoQt 专利/申请号
	 * @param ajNoGf 案件编号（前台显示）
	 * @param ajTitle 案件标题
	 * @param ajType 案件类型
	 * @param ajFieldId 案件技术领域
	 * @param ajSqrId 申请人编号
	 * @param ajSqr 申请人姓名
	 * @param ajFmrId 发明人
	 * @param ajLxrId 联系人
	 * @param ajFjInfo 案件费减明细
	 * @param ajSqAddress 申请地区
	 * @param ajYxqDetail 优先权
	 * @param ajUpload 案件上传底稿
	 * @param ajRemark 案件备注
	 * @param ajEwyqId 额外要求
	 * @param ajApplyDate 申请日期
	 * @param ajStatus 案件状态
	 * @param ajStatusChi 下个案件节点
	 * @param pubZlId 发布案件的编号
	 * @param checkUserId 审查人员编号
	 * @param cpyId 代理机构编号
	 * @param zxUserId 撰写人员编号
	 * @param tjUserId 定稿提交人员
	 * @param tzsUserId 通知书导入人员
	 * @param feeUserId 费用催缴人员
	 * @param bzUserId 案件补正人员
	 * @param bzshUserId 案件补正审核人员
	 * @param bhUserId 案件驳回人员
	 * @param ajAddUserId 案件录入人员
	 * @return
	 * @throws WEBException
	 */
	Integer addZL(String ajNo, String ajNoQt,String ajNoGf,
			String ajTitle, String ajType, String ajFieldId, String ajSqrId,String ajSqrName,
			String ajFmrId, String ajLxrId, Double ajFjInfo,String ajSqAddress, String ajYxqDetail,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus,String ajStatusChi,Integer pubZlId,Integer checkUserId,Integer zxUserId,
			Integer tjUserId,Integer tzsUserId,Integer feeUserId,Integer bzUserId,Integer bzshUserId,Integer bhUserId,Integer cpyId,Integer ajAddUserId) throws WEBException;
	
	/**
	 * 根据条件分页获取专利列表(ID降序)
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-24 下午10:20:56
	 * @param cpyId 代理机构编号（0时表示全部）
	 * @param stopStatus 案件终止状态（-1时表示全部）
	 * @param sqAddress 申请地区（""时表示全部）
	 * @param ajNoQt 案件编号（""时表示全部）
	 * @param zlNo 专利号/申请号（""时表示全部）
	 * @param ajTitle 案件标题（""时表示全部）
	 * @param ajType 案件类型（""时表示全部）
	 * @param lxr 联系人（""时表示全部）
	 * @param sDate 开始日期（""时表示全部）
	 * @param eDate 结束日期（""时表示全部）
	 * @param lqStatus 任务条件（0：流程任务分配，1：专利任务，2：撰写任务领取）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<ZlajMainInfoTb> listPageInfoByOpt(Integer cpyId,Integer stopStatus, String sqAddress, String ajNoQt, String zlNo,
			String ajTitle, String ajType, String lxr, String sDate,
			String eDate, Integer lqStatus, Integer pageNo, Integer pageSize) throws WEBException;
	
	/**
	 * 根据条件获取专利记录条数
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-24 下午10:21:13
	 * @param cpyId 代理机构编号（0时表示全部）
	 * @param stopStatus 案件终止状态（-1时表示全部）
	 * @param sqAddress 申请地区（""时表示全部）
	 * @param ajNoQt 案件编号（""时表示全部）
	 * @param zlNo 专利号/申请号（""时表示全部）
	 * @param ajTitle 案件标题（""时表示全部）
	 * @param ajType 案件类型（""时表示全部）
	 * @param lxr 联系人（""时表示全部）
	 * @param sDate 开始日期（""时表示全部）
	 * @param eDate 结束日期（""时表示全部）
	 * @param lqStatus 任务条件（0：流程任务分配，1：专利任务，2：撰写任务领取）
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer cpyId,Integer stopStatus, String sqAddress, String ajNoQt, String zlNo,
			String ajTitle, String ajType, String lxr, String sDate,String eDate,Integer lqStatus)  throws WEBException;
	
	/**
	 * 根据主键获取专利信息
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-24 下午10:21:20
	 * @param id
	 * @param cpyId 0时表示全部（当平台用户浏览时）
	 * @return
	 * @throws WEBException
	 */
	List<ZlajMainInfoTb> listSpecInfoById(Integer id,Integer cpyId) throws WEBException;
	
	/**
	 * 获取第一条记录（按照案件编号降序排列）获取即将增加的案件号
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-24 下午10:21:24
	 * @param cpyId 代理机构编号
	 * @param ajType 专利类型
	 * @param currYear 当前年份
	 * @return
	 * @throws WEBException
	 */
	List<ZlajMainInfoTb> listFirstInfoByOpt(Integer cpyId,String ajType,String currYear) throws WEBException;
	
	/**
	 * 根据条件全网获取案件专利（填写优先权时使用）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-24 下午10:21:30
	 * @param ajNoGf 专利/申请号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajMainInfoTb> listSpecInfoByZlNo(String ajNoGf) throws WEBException;
	
	/**
	 * 根据主键修改案件终止状态信息
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-8-27 下午09:34:06
	 * @param zlId 主键
	 * @param stopStatus 终止状态
	 * @param stopDate 终止时间
	 * @param stopUser 终止人员
	 * @param userType 人员类型
	 * @return
	 * @throws WEBException
	 */
	boolean updateStopStatusById(Integer zlId,Integer stopStatus,String stopDate,String stopUser,String userType) throws WEBException;
	
	/**
	 * 根据主键修改案件基本信息
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午08:18:28
	 * @param zlId 主键
	 * @param zlTitle 案件标题
	 * @param zlNo 案件编号(年专利类型[01,02,03]000x机构编号)构成(排序用)(""不修改)
	 * @param zlNoQt 案件编号(年专利类型[01,02,03]000x.机构编号)构成(前台显示用)(""不修改)
	 * @param sqAddress 案件地区
	 * @param zlType 案件类型
	 * @param ajFieldId 案件技术领域
	 * @param sqrId 申请人编号
	 * @param sqrName 申请人姓名
	 * @param fmrId 发明人
	 * @param lxrId 联系人
	 * @param ajFjInfo 案件费减明细
	 * @param yxqDetail 优先权明细
	 * @param upFile 技术底稿
	 * @param remark 备注
	 * @param ewyq 额外要求
	 * @param applyDate 申请日期
	 * @param faId 分案编号（0不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateBasicInfoById(Integer zlId,String zlTitle,String zlNo,String zlNoQt,Integer pubId, String sqAddress,String zlType,String ajFieldId,
			String sqrId,String sqrName,String fmrId,String lxrId,Double ajFjInfo,String yxqDetail,String upFile,String remark,String ewyq,String applyDate,Integer faId) throws WEBException;
	
	/**
	 * 更新专利申请/专利号
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午08:20:25
	 * @param zlId
	 * @param ajNoGf
	 * @return
	 * @throws WEBException
	 */
	boolean updateAjNoGfById(Integer zlId,String ajNoGf) throws WEBException;
	
	/**
	 * 根据主键修改专利环节负责人员
	 * @description
	 * @author wm
	 * @date 2018-9-6 上午08:24:54
	 * @param zlId 专利编号
	 * @param checkUserId 审查人员编号（-1不修改）
	 * @param zxUserId 撰写人员编号（-1不修改）
	 * @param tjUserId 定稿提交人员（-1不修改）
	 * @param tzsUserId 通知书导入人员（-1不修改）
	 * @param feeUserId 费用催缴人员（-1不修改）
	 * @param bzUserId 案件补正人员（-1不修改）
	 * @param bzshUserId 案件补正审核人员（-1不修改）
	 * @param bhUserId 案件驳回人员（-1不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateOperatorUserInfoByZlId(Integer zlId,Integer checkUserId,Integer zxUserId,
			Integer tjUserId,Integer tzsUserId,Integer feeUserId,Integer bzUserId,Integer bzshUserId,Integer bhUserId) throws WEBException;
	
	/**
	 * 修改专利状态
	 * @description
	 * @author wm
	 * @date 2018-9-6 上午09:06:32
	 * @param zlStatus 专利状态
	 * @param ajStatusChi 下个案件节点
	 * @return
	 * @throws WEBException
	 */
	boolean updateZlStatusById(Integer id,String zlStatus,String ajStatusChi) throws WEBException;
	
	/**
	 * 修改专利底稿路径
	 * @description
	 * @author wm
	 * @date 2018-9-15 下午05:08:30
	 * @param id
	 * @param zlUpFile 专利底稿路径
	 * @return
	 * @throws WEBException
	 */
	boolean updateZlUpFile_dg(Integer id,String zlUpFile) throws WEBException;
	
	/**
	 * 根据主键修改专利的最终标题、申请人、发明人、联系人、费减
	 * @description
	 * @author Administrator
	 * @date 2018-9-21 上午10:59:35
	 * @param id 主键
	 * @param zlTitle 专利标题（""不修改）
	 * @param sqrId 申请人（""不修改）
	 * @param sqrName 申请人（""不修改）
	 * @param fmrId 发明人（""不修改）
	 * @param lxrId 联系人（""不修改）
	 * @param ajFjInfo 费减（-1.0时不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateBasicInfoById(Integer id,String zlTitle,String sqrId,String sqrName,String fmrId,String lxrId,Double ajFjInfo) throws WEBException;
	
	/**
	 * 根据专利标题、专利申请人、专利类型获取专利（一般在导入受理通知书书时使用）
	 * @description
	 * @author Administrator
	 * @date 2018-9-23 上午09:39:30
	 * @param sess
	 * @param zlTitle 专利名称
	 * @param sqrName 专利申请人
	 * @param zlType 专利类型
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajMainInfoTb> listSpecInfoByOpt(String zlTitle,String sqrName, String zlType,Integer cpyId) throws WEBException;
	
	/**
	 * 修改专利申请日(导入受理通知书时)
	 * @description
	 * @author Administrator
	 * @date 2018-9-28 上午09:20:52
	 * @param zlId 主键
	 * @param applyDate 申请日
	 * @return
	 * @throws WEBException
	 */
	boolean updateZlApplyDate(Integer zlId,String applyDate) throws WEBException;
	
	/**
	 * 修改专利费减详情(发明专利有效)
	 * @description
	 * @author Administrator
	 * @date 2018-9-28 上午10:44:03
	 * @param zlId 专利编号
	 * @param fjRate 费减详情
	 * @return
	 * @throws WEBException
	 */
	boolean updateZlFjInfo(Integer zlId,Double fjRate) throws WEBException;
}
