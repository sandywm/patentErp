<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利基本信息管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="keywords" content="专利管理系统,专利基本信息管理">
	<meta http-equiv="description" content="专利管理系统专利基本信息管理">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/zlBasicInfoManager/css/zlBasicInfoManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>	
  </head>
  <body>
  	<div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<input type="hidden" id="lqZlIdInp"/>
  			<input type="hidden" id="lqZlIdInpText"/>
  			<input type="hidden" id="lqZlTypeInp"/>
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div id="layuiTab" class="layui-tab layui-tab-brief" lay-filter="zlWrapFilter">
  						<input type="hidden" id="lqStatusInp" value="1"/>
  						<a id="addZlBtn" class="posAbs newAddBtn" opts="addZlOpts" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加新专利</a>
  						<a id="importBtn" style="display:none;" class="posAbs newAddBtn" href="javascript:void(0)"><i class="iconfont layui-extend-daoru"></i>批量导入通知书</a>
						<!--  i class="iconfont layui-extend-bangzhu helpIcon" title="帮助"></i-->
					</div>   
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/jquery/jquery.min.js"></script>
   	<script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
    	var loginType=parent.loginType,roleName=parent.roleName,hasReadFlag=false,tmpAddBackFee='';
		var addEditZlOpts='',addZlFlag = false,globalTaskOpts={taskOpts:'0',yjFzrFlag:false,currLcNo:0,fzUserId:0,globalLcMxId:0,globalMxId:0,yjTypeTxt:'',applyCause:'',applyName:'',yjId:0,zlType:'',tzsId:0},zlTypeInp='',globalLqStatus=1,globalWid=160,globalZlId=0,globalZlTit='',clickOptsFlag=false,globalIndex=0;
		var zlShowTagFlag = '${requestScope.zlTagShowFlag}';
		layui.config({
			base: '/plugins/frame/js/'
		}).extend({ //设定组件别名
		    common: 'common'// 表示模块文件的名字
		}).use(['layer','table','form','common','laydate','element'],function(){
			var layer = layui.layer,
				table = layui.table,
				form = layui.form,
				common = layui.common,
				element = layui.element,
				laydate = layui.laydate;
			
			//tab点击事件的监听
  			element.on('tab(zlWrapFilter)', function(data){
  				var lqStatus = $(this).attr('lqStatus'),zlsearchopts = $(this).attr('zlsearchopts');
  				$('#lqStatusInp').val(lqStatus);
  				globalLqStatus = lqStatus;
  				if(zlsearchopts != ''){
  					page.createSearchPart(zlsearchopts,false);
  				}else{
  					$('.queryPart ').html('');
  				}
  				if(lqStatus == 0 || lqStatus == 1 || lqStatus == 2 || lqStatus == 3){
  					$('.zlStatusInfoBox').show();
  				}else{
  					$('.zlStatusInfoBox').hide();
  				}
  				if(lqStatus == 1 || lqStatus == 2){
  					globalWid=170;
  				}else if(globalLqStatus == 4){
  					globalWid=200;
  				}else{
  					globalWid = 110;
  				}
  				if(lqStatus == 1 && page.data.addZlFlag){
  					$('#addZlBtn').show();
  				}else{
  					$('#addZlBtn').hide();
  				}
  				if(lqStatus == 6 && page.data.dealZlFlag){
  					$('#importBtn').show();
  				}else{
  					$('#importBtn').hide();
  				}
  				page.queryFun();
  				loadZlInfoList('initLoad');
 			});
			var page = {
				data : {
					addZlFlag : false,
					upZlFlag : false,
					fpZlFlag : false,
					lqZlFlag : false,
					listZlFlag : false,
					dealZlFlag : false
				},
				init : function(){
					this.data.fpZlFlag = common.getPermission('fpZl','',0);
					this.data.lqZlFlag = common.getPermission('lqZl','',0);
					this.data.addZlFlag = common.getPermission('addZl','',0);
					this.data.dealZlFlag = common.getPermission('dealZl','tzs',0);
					this.data.addZlFlag ? $('#addZlBtn').show() : $('#addZlBtn').hide();
					//创建tab
					this.createTab();
					this.bindEvent();
					
				},
				bindEvent : function(){
					var _this = this;
					_this.queryFun();
					//帮助
					$('.helpIcon').on('click',function(){
						layer.open({
							title:'操作介绍',
							type: 2,
						  	area: ['500px', '360px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :true,
						  	content: '/Module/zlBasicInfoManager/jsp/readMe.html'
						});	
					});
					$('#addZlBtn').on('click',function(){
						if(_this.data.addZlFlag == true){
							var addZlOpts = $(this).attr('opts');
							addEditZlOpts = addZlOpts;
							addZlFlag = false;
							var fullScreenIndex = layer.open({
								title:'',//增加新专利
								type: 2,
							  	area: ['700px', '500px'],
							  	fixed: true, //不固定
							  	maxmin: false,
							  	shadeClose :false,
							  	closeBtn : 0,
							  	content: '/Module/zlBasicInfoManager/jsp/addEditZl.html',
							  	end:function(){
							  		if(addZlFlag){
							  			loadZlInfoList('initLoad');
							  		}
							  	}
							});	
							layer.full(fullScreenIndex);
						}else{
							layer.msg('抱歉，您暂无权限增加新专利', {icon:5,anim:6,time:1000});
						}
					});
					//批量导入
					$('#importBtn').on('click',function(){
						if(_this.data.dealZlFlag){
							hasReadFlag = false;
							var fullScreenIndex = layer.open({
								title:'',
								type: 2,
							  	area: ['700px', '500px'],
							  	fixed: true, //不固定
							  	maxmin: false,
							  	shadeClose :false,
							  	closeBtn:0,
							  	content: '/Module/zlBasicInfoManager/jsp/batchImport.html',
							  	end:function(){
							  		if(hasReadFlag){
							  			loadZlInfoList('initLoad');
							  		}
							  	}
							});	
							layer.full(fullScreenIndex);
						}else{
							layer.msg('抱歉，您暂无批量导入通知书的权限', {icon:5,anim:6,time:1000});
						}
					});
				},
				queryFun : function(){
					$('#queryBtn').on('click',function(){
						loadZlInfoList('queryLoad');
					});
					$('.resetBtn').on('click',function(){
						$('#ajTypeInp').val('');
						$('#ajStopStatusInp').val(-1);
						$('#ajNoTitInp_ajNo').val('');
						$('#ajNoTitInp_ajZlNo').val('');
						$('#ajNoTitInp_ajTit').val('');
						$('#cusIdInp').val(0);
						$('.cusName').html('请选择客户').attr('title','').css({'color':'#888'});
						$('#sDateInp').val('');
						$('eDateInp').val('');
						loadZlInfoList('initLoad');
					});
					//选择客户
					$('.selCusP').on('click',function(){
						tmpAddBackFee = '';
						layer.open({
							title:'选择客户',
							type: 2,
						  	area: ['800px', '500px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/feeManager/jsp/addSqr.html',
						  	end : function(){
						  		if($('#cusIdInp').val() != 0){
						  			//执行加载
						  			loadZlInfoList('queryLoad');
						  		}
						  	}
						});	
					});
				},
				//创建tab
				createTab : function(){
					var strHtmlTit = '',strHtmlCon = '',strZlStatusTxt = '';
					var strZlStatusTxt = '<div class="zlStatusInfoBox"><p><span class="zlStaColor_zc"></span>正常</p><p><span class="zlStaColor_jj"></span>即将过期</p><p><span class="zlStaColor_gq"></span>已过期</p><p><span class="zlStaColor_zz"></span>案件终止</p></div>';
					strHtmlTit += '<ul class="layui-tab-title">';
					if(loginType == 'spUser'){
						strHtmlTit += '<li class="layui-this" zlSearchOpts="zlGlOpt" lqStatus="1">专利</li>';
					}else if(loginType == 'cpyUser'){
						strHtmlTit += '<li class="layui-this" zlSearchOpts="zlGlOpt" lqStatus="1">专利</li>';
						if(this.data.fpZlFlag == true){
							strHtmlTit += ' <li zlSearchOpts="lcfpOpt" lqStatus="0">流程分配</li>';
						}
						if(this.data.lqZlFlag == true){
							strHtmlTit += ' <li zlSearchOpts="rwlqOpt" lqStatus="2">撰写任务领取</li>';
						}
						if(zlShowTagFlag == 'true'){
							strHtmlTit += ' <li zlSearchOpts="myZlOpt" lqStatus="3">我的专利</li>';
						}
						if(roleName == '管理员'){//查看机构下所有员工的任务
							strHtmlTit += ' <li zlSearchOpts="myTaskOpt" lqStatus="4">专利任务</li>';
						}else{
							strHtmlTit += ' <li zlSearchOpts="myTaskOpt" lqStatus="4">我的任务</li>';
						}
						//管理员下增加个移交申请审核
						if(roleName == '管理员' || this.data.fpZlFlag == true){
							strHtmlTit += ' <li zlSearchOpts="shenheOpt" lqStatus="5">任务移交审核</li>';
							strHtmlTit += ' <li zlSearchOpts="" lqStatus="6">通知书批量导入</li>';
						}else{
							strHtmlTit += ' <li zlSearchOpts="shenheOpt" lqStatus="5">任务移交记录</li>';
						}
					}
					strHtmlTit += '</ul>';
					strHtmlCon += '<div class="layui-card-body layui-tab-content">';
					strHtmlCon += strZlStatusTxt;
					var tmpStrHtml = this.createSearchPart('zlGlOpt',true);
					strHtmlCon += '<div class="queryPart layui-form layui-clear">'+ tmpStrHtml +'</div>';
					if(loginType == 'spUser'){
						strHtmlCon += '<div class="layui-tab-item layui-show">';
						strHtmlCon += '<table id="zlBasicListTab_1" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
					}else if(loginType == 'cpyUser'){
						strHtmlCon += '<div class="layui-tab-item layui-show">';
						strHtmlCon += '<table id="zlBasicListTab_1" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						if(this.data.fpZlFlag == true){//流程分配
							strHtmlCon += '<div class="layui-tab-item">';
							strHtmlCon += '<table id="zlBasicListTab_0" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						}
						if(this.data.lqZlFlag == true){//撰写任务领取
							strHtmlCon += '<div class="layui-tab-item">';
							strHtmlCon += '<table id="zlBasicListTab_2" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						}
						//我的专利对应内容
						if(zlShowTagFlag == 'true'){
							strHtmlCon += '<div class="layui-tab-item">';
							strHtmlCon += '<table id="zlBasicListTab_3" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						}
						//我的任务对应内容 
						strHtmlCon += '<div class="layui-tab-item"><div class="taskStatusBox layui-form"><input id="taskStaInp" type="hidden" value="0"/>';
						strHtmlCon += '<input type="radio" name="taskStatusInp" lay-filter="taskStatusFilter" value="0" title="未完成" checked/>';
						strHtmlCon += '<input type="radio" name="taskStatusInp" lay-filter="taskStatusFilter" value="1" title="已完成"/>';
						if(roleName == '管理员'){//管理员这块对任务这块已完成和未完成记录做个特别说明
							strHtmlCon += '<p class="tipsInfoTask"><i class="layui-icon layui-icon-tips"></i> <span>未完成：管理员可以操作专利的所有流程任务</span> <span>已完成：管理员只能查看自己完成的流程任务</span></p>';
							strHtmlCon += '<div class="overDateTxt"><p><span class="zlStaColor_zc"></span>未过期</p><p><span class="zlStaColor_gq"></span>已过期</p></div>';
						}
						strHtmlCon += '</div>';
						strHtmlCon += '<table id="zlBasicListTab_4" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						//专利移交申请审核/记录
						strHtmlCon += '<div class="layui-tab-item"><div class="taskStatusBox layui-form"><input id="tranStatusInp" type="hidden" value="0"/>';
						strHtmlCon += '<div class="verifyTxt"><p><span class="noVerifySpan"></span>未审核</p><p><span class="pasVerifySpan"></span>审核已通过</p><p><span class="noPasVerifySpan"></span>审核未通过</p></div>';
						strHtmlCon += '<input type="radio" name="tranStatus" lay-filter="tranStatusFilter" value="0" title="未审核" checked/>';
						strHtmlCon += '<input type="radio" name="tranStatus" lay-filter="tranStatusFilter" value="1" title="审核通过"/>';
						strHtmlCon += '<input type="radio" name="tranStatus" lay-filter="tranStatusFilter" value="2" title="审核未通过"/></div>';
						strHtmlCon += '<table id="zlBasicListTab_5" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						//通知书批量导入
						strHtmlCon += '<div class="layui-tab-item"><div class="taskStatusBox layui-form"><input id="readResInp" type="hidden" value="2"/><input id="tzsTypeInp" type="hidden" value=""/>';
						strHtmlCon += '<div class="verifyTxt" style="left:475px;"><p><span class="noPasVerifySpan"></span>读取失败</p><p><span class="pasVerifySpan"></span>读取成功</p></div>';
						strHtmlCon += '<div class="" style="float:left;margin-right:10px;"><div class="layui-input-inline">';
						strHtmlCon += '<select lay-filter="tzsTypeSelFilter"><option value="">请选择通知书类型(全部)</option>';
						strHtmlCon += '<option value="tzs">通知书</option><option value="sqd">电子回执单</option>';
						strHtmlCon += '</select></div></div>';
						strHtmlCon += '<input type="radio" name="readResStatus" lay-filter="readResFilter" value="2" title="全部" checked/>';
						strHtmlCon += '<input type="radio" name="readResStatus" lay-filter="readResFilter" value="1" title="读取成功"/>';
						strHtmlCon += '<input type="radio" name="readResStatus" lay-filter="readResFilter" value="0" title="读取失败"/></div>';
						strHtmlCon += '<table id="zlBasicListTab_6" class="layui-table" lay-filter="zlInfoListTable"></table>';
					}
					strHtmlCon += '</div>';
					$('#layuiTab').append(strHtmlTit + strHtmlCon);
					form.render();
				},
				//创建查询条件
				createSearchPart : function(options,isReturn){
					var searchHtml = '';
					var queryHtmlStr = {
						searchHtml_stop : '',
						searchHtml_NoTit : '',
						searchHtml_ajType : '',
						searchHtml_date : '',
						searchHtml_cus : ''
					};
					//专利终止状态 案件编号 案卷专利号 案件名称 案件类型 开始结束日期 客户名称
					queryHtmlStr.searchHtml_stop += '<div class="queryItem selWid"><input id="ajStopStatusInp" type="hidden" value="-1"/>';
					queryHtmlStr.searchHtml_stop += '<select id="ajStopStatusSel" lay-filter="ajStopStatusSel"><option value="">案件终止状态(全部)</option>';
					queryHtmlStr.searchHtml_stop += '<option value="0">正常</option><option value="1">终止</option></select></div>';
					queryHtmlStr.searchHtml_NoTit += '<div class="queryItem selWid2"><input id="ajStopStatusInp" type="hidden" value="0"/>';
					//0：案件编号 1：专利申请号 2：案件标题
					queryHtmlStr.searchHtml_NoTit += '<select id="ajNoTitSel" lay-filter="ajNoTitSel"><option value="0">案件编号</option>';
					queryHtmlStr.searchHtml_NoTit += '<option value="1">案件专利号</option><option value="2">案件标题</option></select></div>';
					queryHtmlStr.searchHtml_NoTit += '<div class="queryItem inpWid" style="margin-left:-15px;">';
					queryHtmlStr.searchHtml_NoTit += '<input type="text" id="ajNoTitInp_ajNo" style="border-left:none;" maxlength="25" placeholder="请输入案件编号" autocomplete="off" class="layui-input">';
					queryHtmlStr.searchHtml_NoTit += '<input type="text" id="ajNoTitInp_ajZlNo" style="border-left:none;display:none;" maxlength="25" placeholder="请输入案件专利号" autocomplete="off" class="layui-input">';
					queryHtmlStr.searchHtml_NoTit += '<input type="text" id="ajNoTitInp_ajTit" style="border-left:none;display:none;" maxlength="25" placeholder="请输入案件标题" autocomplete="off" class="layui-input"></div>';

					queryHtmlStr.searchHtml_ajType += '<div class="queryItem selWid1"><input id="ajTypeInp" type="hidden" value=""/>';
					queryHtmlStr.searchHtml_ajType += '<select id="ajTypeSel" lay-filter="ajTypeSel"><option value="">案件类型(全部)</option>';
					queryHtmlStr.searchHtml_ajType += '<option value="fm">发明</option><option value="wg">外观</option><option value="syxx">实用新型</option></select></div>';
					queryHtmlStr.searchHtml_date += '<div class="queryItem"><div class="layui-input-inline"><input type="text" id="sDateInp" placeholder="请选择新建日期(开始)" autocomplete="off" class="layui-input"></div></div>';
					queryHtmlStr.searchHtml_date += '<div class="queryItem"><div class="layui-input-inline"><input type="text" id="eDateInp" placeholder="请选择新建日期(结束)" autocomplete="off" class="layui-input"></div></div>';
					queryHtmlStr.searchHtml_cus += '<div class="queryItem cusIdDiv"><div class="layui-input-inline"><input type="hidden" id="cusIdInp" value="0"/>';
					queryHtmlStr.searchHtml_cus += '<div class="layui-input selCusP"><p class="cusName ellip">请选择客户</p><i class="layui-edge"></i></div></div></div>';
					
					if(options == 'zlGlOpt'){//专利
						searchHtml += queryHtmlStr.searchHtml_ajType+queryHtmlStr.searchHtml_stop+queryHtmlStr.searchHtml_NoTit+queryHtmlStr.searchHtml_cus+queryHtmlStr.searchHtml_date;	
					}else if(options == 'lcfpOpt' || options == 'rwlqOpt' || options == 'myZlOpt' || options == 'myTaskOpt'){
						searchHtml += queryHtmlStr.searchHtml_ajType + queryHtmlStr.searchHtml_NoTit + queryHtmlStr.searchHtml_cus;
					}else if(options == 'shenheOpt'){
						searchHtml += queryHtmlStr.searchHtml_NoTit;
					}
					searchHtml += '<div class="queryItem"><div class="layui-input-inline"><button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search"></i></button></div></div><a class="resetBtn" href="javascript:void(0)"><i class="layui-icon layui-icon-refresh"></i>重置</a>';
					if(isReturn){
						return searchHtml;
					}else{
						$('.queryPart').html(searchHtml);
						form.render();
					}
					
				}
			};
			function loadZlInfoList(opts){
				var lqStatusVal = $('#lqStatusInp').val(),taskStaVal = $('#taskStaInp').val(),readResVal = $('#readResInp').val(),tzsTypeVal=$('#tzsTypeInp').val();
				var ajTypeInpVal = $('#ajTypeInp').val(),ajStopStatusInpVal = $('#ajStopStatusInp').val(),ajNoTitInp_ajNoVal = $('#ajNoTitInp_ajNo').val(),
				ajNoTitInp_ajZlNoVal = $('#ajNoTitInp_ajZlNo').val(),ajNoTitInp_ajTitVal = $('#ajNoTitInp_ajTit').val(),cusIdInpVal = $('#cusIdInp').val(),sDateInpVal = $('#sDateInp').val(),eDateInpVal = $('#eDateInp').val();
				if(loginType == 'spUser'){
					var field = {stopStatus:ajStopStatusInpVal,ajNoQt:ajNoTitInp_ajNoVal,zlNo:ajNoTitInp_ajZlNoVal,ajTitle:ajNoTitInp_ajTitVal,ajType:ajTypeInpVal,cusId:cusIdInpVal,sDate:sDateInpVal,eDate:eDateInpVal};
				}else{
					if(lqStatusVal != 4 && lqStatusVal != 5 && lqStatusVal != 6){	
						var field = {stopStatus:ajStopStatusInpVal,ajNoQt:ajNoTitInp_ajNoVal,zlNo:ajNoTitInp_ajZlNoVal,ajTitle:escape(ajNoTitInp_ajTitVal),ajType:ajTypeInpVal,cusId:cusIdInpVal,sDate:sDateInpVal,eDate:eDateInpVal,lqStatus: lqStatusVal};
					}else if(lqStatusVal == 4){
						var field = {ajNoQt:ajNoTitInp_ajNoVal,zlNo:ajNoTitInp_ajZlNoVal,ajTitle:escape(ajNoTitInp_ajTitVal),cusId:cusIdInpVal,comStatus:taskStaVal,lqStatus: lqStatusVal};//0未完成(默认) 1 已完成
					}else if(lqStatusVal == 5){
						//专利移交审核/专利移交记录
						var field = {ajNoQt:ajNoTitInp_ajNoVal,zlNo:ajNoTitInp_ajZlNoVal,ajTitle:escape(ajNoTitInp_ajTitVal),checkStatus:$('#tranStatusInp').val(),lqStatus: lqStatusVal};
					}else if(lqStatusVal == 6){
						var field = {lqStatus:lqStatusVal,readStatus:readResVal,tzsType:tzsTypeVal};
					}	
				}
				if(opts == 'queryLoad'){
					if($('#ajNoTitInp_ajNo').val() != '' && $('#ajNoTitInp_ajNo').val().length < 4){
						layer.msg('案件编号必须4位以上', {icon:5,anim:6,time:1200});
						return;
					}
					if($('#ajNoTitInp_ajZlNo').val() != '' && $('#ajNoTitInp_ajZlNo').val().length < 4){
						layer.msg('案件申请/专利号必须4位以上', {icon:5,anim:6,time:1200});
						return;
					}
					if($('#ajNoTitInp_ajTit').val() != '' && $('#ajNoTitInp_ajTit').val().length < 4){
						layer.msg('案件标题必须大于4个字符', {icon:5,anim:6,time:1200});
						return;
					}
					if(sDateInpVal != '' && eDateInpVal == ''){
						layer.msg('请选择创建专利结束时间段', {icon:5,anim:6,time:1200});
						return;
					}else if(sDateInpVal == '' && eDateInpVal != ''){
						layer.msg('请选择创建专利开始时间段', {icon:5,anim:6,time:1200});
						return;
					}else if(eDateInpVal < sDateInpVal){
						layer.msg('创建专利开始时间不能大于创建专利结束时间', {icon:5,anim:6,time:1200});
						return;
					}
				}
				layer.load('1');
				if(lqStatusVal != 4 && lqStatusVal != 5 && lqStatusVal != 6){
					table.render({
						elem: '#zlBasicListTab_'+lqStatusVal,
						height: 'full-200',
						url : '/zlm.do?action=getPageZlData',
						method : 'post',
						where:field,
						page : true,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						cols : [[
							{field : '', title: '序号',type:'numbers', width:60, fixed: 'left' , align:'center'},
							{field : 'ajTitle', title: '案件标题', width:220, fixed: 'left' , align:'center',templet : function(d){
								var strHtml = '';
								if(d.ajStopStatus == 0){
									if(d.zlStatusInfo == 'outDate_zc'){//正常
										strHtml += '<span class="commonStaSp zlStaColor_zc"></span>';
									}else if(d.zlStatusInfo == 'outDate_jj'){
										strHtml += '<span class="commonStaSp zlStaColor_jj"></span>';
									}else if(d.zlStatusInfo == 'outDate'){
										strHtml += '<span class="commonStaSp zlStaColor_gq"></span>';
									}
								}else if(d.ajStopStatus == 1){//案件终止
									strHtml += '<span class="commonStaSp zlStaColor_zz"></span>';
								}
								return strHtml + d.ajTitle;
							}},
							{field : 'ajNo', title: '案件编号', width:170, align:'center'},
							{field : 'ajNoGf', title: '案件申请/专利号', width:140, align:'center'},
							{field : 'ajType', title: '案件类型', width:140, align:'center'},
							{field : 'ajFieldName', title: '案件涉及领域', width:150, align:'center',templet:function(d){
								if(d.ajFieldName != ''){
									return common.switchToArray(d.ajFieldName.split(','));
								}else{
									return '';
								}
							}},
							{field : 'ajAddress', title: '案件申请地区', width:140, align:'center'},
							{field : 'sqrInfo', title: '案件申请人', width:180, align:'center',templet:function(d){
								if(d.sqrInfo != ''){
									return common.switchToArray(d.sqrInfo.split(','));
								}else{
									return '';
								}
							}},
							{field : 'fmrInfo', title: '案件发明人', width:160, align:'center',templet:function(d){
								if(d.fmrInfo != ''){
									return common.switchToArray(d.fmrInfo.split(','));
								}else{
									return '';
								}
							}},
							{field : 'jsLxrInfo', title: '案件技术联系人', width:160, align:'center',templet:function(d){
								if(d.jsLxrInfo != ''){
									return common.switchToArray(d.jsLxrInfo.split(','));
								}else{
									return '';
								}
							}},
							{field : 'lxrInfo', title: '案件联系人', width:160, align:'center',templet:function(d){
								if(d.lxrInfo != ''){
									return common.switchToArray(d.lxrInfo.split(','));
								}else{
									return '';
								}
							}},
							{field : 'applyDate', title: '案件申请时间', width:160, align:'center'},
							{field : 'ajStatus', title: '流程状态', width:180, align:'center'},
							{field : 'ajStopDate', title: '案件终止日期', width:120, align:'center'},
							{field : 'ajStopUser', title: '案件终止人', width:120, align:'center'},
							{field : 'ajStopUserType', title: '案件终止人类型', width:140, align:'center'},
							{field : 'ajAddDate', title: '案件录入时间', width:120, align:'center'},
							{field : 'zxUserName', title: '案件撰写人', width:120, align:'center'},
							{field : '', title: '操作', width:globalWid, fixed: 'right', align:'center',templet : function(d){
								if(globalLqStatus == 0){//流程分配
									return '<a class="layui-btn layui-btn-xs" lay-event="lcfpFun" zlId="'+ d.id +'" ajTitle="'+ d.ajTitle +'" taskOpts="0"><i class="layui-icon layui-icon-edit"></i>流程分配</a>';
								}else if(globalLqStatus == 1){//专利任务
									return '<a class="layui-btn layui-btn-xs" zlId="'+ d.id +'" lay-event="editZlTask" opts="editZlOpts" taskOpts="1" fzUserId="'+ d.fzUserId +'" currLcNo="'+ d.lcNo +'"><i class="layui-icon layui-icon-edit"></i>查看 / 编辑</a>';
								}else if(globalLqStatus == 2){//撰写任务领取
									return '<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="viewInfo" zlId="'+ d.id +'" ajTitle="'+ d.ajTitle +'"><i class="layui-icon layui-icon-search"></i>查看</a> <a class="layui-btn layui-btn-xs" zlId="'+ d.id +'" ajTitle = "'+ d.ajTitle +'" lay-event="lqZlTaskFun"><i class="iconfont layui-extend-lingqu"></i>领取</a>';
								}else if(globalLqStatus == 3){//已增加专利
									return '<a class="layui-btn layui-btn-xs" lay-event="editZlInfoHasAdd" opts="editZlOpts" zlId="'+ d.id +'"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
								}
							}}
						]],
						done : function(){
							layer.closeAll('loading');
							laydate.render({elem:'#sDateInp'});
							laydate.render({elem:'#eDateInp'});
						}
					});
				}else if(lqStatusVal == 4){
					//加载我的任务
					table.render({
						elem: '#zlBasicListTab_'+lqStatusVal,
						height: 'full-200',
						url : '/zlm.do?action=getPageZlData',
						method : 'post',
						where:field,
						page : true,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						cols:[[
							{field : '', title: '序号',type:'numbers', width:50, fixed: 'left' , align:'center'},
							{field : 'zlTitle', title: '案件标题', width:200, fixed: 'left' , align:'center'},
							{field : 'zlNoQt', title: '案件编号', width:170, align:'center'},
							{field : 'zlNo', title: '专利/申请号', width:140, align:'center'},
							{field : 'fzUserName', title: '流程负责人', width:120, align:'center'},
							{field : 'taskName', title: '任务名称', width:140, align:'center'},
							{field : 'zlType', title: '专利类型', width:120, align:'center'},
							{field : 'taskEdateCpy', title: '任务完成期限(机构)', width:220, align:'center',templet:function(d){
								if(d.diffDaysCpy < 0 && d.taskEdateCpy != ''){
									var strDiffDays_Cpy = d.diffDaysCpy.toString();
									return d.taskEdateCpy + '(<span class="hasOverDate">已过期'+ strDiffDays_Cpy.substring(1,strDiffDays_Cpy.length) +'天</span>)';
								}else{
									return d.taskEdateCpy;
								}
							}},
							{field : 'taskEdateGf', title: '任务完成期限(官方)', width:220, align:'center',templet:function(d){
								if(d.diffDaysGf < 0 && d.taskEdateGf != ''){
									var strDiffDays_Gf = d.diffDaysGf.toString();
									return d.taskEdateGf + '(<span class="hasOverDate">已过期'+ strDiffDays_Gf.substring(1,strDiffDays_Gf.length) +'天</span>)';
								}else{
									return d.taskEdateGf;
								}
							}},
							{field : 'taskSdate', title: '任务开始日期', width:150, align:'center'},
							{field : 'taskComDate', title: '任务完成日期', width:150, align:'center'},
							{field : 'sets', title: '操作', width:globalWid, fixed: 'right', align:'center',templet:function(d){
								//console.log(typeof d.yjFzrFlag)
								if(taskStaVal == 0){
									var strHtml = '';
									strHtml += '<a class="layui-btn layui-btn-xs" lay-event="goCompleteTask" lcNo="'+ d.lcNo +'" zlId="'+ d.zlId +'" mxId = "'+ d.mxId +'" zlType="'+ d.zlType +'" ajTitle="'+ d.zlTitle +'"><i class="layui-icon layui-icon-edit"></i>去完成</a>';
									if(roleName == '管理员' || page.data.fpZlFlag == true){
										if(d.applyFlag){
											strHtml += '<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="tranformFun" yjFzrFlag="'+ d.yjFzrFlag +'" lcMxId="'+ d.mxId +'" lcTask="'+ d.taskName +'" ajTitle="'+ d.zlTitle +'" fzUserId="'+ d.fzUserId +'" applyUserName="'+ d.fzUserName +'"><i class="iconfont layui-extend-yijiao"></i>任务移交</a>';
										}
									}else{
										strHtml += '<a lay-event="transferFun" class="layui-btn layui-btn-danger layui-btn-xs" ajTitle="'+ d.zlTitle +'" yjFzrFlag="'+ d.yjFzrFlag +'" lcMxId="'+ d.mxId +'" lcTask="'+ d.taskName +'"><i class="iconfont layui-extend-yijiao"></i>移交申请</a>';
									}
									return strHtml;
								}else{//表示当前任务已完成
									return '<span class="hasDoneTask"><i class="layui-icon layui-icon-ok statusIcon_com"></i>当前任务已完成</span>';
								}
							}}
						]],
						done : function(res){
							layer.closeAll('loading');
							if(taskStaVal == 1){
								$('.layui-table-box').find('[data-field="sets"]').css('display','none');
							}
						}
					});
				}else if(lqStatusVal == 5){
					//加载专利移交申请审核/专利移交申请记录
					table.render({
						elem: '#zlBasicListTab_'+lqStatusVal,
						height: 'full-200',
						url : '/zlm.do?action=getPageZlData',
						method : 'post',
						where:field,
						page : true,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						cols:[[
							{field : '', title: '序号',type:'numbers', width:60, fixed: 'left' , align:'center'},
							{field : 'zlTitle', title: '案件标题', width:220, fixed: 'left' , align:'center',templet : function(d){
								var strHtml = '';
								if(d.checkStatus == 0){//未审核
									strHtml += '<span class="notCheckStatus"></span>';
								}else if(d.checkStatus == 1){//审核通过
									strHtml += '<span class="hasCheckStatus"></span>';
								}else if(d.checkStatus == 2){//审核未通过
									strHtml += '<span class="noPasCheckStatus"></span>';
								}
								return strHtml + d.zlTitle;
							}},
							{field : 'zlNoQt', title: '案件编号', width:180, align:'center'},
							{field : 'zlNo', title: '专利/申请号', width:180, align:'center'},
							{field : 'zlType', title: '专利类型', width:150, align:'center'},
							{field : 'taskName', title: '任务名称', width:180, align:'center'},
							{field : 'applyUserName', title: '申请人姓名', width:200, align:'center'},
							{field : 'applyDate', title: '申请日期', width:200, align:'center'},
							{field : 'applyCause', title: '申请原因', width:200, align:'center'},
							{field : 'checkUserName', title: '审核人姓名', width:200, align:'center'},
							{field : 'checkDate', title: '审核时间', width:200, align:'center'},
							{field : 'set', title: '操作', width:globalWid, fixed: 'right', align:'center',templet:function(d){
								if(roleName == '管理员' || page.data.fpZlFlag == true){
									return '<a lay-event="verifyFun" class="layui-btn layui-btn-xs" yjType="'+ d.yjType +'" zlId="'+ d.zlId +'" yjId="'+ d.yjId +'" applyUserName="'+ d.applyUserName +'" applyCause="'+ d.applyCause +'" taskOpts="1" ajTitle="'+ d.zlTitle +'" lcTask="'+ d.taskName +'" fzUserId="'+ d.applyUserId +'"><i class="layui-icon layui-icon-vercode"></i>审核</a>';
								}else{
									return '';
								}
							}}
						]],
						done : function(res){
							layer.closeAll('loading');
							if($('#tranStatusInp').val() == 1 || $('#tranStatusInp').val() == 2 || page.data.fpZlFlag == false){
								$('.layui-table-box').find('[data-field="set"]').css('display','none');
							}
						}
					});
				}else if(lqStatusVal == 6){//通知书批量导入读取记录
					layer.closeAll('loading');
					table.render({
						elem: '#zlBasicListTab_'+lqStatusVal,
						height: 'full-200',
						url : '/zlm.do?action=getPageZlData',
						method : 'post',
						where:field,
						page : true,
						even : true,
						limit : 10,
						cols:[[
							{field : '', title: '序号', width:60, fixed: 'left' , align:'center',templet:function(d){
								var strHtml = '';
								if(d.readStatus== 1){
									strHtml += '<span class="hasCheckStatus"></span>';
								}else if(d.readStatus== 0){
									strHtml += '<span class="noPasCheckStatus"></span>';
								}
								return strHtml + d.LAY_INDEX;
							}},
							{field : 'zlName', title: '专利名称', width:220, fixed: 'left' , align:'center',templet : function(d){
								var strHtml = '';
								if(d.tzsType == 'sqd'){
									strHtml += '<i class="iconfont layui-extend-huizhi huizhiIcon" title="电子申请回执单"></i>';
								}else{
									strHtml += '';
								}
								return strHtml + d.zlName;
							}},
							{field : 'zlNo', title: '案件编号', width:180, align:'center'},
							{field : 'tzsName', title: '通知书名称', width:260, align:'center'},
							{field : 'readDetail', title: '读取结果', width:280, align:'center',templet : function(d){
								if(d.readStatus== 0){//失败
									return '<span class="faileTxt">'+ d.readDetail +'</span>';
								}else if(d.readStatus== 1){//成功
									return '<span class="succTxt">'+ d.readDetail +'</span>';
								}
							}},
							{field : 'tzsFwr', title: '通知书发文日', width:150,align:'center'},
							{field : 'fwSerial', title: '发文编号',width:180, align:'center'},
							{field : 'uploadUserName', title: '上传者',width:100, align:'center'},
							{field : 'uploadTime', title: '上传日期',width:180, align:'center'},
							{field : '', title: '操作', align:'center',width:180,fixed:'right',templet : function(d){
								return '<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="viewTzsResDetail" tzsType="'+ d.tzsType +'" tzsId="'+ d.tzsId +'"><i class="layui-icon layui-icon-search"></i>查看</a><a class="layui-btn layui-btn-xs" lay-event="downFileFun" downFilePath="'+ d.tzsPath +'"><i class="layui-icon layui-icon-download-circle"></i>下载</a>';
							}},
						]],
						done : function(){
							layer.closeAll('loading');
						}
					});
				}
				
			}
			form.on('select(ajTypeSel)', function(data){
				var value = data.value;
				value == '' ? $('#ajTypeInp').val('') : $('#ajTypeInp').val(value);
				loadZlInfoList('queryLoad');
			});
			form.on('select(ajStopStatusSel)', function(data){
				var value = data.value;
				value == '' ? $('#ajStopStatusInp').val('-1') : $('#ajStopStatusInp').val(value);
				loadZlInfoList('queryLoad');
			});
			form.on('select(ajNoTitSel)', function(data){
				var value = data.value;
				if(value == 0){//案件编号
					$('#ajNoTitInp_ajNo').show().val('');
					$('#ajNoTitInp_ajZlNo').hide().val('');
					$('#ajNoTitInp_ajTit').hide().val('');
				}else if(value == 1){
					$('#ajNoTitInp_ajZlNo').val('').show();
					$('#ajNoTitInp_ajNo').hide().val('');
					$('#ajNoTitInp_ajTit').hide().val('');
				}else{
					$('#ajNoTitInp_ajTit').show().val('');
					$('#ajNoTitInp_ajNo').hide().val('');
					$('#ajNoTitInp_ajZlNo').hide().val('');
				}
			});
			table.on('tool(zlInfoListTable)',function(obj){
				if(obj.event == 'editZlTask'){//专利(查看/编辑)
					addEditZlOpts = $(this).attr('opts');
					globalZlId = $(this).attr('zlId');
					addZlFlag = false;
					var fullScreenIndex = layer.open({
						title:'',
						type: 2,
					  	area: ['700px', '500px'],
					  	fixed: true, //不固定
					  	maxmin: false,
					  	shadeClose :false,
					  	closeBtn:0,
					  	content: '/Module/zlBasicInfoManager/jsp/zlDetail.html',
					  	end:function(){
					  		if(addZlFlag){
					  			loadZlInfoList('initLoad');
					  		}
					  	}
					});	
					layer.full(fullScreenIndex);
				}else if(obj.event == 'editZlInfoHasAdd'){//我的专利编辑
					page.data.upZlFlag = common.getPermission('upZl','',0);
					if(page.data.upZlFlag){
						addEditZlOpts = $(this).attr('opts');
						globalZlId = $(this).attr('zlId');
						addZlFlag = false;
						var fullScreenIndex = layer.open({
							title:'',//
							type: 2,
						  	area: ['700px', '500px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	closeBtn : 0,
						  	content: '/Module/zlBasicInfoManager/jsp/addEditZl.html',
						  	end:function(){
						  		if(addZlFlag){
						  			loadZlInfoList('initLoad');
						  		}
						  	}
						});	
						layer.full(fullScreenIndex);
					}else{
						layer.msg('抱歉，您暂无权限编辑专利', {icon:5,anim:6,time:1000});
					}
				}else if(obj.event == 'lcfpFun'){//流程分配
					page.data.fpZlFlag = common.getPermission('fpZl','',0);
					if(page.data.fpZlFlag){
						var ajTitle = $(this).attr('ajTitle');
						globalZlId = $(this).attr('zlId');
						globalTaskOpts.taskOpts = $(this).attr('taskOpts');
						globalTaskOpts.currLcNo = 0;
						if(globalTaskOpts.taskOpts == '1'){
							globalTaskOpts.currLcNo = $(this).attr('currLcNo');
							globalTaskOpts.fzUserId = $(this).attr('fzUserId');
						}
						addZlFlag = false;
						var fullScreenIndex = layer.open({
							title:'['+ ajTitle +']的人员流程分配',
							type: 2,
						  	area: ['700px', '500px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/zlBasicInfoManager/jsp/lcFp.html',
						  	end:function(){
						  		if(addZlFlag){
						  			loadZlInfoList('initLoad');
						  		}
						  	}
						});	
						layer.full(fullScreenIndex);
					}else{
						layer.msg('抱歉，您暂无权限进行流程分配', {icon:5,anim:6,time:1000});
					}
				}else if(obj.event == 'lqZlTaskFun'){//领取专利
					var zlId = $(this).attr('zlId'),ajTitle = $(this).attr('ajTitle');
					page.data.lqZlFlag = common.getPermission('lqZl','',0);
					if(page.data.lqZlFlag){
						layer.confirm('确定要领取<span style="color:#F47837;">['+ ajTitle +']</span>撰写任务吗？',{
							title:'提示',
						  	skin: 'layui-layer-molv',
						  	btn: ['确定','取消'] //按钮
						},function(){
							layer.load('1');
							$.ajax({
		    					type:'post',
		    			        async:false,
		    			        dataType:'json',
		    			        data : {zlId : zlId},
		    			        url:'/zlm.do?action=lqZlTask',
		    			        success:function (json){
		    			        	layer.closeAll('loading');	
		    			        	if(json['result'] == 'success'){
		    			        		layer.msg('领取当前撰写任务成功',{icon:1,time:1000},function(){
		    			        			loadZlInfoList('initLoad');
		    			        		});
		    			       		}else if(json['result'] == 'noAbility'){
		    			        		layer.msg('抱歉，您暂无权限领取撰写任务',{icon:5,anim:6,time:1500});
		    			        	}else if(json['result'] == 'error'){
		    			        		layer.msg('系统错误，请重试',{icon:5,anim:6,time:1000});
		    			        	}else if(json['result'] == 'outDate'){
		    			        		layer.msg('抢购期限已过，已不能领取',{icon:5,anim:6,time:1500});
		    			        	}else if(json['result'] == 'noReceive'){
		    			        		layer.msg('该撰写任务已被其他员工领取',{icon:5,anim:6,time:1500});
		    			        	}else if(json['result'] == 'stopStatus'){
		    			        		layer.msg('当前案件已终止，不能进行撰写任务领取',{icon:5,anim:6,time:1500});
		    			        	}
		    			        }
		    				});
						});	
					}else{
						layer.msg('抱歉，您暂无领取撰写任务的权限', {icon:5,anim:6,time:1000});
					}
				}else if(obj.event == 'viewInfo'){
					//查看专利基本信息
					//page.data.listZlFlag = common.getPermission('listZl','',0);
					//if(page.data.listZlFlag){
						var ajTitle = $(this).attr('ajTitle');
						globalZlId = $(this).attr('zlId');
						addZlFlag = false;
						var fullScreenIndex = layer.open({
							title:'专利['+ ajTitle +']基本信息',
							type: 2,
						  	area: ['700px', '500px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/zlBasicInfoManager/jsp/zlDetailTxt.html',
						  	end : function(){
						  		if(addZlFlag){
						  			loadZlInfoList('initLoad');
						  		}
						  	}
						});	
						layer.full(fullScreenIndex);
					/*}else{
						layer.msg('抱歉，您暂无查看专利基本信息的权限', {icon:5,anim:6,time:1000});
					}*/
				}else if(obj.event == 'goCompleteTask'){//做任务
					var ajTitle = $(this).attr('ajTitle'),currLcNo = $(this).attr('lcNo'),lcTaskName = '';
					globalZlId = $(this).attr('zlId');
					globalTaskOpts.globalMxId = $(this).attr('mxId');
					globalZlTit = ajTitle;
					globalTaskOpts.zlType = $(this).attr('zlType');
					addZlFlag = false;
					if(currLcNo >= 3 && currLcNo < 4){//新申请撰稿 撰稿修改
						lcTaskName = 'zx';
					}else if(currLcNo >= 4 && currLcNo < 5){//专利审核 专利复审
						lcTaskName = 'sc';
					}else if(currLcNo >= 5 && currLcNo < 6){//客户确认
						lcTaskName = 'cus';
					}else if(currLcNo >= 6 && currLcNo < 7){//定稿提交
						lcTaskName = 'dgtj';
					}
					page.data.dealZlFlag = common.getPermission('dealZl',lcTaskName,globalZlId);
					if(page.data.dealZlFlag){
						var fullScreenIndex = layer.open({
							title:'',
							type: 2,
						  	area: ['700px', '500px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	closeBtn: 0,
						  	content: '/Module/zlBasicInfoManager/jsp/zlTaskDetail.html',
						  	end : function(){
						  		if(addZlFlag){
						  			loadZlInfoList('initLoad');
						  		}
						  	}
						});	
						layer.full(fullScreenIndex);
					}else{
						layer.msg('抱歉，您暂无处理该任务的权限', {icon:5,anim:6,time:1000});
					}
				}else if(obj.event == 'transferFun' || obj.event == 'tranformFun'){//员工下的移交申请以及管理员下任务移交
					globalTaskOpts.globalLcMxId = $(this).attr('lcmxid');
					globalTaskOpts.applyName = $(this).attr('applyUserName');
					globalTaskOpts.yjFzrFlag = $(this).attr('yjfzrflag');
					var ajTitle = $(this).attr('ajTitle'),lcTask = $(this).attr('lctask');
					var url = '',title = '',height = '';
					if(obj.event == 'transferFun'){
						url = '/Module/zlBasicInfoManager/jsp/taskTransformApply.html';
						title = '任务移交申请提醒';
						height = '420px';
					}else{
						url = '/Module/zlBasicInfoManager/jsp/changePeoByAdmin.html';
						title = '任务移交';
						height = '280px';
						globalTaskOpts.fzUserId = $(this).attr('fzUserId');
						globalTaskOpts.taskOpts = 1;
					}
					layer.confirm('确定要移交申请专利[<span style="color:#F47837;">'+ ajTitle +'</span>]的任务[<span style="color:#F47837;">'+ lcTask +'</span>]吗？',{
						title:title,
					  	skin: 'layui-layer-molv',
					  	btn: ['确定','取消'] //按钮
					},function(index){
						addZlFlag = false;
						layer.close(index);
						globalIndex = layer.open({
							title:'<span title="关于专利['+ ajTitle +']任务['+ lcTask +']的移交申请">关于专利['+ ajTitle +']任务['+ lcTask +']的移交申请</span>',
							type: 2,
						  	area: ['600px', height],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	move : false,
						  	content: url,
						  	end:function(){
						  		if(addZlFlag){
						  			loadZlInfoList('initLoad');
						  		}
						  	}
						});
					});	
				}else if(obj.event == 'verifyFun'){//管理员 流程分配人员审核
					page.data.fpZlFlag = common.getPermission('fpZl','',0);
					if(page.data.fpZlFlag){
						var yjType = $(this).attr('yjType'),ajTitle = $(this).attr('ajTitle'),applyUserName = $(this).attr('applyUserName'),lcTask = $(this).attr('lctask');
						if(yjType == 0){
							globalTaskOpts.yjTypeTxt = '临时移交';
						}else{
							globalTaskOpts.yjTypeTxt = '永久移交';
						}
						globalZlId = $(this).attr('zlId');
						globalTaskOpts.taskOpts = $(this).attr('taskOpts');
						globalTaskOpts.applyCause = $(this).attr('applyCause');
						globalTaskOpts.applyName = applyUserName;
						globalTaskOpts.yjId = $(this).attr('yjId');
						if(globalTaskOpts.taskOpts == '1'){
							globalTaskOpts.fzUserId = $(this).attr('fzUserId');
						}
						addZlFlag = false;
						globalIndex = layer.open({
							title:'关于<span class="hasColor">'+ applyUserName +'</span>对专利[<span class="hasColor">'+ ajTitle +'</span>]任务[<span class="hasColor">'+ lcTask +'</span>]的移交申请审核',
							type: 2,
						  	area: ['530px', '280px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/zlBasicInfoManager/jsp/verifyTransform.html',
						  	end:function(){
						  		if(addZlFlag){
						  			loadZlInfoList('initLoad');
						  		}
						  	}
						});
					}else{
						layer.msg('抱歉，您暂无权限进行流程分配', {icon:5,anim:6,time:1000});
					}
				}else if(obj.event == 'viewTzsResDetail'){//查看电子回执单
					globalTaskOpts.tzsId = $(this).attr('tzsId');
					var tzsType = $(this).attr('tzsType');
					if(tzsType == 'sqd'){//查看电子回执单
						layer.open({
							title:'查看回执单',
							type: 2,
						  	area: ['700px', '400px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	closeBtn : 1,
						  	content: '/Module/zlBasicInfoManager/jsp/viewReceipt.html'
						});	
					}else{
						layer.msg('正在开发中，后续开放...', {icon:5,anim:6,time:1000});
					}
				}else if(obj.event == 'downFileFun'){
					var downFilePath = $(this).attr('downFilePath');
					common.downFiles(downFilePath,0);
				}
			});
			//form 监听获取查看我的任务 未完成/已完成
			form.on('radio(taskStatusFilter)', function(data){
				$('#taskStaInp').val(data.value);
				loadZlInfoList('initLoad');
			}); 
			form.on('radio(tranStatusFilter)', function(data){
				$('#tranStatusInp').val(data.value);
				loadZlInfoList('initLoad');
			}); 
			//通知书读取结果
			form.on('radio(readResFilter)', function(data){
				$('#readResInp').val(data.value);
				loadZlInfoList('initLoad');
			}); 
			//根据通知书类型查看通知书读取结果
			form.on('select(tzsTypeSelFilter)', function(data){
				var value = data.value;
				$('#tzsTypeInp').val(value);
				loadZlInfoList('initLoad');
  			});
			$(function(){
				page.init();
				loadZlInfoList('initLoad');
			});
		});
		
	</script>
  </body>
</html>
