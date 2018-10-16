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
  					<div class="layui-card-header posRel">
  						<span>专利基本信息列表</span>
  						<a id="addZlBtn" class="posAbs newAddBtn" opts="addZlOpts" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加新专利</a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<div>
  							<div class='noData'></div>
  							<table id="zlBasicListTab" class="layui-table" lay-filter="zlInfoListTable"></table>
  						</div>
	  				</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/jquery/jquery-1.7.2.min.js"></script>
   	<script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
		var delFlag = "${ requestScope.delFlag }",
			upFlag = "${ requestScope.upFlag }",
			addFlag = "${ requestScope.addFlag }",addEditZlOpts='',addZlFlag = false,zlTypeInp='';
		layui.use(['layer','table','form'],function(){
			var layer = layui.layer,
				//$ = layui.jquery,
				table = layui.table,
				form = layui.form;
			var page = {
				init : function(){
					this.bindEvent();
				},
				bindEvent : function(){
					$('#addZlBtn').on('click',function(){
						if(addFlag == 'true'){
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
							  			alert("zhixing111")
							  			loadZlInfoList('initLoad');
							  		}
							  	}
							});	
							layer.full(fullScreenIndex);
						}else{
							layer.msg('抱歉，您暂无权限增加新专利', {icon:5,anim:6,time:1000});
						}
					});
				}
			};
			function loadZlInfoList(opts){
				if(opts == 'initLoad'){
					var field = {stopStatus:-1,ajNoQt:'',sqAddress:'',zlNo:'',ajTitle:'',ajType:'',lxr:'',sDate:'',eDate:'',lqStatus:1};
				}else{
					var field = {};
				}
				//console.log(field)
				layer.load('1');
				table.render({
					elem: '#zlBasicListTab',
					height: 'full-200',
					url : 'zlm.do?action=getPageZlData',
					method : 'post',
					where:field,
					page : true,
					even : true,
					limit : 10,
					limits:[10,20,30,40],
					cols : [[
						{field : '', title: '序号',type:'numbers', width:60, fixed: 'left' , align:'center'},
						{field : 'ajTitle', title: '案件标题', width:150, fixed: 'left' , align:'center'},
						{field : 'ajNo', title: '案件编号', width:150, align:'center'},
						{field : 'ajNoGf', title: '案件申请/专利号', width:150, align:'center'},
						{field : 'ajType', title: '案件类型', width:150, align:'center'},
						{field : 'ajFieldName', title: '案件涉及领域', width:150, align:'center'},
						{field : 'ajAddress', title: '案件申请地区', width:160, align:'center'},
						{field : 'sqrInfo', title: '案件申请人', width:100, align:'center'},
						{field : 'fmrInfo', title: '案件发明人', width:100, align:'center'},
						{field : 'lxrInfo', title: '案件联系人', width:100, align:'center'},
						{field : 'applyDate', title: '案件申请时间', width:160, align:'center'},
						{field : 'ajStatus', title: '案件状态', width:100, align:'center'},
						{field : 'ajStopStatus', title: '案件终止状态', width:160, align:'center'},
						{field : 'ajStopDate', title: '案件终止日期', width:120, align:'center'},
						{field : 'ajStopUser', title: '案件终止人', width:80, align:'center'},
						{field : 'ajStopUserType', title: '案件终止人类型', width:120, align:'center'},
						{field : 'ajAddDate', title: '案件录入时间', width:80, align:'center'},
						{field : 'zxUserName', title: '案件撰写人', width:80, align:'center'},
						{field : 'zlStatusInfo', title: '案件状态', width:80, align:'center'},
						{field : '', title: '操作', width:80, fixed: 'right', align:'center'}
					]],
					done : function(res, curr, count){
						layer.closeAll('loading');
						console.log(res)
						if(res.msg == 'success'){
							$('.noData').hide().html('');
			        		$('.layui-table-view').show();
						}else if(res.msg == 'noInfo'){
							$('.layui-table-view').hide();
			        		$('.noData').show();
			        		if(opts == 'initLoad'){
			        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无专利信息</p>");
			        		}else{
			        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
			        		}
						}
					}
				});
			}
			$(function(){
				loadZlInfoList('initLoad');
				page.init();
			});
		});
		
	</script>
  </body>
</html>
