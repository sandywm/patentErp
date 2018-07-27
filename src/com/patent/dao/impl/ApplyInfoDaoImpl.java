package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ApplyInfoDao;
import com.patent.module.ApplyInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class ApplyInfoDaoImpl implements ApplyInfoDao{

	@Override
	public ApplyInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ApplyInfoTb) sess.load(ApplyInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ApplyInfoTb aInfo) {
		// TODO Auto-generated method stub
		sess.save(aInfo);
	}

	@Override
	public void delete(Session sess, ApplyInfoTb aInfo) {
		// TODO Auto-generated method stub
		sess.delete(aInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ApplyInfoTb aInfo) {
		// TODO Auto-generated method stub
		sess.update(aInfo);
	}

	@Override
	public List<ApplyInfoTb> findPageInfoByOpt(Session sess,String appType,
			String appNamePy, String lxrTel, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from ApplyInfoTb as ai where 1 = 1";
		if(!appType.equals("")){
			hql += " and ai.appType = '"+appType+"'";
		}
		if(!appNamePy.equals("")){
			hql += " and ai.appNamePy like '%"+appNamePy+"%'";
		}
		if(!lxrTel.equals("")){
			hql += " and ai.lxrTel = '"+lxrTel+"'";
		}
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess,String appType, String appNamePy, String lxrTel) {
		// TODO Auto-generated method stub
		String hql = "select count(ai.id) from ApplyInfoTb as ai where 1 = 1";
		if(!appType.equals("")){
			hql += " and ai.appType = '"+appType+"'";
		}
		if(!appNamePy.equals("")){
			hql += " and ai.appNamePy like '%"+appNamePy+"%'";
		}
		if(!lxrTel.equals("")){
			hql += " and ai.lxrTel = '"+lxrTel+"'";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public ApplyInfoTb getEntityById(Session sess,Integer appId) {
		// TODO Auto-generated method stub
		String hql = " from ApplyInfoTb as ai where ai.id = "+appId;
		List<ApplyInfoTb> appList = sess.createQuery(hql).list();
		if(appList.size() > 0){
			return appList.get(0);
		}
		return null;
	}

	@Override
	public List<ApplyInfoTb> findInfoByAccount(Session sess, String account) {
		// TODO Auto-generated method stub
		String hql = " from ApplyInfoTb as ai where ai.appAccount = '"+account+"'";
		return sess.createQuery(hql).list();
	}

}
