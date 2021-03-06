package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajLcMxInfoDao;
import com.patent.module.ZlajLcMxInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class ZlajLcMxInfoDaoImpl implements ZlajLcMxInfoDao{

	@Override
	public ZlajLcMxInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajLcMxInfoTb) sess.load(ZlajLcMxInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajLcMxInfoTb lcMxInfo) {
		// TODO Auto-generated method stub
		sess.save(lcMxInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajLcMxInfoTb lcMxInfo) {
		// TODO Auto-generated method stub
		sess.update(lcMxInfo);
	}

	@Override
	public List<ZlajLcMxInfoTb> findDetailInfoByLcId(Session sess, Integer lcId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.id = "+lcId + " order by lcmx.id desc";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findLastInfoByLcId(Session sess, Integer lcId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.id = "+lcId + " order by lcmx.id desc";
		return sess.createQuery(hql).setFirstResult(0).setMaxResults(1).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findFirstInfoByLcId(Session sess, Integer lcId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.id = "+lcId + " and lcmx.lcFzUserId = 0";
		return sess.createQuery(hql).setFirstResult(0).setMaxResults(1).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> listUnComInfoByOpt(Session sess,
			String lcMxName, Double lcMxNo, Integer lcId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.id = "+lcId + " and lcmx.lcMxName = '"+lcMxName+"' and lcmx.lcMxNo = "+lcMxNo + " and lcmx.lcMxEDate = ''";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findDetailInfoById(Session sess, Integer mxId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.id = "+mxId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findSpecInfoByOpt(Session sess,
			Integer zlId, String lcMxName) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.zlajMainInfoTb.id = "+zlId + " and lcmx.lcMxName = '"+lcMxName+"'";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findLcMxByOpt(Session sess, Integer fzUserId,Integer comStatus, 
			String zlNo,String ajNo,String zlTitle,Integer cusId,Integer cpyId, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.lcMxNo >= 3";
		if(fzUserId > 0){//指定流程负责人
			hql += " and lcmx.lcFzUserId = "+fzUserId;
		}else{//管理员可获取所有，必须要指定代理机构
			hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.cpyInfoTb.id = "+cpyId;
		}
		if(comStatus.equals(0)){//未完成
			//需要获取所有没有移交任务或者移交审核未通过的
			hql += " and lcmx.lcMxEDate = '' and lcmx.yjCheckStatus != 0" ;
		}else if(comStatus.equals(1)){
			hql += " and lcmx.lcMxEDate != ''";
		}
		if(!zlNo.equals("")){
			hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}
		if(!ajNo.equals("")){
			hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.ajNoQt like '%"+ajNo+"%'";
		}
		if(!zlTitle.equals("")){
			hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.ajTitle like '%"+zlTitle+"%'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",lcmx.zlajLcInfoTb.zlajMainInfoTb.ajSqrId) > 0";
		}
		hql += " order by lcmx.zlajLcInfoTb.lcCpyDate asc";
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer fzUserId,Integer comStatus, String zlNo,
			String ajNo,String zlTitle,Integer cusId,Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = "select count(lcmx.id)  from ZlajLcMxInfoTb as lcmx where lcmx.lcMxNo >= 3";
		if(fzUserId > 0){//指定流程负责人
			hql += " and lcmx.lcFzUserId = "+fzUserId;
		}else{//管理员可获取所有，必须要指定代理机构
			hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.cpyInfoTb.id = "+cpyId;
		}
		if(comStatus.equals(0)){//未完成
			//需要获取所有没有移交任务或者移交审核通过/未通过的
			hql += " and lcmx.lcMxEDate = '' and lcmx.yjCheckStatus != 0" ;
		}else if(comStatus.equals(1)){
			hql += " and lcmx.lcMxEDate != ''";
		}
		if(!zlNo.equals("")){
			hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}
		if(!ajNo.equals("")){
			hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.ajNoQt like '%"+ajNo+"%'";
		}
		if(!zlTitle.equals("")){
			hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.ajTitle like '%"+zlTitle+"%'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",lcmx.zlajLcInfoTb.zlajMainInfoTb.ajSqrId) > 0";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<ZlajLcMxInfoTb> findUnComLcMxByOpt(Session sess,
			Integer fzUserId, Integer zlId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.lcFzUserId = "+fzUserId + " and lcmx.lcMxEDate != ''";
		hql += " and lcmx.zlajLcInfoTb.zlajMainInfoTb.id = "+zlId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findSpecFjInfoByOpt(Session sess, Integer zlId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where exists(";
		hql += " select lc.id from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+zlId;
		hql += " and (lc.lcDetail like '%补正通知书%' or lc.lcDetail like '%审查意见通知书%' or lc.lcDetail = '补正提交') and lc.id = lcmx.zlajLcInfoTb.id order by lc.id asc)";
		return sess.createQuery(hql).list();
	}

}
