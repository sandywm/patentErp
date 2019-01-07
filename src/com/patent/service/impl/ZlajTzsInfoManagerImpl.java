package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.dao.ZlajTzsInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.TzsApplyFileInfo;
import com.patent.module.ZlajTzsInfoTb;
import com.patent.service.ZlajTzsInfoManager;
import com.patent.tools.CurrentTime;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajTzsInfoManagerImpl implements ZlajTzsInfoManager{

	ZlajMainInfoDao zlDao = null;
	ZlajTzsInfoDao tzsDao = null;
	CpyUserInfoDao uDao = null;
	CpyInfoDao cDao = null;
	Transaction tran = null;
	
	@Override
	public Integer addTzs(Integer zlId,String ajNo,String tzsName,String fwrDate,String gfrDate,String fwSerial,String tzsPath,
			Integer uploadUserId,Integer readStatus,String readDetail,Integer cpyId,String tzsType) throws WEBException{
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajTzsInfoTb tzs = new ZlajTzsInfoTb(zlId,ajNo,tzsName,fwrDate,gfrDate,fwSerial,tzsPath,uDao.get(sess, uploadUserId),
					CurrentTime.getCurrentTime(),readStatus,readDetail,cDao.get(sess, cpyId),tzsType);
			tzsDao.save(sess, tzs);
			tran.commit();
			return tzs.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加专利通知书时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajTzsInfoTb> listInfoByZlId(Integer zlId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.findInfoByAjId(sess, zlId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据案件编号获取专利通知书列表信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajTzsInfoTb getEntityById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.getEntityById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据通知书主键编号获取专利通知书详细信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajTzsInfoTb> listInfoByOpt(Integer zlId, String fwSerial)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.findInfoByOpt(sess, zlId, fwSerial);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号、通知书发文序号获取通知书信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajTzsInfoTb> listPageInfoByOpt(Integer cpyId, Integer zlId,String ajNo,
			Integer readStatus,String tzsType, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.findPageInfoByOpt(sess, cpyId, zlId, ajNo,readStatus, tzsType, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取读取专利通知书列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer cpyId, Integer zlId, String ajNo,Integer readStatus,String tzsType)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.getCountByOpt(sess, cpyId, zlId, ajNo,readStatus,tzsType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取读取专利通知书记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<TzsApplyFileInfo> listInfoByTzsId(Integer tzsId)throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			return tzsDao.findInfoByTzsId(sess, tzsId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据通知书编号获取申请文件列表（电子申请回单时使用）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer addAF(Integer tzsId, String fileName, String fileType,
			String fileSize) throws WEBException {
		// TODO Auto-generated method stub
		try {
			tzsDao = (ZlajTzsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_TZS_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			TzsApplyFileInfo tzsAf = new TzsApplyFileInfo(tzsDao.get(sess, tzsId), fileName,fileType, fileSize);
			tzsDao.saveAf(sess, tzsAf);
			tran.commit();
			return tzsAf.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加电子回单申请文件信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
