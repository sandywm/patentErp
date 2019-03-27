package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ZlajQrhDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajQrh;
import com.patent.service.ZlajQrhManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajQrhManagerImpl implements ZlajQrhManager{

	ZlajQrhDao qrhDao = null;
	Transaction tran = null;
	@Override
	public Integer addQrh(String zipName, String zipPath, Integer createUserId,
			String createUserName, String createTime, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			qrhDao = (ZlajQrhDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_QRH_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajQrh qrh = new ZlajQrh(zipName, zipPath, createUserId,createUserName, createTime,cpyId);
			qrhDao.save(sess, qrh);
			tran.commit();
			return qrh.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加确认函包时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajQrh getEntityInfoById(Integer qrhId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			qrhDao = (ZlajQrhDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_QRH_INFO);
			Session sess = HibernateUtil.currentSession();
			return qrhDao.get(sess, qrhId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定的确认函包时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCount(Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			qrhDao = (ZlajQrhDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_QRH_INFO);
			Session sess = HibernateUtil.currentSession();
			return qrhDao.getCount(sess, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定代理机构下生成的确认函包记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajQrh> listPageInfo(Integer cpyId, Integer pageNo,
			Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		try {
			qrhDao = (ZlajQrhDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_QRH_INFO);
			Session sess = HibernateUtil.currentSession();
			return qrhDao.findPageInfo(sess, cpyId, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("分页获取指定代理机构下生成的确认函包时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
