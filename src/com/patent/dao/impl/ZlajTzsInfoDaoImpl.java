package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajTzsInfoDao;
import com.patent.module.ZlajTzsInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class ZlajTzsInfoDaoImpl implements ZlajTzsInfoDao{

	@Override
	public ZlajTzsInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajTzsInfoTb) sess.load(ZlajTzsInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajTzsInfoTb tzsInfo) {
		// TODO Auto-generated method stub
		sess.save(tzsInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajTzsInfoTb tzsInfo) {
		// TODO Auto-generated method stub
		sess.update(tzsInfo);
	}

	@Override
	public ZlajTzsInfoTb getEntityById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajTzsInfoTb as tzs where tzs.id = "+id;
		List<ZlajTzsInfoTb> tzsList = sess.createQuery(hql).list();
		if(tzsList.size() > 0){
			return tzsList.get(0);
		}
		return null;
	}

	@Override
	public List<ZlajTzsInfoTb> findInfoByAjId(Session sess, Integer ajId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajTzsInfoTb as tzs where tzs.ajId = "+ajId + "  and tzs.readStatus = 1";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajTzsInfoTb> findInfoByOpt(Session sess, Integer ajId,
			String fwSerial) {
		// TODO Auto-generated method stub
		String hql = " from ZlajTzsInfoTb as tzs where  tzs.ajId = "+ajId + " and tzs.fwSerial = '"+fwSerial+"' and tzs.readStatus = 1";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajTzsInfoTb> findPageInfoByOpt(Session sess,Integer cpyId,Integer zlId,String ajNo,Integer readStatus,Integer pageNo,Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from ZlajTzsInfoTb as tzs where tzs.cpy.id = "+cpyId;
		if(zlId > 0){
			hql += " and tzs.zlajMainInfoTb.id = "+zlId;
		}
		if(!ajNo.equals("")){
			hql += " and tzs.ajNo like '%"+ajNo+"%'";
		}
		if(!readStatus.equals(2)){
			hql += " and tzs.readStatus = "+readStatus;
		}
		hql += " order by id desc";
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer cpyId, Integer zlId,String ajNo,
			Integer readStatus) {
		// TODO Auto-generated method stub
		String hql = "select count(tzs.id) from ZlajTzsInfoTb as tzs where tzs.cpy.id = "+cpyId;
		if(zlId > 0){
			hql += " and tzs.ajId = "+zlId;
		}
		if(!ajNo.equals("")){
			hql += " and tzs.ajNo like '%"+ajNo+"%'";
		}
		if(!readStatus.equals(2)){
			hql += " and tzs.readStatus = "+readStatus;
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

}
