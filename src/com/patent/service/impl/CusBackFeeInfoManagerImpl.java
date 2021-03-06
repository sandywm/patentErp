/**
 * 
 */
package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.CusBackFeeInfoDao;
import com.patent.dao.CusPzInfoDao;
import com.patent.dao.CustomerInfoDao;
import com.patent.dao.ZlajFeeInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CpyInfoTb;
import com.patent.module.CpyUserInfo;
import com.patent.module.CusBackFeeInfo;
import com.patent.module.CusPzInfo;
import com.patent.module.CustomerInfoTb;
import com.patent.service.CusBackFeeInfoManager;
import com.patent.tools.CurrentTime;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CusBackFeeInfoManagerImpl implements CusBackFeeInfoManager{

	CpyUserInfoDao uDao = null;
	CpyInfoDao cpyDao = null;
	CustomerInfoDao cusDao = null;
	CusBackFeeInfoDao cbfDao = null;
	ZlajFeeInfoDao feeDao = null;
	CusPzInfoDao pzDao = null;
	Transaction tran = null;
	@Override
	public Integer addCBF(String backFeePrice, String backDate,
			String backType, Integer cusId, Integer cpyId,
			Integer operateUserId, String operateTime, String remark)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			cpyDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			cusDao = (CustomerInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUSTOMER_INFO);
			cbfDao = (CusBackFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUS_BACK_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CusBackFeeInfo cbf = new CusBackFeeInfo(cusDao.get(sess, cusId), uDao.get(sess, operateUserId), cpyDao.get(sess, cpyId),backFeePrice, backDate,backType, 
					operateTime, remark);
			cbfDao.save(sess, cbf);
			tran.commit();
			return cbf.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加客户汇款信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CusBackFeeInfo> listPageInfoByOpt(Integer cpyId, Integer cusId,
			String sDate, String eDate, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			cbfDao = (CusBackFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUS_BACK_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return cbfDao.findPageInfoByOpt(sess, cpyId, cusId, sDate, eDate, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取客户汇款信息列表（汇款时间降序排列）信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer cpyId, Integer cusId, String sDate,
			String eDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			cbfDao = (CusBackFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUS_BACK_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return cbfDao.getCountByOpt(sess, cpyId, cusId, sDate, eDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取客户汇款信息记录条数信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<CusPzInfo> listInfoByOpt(Integer backFeeId, Integer cusId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			pzDao = (CusPzInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUS_PZ_INFO);
			Session sess = HibernateUtil.currentSession();
			return pzDao.findInfoByOpt(sess, backFeeId, cusId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取客户汇款平账记录信息列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer addCusPz(Integer backFeeId, Integer feeId, Double pzPrice,
			Double remainPrice) throws WEBException {
		// TODO Auto-generated method stub
		try {
			feeDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			pzDao = (CusPzInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUS_PZ_INFO);
			cbfDao = (CusBackFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CUS_BACK_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			CusPzInfo pz = new CusPzInfo(cbfDao.get(sess, backFeeId),feeDao.get(sess, feeId), pzPrice, remainPrice,CurrentTime.getCurrentTime());
			pzDao.save(sess, pz);
			tran.commit();
			return pz.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加客户汇款平账信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
