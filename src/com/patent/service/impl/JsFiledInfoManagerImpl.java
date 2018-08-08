package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.JsFiledInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.JsFiledInfoTb;
import com.patent.service.JsFiledInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class JsFiledInfoManagerImpl implements JsFiledInfoManager{

	JsFiledInfoDao jfDao = null;
	CpyInfoDao cDao = null;
	Transaction tran = null;
	@Override
	public Integer addJF(String zyName, Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			jfDao = (JsFiledInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_JS_FIELD_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			JsFiledInfoTb jd = new JsFiledInfoTb(cDao.get(sess, cpyId),zyName);
			jfDao.save(sess, jd);
			tran.commit();
			return jd.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加专业技术区域信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<JsFiledInfoTb> listInfoByOpt(Integer cpyId, String jsIdStr)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			jfDao = (JsFiledInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_JS_FIELD_INFO);
			Session sess = HibernateUtil.currentSession();
			return jfDao.findInfoByOpt(sess, cpyId, jsIdStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取专业技术区域信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateJfById(Integer jfId, String zyName)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			jfDao = (JsFiledInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_JS_FIELD_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			JsFiledInfoTb jd = jfDao.get(sess, jfId);
			if(jd != null){
				if(!zyName.equals("")){
					jd.setZyName(zyName);
					jfDao.update(sess, jd);
					tran.commit();
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定主键的专业技术区域信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delJfById(Integer jfId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			jfDao = (JsFiledInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_JS_FIELD_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			jfDao.delete(sess, jfId);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除指定主键的专业技术区域信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<JsFiledInfoTb> listPageInfoByCpyId(Integer cpyId,Integer pageNo,Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByCpyId(Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JsFiledInfoTb> listInfoByOpt_1(Integer cpyId, String zyName)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			jfDao = (JsFiledInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_JS_FIELD_INFO);
			Session sess = HibernateUtil.currentSession();
			return jfDao.findInfoByOpt_1(sess, cpyId, zyName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定代理机构下指定专业名字的信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
