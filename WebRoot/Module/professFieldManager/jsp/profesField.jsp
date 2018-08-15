<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
  <head>
    <title>专利管理系统技术领域管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"> 
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
  						<div id="fieldList"></div>
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
    					console.log(addFlag)
        	        	//增加角色
        				if(addFlag){
        					
    	        		}else{
    	        			layer.msg("抱歉，您没有权限添加角色！", {icon:5,anim:6,time:1000});
    	        		}	
    				});
    			},
    			loadTechFieldList : function(){
    				$.ajax({
  						type:"post",
				        async:false,
				        dataType:"json",
				        url:"jfm.do?action=getJfData",
				        success:function (json){
				        	layer.closeAll("loading");	
				        	getTechFieldList(json);
				        }
  					});
    			}
    		};
    		//获取专业技术列表
    		function getTechFieldList(list){
    			
    		}
    		
    		
    		$(function(){
    			page.init();
    		});
    	});
    </script>
  </body>
</html>
