package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.ZlajLcInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajLcInfoTb;
import com.patent.service.ZlajLcInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajLcInfoManagerImpl implements ZlajLcInfoManager{

	ZlajLcInfoDao lcDao = null;
	ZlajMainInfoDao ajDao = null;
	CpyUserInfoDao uDao = null;
	Transaction tran = null;
	@Override
	public Integer addLcInfo(Integer ajId,String lcName,String lcDetail,String sDate,String cpyDate,
			String comDate,String gfDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			ajDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcInfoTb lc = new ZlajLcInfoTb(ajDao.get(sess, ajId),lcName, lcDetail, sDate,cpyDate, comDate, gfDate);
			lcDao.save(sess, lc);
			tran.commit();
			return lc.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加流程信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateLcBasicInfoById(Integer id,String lcName,String lcDetail,String sDate,String cpyDate,
			String gfDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcInfoTb lc = lcDao.get(sess, id);
			if(lc != null){
				if(!lcName.equals("")){
					lc.setLcMz(lcName);
				}
				if(!lcDetail.equals("")){
					lc.setLcDetail(lcDetail);
				}
				if(!sDate.equals("")){
					lc.setLcSDate(sDate);
				}
				if(!cpyDate.equals("")){
					lc.setLcCpyDate(cpyDate);
				}
				if(!gfDate.equals("")){
					lc.setLcGfDate(gfDate);
				}
				lcDao.update(sess, lc);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改制定编号的流程基本信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcInfoTb> listLcInfoById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			Session sess = HibernateUtil.currentSession();
			return lcDao.findInfoById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取流程信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcInfoTb> listLcInfoByAjId(Integer ajId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			Session sess = HibernateUtil.currentSession();
			return lcDao.findInfo(sess, ajId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据案件编号获取该案件所有的流程列表（编号降序排列）时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateComInfoById(Integer id, String comDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajLcInfoTb lc = lcDao.get(sess, id);
			if(lc != null){
				lc.setLcEDate(comDate);
				lcDao.update(sess, lc);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改制定编号的流程完成信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcInfoTb> listLcInfoByLcMz(String lcTitle)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			Session sess = HibernateUtil.currentSession();
			return lcDao.findInfoByLcMz(sess, lcTitle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据流程名字获取流程信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajLcInfoTb> listLastInfoByAjId(Integer ajId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			lcDao = (ZlajLcInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_LC_INFO);
			Session sess = HibernateUtil.currentSession();
			return lcDao.findLastInfo(sess, ajId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取最后一个未完成的流程任务时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
