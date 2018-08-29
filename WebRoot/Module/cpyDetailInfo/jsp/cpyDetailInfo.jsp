<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>代理机构信息修改</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="keywords" content="专利管理系统,代理机构信息修改">
	<meta http-equiv="description" content="代理机构信息修改">
	<link href="/Module/userManager/css/formInp.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/cpyDetailInfo/css/cpyBasicInfo.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
	<script src="http://at.alicdn.com/t/font_787377_fddbgkpmuto.js"></script>
  </head>
  <body style="background:#f2f2f2;color:#666;">
  	 <div class="layui-fluid" style="margin-top:15px;">
  	 	<input id="provVal" type="hidden"/>
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div id="cpyTxtTit" class="layui-card-header">查看代理机构基本信息</div>
  					<div class="layui-card-body" pad15>
  						<div id="cpyInfo" class="layui-form"></div>
  						<div id="basicInfoDiv"></div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	 
    <script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
    	var abilityFlag = "${requestScope.abilityFlag}";
    	layui.config({
			base : '/plugins/frame/js/'
		}).use(['layer','form','jquery','address'],function(){
   			var layer = layui.layer,
   				$ = layui.jquery,
   				form = layui.form,
   				address = layui.address();
   			var page = {
   				init : function(){
   					this.onLoad();
   				},
   				onLoad : function(){
   					//获取代理机构基本信息
  					layer.load("1");
  					$.ajax({
  						type:"post",
				        async:false,
				        dataType:"json",
				        url:"cpyManager.do?action=getCpyDetailInfo",
				        success:function (json){
				        	layer.closeAll("loading");	
				        	//回填基本信息
				        	getUserInfo(json);
				        }
  					});
   				}
   			};
   			function renderBasicInfo(list){
   				$("#cpyTxtTit").html("查看代理机构基本信息");
   				var strHtml = "";
   				var	jiangpaiHtml = "";
   				strHtml += '<ul>';
   				strHtml += '<li class="cpy_Name">'+ list.cpyName +'</li>';
   				strHtml += '<li><div class="innerPar"><p>公司法人</p><p>'+ list.cpyFr +'</p></div>';
   				strHtml += '<div class="innerPar"><p><span>公司热度</span><i class="iconfont layui-extend-wenhao" onclick="showHotStatusNote()"></i></p><p>'+ list.hotStatus +'</p></div></li>';
   				strHtml += '<li><div class="innerPar"><p>账号注册时间</p><p>'+ list.signDate +'</p></div>';
   				if(list.cpyLevel == '铜牌'){
					jiangpaiHtml += '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-tongpai-N"></use></svg>';
				}else if(list.cpyLevel == '银牌'){
					jiangpaiHtml += '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-yinpai-N"></use></svg>';
				}else if(list.cpyLevel == '金牌'){
					jiangpaiHtml += '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-jinpai-N"></use></svg>';
				}else if(list.cpyLevel == '钻石'){
					jiangpaiHtml += '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-zuanshi"></use></svg>';
				}
   				strHtml += '<div class="innerPar"><p>会员等级</p><p>'+ jiangpaiHtml +' '+ list.cpyLevel +'</p></div></li>';
   				strHtml += '<li><div class="innerPar"><p>公司组织机构代码</p><p>'+ list.cpyYyzz +'</p></div>';
   				if(list.cpyLevel != '铜牌'){
   					if(list.endFlag){
   						strHtml += '<div class="innerPar"><p>会员到期时间</p><p>'+ list.endDate +' (<span class="endDateColor">会员已到期</span>)</p></div></li>';
   					}else{
   						strHtml += '<div class="innerPar"><p>会员到期时间</p><p>'+ list.endDate +'</p></div></li>';
   					}
   				}else{
   					strHtml += '<div class="innerPar"><p>会员到期时间</p><p>永久有效</p></div></li>';
   				}
				strHtml += '<li><div class="innerPar"><p>公司联系人</p><p>'+ list.cpyLxr +'</p></div>';
   				strHtml += '<div class="innerPar"><p>联系人电话</p><p>'+ list.lxrTel +'</p></div></li>';
   				strHtml += '<li><div class="innerPar"><p>联系人邮箱</p><p>'+ list.lxrEmail +'</p></div>';
   				strHtml += '<div class="innerPar"><p>所在地区</p><p>'+ list.cpyProv +' '+ list.cpyCity +'</p></div></li>';
   				strHtml += '<li><div class="innerPar"><p>联系地址</p><p>'+ list.cpyAddress +'</p></div>';
   				strHtml += '<div class="innerPar"><p>公司网址</p><p>'+ list.cpyUrl +'</p></div></li>';
   				strHtml += '<li class="specHeiLi"><div class="innerPar" style="width:100%;"><p>公司简介</p><p>'+ list.cpyProfile +'</p></div></li>';
   				strHtml += '</ul>';
   				$("#basicInfoDiv").html(strHtml);
   			}
   			function renderForm(list){
   				var strHtml = "";
   				var	jiangpaiHtml = "";
   				$("#cpyTxtTit").html("代理机构基本信息修改");
   				//注册时间
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">注册账号时间</label>';
				strHtml += '<div class="layui-input-inline"><input type="text" name="signDate" value="'+ list.signDate +'" disabled class="layui-input"></div></div>';
				//试用到期时间
				if(list.cpyLevel != '铜牌'){
					strHtml += '<div class="layui-form-item"><label class="layui-form-label">会员到期时间</label>';
					if(list.endFlag){
   						strHtml += '<div class="layui-input-inline"><input type="text" name="endDate" value="'+ list.endDate +'" disabled class="layui-input"></div>';
   						strHtml += '<div class="layui-form-mid layui-word-aux"><span class="endDateColor">会员已到期</span></div></div>';
					}else{
   						strHtml += '<div class="layui-input-inline"><input type="text" name="endDate" value="'+ list.endDate +'" disabled class="layui-input"></div></div>';
   					}
				}
				//公司名字
  				strHtml += '<div class="layui-form-item"><label class="layui-form-label">公司名字</label>';
  				strHtml += '<div class="layui-input-inline"><input type="text" name="cpyName" value="'+ list.cpyName +'" disabled class="layui-input"></div>';
  				strHtml += '<div class="layui-form-mid layui-word-aux"><i id="redu" class="iconfont layui-extend-huo"></i><input type="hidden" id="reduInp" name="hotStatus" value="'+ list.hotStatus +'" disabled></div></div>';
  				//当前会员等级
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">会员等级</label>';
				if(list.cpyLevel == '铜牌'){
					jiangpaiHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-tongpai-N"></use></svg>';
				}else if(list.cpyLevel == '银牌'){
					jiangpaiHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-yinpai-N"></use></svg>';
				}else if(list.cpyLevel == '金牌'){
					jiangpaiHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-jinpai-N"></use></svg>';
				}else if(list.cpyLevel == '钻石'){
					jiangpaiHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-zuanshi"></use></svg>';
				}
				strHtml += '<div class="layui-input-inline">'+ jiangpaiHtml +'<p>'+ list.cpyLevel +'</p><input type="hidden" name="cpyLevel" value="'+ list.cpyLevel +'" disabled></div></div>';
  				//公司法人
   				strHtml += '<div class="layui-form-item"><label class="layui-form-label">公司法人</label>';
  				strHtml += '<div class="layui-input-inline"><input type="text" name="cpyFr" value="'+ list.cpyFr +'" placeholder="请输入公司法人姓名" lay-verify="judegeName" class="layui-input" autocomplete="off"  maxlength="4"></div></div>';
  				//公司组织机构代码
  				strHtml += '<div class="layui-form-item"><label class="layui-form-label">公司组织机构代码</label>';
  				strHtml += '<div class="layui-input-inline"><input type="text" name="cpyYyzz" value="'+ list.cpyYyzz +'" required placeholder="请输入公司组织机构代码" autocomplete="off" class="layui-input" maxlength="30"></div></div>';
  				//公司联系人
  				strHtml += '<div class="layui-form-item"><label class="layui-form-label">公司联系人</label>';
				strHtml += '<div class="layui-input-inline"><input type="text" name="cpyLxr" value="'+ list.cpyLxr +'" required placeholder="请输入联系人姓名" lay-verify="judegeName" autocomplete="off" class="layui-input" maxlength="4"></div></div>';
  				//联系人电话
  				strHtml += '<div class="layui-form-item"><label class="layui-form-label">联系人手机号码</label>';
				strHtml += '<div class="layui-input-inline"><input type="text" name="lxrTel" value="'+ list.lxrTel +'" required placeholder="请输入手机号码" lay-verify="phoneNum" autocomplete="off" class="layui-input" maxlength="11"></div></div>';
				//联系人邮箱
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">联系人邮箱</label>';
				strHtml += '<div class="layui-input-inline"><input type="text" name="lxrEmail" value="'+ list.lxrEmail +'" required lay-verify="email" placeholder="请输入联系人邮箱" autocomplete="off" class="layui-input"></div></div>';
				//选择省、市
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">选择地区</label>';
				if(list.cpyProv != '' && list.cpyCity != ''){
					strHtml += '<div class="layui-input-inline"><input id="provInp" name="cpyProv" value="'+ list.cpyProv +'" type="hidden"/><select name="cpyProvSel" lay-verify="judgeProv" lay-filter="province" class="province"><option value="">'+ list.cpyProv +'</option></select></div>';
					strHtml += '<div class="layui-input-inline"><input id="cityInp" name="cpyCity" value="'+ list.cpyCity +'" type="hidden"/><select name="cpyCitySel" lay-verify="judgeCity" lay-filter="city"><option value="">'+ list.cpyCity +'</option></select></div></div>';
				}else{
					strHtml += '<div class="layui-input-inline"><input id="provInp" name="cpyProv" type="hidden"/><select name="cpyProvSel" required lay-verify="judgeProv" lay-filter="province" class="province"><option value="">请选择省</option></select></div>';
					strHtml += '<div class="layui-input-inline"><input id="cityInp" name="cpyCity" type="hidden"/><select name="cpyCitySel" required lay-verify="judgeCity" lay-filter="city" disabled><option value="">请选择市</option></select></div></div>';
				}
				//具体地址
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">联系地址</label>';
				strHtml += '<div class="layui-input-inline"><input type="text" name="cpyAddress" value="'+ list.cpyAddress +'" required placeholder="请输入联系地址" lay-verify="judegeAddress" autocomplete="off" class="layui-input"></div></div>';
				//公司网址
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">公司网址</label>';
				strHtml += '<div class="layui-input-inline"><input type="text" name="cpyUrl" value="'+ list.cpyUrl +'" placeholder="请输入公司地址" lay-verify="judgeWebSite" autocomplete="off" class="layui-input"></div></div>';
				//公司简介
				strHtml += '<div class="layui-form-item"><label class="layui-form-label">公司简介</label>';
				strHtml += '<div class="layui-input-inline"><textarea name="cpyProfile" value="'+ list.cpyProfile +'" placeholder="请输入公司简介(200字以内)" lay-verify="judgejianjie" class="layui-textarea" maxlength="200">'+ list.cpyProfile +'</textarea></div></div>';
				strHtml += '<div class="layui-form-item"><label class="layui-form-label"></label><div class="layui-input-inline"><button class="layui-btn" lay-submit lay-filter="setCpyInfo">保存修改</button></div></div>';
  				$('#cpyInfo').html(strHtml);
  				form.render();
   			}
   			
   			function getUserInfo(list){
   				if(list["result"] == "success"){
   					if(abilityFlag == "true"){
   		  				renderForm(list);
   		  				if(list.cpyProv != ""){
   			  				setTimeout(function(){
   			  					$("select[name='cpyProvSel']").next().find("input").val(list.cpyProv);
   			  					$("select[name='cpyCitySel']").next().find("input").val(list.cpyCity);
   			  					$(".layui-disabled").css({"color":"black"});
   			  				},100);
   		  				}
   					}else{
   						renderBasicInfo(list);
   					}
  				}else if(list["result"] == "error"){
  					$('#cpyInfo').html("<div class='noThisPeo'><i class='iconfont layui-extend-noData'></i><p>加载基本信息出错，请<a href='cpyManager.do?action=goCpyDetailPage'>刷新试下</a></p></div>");
  				}
   			}
   			form.verify({
   				judegeName : function(value){
					var reg = /^[\u4E00-\u9FA5]+$/; 
					if(value == ''){
						return '姓名不能为空';
					}else if(!reg.test(value)){
				      return '姓名应为汉字';
				    }else if(value.length < 2 || value.length > 4){
				    	 return '姓名最少应为2个字符最多为4个字符';
				    }
				},
				phoneNum : function(value){
					var v= value.replace(/ /g,"");
					if(v!='' && v.length == 11){
						var reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
						if(!reg.test(value)){
							return '手机号码格式不对,请从新输入';
						}
					}else{
						return '手机号码格式不对,请从新输入';
					}
				},
				judegeAddress : function(value){
					if(value == ''){
						return '联系地址不能为空';
					}else{
						if(value.length < 4){
							return '请如实填写地址';
						}
					}
				},
				judgejianjie : function(value){
					if(value != ''){
						if(value.length < 10){
							return '请如实公司简介';
						}
					}
				},
				judgeProv : function(value){
					var value = $("#provInp").val();
					if(value == ''){
						return '请选择省';
					}
				},
				judgeCity : function(){
					var value = $("#cityInp").val();
					if(value == ''){
						return '请选择市';
					}
				},
				judgeWebSite : function(value){
					var reg = /(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/;
					if(value != ""){
						if(!reg.test(value)){
							return '网址格式错误，请输入以http://或https://开头的完整url';
						}
					}
				}
   			});
   			form.on('submit(setCpyInfo)',function(data){
   				var field = data.field;
   				for(var attr in field){
		  			if(attr == "cpyFr" || attr == "cpyLxr" || attr == "cpyProv" || attr == "cpyCity" || attr == "cpyAddress" || attr == "cpyProfile"){
		  				field[attr] = $.trim(escape(field[attr]));
		  			}
		  		}
   				$.ajax({
		    		type:"post",
			        async:false,
			        dataType:"json",
			        url:"cpyManager.do?action=updateCpyBasicInfo",
			        data:field,
			        success:function (json){
			        	layer.closeAll('loading');
			        	if(json["result"] == "success"){
			        		layer.msg("保存成功",{icon:1,time:1000},function(){
			        			//form.render("select");
			        		});
			        	}else if(json["result"] == "error"){
			        		layer.msg("保存失败，请重试！",{icon:5,anim:6,time:1000});
			        	}else if(json["result"] == "noAbility"){
			        		layer.msg("您暂无权限",{icon:5,anim:6,time:1000});
			        	}
			        }
		    	});
   			});
   			$(function(){
   				page.init();
   			});
   			$('#redu').hover(function(){
   				layer.tips('热度值：' + $('#reduInp').val(), '#redu', {tips:[1,'#FF8000'],time:0});
			},function(){
				layer.closeAll('tips'); 
			});
   		});
		function showHotStatusNote(){
			layer.tips('公司热度意思是公司完成的专利数，公司每次为客户撰写一个专利，热度加0.1，依次可作为专利公司热度搜索排名', '.layui-extend-wenhao', {
			  tips: [2, '#3595CC'],
			  time: 6000
			});	
		}
    </script>
  </body>
</html>
