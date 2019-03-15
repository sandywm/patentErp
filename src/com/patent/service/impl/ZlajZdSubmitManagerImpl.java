package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.dao.ZlajQqsInfoDao;
import com.patent.dao.ZlajZdSubmitDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajZdSubmitTb;
import com.patent.service.ZlajZdSubmitManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajZdSubmitManagerImpl implements ZlajZdSubmitManager{

	CpyUserInfoDao uDao = null;
	ZlajMainInfoDao zlDao = null;
	ZlajQqsInfoDao qqsDao = null;
	CpyInfoDao cDao = null;
	ZlajZdSubmitDao zzsDao = null;
	Transaction tran = null;
	@Override
	public Integer addZZS(Integer qqsId, Integer userId, Integer zlId,
			String qqsFjPath, String uploadDate, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			qqsDao = (ZlajQqsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_QQS_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			zzsDao = (ZlajZdSubmitDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_ZD_SUBMIT_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajZdSubmitTb zzs = new ZlajZdSubmitTb(qqsDao.get(sess, qqsId), uDao.get(sess, userId),
					zlDao.get(sess, zlId), qqsFjPath, uploadDate,
					cDao.get(sess, cpyId), 1);
			zzsDao.save(sess, zzs);
			tran.commit();
			return zzs.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加主动提交时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajZdSubmitTb> listPageInfoByOpt(Integer cpyId,
			String zlTitle, String zlNo, Integer signStatus, Integer pageNo,
			Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zzsDao = (ZlajZdSubmitDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_ZD_SUBMIT_INFO);
			Session sess = HibernateUtil.currentSession();
			return zzsDao.findPageInfoByOpt(sess, cpyId, zlTitle, zlNo, signStatus, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定条件下的主动提交记录列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer cpyId, String zlTitle, String zlNo,
			Integer signStatus) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zzsDao = (ZlajZdSubmitDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_ZD_SUBMIT_INFO);
			Session sess = HibernateUtil.currentSession();
			return zzsDao.getCountByOpt(sess, cpyId, zlTitle, zlNo, signStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定条件下的主动提交记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajZdSubmitTb> listInfoByZlNo(Integer cpyId, String zlNo,
			String qqsName, Integer signStatus)  throws WEBException {
		// TODO Auto-generated method stub
		try {
			zzsDao = (ZlajZdSubmitDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_ZD_SUBMIT_INFO);
			Session sess = HibernateUtil.currentSession();
			return zzsDao.findInfoByZlNo(sess, cpyId, zlNo, qqsName, signStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利号、请求书名称、签收状态获取主动提交信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
