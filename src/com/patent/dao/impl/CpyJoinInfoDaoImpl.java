package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CpyJoinInfoDao;
import com.patent.module.CpyJoinInfoTb;

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
		return null;
	}

	@Override
	public List<CpyJoinInfoTb> findPageInfoByOpt(Session sess,
			Integer parCpyId, Integer subCpyId, Integer joinStatus,
			Integer applyCpyId, String czDate, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer parCpyId,
			Integer subCpyId, Integer joinStatus, Integer applyCpyId,
			String czDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
