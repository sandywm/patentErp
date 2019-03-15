package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajQqsInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.ZlajQqsInfoTb;
import com.patent.service.ZlajQqsInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajQqsInfoManagerImpl implements ZlajQqsInfoManager{

	@Override
	public List<ZlajQqsInfoTb> listInfoByTypeEng(String typeEng)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			ZlajQqsInfoDao qqsDao = (ZlajQqsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_QQS_INFO);
			Session sess = HibernateUtil.currentSession();
			return qqsDao.findInfoByTypeEng(sess, typeEng);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据提交类型获取请求书列表时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajQqsInfoTb getSpecInfoById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			ZlajQqsInfoDao qqsDao = (ZlajQqsInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_QQS_INFO);
			Session sess = HibernateUtil.currentSession();
			return qqsDao.get(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取请求书详情时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
