package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ModuleInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ModuleInfoTb;
import com.patent.service.ModuleInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ModuleInfoManagerImpl implements ModuleInfoManager{

	ModuleInfoDao mDao = null;
	Transaction tran = null;
	@Override
	public Integer addModule(String modName, String modPic, String resUrl,
			Integer orderNo, Integer modLevel) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ModuleInfoTb mod = new ModuleInfoTb(modName,modPic,resUrl,orderNo,modLevel);
			mDao.save(sess, mod);
			tran.commit();
			return mod.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加模块信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ModuleInfoTb> listInfoByLevel(Integer modLevel)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			return mDao.findInfoByLevel(sess, modLevel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据模块级别获取模块列表信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ModuleInfoTb> listInfoByName(String modName)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			return mDao.findInfoByName(sess, modName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据模块名字获取模块信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
