package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ActRoleInfoDao;
import com.patent.module.ActRoleInfoTb;

@SuppressWarnings("unchecked")
public class ActRoleInfoDaoImpl implements ActRoleInfoDao{

	@Override
	public ActRoleInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ActRoleInfoTb) sess.load(ActRoleInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ActRoleInfoTb arInfo) {
		// TODO Auto-generated method stub
		sess.save(arInfo);
	}

	@Override
	public void delete(Session sess, ActRoleInfoTb arInfo) {
		// TODO Auto-generated method stub
		sess.delete(arInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ActRoleInfoTb arInfo) {
		// TODO Auto-generated method stub
		sess.update(arInfo);
	}
	
	@Override
	public List<ActRoleInfoTb> findSpecInfoByOpt(Session sess,Integer roleId,Integer maId) {
		// TODO Auto-generated method stub
		String hql = " from ActRoleInfoTb as ar where 1=1";
		if(roleId > 0){
			hql += " and ar.cpyRoleInfoTb.id = "+roleId;
		}
		if(maId > 0){
			hql += " and ar.modActInfoTb.id = "+maId;
		}
		return sess.createQuery(hql).list();
	}
}
