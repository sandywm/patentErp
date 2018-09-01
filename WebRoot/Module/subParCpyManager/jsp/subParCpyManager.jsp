<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>主/子公司管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="keywords" content="专利管理系统,代理机构主/子公司管理">
	<meta http-equiv="description" content="专利管理系统代理机构主/子公司管理">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/subParCpyManager/css/subParCpyManager.css" rel="stylesheet" type="text/css"/>
 	<link href="/Module/cpyDetailInfo/css/cpyBasicInfo.css" rel="stylesheet" type="text/css"/>
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
  						<span>代理机构主/子公司管理</span>
  						<a id="addSubCpyBtn" class="posAbs newAddBtn" href="javascript:void(0)"><i class="layui-icon layui-icon-add-circle"></i>添加子公司</a>
  					</div>
  					<div class="layui-card-body" pad15>
  						<div id="subParList"></div>
	  				</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
  	<script type="text/javascript">
  	var addFlag = "${ requestScope.addFlag }",loadFlag = false;	
		layui.use(['layer','jquery','element'],function(){
			var layer = layui.layer,
				$ = layui.jquery,
				element = layui.element;
			var page = {
				init : function(){
					this.onLoad();
					this.bindEvent();
				},
				onLoad : function(){
					this.initLoad();
				},
				initLoad : function(){
					var _this = this;
					layer.load('1');
					$.ajax({
	   					type:'post',
	   			        async:false,
	   			        dataType:'json',
	   			        url:'cpyManager.do?action=getSubParCpyData',
	   			        success:function (json){
	   			        	layer.closeAll('loading');
	   			        	_this.renderData(json);
	   			        }
	   				});
				},
				bindEvent : function(){
					var _this = this;
					$('#addSubCpyBtn').on('click',function(){
						if(addFlag == 'true'){
							layer.open({
								title:'添加子公司',
								type: 2,
							  	area: ['700px', '500px'],
							  	fixed: false, //不固定
							  	maxmin: true,
							  	shadeClose :false,
							  	content: '/Module/subParCpyManager/jsp/addSubCpy.html',
							  	end:function(){
							  		if(loadFlag){
							  			_this.initLoad();
							  		}
							  	}
							});
						}else{
							layer.msg('抱歉，您暂无权限添加子公司',{icon:5,anim:6,time:1000});
						}
					});
					$('.showDetailSpan').on('click',function(){
						var cpySubId = $(this).attr('cpySubId'),
							cpySubName = $(this).attr('cpySubName');
						layer.load('1');
						$.ajax({
		   					type:'post',
		   			        async:false,
		   			        dataType:'json',
		   			        data:{cpySubId:cpySubId},
		   			        url:'cpyManager.do?action=getSubCpyDetail',
		   			        success:function (json){
		   			        	layer.closeAll('loading');		   			        	
		   			        	var list = json;
		   			        	var strHtml = '<div id="basicInfoDiv" style="margin-top:20px;"></div>';
		   			        	var fullScreenIndex = layer.open({
									title :'子公司--' + cpySubName + '--的基本信息',
									type : 1, 
									content:strHtml, 
									area: ['700px', '450px'],
									shadeClose :true,
									btn : ['确定'],
									success : function(){
										_this.renderParCpyBasicInfo(list,'clickload');
									},
									yes: function(index, layero){
										layer.close(index);
									}
								});
		   	  					layer.full(fullScreenIndex);
		   			        }
		   				});
					});
				},
				renderData : function(json){
					if(json['result'] == 'existInfo'){//表示存在数据
						var type = 'sub';
		        		if(json['psInfo'] == 'sub'){//表示存在子公司
		        			//按钮只有在主公司、子公司都没数据，或者在主公司没数据时候才能存在
		        			$('#addSubCpyBtn').show();
		        			type = 'sub';
		        		}else if(json['psInfo'] == 'par'){//表示存在主公司
		        			//我的主公司存在数据表示当前账号没有增加子公司的权限了
		        			$('#addSubCpyBtn').hide();
		        			type = 'par';
		        		}
		        		var cpyInfo = json.cpyInfo;
						this.renderHtml(cpyInfo,type);
		        	}else if(json['result'] == 'noInfo'){//表示没有数据
		        		$('#addSubCpyBtn').show();
		        		$('#subParList').html('<div class="noDataList"><i class="iconfont layui-extend-noData"></i><p>暂无主/子公司，如有需要，请添加子公司</p></div>');
		        	}
				},
				//根据不同情况渲染不同的tab
				renderHtml : function(cpyInfo,type){
					var strHtml = '';
					strHtml += '<div id="subParCpyWrap" class="layui-tab layui-tab-brief">';
					//头部导航
					strHtml += '<ul class="layui-tab-title">';
					if(type == 'sub'){
						strHtml += '<li id="mySubCpyNav" class="layui-this">我的子公司</li>';
					}else if(type == 'par'){
						strHtml += '<li id="myParCpyNav" class="layui-this">我的主公司</li>';
					}
					strHtml += '</ul>';
					//对应内容
					strHtml += '<div class="layui-tab-content">';
					if(type == 'sub'){
						strHtml += '<div id="mySubCpyCon" class="layui-tab-item layui-show clearfix"></div>';
					}else if(type == 'par'){
						strHtml += '<div id="myParCpyCon" class="layui-tab-item layui-show"><div id="basicInfoDiv" style="width:100%;"></div></div>';
					}
					strHtml += '</div></div>';
					$('#subParList').html(strHtml);
					if(type == 'sub'){
						this.renderSubCpyBasicInfo(cpyInfo);
					}else if(type == 'par'){
						this.renderParCpyBasicInfo(cpyInfo,'initLoad');
					}
				},
				//渲染子公司列表数据
				renderSubCpyBasicInfo : function(cpyInfo){
					var strHtml = '';
					for(var i=0;i<cpyInfo.length;i++){
						strHtml += '<div class="subCpyList">';
						//头部
						strHtml += '<div class="subCpyName">';
						strHtml += '<p class="titP ellip">'+ cpyInfo[i].cpyName +'</p>';
						strHtml += '<div class="smInfo clearfix">';
						strHtml += '<p>公司法人：'+ cpyInfo[i].cpyFr +'</p><p>会员等级：'+ cpyInfo[i].cpyLevel +'</p><p>热度：'+ cpyInfo[i].hotStatus +'</p>';
						strHtml += '</div></div>';
						//底部内容
						strHtml += '<div class="subCpyDetailInfo">';
						strHtml += '<p>公司联系人：'+ cpyInfo[i].cpyLxr +'</p>';
						strHtml += '<p>联系人电话：'+ cpyInfo[i].lxrTel +'</p>';
						strHtml += '<p>联系人邮箱：'+ cpyInfo[i].lxrEmail +'</p>';
						strHtml += '<p>注册日期：'+ cpyInfo[i].signDate +'</p>';
						strHtml += '<p>会员有效期：'+ cpyInfo[i].endDate +'</p>';
						strHtml += '<p class="ellip">公司组织机构代码：'+ cpyInfo[i].cpyYyzz +'</p>';
						strHtml += '</div>';
						//查看详情
						strHtml += '<div class="subCpyLayer">';
						strHtml += '<span class="showDetailSpan" cpySubName="'+ cpyInfo[i].cpyName +'" cpySubId="'+ cpyInfo[i].cpyId +'" title="查看详情"><i class="layui-icon layui-icon-search"></i></span>';
						strHtml += '</div>';
						strHtml += '</div>';
					}
					$('#mySubCpyCon').html(strHtml);
				},
				//渲染主公司列表数据
				renderParCpyBasicInfo : function(list,opts){
					var strHtml = '';
	   				var	jiangpaiHtml = '';
	   				opts == 'initLoad' ? list = list[0] : list = list;
	   				strHtml += '<ul>';
	   				strHtml += '<li class="cpy_Name">'+ list.cpyName +'</li>';
	   				strHtml += '<li><div class="innerPar"><p>公司法人</p><p>'+ list.cpyFr +'</p></div>';
	   				strHtml += '<div class="innerPar"><p><span>公司热度</span><i class="iconfont layui-extend-wenhao" onclick="showHotStatusNote()"></i></p><p>'+ list.hotStatus +'</p></div></li>';
	   				strHtml += '<li><div class="innerPar"><p>账号注册时间</p><p>'+ list.signDate +'</p></div>';
	   				if(list.cpyLevel == '铜牌'){
						jiangpaiHtml += '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-tongpai-N"></use></svg>';
					}else if(list.cpyLevel == '银牌'){
						jiangpaiHtml += '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-yinpai-N"></use></svg>';
					}else if(list.cpyLevel == '金牌'){
						jiangpaiHtml += '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-jinpai-N"></use></svg>';
					}else if(list.cpyLevel == '钻石'){
						jiangpaiHtml += '<svg class="icon-svg specSvg" aria-hidden="true"><use xlink:href="#icon-svg-zuanshi"></use></svg>';
					}
	   				strHtml += '<div class="innerPar"><p>会员等级</p><p>'+ jiangpaiHtml +' '+ list.cpyLevel +'</p></div></li>';
	   				strHtml += '<li><div class="innerPar"><p>公司组织机构代码</p><p>'+ list.cpyYyzz +'</p></div>';
	   				if(list.cpyLevel != '铜牌'){
	   					if(list.endFlag){
	   						strHtml += '<div class="innerPar"><p>会员到期时间</p><p>'+ list.endDate +' (<span class="endDateColor">会员已到期</span>)</p></div></li>';
	   					}else{
	   						strHtml += '<div class="innerPar"><p>会员到期时间</p><p>'+ list.endDate +'</p></div></li>';
	   					}
	   				}else{
	   					strHtml += '<div class="innerPar"><p>会员到期时间</p><p>永久有效</p></div></li>';
	   				}
					strHtml += '<li><div class="innerPar"><p>公司联系人</p><p>'+ list.cpyLxr +'</p></div>';
	   				strHtml += '<div class="innerPar"><p>联系人电话</p><p>'+ list.lxrTel +'</p></div></li>';
	   				strHtml += '<li><div class="innerPar"><p>联系人邮箱</p><p>'+ list.lxrEmail +'</p></div>';
	   				strHtml += '<div class="innerPar"><p>所在地区</p><p>'+ list.cpyProv +' '+ list.cpyCity +'</p></div></li>';
	   				strHtml += '<li><div class="innerPar"><p>联系地址</p><p>'+ list.cpyAddress +'</p></div>';
	   				strHtml += '<div class="innerPar"><p>公司网址</p><p>'+ list.cpyUrl +'</p></div></li>';
	   				strHtml += '<li class="specHeiLi"><div class="innerPar" style="width:100%;"><p>公司简介</p><p>'+ list.cpyProfile +'</p></div></li>';
	   				strHtml += '</ul>';
	   				$('#basicInfoDiv').html(strHtml);
				}
			};
			$(function(){
				page.init();
			});
		});
		function showHotStatusNote(){
			layer.tips('公司热度意思是公司完成的专利数，公司每次为客户撰写一个专利，热度加0.1，依次可作为专利公司热度搜索排名', '.layui-extend-wenhao', {
			  tips: [2, '#3595CC'],
			  time: 4000
			});	
		}
  	</script>
	
  </body>
</html>