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
	public List<PubZlInfoTb> findSpecInfoByOpt_1(Session sess,Integer lqCpyId,Integer ajId){
		// TODO Auto-generated method stub
		String hql = " from PubZlInfoTb as pz where pz.lqCpyId = "+lqCpyId;
		if(ajId > 0){
			hql += " and pz.ajIdStr = "+ajId;
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<PubZlInfoTb> findSpecInfoByOpt(Session sess, Integer id,
			Integer pubId) {
		// TODO Auto-generated method stub
		String hql = " from PubZlInfoTb as pz where pz.id = "+id;
		if(pubId > 0){
			hql += " and pz.applyInfoTb.id = "+pubId;
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<PubZlInfoTb> findSpecInfoByOpt_2(Session sess, Integer lqCpyId,Integer addStatus,boolean pageFlag,Integer pageNo,Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from PubZlInfoTb as pz where pz.lqCpyId = "+lqCpyId;
		if(addStatus.equals(1)){
			hql += " and pz.ajId > 0";
		}else if(addStatus.equals(0)){
			hql += " and pz.ajId = 0";
		}
		if(pageFlag){
			int offset = (pageNo - 1) * pageSize;
			if (offset < 0) {
				offset = 0;
			}
			return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public Integer getCountByOpt_2(Session sess, Integer lqCpyId,
			Integer addStatus) {
		// TODO Auto-generated method stub
		String hql = "select count(pz.id) from PubZlInfoTb as pz where pz.lqCpyId = "+lqCpyId;
		if(addStatus.equals(1)){
			hql += " and pz.ajId > 0";
		}else if(addStatus.equals(0)){
			hql += " and pz.ajId = 0";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

}
