package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajMainInfoTb;
import com.patent.service.ZlajMainInfoManager;
import com.patent.tools.CurrentTime;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajMainInfoManagerImpl implements ZlajMainInfoManager{

	ZlajMainInfoDao zlDao = null;
	CpyInfoDao cDao = null;
	Transaction tran = null;
	@Override
	public Integer addZL(String ajNo, String ajNoQt, String ajNoGf,
			String ajTitle, String ajType, String ajFieldId, String ajSqrId,
			String ajFmrId, String ajLxrId, String ajSqAddress, String ajYxqDetail,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus,Integer pubZlId, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = new ZlajMainInfoTb(cDao.get(sess, cpyId), ajNo, ajNoQt, ajNoGf,
					ajTitle, ajType, ajFieldId, ajSqrId,ajFmrId, ajLxrId, ajSqAddress, ajYxqDetail,
					ajUpload, ajRemark, ajEwyqId,ajApplyDate, ajStatus, 0,0,pubZlId,"","","",CurrentTime.getStringDate());
			zlDao.save(sess, zl);
			tran.commit();
			return zl.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("初始增加专利时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajMainInfoTb> listPageInfoByOpt(Integer cpyId,
			Integer stopStatus, String sqAddress, String ajNoQt, String zlNo,
			String ajTitle, String ajType, String lxr, String sDate,
			String eDate, Integer pageNo, Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findPageInfoByOpt(sess, cpyId, stopStatus, sqAddress, ajNoQt, zlNo, ajTitle, ajType, lxr, sDate, eDate, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取专利基本信息表列表（ID降序排列）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer cpyId, Integer stopStatus,
			String sqAddress, String ajNoQt, String zlNo, String ajTitle,
			String ajType, String lxr, String sDate, String eDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.getCountByOpt(sess, cpyId, stopStatus, sqAddress, ajNoQt, zlNo, ajTitle, ajType, lxr, sDate, eDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取专利基本信息记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajMainInfoTb> listSpecInfoById(Integer id, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findSpecInfoById(sess, id, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取专利信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajMainInfoTb> listFirstInfoByOpt(Integer cpyId,String ajType,String currYear)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findFirstInfoByOpt(sess, cpyId,ajType,currYear);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取第一条记录（按照案件编号降序排列）获取即将增加的案件号时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajMainInfoTb> listSpecInfoByOpt(String ajNoGf,
			String sqAddress, String sqDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findSpecInfoByOpt(sess, ajNoGf, sqAddress, sqDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件全网获取案件专利（填写优先权时使用）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateStopStatusById(Integer zlId, Integer stopStatus,
			String stopDate, String stopUser, String userType)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				if(stopStatus.equals(0) || stopStatus.equals(1)){//正常/终止
					zl.setAjStopStatus(stopStatus);
					zl.setAjStopDate(stopDate);
					zl.setAjStopUser(stopUser);
					zl.setAjStopUserType(userType);
					zlDao.update(sess, zl);
					tran.commit();
					return true;
				}else{
					return false;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改案件终止状态信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
