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
			String ajTitle, String ajType, String ajFieldId, String ajSqrName,
			String ajFmrName, String ajLxrName, Double ajFjInfo,String ajSqAddress, String ajYxqDetail,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus,String ajStatusChi,Integer pubZlId, Integer checkUserId,Integer zxUserId,
			Integer tjUserId,Integer tzsUserId,Integer feeUserId,Integer bzUserId,Integer bzshUserId,Integer bhUserId,Integer cpyId,Integer ajAddUserId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = new ZlajMainInfoTb(cDao.get(sess, cpyId), ajNo, ajNoQt, ajNoGf,
					ajTitle, ajType, ajFieldId, ajSqrName,ajFmrName, ajLxrName, ajFjInfo,ajSqAddress, ajYxqDetail,
					ajUpload, ajRemark, ajEwyqId,ajApplyDate, ajStatus, ajStatusChi, 0,0,pubZlId,"","","",CurrentTime.getStringDate(),checkUserId,zxUserId,
					tjUserId,tzsUserId,feeUserId,bzUserId,bzshUserId,bhUserId,ajAddUserId);
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
			String eDate,Integer lqStatus, Integer pageNo, Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findPageInfoByOpt(sess, cpyId, stopStatus, sqAddress, ajNoQt, zlNo, ajTitle, ajType, lxr, sDate, eDate, lqStatus, pageNo, pageSize);
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
			String ajType, String lxr, String sDate, String eDate,Integer lqStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.getCountByOpt(sess, cpyId, stopStatus, sqAddress, ajNoQt, zlNo, ajTitle, ajType, lxr, sDate, eDate, lqStatus);
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
	public List<ZlajMainInfoTb> listSpecInfoByZlNo(String ajNoGf) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findSpecInfoByOpt(sess, ajNoGf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利号全网获取案件专利（填写优先权时使用）时出现异常!");
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

	@Override
	public boolean updateBasicInfoById(Integer zlId, String zlTitle,String zlNo,
			String zlNoQt,Integer pubId,String sqAddress, String zlType,
			String ajFieldId, String sqrName, String fmrName, String lxrName,Double ajFjInfo,
			String yxqDetail, String upFile, String remark, String ewyq,
			String applyDate, Integer faId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				zl.setAjTitle(zlTitle);
				if(!zlNo.equals("")){
					zl.setAjNo(zlNo);
				}
				if(!zlNoQt.equals("")){
					zl.setAjNoQt(zlNoQt);
				}
				zl.setAjSqAddress(sqAddress);
				zl.setAjType(zlType);
				zl.setAjFieldId(ajFieldId);
				zl.setAjSqrName(sqrName);
				zl.setAjFmrName(fmrName);
				zl.setAjLxrName(lxrName);
				zl.setAjFjInfo(ajFjInfo);
				zl.setPubZlId(pubId);
				zl.setAjYxqDetail(yxqDetail);
				zl.setAjUpload(upFile);
				zl.setAjRemark(remark);
				zl.setAjEwyqId(ewyq);
				zl.setAjApplyDate(applyDate);
				if(faId > 0){
					zl.setAjFaId(faId);
				}
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改案件基本信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateAjNoGfById(Integer zlId, String ajNoGf)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				zl.setAjNoGf(ajNoGf);
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改案件专利号时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateOperatorUserInfoByZlId(Integer zlId,
			Integer checkUserId, Integer zxUserId, Integer tjUserId,
			Integer tzsUserId, Integer feeUserId, Integer bzUserId,
			Integer bzshUserId, Integer bhUserId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				if(checkUserId >= 0){
					zl.setCheckUserId(checkUserId);
				}
				if(zxUserId >= 0){
					zl.setZxUserId(zxUserId);
				}
				if(tjUserId >= 0){
					zl.setTjUserId(tjUserId);
				}
				if(tzsUserId >= 0){
					zl.setTzsUserId(tzsUserId);
				}
				if(feeUserId >= 0){
					zl.setFeeUserId(feeUserId);
				}
				if(bzUserId >= 0){
					zl.setBzUserId(bzUserId);
				}
				if(bzshUserId >= 0){
					zl.setBzshUserId(bzshUserId);
				}
				if(bhUserId >= 0){
					zl.setBhUserId(bhUserId);
				}
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改专利环节负责人员时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateZlStatusById(Integer id,String zlStatus,String ajStatusChi) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, id);
			if(zl != null){
				if(Integer.parseInt(zlStatus) >= 0){
					zl.setAjStatus(zlStatus);
					zl.setAjStatusChi(ajStatusChi);
					zlDao.update(sess, zl);
					tran.commit();
					return true;
				}
				return false;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改专利状态时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateZlUpFile_dg(Integer id, String zlUpFile)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, id);
			if(zl != null){
				zl.setAjUpload(zlUpFile);
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利底稿信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateBasicInfoById(Integer id, String zlTitle,
			String sqrName, String fmrName, String lxrName, Double ajFjInfo)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, id);
			if(zl != null){
				if(!zlTitle.equals("")){
					zl.setAjTitle(zlTitle);
				}
				if(!sqrName.equals("")){
					zl.setAjSqrName(sqrName);
				}
				if(!fmrName.equals("")){
					zl.setAjFmrName(fmrName);
				}
				if(!lxrName.equals("")){
					zl.setAjLxrName(lxrName);
				}
				if(ajFjInfo == 0.70 || ajFjInfo == 0.85){
					zl.setAjFjInfo(ajFjInfo);
				}
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改专利的最终标题、申请人、发明人、联系人、费减信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajMainInfoTb> listSpecInfoByOpt(String zlTitle,
			String sqrName, String zlType,Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findSpecInfoByOpt(sess, zlTitle, sqrName, zlType,cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利标题、专利申请人、专利类型获取专利（一般在导入受理通知书书时使用）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
