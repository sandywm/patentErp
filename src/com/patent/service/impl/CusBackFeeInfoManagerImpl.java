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
import com.patent.dao.CustomerInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.CusBackFeeInfo;
import com.patent.service.CusBackFeeInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class CusBackFeeInfoManagerImpl implements CusBackFeeInfoManager{

	CpyUserInfoDao uDao = null;
	CpyInfoDao cpyDao = null;
	CustomerInfoDao cusDao = null;
	CusBackFeeInfoDao cbfDao = null;
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
			CusBackFeeInfo cbf = new CusBackFeeInfo(backFeePrice, backDate,backType, 
					cusDao.get(sess, cusId), cpyDao.get(sess, cpyId),
					uDao.get(sess, operateUserId), operateTime, remark);
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

}
