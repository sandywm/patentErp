<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>客户管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="keywords" content="专利管理系统,客户管理">
	<meta http-equiv="description" content="专利管理系统客户管理模块">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/customerManager/css/customerManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
  </head>
  
  <body>
  	<div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header posRel">
  						<!--  span>客户管理</span-->
  						<!-- 查询层  -->
  						<div class="clearfix">
	  						<div class="itemDiv fl">
	  							<div class="layui-input-inline">
	  								<input type="text" id="custNameInp" placeholder="请输入客户名称" autocomplete="off" class="layui-input">
	  							</div>
	  						</div>
	  						<div class="itemDiv fl" style="margin-left:10px;">
	  							<div class="layui-input-inline">
	  								<button id="queryBtn" class="layui-btn" style="margin-bottom:4px;"><i class="layui-icon layui-icon-search"></i></button>
	  							</div>
	  						</div>
  						</div>
  						<a id="addCusBtn" class="posAbs newAddBtn" opts="addCusOpts" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加客户</a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<div id="customerList">
  							<div class='noData'></div>
  							<table id="cusListTable" class="layui-table" style="margin-bottom:-20px;" lay-filter="cusTableFilter"></table>
  						</div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
	    var delFlag = "${ requestScope.delFlag }",
			upFlag = "${ requestScope.upFlag }",
			addFlag = "${ requestScope.addFlag }",addEditCusOpts='',addEditLxrOpts='',addEditFmrOpts='',addCustFlag = false,globalCusId=0,isZlAddFmLxFlag=false;
    	layui.use(['layer','jquery','form','table'],function(){
    		var layer = layui.layer,
    			form = layui.form,
    			table = layui.table,
    			$ = layui.jquery;
    		var page = {
    			init : function(){
    				this.bindEvent();
    			},
    			bindEvent : function(){
    				$('#addCusBtn').on('click',function(){
    					if(addFlag == 'true'){
    						var addCusOpts = $(this).attr('opts');
    						addEditCusOpts = addCusOpts;
    						addCustFlag = false;
   							layer.open({
   								title:'增加新客户',
   								type: 2,
   							  	area: ['700px', '400px'],
   							  	fixed: false, //不固定
   							  	maxmin: false,
   							  	shadeClose :false,
   							  	content: '/Module/customerManager/jsp/addEditCustomer.html',
   							  	end:function(){
   							  		if(addCustFlag){
   							  			loadCustomList('initLoad');
   							  		}
   							  	}
   							});	
    					}else{
    						layer.msg('抱歉，您暂无权限增加新客户', {icon:5,anim:6,time:1000});
    					}
    				});
    				//查询
					$('#queryBtn').on('click',function(){
						loadCustomList('queryLoad');
					});
    			}
    		};
    		function loadCustomList(opts){
    			if(opts == 'initLoad'){
    				var field = {cusName : ''};
    			}else{
    				var field = {cusName : escape($.trim($('#custNameInp').val()))};
    			}
    			layer.load('1');
    			table.render({
    				elem: '#cusListTable',
					height: 'full-200',
					url : 'customer.do?action=getCusPageInfo',
					method : 'post',
					where:field,
					page : true,
					even : true,
					limit : 10,
					limits:[10,20,30,40],
					cellMinWidth : 100,
					cols : [[
						{field : '', title: '序号',type:'numbers', fixed: 'left' , align:'center'},
						{field : 'cusName', title: '客户名称',width:240, align:'center',fixed: 'left'},
						{field : 'cusType', title: '客户类型', align:'center',templet : function(d){
							if(d.cusType == 'gr'){return '个人';}else{return d.cusType;};
						}},
						{field : 'cusICard', title:'组织机构代码/身份证号码',width:200, align:'center'},
						{field : 'cusZip', title: '客户邮编', align:'center'},
						{field : '', title: '操作', align:'center', fixed: 'right', width:390,templet : function(d){
							return '<a class="btn layui-btn layui-btn-primary layui-btn-xs" lay-event="viewCusDetails" cusName="'+ d.cusName +'" cusId="'+ d.id +'"><i class="layui-icon layui-icon-search"></i>查看详情</a> <a class="btn layui-btn layui-btn-xs" lay-event="editCust" cusName="'+ d.cusName +'" cusId="'+ d.id +'"><i class="layui-icon layui-icon-edit"></i>编辑</a> <a class="btn layui-btn layui-btn-xs layui-btn-normal" lay-event="addLxr" cusName="'+ d.cusName +'" cusId="'+ d.id +'" opts="addLxrOpts"><i class="layui-icon layui-icon-add-1"></i>添加联系人</a>  <a class="btn layui-btn layui-btn-xs" lay-event="addFmr" opts="addFmrOpts" cusName="'+ d.cusName +'" cusId="'+ d.id +'"><i class="layui-icon layui-icon-add-1"></i>添加发明人</a>';
						}}
					]],
					done : function(res, curr, count){
						layer.closeAll('loading');
						if(res.msg == 'success'){
							$('.noData').hide().html('');
			        		$('.layui-table-view').show();
						}else if(res.msg == 'noInfo'){
							$('.layui-table-view').hide();
			        		$('.noData').show();
			        		if(opts == 'initLoad'){
			        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无客户记录</p>");
			        		}else{
			        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
			        		}
						}else if(res.msg == 'error'){
							layer.msg('系统错误，请稍后重试', {icon:5,anim:6,time:1000});
						}
					}
    			});
    		}
    		//table工具栏动作
    		table.on('tool(cusTableFilter)',function(obj){
    			var addFlag_tmp = false;
    			if(obj.event == 'viewCusDetails'){//查看信息详情
    				var cusId = $(this).attr('cusId'),cusName = $(this).attr('cusName');
    				globalCusId = cusId;
    				var fullScreenIndex = layer.open({
						title:'客户['+ cusName +']信息',
						type: 2,
					  	area: ['700px', '500px'],
					  	fixed: true, //不固定
					  	maxmin: false,
					  	shadeClose :false,
					  	content: '/Module/customerManager/jsp/viewCustomerDetail.html'
					});	
					layer.full(fullScreenIndex);
    			}else if(obj.event === 'addLxr'){
    				var cusId = $(this).attr('cusId'),cusName = $(this).attr('cusName'),addLxrOpts = $(this).attr('opts');
        			addEditLxrOpts = addLxrOpts;
        			globalCusId = cusId;
        			addTitle='联系人';
        			var url = '/Module/customerManager/jsp/addEditLxr.html',height = '300px';
        			if(addFlag == 'true'){
        				addFlag_tmp = true;
        			}else{
        				addFlag_tmp = false;
        				layer.msg('抱歉，您暂无权限增加联系人', {icon:5,anim:6,time:1000});
        			}
    			}else if(obj.event === 'addFmr'){
    				var cusId = $(this).attr('cusId'),cusName = $(this).attr('cusName'),addFmrOpts = $(this).attr('opts');
    				addEditFmrOpts = addFmrOpts;
        			globalCusId = cusId;
        			addTitle='发明人';
        			var url = '/Module/customerManager/jsp/addEditFmr.html',height = '350px';
        			if(addFlag == 'true'){
        				addFlag_tmp = true;
        			}else{
        				addFlag_tmp = false;
        				layer.msg('抱歉，您暂无权限增加发明人', {icon:5,anim:6,time:1000});
        			}
    			}else if(obj.event == 'editCust'){//编辑
    				if(upFlag == 'true'){
    					var cusId = $(this).attr('cusId'),cusName = $(this).attr('cusName');
    					addCustFlag = false;
    					globalCusId = cusId;
    					var fullScreenIndex = layer.open({
							title:'编辑客户['+ cusName +']信息',
							type: 2,
						  	area: ['700px', '500px'],
						  	fixed: true, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/customerManager/jsp/editCustomerAll.html',
						  	end:function(){
						  		if(addCustFlag){
							  		loadCustomList('initLoad');
							  	}
						  	}
						});	
						layer.full(fullScreenIndex);
    				}else{
    					layer.msg('抱歉，您暂无编辑权限', {icon:5,anim:6,time:1000});
    				}
    			}
    			if(addFlag_tmp){
	    			layer.open({
						title:'客户 ['+ cusName +'] 添加' + addTitle,
						type: 2,
					  	area: ['500px', height],
					  	fixed: true, //不固定
					  	maxmin: false,
					  	shadeClose :false,
					  	content: url
					});	
    			}
    		});
    		
    		$(function(){
    			page.init();
    			loadCustomList('initLoad');
    		});
    	});
    </script>
  </body>
</html>
