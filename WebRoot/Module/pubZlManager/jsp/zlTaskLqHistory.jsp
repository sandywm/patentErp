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
  						<span>专利领取记录</span>
  						<a class="closeBtns" href="javascript:void(0)"><i class="layui-icon layui-icon-close"></i></a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<div class="layui-form searchForm clearfix">
  							<div id="selHisRange" class="fl">
  								<input type="hidden" id="lqZlHisInp" value="yg"/>
  								<input type="radio" name="lqRangeInp" lay-filter="lqRangeFilter" value="yg" title="个人领取记录" checked/>
								<input type="radio" name="lqRangeInp" lay-filter="lqRangeFilter" value="cpy" title="公司领取记录"/>
  							</div>
  							<div class="queryWrap fl">
	  							<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input id="addStatusInp" type="hidden" value="0"/>
		  								<select id="addStatusSel" lay-filter="addStatusSelFilter">
									       	<option value="">请选择添加状态</option>
									        <option value="0">未添加</option>
							    			<option value="1">已添加</option>
									      </select>
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search
	 "></i></button>
		  							</div>
		  						</div>
	  						</div>
  						</div>
  						<div id="zlLqHisList">
  							<div class='noData'></div>
  							<table id="zlLqHisListTable" class="layui-table" lay-filter="zlLqHisTable"></table>
  						</div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
	<script type="text/javascript">
		var loginType = parent.loginType,viewZlFlag=false,globalWid=200;
		layui.use(['layer','form','jquery','table','laydate'],function(){
			var layer = layui.layer,
				form = layui.form,
				$ = layui.jquery,
				table = layui.table;
			var page = {
				init : function(){
					this.bindEvent();
				},
				bindEvent : function(){
					//查询
					$('#queryBtn').on('click',function(){
						loadZlLqHisList('queryLoad');
					});
					//关闭
					$('.closeBtns').on('click',function(){
						var index= parent.layer.getFrameIndex(window.name);
        				parent.layer.close(index);
					});
				}
			};
			//form 监听获取查看领取记录
			form.on('radio(lqRangeFilter)', function(data){
				$('#lqZlHisInp').val(data.value);
				data.value == 'yg' ? globalWid=200 : globalWid=120;
				loadZlLqHisList('initLoad');
			});      
			form.on('select(addStatusSelFilter)', function(data){
				var value = data.value;
				value == '' ? $('#addStatusInp').val(0) : $('#addStatusInp').val(value);
			});	    
			//获取已发布专利的list
			function loadZlLqHisList(opts){
				var option = $('#lqZlHisInp').val();
				if(opts == 'initLoad'){//初始加载
					var field = {addStatus : 0, option : option, purpose : 'allInfo'};
				}else{//查询 queryLoad
					var addStatusInp = $('#addStatusInp').val();
					var field = {addStatus : addStatusInp, option : option, purpose : 'allInfo'};
				}
				layer.load('1');
				table.render({
					elem: '#zlLqHisListTable',
					height: 'full-200',
					url : '/pubZl.do?action=getLqPzListData',
					method : 'post',
					where:field,
					page : true,
					even : true,
					limit : 10,
					limits:[10,20,30,40],
					cols : [[
						{field : '', title: '序号',type:'numbers', width:60, fixed: 'left' , align:'center'},
						{field : 'pzTitle', title: '专利名称', width:200 , align:'center',fixed: 'left'},
						{field : 'zlTypeChi', title: '专利类型', width:150 , align:'center'},
						{field : 'upCl', title: '是否有上传材料', width:140 , align:'center',templet:function(d){
							if(d.upCl != ''){//表示有上传材料
								return '<span class="hasFujianSpan">附有上传材料</span>';
							}else{
								return '<span>否</span>';
							}
						}},
						{field : 'addDate', title: '发布日期', width:180 , align:'center'},
						{field : 'lqr', title: '领取人', width:80 , align:'center'},
						{field : 'lqDate', title: '领取日期', width:150 , align:'center'},
						{field : '', title: '操作', fixed: 'right', width:globalWid,templet : function(d){
							if(option == 'yg'){
								return '<a class="btn layui-btn layui-btn-primary layui-btn-xs" lay-event="viewPubZlDetails" pubId="'+ d.pzId +'" taskTit="'+ d.pzTitle +'"><i class="layui-icon layui-icon-search"></i>查看详情</a> <a class="btn layui-btn layui-btn-danger layui-btn-xs" lay-event="receiveZlTask" zlTitle="'+ d.pzTitle +'" pubId="'+ d.pzId +'">撤销此任务</a>';
							}else{
								return '<a class="btn layui-btn layui-btn-primary layui-btn-xs" lay-event="viewPubZlDetails" pubId="'+ d.pzId +'" taskTit="'+ d.pzTitle +'"><i class="layui-icon layui-icon-search"></i>查看详情</a>';
							}
							//return '<a class="btn layui-btn layui-btn-primary layui-btn-xs" lay-event="viewPubZlDetails" pubId="'+ d.pzId +'" taskTit="'+ d.pzTitle +'"><i class="layui-icon layui-icon-search"></i></a>';
						}},
					]],
					done : function(res, curr, count){
						layer.closeAll('loading');
						if(res.result == 'success'){
							$('.noData').hide().html('');
			        		$('.layui-table-view').show();
						}else if(res.result == 'noInfo'){
							$('.layui-table-view').hide();
			        		$('.noData').show();
			        		if(opts == 'initLoad'){
			        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无专利领取记录</p>");
			        		}else{
			        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
			        		}
						}else if(res.result == 'error'){
							layer.msg('系统错误，请稍后重试', {icon:5,anim:6,time:1500});
						}
					}
				});
			}
			table.on('tool(zlLqHisTable)',function(obj){
				if(obj.event == 'viewPubZlDetails'){
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
					  			loadZlLqHisList('initLoad');
					  		}
					  	}
					});	
					layer.full(fullScreenIndex);
					
				}else if(obj.event == 'receiveZlTask'){
					var ajStatus = $(this).attr('ajStatus'),
						pubId = $(this).attr('pubId'),
						zlTitle = $(this).attr('zlTitle'),
						changeHtml = '',changeTit = '';
					changeHtml = '确定要<span style="color:#F47837;">撤销领取</span>专利任务[<span class="taskColor">'+ zlTitle +'</span>]吗？';
					changeTit = '撤销成功';
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
		   			        data:{pubId : pubId,zlStatus:0},
		   			        url:'/pubZl.do?action=updateHignPzInfo',
		   			        success:function (json){
		   			        	layer.closeAll('loading');
		   			        	if(json['result'] == 'success'){
		   			        		layer.msg(changeTit,{icon:1,time:1000},function(){
		   			        			//var index= parent.layer.getFrameIndex(window.name);
		   		        				parent.viewZlFlag = true;
		   			        			loadZlLqHisList('initLoad');
		   			        			//parent.layer.close(index);
					        		});
		   			        	}else if(json['result'] == 'lowLevel'){
		   			        		layer.msg('抱歉，您当前的会员已经到期或当前等级不够，暂不能领取', {icon:5,anim:6,time:2500});
		   			        	}else if(json['result'] == 'infoDiff'){
		   			        		layer.msg('抱歉，此任务只有任务领取者或申请人才有权利进行撤销', {icon:5,anim:6,time:2500});
		   			        	}else if(json['result'] == 'noReceiv'){
		   			        		layer.msg('抱歉，该专利已被领取!', {icon:5,anim:6,time:1500});
		   			        	}
		   			        }
		   				});
					});
				}
			});
			$(function(){
				page.init();
				loadZlLqHisList('initLoad');
			});
		});
	</script>
  </body>
</html>
