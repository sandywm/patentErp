<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利费用管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="keywords" content="专利管理系统,客户汇款记录管理">
	<meta http-equiv="description" content="专利管理系统客户汇款记录管理">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/backFeeManager/css/backFeeManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>	
    </head>
	<body>
		<div class="layui-fluid" style="margin-top:15px;">
	  		<div class="layui-row">
	  			<div class="layui-col-md12 layui-col-lg12">
	  				<div class="layui-card">
	  					<div class="layui-card-header posRel">
	  						<span>客户汇款记录</span>
	  						<a id="addBackFeeBtn" class="posAbs newAddBtn" opts="addCusOpts" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加汇款记录</a>
	  					</div>
	  					<div class="layui-card-body" pad15>
	  						<!-- 查询层  -->
	  						<div class="searchPart clearfix">
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="text" id="sDateInp" placeholder="请选择汇款时间(开始)" autocomplete="off" class="layui-input">
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="text" id="eDateInp" placeholder="请选择汇款时间(结束)" autocomplete="off" class="layui-input">
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="hidden" id="cusIdInp"/>
		  								<div class="layui-input selCusP"><p class="cusName ellip">请选择客户</p><i class="layui-edge"></i></div>
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search"></i></button>
		  							</div>
		  						</div>
		  						<a class="resetTime" href="javascript:void(0)"><i class="layui-icon layui-icon-refresh"></i>重置</a>
	  						</div>
	  						<div class="backFeeWrap">
	  							<div class='noData'></div>
	  							<table id="backFeeList" class="layui-table" lay-filter="backFeeList"></table>
	  						</div>
	  					</div>
	  				</div>
	  			</div>
	  		</div>
	  	</div>
    </body>
  	<script src="/plugins/jquery/jquery.min.js"></script>
   	<script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
    var addFeeFlag = false,tmpAddBackFee='',globalIndex = 0;
    layui.config({
		base: '/plugins/frame/js/'
	}).extend({ //设定组件别名
	    common: 'common'// 表示模块文件的名字
	}).use(['layer','table','laydate','common'],function(){
			var layer = layui.layer,
				common = layui.common,
				table = layui.table,
				laydate = layui.laydate;
			laydate.render({elem:'#sDateInp'});
			laydate.render({elem:'#eDateInp'});
			var page = {
				data : {
					addBackFeeFlag : false
				},
				init : function(){
					this.data.addBackFeeFlag = common.getPermission('addBackFee','',0);
					this.onLoad();
					this.bindEvent();
				},
				onLoad : function(){
					this.loadBackFeeList('initLoad');
				},
				bindEvent : function(){
					var _this = this;
					//添加汇款记录
					$('#addBackFeeBtn').on('click',function(){
						if(_this.data.addBackFeeFlag){
							addFeeFlag = false;
							tmpAddBackFee = 'addBackFeeStr';
							globalIndex = layer.open({
								title:'增加客户汇款记录',
								type: 2,
								area: ['720px', '440px'],
							  	fixed: true, //不固定
							  	maxmin: false,
							  	shadeClose :false,
							  	content: '/Module/backFeeManager/jsp/addBackFee.html',
							  	end:function(){
							  		if(addFeeFlag){
							  			_this.loadBackFeeList('initLoad');
								  	}
							  	}
							});	
						}else{
							layer.msg('抱歉，您暂无批量增加客户汇款记录', {icon:5,anim:6,time:1200});
						}
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
						  	content: '/Module/feeManager/jsp/addSqr.html'
						});	
					});
					$('#queryBtn').on('click',function(){
						_this.loadBackFeeList('queryLoad');
					});
					$('.resetTime').on('click',function(){
						$('#cusIdInp').val('');
						$('.cusName').html('请选择客户').attr('title','');
						$('#sDateInp').val('');
						$('#eDateInp').val('');
						_this.loadBackFeeList('initLoad');
					});
				},
				loadBackFeeList : function(opts){
					if(opts == 'initLoad'){
						var field = {cusId : 0,sDate : '',eDate : ''};
					}else{
						var sDateInpVal = $('#sDateInp').val(),
							eDateInpVal = $('#eDateInp').val();
						var field = {cusId : $('#cusIdInp').val(),sDate : sDateInpVal,eDate : eDateInpVal};
						if(sDateInpVal != '' && eDateInpVal == ''){
							layer.msg('请选择汇款结束时间段', {icon:5,anim:6,time:1200});
							return;
						}else if(sDateInpVal == '' && eDateInpVal != ''){
							layer.msg('请选择汇款开始时间段', {icon:5,anim:6,time:1200});
							return;
						}else if(eDateInpVal < sDateInpVal){
							layer.msg('汇款开始时间不能大于汇款结束时间', {icon:5,anim:6,time:1200});
							return;
						}
					}
					table.render({
						elem: '#backFeeList',
						height: 'full-200',
						url : '/fee.do?action=getCusBackFeePageData',
						method : 'post',
						where:field,
						page : true,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						cellMinWidth:120,
						cols : [[
							{type : 'numbers', title: '序号', fixed:'left'},
							{field : 'cusInfo', title: '客户名称', width:200,fixed:'left', align:'center'},
							{field : 'backFee', title: '汇款费用', align:'center',templet : function(d){
								return '<span class="backFeeTxt">¥'+ d.backFee +'元</span>';
							}},
							{field : 'backType', title: '汇款类型', align:'center'},
							{field : 'backDate', title: '汇款日期', width:150, align:'center'},
							{field : 'operateUserInfo', title: '操作人', align:'center'},
							{field : 'operateDate', title: '操作日期', width:180, align:'center'},
							{field : 'remark', title: '备注', align:'center'}
						]],
						done : function(res){
							if(res.msg == 'success'){
								$('#backFeeList').siblings('.layui-table-view').show();
								$('.noData').hide().html('');
							}else if(res.msg == 'noInfo'){
								$('#backFeeList').siblings('.layui-table-view').hide();
								$('.noData').show();
				        		if(opts == 'initLoad'){
				        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无客户汇款记录</p>");
				        		}else{
				        			$('.noData').html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
				        		}
							}else if(res.msg == 'noAbility'){
								layer.msg('抱歉，您暂无权限查看客户汇款记录', {icon:5,anim:6,time:1000});
							}
						}
					});
				}
			};
			$(function(){
				page.init();
			});
		});
	</script>
  </body>
</html>
