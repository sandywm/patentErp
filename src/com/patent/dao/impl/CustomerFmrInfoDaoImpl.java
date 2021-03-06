package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CustomerFmrInfoDao;
import com.patent.module.CustomerFmrInfoTb;

@SuppressWarnings("unchecked")
public class CustomerFmrInfoDaoImpl implements CustomerFmrInfoDao{

	@Override
	public CustomerFmrInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CustomerFmrInfoTb) sess.load(CustomerFmrInfoTb.class, id);
	}

	@Override
	public void save(Session sess, CustomerFmrInfoTb cusLxrInfo) {
		// TODO Auto-generated method stub
		sess.save(cusLxrInfo);
	}

	@Override
	public void delete(Session sess, CustomerFmrInfoTb cusLxrInfo) {
		// TODO Auto-generated method stub
		sess.delete(cusLxrInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CustomerFmrInfoTb cusLxrInfo) {
		// TODO Auto-generated method stub
		sess.update(cusLxrInfo);
	}

	@Override
	public List<CustomerFmrInfoTb> findInfoByCusId(Session sess, Integer cusId,Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from CustomerFmrInfoTb as cf where cf.customerInfoTb.id = "+cusId + " and cf.customerInfoTb.cpyInfoTb.id = "+ cpyId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<CustomerFmrInfoTb> findInfoByOpt(Session sess, Integer fmrId,
			Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from CustomerFmrInfoTb as cf where cf.customerInfoTb.cpyInfoTb.id = "+ cpyId;
		if(fmrId > 0){
			hql += " and cf.id = "+fmrId;
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<CustomerFmrInfoTb> findInfoByOpt(Session sess, String fmrName,
			String fmrIcard, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from CustomerFmrInfoTb as cf where cf.customerInfoTb.cpyInfoTb.id = "+ cpyId + " and cf.cusFmrName = '"+fmrName+"'";
		if(!fmrIcard.equals("")){
			hql += " and cf.cusFmrICard = '"+fmrIcard+"'";
		}
		return sess.createQuery(hql).list();
	}

}
