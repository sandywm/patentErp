/**
 * @Description: 专利增加/编辑公共方法
 * @author: hlf
 */
var singleDlFeeNum = 0,multiDlFeeNum = 0,currOpts = '';
//num:案件优先权的个数
layui.define(['laydate','form','upLoadFiles','common','lcfpMethod','rate','scrollBar'],function(exports){
	var laydate = layui.laydate,
		form = layui.form,
		globalUpload = layui.upLoadFiles,
		common = layui.common,
		lcfpMethod=layui.lcfpMethod,
		rate = layui.rate,
		commonScrollBar = layui.scrollBar;
	var obj = {
		data : {
			upZlFlag : false,
			actGetMxFlag : false,//流程明细详情层是否被打开，true:打开表示点击主流程标题可直接更换明细详情
			ajStatus : 0,//当前流程案件状态值
			isXxFeeFlag : false,
			isClickZlTypeFlag : false,
			switchZlTitFlag : true,
			switchSqDateFlag : true,
			onlyOneFlag_tit : true,//创建一次组合类型下的案件title
			onlyOneFlag_zlNum : true//创建一次组合类型的专利号
		},
		//编辑时渲染结构
		renderZlBasicInfo : function(json){
			var strHtml = '',isReadFlag = true,zlTypeTxtStr='';
			if(parent.zlStrOpts == 'zlDetailOpts'){
				lcfpMethod.data.zlAjType = json.ajType1;
			}
			if(!this.data.upZlFlag){
				//表示不具有编辑权限
				strHtml += '<div class="noUpTipTxt"><i class="layui-icon layui-icon-face-cry"></i>抱歉，您暂无编辑此专利的权限！</div>';
			}
			//新案、旧案
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>案件类型</label><div class="layui-input-block"><input type="hidden" id="anjianType" value="'+ json.ajType1 +'"/>';
			if(json.ajType1 == 'new'){
				strHtml += '<input type="radio" name="ajTypeRad" class="ajTypeRad" value="new" title="新案" checked/>';
			}else if(json.ajType1 == 'old'){
				strHtml += '<input type="radio" name="ajTypeRad" class="ajTypeRad" value="old" title="旧案(中途转入)" checked/>';
			}
			strHtml += '</div></div>';
			//专利类型
			strHtml += '<div class="layui-form-item">';
			strHtml += '<div class="itemDiv"><label class="layui-form-label"><span class="mustItem">*</span>专利类型</label><input id="zlTypeInp" name="ajType" type="hidden" value="'+ json.ajType +'"/><div class="layui-input-inline">';
			if(json.ajType == 'fm'){
				zlTypeTxtStr = '发明';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="fm" title="发明" checked/>';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="syxx" title="实用新型"/>';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="wg" title="外观"/>';
			}else if(json.ajType == 'syxx'){
				zlTypeTxtStr = '实用新型';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="fm" title="发明"/>';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="syxx" title="实用新型" checked/>';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="wg" title="外观"/>';
			}else if(json.ajType == 'wg'){
				zlTypeTxtStr = '外观';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="fm" title="发明"/>';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="syxx" title="实用新型"/>';
				strHtml += '<input type="radio" name="zlTypeRad" class="zlTypeRad" lay-filter="zlTypeFilter" value="wg" title="外观" checked/>';
			}
			strHtml += '</div></div>';
			
			strHtml += '</div>';
			
			//旧案下案件申请日
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>案件申请日</label><div class="layui-input-block"><input type="text" value="'+ json.sqDate +'" readonly autocomplete="off" class="layui-input"/></div></div>';
			//案件标题
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>案件标题</label><div class="layui-input-block">';
			strHtml += '<input id="ajTitleInp" type="text" name="ajTitle" placeholder="请输入案件标题(40字以内)" maxlength="40" value="'+ json.ajTitle +'" autocomplete="off" class="layui-input"/><p class="zlTitTipsTxt"></p></div></div>';
			
			if(json.ajType1 == 'old'){
				strHtml += '<div class="layui-form-item feeDiv" style="display:block;"><label class="layui-form-label titLabel"><span class="mustItem">*</span>是否费减</label><input id="rateInp" type="hidden" value="'+ json.ajFjInfo +'"/>';
				strHtml += '<div class="layui-input-block">';
				if(json.ajFjInfo == 0){
					strHtml += '<input type="radio" name="feeRateInp" value="0" lay-filter="isHasFeeRateFilter" title="无费减" checked/>';
					strHtml += '<input type="radio" name="feeRateInp" lay-filter="isHasFeeRateFilter" value="0.7" title="70%"/>';
					strHtml += '<input type="radio" name="feeRateInp" lay-filter="isHasFeeRateFilter" value="0.85" title="85%"/>';	
				}else if(json.ajFjInfo == 0.7){
					strHtml += '<input type="radio" name="feeRateInp" value="0" lay-filter="isHasFeeRateFilter" title="无费减">';
					strHtml += '<input type="radio" name="feeRateInp" lay-filter="isHasFeeRateFilter" value="0.7" title="70%" checked/>';
					strHtml += '<input type="radio" name="feeRateInp" lay-filter="isHasFeeRateFilter" value="0.85" title="85%"/>';
				}else if(json.ajFjInfo == 0.85){
					strHtml += '<input type="radio" name="feeRateInp" value="0" lay-filter="isHasFeeRateFilter" title="无费减">';
					strHtml += '<input type="radio" name="feeRateInp" lay-filter="isHasFeeRateFilter" value="0.7" title="70%">';
					strHtml += '<input type="radio" name="feeRateInp" lay-filter="isHasFeeRateFilter" value="0.85" title="85%" checked/>';
				}
				strHtml += '</div></div>';	
			}
			
			//案件编号 && 案件申请号/专利号
			strHtml += '<div class="layui-form-item clearfix"><div class="itemDiv"><label class="layui-form-label">案件编号</label><div class="layui-input-inline">';
			strHtml += '<input id="ajNumInp" type="text" disabled autocomplete="off" value="'+ json.ajNo +'" class="layui-input"/></div></div>';
			strHtml += '<div class="itemDiv"><label class="layui-form-label"><span class="mustItem zlNoMustBe"></span>案件申请号/专利号</label><div class="layui-input-inline">';
			if(json.ajType1 == 'new'){
				strHtml += '<input id="ajSqZlNum" maxlength="20" type="text" name="ajSqZlNum" disabled autocomplete="off" value="'+ json.ajNoGf +'" class="layui-input"/></div></div></div>';	
			}else{
				var tmpStr = json.ajNoGf.substring(4,5).toUpperCase();
				var tmpStr_four = json.ajNoGf.substring(0,4).toUpperCase();
				var tmpStr_end = json.ajNoGf.substring(5).toUpperCase();
				strHtml += '<p class="zlPatternNum specWid" style="display:block;">'+ tmpStr_four +'<strong class="zlPatColor">'+ tmpStr +'</strong>'+ tmpStr_end +'</p>';
				strHtml += '<input id="ajSqZlNum" maxlength="20" type="text" name="ajSqZlNum" autocomplete="off" value="'+ json.ajNoGf +'" class="layui-input editAjNum"/></div></div></div>';
			}
			//已领取专利任务
			strHtml += '<div class="layui-form-item clearfix">';
			strHtml += '<div class="itemDiv"><label class="layui-form-label">已领取专利任务</label><div class="layui-input-inline">';
			strHtml += '<input id="pubZlIdInp" type="hidden" value="'+ json.pubZlId +'" name="pubZlId"/>';
			strHtml += '<input id="pubZlIdInpTxt" type="text" disabled autocomplete="off" value="'+ json.pubZlName +'" class="layui-input" style="width:94%"/>';
			if(this.data.upZlFlag){
				strHtml += '<span class="selZlTaskSp" title="选择"><i class="layui-icon layui-icon-more"></i></span>';
				strHtml += '<span class="resetSp" title="重置"><i class="layui-icon layui-icon-delete"></i></span></div></div>';
			}
			strHtml += '<div class="itemDiv"><div class="diffDiv oneWid fl">';
			strHtml += '<label class="layui-form-label"><i class="diffTit">专利难易程度</i></label>';
			strHtml += '<div class="layui-input-inline"><div id="diffLevelOne"></div></div></div></div>';
			strHtml += '</div>';
			//专利难易度 专利代理费用
			strHtml += '<div class="layui-form-item">';
			strHtml += '<div class="agentFeeDiv"><label class="layui-form-label"><span class="dlFeeSpan mustItem">*</span>专利代理费</label>';
			strHtml += '<div class="layui-input-block"><div class="agentBox" style="display:block;">';
			strHtml += '<div class="initDlFeeBox fl"><p class="getTypeTxt fl">提醒方式</p>';
			strHtml += '<div class="getDlFeeType fl"><input type="hidden" id="remindInp" value="'+ json.feeTxType +'"/>';
			if(json.feeTxType == 0){//时间提醒
				if(json.feeTxFlag){//表示已经交过费用了，提醒方式不能修改
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="0" title="时间提醒" checked/>';
				}else{
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="0" title="时间提醒" checked/>';
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="1" title="事务提醒"/>';
					if(json.ajType1 == 'old'){
						strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="-1" title="无提醒"/>';
					}
				}
			}else if(json.feeTxType == 1){//事务提醒
				if(json.feeTxFlag){
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="1" title="事务提醒" checked/>';
				}else{
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="0" title="时间提醒"/>';
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="1" title="事务提醒" checked/>';
					if(json.ajType1 == 'old'){
						strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="-1" title="无提醒"/>';
					}
				}
			}else if(json.feeTxType == -1){
				if(json.feeTxFlag){
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="-1" title="无提醒" checked/>';
				}else{
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="0" title="时间提醒"/>';
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="1" title="事务提醒"/>';
					strHtml += '<input type="radio" name="remindInpRad" lay-filter="remindInpFilter" value="-1" title="无提醒" checked/>';
				}
			}
			strHtml += '</div><p class="initFeeTxt fl"><i class="layui-icon layui-icon-tips"></i><span class="singleDlFee"></span><span class="secInitDlFee"></span></p></div>';
			strHtml += '<div class="addDlFeeBox fmxxDlFee fl">';
			strHtml += '<div id="dlFeeListWrap">';
			if(json.feeTxType == 0 || json.feeTxType == 1){
				singleDlFeeNum = json.feeTxInfo.length;
				for(var i=0;i<json.feeTxInfo.length;i++){
					strHtml += '<div class="dlFeeList singleDlFeeList layui-form layui-clear">';
					if(json.feeTxType == 0){//时间提醒
						if(json.feeTxFlag == false){
							strHtml += '<input type="text" id="remdDateInp_'+ (i+1) +'" readonly class="layui-input remindDateInp" value="'+ json.feeTxInfo[i].feeTxOpt +'" placeholder="请选择提醒日期" autocomplete="off"/>';	
						}else{
							strHtml += '<input type="text" id="remdDateInp_'+ (i+1) +'" class="layui-input remindDateInp" value="'+ json.feeTxInfo[i].feeTxOpt +'" disabled placeholder="请选择提醒日期" autocomplete="off"/>';
						}
					}else if(json.feeTxType == 1){//事务提醒
						strHtml += '<div class="shiwuSel layui-form">';
						strHtml += '<input type="hidden" value="'+ json.feeTxInfo[i].feeTxOpt +'" class="shiwuInp singleInp_shiwu"/>';
						if(json.feeTxFlag == false){
							strHtml += '<select class="shiwuSelect singleSelect" lay-filter="shiwuSelectFilter">';	
						}else{
							strHtml += '<select class="shiwuSelect singleSelect" disabled lay-filter="shiwuSelectFilter">';
						}
						strHtml += '<option value="">请选择事务</option>';
						if(json.feeTxInfo[i].feeTxOpt == 'sl'){
							strHtml += '<option value="sl" selected>受理通知书</option>';
							strHtml += '<option value="gb">公布通知书</option>';
							if(json.ajType == 'fm'){
								strHtml += '<option value="sc">实质审查通知书</option>';
							}
							strHtml += '<option value="sq">授权通知书</option>';
						}else if(json.feeTxInfo[i].feeTxOpt == 'gb'){
							strHtml += '<option value="sl">受理通知书</option>';
							strHtml += '<option value="gb" selected>公布通知书</option>';
							if(json.ajType == 'fm'){
								strHtml += '<option value="sc">实质审查通知书</option>';
							}
							strHtml += '<option value="sq">授权通知书</option>';
						}else if(json.feeTxInfo[i].feeTxOpt == 'sc'){
							strHtml += '<option value="sl">受理通知书</option>';
							strHtml += '<option value="gb">公布通知书</option>';
							strHtml += '<option value="sc" selected>实质审查通知书</option>';
							strHtml += '<option value="sq">授权通知书</option>';
						}else if(json.feeTxInfo[i].feeTxOpt == 'sq'){
							strHtml += '<option value="sl">受理通知书</option>';
							strHtml += '<option value="gb">公布通知书</option>';
							if(json.ajType == 'fm'){
								strHtml += '<option value="sc">实质审查通知书</option>';
							}
							strHtml += '<option value="sq" selected>授权通知书</option>';
						}
						strHtml += '</select></div>';
					}
					strHtml += '<div class="dlFeeNumBox"><em class="moneyDec">¥</em>';
					if(json.feeTxFlag == false){
						strHtml += '<input id="dlFeeInpNum_'+ (i+1) +'" type="text" class="layui-input dlFeeInpNum dlFeeNum_dan" placeholder="请输入'+ zlTypeTxtStr +'专利代理费" value="'+ json.feeTxInfo[i].feePrice +'"/></div>';
						strHtml += '<i id="delDlFee_'+ (i+1) +'" class="layui-icon layui-icon-delete delDlFee" title="删除"></i>';	
					}else{
						strHtml += '<input id="dlFeeInpNum_'+ (i+1) +'" type="text" class="layui-input dlFeeInpNum dlFeeNum_dan" placeholder="请输入'+ zlTypeTxtStr +'专利代理费" disabled value="'+ json.feeTxInfo[i].feePrice +'"/></div>';
					}
					strHtml += '</div>';
				}
			}
			strHtml += '</div>';
			strHtml += '<span id="addDlFeeBtn" class="addSpan fl">添加代理费</span></div>';
			strHtml += '</div></div></div></div>';
			//案件技术领域
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>案件技术领域</label><div class="layui-input-block fixHeiDiv clearfix">';
			strHtml += '<div id="fieldBox" class="multiBox fl">';
			for(var i=0;i<json.jsFieldInfo.length;i++){
				if(json.jsFieldInfo[i].checked){
					strHtml += '<p id="fieldId_'+ json.jsFieldInfo[i].jsId +'" jffieldattrid="'+ json.jsFieldInfo[i].jsId +'" class="delFieldBtn"><span>'+ json.jsFieldInfo[i].jsName +'</span><i class="layui-icon layui-icon-close"></i></p>';	
					fieldArray.push('fieldId_'+json.jsFieldInfo[i].jsId);
				}
			}
			strHtml += '<input id="ajFieldId" type="hidden" name="ajFieldId"/></div>';
			if(this.data.upZlFlag){
				strHtml += '<span id="addFieldBtn" class="addSpan fl">添加技术领域</span></div></div>';
			}else{
				strHtml += '</div></div>';
			}
			//案件申请地区
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>案件申请地区</label>';
			strHtml += '<input id="ajSqAddress" type="hidden" name="ajSqAddress" value="'+ json.ajAddress +'"/><div class="layui-input-block fixHeiDiv clearfix">';
			if(json.ajAddress == '中国'){
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国" title="中国[CN]" checked/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国香港" title="中国香港[HK]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国台湾" title="中国台湾[TW]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="澳门" title="澳门[MO]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="日本" title="日本[JP]"/>';
			}else if(json.ajAddress == '中国香港'){
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国" title="中国[CN]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国香港" title="中国香港[HK]" checked/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国台湾" title="中国台湾[TW]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="澳门" title="澳门[MO]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="日本" title="日本[JP]"/>';
			}else if(json.ajAddress == '中国台湾'){
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国" title="中国[CN]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国香港" title="中国香港[HK]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国台湾" title="中国台湾[TW]" checked/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="澳门" title="澳门[MO]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="日本" title="日本[JP]"/>';
			}else if(json.ajAddress == '澳门'){
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国" title="中国[CN]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国香港" title="中国香港[HK]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国台湾" title="中国台湾[TW]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="澳门" title="澳门[MO]" checked/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="日本" title="日本[JP]"/>';
			}else{
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国" title="中国[CN]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国香港" title="中国香港[HK]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="中国台湾" title="中国台湾[TW]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="澳门" title="澳门[MO]"/>';
				strHtml += '<input type="radio" name="ajSqRad" lay-filter="ajSqAreaFilter" value="日本" title="日本[JP]" checked/>';
			}
			strHtml += '</div></div>';
			//申请人
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>申请人</label><input id="sqrIdInp" type="hidden"/>';
			strHtml += '<div class="layui-input-block fixHeiDiv clearfix"><input id="ajSqrId" type="hidden" value="'+ json.sqrId +'" name="ajSqrId"/>'; 
			strHtml += '<div id="sqrBox" class="multiBox fl">';
			var tmpArraySqrId = json.sqrId.split(',');
			var tmpArraySqrName = json.sqrName.split(',');
			sqrIdArray = tmpArraySqrId;
			sqrTxtArray = tmpArraySqrName;
			for(var i=0;i<tmpArraySqrName.length;i++){
				strHtml += '<div class="dgTjBox"><p id="sqrId_'+ tmpArraySqrId[i] +'" clsColor="'+ colorArray[i] +'" sqrattrname="'+ tmpArraySqrName[i] +'" sqrattrid="'+ tmpArraySqrId[i] +'" class="delSqrBtn '+ colorArray[i] +'"><span>'+ tmpArraySqrName[i] +'</span><i class="layui-icon layui-icon-close"></i></p><a class="getSqrDetailBtn" cusId="'+ tmpArraySqrId[i] +'" cusName="'+ tmpArraySqrName[i] +'" href="javascript:void(0)">详情</a></div>';
				sqrArray.push('sqrId_'+tmpArraySqrId[i]);
			}
			strHtml += '</div>';
			if(this.data.upZlFlag){
				strHtml += '<span id="addSqrBtn" class="addSpan fl">添加/编辑申请人</span>';
			}
			strHtml += '</div></div>';
			//联系人
			strHtml += '<div class="layui-form-item"><label class="layui-form-label">';
			if(json.ajType1 == 'old'){
				strHtml += '<span class="mustItem">*</span>';
			}
			strHtml += '事务联系人</label><div class="layui-input-block fixHeiDiv clearfix">';
			strHtml += '<input id="ajLxrId" type="hidden" value="'+ json.lxrId +'" name="ajLxrId"/><div class="multiBox fl" style="width:100%;">';
			strHtml += '<div id="lxrBox" class="multiBox fl">';
			if(json.lxrId != ''){
				var tmpArrayLxrId = json.lxrId.split(',');
				var tmpArrayLxrName = json.lxrName.split(',');
				var tmpArraySqrLxrId = json.sqrLxrId.split(',');
				lxrIdNum = tmpArrayLxrId;
				for(var i=0;i<tmpArrayLxrName.length;i++){
					strHtml += '<div class="dgTjBox"><p id="lxrId_'+ tmpArrayLxrId[i] +'" lxridattr="'+ tmpArrayLxrId[i] +'" sqrids="'+ tmpArraySqrLxrId[i] +'" class="lxFmTag delLxrBtn"><input type="hidden" class="lxrFmrInpHid" name="myParSqr_'+ tmpArraySqrLxrId[i] +'"><span>'+ tmpArrayLxrName[i] +'</span><i class="layui-icon layui-icon-close"></i></p><a class="getLxrInfoBtn" lxrId="'+ tmpArrayLxrId[i] +'" lxrName="'+ tmpArrayLxrName[i] +'" href="javascript:void(0)">详情</a></div>';
				}
			}
			strHtml += '</div>';
			if(this.data.upZlFlag){
				strHtml += '<span id="addlxrBtn" opts="addLxrOpts" class="addSpan fl">添加/编辑事务联系人</span></div>';
			}
			strHtml += '</div></div>';		
			//发明人
			strHtml += '<div class="layui-form-item"><label class="layui-form-label">';
			if(json.ajType1 == 'old'){
				strHtml += '<span class="mustItem">*</span>';
			}
			strHtml += '发明人</label><div class="layui-input-block fixHeiDiv clearfix">';
			strHtml += '<input id="ajFmrId" type="hidden" name="ajFmrId" value="'+ json.fmrId +'"/><div class="multiBox fl" style="width:100%;">';
			strHtml += '<div id="fmrBox" class="multiBox fl">';	
			if(json.fmrId != ''){
				var tmpArrayFmrId = json.fmrId.split(',');
				var tmpArrayFmrName = json.fmrName.split(',');
				var tmpArraySqrFmrId = json.sqrFmrId.split(',');
				fmrIdNum = tmpArrayFmrId;	
				for(var i=0;i<tmpArrayFmrName.length;i++){
					strHtml += '<div class="dgTjBox"><p id="fmrId_'+ tmpArrayFmrId[i] +'" fmridattr="'+ tmpArrayFmrId[i] +'" sqrids="'+ tmpArraySqrFmrId[i] +'" class="lxFmTag delFmrBtn"><input type="hidden" class="lxrFmrInpHid" name="myParSqr_'+ tmpArraySqrFmrId[i] +'">';
					if(i == 0){//指定第一发明人
						strHtml += '<span><em id="firstFmrId" class="firstNoteTxt fmrNoteTxt">[第一发明人]</em>'+ tmpArrayFmrName[i] +'</span><i class="layui-icon layui-icon-close"></i></p>';
					}else{
						strHtml += '<span>'+ tmpArrayFmrName[i] +'</span><i class="layui-icon layui-icon-close"></i></p>';
					}
					strHtml += '<a class="getFmrInfoBtn" fmrId="'+ tmpArrayFmrId[i] +'" fmrName="'+ tmpArrayFmrName[i] +'"  href="javascript:void(0)">详情</a></div>';
				}	
			}
			strHtml += '</div>';
			if(this.data.upZlFlag){
				strHtml += '<span id="addFmrBtn" opts="addFmrOpts" class="addSpan fl">添加/编辑发明人</span></div>';
			}
			strHtml += '</div></div>';
			//技术联系人
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>技术联系人</label><div class="layui-input-block fixHeiDiv clearfix">';
			strHtml += '<input id="jsLxrIdInp" type="hidden" value="'+ json.jsLxrId +'" name="jsLxrIdInp"/><div class="multiBox fl" style="width:100%;">';
			strHtml += '<div id="techLxrBox" class="multiBox fl">';
			if(json.jsLxrId != ''){
				var tmpArrayJsLxrId = json.jsLxrId.split(',');
				var tmpArrayJsLxrName = json.jsLxrName.split(',');
				var tmpArraySqrJsLxrId = json.sqrJsrId.split(',');
				jsLxrIdNum = tmpArrayJsLxrId;
				for(var i=0;i<tmpArrayJsLxrName.length;i++){
					strHtml += '<div class="dgTjBox"><p id="fmrId_'+ tmpArrayJsLxrId[i] +'" fmridattr="'+ tmpArrayJsLxrId[i] +'" sqrids="'+ tmpArraySqrJsLxrId[i] +'" class="lxFmTag delJsLxrBtn"><input type="hidden" class="lxrFmrInpHid" name="myParSqr_'+ tmpArraySqrJsLxrId[i] +'"><span>'+ tmpArrayJsLxrName[i] +'</span><i class="layui-icon layui-icon-close"></i></p><a class="getJsLxrInfoBtn" fmrId="'+ tmpArrayJsLxrId[i] +'" fmrName="'+ tmpArrayJsLxrName[i] +'" href="javascript:void(0)">详情</a></div>';
				}
			}
			strHtml += '</div>';
			if(this.data.upZlFlag){
				strHtml += '<span id="addJsLxrBtn" opts="addJsLxrOpts" class="addSpan fl">添加/编辑技术联系人</span></div>';
			}
			strHtml += '</div></div>';
			//付款方
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>付款方</label>';
			var payerPeo = json.payUserInfo == '' ? tmpArraySqrName[0] : json.payUserInfo;
			strHtml += '<div class="layui-input-block"><input id="payerInp" type="text" maxlength="30" value="'+ payerPeo +'" placeholder="请添加付款方" autocomplete="off" class="layui-input"/></div></div>';
			//案件优先权
			strHtml += '<div class="layui-form-item"><label class="layui-form-label">优先权</label><div class="blockDiv fixHeiDiv clearfix">';
			strHtml += '<input id="yxqDetailInp" type="hidden" name="yxqDetail"/><div id="yxqBox"><div id="innerYxqBox">';
			if(json.ajYxqDetail != ''){
				var tmpYxqZlNum = json.ajYxqDetail.split(':')[0].split(',');
				var tmpYxqSqArea = json.ajYxqDetail.split(':')[1].split(',');
				var tmpYxqDate = json.ajYxqDetail.split(':')[2].split(',');
				num = tmpYxqZlNum.length;
				for(var i=0;i<tmpYxqZlNum.length;i++){
					strHtml += '<div class="innerYxqBox">';
					strHtml += '<input placeholder="请输入案件申请号/专利号" type="text" value="'+ tmpYxqZlNum[i] +'" autocomplete="off" class="layui-input zlAjNumInp"/>';
					strHtml += '<div class="innerSelAddBox"><select class="zlYxqAreaInp" lay-filter="selAreaFilter">';
					strHtml += '<option value="">请选择</option>';
					if(tmpYxqSqArea[i] == '中国'){
						strHtml += '<option value="中国" selected>中国[CN]</option>';
						strHtml += '<option value="中国香港">中国香港[HK]</option>';
						strHtml += '<option value="中国台湾">中国台湾[TW]</option>';
						strHtml += '<option value="澳门">澳门[MO]</option>';
						strHtml += '<option value="日本">日本[JP]</option>';
					}else if(tmpYxqSqArea[i] == '中国香港'){
						strHtml += '<option value="中国">中国[CN]</option>';
						strHtml += '<option value="中国香港" selected>中国香港[HK]</option>';
						strHtml += '<option value="中国台湾">中国台湾[TW]</option>';
						strHtml += '<option value="澳门">澳门[MO]</option>';
						strHtml += '<option value="日本">日本[JP]</option>';
					}else if(tmpYxqSqArea[i] == '中国台湾'){
						strHtml += '<option value="中国">中国[CN]</option>';
						strHtml += '<option value="中国香港">中国香港[HK]</option>';
						strHtml += '<option value="中国台湾" selected>中国台湾[TW]</option>';
						strHtml += '<option value="澳门">澳门[MO]</option>';
						strHtml += '<option value="日本">日本[JP]</option>';
					}else if(tmpYxqSqArea[i] == '澳门'){
						strHtml += '<option value="中国">中国[CN]</option>';
						strHtml += '<option value="中国香港">中国香港[HK]</option>';
						strHtml += '<option value="中国台湾">中国台湾[TW]</option>';
						strHtml += '<option value="澳门" selected>澳门[MO]</option>';
						strHtml += '<option value="日本">日本[JP]</option>';
					}else if(tmpYxqSqArea[i] == '日本'){
						strHtml += '<option value="中国">中国[CN]</option>';
						strHtml += '<option value="中国香港">中国香港[HK]</option>';
						strHtml += '<option value="中国台湾">中国台湾[TW]</option>';
						strHtml += '<option value="澳门">澳门[MO]</option>';
						strHtml += '<option value="日本" selected>日本[JP]</option>';
					}
					strHtml += '</select></div>';
					strHtml += '<input placeholder="请选择日期" type="text" name="" autocomplete="off" id="dateInp_'+ (i+1) +'" value="'+ tmpYxqDate[i] +'" class="layui-input yxqInpDate"/>';
					if(this.data.upZlFlag){
						strHtml += '<i class="layui-icon layui-icon-delete delYxqIcon"  title="删除"></i>';
					}
					strHtml += '</div>';
				}
			}
			strHtml += '</div>';
			if(this.data.upZlFlag){
				strHtml += '<span id="addYxqBtn" class="addSpan margLAddSp fl">添加优先权</span></div>';
			}
			strHtml += '</div></div>';
			//案件额外要求
			strHtml += '<div class="layui-form-item"><label class="layui-form-label">案件额外要求</label>';
			strHtml += '<input id="ajEwyqId" type="hidden" name="ajEwyqId"/><input id="tmpAjEwyqId" type="hidden"/>';
			strHtml += '<div id="ewyqBox" class="layui-input-block">';
			if(json.yqInfo.length > 0){
				for(var i=0;i<json.yqInfo.length;i++){
					if(json.yqInfo[i].checked){
						strHtml += '<input type="checkbox" name="zlYqInfoInp" lay-filter="ewyqFilter" value="'+ json.yqInfo[i].yqId +'" title="'+ json.yqInfo[i].yaContent +'" lay-skin="primary" checked/>';
						tmpEwyqIdArray.push(json.yqInfo[i].yqId);
					}else{
						strHtml += '<input type="checkbox" name="zlYqInfoInp" lay-filter="ewyqFilter" value="'+ json.yqInfo[i].yqId +'" title="'+ json.yqInfo[i].yaContent +'" lay-skin="primary"/>';	
					}
				}
			}else{
				strHtml += '<p class="noYqData"><i class="layui-icon layui-icon-face-cry"></i>暂无此专利类型的案件专利额外要求，如若需要，请联系超级管理员进行添加</p>';
			}
			strHtml += '</div></div>';
			//案件备注
			strHtml += '<div class="layui-form-item"><label class="layui-form-label">案件备注</label><div class="layui-input-block">';
			strHtml += '<textarea id="ajRemark" name="ajRemark" placeholder="请输入案件备注(80字以内)" class="layui-textarea" maxlength="80">'+ json.ajRemark +'</textarea>';
			strHtml += '</div></div>';
			//定稿提交截至时间
			strHtml += '<div class="layui-form-item"><label class="layui-form-label"><span class="mustItem">*</span>定稿提交截止时间</label><div class="layui-input-inline">';
			strHtml += '<input type="text" class="layui-input" id="cpyDateInp" value="'+ json.cpyDate +'" name="cpyDate"/></div><div class="layui-form-mid layui-word-aux">(系统默认一个月)</div></div>';
			if(this.data.upZlFlag){
			//案件附件
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">';
				if(json.ajType1 == 'old'){
					strHtml += '<span class="mustItem">*</span>';
				}
				strHtml += '案件附件</label><div class="layui-input-block">';
				strHtml += '<button type="button" class="layui-btn layui-btn-normal" id="selFileBtn" style="display:inline-block;">选择文件</button>';
				strHtml += '  <span class="noticeSpan">注：附件中单个图片大小不能超过5M，单个文件大小不能超过10M (非必填项)，最多可上传5个文件</span>';
				strHtml += '<button type="button" class="layui-btn" id="upListAction">上传附件</button></div></div>';
				strHtml += '<div class="layui-form-item"><input id="fujianInp" name="zlUpCl" type="hidden"/>';
			}else{
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">案件附件</label><input id="fujianInp" name="zlUpCl" type="hidden"/>';
			}
			strHtml += '<div class="layui-input-block tabParents"><table class="layui-table" style="width:100%;"><thead>';
			strHtml += '<tr><th>附件名</th><th>附件类型</th><th>专利类型</th><th>大小</th><th>状态</th><th>进度</th><th>操作</th></tr></thead>';
			strHtml += '<tbody id="upLoadFileList">';
			var zlTypeTxt = globalUpload.switchZlTypeCHN(json.ajType);
			if(json.upFile.length > 0){
				var zlUpClArray = json.upFile; //技术底稿
				for(var i=0;i<zlUpClArray.length;i++){
					strHtml += '<tr id="upload-'+ i +'">';
					strHtml += '<td style="max-width:260px;">'+ zlUpClArray[i].fileName +'</td>';
					strHtml += '<td style="width:130px;"><p>技术底稿</p></td>';
					strHtml += '<td><div class="zlTypeTxt layui-form">'+ zlTypeTxt +'</div></td>';
					strHtml += '<td>'+ zlUpClArray[i].fileSize +'</td>';
					strHtml += '<td><span style="color: #5FB878;">上传成功</span></td>';
					strHtml += '<td style="width:120px;"><div class="layui-progress layui-progress-big" lay-showpercent="true">';
					strHtml += '<div class="layui-progress-bar progressBarBg" lay-percent="100%" style="width:100%;"><span class="layui-progress-text">100%</span></div></div>';
					strHtml += '</td>';
					strHtml += '<td>';
					strHtml += '<input class="uploadInpHid ajJsPathInp" name="hasUpSuccInp" value="'+ zlUpClArray[i].upPath +'" type="hidden"/>';
					strHtml += '<button class="layui-btn layui-btn-xs layui-btn-danger deleteBtn deleteBtn_edit">删除</button>';
					strHtml += '<button class="layui-btn layui-btn-xs downloadBtn" downFilePath="'+ zlUpClArray[i].downFilePath +'">下载</button></td>';
					strHtml += '</tr>';
				}
			}
			if(json.upFileDg != undefined && json.upFileDg.length > 0){
				var zlUpDgArray = json.upFileDg;//定稿文件
				for(var i=0;i<zlUpDgArray.length;i++){
					strHtml += '<tr id="upload-'+ i +'">';
					strHtml += '<td style="max-width:260px;">'+ zlUpDgArray[i].fileName +'</td>';
					strHtml += '<td style="width:130px;"><p>定稿文件</p></td>';
					strHtml += '<td><div class="zlTypeTxt layui-form"><p>'+ zlTypeTxt +'</p></div></td>';
					strHtml += '<td>'+ zlUpDgArray[i].fileSize +'</td>';
					strHtml += '<td><span style="color: #5FB878;">上传成功</span></td>';
					strHtml += '<td style="width:120px;"><div class="layui-progress layui-progress-big" lay-showpercent="true">';
					strHtml += '<div class="layui-progress-bar progressBarBg" lay-percent="100%" style="width:100%;"><span class="layui-progress-text">100%</span></div></div>';
					strHtml += '</td>';
					strHtml += '<td>';
					strHtml += '<input class="uploadInpHid ajDgPathInp" name="hasUpSuccInp" value="'+ zlUpDgArray[i].upPath +'" type="hidden"/>';
					strHtml += '<button class="layui-btn layui-btn-xs layui-btn-danger deleteBtn deleteBtn_edit">删除</button>';
					strHtml += '<button class="layui-btn layui-btn-xs downloadBtn" downFilePath="'+ zlUpDgArray[i].downFilePath +'">下载</button></td>';
					strHtml += '</tr>';
				}
			}
			if(json.upFileHt != undefined && json.upFileHt.length > 0){
				var zlUpHtArray = json.upFileHt;//合同文件
				for(var i=0;i<zlUpHtArray.length;i++){
					strHtml += '<tr id="upload-'+ i +'">';
					strHtml += '<td style="max-width:260px;">'+ zlUpHtArray[i].fileName +'</td>';
					strHtml += '<td style="width:130px;"><p>合同文件</p></td>';
					strHtml += '<td><div class="zlTypeTxt layui-form"><p class="noZlTypeTxt">无需指派专利类型</p></div></td>';
					strHtml += '<td>'+ zlUpHtArray[i].fileSize +'</td>';
					strHtml += '<td><span style="color: #5FB878;">上传成功</span></td>';
					strHtml += '<td style="width:120px;"><div class="layui-progress layui-progress-big" lay-showpercent="true">';
					strHtml += '<div class="layui-progress-bar progressBarBg" lay-percent="100%" style="width:100%;"><span class="layui-progress-text">100%</span></div></div>';
					strHtml += '</td>';
					strHtml += '<td>';
					strHtml += '<input class="uploadInpHid ajHtPathInp" name="hasUpSuccInp" value="'+ zlUpHtArray[i].upPath +'" type="hidden"/>';
					strHtml += '<button class="layui-btn layui-btn-xs layui-btn-danger deleteBtn deleteBtn_edit">删除</button>';
					strHtml += '<button class="layui-btn layui-btn-xs downloadBtn" downFilePath="'+ zlUpHtArray[i].downFilePath +'">下载</button></td>';
					strHtml += '</tr>';
				}
			}
			strHtml += '</tbody></table>';
			strHtml += '</div></div>';
			if(this.data.upZlFlag){
				//保存按钮
				strHtml += '<div class="layui-form-item"><label class="layui-form-label"></label><div class="layui-input-block" style="width:75%;text-align:center;">';
				strHtml += '<button type="button" id="saveZlBtn" class="layui-btn" style="width:120px;margin-left:0px;">保存专利</button></div></div>';
			}
			$('#addNewZl').html(strHtml);
			this.judgeZlTitExist('ajTitleInp');//判断专利标题是否存在
			commonAddColorCls();//不同申请人对应其下联系人 发明人 技术联系人 显示对应申请人色块
			json.ajType1 == 'old' ? $('.dlFeeSpan').hide() : $('.dlFeeSpan').show();
			form.render();
			if(this.data.upZlFlag){
				if(json.feeTxType == 0 || json.feeTxType == 1){
					for(var i=0;i<json.feeTxInfo.length;i++){
						laydate.render({
							elem:'#remdDateInp_' + (i+1),
							min:this.getMinDate()
						});
						this.agentDlFee_usual('dlFeeInpNum_'+(i+1));
						this.delCurrDlFee('delDlFee_'+(i+1));
					}
				}
				for(var i=0;i<num;i++){
					laydate.render({
						elem:'#dateInp_' + (i+1)
					});
				}
				if(json.ajYxqDetail != ''){	
					this.delYxq();
				}
				laydate.render({
					elem: '#cpyDateInp'
				});
				this.selectFileUpload();//编辑时渲染结构完成调用上传方法
				delHasAddMethod('delFieldBtn','delFieldOpt');
				delHasAddMethod('delSqrBtn','delSqrOpt');
				if(json.lxrId != ''){
					delHasAddMethod('delLxrBtn','delLxrOpt');
				}
				if(json.fmrId != ''){
					delHasAddMethod('delFmrBtn','delFmrOpt');
				}
				delHasAddMethod('delJsLxrBtn','delJsLxrOpt');
			}
			if(json.pubZlName != ''){//编辑首次进来页面判断是否存在已添加的专利任务存在激活重置
				parent.$('#lqZlIdInp').val(json.pubZlId);//将已经存在数据库的pubZlId赋给父级用于弹窗页面的回显
	  		}
			if($('.downloadBtn').length > 0){
				$('.downloadBtn').on('click',function(){
					var downFilePath = $(this).attr('downFilePath');
					common.downFiles(downFilePath,0);
				});
			}
			if(parent.zlStrOpts == 'zlDetailOpts'){
				lcfpMethod.rateFun('diffLevelOne',json.zlLevel,isReadFlag);
			}else{//我的专利编辑下显示当前案件难易度
				common.rateFunReadOnly('diffLevelOne',json.zlLevel);
			}
			if(json.ajType1 == 'old'){
				this.checkOldAjNumPattern('ajSqZlNum','');
			}
			this.agentFeeTxtByZlType(json.ajType);
			var dlFeeNum =  this.getCpyDlFee(json.ajType);//获取代理机构指定专利类型费用;
			$('#singDlFeeNum').text(dlFeeNum);
			//查看申请人 发明人 联系人基本信息
			viewDetInfo_cus();
			viewDetInfo_lxr();
			viewDetInfo_fmr();
			viewDetInfo_jslxr();
		},
		//获取流程明细(主流程)
		loadLcDetail : function(json){
			var lcInfo = json.lcInfo;
			var strHtml = '';
			strHtml += '<ul>';
			for(var i=0,iLen=lcInfo.length;i<iLen;i++){
				if(lcInfo[i].comDate != ''){//表示完成
					strHtml += '<li class="viewMxBtn" lcId="'+ lcInfo[i].lcId +'" lcNo = "'+ lcInfo[i].lcNo +'" lcName="'+ lcInfo[i].lcName +'"><p class="oneWid ellip"><i class="layui-icon layui-icon-ok statusIcon_com_1" title="已完成"></i>'+ lcInfo[i].lcName +'</p>';
				}else{
					strHtml += '<li class="viewMxBtn" lcId="'+ lcInfo[i].lcId +'" lcNo = "'+ lcInfo[i].lcNo +'" lcName="'+ lcInfo[i].lcName +'"><p class="oneWid ellip"><i class="iconfont layui-extend-jinhangzhong statusIcon" title="进行中"></i>'+ lcInfo[i].lcName +'</p>';
				}
				strHtml += '<p class="twoWid">'+ lcInfo[i].sDate +'</p>';
				strHtml += '<p class="twoWid">'+ lcInfo[i].cpyDate +'</p>';
				if(lcInfo[i].gfDate != ''){
					strHtml += '<p class="twoWid">'+ lcInfo[i].gfDate +'</p>';	
				}else{
					strHtml += '<p class="twoWid">&nbsp;</p>';
				}
				if(lcInfo[i].comDate != ''){
					strHtml += '<p class="twoWid">'+ lcInfo[i].comDate +'</p>';
				}else{
					strHtml += '<p class="twoWid">&nbsp;</p>';
				}
				strHtml += '<p class="threeWid"><a class="viewDetailBtn" href="javascript:void(0)" lcNo="'+lcInfo[i].lcNo  +'" lcId="'+ lcInfo[i].lcId +'" lcName="'+ lcInfo[i].lcName +'">查看明细</a></p></li>';
			}
			strHtml +=  '</ul>';
			$('#lcDetailCon').html(strHtml);
			$('#lcDetailCon li:odd').addClass('oddColor');
			$('#lxMxDetailCon').height($(window).height()-41);
			this.bindEvent_lc();
		},
		//获取流程明细(子流程)
		loadLxMxDetail : function(json,lcNo){
			var mxInfo = json.mxInfo;
			var strHtml = '';
			if(lcNo == 2){
				strHtml += '<ul id="mxDetailConUl">';
				for(var i=0,iLen = mxInfo.length;i<iLen;i++){
					strHtml += '<li><p>'+ mxInfo[i].mxName +'</p>';
					if(mxInfo[i].fzUserName != ''){
						strHtml += '<p>'+ mxInfo[i].fzUserName +'</p>';
					}else{
						strHtml += '<p>&nbsp;</p>';
					}
					strHtml += '<p>'+ mxInfo[i].mxSDate +'</p>';
					if(mxInfo[i].mxEDate != ''){
						strHtml += '<p>'+ mxInfo[i].mxEDate +'</p>';
					}else{
						strHtml += '<p>&nbsp;</p>';
					}
					strHtml += '</li>';
				}
				strHtml += '</ul>';
				$('#mxDetailCon').html(strHtml);
				$('#mxDetailConUl li:odd').addClass('oddColor');
			}else{
				strHtml += '<ul id="mxDetailConUl">';
				for(var i=0,iLen = mxInfo.length;i<iLen;i++){
					strHtml += '<li class="otherLi hasFlex"><strong>子节点名称 ：</strong><p>'+ mxInfo[i].mxName +'<p></li>';
					strHtml += '<li class="otherLi hasFlex"><strong>流程负责人：</strong><p>'+ mxInfo[i].fzUserName +'<p></li>';
					strHtml += '<li class="otherLi hasFlex"><strong>开始时间：</strong><p>'+ mxInfo[i].mxSDate +'<p></li>';
					if(mxInfo[i].mxEDate != ''){
						strHtml += '<li class="otherLi hasFlex"><strong>结束时间：</strong><p>'+ mxInfo[i].mxEDate +'</p></li>';
					}else{
						strHtml += '<li class="otherLi hasFlex"><strong>结束时间：</strong><p>&nbsp;</p></li>';
					}
					var upFileDetail = mxInfo[i].upFileDetail;
					strHtml += '<li class="otherLi noFlex clearfix"><strong>附件：</strong>';
					if(upFileDetail.length != 0){
						for(var j=0;j<upFileDetail.length;j++){
							strHtml += '<p><i class="layui-icon layui-icon-file"></i>'+ upFileDetail[j].upFileName +' <a href="javascript:void(0)" class="downLoadBtn layui-btn layui-btn-xs" downFilePath="'+ upFileDetail[j].downFilePath +'">下载</a></p>';
						}
					}else{
						strHtml += '<p class="hasNoTxt">暂无附件</p>';
					}
					strHtml += '</li>';
					if(lcNo >=4 && lcNo < 5 ){//增加专利审核审核意见
						strHtml += '<li class="otherLi hasFlex"><strong>审核意见：</strong>';
						if(mxInfo[i].lcPjScore == 0){//审核未通过
							strHtml += '<p class="notPassTxt"><i class="layui-icon layui-icon-face-cry"></i>未通过，退回修改</p>';
						}else{
							var lcScoreVal = 0;
							if(mxInfo[i].lcPjScore == -1){
								strHtml += '<p class="hasNoTxt">暂未审核</p>';
							}else if(mxInfo[i].lcPjScore == 1){
								lcScoreVal = 1;
								strHtml += '<p id="judgeStar">一般</p>';
							}else if(mxInfo[i].lcPjScore == 2){
								lcScoreVal = 2;
								strHtml += '<p id="judgeStar">良</p>';
							}else if(mxInfo[i].lcPjScore == 5){
								lcScoreVal = 5;
								strHtml += '<p id="judgeStar">优秀</p>';
							}
						}
						strHtml += '</li>';
					}else if(lcNo >=5 && lcNo < 6){//客户确认增加客户审核意见
						strHtml += '<li class="otherLi hasFlex"><strong>审核意见：</strong>';
						if(mxInfo[i].cusCheckStatus == '客户确认通过'){
							strHtml += '<p class="cusPassTxt">客户确认通过</p>';
						}else{
							strHtml += '<p class="cusNoPassTxt">客户审核未通过</p>';
						}
						strHtml += '</li>';
					}
					strHtml += '<li class="otherLi noFlex clearfix"><strong>备注：</strong><p>'+ mxInfo[i].mxRemark +'</p></li>';
				}
				strHtml += '</ul>';
				$('#mxDetailCon').html(strHtml);
				rate.render({
				    elem: '#judgeStar'
				    ,value: lcScoreVal
				    ,length:3
				    ,text: true
				    ,readonly: true
				    ,setText: function(value){ //自定义文本的回调
				      var arrs = {
				        '1': '一般'
				        ,'2': '良'
				        ,'3': '优'
				      };
				      this.span.text(arrs[value]);
				    }
				});
				$('.downLoadBtn').on('click',function(){
					var downFilePath = $(this).attr('downFilePath');
					common.downFiles(downFilePath,0);
				});
			}
			this.addActScrollBar();
		},
		//检测高度动态给容器添加模拟滚动条
		addActScrollBar : function(){
			if($('#mxDetailCon').height() < $('#mxDetailConUl').height()){
				var oScroll = '<div id="scrollParent" class="parentScroll"><div id="scrollSon" class="scrollBar"></div></div>';
				//创建动态模拟滚动条
				$('#mxDetailCon').append(oScroll);
				commonScrollBar.scrollBar('mxDetailCon','mxDetailConUl','scrollParent','scrollSon',25);
			}
		},
		//流程的事件绑定
		bindEvent_lc : function(){
			var _this = this;
			//查看流程明细时间
			$('.viewDetailBtn').on('click',function(e){
				var lcId = $(this).attr('lcId'),lcName = $(this).attr('lcName'),lcNo = $(this).attr('lcNo');
				$('.closeBtns').attr('opts','closeLcMx');
				if(lcNo == 2){
					$('.mxDetailTit').show().addClass('hasPos');
					$('#mxDetailCon').addClass('hasPos');
				}else{
					$('.mxDetailTit').hide().removeClass('hasPos');
					$('#mxDetailCon').removeClass('hasPos');
				}
				$('#lxMxDetailCon').addClass('animation').removeClass('animationClose');
				$('#mxTit').html(lcName + '--明细详情');
				_this.getMxDetailInfo(lcId,lcNo);
				_this.data.actGetMxFlag = true;
				e.stopPropagation();
			});
			$('.viewMxBtn').on('click',function(){
				if(_this.data.actGetMxFlag){//表示当前明细详情层已经被打开，点击主流程标题可直接更换明细详情
					var lcId = $(this).attr('lcId'),lcName = $(this).attr('lcName'),lcNo = $(this).attr('lcNo');
					if(lcNo == '2'){
						$('.mxDetailTit').show().addClass('hasPos');
						$('#mxDetailCon').addClass('hasPos');
					}else{
						$('.mxDetailTit').hide().removeClass('hasPos');
						$('#mxDetailCon').removeClass('hasPos');
					}
					$('#mxTit').html(lcName + '--明细详情');
					_this.getMxDetailInfo(lcId,lcNo);
				}
			});
		},
		//获取主流程对应的明细详情
		getMxDetailInfo : function(lcId,lcNo){
			var _this = this,noData='<div class="noData" style="display:block;"><i class="iconfont layui-extend-noData"></i><p>暂无明细记录<p></div>';
			$.ajax({
				type:'post',
		        async:false,
		        dataType:'json',
		        data : {lcId : lcId},
		        url:'/zlm.do?action=getLcMxDetail',
		        success:function (json){
		        	layer.closeAll('loading');
		        	if(json['result'] == 'success'){
		        		_this.loadLxMxDetail(json,lcNo);
		        	}else if(json['result'] == 'noInfo'){
		        		$('#mxDetailCon').html(noData);
		        	}
		        }
				});
		},
		//旧案下根据提醒方式判断代理费是否为必选
		judgeDlFeeIsMustSel : function(){
			var remindInpVal = $('#remindInp').val(),isMustFlag = false;
			//true 为必选必填 提醒方式为0 1的时候
			if(remindInpVal == '0' || remindInpVal == '1'){
				isMustFlag = true;
			}else if(remindInpVal == '' || remindInpVal == '-1'){
				isMustFlag = false;//非必选 空或-1
			}
			return isMustFlag;
		},
		//判断案件标题是否重复或已存在
		judgeZlTitExist : function(obj){
			$('#' + obj).on('blur',function(){
				var tmpVal = $.trim(escape($('#' + obj).val()));
				if(addEditZlOpts == 'addZlOpts'){//增加专利
					var field = {ajTitle:tmpVal};
				}else if(addEditZlOpts == 'editZlOpts'){
					var field = {zlId:parent.globalZlId,ajTitle:tmpVal};
				}
				layer.load('1');
				$.ajax({
   					type:'post',
   			        dataType:'json',
   			        data:field,
   			        url:'/zlm.do?action=checkExistByZlTitle',
   			        success:function (json){
   			        	layer.closeAll('loading');
   			        	if(json['result'] == 'exist'){//填写 当重复时
   			        		$('#'+obj).next().show().html('<i class="layui-icon layui-icon-tips"></i>当前案件标题已存在');
							return;
   			        	}else if(json['result'] == 'error'){//为空没有填写时
   			        		$('#'+obj).next().show().html('<i class="layui-icon layui-icon-tips"></i>案件标题不能为空');
							return;
   			        	}else if(json['result'] == 'noInfo'){//right
   			        		$('#'+obj).next().hide().html('');
   			        	}
   			        }
   				});
			});
		},
		bindEvent_comMet : function(){
			var _this = this;
			$('.closeBtns').on('click',function(){
				var index= parent.layer.getFrameIndex(window.name),opts = $(this).attr('opts');
				if(opts == 'closeGlobal'){//表示关闭整个弹层
					parent.layer.close(index);
				}else{
					$('#lxMxDetailCon').addClass('animationClose').removeClass('animation');
					$('.closeBtns').attr('opts','closeGlobal');
					_this.data.actGetMxFlag = false;
				}
			});
			//保存 编辑专利
			$('#saveZlBtn').on('click',function(){
				var anjianTypeVal=$('#anjianType').val(),sqrName='',tmpFmPath=[],tmpXxPath=[], ajTitleVal = $.trim($('#ajTitleInp').val()),//案件标题
					//ajNumInpVal = $('#ajNumInp').val(),//案件编号(旧案下使用)
					oldAjSqDateInpVal = $('#oldAjSqDateInp').val(),//旧案下增加案件申请日
					remindInpVal = $('#remindInp').val(),//专利代理费提醒方式
					anjianTypeVal = $('#anjianType').val(),//案件类型 新案/旧案
					zlTypeInpVal = $('#zlTypeInp').val(),//专利类型
					zlAgentFeeInpVal = $('#zlAgentFeeInp').val(),//发明、外观、实用新型对应专利代理费
					xxAgentFeeInpVal = $('#xxAgentFeeInp').val(),//新型对应的专利代理费
					ajFieldIdVal = $('#ajFieldId').val(),//案件技术领域
					ajSqAddressVal = $('#ajSqAddress').val(),//案件申请地区
					ajSqrIdVal = $('#ajSqrId').val(),//案件申请人
					ajLxrIdVal = $('#ajLxrId').val(),//案件事务联系人
					ajFmrIdVal = $('#ajFmrId').val(),//案件发明人
					jsLxrIdInpVal = $('#jsLxrIdInp').val(),//技术联系人
					yxqDetailInpVal = $('#yxqDetailInp').val(),//案件优先权
					//fujianInpVal = $('#fujianInp').val(),//案件附件
					ajEwyqIdVal = $('#ajEwyqId').val(),//案件额外要求
					ajEwyqId_xxVal = $('#tmpAjEwyqId_xx').val(),//案件额外要求->新型
					ajRemarkVal = $('#ajRemark').val(),//案件备注
					cpyDateInpVal = $('#cpyDateInp').val(),//定稿提交截至时间
					pubZlIdInpVal = $('#pubZlIdInp').val(),//已领取专利任务
					payerInpVal = $('#payerInp').val(),//付款方
					url = '',fmPathVal = '',xxPathVal = '',
					globalDlFee='',globalDlFee_fm='',globalDlFee_xx='',ajEyYqIdStr='',//发明+新型下的组合字符串
					ajJsPathVal='',ajHtPathVal='',ajDgPathVal_fm='',ajDgPathVal_xx='',ajDgPathVal='',isFjTypeFlag=true;
				ajFieldIdVal = _this.getIdStr('delFieldBtn','jffieldattrid');
				ajSqrIdVal = _this.getIdStr('delSqrBtn','sqrattrid');
				sqrName = _this.getIdStr('delSqrBtn','sqrattrname');
				ajLxrIdVal = _this.getIdStr('delLxrBtn','lxridattr');
				ajFmrIdVal = _this.getIdStr('delFmrBtn','fmridattr');
				ajJsLxrIdVal = _this.getIdStr('delJsLxrBtn','fmridattr');
				//alert(ajFieldIdVal + "----" + ajSqrIdVal + "--" + ajLxrIdVal + "--" + ajFmrIdVal + "----@@" + ajJsLxrIdVal)
				if(zlTypeInpVal != 'fmxx'){
					if(tmpEwyqIdArray.length != 0){
						$('#tmpAjEwyqId').val(tmpEwyqIdArray.join(','));
						ajEwyqIdVal = $('#tmpAjEwyqId').val();
					}else{
						$('#tmpAjEwyqId').val('');
					}
				}else{
					//01->1,2,3:''  02-> '':3,4,5    03->'' 04->1,2,3:4,5,6
					if(tmpEwyqIdArray.length != 0 && tmpEwyqIdArray_xx.length != 0){
						$('#tmpAjEwyqId').val(tmpEwyqIdArray.join(','));
						$('#tmpAjEwyqId_xx').val(tmpEwyqIdArray_xx.join(','));
						ajEyYqIdStr = $('#tmpAjEwyqId').val() + ':' + $('#tmpAjEwyqId_xx').val();
					}else if(tmpEwyqIdArray.length != 0 && tmpEwyqIdArray_xx.length == 0){
						$('#tmpAjEwyqId').val(tmpEwyqIdArray.join(','));
						$('#tmpAjEwyqId_xx').val('');
						ajEyYqIdStr = $('#tmpAjEwyqId').val() + ':' + '';
					}else if(tmpEwyqIdArray.length == 0 && tmpEwyqIdArray_xx.length != 0){
						$('#tmpAjEwyqId').val('');
						$('#tmpAjEwyqId_xx').val(tmpEwyqIdArray_xx.join(','));
						ajEyYqIdStr = '' + ':' + $('#tmpAjEwyqId_xx').val();
					}else if(tmpEwyqIdArray.length == 0 && tmpEwyqIdArray_xx.length == 0){
						ajEyYqIdStr = '';
					}	
				}
				if(anjianTypeVal == ''){
					layer.msg('请选择案件类型', {icon:5,anim:6,time:1500});
				}else if(zlTypeInpVal == ''){
					layer.msg('请选择专利类型', {icon:5,anim:6,time:1500});
				}else{
					var isNewAjTypeFlag = true,isOldAjRightFlag=false;//旧案下各个条件是否都已满足，新案下为true
					var fmZlTitInpVal = $('#fmZlTitInp').val(),xxZlTitInpVal = $('#xxZlTitInp').val(),fmSqDateInpVal = $('#fmSqDateInp').val(),xxSqDateInpVal = $('#xxSqDateInp').val();
					//案件类型 分为新建new 和 旧案 old
					anjianTypeVal == 'new' ? isNewAjTypeFlag : isNewAjTypeFlag = false;
					if(!isNewAjTypeFlag){//旧案下增加案件申请日判断
						if(oldAjSqDateInpVal == '' && _this.data.switchSqDateFlag || fmSqDateInpVal == '' || xxSqDateInpVal == ''){
							layer.msg('请选择案件申请日', {icon:5,anim:6,time:1500});
							return;
						}
					}
					if(ajTitleVal == '' && _this.data.switchZlTitFlag || fmZlTitInpVal == '' || xxZlTitInpVal == ''){
						layer.msg('案件标题不能为空', {icon:5,anim:6,time:1500});
					}else if(isNewAjTypeFlag == false){//旧案
						//判断费减 案件专利号
						var rateInpVal = $('#rateInp').val(),ajSqZlNumVal = $('#ajSqZlNum').val();
						if(rateInpVal == ''){
							layer.msg('请选择费减', {icon:5,anim:6,time:1500});
							return;
						}
						if(zlTypeInpVal != 'fmxx'){
							if(ajSqZlNumVal == ''){
								layer.msg('请填写旧案专利号', {icon:5,anim:6,time:1500});
								return;
							}
						}else{//专利类型为发明+新型
							var fmZlNumInpVal = $('#fmZlNumInp').val(),xxZlNumInpVal = $('#xxZlNumInp').val();
							if(fmZlNumInpVal == ''){
								layer.msg('请填写旧案发明专利号', {icon:5,anim:6,time:1500});
								return;
							}
							if(xxZlNumInpVal == ''){
								layer.msg('请填写旧案新型专利号', {icon:5,anim:6,time:1500});
								return;
							}
						}
						isOldAjRightFlag = true;//旧案下需要填写的都已满足条件
					}else if(isNewAjTypeFlag){//新案下isOldAjRightFlag变为true
						isOldAjRightFlag = true;
					}
					if(isOldAjRightFlag){
						//代理费和对应提醒方式 新案下必填 旧案下为选填 （没有填写 提醒:-1 代理费：''）
						var dlFeeListDivLen = $('#dlFeeListWrap div').length,
							dlFeeList_xxLen = $('#dlFeeListWrap_xx div').length,
							isDlFeeRightFlag = false;//检测代理费是否填写正确
							fmxxZlNumStr = fmZlNumInpVal + ',' + xxZlNumInpVal,ajSqDateStr='';
							//组装案件申请日
							if(!isNewAjTypeFlag){//旧案下
								if(zlTypeInpVal != 'fmxx'){
									ajSqDateStr = oldAjSqDateInpVal;
								}else{
									ajSqDateStr = fmSqDateInpVal + ',' + xxSqDateInpVal;
								}
							}
						var isMustFlag = _this.judgeDlFeeIsMustSel();
						if(isNewAjTypeFlag){
							if(remindInpVal == ''){
								layer.msg('请选择提醒方式', {icon:5,anim:6,time:1500});
								return;
							}
						}
						if(isMustFlag){//选择了提醒方式 且值为0 1
							if(dlFeeListDivLen == 0 && zlTypeInpVal != 'fmxx'){
								layer.msg('请添加相应专利代理费', {icon:5,anim:6,time:1500});
							}else if(dlFeeListDivLen >= 0 && dlFeeList_xxLen == 0 && zlTypeInpVal == 'fmxx'){
								layer.msg('请添加发明和新型相应专利代理费', {icon:5,anim:6,time:2000});
							}else if(dlFeeListDivLen == 0 && dlFeeList_xxLen >= 0 && zlTypeInpVal == 'fmxx'){
								layer.msg('请添加发明和新型相应专利代理费', {icon:5,anim:6,time:2000});
							}else if(dlFeeListDivLen > 0 || dlFeeListDivLen){
								var strResult = _this.getNewDlFeeRes();
								if(strResult != undefined){
									isDlFeeRightFlag = true;
									if(zlTypeInpVal != 'fmxx'){
										globalDlFee = strResult;
									}else{
										globalDlFee_fm = strResult.split('@')[0];
										globalDlFee_xx = strResult.split('@')[1];
									}
								}else{
									isDlFeeRightFlag = false;
									return;
								}
							}
						}else{//提醒方式为空或者-1（无）
							isDlFeeRightFlag = true;
							if(zlTypeInpVal != 'fmxx'){
								globalDlFee = '';
							}else{
								globalDlFee_fm = '';
								globalDlFee_xx = '';
							}
						}		
						if(isDlFeeRightFlag){
							if(ajFieldIdVal == ''){
								layer.msg('请选择案件所属技术领域', {icon:5,anim:6,time:1500});
							}else if(ajSqAddressVal == ''){
								layer.msg('请选择案件申请地区', {icon:5,anim:6,time:1500});
							}else if(ajSqrIdVal == ''){
								layer.msg('请添加申请人', {icon:5,anim:6,time:1500});
							}else if(ajLxrIdVal == '' && isNewAjTypeFlag == false){//旧案下事务联系人必填
								layer.msg('请添加事务联系人', {icon:5,anim:6,time:1500});
							}else if(ajFmrIdVal == '' && isNewAjTypeFlag == false){//旧案下发明人必填
								layer.msg('请添加发明人', {icon:5,anim:6,time:1500});
							}else if(ajJsLxrIdVal == ''){
								layer.msg('请添加技术联系人', {icon:5,anim:6,time:1500});
							}else if(payerInpVal == ''){
								layer.msg('请添加付款方', {icon:5,anim:6,time:1500});
							}else if(cpyDateInpVal == ''){
								layer.msg('定稿提交截止时间不能为空', {icon:5,anim:6,time:1500});
							}else{
								if($('.innerYxqBox').length > 0){
									var strResult = _this.getYxqId();
									if(strResult != undefined){
										yxqDetailInpVal = strResult;
									}else{
										//表明现在还有已经增加好但是没有填写完整的表单
										return;
									}
								}else{
									yxqDetailInpVal = '';
								}
								if(zlTypeInpVal != 'fmxx'){
									$('#tmpAjEwyqId').val() == '' ? ajEwyqIdVal = 0 : ajEwyqIdVal = $('#tmpAjEwyqId').val();
								}
								if($('#fmrBox p').length > 0 && $('#firstFmrId').length == 0){
									layer.msg('请给当前已选择发明人指定一个第一发明人',{icon:5,anim:6,time:2000});
									return;
								}
								if(anjianTypeVal == 'old'){
									if($('.deleteBtn').length == 0){
										layer.msg('请添加附件', {icon:5,anim:6,time:1500});
										return;
									}
								}else if(anjianTypeVal == 'new'){//新案下附件可传可不传
									if($('.deleteBtn').length == 0){//未传附件
										if(zlTypeInpVal != 'fmxx'){
											var field = {ajTitle:escape(ajTitleVal),
													ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
													ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
													yxqDetail:yxqDetailInpVal,ajEwyqId:ajEwyqIdVal,ajRemark:escape(ajRemarkVal),
													ajUpload:'',ajUploadHt:'',cpyDate:cpyDateInpVal,pubZlId:pubZlIdInpVal,dlFee:globalDlFee};
										}else{//发明+新型
											var field = {ajTitle_fm:fmZlTitInpVal,ajTitle_xx:xxZlTitInpVal,
													ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
													ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
													yxqDetail:yxqDetailInpVal,ajEwyqId:ajEyYqIdStr,ajRemark:escape(ajRemarkVal),           
													fmPath:'',xxPath:'',ajUploadHt:'',cpyDate:cpyDateInpVal,pubZlId:pubZlIdInpVal,dlFee_fm:globalDlFee_fm,dlFee_xx:globalDlFee_xx};
										}
									}
								}
								var isHasUpLoadFlag = false,isFmxxFlag=true;
								if($('.deleteBtn').length > 0){
									if($('input[name="hasUpSuccInp"]').length == 0){//表示用户选择了文件，但是没有点击上传
										isHasUpLoadFlag = false;
									}else if($('input[name="hasUpSuccInp"]').length > 0 && $('input[name="notUpInp"]').length == 0){
										//表示用户选择了文件并且全部上传了
										isHasUpLoadFlag = true;
									}else if($('input[name="hasUpSuccInp"]').length > 0 && $('input[name="notUpInp"]').length > 0 && errorTypeFlag == false){
										//表示用户选择了文件并且上传了，然后用户又选择了文件但是并没有上传(格式都是正确的时候)
										isHasUpLoadFlag = false;
									}else if($('input[name="hasUpSuccInp"]').length > 0 && $('input[name="notUpInp"]').length > 0 && errorTypeFlag == true){
										//表示用户选择了文件并且上传了，然后用户又选择了文件但是并没有上传(格式有部分不正确，用户如果没有点击删除不正确按钮，点击保存直接保存)
										isHasUpLoadFlag = true;
									}
									if(isHasUpLoadFlag){//发明 外观 实用新型 采用ajUpload
										/*if(zlTypeInpVal != 'fmxx'){
											upSuccSrcArray.length = 0;
											$('input[name="hasUpSuccInp"]').each(function(i){
												upSuccSrcArray.push($('input[name="hasUpSuccInp"]').eq(i).val());
											});
											$('#fujianInp').val(upSuccSrcArray.join(','));
										}*/
									}else{
										layer.msg('您有未上传的附件，请先上传附件',{icon:5,anim:6,time:1000});
										return;
									}
									
									if(zlTypeInpVal == 'fmxx' && $('.zlTypeInpTarg').length > 0){
										$('.zlTypeInpTarg').each(function(i){
											if($('.zlTypeInpTarg').eq(i).val() == ''){
												isFmxxFlag = false;//所有当前专利类型是发明+新型的上传成功的附件部分未指定专利类型
											}
										});
									}
									$('.ajFjTypeInp').each(function(i){
										if($('.ajFjTypeInp').eq(i).val() == ''){
											isFjTypeFlag = false;//所有当前专利类型是发明+新型的上传成功的附件部分未指定附件类型
										}
									});
									if(isFjTypeFlag && isHasUpLoadFlag){
										if($('.ajHtPathInp').length > 0){//存在合同文件
											var tmpAjHtPath = [];
											tmpAjHtPath.length = 0;
											$('.ajHtPathInp').each(function(i){
												tmpAjHtPath.push($('.ajHtPathInp').eq(i).val());
											});
											ajHtPathVal = tmpAjHtPath.join(',');
										}
										if(zlTypeInpVal != 'fmxx'){//整合技术底稿
											if($('.ajJsPathInp').length > 0){//存在技术底稿
												var tmpAjJsPath = [];
												tmpAjJsPath.length = 0;
												$('.ajJsPathInp').each(function(i){
													tmpAjJsPath.push($('.ajJsPathInp').eq(i).val());
												});
												ajJsPathVal = tmpAjJsPath.join(',');
											}
										}
										//判断附件类型
										if(anjianTypeVal == 'new'){
											if(zlTypeInpVal != 'fmxx'){
												//新案下单个类型 ajUpload ajUploadHt
												var field = {ajTitle:escape(ajTitleVal),
													ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
													ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
													yxqDetail:yxqDetailInpVal,ajEwyqId:ajEwyqIdVal,ajRemark:escape(ajRemarkVal),
													ajUpload:ajJsPathVal,ajUploadHt:ajHtPathVal,cpyDate:cpyDateInpVal,pubZlId:pubZlIdInpVal,dlFee:globalDlFee};
											}else{
												if(isFmxxFlag && isHasUpLoadFlag){//所有是发明+新型选择的附件都已经上传成功
													tmpFmPath.length = 0;
													tmpXxPath.length = 0;
													$('.fmPathInp').each(function(i){
														tmpFmPath.push($('.fmPathInp').eq(i).val());
													});
													fmPathVal = tmpFmPath.join(',');
													$('.xxPathInp').each(function(i){
														tmpXxPath.push($('.xxPathInp').eq(i).val());
													});
													xxPathVal = tmpXxPath.join(',');
													var field = {ajTitle_fm:fmZlTitInpVal,ajTitle_xx:xxZlTitInpVal,
															ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
															ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
															yxqDetail:yxqDetailInpVal,ajEwyqId:ajEyYqIdStr,ajRemark:escape(ajRemarkVal),
															fmPath:fmPathVal,xxPath:xxPathVal,ajUploadHt:ajHtPathVal,cpyDate:cpyDateInpVal,pubZlId:pubZlIdInpVal,dlFee_fm:globalDlFee_fm,dlFee_xx:globalDlFee_xx};
												}else{
													layer.msg('请给您上传的附件指派一个专利类型',{icon:5,anim:6,time:1500});
													return;
												}
											}
										}else if(anjianTypeVal == 'old'){//旧案
											if(zlTypeInpVal != 'fmxx'){
												if($('.ajDgPathInp').length > 0){
													var tmpAjDgPath=[];
													tmpAjDgPath.length = 0;
													$('.ajDgPathInp').each(function(i){
														tmpAjDgPath.push($('.ajDgPathInp').eq(i).val());
													});
													ajDgPathVal = tmpAjDgPath.join(',');
													//旧案下单个类型 ajUpload ajUploadHt ajUploadDg
													var field = {ajTitle:escape(ajTitleVal),sqDate:ajSqDateStr,
															ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,feeRate:rateInpVal,payUserInfo:payerInpVal,zlNoGf:ajSqZlNumVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
															ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
															yxqDetail:yxqDetailInpVal,ajEwyqId:ajEwyqIdVal,ajRemark:escape(ajRemarkVal),
															ajUpload:ajJsPathVal,ajUploadHt:ajHtPathVal,ajUploadDg:ajDgPathVal,cpyDate:cpyDateInpVal,pubZlId:pubZlIdInpVal,dlFee:globalDlFee};
												}else{//无定稿文件
													layer.msg('请至少指定一个上传文件为定稿文件',{icon:5,anim:6,time:2000});
													return;
												}
											}else{
												//旧案下发明+新型 xxPath fmPath ajUploadHt ajUploadDg_xx ajUploadDg_fm
												if($('.ajDgPathInp').length > 0 && $('.ajDgPathInp').length >= 2){
													var tmpAjDgPath_fm=[],tmpAjDgPath_xx=[];
													tmpAjDgPath_fm.length = 0;tmpAjDgPath_xx.length = 0;
													if($('.fmPathInp_dg').length == 0){//选择定稿文件中未指定发明定稿文件
														layer.msg('请至少指定一个上传文件为发明定稿文件',{icon:5,anim:6,time:2500});
														return;
													}
													if($('.xxPathInp_dg').length == 0){
														layer.msg('请至少指定一个上传文件为新型定稿文件',{icon:5,anim:6,time:2500});
														return;
													}
													$('.fmPathInp_dg').each(function(i){
														tmpAjDgPath_fm.push($('.fmPathInp_dg').eq(i).val());
													});
													$('.xxPathInp_dg').each(function(i){
														tmpAjDgPath_xx.push($('.xxPathInp_dg').eq(i).val());
													});
													ajDgPathVal_fm = tmpAjDgPath_fm.join(',');
													ajDgPathVal_xx = tmpAjDgPath_xx.join(',');
													if(isFmxxFlag && isHasUpLoadFlag){//所有是发明+新型选择的附件都已经上传成功
														tmpFmPath.length = 0;
														tmpXxPath.length = 0;
														$('.fmPathInp').each(function(i){
															tmpFmPath.push($('.fmPathInp').eq(i).val());
														});
														$('.xxPathInp').each(function(i){
															tmpXxPath.push($('.xxPathInp').eq(i).val());
														});
														fmPathVal = tmpFmPath.join(',');
														xxPathVal = tmpXxPath.join(',');
														var field = {ajTitle_fm:fmZlTitInpVal,ajTitle_xx:xxZlTitInpVal,sqDate:ajSqDateStr,
															ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,feeRate:rateInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,zlNoGf:fmxxZlNumStr,ajSqAddress:escape(ajSqAddressVal),
															ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
															yxqDetail:yxqDetailInpVal,ajEwyqId:ajEyYqIdStr,ajRemark:escape(ajRemarkVal),
															fmPath:fmPathVal,xxPath:xxPathVal,ajUploadHt:ajHtPathVal,ajUploadDg_fm:ajDgPathVal_fm,ajUploadDg_xx:ajDgPathVal_xx,cpyDate:cpyDateInpVal,pubZlId:pubZlIdInpVal,dlFee_fm:globalDlFee_fm,dlFee_xx:globalDlFee_xx};
													}else{
														layer.msg('请给您上传的附件指派一个专利类型',{icon:5,anim:6,time:1500});
														return;
													}
												}else{//无定稿文件
													layer.msg('请至少指定两个上传文件为定稿文件',{icon:5,anim:6,time:2000});
													return;
												}
											}
										}
									}else{
										layer.msg('请选择附件类型',{icon:5,anim:6,time:1500});
										return;
									}
								}
								if(addEditZlOpts == 'editZlOpts'){
									url = '/zlm.do?action=updateZlBasicInfo&zlId=' + zlId;
								}else{
									//表示增加专利
									var url = '/zlm.do?action=addZlData';	
				   				}
				   				layer.load('1');
								$.ajax({
				   					type:'post',
				   			        async:false,
				   			        dataType:'json',
				   			        data:field,
				   			        url:url,
				   			        success:function (json){
				   			        	layer.closeAll('loading');
				   			        	if(json['result'] == 'success'){
				   			        		function callBackSucc(){
				   			        			var index= parent.layer.getFrameIndex(window.name);
						        				parent.addZlFlag = true;
						        				parent.layer.close(index);
				   			        		}
				   			        		if(addEditZlOpts == 'editZlOpts'){
				   			        			layer.msg('编辑专利成功',{icon:1,time:1000},callBackSucc);
				   			        		}else{
				   			        			layer.msg('添加专利成功',{icon:1,time:1000},callBackSucc);
				   			        		}
				   			        	}else if(json['result'] == 'noAbility'){
				   			        		layer.msg('抱歉，您暂无权限添加编辑专利', {icon:5,anim:6,time:1000});
				   			        	}else if(json['result'] == 'error'){
				   			        		layer.msg('系统错误，请稍后重试', {icon:5,anim:6,time:1000});
				   			        	}else if(json['result'] == 'typeDiff'){
				   			        		layer.msg('选择的专利类型和专利任务类型不一致', {icon:5,anim:6,time:2200});
				   			        	}else if(json['result'] == 'notUpdate'){
				   			        		layer.msg('定稿以后或专利状态终止条件下不能修改', {icon:5,anim:6,time:2200});
				   			        	}else if(json['result'] == 'addError'){
				   			        		layer.msg('当前选择的专利任务已被其他专利占用', {icon:5,anim:6,time:2200});
				   			        	}else if(json['result'] == 'exist'){
				   			        		layer.msg('当前专利号已存，请重新输入', {icon:5,anim:6,time:2200});
				   			        	}
				   			        }
				   				});
							}
						}
					}
				}
			});
			//添加代理费用
			$('#addDlFeeBtn').on('click',function(){
				var remindInpVal = $('#remindInp').val(),zlTypeVal = $('#zlTypeInp').val();
				currOpts = 'fm';
				if(remindInpVal == ''){
					layer.msg('请选择提醒方式', {icon:5,anim:6,time:1000});
					return;
				}else if(remindInpVal == '-1'){
					layer.msg('无提醒方式下不能添加代理费', {icon:5,anim:6,time:2000});
					return;
				}
				//如果是时间提醒方式 最多可以添加5个，如果是事务提醒方式，发明这块可以添加4个 其它是3个
				singleDlFeeNum ++;
				if(remindInpVal == 0){//时间提醒
					if($('.singleDlFeeList').length > 4){
						layer.msg('抱歉，时间提醒方式下最多可以添加5个代理费', {icon:5,anim:6,time:2000});
						return;
					}
				}else if(remindInpVal == 1){//事务提醒
					if(zlTypeVal == 'fm' || zlTypeVal == 'fmxx' && currOpts == 'fm'){//发明事务提醒方式增加实质审查通知书 最多可以创建四个
						if($('.singleDlFeeList').length > 3){
							layer.msg('抱歉，事务提醒方式下发明专利最多可以添加4个代理费', {icon:5,anim:6,time:2000});
							return;
						}
					}else{
						if($('.singleDlFeeList').length > 2){
							layer.msg('抱歉，事务提醒方式下专利最多可以添加3个代理费', {icon:5,anim:6,time:2000});
							return;
						}
					}
				}
				
				_this.createDlFeeInp(remindInpVal,zlTypeVal,'dlFeeListWrap',currOpts);
			});
			//添加新型代理费
			$('#addDlFeeBtn_xx').on('click',function(){
				var remindInpVal = $('#remindInp').val(),zlTypeVal = $('#zlTypeInp').val();
				currOpts = 'xx';
				if(remindInpVal == ''){
					layer.msg('请选择提醒方式', {icon:5,anim:6,time:1200});
					return;
				}else if(remindInpVal == '-1'){
					layer.msg('无提醒方式下不能添加代理费', {icon:5,anim:6,time:2000});
					return;
				}
				multiDlFeeNum++;
				if(remindInpVal == 0){//时间提醒
					if($('.multiDlFeeList').length > 4){
						layer.msg('抱歉，时间提醒方式下最多可以添加5个代理费', {icon:5,anim:6,time:2000});
						return;
					} 
				}else if(remindInpVal == 1){//事务提醒
					if($('.multiDlFeeList').length > 2){
						layer.msg('抱歉，事务提醒方式下新型专利最多可以添加3个代理费', {icon:5,anim:6,time:2000});
						return;
					}
				}
				_this.createDlFeeInp(remindInpVal,zlTypeVal,'dlFeeListWrap_xx',currOpts);
			});
			//选择已领取未添加的专利
			$('.selZlTaskSp').on('click',function(){
				parent.zlTypeInp = $('#zlTypeInp').val();
				parent.addZlFlag = false;
				parent.layer.open({
					title:'添加已领取未添加专利任务',
					type: 2,
				  	area: ['850px', '510px'],
				  	fixed: true, //不固定
				  	maxmin: false,
				  	shadeClose :false,
				  	content: '/Module/zlBasicInfoManager/jsp/addHasLqZl.html',
				  	end:function(){
				  		if(parent.addZlFlag){
							$('#pubZlIdInp').val(parent.$('#lqZlIdInp').val());
							$('#pubZlIdInpTxt').val(parent.$('#lqZlIdInpText').val());
							$('#zlTypeInp').val(parent.$('#lqZlTypeInp').val());
							$('.zlTypeRad').each(function(i){
								if($('.zlTypeRad').eq(i).val() == parent.$('#lqZlTypeInp').val()){
									$('.zlTypeRad').eq(i).attr('checked',true);
									var dlFeeNum =  _this.getCpyDlFee($('.zlTypeRad').eq(i).val());//获取代理机构指定专利类型费用;
									!_this.data.isClickZlTypeFlag ? _this.agentFeeTxtByZlType($('.zlTypeRad').eq(i).val()) : '';
									$('#singDlFeeNum').text(dlFeeNum);
								}
							});	
							form.render();
							_this.agentFeeInpShow();		
							_this.getAjNum(parent.$('#lqZlTypeInp').val());		
							_this.getEwYqList(parent.$('#lqZlTypeInp').val());		
							tmpEwyqIdArray.length = 0;		
							parent.addZlFlag = false;
				  		}
				  	}
				});	
			});
			//重置已选择的专利领取任务
			$('.resetSp').on('click',function(){
				if($('#pubZlIdInp').val() != ''){
					layer.confirm('确认要清空当前已选择的领取任务？', {
					  title:'提示',
					  skin: 'layui-layer-molv',
					  btn: ['确定','取消'] //按钮
					},function(index){
						$('#pubZlIdInp').val('');
						parent.$('#lqZlIdInp').val('');
						$('#pubZlIdInpTxt').val('');
						parent.$('#lqZlIdInpText').val('');
						layer.close(index);
					});
				}
			});
			//添加技术领域
			$('#addFieldBtn').on('click',function(){
				layer.open({
					title:'添加专业领域',
					type: 2,
				  	area: ['500px', '320px'],
				  	fixed: true, //不固定
				  	maxmin: false,
				  	shadeClose :false,
				  	content: '/Module/zlBasicInfoManager/jsp/addZyField.html'
				});	
			});
			//添加申请人
			$('#addSqrBtn').on('click',function(){
				var fullScreenIndex = layer.open({
					title:'添加申请人',
					type: 2,
				  	area: ['720px', '350px'],
				  	fixed: true, //不固定
				  	maxmin: false,
				  	shadeClose :false,
				  	content: '/Module/zlBasicInfoManager/jsp/addSqr.html'
				});	
				layer.full(fullScreenIndex);
			});
			//添加联系人
			$('#addlxrBtn').on('click',function(){
				_this.commonLayerLxrFmr('添加联系人');
				addEditLxrOpts = tmpLxrFmrOpts = $(this).attr('opts');
				if($('#lxrBox p').length > 0 /*&& isHasLxrFlag*/){
					//表示已经创建了联系人，假如删除了某个申请人，此时应该从新匹配lxrIdArray lxrIdNum
					lxrIdArray.length = 0;
					lxrIdNum.length = 0;
					$('#lxrBox p').each(function(i){
						lxrIdArray.push($('#lxrBox p').eq(i).attr('id'));
						lxrIdNum.push($('#lxrBox p').eq(i).attr('id').split('_')[1]);
					});
				}else{
					lxrIdArray.length = 0;
					lxrIdNum.length = 0;
				}
			});
			//添加发明人
			$('#addFmrBtn').on('click',function(){
				_this.commonLayerLxrFmr('添加发明人');
				addEditFmrOpts = tmpLxrFmrOpts = $(this).attr('opts');
				if($('#fmrBox p').length > 0 /*&& isHasFmrFlag*/){
					//表示已经创建了发明人，假如删除了某个申请人，此时应该从新匹配fmrIdArray fmrIdNum
					fmrIdArray.length = 0;
					fmrIdNum.length = 0;
					$('#fmrBox p').each(function(i){
						fmrIdArray.push($('#fmrBox p').eq(i).attr('id'));
						fmrIdNum.push($('#fmrBox p').eq(i).attr('id').split('_')[1]);
					});
				}else{
					fmrIdArray.length = 0;
					fmrIdNum.length = 0;
				}
			});
			//添加技术联系人
			$('#addJsLxrBtn').on('click',function(){
				_this.commonLayerLxrFmr('添加技术联系人');
				addEditFmrOpts = tmpLxrFmrOpts = $(this).attr('opts');
				if($('#techLxrBox p').length > 0 /*&& isHasJsLxrFlag*/){
					//表示已经创建了技术联系人，假如删除了某个申请人，此时应该从新匹配jsLxrIdArray jsLxrIdNum
					jsLxrIdArray.length = 0;
					jsLxrIdNum.length = 0;
					$('#techLxrBox p').each(function(i){
						jsLxrIdArray.push($('#techLxrBox p').eq(i).attr('id'));
						jsLxrIdNum.push($('#techLxrBox p').eq(i).attr('id').split('_')[1]);
					});
				}else{
					jsLxrIdArray.length = 0;
					jsLxrIdNum.length = 0;
				}
			});
			//添加优先权
			$('#addYxqBtn').on('click',function(){
				var strHtml = '';
				num ++;
				if($('.innerYxqBox').length > 4){
					layer.msg('抱歉，最多可以添加5个优先权', {icon:5,anim:6,time:1500});
					return;
				}
				strHtml += '<div class="innerYxqBox">';
				strHtml += '<input placeholder="请输入案件申请号/专利号" type="text" autocomplete="off" class="layui-input zlAjNumInp"/>';
				strHtml += '<div class="innerSelAddBox"><select class="zlYxqAreaInp" lay-filter="selAreaFilter">';
				strHtml += '<option value="">请选择申请地区</option>';
				strHtml += '<option value="中国">中国[CN]</option>';
				strHtml += '<option value="中国香港">中国香港[HK]</option>';
				strHtml += '<option value="中国台湾">中国台湾[TW]</option>';
				strHtml += '<option value="澳门">澳门[MO]</option>';
				strHtml += '<option value="日本">日本[JP]</option></select></div>';
				strHtml += '<input placeholder="请选择日期" type="text" name="" autocomplete="off" id="dateInp_'+ num +'" class="layui-input yxqInpDate"/>';
				strHtml += '<i class="layui-icon layui-icon-delete delYxqIcon"  title="删除"></i>';
				strHtml += '</div>';
				$('#innerYxqBox').append(strHtml);
				form.render();
				laydate.render({
					elem:'#dateInp_' + num
				});
				_this.delYxq();				
			});
		},
		//旧案下案件编号只能输入数字和字母的检测
		checkOldAjNumPattern : function(obj,opts){
			var zlTypeVal = $('#zlTypeInp').val(),_this = this;
			$('#'+obj).on('blur',function(){
				var reg = /^[0-9a-zA-Z]*$/g;
				if($('#zlTypeInp').val() == ''){
					$('#'+obj).val('');
					layer.msg('请选选择专利类型', {icon:5,anim:6,time:2000});
					return;
				}
				if($('#'+obj).val() == ''){
					if(opts == ''){
						layer.msg('旧案专利号不能为空', {icon:5,anim:6,time:2000});
					}else if(opts == 'fmZlNum'){
						layer.msg('旧案发明专利号不能为空', {icon:5,anim:6,time:2000});
					}else if(opts == 'xxZlNum'){
						layer.msg('旧案新型专利号不能为空', {icon:5,anim:6,time:2000});
					}
					return;
				}else{
					if($('#'+obj).val().length < 5){
						layer.msg('旧案专利号最少输入5个字符', {icon:5,anim:6,time:2000});
						return;
					}else if($('#'+obj).val().length > 20){
						layer.msg('旧案专利号最多可输入20个字符', {icon:5,anim:6,time:2000});
						return;
					}else if(!reg.test($('#'+obj).val())){
						layer.msg('旧案专利号只能是数字和字母组合，不能有空格、汉字和特殊字符', {icon:5,anim:6,time:2000});
						$('#'+obj).val('');
						return;
					}
				}
				var tmpStr = $('#'+obj).val().substring(4,5).toUpperCase();
				var tmpStr_four = $('#'+obj).val().substring(0,4).toUpperCase();
				var tmpStr_end = $('#'+obj).val().substring(5).toUpperCase();
				if($('#zlTypeInp').val() == 'fm' || $('#zlTypeInp').val() == 'fmxx' && opts == 'fmZlNum'){//第五位1
					if(tmpStr == 1){
					}else{
						$('#'+obj).focus();
						$('#'+obj).prev().hide().html('');
						layer.msg('发明专利号格式错误', {icon:5,anim:6,time:2000});
						return;
					}
				}else if($('#zlTypeInp').val() == 'syxx' || $('#zlTypeInp').val() == 'fmxx' && opts == 'xxZlNum'){//第五位 2、8
					if(tmpStr == 2 || tmpStr == 8){
					}else{
						$('#'+obj).focus();
						$('#'+obj).prev().hide().html('');
						layer.msg('实用新型专利号格式错误', {icon:5,anim:6,time:2000});
						return;
					}
				}else if($('#zlTypeInp').val() == 'wg'){//3、9
					if(tmpStr == 3 || tmpStr == 9){
					}else{
						$('#'+obj).prev().hide().html('');
						$('#'+obj).focus();
						layer.msg('外观专利号格式错误', {icon:5,anim:6,time:2000});
						return;
					}
				}
				$('#'+obj).val($('#'+obj).val().toUpperCase());
				$('#'+obj).prev().show().html(tmpStr_four + '<strong class="zlPatColor">'+ tmpStr +'</strong>' + tmpStr_end);
			});
			$('.zlPatternNum').on('click',function(){
				$(this).hide().next().show().focus();
			});
		},
		//旧案下 发明+新型下增加发明专利号 新型专利号 可编辑
		createFmXxZlNumDom : function(){
			var strHtml = '';
			strHtml += '<div id="fmxxZlNumWrap" class="clearfix"><div class="zlNumBox"><label><span>*</span>发明专利号</label>';
			strHtml += '<div class="innerFmxxNum"><p class="zlPatternNum multiWid"></p><input id="fmZlNumInp" type="text" placeholder="请输入发明专利号" maxlength="30" autocomplete="off" class="layui-input"/></div></div>';
			strHtml += '<div class="zlNumBox"><label><span>*</span>新型专利号</label>';
			strHtml += '<div class="innerFmxxNum"><p class="zlPatternNum multiWid"></p><input id="xxZlNumInp" type="text" placeholder="请输入新型专利号" maxlength="30" autocomplete="off" class="layui-input"/></div></div>';
			return strHtml;
		},
		//发明+新型下增加发明专利标题 新型专利标题
		createFmxxZlTitDom : function(){
			var strHtml = '';
			strHtml += '<div id="fmxxZlTitBox" class="clearfix">';
			strHtml += '<div class="fmxxZlTitBox"><label><span>*</span>发明案件标题</label><input id="fmZlTitInp" type="text" placeholder="请输入发明案件标题(40字以内)" maxlength="40" class="layui-input"/><p class="zlTitTipsTxt_1"></p></div>';
			strHtml += '<div class="fmxxZlTitBox"><label><span>*</span>新型案件标题</label><input id="xxZlTitInp" type="text" placeholder="请输入新型案件标题(40字以内)" maxlength="40" class="layui-input"/><p class="zlTitTipsTxt_1"></p></div></div>';
			return strHtml;
		},
		//发明+新型下增加发明案件申请日 新型案件申请日
		createFmxxAjSqDate : function(){
			var strHtml = '';
			strHtml += '<div id="fmxxAjSqBox" class="clearfix">';
			strHtml += '<div class="fmxxAjSqBox"><label><span>*</span>发明案件申请日</label><input id="fmSqDateInp" type="text" placeholder="请选择发明案件申请日" readonly class="layui-input"/></div>';
			strHtml += '<div class="fmxxAjSqBox"><label><span>*</span>新型案件申请日</label><input id="xxSqDateInp" type="text" placeholder="请选择新型案件申请日" readonly class="layui-input"/></div></div>';
			return strHtml;
		},
		//创建添加代理费input
		createDlFeeInp : function(remindType,zlType,appendWrap,currOpts){
			var strHtml = '',zlTypeDlInpTxt = '',_this = this;
			if(zlType == 'fm'){
				zlTypeDlInpTxt = '发明';
			}else if(zlType == 'wg'){
				zlTypeDlInpTxt = '外观';
			}else if(zlType == 'syxx'){
				zlTypeDlInpTxt = '实用新型';
			}else{
				if(currOpts == 'fm'){
					zlTypeDlInpTxt = '发明';
				}else if(currOpts == 'xx'){
					zlTypeDlInpTxt = '新型';
				}
			}
			if(zlType != 'fmxx' || currOpts == 'fm'){
				strHtml += '<div class="dlFeeList singleDlFeeList layui-form layui-clear">';
			}else{
				strHtml += '<div class="dlFeeList multiDlFeeList layui-clear">';
			}
			if(remindType == 0){//时间段提醒方式
				if(zlType != 'fmxx' || currOpts == 'fm'){
					strHtml += '<input type="text" id="remdDateInp_'+ singleDlFeeNum +'" readonly class="layui-input remindDateInp" placeholder="请选择提醒日期" autocomplete="off"/>';
				}else{
					//currOpts->xx
					strHtml += '<input type="text" id="remDateInpMul_'+ multiDlFeeNum +'" readonly  class="layui-input remDateInp_multi" placeholder="请选择提醒日期" autocomplete="off"/>';
				}
			}else{//事务提醒方式
				strHtml += '<div class="shiwuSel layui-form">';
				if(zlType != 'fmxx' || currOpts == 'fm'){
					strHtml += '<input type="hidden" class="shiwuInp singleInp_shiwu"/>';
					strHtml += '<select class="shiwuSelect singleSelect" lay-filter="shiwuSelectFilter">';
				}else{
					strHtml += '<input type="hidden" class="shiwuInp xxInp_shiwu"/>';
					strHtml += '<select class="shiwuSelect xxSelect" lay-filter="shiwuSelectFilter">';
				}
				strHtml += '<option value="">请选择事务</option>';
				strHtml += '<option value="sl">受理通知书</option>';
				strHtml += '<option value="gb">公布通知书</option>';
				if(zlType == 'fm' || zlType == 'fmxx' && currOpts == 'fm'){
					strHtml += '<option value="sc">实质审查通知书</option>';
				}
				strHtml += '<option value="sq">授权通知书</option></select></div>';
			}
			strHtml += '<div class="dlFeeNumBox"><em class="moneyDec">¥</em>';
			if(zlType != 'fmxx' || currOpts == 'fm'){
				strHtml += '<input type="text" class="layui-input dlFeeInpNum dlFeeNum_dan" placeholder="请输入'+ zlTypeDlInpTxt +'专利代理费"/></div>';
			}else{
				strHtml += '<input type="text" class="layui-input dlFeeInpNum dlFeeNum_xx" placeholder="请输入'+ zlTypeDlInpTxt +'专利代理费"/></div>';
			}
						
			strHtml += '<i class="layui-icon layui-icon-delete delDlFee" title="删除"></i>';
			strHtml += '</div>';
			$('#'+appendWrap).append(strHtml);
			if(remindType == 0){
				if(zlType != 'fmxx' || currOpts == 'fm'){
					laydate.render({
						elem:'#remdDateInp_' + singleDlFeeNum,
						min:_this.getMinDate()
					});
				}else{
					laydate.render({
						elem:'#remDateInpMul_' + multiDlFeeNum,
						min:_this.getMinDate()
					});
				}
			}
			form.render();
			$('.dlFeeInpNum').each(function(i){
				$('.dlFeeInpNum').eq(i).attr('id','dlFeeInpNum_' + (i+1));
				$('.delDlFee').eq(i).attr('id','delDlFee_'+(i+1));
				_this.agentDlFee_usual('dlFeeInpNum_'+(i+1));
				_this.delCurrDlFee('delDlFee_'+(i+1));
			});
		},
		checkIsExistEle : function(existArray){
			var hash = {};
		    for(var i in existArray) {
		        if(hash[existArray[i]]) {
		            return true;
		        }
		        hash[existArray[i]] = true;
		    }
		    return false;
		},
		//组装代理费
		getNewDlFeeRes : function(){
			var _this = this,totalFlag=false,totalRes='',len = $('#dlFeeListWrap').find('.dlFeeList ').length,zlTypeVal = $('#zlTypeInp').val(),
			remindType = $('#remindInp').val(),
			tmpRemDateFlag=true,remDateStr='',totalRemDate='',
			tmpRemDateFlag_xx=true,remDateStr_xx='',totalRemDate_xx='',
			tmpDlFeeNumFlag=true,dlFeeStr='',totalDlFeeStr='',
			tmpSingleFlag_shiwu=true,singleShiwuStr='',totShiWuStr_dan='',
			tmpXXFlag_shiwu=true,xxShiwuStr='',totShiwu_xx='',
			tmpDlFeeNumFlag_xx=true,dlFeeStr_xx='',totalDlFeeStr_xx='';
			if(len == 0){
				layer.msg('请添加代理费', {icon:5,anim:6,time:1500});
				return;
			}
			if(remindType == 0){//时间提醒方式
				if(zlTypeVal == 'fmxx'){
					if($('.remDateInp_multi').length == 0){
						tmpRemDateFlag_xx = false;
						layer.msg('请添加新型专利代理费', {icon:5,anim:6,time:1500});
						return;
					}
					$('.remDateInp_multi').each(function(i){
						if($('.remDateInp_multi').eq(i).val() == ''){
							tmpRemDateFlag_xx = false;
						}
						if($('.remDateInp_multi').length == 1){
							remDateStr_xx = $('.remDateInp_multi').eq(i).val();
							totalRemDate_xx = remDateStr_xx;
						}else{
							remDateStr_xx += $('.remDateInp_multi').eq(i).val() + ',';
							totalRemDate_xx =remDateStr_xx.substring(0,remDateStr_xx.length-1);
						}
					});
					if(!tmpRemDateFlag_xx){
						layer.msg('请选择提醒时间', {icon:5,anim:6,time:1500});
						return;
					}
				}
				$('.remindDateInp').each(function(i){
					if($('.remindDateInp').eq(i).val() == ''){
						tmpRemDateFlag = false;
					}
					if($('.remindDateInp').length == 1){
						remDateStr = $('.remindDateInp').eq(i).val();
						totalRemDate = remDateStr;
					}else{
						remDateStr += $('.remindDateInp').eq(i).val() + ',';
						totalRemDate = remDateStr.substring(0,remDateStr.length-1);
					}
				});
				if(!tmpRemDateFlag){
					layer.msg('请选择提醒时间', {icon:5,anim:6,time:1500});
					return;
				}
			}else{
				if(zlTypeVal == 'fmxx'){
					if($('.dlFeeNum_xx').length == 0){
						tmpDlFeeNumFlag_xx = false;
						layer.msg('请添加新型专利代理费', {icon:5,anim:6,time:1500});
						return;
					}
					$('.xxInp_shiwu').each(function(i){
						if($('.xxInp_shiwu').eq(i).val() == ''){
							tmpXXFlag_shiwu = false;
						}
						if($('.xxInp_shiwu').length == 1){
							xxShiwuStr = $('.xxInp_shiwu').eq(i).val();
							totShiwu_xx = xxShiwuStr;
						}else{
							xxShiwuStr += $('.xxInp_shiwu').eq(i).val() + ',';
							totShiwu_xx = xxShiwuStr.substring(0,xxShiwuStr.length-1);
						}
					});
					if(!tmpXXFlag_shiwu){
						layer.msg('请选择事务', {icon:5,anim:6,time:1500});
						return;
					}
					var tmpShiwuXx = totShiwu_xx.split(',');
					var isExistFlag = _this.checkIsExistEle(tmpShiwuXx);
					if(isExistFlag){//表示选中的事务通知书中存在重复的
						tmpXXFlag_shiwu = false;
						layer.msg('每个事务提醒方式只能选择一次', {icon:5,anim:6,time:4000});
						return;
					}else{
						//表示选中的事务通知书没有重复的
						totShiwu_xx = tmpShiwuXx.join(',');
					}
				}
				$('.singleInp_shiwu').each(function(i){
					if($('.singleInp_shiwu').eq(i).val() == ''){
						tmpSingleFlag_shiwu = false;
					}
					if($('.singleInp_shiwu').length == 1){
						singleShiwuStr = $('.singleInp_shiwu').eq(i).val();
						totShiWuStr_dan = singleShiwuStr;
					}else{
						singleShiwuStr += $('.singleInp_shiwu').eq(i).val() + ',';
						totShiWuStr_dan = singleShiwuStr.substring(0,singleShiwuStr.length-1);
					}
				});
				if(!tmpSingleFlag_shiwu){
					layer.msg('请选择事务', {icon:5,anim:6,time:1500});
					return;
				}
				var tmpShiwuDan = totShiWuStr_dan.split(',');
				var isExistFlag = _this.checkIsExistEle(tmpShiwuDan);
				if(isExistFlag){//表示选中的事务通知书中存在重复的
					tmpSingleFlag_shiwu = false;
					layer.msg('每个事务提醒方式只能选择一次', {icon:5,anim:6,time:4000});
					return;
				}else{
					//表示选中的事务通知书没有重复的
					totShiWuStr_dan = tmpShiwuDan.join(',');
				}
			}
			$('.dlFeeNum_dan').each(function(i){
				if($('.dlFeeNum_dan').eq(i).val() == ''){
					tmpDlFeeNumFlag = false;
				}
				if($('.dlFeeNum_dan').length == 1){
					dlFeeStr = $('.dlFeeNum_dan').eq(i).val();
					totalDlFeeStr = dlFeeStr;
				}else{
					dlFeeStr += $('.dlFeeNum_dan').eq(i).val() + ',';
					totalDlFeeStr = dlFeeStr.substring(0,dlFeeStr.length-1);
				}
			});
			if(!tmpDlFeeNumFlag){
				layer.msg('请填写代理费', {icon:5,anim:6,time:1500});
				return;
			}
			if($('.dlFeeNum_xx').length > 0){
				$('.dlFeeNum_xx').each(function(i){
					if($('.dlFeeNum_xx').eq(i).val() == ''){
						tmpDlFeeNumFlag_xx = false;
					}
					if($('.dlFeeNum_xx').length == 1){
						dlFeeStr_xx = $('.dlFeeNum_xx').eq(i).val();
						totalDlFeeStr_xx = dlFeeStr_xx;
					}else{
						dlFeeStr_xx += $('.dlFeeNum_xx').eq(i).val() + ',';
						totalDlFeeStr_xx = dlFeeStr_xx.substring(0,dlFeeStr_xx.length-1);
					}
				});
				if(!tmpDlFeeNumFlag_xx){
					layer.msg('请填写新型代理费', {icon:5,anim:6,time:1500});
					return;
				}
			}
			if(remindType == 0){
				if($('.remDateInp_multi').length == 0){
					if(tmpRemDateFlag  && tmpDlFeeNumFlag){
						totalFlag = true;
					}
					if(totalFlag){				
						totalRes = totalRemDate + ':' + totalDlFeeStr;
						return totalRes;
					}
				}else{
					if(tmpRemDateFlag && tmpRemDateFlag_xx && tmpDlFeeNumFlag && tmpDlFeeNumFlag_xx){
						totalFlag = true;
					}
					if(totalFlag){
						totalRes = totalRemDate + ':' + totalDlFeeStr + '@' + totalRemDate_xx + ':' + totalDlFeeStr_xx;
						return totalRes;
					}
				}
			}else{
				if($('.dlFeeNum_xx').length == 0){
					if(tmpSingleFlag_shiwu && tmpDlFeeNumFlag){
						totalFlag = true;
					}
					if(totalFlag){				
						totalRes = totShiWuStr_dan + ':' + totalDlFeeStr;
						return totalRes;
					}
				}else{
					if(tmpSingleFlag_shiwu && tmpXXFlag_shiwu && tmpDlFeeNumFlag && tmpDlFeeNumFlag_xx){
						totalFlag = true;
					}
					if(totalFlag){				
						totalRes = totShiWuStr_dan + ':' + totalDlFeeStr + '@' + totShiwu_xx + ':' +  totalDlFeeStr_xx;
						return totalRes;
					}
				}
			}
		},
		getMinDate : function(){
			 var now = new Date();
			 return now.getFullYear()+"-" + (now.getMonth()+1) + "-" + now.getDate();
		},
		delCurrDlFee : function(obj){
			$('#'+obj).on('click',function(){
				$(this).parent().remove();
			});
		},
		clearResetDlFee : function(){
			$('#dlFeeListWrap').html('');
			$('#dlFeeListWrap_xx').html('');
			singleDlFeeNum = 0;
			multiDlFeeNum = 0;
		},
		//未选择专利类型先选择的已领取未添加任务这块显示对应文本框公共调用
		agentFeeInpShow : function(){
			$('.tipsInfo').remove();
			$('.agentBox').show();
		},
		//根据专利类型显示对应的代理费用文字
		agentFeeTxtByZlType : function(value){
			if(value == 'fm'){
				$('#addDlFeeBtn').html('添加发明专利代理费');
				$('.singleDlFee').html('发明专利代理费参考费用：¥<span id="singDlFeeNum"></span>元');
			}else if(value == 'syxx'){
				$('#addDlFeeBtn').html('添加实用新型专利代理费');
				$('.singleDlFee').html('实用新型专利代理费参考费用：¥<span id="singDlFeeNum"></span>元');
			}else if(value == 'wg'){
				$('#addDlFeeBtn').html('添加外观专利代理费');
				$('.singleDlFee').html('外观专利代理费参考费用：¥<span id="singDlFeeNum"></span>元');
			}
			this.data.isXxFeeFlag = false;
		},
		//获取代理机构指定专利类型的代理费用(增加/修改)
		getCpyDlFee : function(ajType){
			layer.load('1');
			var tmpDlFeeNum = '';
			$.ajax({
				type:'post',
		        async:false,
		        dataType:'json',
		        data:{ajType : ajType},
		        url:'/cpyManager.do?action=getCpyDlFee',
		        success:function (json){
		        	layer.closeAll('loading');
		        	if(json['result'] == 'success'){
		        		if(ajType == 'fmxx'){
		        			var dlFeeFm = '',dlFeeXx = '';
		        			if(json.dlFeeFm == ''){
		        				dlFeeFm = '';
		        			}else{
		        				dlFeeFm = json.dlFeeFm;
		        			}
		        			if(json.dlFeeXx == ''){
		        				dlFeeXx = '';
		        			}else{
		        				dlFeeXx = json.dlFeeXx;
		        			}
		        			tmpDlFeeNum = dlFeeFm + "-" + dlFeeXx;
			        	}else{
			        		if(json.dlFee == ''){
			        			tmpDlFeeNum = '';
			        		}else{
			        			tmpDlFeeNum = json.dlFee;
			        		}
			        	}
		        	}
		        }
			});
			return tmpDlFeeNum;
		},
		//获取优先权
		getYxqId : function(){
			var _this = this,totalRes='';
			var totalFlag=false,tmpZlAjNumFlag=true, tmpZlYxqAreaFlag = true,tmpZlYxqDateFlag=true,zlAjNum = '',totalZlAjNum='',zlYxqArea = '',totalZlYxqArea='',zlYxqDate = '',totalZlYxqDate='';
			$('.zlAjNumInp').each(function(i){
				if($('.zlAjNumInp').eq(i).val() == ''){
					tmpZlAjNumFlag = false;
				}
				if($('.zlAjNumInp').length == 1){
					zlAjNum = $('.zlAjNumInp').eq(i).val();
					totalZlAjNum = zlAjNum;
				}else{
					zlAjNum += $('.zlAjNumInp').eq(i).val() + ',';
					totalZlAjNum = zlAjNum.substring(0,zlAjNum.length-1);
				}
			});
			$('.zlYxqAreaInp').each(function(i){
				if($('.zlYxqAreaInp').eq(i).val() == ''){
					tmpZlYxqAreaFlag = false;
				}
				if($('.zlYxqAreaInp').length == 1){
					zlYxqArea = $('.zlYxqAreaInp').eq(i).val();
					totalZlYxqArea = zlYxqArea;
				}else{
					zlYxqArea += $('.zlYxqAreaInp').eq(i).val() + ',';
					totalZlYxqArea = zlYxqArea.substring(0,zlYxqArea.length-1);
				}
			});
			$('.yxqInpDate').each(function(i){
				if($('.yxqInpDate').eq(i).val() == ''){
					tmpZlYxqDateFlag = false;
				}
				if($('.yxqInpDate').length == 1){
					zlYxqDate = $('.yxqInpDate').eq(i).val();
					totalZlYxqDate = zlYxqDate;
				}else{
					zlYxqDate += $('.yxqInpDate').eq(i).val() + ',';
					totalZlYxqDate = zlYxqDate.substring(0,zlYxqDate.length-1);
				}
			});
			if(!tmpZlAjNumFlag){
				layer.msg('请输入案件申请号/专利号', {icon:5,anim:6,time:1000});
				return;
			}else if(!tmpZlYxqAreaFlag){
				layer.msg('请选择申请地区', {icon:5,anim:6,time:1000});
				return;
			}else if(!tmpZlYxqDateFlag){
				layer.msg('请选择申请时间', {icon:5,anim:6,time:1000});
				return;
			}
			if(tmpZlAjNumFlag &&  tmpZlYxqAreaFlag && tmpZlYxqDateFlag){
				totalFlag = true;
			}	
			if(totalFlag){				
				totalRes = totalZlAjNum + ':' + totalZlYxqArea + ':' + totalZlYxqDate;
				return totalRes;
			}	
		},
		//获取案件技术领域/案件申请人、联系人、发明人的公共方法
		getIdStr : function(obj,attrName){
			var tmpIdArray=[],strId = '';
			$('.'+obj).each(function(i){
				tmpIdArray.push($('.'+obj).eq(i).attr(attrName));
			});
			strId = tmpIdArray.join(',');
			return strId;
		},
		//删除添加的优先权
		delYxq : function(){
			$('.delYxqIcon').on('click',function(){
				$(this).parent().remove();
				return false;
			});
		},
		//发明人联系公共弹窗
		commonLayerLxrFmr : function(title,opts){
			var _this = this;
			if(sqrArray.length == 0){
				layer.msg('请先添加申请人', {icon:5,anim:6,time:1000});
				return;
			}
			layer.open({
				title:title,
				type: 2,
			  	area: ['850px', '520px'],
			  	fixed: true, //不固定
			  	maxmin: false,
			  	shadeClose :false,
			  	content: '/Module/zlBasicInfoManager/jsp/addLxr.html'
			});	
		},
		//获取定稿提交截至时间
		getCpyDate : function(){
			var date1 = new Date();
			var date2 = new Date(date1);
			date2.setDate(date1.getDate()+30);
			var defaultVal = date2.getFullYear() + "-" + (date2.getMonth()+1) + "-" + date2.getDate();
			laydate.render({
				elem: '#cpyDateInp',
				value : defaultVal
			});
		},
		//转换额外要求专利类型->文字
		switchYqTxtCHN : function(yqType){
			var yqTxt = '';
			if(yqType == 'fm' || yqType == 'fmxx'){
				yqTxt = '发明';
			}else if(yqType == 'wg'){
				yqTxt = '外观';
			}else if(yqType == 'syxx'){
				yqTxt = '新型';
			}
			return yqTxt;
		},
		//创建新型额外要求DOM
		createXxEyYqHtml : function(){
			var xxYqHtml = '';
			xxYqHtml += '<label class="layui-form-label">新型案件额外要求</label><input id="ajEwyqId_xx" type="hidden" name="ajEwyqId_xx"/>';
			xxYqHtml += '<input id="tmpAjEwyqId_xx" type="hidden"/>';
			xxYqHtml += '<div id="ewyqBox_xx" class="layui-input-block"><p class="ewyqTips">请先选择专利类型</p></div>';
			return xxYqHtml;
		},
		//获取案件额外要求记录
		getEwYqList : function(yqType){
			var _this = this;
			yqTxt = this.switchYqTxtCHN(yqType);
			$('.singleYqTxt').html(yqTxt + '案件额外要求');
			$.ajax({
				type:'post',
		        async:false,
		        dataType:'json',
		        data:{yqType : yqType},
		        url:'/zlyq.do?action=getZlyqData',
		        success:function (json){
		        	layer.closeAll('loading');
		        	if(json['result'] == 'success'){
		        		if(yqType == 'fmxx'){
		        			var fmyqInfo = json.fmyqInfo,xxyqInfo = json.xxyqInfo;
		        			var xxYqHtml = _this.createXxEyYqHtml();
		        			$('#xxEyYqBox').show().html(xxYqHtml);
		        			_this.renderZlYq_multi(fmyqInfo,xxyqInfo);
		        		}else{
		        			var yqInfo = json.yqInfo;
		        			_this.renderZlYq(yqInfo);
		        		}
		        	}else if(json['result'] == 'noAbility'){
		        		$('#ewyqBox').html('<p class="noYqData"><i class="layui-icon layui-icon-face-cry"></i>抱歉，您暂无权限获取专利额外要求列表</p>');
		        	}else if(json['result'] == 'noInfo'){
		        		$('#ewyqBox').html('<p class="noYqData"><i class="layui-icon layui-icon-face-cry"></i>暂无此专利类型的案件专利额外要求，如若需要，请联系超级管理员进行添加</p>');
		        	}
		        }
			});
		},
		//获取案件编号
		getAjNum : function(yqType){
			layer.load('1');
			$.ajax({
				type:'post',
		        async:false,
		        dataType:'json',
		        data:{ajType : yqType},
		        url:'/zlm.do?action=getCurrAjNo',
		        success:function (json){
		        	layer.closeAll('loading');
		        	if(json['result'] == 'success'){
		        		$('#ajNumInp').val(json['currNextAjNo']);
		        	}else if(json['result'] == 'error'){
		        		layer.msg('系统错误，请稍后重试', {icon:5,anim:6,time:1000});
		        	}
		        }
			});
		},
		//通过选择的专利类型加载对应的专利额外要求
		renderZlYq : function(yqInfo){
			var strHtml = '';
			for(var i=0;i<yqInfo.length;i++){
				strHtml += '<input type="checkbox" name="zlYqInfoInp" lay-filter="ewyqFilter" value="'+ yqInfo[i].id +'" title="'+ yqInfo[i].yqContent +'" lay-skin="primary"/>';
			}
			$('#ewyqBox').html(strHtml);
			form.render();
		},
		renderZlYq_multi : function(fmyqInfo,xxyqInfo){
			var strHtml = '',strHtml_xx = '';
			for(var i=0;i<fmyqInfo.length;i++){
				strHtml += '<input type="checkbox" name="zlYqInfoInp" lay-filter="ewyqFilter" value="'+ fmyqInfo[i].id +'" title="'+ fmyqInfo[i].yqContent +'" lay-skin="primary"/>';
			}
			for(var i=0;i<xxyqInfo.length;i++){
				strHtml_xx += '<input type="checkbox" name="zlYqInfoInp_xx" lay-filter="ewyqFilter_xx" value="'+ xxyqInfo[i].id +'" title="'+ xxyqInfo[i].yqContent +'" lay-skin="primary"/>';
			}
			$('#ewyqBox').html(strHtml);
			$('#ewyqBox_xx').html(strHtml_xx);
			form.render();
		},
		agentDlFee_usual : function(obj){
			var _this = this;
			$('#'+obj).on('keyup',function(){
				_this.judgeAgentFee(obj);
			});
			$('#'+obj).on('blur',function(){
				var tmpFlag = false;
				addEditZlOpts == 'editZlOpts' ? tmpFlag = true : tmpFlag = false;
				_this.judgeAgentFee_num(obj,tmpFlag,parent.globalZlId);
			});
		},
		//增加专利时代理费用填写格式
		judgeAgentFee : function(obj){
			if($('#'+obj).val().length==1){
				$('#'+obj).val($('#'+obj).val().replace(/[^1-9]/g,''));
			}else{
				$('#'+obj).val($('#'+obj).val().replace(/\D/g,''));
			}
		},
		judgeAgentFee_num : function(obj,isRightFlag,zlId){
			/*if($('#'+obj).val() == ''){
				layer.msg('专利代理费用不能为空', {icon:5,anim:6,time:1500});
				return;
			}else */if($('#'+obj).val() < 100 && $('#'+obj).val() != ''){
				layer.msg('专利代理费用最少不能低于100元', {icon:5,anim:6,time:1500});
				$('#'+obj).val('');
				return;
			}else if($('#'+obj).val() > 99999){
				layer.msg('专利代理费用最高不能超过99999', {icon:5,anim:6,time:1500});
				$('#'+obj).val('');
				return;
			}else{
				if(isRightFlag && $('#'+obj).val() != ''){//当是编辑的时候需要检测代理费用是否合理
					this.judgeIsRight_agentFee(zlId,$('#'+obj).val());
				}
			}
		},
		//检测当前输入代理费是否合理
		judgeIsRight_agentFee : function(zlId,dlFee){
			layer.load('1');
			$.ajax({
				type:"post",
		        async:false,
		        dataType:"json",
		        data : {zlId :zlId,dlFee:dlFee},
		        url:"/zlm.do?action=checkInpDlFee",
		        success:function (json){
		        	layer.closeAll('loading');
		        	if(json["result"] == 0){
		        	}else if(json["result"] == 1){
		        		layer.msg('客户已交完代理费，不能进行修改', {icon:5,anim:6,time:1200});
		        		return;
		        	}else if(json["result"]  == 2){
		        		layer.msg('代理费不能小于客户已交费用', {icon:5,anim:6,time:1200});
		        		return;
		        	}else if(json["result"]  == 3){
		        		layer.msg('代理费必须是100-100000的正整数', {icon:5,anim:6,time:1200});
		        		return;
		        	}
		        }
			});
		},
		selectFileUpload : function(){
			var upLoadFileList = $('#upLoadFileList'),url = '',fileType = 'doc|docx|wps|xls|xlsx|txt|pdf|pptx|ppt|zip|rar|dwg|eml|jpg|png|bmp|gif|jpeg|vsd|vsdx';;
			if(addEditZlOpts == 'editZlOpts'){//编辑已增加的专利
				url = '/upload.do?action=uploadFile&ajId=' + zlId + '&fileType=dg';
			}else{//增加专利
				url = '/upload.do?action=uploadFile&ajId=0&fileType=dg';
			}
			//addEditZlOpts 增加专利 编辑专利 流程分配完成后的专利编辑
			globalUpload.uploadFiles(url,5,fileType,'addEditZlOpts');
			if(addEditZlOpts == 'editZlOpts' && $('.deleteBtn_edit').length > 0){//表示编辑的时候执行附件删除动作
				$('.deleteBtn_edit').on('click',function(){
					$(this).parent().parent().remove();
					if($('.deleteBtn_edit').length == 0){
						$('#fujianInp').val('');
					}
				});
			}
		}
	};
	//是否费减
	form.on('radio(isHasFeeRateFilter)', function(data){
		var value = data.value;
		$('#rateInp').val(value);
	});
	//事务提醒方式
	form.on('select(shiwuSelectFilter)', function(data){
		var value = data.value,parent = $(this).parent().parent().parent();
		parent.find('.shiwuInp').val(value);
	});
	form.on('radio(ajSqAreaFilter)', function(data){
		var value = data.value;
		$('#ajSqAddress').val(value);
	});
	//案件额外要求->单个专利类型下
	form.on('checkbox(ewyqFilter)',function(data){
		var value = data.value;
		if(data.elem.checked){
			tmpEwyqIdArray.push(value);
		}else{
			for(var i=0;i<tmpEwyqIdArray.length;i++){
				if(value == tmpEwyqIdArray[i]){
					tmpEwyqIdArray.splice(i,1);
				}
			}
		}
	});
	//案件额外要求->发明+新型下的新型
	form.on('checkbox(ewyqFilter_xx)',function(data){
		var value = data.value;
		if(data.elem.checked){
			tmpEwyqIdArray_xx.push(value);
		}else{
			for(var i=0;i<tmpEwyqIdArray_xx.length;i++){
				if(value == tmpEwyqIdArray_xx[i]){
					tmpEwyqIdArray_xx.splice(i,1);
				}
			}
		}
	});
	//输出接口
    exports('zlInfoMethod', obj);
});