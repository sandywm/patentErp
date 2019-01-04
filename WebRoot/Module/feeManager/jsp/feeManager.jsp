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
  					<div id="layuiTabCon" class="layui-tab layui-tab-brief" lay-filter="feeTabFilter">
  						<div class="btnWrap"></div>
  						<ul class="layui-tab-title">
  							<li feeStatus="5" class="layui-this">费用</li>
  							<li feeStatus="3">费用导出记录</li>
  							<li feeStatus="4">费用导入记录</li>
  						</ul>
  						<div class="layui-tab-content">
  							<input id="feeStatusInp" type="hidden" value="0"/>
  							<div class="searchPart layui-form clearfix"></div>
  							<div class="layui-tab-item layui-show">
  								<div id="noData_5" class="noData"></div>
  								<table id="feeListTab_5" class="layui-table" lay-filter="feeInfoListTable"></table>
  								<div id="totalWrap"></div>
  							</div>
  							<div class="layui-tab-item">
  								<div id="noData_3" class="noData"></div>
  								<table id="feeListTab_3" class="layui-table" lay-filter="feeInfoListTable"></table>
  							</div>
  							<div class="layui-tab-item">
  								<div id="noData_4" class="noData"></div>
  								<table id="feeListTab_4" class="layui-table" lay-filter="feeInfoListTable"></table>
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
    	var globalFirId = 0,globalFileName='',globalFeeStatus=0,globalIndex = 0,tmpAddBackFee='',globalFileUrl='',hasReadFlag = false;//是否执行了批量导入并且成功读取结果;
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
  				page.data.globalFeeStatus = feeStatus;//切换交费状态
  				page.createSearchHt(feeStatus);
  				loadFeeInfoList('queryLoad');
  				page.bindEvent();
 			});
  			form.on('select(feeStatusSel)', function(data){
				var value = data.value;
				$('#feeStatusInp').val(value);
				globalFeeStatus = value;
				if($('#feeStatusInp').val() == 0){//未交费不分页
					page.data.isHasPageFun = false;
				}else{
					page.data.isHasPageFun = true;
				}
				page.createSearchHt(5);
				page.bindEvent();
				loadFeeInfoList('initLoad');
  			});
  			form.on('select(diffDaysSel)', function(data){
				var value = data.value;
				value == '' ? $('#diffDaysSelInp').val(-1) : $('#diffDaysSelInp').val(value);
				loadFeeInfoList('queryLoad');
  			});
  			//选择交费对象
  			form.on('select(qdStatusSel)', function(data){
				var value = data.value;
				$('#qdStatusInp').val(value);
				loadFeeInfoList('queryLoad');
  			});
  			//获取导出未交费记录
			var active = {
			    getCheckData: function(){
			      	var checkStatus = table.checkStatus('feeListTab_5'),data = checkStatus.data;
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
					globalFeeStatus : 5, //费用
					addFeeFlag : false,
					globalFeeId : [],
					exportFlag : false
				},
				init : function(){
					//获取权限
					this.data.addFeeFlag = common.getPermission('addFee','',0);
					this.createSearchHt(5);
					loadFeeInfoList('initLoad');
					this.bindEvent();
				},
				bindEvent : function(){
					var _this = this;
					this.queryFun();
					//交费单据批量导入
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
							layer.msg('抱歉，您暂无批量导入交费单据的权限', {icon:5,anim:6,time:1200});
						}
					});
					//导出费用账单(未交费 导出国家专利局 导出客户清单)
					$('#exportFeeBtn_noSub').on('click',function(){
						if(_this.data.addFeeFlag){
							var type = $(this).data('type');
						    active[type] ? active[type].call(this) : '';
						    if(_this.data.exportFlag){
							    var idStr = '',qdStatus = $('#qdStatusInp').val();
							    idStr = page.data.globalFeeId.join(',');
							    layer.load('1');
							    var form = $("<form>");   //定义一个form表单
								form.attr('style', 'display:none;'); //在form表单中添加查询参数
								form.attr('target', '');
								form.attr('method', 'post');
								form.attr('action', "/fee.do?action=exportFeeInfoToExcel_1&feeStatus=0&qdStatus=" + qdStatus);
								var input1 = $('<input>');
								input1.attr('type', 'text');
								input1.attr('name', 'idStr');
								input1.attr('value', idStr);
								$('body').append(form);  //将表单放置在web中 
								form.append(input1);   //将查询参数控件提交到表单上
							  	form.submit();
								layer.closeAll('loading');
						    }
						}else{
							layer.msg('抱歉，您暂无导出费用账单的权限', {icon:5,anim:6,time:1200});
						}
					});
					//导出费用(已缴费 全部)
					$('#exportFeeBtn_hasSub').on('click',function(){
						if(_this.data.addFeeFlag){
							tmpAddBackFee = 'addBackFeeStr';
							var fullScreenIndex = globalIndex = layer.open({
								title:'',
								type: 2,
							  	area: ['700px', '500px'],
							  	fixed: true, //不固定
							  	maxmin: false,
							  	shadeClose :false,
							  	closeBtn:0,
							  	content: '/Module/feeManager/jsp/exportFeeList.html'
							});	
							layer.full(fullScreenIndex);
						}else{
							layer.msg('抱歉，您暂无导出费用账单的权限', {icon:5,anim:6,time:1200});
						}
					});
				},
				queryFun : function(){
					$('#queryBtn').on('click',function(){
						if(page.data.globalFeeStatus == 5 && $('#zlNoInp').val() != 0 && $('#zlNoInp').val().length < 4){
							layer.msg('专利编号必须4位以上', {icon:5,anim:6,time:1200});
							return;
						}
						loadFeeInfoList('queryLoad');
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
					$('.resetTime').on('click',function(){
						$('#zlNoInp').val('');
						$('#cusIdInp').val('');
						$('.cusName').html('请选择客户').attr('title','').css({'color':'#888'});
						$('#sDateInp').val('');
						$('#eDateInp').val('');
						loadFeeInfoList('queryLoad');
					});
				},
				//创建查询层
				createSearchHt : function(feeStatus){
					var strHtmlBtn_noSub = '',strHtmlBtn_hasSub = '',strHtml_impBtn='';
					var strHtml1='',strHtml2='',strHtml3='',strHtml4='' ,strHtml5='',strHtml6='',strHtml7='';
					strHtmlBtn_noSub += '<a id="exportFeeBtn_noSub" class="posAbs newAddBtn exportFeeBtn_noSub" data-type="getCheckData" href="javascript:void(0)"><i class="iconfont layui-extend-daochuexcel"></i>导出未交费用账单</a>';
					strHtmlBtn_hasSub += '<a id="exportFeeBtn_hasSub" class="posAbs newAddBtn"href="javascript:void(0)"><i class="iconfont layui-extend-daochuexcel"></i>导出费用账单</a>';
					strHtml_impBtn += '<a id="importFeeBtn" class="posAbs newAddBtn" href="javascript:void(0)"><i class="iconfont layui-extend-daoru"></i>批量导入交费单据</a>';
					strHtml1 += '<div class="itemDiv fl" style="width:120px;"><div class="layui-input-inline">';
					strHtml1 += '<select id="feeStatusSel" lay-filter="feeStatusSel"><option value="2">全部</option>';
					strHtml1 += '<option value="0" selected>未交费</option><option value="1">已交费</option>';
					strHtml1 += '</select></div></div>';
					strHtml6 += '<div class="itemDiv fl" style="width:180px;"><div class="layui-input-inline"><input type="hidden" id="qdStatusInp" value="0"/>';
					strHtml6 += '<select id="qdStatusSel" lay-filter="qdStatusSel">';
					strHtml6 += '<option value="0" selected>国家专利局交费清单</option><option value="1">客户交费清单</option>';
					strHtml6 += '</select></div></div>';
					strHtml7 += '<div class="itemDiv zlNoDiv fl"><div class="layui-input-inline" style="width:180px;">';
					strHtml7 += '<input type="text" id="zlNoInp" placeholder="请输入专利申请号/专利号" autocomplete="off" class="layui-input"></div></div>';
					strHtml7 += '<div class="itemDiv cusIdDiv fl"><div class="layui-input-inline">';
					strHtml7 += '<input type="hidden" id="cusIdInp"/><div class="layui-input selCusP"><p class="cusName ellip">请选择客户</p><i class="layui-edge"></i></div></div></div>';
					strHtml2 += '<div class="itemDiv fl"><div class="layui-input-inline"><input id="diffDaysSelInp" type="hidden" value="15"/>';
					strHtml2 += '<select id="diffDaysSel" lay-filter="diffDaysSel"><option value="">请选择距代理机构交费期限(全部)</option>';
					strHtml2 += '<option value="1">1天</option><option value="7">7天</option><option value="15" selected>15天</option><option value="30">30天</option><option value="60">60天</option>';
					strHtml2 += '</select></div></div>';
					strHtml3 += '<div class="itemDiv sEDateDiv fl"><div class="layui-input-inline">';
					strHtml3 += '<input type="text" id="sDateInp" placeholder="请选择交费日期(开始)" autocomplete="off" class="layui-input"></div></div>';
					strHtml3 += '<div class="itemDiv sEDateDiv fl"><div class="layui-input-inline">';
					strHtml3 += '<input type="text" id="eDateInp" placeholder="请选择交费日期(结束)" autocomplete="off" class="layui-input"></div></div>';
					strHtml5 += '<a class="resetTime" href="javascript:void(0)"><i class="layui-icon layui-icon-refresh"></i>重置</a>';
					strHtml4 += '<div class="itemDiv fl"><div class="layui-input-inline">';
					strHtml4 += '<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search"></i></button></div></div>';
					if(feeStatus == 5){
						var feeStaInp = $('#feeStatusInp').val();
						if(feeStaInp == 0){
							$('.btnWrap').html(strHtmlBtn_noSub);
							$('.searchPart').html(strHtml1 + strHtml6 + strHtml7 + strHtml2 + strHtml4 + strHtml5);
						}else if(feeStaInp == 1){//已交费
							$('.btnWrap').html(strHtmlBtn_hasSub);
							$('.searchPart').html(strHtml1 + strHtml7 +  strHtml3 + strHtml4 + strHtml5);
							$('#feeStatusSel option:last').attr('selected','selected');
						}else{
							$('.btnWrap').html(strHtmlBtn_hasSub);
							$('.searchPart').html(strHtml1 + strHtml7 + strHtml4 + strHtml5);
							$('#feeStatusSel option:first').attr('selected','selected');
						}
						form.render();
					}else if(feeStatus == 3 || feeStatus == 4){
						if(feeStatus == 4){
							$('.btnWrap').html(strHtml_impBtn);
						}else if(feeStatus == 3){
							$('.btnWrap').html('');
						}
						$('.searchPart').html(strHtml3 + strHtml4 + strHtml5);
						form.render();
					}
					laydate.render({elem:'#sDateInp'});
					laydate.render({elem:'#eDateInp'});
				}
			};
					  
			//加载费用列表
			function loadFeeInfoList(opts){
				var feeStatusInpVal = $('#feeStatusInp').val();
				if(opts == 'initLoad'){
					if(page.data.globalFeeStatus == 5){//费用
						if(feeStatusInpVal == 0){
							var field = {feeStatus:feeStatusInpVal,qdStatus:0,diffDays:15,zlNo:'',cusId:0};	
						}else{
							var field = {feeStatus:feeStatusInpVal,zlNo:'',cusId:0,sDate:'',eDate:''};
						}
					}else if(page.data.globalFeeStatus == 3 || page.data.globalFeeStatus == 4){//费用导出记录 / 费用导入记录
						var field = {sDate:'',eDate:''};
					}
				}else{//查询
					var zlNoInpVal = $.trim($('#zlNoInp').val()),
						cusIdInpVal = $('#cusIdInp').val(),
						qdStatusInpVal = $('#qdStatusInp').val(),
						diffDaysSelInpVal = $('#diffDaysSelInp').val(),
						sDateInpVal = $('#sDateInp').val(),
						eDateInpVal = $('#eDateInp').val();
					if(page.data.globalFeeStatus == 5){//费用
						if(feeStatusInpVal == 0){
							var field = {feeStatus:feeStatusInpVal,qdStatus:qdStatusInpVal,diffDays:diffDaysSelInpVal,zlNo:zlNoInpVal,cusId:cusIdInpVal};
							console.log(qdStatusInpVal)
						}else{
							var field = {feeStatus:feeStatusInpVal,zlNo:zlNoInpVal,cusId:cusIdInpVal,sDate:sDateInpVal,eDate:eDateInpVal};	
						}
					}else if(page.data.globalFeeStatus == 3 || page.data.globalFeeStatus == 4){//费用导出记录 / 费用导入记录
						var field = {sDate:sDateInpVal,eDate:eDateInpVal};
					}
					if(sDateInpVal != '' && eDateInpVal == ''){
						layer.msg('请选择交费结束时间段', {icon:5,anim:6,time:1200});
						return;
					}else if(sDateInpVal == '' && eDateInpVal != ''){
						layer.msg('请选择交费开始时间段', {icon:5,anim:6,time:1200});
						return;
					}else if(eDateInpVal < sDateInpVal){
						layer.msg('交费开始时间不能大于交费结束时间', {icon:5,anim:6,time:1200});
						return;
					}
				}
				layer.load('1');
				if(page.data.globalFeeStatus == 5){
					var overdueSp = '';
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
							{type : feeStatusInpVal == 0 ? 'checkbox' : 'numbers', title: '序号', fixed:'left'},
							{field : 'zlName', title: '专利名称', width:200,fixed:'left', align:'center',templet : function(d){
								if(d.bankSerialNo == ''){//未交费
									return '<span class="noPayStatus"></span>' + d.zlName;
								}else{//已交费
									return '<span class="hasPayStatus"></span>' + d.zlName ;
								}
								
							}},
							{field : 'zlNo', title: '专利申请/专利号', width:180, align:'center'},
							{field : 'ajNo', title: '案件编号', width:170, align:'center'},
							{field : page.data.globalFeeStatus == 0 ? 'diffDays_jj' : 'noneSets', title: '机构绝限(天)', width:140, align:'center',templet : function(d){
								if(d.diffDays_jj != undefined){
									var strDiffDays_jj = d.diffDays_jj.toString();
									if(strDiffDays_jj >= 20){
										overdueSp = 'greenColor';
									}else if(strDiffDays_jj >= 10 && strDiffDays_jj < 20){
										overdueSp = 'blueColor';
									}else if(strDiffDays_jj < 10 && strDiffDays_jj > 0){
										overdueSp = 'yellowColor';
									}else if(strDiffDays_jj < 0){
										overdueSp = 'redColor';
									}
									if(strDiffDays_jj > 0){
										return '<span class="'+ overdueSp +'">'+ strDiffDays_jj +'天后到期</span>';
									}else{
										if(strDiffDays_jj == 0){
											if(feeStatusInpVal == 0){
												return '<span class="'+ overdueSp +'">明天到期</span>';
											}else{
												return '<span class="hasPayTxt">已交费</span>';
											}
										}else{
											var newDiffDays_jj = strDiffDays_jj == 0 ? 0 : strDiffDays_jj.substring(1,strDiffDays_jj.length);
											return '<span class="'+ overdueSp +'">已过期'+ newDiffDays_jj +'天</span>';	
										}
									}
								}
							}},
							{field : page.data.globalFeeStatus == 0 ? 'diffDays_Gf' : 'noneSets', title: '官方绝限(天)', width:140, align:'center',templet : function(d){
								if(d.diffDays_Gf != undefined){
									var strDiffDays_Gf = d.diffDays_Gf.toString();
									if(strDiffDays_Gf >= 20){
										overdueSp = 'greenColor';
									}else if(strDiffDays_Gf >= 10 && strDiffDays_Gf < 20){
										overdueSp = 'blueColor';
									}else if(strDiffDays_Gf < 10  && strDiffDays_Gf > 0){
										overdueSp = 'yellowColor';
									}else if(strDiffDays_Gf < 0){
										overdueSp = 'redColor';
									}
									if(strDiffDays_Gf > 0){
										return '<span class="'+ overdueSp +'">'+ strDiffDays_Gf +'天后到期</span>';
									}else{
										if(strDiffDays_Gf == 0){
											if(feeStatusInpVal == 0){
												return '<span class="'+ overdueSp +'">明天到期</span>';
											}else{
												return '<span class="hasPayTxt">已交费</span>';
											}
										}else{
											var newDiffDays_gf = strDiffDays_Gf.substring(1,strDiffDays_Gf.length);
											return '<span class="'+ overdueSp +'">已过期'+ newDiffDays_gf +'天</span>';
										}
									}
								}
							}},
							{field : 'feeName', title: '费用名称', width:160, align:'center',templet : function(d){
								return '<span class="feeNameSp">'+ d.feeName +'</span>';
							}},
							{field : 'feePrice', title: '专利费用', width:150, align:'center',templet : function(d){
								return '<span class="feeSpan">¥'+ d.feePrice +'元</span>';
							}},
							{field : page.data.globalFeeStatus == 0 ? 'noneSets' : 'backFee', title: '实收费用', width:160, align:'center',templet : function(d){
								return '<span class="feeSpan">¥'+ d.backFee +'元</span>';
							}},
							{field : 'sqrName', title: '申请人', width:220, align:'center',templet : function(d){
								if(d.sqrName != ''){
									return common.switchToArray(d.sqrName.split(','));
								}else{
									return '';
								}
							}},
							{field : 'cusId', title: '客户/申请人编号', width:160, align:'center'},
							{field : page.data.globalFeeStatus == 0 ? 'noneSets' : 'jfDate', title: '交费日期（代理机构交费日期）', width:230, align:'center'},
							{field : page.data.globalFeeStatus == 0 ? 'noneSets' : 'backDate', title: '收款日期', width:160, align:'center'},
							{field : 'feeEndDateJj', title: '费用截止日期(机构)', width:160, align:'center'},
							{field : 'feeEndDateGf', title: '费用截止日期(官方)', width:160, align:'center'},
							{field : 'feeBatchNo', title: '交费单号', width:150, align:'center'},
							{field : 'bankSerialNo', title: '银行流水号', width:150, align:'center'},
							{field : 'fpDate', title: '开票日期', width:140, align:'center'},
							{field : 'fpNo', title: '票号', width:140, align:'center'}
						]],
						done : function(res, curr, count){
							callBackDone(res);
						}
					});
				}else if(page.data.globalFeeStatus == 3){//费用导出记录
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
							callBackDone(res);
						}
					});
				}else if(page.data.globalFeeStatus == 4){//费用导入记录
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
								return '<a lay-event="viewImpRes" fileName="'+ d.fileName +'" fileUrl="'+ d.excelPath +'" firId="'+ d.firId +'" class="layui-btn layui-btn-primary layui-btn-xs"><i class="layui-icon layui-icon-search"></i>查看结果</a> <a lay-event="downFileFun" downFilePath="'+ d.excelPath +'" class="layui-btn layui-btn-xs"><i class="layui-icon layui-icon-download-circle"></i>下载</a>';
							}},
						]],
						done : function(res, curr, count){
							callBackDone(res);
						}
					});
				}
				function callBackDone(res){
					layer.closeAll('loading');
					if(res.msg == 'success'){
						$('#feeListTab_'+page.data.globalFeeStatus).siblings('.layui-table-view').show();
						$('#noData_'+page.data.globalFeeStatus).hide().html('');
						if(page.data.globalFeeStatus == 5){
							var feeStatusInpVal = $('#feeStatusInp').val(),qdStatusInpVal = $('#qdStatusInp').val();
							if(feeStatusInpVal == 0){
								$('#totalWrap').html('<p>根据系统结算，当前应交费用总计为<span>'+ res.wjFeeTotal +'元</span></p>');
							}else if(feeStatusInpVal == 1){
								$('#totalWrap').html('<p>根据系统结算，当前已交费用总计为<span>'+ res.yjFeeTotal +'元</span>，实收费用总计为<span>'+ res.backFeeTotal +'元</span>，未收费用总计为<span>'+ res.noBackFeeTotal +'元</span></p>');	
							}else{//全部
								$('#totalWrap').html('<p>根据系统结算，当前所用费用总计为<span>'+ res.feeTotal +'</span>其中，应交费用总计为<span>'+ res.wjFeeTotal +'元</span>，已交费用总计为<span>'+ res.yjFeeTotal +'元</span>，实收费用总计为<span>'+ res.backFeeTotal +'元</span>，未收费用总计为<span>'+ res.noBackFeeTotal +'元</span></p>');	
							}
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
						layer.msg('抱歉，您暂无权限', {icon:5,anim:6,time:1000});
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
					globalFileUrl = $(this).attr('fileUrl');
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
