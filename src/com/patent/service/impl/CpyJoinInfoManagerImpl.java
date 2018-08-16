package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyJoinInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyInfoTb;
import com.patent.module.CpyJoinInfoTb;
import com.patent.service.CpyJoinInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CpyJoinInfoManagerImpl implements CpyJoinInfoManager{

	CpyInfoDao cDao = null;
	CpyJoinInfoDao cjDao = null;
	Transaction tran = null;
	@Override
	public Integer addCJ(Integer applyCpyId, Integer parCpyId,
			Integer subCpyId, String parCpyName, String subCpyName,
			Integer joinStatus, String applyContent, String applyDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			cjDao = (CpyJoinInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_JOIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyJoinInfoTb cj = new CpyJoinInfoTb(cDao.get(sess, applyCpyId), parCpyId,
					subCpyId, parCpyName, subCpyName,
					joinStatus, applyContent, applyDate,"","");
			cjDao.save(sess, cj);
			tran.commit();
			return cj.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加子/主公司合并申请记录时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateCJById(Integer id, Integer joinStatus, String czDate,
			String czContent) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			cjDao = (CpyJoinInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_JOIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyJoinInfoTb cj = cjDao.get(sess, id);
			if(cj != null){
				cj.setJoinStatus(joinStatus);
				cj.setCzDate(czDate);
				cj.setCzContent(czContent);
				cjDao.update(sess, cj);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定合并申请记录的合并状态、操作时间、操作内容时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delCJById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cjDao = (CpyJoinInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_JOIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			cjDao.delete(sess, id);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除指定主键的合并申请记录（在没处理之前可以删除）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CpyJoinInfoTb> listInfoByOpt(Integer parCpyId,
			Integer subCpyId, Integer joinStatus, Integer applyCpyId,
			String czDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cjDao = (CpyJoinInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_JOIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return cjDao.findInfoByOpt(sess, parCpyId, subCpyId, joinStatus, applyCpyId, czDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除指定主键的合并申请记录（在没处理之前可以删除）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
