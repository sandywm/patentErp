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
  						<span id="zlNavTit"></span>
  						<a id="addPubZlBtn" class="posAbs newAddBtn" opts="addZlOpts" href="javascript:void(0)"><i class="layui-icon" style="font-size:20px;">&#xe609;</i>发布专利任务</a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<!-- 查询层 -->
  						<div class="layui-form searchForm clearfix">
	  						<div class="itemDiv fl">
	  							<div class="layui-input-inline">
	  								 <input id="zlTypeInp" type="hidden" value=""/>
	  								 <select id="zlTypeListSel" lay-filter="zlTypeListSel">
	  								 	<option value="">请选择专利类型</option>
	  								 	<option value="fm">发明</option>
	  								 	<option value="syxx">实用新型</option>
	  								 	<option value="wg">外观</option>
	  								 	<option value="fmxx">发明+新型</option>
	  								 </select> 
	  							</div>
	  						</div>
	  						<div class="itemDiv fl">
	  							<div class="layui-input-inline">
	  								<input id="zlStatusInp" type="hidden" value="-1"/>
	  								<select id="zlStatusSel" lay-filter="zlStatusSel">
								       	<option value="">请选择领取状态</option>
								        <option value="0">待领取</option>
						    			<option value="1">已领取</option>
								      </select>
	  							</div>
	  						</div>
	  						<div class="itemDiv fl">
	  							<div class="layui-input-inline">
	  								<input id="pubDateInp" type="text" readonly value="" placeholder="请选择发布日期" autocomplete="off" class="layui-input"/>
	  							</div>
	  						</div>
	  						<div class="itemDiv fl">
	  							<div class="layui-input-inline">
	  								<input type="text" id="zlNoInp" placeholder="请输入专利案件编号" autocomplete="off" class="layui-input">
	  							</div>
	  						</div>
	  						<div class="itemDiv fl">
	  							<div class="layui-input-inline">
	  								<input type="text" id="zlTitleInp" placeholder="请输入专利名称" autocomplete="off" class="layui-input">
	  							</div>
	  						</div>
	  						<div class="itemDiv fl">
	  							<div class="layui-input-inline">
	  								<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search
 "></i></button>
	  							</div>
	  						</div>
	  					</div>
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
		var loginType = parent.loginType,addEditZlOpts='',addZlFlag = false,viewZlFlag=false;globalPubId=0;
		layui.use(['layer','form','jquery','table','laydate'],function(){
			var layer = layui.layer,
				form = layui.form,
				$ = layui.jquery,
				table = layui.table,
				laydate = layui.laydate;
			 laydate.render({
			    elem: '#pubDateInp' //指定元素
			 });
			var page = {
				init : function(){
					this.onLoad();
					this.bindEvent();
				},
				onLoad : function(){
					if(loginType == 'appUser'){
						$('#addPubZlBtn').show();
						$('#zlNavTit').html('专利任务发布');
					}else{
						$('#zlNavTit').html('专利任务列表');
					}
					
					/*$.ajax({
	   					type:'post',
	   			        async:false,
	   			        dataType:'json',
	   			        data:{zlTitle : '',zlNo : '',zlType : '',pubDate : '', zlStatus : -1},
	   			        url:'pubZl.do?action=getPageInfo',
	   			        success:function (json){
	   			        	layer.closeAll('loading');
	   			        	if(json['result'] == 'success'){
	   			        		console.log(json)
	   			        	}else if(json['result'] == 'outDate'){
	   			        		layer.msg('抱歉，您当前的会员已经到期，暂不能查看', {icon:5,anim:6,time:1000});
	   			        	}
	   			        }
	   				});*/
				},
				bindEvent : function(){
					//增加专利
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
					//查询
					$('#queryBtn').on('click',function(){
						loadQueryZlList('queryLoad');
					});
				}
			};
			//获取已发布专利的list
			function loadQueryZlList(opts){
				if(opts == 'initLoad'){//初始加载
					var field = {zlTitle : '',zlNo : '',zlType : '',pubDate : '', zlStatus : -1};
				}else{//查询 queryLoad
					var zlTypeInp = $('#zlTypeInp').val(),
						zlStatusInp = $('#zlStatusInp').val(),
						pubDateInp = $('#pubDateInp').val(),
						zlNoInp = $.trim($('#zlNoInp').val()),
						zlTitleInp = $.trim($('#zlTitleInp').val());
					var field = {zlTitle : escape(zlTitleInp),zlNo : escape(zlNoInp),zlType : zlTypeInp,pubDate : pubDateInp, zlStatus : zlStatusInp};
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
						{field : '', title: '序号',type:'numbers', width:60, fixed: 'left' , align:'center'},
						{field : 'title', title: '专利名称', width:200 , align:'center',fixed: 'left',templet:function(d){
							if(d.ajLqStatus == 0){//待领取
								return '<span class="waitReceive fl">[待领取]</span><p class="zlTitleP fl">'+ d.title +'</p>';	
							}else{
								return '<span class="hasReceive fl">[已领取]</span><p class="zlTitleP fl">'+ d.title +'</p>';
							}
						}},
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
						{field : '', title: '操作', fixed: 'right', width:200,templet : function(d){
							if(loginType == 'appUser'){
								if(d.ajLqStatus == 0){
									return '<a class="layui-btn layui-btn-xs editInfoBtns" lay-event="updatePubZl" ajStatus="'+ d.ajLqStatus +'" zlTitle="'+ d.title +'"  pubId="'+ d.id +'" opts="editZlOpts"><i class="layui-icon layui-icon-edit"></i>编辑任务</a>';
								}else{
									return '<a class="layui-btn layui-btn-xs editInfoBtns" lay-event="updatePubZl" ajStatus="'+ d.ajLqStatus +'" zlTitle="'+ d.title +'"  pubId="'+ d.id +'" opts="editZlOpts"><i class="layui-icon layui-icon-edit"></i>编辑任务</a> <a class="btn layui-btn layui-btn-danger layui-btn-xs" lay-event="receiveZlTask" lqrCpyName = "'+ d.lqrCpyName +'" ajStatus="'+ d.ajLqStatus +'" zlTitle="'+ d.title +'" pubId="'+ d.id +'">解除合作</a>';
								}
							}else if(loginType == 'spUser'){
								return '<a class="btn layui-btn layui-btn-primary layui-btn-xs" lay-event="viewPubZlDetails" pubId="'+ d.id +'" taskTit="'+ d.title +'"><i class="layui-icon layui-icon-search"></i>查看任务详情</a>';
							}else if(loginType == 'cpyUser'){
								if(d.ajLqStatus == 0){
									return '<a class="btn layui-btn layui-btn-primary layui-btn-xs" lay-event="viewPubZlDetails" pubId="'+ d.id +'" taskTit="'+ d.title +'"><i class="layui-icon layui-icon-search"></i>任务详情</a> <a class="btn layui-btn layui-btn-xs" lay-event="receiveZlTask" ajStatus="'+ d.ajLqStatus +'" zlTitle="'+ d.title +'" pubId="'+ d.id +'">领取任务</a>';
								}else{
									if(d.undoShowFlag){//表示是领取本人 有撤销任务的权利
										return '<a class="btn layui-btn layui-btn-primary layui-btn-xs" lay-event="viewPubZlDetails" pubId="'+ d.id +'" taskTit="'+ d.title +'"><i class="layui-icon layui-icon-search"></i>任务详情</a> <a class="btn layui-btn layui-btn-danger layui-btn-xs" lay-event="receiveZlTask" ajStatus="'+ d.ajLqStatus +'" zlTitle="'+ d.title +'" pubId="'+ d.id +'">撤销领取</a>';
									}else{
										return '<a class="btn layui-btn layui-btn-primary layui-btn-xs" lay-event="viewPubZlDetails" pubId="'+ d.id +'" taskTit="'+ d.title +'"><i class="layui-icon layui-icon-search"></i>任务详情</a>';
									}
								}
							}
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
					var zlTitle = $(this).attr('zlTitle'),editZlOpts = $(this).attr('opts'),ajLqStatus = $(this).attr('ajStatus');
					addEditZlOpts = editZlOpts;
					if(ajLqStatus == 1){
						layer.msg('抱歉，此专利任务已被代理机构领取，暂不能编辑', {icon:5,anim:6,time:1000});
						return;
					}
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
				}else if(obj.event == 'viewPubZlDetails'){
					//代理机构下查看专利任务信息
					globalPubId = $(this).attr('pubId');
					viewZlFlag = false;
					var taskTit = $(this).attr('taskTit');
					var fullScreenIndex = layer.open({
						title:taskTit + '专利任务详情',
						type: 2,
					  	area: ['700px', '500px'],
					  	fixed: false, //不固定
					  	maxmin: false,
					  	shadeClose :false,
					  	content : '/Module/pubZlManager/jsp/viewZlTaskDetail.html',
					  	end:function(){
					  		if(viewZlFlag){
					  			loadQueryZlList('initLoad');
					  		}
					  	}
					});	
					layer.full(fullScreenIndex);
					
				}else if(obj.event == 'receiveZlTask'){
					var ajStatus = $(this).attr('ajStatus'),
						pubId = $(this).attr('pubId'),
						zlTitle = $(this).attr('zlTitle'),
						lqrCpyName = $(this).attr('lqrCpyName'),
						zlStatus = 0,
						changeHtml = '',changeTit = '';
					if(ajStatus == 0){
						zlStatus = 1; //撤销领取
						changeTit = '领取成功';
						changeHtml = '确定要<span style="color:#F47837;">领取</span>专利任务[<span class="taskColor">'+ zlTitle +'</span>]吗？';
					}else{
						if(loginType == 'cpyUser'){
							changeHtml = '确定要<span style="color:#F47837;">撤销领取</span>专利任务[<span class="taskColor">'+ zlTitle +'</span>]吗？';
							changeTit = '撤销成功';
						}else if(loginType == 'appUser'){
							var otherHtml = '';
							changeHtml = '确定要<span style="color:#F47837;">解除</span>和<span style="color:#F47837;">'+ lqrCpyName +'</span>代理机构关于对专利任务[<span class="taskColor">'+ zlTitle +'</span>]的合作吗？';
							changeTit = '解除成功';
						}
						zlStatus = 0; //领取
					}
					layer.confirm(changeHtml, {
					  title:'提示',
					  skin: 'layui-layer-molv',
					  btn: ['确定','取消'] //按钮
					},function(){
						layer.load('1');
						$.ajax({
		   					type:'post',
		   			        async:false,
		   			        dataType:'json',
		   			        data:{pubId : pubId,zlStatus:zlStatus},
		   			        url:'/pubZl.do?action=updateHignPzInfo',
		   			        success:function (json){
		   			        	layer.closeAll('loading');
		   			        	console.log(json)
		   			        	if(json['result'] == 'success'){
		   			        		layer.msg(changeTit,{icon:1,time:1000},function(){
		   			        			loadQueryZlList('initLoad');
					        		});
		   			        	}else if(json['result'] == 'lowLevel'){
		   			        		layer.msg('抱歉，您当前的会员已经到期或当前等级不够，暂不能领取', {icon:5,anim:6,time:2500});
		   			        	}else if(json['result'] == 'infoDiff'){
		   			        		layer.msg('抱歉，此任务只有任务领取者或申请人才有权利进行撤销', {icon:5,anim:6,time:2500});
		   			        	}else if(json['result'] == 'noReceiv'){
		   			        		layer.msg('抱歉，该专利已被领取，不能再领取了', {icon:5,anim:6,time:1500});
		   			        	}
		   			        }
		   				});
					});
				}
			});
			//form 监听选择专利类型
			form.on('select(zlTypeListSel)', function(data){
				var value = data.value;
				value == '' ? $('#zlTypeInp').val('') : $('#zlTypeInp').val(value);
			});
			//form 监听选择领取状态
			form.on('select(zlStatusSel)', function(data){
				var value = data.value;
				value == '' ? $('#zlStatusInp').val(-1) : $('#zlStatusInp').val(value);
			});
			
			$(function(){
				page.init();
				loadQueryZlList('initLoad');
			});
		});
	</script>
  </body>
</html>
