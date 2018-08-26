package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CustomerLxrInfoDao;
import com.patent.module.CustomerLxrInfoTb;

@SuppressWarnings("unchecked")
public class CustomerLxrInfoDaoImpl implements CustomerLxrInfoDao{

	@Override
	public CustomerLxrInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CustomerLxrInfoTb) sess.load(CustomerLxrInfoTb.class, id);
	}

	@Override
	public void save(Session sess, CustomerLxrInfoTb cusLxrInfo) {
		// TODO Auto-generated method stub
		sess.save(cusLxrInfo);
	}

	@Override
	public void delete(Session sess, CustomerLxrInfoTb cusLxrInfo) {
		// TODO Auto-generated method stub
		sess.delete(cusLxrInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CustomerLxrInfoTb cusLxrInfo) {
		// TODO Auto-generated method stub
		sess.update(cusLxrInfo);
	}

	@Override
	public List<CustomerLxrInfoTb> findInfoByCusId(Session sess, Integer cusId) {
		// TODO Auto-generated method stub
		String hql = " from CustomerLxrInfoTb as cli where cli.customerInfoTb.id = "+cusId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<CustomerLxrInfoTb> findInfoByCusId(Session sess, Integer lxrId,
			Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from CustomerLxrInfoTb as cli where cli.customerInfoTb.cpyInfoTb.id = "+cpyId;
		if(lxrId > 0){
			hql += " and cli.id = "+lxrId;
		}
		return sess.createQuery(hql).list();
	}

}
