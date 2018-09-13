<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利要求管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="keywords" content="专利管理系统,专利要求管理">
	<meta http-equiv="description" content="专利管理系统专利要求管理模块">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/zlYqManager/css/zlYqManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
  </head>
  
  <body>
  	<div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div class="layui-card-header posRel">
  						<span>专利额外要求管理</span>
  						<a id="addYqBtn" class="posAbs newAddBtn" opts="addZlYqOpts" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加专利额外要求</a>
  					</div>
  					<div class="layui-card-body layui-form" pad15>
  						<div id="yqList">
  							<ul class="zlyqTitUl clearfix">
  								<li class="zlyqWid1">专利额外要求类型</li>
  								<li class="zlyqWid2">专利额外要求内容</li>
  								<li class="zlyqWid3">操作</li>
  							</ul>
  							<ul class="zlyqConUl clearfix"></ul>
  						</div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
    <script type="text/javascript">
    	var addEditZlYqFlag = false,addEditZlYqOpts='',globalZlYqId=0;
		layui.use(['layer','jquery'],function(){
    		var layer = layui.layer,
    			$ = layui.jquery;
    		var page = {
    			init : function(){
    				this.onLoad();
    				this.bindEvent();
    			},
    			onLoad : function(){
    				this.loadZlYqListData();
    			},
    			loadZlYqListData : function(){
    				var _this = this;
    				layer.load('1');
    				$.ajax({
	   					type:'post',
	   			        async:false,
	   			        dataType:'json',
	   			        data:{yqType : ''},
	   			        url:'/zlyq.do?action=getZlyqData',
	   			        success:function (json){
	   			        	layer.closeAll('loading');
	   			        	if(json['result'] == 'success'){
	   			        		var yqInfo = json.yqInfo;
	   			        		_this.renderYq(yqInfo);
	   			        		_this.bindEvent_Edit();
	   			        	}else if(json['result'] == 'noAbility'){
	   			        		layer.msg('抱歉，您暂无权限获取专利额外要求列表', {icon:5,anim:6,time:1000});
	   			        	}else if(json['result'] == 'noInfo'){
	   			        		$('.zlyqConUl').html("<div class='noData' style='display:block;'><i class='iconfont layui-extend-noData'></i><p>暂专利额外要求记录，请添加</p></div>");
	   			        	}
	   			        }
	   				});
    			},
    			bindEvent : function(){
    				//添加专利额外要求
    				var _this = this;
    				$('#addYqBtn').on('click',function(){
    					var addZlYqOpts = $(this).attr('opts');
    					addEditZlYqFlag = false;
    					addEditZlYqOpts = addZlYqOpts;
    					layer.open({
							title:'增加专利额外要求',
							type: 2,
						  	area: ['500px', '220px'],
						  	fixed: false, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/zlYqManager/jsp/addEditZlYq.html',
						  	end:function(){
						  		if(addEditZlYqFlag){
						  			_this.loadZlYqListData();
						  		}
						  	}
						});	
    				});
    			},
    			bindEvent_Edit : function(){
    				//编辑专利额外要求
    				var _this = this;
    				$('.editZlYqSp').on('click',function(){
    					var editZlYqOpts = $(this).attr('opts'),zlYqId = $(this).attr('yqId');
    					addEditZlYqFlag = false;
    					addEditZlYqOpts = editZlYqOpts;
    					globalZlYqId = zlYqId;
    					layer.open({
							title:'编辑专利额外要求',
							type: 2,
						  	area: ['500px', '220px'],
						  	fixed: false, //不固定
						  	maxmin: false,
						  	shadeClose :false,
						  	content: '/Module/zlYqManager/jsp/addEditZlYq.html',
						  	end:function(){
						  		if(addEditZlYqFlag){
						  			_this.loadZlYqListData();
						  		}
						  	}
						});	
    				});
    			},
    			renderYq : function(yqInfo){
    				var strHtml = '';
    				for(var i=0;i<yqInfo.length;i++){
    					strHtml += '<li>';
    					strHtml += '<div class="editLayer"></div>';
    					strHtml += '<p class="zlyqWid1">'+ yqInfo[i].yqTypeChi +'</p>';
    					strHtml += '<p class="zlyqWid2">'+ yqInfo[i].yqContent +'</p>';
    					strHtml += '<p class="zlyqWid3"><span class="editZlYqSp" opts="editZlYqOpts" yqId="'+ yqInfo[i].id +'" title="编辑"><i class="layui-icon layui-icon-edit"></i>编辑</span></p>';
    					strHtml += '</li>';
    				}
    				$('.zlyqConUl').html(strHtml);
    				$('.zlyqConUl li:odd').addClass('oddBgColor');
    			}
    		};
    		$(function(){
    			page.init();
    		});
    	});
    </script>
  </body>
</html>
