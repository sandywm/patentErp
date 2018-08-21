package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ZlajEwyqInfoTb;

public interface ZlajEwyqInfoManager {

	/**
	*  增加额外要求
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:27:49
	*  @param yqContent 额外要求
	*  @param yqType 类型
	*  @return
	*  @throws WEBException
	 */
	Integer addYq(String yqContent,String yqType) throws WEBException;
	
	/**
	*  修改指定的额外要求
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:32:41
	*  @param id
	*  @param yqContent 额外要求（""不修改）
	*  @param yqType 类型（""不修改）
	*  @return
	*  @throws WEBException
	 */
	boolean updateYq(Integer id,String yqContent,String yqType) throws WEBException;
	
	/**
	*  删除指定的额外要求
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:33:20
	*  @param id
	*  @return
	*  @throws WEBException
	 */
	boolean delYq(Integer id) throws WEBException;
	
	/**
	*  根据类型获取额外要求
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:33:26
	*  @param yqType 额外要求(""时表示全部)
	*  @return
	*  @throws WEBException
	 */
	List<ZlajEwyqInfoTb> listInfoByType(String yqType) throws WEBException;
	
	/**
	*  根据要求获取额外信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:33:32
	*  @param yqContent 要求内容
	*  @return
	*  @throws WEBException
	 */
	List<ZlajEwyqInfoTb> listInfoByCnt(String yqContent) throws WEBException;
	
	/**
	*  根据主键获取额外要求实体信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:33:39
	*  @param id
	*  @return
	*  @throws WEBException
	 */
	ZlajEwyqInfoTb getEntityById(Integer id) throws WEBException;
}
