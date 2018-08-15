package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.JsFiledInfoDao;
import com.patent.module.JsFiledInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class JsFiledInfoDaoImpl implements JsFiledInfoDao{

	@Override
	public JsFiledInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (JsFiledInfoTb) sess.load(JsFiledInfoTb.class, id);
	}

	@Override
	public void save(Session sess, JsFiledInfoTb jsInfo) {
		// TODO Auto-generated method stub
		sess.save(jsInfo);
	}

	@Override
	public void delete(Session sess, JsFiledInfoTb jsInfo) {
		// TODO Auto-generated method stub
		sess.delete(jsInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public List<JsFiledInfoTb> findInfoByOpt(Session sess,Integer cpyId,String jsIdStr) {
		// TODO Auto-generated method stub
		String hql = " from JsFiledInfoTb as js where js.cpyInfoTb.id = "+cpyId;
		if(!jsIdStr.equals("")){
			if(jsIdStr.split(",").length == 1){
				hql += " and js.id = "+Integer.parseInt(jsIdStr);
			}else{
				hql += " and js.id in ("+jsIdStr+")";
			}
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public void update(Session sess, JsFiledInfoTb jsInfo) {
		// TODO Auto-generated method stub
		sess.update(jsInfo);
	}

	@Override
	public List<JsFiledInfoTb> findPageInfoByCpyId(Session sess, Integer cpyId,Integer pageNo,Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from JsFiledInfoTb as js where js.cpyInfoTb.id = "+cpyId;
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByCpyId(Session sess, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = "select count(js.id) from JsFiledInfoTb as js where js.cpyInfoTb.id = "+cpyId;
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
//		long count = Long.parseLong(String.valueOf(sess.createQuery(hql).uniqueResult()));
//		Integer count_1 = (int)count;
//		return count_1;
	}

	@Override
	public List<JsFiledInfoTb> findInfoByOpt_1(Session sess, Integer cpyId,
			String zyName) {
		// TODO Auto-generated method stub
		String hql = " from JsFiledInfoTb as js where js.cpyInfoTb.id = "+cpyId + " and js.zyName = '"+zyName+"'";
		return sess.createQuery(hql).list();
	}

}
