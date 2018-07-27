package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyUserInfo;
import com.patent.service.CpyUserInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CpyUserInfoManagerImpl implements CpyUserInfoManager{

	CpyUserInfoDao cUserDao = null;
	CpyInfoDao cDao = null;
	Transaction tran = null;
	@Override
	public Integer addCpyUser(String userName, String userNamePy,
			String account, String password, String userSex, String userEmail,
			String userTel, String userInDate, Integer cpyId,
			String userScFieldIdStr) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cUserDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyUserInfo cpyUser = new CpyUserInfo(cDao.get(sess, cpyId), userName,userNamePy,
					account, password, userSex,userEmail, userTel, userInDate,"", 1, 1,
					0, "", "","", 0,0);
			cUserDao.save(sess, cpyUser);
			tran.commit();
			return cpyUser.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("增加用户信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateBasicInfoById(Integer id, String userName,
			String userNamePy, String password, String userSex,
			String userEmail, String userTel, String userOutDate,
			Integer userLzStatus, Integer userYxStatus) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateInfoById(Integer id, Integer newUserZxNum,
			String userScFiledIdStr, Integer newUserExper) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateLoginInfoById(Integer id, String lastLoginDate,
			Integer loginTimes) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cUserDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyUserInfo cpyUser = cUserDao.get(sess, id);
			if(cpyUser != null){
				cpyUser.setLastLoginDate(lastLoginDate);
				cpyUser.setUserLoginTimes(loginTimes);
				cUserDao.update(sess, cpyUser);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("增加用户信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delSpecUserById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CpyUserInfo> listPageInfoByOpt(Integer cpyId,
			Integer userLzStatus, Integer userYxStatus, Integer roleId,
			String userNamePy, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByOpt(Integer cpyId, Integer userLzStatus,
			Integer userYxStatus, Integer roleId, String userNamePy)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CpyUserInfo> listSpecInfoByAccount(String account)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cUserDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			return cUserDao.findInfoByAccount(sess, account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("根据账号获取代理机构用户信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	

}
