<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>代理机构员工管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="keywords" content="专利管理系统,代理机构员工管理">
	<meta http-equiv="description" content="专利管理系统代理机构员工管理">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/staffManager/css/staffManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
  </head>
  <body>
  	<body>
	    <div class="layui-fluid" style="margin-top:15px;">
	  		<div class="layui-row">
	  			<div class="layui-col-md12 layui-col-lg12">
	  				<div class="layui-card">
	  					<div class="layui-card-header posRel">
	  						<span>代理机构员工管理</span>
	  						<a id="addRole" class="posAbs newAddBtn" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加员工</a>
	  					</div>
	  					<div class="layui-card-body" pad15>
	  						<!-- 查询条件 -->
		  					<div class="layui-form searchForm clearfix">
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input id="roleIdInp" type="hidden" value="-1"/>
		  								 <select id="roleListSel" lay-filter="roleListSel">
		  								 	<option value="">请选择角色列表</option>
		  								 </select> 
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input id="accStatusInp" type="hidden" value="-1"/>
		  								<select id="accStatusSel" lay-filter="accStatusSel">
									       	<option value="">请选择账号状态</option>
									        <option value="0">无效</option>
							    			<option value="1">有效</option>
									      </select>
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input id="lzStatusInp" type="hidden" value="-1"/>
		  								<select id="lzStatusSel" lay-filter="lzStatusSel">
									       	<option value="">请选择离职状态</option>
									         <option value="0">离职</option>
							    			<option value="1">在职</option>
									      </select>
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="text" id="userPyInp" placeholder="请输入用户拼音首字母" autocomplete="off" class="layui-input">
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search
	 "></i></button>
		  							</div>
		  						</div>
		  					</div>
	  						<div id="staffList">
	  							<div class='noData'></div>
	  							<table id="staffListTable" class="layui-table" lay-filter="staffTable"></table>
								<!-- 设置中账号有效状态  -->
								<script type="text/html" id="isAccYxTpl">
									{{#  if(d.selfFlag != true){ }}
										<input type="hidden" value="{{ d.userId }}"/>
										<input type="checkbox" name="yxStatus" value="{{d.yxStatus}}" lay-skin="switch" lay-text="有效|无效" lay-filter="isAccYxSwitch" {{ d.yxStatus == '有效' ? 'checked' : '' }}/>
									{{#  } }} 
								</script>
								<!-- 设置离职状态  -->
								<script type="text/html" id="isLzTpl">
									{{#  if(d.selfFlag != true){ }}
										<input type="checkbox" name="lzStatus" userId="{{ d.userId }}" inDate="{{ d.inDate }}" value="{{d.lzStatus}}" lay-skin="switch" lay-text="在职|离职" lay-filter="isLzSwitch" {{ d.lzStatus == '在职' ? 'checked' : '' }}/>
									{{#  } }}
								</script>
								<script type="text/html" id="sexTpl">
  									{{#  if(d.sex === 'f'){ }}
    									<span style="color: #F581B1;">{{ d.sex = "女" }}</span>
  									{{#  } else { }}
    									{{ d.sex = "男" }}
  									{{#  } }}
	</script>
	  						</div>
	  					</div>
	  				</div>
	  			</div>
	  		</div>
	  	</div>
	  	<script src="/plugins/layui/layui.js"></script>
	  	<script type="text/javascript">
		  	var delFlag = "${ requestScope.delFlag }",
				upFlag = "${ requestScope.upFlag }",
				addFlag = "${ requestScope.addFlag }",
				loadFlag = false,
				upRoleList = [],upRoleIndex = 0,upRoleListName=[];
			layui.use(['layer','jquery','table','form','laydate'],function(){
				var layer = layui.layer,
					$ = layui.jquery,
					table = layui.table,
					form = layui.form,
					laydate = layui.laydate;
				var page = {
					init : function(){
						this.onLoad();
						this.bindEvent();
					},
					bindEvent : function(){
						var _this = this;
						$("#addRole").on("click",function(){
							if(addFlag == "true"){
								layer.open({
									title:'添加员工',
									type: 2,
								  	area: ['700px', '500px'],
								  	fixed: false, //不固定
								  	maxmin: true,
								  	shadeClose :false,
								  	content: "/Module/staffManager/jsp/addStaffInfo.html",
								  	end:function(){
								  		if(loadFlag){
								  			loadQueryStaffList('loadListData');
								  		}
								  	}
								});	
							}else{
								layer.msg("抱歉，您暂无权限添加员工",{icon:5,anim:6,time:1000});
							}
						});
					},
					onLoad : function(){
						this.getRoleList();
					},
					//获取用户角色列表
					getRoleList : function(){
						//获取用户角色列表
						layer.load("1");
						$.ajax({
	    					type:"post",
	    			        async:false,
	    			        dataType:"json",
	    			        url:"role.do?action=getRoleList",
	    			        success:function (json){
	    			        	layer.closeAll("loading");	
	    			        	var roleList = json.roleList;
	    			        	loadRoleList(roleList);
	    			        }
	    				});
					}
				};
				$('#queryBtn').on('click',function(){
					loadQueryStaffList('queryLoad');
				});
				//load角色列表
				function loadRoleList(roleList){
					var strHtml = '';
					for(var i=0;i<roleList.length;i++){
						strHtml += '<option value="'+ roleList[i].id +'">'+ roleList[i].roleName +'</option>';
					}
					$('#roleListSel').append(strHtml);
					form.render();
				}
				form.on('select(roleListSel)', function(data){
					var value = data.value;
					value == '' ? $('#roleIdInp').val(-1) : $('#roleIdInp').val(value);
				});
				form.on('select(accStatusSel)', function(data){
					var value = data.value;
					value == '' ? $('#accStatusInp').val(-1) : $('#accStatusInp').val(value);
				});
				form.on('select(lzStatusSel)', function(data){
					var value = data.value;
					value == '' ? $('#lzStatusInp').val(-1) : $('#lzStatusInp').val(value);
				});
				//获取员工信息list
				function loadQueryStaffList(opts){
					if(opts == "loadListData"){
						var field = {userLzStatus : -1,userYxStatus : -1,roleId : -1,userNamePy : ""};
					}else{
						var roleId = $("#roleIdInp").val(),
						userLzStatus = $("#lzStatusInp").val(),
						userYxStatus = $("#accStatusInp").val(),
						userNamePy = $.trim($("#userPyInp").val());
						var field = {userLzStatus : userLzStatus ,userYxStatus : userYxStatus , roleId : roleId, userNamePy : userNamePy};
					}
					layer.load("1");
					table.render({
						elem: '#staffListTable',
						height: 'full-200',
						url : "user.do?action=getCpyUserPageInfo",
						method : "post",
						where:field,
						page : true,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						text: {none : '暂无相关数据'},
						cols : [[
							{field : 'userId', title: 'ID', width:60, fixed: 'left' , align:'center'},
							{field : 'name', title: '姓名', width:80 , align:'center',fixed: 'left' },
							{field : 'account', title: '用户名', width:80 , align:'center',style:'color:#01AAED;'},
							{field : 'namePy', title: '用户拼音', width:100 , align:'center'},
							{field : 'roleName', title: '用户身份', width:150 , align:'center',style:'background-color: #eee;'},
							{field : 'sex', title: '性别', width:60 , align:'center',templet: '#sexTpl'},
							{field : 'scFiledName', title: '擅长领域', width:150 , align:'center',style:'background-color: #eee;'},
							{field : 'inDate', title: '入职时间', width:120 , align:'center'},
							{field : 'yxStatus', title: '账号状态', width:100 , align:'center', templet: '#isAccYxTpl'},
							{field : 'lzStatus', title: '离职状态', width:100 , align:'center',templet: '#isLzTpl'},
							{field : 'outDate', title: '离职时间', width:120 , align:'center'},
							{field : '', title: '操作', width:265 , fixed: 'right', align:'center',templet : function(d){
								if(d.selfFlag != true){
									return '<a class="layui-btn layui-btn-primary layui-btn-xs viewDetailInfoBtn" lay-event="viewInfo" name="'+ d.name +'" userId="'+ d.userId +'"><i class="layui-icon layui-icon-search"></i>查看</a> <a class="layui-btn layui-btn-xs editInfoBtns" lay-event="updateInfo" name="'+ d.name +'"  userId="'+ d.userId +'"><i class="layui-icon layui-icon-edit"></i>编辑身份</a> <a class="layui-btn layui-btn-danger layui-btn-xs resetPassword" lay-event="resetPass" name="'+ d.name +'" userId="'+ d.userId +'"><i class="layui-icon layui-icon-refresh"></i>重置密码</a>';	
								}else{
									return '';
								}
							}},
						]],
						done : function(res, curr, count){
							console.log(res.msg);
							layer.closeAll("loading");
							if(res.msg == "success"){
								$(".noData").hide().html("");
    			        		$(".layui-table-view").show();
    			        	}else if(res.msg == "noInfo"){
    			        		$(".layui-table-view").hide();
    			        		$(".noData").show();
    			        		if(opts == "loadListData"){
    			        			$(".noData").html("<i class='iconfont layui-extend-noData'></i><p>暂无员工，请添加</p>");
    			        		}else{
    			        			$(".noData").html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
    			        		}
							}else if(res.msg == "fail"){
    			        		layer.msg("加载失败，请刷新重试", {icon:5,anim:6,time:1000});
    			        	}
						}
					});
				}
				//加载编辑员工身份列表
				function loadEditRoleList(userId,name,roleList,obj){
					var strHtml = '';
					strHtml += '<div class="layui-form autoDiv"><div class="layui-form-item"><div class="layui-input-block" style="margin-left:0px;">';
					for(var i=0;i<roleList.length;i++){
	        			if(roleList[i].checked){
	        				strHtml += '<input type="checkbox" class="roleNames" lay-filter="roleListInp" value="'+ roleList[i].roleId +'" title="'+ roleList[i].roleName +'" checked lay-skin="primary">';
	        				upRoleList.push(roleList[i].roleId);
	        				upRoleListName.push(roleList[i].roleName);
	        			}else{
	        				strHtml += '<input type="checkbox" class="roleNames" lay-filter="roleListInp" value="'+ roleList[i].roleId +'" title="'+ roleList[i].roleName +'" lay-skin="primary">';
	        			}
	        		}
					strHtml += '<input type="hidden" name="hasSelRoleIdInp" value="'+ upRoleList.join(',')  +'"/>';
					strHtml += '<input type="hidden" name="hasSelRoleNameInp" value="'+ upRoleListName.join(',')  +'"/>';
					strHtml += '</div></div></div>';
	        		upRoleIndex = layer.open({
						title :'员工' +  name + '的身份编辑',
						skin:'layui-layer-molv',
						type : 1, 
						content:strHtml, 
						area : ['500px','235px'],
						btn : ['保存','取消'],
						yes: function(index, layero){
							updateUserRole(userId,obj);
						},
						btn2:function(index, layero){
							upRoleList.length = 0;
							upRoleListName.length = 0;
							$('input[name=hasSelRoleIdInp]').val('');
							$('input[name=hasSelRoleNameInp]').val('');
						},
						cancel : function(){
							upRoleList.length = 0;
							upRoleListName.length = 0;
							$('input[name=hasSelRoleIdInp]').val('');
							$('input[name=hasSelRoleNameInp]').val('');
						},
						success : function(layero, index){
							form.render();
						}
					});
				}
				//获取roleList的角色列表值
				form.on('checkbox(roleListInp)', function(data){
	  				var str = '',strText = '';
	  				//var innerText = data.othis[0].innerText;
	  				if(data.elem.checked){
	  					upRoleList.push(data.value);
	  					upRoleListName.push(data.othis[0].innerText);
	  				}else{
	  					for(var i=0;i<upRoleList.length;i++){
	  						if(data.value == upRoleList[i]){
	  							upRoleList.splice(i,1);
	  							upRoleListName.splice(i,1);
	  						}
	  					}
	  				}
	  				str = upRoleList.join(',');
	  				strText = upRoleListName.join(',');
	  				$('input[name=hasSelRoleIdInp]').val(str);
	  				$('input[name=hasSelRoleNameInp]').val(strText);
				});
				//更新员工角色身份信息
				function updateUserRole(userId,obj){
					var selRoleId = $('input[name=hasSelRoleIdInp]').val();
					if (!$('.roleNames').is(':checked')) {
						layer.msg("请选择一个用户身份",{icon:5,anim:6,time:1000});
						return;
					}
					$.ajax({
    					type:"post",
    			        async:false,
    			        dataType:"json",
    			        data : {userId : userId,selRoleId:selRoleId},
    			        url:"user.do?action=updateUserRoleInfo",
    			        success:function (json){
    			        	layer.closeAll("loading");	
    			        	if(json["result"] == "success"){
    			        		layer.msg("编辑员工身份成功！",{icon:1,time:1000},function(){
    			        			obj.update({
    			        				roleName : $('input[name=hasSelRoleNameInp]').val()
    			        			});
    			        			layer.close(upRoleIndex);
    			        			upRoleList.length = 0;
    			        			upRoleListName.length = 0;
    			        			$('input[name=hasSelRoleIdInp]').val('');
    			        			$('input[name=hasSelRoleNameInp]').val('');
    			        		});
    			        	}else if(json["result"] == "noAbility"){
    			        		layer.msg("抱歉，您暂无权限进行员工身份编辑",{icon:5,anim:6,time:1000});
    			        	}else if(json["result"] == "error"){
    			        		layer.msg("系统错误，请重试",{icon:5,anim:6,time:1000});
    			        	}
    			        }
    				});
				}
				//toobar工具栏方法
				table.on('tool(staffTable)',function(obj){
					if(obj.event === 'viewInfo'){ //查看员工信息
						var userId = $(this).attr("userId"),
							name = $(this).attr("name");
						parent.layer.open({
							title:'员工' + name + '的基本信息',
							type: 2,
						  	area: ['700px', '450px'],
						  	fixed: false, //不固定
						  	maxmin: true,
						  	shadeClose :true,
						  	content: "/Module/staffManager/jsp/staffBasicInfo.html",
						  	success : function(layero, index){
						  		var body = parent.layer.getChildFrame('body', index);
						  		//var iframeWin = parent.window[layero.find('iframe')[0]['name']]; 
						  		body.find("#userId").val(userId);
						  	}
						});	
					}else if(obj.event === 'updateInfo'){
						var userId = $(this).attr("userId"),
						name = $(this).attr("name");
						if(upFlag == "true"){
							layer.load("1");
							$.ajax({
		    					type:"post",
		    			        async:false,
		    			        dataType:"json",
		    			        data : {userId : userId},
		    			        url:"user.do?action=getUserRoleData",
		    			        success:function (json){
		    			        	layer.closeAll("loading");	
		    			        	if(json["result"] == "success"){
		    			        		var roleList = json.roleNameInfo;
		    			        		loadEditRoleList(userId,name,roleList,obj);
		    			        	}else if(json["result"] == "noAbility"){
		    			        		layer.msg("抱歉，您暂无权限进行密码重置",{icon:5,anim:6,time:1000});
		    			        	}else if(json["result"] == "error"){
		    			        		layer.msg("系统错误，请重试",{icon:5,anim:6,time:1000});
		    			        	}
		    			        }
		    				});
						}else{
							layer.msg("抱歉，您暂无权限对用户身份进行编辑",{icon:5,anim:6,time:1000});
						}
					}else if(obj.event == 'resetPass'){//重置密码
						var userId = $(this).attr("userId"),
							name = $(this).attr("name");
						if(upFlag == "true"){
							layer.confirm('是否要将此员工'+ name +'的密码重置为123456',{
								title:'提示',
							  	skin: 'layui-layer-molv',
							  	btn: ['确定','取消'] //按钮
							},function(){
								layer.load("1");
								$.ajax({
			    					type:"post",
			    			        async:false,
			    			        dataType:"json",
			    			        data : {userId : userId},
			    			        url:"user.do?action=initUserPass",
			    			        success:function (json){
			    			        	layer.closeAll("loading");	
			    			        	if(json["result"] == "success"){
			    			        		layer.msg("重置密码成功",{icon:1,time:1000});
			    			        	}else if(json["result"] == "noAbility"){
			    			        		layer.msg("抱歉，您暂无权限进行密码重置",{icon:5,anim:6,time:1000});
			    			        	}else if(json["result"] == "error"){
			    			        		layer.msg("系统错误，请重试",{icon:5,anim:6,time:1000});
			    			        	}
			    			        }
			    				});
							});	
						}else{
							layer.msg("抱歉，您暂无权限进行密码重置",{icon:5,anim:6,time:1000});
						}
					}
				});
				//监听离职状态操作
				form.on('switch(isLzSwitch)',function(data){
					if(upFlag == "true"){
						var checkStatus = data.elem.checked;
						var _this = $(this);
						$(this).parent().parent().attr('lay-event','setLzStatus');//给父级td动态增加一个lay-event
						var userId = $(this).attr('userId'),content = '',outDate = '',inDate = $(this).attr('inDate');
						if(!checkStatus){//设置为离职 增加离职日期这块
							content = '<div class="layui-inline"><input type="text" class="layui-input" id="outDateInp" placeholder="请选择离职日期"/><p style="color:#f00;display:none;"></p></div>';
						}else{//设为在职，离职日期清空
							content = '是否要将此员工设置为在职状态？';
						}
						layer.confirm(content, {
							title:'提示',
							closeBtn : 0,
							skin: 'layui-layer-molv',
							btn: ['确定','取消'] //按钮
						},function(index,layero){
							data.elem.checked = checkStatus;
							if(!checkStatus){//设置成离职
								lzStatus = 0;
								outDate = $('#outDateInp').val();
								if(outDate == ''){
									$('#outDateInp').next().show().html('<i class="layui-icon layui-icon-tips"></i>请选择离职日期');
									return;
								}
								if(outDate < inDate){
									$('#outDateInp').next().show().html('<i class="layui-icon layui-icon-tips"></i>离职日期不能小于入职日期');
									return;
								}else{
									$('#outDateInp').next().hide();
								}
							}else{//设置成在职
								lzStatus = 1;
								outDate = '';
							}
							layer.load("1");
							$.ajax({
		  						type:"post",
						        async:false,
						        dataType:"json",
						        data:{userId : userId, yxStatus : -1, lzSatatus : lzStatus, outDate : outDate},
						        url:"user.do?action=setUserInfo",
						        success:function (json){
						        	layer.closeAll("loading");	
				                    if(json["result"] == "success"){
				                    	layer.msg("设置成功",{icon:1,time:1000},function(){
				                    		_this.parent().parent().next().text(outDate);
				                    		//<div class="layui-table-cell laytable-cell-1-outDate">2018-08-20</div>
				                    		_this.parent().parent().next().html('<div class="layui-table-cell laytable-cell-1-outDate" style="width:120px;">'+ outDate +'</div>');
						                    form.render();
						                    layer.close(index);
				                    	});
				                    }else if(json["result"] == "error"){
				                    	layer.msg("系统错误，请重试",{icon:5,anim:6,time:1000});
				                    }else if(json["result"] == "noAbility"){
				                    	layer.msg("抱歉，您暂无权限设置员工离职状态",{icon:5,anim:6,time:1000});
				                    }
				                  	
						        }
		  					});
						},function(index,layero){//取消动作
							data.elem.checked=!checkStatus;
			                form.render();
			                layer.close(index);
						});
						if($("#outDateInp").length > 0){
							laydate.render({elem : "#outDateInp"});
						}
					}else{
						layer.msg("抱歉，您暂无权限设置员工离职状态",{icon:5,anim:6,time:1000});
					}
				});
				//监听账号状态操作
				form.on('switch(isAccYxSwitch)', function(data){
					var userId = $(this).prev().val(),//获取到userId
					checkStatus = data.elem.checked,
					yxStatus = -1,content = '';
					if(upFlag == "true"){
						if(!checkStatus){
							content = '是否要设置此此账号状态为无效状态？';
						}else{
							content = '是否要设置此此账号状态为有效状态？';
						}
						layer.confirm(content, {
							title:'提示',
							closeBtn : 0,
							skin: 'layui-layer-molv',
							btn: ['确定','取消']
						},function(index,layero){
							data.elem.checked = checkStatus;
							if(!checkStatus){//设置成无效
								yxStatus = 0;
							}else{//设置成有效
								yxStatus = 1;
							}
							layer.load("1");
							$.ajax({
		  						type:"post",
						        async:false,
						        dataType:"json",
						        data:{userId : userId, yxStatus : yxStatus, lzSatatus : -1, outDate : ''},
						        url:"user.do?action=setUserInfo",
						        success:function (json){
						        	if(json["result"] == "success"){
						        		layer.closeAll("loading");	
				                    	layer.msg("设置成功",{icon:1,time:1000});
					                    form.render();
					                    layer.close(index);
				                    }else if(json["result"] == "error"){
				                    	layer.msg("系统错误，请重试",{icon:5,anim:6,time:1000});
				                    }else if(json["result"] == "noAbility"){
				                    	layer.msg("抱歉，您暂无权限设置账号状态",{icon:5,anim:6,time:1000});
				                    }
						        }
		  					});
						},function(index,layero){//取消动作
							data.elem.checked=!checkStatus;
			                form.render();
			                layer.close(index);
						});
					}else{
						layer.msg("抱歉，您暂无权限修改账号状态",{icon:5,anim:6,time:1000});
					}
				});
				
				
				$(function(){
					page.init();
					loadQueryStaffList('loadListData');
				});
				
			});
	  	</script>
  </body>
</html>
