package com.patent.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyInfoTb;
import com.patent.service.CpyInfoManager;
import com.patent.tools.Convert;
import com.patent.tools.CurrentTime;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CpyInfoManagerImpl implements CpyInfoManager{

	CpyInfoDao cDao = null;
	Transaction tran = null;
	
	@Override
	public Integer addCpy(String cpyName, String cpyAddress, String cpyProv,
			String cpyCity, String cpyFr, String cpyYyzz, String cpyLxr, String lxrTel,
			String lxrEmail,String cpySubId,Integer cpyParId,String cpyUrl, String cpyProfile, 
			String signDate, Date endDate,Integer hotStatus, Integer cpyLevel)
			throws WEBException {
		// TODO Auto-generated method stub
		
		try {
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyInfoTb cpy = new CpyInfoTb(cpyName, Convert.getFirstSpell(cpyName),cpyAddress, cpyProv,cpyCity, cpyFr, cpyYyzz, cpyLxr,
					lxrTel, lxrEmail, cpySubId, cpyParId, cpyUrl,cpyProfile,CurrentTime.stringToDate_1(signDate),
					endDate, hotStatus, cpyLevel);	
			cDao.save(sess, cpy);
			tran.commit();
			return cpy.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("增加代理机构信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateBasicCpyInfoById(Integer id, String cpyName,String cpyAddress,String cpyProv,String cpyCity,String cpyFr,
			String cpyLxr,String lxrTel,String lxrEmail,String cpyYyzz,String cpyUrl,String cpyProfile)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CpyInfoTb cpy = cDao.get(sess, id);
			if(cpy != null){
				cpy.setCpyName(cpyName);
				cpy.setCpyAddress(cpyAddress);
				cpy.setCpyProv(cpyProv);
				cpy.setCpyCity(cpyCity);
				cpy.setCpyFr(cpyFr);
				cpy.setCpyLxr(cpyLxr);
				cpy.setLxrTel(lxrTel);
				cpy.setLxrEmail(lxrEmail);
				cpy.setCpyYyzz(cpyYyzz);
				cpy.setCpyUrl(cpyUrl);
				cpy.setCpyProfile(cpyProfile);
				cDao.update(sess, cpy);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("根据主键修改代理机构基本信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateJoinInfoById(Integer id, Integer cpyParId,
			Integer cpySubId) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCpyInfoById(Integer id, Date endDate,
			Integer hotStatus, Integer cpyLevel) throws WEBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CpyInfoTb> listPageInfoByOpt(String cpyNamePy, String cpyProv,
			String cpyCity, String cpyFr, String cpyLxr, Integer cpyLevel,
			Integer yxStatus, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByOpt(String cpyNamePy, String cpyProv,
			String cpyCity, String cpyFr, String cpyLxr, Integer cpyLevel,
			Integer yxStatus) throws WEBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CpyInfoTb> listEndDateCpyInfo() throws WEBException {
		// TODO Auto-generated method stub
		try {
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			Session sess = HibernateUtil.currentSession();
			return cDao.findEndDateCpyInfo(sess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  WEBException("获取所有即将到期或者已到期的代理机构（即将到期5、1天内/已到期0,1天内进行邮件提醒）信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
