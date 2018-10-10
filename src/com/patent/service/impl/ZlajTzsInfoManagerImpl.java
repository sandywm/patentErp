package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ZlajMainInfoDao;
import com.patent.dao.ZlajTzsInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajTzsInfoTb;
import com.patent.service.ZlajTzsInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajTzsInfoManagerImpl implements ZlajTzsInfoManager{

	ZlajMainInfoDao zlDao = null;
	ZlajTzsInfoDao tzsDao = null;
	Transaction tran = null;
	
	@Override
	public Integer addTzs(Integer zlId, String tzsName, String fwrDate,
			String gfrDate,String fwSerial,String tzsPath) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajTzsInfoTb tzs = new ZlajTzsInfoTb(zlDao.get(sess, zlId),tzsName,fwrDate,gfrDate,fwSerial,tzsPath);
			tzsDao.save(sess, tzs);
			tran.commit();
			return tzs.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加专利通知书时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajTzsInfoTb> listInfoByZlId(Integer zlId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.findInfoByAjId(sess, zlId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据案件编号获取专利通知书列表信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajTzsInfoTb getEntityById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.getEntityById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据通知书主键编号获取专利通知书详细信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajTzsInfoTb> listInfoByOpt(Integer zlId, String fwSerial)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.findInfoByOpt(sess, zlId, fwSerial);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号、通知书发文序号获取通知书信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
