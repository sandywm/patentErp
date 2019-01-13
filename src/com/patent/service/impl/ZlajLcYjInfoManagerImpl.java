package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.ZlajLcMxInfoDao;
import com.patent.dao.ZlajLcYjInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajLcYjInfoTb;
import com.patent.service.ZlajLcYjInfoManager;
import com.patent.tools.CurrentTime;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajLcYjInfoManagerImpl implements ZlajLcYjInfoManager{

	ZlajLcMxInfoDao mxDao = null;
	ZlajLcYjInfoDao yjDao = null;
	CpyUserInfoDao uDao = null;
	CpyInfoDao cDao = null;
	Transaction tran = null;
	
	@Override
	public Integer addYj(Integer lcmxId, Integer applyUserId,String lcName,
			String applyCause, Integer checkUserId, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mxDao = (ZlajLcMxInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_MX_INFO);
			yjDao = (ZlajLcYjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_YJ_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcYjInfoTb yj = new ZlajLcYjInfoTb(mxDao.get(sess, lcmxId), uDao.get(sess, applyUserId),
					cDao.get(sess, cpyId), lcName,CurrentTime.getCurrentTime(), applyCause,0, "", checkUserId);
			yjDao.save(sess, yj);
			tran.commit();
			return yj.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加流程移交申请信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateYjInfoById(Integer yjId, Integer checkStatus,
			Integer checkUserId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			yjDao = (ZlajLcYjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_YJ_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcYjInfoTb yj = yjDao.getEntityById(sess, yjId);
			if(yj != null){
				yj.setCheckStatus(checkStatus);
				yj.setCheckDate(CurrentTime.getCurrentTime());
				yj.setCheckUserId(checkUserId);
				yjDao.update(sess, yj);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("审核指定移交申请信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcYjInfoTb> listPageInfoByOpt(Integer applyUserId,
			Integer checkStauts, Integer checkUserId, Integer cpyId,
			String zlTitle,String ajNo,String zlNo,Integer pageNo, Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		try {
			yjDao = (ZlajLcYjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_YJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return yjDao.findPageInfoByOpt(sess, applyUserId, checkStauts, checkUserId, cpyId, zlTitle,ajNo,zlNo,pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取移交申请信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer applyUserId, Integer checkStauts,
			Integer checkUserId, Integer cpyId,String zlTitle,String ajNo,String zlNo) throws WEBException {
		// TODO Auto-generated method stub
		try {
			yjDao = (ZlajLcYjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_YJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return yjDao.getCountByOpt(sess, applyUserId, checkStauts, checkUserId, cpyId, zlTitle,ajNo,zlNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取移交申请信息记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajLcYjInfoTb getEntityById(Integer yjId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			yjDao = (ZlajLcYjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_YJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return yjDao.getEntityById(sess, yjId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取移交申请信息记录信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcYjInfoTb> listUnCheckInfoByOpt(String lcTask, Integer zlId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			yjDao = (ZlajLcYjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_YJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return yjDao.findUnCheckInfoByOpt(sess, lcTask, zlId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定专利指定流程的未审核的流程任务申请列表信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajLcYjInfoTb getEntityByOpt(Integer applyUserId, String lcTask,
			Integer zlId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			yjDao = (ZlajLcYjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_YJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return yjDao.findEntityByOpt(sess, applyUserId, lcTask, zlId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定专利、申请人、流程任务的流程移交实体（未审核）信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
