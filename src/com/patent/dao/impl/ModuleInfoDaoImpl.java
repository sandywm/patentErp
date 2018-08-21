package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ModuleInfoDao;
import com.patent.module.ModuleInfoTb;

@SuppressWarnings("unchecked")
public class ModuleInfoDaoImpl implements ModuleInfoDao{

	@Override
	public ModuleInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ModuleInfoTb) sess.load( ModuleInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ModuleInfoTb mInfo) {
		// TODO Auto-generated method stub
		sess.save(mInfo);
	}

	@Override
	public void delete(Session sess, ModuleInfoTb mInfo) {
		// TODO Auto-generated method stub
		sess.delete(mInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ModuleInfoTb mInfo) {
		// TODO Auto-generated method stub
		sess.update(mInfo);
	}

	@Override
	public List<ModuleInfoTb> findInfoByLevel(Session sess,Integer modLevel,Integer showStatus) {
		// TODO Auto-generated method stub
		String hql = " from ModuleInfoTb as mi where 1=1";
		if(modLevel >= 0){
			hql += " and mi.modLevel <= "+modLevel;
		}
		if(showStatus >= 0){
			hql += " and mi.showStatis = "+showStatus;
		}
		hql += "  order by mi.modLevel asc,mi.orderNo asc";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ModuleInfoTb> findInfoByName(Session sess, String modName) {
		// TODO Auto-generated method stub
		String hql = " from ModuleInfoTb as mi where mi.modName = '"+modName+"'";
		return sess.createQuery(hql).list();
	}

	@Override
	public ModuleInfoTb getEntityById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ModuleInfoTb as mi where mi.id = "+id;
		List<ModuleInfoTb> mList = sess.createQuery(hql).list();
		if(mList.size() > 0){
			return mList.get(0);
		}
		return null;
	}

}
