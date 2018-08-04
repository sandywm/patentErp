<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
  <head>
    <title>专利管理系统--忘记密码找回密码</title>
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="keywords" content="申请专利,忘记密码,找回密码">
	<meta http-equiv="description" content="专利申请系统忘记密码找回密码">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<link href="/css/login.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js"></script>
	<style>
		body{background:#f2f2f2;}
		.step-succ p{position:relative;padding-left:45px;font-size:16px;margin-bottom:35px;}
		.layui-extend-success{font-size:40px;position:absolute;left:0;top:0;color:#009688;}
		.step-con{display:none;}
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
	      	<!-- 第一步填写注册账户 -->
	      	<div class="step-con step-username">
	      		<div class="layui-form-item">
		          <label class="layadmin-user-login-icon iconfont layui-extend-wode"></label>
		          <input type="text" id="account" placeholder="请输入账号" autocomplete="off" class="layui-input">
		        </div>
		        <div class="layui-form-item">
		          <div class="layui-row">
		            <div class="layui-col-xs7">
		              <label class="layadmin-user-login-icon iconfont layui-extend-vercode" for="LAY-user-login-vercode"></label>
		              <input type="text" id="inputCode" placeholder="请输入图形验证码" autocomplete="off" class="layui-input">
		            </div>
		            <div class="layui-col-xs5">
		              <div style="margin-left: 10px;">
		                <img id="sessCode" src="authImg" alt="请输入验证码" title="看不清换一张" width="130" style="cursor:pointer;"/>
		              </div>
		            </div>
		          </div>
		        </div>
		        <div class="layui-form-item">
		          <button id="submit_username" class="layui-btn layui-btn-fluid">下一步</button>
		        </div>
	      	</div>
	      	<!-- 第二步填写邮箱验证码 -->
	        <div class="step-con step-email">
	        	 <div class="layui-form-item">
	        	 	<label class="layadmin-user-login-icon iconfont layui-extend-wode"></label>
		          	<input type="text" id="userType" value="" disabled autocomplete="off" class="layui-input">
	        	 </div>
	        	 <div class="layui-form-item">
	        	 	<label class="layadmin-user-login-icon iconfont layui-extend-youxiang"></label>
		          	<input type="text" id="email" value="" disabled autocomplete="off" class="layui-input">
	        	 </div>
	        	 <div class="layui-form-item">
		          <div class="layui-row">
		            <div class="layui-col-xs7">
		              <label class="layadmin-user-login-icon iconfont layui-extend-vercode" for="LAY-user-login-vercode"></label>
		              <input type="text" id="emailCodeInp" placeholder="请输入邮箱验证码" autocomplete="off" class="layui-input">
		            </div>
		            <div class="layui-col-xs5">
		              <div style="margin-left: 10px;">
		                <button id="sendEmailCode" class="btn layui-btn" style="width:100%;">发送验证码</button>
		              </div>
		            </div>
		          </div>
		        </div>
		        <div class="layui-form-item">
		          <button id="submit-email" class="layui-btn layui-btn-fluid">下一步</button>
		        </div>
	        </div>
	        <!-- 第三步根据填写的邮箱验证码向邮箱发送随机密码 -->
	        <div class="step-con step-succ">
	        	<p style="width:500px;margin-left:-75px;"><i class="iconfont layui-extend-success"></i>恭喜你，重置密码成功，系统已将随机密码发送至你邮箱，请用随机密码登录系统后尽快进行修改!
</p>
				<div class="layui-form-item">
					<a href="login.do?action=loginOut" style="width:100%;padding:0px;" class="btn layui-btn layui-btn-block">完成</a>
		        </div>
	        </div>
	        <div id="smFunDiv" class="layui-form-item" style="margin-bottom: 20px;">
	        	<a href="login.do?action=goSignPage" class="layadmin-link" style="float:left;margin-top:7px;">注册帐号</a>
	            <a href="login.do?action=loginOut" class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;">立即登录</a>
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
  		var count = 60
	  	layui.config({
			base: '/plugins/frame/js/'
		}).extend({ //设定组件别名
		    common:   'common'// 表示模块文件的名字
		}).use(["common","jquery","layer"],function(){
			var $ = layui.jquery,
				layer = layui.layer,
				common = layui.common;
			$("#sessCode").on('click',function(){
				common.vercode();
			});
			var page = {
				data : {
					userType : "",
					userId : "",
					inpCode : "",
					userEmail : "",
					userTypeTxt : ""
				},
				init : function(){
					this.onLoad();
					this.bindEvent();
				},
				onLoad : function(){
					this.loadStepUsername();
				},
				bindEvent : function(){
					var _this = this;
					//第一步根据用户名加载邮箱
					$("#submit_username").on("click",function(){
						var account = $.trim($("#account").val());
						var vCode = $.trim($("#inputCode").val());
						if(account == ""){
							layer.msg('请输入用户名',{icon:5,anim:6,time:1000});
							$("#account").focus().addClass("layui-form-danger");
						}else if(vCode == ""){
							layer.msg('请输入验证码',{icon:5,anim:6,time:1000});
							$("#inputCode").focus().addClass("layui-form-danger");
						}else{
							layer.load();
							$.ajax({
								type:"post",
						        async:false,
						        dataType:"json",
						        url:"login.do?action=getUserEmailInfo",
						        data:{account:account,vCode:vCode},
						        success:function (json){
						        	layer.closeAll('loading');
						        	//加载邮箱层
						        	proccessEmail(json,_this);
						        }
							});
						}
					});
					//第二步根据邮箱获取邮箱验证码
					$("#sendEmailCode").on("click",function(){
						showCountDown();
						$.ajax({
							type:"post",
					        async:false,
					        dataType:"json",
					        url:"login.do?action=sendSysEmailCode",
					        data:{userEmail:_this.data.userEmail},
					        success:function (json){
					        	proccessEmailCode(json,_this);
					        }
						});
					});
					//下一步（邮箱）
					$("#submit-email").on("click",function(){
						var emailCodeInp = $.trim($("#emailCodeInp").val());
						if(emailCodeInp == ""){
							layer.msg("邮箱验证码不能为空", {icon:5,anim:6,time:1000});
						}else{
							$.ajax({
								type:"post",
						        async:false,
						        dataType:"json",
						        url:"login.do?action=checkInputPass",
						        data:{
						        	userType:_this.data.userType,
						        	inpCode:emailCodeInp,
						        	userEmail:_this.data.userEmail,
						        	userId : _this.data.userId
						        },
						        success:function (json){
						        	console.log(json)
						        	proccessSendEmail(json,_this);
						        }
							});
						}
					});
				},
				
				//加载用户名输入
				loadStepUsername : function(){
					$(".step-username").show();
				},
				//加载邮箱层
				loadStepEmail : function(){
					if(this.data.userType == "appUser"){
						this.data.userTypeTxt = "申请专利(人/公司)用户";
					}else if(this.data.userType == "cpyUser"){
						this.data.userTypeTxt = "代理机构用户";
					}
					$(".step-username").hide().siblings(".step-email").show()
						.find("#userType").val("您的账户类型：" + this.data.userTypeTxt);
					$(".step-email").find("#email").val("您注册的邮箱：" + this.data.userEmail);
				},
				//获取邮箱验证码成功
				loadStepSucc : function(){
					layer.msg("密码重置成功！",{icon:1,time:1000},function(){
						$("#smFunDiv").hide();
						$(".step-email").hide().siblings(".step-succ").show();
					});
				}
			};
			//一分钟只允许发送一次
			function showCountDown(){
				$("#sendEmailCode").attr("disabled",true).html(count + "秒后点击获取");
				count--;
				if(count > 0){
					var _this = this;
					setTimeout(showCountDown, 1000);
				}else{
					$("#sendEmailCode").html("点击获取验证码").attr("disabled",false);
					count = 60;
				}
			}
			//加载邮箱层
			function proccessEmail(list,that){
				if(list["result"] == "success"){
					that.data.userType = list.userType;
					that.data.userId = list.id;
					that.data.userEmail = list.userEmail;
					that.loadStepEmail();
				}else if(list["result"] == "vercodeFail"){
					layer.msg("验证码错误", {icon:5,anim:6,time:1000});
					$("#inputCode").focus().addClass("layui-form-danger");
				}else if(list["result"] == "typeNull"){
					layer.msg("用户类型为空", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "userTypeError"){
					layer.msg("用户类型错误", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "accountNull"){
					layer.msg("账号为空", {icon:5,anim:6,time:1000});
					$("#inputCode").focus().addClass("layui-form-danger");
				}else if(list["result"] == "noInfo"){
					layer.msg("该账号不存在", {icon:5,anim:6,time:1000});
				}
			}
			//获取发送邮箱验证码反馈回来的状态
			function proccessEmailCode(list,that){
				if(list["result"] == "success"){
					layer.msg("验证码已发送到您的邮箱，请查收");
				}else if(list["result"] == "noEmail"){//没有邮箱
					layer.msg("暂未绑定邮箱", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "noSend"){
					layer.msg("一分钟内只能发送一次", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "sendFail"){
					layer.msg("发送邮箱失败", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "emailError"){
					layer.msg("邮箱格式错误", {icon:5,anim:6,time:1000});
				}
			}
			//根据用户输入的验证码来向用户邮箱发送随机密码
			function proccessSendEmail(list,that){
				if(list["result"] == "success"){//邮箱发送成功
					that.loadStepSucc();
				}else if(list["result"] == "senfFail"){//没有邮箱
					layer.msg("邮件发送失败", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "noUser"){//查无此人
					layer.msg("该账号不存在", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "error"){
					layer.msg("验证码无效或已被使用", {icon:5,anim:6,time:1000});
				}else if(list["result"] == "emailError"){
					layer.msg("邮箱格式错误", {icon:5,anim:6,time:1000});
				}
			}
			$(function(){
				page.init();
			});
		});
  	</script>
  </body>
</html>
