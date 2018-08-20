package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CustomerFmrInfoDao;
import com.patent.dao.CustomerInfoDao;
import com.patent.dao.CustomerLxrInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyInfoTb;
import com.patent.module.CustomerFmrInfoTb;
import com.patent.module.CustomerInfoTb;
import com.patent.module.CustomerLxrInfoTb;
import com.patent.service.CustomerInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CustomerInfoManagerImpl implements CustomerInfoManager{

	CustomerInfoDao cDao = null;
	CustomerLxrInfoDao clDao = null;
	CustomerFmrInfoDao cfDao = null;
	CpyInfoDao ciDao = null;
	Transaction tran = null;
	@Override
	public Integer addCusInfo(String cusType, String cusName, String cusiCard,
			String cusAddress, String cusZip, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			ciDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CustomerInfoTb cus = new CustomerInfoTb(ciDao.get(sess, cpyId),cusType, cusName, cusiCard,cusAddress, cusZip);
			cDao.save(sess, cus);
			tran.commit();
			return cus.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加代理机构下客户信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delCusInfo(Integer id, Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			List<CustomerInfoTb> cusList = cDao.findInfoByOpt(sess, cpyId, id);
			if(cusList.size() > 0){
				cDao.delete(sess, id);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除指定代理机构下指定客户的信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean upCusInfo(Integer id, Integer cpyId, String cusType,
			String cusName, String cusiCard, String cusAddress, String cusZip)
			throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CustomerInfoTb> listPageInfoByOpt(Integer cpyId,
			String cusName, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByOpt(Integer cpyId, String cusName)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addCusLxrInfo(Integer cusId, String lxrName, String lxrTel,
			String lxrEmail) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean upCusLxrById(Integer lxrId, String lxrName, String lxrTel,
			String lxrEmail) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delCysLxrById(Integer lxrId) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CustomerLxrInfoTb> listLxrInfoByCusId(Integer cusId)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addCusFmrInfo(Integer cusId, String fmrName,
			String fmriCard, String fmrTel, String fmrEmail)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean upCusFmrById(Integer fmrId, String fmrName, String fmriCard,
			String fmrTel, String fmrEmail) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delCusFmrById(Integer fmrId) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CustomerFmrInfoTb> listFmrInfoByCusId(Integer cusId)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

}
