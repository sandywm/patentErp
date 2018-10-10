package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajFeeInfoDao;
import com.patent.module.ZlajFeeInfoTb;

@SuppressWarnings("unchecked")
public class ZlajFeeInfoDaoImpl implements ZlajFeeInfoDao{

	@Override
	public ZlajFeeInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajFeeInfoTb) sess.load(ZlajFeeInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajFeeInfoTb feeInfo) {
		// TODO Auto-generated method stub
		sess.save(feeInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajFeeInfoTb feeInfo) {
		// TODO Auto-generated method stub
		sess.update(feeInfo);
	}

	@Override
	public ZlajFeeInfoTb getFeeEntityById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.id = "+id;
		List<ZlajFeeInfoTb> zlfList = sess.createQuery(hql).list();
		if(zlfList.size() > 0){
			return zlfList.get(0);
		}
		return null;
	}

	@Override
	public List<ZlajFeeInfoTb> findInfoByOpt(Session sess, Integer zlId,
			String feeTypeStatus) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.zlajMainInfoTb.id = "+zlId;
		if(!feeTypeStatus.equals("")){
			if(feeTypeStatus.equals("nf")){
				hql += " and zlf.feeTypeInfoTb.feeStatus like '%"+feeTypeStatus+"%'";
			}else{
				hql += " and zlf.feeTypeInfoTb.feeStatus = '"+feeTypeStatus+"'";
			}
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findInfoByOpt(Session sess, Integer zlId,
			Integer feeTypeId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.zlajMainInfoTb.id = "+zlId + " and zlf.feeTypeInfoTb.id = "+feeTypeId;
		return sess.createQuery(hql).list();
	}

}
