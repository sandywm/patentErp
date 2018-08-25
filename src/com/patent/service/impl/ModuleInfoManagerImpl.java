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
			Integer orderNo, Integer showStatus,Integer modLevel,String actNameEng) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ModuleInfoTb mod = new ModuleInfoTb(modName,modPic,resUrl,orderNo,showStatus,modLevel,actNameEng);
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
	public List<ModuleInfoTb> listInfoByLevel(Integer modLevel,Integer showStatus)throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			return mDao.findInfoByLevel(sess, modLevel,showStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定模块级别及其以下的模块列表信息时出现异常!");
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

	@Override
	public boolean upModule(Integer id, String modName, String modPic,
			String resUrl, Integer orderNo, Integer showStatus, Integer modLevel,String actNameEng)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ModuleInfoTb mod = mDao.get(sess, id);
			if(mod != null){
				mod.setModName(modName);
				mod.setModPic(modPic);
				mod.setResUrl(resUrl);
				mod.setOrderNo(orderNo);
				mod.setModLevel(modLevel);
				mod.setShowStatus(showStatus);
				mod.setActNameEng(actNameEng);
				mDao.update(sess, mod);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定模块信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delModuleById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			mDao.delete(sess, id);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除指定模块信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ModuleInfoTb getEntityById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			return mDao.getEntityById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定模块的信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ModuleInfoTb> listMaxOrderInfo() throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			Session sess = HibernateUtil.currentSession();
			return mDao.findMaxOrderInfo(sess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取当前最大的排序号的模块信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
