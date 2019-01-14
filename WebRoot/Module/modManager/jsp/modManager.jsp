<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
  <head>
    <title>专利管理系统模块管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="keywords" content="专利管理,模块权限管理">
	<meta http-equiv="description" content="专利机构模块权限管理">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/modManager/css/modManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
	<script src="http://at.alicdn.com/t/font_787377_fddbgkpmuto.js"></script>
  </head>
  	
  <body>
  	<div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header posRel">
  						<span>模块权限管理</span>
  						<span id="outDateTips"></span>
  						<a id="addMod" class="posAbs newAddBtn" opts="addBtn" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加模块</a>
  						<div class="roleList clearfix fr">
  							<input id="roleIdInp" type="hidden" value="0"/>
  							<div id="roleLists" class="fl layui-form"></div>
  							<button class="layui-btn saveMod fl">保存</button>
  						</div>
  					</div>
  					<div class="layui-card-body" pad15>
  						<input type="hidden" id="madIdListInp"/>
  						<div id="moduleList" class="moduleList"></div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/jquery/jquery.min.js"></script>
    <script src="/plugins/layui/layui.js"></script>
	<script type="text/javascript">
		var roleName = parent.roleName,loginType = parent.loginType,globalOpts = "addBtn",globalModId=0,madIdArray=[],roleList=[];
		layui.use(['layer','form'],function(){
			var layer = layui.layer,
				//$ = layui.jquery,
				form = layui.form;
			var page = {
				data : {
					inpMainModName_cn : '',
					//inpMainModName_eng : '',
					inpMainModUrl : '',
					inpMainModOrderNo : 0,
					mainModLevelInp : 0,
					mainModShowInp  : 0,
					globalIndex : 0,
					globalMainModId : [] //用于存放所有主模块Id
				},
				init : function(){
					this.onLoad();
					this.bindEvent();
					this.bindEvent_multi();
				},
				onLoad : function(){
    				this.loadModuleList();
				},
				loadModuleList : function(){
					this.loadMainModuleList();
					if(loginType == "spUser"){
						$("#addMod").show();
					}else if(loginType == "cpyUser"){
						$(".roleList").show();
						this.loadRoleList(roleList);
					}
				},
				//获取模块列表
				loadMainModuleList : function(){
					var url = "",_this = this,
					selRoleId = $("#roleIdInp").val();
					if(loginType == "spUser"){
						url = "modM.do?action=getModuleDetail";
					}else if(loginType == "cpyUser"){
						url = "modM.do?action=getModuleDetail&selRoleId=" + selRoleId;
					}
					layer.load('1');
					$.ajax({
	   					type:"post",
	   			        async:false,
	   			        dataType:"json",
	   			        url:url,
	   			        success:function (json){
	   			        	layer.closeAll("loading");
	   			        	var modList = json.result,
	   			        		allBindFlag = json.allBindFlag,
	   			        		endFlag = json.endFlag;
	   			        	roleList = json.roleInfo;
	   			        	getModuleList(modList,allBindFlag,endFlag);
	   			        }
	   				});
				},
				//管理员身份下加载角色列表
				loadRoleList : function(roleList){
					var roleList = roleList;
					var strHtml = '';
					strHtml += '<div class="layui-input-inline">';
					strHtml += '<select id="roleListSel" lay-filter="roleListSel"><option value="">请选择角色列表</option>';		 	
					for(var i=0;i<roleList.length;i++){
						strHtml += '<option value="'+ roleList[i].roleId +'">'+ roleList[i].roleName +'</option>';
					}
					strHtml += '</select></div>';
					$('#roleLists').html(strHtml);
					form.render();
				},
				bindEvent:function(){
					var _this = this;
					$('.saveMod').on('click',function(){
						if(loginType == 'cpyUser'){
							if($('#roleIdInp').val() == 0){
								layer.msg('请选择您要绑定的角色', {icon:5,anim:6,time:1000});
								return;
							}
							madIdArray.length = 0;
							$('.sonModSelInp').each(function(i){
								var checkStatus = $('.sonModSelInp').eq(i).prop('checked');
								if(checkStatus){
									madIdArray.push($('.sonModSelInp').eq(i).val());
								}
							});
							$('#madIdListInp').val(madIdArray.join(','));
							if($('#madIdListInp').val() == ''){
								layer.msg('请选择您要绑定的模块', {icon:5,anim:6,time:1000});
							}else{
								layer.load('1');
								$.ajax({
				   					type:"post",
				   			        async:false,
				   			        dataType:"json",
				   			        data:{selRoleId:$('#roleIdInp').val(),selMaIdStr:$('#madIdListInp').val()},
				   			        url:"modM.do?action=bindMod",
				   			        success:function (json){
				   			        	layer.closeAll("loading");
				   			        	if(json['result'] == 'success'){
				   			        		layer.msg('绑定模块成功',{icon:1,time:1000},function(){
				   			        			$('#madIdListInp').val('');
		    				        		});
				   			        	}else if(json['result'] == 'noAbility'){
				   			        		layer.msg('抱歉，您暂无权限为角色绑定模块', {icon:5,anim:6,time:1000});
				   			        	}
				   			        }
				   				});
							}
						}else{
							layer.msg('抱歉，您暂无权限对模块保存操作', {icon:5,anim:6,time:1000});
						}
					});
					//增加模块
					$('#addMod').on('click',function(){
						if(loginType == 'spUser'){
							globalOpts = $(this).attr("opts");
        					var addEditMainModCon = '';
        					addEditMainModCon += '<div class="addEditMainModWrap layui-form">';
        					addEditMainModCon += '<div class="comAddEditDiv"><span class="fl">模块中文名字：</span><input id="inpMainModName_cn" type="text" placeholder="请输入模块中文名字(15字以内)" maxlength="15"></div>';
        					//addEditMainModCon += '<div class="comAddEditDiv"><span class="fl">模块英文名字：</span><input id="inpMainModName_eng" type="text" placeholder="请输入模块英文名字"></div>';
        					addEditMainModCon += '<div class="comAddEditDiv"><span class="margLSpan_url fl">模块动作Url：</span><input id="inpMainModUrl" type="text" placeholder="请输入模块动作url"></div>';
        					//addEditMainModCon += '<div class="comAddEditDiv"><span class="margLSpan_order fl">模块序列号：</span><input id="inpMainModOrderNo" type="text" placeholder="模块排列序列号(大于0的正整数)"></div>';
        					addEditMainModCon += '<div class="comAddEditDiv"><span class="margLSpan_level fl">模块权限等级：</span>';
        					addEditMainModCon += '<input type="hidden" id="mainModLevelInp" value="0"/>';
        					addEditMainModCon += '<input type="radio" name="modLevel" lay-filter="modLevelFilter" value="0" title="铜牌" checked/>';
        					addEditMainModCon += '<input type="radio" name="modLevel" lay-filter="modLevelFilter" value="1" title="银牌"/>';
        					addEditMainModCon += '<input type="radio" name="modLevel" lay-filter="modLevelFilter" value="2" title="金牌"/>';
        					addEditMainModCon += '<input type="radio" name="modLevel" lay-filter="modLevelFilter" value="3" title="钻石"/>';
        					addEditMainModCon += '</div>';
        					addEditMainModCon += '<div class="comAddEditDiv"><span class="margLSpan_show fl">是否可见：</span>';
        					addEditMainModCon += '<input type="hidden" id="mainModShowInp" value="0"/>';
        					addEditMainModCon += '<input type="radio" name="showStatus" lay-filter="modShowFilter" value="0" title="可见" checked/>';
        					addEditMainModCon += '<input type="radio" name="showStatus" lay-filter="modShowFilter" value="1" title="不可见"/>';
        					addEditMainModCon += '</div>';
        					addEditMainModCon += '</div>';
        					_this.data.globalIndex = layer.open({
    	        				title : '添加模块',
    	        				type: 1,
    	        				skin:'layui-layer-molv', //样式类名
    	        				closeBtn: 0, //不显示关闭按钮
    	        				area : ['500px','300px'],
    	        			  	content: addEditMainModCon,
    	        			  	btn : ['确定','取消'],
    	        			  	btnAlign:'c',
    	        			  	yes: function(index, layero){
    	        			  		_this.addEditMainModFun();
    	        				},
    	        				success : function(layero, index){
    								form.render();
    							}
    	        			});
						}else{
							layer.msg('抱歉，您暂无权限增加模块', {icon:5,anim:6,time:1000});
						}
					});
				},
				bindEvent_multi : function(){
					var _this = this;
					//编辑主模块
					$('.editMainModName').on('click',function(){
						var modId = $(this).attr('modId');
						globalModId = modId;
						globalOpts = $(this).attr("opts");
						_this.data.globalIndex = layer.open({
	        				title : '编辑模块',
	        				type: 2,
	        				skin:'layui-layer-molv', //样式类名
	        				closeBtn: 1, //不显示关闭按钮
	        				area : ['500px','330px'],
	        				btn : ['确定','取消'],
	        			  	btnAlign:'c',
	        				content: '/Module/modManager/jsp/editMainModName.html',
	        				yes : function(index, layero){
	        					var body = layer.getChildFrame('body', index);
	        					if(body.find('#isHasData').val() == 1){//表示有数据
	        						_this.data.inpMainModName_cn = body.find('#inpMainModName_cn').val();
		        					//_this.data.inpMainModName_eng = body.find('#inpMainModName_eng').val();
		        					_this.data.inpMainModUrl = body.find('#inpMainModUrl').val();
		        					_this.data.inpMainModOrderNo = body.find('#inpMainModOrderNo').val();
		        					_this.data.mainModLevelInp = body.find('#mainModLevelInp').val();
		        					_this.data.mainModShowInp = body.find('#mainModShowInp').val();
		        					_this.addEditMainModFun(modId);
	        					}else{
	        						layer.close(index);
	        					}
	        				}
	        			});
						
					});
					//增加子模块
					$('.addSonModBtn').on('click',function(){
						var modId = $(this).attr('modId');
						var sonModNameStr = '';
						sonModNameStr += '<div class="editSonModNameCon">';
						sonModNameStr += '<div class="comEditDiv"><span class="fl">子模块中文名：</span><input id="sonModChiNameInp" type="text" placeholder="请输入子模块中文名" maxlength="10"/></div>';
						sonModNameStr += '<div class="comEditDiv"><span class="fl">子模块英文名：</span><input id="sonModEngNameInp" type="text" placeholder="请输入子模块英文名" maxlength="20"></div>';
						sonModNameStr += '</div>';
						_this.data.globalIndex = layer.open({
   	        				title : '增加子模块',
   	        				type : 1, 
   	        				closeBtn: 0, //不显示关闭按钮
   	        				area : ['400px','200px'],
   	        				skin:'layui-layer-molv',
   	        			  	content: sonModNameStr,
   	        			  	btn : ['确定','取消'],
   	        			  	btnAlign:'c',
   	        			  	yes: function(index, layero){
   	        			  		_this.addEditSonModName('addSonModOpt',modId,'','');
   	        				}
   	        			});
					});
					//编辑子模块内容 editSpanBtn
					$('.editSpanBtn').on('click',function(){
						var parent = $(this).parent();
						var maId = parent.attr('maId'),
							actNameChi = parent.attr('actNameChi'),actNameEng = parent.attr('actNameEng'),editThis = parent;
						var editSonModCon = '';
						editSonModCon += '<div class="editSonModNameCon">';
						editSonModCon += '<div class="comEditDiv"><span class="fl">子模块中文名：</span><input id="sonModChiNameInp" type="text" value="'+ actNameChi +'"></div>';
						editSonModCon += '<div class="comEditDiv"><span class="fl">子模块英文名：</span><input id="sonModEngNameInp" value="'+ actNameEng +'" type="text" placeholder="请输入模块英文名" maxlength="20"></div>';
						editSonModCon += '</div>';
						_this.data.globalIndex = layer.open({
   	        				title : '编辑子模块——' + actNameChi + '('+ actNameEng + ')',
   	        				type : 1, 
   	        				closeBtn: 0, //不显示关闭按钮
   	        				area : ['400px','200px'],
   	        				skin:'layui-layer-molv',
   	        			  	content: editSonModCon,
   	        			  	btn : ['确定','取消'],
   	        			  	btnAlign:'c',
   	        			  	yes: function(index, layero){
   	        			  		_this.addEditSonModName('editSonModOpt','',maId,editThis);
   	        				}
   	        			});
					});
					//删除子模块内容
					$('.delSpanBtn').on('click',function(){
						var parent = $(this).parent();
						var maId = parent.attr('maId');
						layer.confirm('确认要删除当前子模块吗？', {
						  title:'提示',
						  skin: 'layui-layer-molv',
						  btn: ['确定','取消'] //按钮
						},function(index){
							$.ajax({
       	  						type:"post",
       					        async:false,
       					        dataType:"json",
       					        data:{maId:maId},
       					        url:"/modM.do?action=delModAct",
       					        success:function (json){
       					        	if(json["result"] == "success"){
       					        		layer.msg("删除成功",{icon:1,time:1000},function(){
	    				        			parent.parent().remove();
	    				        			layer.close(index);
	    				        		});
       					        	}else if(json["result"] == "exist"){
       					        		layer.msg("该子模块存在角色权限绑定信息，不能删除", {icon:5,anim:6,time:1000});
       					        	}else if(json["result"] == "error"){
       					        		layer.msg("系统错误，请重试", {icon:5,anim:6,time:1000});
       					        	}else if(json["result"] == "noAbility"){
       					        		layer.msg("对不起，您暂无权限删除该主模块", {icon:5,anim:6,time:1000});
       					        	}
       					        }
       	  					});
						});
					});
					//删除主模块
					$('.delMainModName').on('click',function(){
						var modId = $(this).attr('modId');
       					layer.confirm('确认要删除该模块吗？',{
       					  title:'提示',
       					  skin: 'layui-layer-molv',
       					  btn: ['确定','取消'] //按钮
       					 }, function(){
       						$.ajax({
       	  						type:"post",
       					        async:false,
       					        dataType:"json",
       					        data:{modId:modId},
       					        url:"/modM.do?action=delModule",
       					        success:function (json){
       					        	if(json["result"] == "success"){
       					        		layer.msg("删除成功",{icon:1,time:1000},function(){
	    				        			_this.loadModuleList();
	    				        			_this.bindEvent_multi();
	    				        		});
       					        	}else if(json["result"] == "exist"){
       					        		layer.msg("该模块下子模块存在角色权限绑定信息，不能删除", {icon:5,anim:6,time:1000});
       					        	}else if(json["result"] == "error"){
       					        		layer.msg("系统错误，请重试", {icon:5,anim:6,time:1000});
       					        	}else if(json["result"] == "noAbility"){
       					        		layer.msg("对不起，您暂无权限删除该主模块", {icon:5,anim:6,time:1000});
       					        	}
       					        }
       	  					});
       					});
					});
					
				},
				//添加、编辑主模块方法
				addEditMainModFun : function(modId){
					var _this = this,
						modId = modId,
						inpMainModName_cn = globalOpts == 'addBtn' ? $.trim($('#inpMainModName_cn').val()) : _this.data.inpMainModName_cn,
						//inpMainModName_eng = globalOpts == 'addBtn' ? $.trim($('#inpMainModName_eng').val()) : _this.data.inpMainModName_eng,
						inpMainModUrl = globalOpts == 'addBtn' ? $.trim($('#inpMainModUrl').val()) : _this.data.inpMainModUrl,
						inpMainModOrderNo = globalOpts == 'addBtn' ? "" : _this.data.inpMainModOrderNo,
						mainModLevelInp = globalOpts == 'addBtn' ? $('#mainModLevelInp').val() : _this.data.mainModLevelInp,
						mainModShowInp = globalOpts == 'addBtn' ? $('#mainModShowInp').val() : _this.data.mainModShowInp,
						regCN = /^[\u4E00-\u9FA5]+$/,
						regEN =  /^[a-zA-Z]+$/,
						regNum = /^[1-9]\d*$/;
					if(inpMainModName_cn == ''){
						layer.msg('模块中文名字不能为空', {icon:5,anim:6,time:1000});
					}/*else if(inpMainModName_eng == ''){
						layer.msg('模块英文名字不能为空', {icon:5,anim:6,time:1000});
					}*/else if(inpMainModUrl == ''){
						layer.msg('模块动作Url不能为空', {icon:5,anim:6,time:1000});
					}else if(inpMainModOrderNo == ''  && globalOpts == 'editBtn'){
						layer.msg('模块序列号不能为空', {icon:5,anim:6,time:1000});
					}else{
						if(!regCN.test(inpMainModName_cn)){
							layer.msg('模块中文名字应为汉字', {icon:5,anim:6,time:1000});
						}/*else if(!regEN.test(inpMainModName_eng)){
							layer.msg('模块英文名字应为英语', {icon:5,anim:6,time:1000});
						}*/else if(regCN.test(inpMainModUrl)){
							layer.msg('模块动作Url不能含有汉字', {icon:5,anim:6,time:1000});
						}else if(!regNum.test(inpMainModOrderNo) && globalOpts == 'editBtn'){
							layer.msg('模块序列号应为大于0的正整数', {icon:5,anim:6,time:1000});
						}else{
							//执行ajax
							var url = '';
							if(globalOpts == 'addBtn'){//表示增加主模块
								url = 'modM.do?action=addModule';
								var field = {modName:inpMainModName_cn,/*actNameEng:inpMainModName_eng,*/resUrl:inpMainModUrl,modLevel:mainModLevelInp,showStatus:mainModShowInp};
							}else if(globalOpts == 'editBtn'){//表示编辑主模块
								url = 'modM.do?action=upModule';
								var field = {modId : modId,modName:inpMainModName_cn,/*actNameEng:inpMainModName_eng,*/resUrl:inpMainModUrl,orderNo:inpMainModOrderNo,modLevel:mainModLevelInp,showStatus:mainModShowInp};;
							}
							layer.load('1');
							$.ajax({
	      						type:"post",
	    				        async:false,
	    				        dataType:"json",
	    				        data :field,
	    				        url:url,
	    				        success:function (json){
	    				        	layer.closeAll("loading");
	    				        	if(json["result"] == "success"){
	    				        		var strTxt = '';
	    				        		if(globalOpts == "addBtn"){//表示增加主模块
	    				        			strTxt = '添加成功';
	    				        		}else if(globalOpts == "editBtn"){//表示编辑主模块
	    				        			strTxt = '编辑成功';
	    				        		}
	    				        		function callBackSuccess(){
	    				        			layer.msg(strTxt,{icon:1,time:1000},function(){
	    				        				_this.loadModuleList();
		    				        			_this.bindEvent_multi();
		    				        			layer.close(_this.data.globalIndex);
		    				        			_this.data.globalIndex = 0;
		    				        		});
	    				        		}
	    				        		callBackSuccess(strTxt);
	    				        	}else if(json["result"] == "exist"){//模块名字已存在
	    				        		layer.msg("该模块已存在", {icon:5,anim:6,time:1000});
	    				        	}else if(json["result"] == "error"){//系统错误
	    				        		layer.msg("系统错误，请稍后重试", {icon:5,anim:6,time:1000});
	    				        	}else if(json["result"] == "noAbility"){
	    				        		layer.msg("抱歉，您暂无权限增加模块", {icon:5,anim:6,time:1000});
	    				        	}
	    				        }
	      					});
						}
					}
				}, 
				//添加 编辑 子模块
				addEditSonModName : function(opts,modId,maId,editThis){
					var _this = this,url='';
					var sonModChiNameInp = $.trim($('#sonModChiNameInp').val()),
		  			sonModEngNameInp = $.trim($('#sonModEngNameInp').val()),
		  			regCN = /^[\u4E00-\u9FA5]+$/,
					regEN =  /^[a-zA-Z]+$/;
					if(opts == 'addSonModOpt'){
						//增加子模块
						url = '/modM.do?action=addModAct';
						var field = {modId:modId,actNameChi:escape(sonModChiNameInp),actNameEng:sonModEngNameInp};
					}else{//编辑子模块
						url = '/modM.do?action=upModAct';
						var field = {maId:maId,actNameChi:escape(sonModChiNameInp),actNameEng:sonModEngNameInp};
					}
			  		if(sonModChiNameInp == ''){
			  			layer.msg('子模块中文名字不能为空', {icon:5,anim:6,time:1000});
			  		}else if(sonModEngNameInp == ''){
			  			layer.msg('子模块英文名字不能为空', {icon:5,anim:6,time:1000});
			  		}else{
			  			if(!regCN.test(sonModChiNameInp)){
			  				layer.msg('子模块中文名字应为汉字', {icon:5,anim:6,time:1000});
			  			}else if(!regEN.test(sonModEngNameInp)){
			  				layer.msg('子模块英文名字应为英语', {icon:5,anim:6,time:1000});
			  			}else{
   			  			$.ajax({
      	  						type:"post",
      					        async:false,
      					        dataType:"json",
      					        data:field,
      					        url:url,
      					        success:function (json){
      					        	if(json["result"] == "success"){
      					        		if(opts == 'addSonModOpt'){
      					        			layer.msg("添加子模块成功",{icon:1,time:1000},function(){
    	      					        		_this.loadModuleList();
    					        				_this.bindEvent_multi();
    					        				layer.close(_this.data.globalIndex);
    					        				_this.data.globalIndex = 0;
    	    				        		});
  					        			}else{
  					        				layer.msg("编辑子模块成功",{icon:1,time:1000},function(){
  		      					        		/*_this.loadModuleList();
  						        				_this.bindEvent_multi();
  						        				_this.data.globalIndex = 0;
  						        				layer.close(index);*/
  						        				editThis.prev().html(sonModChiNameInp + '(' + sonModEngNameInp + ')');
  				   			        			editThis.attr('actNameEng',sonModEngNameInp);
  				   			        			layer.close(_this.data.globalIndex);
  				   			        			_this.data.globalIndex = 0;
  		    				        		});
  					        			}
      					        		
      					        	}else if(json["result"] == "exist"){
      					        		layer.msg("该子模块已存在，请重新填写", {icon:5,anim:6,time:1000});
      					        	}else if(json["result"] == "error"){
      					        		layer.msg("系统错误，请重试", {icon:5,anim:6,time:1000});
      					        	}else if(json["result"] == "noAbility"){
      					        		layer.msg("对不起，您暂无权限添加/编辑子模块", {icon:5,anim:6,time:1000});
      					        	}
      					        }
      	  					});
			  			}
			  		}
				}
			};
			//全选动作、左侧主模块选择选动作、右侧所有子模块选择动作（页面初始化就加载一次，然后每次选择身份后会加载一次）
			function inpCheckboxSel(){
				//全选
				$('#selAllModInp').click(function(){
		        	var checkStatus = this.checked;
					$('.comModInp').each(function(i){
						var canUseFlag = $('.comModInp').eq(i).attr('canUseFlag');
						if(canUseFlag == 'true'){
							this.checked = checkStatus;
							if(checkStatus){
								$(this).prev().addClass('hasActive');
								if($(this).attr('actNameEng') == 'list'){
									$(this).attr('disabled','disabled');
								}
							}else{
			            		$(this).prev().removeClass('hasActive');
			            		if($(this).attr('actNameEng') == 'list'){
									$(this).removeAttr('disabled');
								}
			            	}
						}
					});
		            if(checkStatus){
	            		$(this).prev().addClass('hasActive');
	            	}else{
	            		$(this).prev().removeClass('hasActive');
	            	}
		        });
				//左侧主模块动作
		    	$('.singleRowSelInp').each(function(i){
					var canUseFlag = $('.singleRowSelInp').eq(i).attr('canUseFlag');
					var aInp = $('input[name=singleRowSelAllMod_'+ canUseFlag +']').eq(i).parent().parent().find('.sonModWid input');
					if(canUseFlag == 'true'){
						$(this).click(function(){
							var rowMainModId = $(this).attr('rowMainModId');
							var totalLen = $('input[name=singleRowSelAllMod_'+ canUseFlag +']').length;
			    			var hasCheckedLen = $('input[name=singleRowSelAllMod_'+ canUseFlag +']:checked').length;
			    			var checkStatus = this.checked;
			    			aInp.prop('checked',checkStatus);
							if(checkStatus){
								$(this).prev().addClass('hasActive');
								aInp.prev().addClass('hasActive');
								if($('.listSonMod_'+rowMainModId).length > 0){
									$('.listSonMod_'+rowMainModId).attr('disabled','disabled');
								}
							}else{
								$(this).prev().removeClass('hasActive');
								aInp.prev().removeClass('hasActive');
								if($('.listSonMod_'+rowMainModId).length > 0){
									$('.listSonMod_'+rowMainModId).removeAttr('disabled');
								}
							}
							if(hasCheckedLen == totalLen){
			    				$('#selAllModInp').prop('checked',true).prev().addClass('hasActive');
			    			}else{
			    				$('#selAllModInp').prop('checked',false).prev().removeClass('hasActive');
			    			}
						});
					}else{
						//useFlag为false时给左侧主模块增加disabled 左侧所有子模块增加disabled 给主模块父级增加灰色
						$('.singleRowSelInp').eq(i).attr('disabled',true);
					}

				});
				//右侧所有子模块动作
		    	$('.sonModSelInp').each(function(i){
		    		var canUseFlag = $('.sonModSelInp').eq(i).attr('canUseFlag');
		    		if(canUseFlag == 'true'){
			    		$(this).click(function(){
			    			var parent = $(this).parent().parent();
			    			var parInp = parent.parent().find('.selAllModWid input');
			    			var totalRowLen = parent.find('p').length;
			    			var hasCheckedLen_row = parent.find($('input[name=sonInpCheck_'+ canUseFlag +']:checked')).length;
			    			var totalLen = $('input[name=sonInpCheck_'+ canUseFlag +']').length;
			    			var hasCheckedLen = $('input[name=sonInpCheck_'+ canUseFlag +']:checked').length;
			    			var checkStatus = this.checked;
			    			if(checkStatus){
			            		$(this).prev().addClass('hasActive');
			            	}else{
			            		$(this).prev().removeClass('hasActive');
			            	}
			    			if(hasCheckedLen_row == totalRowLen){
			    				parInp.prop('checked',true);
			    				parInp.prev().addClass('hasActive');
			    			}else{
			    				parInp.prop('checked',false);
			    				parInp.prev().removeClass('hasActive');
			    			}
			    			if(hasCheckedLen == totalLen){
			    				$('#selAllModInp').prop('checked',true).prev().addClass('hasActive');
			    			}else{
			    				$('#selAllModInp').prop('checked',false).prev().removeClass('hasActive');
			    			}
			    		});
		    		}else{
		    			$('.sonModSelInp').eq(i).attr('disabled',true).css({'cursor':'default'});
		    			$('.sonModSelInp').eq(i).parent().parent().parent().addClass('disabledBg');
		    		}
		    	});
			}
			//管理员身份下选择角色
			form.on('select(roleListSel)', function(data){
				var value = data.value;
				value == '' ? $('#roleIdInp').val(0) : $('#roleIdInp').val(value);
				page.loadMainModuleList();
			});
			//监听添加主模块，选择模块等级事件
			form.on('radio(modLevelFilter)', function(data){
				$('#mainModLevelInp').val(data.value);
			}); 
			//监听添加主模块选择是否可见事件
			form.on('radio(modShowFilter)', function(data){
				$('#mainModShowInp').val(data.value);
			});
			
			//加载模块列表list
			function getModuleList(modList,allBindFlag,endFlag){
				var strHtml = '';
				//渲染模拟表格头部
				if(loginType == 'spUser'){
					strHtml += '<ul id="modTit" class="noSelAll modListTit clearfix">';
					//序号
					strHtml += '<li class="orderNumWid">序号</li>';
				}else if(loginType = 'cpyUser'){
					strHtml += '<ul id="modTit" class="hasSelAll modListTit clearfix">';
					strHtml +='<li class="selAllModWid" style="height:40px;">';
					if(allBindFlag && $('#roleIdInp').val() != 0 && endFlag){//表示当前选择的某个身份下的所有子模块全部选中后全选呈现的状态
						strHtml += '<span class="likeCheckSpan hasActive" style="margin:11px auto 0;"><b class="layui-icon layui-icon-ok" style="margin-left:1px;"></b></span>';
						strHtml +='<input type="checkbox" id="selAllModInp" class="inpRadCheck" checked/></li>';
					}else{
						strHtml += '<span class="likeCheckSpan" style="margin:11px auto 0;"><b class="layui-icon layui-icon-ok" style="margin-left:1px;"></b></span>';
						strHtml +='<input type="checkbox" id="selAllModInp" class="inpRadCheck"/></li>';
					}
				}
				strHtml += '<li class="mainModWid">主模块名</li><li class="modLevelWid">模块级别</li><li class="sonModWid">子模块名</li></ul>';
				//模拟表格内容
				if(loginType == 'spUser'){
					strHtml += '<div id="listModCon" class="noSelAll">';
				}else if(loginType = 'cpyUser'){
					strHtml += '<div id="listModCon" class="hasSelAll">';
				}
				for(var i=0;i<modList.length;i++){
					page.data.globalMainModId.push(modList[i].modId);
					strHtml += '<ul class="clearfix">';
					if(loginType == 'spUser'){//增加序号
						if(modList[i].modLevel == 0){
							strHtml += '<li class="orderNumWid hasPosAbso posL tongpaiColor flexBox">'+ modList[i].orderNo +'</li>';
						}else if(modList[i].modLevel == 1){
							strHtml += '<li class="orderNumWid hasPosAbso posL yinpaiColor flexBox">'+ modList[i].orderNo +'</li>';
						}else if(modList[i].modLevel == 2){
							strHtml += '<li class="orderNumWid hasPosAbso posL jinpaiColor flexBox">'+ modList[i].orderNo +'</li>';
						}else if(modList[i].modLevel == 3){
							strHtml += '<li class="orderNumWid hasPosAbso posL zuanshiColor flexBox">'+ modList[i].orderNo +'</li>';
						}
					}else if(loginType == 'cpyUser'){//当为代理机构管理员的时候增加全选功能
						if(modList[i].useFlag){//右侧主模块
							if(modList[i].mainBindFlag && $('#roleIdInp').val() != 0){
								strHtml += '<li class="selAllModWid hasPosAbso posL flexBox">';
								strHtml += '<span class="likeCheckSpan hasActive"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" rowMainModId="'+ modList[i].modId +'" value="'+ i +'" name="singleRowSelAllMod_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="singleRowSelInp inpRadCheck comModInp" checked/></li>';
							}else{
								strHtml += '<li class="selAllModWid hasPosAbso posL flexBox">';
								strHtml += '<span class="likeCheckSpan"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" rowMainModId="'+ modList[i].modId +'" value="'+ i +'" name="singleRowSelAllMod_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="singleRowSelInp inpRadCheck comModInp" ></li>';
							}
						}else{
							if(modList[i].mainBindFlag && $('#roleIdInp').val() != 0){
								strHtml += '<li class="selAllModWid hasPosAbso posL flexBox">';
								strHtml += '<span class="likeCheckSpan hasActive"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" rowMainModId="'+ modList[i].modId +'" value="'+ i +'" name="singleRowSelAllMod_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="singleRowSelInp inpRadCheck comModInp" checked disabled/></li>';
							}else{
								strHtml += '<li class="selAllModWid hasPosAbso posL flexBox">';
								strHtml += '<span class="likeCheckSpan"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" rowMainModId="'+ modList[i].modId +'" value="'+ i +'" name="singleRowSelAllMod_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="singleRowSelInp inpRadCheck comModInp" disabled/></li>';
							}
						}
					}
					//主模块名字
					strHtml += '<li class="mainModWid hasAlign hasPosAbso posL1"><span class="modMainNameSpan flexBox"><b class="ellip">'+ modList[i].modName +'</b></span>';
					if(loginType == 'spUser'){//当为平台用户的时候进行编辑和删除
						strHtml += '<div class="setMainMod posAbs"><i class="layui-icon layui-icon-add-1 addSonModBtn" modId="'+ modList[i].modId +'"></i><i class="layui-icon layui-icon-edit editMainModName" opts="editBtn" modId="'+ modList[i].modId +'"></i>';
						strHtml += '<i class="layui-icon layui-icon-delete delMainModName" modId="'+ modList[i].modId +'"></i></div>';
					}
					strHtml += '</li>';
					//模块等级
					strHtml += '<li class="modLevelWid hasPosAbso posL2 flexBox">';
					if(modList[i].modLevel == 0){//铜牌
						strHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-tongpai-N"></use></svg><span class="fl">铜牌</span>';
					}else if(modList[i].modLevel == 1){//银牌
						strHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-yinpai-N"></use></svg><span class="fl">银牌</span>';
					}else if(modList[i].modLevel == 2){//金牌
						strHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-jinpai-N"></use></svg><span class="fl">金牌</span>';
					}else if(modList[i].modLevel == 3){//钻石
						strHtml += '<svg class="icon-svg specZuan" aria-hidden="true"><use xlink:href="#icon-svg-zuanshi"></use></svg><span class="fl">钻石</span>';
					}
					strHtml += '</li>';
					//子模块名
					strHtml += '<li class="sonModWid sonModNames hasMargL">';
					for(var j=0;j<modList[i].modActInfo.length;j++){
						if(loginType == 'spUser'){
							strHtml += '<p class="hasTxtAlign">';
						}else{
							strHtml += '<p>';
						}
						if(loginType == 'spUser'){//没有checkbox
							strHtml += '<span>'+ modList[i].modActInfo[j].actNameChi+'('+modList[i].modActInfo[j].actNameEng +')</span>';
							strHtml += '<a href="javascript:void(0)" class="seSonMod posAbs editSonModName" maId="'+ modList[i].modActInfo[j].maId +'" actNameChi="'+ modList[i].modActInfo[j].actNameChi +'" actNameEng="'+ modList[i].modActInfo[j].actNameEng +'"><b title="编辑" class="editDelSpanBtn editSpanBtn"><i class="layui-icon layui-icon-edit"></i></b><b title="删除" class="editDelSpanBtn delSpanBtn"><i class="layui-icon layui-icon-delete"></i></b></a>';
						}else if(loginType == 'cpyUser'){//存在checkbox 左侧子模块
							//bindFlag 用于判断当前身份是否选中
							var actNameEng = modList[i].modActInfo[j].actNameEng.substring(0,4);
							var disabFlag = modList[i].modActInfo[j].disabledFlag;
							if(modList[i].modActInfo[j].bindFlag){
								strHtml += '<span class="likeCheckSpan hasActive"><b class="layui-icon layui-icon-ok"></b></span>';
								if(actNameEng == 'list'){
									if(disabFlag){
										strHtml += '<input type="checkbox" actNameEng="'+ actNameEng +'" onclick="checkStatusInp(this,'+ modList[i].modActInfo[j].modId +',\''+ actNameEng +'\')" name="sonInpCheck_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="sonModSelInp inpRadCheck comModInp  listSonMod_'+ modList[i].modActInfo[j].modId +'" value="'+ modList[i].modActInfo[j].maId +'" checked disabled/>';
									}else{
										strHtml += '<input type="checkbox" actNameEng="'+ actNameEng +'" onclick="checkStatusInp(this,'+ modList[i].modActInfo[j].modId +',\''+ actNameEng +'\')" name="sonInpCheck_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="sonModSelInp inpRadCheck comModInp  listSonMod_'+ modList[i].modActInfo[j].modId +'" value="'+ modList[i].modActInfo[j].maId +'" checked/>';
									}
								}else{
									strHtml += '<input type="checkbox" actNameEng="'+ actNameEng +'" onclick="checkStatusInp(this,'+ modList[i].modActInfo[j].modId +',\''+ actNameEng +'\')" name="sonInpCheck_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="sonModSelInp inpRadCheck comModInp otherSonMod_'+ modList[i].modActInfo[j].modId +'" value="'+ modList[i].modActInfo[j].maId +'" checked/>';	
								}
								strHtml += '<strong class="ellip">'+ modList[i].modActInfo[j].actNameChi +'</strong>';
							}else{
								strHtml += '<span class="likeCheckSpan"><b class="layui-icon layui-icon-ok"></b></span>';
								if(actNameEng == 'list'){
									strHtml += '<input type="checkbox" actNameEng="'+ actNameEng +'" onclick="checkStatusInp(this,'+ modList[i].modActInfo[j].modId +',\''+ actNameEng +'\')" name="sonInpCheck_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="sonModSelInp inpRadCheck comModInp listSonMod_'+ modList[i].modActInfo[j].modId +'" value="'+ modList[i].modActInfo[j].maId +'">';
								}else{
									strHtml += '<input type="checkbox" actNameEng="'+ actNameEng +'" onclick="checkStatusInp(this,'+ modList[i].modActInfo[j].modId +',\''+ actNameEng +'\')" name="sonInpCheck_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="sonModSelInp inpRadCheck comModInp otherSonMod_'+ modList[i].modActInfo[j].modId +'" value="'+ modList[i].modActInfo[j].maId +'">';	
								}
								strHtml += '<strong class="ellip">'+ modList[i].modActInfo[j].actNameChi+'</strong>';
							}
						}
						strHtml += '</p>';
					}
					strHtml += '</li>';
					strHtml += '</ul>';
				}
				strHtml += '</div>';
				$('#moduleList').html(strHtml);
				form.render();
				inpCheckboxSel();
				$('#listModCon').find('ul:odd').addClass('oddBg');
			}
			function showOutDateTips(){
				$('#outDateTip').click(function(){
					layer.tips('您的会员已到期，银牌以上的模块将不能使用，如需使用，请及时续费购买！','#outDateTip', {tips:[1,'#FF8000'],time:4000});
				});
			}
			//获取当前代理机构会员是否已到期状态
			function getCpyHyStatus(){
				var hyEndFlag = false;
				$.ajax({
   					type:"post",
   			        async:false,
   			        dataType:"json",
   			        url:'cpyManager.do?action=getCpyHyInfo',
   			        success:function (json){
   			        	hyEndFlag = json.hyEndFlag;
   			        }
   				});
				return hyEndFlag;
			}
			//过期提示
			function memberOutDateTip(){
				var hyEndFlag = getCpyHyStatus();
				if(hyEndFlag){//表示过期
					$('#outDateTips').html('您的会员已到期， 铜牌以上的模块将不能使用，如需使用，请及时续费购买！');
				}
			}
			$(function(){
				page.init();
				if(loginType == 'cpyUser'){
					memberOutDateTip();
				}
			});
		});
		function checkStatusInp(obj,modId,actNameEng){
			var checkStatus = $(obj).prop('checked');
			if(checkStatus){//选中
				if(actNameEng != 'list' && $('.listSonMod_' + modId).length > 0){
					//选择了当前主模块下的其他子模块 自动让list子模块选中并且disabled
					$('.listSonMod_' + modId).prop('checked',true);
					$('.listSonMod_' + modId).prev().addClass('hasActive');
					$('.listSonMod_' + modId).attr('disabled','disabled');
				}
			}else{//取消选中的时候
				var len = $('.otherSonMod_'+ modId +':checked').length;
				if(actNameEng == 'list'){
					$('.listSonMod_' + modId).prop('checked',false);
				}
				if(len == 0 && actNameEng != 'list' && $('.listSonMod_' + modId).length > 0){
					//检测当前除了list之外取消选中如果为0且存在list子模块 将list设置未选中但是去除disabled
					$('.listSonMod_' + modId).removeAttr('disabled');
				}
			}
		}
	</script>
  </body>
</html>
