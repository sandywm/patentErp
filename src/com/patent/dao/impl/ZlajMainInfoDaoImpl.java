package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajMainInfoDao;
import com.patent.module.ZlajMainInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class ZlajMainInfoDaoImpl implements ZlajMainInfoDao{

	@Override
	public ZlajMainInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajMainInfoTb) sess.load(ZlajMainInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajMainInfoTb ajInfo) {
		// TODO Auto-generated method stub
		sess.save(ajInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajMainInfoTb ajInfo) {
		// TODO Auto-generated method stub
		sess.update(ajInfo);
	}

	@Override
	public List<ZlajMainInfoTb> findPageInfoByOpt(Session sess, Integer cpyId,
			Integer stopStatus, String sqAddress, String ajNoQt, String zlNo,
			String ajTitle, String ajType, Integer cusId, String sDate,
			String eDate,Integer lqStatus,Integer ajAddUserId, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		} 
		String hql = " from ZlajMainInfoTb as zl where 1=1";
		if(cpyId > 0){
			hql += " and zl.cpyInfoTb.id = "+cpyId;
		}
		if(stopStatus > 0){
			hql += " and zl.ajStopStatus = "+stopStatus;
		}
		if(!ajTitle.equals("")){
			hql += " and zl.ajTitle like '%"+ajTitle+"%'";
		}
		if(!sqAddress.equals("")){
			hql += " and zl.sqAddress = '"+sqAddress+"'";
		}
		if(!ajNoQt.equals("")){
			hql += " and zl.ajNoQt like '%"+ajNoQt+"%'";
		}
		if(!zlNo.equals("")){
			hql += " and zl.ajNoGf like '%"+zlNo+"%'";
		}
		if(!ajType.equals("")){
			hql += " and zl.ajType = '"+ajType+"'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",zl.ajSqrId) > 0";
		}
		if(!sDate.equals("")){
			hql += " and SUBSTR(zl.ajAddDate,1,7) >= '"+sDate+"' and SUBSTR(zl.ajAddDate,1,7) <= '"+eDate+"'";
		}
		if(lqStatus.equals(0)){
			hql += " and (( zl.zxUserId = 0 and zl.ajType1 = 'new') or (zl.tzsUserId = 0 and zl.ajType1 = 'old'))";
		}else if(lqStatus.equals(1)){
			hql += " and zl.ajStatus >= 3.0";//新案的话3.0时流程分配完成，旧案时是7.0
		}else if(lqStatus.equals(2)){//撰写任务领取只针对新案
			hql += " and zl.zxUserId = 0 and zl.tzsUserId > 0 and zl.ajType1 = 'new'";
		}else if(lqStatus.equals(3)){
			hql += " and zl.ajAddUserId = "+ajAddUserId;
		}
		hql += " order by zl.id desc";
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer cpyId,
			Integer stopStatus, String sqAddress, String ajNoQt, String zlNo,
			String ajTitle, String ajType, Integer cusId, String sDate,
			String eDate,Integer lqStatus,Integer ajAddUserId) {
		// TODO Auto-generated method stub
		String hql = "select count(zl.id) from ZlajMainInfoTb as zl where 1=1";
		if(cpyId > 0){
			hql += " and zl.cpyInfoTb.id = "+cpyId;
		}
		if(stopStatus > 0){
			hql += " and zl.ajStopStatus = "+stopStatus;
		}
		if(!ajTitle.equals("")){
			hql += " and zl.ajTitle like '%"+ajTitle+"%'";
		}
		if(!sqAddress.equals("")){
			hql += " and zl.sqAddress = '"+sqAddress+"'";
		}
		if(!ajNoQt.equals("")){
			hql += " and zl.ajNoQt like '%"+ajNoQt+"%'";
		}
		if(!zlNo.equals("")){
			hql += " and zl.ajNoGf like '%"+zlNo+"%'";
		}
		if(!ajType.equals("")){
			hql += " and zl.ajType = '"+ajType+"'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",zl.ajSqrId) > 0";
		}
		if(!sDate.equals("")){
			hql += " and SUBSTR(zl.ajAddDate,1,7) >= '"+sDate+"' and SUBSTR(zl.ajAddDate,1,7) <= '"+eDate+"'";
		}
		if(lqStatus.equals(0)){
			hql += " and (( zl.zxUserId = 0 and zl.ajType1 = 'new') or (zl.tzsUserId = 0 and zl.ajType1 = 'old'))";
		}else if(lqStatus.equals(1)){
			hql += " and zl.ajStatus >= 3.0";//新案的话3.0时流程分配完成，旧案时是7.0
		}else if(lqStatus.equals(2)){//撰写任务领取只针对新案
			hql += " and zl.zxUserId = 0 and zl.tzsUserId > 0 and zl.ajType1 = 'new'";
		}else if(lqStatus.equals(3)){
			hql += " and zl.ajAddUserId = "+ajAddUserId;
		}else if(lqStatus.equals(4)){
			
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<ZlajMainInfoTb> findSpecInfoById(Session sess, Integer id,
			Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajMainInfoTb as zl where zl.id = "+id;
		if(cpyId > 0){
			hql += " and zl.cpyInfoTb.id = "+cpyId;
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajMainInfoTb> findFirstInfoByOpt(Session sess, Integer cpyId,String ajType,String currYear) {
		// TODO Auto-generated method stub
		String hql = " from ZlajMainInfoTb as zl where zl.cpyInfoTb.id = "+cpyId + " and zl.ajType = '"+ajType+"' and SUBSTR(zl.ajAddDate,1,4) = '"+currYear+"' order by zl.ajNo desc";
		return sess.createQuery(hql).setFirstResult(0).setMaxResults(1).list();//hql不支持limit 1;
	}

	@Override
	public List<ZlajMainInfoTb> findSpecInfoByOpt(Session sess, String ajNoGf,Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajMainInfoTb as zl where zl.ajNoGf = '"+ajNoGf+"' and zl.cpyInfoTb.id = "+cpyId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajMainInfoTb> findSpecInfoByOpt(Session sess, String zlTitle,String sqrName, String zlType,Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajMainInfoTb as zl where zl.ajTitle = '"+zlTitle+"' and zl.ajSqrName = '"+sqrName+"' and zl.ajType = '"+zlType+"' and zl.cpyInfoTb.id = "+cpyId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajMainInfoTb> findInfoByOpt(Session sess, String lcNameEng,
			Integer userId, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = "from ZlajMainInfoTb as zl where zl.cpyInfoTb.id = "+cpyId;
		if(lcNameEng.equals("tzs")){//导入通知书
			hql += " and zl.tzsUserId = "+userId;
		}else if(lcNameEng.equals("fycj")){//费用催缴
			hql += " and zl.feeUserId = "+userId;
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajMainInfoTb> findInfoByOpt_1(Session sess, String zlNo,
			Integer cusId, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajMainInfoTb as zl where zl.cpyInfoTb.id = "+cpyId;
		if(!zlNo.equals("")){
			hql += " and zl.ajNoGf like '"+zlNo+"'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",zl.ajSqrId) > 0";
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public Integer getCountByAddUserId(Session sess, Integer addUserId) {
		// TODO Auto-generated method stub
		String hql = "select count(zl.id) from ZlajMainInfoTb as zl where zl.ajAddUserId = "+addUserId;
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<ZlajMainInfoTb> findInfoByZlTitle(Session sess, String zlTitle,
			Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajMainInfoTb as zl where zl.cpyInfoTb.id = "+cpyId + " and zl.ajTitle = '"+zlTitle+"' and zl.ajStopStatus = 0";
		return sess.createQuery(hql).list();
	}

}
