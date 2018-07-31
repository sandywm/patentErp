package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.SuperUserDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.SuperUser;
import com.patent.service.SuperUserManager;
import com.patent.tools.HibernateUtil;
import com.patent.tools.MD5;
import com.patent.util.Constants;

public class SuperUserManagerImpl implements SuperUserManager{

	SuperUserDao suDao = null;
	Transaction tran = null;
	@Override
	public Integer addSUser(String account, String password, String userName,
			String userType) throws WEBException {
		// TODO Auto-generated method stub
		try {
			suDao = (SuperUserDao) DaoFactory.instance(null).getDao(Constants.DAO_SUPER_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			SuperUser su = new SuperUser(account,password,userName,1,userType,0);
			suDao.save(sess, su);
			tran.commit();
			return su.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加平台用户时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delSUser(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			suDao = (SuperUserDao) DaoFactory.instance(null).getDao(Constants.DAO_SUPER_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			suDao.delete(sess, id);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除平台指定用户时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateSUserById(Integer userId,String password, String userName)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			suDao = (SuperUserDao) DaoFactory.instance(null).getDao(Constants.DAO_SUPER_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			SuperUser su = suDao.get(sess, userId);
			if(su != null){
				if(!password.equals("")){
					su.setPassword(new MD5().calcMD5(password));
				}
				if(!userName.equals("")){
					su.setUserName(userName);
				}
				suDao.update(sess, su);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改平台指定用户的密码、姓名时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
		
	}

	@Override
	public List<SuperUser> listInfoById(Integer userId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			suDao = (SuperUserDao) DaoFactory.instance(null).getDao(Constants.DAO_SUPER_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			return suDao.findInfoById(sess, userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取平台用户列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<SuperUser> listInfoByAccount(String account)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			suDao = (SuperUserDao) DaoFactory.instance(null).getDao(Constants.DAO_SUPER_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			return suDao.findInfoByAccount(sess, account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取平台指定用户账号的信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateStatusById(Integer userId, Integer yxStatus,Integer loginTimes)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			suDao = (SuperUserDao) DaoFactory.instance(null).getDao(Constants.DAO_SUPER_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			SuperUser su = suDao.get(sess, userId);
			tran = sess.beginTransaction();
			if(su != null){
				if(yxStatus >= 0){
					su.setYxStatus(yxStatus);
				}
				if(loginTimes >= 0){
					su.setLoginTimes(loginTimes);
				}
				if(yxStatus >= 0 || loginTimes >= 0){
					suDao.update(sess, su);
					tran.commit();
					return true;
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改平台指定用户的有效 状态、登录次数时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
