package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CpyBonusInfoDao;
import com.patent.module.CpyBonusInfo;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class CpyBonusInfoDaoImpl implements CpyBonusInfoDao{

	@Override
	public CpyBonusInfo get(Session sess, int id) {
		// TODO Auto-generated method stub
		String hql = " from CpyBonusInfo as cb where cb.id = "+id;
		List<CpyBonusInfo> cbList = sess.createQuery(hql).list();
		if(cbList.size() > 0){
			return cbList.get(0);
		}
		return null;
	}

	@Override
	public void save(Session sess, CpyBonusInfo cbInfo) {
		// TODO Auto-generated method stub
		sess.save(cbInfo);
	}

	@Override
	public void update(Session sess, CpyBonusInfo cbInfo) {
		// TODO Auto-generated method stub
		sess.update(cbInfo);
	}

	@Override
	public List<CpyBonusInfo> findPageInfoByOpt(Session sess, String workType,
			String zlType, Integer zlLevel, Integer cpyId, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		String hql =" from CpyBonusInfo as cb where cb.cpyInfoTb.id = "+cpyId;
		if(!workType.equals("")){
			hql += " and cb.workType = '"+workType+"'";
		}
		if(!zlType.equals("")){
			hql += " and cb.zlType = '"+zlType+"'";
		}
		if(zlLevel > 0){
			hql += " and cb.zlLevel = "+zlLevel;
		}
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, String workType, String zlType,
			Integer zlLevel, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = "select count(cb.id) from CpyBonusInfo as cb where cb.cpyInfoTb.id = "+cpyId;
		if(!workType.equals("")){
			hql += " and cb.workType = '"+workType+"'";
		}
		if(!zlType.equals("")){
			hql += " and cb.zlType = '"+zlType+"'";
		}
		if(zlLevel > 0){
			hql += " and cb.zlLevel = "+zlLevel;
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<CpyBonusInfo> findInfoByOpt(Session sess, String workType,
			String zlType, Integer zlLevel, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql =" from CpyBonusInfo as cb where cb.cpyInfoTb.id = "+cpyId + " and cb.workType = '"+workType+"'";
		hql += " and cb.zlType = '"+zlType+"' and cb.zlLevel = "+zlLevel;
		return sess.createQuery(hql).list();
	}

}
