<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利管理系统代理机构角色管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="专利管理系统,代理机构,角色管理">
	<meta http-equiv="description" content="专利管理系统代理机构角色管理">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/roleManager/css/roleManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
  </head>
  <body style="background:#f2f2f2;color:#666;">
    <div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header posRel">
  						<span>代理机构角色管理</span>
  						<a id="addRole" class="posAbs newAddBtn" opts="addBtn" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加角色</a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<div id="roleList" class="lists roleList"></div>
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
    		globalOpts = "addBtn";
    	layui.use(['layer','jquery'],function(){
    		var layer = layui.layer,
    			$ = layui.jquery;
    		var page = {
   				data : {
       				roleId : "",
       				roleName : "",
       				roleProfile : "",
       				globalIndex : 0
       			},
    			init : function(){
    				this.onLoad();
    				this.bindEvent(),
    				this.bindEvent_multi();
    			},
    			onLoad : function(){
    				//获取代理机构下角色列表
  					layer.load("1");
    				this.loadRoleList();	
    			},
    			loadRoleList : function(){
        			$.ajax({
    					type:"post",
    			        async:false,
    			        dataType:"json",
    			        url:"role.do?action=getRoleList",
    			        success:function (json){
    			        	layer.closeAll("loading");	
    			        	var roleList = json.roleList;
    			        	getRoleList(roleList);
    			        }
    				});
    			},
    			bindEvent : function(){
    				var _this = this;
    				//增加角色
    				$("#addRole").on("click",function(){
        	        	//增加角色
        				if(addFlag == "true"){
        					globalOpts = $(this).attr("opts");
        					var addEditRoleCon = '';
        					addEditRoleCon += '<div class="addEditRoleCon">';
            				addEditRoleCon += '<div class="comRoleDiv"><span class="fl">角色名：</span><input id="inpRoleName" type="text" placeholder="请输入角色名(6字以内)" maxlength="6"></div>';
            				addEditRoleCon += '<div class="comRoleDiv"><span class="fl">角色简介：</span><input id="roleProfile" type="text" placeholder="请输入角色简介(20字以内)" maxlength="20"></div>';
            				addEditRoleCon += '</div>';
            				_this.data.globalIndex = layer.open({
    	        				title : '添加角色',
    	        				type: 1,
    	        				skin: 'addEditRole', //样式类名
    	        				closeBtn: 0, //不显示关闭按钮
    	        			 	anim: 1,
    	        			  	content: addEditRoleCon,
    	        			  	btn : ['确定','取消'],
    	        			  	btnAlign:'c',
    	        			  	yes: function(index, layero){
    	        			  		_this.addRole();
    	        				}
    	        			});
    	        		}else{
    	        			layer.msg("抱歉，您没有权限添加角色！", {icon:5,anim:6,time:1000});
    	        		}	
    				});
    			},
    			bindEvent_multi : function(){
    				var _this = this;
    				//编辑角色
    				$(".editABtn").on("click",function(){
    					var addEditRoleCon = '';
    					_this.data.roleId =  $(this).attr("roleId");
						_this.data.roleName = $(this).attr("roleName");
						_this.data.roleProfile = $(this).attr("roleProfile");
						
        				addEditRoleCon += '<div class="addEditRoleCon">';
        				addEditRoleCon += '<input type="hidden" id="roleIdInp" value="'+ _this.data.roleId +'"/>';
        				addEditRoleCon += '<div class="comRoleDiv"><span class="fl">角色名：</span><input id="inpRoleName" type="text" placeholder="请输入角色名(6字以内)" value="'+ _this.data.roleName +'" maxlength="6"></div>';
        				addEditRoleCon += '<div class="comRoleDiv"><span class="fl">角色简介：</span><input id="roleProfile" type="text" placeholder="请输入角色简介(20字以内)" value="'+ _this.data.roleProfile +'" maxlength="20"></div>';
        				addEditRoleCon += '</div>';
    					if(upFlag == "true"){
    						globalOpts = $(this).parent().attr("opts");
    						_this.data.globalIndex = layer.open({
    	        				title : '编辑角色',
    	        				type: 1,
    	        				skin: 'addEditRole', //样式类名
    	        				closeBtn: 0, //不显示关闭按钮
    	        			 	anim: 1,
    	        			  	shadeClose: true, //开启遮罩关闭
    	        			  	content: addEditRoleCon,
    	        			  	btn : ['确定','取消'],
    	        			  	btnAlign:'c',
    	        			  	yes: function(index, layero){
    	        			  		_this.addRole();
    	        				}
    	        			});
    					}else{
    						layer.msg("抱歉，您没有权限编辑角色！", {icon:5,anim:6,time:1000});
    					}
    				});
    				//删除角色
       				$(".delABtn").on("click",function(){
       					var roleId = $(this).attr("roleId");
       					if(delFlag == "true"){
	       					layer.confirm('确认要删除该角色吗？', {
	       					  title:'提示',
	       					  skin: 'layui-layer-molv',
	       					  btn: ['确定','取消'] //按钮
	       					}, function(){
	       						$.ajax({
	       	  						type:"post",
	       					        async:false,
	       					        dataType:"json",
	       					        data:{roleId:roleId},
	       					        url:"role.do?action=delRole",
	       					        success:function (json){
	       					        	if(json["result"] == "success"){
	       					        		layer.msg("删除成功",{icon:1,time:1000},function(){
		    				        			_this.loadRoleList();
		    				        			_this.bindEvent_multi();
		    				        		});
	       					        	}else if(json["result"] == "existUser"){
	       					        		layer.msg("该角色已绑定用户，不能删除", {icon:5,anim:6,time:1000});
	       					        	}else if(json["result"] == "error"){
	       					        		layer.msg("系统错误，请重试", {icon:5,anim:6,time:1000});
	       					        	}else if(json["result"] == "noAbility"){
	       					        		layer.msg("对不起，您暂无权限删除该角色", {icon:5,anim:6,time:1000});
	       					        	}
	       					        }
	       	  					});
	       					});
       					}else{
       						layer.msg("抱歉，您没有权限删除角色！", {icon:5,anim:6,time:1000});
       					}
       				});
    			},
    			//增加编辑角色共同方法
    			addRole : function(){
    				var _this = this;
    				var inpRoleName = $.trim($("#inpRoleName").val()),
        			roleProfile = $.trim($("#roleProfile").val());
	        		if(inpRoleName == ""){
	        			layer.msg("角色名称不能为空", {icon:5,anim:6,time:1000});
	        		}/*else if(roleProfile == ""){
	        			layer.msg("角色简介不能为空", {icon:5,anim:6,time:1000});
	        		}*/else{
	        			var reg = /^[\u4E00-\u9FA5]+$/; 
	        			if(!reg.test(inpRoleName)){
	        				layer.msg("角色名应为汉字", {icon:5,anim:6,time:1000});
	        			}/*else if(!reg.test(roleProfile)){
	        				layer.msg("角色简介应为汉字", {icon:5,anim:6,time:1000});
	        			}*/else{
	        				var url = "";
	        				if(globalOpts == "addBtn"){//表示增加角色
	        					var field = {inpRoleName : escape(inpRoleName),roleProfile : escape(roleProfile)};
	        					url = "role.do?action=addRole";
	        				}else{//表示编辑角色
	        					var field = {roleId : $("#roleIdInp").val(),inpRoleName : escape(inpRoleName),roleProfile : escape(roleProfile)};
	        					url = "role.do?action=updateRole";
	        				}
	        				$.ajax({
	      						type:"post",
	    				        async:false,
	    				        dataType:"json",
	    				        data :field,
	    				        url:url,
	    				        success:function (json){
	    				        	if(json["result"] == "success"){
	    				        		if(globalOpts == "addBtn"){//表示增加角色
	    				        			layer.msg("添加成功",{icon:1,time:1000},function(){
		    				        			_this.loadRoleList();
		    				        			_this.bindEvent_multi();
		    				        			layer.close(_this.data.globalIndex);
		    				        		});
	    				        		}else{
	    				        			layer.msg("编辑成功",{icon:1,time:1000},function(){
		    				        			_this.loadRoleList();
		    				        			_this.bindEvent_multi();
		    				        			layer.close(_this.data.globalIndex);

		    				        		});
	    				        		}
	    				        	}else if(json["result"] == "exist"){
	    				        		layer.msg("角色名称存在，请重新编辑", {icon:5,anim:6,time:1000});
	    				        	}else if(json["result"] == "error"){
	    				        		layer.msg("系统错误，请重试", {icon:5,anim:6,time:1000});
	    				        	}else if(json["result"] == "noAbility"){
	    				        		layer.msg("对不起，您暂无权限增加编辑角色", {icon:5,anim:6,time:1000});
	    				        	}else if(json["result"] == "fail"){
	    				        		layer.msg("添加失败，请重试", {icon:5,anim:6,time:1000});
	    				        	}
	    				        }
	      					});
	        			}
	        		}
    			}
    		};
    		//渲染roleList
    		function getRoleList(roleList){
    			var strHtml = '';
    			strHtml += '<ul class="clearfix">';
    			for(var i=0;i<roleList.length;i++){
        			strHtml += '<li><div class="topRoleName">'+ roleList[i].roleName +'</div><div class="roleProfile"><strong>简介</strong><p>'+ roleList[i].roleProfile +'</p></div>';
        			if(roleList[i].roleName != '管理员'){
        				strHtml += '<div class="smFunDiv" opts="funBtn"><a class="editABtn" title="编辑" roleId="'+ roleList[i].id +'" roleName="'+  roleList[i].roleName +'" roleProfile="'+ roleList[i].roleProfile +'" href="javascript:void(0)"><i class="iconfont layui-extend-bianji"></i></a><a title="删除" class="delABtn" roleId="'+ roleList[i].id +'" href="javascript:void(0)"><i class="iconfont layui-extend-shanchu"></i></a></div>';
        			}
        			strHtml += '</li>';
    			}
    			strHtml += '</ul>';
    			$('#roleList').html(strHtml);
    		}
    		$(function(){
    			page.init();
    		});
    	});
    </script>
  </body>
</html>
