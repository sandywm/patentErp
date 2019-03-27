package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajLcInfoDao;
import com.patent.module.ZlajLcInfoTb;

@SuppressWarnings("unchecked")
public class ZlajLcInfoDaoImpl implements ZlajLcInfoDao{

	@Override
	public ZlajLcInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajLcInfoTb) sess.load(ZlajLcInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajLcInfoTb lcInfo) {
		// TODO Auto-generated method stub
		sess.save(lcInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajLcInfoTb lcInfo) {
		// TODO Auto-generated method stub
		sess.update(lcInfo);
	}

	@Override
	public List<ZlajLcInfoTb> findInfo(Session sess, Integer ajId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+ajId + " order by lc.id desc";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcInfoTb> findInfoById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.id = "+id;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcInfoTb> findInfoByLcMz(Session sess, Integer ajId, String lcMz) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+ajId + " and lc.lcMz = '"+lcMz+"'";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcInfoTb> findLastInfo(Session sess, Integer ajId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+ajId + " and lc.lcEDate = ''  order by lc.lcNo desc";
		return sess.createQuery(hql).setFirstResult(0).setMaxResults(1).list();
	}

	@Override
	public List<ZlajLcInfoTb> findInfoByZlId(Session sess, Integer zlId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+zlId + " order by lc.id desc";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcInfoTb> findUnComInfoByOpt(Session sess, Integer cpyId,
			String lcTask,String ajNo,String ajTitle,Integer cusId,Integer createStatus) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.cpyInfoTb.id = "+cpyId + " and lc.lcDetail = '"+lcTask+"' and lc.lcEDate = '' and lc.zlajMainInfoTb.ajStopStatus = 0";
		if(!ajNo.equals("")){
			hql += " and lc.zlajMainInfoTb.ajNo like '%"+ajNo+"%'";
		}else if(!ajTitle.equals("")){
			hql += " and lc.zlajMainInfoTb.ajTitle like '%"+ajTitle+"%'";
		}
		if(cusId > 0){
			hql += " and lc.zlajMainInfoTb.cpyInfoTb.id = "+cpyId;
		}
		if(createStatus > 0){
			hql += " and lc.createStatus = "+createStatus;
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcInfoTb> findInfoByQrhId(Session sess, Integer cpyId,
			Integer qrhId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.cpyInfoTb.id = "+cpyId + " and lc.qrhId = "+qrhId;
		return sess.createQuery(hql).list();
	}

}
