package com.patent.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajMainInfoTb;
import com.patent.service.ZlajMainInfoManager;
import com.patent.util.Constants;

public class ZlajMainInfoManagerImpl implements ZlajMainInfoManager{

	ZlajMainInfoDao zlDao = null;
	CpyInfoDao cDao = null;
	Transaction tran = null;
	@Override
	public Integer addZL(String ajNo, String ajNoQt, String ajNoGf,
			String ajTitle, Integer ajType, String ajFieldId, String ajSqrId,
			String ajFmrId, String ajLxrId, String ajSqAddress, String ajYxqId,
			String ajUpload, String ajRemark, String ajEwyqId,
			Date ajApplyDate, String ajStatus, Integer cpyId, String addDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ZlajMainInfoTb> listPageInfoByOpt(Integer cpyId,
			Integer stopStatus, String sqAddress, String ajNoQt, String zlNo,
			String ajTitle, String ajType, String lxr, String sDate,
			String eDate, Integer pageNo, Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByOpt(Integer cpyId, Integer stopStatus,
			String sqAddress, String ajNoQt, String zlNo, String ajTitle,
			String ajType, String lxr, String sDate, String eDate)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZlajMainInfoTb> listSpecInfoById(Integer id, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZlajMainInfoTb> listFirstInfoByCpyId(Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZlajMainInfoTb> listSpecInfoByOpt(String ajNoGf,
			String sqAddress, String sqDate) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

}
