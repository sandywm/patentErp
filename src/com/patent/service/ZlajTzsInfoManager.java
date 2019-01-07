package com.patent.service;

import java.util.List;

import org.hibernate.Session;

import com.patent.exception.WEBException;
import com.patent.module.TzsApplyFileInfo;
import com.patent.module.ZlajTzsInfoTb;

public interface ZlajTzsInfoManager {

	/**
	 * 增加专利通知书信息
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午10:25:26
	 * @param zlId 专利编号
	 * @param ajNo 专利号
	 * @param tzsName 通知书名称
	 * @param fwrDate 发文日
	 * @param gfrDate 官方绝限日
	 * @param fwSerial 发文序号
	 * @param tzsPath 通知书上传路径
	 * @param uploadUserId 上传人
	 * @param readStatus 读取状态（0：失败，1：成功）
	 * @param readDetail 读取详情
	 * @param cpyId 代理机构编号
	 * @param tzsType 通知书类型(tzs--通知书,sqd--申请单)
	 * @return
	 * @throws WEBException
	 */
	Integer addTzs(Integer zlId,String ajNo,String tzsName,String fwrDate,String gfrDate,String fwSerial,String tzsPath,
			Integer uploadUserId,Integer readStatus,String readDetail,Integer cpyId,String tzsType) throws WEBException;
	
	/**
	 * 根据案件编号获取所有的通知书信息列表(读取成功的)
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午10:25:57
	 * @param zlId 专利编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajTzsInfoTb> listInfoByZlId(Integer zlId) throws WEBException;
	
	/**
	 * 根据主键获取通知书信息详情
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午10:43:26
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	ZlajTzsInfoTb getEntityById(Integer id) throws WEBException;
	
	/**
	 * 根据专利编号、通知书发文序号获取读取成功的通知书信息列表(读取成功的)
	 * @description
	 * @author Administrator
	 * @date 2018-10-9 上午09:53:53
	 * @param zlId 专利编号
	 * @param fwSerial 发文序号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajTzsInfoTb> listInfoByOpt(Integer zlId,String fwSerial) throws WEBException;
	
	/**
	 * 根据条件分页获取读取专利通知书列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-3 下午05:05:26
	 * @param cpyId 代理机构编号
	 * @param zlId 专利编号（0表示全部）
	 * @param ajNo 专利号（""表示全部）
	 * @param readStatus 读取状态（2：表示全部，1：读取成功，0:读取失败）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<ZlajTzsInfoTb> listPageInfoByOpt(Integer cpyId,Integer zlId,String ajNo,Integer readStatus,Integer pageNo,Integer pageSize) throws WEBException;
	
	/**
	 * 根据条件获取读取专利通知书记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-12-3 下午05:05:29
	 * @param cpyId 代理机构编号
	 * @param zlId 专利编号（0表示全部）
	 * @param ajNo 专利号（""表示全部）
	 * @param readStatus 读取状态（2：表示全部，1：读取成功，0:读取失败）
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer cpyId,Integer zlId,String ajNo,Integer readStatus) throws WEBException;
	
	/**
	 * 根据通知书编号获取申请文件列表（电子申请回单时使用）
	 * @description
	 * @author Administrator
	 * @date 2019-1-5 上午11:05:34
	 * @param tzsId 通知书编号
	 */
	List<TzsApplyFileInfo> listInfoByTzsId(Integer tzsId)throws WEBException;
	
	/**
	 * 给电子回单通知书增加申请文件信息
	 * @description
	 * @author Administrator
	 * @date 2019-1-5 上午11:11:17
	 * @param tzsId 通知书编号
	 * @param fileName 文件名称
	 * @param fileType 文件类型
	 * @param fileSize 文件大小
	 * @return
	 * @throws WEBException
	 */
	Integer addAF(Integer tzsId,String fileName,String fileType,String fileSize)throws WEBException;
}
