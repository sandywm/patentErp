package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ApplyInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ApplyInfoTb;
import com.patent.service.ApplyInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ApplyInfoManagerImpl implements ApplyInfoManager{

	ApplyInfoDao aDao = null;
	Transaction tran = null;
	@Override
	public Integer addAppInfo(String appType, String appName, String appNamePy,String appICard,
			String appAddress, String appAccount, String appPass,
			String appLxr, String appTel, String appEmail, String appQq) throws WEBException {
		// TODO Auto-generated method stub
		try {
			aDao = (ApplyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_APPLY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ApplyInfoTb app = new ApplyInfoTb(appType, appName, appNamePy,appICard,
					appAddress, appAccount, appPass,appLxr, appTel, appEmail, 
					appQq,"",0);
			aDao.save(sess, app);
			tran.commit();
			return app.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("增加申请专利（人/公司）信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ApplyInfoTb> findPageInfoByOpt(String appType,
			String appNamePy, String appLxr, String lxrTel, Integer pageNo,
			Integer pageSize) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByOpt(String appType, String appNamePy,
			String appLxr, String lxrTel) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplyInfoTb getEntityById(Integer appId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			aDao = (ApplyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_APPLY_INFO);
			Session sess = HibernateUtil.currentSession();
			return aDao.getEntityById(sess, appId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("根据主键获取详细信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateAppLoginInfoById(Integer id, String lastLoginDate,
			Integer userLoginTimes) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ApplyInfoTb> listInfoByAccount(String account)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			aDao = (ApplyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_APPLY_INFO);
			Session sess = HibernateUtil.currentSession();
			return aDao.findInfoByAccount(sess, account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("根据账号获取申请专利（人/公司）信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updatePassById(Integer userId, String newPass)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			aDao = (ApplyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_APPLY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ApplyInfoTb app = aDao.get(sess, userId);
			if(app != null){
				if(!newPass.equals("")){
					app.setAppPass(newPass);
					aDao.update(sess, app);
					tran.commit();
					return true;
				}
				return false;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("根据主键修改申请专利（人/公司）密码时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
