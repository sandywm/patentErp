layui.define(['laydate','form','upLoadFiles'],function(exports){
	var laydate = layui.laydate,form = layui.form,globalUpload = layui.upLoadFiles;
	var obj = {
		data : {
			isXxFeeFlag : false,
			isClickZlTypeFlag : false
		},
		bindEvent_comMet : function(){
			var _this = this;
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
									$('#zlAgentFeeInp').val(dlFeeNum);
									!_this.data.isClickZlTypeFlag ? _this.agentFeeTxtByZlType($('.zlTypeRad').eq(i).val()) : '';
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
				layer.open({
					title:'添加申请人',
					type: 2,
				  	area: ['720px', '350px'],
				  	fixed: true, //不固定
				  	maxmin: false,
				  	shadeClose :false,
				  	content: '/Module/zlBasicInfoManager/jsp/addSqr.html'
				});	
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
					layer.msg('抱歉，最多可以添加5个优先权', {icon:5,anim:6,time:1000});
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
		//未选择专利类型先选择的已领取未添加任务这块显示对应文本框公共调用
		agentFeeInpShow : function(){
			this.agentDlFee_usual();
			$('.agentInp').show();
			$('.moneyDec').show();
			$('.tipsInfo').remove();
		},
		//根据专利类型显示对应的代理费用文字
		agentFeeTxtByZlType : function(value){
			$('.twoAgentDiv').hide();
			$('.oneAgentDiv').addClass('oneWid').removeClass('twoWid');
			if(value == 'fm'){
				$('.agentTit').html('发明专利代理费');
			}else if(value == 'syxx'){
				$('.agentTit').html('实用新型专利代理费');
			}else if(value == 'wg'){
				$('.agentTit').html('外观专利代理费');
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
			  	area: ['720px', '380px'],
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
		agentDlFee_usual : function(){
			var _this = this;
			$('#zlAgentFeeInp').on('keyup',function(){
				_this.judgeAgentFee('zlAgentFeeInp');
			});
			$('#zlAgentFeeInp').on('blur',function(){
				var tmpFlag = false;
				addEditZlOpts == 'editZlOpts' ? tmpFlag = true : tmpFlag = false;
				_this.judgeAgentFee_num('zlAgentFeeInp',tmpFlag,parent.globalZlId);
			});
		},
		agentDlFee_special : function(){
			var _this = this;
			$('#xxAgentFeeInp').on('keyup',function(){
				_this.judgeAgentFee('xxAgentFeeInp');
			});
			$('#xxAgentFeeInp').on('blur',function(){
				_this.judgeAgentFee_num('xxAgentFeeInp',false);
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
				layer.msg('专利代理费用最少不能低于100元', {icon:5,anim:6,time:1200});
				$('#'+obj).val('');
				return;
			}else if($('#'+obj).val() > 99999){
				layer.msg('专利代理费用最高不能超过99999', {icon:5,anim:6,time:1200});
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
			globalUpload.uploadFiles(url,5,fileType,'');
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