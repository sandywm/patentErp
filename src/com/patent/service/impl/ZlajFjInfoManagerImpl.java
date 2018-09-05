package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.ZlajFjInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajFjInfoTb;
import com.patent.service.ZlajFjInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajFjInfoManagerImpl implements ZlajFjInfoManager {

	CpyUserInfoDao uDao = null;
	ZlajMainInfoDao zlDao = null;
	ZlajFjInfoDao fjDao = null;
	Transaction tran = null;
	@Override
	public Integer addFj(Integer ajId, String fjName, String fjVersion,
			String fjType, String fjGs, String fjDx, Integer upUserId,
			String upDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			fjDao = (ZlajFjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FJ_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFjInfoTb fj = new ZlajFjInfoTb(uDao.get(sess, upUserId),zlDao.get(sess, ajId),fjName, fjVersion, fjType, fjGs,fjDx, upDate);
			fjDao.save(sess, fj);
			tran.commit();
			return fj.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加专利附件时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFjInfoTb> listInfoByAjId(Integer ajId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fjDao = (ZlajFjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return fjDao.findInfoByAjId(sess, ajId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号获取专利附件时（id降序排列）出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFjInfoTb> listLastInfoByAjId(Integer ajId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fjDao = (ZlajFjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return fjDao.findLastInfoByAjId(sess, ajId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号获取第一条专利附件时（id降序排列）出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
