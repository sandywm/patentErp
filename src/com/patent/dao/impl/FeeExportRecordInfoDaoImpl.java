package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.FeeExportRecordInfoDao;
import com.patent.module.FeeExportRecordInfo;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class FeeExportRecordInfoDaoImpl implements FeeExportRecordInfoDao{

	@Override
	public FeeExportRecordInfo get(Session sess, int id) {
		// TODO Auto-generated method stub
		String hql = " from FeeExportRecordInfo as fer where fer.id = "+id;
		List<FeeExportRecordInfo> fList = sess.createQuery(hql).list();
		if(fList.size() > 0){
			return fList.get(0);
		}
		return null;
	}

	@Override
	public void save(Session sess, FeeExportRecordInfo frInfo) {
		// TODO Auto-generated method stub
		sess.save(frInfo);
	}

	@Override
	public List<FeeExportRecordInfo> findPageInfoByOpt(Session sess,
			String addDateS, String addDateE, Integer cpyId, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from FeeExportRecordInfo as fer where fer.cpyInfoTb.id = "+cpyId;
		if(!addDateS.equals("") && !addDateE.equals("")){
			hql += " and substring(fer.addTime,1,10) >= '"+addDateS+"' and substring(fer.addTime,1,10) <= '"+addDateE+"'";
		}
		hql += " order by fer.id desc";
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, String addDateS, String addDateE, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = "select count(fer.id) from FeeExportRecordInfo as fer where fer.cpyInfoTb.id = "+cpyId;
		if(!addDateS.equals("") && !addDateE.equals("")){
			hql += " and substring(fer.addTime,1,10) >= '"+addDateS+"' and substring(fer.addTime,1,10) <= '"+addDateE+"'";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}
}
