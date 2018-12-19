package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.FeeImportDealRecordInfoDao;
import com.patent.dao.FeeImportRecordInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.FeeImportDealRecordInfo;
import com.patent.module.FeeImportRecordInfo;
import com.patent.service.FeeImportRecordInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class FeeImportRecordInfoManagerImpl implements FeeImportRecordInfoManager{

	CpyUserInfoDao uDao = null;
	CpyInfoDao cDao = null;
	FeeImportDealRecordInfoDao fidrDao = null;
	FeeImportRecordInfoDao firDao = null;
	Transaction tran = null;
	@Override
	public Integer addFIR(Integer uploadUserId, Integer cpyId,
			String excelName, String uploadTime, String excelPath)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			firDao = (FeeImportRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_IMPORT_RECORD_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			FeeImportRecordInfo fir = new FeeImportRecordInfo(uDao.get(sess, uploadUserId), cDao.get(sess, cpyId),excelName, uploadTime, excelPath);
			firDao.save(sess, fir);
			tran.commit();
			return fir.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加已缴费用导入信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer addFIDR(Integer firId, String zlNo, String feeName,
			String dealTime, String dealStatus, String dealResult)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			firDao = (FeeImportRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_IMPORT_RECORD_INFO);
			fidrDao = (FeeImportDealRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_IMPORT_DEAL_RECORD_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			FeeImportDealRecordInfo fidr = new FeeImportDealRecordInfo(firDao.get(sess, firId), zlNo,feeName, dealTime, dealStatus,dealResult);
			fidrDao.save(sess, fidr);
			tran.commit();
			return fidr.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加已缴费用导入处理详情时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(String addDateS, String addDateE, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			firDao = (FeeImportRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_IMPORT_RECORD_INFO);
			Session sess = HibernateUtil.currentSession();
			return firDao.getCountByOpt(sess, addDateS, addDateE, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定时间段的费用导入信息记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<FeeImportRecordInfo> listPageInfoByOpt(String addDateS,
			String addDateE, Integer cpyId, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			firDao = (FeeImportRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_IMPORT_RECORD_INFO);
			Session sess = HibernateUtil.currentSession();
			return firDao.findPageInfoByOpt(sess, addDateS, addDateE, cpyId, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("分页获取指定时间段的费用导入信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<FeeImportDealRecordInfo> listInfoByFirId(Integer firId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fidrDao = (FeeImportDealRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_IMPORT_DEAL_RECORD_INFO);
			Session sess = HibernateUtil.currentSession();
			return fidrDao.findInfoByFirId(sess, firId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据费用导入编号获取已缴费导入处理详情列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
