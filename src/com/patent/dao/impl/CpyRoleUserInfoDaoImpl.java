package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CpyRoleUserInfoDao;
import com.patent.module.CpyRoleUserInfoTb;

@SuppressWarnings("unchecked")
public class CpyRoleUserInfoDaoImpl implements CpyRoleUserInfoDao{

	@Override
	public CpyRoleUserInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CpyRoleUserInfoTb) sess.load(CpyRoleUserInfoTb.class, id);
	}

	@Override
	public void save(Session sess, CpyRoleUserInfoTb cpyRuInfo) {
		// TODO Auto-generated method stub
		sess.save(cpyRuInfo);
	}

	@Override
	public void delete(Session sess, CpyRoleUserInfoTb cpyRuInfo) {
		// TODO Auto-generated method stub
		sess.delete(cpyRuInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CpyRoleUserInfoTb cpyRuInfo) {
		// TODO Auto-generated method stub
		sess.update(cpyRuInfo);
	}

	
	@Override
	public List<CpyRoleUserInfoTb> findInfoByOpt(Session sess, Integer roleId,
			Integer userId) {
		// TODO Auto-generated method stub
		String hql = " from CpyRoleUserInfoTb as cru where 1 = 1";
		if(roleId > 0){
			hql += " and cru.cpyRoleInfoTb.id = "+roleId;
		}
		if(userId > 0){
			hql += " and cru.cpyUserInfo.id = "+userId;
		}
		return sess.createQuery(hql).list();
	}

}
