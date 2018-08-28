package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ApplyInfoDao;
import com.patent.dao.PubZlInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.PubZlInfoTb;
import com.patent.service.PubZlInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class PubZlInfoManagerImpl implements PubZlInfoManager{

	ApplyInfoDao aDao = null;
	PubZlInfoDao pzDao = null;
	Transaction tran = null;
	@Override
	public Integer addPubZl(Integer pubUserId, String zlTitle,
			String zlContent, String zlType, String zlUpCl, String zlNewDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			aDao = (ApplyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_APPLY_INFO);
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			PubZlInfoTb pz = new PubZlInfoTb(aDao.get(sess, pubUserId), zlTitle, zlContent,"",zlType,
					zlUpCl, zlNewDate, 0, 0,"", 0, "", "",0);
			pzDao.save(sess, pz);
			tran.commit();
			return pz.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加发布专利任务信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateBasicInfoById(Integer id, String zlTitle,
			String zlContent, String zlType, String zlUpCl) throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			PubZlInfoTb pz = pzDao.get(sess, id);
			if(pz != null){
				pz.setZlTitle(zlTitle);
				pz.setZlContent(zlContent);
				pz.setZlType(zlType);
				pz.setZlUpCl(zlUpCl);
				pzDao.update(sess, pz);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改发布的专利的基本信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updatePubZlById(Integer id, Integer zlStatus,
			Integer lqUserId, String lqUserName, Integer lqCpyId,
			String lqCpyName, String lqDate, Integer ajId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			PubZlInfoTb pz = pzDao.get(sess, id);
			if(pz != null){
				if(zlStatus.equals(0)){//设置成未领取
					pz.setZlStatus(zlStatus);
					pz.setLqUserId(0);
					pz.setLqUserName("");
					pz.setLqCpyId(0);
					pz.setLqCpyName("");
					pz.setLqDate(null);
					pz.setAjId(0);
				}else if(zlStatus.equals(1)){//设置成已领取
					pz.setLqUserId(lqUserId);
					pz.setLqUserName(lqUserName);
					pz.setLqCpyId(lqCpyId);
					pz.setLqCpyName(lqCpyName);
					pz.setLqDate(lqDate);
					pz.setAjId(0);
				}
				pzDao.update(sess, pz);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改专利的高级信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delPubZlById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			pzDao.delete(sess, id);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键删除发布专利信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<PubZlInfoTb> listPageInfoByOpt(Integer pubId, String zlTitle,
			String zlNo, String zlType, String pubDate, Integer zlStatus,
			Integer pageNo, Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			return pzDao.findPageInfoByOpt(sess, pubId, zlTitle, zlNo, zlType, pubDate, zlStatus, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取发布专利任务列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer pubId, String zlTitle, String zlNo,
			String zlType, String pubDate, Integer zlStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			return pzDao.getCountByOpt(sess, pubId, zlTitle, zlNo, zlType, pubDate, zlStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取发布专利任务记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<PubZlInfoTb> listSpecInfoByOpt_1(Integer lqCpyId, Integer ajId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			return pzDao.findSpecInfoByOpt_1(sess, lqCpyId, ajId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定领取公司指定案件编号的发布专利信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<PubZlInfoTb> listSpecInfoByOpt(Integer id, Integer pubId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			return pzDao.findSpecInfoByOpt(sess, id, pubId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定主键、指定发布人编号的专利发布信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateAjIdById(Integer id, Integer ajId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			PubZlInfoTb pz = pzDao.get(sess, id);
			if(pz != null){
				if(ajId > 0){
					pz.setAjId(ajId);
					pzDao.update(sess, pz);
					tran.commit();
					return true;
				}
				return false;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据指定发布编号的案件编号时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<PubZlInfoTb> listSpecInfoByOpt_2(Integer lqCpyId,Integer addStatus)throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (PubZlInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_PUB_ZL_INFO);
			Session sess = HibernateUtil.currentSession();
			return pzDao.findSpecInfoByOpt_2(sess, lqCpyId, addStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取领取人所属公司的领取记录列表信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
