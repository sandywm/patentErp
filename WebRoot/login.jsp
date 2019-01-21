<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利管理系统--用户登录</title>
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<link href="/css/login.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js"></script>
	<style>
		body{background:#f2f2f2;}
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
	      	   <label class="layadmin-user-login-icon iconfont layui-extend-wode" style="z-index:10;"></label>
		       <select id="loginType">
		       	<option value="">请选择账户类型</option>
		        <option value="cpyUser">代理机构用户</option>
    			<option value="appUser">申请专利（人/公司）用户</option>
		      </select>
	      	</div>
	        <div class="layui-form-item">
	          <label class="layadmin-user-login-icon iconfont layui-extend-wode"></label>
	          <input type="text" id="account" placeholder="请输入账号" maxlength="12" autocomplete="off" class="layui-input">
	        </div>
	        <div class="layui-form-item">
	          <label class="layadmin-user-login-icon iconfont layui-extend-mima" for="LAY-user-login-password"></label>
	          <input type="password" id="password" placeholder="请输入密码" maxlength="16" autocomplete="off" class="layui-input">
	        </div>
	        <div class="layui-form-item">
	          <div class="layui-row">
	            <div class="layui-col-xs7">
	              <label class="layadmin-user-login-icon iconfont layui-extend-vercode" for="LAY-user-login-vercode"></label>
	              <input type="text" id="inputCode" maxlength="4" placeholder="请输入图形验证码" autocomplete="off" class="layui-input">
	            </div>
	            <div class="layui-col-xs5">
	              <div style="margin-left: 10px;">
	                <img id="sessCode" src="authImg" alt="请输入验证码" title="看不清换一张" width="130" style="cursor:pointer;"/>
	              </div>
	            </div>
	          </div>
	        </div>
	        <div class="layui-form-item" style="margin-bottom: 20px;">
	        	<a href="login.do?action=goSignPage" class="layadmin-link" style="float:left;margin-top:7px;">注册帐号</a>
	            <a href="login.do?action=goForgetPage" class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;">忘记密码？</a>
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
	<script src="/plugins/layui/layui.js"></script>
	<script type="text/javascript">
		layui.config({
			base: '/plugins/frame/js/'
		}).extend({ //设定组件别名
		    common:  'common'// 表示模块文件的名字
		}).use(['layer','jquery','form','common'],function(){
			var layer = layui.layer,
				$ = layui.jquery,
				form = layui.form,
				common = layui.common;
			$("#button").on('click',function(){
				login();
			});
			$("#sessCode").on('click',function(){
				common.vercode();
			});
			$(function(){
				$("#account").on("keypress",function(){
					enterPress(event);
				});
				$("#password").on("keypress",function(){
					enterPress(event);
				});
				$("#inputCode").on("keypress",function(){
					enterPress(event);
				});
			});
			function login(){
				var account = $.trim($("#account").val());
				var password = $.trim($("#password").val());
				var vCode = $.trim($("#inputCode").val());
				var loginType = $("#loginType").val();
				if(loginType == ""){
					layer.msg("请选择账户类型", {icon:5,anim:6,time:1000});
				}else if(account == ""){
					layer.msg("账号不能为空", {icon:5,anim:6,time:1000});
					$("#account").focus().addClass("layui-form-danger");
				}else if(password == ""){
					layer.msg("密码不能为空", {icon:5,anim:6,time:1000});
					$("#password").focus().addClass("layui-form-danger");
				}else if(vCode == ""){
					layer.msg("验证码不能为空", {icon:5,anim:6,time:1000});
					$("#inputCode").focus().addClass("layui-form-danger");
				}else{
					layer.load();
					$.ajax({
				        type:"post",
				        async:false,
				        dataType:"json",
				        url:"login.do?action=login",
				        data:{account:account,password:password,vCode:vCode,loginType:loginType},
				        success:function (json){
				        	layer.closeAll('loading');
				        	proccessLogin(json,loginType);
				        }
				    });
				}
			}
			//处理登录
			function proccessLogin(list,loginType){
				if(list["result"] == "success"){
					if(loginType == "cpyUser"){
						var roleObj = list["roleList"];
						var roleLength = roleObj.length;
						if(roleLength == 1){//一种身份
							window.location.href = "user.do?action=goPage&roleId="+roleObj[0].roleId+"&loginType="+loginType;
						}else{//多重身份下执行
							listRole(list["roleList"]);
						}
					}else if(loginType == "appUser"){
						window.location.href = "user.do?action=goPage&loginType=" + loginType;
					}
				}else if(list["result"] == "lock"){
					layer.msg("该账号无效,已被锁定", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "fail"){
					layer.msg("账号密码错误", {icon:5,anim:6,time:1000});
					common.vercode();
				}else if(list["result"] == "vercodeFail"){
					layer.msg("验证码错误", {icon:5,anim:6,time:1000});
					$("#inputCode").focus().addClass("layui-form-danger");
				}else if(list["result"] == "roleErr"){
					layer.msg("登录异常", {icon:5,anim:6,time:1000});
				}
			}
			//多重身份下进行身份选择登录
			function listRole(list){
				layui.use('form',function(){
					var html = '';
					var form = layui.form;
					html += '<div class="layui-form" style="width:90%;margin:0 auto;">';
					html += '<div class="layui-input-inline">';
					for(i=0; i<list.length; i++){
						html += '<input type="radio" name="roleSel" value="'+ list[i].roleId +'" title="'+ list[i].roleName +'">';
					}
					html += '</div></div>';
					$("#roleIdInp").val("");
					layer.open({
						title : '系统检测到您当前账户绑有多重身份，请选择一种身份登录',
						skin:'layui-layer-molv',
						type : 1, 
						content:html, 
						area : ['470px','200px'],
						btn : ['进入系统','取消'],
						yes: function(index, layero){
							goPage();
						}
					});
					form.on('radio', function(data){
					    $("#roleIdInp").val(data.value); 
					    //$("#roleNameInp").val(data.elem.title);
					}); 
					form.render();
				});
			}
			//多重身份下的页面跳转
			function goPage(){
				var roleId =  $("#roleIdInp").val();
				var loginType = $("#loginType").val();
				if(roleId == ""){
					layer.msg("请选择一个身份进入系统",{icon:5,anim:6,time:1000});
				}else{
					window.location.href = "user.do?action=goPage&roleId="+roleId+"&loginType="+loginType;
				}
			}
			//回车事件
			function enterPress(e){
				var e = e || window.event;
				if(e.keyCode == 13){
					login();
				}
			}
		});

		
	</script>
  </body>
  
</html>
