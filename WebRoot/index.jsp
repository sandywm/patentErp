<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
  <head>
    <title>首页--专利管理系统</title>
    <meta http-equiv="keywords" content="申请专利,首页"/>
	<meta http-equiv="description" content="专利申请系统首页"/>
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/css/diyReset.css" rel="stylesheet" type="text/css"/>
	<link href="/css/index.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js"></script>
	<script type="text/javascript">
		var roleName = "${sessionScope.login_user_role_name}",
			loginType = "${sessionScope.login_type}";
	</script>
  </head>
  
  <body class="layui-layout-body">
  	<div class="layui-layout layui-layout-admin">
  		<div class="layui-header">
  			<div class="layui-logo">专利管理系统  <i id="animation-left-nav" class="layui-icon layui-icon-shrink-right"></i></div>
  			<ul class="layui-nav layui-layout-right">
  				<li class="weather">
  					<div id="tp-weather-widget"></div>
	  					<script>(function(T,h,i,n,k,P,a,g,e){g=function(){P=h.createElement(i);a=h.getElementsByTagName(i)[0];P.src=k;P.charset="utf-8";P.async=1;a.parentNode.insertBefore(P,a)};T["ThinkPageWeatherWidgetObject"]=n;T[n]||(T[n]=function(){(T[n].q=T[n].q||[]).push(arguments)});T[n].l=+new Date();if(T.attachEvent){T.attachEvent("onload",g)}else{T.addEventListener("load",g,false)}}(window,document,"script","tpwidget","//widget.seniverse.com/widget/chameleon.js"))</script>
						<script>tpwidget("init", {
					        "flavor": "slim",
					        "location": "WX4FBXXFKE4F",
					        "geolocation": "enabled",
					        "language": "zh-chs",
					        "unit": "c",
					        "theme": "chameleon",
					        "container": "tp-weather-widget",
					        "bubble": "enabled",
					        "alarmType": "badge",
					        "color": "#F47837",
					        "uid": "UC6AD9E048",
					        "hash": "76465b415261736ddd08da3f7f9b24d0"
					    });
					    tpwidget("show");</script>
  				</li>
  				<li id="mailNavLi" class="layui-nav-item topHeadNav navLi">
  			 		<a href="javascript:void(0);" path="mail.do?action=goMailPage" tab-id="18">
  			 			<span style="display:none;">邮件管理</span>
	            		<i class="iconfont layui-extend-youxiang headsmIcon"></i>
	            		<span id="hasNoReadNum" class="layui-badge-dot emailDot"></span>
	            	</a>
  			 	</li>
	            <li class="layui-nav-item">
	                <a href="javascript:;">
	                	<i class="layui-icon layui-icon-username headsmIcon"></i>
	                   	黄利峰<span class="layui-nav-more"></span>
	                </a>
	                <dl class="layui-nav-child">
	                    <dd class="navLi"><a href="javascript:void(0)" tab-id="1" path="user.do?action=goUserDetailPage">基本资料</a></dd>
	                    <dd class="navLi"><a href="javascript:void(0)" tab-id="2" path="user.do?action=goUpdatePassPage">密码设置</a></dd>
	                </dl>
	            </li>
	            <li class="tuichu">
	            	 <a id="loginOut" href="javascript:;" title="退出">
	            	 	<i class="iconfont layui-extend-tuichu"></i>
	            	 </a>
	            </li>
	    	</ul>
  		</div>
  		<div class="layui-side layui-bg-black layui-side-menu">
  			<div class="layui-side-scroll">
  				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
  				<ul id="leftSideNav" class="layui-nav layui-nav-tree" lay-fliter="leftSideNav"></ul>
  			</div>
  		</div>
  		<!-- iframe -->
  		<div class="content-body">
  			<!-- 内容主体区域 -->
  			<div class="layui-tab layui-tab-brief" lay-filter="mainTab" lay-allowClose="true">
	            <ul class="layui-tab-title">
	                <li class="layui-this"><i class="layui-icon">&#xe68e;</i>首页</li>
	            </ul>
	            <div class="layui-tab-content">
	                <div class="layui-tab-item layui-show">
	                	<!--  iframe id="mainIframe" src="cpyManager.do?action=goCpyDetailPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                    <!-- iframe id="mainIframe" src="user.do?action=goWelcomePage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                    <!--  iframe id="mainIframe" src="jfm.do?action=goJfPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                    <!-- iframe id="mainIframe" src="user.do?action=goUserPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                    <!-- iframe id="mainIframe" src="user.do?action=goUserDetailPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                    <!--  iframe id="mainIframe" src="modM.do?action=goModulePage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                    <!-- iframe id="mainIframe" src="cpyManager.do?action=goCpyPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe -->
	                	<!--  iframe id="mainIframe" src="cpyManager.do?action=goSubParCpyPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                	<!--  iframe id="mainIframe" src="mail.do?action=goMailPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                	<!--  iframe id="mainIframe" src="pubZl.do?action=goPubZlPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe-->
	                	<iframe id="mainIframe" src="customer.do?action=goCusPage" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe>
	                </div>
	            </div>
	        </div>
  		</div>
  		<!-- footer -->
  		<!--  div class="layui-footer">
	        © 2018 版权所有 Copyright@2018 Sandy.wm All Rights Reserved.
	    </div-->
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
  	<script type="text/javascript">
  		//console.log(roleName + "--roleName" + loginType)
	  	layui.use(['element','layer'], function(){
	  		 //动态加载模块
	        var element = layui.element,
	        	layer = layui.layer,
	        	$ = layui.jquery,
	        	i = 0;
	        function renderModuleList(){
	        	var liItem = '';
	        	if(loginType == 'cpyUser'){//代理机构管理员
	        		if(roleName == '管理员'){
	        			liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" path="cpyManager.do?action=goCpyDetailPage" tab-id="3"><i class="iconfont layui-extend-xiugai"></i><cite>代理机构信息修改</cite></a></li>';
		        		liItem += '<li class="layui-nav-item"><a href="javascript:void(0)"><i class="iconfont layui-extend-guanli"></i>代理机构管理</a>';
		        		liItem += '<dl class="layui-nav-child"><dd class="navLi"><a href="javascript:void(0)" path="user.do?action=goUserPage" tab-id="4">代理机构员工管理</a></dd>';
		        		liItem += '<dd class="navLi"><a href="javascript:void(0)" path="role.do?action=goRolePage" tab-id="5">代理机构角色管理</a></dd>';
		        		liItem += '<dd class="navLi"><a href="javascript:void(0)" path="jfm.do?action=goJfPage" tab-id="6">技术领域管理</a></dd>';
		        		liItem += '<dd class="navLi"><a href="javascript:void(0)" path="modM.do?action=goModulePage" tab-id="7">代理机构角色权限管理</a></dd>';
		        		liItem += '<dd class="navLi"><a href="javascript:void(0)" path="cpyManager.do?action=goSubParCpyPage" tab-id="8">代理机构主/子公司</a></dd></dl></li>';
		        		liItem += '<li class="layui-nav-item"><a href="javascript:void(0)"><i class="layui-icon layui-icon-form" style="font-size:16px;"></i>专利任务管理</a>';
		        		liItem += '<dl class="layui-nav-child"><dd class="navLi"><a href="javascript:void(0)" path="pubZl.do?action=goPubZlPage" tab-id="10">专利任务领取</a></dd>';
		        		liItem += '<dd class="navLi"><a href="javascript:void(0)" path="pubZl.do?action=goLqZlPage" tab-id="11">专利任务领取记录</a></dd></dl></li>';
			        	liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" tab-id="9"><i class="iconfont layui-extend-goumai"></i>会员续费/购买</a></li>';
	        		}else{
	        			liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" path="cpyManager.do?action=goCpyDetailPage" tab-id="3"></i><cite>我的代理机构</cite></a></li>';
	        			//代理机构下除管理员外其他身份登录动态加载对应模块
	        			$.ajax({
	       					type:"post",
	       			        async:false,
	       			        dataType:"json",
	       			        url:"modM.do?action=getSelfModule",
	       			        success:function (json){
	       			        	var modInfo = json.modInfo;
	       			        	if(modInfo != undefined){
									for(var i=0;i<modInfo.length;i++){
										if(modInfo[i].useFlag){
											liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" path="'+ modInfo[i].modUrl +'" tab-id="'+ modInfo[i].modId +'_'+ i +'"><cite>'+ modInfo[i].modName +'</cite></a></li>';
										}else{
											liItem += '<li class="layui-nav-item outDateLi"><a href="javascript:void(0)"><cite>'+ modInfo[i].modName +'</cite></a></li>';
										}
									}	
	       			        	}
	       			        }
	       				});
	        		}
	        	}else if(roleName == '申请人/公司' && loginType == 'appUser'){//申请人/公司
	        		liItem += '<li class="layui-nav-item"><a href="javascript:void(0)">任务</a>';
	        		liItem += '<dl class="layui-nav-child"><dd class="navLi"><a href="javascript:void(0)" path="pubZl.do?action=goPubZlPage" tab-id="3">专利任务发布</a></dd>';
	        		liItem += '<dd class="navLi"><a href="javascript:void(0)" tab-id="4">查看任务进度</a></dd></dl></li>';
	        		liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" path="cpyManager.do?action=goCpyPage" tab-id="5">查看代理机构</a></li>';
	        	}else{ //平台用户（超管 财务、总经理）
	        		liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" path="modM.do?action=goModulePage" tab-id="3"><i class="iconfont layui-extend-mokuai"></i>模块权限管理</a></li>';
	        		liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" path="cpyManager.do?action=goCpyPage" tab-id="4"><i class="iconfont layui-extend-jigou"></i>代理机构管理</a></li>'; 		
	        		liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" path="pubZl.do?action=goPubZlPage" tab-id="7"><i class="layui-icon layui-icon-search"></i>查看专利任务</a></li>';
	        		liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" tab-id="5"><i class="iconfont layui-extend-chakan"></i>查看专利申请(人/公司)</a></li>';
	        		liItem += '<li class="layui-nav-item navLi"><a href="javascript:void(0)" tab-id="6"><i class="iconfont layui-extend-feiyong"></i>费用列表</a></li>';
	        	}
	        	
	        	$("#leftSideNav").html(liItem);
	        	element.init(); 
	        }
	        function getNoReadStatusNum(){
				$.ajax({
					type:'post',
			        async:false,
			        dataType:'json',
			        url:'mail.do?action=getNoReadInfo',
			        success:function (json){
			        	if(json['result'] > 0){
			        		$('#hasNoReadNum').show();
			        	}else{
			        		$('#hasNoReadNum').hide();
			        	}
			        }
				});
			}
	        $(function(){
	        	renderModuleList();
	        	if(loginType == 'appUser' || loginType == 'cpyUser'){
	        		$('#mailNavLi').css('visibility','visible');
	        		getNoReadStatusNum();
	        	}else{
	        		$('#mailNavLi').remove();
	        	}
	        });
	        
	        $(".navLi").click(function () {
		        var title = $(this).text();
		        var path = $(this).children('a').attr('path');
		        var tabId = $(this).children('a').attr('tab-id');
		        // 去重复选项卡
		        for (var i = 0; i < $('.mainIframe').length; i++) {
		            if ($('.mainIframe').eq(i).attr('tab-id') == tabId) {
		                element.tabChange("mainTab", tabId);
		                event.stopPropagation();
		                return;
		            }
		        }
		        // 添加选项卡
		        element.tabAdd("mainTab", {
		            title: title,
		            content: "<iframe src='" + path + "' tab-id='" + tabId + "' class='mainIframe' frameborder='0' scrolling='yes' width='100%' height='100%'></iframe>",
		            id: tabId
		        });
		        // 切换选项卡
		        element.tabChange("mainTab", tabId);
		    });
	        
	        $("#animation-left-nav").click(function(){
				//这里定义一个全局变量来方便判断动画收缩的效果,也就是放在最外面
				if(i == 0){
					$(this).removeClass("layui-icon-shrink-right").addClass("layui-icon-spread-left");
					$(".layui-side").stop().animate({width:'toggle'});
					$(".content-body").stop().animate({left:'0px'});			
					//$(".layui-footer").animate({left:'0px'});
					i++;
				}else{
					$(this).removeClass("layui-icon-spread-left").addClass("layui-icon-shrink-right");
					$(".layui-side").stop().animate({width:'toggle'});
					$(".content-body").stop().animate({left:'200px'});
					//$(".layui-footer").animate({left:'200px'});
					i--;
				}		
			});
	        $('.outDateLi').on('click',function(){
	        	layer.confirm('您所属代理机构的会员已到期，之前的高级功能将不能使用，如需使用，请联系代理机构及时续费购买！',{
					title:'所属代理机构会员到期提醒',
				  	skin: 'layui-layer-molv',
				  	btn: ['确定','取消'] //按钮
				},function(index){
					layer.close(index);
				});	
	        })
			//左侧导航栏收缩提示
			$('#animation-left-nav').hover(function(){
				if(i==0){
					layer.tips('收缩左侧导航栏', '#animation-left-nav', {tips:[2,'#FF8000'],time:0});
				}else{
					layer.tips('展开左侧导航栏', '#animation-left-nav', {tips:[2,'#FF8000'],time:0});
				}
				
			},function(){
				layer.closeAll('tips'); 
			});
			
			//登出
			$("#loginOut").on('click',function(){
				layer.confirm('确认退出系统么？', {
				  title:'提示',
				  skin: 'layui-layer-molv',
				  btn: ['确定','取消'] //按钮
				},function(){
					if(roleName == 'super'){
						window.location.href = "login.do?action=spLoginPage";
					}else{
						window.location.href = "login.do?action=loginOut";
					}
				});
			});
	    });
  	</script>
  </body>
</html>
