<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利管理系统技术领域管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"/> 
	<meta http-equiv="keywords" content="专利管理,技术领域,专业领域,增加专业领域"/>
	<meta http-equiv="description" content="专利管理系统代理机构专业领域增加和编辑"/>
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/roleManager/css/roleManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
  </head>
  
  <body>
  	<body style="background:#f2f2f2;color:#666;">
    <div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header posRel">
  						<span>专业领域管理</span>
  						<a id="addProfessField" class="posAbs newAddBtn" opts="addBtn" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加专业领域</a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<div id="fieldList" class="lists fieldList"></div>
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
    				jfId : "",
       				zyName : "",
       				zyProfile : "",
    				globalIndex : 0
    			},
    			init : function(){
    				this.onLoad();
    				this.bindEvent();
    			},
    			onLoad : function(){
    				//获取代理机构下专业技术领域list
    				layer.load("1");
  					this.loadTechFieldList();
    			},
    			bindEvent : function(){
    				var _this = this;
    				$("#addProfessField").on("click",function(){
        	        	//增加角色
        				if(addFlag == "true"){
        					globalOpts = $(this).attr("opts");
        					var addEditRoleCon = '';
        					addEditRoleCon += '<div class="addEditRoleCon">';
            				addEditRoleCon += '<div class="comRoleDiv"><span class="fl"><em style="color:#f00;font-style:normal;">*</em>专业名：</span><input id="inpRoleName" type="text" placeholder="请输入专业名(8字以内)" maxlength="8"></div>';
            				addEditRoleCon += '<div class="comRoleDiv"><span class="fl">专业简介：</span><input id="roleProfile" type="text" placeholder="请输入专业简介(20字以内)" maxlength="20"></div>';
            				addEditRoleCon += '</div>';
            				_this.data.globalIndex = layer.open({
    	        				title : '添加专业领域',
    	        				type: 1,
    	        				skin: 'addEditRole', //样式类名
    	        				closeBtn: 0, //不显示关闭按钮
    	        			 	anim: 1,
    	        			  	content: addEditRoleCon,
    	        			  	btn : ['确定','取消'],
    	        			  	btnAlign:'c',
    	        			  	yes: function(index, layero){
    	        			  		_this.addTechField();
    	        				}
    	        			});
    	        		}else{
    	        			layer.msg("抱歉，您没有权限添加专业！", {icon:5,anim:6,time:1000});
    	        		}	
    				});
    			},
    			bindEvent_multi : function(){
    				var _this = this;
    				//编辑
    				$(".editABtn").on("click",function(){
    					var addEditRoleCon = '';
    					_this.data.jfId =  $(this).attr("jfId");
						_this.data.zyName = $(this).attr("zyName");
						_this.data.zyProfile = $(this).attr("zyProfile");
						
        				addEditRoleCon += '<div class="addEditRoleCon">';
        				addEditRoleCon += '<input type="hidden" id="jfIdInp" value="'+ _this.data.jfId +'"/>';
        				addEditRoleCon += '<div class="comRoleDiv"><span class="fl"><em style="color:#f00;font-style:normal;">*</em>专业名：</span><input id="inpRoleName" type="text" placeholder="请输入角色名(6字以内)" value="'+ _this.data.zyName +'" maxlength="6"></div>';
        				addEditRoleCon += '<div class="comRoleDiv"><span class="fl">专业简介：</span><input id="roleProfile" type="text" placeholder="请输入角色简介(20字以内)" value="'+ _this.data.zyProfile +'" maxlength="20"></div>';
        				addEditRoleCon += '</div>';
    					if(upFlag == "true"){
    						globalOpts = $(this).parent().attr("opts");
    						_this.data.globalIndex = layer.open({
    	        				title : '编辑专业',
    	        				type: 1,
    	        				skin: 'addEditRole', //样式类名
    	        				closeBtn: 0, //不显示关闭按钮
    	        			 	anim: 1,
    	        			  	shadeClose: true, //开启遮罩关闭
    	        			  	content: addEditRoleCon,
    	        			  	btn : ['确定','取消'],
    	        			  	btnAlign:'c',
    	        			  	yes: function(index, layero){
    	        			  		_this.addTechField();
    	        				}
    	        			});
    					}else{
    						layer.msg("抱歉，您没有权限编辑！", {icon:5,anim:6,time:1000});
    					}
    				});
    				//删除
    				$(".delABtn").on("click",function(){
       					var jfId = $(this).attr("jfId");
       					if(delFlag == "true"){
	       					layer.confirm('确认要删除该专业吗？', {
	       					  title:'提示',
	       					  skin: 'layui-layer-molv',
	       					  btn: ['确定','取消'] //按钮
	       					}, function(){
	       						$.ajax({
	       	  						type:"post",
	       					        async:false,
	       					        dataType:"json",
	       					        data:{jfId:jfId},
	       					        url:"jfm.do?action=delJf",
	       					        success:function (json){
	       					        	if(json["result"] == "success"){
	       					        		layer.msg("删除成功",{icon:1,time:1000},function(){
	       					        			_this.loadTechFieldList();
		    				        			_this.bindEvent_multi();
		    				        		});
	       					        	}else if(json["result"] == "existBind"){
	       					        		layer.msg("该专业已被员工绑定，不能删除", {icon:5,anim:6,time:1000});
	       					        	}else if(json["result"] == "error"){
	       					        		layer.msg("系统错误，请重试", {icon:5,anim:6,time:1000});
	       					        	}else if(json["result"] == "fail"){
	       					        		layer.msg("删除失败，请重试", {icon:5,anim:6,time:1000});
	       					        	}else if(json["result"] == "noAbility"){
	       					        		layer.msg("抱歉，您暂无权限删除", {icon:5,anim:6,time:1000});
	       					        	}
	       					        }
	       	  					});
	       					});
       					}else{
       						layer.msg("抱歉，您暂无权限删除", {icon:5,anim:6,time:1000});
       					}
       				});
    			},
    			loadTechFieldList : function(){
    				$.ajax({
  						type:"post",
				        dataType:"json",
				        url:"jfm.do?action=getJfData",
				        success:function (json){
				        	layer.closeAll("loading");	
				        	if(json["result"] == "success"){//代表加载数据成功
					        	var jfFieldList = json.jsInfo;
					        	getTechFieldList(jfFieldList);
				        	}else if(json["result"] == "fail"){
				        		layer.msg("加载失败，请刷新重试", {icon:5,anim:6,time:1000});
				        	}else if(json["result"] == "noInfo"){//表示没数据
				        		$("#fieldList").html("<div class='noData'><i class='iconfont layui-extend-noData'></i><p>暂无专业领域，请添加</p></div>");	
				        	}
				        }
  					});
    			},
    			addTechField : function(){
    				var _this = this;
    				var zyName = $.trim($("#inpRoleName").val()),
        			zyProfile = $.trim($("#roleProfile").val());
    				if(zyName == ""){
	        			layer.msg("专业名称不能为空", {icon:5,anim:6,time:1000});
	        			return;
	        		}
        			var reg = /^[\u4E00-\u9FA5]+$/; 
        			if(!reg.test(zyName)){
        				layer.msg("专业名应为汉字", {icon:5,anim:6,time:1000});
        				return;
        			}else if(zyProfile != ""){
        				if(!reg.test(zyProfile)){
        					layer.msg("专业简介应为汉字", {icon:5,anim:6,time:1000});
        					return;
        				}
        			}
       				var url = "";
       				if(globalOpts == "addBtn"){//表示增加专业
       					var field = {zyName : escape(zyName),zyProfile : escape(zyProfile)};
       					url = "jfm.do?action=addJf";
       				}else{//表示编辑专业
       					var field = {jfId : $("#jfIdInp").val(),zyName : escape(zyName),zyProfile : escape(zyProfile)};
       					url = "jfm.do?action=updateJf";
       				}
       				$.ajax({
     					type:"post",
   				        async:false,
   				        dataType:"json",
   				        data :field,
   				        url:url,
   				        success:function (json){
   				        	if(json["result"] == "success"){
   				        		if(globalOpts == "addBtn"){//表示增加专业
   				        			layer.msg("添加成功",{icon:1,time:1000},function(){
    				        			_this.loadTechFieldList();
    				        			_this.bindEvent_multi();
    				        			layer.close(_this.data.globalIndex);
    				        		});
   				        		}else{
   				        			layer.msg("编辑成功",{icon:1,time:1000},function(){
   				        				_this.loadTechFieldList();
    				        			_this.bindEvent_multi();
    				        			layer.close(_this.data.globalIndex);
    				        		});
   				        		}
   				        	}else if(json["result"] == "exist"){
   				        		layer.msg("专业名称已存在，请重新编辑", {icon:5,anim:6,time:1000});
   				        	}else if(json["result"] == "error"){
   				        		layer.msg("系统错误，请重试", {icon:5,anim:6,time:1000});
   				        	}else if(json["result"] == "noAbility"){
   				        		layer.msg("对不起，您暂无权限增加编辑", {icon:5,anim:6,time:1000});
   				        	}else if(json["result"] == "fail"){
   				        		layer.msg("添加失败，请重试", {icon:5,anim:6,time:1000});
   				        	}
   				        }
     				});
    			}
    		};
    		//获取专业技术列表
    		function getTechFieldList(jfFieldList){
    			var strHtml = '';
    			strHtml += '<ul class="clearfix">';
    			for(var i=0;i<jfFieldList.length;i++){
        			strHtml += '<li><div class="topRoleName">'+ jfFieldList[i].zyName +'</div><div class="roleProfile"><strong>简介</strong><p>'+ jfFieldList[i].zyProfile +'</p></div>';
        			strHtml += '<div class="smFunDiv" opts="funBtn"><a class="editABtn" title="编辑" jfId="'+ jfFieldList[i].id +'" zyName="'+  jfFieldList[i].zyName +'" zyProfile="'+ jfFieldList[i].zyProfile +'" href="javascript:void(0)"><i class="iconfont layui-extend-bianji"></i></a><a title="删除" class="delABtn" jfId="'+ jfFieldList[i].id +'" href="javascript:void(0)"><i class="iconfont layui-extend-shanchu"></i></a></div>';
        			strHtml += '</li>';
    			}
    			strHtml += '</ul>';
    			$('#fieldList').html(strHtml);
    			page.bindEvent_multi();
    		}
    		$(function(){
    			page.init();
    		});
    	});
    </script>
  </body>
</html>
