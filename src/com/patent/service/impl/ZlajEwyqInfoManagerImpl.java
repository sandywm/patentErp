package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.ZlajEwyqInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajEwyqInfoTb;
import com.patent.service.ZlajEwyqInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajEwyqInfoManagerImpl implements ZlajEwyqInfoManager{

	ZlajEwyqInfoDao yqDao = null;
	Transaction tran = null;
	@Override
	public Integer addYq(String yqContent, String yqType) throws WEBException {
		// TODO Auto-generated method stub
		try {
			yqDao = (ZlajEwyqInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_EWYQ_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajEwyqInfoTb yqInfo = new ZlajEwyqInfoTb(yqContent,yqType);
			yqDao.save(sess, yqInfo);
			tran.commit();
			return yqInfo.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加额外要求信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateYq(Integer id, String yqContent, String yqType)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			yqDao = (ZlajEwyqInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_EWYQ_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajEwyqInfoTb yqInfo = yqDao.get(sess, id);
			if(yqInfo != null){
				if(!yqContent.equals("")){
					yqInfo.setYqContent(yqContent);
				}
				if(!yqType.equals("")){
					yqInfo.setYqType(yqType);
				}
				yqDao.update(sess, yqInfo);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定额外要求信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delYq(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ZlajEwyqInfoTb> listInfoByType(String yqType)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			yqDao = (ZlajEwyqInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_EWYQ_INFO);
			Session sess = HibernateUtil.currentSession();
			return yqDao.findInfo(sess, yqType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据类型获取额外要求信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajEwyqInfoTb> listInfoByCnt(String yqContent)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			yqDao = (ZlajEwyqInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_EWYQ_INFO);
			Session sess = HibernateUtil.currentSession();
			return yqDao.findInfoByCnt(sess, yqContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据要求名字获取额外要求信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajEwyqInfoTb getEntityById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			yqDao = (ZlajEwyqInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_EWYQ_INFO);
			Session sess = HibernateUtil.currentSession();
			return yqDao.getEntityById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取额外要求信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
