package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajLcYjInfoDao;
import com.patent.module.ZlajLcYjInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class ZlajLcYjInfoDaoImpl implements ZlajLcYjInfoDao{

	@Override
	public ZlajLcYjInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajLcYjInfoTb) sess.load(ZlajLcYjInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajLcYjInfoTb lcyj) {
		// TODO Auto-generated method stub
		sess.save(lcyj);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajLcYjInfoTb lcyj) {
		// TODO Auto-generated method stub
		sess.update(lcyj);
	}

	@Override
	public ZlajLcYjInfoTb getEntityById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcYjInfoTb as lcyj where lcyj.id = "+id;
		List<ZlajLcYjInfoTb> l = sess.createQuery(hql).list();
		if(l.size() > 0){
			return l.get(0);
		}
		return null;
		
	}

	@Override
	public List<ZlajLcYjInfoTb> findPageInfoByOpt(Session sess,
			Integer applyUserId, Integer checkStatus, Integer checkUserId,
			Integer cpyId, String zlTitle,String ajNo,String zlNo,Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcYjInfoTb as lcyj where lcyj.cpy.id = "+cpyId + " and lcyj.checkStatus = "+checkStatus;
		if(applyUserId > 0){
			hql += " and lcyj.user.id = "+applyUserId;
		}
		if(checkUserId > 0){
			hql += " and lcyj.checkUserId = "+checkUserId;
		}
		if(!zlTitle.equals("")){
			hql += " and lcyj.lcmx.zlajLcInfoTb.zlajMainInfoTb.ajTitle like '%"+zlTitle+"%'";
		}
		if(!ajNo.equals("")){
			hql += " and lcyj.lcmx.zlajLcInfoTb.zlajMainInfoTb.ajNoQt like '%"+ajNo+"%'";
		}
		if(!zlNo.equals("")){
			hql += " and lcyj.lcmx.zlajLcInfoTb.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}
		hql += " order by lcyj.id desc";
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer applyUserId,
			Integer checkStatus, Integer checkUserId, Integer cpyId,
			String zlTitle,String ajNo,String zlNo) {
		// TODO Auto-generated method stub
		String hql = "select count(lcyj.id) from ZlajLcYjInfoTb as lcyj where lcyj.cpy.id = "+cpyId + " and lcyj.checkStatus = "+checkStatus;
		if(applyUserId > 0){
			hql += " and lcyj.user.id = "+applyUserId;
		}
		if(checkUserId > 0){
			hql += " and lcyj.checkUserId = "+checkUserId;
		}
		if(!zlTitle.equals("")){
			hql += " and lcyj.lcmx.zlajLcInfoTb.zlajMainInfoTb.ajTitle like '%"+zlTitle+"%'";
		}
		if(!ajNo.equals("")){
			hql += " and lcyj.lcmx.zlajLcInfoTb.zlajMainInfoTb.ajNoQt like '%"+ajNo+"%'";
		}
		if(!zlNo.equals("")){
			hql += " and lcyj.lcmx.zlajLcInfoTb.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<ZlajLcYjInfoTb> findUnCheckInfoByOpt(Session sess, String lcTask,Integer zlId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcYjInfoTb as lcyj where lcyj.lcName = '"+lcTask+"' and lcyj.lcmx.zlajLcInfoTb.zlajMainInfoTb.id = "+zlId + " and lcyj.checkStatus = 0";
		return sess.createQuery(hql).list();
	}

	@Override
	public ZlajLcYjInfoTb findEntityByOpt(Session sess, Integer applyUserId,
			String lcTask, Integer zlId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcYjInfoTb as lcyj where lcyj.lcName = '"+lcTask+"'";
		hql += " and lcyj.lcmx.zlajLcInfoTb.zlajMainInfoTb.id = "+zlId + " and lcyj.checkStatus = 0";
		hql += " and lcyj.user.id = "+applyUserId;
		List<ZlajLcYjInfoTb> yjList = sess.createQuery(hql).list();
		if(yjList.size() > 0){
			return yjList.get(0);
		}
		return null;
	}

}
