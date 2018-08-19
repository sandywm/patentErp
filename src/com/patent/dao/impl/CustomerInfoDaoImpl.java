package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CustomerInfoDao;
import com.patent.module.CustomerInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class CustomerInfoDaoImpl implements CustomerInfoDao{

	@Override
	public CustomerInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CustomerInfoTb) sess.load(CustomerInfoTb.class, id);
	}

	@Override
	public void save(Session sess, CustomerInfoTb cusInfo) {
		// TODO Auto-generated method stub
		sess.save(cusInfo);
	}

	@Override
	public void delete(Session sess, CustomerInfoTb cusInfo) {
		// TODO Auto-generated method stub
		sess.delete(cusInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CustomerInfoTb cusInfo) {
		// TODO Auto-generated method stub
		sess.update(cusInfo);
	}
	
	@Override
	public List<CustomerInfoTb> findPageInfoByCpyId(Session sess, Integer cpyId,String cusName,Integer pageNo,Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from CustomerInfoTb as ci where ci.cpyInfoTb.id = "+cpyId;
		if(!cusName.equals("")){
			hql += " and ci.cusName like '"+cusName+"'";
		}
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByCpyId(Session sess, Integer cpyId,String cusName) {
		// TODO Auto-generated method stub
		String hql = "select count(ci.id) from CustomerInfoTb as ci where ci.cpyInfoTb.id = "+cpyId;
		if(!cusName.equals("")){
			hql += " and ci.cusName like '"+cusName+"'";
		}
		Object countObj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(countObj);
	}

	@Override
	public List<CustomerInfoTb> findInfoByOpt(Session sess, Integer cpyId,
			Integer id) {
		// TODO Auto-generated method stub
		String hql = " from CustomerInfoTb as ci where ci.id = "+id + " and ci.cpyInfoTb.id = "+cpyId;
		return sess.createQuery(hql).list();
	}

}
