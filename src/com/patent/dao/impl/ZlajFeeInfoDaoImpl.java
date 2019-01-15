package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajFeeInfoDao;
import com.patent.module.ZlajFeeInfoTb;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;

@SuppressWarnings("unchecked")
public class ZlajFeeInfoDaoImpl implements ZlajFeeInfoDao{

	@Override
	public ZlajFeeInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajFeeInfoTb) sess.load(ZlajFeeInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajFeeInfoTb feeInfo) {
		// TODO Auto-generated method stub
		sess.save(feeInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajFeeInfoTb feeInfo) {
		// TODO Auto-generated method stub
		sess.update(feeInfo);
	}

	@Override
	public ZlajFeeInfoTb getFeeEntityById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.id = "+id;
		List<ZlajFeeInfoTb> zlfList = sess.createQuery(hql).list();
		if(zlfList.size() > 0){
			return zlfList.get(0);
		}
		return null;
	}

	@Override
	public List<ZlajFeeInfoTb> findInfoByOpt(Session sess, Integer zlId,
			String feeTypeStatus) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.zlajMainInfoTb.id = "+zlId;
		if(!feeTypeStatus.equals("")){
			if(feeTypeStatus.equals("nf")){
				hql += " and zlf.feeTypeInfoTb.feeStatus like '%"+feeTypeStatus+"%'";
			}else{
				hql += " and zlf.feeTypeInfoTb.feeStatus = '"+feeTypeStatus+"'";
			}
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findInfoByOpt(Session sess, Integer zlId,
			Integer feeTypeId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.zlajMainInfoTb.id = "+zlId + " and zlf.feeTypeInfoTb.id = "+feeTypeId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findInfoByOpt(Session sess, Integer zlId,
			String tzsArea, Integer feeStatus, Integer djStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZlajFeeInfoTb> findYearFeeInfoByOpt(Session sess) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZlajFeeInfoTb> findAllFeeByZlId(Session sess, Integer zlId,
			Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.zlajMainInfoTb.id = "+zlId + " and zlf.cpyInfoTb.id = "+cpyId + " order by zlf.feeEndDateGf asc";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findAllFeeByOpt(Session sess, Integer zlId,
			String feeTypeStatus, Integer djStatus, Integer feeStatus,
			Integer backStatus, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.zlajMainInfoTb.id = "+zlId + " and zlf.cpyInfoTb.id = "+cpyId;
		if(!feeTypeStatus.equals("")){
			hql += " and zlf.feeTypeInfoTb.feeStatus = '"+feeTypeStatus+"'";
		}
		if(djStatus >= 0){
			hql += " and zlf.djStatus = "+djStatus;
			if(djStatus.equals(1)){//代缴
				if(backStatus >= 0){
					hql += " and zlf.backStatus = "+backStatus;
				}
			}
		}
		if(feeStatus >= 0){
			hql += " and zlf.feeStatus = "+feeStatus;
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findYearFeeInfoByOpt(Session sess, Integer zlId,
			Integer yearNo,String feeName) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.zlajMainInfoTb.id = "+zlId + " and zlf.yearFeeNo = "+yearNo;
		if(feeName.equals("znjFee")){
			hql += " and zlf.feeTypeInfoTb.feeName like '%滞纳金%'";
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findInfoByOpt(Session sess, Integer cpyId,
			Integer feeStatus, Integer diffDays,String zlNo,String ajNo,Integer cusId,String sDate,String eDate,Integer qdStatus,Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.cpyInfoTb.id = "+cpyId;
		if(!zlNo.equals("")){
			hql += " and zlf.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}else if(!ajNo.equals("")){
			hql += " and zlf.zlajMainInfoTb.ajNo = '"+ajNo+"'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",zlf.zlajMainInfoTb.ajSqrId) > 0";
		}
		if(feeStatus.equals(0)){//未缴费的费用列表时专利必须在正常状态下
			hql += " and zlf.feeStatus = "+feeStatus;
			if(qdStatus.equals(0)){//国家局--去除代理费
				hql += " and zlf.feeTypeInfoTb.feeStatus != 'dlf'";
			}//客户的不用去除
			if(diffDays > 0){
				hql += " and TO_DAYS(zlf.feeEndDateJj) - TO_DAYS('"+CurrentTime.getStringDate()+"') <= "+diffDays;
				hql += " and TO_DAYS(zlf.feeEndDateJj) - TO_DAYS('"+CurrentTime.getStringDate()+"') >= 0 ";
			}else if(diffDays.equals(-2)){
				hql += " and TO_DAYS(zlf.feeEndDateJj) - TO_DAYS('"+CurrentTime.getStringDate()+"') < 0 ";
			}
			hql += " and zlf.zlajMainInfoTb.ajStopStatus = 0 order by zlf.feeEndDateJj asc";
			return sess.createQuery(hql).list();
		}else{
			if(feeStatus.equals(1)){
				hql += " and zlf.feeStatus = "+feeStatus;
			}//全部就不判断费用状态
			if(!sDate.equals("") && !eDate.equals("")){
				hql += " and zlf.feeJnDate >= '"+sDate+"' and zlf.feeJnDate <= '"+eDate+"'";
			}
			int offset = (pageNo - 1) * pageSize;
			if (offset < 0) {
				offset = 0;
			}
			return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
		}
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer cpyId,Integer feeStatus,String zlNo,String ajNo,Integer cusId,String sDate,String eDate) {
		// TODO Auto-generated method stub
		String hql ="select count(zlf.id) from ZlajFeeInfoTb as zlf where zlf.cpyInfoTb.id = "+cpyId;
		if(!zlNo.equals("")){
			hql += " and zlf.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}else if(!ajNo.equals("")){
			hql += " and zlf.zlajMainInfoTb.ajNo = '"+ajNo+"'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",zlf.zlajMainInfoTb.ajSqrId) > 0";
		}
		if(feeStatus.equals(1)){
			hql += " and zlf.feeStatus = "+feeStatus;
			if(!sDate.equals("") && !eDate.equals("")){
				hql += " and zlf.feeJnDate >= '"+sDate+"' and zlf.feeJnDate <= '"+eDate+"'";
			}
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<Object> getTjFeeInfoByOpt(Session sess, Integer cpyId,
			String zlNo, String ajNo, Integer cusId, String sDate, String eDate) {
		// TODO Auto-generated method stub
		String hql = "select sum(zlf.feePrice),sum(zlf.backFee) from ZlajFeeInfoTb as zlf where zlf.cpyInfoTb.id = "+cpyId + " and zlf.feeStatus = 1";
		if(!zlNo.equals("")){
			hql += " and zlf.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}else if(!ajNo.equals("")){
			hql += " and zlf.zlajMainInfoTb.ajNo = '"+ajNo+"'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",zlf.zlajMainInfoTb.ajSqrId) > 0";
		}
		if(!sDate.equals("") && !eDate.equals("")){
			hql += " and zlf.feeJnDate >= '"+sDate+"' and zlf.feeJnDate <= '"+eDate+"'";
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findUnJfInfoByOpt(Session sess, Integer cpyId,
			String idStr,Integer feeStatus) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.cpyInfoTb.id = "+cpyId + " and zlf.feeStatus = "+feeStatus;
		hql += " and zlf.zlajMainInfoTb.ajStopStatus = 0 and zlf.id in("+idStr+")";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findInfoByOpt(Session sess, Integer cpyId,
			String zlNo, String feeName) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.cpyInfoTb.id = "+cpyId;
		hql += " and zlf.feeTypeInfoTb.feeName = '"+feeName+"' and zlf.zlajMainInfoTb.ajNoGf = '"+zlNo+"'";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findUnBackInfoByOpt(Session sess, Integer cpyId,Integer cusId,String feeType) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.cpyInfoTb.id = "+cpyId + " and zlf.djStatus = 1 and zlf.feeStatus = 1 and zlf.backStatus = 0";
		hql += " and FIND_IN_SET("+cusId+",zlf.zlajMainInfoTb.ajSqrId) > 0";
		if(feeType.equals("gf")){//官费
			hql += " and zlf.feeTypeInfoTb.feeName != '代理费'";
		}else{//只有代理费
			hql += " and zlf.feeTypeInfoTb.feeName = '代理费'";
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findAllInfoByOpt(Session sess, Integer cpyId,
			String zlNo, Integer cusId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.cpyInfoTb.id = "+cpyId;
		if(!zlNo.equals("")){
			hql += " and zlf.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}
		if(cusId > 0){
			hql += " and FIND_IN_SET("+cusId+",zlf.zlajMainInfoTb.ajSqrId) > 0";
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeInfoTb> findSpecInfoByFeeIdArr(Session sess,
			String feeIdStr) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeInfoTb as zlf where zlf.id in("+feeIdStr+")";
		return sess.createQuery(hql).list();
	}

}
