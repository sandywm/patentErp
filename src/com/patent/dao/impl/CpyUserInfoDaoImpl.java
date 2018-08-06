package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CpyUserInfoDao;
import com.patent.module.CpyUserInfo;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class CpyUserInfoDaoImpl implements CpyUserInfoDao{

	@Override
	public CpyUserInfo get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CpyUserInfo) sess.load(CpyUserInfo.class, id);
	}

	@Override
	public void save(Session sess, CpyUserInfo cpyUser) {
		// TODO Auto-generated method stub
		sess.save(cpyUser);
	}

	@Override
	public void delete(Session sess, CpyUserInfo cpyUser) {
		// TODO Auto-generated method stub
		sess.delete(cpyUser);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CpyUserInfo cpyUser) {
		// TODO Auto-generated method stub
		sess.update(cpyUser);
	}

	@Override
	public List<CpyUserInfo> findInfoByAccount(Session sess, String account) {
		// TODO Auto-generated method stub
		String hql = " from CpyUserInfo as cu where cu.userAccount = '"+account+"'";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<CpyUserInfo> findInfoById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = "from CpyUserInfo as cu where cu.id = "+id;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<CpyUserInfo> findPageInfoByOpt(Session sess, Integer cpyId,
			Integer userLzStatus,Integer userYxStatus,Integer roleId, String userNamePy,
			Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql =" from CpyUserInfo as cu where cu.cpyInfoTb.id = "+cpyId;
		if(userLzStatus.equals(-1)){
			hql += " and cu.userLzStatus = "+userLzStatus;
		}
		if(userYxStatus.equals(-1)){
			hql += " and cu.userYxStatus = "+userYxStatus;
		}
		if(!userNamePy.equals("")){
			hql += " and cu.userNamePy like '%"+userNamePy+"%'";
		}
		if(roleId > 0){
			hql += " and exists(select cru.id from CpyRoleUserInfoTb as cru where cru.cpyUserInfo.id = cu.id and cru.cpyRoleInfoTb.id = "+roleId +" )";
		}
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer cpyId,
			Integer userLzStatus,Integer userYxStatus,Integer roleId, String userNamePy) {
		// TODO Auto-generated method stub
		String hql ="select count(cu.id) from CpyUserInfo as cu where cu.cpyInfoTb.id = "+cpyId;
		if(userLzStatus.equals(-1)){
			hql += " and cu.userLzStatus = "+userLzStatus;
		}
		if(userYxStatus.equals(-1)){
			hql += " and cu.userYxStatus = "+userYxStatus;
		}
		if(!userNamePy.equals("")){
			hql += " and cu.userNamePy like '%"+userNamePy+"%'";
		}
		if(roleId > 0){
			hql += " and exists(select cru.id from CpyRoleUserInfoTb as cru where cru.cpyUserInfo.id = cu.id and cru.cpyRoleInfoTb.id = "+roleId +" )";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<CpyUserInfo> findManagerInfoByOpt(Session sess, Integer cpyId,
			String roleName) {
		// TODO Auto-generated method stub
		String hql = " from CpyUserInfo as cu where cu.cpyInfoTb.id = "+cpyId;
		hql += " and cu.userLzStatus = 1 and cu.userYxStatus = 1";
		hql += " and exists(select cru.id from CpyRoleUserInfoTb as cru where cru.cpyUserInfo.id = cu.id and cru.cpyRoleInfoTb.roleName = '"+roleName+"' )";
		return sess.createQuery(hql).list();
	}

}
