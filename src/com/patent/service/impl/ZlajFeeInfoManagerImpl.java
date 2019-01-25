package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.CpyInfoDao;
import com.patent.dao.CpyUserInfoDao;
import com.patent.dao.FeeTypeInfoDao;
import com.patent.dao.ZlajFeeInfoDao;
import com.patent.dao.ZlajFeeSubInfoDao;
import com.patent.dao.ZlajMainInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.FeeTypeInfoTb;
import com.patent.module.ZlajFeeInfoTb;
import com.patent.module.ZlajFeeSubInfoTb;
import com.patent.service.ZlajFeeInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class ZlajFeeInfoManagerImpl implements ZlajFeeInfoManager{

	FeeTypeInfoDao ftDao = null;
	ZlajFeeInfoDao fDao = null;
	ZlajFeeSubInfoDao fsDao = null;
	CpyInfoDao cDao = null;
	CpyUserInfoDao uDao = null;
	ZlajMainInfoDao zlDao = null;
	Transaction tran = null;
	@Override
	public List<ZlajFeeInfoTb> listInfoByOpt(Integer zlId, String feeTypeStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findInfoByOpt(sess, zlId, feeTypeStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取费用信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public ZlajFeeInfoTb getFeeEntityById(Integer id) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.getFeeEntityById(sess, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键获取费用信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<FeeTypeInfoTb> listInfoByStatus(String feeStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			ftDao = (FeeTypeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_TYPE_INFO);
			Session sess = HibernateUtil.currentSession();
			return ftDao.findInfoByStatus(sess, feeStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据费用标记获取费用类型信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listInfoByOpt(Integer zlId, Integer feeTypeId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findInfoByOpt(sess, zlId, feeTypeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号、费用类型获取专利案件缴费列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer addZLFee(Integer zlId, Integer appUserId, Integer geeTypeId,
			Double feePrice, Double feeRate,String feeEndDateCpy, String feeEndDateGf,
			String feeRemark, Integer feeStatus, Integer cpyId,Integer djStatus, String feeJnDate, 
			String feeUpZd,String tzsArea,Integer yearFeeNo,String feeRange,Integer addStatus,
			String backDate,String feeBatchNo,String bankSerialNo,String fpDate,String fpNo,Integer feeTxType,String tzsTx)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			ftDao = (FeeTypeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_TYPE_INFO);
			cDao = (CpyInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_INFO);
			uDao = (CpyUserInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_CPY_USER_INFO);
			zlDao = (ZlajMainInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_MAIN_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = new ZlajFeeInfoTb(ftDao.getTypeEntityById(sess, geeTypeId), uDao.get(sess, appUserId),
					cDao.get(sess, cpyId), zlDao.get(sess, zlId), feePrice, feeRate,feeEndDateCpy,
					feeEndDateGf, feeRemark, feeStatus,djStatus, feeJnDate, feeUpZd,tzsArea,yearFeeNo,feeRange,addStatus,
					backDate,0.0,0,0.0,feeBatchNo,bankSerialNo,fpDate,fpNo,feeTxType,tzsTx);
			fDao.save(sess, zlFee);
			tran.commit();
			return zlFee.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加专利缴纳费用信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateFeeInfoById(Integer id, Double feePrice,
			String feeRemark, Integer feeStatus, Integer djStatus,
			String feeJnDate, String feeUpZd) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = fDao.getFeeEntityById(sess, id);
			if(zlFee != null){
				zlFee.setFeePrice(feePrice);
				zlFee.setFeeRemark(feeRemark);
				zlFee.setFeeStatus(feeStatus);
				zlFee.setDjStatus(djStatus);
				zlFee.setFeeJnDate(feeJnDate);
				zlFee.setFeeUpZd(feeUpZd);
				fDao.update(sess, zlFee);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定缴费信息的信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateFeeInfoById(Integer id, String feeEndDateCpy,
			String feeEndDateGf) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = fDao.getFeeEntityById(sess, id);
			if(zlFee != null){
				zlFee.setFeeEndDateJj(feeEndDateCpy);
				zlFee.setFeeEndDateGf(feeEndDateGf);
				fDao.update(sess, zlFee);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定编号的费用期限的信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<FeeTypeInfoTb> listInfoByName(String feeName) throws WEBException {
		// TODO Auto-generated method stub
		try {
			ftDao = (FeeTypeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_TYPE_INFO);
			Session sess = HibernateUtil.currentSession();
			return ftDao.findInfoByName(sess, feeName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据费用名称获取费用类型信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listAllFeeByZlId(Integer zlId, Integer cpyId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findAllFeeByZlId(sess, zlId, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定代理机构下指定专利的所有费用（按照官方期限升序排列）时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer addFeeSubInfo(String feeRange, Double latePrice,
			Integer feeId, Integer feeTypeId, String remark)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			fsDao = (ZlajFeeSubInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_SUB_INFO);
			ftDao = (FeeTypeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_TYPE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeSubInfoTb fs = new ZlajFeeSubInfoTb(fDao.get(sess, feeId),
					ftDao.getTypeEntityById(sess, feeTypeId), feeRange, latePrice,remark);
			fsDao.save(sess, fs);
			tran.commit();
			return fs.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加费用信息子表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeSubInfoTb> listInfoByFeeId(Integer feeId)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fsDao = (ZlajFeeSubInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_SUB_INFO);
			Session sess = HibernateUtil.currentSession();
			return fsDao.findInfoByFeeId(sess, feeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据费用编号获取费用子表列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateBackFeeInfoById(Integer feeId, String backDate,
			Double backFee, Integer backStatus, Double discountsFee)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = fDao.getFeeEntityById(sess, feeId);
			if(zlFee != null){
				zlFee.setBackDate(backDate);
				if(backFee > 0){
					zlFee.setBackFee(backFee);
				}
				if(backStatus >= 0){
					zlFee.setBackStatus(backStatus);
					if(backStatus.equals(1)){
						zlFee.setDiscountsFee(discountsFee);
					}
				}
				fDao.update(sess, zlFee);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改指定编号的费用代缴信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listAllFeeByOpt(Integer zlId,
			String feeTypeStatus, Integer djStatus, Integer feeStatus,
			Integer backStatus, Integer cpyId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findAllFeeByOpt(sess, zlId, feeTypeStatus, djStatus, feeStatus, backStatus, cpyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取缴纳费用清单列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listYearFeeByOpt(Integer zlId, Integer yearNo,String feeName)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findYearFeeInfoByOpt(sess, zlId, yearNo,feeName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定专利指定年度的费用/滞纳金信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateFeeInfoById(Integer feeId, String feeBatchNo,
			String bankSerialNo, String fpDate, String fpNo)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = fDao.getFeeEntityById(sess, feeId);
			if(zlFee != null){
				if(!feeBatchNo.equals("")){
					zlFee.setFeeBatchNo(feeBatchNo);
				}
				if(!bankSerialNo.equals("")){
					zlFee.setBankSerialNo(bankSerialNo);				
								}
				if(!fpDate.equals("")){
					zlFee.setFpDate(fpDate);
				}
				if(!fpNo.equals("")){
					zlFee.setFpNo(fpNo);
				}
				fDao.update(sess, zlFee);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改费用的缴费批次号、银行缴费流水号、开票时间、票号信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listInfoByOpt(Integer cpyId,Integer feeStatus,Integer diffDays,String zlNo,String ajNo,Integer cusId,String sDate,String eDate,
			Integer qdStatus,Integer pageNo,Integer pageSize)throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findInfoByOpt(sess, cpyId, feeStatus, diffDays, zlNo, ajNo, cusId, sDate, eDate, qdStatus, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页/不分页获取费用列表（获取未缴费的费用列表时专利必须在正常状态下）信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer cpyId,Integer feeStatus,String zlNo,String ajNo,Integer cusId,String sDate,String eDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.getCountByOpt(sess, cpyId, feeStatus, zlNo, ajNo, cusId, sDate, eDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定代理机构下已缴费的费用记录条数（专利正常条件下）信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateFeePriceById(Integer feeId, Double newFeePrice)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = fDao.getFeeEntityById(sess, feeId);
			if(zlFee != null){
				if(newFeePrice > 0){
					zlFee.setFeePrice(newFeePrice);
				}
				fDao.update(sess, zlFee);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改费用金额信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<Object> getTjFeeInfoByOpt(Integer cpyId, String zlNo,
			String ajNo, Integer cusId, String sDate, String eDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.getTjFeeInfoByOpt(sess, cpyId, zlNo, ajNo, cusId, sDate, eDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定代理机构下已交费用、实收费用、未收费用统计（在已交费用模式下）信息时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listUnJfInfoByOpt(Integer cpyId, String idStr,Integer feeStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findUnJfInfoByOpt(sess, cpyId, idStr,feeStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根根据拼接的专利费用编号获取指定代理机构下的未交费用信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listInfoByOpt(Integer cpyId, String zlNo,
			String feeName) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findInfoByOpt(sess, cpyId, zlNo, feeName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利号、费用名称获取指定代理机构下的费用列表(导入excel缴费清单平账用)列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateComJfInfoById(Integer feeId, String jfDate)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			ZlajFeeInfoTb zlFee = fDao.getFeeEntityById(sess, feeId);
			if(zlFee != null){
				zlFee.setFeeStatus(1);
				zlFee.setFeeJnDate(jfDate);
				fDao.update(sess, zlFee);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("完成缴费信息（缴费状态修改成1并设置缴费时间）时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeSubInfoTb> listCurrSubFeeInfoByOpt(Integer feeId,String currDate) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fsDao = (ZlajFeeSubInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_SUB_INFO);
			Session sess = HibernateUtil.currentSession();
			return fsDao.findCurrSubFeeInfoByOpt(sess, feeId, currDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利费用编号、当前时间获取当前时间所对应的滞纳金列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listUnBackInfoByOpt(Integer cpyId,Integer cusId,
			String feeType) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findUnBackInfoByOpt(sess, cpyId, cusId, feeType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("获取指定代理机构下所有未平账已缴费的费用记录列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listAllInfoByOpt(Integer cpyId, String zlNo,
			Integer cusId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findAllInfoByOpt(sess, cpyId, zlNo, cusId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利编号、客户编号获取专利费用列表（统计用）时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<ZlajFeeInfoTb> listSpecInfoByFeeIdArr(String feeIdStr)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			fDao = (ZlajFeeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_ZLAJ_FEE_INFO);
			Session sess = HibernateUtil.currentSession();
			return fDao.findSpecInfoByFeeIdArr(sess, feeIdStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据组合的id获取所有的费用列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<FeeTypeInfoTb> listInfoByzlType(String zlType)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			ftDao = (FeeTypeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_FEE_TYPE_INFO);
			Session sess = HibernateUtil.currentSession();
			return ftDao.findInfoByzlType(sess, zlType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据专利类型获取专利费用信息列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}
}
