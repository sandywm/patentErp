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
						<i class="iconfont layui-extend-bangzhu helpIcon" title="帮助"></i>
					</div>   
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/jquery/jquery.min.js"></script>
   	<script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
    	var fpZlFlag = "${requestScope.fpZlFlag}",lqZlFlag = "${requestScope.lqzLFlag}",loginType=parent.loginType;
		var addEditZlOpts='',addZlFlag = false,zlTypeInp='',globalLqStatus=1,globalWid=160,globalZlId=0;
		layui.config({
			base: '/plugins/frame/js/'
		}).extend({ //设定组件别名
		    common: 'common'// 表示模块文件的名字
		}).use(['layer','table','form','common','element'],function(){
			var layer = layui.layer,
				//$ = layui.jquery,
				table = layui.table,
				form = layui.form,
				common = layui.common,
				element = layui.element;
			
			//tab点击事件的监听
  			element.on('tab(zlWrapFilter)', function(data){
  				var lqStatus = $(this).attr('lqStatus');
  				$('#lqStatusInp').val(lqStatus);
  				globalLqStatus = lqStatus;
  				globalLqStatus == 0 || globalLqStatus == 1 ? globalWid=180 : globalWid = 100;
  				loadZlInfoList('initLoad');
  				if(lqStatus == 1){
  					$('#addZlBtn').show();
  				}else{
  					$('#addZlBtn').hide();
  				}
 			});
			var page = {
				data : {
					addZlFlag : false,
					upZlFlag : false,
					fpZlFlag : false
				},
				init : function(){
					//创建tab
					this.createTab();
					this.bindEvent();
					this.data.addZlFlag = common.getPermission('addZl','',0);
					$('#addZlBtn').show();
					/*$.ajax({
  						type:"post",
				        async:false,
				        dataType:"json",
				        data : {stopStatus:-1,ajNoQt:'',sqAddress:'',zlNo:'',ajTitle:'',ajType:'',lxr:'',sDate:'',eDate:'',lqStatus:loginType == 'spUser' ? -1 : 1},
				        url:'/zlm.do?action=getPageZlData',
				        success:function (json){
				        	//console.log(json)
				        }
  					});*/
				},
				bindEvent : function(){
					var _this = this;
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
								title:'增加新专利',
								type: 2,
							  	area: ['700px', '500px'],
							  	fixed: true, //不固定
							  	maxmin: false,
							  	shadeClose :false,
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
				},
				//创建tab
				createTab : function(){
					var strHtmlTit = '',strHtmlCon = '';
					strHtmlTit += '<ul class="layui-tab-title">';
					if(loginType == 'spUser'){
						strHtmlTit += '<li class="layui-this" lqStatus="1">专利</li>';
					}else if(loginType == 'cpyUser'){
						strHtmlTit += '<li class="layui-this" lqStatus="1">专利</li>';
						if(fpZlFlag == 'true'){
							strHtmlTit += ' <li lqStatus="0">流程分配</li>';
						}
						if(lqZlFlag == 'true'){
							strHtmlTit += ' <li lqStatus="2">撰写任务领取</li>';
						}
						strHtmlTit += ' <li lqStatus="3">我的专利</li>';
						strHtmlTit += ' <li lqStatus="4">我的任务</li>';
					}
					strHtmlTit += '</ul>';
					strHtmlCon += '<div class="layui-card-body layui-tab-content">';
					if(loginType == 'spUser'){
						strHtmlCon += '<div class="layui-tab-item layui-show"><div id="noData_1" class="noData"></div>';
						strHtmlCon += '<table id="zlBasicListTab_1" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
					}else if(loginType == 'cpyUser'){
						strHtmlCon += '<div class="layui-tab-item layui-show"><div id="noData_1" class="noData"></div>';
						strHtmlCon += '<table id="zlBasicListTab_1" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						if(fpZlFlag == 'true'){
							strHtmlCon += '<div class="layui-tab-item"><div id="noData_0" class="noData"></div>';
							strHtmlCon += '<table id="zlBasicListTab_0" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						}
						if(lqZlFlag == 'true'){
							strHtmlCon += '<div class="layui-tab-item"><div id="noData_2" class="noData"></div>';
							strHtmlCon += '<table id="zlBasicListTab_2" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						}
						//我的专利对应内容
						strHtmlCon += '<div class="layui-tab-item"><div id="noData_3" class="noData"></div>';
						strHtmlCon += '<table id="zlBasicListTab_3" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
						//我的任务对应内容 
						strHtmlCon += '<div class="layui-tab-item"><div class="taskStatusBox layui-form"><input id="taskStaInp" type="hidden" value="0"/>';
						strHtmlCon += '<input type="radio" name="taskStatusInp" lay-filter="taskStatusFilter" value="0" title="未完成" checked/>';
						strHtmlCon += '<input type="radio" name="taskStatusInp" lay-filter="taskStatusFilter" value="1" title="已完成"/></div>';
						strHtmlCon += '<div id="noData_4" class="noData"></div>';
						strHtmlCon += '<table id="zlBasicListTab_4" class="layui-table" lay-filter="zlInfoListTable"></table></div>';
					}
					strHtmlCon += '</div>';
					$('#layuiTab').append(strHtmlTit + strHtmlCon);
					form.render();
				},
				switchToArray : function(tmpArray){
					var strHtml = '';
					for(var i=0;i<tmpArray.length;i++){
						strHtml += '<span class="blockSpan">'+ tmpArray[i] +'</span>';
					}
					return strHtml;
				}
			};
			function loadZlInfoList(opts){
				var lqStatusVal = $('#lqStatusInp').val();
				if(opts == 'initLoad'){
					if(loginType == 'spUser'){
						var field = {stopStatus:-1,ajNoQt:'',sqAddress:'',zlNo:'',ajTitle:'',ajType:'',lxr:'',sDate:'',eDate:''};
					}else{
						if(lqStatusVal != 4){
							var field = {stopStatus:-1,ajNoQt:'',sqAddress:'',zlNo:'',ajTitle:'',ajType:'',lxr:'',sDate:'',eDate:'',lqStatus: lqStatusVal};	
						}else{
							var field = {ajTitle:'',ajNoQt:'',zlNo:'',comStatus:0,lqStatus: lqStatusVal};//0未完成(默认)
						}	
					}
				}else{
					var field = {};
				}
				layer.load('1');
				if(lqStatusVal != 4){
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
							{field : 'ajTitle', title: '案件标题', width:220, fixed: 'left' , align:'center'},
							{field : 'ajNo', title: '案件编号', width:150, align:'center'},
							{field : 'ajNoGf', title: '案件申请/专利号', width:150, align:'center'},
							{field : 'ajType', title: '案件类型', width:150, align:'center'},
							{field : 'ajFieldName', title: '案件涉及领域', width:150, align:'center',templet:function(d){
								if(d.ajFieldName != ''){
									return page.switchToArray(d.ajFieldName.split(','));
								}else{
									return '';
								}
							}},
							{field : 'ajAddress', title: '案件申请地区', width:140, align:'center'},
							{field : 'sqrInfo', title: '案件申请人', width:180, align:'center',templet:function(d){
								if(d.sqrInfo != ''){
									return page.switchToArray(d.sqrInfo.split(','));
								}else{
									return '';
								}
							}},
							{field : 'fmrInfo', title: '案件发明人', width:160, align:'center',templet:function(d){
								if(d.fmrInfo != ''){
									return page.switchToArray(d.fmrInfo.split(','));
								}else{
									return '';
								}
							}},
							{field : 'jsLxrInfo', title: '案件技术联系人', width:160, align:'center',templet:function(d){
								if(d.jsLxrInfo != ''){
									return page.switchToArray(d.jsLxrInfo.split(','));
								}else{
									return '';
								}
							}},
							{field : 'lxrInfo', title: '案件联系人', width:160, align:'center',templet:function(d){
								if(d.lxrInfo != ''){
									return page.switchToArray(d.lxrInfo.split(','));
								}else{
									return '';
								}
							}},
							{field : 'applyDate', title: '案件申请时间', width:160, align:'center'},
							{field : 'ajStatus', title: '案件状态', width:100, align:'center'},
							{field : 'ajStopStatus', title: '案件终止状态', width:160, align:'center'},
							{field : 'ajStopDate', title: '案件终止日期', width:120, align:'center'},
							{field : 'ajStopUser', title: '案件终止人', width:120, align:'center'},
							{field : 'ajStopUserType', title: '案件终止人类型', width:140, align:'center'},
							{field : 'ajAddDate', title: '案件录入时间', width:120, align:'center'},
							{field : 'zxUserName', title: '案件撰写人', width:120, align:'center'},
							{field : 'zlStatusInfo', title: '案件状态', width:120, align:'center'},
							{field : '', title: '操作', width:globalWid, fixed: 'right', align:'center',templet : function(d){
								if(globalLqStatus == 0){//流程分配
									return '<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="viewInfo"><i class="layui-icon layui-icon-search"></i>查看</a> <a class="layui-btn layui-btn-xs" lay-event="lcfpFun" zlId="'+ d.id +'"><i class="layui-icon layui-icon-edit"></i>流程分配</a>';
								}else if(globalLqStatus == 1){//专利任务
									return '<a class="layui-btn layui-btn-xs" zlId="'+ d.id +'" lay-event="editZlTask" opts="editZlOpts"><i class="layui-icon layui-icon-edit"></i>查看 / 编辑</a>';
								}else if(globalLqStatus == 2){//撰写任务领取
									return '<a class="layui-btn layui-btn-xs" lay-event="lqZlTaskFun"><i class="layui-icon layui-icon-edit"></i>领取</a>';
								}else if(globalLqStatus == 3){//已增加专利
									return '<a class="layui-btn layui-btn-xs" lay-event="editZlInfoHasAdd" opts="editZlOpts" zlId="'+ d.id +'"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
								}
							}}
						]],
						done : function(res, curr, count){
							callBackDone(res);
						}
					});
				}else{
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
							{field : '', title: '序号',type:'numbers', width:60, fixed: 'left' , align:'center'},
							{field : 'zlTitle', title: '案件标题', width:220, fixed: 'left' , align:'center'},
							{field : 'ajNoQt ', title: '专利编号', width:150, align:'center'},
							{field : 'zlNo', title: '专利/申请号', width:150, align:'center'},
							{field : 'taskName', title: '任务名称', width:180, align:'center'},
							{field : 'taskSdate', title: '任务开始日期', width:200, align:'center'},
							{field : 'taskComdate', title: '任务完成日期', width:200, align:'center'},
							{field : 'taskEdateCpy', title: '任务完成期限(机构)', width:200, align:'center'},
							{field : 'taskEdateGf', title: '任务完成期限(官方)', width:200, align:'center'},
							{field : '', title: '操作', width:globalWid, fixed: 'right', align:'center',templet:function(d){
								return '<a class="layui-btn layui-btn-xs"><i class="layui-icon layui-icon-edit"></i>继续任务</a>';
							}}
						]],
						done : function(res){
							callBackDone(res);
						}
					});
				}
				function callBackDone(res){
					layer.closeAll('loading');
					if(res.msg == 'success'){
						$('#noData_'+lqStatusVal).hide().html('');
		        		$('#zlBasicListTab_'+lqStatusVal).siblings('.layui-table-view').show();
					}else if(res.msg == 'noInfo'){
						$('#zlBasicListTab_'+lqStatusVal).siblings('.layui-table-view').hide();
		        		$('#noData_'+lqStatusVal).show();
		        		if(opts == 'initLoad'){
		        			$('#noData_'+lqStatusVal).html("<i class='iconfont layui-extend-noData'></i><p>暂无记录</p>");
		        		}else{
		        			$('#noData_'+lqStatusVal).html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
		        		}
					}
				}
			}
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
							title:'编辑专利',
							type: 2,
						  	area: ['700px', '500px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
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
						globalZlId = $(this).attr('zlId');
						addZlFlag = false;
						var fullScreenIndex = layer.open({
							title:'流程分配',
							type: 2,
						  	area: ['700px', '500px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/zlBasicInfoManager/jsp/lcFp.html',
						  	end:function(){
						  		/*if(addZlFlag){
						  			loadZlInfoList('initLoad');
						  		}*/
						  	}
						});	
						layer.full(fullScreenIndex);
					}else{
						layer.msg('抱歉，您暂无权限进行流程分配', {icon:5,anim:6,time:1000});
					}
				}
			});
			//form 监听获取查看我的任务 未完成/已完成
			form.on('radio(taskStatusFilter)', function(data){
				$('#taskStaInp').val(data.value);
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
