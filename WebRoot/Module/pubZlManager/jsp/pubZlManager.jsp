<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>发布专利管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="keywords" content="专利管理系统,发布专利">
	<meta http-equiv="description" content="专利管理系统发布专利管理">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/pubZlManager/css/pubZlManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
  </head>
  
  <body>
  	<div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header posRel">
  						<span>专利任务发布</span>
  						<a id="addPubZlBtn" class="posAbs newAddBtn" opts="addZlOpts" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>增加新专利</a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<div id="puZlList">
  							<div class='noData'></div>
  							<table id="pubZlListTable" class="layui-table" lay-filter="pubZlTable"></table>
  						</div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
	<script type="text/javascript">
		var loginType = parent.loginType,addEditZlOpts='',addZlFlag = false,globalPubId=0;
		layui.use(['layer','form','jquery','table'],function(){
			var layer = layui.layer,
				form = layui.form,
				$ = layui.jquery,
				table = layui.table;
			var page = {
				init : function(){
					this.onLoad();
					this.bindEvent();
				},
				onLoad : function(){
					if(loginType == 'appUser'){
						$('#addPubZlBtn').show();
					}
				},
				bindEvent : function(){
					$('#addPubZlBtn').on('click',function(){
						var addZlOpts = $(this).attr('opts');
						addEditZlOpts = addZlOpts;
						addZlFlag = false;
						if(loginType == 'appUser'){
							var fullScreenIndex = layer.open({
								title:'增加新专利',
								type: 2,
							  	area: ['700px', '500px'],
							  	fixed: false, //不固定
							  	maxmin: false,
							  	shadeClose :false,
							  	content: '/Module/pubZlManager/jsp/addEditZl.html',
							  	end:function(){
							  		if(addZlFlag){
							  			loadQueryZlList('initLoad');
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
			//获取已发布专利的list
			function loadQueryZlList(opts){
				if(opts == 'initLoad'){//初始加载
					var field = {zlTitle : '',zlNo : '',zlType : '',pubDate : '', zlStatus : -1};
				}else{//查询
					
				}
				layer.load('1');
				table.render({
					elem: '#pubZlListTable',
					height: 'full-200',
					url : 'pubZl.do?action=getPageInfo',
					method : 'post',
					where:field,
					page : true,
					even : true,
					limit : 10,
					limits:[10,20,30,40],
					cols : [[
						{field : 'id', title: 'ID', width:60, fixed: 'left' , align:'center'},
						{field : 'title', title: '专利名称', width:200 , align:'center',fixed: 'left' },
						{field : 'type', title: '专利类型', width:150 , align:'center',templet : function(d){
							return '<span class="zlTypeSpan">'+ d.type +'</span>';
						}},
						{field : 'clFlag', title: '是否有上传材料', width:140 , align:'center',templet:function(d){
							if(d.clFlag == true){
								return '<span class="hasFujianSpan">附有上传材料</span>';
							}else{
								return '<span>否</span>';
							}
						}},
						{field : 'pubDate', title: '发布日期', width:180 , align:'center'},
						{field : 'lqrName', title: '领取人', width:80 , align:'center'},
						{field : 'lqrCpyName', title: '领取人所属公司', width:180 , align:'center'},
						{field : 'lqDate', title: '领取日期', width:150 , align:'center'},
						{field : 'pubInfo', title: '发布者', width:100 , align:'center'},
						{field : '', title: '操作', width:100 , fixed: 'right', align:'center',templet : function(d){
							return '<a class="layui-btn layui-btn-xs editInfoBtns" lay-event="updatePubZl" zlTitle="'+ d.title +'"  pubId="'+ d.id +'" opts="editZlOpts"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
						}},
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
			        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无专利记录</p>");
			        		}else{
			        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
			        		}
						}
					}
				});
			}
			table.on('tool(pubZlTable)',function(obj){
				if(obj.event === 'updatePubZl'){
					globalPubId = $(this).attr('pubId');
					var zlTitle = $(this).attr('zlTitle'),editZlOpts = $(this).attr('opts');
					addEditZlOpts = editZlOpts;
					if(loginType == 'appUser'){
						var fullScreenIndex = layer.open({
							title:zlTitle + '的编辑',
							type: 2,
						  	area: ['700px', '500px'],
						  	fixed: false, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content : '/Module/pubZlManager/jsp/addEditZl.html',
						  	end:function(){
						  		if(addZlFlag){
						  			loadQueryZlList('initLoad');
						  		}
						  	}
						});	
						layer.full(fullScreenIndex);
					}else{
						layer.msg('抱歉，您暂无权限编辑此专利', {icon:5,anim:6,time:1000});
					}
				}
			});
			
			
			
			$(function(){
				page.init();
				loadQueryZlList('initLoad');
			});
		});
	</script>
  </body>
</html>
