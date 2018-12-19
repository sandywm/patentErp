package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.FeeImportRecordInfoDao;
import com.patent.module.FeeImportRecordInfo;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class FeeImportRecordInfoDaoImpl implements FeeImportRecordInfoDao{

	@Override
	public FeeImportRecordInfo get(Session sess, int id) {
		// TODO Auto-generated method stub
		String  hql = " from FeeImportRecordInfo as fir where fir.id = "+id;
		List<FeeImportRecordInfo> firList = sess.createQuery(hql).list();
		if(firList.size() > 0){
			return firList.get(0);
		}
		return null;
	}

	@Override
	public void save(Session sess, FeeImportRecordInfo firInfo) {
		// TODO Auto-generated method stub
		sess.save(firInfo);
	}

	
	@Override
	public List<FeeImportRecordInfo> findPageInfoByOpt(Session sess,
			String addDateS, String addDateE, Integer cpyId, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		String  hql = " from FeeImportRecordInfo as fir where fir.cpyInfoTb.id = "+cpyId;
		if(!addDateS.equals("") && !addDateE.equals("")){
			hql += " and substring(fir.uploadTime,1,10) >= '"+addDateS+"' and substring(fir.uploadTime,1,10) <= '"+addDateE+"'";
		}
		hql += " order by fir.id desc";
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, String addDateS,String addDateE, Integer cpyId) {
		// TODO Auto-generated method stub
		String  hql = "select count(fir.id) from FeeImportRecordInfo as fir where fir.cpyInfoTb.id = "+cpyId;
		if(!addDateS.equals("") && !addDateE.equals("")){
			hql += " and substring(fir.uploadTime,1,10) >= '"+addDateS+"' and substring(fir.uploadTime,1,10) <= '"+addDateE+"'";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}
}
