<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>代理机构信息修改</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="keywords" content="专利管理系统,代理机构信息管理">
	<meta http-equiv="description" content="代理机构信息管理">
	<link href="/Module/userManager/css/formInp.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/cpyDetailInfo/css/cpyBasicInfo.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
	<script src="https://at.alicdn.com/t/font_787377_nv2i23trqi.js"></script>
  </head>
  <body style="background:#f2f2f2;color:#666;">
  	 <div class="layui-fluid">
  	 	<input id="provVal" type="hidden"/>
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div id="cpyInfoSetBox" class="layui-card">
  					<div id="cpyTxtTit" class="layui-card-header"></div>
  					<div id="basicInfoDiv" style="margin-top:15px;"></div>
  				</div>
  			</div>
  		</div>
  	</div>
  	 
    <script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
    	var abilityFlag = "${requestScope.abilityFlag}",globalWorkBonus={globalCbId:0,workPosition:'',zlLevel:0,zlType:'',bonusFee:''};addWorkBonusFlag = false,cpyInitInfo = ['dlFee','bankInfo','saleBonus'];
    	layui.config({
			base : '/plugins/frame/js/'
		}).use(['layer','form','jquery','address','element','table','rate'],function(){
   			var layer = layui.layer,
   				$ = layui.jquery,
   				form = layui.form,
   				element = layui.element,
   				table = layui.table,
   				address = layui.address(),
   				rate = layui.rate;
   			element.on('tab(cpySetFilter)', function(data){
   				var goTarget = $(this).attr('goTarget'),pos = 0;
   				if(goTarget != 'cpyInfo'){
   					pos = $('#' + goTarget).offset().top - 50;
   				}
   				$('html,body').animate({scrollTop:pos},300);
   			});
   			var page = {
   				init : function(){
   					this.onLoad();
   				},
   				onLoad : function(){
   					//获取代理机构基本信息
  					layer.load("1");
  					
  					if(abilityFlag == "true"){
  						this.createTab();
  						for(var i=0;i<cpyInitInfo.length;i++){
  	  						this.getCpyInitSet(cpyInitInfo[i]);//银行账户、代理费、销售提成比例、工作奖金
  	  					}
  						this.workBonusSet();
  						this.bindEvent();
  					}
  					this.getCpyBasicInfo();
   				},
   				getCpyInitSet : function(opt){
   					var _this = this;
   					$.ajax({
  						type:"post",
				        async:false,
				        dataType:"json",
				        data : {opt : opt},
				        url:"cpyManager.do?action=getCpyInitInfo",
				        success:function (json){
				        	if(opt == 'dlFee'){
				        		_this.setAgentFee(json);
				        	}else if(opt == 'bankInfo'){
				        		_this.bankInfoSet(json);
				        	}else if(opt == 'saleBonus'){
				        		_this.saleBonusSet(json);
				        	}
				        }
  					});
   				},
   				getCpyBasicInfo : function(){
   					var _this = this;
   					$.ajax({
  						type:"post",
				        async:false,
				        dataType:"json",
				        url:"cpyManager.do?action=getCpyDetailInfo",
				        success:function (json){
				        	layer.closeAll("loading");
				        	//回填基本信息
				        	_this.getUserInfo(json);
				        }
  					});
   				},
   				getUserInfo : function(list){
   					if(list["result"] == "success"){
   	   					if(abilityFlag == "true"){
   	   		  				this.renderForm(list);
   	   					}else{
   	   						$('.layui-fluid').css({'margin-top':'15px'});
   	   						this.renderBasicInfo(list);
   	   					}
   	  				}else if(list["result"] == "error"){
   	  					$('#cpyInfo').html("<div class='noThisPeo'><i class='iconfont layui-extend-noData'></i><p>加载基本信息出错，请<a href='cpyManager.do?action=goCpyDetailPage'>刷新试下</a></p></div>");
   	  				}
   				},
   				bindEvent : function(){
   					var _this = this;
   					$('#fmDlFeeInp').on('keyup',function(){
						_this.judgeBackFee('fmDlFeeInp');
					});
   					$('#fmDlFeeInp').on('blur',function(){
						_this.judgeAgentFee_num('fmDlFeeInp');
					});
   					$('#wgDlFeeInp').on('keyup',function(){
						_this.judgeBackFee('wgDlFeeInp');
					});
   					$('#wgDlFeeInp').on('blur',function(){
						_this.judgeAgentFee_num('wgDlFeeInp');
					});
   					$('#xxDlFeeInp').on('keyup',function(){
						_this.judgeBackFee('xxDlFeeInp');
					});
   					$('#xxDlFeeInp').on('blur',function(){
						_this.judgeAgentFee_num('xxDlFeeInp');
					});
   					$('#saleBonusInp').on('keyup',function(){
						_this.judgeBackFee('saleBonusInp');
					});
   					//保存专利代理费
   					$('.saveDlFee').on('click',function(){
   						var fmDlFeeInpVal = $.trim($('#fmDlFeeInp').val()),
   							wgDlFeeInpVal = $.trim($('#wgDlFeeInp').val()),
   							xxDlFeeInpVal = $.trim($('#xxDlFeeInp').val());
   						if(fmDlFeeInpVal == ''){
   							layer.msg('发明代理费初始值不能为空', {icon:5,anim:6,time:1000});
   						}else if(wgDlFeeInpVal == ''){
   							layer.msg('外观代理费初始值不能为空', {icon:5,anim:6,time:1000});
   						}else if(xxDlFeeInpVal == ''){
   							layer.msg('新型代理费初始值不能为空', {icon:5,anim:6,time:1000});
   						}else{
   							var field = {opt:'dlFee',dlFeeFm:fmDlFeeInpVal,dlFeeWg:wgDlFeeInpVal,dlFeeXx:xxDlFeeInpVal};
							_this.commonSaveCpyInfo('dlFee',field);
   						}
   					});
   					//保存银行账户信息
   					$('.saveBankInfo').on('click',function(){
   						var bankAccNameInpVal = $.trim($('.bankAccNameInp').val()),
   							bankNameInpVal = $.trim($('.bankNameInp').val()),
   							bankNumInpVal = $.trim($('.bankNumInp').val());
   						if(bankAccNameInpVal == ''){
   							layer.msg('银行账户名不能为空', {icon:5,anim:6,time:1000});
   						}else if(bankNameInpVal == ''){
   							layer.msg('开户行不能为空', {icon:5,anim:6,time:1000});
   						}else if(bankNumInpVal == ''){
   							layer.msg('银行卡号不能为空', {icon:5,anim:6,time:1000});
   						}else if(bankNumInpVal.length < 16){
							layer.msg('请输入正确的银行卡号', {icon:5,anim:6,time:1000});
						}else{
   							var regCHN = /^[\u4E00-\u9FA5]+$/; //检测是否中文
   							if(!regCHN.test(bankAccNameInpVal)){
   								layer.msg('银行账户名应为中文名', {icon:5,anim:6,time:1000});
   							}else if(!regCHN.test(bankNameInpVal)){
   								layer.msg('开户行名称应为中文名', {icon:5,anim:6,time:1000});
   							}else{
   								var field = {opt:'bankInfo',bankAccountName:bankAccNameInpVal,bankName:bankNameInpVal,bankNo:bankNumInpVal};
   								_this.commonSaveCpyInfo('bankInfo',field);
   							}
   						}
   					});
   					//保存销售提成比例
   					$('.saveSaleBonusBtn').on('click',function(){
   						var saleBonusInpVal = $.trim($('#saleBonusInp').val());
   						if(saleBonusInpVal == ''){
   							layer.msg('销售提成比例不能为空', {icon:5,anim:6,time:1000});
   						}else{
   							if(saleBonusInpVal >= 100){
   								layer.msg('销售提成比例应为1-99之间的正整数', {icon:5,anim:6,time:1000});
   								$('#saleBonusInp').val('');
   								return;
   							}
   							var field = {opt:'saleBonus',saleBonus:saleBonusInpVal};
							_this.commonSaveCpyInfo('saleBonus',field);
   						}
   					});
   					//增加工作奖金
   					$('.addWorkBonusBtn').on('click',function(){
						addWorkBonusFlag = false;
   						globalWorkBonus.globalCbId = 0;
   	   					globalWorkBonus.workPosition = '';
   	   					globalWorkBonus.zlLevel = 0;
   	   					globalWorkBonus.zlType = ''; 
   	   					globalWorkBonus.bonusFee = ''; 
						var fullScreenIndex = layer.open({
							title:'<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-bonus"></use></svg><span style="margin-left:5px;">添加工作奖金</span>',
							type: 2,
							area: ['500px', '550px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/cpyDetailInfo/jsp/addBonus.html',
						  	end:function(){
						  		if(addWorkBonusFlag){
						  			_this.loadWorkBonusList('initLoad');
							  	}
						  	}
						});
						layer.full(fullScreenIndex);
   					});
   					$('#queryBtn').on('click',function(){
   						_this.loadWorkBonusList('queryLoad');
   					});
   				},
   				//代理费、银行信息、销售提成、工作奖金公共方法
   				commonSaveCpyInfo : function(opts,field){
   					var nowOpt = '';
   					if(opts == 'dlFee'){
   						nowOpt = '专利代理费初始金额';
   					}else if(opts == 'bankInfo'){
   						nowOpt = '银行账户信息';
   					}else if(opts == 'saleBonus'){
   						nowOpt = '销售提成比例信息';
   					}
   					layer.load('1');
   					$.ajax({
  						type:'post',
				        async:true,
				        dataType:'json',
				        data : field,
				        url:'/cpyManager.do?action=updateCpyInitInfo',
				        success:function (json){
				        	layer.closeAll('loading');
				        	if(json['result'] == 'success'){
				        		layer.msg(nowOpt + '保存成功',{icon:1,time:1000});
				        	}else if(json['result'] == 'noAbility'){
				        		layer.msg('抱歉，您暂无权限操作' + nowOpt, {icon:5,anim:6,time:1000});
				        	}
				        }
  					});
   				},
   				//代理机构管理员下创建tab
   				createTab : function(){
   					var strHtml = '';
   					strHtml += '<div class="layui-tab layui-tab-brief" lay-filter="cpySetFilter">';
   					strHtml += '<ul class="layui-tab-title">';
   					strHtml += '<li class="layui-this" goTarget="cpyInfo">基本信息设置</li>';
   					strHtml += '<li goTarget="agentFeeSet">专利代理费设置</li>';
   					strHtml += '<li goTarget="bankInfoSet">银行账户设置</li>';
   					strHtml += '<li goTarget="saleBonusSet">销售提成设置</li>';
   					strHtml += '<li goTarget="workBonusSet">工作奖金设置</li></ul></div>';
   					strHtml += '<div class="layui-tab-content">';
   					//基本信息设置
   					strHtml += '<div class="layui-tab-item layui-show"><div id="cpyInfo" class="layui-form"></div></div>';
   					//专利代理费设置
   					strHtml += '<div class="layui-tab-item layui-show"><div id="agentFeeSet"></div></div>';
   					//银行账户设置
   					strHtml += '<div class="layui-tab-item layui-show"><div id="bankInfoSet"></div></div>';
   					//销售提成设置
   					strHtml += '<div class="layui-tab-item layui-show"><div id="saleBonusSet"></div></div>';
   					//工作奖金设置
   					strHtml += '<div class="layui-tab-item layui-show"><div id="workBonusSet"></div></div>';
   					strHtml += '</div>';
   					$('#cpyInfoSetBox').html(strHtml);
   				},
   				//代理机构管理员下
   				renderForm : function(list){
   	   				var strHtml = "";
   	   				var	jiangpaiHtml = "";
   	   				strHtml += '<blockquote class="layui-elem-quote basicQuote" style="margin-top:40px;">基本信息设置</blockquote>';
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
   					strHtml += '<div class="layui-input-inline"><input type="text" name="lxrEmail" value="'+ list.lxrEmail +'" required lay-verify="email" placeholder="请输入联系人邮箱" maxlength="30" autocomplete="off" class="layui-input"></div></div>';
   					//选择省、市
   					strHtml += '<div class="layui-form-item"><label class="layui-form-label">选择地区</label>';
   					if(list.cpyProv != '' && list.cpyCity != ''){
   						strHtml += '<div class="layui-input-inline"><select name="cpyProvSel" lay-verify="judgeProv" lay-filter="province" class="province"></select><input id="provInp" name="cpyProv" value="'+ list.cpyProv +'" type="hidden"/></div>';
   						strHtml += '<div class="layui-input-inline"><select name="cpyCitySel" lay-verify="judgeCity" lay-filter="city"></select><input id="cityInp" name="cpyCity" value="'+ list.cpyCity +'" type="hidden"/></div></div>';
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
   					strHtml += '<div class="layui-form-item"><label class="layui-form-label"></label><div class="layui-input-inline"><button class="layui-btn" lay-submit lay-filter="setCpyInfo">保存基本信息</button></div></div>';
   	  				$('#cpyInfo').html(strHtml);
   	  				form.render();
   				},
   				//代理机构员工
   				renderBasicInfo : function(list){
   					$('#cpyTxtTit').html('查看代理机构基本信息');
   	   				var strHtml = '';
   	   				var	jiangpaiHtml = '';
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
   	   				$('#basicInfoDiv').html(strHtml);
   				},
   				//代理费设置
   				setAgentFee : function(json){
   					var strHtml = '';
   					strHtml += '<blockquote class="layui-elem-quote">专利代理费初始设置</blockquote>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label">发明专利代理费</label>';
   					strHtml += '<div class="layui-input-inline posRe">';
   					strHtml += '<em class="moneyDec">¥</em>';
   					strHtml += '<input type="text" id="fmDlFeeInp" placeholder="请输入发明专利代理费" maxlength="6" value="'+ json.dlFeeFm +'" autocomplete="off" class="layui-input"/></div><div class="layui-form-mid layui-word-aux">发明代理费须为不能小于100大于99999的正整数</div></div>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label">外观专利代理费</label>';
   					strHtml += '<div class="layui-input-inline posRe">';
   					strHtml += '<em class="moneyDec">¥</em>';
   					strHtml += '<input type="text" id="wgDlFeeInp" placeholder="请输入外观专利代理费" maxlength="6" value="'+ json.dlFeeWg +'" autocomplete="off" class="layui-input"/></div><div class="layui-form-mid layui-word-aux">外观代理费须为不能小于100大于99999的正整数</div></div>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label">新型专利代理费</label>';
   					strHtml += '<div class="layui-input-inline posRe">';
   					strHtml += '<em class="moneyDec">¥</em>';
   					strHtml += '<input type="text" id="xxDlFeeInp" placeholder="请输入新型专利代理费" maxlength="6" value="'+ json.dlFeeXx +'" autocomplete="off" class="layui-input"/></div><div class="layui-form-mid layui-word-aux">新型代理费须为不能小于100大于99999的正整数</div></div>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label"></label>';
   					strHtml += '<div class="layui-input-inline">';
   					strHtml += '<button class="layui-btn saveDlFee">保存专利代理费</button></div></div>';
   					$('#agentFeeSet').html(strHtml);
   				},
   				//银行账户信息设置
   				bankInfoSet : function(json){
   					var strHtml = '';
   					strHtml += '<blockquote class="layui-elem-quote">银行账户设置</blockquote>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label">账户名</label>';
   					strHtml += '<div class="layui-input-inline">';
   					strHtml += '<input type="text" placeholder="请输入账户人姓名" value="'+ json.bankAccountName +'" maxlength="5" autocomplete="off" class="layui-input bankAccNameInp"/></div></div>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label">开户行</label>';
   					strHtml += '<div class="layui-input-inline">';
   					strHtml += '<input type="text" placeholder="请输入开户行" value="'+ json.bankName +'" maxlength="40" autocomplete="off" maxlength="30" class="layui-input bankNameInp"/></div></div>';
   					strHtml += '<div class="layui-form-item" style="position:realtive;">';
   					strHtml += '<label class="layui-form-label">银行卡号</label>';
   					strHtml += '<div class="layui-input-inline">';
   					strHtml += '<input type="text" id="pattbBankNum" readonly/>';
   					strHtml += '<input type="text" id="bankNo" placeholder="请输入银行卡号" value="'+ json.bankNo +'" maxlength="19" autocomplete="off" class="layui-input bankNumInp"/></div></div>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label"></label>';
   					strHtml += '<div class="layui-input-inline">';
   					strHtml += '<button class="layui-btn saveBankInfo">保存银行账户</button></div></div>';
   					$('#bankInfoSet').html(strHtml);
   					this.bankNumPattern();
   				},
   				//银行账号格式化
   				bankNumPattern : function(){
   					var oBankNum = document.getElementById('bankNo');
   					var oPattBankNum = document.getElementById('pattbBankNum');
   					$('#bankNo').on('keyup',function(){
   						oBankNum.value=oPattBankNum.value.replace(/\D/g,'');
   						oPattBankNum.value = oPattBankNum.value.replace(/\D/g,'').replace(/(\d{4})(?=\d)/g,"$1 ");
   					});
   					oBankNum.onkeydown=function(e){
   						if(!isNaN(this.value.replace(/[ ]/g,""))){         
   							setTimeout(function(){
   								oPattBankNum.value =oBankNum.value.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ");//四位数字一组，以空格分割     
   							},30);
   							
   						}else{         
   							if(e.keyCode==8){//当输入非法字符时，禁止除退格键以外的按键输入
   								return true;         
   							}else{
   								return false;        
   							}
   						}
   					};
   					oBankNum.onfocus = function(){
   						oPattBankNum.style.display="block";
   						oPattBankNum.value =oBankNum.value.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ");//四位数字一组，以空格分割     
   					};
   					oBankNum.onblur = function(){
   						oPattBankNum.style.display="none";
   					};
   				},
   				//销售奖金设置
   				saleBonusSet : function(json){
   					var strHtml = '';
   					strHtml += '<blockquote class="layui-elem-quote">销售提成比例设置</blockquote>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label">销售提成比例</label>';
   					strHtml += '<div class="layui-input-inline posRe">';
   					strHtml += '<em class="percentDec">%</em>';
   					strHtml += '<input type="text" id="saleBonusInp" placeholder="请输入销售提成比例" maxlength="3" value="'+ json.saleBonus +'" autocomplete="off" class="layui-input"/></div><div class="layui-form-mid layui-word-aux">销售提成比例数值在1-99之间设置</div></div>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<label class="layui-form-label"></label>';
   					strHtml += '<div class="layui-input-inline">';
   					strHtml += '<button class="layui-btn saveSaleBonusBtn">保存销售提成比例</button></div></div>';
   					$('#saleBonusSet').html(strHtml);
   				},
   				//工作奖金设置
   				workBonusSet : function(){
   					var strHtml = '';
   					strHtml += '<blockquote class="layui-elem-quote posRe workBonusQute">工作奖金设置<a href="javascript:void(0)" class="addWorkBonusBtn"><i class="layui-icon layui-icon-add-circle"></i>添加工作奖金</a></blockquote>';
   					strHtml += '<div class="searchPart layui-form layui-clear">';
   					strHtml += '<div class="itemDiv">';
   					strHtml += '<div class="layui-input-inline"><input id="zlTypeInp" type="hidden" value=""/>';
   					strHtml += '<select lay-filter="zlTypeFilter">';
   					strHtml += '<option value="">请选择专利类型(全部)</option>'; 	
   					strHtml += '<option value="fm">发明</option>';
   					strHtml += '<option value="syxx">实用新型</option>';
   					strHtml += '<option value="wg">外观</option></select></div></div>';
   					strHtml += '<div class="itemDiv">';
   					strHtml += '<div class="layui-input-inline"><input id="workPositionInp" type="hidden" value=""/>';
   					strHtml += '<select lay-filter="workPositionFilter">';
   					strHtml += '<option value="">请选择工种(全部)</option>'; 	
   					strHtml += '<option value="zx">专利撰写</option>';
   					strHtml += '<option value="sc">专利审核</option></select></div></div>';
   					strHtml += '<div class="itemDiv">';
   					strHtml += '<div class="layui-input-inline"><input id="zlLevelInp" type="hidden" value="0"/>';
   					strHtml += '<select lay-filter="zlLevelFilter">';
   					strHtml += '<option value="">请选择专利难易度(全部)</option>'; 	
   					strHtml += '<option value="1">1颗星(简单)</option>';
   					strHtml += '<option value="2">2颗星(困难)</option>';
   					strHtml += '<option value="3">3颗星(复杂)</option></select></div></div>';
   					strHtml += '<div class="itemDiv"><div class="layui-input-inline">';
   					strHtml += '<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search"></i></button></div></div>';		
   					strHtml += '</div>';
   					strHtml += '<div class="layui-form-item">';
   					strHtml += '<div class="noWorkBonusRec"><i class="iconfont layui-extend-fee"></i><p>暂无工作奖金记录，请添加！</p></div>';
   					strHtml += '<table id="workBonusTable" lay-filter="workBonusTable" class="layui-table"></table>';
   					strHtml += '</div>';
   					$('#workBonusSet').html(strHtml);
   					this.loadWorkBonusList('initLoad');
   				},
   				loadWorkBonusList : function(opts){
   					var tmpZlLevel = [];
   					if(opts == 'initLoad'){
   						var field = {opt : 'workBonus',zlType:'',workPosition:'',zlLevel:0};
   					}else{
   						var zlTypeInpVal = $('#zlTypeInp').val(),
   							workPositionInpVal = $('#workPositionInp').val(),
   							zlLevelInpVal = $('#zlLevelInp').val();
   						var field = {opt : 'workBonus',zlType:zlTypeInpVal,workPosition:workPositionInpVal,zlLevel:zlLevelInpVal};
   					}
   					table.render({
   						elem : '#workBonusTable',
   						height: 'full-200',
						url : '/cpyManager.do?action=getCpyInitInfo',
						method : 'post',
						where:field,
						page : true,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						cellMinWidths : 150,
						cols : [[
							{type : 'numbers', title: '序号', fixed:'left'},
							{field : 'zlType', title: '专利类型', fixed:'left', align:'center'},
							{field : 'workPosition', title: '工种',align:'center'},
							{field : 'zlLevel', title: '专利难易度', align:'center',templet : function(d){
								tmpZlLevel.push(d.zlLevel);
								return '<div class="diffLevelOne" id="diffLevelOne_'+ d.LAY_INDEX +'"></div>';
							}},
							{field : 'bonusFee', title: '奖金',align:'center',templet : function(d){
								return '<span class="bonusTxt">¥'+ d.bonusFee +'元</span>';
							}},
							{field : '', title: '操作',fixed:'right',width:80,align:'center',templet : function(d){
								return '<a class="layui-btn layui-btn-xs" cbId="'+ d.cbId +'" zlType="'+ d.zlType +'" workPosition="'+ d.workPosition +'" zlLevel="'+ d.zlLevel +'" bonusFee="'+ d.bonusFee +'" lay-event="editWorkBonus"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
							}},
						]],
						done : function(res){
							layer.closeAll('loading');
							if(res.count == 0){
								$('.noWorkBonusRec').show();
								$('#workBonusTable').siblings('.layui-table-view').hide();
								if(opts == 'initLoad'){
									$('.noWorkBonusRec').find('p').html('暂无工作奖金记录，请添加！');
				        		}else{
				        			$('.noWorkBonusRec').find('p').html('暂无查询记录');
				        		}
							}else{
								$('.noWorkBonusRec').hide();
								$('#workBonusTable').siblings('.layui-table-view').show();
								for(var i= 0;i<tmpZlLevel.length;i++){
									var tmpValue = tmpZlLevel[i];
									rate.render({
									    elem: '#diffLevelOne_' + (i+1)
									    ,value : tmpZlLevel[i]
									    ,length:3
									    ,readonly:true
									    ,text: true
									    ,theme: '#FF8000'
									    ,setText: function(){ //自定义文本的回调
									      var arrs = {
									        '1': '简单'
									        ,'2': '困难'
									        ,'3': '复杂'
									      };
									      this.span.text('');
									    }
									});
								}
							}
						}
   					});
   				},
   				//判断代理费格式
   				judgeBackFee : function(obj){
					if($('#'+obj).val().length==1){
						$('#'+obj).val($('#'+obj).val().replace(/[^1-9]/g,''));
					}else{
						$('#'+obj).val($('#'+obj).val().replace(/\D/g,''));
					}
				},
				judgeAgentFee_num : function(obj){
					if($('#'+obj).val() < 100 && $('#'+obj).val() != ''){
						layer.msg('专利代理费用最少不能低于100元', {icon:5,anim:6,time:1200});
						$('#'+obj).val('');
						return;
					}else if($('#'+obj).val() > 99999){
						layer.msg('专利代理费用最高不能超过99999', {icon:5,anim:6,time:1200});
						$('#'+obj).val('');
						return;
					}
				},
				addWorkBounsLayer : function(title){
					var _this = this;
					addWorkBonusFlag = false;
					layer.open({
						title:'<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-svg-bonus"></use></svg><span style="margin-left:5px;">'+ title +'</span>',
						type: 2,
						area: ['600px', '330px'],
					  	fixed: true, //不固定
					  	maxmin: false,
					  	shadeClose :false,
					  	content: '/Module/cpyDetailInfo/jsp/addWorkBonus.html',
					  	end:function(){
					  		if(addWorkBonusFlag){
					  			_this.loadWorkBonusList('initLoad');
						  	}
					  	}
					});
				}
   			};
   			table.on('tool(workBonusTable)',function(obj){
   				if(obj.event == 'editWorkBonus'){
   					globalWorkBonus.globalCbId = $(this).attr('cbId');
   					globalWorkBonus.workPosition = $(this).attr('workposition');
   					globalWorkBonus.zlLevel = $(this).attr('zllevel');
   					globalWorkBonus.zlType = $(this).attr('zltype'); 
   					globalWorkBonus.bonusFee = $(this).attr('bonusfee'); 
   					page.addWorkBounsLayer('编辑奖金');
   				}
   			});
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
						if(value.length < 6){
							return '联系地址不能少于6个字符';
						}
					}
				},
				judgejianjie : function(value){
					if(value != ''){
						if(value.length < 10){
							return '公司简介不能少于10个字符';
						}
					}
				},
				judgeProv : function(value){
					var value = $("#provInp").val();
					if(value == '' || value == '请选择省'){
						return '请选择省';
					}
				},
				judgeCity : function(){
					var value = $("#cityInp").val();
					if(value == '' || value == '请选择市'){
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
			        		layer.msg("保存成功",{icon:1,time:1000});
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
   			form.on('select(zlTypeFilter)', function(data){
				var value = data.value;
				value == '' ? $('#zlTypeInp').val('') : $('#zlTypeInp').val(value);
				page.loadWorkBonusList('queryLoad');
			});
   			form.on('select(workPositionFilter)', function(data){
				var value = data.value;
				value == '' ? $('#workPositionInp').val('') : $('#workPositionInp').val(value);
				page.loadWorkBonusList('queryLoad');
			});
   			form.on('select(zlLevelFilter)', function(data){
				var value = data.value;
				value == '' ? $('#zlLevelInp').val(0) : $('#zlLevelInp').val(value);
				page.loadWorkBonusList('queryLoad');
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
