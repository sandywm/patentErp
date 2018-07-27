package com.patent.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyInfoTb;
import com.patent.service.CpyInfoManager;
import com.patent.tools.CurrentTime;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CpyInfoManagerImpl implements CpyInfoManager{

	CpyInfoDao cDao = null;
	Transaction tran = null;
	
	@Override
	public Integer addCpy(String cpyName, String cpyAddress, String cpyProv,
			String cpyCity, String cpyFr, String cpyLxr, String lxrTel)
			throws WEBException {
		// TODO Auto-generated method stub
		
		try {
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyInfoTb cpy = new CpyInfoTb(cpyName, cpyAddress, cpyProv,cpyCity, cpyFr, "", cpyLxr,
					lxrTel, "0", 0, CurrentTime.stringToDate_1(CurrentTime.getStringDate()),
					CurrentTime.stringToDate_1(CurrentTime.getFinalDate(Constants.freeDays)), 0, 0);		
			cDao.save(sess, cpy);
			tran.commit();
			return cpy.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("增加代理机构信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateBasicCpyInfoById(Integer id, String cpyAddress,
			String cpyProv, String cpyCity, String cpyFr, String cpyLxr,
			String lxrTel, String lxrEmail, String cpyUrl, String cpyProfile)
			throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateJoinInfoById(Integer id, Integer cpyParId,
			Integer cpySubId) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCpyInfoById(Integer id, Date endDate,
			Integer hotStatus, Integer cpyLevel) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CpyInfoTb> listPageInfoByOpt(String cpyName, String cpyProv,
			String cpyCity, String cpyFr, String cpyLxr, Integer cpyLevel,
			Integer yxStatus, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByOpt(String cpyName, String cpyProv,
			String cpyCity, String cpyFr, String cpyLxr, Integer cpyLevel,
			Integer yxStatus) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

}
