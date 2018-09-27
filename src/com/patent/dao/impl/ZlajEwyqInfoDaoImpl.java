package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajEwyqInfoDao;
import com.patent.module.ZlajEwyqInfoTb;

@SuppressWarnings("unchecked")
public class ZlajEwyqInfoDaoImpl implements ZlajEwyqInfoDao{

	@Override
	public ZlajEwyqInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajEwyqInfoTb) sess.load(ZlajEwyqInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajEwyqInfoTb yqInfo) {
		// TODO Auto-generated method stub
		sess.save(yqInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajEwyqInfoTb yqInfo) {
		// TODO Auto-generated method stub
		sess.update(yqInfo);
	}

	@Override
	public List<ZlajEwyqInfoTb> findInfo(Session sess, String yqType) {
		// TODO Auto-generated method stub
		String hql = " from ZlajEwyqInfoTb as yq";
		if(!yqType.equals("")){
//			if(yqType.indexOf(",") > 0){//说明是发明+实用新型
//				hql += " where yq.yqType = '%"+yqType+"%'";
//			}else{
//				hql += " where yq.yqType = '"+yqType+"'";
//			}
			hql += " where yq.yqType like '%"+yqType+"%'";
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public ZlajEwyqInfoTb getEntityById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String  hql = " from ZlajEwyqInfoTb as yq where yq.id = "+id;
		List<ZlajEwyqInfoTb> l = sess.createQuery(hql).list();
		if(l.size() > 0){
			return l.get(0);
		}
		return null;
	}

	@Override
	public List<ZlajEwyqInfoTb> findInfoByCnt(Session sess, String yqContent) {
		// TODO Auto-generated method stub
		String hql = " from ZlajEwyqInfoTb as yq where yq.yqContent = '"+yqContent+"'";
		return sess.createQuery(hql).list();
	}

}
