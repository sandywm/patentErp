<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利费用管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="keywords" content="专利管理系统,专利费用管理">
	<meta http-equiv="description" content="专利管理系统专利费用">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/feeManager/css/feeManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>	
  </head>
  <body>
  	<div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card hasPadBot">
  					<div class="layui-tab layui-tab-brief" lay-filter="feeTabFilter">
  						<a id="exportFeeBtn" class="posAbs newAddBtn" href="javascript:void(0)"><i class="iconfont layui-extend-daochuexcel"></i>导出费用账单</a>
  						<ul class="layui-tab-title">
  							<li class="layui-this" feeStatus="0">未缴费</li>
  							<li feeStatus="1">已缴费</li>
  						</ul>
  						<div class="layui-tab-content">
  							<div class="searchPart layui-form">
  								<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="text" id="zlNoInp" placeholder="请输入专利申请/专利号" autocomplete="off" class="layui-input">
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="text" id="ajNoInp" placeholder="请输入案件编号" autocomplete="off" class="layui-input">
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="text" id="cusIdInp" placeholder="客户/申请人编号" autocomplete="off" class="layui-input">
		  							</div>
		  						</div>
		  						<div class="itemDiv diffDaysDiv fl">
		  							<div class="layui-input-inline">
		  								<input id="diffDaysSelInp" type="hidden" value="15"/>
		  								<select id="diffDaysSel" lay-filter="diffDaysSel">
									       	<option value="">请选择距代理机构缴费期限(全部)</option>
									    	<option value="1">1天</option>
							    			<option value="7">7天</option>
							    			<option value="15">15天</option>
							    			<option value="30">30天</option>
							    			<option value="60">60天</option>
									    </select>
		  							</div>
		  						</div>
		  						<div class="itemDiv sEDateDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="text" id="sDateInp" placeholder="请选择缴费日期(开始)" autocomplete="off" class="layui-input">
		  							</div>
		  						</div>
		  						<div class="itemDiv sEDateDiv fl">
		  							<div class="layui-input-inline">
		  								<input type="text" id="eDateInp" placeholder="请选择缴费日期(结束)" autocomplete="off" class="layui-input">
		  							</div>
		  						</div>
		  						<div class="itemDiv fl">
		  							<div class="layui-input-inline">
		  								<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search
	 "></i></button>
		  							</div>
		  						</div>
  							</div>
  							<div class="layui-tab-item layui-show">
  								<div id="noData_0" class="noData"></div>
  								<table id="feeListTab_0" class="layui-table" lay-filter="feeInfoListTable"></table>
  							</div>
  							<div class="layui-tab-item">
  								<div id="noData_1" class="noData"></div>
  								<table id="feeListTab_1" class="layui-table" lay-filter="feeInfoListTable"></table>
  							</div>
  						</div> 
  					</div>
  					<div id="totalWrap"></div> 
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/jquery/jquery.min.js"></script>
   	<script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
	    layui.config({
			base: '/plugins/frame/js/'
		}).extend({ //设定组件别名
		    common: 'common'// 表示模块文件的名字
		}).use(['layer','element','common','form','table','laydate'],function(){
			var layer = layui.layer,
				element = layui.element,
				common = layui.common,
				table = layui.table,
				form = layui.form,
				laydate = layui.laydate;
			laydate.render({elem:'#sDateInp'});
			laydate.render({elem:'#eDateInp'});
			//tab点击事件的监听
  			element.on('tab(feeTabFilter)', function(data){
  				var feeStatus = $(this).attr('feeStatus');
  				page.data.globalFeeStatus = feeStatus;//切换缴费状态
  				if(feeStatus == '0'){//未缴费 不分页
  					page.data.isHasPageFun = false;
  					$('#totalWrap').hide();
  					$('.diffDaysDiv').show();
  					$('.sEDateDiv').hide();
  					$('.sEDateDiv').find('input').val('');
  				}else if(feeStatus == '1'){//已缴费 开启分页
  					page.data.isHasPageFun = true;
  					$('#totalWrap').show();
  					$('.diffDaysDiv').hide();
  					$('.sEDateDiv').show();
  				}
  				loadFeeInfoList('initLoad');
 			});
  			form.on('select(diffDaysSel)', function(data){
				var value = data.value;
				value == '' ? $('#diffDaysSelInp').val(15) : $('#diffDaysSelInp').val(value);
				loadFeeInfoList('queryLoad');
  			});
			var page = {
				data : {
					isHasPageFun : false,//用于检测是否需要分页
					globalFeeStatus : 0, //缴费状态
					addFeeFlag : false
				},
				init : function(){
					//获取权限
					this.data.addFeeFlag = common.getPermission('addFee','',0);
					loadFeeInfoList('initLoad');
					this.bindEvent();
				},
				bindEvent : function(){
					//查询
					$('#queryBtn').on('click',function(){
						loadFeeInfoList('queryLoad');
					});
					//导出费用账单
					$('#exportFeeBtn').on('click',function(){
						layer.confirm('确定要导出当前未缴费用账单？',{
							title:'导出未缴费用账单',
						  	skin: 'layui-layer-molv',
						  	btn: ['确定','取消'] //按钮
						},function(index){
							layer.load('1');
							var zlNoInpVal = $.trim($('#zlNoInp').val()),
								ajNoInpVal = $.trim($('#ajNoInp').val()),
								cusIdInpVal = $.trim($('#cusIdInp').val()),
								diffDaysSelInpVal = $('#diffDaysSelInp').val();
							//console.log(zlNoInpVal + "--" + ajNoInpVal + "-" + cusIdInpVal + "-" + diffDaysSelInpVal);
							var form = $("<form>");   //定义一个form表单
							var param = 
							form.attr('style', 'display:block'); //在form表单中添加查询参数
							form.attr('target', '');
							form.attr('method', 'post');
							form.attr('action', "/fee.do?action=exportFeeInfoToExcel_1");
							var input1 = $('<input>');
							input1.attr('type', 'text');
							input1.attr('name', 'fileUrl');
							input1.attr('value', diffDaysSelInpVal);
							input1.attr('zlNo', zlNoInpVal);
							input1.attr('ajNo',ajNoInpVal);
							input1.attr('cusId',cusIdInpVal);
							$('body').append(form);  //将表单放置在web中 
							form.append(input1);   //将查询参数控件提交到表单上
						  	form.submit();
						  	layer.closeAll('loading');
						  	layer.close(index);
						});	
					});
				}
			};
			//加载费用列表
			function loadFeeInfoList(opts){
				var actHei = '';
				if(opts == 'initLoad'){
					if(page.data.globalFeeStatus == '0'){//未缴费
						actHei = 'full-180';
						var field = {feeStatus:0,diffDays:15,zlNo:'',ajNo:'',cusId:0};	
					}else if(page.data.globalFeeStatus == '1'){//已缴费
						actHei = 'full-200';
						var field = {feeStatus:1,zlNo:'',ajNo:'',cusId:0,sDate:'',eDate:''};
					}
				}else{//查询
					var zlNoInpVal = $.trim($('#zlNoInp').val()),
						ajNoInpVal = $.trim($('#ajNoInp').val()),
						cusIdInpVal = $.trim($('#cusIdInp').val()),
						diffDaysSelInpVal = $('#diffDaysSelInp').val(),
						sDateInpVal = $('#sDateInp').val(),
						eDateInpVal = $('#eDateInp').val();
					if(page.data.globalFeeStatus == '0'){//未缴费
						var field = {feeStatus:0,diffDays:diffDaysSelInpVal,zlNo:zlNoInpVal,ajNo:ajNoInpVal,cusId:cusIdInpVal};
					}else{
						var field = {feeStatus:1,zlNo:zlNoInpVal,ajNo:ajNoInpVal,cusId:cusIdInpVal,sDate:sDateInpVal,eDate:eDateInpVal};
					}
					if(sDateInpVal != '' && eDateInpVal == ''){
						layer.msg('请选择缴费结束时间段', {icon:5,anim:6,time:1000});
						return;
					}else if(sDateInpVal == '' && eDateInpVal != ''){
						layer.msg('请选择缴费开始时间段', {icon:5,anim:6,time:1000});
						return;
					}else if(eDateInpVal < sDateInpVal){
						layer.msg('缴费开始时间不能大于缴费结束时间', {icon:5,anim:6,time:1200});
						return;
					}
				}
				layer.load('1');
				table.render({
					elem: '#feeListTab_'+page.data.globalFeeStatus,
					height: actHei,
					url : '/fee.do?action=getAllFeeInfo',
					method : 'post',
					where:field,
					page : page.data.isHasPageFun,
					even : true,
					limit : 10,
					limits:[10,20,30,40],
					cols : [[
						{type: 'numbers', title: '序号', fixed:'left'},
						{field : 'zlName', title: '专利名称', width:200,fixed:'left', align:'center'},
						{field : 'zlNo', title: '专利申请/专利号', width:180, align:'center'},
						{field : 'ajNo', title: '案件编号', width:170, align:'center'},
						{field : 'feeName', title: '费用名称', width:160, align:'center',templet : function(d){
							return '<span class="feeNameSp">'+ d.feeName +'</span>';
						}},
						{field : 'feePrice', title: '专利费用', width:150, align:'center',templet : function(d){
							return '<span class="feeSpan">'+ d.feePrice +'元</span>';
						}},
						{field : page.data.globalFeeStatus == 0 ? 'noneSets' : 'backFee', title: '实收费用', width:160, align:'center',templet : function(d){
							return '<span class="feeSpan">'+ d.backFee +'元</span>';
						}},
						{field : 'sqrName', title: '申请人', width:220, align:'center',templet : function(d){
							if(d.sqrName != ''){
								return common.switchToArray(d.sqrName.split(','));
							}else{
								return '';
							}
						}},
						{field : 'cusId', title: '客户/申请人编号', width:160, align:'center'},
						{field : page.data.globalFeeStatus == 0 ? 'noneSets' : 'jfDate', title: '缴费日期（代理机构缴费日期）', width:230, align:'center'},
						{field : page.data.globalFeeStatus == 0 ? 'noneSets' : 'backDate', title: '收款日期', width:160, align:'center'},
						{field : 'feeEndDateJj', title: '费用截止日期(机构)', width:160, align:'center'},
						{field : 'feeEndDateGf', title: '费用截止日期(官方)', width:160, align:'center'},
						{field : 'feeBatchNo', title: '交费单号', width:150, align:'center'},
						{field : 'bankSerialNo', title: '银行流水号', width:150, align:'center'},
						{field : 'fpDate', title: '开票日期', width:140, align:'center'},
						{field : 'fpNo', title: '票号', width:140, align:'center'},
						{field : page.data.globalFeeStatus == 0 ? 'diffDays_jj' : 'noneSets', title: '机构绝限(天)', width:140, align:'center',templet : function(d){
							if(d.diffDays_jj != undefined){
								var strDiffDays_jj = d.diffDays_jj.toString();
								var newDiffDays_jj = strDiffDays_jj.substring(1,strDiffDays_jj.length);
								return '<span class="overdueSp">已过期'+ newDiffDays_jj +'天</span>';	
							}
						}},
						{field : page.data.globalFeeStatus == 0 ? 'diffDays_Gf' : 'noneSets', title: '官方绝限(天)', width:140, align:'center',templet : function(d){
							if(d.diffDays_Gf != undefined){
								var strDiffDays_Gf = d.diffDays_Gf.toString();
								var newDiffDays_gf = strDiffDays_Gf.substring(1,strDiffDays_Gf.length);
								return '<span class="overdueSp">已过期'+ newDiffDays_gf +'天</span>';
							}
						}}
					]],
					done : function(res, curr, count){
						layer.closeAll('loading');
						if(page.data.globalFeeStatus == 0 || page.data.globalFeeStatus == 1){
							$('.layui-table-box').find('[data-field="noneSets"]').css('display','none');
						}
						callBackDone(res);
					}
				});
				function callBackDone(res){
					layer.closeAll('loading');
					if(res.msg == 'success'){
						$('#feeListTab_'+page.data.globalFeeStatus).siblings('.layui-table-view').show();
						$('#noData_'+page.data.globalFeeStatus).hide().html('');
						if(page.data.globalFeeStatus == 1){
							$('#totalWrap').html('<p>根据系统结算，当前已交费用总计为<span>'+ res.yjFeeTotal +'元</span>，实收费用总计为<span>'+ res.backFeeTotal +'元</span>，未收费用总计未<span>'+ res.noBackFeeTotal +'元</span></p>');
						}
					}else if(res.msg == 'noInfo'){
						$('#totalWrap').hide().html('');
						$('#feeListTab_'+page.data.globalFeeStatus).siblings('.layui-table-view').hide();
		        		$('#noData_'+page.data.globalFeeStatus).show();
		        		if(opts == 'initLoad'){
		        			$('#noData_'+page.data.globalFeeStatus).html("<i class='iconfont layui-extend-noData'></i><p>暂无记录</p>");
		        		}else{
		        			$('#noData_'+page.data.globalFeeStatus).html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
		        		}
					}else if(res.msg == 'noAbility'){
						
					}
				}
			}
			$(function(){
				page.init();
			});
		});
	</script>
  </body>
</html>