package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.PubZlInfoDao;
import com.patent.module.PubZlInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class PubZlInfoDaoImpl implements PubZlInfoDao{

	@Override
	public PubZlInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (PubZlInfoTb) sess.load(PubZlInfoTb.class, id);
	}

	@Override
	public void save(Session sess, PubZlInfoTb pzInfo) {
		// TODO Auto-generated method stub
		sess.save(pzInfo);
	}

	@Override
	public void delete(Session sess, PubZlInfoTb pzInfo) {
		// TODO Auto-generated method stub
		sess.delete(pzInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, PubZlInfoTb pzInfo) {
		// TODO Auto-generated method stub
		sess.update(pzInfo);
	}

	@Override
	public List<PubZlInfoTb> findPageInfoByOpt(Session sess, Integer pubId,
			String zlTitle, String zlNo, String zlType, String pubDate,
			Integer zlStatus, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from PubZlInfoTb as pz where 1=1";
		if(pubId > 0){
			hql += " and pz.applyInfoTb.id = "+pubId;
		}
		if(!zlTitle.equals("")){
			hql += " and pz.zlTitle like '"+zlTitle+"%'";
		}
		if(!zlNo.equals("")){
			hql += " and pz.zlNo like '%"+zlNo+"%'";
		}
		if(!zlType.equals("")){
			hql += " and pz.zlType = '"+zlType+"'";
		}
		if(!pubDate.equals("")){
			hql += " and pz.zlNewDate = '"+pubDate+"'";
		}
		if(zlStatus >= 0){
			hql += " and pz.zlStatus = "+zlStatus;
		}
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer pubId, String zlTitle,
			String zlNo, String zlType, String pubDate, Integer zlStatus) {
		// TODO Auto-generated method stub
		String hql = "select count(pz.id) from PubZlInfoTb as pz where 1=1";
		if(pubId > 0){
			hql += " and pz.applyInfoTb.id = "+pubId;
		}
		if(!zlTitle.equals("")){
			hql += " and pz.zlTitle like '"+zlTitle+"%'";
		}
		if(!zlNo.equals("")){
			hql += " and pz.zlNo like '%"+zlNo+"%'";
		}
		if(!zlType.equals("")){
			hql += " and pz.zlType = '"+zlType+"'";
		}
		if(!pubDate.equals("")){
			hql += " and pz.zlNewDate = '"+pubDate+"'";
		}
		if(zlStatus >= 0){
			hql += " and pz.zlStatus = "+zlStatus;
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<PubZlInfoTb> findSpecInfoByOpt(Session sess, Integer lqCpyId,
			String ajIdStr) {
		// TODO Auto-generated method stub
		String hql = " from PubZlInfoTb as pz where pz.lqCpyId = "+lqCpyId;
		if(ajIdStr.indexOf(",") >= 0){
			//两个案件编号的组合
			hql += " and pz.ajIdStr in ("+ajIdStr+")"; 
		}else{//单个案件
			hql += " and pz.ajIdStr = "+Integer.parseInt(ajIdStr);
		}
		return sess.createQuery(hql).list();
	}

}
