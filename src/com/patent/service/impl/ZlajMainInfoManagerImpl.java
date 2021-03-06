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
			String ajTitle, String ajType, String ajFieldId, String ajSqrId,String ajSqrName,
			String ajFmrId, String ajLxrId, String jsLxrId,Double ajFjInfo,String ajSqAddress, String ajYxqDetail,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus,String ajStatusChi,Integer pubZlId, Integer checkUserId,Integer zxUserId,Integer cusCheckUserId,
			Integer tjUserId,Integer tzsUserId,Integer feeUserId,Integer bzUserId,Integer bzshUserId,Integer bhUserId,Integer cpyId,Integer ajAddUserId,Integer zlLevel,
			String ajType1,String ajUploadDg,String ajUploadHt,String payUserInfo,Integer bzTjUserId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = new ZlajMainInfoTb(cDao.get(sess, cpyId), ajNo, ajNoQt, ajNoGf,
					ajTitle, ajType, ajFieldId, ajSqrId, ajSqrName,ajFmrId, ajLxrId, jsLxrId,ajFjInfo,ajSqAddress, ajYxqDetail,
					ajUpload, ajRemark, ajEwyqId,ajApplyDate, ajStatus, ajStatusChi, 0,0,pubZlId,"","","",CurrentTime.getStringDate(),checkUserId,zxUserId,cusCheckUserId,
					tjUserId,tzsUserId,feeUserId,bzUserId,bzshUserId,bhUserId,ajAddUserId,zlLevel,ajType1,ajUploadDg,ajUploadHt,payUserInfo,bzTjUserId);
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
			String ajTitle, String ajType,  Integer cusId, String sDate,
			String eDate,Integer lqStatus,Integer ajAddUserId, Integer pageNo, Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findPageInfoByOpt(sess, cpyId, stopStatus, sqAddress, ajNoQt, zlNo, ajTitle, ajType, cusId, sDate, eDate, lqStatus,ajAddUserId, pageNo, pageSize);
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
			String ajType,  Integer cusId, String sDate, String eDate,Integer lqStatus,Integer ajAddUserId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.getCountByOpt(sess, cpyId, stopStatus, sqAddress, ajNoQt, zlNo, ajTitle, ajType, cusId, sDate, eDate, lqStatus,ajAddUserId);
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
	public List<ZlajMainInfoTb> listSpecInfoByZlNo(String ajNoGf,Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findSpecInfoByOpt(sess, ajNoGf,cpyId);
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
			String zlNoQt,String zlNoGf,Integer pubId,String sqAddress, String zlType,
			String ajFieldId, String ajSqrId,String sqrName, String fmrId, String lxrId,String jsLxrId,Double ajFjInfo,
			String yxqDetail, String upFile, String remark, String ewyq,
			String applyDate, Integer faId,String upFileDg,String upFileHt,String payUserInfo) throws WEBException {
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
				if(!zlNoGf.equals("")){
					zl.setAjNoGf(zlNoGf);
				}
				zl.setAjSqAddress(sqAddress);
				zl.setAjType(zlType);
				zl.setAjFieldId(ajFieldId);
				zl.setAjSqrId(ajSqrId);
				zl.setAjSqrName(sqrName);
				zl.setAjFmrId(fmrId);
				zl.setAjLxrId(lxrId);
				zl.setJsLxrId(jsLxrId);
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
				zl.setAjUploadDg(upFileDg);
				zl.setAjUploadHt(upFileHt);
				if(!payUserInfo.equals("")){
					zl.setPayUserInfo(payUserInfo);
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
			Integer checkUserId, Integer zxUserId, Integer cusCheckUserId,Integer tjUserId,
			Integer tzsUserId, Integer feeUserId, Integer bzUserId,
			Integer bzshUserId, Integer bhUserId,Integer bzTjUserId) throws WEBException {
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
				if(cusCheckUserId >= 0){
					zl.setCusCheckUserId(cusCheckUserId);
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
				if(bzTjUserId >= 0){
					zl.setBzTjUserId(bzTjUserId);
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
				if(Double.parseDouble(zlStatus) >= 0){
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
			String sqrId,String sqrName, String fmrId, String lxrId, Double ajFjInfo)
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
				if(!sqrId.equals("")){
					zl.setAjSqrId(sqrId);
				}
				if(!sqrName.equals("")){
					zl.setAjSqrName(sqrName);
				}
				if(!fmrId.equals("")){
					zl.setAjFmrId(fmrId);
				}
				if(!lxrId.equals("")){
					zl.setAjLxrId(lxrId);
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

	@Override
	public boolean updateZlApplyDate(Integer zlId, String applyDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				zl.setAjApplyDate(applyDate);
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改专利的申请日时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateZlFjInfo(Integer zlId, Double fjRate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				zl.setAjFjInfo(fjRate);
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改专利的费减时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajMainInfoTb> listInfoByOpt(String lcNameEng, Integer userId,
			Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findInfoByOpt(sess, lcNameEng, userId, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定用户、指定代理机构、指定流程任务的专利列表（获取指定人是否具有通知书导入和费用催缴流程任务）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateZlLevelById(Integer zlId, Integer zlLevel)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				if(zlLevel > 0){
					zl.setZlLevel(zlLevel);
					zlDao.update(sess, zl);
					tran.commit();
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改专利难易度时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajMainInfoTb> listInfoByOpt_1(String zlNo, Integer cusId,
			Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findInfoByOpt_1(sess, zlNo, cusId, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("通过专利号、客户编号获取专利列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByAddUserId(Integer addUserId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.getCountByAddUserId(sess, addUserId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定专利人员的添加的专利数量时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateFjFileById(Integer zlId, String dgFile,String htFile)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				if(!dgFile.equals("")){
					zl.setAjUploadDg(dgFile);
				}
				if(!htFile.equals("")){
					zl.setAjUploadHt(htFile);
				}
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改指定专利的定稿文件、合同文件时(定稿提交)出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateZlFaInfoById(Integer zlId, Integer faId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, zlId);
			if(zl != null){
				zl.setAjFaId(faId);
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改案件分案信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateZlUpFile_dg1(Integer id, String zlUpFile)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajMainInfoTb zl = zlDao.get(sess, id);
			if(zl != null){
				zl.setAjUploadDg(zlUpFile);
				zlDao.update(sess, zl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改专利定稿文件时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajMainInfoTb> listInfoByZlTitle(String zlTitle, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			return zlDao.findInfoByZlTitle(sess, zlTitle, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定专利名称的专利信息（案件正常状态下）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delZlInfoById(Integer zlId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			zlDao.delete(sess, zlId);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键删除案件信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
