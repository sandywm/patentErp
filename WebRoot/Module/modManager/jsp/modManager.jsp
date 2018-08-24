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
  						<a id="addMod" class="posAbs newAddBtn" opts="addBtn" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加模块</a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<input type="hidden" id="madIdListInp"/>
  						<div class="roleList clearfix">
  							<input id="roleIdInp" type="hidden" value="0"/>
  							<button class="layui-btn saveMod fr">保存</button>
  							<div id="roleLists" class="fr layui-form"></div>
  						</div>
  						<div id="moduleList" class="moduleList"></div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
    <script src="/plugins/layui/layui.js"></script>
	<script type="text/javascript">
		var roleName = parent.roleName,globalOpts = "addBtn",globalModId=0,madIdArray=[],roleList=[];
		layui.use(['layer','jquery','form'],function(){
			var layer = layui.layer,
				$ = layui.jquery,
				form = layui.form;
			var page = {
				data : {
					inpMainModName_cn : '',
					inpMainModName_eng : '',
					inpMainModUrl : '',
					inpMainModOrderNo : 0,
					mainModLevelInp : 0,
					mainModShowInp  : 0,
					globalIndex : 0
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
					if(roleName == "super"){
						$("#addMod").show();
					}else if(roleName == "管理员"){
						$(".roleList").show();
						this.loadRoleList(roleList);
					}
				},
				//获取模块列表
				loadMainModuleList : function(){
					var url = "",_this = this,
					selRoleId = $("#roleIdInp").val();
					if(roleName == "super"){
						url = "modM.do?action=getModuleDetail";
					}else if(roleName == "管理员"){
						url = "modM.do?action=getModuleDetail&selRoleId=" + selRoleId;
					}
					layer.load('1');
					$.ajax({
	   					type:"post",
	   			        async:false,
	   			        dataType:"json",
	   			        url:url,
	   			        success:function (json){
	   			        	console.log(json)
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
					});
					//增加模块
					$('#addMod').on('click',function(){
						if(roleName == 'super'){
							globalOpts = $(this).attr("opts");
        					var addEditMainModCon = '';
        					addEditMainModCon += '<div class="addEditMainModWrap layui-form">';
        					addEditMainModCon += '<div class="comAddEditDiv"><span class="fl">模块中文名字：</span><input id="inpMainModName_cn" type="text" placeholder="请输入模块中文名字(15字以内)" maxlength="15"></div>';
        					addEditMainModCon += '<div class="comAddEditDiv"><span class="fl">模块英文名字：</span><input id="inpMainModName_eng" type="text" placeholder="请输入模块英文名字"></div>';
        					addEditMainModCon += '<div class="comAddEditDiv"><span class="margLSpan_url fl">模块动作Url：</span><input id="inpMainModUrl" type="text" placeholder="请输入模块动作url"></div>';
        					addEditMainModCon += '<div class="comAddEditDiv"><span class="margLSpan_order fl">模块序列号：</span><input id="inpMainModOrderNo" type="text" placeholder="模块排列序列号(大于0的正整数)"></div>';
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
    	        				area : ['500px','390px'],
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
	        				area : ['500px','390px'],
	        				btn : ['确定','取消'],
	        			  	btnAlign:'c',
	        				content: '/Module/modManager/jsp/editMainModName.html',
	        				yes : function(index, layero){
	        					var body = layer.getChildFrame('body', index);
	        					if(body.find('#isHasData').val() == 1){//表示有数据
	        						_this.data.inpMainModName_cn = body.find('#inpMainModName_cn').val();
		        					_this.data.inpMainModName_eng = body.find('#inpMainModName_eng').val();
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
					//编辑子模块内容
					$('.editSonModName').on('click',function(){
						var maId = $(this).attr('maId'),
							actNameChi = $(this).attr('actNameChi'),actNameEng = $(this).attr('actNameEng'),editThis = $(this);
						var editSonModCon = '';
						editSonModCon += '<div class="editSonModNameCon">';
						editSonModCon += '<div class="comEditDiv"><span class="fl">子模块中文名：</span><input type="text" value="'+ actNameChi +'" disabled></div>';
						editSonModCon += '<div class="comEditDiv"><span class="fl">子模块英文名：</span><input id="sonModEngName" type="text" placeholder="英文名须以add,del,list,up开头" maxlength="20"></div>';
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
   	        			  		var sonModEngName = $('#sonModEngName').val();
   	        			  		_this.editSonModName(maId,actNameChi,sonModEngName,editThis);
   	        				}
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
       					        url:"modM.do?action=delModule",
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
						inpMainModName_eng = globalOpts == 'addBtn' ? $.trim($('#inpMainModName_eng').val()) : _this.data.inpMainModName_eng,
						inpMainModUrl = globalOpts == 'addBtn' ? $.trim($('#inpMainModUrl').val()) : _this.data.inpMainModUrl,
						inpMainModOrderNo = globalOpts == 'addBtn' ? $.trim($('#inpMainModOrderNo').val()) : _this.data.inpMainModOrderNo,
						mainModLevelInp = globalOpts == 'addBtn' ? $('#mainModLevelInp').val() : _this.data.mainModLevelInp,
						mainModShowInp = globalOpts == 'addBtn' ? $('#mainModShowInp').val() : _this.data.mainModShowInp,
						regCN = /^[\u4E00-\u9FA5]+$/,
						regEN =  /^[a-zA-Z]+$/,
						regNum = /^[1-9]\d*$/;
					if(inpMainModName_cn == ''){
						layer.msg('模块中文名字不能为空', {icon:5,anim:6,time:1000});
					}else if(inpMainModName_eng == ''){
						layer.msg('模块英文名字不能为空', {icon:5,anim:6,time:1000});
					}else if(inpMainModUrl == ''){
						layer.msg('模块动作Url不能为空', {icon:5,anim:6,time:1000});
					}else if(inpMainModOrderNo == ''){
						layer.msg('模块序列号不能为空', {icon:5,anim:6,time:1000});
					}else{
						if(!regCN.test(inpMainModName_cn)){
							layer.msg('模块中文名字应为汉字', {icon:5,anim:6,time:1000});
						}else if(!regEN.test(inpMainModName_eng)){
							layer.msg('模块英文名字应为英语', {icon:5,anim:6,time:1000});
						}else if(regCN.test(inpMainModUrl)){
							layer.msg('模块动作Url不能含有汉字', {icon:5,anim:6,time:1000});
						}else if(!regNum.test(inpMainModOrderNo)){
							layer.msg('模块序列号应为大于0的正整数', {icon:5,anim:6,time:1000});
						}else{
							//执行ajax
							var url = '';
							if(globalOpts == 'addBtn'){//表示增加主模块
								url = 'modM.do?action=addModule';
								var field = {modName:inpMainModName_cn,actNameEng:inpMainModName_eng,resUrl:inpMainModUrl,orderNo:inpMainModOrderNo,modLevel:mainModLevelInp,showStatus:mainModShowInp};
							}else if(globalOpts == 'editBtn'){//表示编辑主模块
								url = 'modM.do?action=upModule';
								var field = {modId : modId,modName:inpMainModName_cn,actNameEng:inpMainModName_eng,resUrl:inpMainModUrl,orderNo:inpMainModOrderNo,modLevel:mainModLevelInp,showStatus:mainModShowInp};;
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
	    				        		if(globalOpts == "addBtn"){//表示增加主模块
	    				        			layer.msg("添加成功",{icon:1,time:1000},function(){
	    				        				_this.loadModuleList();
		    				        			_this.bindEvent_multi();
		    				        			layer.close(_this.data.globalIndex);
		    				        			_this.data.globalIndex = 0;
		    				        		});
	    				        		}else if(globalOpts == "editBtn"){//表示编辑主模块
	    				        			layer.msg("编辑成功",{icon:1,time:1000},function(){
	    				        				_this.loadModuleList();
		    				        			_this.bindEvent_multi();
		    				        			layer.close(_this.data.globalIndex);
		    				        			_this.data.globalIndex = 0;
		    				        		});
	    				        		}
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
				//编辑子模块名字
				editSonModName : function(maId,actNameChi,sonModEngName,editThis){
					var _this = this;
					if(sonModEngName == ''){
						layer.msg('子模块英文名不能为空', {icon:5,anim:6,time:1000});
					}else{
						var reg = /^[a-zA-Z]+$/;
						if(!reg.test(sonModEngName)){
							layer.msg('子模块必须是英文,(须以add,del,list,up开头)', {icon:5,anim:6,time:1000});
						}else{
							layer.load("1");
							$.ajax({
			   					type:"post",
			   			        async:false,
			   			        dataType:"json",
			   			        data:{maId:maId,actNameChi:escape(actNameChi),actNameEng:sonModEngName},
			   			        url:"modM.do?action=upModAct",
			   			        success:function (json){
			   			        	layer.closeAll("loading");
			   			        	if(json["result"] == "success"){
			   			        		layer.msg("修改成功",{icon:1,time:1000},function(){
			   			        			editThis.prev().html(actNameChi + '(' + sonModEngName + ')');
			   			        			editThis.attr('actNameEng',sonModEngName);
			   			        			layer.close(_this.data.globalIndex);
			   			        			_this.data.globalIndex = 0;
			   			        		});
			   			        	}else if(json["result"] == "exist"){
			   			        		layer.msg("该模块名字已存在，请重新输入", {icon:5,anim:6,time:1000});
			   			        	}else if(json["result"] == "noAbility"){
			   			        		layer.msg("抱歉，您暂无权限修改子模块", {icon:5,anim:6,time:1000});
			   			        	}else if(json["result"] == "startError"){
			   			        		layer.msg("英文名字必须以add,del,list,up开头", {icon:5,anim:6,time:1000});
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
							}else{
			            		$(this).prev().removeClass('hasActive');
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
							var totalLen = $('input[name=singleRowSelAllMod_'+ canUseFlag +']').length;
			    			var hasCheckedLen = $('input[name=singleRowSelAllMod_'+ canUseFlag +']:checked').length;
			    			var checkStatus = this.checked;
			    			aInp.prop('checked',checkStatus);
							if(checkStatus){
								$(this).prev().addClass('hasActive');
								aInp.prev().addClass('hasActive');
							}else{
								$(this).prev().removeClass('hasActive');
								aInp.prev().removeClass('hasActive');
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
						aInp.attr('disabled',true);
						aInp.parent().parent().parent().addClass('disabledBg');
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
		    		}/*else{
		    			$('.sonModSelInp').eq(i).attr('disabled',true);
		    		}*/
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
				console.log(modList)
				var strHtml = '';
				//渲染模拟表格头部
				if(roleName == 'super'){
					strHtml += '<ul id="modTit" class="noSelAll modListTit clearfix">';
				}else if(roleName = '管理员'){
					strHtml += '<ul id="modTit" class="hasSelAll modListTit clearfix">';
					strHtml +='<li class="selAllModWid">';
					if(allBindFlag && $('#roleIdInp').val() != 0 && endFlag){//表示当前选择的某个身份下的所有子模块全部选中后全选呈现的状态
						strHtml += '<span class="likeCheckSpan hasActive"><b class="layui-icon layui-icon-ok" style="margin-left:1px;"></b></span>';
						strHtml +='<input type="checkbox" id="selAllModInp" class="inpRadCheck" checked/></li>';
					}else{
						strHtml += '<span class="likeCheckSpan"><b class="layui-icon layui-icon-ok" style="margin-left:1px;"></b></span>';
						strHtml +='<input type="checkbox" id="selAllModInp" class="inpRadCheck"/></li>';
					}
				}
				strHtml += '<li class="mainModWid">主模块名</li><li class="modLevelWid">模块级别</li><li class="sonModWid">子模块名</li></ul>';
				//模拟表格内容
				if(roleName == 'super'){
					strHtml += '<div id="listModCon" class="noSelAll">';
				}else if(roleName = '管理员'){
					strHtml += '<div id="listModCon" class="hasSelAll">';
				}
				for(var i=0;i<modList.length;i++){
					strHtml += '<ul class="clearfix">';
					if(roleName == '管理员'){//当为代理机构管理员的时候增加全选功能
						if(modList[i].useFlag){//右侧主模块
							if(modList[i].mainBindFlag && $('#roleIdInp').val() != 0){
								strHtml += '<li class="selAllModWid">';
								strHtml += '<span class="likeCheckSpan hasActive"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" value="'+ i +'" name="singleRowSelAllMod_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="singleRowSelInp inpRadCheck comModInp" checked/></li>';
							}else{
								strHtml += '<li class="selAllModWid">';
								strHtml += '<span class="likeCheckSpan"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" value="'+ i +'" name="singleRowSelAllMod_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="singleRowSelInp inpRadCheck comModInp" ></li>';
							}
						}else{
							if(modList[i].mainBindFlag && $('#roleIdInp').val() != 0){
								strHtml += '<li class="selAllModWid">';
								strHtml += '<span class="likeCheckSpan hasActive"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" value="'+ i +'" name="singleRowSelAllMod_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="singleRowSelInp inpRadCheck comModInp" checked disabled/></li>';
							}else{
								strHtml += '<li class="selAllModWid">';
								strHtml += '<span class="likeCheckSpan"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" value="'+ i +'" name="singleRowSelAllMod_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="singleRowSelInp inpRadCheck comModInp" disabled/></li>';
							}
						}
					}
					//主模块名字
					strHtml += '<li class="mainModWid posRel hasAlign"><span class="modMainNameSpan ellip">'+ modList[i].modName +'</span>';
					if(roleName == 'super'){//当为平台用户的时候进行编辑和删除
						strHtml += '<div class="setMainMod posAbs"><i class="layui-icon layui-icon-edit editMainModName" opts="editBtn" modId="'+ modList[i].modId +'"></i>';
						strHtml += '<i class="layui-icon layui-icon-delete delMainModName" modId="'+ modList[i].modId +'"></i></div>';
					}
					strHtml += '</li>';
					//模块等级
					strHtml += '<li class="modLevelWid">';
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
					strHtml += '<li class="sonModWid sonModNames">';
					for(var j=0;j<modList[i].modActInfo.length;j++){
						strHtml += '<p>';
						if(roleName == 'super'){//没有checkbox
							strHtml += '<span>'+ modList[i].modActInfo[j].actNameChi+'('+modList[i].modActInfo[j].actNameEng +')</span>';
							strHtml += '<a href="javascript:void(0)" class="seSonMod posAbs editSonModName" maId="'+ modList[i].modActInfo[j].maId +'" actNameChi="'+ modList[i].modActInfo[j].actNameChi +'" actNameEng="'+ modList[i].modActInfo[j].actNameEng +'"><i class="layui-icon layui-icon-edit"></i></a>';
						}else if(roleName == '管理员'){//存在checkbox 左侧子模块
							if(modList[i].modActInfo[j].bindFlag){
								strHtml += '<span class="likeCheckSpan hasActive"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" name="sonInpCheck_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="sonModSelInp inpRadCheck comModInp" value="'+ modList[i].modActInfo[j].maId +'" checked/>';
								strHtml += '<strong class="ellip">'+ modList[i].modActInfo[j].actNameChi+'('+modList[i].modActInfo[j].actNameEng +')</strong>';
							}else{
								strHtml += '<span class="likeCheckSpan"><b class="layui-icon layui-icon-ok"></b></span>';
								strHtml += '<input type="checkbox" name="sonInpCheck_'+ modList[i].useFlag +'" canUseFlag="'+ modList[i].useFlag +'" class="sonModSelInp inpRadCheck comModInp" value="'+ modList[i].modActInfo[j].maId +'">';
								strHtml += '<strong class="ellip">'+ modList[i].modActInfo[j].actNameChi+'('+modList[i].modActInfo[j].actNameEng +')</strong>';
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
			}
			$(function(){
				page.init();
			});
		});
	</script>
  </body>
</html>
