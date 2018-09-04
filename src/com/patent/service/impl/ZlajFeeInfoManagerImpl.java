package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import com.patent.dao.FeeTypeInfoDao;
import com.patent.dao.ZlajFeeInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.FeeTypeInfoTb;
import com.patent.module.ZlajFeeInfoTb;
import com.patent.service.ZlajFeeInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajFeeInfoManagerImpl implements ZlajFeeInfoManager{

	FeeTypeInfoDao ftDao = null;
	ZlajFeeInfoDao fDao = null;
	@Override
	public List<ZlajFeeInfoTb> listInfoByOpt(Integer zlId, String feeTypeStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findInfoByOpt(sess, zlId, feeTypeStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取费用信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajFeeInfoTb getFeeEntityById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.getFeeEntityById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取费用信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<FeeTypeInfoTb> listInfoByStatus(String feeStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			ftDao = (FeeTypeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_TYPE_INFO);
			Session sess = HibernateUtil.currentSession();
			return ftDao.findInfoByStatus(sess, feeStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据费用标记获取费用类型信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
