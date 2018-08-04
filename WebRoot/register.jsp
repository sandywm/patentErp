<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利管理系统--用户注册</title>
    <link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<link href="/css/diyReset.css" rel="stylesheet" type="text/css"/>
	<link href="/css/register.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js"></script>
  </head>
  <body>
  	  	<div class="layui-container registerWrap">
	      <div class="regHeader">
	        <h2>专利管理系统</h2>
	        <p>Patent management system</p>
	      </div>
      	 <!-- 第一步 选择身份 -->
      	 <div class="layui-row selectRole">
      	 	 <div class="layui-col-md4 layui-col-lg4 selRoleWrap">
      	 	 	<label for="agencyInp" class="inpLabel agentLabel">
     	 	 		<i class="iconfont layui-extend-jigou comRegIcon"></i>
     	 	 		<p>代理机构</p>
     	 	 		<input id="agencyInp" type="radio" name="selSignType" class="diyRadCheckInp" value="cpyUser" onclick="selSignType(this)">
     	 	 		<span><b></b></span>
     	 	 	</label>
      	 	 </div>
      	 	  <div class="layui-col-md4 layui-col-lg4 selRoleWrap">
      	 	  	<label for="perUserInp" class="inpLabel perUserLabel" attrRole="gr">
      	 	 		<i class="iconfont layui-extend-geren comRegIcon"></i>
      	 	 		<p>个人用户</p>
     	 	 		<input id="perUserInp" type="radio" name="selSignType" class="diyRadCheckInp" value="appUser" onclick="selSignType(this)">
     	 	 		<span><b></b></span>
     	 	 	</label>
      	 	 </div>
      	 	  <div class="layui-col-md4 layui-col-lg4 selRoleWrap">
      	 	  	<label for="compUserInp" class="inpLabel compUserLabel" attrRole="dzyx">
      	 	 		<i class="iconfont layui-extend-company comRegIcon"></i>
      	 	 		<p>公司用户</p>
     	 	 		<input id="compUserInp" type="radio" name="selSignType" class="diyRadCheckInp" value="appUser" onclick="selSignType(this)">
     	 	 		<span class=""><b></b></span>
     	 	 	</label>
      	 	 </div>
      	 </div>
      	 <div class="layui-row roleRegister layui-form" style="display:none;">
      	 	<input id="signType" name="signType" type="hidden" value=""/>
      	 	<div id="formWrap" class=" layui-col-md-8 layui-col-lg8"></div>
      	 	<div class="layui-col-md-4 layui-col-lg4 rightRole">
      	 		<div class="rolePart">
      	 			<i class="iconfont"></i>
      	 			<p></p>
      	 		</div>
      	 		<div class="hasAcc">
      	 			<p>如果您已经拥有帐号</p>
					<p>请 <a href="login.do?action=loginOut">点此登录</a></p>
      	 		</div>
      	 	</div>
   			<button class="layui-btn subBtn" lay-submit lay-filter="registerNow" style="display:none;">注册</button>
      	 </div>
      	 
      	 <div class="btnWrap">
      	 	<button id="prevBtn" class="layui-btn defBtn">上一步</button>
      	 	<button id="nextRegBtn" class="layui-btn">下一步</button>
      	 </div>
      	 
	    <div class="layui-trans layadmin-user-login-footer">
	      <p>© 2018 版权所有 Copyright@2018 Sandy.wm All Rights Reserved.</p>
	    </div>
  	</div>
	<script src="/plugins/jquery/jquery-1.7.2.min.js"></script>
  	<script src="/plugins/layui/layui.js"></script>
  	<script type="text/javascript">
  		var nowIndex = 0,
  			strInfo = "",
  			comName = "代理机构",
  			selGrComp="";//来判断选择是个人还是公司对应显示不同的图标
		layui.use(['form','jquery','layer'],function(){
			var	layer = layui.layer,
				form = layui.form,
				$ = layui.jquery;
			//切换显示右侧对应身份
			function showRolePicName(){
				var appTypeSelVal = $("#appTypeSel").val();
				var signType = $('#signType').val();
	  			if(signType == "appUser"){
	  				if(appTypeSelVal == "gr"){
	  					$(".rolePart").find("i").addClass("layui-extend-geren").removeClass("layui-extend-company layui-extend-jigou");
	  					$(".rolePart").find("p").html("当前选择用户：个人用户");
	  				}else{
	  					$(".rolePart").find("i").addClass("layui-extend-company").removeClass("layui-extend-geren layui-extend-jigou");
	  					$(".rolePart").find("p").html("当前选择用户：公司用户");
	  				}
	  			}else{
	  				$(".rolePart").find("i").addClass("layui-extend-jigou").removeClass("layui-extend-company layui-extend-geren");
	  				$(".rolePart").find("p").html("当前选择用户：代理机构");
	  			}
			}
			function renderForm(){
				var signType = $('#signType').val();
	  			var strHtml = '';
	  			if(signType == 'appUser'){
	  				strHtml += '<div class="layui-form-item"><label class="layui-form-label">注册类型</label>';
	  				strHtml += '<div class="layui-input-inline"><select id="appTypeSel" name="appType" lay-filter="appType"><option id="canChange_gr" value="gr">个人</option><option id="canChange_dz" value="dzyx">大专院校</option><option value="kydw">科研单位</option><option value="gkqy">工矿企业</option><option value="sydw">事业单位</option></select></div></div>';
	  			}
	  			//登录账号
	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label">登录账号</label>';
	  			strHtml += '<div class="layui-input-inline"><input id="accountInp" type="text" name="account" required lay-verify="judgeAcc" placeholder="请输入账号" autocomplete="off" class="layui-input"></div></div>';
	  			//登录密码
	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label">登录密码</label>';
	  			strHtml += '<div class="layui-input-inline"><input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input"></div></div>';
	  			//确认密码
	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label">确认密码</label>';
	  			strHtml += '<div class="layui-input-inline"><input type="password" name="comfirmPas" required lay-verify="required" placeholder="请再次确认密码" autocomplete="off" class="layui-input"></div></div>';
				//个人邮箱
	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label">个人邮箱</label>';
	  			strHtml += '<div class="layui-input-inline"><input type="email" name="email" lay-verify="email" placeholder="请输入个人邮箱" autocomplete="off" class="layui-input"></div></div>';
	  			if(signType == 'appUser'){
	  				//申请人名字(默认) 另一种就是公司名字
	  	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label canChange_name">申请人姓名</label>';
	  	  			strHtml += '<div class="layui-input-inline"><input id="applyerName" type="text" name="name" required  lay-verify="judegeName" placeholder="请输入申请人姓名" autocomplete="off" class="layui-input"></div></div>';
	  			}else if(signType == 'cpyUser'){
	  				//代理机构名称
	  	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label canChange_name">代理机构名称</label>';
	  	  			strHtml += '<div class="layui-input-inline"><input type="text" name="name" required  lay-verify="judegeCompName" placeholder="请输入代理机构名称" autocomplete="off" class="layui-input"></div></div>';
	  			}
	  			//联系人
	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label">联系人</label>';
	  			strHtml += '<div class="layui-input-inline"><input type="text" name="lxr" required  lay-verify="judegeName" placeholder="请输入联系人姓名" autocomplete="off" class="layui-input"></div></div>';
	  			//联系人电话
	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label canChange_tel">联系人电话</label>';
	  			strHtml += '<div class="layui-input-inline"><input type="tel" name="tel" lay-verify="phone" placeholder="请输入联系人手机号" autocomplete="off" class="layui-input"></div></div>';

	  			$('#formWrap').html(strHtml);
	  			$('.rightRole').height($('#formWrap').height());
	  			form.render();
	  		}
			form.on('select(appType)', function(data){
				var value = data.value;
				if(value != 'gr'){
					$('.canChange_name').html('公司名称');
					$('input[name="name"]').attr('placeholder','请输入公司名称').attr('lay-verify','judegeCompName');
				}else{
					$('.canChange_name').html('申请人姓名');
					$('input[name="name"]').attr('placeholder','请输入申请人姓名').attr('lay-verify','judegeName');
				}
				showRolePicName();
			});
			$(".agentLabel").hover(function(){
				layer.tips("帮代理撰写专利，申请专利的机构", ".layui-extend-jigou", {tips:[2,'#FF8000'],time:0});
			},function(){
				layer.closeAll('tips'); 
			});
			$(".perUserLabel").hover(function(){
				layer.tips("想申请专利的个人用户", ".layui-extend-geren", {tips:[4,'#FF8000'],time:0});
			},function(){
				layer.closeAll('tips'); 
			});
			$(".compUserLabel").hover(function(){
				layer.tips("想申请专利的公司用户", ".layui-extend-company", {tips:[4,'#FF8000'],time:0});
			},function(){
				layer.closeAll('tips'); 
			});
			$("#prevBtn").on('click',function(){
				if(nowIndex == 0){
					return;
				}
				$(".roleRegister").hide();
				$(".selectRole").show().removeClass("layui-anim-fadeout");
				$("#nextRegBtn").show();
				$("#prevBtn").addClass("defBtn");
				nowIndex = 0;
			});
			$("#nextRegBtn").on('click',function(){
				goRegister();
			});
			function goRegister(){
				if(nowIndex == 0){//代表第一步选择注册类型
					var signType = $("#signType").val();
					if(signType == ""){
						layer.msg("请选择您要注册的类型", {icon:5,anim:6,time:1000});
						return;
					}
					if(signType == "cpyUser"){
						comName = "代理机构";
					}else{
						comName = "公司";
					}
					$(".selectRole").addClass("layui-anim layui-anim-fadeout").hide();
					$(".roleRegister").show().removeClass("layui-anim-fadeout").addClass("layui-anim layui-anim-fadein");
					$("#prevBtn").removeClass("defBtn");
		  			renderForm();
		  			showRolePicName();
		  			if(selGrComp == "gr"){
		  				$("#canChange_gr").val("gr").html("个人");
		  				$("#canChange_dz").val("dzyx").html("大专院校");
		  				$(".layui-select-title input").val("个人");
		  				$(".canChange_name").html("申请人姓名");
						$("input[name='name']").attr("placeholder","请输入申请人姓名").attr("lay-verify","judegeName");
		  				$(".rolePart").find("i").addClass("layui-extend-geren").removeClass("layui-extend-company layui-extend-jigou");
	  					$(".rolePart").find("p").html("当前选择用户：个人用户");
	  					
		  			}else if(selGrComp == "dzyx"){
		  				$("#canChange_gr").val("dzyx").html("大专院校");
		  				$("#canChange_dz").val("gr").html("个人");
		  				$(".layui-select-title input").val("大专院校");
		  				$(".canChange_name").html("公司名称");
						$("input[name='name']").attr("placeholder","请输入公司名称").attr("lay-verify","judegeCompName");
		  				$(".rolePart").find("i").addClass("layui-extend-company").removeClass("layui-extend-geren layui-extend-jigou");
	  					$(".rolePart").find("p").html("当前选择用户：公司用户");
		  			}
		  			
		  			regPage.init();
		  			$("#nextRegBtn").hide();
		  			$(".subBtn").show();
					nowIndex++;	
				}
			}
			//自定义表单验证
			form.verify({
				judgeAcc : function(value){
					var reg = /[\u4e00-\u9fa5]+/,
						regSpec = /^[a-zA-z0-9\u4E00-\u9FA5]*$/;
					if(value == ''){
						return '注册账号不能为空';
					}else if(reg.test(value)){
						return '注册账号不能为中文';
					}else if(!regSpec.test(value)){
						return '注册账号不能有特殊字符、标点、或空格';
					}else if(value.length < 4){
						return '注册账号长度不能小于4个字符';
					}
				},
				judegeName : function(value, item){
					var reg = /^[\u4E00-\u9FA5]+$/; 
					if(value == ''){
						return '联系人姓名不能为空';
					}else if(!reg.test(value)){
				      return '联系人应为汉字';
				    }else if(value.length < 2 || value.length > 4){
				    	 return '联系人姓名最少应为2个字符最多为4个字符';
				    }
				},
				judegeCompName : function(value){
					var reg = /^[\u4E00-\u9FA5]+$/; 
					if(value == ''){
						return comName + '名称不能为空';
					}else if(!reg.test(value)){
				      return comName + '名称应为汉字';
				    }else if(value.length < 4 || value.length > 20){
				    	 return comName + '名称最少为4个字符最多为20个字符';
				    }
				}
			});
			//表单提交
			form.on('submit(registerNow)',function(data){
		  		var field = data.field;
		  		for(var attr in field){
		  			if(attr == "lxr" || attr == "name"){
		  				field[attr] = $.trim(escape(field[attr]));
		  			}else if(attr == "password" || attr == "comfirmPas"){
		  				field[attr] = field[attr].replace(/\s/g,"");
		  			}
		  		}
		  		if(field.password !== field.comfirmPas){
		  	       return layer.msg("两次密码输入不一致",{icon:5,anim:6,time:1000});
		  	    }
		  		layer.load();
		    	$.ajax({
		    		type:"post",
			        async:false,
			        dataType:"json",
			        url:"login.do?action=sign",
			        data:field,
			        success:function (json){
			        	layer.closeAll('loading');
			        	if(json["result"] == "success"){
			        		layer.msg("注册成功",{icon:1,time:1000},function(){
			        			window.location.href = "login.do?action=loginOut";
			        		});
			        	}else if(json["result"] == "unlaw"){
			        		layer.msg("账号含有非法字符,请从新填写",{icon:5,anim:6,time:1000});
			        		$("#accountInp").focus().addClass("layui-form-danger");
			        	}else if(json["result"] == "fail"){
			        		layer.msg("注册失败",{icon:5,anim:6,time:1000});
			        	}else if(json["result"] == "exist"){
			        		layer.msg("该账号已存在，请从新输入",{icon:5,anim:6,time:1000});
			        		$("#accountInp").focus().addClass("layui-form-danger");
			        	}
			        }
		    	});
		  	});
		});
		
		var regPage = {
			init :function(){
				this.bindEvent();
			},
			bindEvent : function(){
				var _this = this;
				$("#accountInp").focus(function(){
					layer.tips("账号由4-12个字符(字母，数字，下划线组成)", "#accountInp", {tips:[2,'#FF8000'],time:0});
				});
				$("input[name = 'password']").focus(function(){
					layer.tips("密码由6-16个字符(字母，数字，符号组成),不能输入空格", "input[name = 'password']", {tips:[2,'#FF8000'],time:0});
				}); 
				$("input[name = 'email']").focus(function(){
					layer.tips("用于找回密码使用，请务必认真填写！", "input[name = 'email']", {tips:[2,'#FF8000'],time:0});
				}); 
				$("input[name = 'password']").blur(function(){layer.closeAll('tips');});
				$("input[name = 'email']").blur(function(){layer.closeAll('tips');});
				$("#accountInp").blur(function(){
					layer.closeAll('tips'); 
					var account = $.trim($(this).val());
					if(!account){return;}//用户名为空不做验证
					var flag = checkExistAcc(account);
					if(flag){
						layer.msg(strInfo,{icon:5,anim:6,time:1000});
					}
				});
			}
		};
		//检测用户名是否已注册
		function checkExistAcc(userName){
			var flagExist = false,
				signType = $("#signType").val();
			$.ajax({
		        type:"post",
		        async:false,
		        dataType:"json",
		        url:"login.do?action=checkAccount",
		        data:{signType:signType,account:userName},
		        success:function (json){
		        	if(json["result"] == "exist"){//代表账号已经注册
		        		flagExist = true;
		        		strInfo = "该账号已存在，请从新输入";
		        	}else if(json["result"] == "unlaw"){
		        		flagExist = true;
		        		strInfo="账号含有非法字符,请从新输入";
		        	}else if(json["reslut" == "fail"]){
		        		flagExist = true;
		        		strInfo="异常错误";
		        	}
		        }
		    });
			return flagExist;
		}
		//选择signType
		function selSignType(obj){
			$(".inpLabel").find("i").removeClass("hasSelColor");
			$(".inpLabel").find("span").removeClass("layui-anim layui-anim-scaleSpring current");
			$(obj).parent().find("i").addClass("hasSelColor");
			$(obj).parent().find("span").addClass("layui-anim layui-anim-scaleSpring current");
			$("#signType").val($(obj).val());
			selGrComp = $(obj).parent().attr("attrRole");
		};

  	</script>
  </body>
</html>
	