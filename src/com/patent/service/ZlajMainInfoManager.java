package com.patent.service;

import java.util.Date;

import com.patent.exception.WEBException;

public interface ZlajMainInfoManager {

	Integer addZL(String ajNo, String ajNoQt,String ajNoGf,
			String ajTitle, Integer ajType, String ajFieldId, String ajSqrId,
			String ajFmrId, String ajLxrId, String ajSqAddress, String ajYxqId,
			String ajUpload, String ajRemark, String ajEwyqId,
			Date ajApplyDate, String ajStatus, Integer ajFaId ) throws WEBException;
}
