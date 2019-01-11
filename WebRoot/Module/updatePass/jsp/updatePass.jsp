<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>修改密码更新密码</title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="keywords" content="专利管理系统,密码修改,密码更新">
	<meta http-equiv="description" content="专利管理系统密码修改，密码更新">
	<link href="/Module/userManager/css/formInp.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
  </head>
  
  <body style="background:#f2f2f2;color:#666;">
    <div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header">修改密码</div>
  					<div class="layui-card-body" pad15>
  						<div id="setPerInfo" class="layui-form">
  							<div class="layui-form-item">
  								<label class="layui-form-label">当前密码</label>
  								<div class="layui-input-inline">
  									<input id="passOldInp" type="password" name="passOld" required lay-verify="judgeOldPass" class="layui-input" autocomplete="off" placeholder="请输入当前密码">
  								</div>
  							</div>
  							<div class="layui-form-item">
  								<label class="layui-form-label">新密码</label>
  								<div class="layui-input-inline">
  									<input id="newPassInp" type="password" name="newPass" required lay-verify="judgePass" class="layui-input" autocomplete="off" placeholder="请输入新密码">
  								</div>
  							</div>
  							<div class="layui-form-item">
  								<label class="layui-form-label">确认新密码</label>
  								<div class="layui-input-inline">
  									<input type="password" name="confirNewPass" lay-verify="required" class="layui-input" autocomplete="off" placeholder="请再次输入新密码">
  								</div>
  							</div>
  							<div class="layui-form-item">
  								<label class="layui-form-label"></label>
  								<div class="layui-input-inline">
  									<button class="layui-btn" lay-submit lay-filter="updatePass">保存修改</button>
  								</div>
  							</div>
  						</div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
  	<script type="text/javascript">
  		layui.use(['layer','form','jquery'],function(){
  			var layer = layui.layer,
  				form = layui.form,
  				$ = layui.jquery;
  			$("#newPassInp").focus(function(){
				layer.tips("新密码由6-16个字符(字母，数字，符号组成),不能输入空格", "#newPassInp", {tips:[2,'#FF8000'],time:0});
			}); 
  			$("#newPassInp").blur(function(){layer.closeAll('tips');});
  			form.verify({
  				judgeOldPass : function(value){
  					if(value == ''){
						return '旧密码不能为空';
					}
  				},
  				judgePass : function(value){
  					if(value == ''){
						return '新密码不能为空';
					}else{
						if(value.length < 6){
							return '新密码的长度不能小于6位';
						}else if(value.length > 16){
							return '新密码的长度不大于小于16位';
						}else if(value == $("#passOldInp").val()){
							return '新密码不能与旧密码相同';
						}
					}
  				}
  			});
  			form.on('submit(updatePass)',function(data){
  				var field = data.field;
  				console.log(field);
  				for(var attr in field){
		  			if(attr == "newPass" || attr == "confirNewPass"){
		  				field[attr] = field[attr].replace(/\s/g,"");
		  			}
		  		}
  				if(field.newPass !== field.confirNewPass){
 		  	       return layer.msg("新密码和确认新密码不相同，请从新输入",{icon:5,anim:6,time:1000});
 		  	    }
  				layer.load();
  				$.ajax({
		    		type:"post",
			        async:false,
			        dataType:"json",
			        url:"user.do?action=updateUserPass",
			        data:field,
			        success:function (json){
			        	layer.closeAll('loading');
			        	if(json["result"] == "success"){
			        		layer.msg("密码修改成功",{icon:1,time:1000},function(){
			        			window.location.href = "user.do?action=goUpdatePassPage";
			        		});
			        	}else if(json["result"] == "oldNull"){
			        		layer.msg("旧密码不能为空",{icon:5,anim:6,time:1000});
			        		$("#passOldInp").focus().addClass("layui-form-danger");
			        	}else if(json["result"] == "noMatch"){
			        		layer.msg("旧密码错误，请从新输入",{icon:5,anim:6,time:1000});
			        		$("#passOldInp").focus().addClass("layui-form-danger");
			        	}
			        }
		    	});
  			});
  		});
  	</script>
  </body>
</html>
