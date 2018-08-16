package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CpyJoinInfoDao;
import com.patent.module.CpyJoinInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class CpyJoinInfoDaoImpl implements CpyJoinInfoDao{

	@Override
	public CpyJoinInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CpyJoinInfoTb) sess.load(CpyJoinInfoTb.class, id);
	}

	@Override
	public void save(Session sess, CpyJoinInfoTb cjInfo) {
		// TODO Auto-generated method stub
		sess.save(cjInfo);
	}

	@Override
	public void delete(Session sess, CpyJoinInfoTb cjInfo) {
		// TODO Auto-generated method stub
		sess.delete(cjInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CpyJoinInfoTb cjInfo) {
		// TODO Auto-generated method stub
		sess.update(cjInfo);
	}

	@Override
	public List<CpyJoinInfoTb> findInfoByOpt(Session sess, Integer parCpyId,
			Integer subCpyId, Integer joinStatus, Integer applyCpyId,
			String czDate) {
		// TODO Auto-generated method stub
		String hql = " from CpyJoinInfoTb as cj where 1 = 1";
		if(parCpyId > 0){
			hql += " and cj.parCpyId = "+parCpyId;
		}
		if(subCpyId > 0){
			hql += "and cj.subCpyId = "+subCpyId;
		}
		if(!joinStatus.equals(-1)){
			hql += " and cj.joinStatus = "+joinStatus;
		}
		if(applyCpyId > 0){
			hql += " and cj.cpyInfoTb.id = "+applyCpyId;
		}
		if(!czDate.equals("")){
			hql += " and cj.czDate = '"+czDate+"'";
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<CpyJoinInfoTb> findPageInfoByOpt(Session sess,
			Integer parCpyId, Integer subCpyId, Integer joinStatus,
			Integer applyCpyId, String czDate, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from CpyJoinInfoTb as cj where 1 = 1";
		if(parCpyId > 0){
			hql += " and cj.parCpyId = "+parCpyId;
		}
		if(subCpyId > 0){
			hql += "and cj.subCpyId = "+subCpyId;
		}
		if(!joinStatus.equals(-1)){
			hql += " and cj.joinStatus = "+joinStatus;
		}
		if(applyCpyId > 0){
			hql += " and cj.cpyInfoTb.id = "+applyCpyId;
		}
		if(!czDate.equals("")){
			hql += " and cj.czDate = '"+czDate+"'";
		}
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer parCpyId,
			Integer subCpyId, Integer joinStatus, Integer applyCpyId,
			String czDate) {
		// TODO Auto-generated method stub
		String hql = "select count(cj.id) from CpyJoinInfoTb as cj where 1 = 1";
		if(parCpyId > 0){
			hql += " and cj.parCpyId = "+parCpyId;
		}
		if(subCpyId > 0){
			hql += "and cj.subCpyId = "+subCpyId;
		}
		if(!joinStatus.equals(-1)){
			hql += " and cj.joinStatus = "+joinStatus;
		}
		if(applyCpyId > 0){
			hql += " and cj.cpyInfoTb.id = "+applyCpyId;
		}
		if(!czDate.equals("")){
			hql += " and cj.czDate = '"+czDate+"'";
		}
		Object countObj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(countObj);
	}

	@Override
	public List<CpyJoinInfoTb> findInfoById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from CpyJoinInfoTb as cj where cj.id = "+id;
		return sess.createQuery(hql).list();
	}

}
