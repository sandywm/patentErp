package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CustomerFmrInfoDao;
import com.patent.dao.CustomerInfoDao;
import com.patent.dao.CustomerLxrInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyInfoTb;
import com.patent.module.CustomerFmrInfoTb;
import com.patent.module.CustomerInfoTb;
import com.patent.module.CustomerLxrInfoTb;
import com.patent.service.CustomerInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CustomerInfoManagerImpl implements CustomerInfoManager{

	CustomerInfoDao cDao = null;
	CustomerLxrInfoDao clDao = null;
	CustomerFmrInfoDao cfDao = null;
	CpyInfoDao ciDao = null;
	Transaction tran = null;
	@Override
	public Integer addCusInfo(String cusType, String cusName, String cusiCard,
			String cusAddress, String cusZip, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			ciDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CustomerInfoTb cus = new CustomerInfoTb(ciDao.get(sess, cpyId),cusType, cusName, cusiCard,cusAddress, cusZip);
			cDao.save(sess, cus);
			tran.commit();
			return cus.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加代理机构下客户信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delCusInfo(Integer id, Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			List<CustomerInfoTb> cusList = cDao.findInfoByOpt(sess, cpyId, id);
			if(cusList.size() > 0){
				cDao.delete(sess, id);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除指定代理机构下指定客户的信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean upCusInfo(Integer id, Integer cpyId, String cusType,
			String cusName, String cusiCard, String cusAddress, String cusZip)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			List<CustomerInfoTb> cusList = cDao.findInfoByOpt(sess, cpyId, id);
			if(cusList.size() > 0){
				CustomerInfoTb cus = cusList.get(0);
				if(!cusType.equals("")){
					cus.setCusType(cusType);
				}
				if(!cusName.equals("")){
					cus.setCusName(cusName);
				}
				if(!cusiCard.equals("")){
					cus.setCusICard(cusiCard);
				}
				if(!cusAddress.equals("")){
					cus.setCusAddress(cusAddress);
				}
				if(!cusZip.equals("")){
					cus.setCusZip(cusZip);
				}
				cDao.update(sess, cus);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定代理机构下指定客户的信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CustomerInfoTb> listPageInfoByOpt(Integer cpyId,
			String cusName, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			Session sess = HibernateUtil.currentSession();
			return cDao.findPageInfoByCpyId(sess, cpyId, cusName, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取指定代理机构下客户的信息列表出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer cpyId, String cusName)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			Session sess = HibernateUtil.currentSession();
			return cDao.getCountByCpyId(sess, cpyId, cusName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取指定代理机构下客户的记录条数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer addCusLxrInfo(Integer cusId, String lxrName, String lxrTel,
			String lxrEmail) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			clDao = (CustomerLxrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_LXR_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CustomerLxrInfoTb cl = new CustomerLxrInfoTb(cDao.get(sess, cusId), lxrName, lxrTel, lxrEmail);
			clDao.save(sess, cl);
			tran.commit();
			return cl.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加代理机构下客户的联系人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean upCusLxrById(Integer lxrId, String lxrName, String lxrTel,
			String lxrEmail) throws WEBException {
		// TODO Auto-generated method stub
		try {
			clDao = (CustomerLxrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_LXR_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CustomerLxrInfoTb cl = clDao.get(sess, lxrId);
			if(cl != null){
				if(!lxrName.equals("")){
					cl.setCusLxrName(lxrName);
				}
				if(!lxrTel.equals("")){
					cl.setCusLxrTel(lxrTel);
				}
				if(!lxrEmail.equals("")){
					cl.setCusLxrEmail(lxrEmail);
				}
				clDao.update(sess, cl);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改代理机构下指定客户的联系人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delCysLxrById(Integer lxrId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			clDao = (CustomerLxrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_LXR_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			clDao.delete(sess, lxrId);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除代理机构下指定客户的联系人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CustomerLxrInfoTb> listLxrInfoByOpt(Integer cusId,Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			clDao = (CustomerLxrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_LXR_INFO);
			Session sess = HibernateUtil.currentSession();
			return clDao.findInfoByOpt(sess, cusId,cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取代理机构下指定客户的联系人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CustomerLxrInfoTb> listLxrInfoByCusId(Integer lxrId,
			Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			clDao = (CustomerLxrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_LXR_INFO);
			Session sess = HibernateUtil.currentSession();
			return clDao.findInfoByCusId(sess, lxrId, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据联系人编号、代理机构编号获取联系人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}
	
	@Override
	public Integer addCusFmrInfo(Integer cusId, String fmrName,
			String fmriCard, String fmrTel, String fmrEmail)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			cfDao = (CustomerFmrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_FMR_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CustomerFmrInfoTb cf = new CustomerFmrInfoTb(cDao.get(sess, cusId), fmrName, fmriCard,fmrTel, fmrEmail);
			cfDao.save(sess, cf);
			tran.commit();
			return cf.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加代理机构下客户的发明人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean upCusFmrById(Integer fmrId, String fmrName, String fmriCard,
			String fmrTel, String fmrEmail) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cfDao = (CustomerFmrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_FMR_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CustomerFmrInfoTb cf = cfDao.get(sess, fmrId);
			if(cf != null){
				if(!fmrName.equals("")){
					cf.setCusFmrName(fmrName);
				}
				if(!fmrTel.equals("")){
					cf.setCusFmrTel(fmrTel);
				}
				if(!fmrEmail.equals("")){
					cf.setCusFxrEmail(fmrEmail);
				}
				if(!fmriCard.equals("")){
					cf.setCusFmrICard(fmriCard);
				}
				cfDao.update(sess, cf);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改代理机构下指定客户的发明人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delCusFmrById(Integer fmrId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cfDao = (CustomerFmrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_FMR_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			cfDao.delete(sess, fmrId);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除代理机构下指定客户的发明人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CustomerFmrInfoTb> listFmrInfoByCusId(Integer cusId,Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cfDao = (CustomerFmrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_FMR_INFO);
			Session sess = HibernateUtil.currentSession();
			return cfDao.findInfoByCusId(sess, cusId,cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取代理机构下指定客户的发明人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CustomerInfoTb> listInfoById(Integer cpyId,Integer cusId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			Session sess = HibernateUtil.currentSession();
			return cDao.findInfoByOpt(sess, cpyId, cusId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定代理机构下指定客户编号的信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CustomerFmrInfoTb> listFmrInfoByFmrId(Integer fmrId,
			Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cfDao = (CustomerFmrInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_FMR_INFO);
			Session sess = HibernateUtil.currentSession();
			return cfDao.findInfoByOpt(sess, fmrId, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键、代理机构编号获取发明人信息出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
