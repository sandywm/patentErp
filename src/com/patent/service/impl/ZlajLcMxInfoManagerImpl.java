package com.patent.service.impl;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ZlajLcMxInfoTb;
import com.patent.service.ZlajLcMxInfoManager;

public class ZlajLcMxInfoManagerImpl implements ZlajLcMxInfoManager{

	@Override
	public Integer addLcMx(Integer lcId, Integer fzUserId, String lcMxName,
			Double lcMxNo, String lcMxSDate, String lcMxEDate,
			String lcMxUpFile, Integer lcMxUpUserId, String lcMxUpDate,
			String lcMxUpSize, String lcMxRemark) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateEdateById(Integer id, String eDate, String lcMxRemark)
			throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ZlajLcMxInfoTb> listDetailInfoByLcId(Integer lcId)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZlajLcMxInfoTb> listLastInfoByLcId(Integer lcId)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

}
