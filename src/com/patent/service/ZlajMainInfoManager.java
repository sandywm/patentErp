package com.patent.service;

import java.util.Date;
import java.util.List;

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
	 * @param ajSqrId 申请人
	 * @param ajFmrId 发明人
	 * @param ajLxrId 联系人
	 * @param ajSqAddress 申请地区
	 * @param ajYxqDetail 优先权
	 * @param ajUpload 案件上传底稿
	 * @param ajRemark 案件备注
	 * @param ajEwyqId 额外要求
	 * @param ajApplyDate 申请日期
	 * @param ajStatus 案件状态
	 * @param pubZlId 发布案件的编号
	 * @param checkUserId 审查人员编号
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer addZL(String ajNo, String ajNoQt,String ajNoGf,
			String ajTitle, String ajType, String ajFieldId, String ajSqrId,
			String ajFmrId, String ajLxrId, String ajSqAddress, String ajYxqDetail,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus,Integer pubZlId,Integer checkUserId,Integer cpyId) throws WEBException;
	
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
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<ZlajMainInfoTb> listPageInfoByOpt(Integer cpyId,Integer stopStatus, String sqAddress, String ajNoQt, String zlNo,
			String ajTitle, String ajType, String lxr, String sDate,
			String eDate, Integer pageNo, Integer pageSize) throws WEBException;
	
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
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer cpyId,Integer stopStatus, String sqAddress, String ajNoQt, String zlNo,
			String ajTitle, String ajType, String lxr, String sDate,String eDate)  throws WEBException;
	
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
	 * @param sqAddress 申请地区
	 * @param sqDate 申请日期
	 * @return
	 * @throws WEBException
	 */
	List<ZlajMainInfoTb> listSpecInfoByOpt(String ajNoGf,String sqAddress, String sqDate) throws WEBException;
	
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
}
