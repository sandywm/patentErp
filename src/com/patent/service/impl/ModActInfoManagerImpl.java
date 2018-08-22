package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ModActInfoDao;
import com.patent.dao.ModuleInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ModActInfoTb;
import com.patent.service.ModActInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ModActInfoManagerImpl implements ModActInfoManager{

	ModuleInfoDao mDao = null;
	ModActInfoDao maDao = null;
	Transaction tran = null;
	@Override
	public Integer addMAct(String actNameChi, String actNameEng,
			Integer orderNo, Integer modId) throws WEBException {
		// TODO Auto-generated method stub.
		try {
			mDao = (ModuleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MODULE_INFO);
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ModActInfoTb ma = new ModActInfoTb(mDao.get(sess, modId),actNameChi,actNameEng,orderNo);
			maDao.save(sess, ma);
			tran.commit();
			return ma.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加指定模块的模块动作时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ModActInfoTb> listInfoByModId(Integer modId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			return maDao.findSpecInfo(sess, modId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("列出指定模块的动作列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ModActInfoTb> listSpecInfoByOpt(Integer modId,
			String actNameChi, String actNameEng) throws WEBException {
		// TODO Auto-generated method stub
		try {
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			return maDao.findSpecInfoByOpt(sess, modId, actNameChi, actNameEng);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("列出指定模块下指定模块动作的模块动作列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delMActById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			maDao.delete(sess, id);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除指定模块动作编号的模块动作时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean upMActById(Integer id, String actNameChi, String actNameEng,
			Integer orderNo) throws WEBException {
		// TODO Auto-generated method stub
		try {
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ModActInfoTb ma = maDao.get(sess, id);
			if(ma != null){
				if(!actNameChi.equals("")){
					ma.setActNameChi(actNameChi);
				}
				if(!actNameEng.equals("")){
					ma.setActNameEng(actNameEng);
				}
				if(!orderNo.equals(-1)){
					ma.setOrderNo(orderNo);
				}
				maDao.update(sess, ma);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定模块动作编号的模块动作时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ModActInfoTb> listSpecInfoById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			return maDao.findSpecInfoById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("列出指定模块动作编号下的信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
