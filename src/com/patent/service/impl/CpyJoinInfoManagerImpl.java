package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyJoinInfoTb;
import com.patent.service.CpyJoinInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CpyJoinInfoManagerImpl implements CpyJoinInfoManager{

	CpyInfoDao cDao = null;
	Transaction tran = null;
	@Override
	public Integer addCJ(Integer applyCpyId, Integer parCpyId,
			Integer subCpyId, String parCpyName, String subCpyName,
			Integer joinStatus, String applyContent, String applyDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加子/主公司合并申请记录时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
		return null;
	}

	@Override
	public boolean updateCJById(Integer id, Integer joinStatus, String czDate,
			String czContent) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delCJById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CpyJoinInfoTb> listInfoByOpt(Integer parCpyId,
			Integer subCpyId, Integer joinStatus, Integer applyCpyId,
			String czDate) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

}
