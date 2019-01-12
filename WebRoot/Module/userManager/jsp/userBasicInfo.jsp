<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
  <head>
    <title>个人资料设置</title>
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="keywords" content="用户信息修改,编辑，保存">
	<meta http-equiv="description" content="个人信息修改">
	<link href="/Module/userManager/css/formInp.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
  </head>
  <body style="background:#f2f2f2;color:#666;">
  	<div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header">设置我的资料</div>
  					<div class="layui-card-body" pad15>
  						<div id="setPerInfo" class="layui-form"></div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
  	<script type="text/javascript">
  		var fieldList = [],loginType = parent.loginType;
  		layui.config({
			base: '/plugins/frame/js/'
		}).extend({ //设定组件别名
		    common: 'common'
		}).use(['layer','jquery','form','common'],function(){
  			var layer = layui.layer,
  				$ = layui.jquery,
  				form = layui.form,
  				common = layui.common;
  			
  			var page = {
  				init : function(){
  					this.onLoad();
  				},
  				onLoad : function(){
  					//获取用户信息
  					layer.load("1");
  					var userInfoList = JSON.parse(sessionStorage.getItem('userInfo'));
  					getUserInfo(userInfoList);
  				}
  			};
  			//渲染form
  			function renderForm(list){
  				/*type: 个人/公司，代理机构员工（包括管理员） ，平台用户*/
  				//console.log(list)
  				var roleName = parent.roleName,
  					loginType = parent.loginType,
  					strHtml = "",
  					cnName = "申请人";
  				if(list.roleName == "gr"){
  					list.roleName = "个人";
  				}else if(list.roleName == "dzyx"){
  					list.roleName = "大专院校";
  				}else if(list.roleName == "kydw"){
  					list.roleName = "科研单位";
  				}else if(list.roleName == "gkqy"){
  					list.roleName = "工矿企业";
  				}else if(list.roleName =="sydw"){
  					list.roleName = "事业单位";
  				}else if(list.roleName == "super"){
  					list.roleName = "超级管理员";
  				}
				if(loginType == 'cpyUser'){
					strHtml += '<div class="layui-form-item"><label class="layui-form-label">当前身份</label>';
					strHtml += '<div class="layui-input-inline"><input type="text" value="'+ parent.roleName +'" disabled class="layui-input"></div></div>';
					//账户身份
					strHtml += '<div class="layui-form-item"><label class="layui-form-label">拥有身份</label>';
					strHtml += '<div class="layui-input-inline"><input type="text" value="'+ list.roleName +'" disabled class="layui-input"></div></div>';
				}
  				//注册账号
	  			strHtml += '<div class="layui-form-item"><label class="layui-form-label">注册账号</label>';
	  			if(loginType == 'cpyUser' || loginType == 'spUser'){
	  				strHtml += '<div class="layui-input-inline"><input type="text" name="account" value="'+ list.account +'" disabled class="layui-input"></div></div>';
	  			}else{
	  				strHtml += '<div class="layui-input-inline"><input type="text" name="account" value="'+ list.appAccount +'" disabled class="layui-input"></div></div>';
	  			}
  				//申请人姓名/公司名称/管理员姓名/平台用户个人姓名
  				if(loginType == 'cpyUser' || loginType == 'spUser'){
  					//管理员姓名
  					strHtml += '<div class="layui-form-item"><label class="layui-form-label">个人姓名</label>';
  					strHtml += '<div class="layui-input-inline"><input type="text" name="name" value="'+ list.name +'" class="layui-input" placeholder="请输入您的真实姓名" lay-verify="judegeName" autocomplete="off" maxlength="4"></div></div>';
  				}else{
  					if(list.roleName != '个人'){
  						strHtml += '<div class="layui-form-item"><label class="layui-form-label">公司名称</label>';
  						strHtml += '<div class="layui-input-inline"><input type="text" name="name" value="'+ list.name +'" class="layui-input" lay-verify="judegeCompName" placeholder="请输入公司名称" autocomplete="off" maxlength="20"></div></div>';
  					}else{
  						//个人 、 代理机构下员工的个人姓名
  						strHtml += '<div class="layui-form-item"><label class="layui-form-label">个人姓名</label>';
  						strHtml += '<div class="layui-input-inline"><input type="text" name="name" value="'+ list.name +'" class="layui-input" lay-verify="judegeName" placeholder="请输入您的真实姓名" autocomplete="off" maxlength="4"></div></div>';
  					}
  				}
  				//appICard 电话 邮箱
  				if(loginType == 'appUser' && roleName == '申请人/公司'){
  					//var cnName = '';
  					if(list.roleName == '个人'){
  						strHtml += '<div class="layui-form-item"><label class="layui-form-label">个人身份证号码</label>';
  						strHtml += '<div class="layui-input-inline"><input type="text" name="appiCard" value="'+ list.appICard +'" required placeholder="请输入个人身份证号码" lay-verify="identity" autocomplete="off" class="layui-input"></div></div>';
  					}else{
  						cnName = '联系人';
  						strHtml += '<div class="layui-form-item"><label class="layui-form-label">公司组织机构代码</label>';
  						strHtml += '<div class="layui-input-inline"><input type="text" name="appiCard" value="'+ list.appICard +'" required placeholder="请输入组织公司机构代码" lay-verify="judegeOrgCode" autocomplete="off" class="layui-input"></div></div>';
  					}
  					//联系人姓名
					strHtml += '<div class="layui-form-item"><label class="layui-form-label">联系人姓名</label>';
					strHtml += '<div class="layui-input-inline"><input type="text" name="lxr" value="'+ list.appLxr +'" required placeholder="请输入联系人姓名" lay-verify="judegeName" autocomplete="off" class="layui-input" maxlength="4"></div></div>';
					//联系人地址
					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'地址</label>';
					strHtml += '<div class="layui-input-inline"><input type="text" name="address" value="'+ list.appAddress +'" required placeholder="请输入'+ cnName +'地址" lay-verify="judegeAddress" autocomplete="off" class="layui-input"></div></div>';
					//联系人QQ	
					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'QQ</label>';
					if(list.qq == '' || list.qq == undefined){
						list.qq = '';
					}
					strHtml += '<div class="layui-input-inline"><input type="text" name="qq" value="'+ list.qq +'" required placeholder="请输入'+ cnName +'QQ" lay-verify="judegeQQ" autocomplete="off" class="layui-input"></div></div>';
  				}else if(loginType == 'cpyUser'){
  					cnName = '';
  					if(roleName != '管理员'){//表示是代理机构下的员工用户
  						//技术领域
  						strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'技术领域</label>';
  						list.scFiled == 'null' ? list.scFiled = '' :  list.scFiled;
  						strHtml += '<input type="hidden" name="userScFiledIdStr" value="'+ list.scFiled +'"/>';
  						strHtml += '<div class="layui-input-block">';
  						for(var i=0;i<list.jsInfo.length;i++){
  							if(list.jsInfo[i].checkFlag){
  								strHtml += '<input type="checkbox" class="fields" lay-filter="fieldListInp" lay-verify="checkFieldList" value="'+ list.jsInfo[i].jsId +'" checked title="'+ list.jsInfo[i].zyName +'" lay-skin="primary">';
  								fieldList.push(list.jsInfo[i].jsId);
  							}else{
  								strHtml += '<input type="checkbox" class="fields" lay-filter="fieldListInp" lay-verify="checkFieldList" value="'+ list.jsInfo[i].jsId +'" title="'+ list.jsInfo[i].zyName +'" lay-skin="primary">';
  							}
  						}
  						strHtml += '</div></div>';
  						//撰写总量
  						strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'撰写总量</label>';
  						strHtml += '<div class="layui-input-inline"><input type="text" value="'+ list.zxNum +'" disabled class="layui-input"></div></div>';
  						//用户经验值
  						strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'经验值</label>';
  						strHtml += '<div class="layui-input-inline"><input type="text" value="'+ list.useExp +'" disabled class="layui-input"></div></div>';
  						//等级
  						strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'等级</label>';
  						var strLevel = '';
  						if(list.useExp >=0 && list.useExp <=100){
  							strLevel = '铜牌';
  						}else if(list.useExp > 100 && list.useExp <= 1000){
  							strLevel = '银牌';
  						}else if(list.useExp > 1001){
  							strLevel = '金牌';
  						}
  						strHtml += '<div class="layui-input-inline"><input type="text" value="'+ strLevel +'" disabled class="layui-input"/></div></div>';
  					}
  					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'性别</label>';
  					if(list.sex == 'm'){
  						strHtml += '<div class="layui-input-inline"><input type="radio" name="sex" value="'+ list.sex +'" title="男" checked><input type="radio" name="sex" value="f" title="女"></div></div>';
  					}else{
  						strHtml += '<div class="layui-input-inline"><input type="radio" name="sex" value="m" title="男"><input type="radio" name="sex" value="'+ list.sex +'" title="女" checked></div></div>';
  					}
  					//入职时间
  					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'入职时间</label>';
					strHtml += '<div class="layui-input-inline"><input type="text" value="'+ list.inDate +'" disabled class="layui-input"></div></div>';
  					//账号状态
  					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'账号状态</label>';
  					list.yxStatus == 1 ? list.yxStatus = '有效' : list.yxStatus = '无效';	
					strHtml += '<div class="layui-input-inline"><input type="text" value="'+ list.yxStatus +'" disabled class="layui-input"></div></div>';
					//离职状态
					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'离职状态</label>';
  					list.lzStatus == 1 ? list.lzStatus = '在职' : list.lzStatus = '离职';	
					strHtml += '<div class="layui-input-inline"><input type="text" value="'+ list.lzStatus +'" disabled class="layui-input"></div></div>';
					//离职时间
					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'离职时间</label>';
  					list.outDate == '' ? list.outDate = '暂未离职' : list.outDate = list.outDate;	
					strHtml += '<div class="layui-input-inline"><input type="text" value="'+ list.outDate +'" disabled class="layui-input"></div></div>';
  				}
  				if(loginType != 'spUser'){
  					//联系人电话
  					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'手机号码</label>';
  					strHtml += '<div class="layui-input-inline"><input type="text" name="tel" value="'+ list.tel +'" required placeholder="请输入手机号码" lay-verify="phoneNum"  autocomplete="off" class="layui-input" maxlength="11"></div></div>';
  	  				//联系人邮箱
  					strHtml += '<div class="layui-form-item"><label class="layui-form-label">'+ cnName +'邮箱</label>';
  					strHtml += '<div class="layui-input-inline"><input type="text" name="email" value="'+ list.email +'" required lay-verify="email" placeholder="请输入'+ cnName +'邮箱" autocomplete="off" class="layui-input"></div></div>';
  				}
				strHtml += '<div class="layui-form-item"><label class="layui-form-label"></label><div class="layui-input-inline"><button class="layui-btn" lay-submit lay-filter="setPerInfo">保存修改</button></div></div>';
				
  				$('#setPerInfo').html(strHtml);
  				form.render();
  			}
  			//回填、修改用户基本信息
  			function getUserInfo(userInfoList){
  				layer.closeAll("loading");
	  			if(userInfoList["result"] == "success"){
	  				renderForm(userInfoList);
  				}else if(userInfoList["result"] == "noUser"){
  					$('#setPerInfo').html("<div class='noThisPeo'><i class='iconfont layui-extend-noData'></i><p>查无此人</p></div");
  				}
  			}
  			//自定义表单验证
  			form.verify({
  				judegeName : function(value, item){
					var reg = /^[\u4E00-\u9FA5]+$/; 
					if(value == ''){
						return '个人姓名不能为空';
					}else if(!reg.test(value)){
				      return '个人姓名应为汉字';
				    }else if(value.length < 2 || value.length > 4){
				    	 return '个人姓名最少应为2个字符最多为4个字符';
				    }
				},
				judegeCompName : function(value){
					var reg = /^[\u4E00-\u9FA5]+$/; 
					if(value == ''){
						return '公司名称不能为空';
					}else if(!reg.test(value)){
				      return '公司名称应为汉字';
				    }else if(value.length < 4 || value.length > 20){
				    	 return '公司名称最少为4个字符最多为20个字符';
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
						return '地址不能为空';
					}else{
						if(value.length < 4){
							return '请如实填写地址';
						}
					}
				},
				judegeQQ : function(value){
					var v= value.replace(/ /g,""),
						regQQ=/^[0-9][0-9]{4,}$/;
					if(v == ''){
						return 'QQ号不能为空';
					}else{
						if(!regQQ.test(v)){
							return 'QQ号格式不正确，请从新输入';
						}
					}
				},
				judegeOrgCode : function(code){
					var ws = [3, 7, 9, 10, 5, 8, 4, 2];  
				    var str = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';  
				    var reg = /^([0-9A-Z]){8}-[0-9|X]$/;// /^[A-Za-z0-9]{8}-[A-Za-z0-9]{1}$/  
				    var sum = 0;  
				    for (var i = 0; i < 8; i++){  
				        sum += str.indexOf(code.charAt(i)) * ws[i];  
				    }  
				    var c9 = 11 - (sum % 11);  
				    c9 = c9 == 10 ? 'X' : c9;  
				    //alert(c9 +" -- "+ code.charAt(9));  
				    if(code == ''){
				    	return '公司组织机构代码不能为空';
				    }else{
				    	if (!reg.test(code) || c9 == code.charAt(9)) {  
					        // alert("不是有效的组织机构代码！");
					        return '不是有效的公司组织机构代码';
					        return false;  
					    }/*else{  
					      return true;  
					    }*/
				    }
				},
				checkFieldList : function(){
					if (!$('.fields').is(':checked')) {
						return '请选择您擅长的技术领域';
					}
				}
  			});
  			//获取技术领域fieldList的列表值
			form.on('checkbox(fieldListInp)',function(data){
				var str = '';
				if(data.elem.checked){
					fieldList.push(data.value);
				}else{
  					for(var i=0;i<fieldList.length;i++){
  						if(data.value == fieldList[i]){
  							fieldList.splice(i,1);
  						}
  					}
  				}
  				str = fieldList.join(',');
  				$('input[name=userScFiledIdStr').val(str);
  				
			});
  			//保存修改
  			form.on('submit(setPerInfo)',function(data){
  				var field = data.field;
  				for(var attr in field){
		  			if(attr == "lxr" || attr == "name" || attr == "address"){
		  				field[attr] = $.trim(escape(field[attr]));
		  			}else if(attr == "qq"){
		  				field[attr] = field[attr].replace(/\s/g,"");
		  			}
		  		}
  				//console.log(field)
  				$.ajax({
		    		type:"post",
			        async:false,
			        dataType:"json",
			        url:"user.do?action=updateUserDetail",
			        data:field,
			        success:function (json){
			        	layer.closeAll('loading');
			        	if(json["result"] == true){
			        		layer.msg("保存成功",{icon:1,time:1000},function(){
			        			common.getUserBasicInfo('myParent');
			        			form.render();
			        		});
			        	}else if(json["result"] == false){
			        		layer.msg("保存失败，请重试！",{icon:5,anim:6,time:1000});
			        	}
			        }
		    	});
  			});
  			$(function(){
  				page.init();
  			});
  		});
  	</script>
  </body>
</html>
