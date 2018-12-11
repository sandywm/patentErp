package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.FeeExportRecordInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.FeeExportRecordInfo;
import com.patent.service.FeeExportRecordInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class FeeExportRecordInfoManagerImpl implements FeeExportRecordInfoManager{

	CpyUserInfoDao uDao = null;
	CpyInfoDao cDao = null;
	FeeExportRecordInfoDao ferDao = null;
	Transaction tran = null;
	@Override
	public Integer addFER(String excelName, String addTime, Integer addUserId,
			String excelPath, Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			ferDao = (FeeExportRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_EXPORT_RECORD_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			FeeExportRecordInfo fer = new FeeExportRecordInfo(uDao.get(sess, addUserId),cDao.get(sess, cpyId), excelName, addTime,excelPath);
			ferDao.save(sess, fer);
			tran.commit();
			return fer.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加未交费信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<FeeExportRecordInfo> listPageInfoByOpt(String addDateS,
			String addDateE, Integer cpyId, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			ferDao = (FeeExportRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_EXPORT_RECORD_INFO);
			Session sess = HibernateUtil.currentSession();
			return ferDao.findPageInfoByOpt(sess, addDateS, addDateE, cpyId, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取未交费清单导出信息列表信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(String addDateS, String addDateE, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			ferDao = (FeeExportRecordInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_EXPORT_RECORD_INFO);
			Session sess = HibernateUtil.currentSession();
			return ferDao.getCountByOpt(sess, addDateS, addDateE, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取未交费清单导出记录条数信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
