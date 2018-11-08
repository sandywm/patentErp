package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.ZlajFjInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajFjInfoTb;
import com.patent.service.ZlajFjInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajFjInfoManagerImpl implements ZlajFjInfoManager {

	CpyUserInfoDao uDao = null;
	ZlajMainInfoDao zlDao = null;
	ZlajFjInfoDao fjDao = null;
	Transaction tran = null;
	@Override
	public Integer addFj(Integer ajId, String fjName, String fjVersion,
			String fjType, String fjGs, String fjDx, Integer upUserId,
			String upDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			fjDao = (ZlajFjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FJ_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFjInfoTb fj = new ZlajFjInfoTb(uDao.get(sess, upUserId),zlDao.get(sess, ajId),fjName, fjVersion, fjType, fjGs,fjDx, upDate);
			fjDao.save(sess, fj);
			tran.commit();
			return fj.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加专利附件时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFjInfoTb> listInfoByAjId(Integer ajId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fjDao = (ZlajFjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return fjDao.findInfoByAjId(sess, ajId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号获取专利附件时（id降序排列）出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFjInfoTb> listLastInfoByAjId(Integer ajId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fjDao = (ZlajFjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return fjDao.findLastInfoByAjId(sess, ajId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号获取第一条专利附件时（id降序排列）出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFjInfoTb> listSpecInfoByOpt(Integer ajId, String fjType,boolean orderFlag,String orderInfo)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fjDao = (ZlajFjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FJ_INFO);
			Session sess = HibernateUtil.currentSession();
			return fjDao.findSpecInfoByOpt(sess, ajId, fjType,orderFlag,orderInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据案件编号、附件类型获取附件信息列表出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delBatchFjInfo(String fjIdStr) throws WEBException {
		// TODO Auto-generated method stub
		if(!fjIdStr.equals("")){
			try {
				fjDao = (ZlajFjInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FJ_INFO);
				Session sess = HibernateUtil.currentSession();
				tran = sess.beginTransaction();
				
				String[] idArr = fjIdStr.split(",");
				Integer idLen = idArr.length;
				for(Integer i = 0 ; i < idLen ; i++){
					fjDao.delete(sess, Integer.parseInt(idArr[i]));
					if(i % 10 == 0){
						sess.flush();
						sess.clear();
						tran.commit();
						tran = sess.beginTransaction();
					}
				}
				tran.commit();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new WEBException("批量删除专利附件时出现异常!");
			} finally{
				HibernateUtil.closeSession();
			}
		}
		return false;
	}

}
