package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CpyInfoDao;
import com.patent.module.CpyInfoTb;
import com.patent.tools.CommonTools;
import com.patent.tools.CurrentTime;

@SuppressWarnings("unchecked")
public class CpyInfoDaoImpl implements CpyInfoDao{

	@Override
	public CpyInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CpyInfoTb) sess.load(CpyInfoTb.class, id);
	}

	@Override
	public void save(Session sess, CpyInfoTb cpyInfo) {
		// TODO Auto-generated method stub
		sess.save(cpyInfo);
	}

	@Override
	public void delete(Session sess, CpyInfoTb cpyInfo) {
		// TODO Auto-generated method stub
		sess.delete(cpyInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CpyInfoTb cpyInfo) {
		// TODO Auto-generated method stub
		sess.update(cpyInfo);
	}

	@Override
	public List<CpyInfoTb> findPageInfoByOpt(Session sess, String cpyName,
			String cpyProv, String cpyCity, String cpyFr, String cpyLxr,
			Integer cpyLevel,Integer gqStatus, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from CpyInfoTb as cpy where 1 = 1";
		if(!cpyName.equals("")){
			hql += " and cpy.cpyName like '%"+cpyName+"%'";
		}
		if(!cpyProv.equals("")){
			hql += " and cpy.cpyProv = '"+cpyProv+"'";
		}
		if(!cpyCity.equals("")){
			hql += " and cpy.cpyCity = '"+cpyCity+"'";
		}
		if(!cpyFr.equals("")){
			hql += " and cpy.cpyFr = '"+cpyFr+"'";
		}
		if(!cpyLxr.equals("")){
			hql += " and cpy.cpyLxr = '"+cpyLxr+"'";
		}
		if(cpyLevel >= 0){
			hql += " and cpy.cpyLevel = "+cpyLevel;
		}
		if(gqStatus.equals(0)){//未过期
			hql += " and cpy.endDate > '"+CurrentTime.getStringDate()+"'";
		}else if(gqStatus.equals(1)){//已过期
			hql += " and cpy.endDate <= '"+CurrentTime.getStringDate()+"'";
		}
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, String cpyName, String cpyProv,
			String cpyCity, String cpyFr, String cpyLxr, Integer cpyLevel,Integer gqStatus) {
		// TODO Auto-generated method stub
		String hql = "select count(cpy.id) from CpyInfoTb as cpy where 1 = 1";
		if(!cpyName.equals("")){
			hql += " and cpy.cpyName like '%"+cpyName+"%'";
		}
		if(!cpyProv.equals("")){
			hql += " and cpy.cpyProv = '"+cpyProv+"'";
		}
		if(!cpyCity.equals("")){
			hql += " and cpy.cpyCity = '"+cpyCity+"'";
		}
		if(!cpyFr.equals("")){
			hql += " and cpy.cpyFr = '"+cpyFr+"'";
		}
		if(!cpyLxr.equals("")){
			hql += " and cpy.cpyLxr = '"+cpyLxr+"'";
		}
		if(cpyLevel >= 0){
			hql += " and cpy.cpyLevel = "+cpyLevel;
		}
		if(gqStatus.equals(0)){//未过期
			hql += " and cpy.endDate > '"+CurrentTime.getStringDate()+"'";
		}else if(gqStatus.equals(1)){//已过期
			hql += " and cpy.endDate <= '"+CurrentTime.getStringDate()+"'";
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<CpyInfoTb> findSubInfoByParCpyId(Session sess, Integer parCpyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CpyInfoTb> findParInfoBySubCpyId(Session sess, Integer subCpyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateCpyParInfoById(Session sess, Integer id,
			Integer cpyParId) {
		// TODO Auto-generated method stub
		return false;
	}

}
