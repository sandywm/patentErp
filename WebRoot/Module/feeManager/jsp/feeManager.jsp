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
  						<a id="exportFeeBtn_noSub" class="posAbs newAddBtn" data-type="getCheckData" href="javascript:void(0)"><i class="iconfont layui-extend-daochuexcel"></i>导出未缴费用账单</a>
  						<a id="importFeeBtn" class="posAbs newAddBtn" data-type="getCheckData" href="javascript:void(0)"><i class="iconfont layui-extend-daoru"></i>批量导入缴费单据</a>
  						<ul class="layui-tab-title">
  							<li class="layui-this" feeStatus="0">未缴费</li>
  							<li feeStatus="1">已缴费</li>
  							<li feeStatus="2">费用导出记录</li>
  							<li feeStatus="3">费用导入记录</li>
  						</ul>
  						<div class="layui-tab-content">
  							<div class="searchPart layui-form clearfix"></div>
  							<div class="layui-tab-item layui-show">
  								<div id="noData_0" class="noData"></div>
  								<table id="feeListTab_0" class="layui-table" lay-filter="feeInfoListTable"></table>
  							</div>
  							<div class="layui-tab-item">
  								<div id="noData_1" class="noData"></div>
  								<table id="feeListTab_1" class="layui-table" lay-filter="feeInfoListTable"></table>
  								<div id="totalWrap"></div> 
  							</div>
  							<div class="layui-tab-item">
  								<div id="noData_2" class="noData"></div>
  								<table id="feeListTab_2" class="layui-table" lay-filter="feeInfoListTable"></table>
  							</div>
  							<div class="layui-tab-item">
  								<div id="noData_3" class="noData"></div>
  								<table id="feeListTab_3" class="layui-table" lay-filter="feeInfoListTable"></table>
  							</div>
  						</div> 
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/jquery/jquery.min.js"></script>
   	<script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
    	var globalFirId = 0,globalFileName = '',hasReadFlag = false;//是否执行了批量导入并且成功读取结果;
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
			//tab点击事件的监听
  			element.on('tab(feeTabFilter)', function(data){
  				var feeStatus = $(this).attr('feeStatus');
  				page.data.globalFeeStatus = feeStatus;//切换缴费状态
  				$('#exportFeeBtn_noSub').hide();
  				$('#importFeeBtn').hide();
  				if(feeStatus == '0'){//未缴费 不分页
  					page.data.isHasPageFun = false;
  					$('#exportFeeBtn_noSub').show();
  				}else if(feeStatus == '1'){//已缴费 开启分页
  					page.data.isHasPageFun = true;
  				}else if(feeStatus == '2'){

  				}else if(feeStatus == '3'){
  					$('#importFeeBtn').show();
  				}
  				page.createSearchHt(feeStatus);
  				loadFeeInfoList('initLoad');
  				page.queryFun();
 			});
  			form.on('select(diffDaysSel)', function(data){
				var value = data.value;
				value == '' ? $('#diffDaysSelInp').val(15) : $('#diffDaysSelInp').val(value);
				loadFeeInfoList('queryLoad');
  			});
  			//获取导出未缴费记录
			var active = {
			    getCheckData: function(){
			      	var checkStatus = table.checkStatus('feeListTab_0'),data = checkStatus.data;
			      	page.data.exportFlag = false;
			      	page.data.globalFeeId.length = 0;
			    	if(data.length > 0){
				    	for(var i=0;i<data.length;i++){
				    		page.data.globalFeeId.push(data[i].feeId);
				    	}
				    	page.data.exportFlag = true;
			    	}else{
			    		layer.msg('请选择您要导出的费用记录', {icon:5,anim:6,time:1000});
						return;
			    	}
			    }
			};
			var page = {
				data : {
					isHasPageFun : false,//用于检测是否需要分页
					globalFeeStatus : 0, //缴费状态
					addFeeFlag : false,
					globalFeeId : [],
					exportFlag : false
				},
				init : function(){
					//获取权限
					this.data.addFeeFlag = common.getPermission('addFee','',0);
					$('#exportFeeBtn_noSub').show();
					this.createSearchHt(0);
					loadFeeInfoList('initLoad');
					this.bindEvent();
					$.ajax({
	   					type:'post',
	   			        async:false,
	   			        dataType:'json',
	   			        data:{sDate:'',eDate:''},
	   			        //url:'/fee.do?action=getPageFER',
	   			        url : '/fee.do?action=getPageFIR',
	   			        success:function (json){
	   			        	layer.closeAll('loading');
	   			        	console.log(json)
	   			        }
					});
				},
				bindEvent : function(){
					var _this = this;
					this.queryFun();
					//缴费单据批量导入
					$('#importFeeBtn').on('click',function(){
						if(_this.data.addFeeFlag){
							hasReadFlag = false;
							var fullScreenIndex = layer.open({
								title:'',
								type: 2,
							  	area: ['700px', '500px'],
							  	fixed: true, //不固定
							  	maxmin: false,
							  	shadeClose :false,
							  	closeBtn:0,
							  	content: '/Module/feeManager/jsp/batchImport.html',
							  	end:function(){
							  		if(hasReadFlag){
							  			loadFeeInfoList('initLoad');
							  		}
							  	}
							});	
							layer.full(fullScreenIndex);
						}else{
							layer.msg('抱歉，您暂无批量导入缴费单据的权限', {icon:5,anim:6,time:1200});
						}
					});
					//导出费用账单
					$('#exportFeeBtn_noSub').on('click',function(){
						var type = $(this).data('type');
					    active[type] ? active[type].call(this) : '';
					    if(_this.data.exportFlag){
						    var idStr = '';
						    idStr = page.data.globalFeeId.join(',');
						    layer.load('1');
						    var form = $("<form>");   //定义一个form表单
							form.attr('style', 'display:none;'); //在form表单中添加查询参数
							form.attr('target', '');
							form.attr('method', 'post');
							form.attr('action', "/fee.do?action=exportFeeInfoToExcel_1");
							var input1 = $('<input>');
							input1.attr('type', 'hidden');
							input1.attr('name', 'idStr');
							input1.attr('value', idStr);
							$('body').append(form);  //将表单放置在web中 
							form.append(input1);   //将查询参数控件提交到表单上
						  	form.submit();
							layer.closeAll('loading');
					    }
					});
				},
				queryFun : function(){
					$('#queryBtn').on('click',function(){
						loadFeeInfoList('queryLoad');
					});
					$('.resetTime').on('click',function(){
						$('#sDateInp').val('');
						$('#eDateInp').val('');
						loadFeeInfoList('queryLoad');
						
					});
				},
				//创建查询层
				createSearchHt : function(feeStatus){
					var strHtml1='',strHtml2='',strHtml3='',strHtml4='' ,strHtml5='';
					strHtml1 += '<div class="itemDiv zlNoDiv fl"><div class="layui-input-inline">';
					strHtml1 += '<input type="text" id="zlNoInp" placeholder="请输入专利申请/专利号" autocomplete="off" class="layui-input"></div></div>';
					strHtml1 += '<div class="itemDiv ajNoDiv fl"><div class="layui-input-inline">';
					strHtml1 += '<input type="text" id="ajNoInp" placeholder="请输入案件编号" autocomplete="off" class="layui-input"></div></div>';
					strHtml1 += '<div class="itemDiv cusIdDiv fl"><div class="layui-input-inline">';
					strHtml1 += '<input type="text" id="cusIdInp" placeholder="客户/申请人编号" autocomplete="off" class="layui-input"></div></div>';
					strHtml2 += '<div class="itemDiv diffDaysDiv fl"><div class="layui-input-inline"><input id="diffDaysSelInp" type="hidden" value="15"/>';
					strHtml2 += '<select id="diffDaysSel" lay-filter="diffDaysSel"><option value="">请选择距代理机构缴费期限(全部)</option>';
					strHtml2 += '<option value="1">1天</option><option value="7">7天</option><option value="15">15天</option><option value="30">30天</option><option value="60">60天</option>';
					strHtml2 += '</select></div></div>';
					strHtml3 += '<div class="itemDiv sEDateDiv fl"><div class="layui-input-inline">';
					strHtml3 += '<input type="text" id="sDateInp" placeholder="请选择缴费日期(开始)" autocomplete="off" class="layui-input"></div></div>';
					strHtml3 += '<div class="itemDiv sEDateDiv fl"><div class="layui-input-inline">';
					strHtml3 += '<input type="text" id="eDateInp" placeholder="请选择缴费日期(结束)" autocomplete="off" class="layui-input"></div></div>';
					strHtml5 += '<a class="resetTime" href="javascript:void(0)"><i class="layui-icon layui-icon-refresh"></i>重置</a>';
					strHtml4 += '<div class="itemDiv fl"><div class="layui-input-inline">';
					strHtml4 += '<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search"></i></button></div></div>';
					if(feeStatus == 0){
						$('.searchPart').html(strHtml1 + strHtml2 + strHtml4);
						form.render();
					}else if(feeStatus == 1){
						$('.searchPart').html(strHtml1 + strHtml3 + strHtml4);
					}else if(feeStatus == 2 || feeStatus == 3){
						$('.searchPart').html(strHtml3 + strHtml4 + strHtml5);
						form.render();
					}
					laydate.render({elem:'#sDateInp'});
					laydate.render({elem:'#eDateInp'});
				}
			};
					  
			//加载费用列表
			function loadFeeInfoList(opts){
				if(opts == 'initLoad'){
					if(page.data.globalFeeStatus == 0){//未缴费
						var field = {feeStatus:0,diffDays:15,zlNo:'',ajNo:'',cusId:0};	
					}else if(page.data.globalFeeStatus == 1){//已缴费
						var field = {feeStatus:1,zlNo:'',ajNo:'',cusId:0,sDate:'',eDate:''};
					}else if(page.data.globalFeeStatus == 2 || page.data.globalFeeStatus == 3){//费用导出记录 / 费用导入记录
						var field = {sDate:'',eDate:''};
					}
				}else{//查询
					var zlNoInpVal = $.trim($('#zlNoInp').val()),
						ajNoInpVal = $.trim($('#ajNoInp').val()),
						cusIdInpVal = $.trim($('#cusIdInp').val()),
						diffDaysSelInpVal = $('#diffDaysSelInp').val(),
						sDateInpVal = $('#sDateInp').val(),
						eDateInpVal = $('#eDateInp').val();
					if(page.data.globalFeeStatus == 0){//未缴费
						var field = {feeStatus:0,diffDays:diffDaysSelInpVal,zlNo:zlNoInpVal,ajNo:ajNoInpVal,cusId:cusIdInpVal};
					}else if(page.data.globalFeeStatus == 1){
						var field = {feeStatus:1,zlNo:zlNoInpVal,ajNo:ajNoInpVal,cusId:cusIdInpVal,sDate:sDateInpVal,eDate:eDateInpVal};
					}else if(page.data.globalFeeStatus == 2 || page.data.globalFeeStatus == 3){//费用导出记录 / 费用导入记录
						var field = {sDate:sDateInpVal,eDate:eDateInpVal};
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
				//layer.load('1');
				if(page.data.globalFeeStatus == 0 || page.data.globalFeeStatus == 1){
					table.render({
						elem: '#feeListTab_'+page.data.globalFeeStatus,
						height: 'full-200',
						url : '/fee.do?action=getAllFeeInfo',
						method : 'post',
						where:field,
						page : page.data.isHasPageFun,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						cols : [[
							{type : page.data.globalFeeStatus == 0 ? 'checkbox' : 'numbers', title: '序号', fixed:'left'},
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
				}else if(page.data.globalFeeStatus == 2){//费用导出记录
					table.render({
						elem: '#feeListTab_'+page.data.globalFeeStatus,
						height: 'full-200',
						url : '/fee.do?action=getPageFER',
						method : 'post',
						where:field,
						page : true,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						cellMinWidth : 120,
						cols : [[
							{type : 'numbers', title: '序号'},
							{field : 'ferName', title: '文件名', align:'center'},
							{field : 'addTime', title: '导出时间',  align:'center'},
							{field : 'userName', title: '导出者',align:'center'},
							{field : '', title: '下载',width:100, align:'center',templet : function(d){
								return '<a lay-event="downFileFun" downFilePath="'+ d.excelPath +'" class="layui-btn layui-btn-xs"><i class="layui-icon layui-icon-download-circle"></i>下载</a>';
							}},
						]],
						done : function(res, curr, count){
							layer.closeAll('loading');
							if(page.data.globalFeeStatus == 0 || page.data.globalFeeStatus == 1){
								$('.layui-table-box').find('[data-field="noneSets"]').css('display','none');
							}
							callBackDone(res);
						}
					});
				}else if(page.data.globalFeeStatus == 3){//费用导入记录
					table.render({
						elem: '#feeListTab_'+page.data.globalFeeStatus,
						height: 'full-200',
						url : '/fee.do?action=getPageFIR',
						method : 'post',
						where:field,
						page : true,
						even : true,
						limit : 10,
						limits:[10,20,30,40],
						cellMinWidth : 160,
						cols : [[
							{type : 'numbers', title: '序号'},
							{field : 'fileName', title: '文件名',width:380, align:'center'},
							{field : 'addTime', title: '导入时间',  align:'center'},
							{field : 'userName', title: '导入者',align:'center'},
							{field : '', title: '操作',width:180, align:'center',templet : function(d){
								return '<a lay-event="viewImpRes" fileName="'+ d.fileName +'" firId="'+ d.firId +'" class="layui-btn layui-btn-primary layui-btn-xs"><i class="layui-icon layui-icon-search"></i>查看结果</a> <a lay-event="downFileFun" downFilePath="'+ d.excelPath +'" class="layui-btn layui-btn-xs"><i class="layui-icon layui-icon-download-circle"></i>下载</a>';
							}},
						]],
						done : function(res, curr, count){
							layer.closeAll('loading');
							if(page.data.globalFeeStatus == 0 || page.data.globalFeeStatus == 1){
								$('.layui-table-box').find('[data-field="noneSets"]').css('display','none');
							}
							callBackDone(res);
						}
					});
				}
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
			table.on('tool(feeInfoListTable)',function(obj){
				if(obj.event == 'downFileFun'){//下载
					var downFilePath = $(this).attr('downFilePath');
					common.downFiles(downFilePath,1);
				}else if(obj.event == 'viewImpRes'){//查看导入结果
					globalFirId = $(this).attr('firId');
					globalFileName = $(this).attr('fileName');
					var fullScreenIndex = layer.open({
						title:'',
						type: 2,
					  	area: ['700px', '500px'],
					  	fixed: true, //不固定
					  	maxmin: false,
					  	shadeClose :false,
					  	closeBtn : 0,
					  	content: '/Module/feeManager/jsp/viewReadRes.html'
					});	
					layer.full(fullScreenIndex);
				}
			});
			$(function(){
				page.init();
			});
		});
	</script>
  </body>
</html>
