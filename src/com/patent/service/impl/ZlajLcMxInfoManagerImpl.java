package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ZlajLcInfoDao;
import com.patent.dao.ZlajLcMxInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajLcMxInfoTb;
import com.patent.service.ZlajLcMxInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajLcMxInfoManagerImpl implements ZlajLcMxInfoManager{

	ZlajLcInfoDao lcDao = null;
	ZlajLcMxInfoDao mxDao = null;
	Transaction tran = null;
	@Override
	public Integer addLcMx(Integer lcId, Integer fzUserId, String lcMxName,
			Double lcMxNo, String lcMxSDate, String lcMxEDate,
			String lcMxUpFile, Integer lcMxUpUserId, String lcMxUpDate,
			String lcMxUpSize, String lcMxRemark) throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcMxInfoTb mxInfo = new ZlajLcMxInfoTb(fzUserId, lcDao.get(sess, lcId),
					lcMxName, lcMxNo, lcMxSDate, lcMxEDate,lcMxUpFile, lcMxUpUserId, lcMxUpDate,
					lcMxUpSize, lcMxRemark);
			mxDao.save(sess, mxInfo);
			tran.commit();
			return mxInfo.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加流程明细时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateEdateById(Integer id,Integer fzUserId,Integer lcMxUpUserId,String lcMxUpFile,
			String lcMxUpDate,String lcMxUpSize,String eDate, String lcMxRemark)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcMxInfoTb mx = mxDao.get(sess, id);
			if(mx != null){
				mx.setLcMxEDate(eDate);
				mx.setLcFzUserId(fzUserId);
				if(!lcMxUpUserId.equals(-1)){
					mx.setLcMxUpUserId(lcMxUpUserId);
				}
				if(!lcMxUpFile.equals("")){
					mx.setLcMxUpFile(lcMxUpFile);
				}
				if(!lcMxUpDate.equals("")){
					mx.setLcMxUpDate(lcMxUpDate);
				}
				if(!lcMxUpSize.equals("")){
					mx.setLcMxUpSize(lcMxUpSize);
				}
				if(!lcMxRemark.equals("")){
					mx.setLcMxRemark(lcMxRemark);
				}
				mxDao.update(sess, mx);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加流程明细完成时间和备注时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcMxInfoTb> listDetailInfoByLcId(Integer lcId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			return mxDao.findDetailInfoByLcId(sess, lcId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据流程编号获取所有流程明细时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcMxInfoTb> listLastInfoByLcId(Integer lcId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			return mxDao.findLastInfoByLcId(sess, lcId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据流程编号获取第一条记录(最后一个动作)（id降序排列）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcMxInfoTb> listFirstInfoByLcId(Integer lcId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			return mxDao.findFirstInfoByLcId(sess, lcId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据流程主键获取第一个动作（获取未领取的流程明细）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
