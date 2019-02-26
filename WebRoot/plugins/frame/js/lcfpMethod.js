layui.define(['rate'],function(exports){
	var rate = layui.rate;
	var obj = {
		data : {
			isSaveLcFpFlag : false,
			isZlLcFpFlag : true,//判断是否是专利信息下的流程分配 true是
			difflevel : 0,
			zlAjType : ''
		},
		bindEvent_fp : function(){
			var _this = this;
			//增加专利撰写人员
			$('#addZxPeople').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加专利撰写人');
			});
			//专利审查人员
			$('#addZlShPeople').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加专利审查人员');
			});
			//定稿提交人员 
			$('#addDinggao').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加定稿提交人员');
			});
			//导入通知书人员
			$('#addTzs').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加通知书导入人员');
			});
			//费用催缴人员
			$('#addFeiyong').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加费用催缴人员');
			});
			//专利补正人员
			$('#addBuzheng').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加专利补正人员');
			});
			//补正审核人员
			$('#addShBz').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加补正审核人员');
			});
			//专利驳回人员
			$('#addBohui').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加专利驳回人员');
			});
			//客户确认人员 
			$('#addCusSure').on('click',function(){
				globalTypeUser = $(this).attr('opt');
				_this.commonLayerOpen('增加客户确认人员');
			});
			//保存
			$('#saveLcFpBtn').on('click',function(){
				var zxUserId = $('#addZxUser_con').attr('userId'),
					checkUserId = $('#addZlShUser_con').attr('userId'),
					tjUserId = $('#addDingGaoUser_con').attr('userId'),
					tzsUserId = $('#addTongZhiShuUser_con').attr('userId'),
					feeUserId = $('#addFeeUser_con').attr('userId'),
					bzUserId = $('#addBuZhengUser_con').attr('userId'),
					bzshUserId = $('#addBuzhengShUser_con').attr('userId'),
					bhUserId = $('#addZlBhUser_con').attr('userId'),
					cusSuerUserId = $('#addCusSureUser_con').attr('userId');
				var index= parent.layer.getFrameIndex(window.name),
					isNewAjTypeFlag = true,isOldAjRightFlag=false;
				_this.data.zlAjType == 'new' ? isNewAjTypeFlag : isNewAjTypeFlag = false;
				if(_this.data.difflevel == 0){
					layer.msg('请设置专利难易度！', {icon:5,anim:6,time:1500});
				}else if(isNewAjTypeFlag){//新案下需要
					if(zxUserId == undefined || zxUserId==''){
						zxUserId = 0;
					}
					if(checkUserId == undefined || checkUserId == ''){
						layer.msg('请选择专利审核人员！', {icon:5,anim:6,time:1500});
						return;
					}
					isOldAjRightFlag = true;
				}else if(isNewAjTypeFlag == false){
					isOldAjRightFlag = true;
				}
				if(isOldAjRightFlag){
					if(cusSuerUserId == undefined || cusSuerUserId==''){
						layer.msg('请选择客户确认人员！', {icon:5,anim:6,time:1500});
					}else if(isNewAjTypeFlag && tjUserId == undefined || tjUserId == ''){//旧案下
						layer.msg('请选择定稿提交人员！', {icon:5,anim:6,time:1500});
					}else if(tzsUserId == undefined || tzsUserId ==''){
						layer.msg('请选择通知书导入人员！', {icon:5,anim:6,time:1500});
					}/*else if(feeUserId == undefined || feeUserId==''){
						layer.msg('请选择费用催缴人员！', {icon:5,anim:6,time:1500});
					}*/else if(bzUserId == undefined || bzUserId==''){
						layer.msg('请选择专利补正人员！', {icon:5,anim:6,time:1500});
					}else if(bzshUserId == undefined || bzshUserId==''){
						layer.msg('请选择专利补正审核人员！', {icon:5,anim:6,time:1500});
					}/*else if(bhUserId == undefined || bhUserId==''){
						layer.msg('请选择专利驳回人员！', {icon:5,anim:6,time:1500});
					}*/else{
						//alert(zxUserId + "=zxUserId-" + checkUserId + "=checkUserId-" + tjUserId + "=tjUserId-" + tzsUserId + "=tzsUserId-" + feeUserId + "=feeUserId-" + bzUserId + "=bzUserId-" + bzshUserId + "=bzshUserId-" + bhUserId + "=bhUserId")
						if(isNewAjTypeFlag){//新案
							var field={zlId:parent.globalZlId,zlLevel:_this.data.difflevel,zxUserId:zxUserId,checkUserId:checkUserId,tjUserId:tjUserId,tzsUserId:tzsUserId,
									feeUserId:0,bzUserId:bzUserId,bzshUserId:bzshUserId,bhUserId:0,cusCheckUserId:cusSuerUserId};
						}else{//旧案
							var field={zlId:parent.globalZlId,zlLevel:_this.data.difflevel,tzsUserId:tzsUserId,
									feeUserId:0,bzUserId:bzUserId,bzshUserId:bzshUserId,bhUserId:0,cusCheckUserId:cusSuerUserId};
						}
						$.ajax({
	  						type:'post',
					        async:false,
					        dataType:'json',
					        data : field,
					        url:'/zlm.do?action=updateOperatorUserInfo',
					        success:function (json){
					        	layer.closeAll('loading');
					        	if(json['result'] == 'success'){
					        		if(_this.data.isZlLcFpFlag){
					        			_this.data.isSaveLcFpFlag = true;
						        		layer.msg('流程人员分配设置成功',{icon:1,time:1000});
					        		}else{
					        			layer.msg('流程分配设置成功',{icon:1,time:1000},function(){
						        			parent.addZlFlag = true;
						        			parent.layer.close(index);
						        		});
					        		}
					        	}else if(json['result'] == 'stopInfo'){
					        		layer.msg('案件终止状态下不能进行修改', {icon:5,anim:6,time:2200});
					        	}else if(json['result'] == 'noAbility'){
					        		layer.msg('抱歉，您暂无权限进行流程分配', {icon:5,anim:6,time:2200});	
					        	}else if(json['result'] == 'notUpdate'){
					        		layer.msg('定稿提交以后不能对撰写人、技术审核人员、定稿提交人员进行分配', {icon:5,anim:6,time:2500});
					        	}
					        }
	  					});
					}
				}
				
			});
		},
		//流程分配公共openlayer方法
		commonLayerOpen : function(title){
			layer.open({
				title:title,
				type: 2,
			  	area: ['900px', '510px'],
			  	fixed: true, //不固定
			  	maxmin: false,
			  	shadeClose :false,
			  	content: '/Module/zlBasicInfoManager/jsp/selectLcFpStaff.html'
			});	
		},
		//设置专利难易度
		rateFun : function(elem,value,isReadFlag){
			var _this = this;
			rate.render({
			    elem: '#' + elem
			    ,value : value
			    ,length:3
			    ,readonly:isReadFlag
			    ,text: true
			    ,theme: '#FF8000'
		    	,choose: function(value){
					if(value == 1){
						_this.data.difflevel = 1;
					}else if(value == 2){
						_this.data.difflevel = 2;
					}else if(value == 3){
						_this.data.difflevel = 3;
					}
				}
			    ,setText: function(value){ //自定义文本的回调
			      var arrs = {
			        '1': '简单'
			        ,'2': '困难'
			        ,'3': '复杂'
			      };
			      this.span.text(arrs[value] || '');
			    }
			});
		}
	};
	
	exports('lcfpMethod',obj);
});