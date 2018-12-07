package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyBonusInfoDao;
import com.patent.dao.CpyInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyBonusInfo;
import com.patent.service.CpyBonusInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CpyBonusInfoManagerImpl implements CpyBonusInfoManager{

	CpyBonusInfoDao cbDao = null;
	CpyInfoDao cDao = null;
	Transaction tran = null;
	@Override
	public Integer addCbInfo(String workType, String zlType, Integer zlLevel,String bonusFee,Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cbDao = (CpyBonusInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_BONUS_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyBonusInfo cb = new CpyBonusInfo(cDao.get(sess, cpyId), zlType, zlLevel,bonusFee, workType);
			cbDao.save(sess, cb);
			tran.commit();
			return cb.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加项目提成信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CpyBonusInfo> listPageInfoByOpt(String workType, String zlType,
			Integer zlLevel, Integer cpyId, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cbDao = (CpyBonusInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_BONUS_INFO);
			Session sess = HibernateUtil.currentSession();
			return cbDao.findPageInfoByOpt(sess, workType, zlType, zlLevel, cpyId, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取提成信息记录列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(String workType, String zlType,
			Integer zlLevel, Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cbDao = (CpyBonusInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_BONUS_INFO);
			Session sess = HibernateUtil.currentSession();
			return cbDao.getCountByOpt(sess, workType, zlType, zlLevel, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取提成信息记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CpyBonusInfo> listInfoByOpt(String workType, String zlType,
			Integer zlLevel, Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cbDao = (CpyBonusInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_BONUS_INFO);
			Session sess = HibernateUtil.currentSession();
			return cbDao.findInfoByOpt(sess, workType, zlType, zlLevel, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取提成信息记录信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateCbInfoById(Integer id, String bonusFee)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cbDao = (CpyBonusInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_BONUS_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyBonusInfo cb = cbDao.get(sess, id);
			if(cb != null){
				cb.setBonusFee(bonusFee);
				cbDao.save(sess, cb);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改项目提成信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public CpyBonusInfo getEntityById(Integer id, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cbDao = (CpyBonusInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_BONUS_INFO);
			Session sess = HibernateUtil.currentSession();
			CpyBonusInfo cb = cbDao.get(sess, id);
			if(cb != null){
				if(cb.getCpyInfoTb().getId().equals(cpyId)){
					return cb;
				}
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取项目提成信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
