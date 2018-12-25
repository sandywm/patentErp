/**
 * 
 */
package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CusBackFeeInfoDao;
import com.patent.module.CusBackFeeInfo;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class CusBackFeeInfoDaoImpl implements CusBackFeeInfoDao{

	@Override
	public CusBackFeeInfo get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CusBackFeeInfo)sess.load(CusBackFeeInfo.class, id);
	}

	@Override
	public void save(Session sess, CusBackFeeInfo cbfInfo) {
		// TODO Auto-generated method stub
		sess.save(cbfInfo);
	}

	@Override
	public void delete(Session sess, CusBackFeeInfo cbfInfo) {
		// TODO Auto-generated method stub
		sess.delete(cbfInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CusBackFeeInfo cbfInfo) {
		// TODO Auto-generated method stub
		sess.update(cbfInfo);
	}

	@Override
	public List<CusBackFeeInfo> findPageInfoByOpt(Session sess, Integer cpyId,
			Integer cusId, String sDate, String eDate, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		String  hql = " from CusBackFeeInfo as cbf where cbf.cpy.id = "+cpyId;
		if(cusId > 0){
			hql += " and cbf.cus.id = "+cusId;
		}
		if(!sDate.equals("") && !eDate.equals("")){
			hql += " and cbf.backDate >= '"+sDate+"' and cbf.backDate <= '"+eDate+"'";
		}
		hql += " order by cbf.backDate desc";
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer cpyId, Integer cusId,
			String sDate, String eDate) {
		// TODO Auto-generated method stub
		String  hql = "select count(cbf.id) from CusBackFeeInfo as cbf where cbf.cpy.id = "+cpyId;
		if(cusId > 0){
			hql += " and cbf.cus.id = "+cusId;
		}
		if(!sDate.equals("") && !eDate.equals("")){
			hql += " and cbf.backDate >= '"+sDate+"' and cbf.backDate <= '"+eDate+"'";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}
}
