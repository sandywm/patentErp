var singleDlFeeNum = 0,multiDlFeeNum = 0,currOpts = '';
layui.define(['laydate','form','upLoadFiles'],function(exports){
	var laydate = layui.laydate,form = layui.form,globalUpload = layui.upLoadFiles;
	var obj = {
		data : {
			isXxFeeFlag : false,
			isClickZlTypeFlag : false,
			isZlNumRightFlag : false//不同专利类型对应专利编号格式
		},
		bindEvent_comMet : function(){
			var _this = this;
			//保存 编辑专利
			$('#saveZlBtn').on('click',function(){
				var anjianTypeVal=$('#anjianType').val(),sqrName='',tmpFmPath=[],tmpXxPath=[], ajTitleVal = $.trim($('#ajTitleInp').val()),//案件标题
					//ajNumInpVal = $('#ajNumInp').val(),//案件编号(旧案下使用)
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
					ajRemarkVal = $('#ajRemark').val(),//案件备注
					cpyDateInpVal = $('#cpyDateInp').val(),//定稿提交截至时间
					pubZlIdInpVal = $('#pubZlIdInp').val(),//已领取专利任务
					payerInpVal = $('#payerInp').val(),//付款方
					url = '',fmPathVal = '',xxPathVal = '',
					globalDlFee='',globalDlFee_fm='',globalDlFee_xx='',
					ajJsPathVal='',ajHtPathVal='',ajDgPathVal_fm='',ajDgPathVal_xx='',ajDgPathVal='',isFjTypeFlag=true;
				ajFieldIdVal = _this.getIdStr('delFieldBtn','jffieldattrid');
				ajSqrIdVal = _this.getIdStr('delSqrBtn','sqrattrid');
				sqrName = _this.getIdStr('delSqrBtn','sqrattrname');
				ajLxrIdVal = _this.getIdStr('delLxrBtn','lxridattr');
				ajFmrIdVal = _this.getIdStr('delFmrBtn','fmridattr');
				ajJsLxrIdVal = _this.getIdStr('delJsLxrBtn','fmridattr');
				//alert(ajFieldIdVal + "----" + ajSqrIdVal + "--" + ajLxrIdVal + "--" + ajFmrIdVal + "----@@" + ajJsLxrIdVal)
				if(tmpEwyqIdArray.length != 0){
					$('#tmpAjEwyqId').val(tmpEwyqIdArray.join(','));
					ajEwyqIdVal = $('#tmpAjEwyqId').val();
				}else{
					$('#tmpAjEwyqId').val(0);
				}
				if(anjianTypeVal == ''){
					layer.msg('请选择案件类型', {icon:5,anim:6,time:1500});
				}else{
					var isNewAjTypeFlag = true,isOldAjRightFlag=false;//旧案下各个条件是否都已满足，新案下为true
					//案件类型 分为新建new 和 旧案 old
					anjianTypeVal == 'new' ? isNewAjTypeFlag : isNewAjTypeFlag = false;
					if(ajTitleVal == ''){
						layer.msg('案件标题不能为空', {icon:5,anim:6,time:1500});
					}else if(zlTypeInpVal == ''){
						layer.msg('请选择专利类型', {icon:5,anim:6,time:1500});
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
						var dlFeeListDivLen = $('#dlFeeListWrap div').length,
							dlFeeList_xxLen = $('#dlFeeListWrap_xx div').length,
							isDlFeeRightFlag = false;//检测代理费是否填写正确
							fmxxZlNumStr = fmZlNumInpVal + ',' + xxZlNumInpVal;
						if(remindInpVal == ''){
							layer.msg('请选择提醒方式', {icon:5,anim:6,time:1500});
						}else if(dlFeeListDivLen == 0 && zlTypeInpVal != 'fmxx'){
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
								$('#tmpAjEwyqId').val() == '' ? ajEwyqIdVal = 0 : ajEwyqIdVal = $('#tmpAjEwyqId').val();
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
													ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,feeRate:rateInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
													ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
													yxqDetail:yxqDetailInpVal,ajEwyqId:ajEwyqIdVal,ajRemark:escape(ajRemarkVal),
													ajUpload:'',ajUploadHt:'',cpyDate:cpyDateInpVal,pubZlId:pubZlIdInpVal,dlFee:globalDlFee};
										}else{//发明+新型
											var field = {ajTitle:escape(ajTitleVal),
													ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,feeRate:rateInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
													ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
													yxqDetail:yxqDetailInpVal,ajEwyqId:ajEwyqIdVal,ajRemark:escape(ajRemarkVal),           
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
													ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,feeRate:rateInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
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
													var field = {ajTitle:escape(ajTitleVal),
															ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,feeRate:rateInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,ajSqAddress:escape(ajSqAddressVal),
															ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
															yxqDetail:yxqDetailInpVal,ajEwyqId:ajEwyqIdVal,ajRemark:escape(ajRemarkVal),
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
													var field = {ajTitle:escape(ajTitleVal),
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
														var field = {ajTitle:escape(ajTitleVal),
															ajType:zlTypeInpVal,ajType1:anjianTypeVal,feeTxType:remindInpVal,feeRate:rateInpVal,payUserInfo:payerInpVal,ajFieldId:ajFieldIdVal,zlNoGf:fmxxZlNumStr,ajSqAddress:escape(ajSqAddressVal),
															ajSqrId:ajSqrIdVal,ajSqrName:escape(sqrName),ajLxrId:ajLxrIdVal,ajFmrId:ajFmrIdVal,jsLxrId:ajJsLxrIdVal,
															yxqDetail:yxqDetailInpVal,ajEwyqId:ajEwyqIdVal,ajRemark:escape(ajRemarkVal),
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
				   			        		layer.msg('当前选择的专利任务已被其他专利占用！', {icon:5,anim:6,time:2200});
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
				if($('#'+obj).val() == ''){
					layer.msg('旧案专利号不能为空', {icon:5,anim:6,time:2000});
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
				var tmpStr = $('#'+obj).val().substring(4,5);
				if($('#zlTypeInp').val() == 'fm' || $('#zlTypeInp').val() == 'fmxx' && opts == 'fmZlNum'){//第五位1
					if(tmpStr == 1){
						//_this.data.isZlNumRightFlag = true;
					}else{
						//_this.data.isZlNumRightFlag = false;
						//$('#'+obj).val('');
						layer.msg('发明专利号格式错误', {icon:5,anim:6,time:2000});
					}
				}else if($('#zlTypeInp').val() == 'syxx' || $('#zlTypeInp').val() == 'fmxx' && opts == 'xxZlNum'){//第五位 2、8
					if(tmpStr == 2 || tmpStr == 8){
						//_this.data.isZlNumRightFlag = true;
					}else{
						//$('#'+obj).val('');
						//_this.data.isZlNumRightFlag = false;
						layer.msg('实用新型专利号格式错误', {icon:5,anim:6,time:2000});
					}
				}else if($('#zlTypeInp').val() == 'wg'){//3、9
					if(tmpStr == 3 || tmpStr == 9){
						//_this.data.isZlNumRightFlag = true;
					}else{
						//$('#'+obj).val('');
						//_this.data.isZlNumRightFlag = false;
						layer.msg('外观专利号格式错误', {icon:5,anim:6,time:2000});
					}
				}
				$('#'+obj).val($('#'+obj).val().toUpperCase());
			});
		},
		//旧案下 发明+新型下增加发明专利号 新型专利号 可编辑
		createFmXxZlNumDom : function(){
			var strHtml = '';
			strHtml += '<div id="fmxxZlNumWrap"><div class="zlNumBox"><span>*</span><p>发明专利号</p>';
			strHtml += '<input id="fmZlNumInp" type="text" placeholder="请输入发明专利号" maxlength="30" autocomplete="off" class="layui-input"/></div>';
			strHtml += '<div class="zlNumBox hasMargLeft"><span>*</span><p>新型专利号</p>';
			strHtml += '<input id="xxZlNumInp" type="text" placeholder="请输入新型专利号" maxlength="30" autocomplete="off" class="layui-input"/></div></div>';
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
					strHtml += '<input type="text" id="remdDateInp_'+ singleDlFeeNum +'" class="layui-input remindDateInp" placeholder="请选择提醒日期" autocomplete="off"/>';
				}else{
					//currOpts->xx
					strHtml += '<input type="text" id="remDateInpMul_'+ multiDlFeeNum +'"  class="layui-input remDateInp_multi" placeholder="请选择提醒日期" autocomplete="off"/>';
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
		//获取案件额外要求记录
		getEwYqList : function(yqType){
			var _this = this;
			$.ajax({
				type:'post',
		        async:false,
		        dataType:'json',
		        data:{yqType : yqType},
		        url:'/zlyq.do?action=getZlyqData',
		        success:function (json){
		        	layer.closeAll('loading');
		        	var yqInfo = json.yqInfo;
		        	if(json['result'] == 'success'){
		        		_this.renderZlYq(yqInfo);
		        	}else if(json['result'] == 'noAbility'){
		        		$('#ewyqBpx').html('<p class="noYqData"><i class="layui-icon layui-icon-face-cry"></i>抱歉，您暂无权限获取专利额外要求列表</p>');
		        	}else if(json['result'] == 'noInfo'){
		        		$('#ewyqBpx').html('<p class="noYqData"><i class="layui-icon layui-icon-face-cry"></i>暂无此专利类型的案件专利额外要求，如若需要，请联系超级管理员进行添加</p>');
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
		//更加选择的专利类型加载对应的专利额外要求
		renderZlYq : function(yqInfo){
			var strHtml = '';
			for(var i=0;i<yqInfo.length;i++){
				strHtml += '<input type="checkbox" name="zlYqInfoInp" lay-filter="ewyqFilter" value="'+ yqInfo[i].id +'" title="'+ yqInfo[i].yqContent +'" lay-skin="primary"/>';
			}
			$('#ewyqBpx').html(strHtml);
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
			/*$('#zlAgentFeeInp').on('keyup',function(){
				_this.judgeAgentFee('zlAgentFeeInp');
			});
			$('#zlAgentFeeInp').on('blur',function(){
				var tmpFlag = false;
				addEditZlOpts == 'editZlOpts' ? tmpFlag = true : tmpFlag = false;
				_this.judgeAgentFee_num('zlAgentFeeInp',tmpFlag,parent.globalZlId);
			});*/
		},
		/*agentDlFee_special : function(){
			var _this = this;
			$('#xxAgentFeeInp').on('keyup',function(){
				_this.judgeAgentFee('xxAgentFeeInp');
			});
			$('#xxAgentFeeInp').on('blur',function(){
				_this.judgeAgentFee_num('xxAgentFeeInp',false);
			});
		},*/
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
	//案件额外要求
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
	//输出接口
    exports('zlInfoMethod', obj);
});