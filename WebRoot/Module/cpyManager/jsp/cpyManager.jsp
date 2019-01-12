<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>代理机构管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="keywords" content="专利管理系统,代理机构管理">
	<meta http-equiv="description" content="专利管理系统代理机构管理">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/cpyManager/css/cpyManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
	<script src="http://at.alicdn.com/t/font_787377_fddbgkpmuto.js"></script>
  </head>
  <body>
  	<div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header posRel">
  						<span id="cpyManagerTxt"></span>
  					</div>
  					<div class="layui-card-body" pad15>
  						<!-- 查询条件 -->
	  					<div class="layui-form searchForm clearfix">
	  						<div class="itemDiv fl" style="width:115px;">
	  							<div class="layui-input-inline">
	  								<input id="provInp" type="hidden" value=""/>
	  								 <select id="cpyProvSel" name="cpyProvSel" lay-filter="province">
	  								 	<option value="">请选择省</option>
	  								 </select> 
	  							</div>
	  						</div>
	  						<div class="itemDiv fl" style="width:115px;">
	  							<div class="layui-input-inline">
	  								<input id="cityInp" type="hidden" value=""/>
	  								 <select id="cpyCitySel" name="cpyCitySel" lay-filter="city" disabled>
	  								 	<option value="">请选择市</option>
	  								 </select> 
	  							</div>
	  						</div>
	  						<div id="accYxWrap" class="itemDiv fl" style="width:135px;">
	  							<div class="layui-input-inline">
	  								<input id="accStatusInp" type="hidden" value="-1"/>
	  								<select id="accStatusSel" lay-filter="accStatusSel">
								       	<option value="">会员有效状态</option>
								        <option value="0">未过期</option>
						    			<option value="1">已过期</option>
								      </select>
	  							</div>
	  						</div>
	  						<div class="itemDiv fl" style="width:160px;">
	  							<div class="layui-input-inline">
	  								<input id="levelSelInp" type="hidden" value="-1"/>
	  								<select id="levelSel" lay-filter="levelSel">
								       	<option value="">代理机构会员级别</option>
								    	<option value="0">铜牌</option>
						    			<option value="1">银牌</option>
						    			<option value="2">金牌</option>
						    			<option value="3">钻石</option>
								    </select>
	  							</div>
	  						</div>
	  						<div class="itemDiv fl">
	  							<div class="layui-input-inline">
	  								<input type="text" id="agencyPyInp" placeholder="代理机构拼音首字母" autocomplete="off" class="layui-input">
	  							</div>
	  						</div>
	  						<div id="frNameWrap" class="itemDiv fl" style="width:120px;">
	  							<div class="layui-input-inline">
	  								<input type="text" id="cpyFrInp" placeholder="请输入法人姓名" autocomplete="off" class="layui-input">
	  							</div>
	  						</div>
	  						<div id="lxrNameWrap" class="itemDiv fl" style="width:130px;">
	  							<div class="layui-input-inline">
	  								<input type="text" id="cpyLxrInp" placeholder="请输入联系人姓名" autocomplete="off" class="layui-input">
	  							</div>
	  						</div>
	  						<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search
 "></i></button>
	  					</div>
  						<div id="cpyList">
  							<div class='noData'></div>
  							<table id="cpyListTable" class="layui-table" lay-filter="cpyListTable"></table>
  						</div>
	  				</div>
  				</div>
  			</div>
  		</div>
  	</div>	
  	<script src="/plugins/layui/layui.js"></script>
  	<script type="text/javascript">
  		var loginType = parent.loginType;
  		var sonUseObj = {
  			globalCpyId : 0,
  			cpyId : 0,
  			cpyLevel : 0,
  			cpyEndDate : '',
  			globalIndex : 0
  		};
  		layui.config({
			base : '/plugins/frame/js/'
		}).use(['layer','table','jquery','form','laydate','address'],function(){
  			var layer = layui.layer,
  				$ = layui.jquery,
  				table = layui.table,
  				laydate = layui.date,
  				form = layui.form,
  				address = layui.address();
  			$('#queryBtn').on('click',function(){
  				loadQueryCpyList('queryLoad');
			});
  			if(loginType == 'spUser'){
  				$('#accYxWrap').show();
  				$('#frNameWrap').show();
  				$('#lxrNameWrap').show();
				$('#cpyManagerTxt').html('代理机构管理');
  			}else{
				$('#cpyManagerTxt').html('查看代理机构');
			}
			//加载代理机构列表
  			function loadQueryCpyList(opts){
  				if(opts == 'loadListData'){//初始化加载
  					if(loginType == 'spUser'){
  						var field = {cpyName:'',cpyProv:'',cpyCity:'',cpyLxr:'',cpyFr:'',cpyLevel:-1,yxStatus:-1};
  					}else if(loginType == 'appUser'){
  						var field = {cpyName:'',cpyProv:'',cpyCity:'',cpyLxr:'',cpyFr:'',cpyLevel:-1,yxStatus:0};
  					}
  				}else{//通过查询
  					var provInp = $('#provInp').val() == '请选择省' ? '' : $('#provInp').val(),
  						cityInp = $('#cityInp').val() == '请选择市' ? '' : $('#cityInp').val(),
  						accStatusInp = loginType == 'spUser' ? $('#accStatusInp').val() : 0,
  						levelSelInp = $('#levelSelInp').val(),
  						agencyPyInp = $.trim($('#agencyPyInp').val()),
  						cpyFrInp = loginType == 'spUser' ? escape($.trim($('#cpyFrInp').val())) : '',
  						cpyLxrInp = loginType == 'spUser' ? escape($.trim($('#cpyLxrInp').val())) : '';
  					var field = {cpyName:agencyPyInp,cpyProv:provInp,cpyCity:cityInp,cpyLxr:cpyLxrInp,cpyFr:cpyFrInp,cpyLevel:levelSelInp,yxStatus:accStatusInp};
  				}
  				layer.load('1');
  				table.render({
  					elem : '#cpyListTable',
  					height: 'full-150',
					url : 'cpyManager.do?action=getCpyPageInfo',
					method : 'post',
					where:field,
					page : true,
					even : true,
					limit:10,
					limits:[10,20,30,40],
					cols : [[
						{field : 'id', title: 'ID', width:60, fixed: 'left' , align:'center'},
						{field : 'cpyName', title: '代理机构名称', width:180 , align:'center',fixed: 'left' },
						{field : 'cpyNamePy', title: '拼音简写', width:100 , align:'center'},
						{field : 'cpyFr', title: '法人姓名', width:100 , align:'center'},
						{field : 'hotStatus', title: '公司热度', width:100 , align:'center',style:'color:#f00;',sort:true},
						{field : 'levelNum', title: '会员等级', width:90 , align:'center',templet:function(d){
							if(d.levelNum == 0){
								return '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-tongpai-N"></use></svg><span>铜牌</span>';	
							}else if(d.levelNum == 1){
								return '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-yinpai-N"></use></svg><span>银牌</span>';
							}else if(d.levelNum == 2){
								return '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-jinpai-N"></use></svg><span>金牌</span>';
							}else if(d.levelNum == 3){
								return '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-zuanshi"></use></svg><span>钻石</span>';
							}  							 
						}},
						{field : 'endDate', title: '会员到期时间', width:120 , align:'center',style:'color:#01AAED;'},
						{field : 'cpyYyzz', title: '组织机构代码', width:120 , align:'center'},
						{field : 'cpyLxr', title: '联系人', width:80 , align:'center'},
						{field : 'lxrTel', title: '联系人电话', width:120 , align:'center'},
						{field : 'lxrEmail', title: '联系人邮箱', width:160 , align:'center'},
						{field : 'cpyUrl', title: '网址', width:180 , align:'center'},
						{field : 'signDate', title: '注册日期', width:110 , align:'center'},
						{field : '', title: '操作', width:180 , fixed: 'right', align:'center',templet : function(d){
							if(loginType == 'spUser'){
								return '<input type="hidden" id="signDateInp_'+ d.id +'" value="'+ d.signDate +'"/><input type="hidden" id="endDateInpPar_'+ d.id +'" value="'+ d.endDate +'"/><input type="hidden" id="levelNumInp_'+ d.id +'" value="'+ d.levelNum +'"/><a class="layui-btn layui-btn-primary layui-btn-xs viewDetailInfoBtn" lay-event="viewInfo" cpyId="'+ d.id +'" cpyName="'+ d.cpyName +'"><i class="layui-icon layui-icon-search"></i>查看</a> <a id="update_'+ d.id +'" class="layui-btn layui-btn-xs editInfoBtns" lay-event="updateInfo" cpyId="'+ d.id +'" cpyName="'+ d.cpyName +'"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
							}else if(loginType == 'appUser'){
								return '<a class="layui-btn layui-btn-primary layui-btn-xs viewDetailInfoBtn" lay-event="viewInfo" cpyId="'+ d.id +'" cpyName="'+ d.cpyName +'"><i class="layui-icon layui-icon-search"></i>查看</a>';
							}
						}},
					]],
					done : function(res,curr,count){
						layer.closeAll("loading");
						if(res.msg == "success"){
							$(".noData").hide().html("");
			        		$(".layui-table-view").show();
			        	}else if(res.msg == "noInfo"){
			        		$(".layui-table-view").hide();
			        		$(".noData").show();
			        		if(opts == "loadListData"){
			        			$(".noData").html("<i class='iconfont layui-extend-noData'></i><p>暂无代理机构</p>");
			        		}else{
			        			$(".noData").html("<i class='iconfont layui-extend-noData'></i><p>暂无查询记录</p>");
			        		}
						}
					}
  				});
			}
			//工具方法绑定
  			table.on('tool(cpyListTable)',function(obj){
  				if(obj.event === 'viewInfo'){//查看代理机构信息
  					var cpyId = $(this).attr('cpyId'),
  						cpyName = $(this).attr('cpyName');
  					sonUseObj.globalCpyId = cpyId;
  					var fullScreenIndex = layer.open({
						title:'代理机构--' + cpyName + '--的基本信息',
						type: 2,
					  	area: ['700px', '450px'],
					  	fixed: false, //不固定
					  	shadeClose :true,
					  	content: '/Module/cpyManager/jsp/viewCpyDetailInfo.html'
					});
  					layer.full(fullScreenIndex);
  					
  				}else if(obj.event === 'updateInfo'){//编辑代理机构会员等级 到期时间
  					var cpyName = $(this).attr('cpyName');
  					sonUseObj.cpyId = $(this).attr('cpyId'),
  					sonUseObj.cpyLevel = $('#levelNumInp_' + sonUseObj.cpyId).val(),
  					sonUseObj.cpyEndDate = $('#endDateInpPar_' + sonUseObj.cpyId).val(),
  					signDate = $('#signDateInp_'+sonUseObj.cpyId).val();
  					sonUseObj.globalIndex = layer.open({
						title:'修改' + cpyName + '的会员等级和到期时间',
						type: 2,
						area : ['500px','450px'],
					  	fixed: false, //不固定
					  	skin:'layui-layer-molv', //样式类名
					  	btn : ['保存','取消'],
        			  	btnAlign:'c',
					  	content: '/Module/cpyManager/jsp/editCpyInfo.html',
					  	yes : function(index, layero){
					  		var body = layer.getChildFrame('body', index),
					  		hasEditCpyId = body.find('#cpyIdInp').val();
        					hadEditCpyLevel = body.find('#levelInp').val();
        					hasEditCpyDate = body.find('#endDateInp').val();
        					if(hasEditCpyDate < signDate){
        						layer.msg('会员有效期不能小于注册日期', {icon:5,anim:6,time:1000});
        						return;
        					}
					  		layer.load('1');
							$.ajax({
			   					type:"post",
			   			        async:false,
			   			        dataType:"json",
			   			        data:{cpyId:hasEditCpyId,cpyLevel:hadEditCpyLevel,enDate:hasEditCpyDate},
			   			        url:'cpyManager.do?action=updateCpyInfo',
			   			        success:function (json){
			   			        	layer.closeAll('loading');
			   			        	if(json['result'] == 'success'){
			   			        		layer.msg('保存成功',{icon:1,time:1000},function(){
			   			        			var htmlTemplete = renderLevelShow(hadEditCpyLevel);
    				        				$('#endDateInpPar_'+ sonUseObj.cpyId).val(hasEditCpyDate);
    				        				$('#levelNumInp_' + sonUseObj.cpyId).val(hadEditCpyLevel);
    				        				obj.update({
    				        					levelNum : htmlTemplete,
    				        					endDate : hasEditCpyDate
		    			        			});
    				        				
	    				        			layer.close(sonUseObj.globalIndex);
	    				        			sonUseObj.globalIndex = 0;
	    				        		});
			   			        	}else if(json['result'] == 'noInfo'){
			   			        		layer.msg('暂无此代理机构', {icon:5,anim:6,time:1000});
			   			        	}else if(json['reslut'] == 'error'){
			   			        		layer.msg('系统错误，请稍后再试', {icon:5,anim:6,time:1000});
			   			        	}
			   			        }
			   				});
					  	}
					});
  				}
  			});
			//监听会员有效状态
			form.on('select(accStatusSel)', function(data){
				var value = data.value;
				value == '' ? $('#accStatusInp').val(-1) : $('#accStatusInp').val(value);
			});
			form.on('select(levelSel)', function(data){
				var value = data.value;
				value == '' ? $('#levelSelInp').val(-1) : $('#levelSelInp').val(value);
			});
			//根据动态改变的levelNum动态显示对的图标
  			function renderLevelShow(levelNum){
  				var templeteHtml = '';
  				if(levelNum == 0){
  					templeteHtml = '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-tongpai-N"></use></svg><span>铜牌</span>';	
				}else if(levelNum == 1){
					templeteHtml = '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-yinpai-N"></use></svg><span>银牌</span>';
				}else if(levelNum == 2){
					templeteHtml = '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-jinpai-N"></use></svg><span>金牌</span>';
				}else if(levelNum == 3){
					templeteHtml = '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-zuanshi"></use></svg><span>钻石</span>';
				} 
  				return templeteHtml;
  			}
  			$(function(){
  				loadQueryCpyList('loadListData');
  			});
  		});
  	</script>
  </body>
</html>
