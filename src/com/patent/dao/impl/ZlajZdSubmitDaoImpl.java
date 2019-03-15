package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajZdSubmitDao;
import com.patent.module.ZlajZdSubmitTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class ZlajZdSubmitDaoImpl implements ZlajZdSubmitDao{

	@Override
	public ZlajZdSubmitTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajZdSubmitTb) sess.load(ZlajZdSubmitTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajZdSubmitTb zdInfo) {
		// TODO Auto-generated method stub
		sess.save(zdInfo);
	}

	@Override
	public List<ZlajZdSubmitTb> findPageInfoByOpt(Session sess, Integer cpyId,
			String zlTitle, String zlNo, Integer signStatus, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from ZlajZdSubmitTb as zzs where zzs.cpyInfoTb.id = "+cpyId;
		if(!zlTitle.equals("")){
			hql += " and zzs.zlajMainInfoTb.ajTitle like '%"+zlTitle+"%'";
		}
		if(!zlNo.equals("")){
			hql += " and zzs.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}
		if(signStatus > 0){
			hql += " and zzs.signStatus = "+signStatus;
		}
		hql += " order by zzs.id desc";
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer cpyId, String zlTitle,
			String zlNo, Integer signStatus) {
		// TODO Auto-generated method stub
		String hql = "select count(zzs.id) from ZlajZdSubmitTb as zzs where zzs.cpyInfoTb.id = "+cpyId;
		if(!zlTitle.equals("")){
			hql += " and zzs.zlajMainInfoTb.ajTitle like '%"+zlTitle+"%'";
		}
		if(!zlNo.equals("")){
			hql += " and zzs.zlajMainInfoTb.ajNoGf like '%"+zlNo+"%'";
		}
		if(signStatus > 0){
			hql += " and zzs.signStatus = "+signStatus;
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<ZlajZdSubmitTb> findInfoByZlNo(Session sess, Integer cpyId,
			String zlNo, String qqsName, Integer signStatus) {
		// TODO Auto-generated method stub
		String hql = "select count(zzs.id) from ZlajZdSubmitTb as zzs where zzs.cpyInfoTb.id = "+cpyId;
		hql += " and zzs.zlajMainInfoTb.ajNoGf = '"+zlNo+"' and zzs.zlajQqsInfoTb.zlQqs = '"+qqsName+"' and zzs.signStatus = "+signStatus;
		return sess.createQuery(hql).list();
	}

}
