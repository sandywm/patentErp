package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyRoleInfoDao;
import com.patent.dao.CpyRoleUserInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyRoleInfoTb;
import com.patent.module.CpyRoleUserInfoTb;
import com.patent.service.CpyRoleInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CpyRoleInfoManagerImpl implements CpyRoleInfoManager{

	CpyRoleInfoDao crDao = null;
	CpyUserInfoDao cuDao = null;
	CpyRoleUserInfoDao cruDao = null;
	CpyInfoDao cDao = null;
	Transaction tran = null;
	@Override
	public Integer addRole(String roleName, String roleProfile, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			crDao = (CpyRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_ROLE_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyRoleInfoTb cr = new CpyRoleInfoTb(cDao.get(sess, cpyId),roleName,roleProfile);
			crDao.save(sess, cr);
			tran.commit();
			return cr.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加角色信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateRoleById(Integer id, String roleName,
			String roleProfile) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer addRoleUser(Integer roleId, Integer userId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			crDao = (CpyRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_ROLE_INFO);
			cuDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			cruDao = (CpyRoleUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_ROLE_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyRoleUserInfoTb cru = new CpyRoleUserInfoTb(cuDao.get(sess, userId),crDao.get(sess, roleId));
			cruDao.save(sess, cru);
			tran.commit();
			return cru.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加角色用户信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delRoleUserByOpt(Integer roleId, Integer userId)
			throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CpyRoleInfoTb> listInfoByCpyId(Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			crDao = (CpyRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_ROLE_INFO);
			Session sess = HibernateUtil.currentSession();
			return crDao.findInfoByCpyId(sess, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定代理机构下的角色信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CpyRoleUserInfoTb> listInfoByUserId(Integer userId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cruDao = (CpyRoleUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_ROLE_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			return cruDao.findInfoByOpt(sess, 0, userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定用户的角色信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
