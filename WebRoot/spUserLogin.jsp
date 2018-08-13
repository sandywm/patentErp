<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利管理系统--平台用户登录</title>
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<link href="/css/login.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js"></script>
	<style>
		.layui-form-select dl dd.layui-this{background:#009688;}
	</style>
  </head>
  
  <body>   
  	<div class="layadmin-user-login layadmin-user-display-show">
	    <div class="layadmin-user-login-main">
	      <div class="layadmin-user-login-box layadmin-user-login-header">
	        <h2>专利管理系统</h2>
	        <p>Patent management system</p>
	      </div>
	      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
	        <div class="layui-form-item">
	          <label class="layadmin-user-login-icon iconfont layui-extend-wode"></label>
	          <input type="text" id="account" placeholder="请输入账号" onkeypress="enterPress(event)" autocomplete="off" class="layui-input">
	        </div>
	        <div class="layui-form-item">
	          <label class="layadmin-user-login-icon iconfont layui-extend-mima" for="LAY-user-login-password"></label>
	          <input type="password" id="password" placeholder="请输入密码" onkeypress="enterPress(event)" autocomplete="off" class="layui-input">
	        </div>
	        <div class="layui-form-item">
	          <div class="layui-row">
	            <div class="layui-col-xs7">
	              <label class="layadmin-user-login-icon iconfont layui-extend-vercode" for="LAY-user-login-vercode"></label>
	              <input type="text" id="inputCode" placeholder="请输入图形验证码" onkeypress="enterPress(event)" autocomplete="off" class="layui-input">
	            </div>
	            <div class="layui-col-xs5">
	              <div style="margin-left: 10px;">
	                <img id="sessCode" src="authImg" alt="请输入验证码" title="看不清换一张" width="130" style="cursor:pointer;"/>
	              </div>
	            </div>
	          </div>
	        </div>
	        <div class="layui-form-item">
	          <button id="button" class="layui-btn layui-btn-fluid">登录</button>
	        </div>
	      </div>
	    </div>
	    <input id="roleIdInp" type="hidden"/>
	    <div class="layui-trans layadmin-user-login-footer">
	      <p>© 2018 版权所有 Copyright@2018 Sandy.wm All Rights Reserved.</p>
	    </div>
  	</div>
    <script src="/plugins/jquery/jquery-1.7.2.min.js"></script>
	<script src="/plugins/layui/layui.js"></script>
	<script type="text/javascript">
		layui.use(['layer','jquery','form'],function(){
			var layer = layui.layer,
				$ = layui.jquery,
				form = layui.form;
			$("#button").on('click',function(){
				login();
			});
			$("#sessCode").on('click',function(){
				verCode();
			});
		});
		function login(){
			var account = $.trim($("#account").val());
			var password = $.trim($("#password").val());
			var vCode = $.trim($("#inputCode").val());
			if(account == ""){
				layer.msg("账号不能为空", {icon:5,anim:6,time:1000});
				$("#account").focus().addClass("layui-form-danger");
			}else if(password == ""){
				layer.msg("密码不能为空", {icon:5,anim:6,time:1000});
				$("#password").focus().addClass("layui-form-danger");
			}else{
				$.ajax({
			        type:"post",
			        async:false,
			        dataType:"json",
			        url:"login.do?action=spLogin",
			        data:{account:account,password:password,vCode:vCode},
			        success:function (json){
			        	proccessLogin(json);
			        }
			    });
			}
		}
		//验证码
		function verCode(){
			var obj = document.getElementById("sessCode");
			obj.src = "authImg?code="+Math.random()+100;
		}
		//处理登录
		function proccessLogin(list){
			if(list["result"] == "success"){
				window.location.href = "user.do?action=spGoPage";
			}else if(list["result"] == "lock"){
				layer.msg("该账号无效", {icon:5,anim:6,time:1000});
			}else if(list["result"] == "fail"){
				layer.msg("账号密码错误", {icon:5,anim:6,time:1000});
				verCode();
			}else if(list["result"] == "vercodeFail"){
				layer.msg("验证码错误", {icon:5,anim:6,time:1000});
				$("#inputCode").focus().addClass("layui-form-danger");
			}else if(list["result"] == "roleErr"){
				layer.msg("登录异常", {icon:5,anim:6,time:1000});
			}
		}
		//回车事件
		function enterPress(e){
			var e = e || window.event;
			if(e.keyCode == 13){
				login();
			}
		}
	</script>
  </body>
  
</html>
