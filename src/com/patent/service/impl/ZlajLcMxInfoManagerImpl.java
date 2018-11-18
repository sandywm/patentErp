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
			String lcMxUpSize, Double lcMxFee, String lcMxRemark,Integer lcPjScore) throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcMxInfoTb mxInfo = new ZlajLcMxInfoTb(fzUserId, lcDao.get(sess, lcId),
					lcMxName, lcMxNo, lcMxSDate, lcMxEDate,lcMxUpFile, lcMxUpUserId, lcMxUpDate,
					lcMxUpSize, lcMxFee, lcMxRemark,lcPjScore);
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
	public boolean updateEdateById(Integer id,Integer fzUserId,String lcMxName,Integer lcMxUpUserId,String lcMxUpFile,
			String lcMxUpDate,String lcMxUpSize,String eDate, String lcMxRemark,Integer lcPjScore)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcMxInfoTb mx = mxDao.get(sess, id);
			if(mx != null){
				if(!eDate.equals("")){
					mx.setLcMxEDate(eDate);
				}
				if(fzUserId > 0){
					mx.setLcFzUserId(fzUserId);
				}
				if(!lcMxName.equals("")){
					mx.setLcMxName(lcMxName);
				}
				if(lcMxUpUserId > 0){
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
				if(!lcPjScore.equals(-1)){
					mx.setLcPjScore(lcPjScore);
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

	@Override
	public List<ZlajLcMxInfoTb> listUnComInfoByOpt(String lcMxName,
			Double lcMxNo, Integer lcId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			return mxDao.findFirstInfoByLcId(sess, lcId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据流程明细名称、流程号、流程主键编号获取未完成的流程明细列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcMxInfoTb> listDetailInfoById(Integer mxId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			return mxDao.findDetailInfoById(sess, mxId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据流程明细主键编号获取流程明细列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcMxInfoTb> listSpecInfoInfoByOpt(Integer zlId,
			String lcmxName) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			return mxDao.findSpecInfoByOpt(sess, zlId, lcmxName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("查看指定专利、指定流程明细有无记录（批量导入时，如果出现先后顺序混乱时使用）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateEdateById(Integer id, String lcMxName)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcMxInfoTb mx = mxDao.get(sess, id);
			if(mx != null){
				mx.setLcMxName(lcMxName);
				mxDao.update(sess, mx);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改流程明细名称时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateMxNoById(Integer id, Double mxNo)throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcMxInfoTb mx = mxDao.get(sess, id);
			if(mx != null){
				mx.setLcMxNo(mxNo);
				mxDao.update(sess, mx);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定流程明细的流程号时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcMxInfoTb> listLcMxByOpt(Integer fzUserId,
			Integer comStatus, Integer cpyId, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			return mxDao.findLcMxByOpt(sess, fzUserId, comStatus, cpyId, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取任务记录列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer fzUserId, Integer comStatus,
			Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			Session sess = HibernateUtil.currentSession();
			return mxDao.getCountByOpt(sess, fzUserId, comStatus, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取任务记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
