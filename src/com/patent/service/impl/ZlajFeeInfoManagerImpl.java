package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.FeeTypeInfoDao;
import com.patent.dao.ZlajFeeInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.FeeTypeInfoTb;
import com.patent.module.ZlajFeeInfoTb;
import com.patent.service.ZlajFeeInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajFeeInfoManagerImpl implements ZlajFeeInfoManager{

	FeeTypeInfoDao ftDao = null;
	ZlajFeeInfoDao fDao = null;
	CpyInfoDao cDao = null;
	CpyUserInfoDao uDao = null;
	ZlajMainInfoDao zlDao = null;
	Transaction tran = null;
	@Override
	public List<ZlajFeeInfoTb> listInfoByOpt(Integer zlId, String feeTypeStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findInfoByOpt(sess, zlId, feeTypeStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取费用信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajFeeInfoTb getFeeEntityById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.getFeeEntityById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取费用信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<FeeTypeInfoTb> listInfoByStatus(String feeStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			ftDao = (FeeTypeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_TYPE_INFO);
			Session sess = HibernateUtil.currentSession();
			return ftDao.findInfoByStatus(sess, feeStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据费用标记获取费用类型信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listInfoByOpt(Integer zlId, Integer feeTypeId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findInfoByOpt(sess, zlId, feeTypeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号、费用类型获取专利案件缴费列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer addZLFee(Integer zlId, Integer appUserId, Integer geeTypeId,
			Double feePrice, String feeEndDateCpy, String feeEndDateGf,
			String feeRemark, Integer feeStatus, Integer cpyId,
			Integer djStatus, String feeJnDate, String feeUpZd)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			ftDao = (FeeTypeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_TYPE_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = new ZlajFeeInfoTb(ftDao.getTypeEntityById(sess, geeTypeId), uDao.get(sess, appUserId),
					cDao.get(sess, cpyId), zlDao.get(sess, zlId), feePrice, feeEndDateCpy,
					feeEndDateGf, feeRemark, feeStatus,djStatus, feeJnDate, feeUpZd);
			fDao.save(sess, zlFee);
			tran.commit();
			return zlFee.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加专利缴纳费用信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateFeeInfoById(Integer id, Double feePrice,
			String feeRemark, Integer feeStatus, Integer djStatus,
			String feeJnDate, String feeUpZd) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = fDao.getFeeEntityById(sess, id);
			if(zlFee != null){
				zlFee.setFeePrice(feePrice);
				zlFee.setFeeRemark(feeRemark);
				zlFee.setFeeStatus(feeStatus);
				zlFee.setDjStatus(djStatus);
				zlFee.setFeeJnDate(feeJnDate);
				zlFee.setFeeUpZd(feeUpZd);
				fDao.update(sess, zlFee);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定缴费信息的信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateFeeInfoById(Integer id, String feeEndDateCpy,
			String feeEndDateGf) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = fDao.getFeeEntityById(sess, id);
			if(zlFee != null){
				zlFee.setFeeEndDateJj(feeEndDateCpy);
				zlFee.setFeeEndDateGf(feeEndDateGf);
				fDao.update(sess, zlFee);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定编号的费用期限的信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
