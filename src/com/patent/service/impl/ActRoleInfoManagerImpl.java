package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ActRoleInfoDao;
import com.patent.dao.CpyRoleInfoDao;
import com.patent.dao.ModActInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ActRoleInfoTb;
import com.patent.service.ActRoleInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ActRoleInfoManagerImpl implements ActRoleInfoManager{

	ActRoleInfoDao arDao = null;
	ModActInfoDao maDao = null;
	CpyRoleInfoDao crDao = null;
	Transaction tran = null;
	@Override
	public Integer addARole(Integer roleId, Integer actId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			arDao = (ActRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ACT_ROLE_INFO);
			crDao = (CpyRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_ROLE_INFO);
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ActRoleInfoTb ar = new ActRoleInfoTb(crDao.get(sess, roleId),maDao.get(sess, actId));
			arDao.save(sess, ar);
			tran.commit();
			return ar.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加角色模块动作绑定关系时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ActRoleInfoTb> listInfoByOpt(Integer roleId,Integer maId)throws WEBException {
		// TODO Auto-generated method stub
		try {
			arDao = (ActRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ACT_ROLE_INFO);
			Session sess = HibernateUtil.currentSession();
			return arDao.findSpecInfoByOpt(sess, roleId, maId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据角色、模块动作编号获取角色动作绑定列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delInfoByOpt(Integer roleId, Integer actId)
			throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addBatchARole(Integer roleId, String actIdStr)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			arDao = (ActRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ACT_ROLE_INFO);
			crDao = (CpyRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_ROLE_INFO);
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			String[] actIdArr = actIdStr.split(",");
			Integer actIdLen = actIdArr.length;
			for(Integer i = 0 ; i < actIdLen ; i++){
				ActRoleInfoTb ar = new ActRoleInfoTb(crDao.get(sess, roleId),maDao.get(sess, Integer.parseInt(actIdArr[i])));
				arDao.save(sess, ar);
				if(i % 10 == 0){
					sess.flush();
					sess.clear();
					tran.commit();
					tran = sess.beginTransaction();
				}
			}
			tran.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("批量增加角色模块动作绑定关系时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public void delBatchInfoById(String idStr) throws WEBException {
		// TODO Auto-generated method stub
		try {
			arDao = (ActRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ACT_ROLE_INFO);
			crDao = (CpyRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_ROLE_INFO);
			maDao = (ModActInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MOD_ACT_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			String[] idArr = idStr.split(",");
			Integer idLen = idArr.length;
			for(Integer i = 0 ; i < idLen ; i++){
				arDao.delete(sess, Integer.parseInt(idArr[i]));
				if(i % 10 == 0){
					sess.flush();
					sess.clear();
					tran.commit();
					tran = sess.beginTransaction();
				}
			}
			tran.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("批量删除指定主键编号组合的角色动作绑定关系时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ActRoleInfoTb> listInfoByOpt(Integer roleId, String actNameEng)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			arDao = (ActRoleInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ACT_ROLE_INFO);
			Session sess = HibernateUtil.currentSession();
			return arDao.findSpecInfoByOpt(sess, roleId, actNameEng);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定角色、指定模块动作ENG的角色动作列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}
}
