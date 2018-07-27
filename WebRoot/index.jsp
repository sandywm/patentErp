<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
  <head>
    <title>专利管理系统</title>
	<link id="resetLink" rel="stylesheet" type="text/css"/>
	<script src="Module/commonJs/jquery-1.7.2.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	//验证码
	function verCode(){
		var obj = document.getElementById("sessCode");
		obj.src = "authImg?code="+Math.random()+100;
	}
	//登录
	function login(){
		var account = $("#account").val();
		var password = $("#password").val();
		var vCode = $("#inputCode").val();
		var loginType = $("#loginType").val();
		if(account == ""){
			alert("账号不能为空");
		}else if(password == ""){
			alert("密码不能为空");
		}else{
			$.ajax({
		        type:"post",
		        async:false,
		        dataType:"json",
		        url:"login.do?action=login",
		        data:{account:account,password:password,vCode:vCode,loginType:loginType},
		        success:function (json){
		        	proccessLogin(json,loginType);
		        }
		    });
		}
	}
	//处理登录
	function proccessLogin(list,loginType){
		if(list["result"] == "success"){
			if(loginType == "cpyUser"){
				var roleObj =	 list["roleList"];
				var roleLength = roleObj.length;
				if(roleLength == 1){//一种身份
					window.location.href = "login.do?action=goPage&roleId="+roleObj[0].roleId+"&loginType="+loginType;
				}
			}else if(loginType == "appUser"){
				window.location.href = "login.do?action=goPage&loginType="loginType;
			}
		}else if(list["result"] == "lock"){
			alert("账号无效");
		}else if(list["result"] == "fail"){
			alert("账号密码错误");
		}else if(list["result"] == "vercodeFail"){
			alert("验证码错误");
		}else if(list["result"] == "roleErr"){
			alert("登录异常");
		}
	}
	</script>
  </head>
  
  <body>
    
    <select id="loginType">
    	<option value="cpyUser">代理机构用户</option>
    	<option value="appUser">申请专利（人/公司）用户</option>
    </select>
          账号：<input type="text" id="account"/>
          密码：<input type="password" id="password"/>
          验证码：<input type="text" id="inputCode"/>
    <img id="sessCode" src="authImg" alt="请输入验证码" width="110"/>
	<a href="javascript:void(0)" onclick="verCode()">看不清，换一张</a>
	<input type="button" onclick="login();" value="登录"/>
  </body>
</html>
