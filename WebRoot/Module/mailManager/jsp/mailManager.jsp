<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>邮件管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="keywords" content="专利管理系统,邮件管理">
	<meta http-equiv="description" content="专利管理系统邮件管理模块">
	<link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/Module/mailManager/css/mailManager.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js" type="text/javascript"></script>
	<script src="http://at.alicdn.com/t/font_814267_mdxupmf70j.js"></script>
  </head>
  <body>
  	<body style="background:#f2f2f2;color:#666;">
  	 <div class="layui-fluid" style="margin-top:15px;">
  		<div class="layui-row">
  			<div class="layui-col-md12 layui-col-lg12">
  				<div class="layui-card">
  					<div id="cpyTxtTit" class="layui-card-header">邮件管理</div>
  					<div class="layui-card-body" pad15>
  						<div class="layui-tab layui-tab-brief posRel" lay-filter="mailTab">
  						  <!-- 查询 -->
						  <div class="queryWrap layui-form posAbs">
						  	<input type="hidden" id="mailTypeInp" value="taskM"/>
	  						<div class="itemDivSel fl">
	  							<div class="layui-input-inline">
	  								<input type="hidden" id="readStatusInp" value="-1"/>
	  								<select id="readStatussSel" lay-filter="readStatussSel">
	  									<option value="">请选择阅读状态</option>
	  								 	<option value="0">未读</option>
	  								 	<option value="1">已读</option>
	  								</select> 
	  							</div>
	  						</div>
	  						<div class="itemDivInp fl">
	  							<div class="layui-input-inline">
	  								<input type="text" id="emailTitInp" placeholder="请输入邮件标题" value="" autocomplete="off" class="layui-input">
	  							</div>
	  						</div>
	  						<div class="fl">
	  							<div class="layui-input-inline">
	  								<button id="queryBtn" class="layui-btn"><i class="layui-icon layui-icon-search
 "></i></button>
	  							</div>
	  						</div>
						  </div>
						  <ul id="tabNav" class="layui-tab-title"></ul>
						  <div id="hasFunWrap" class="clearfix">
						  	<input id="mailIdInp" type="hidden"/>
					  		<div class="sellAll">
					  			<span class="likeCheckSpan"><b class="layui-icon layui-icon-ok" style="margin-left:2px;"></b></span>
					  			<input id="selectAllInp" type="checkbox" class="inpRadCheck" title="全选">
					  		</div>
					  		<a id="hasReadBtn" class="noSelA" canClickFlag="false" href="javascript:void(0)">标记为已读</a>
					  		<a id="delBtn" class="noSelA" canClickFlag="false" href="javascript:void(0)">删除</a>
						  </div>
						  <!-- listTitle -->
						  <div class="listTitle">
						  	<ul class="clearfix">
						  		<li class="checkLiWid"></li>
						  		<li class="readStatusWid">状态</li>
						  		<li class="sendEmailWid">发件人邮箱</li>
						  		<li class="eamilTitWid">邮件标题</li>
						  		<li class="emaiConWid">邮件内容</li>
						  		<li class="sendTimeWid">发送时间</li>
						  		<li class="eamilSetWid">操作</li>
						  	</ul>
						  </div>
						  <div id="tabContent" class="layui-tab-content"></div>
						</div>   
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<script src="/plugins/layui/layui.js"></script>
  	<script type="text/javascript">
  		var loginType = parent.loginType,currPage = 1,mailIdArray=[];
  		layui.use(['layer','jquery','element','laypage','form'],function(){
  			var layer = layui.layer,
  				$ = layui.jquery,
  				element = layui.element,
  				laypage = layui.laypage,
  				form = layui.form;
  			
  			//tab点击事件的监听
  			element.on('tab(mailTab)', function(data){
  				var tabIndex = data.index;
 			    if(tabIndex == 0){
 			    	page.data.mailType = 'taskM';
 			    	page.data.elem = 'laypageTask';
 			    	$('#mailTypeInp').val('taskM');
 			    }else if(tabIndex == 1){
 			    	page.data.mailType = 'endM';
 			    	page.data.elem = 'laypageEndDate';
 			    	$('#mailTypeInp').val('endM');
 			    }else if(tabIndex == 2){
 			    	page.data.mailType = 'buyM';
 			    	page.data.elem = 'laypageBuyM';
 			    	$('#mailTypeInp').val('buyM');
 			    }
 			    //点击tab导航恢复initLoad
 			    page.data.opts = 'initLoad';
 			    page.loadEmailList(page.data.opts,page.data.mailType);
 			    if($(this).attr('isHasData') == 'true'){//表示存在数据
 			    	page.layPages(page.data.elem,page.data.totalConut,page.data.opts,page.data.mailType);
 			    }
 			 	//每次tab点击要初始化全选
 			    page.checkAllInpInit();
 			});
  			var page = {
  				data : {
  					opts : 'initLoad',
  					mailType : 'taskM',
  					totalConut : 0,
  					elem : 'laypageTask',
  					tmpPageNum_task : 1,
  					tmpPageNum_end : 1,
  					tmpPageNum_buy : 1
  				},
  				init : function(){
  					this.onLoad();
  					this.bindEvent();
  				},	
  				onLoad : function(){
  					this.loadTabNavCon();
  					this.getNoReadStatusNum();
  				},
  				bindEvent : function(){
  					var _this = this;
  					$('#queryBtn').on('click',function(){
  						_this.data.opts = 'queryLoad';
  						_this.loadEmailList('queryLoad','taskM');
  						_this.initLayPage();
  					});
  				},
  				//获取未读邮件条数以及三种类型的未读条数
  				getNoReadStatusNum : function(){
					$.ajax({
						type:'post',
				        async:false,
				        dataType:'json',
				        url:'mail.do?action=getNoReadInfo',
				        success:function (json){
				        	if(json['result_t'] > 0){
				        		$('#hasNoReadNum_task').html('<span class="layui-badge-dot navDot"></span>');
				        	}else{
				        		$('#hasNoReadNum_task').html('');
				        	}
				        	if(json['result_e'] > 0){
				        		$('#hasNoReadNum_end').html('<span class="layui-badge-dot navDot"></span>');
				        	}else{
				        		$('#hasNoReadNum_end').html('');
				        	}
				        	if(json['result_b'] > 0){
				        		$('#hasNoReadNum_buy').html('<span class="layui-badge-dot navDot"></span>');
				        	}else{
				        		$('#hasNoReadNum_buy').html('');
				        	}
				        }
					});
  				},
  				//切换tab导航针对全选 标记已读 删除的初始化
  				checkAllInpInit : function(){
  					$('#mailIdInp').val('');
	        		$('#selectAllInp').attr('checked',false).prev().removeClass('hasActive');
	  			    $('#hasReadBtn').removeClass('hasSelA').addClass('noSelA').attr('canClickFlag','false');
	        		$('#delBtn').removeClass('hasSelA').addClass('noSelA').attr('canClickFlag','false');
  				},
  				//layPage每次加载的初始化
  				initLayPage : function(){
  					if($('#mailTypeInp').val() == 'taskM'){
						if($('#taskNavLi').attr('isHasData') == 'true'){page.layPages(page.data.elem,page.data.totalConut,page.data.opts,page.data.mailType);}
					}else if($('#mailTypeInp').val() == 'endM'){
						if($('#endDateLi').attr('isHasData') == 'true'){page.layPages(page.data.elem,page.data.totalConut,page.data.opts,page.data.mailType);}
					}else if($('#mailTypeInp').val() == 'buyM'){
						if($('#buyMLi').attr('isHasData') == 'true'){page.layPages(page.data.elem,page.data.totalConut,page.data.opts,page.data.mailType);}
					}
  				},
  				//根据loginType动态加载不同的nav
  				loadTabNavCon : function(){
  					var _this = this;
  					var strHtmlNav = '',strHtmlCon = '';
  					if(loginType == 'cpyUser'){//代理机构用户
  						strHtmlNav += '<li id="taskNavLi" class="layui-this" isHasData="true">任务通知<div id="hasNoReadNum_task" class="isHasNoReadNumWrap"></div></li>';
  						strHtmlNav += '<li id="endDateLi" isHasData="true">会员到期通知<div id="hasNoReadNum_end" class="isHasNoReadNumWrap"></div></li>';
  						strHtmlNav += '<li id="buyMLi" isHasData="true">会员购买通知<div id="hasNoReadNum_buy" class="isHasNoReadNumWrap"></div></li>';
  						strHtmlCon += '<div class="layui-tab-item layui-show"><div id="taskTab"></div><div id="laypageTask" class="comLayPage"></div></div>';
  						strHtmlCon += '<div class="layui-tab-item"><div id="endDateTab"></div><div id="laypageEndDate" class="comLayPage"></div></div>';
  						strHtmlCon += '<div class="layui-tab-item"><div id="buyMTab"></div><div id="laypageBuyM" class="comLayPage"></div></div>';
  						
  					}else if(loginType == 'appUser'){//个人、公司用户
  						strHtmlNav += '<li id="taskNavLi" class="layui-this" isHasData="true">任务通知<div id="hasNoReadNum_task" class="isHasNoReadNumWrap"></div></li>';
  						strHtmlCon += '<div class="layui-tab-item layui-show"><div id="taskTab"></div><div id="laypageTask" class="comLayPage"></div></div>';
  					}
  					$('#tabNav').html(strHtmlNav);
  					$('#tabContent').html(strHtmlCon);
  					this.loadEmailList(this.data.opts,this.data.mailType);
  					if($('#taskNavLi').attr('isHasData') == 'true'){//初始化加载代表存在数据
  						this.layPages(this.data.elem,this.data.totalConut,this.data.opts,this.data.mailType);
  					}
  				},
  				//初始化邮件列表
  				loadEmailList : function(opts,mailType){ 
  					layer.load('1');
  					var _this = this,limit = 10,currPage=1,noData='<div class="noData"><i class="iconfont layui-extend-noData"></i><p>暂无邮件记录<p></div>';
  					if(opts == 'initLoad'){
  						var field = {mailType:mailType,mailTitle:'',readStatus:-1};
  					}else if(opts == 'queryLoad'){
  						var mailType = $('#mailTypeInp').val(),
  							mailTitle = $('#emailTitInp').val() == '' ? '' : escape($.trim($('#emailTitInp').val())),
  							readStatus = $('#readStatusInp').val(),
  							field = {mailType:mailType,mailTitle:mailTitle,readStatus:readStatus};
  					}
  					if(mailType == 'taskM'){
  						currPage = page.data.tmpPageNum_task;
  	            	}else if(mailType == 'endM'){
  	            		currPage = page.data.tmpPageNum_end;
  	            	}else if(mailType == 'buyM'){
  	            		currPage = page.data.tmpPageNum_buy;
  	            	}
					$.ajax({
						type:'post',
				        async:false,
				        dataType:'json',
				        data : field,
				        url:'mail.do?action=getMailPageList&pageNo=' + currPage + '&pageSize=' + limit,
				        success:function (json){
				        	layer.closeAll('loading');
				        	if(json['msg'] == 'success'){
				        		var listMailData = json.data;
				        		_this.renderListEmail(opts,listMailData,mailType);
				        		if(mailType == 'taskM'){
				        			$('#taskNavLi').attr('isHasData','true');
				        		}else if(mailType == 'endM'){
				        			$('#endDateLi').attr('isHasData','true');
				        		}else if(mailType == 'buyM'){
				        			$('#buyMLi').attr('isHasData','true');
				        		}
				        	}else if(json['msg'] == 'noInfo'){
				        		if(mailType == 'taskM'){
				        			$('#taskNavLi').attr('isHasData','false');
				        			$('#taskTab').html(noData);
				        			$('#laypageTask').html('');
				        		}else if(mailType == 'endM'){
				        			$('#endDateLi').attr('isHasData','false');
				        			$('#endDateTab').html(noData);
				        			$('#laypageEndDate').html('');
				        		}else if(mailType == 'buyM'){
				        			$('#buyMLi').attr('isHasData','false');
				        			$('#buyMTab').html(noData);
				        			$('#laypageBuyM').html('');
				        		}
				        	}
				        }
					});
  				},
  				//渲染邮件列表
  				renderListEmail : function(opts,listMailData,mailType){
  					var strHtml = '',_this = this;
  					strHtml += '<ul class="emailListUl">';
  					for(var i=0;i<listMailData.length;i++){
  						strHtml += '<li class="clearfix">';
  						//选中checkbox
  						strHtml += '<p class="posRel sellAll"><span class="likeCheckSpan"><b class="layui-icon layui-icon-ok"></b></span><input type="checkbox" name="singleCheckInp_'+ mailType +'" class="inpRadCheck singleCheckInp" value="'+ listMailData[i].id +'"></p>';
  						//读取状态
  						strHtml += '<p id="readStatus_'+ listMailData[i].id +'" class="readStatusWid posRel">';
  						if(listMailData[i].readStatus == '未读'){
  							strHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-weidu"></use></svg><span class="layui-badge-dot emailDot"></span>';
  						}else{
  							strHtml += '<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-yidu"></use></svg>';
  						}
  						strHtml += '</p>';
  						//发件人邮箱
  						strHtml += '<p class="sendEmailWid ellip">'+ listMailData[i].sendInfo +'</p>';
  						//邮件标题
  						strHtml += '<p class="eamilTitWid ellip">'+ listMailData[i].mailTitle +'</p>';
  						//邮件内容
  						strHtml += '<p class="emaiConWid ellip">'+ listMailData[i].mailContent +'</p>';
  						//发送时间
  						strHtml += '<p class="sendTimeWid ellip">'+ listMailData[i].sendTime +'</p>';
  						//操作
  						strHtml += '<p class="eamilSetWid">';
  						strHtml += '<span class="viewEmailDetail" mailId="'+ listMailData[i].id +'">查看详情</span>';
  						strHtml += '</p>';
  						strHtml += '</li>';
  					}
  					strHtml += '</ul>';
  					this.data.totalConut = getEmailCount(opts,mailType);
  					if(mailType == 'taskM'){
  						$('#taskTab').html(strHtml);
  					}else if(mailType == 'endM'){
  						$('#endDateTab').html(strHtml);
  					}else if(mailType == 'buyM'){
  						$('#buyMTab').html(strHtml);
  					}
  					$('.emailListUl li:odd').addClass('oddColor');
  					//DOM加载完成调用一次单选
  					singleCheck();
  					viewEmailDetail();
  				},
  				layPages : function(elem,totalConut,opts,mailType){
  					var _this = this;
  					laypage.render({
  	  	            	elem: elem,
  	  	                count: totalConut,
  	  	                limit: 10,
  	  	            	groups : 6,
  	  	            	layout : ['prev', 'page', 'next','skip','count'],
  		  	            jump: function(obj, first){
  		  	            	if(mailType == 'taskM'){
  		  	            		page.data.tmpPageNum_task = obj.curr;
  		  	            	}else if(mailType == 'endM'){
  		  	            		page.data.tmpPageNum_end = obj.curr;
  		  	            	}else if(mailType == 'buyM'){
  		  	            		page.data.tmpPageNum_buy = obj.curr;
  		  	            	}
  		  	                //首次不执行
  		  	                if(!first){
  		  	               		_this.loadEmailList(opts,mailType);
  		  	                }
  		  	            }
  	  	            });
  				}
  			};
  			//查看详情
  			function viewEmailDetail(){
  				$('.viewEmailDetail').on('click',function(){
  					var mailId = $(this).attr('mailId');
  					layer.load('1');
					$.ajax({
	   					type:'post',
	   			        async:false,
	   			        dataType:'json',
	   			        data:{mailId:mailId},
	   			        url:'mail.do?action=getMailDetail',
	   			        success:function (json){
	   			        	layer.closeAll('loading');
	   			        	page.getNoReadStatusNum();
	   			        	renderEmailDetail(json);
	   			        	var mailType = '';
	   			        	$('#readStatus_' + json.id).html('<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-yidu"></use></svg>');
	   			        	if(json.mailType == 'taskM'){
	   			        		mailType = '[任务通知] ';
	   		  				}else if(json.mailType == 'endM'){
	   		  					mailType = '[会员到期通知] ';
	   		  				}else if(json.mailType == 'buyM'){
	   		  					mailType = '[会员购买通知] ';
	   		  				}
	   			        	var strHtml = renderEmailDetail(json);
	   			        	var fullScreenIndex = layer.open({
	   							title :mailType + '邮件详情',
	   							type : 1, 
	   							content:strHtml, 
	   							area: ['700px', '450px']
	   						});
	   		  				//layer.full(fullScreenIndex);
	   			        }
	   				});
  				});
  			}
  			function renderEmailDetail(json){
  				var strHtml = '';
  				strHtml += '<div class="emDetailTop">';
  				strHtml += '<p><strong>'+ json.mailTitle +'</strong></p>';
  				if(json.mailType == 'taskM'){
  					strHtml += '<p>邮件类型：任务通知</p>';
  				}else if(json.mailType == 'endM'){
  					strHtml += '<p>邮件类型：会员到期通知</p>';
  				}else if(json.mailType == 'buyM'){
  					strHtml += '<p>邮件类型：会员购买通知</p>';
  				}
  				strHtml += '<p>发件人：系统邮件   '+ json.sendInfo +'</p>';
  				strHtml += '<p>发送时间：'+ json.sendTime +'</p></div>';
  				strHtml += '<div class="emDetailCon">';
  				strHtml += '<p>'+ json.mailContent +'</p>';
  				strHtml += '</div>';
  				return strHtml;
  			}
  			//查询条件下的form表单监听
  			form.on('select(readStatussSel)', function(data){
				var value = data.value;
				value == '' ? $('#readStatusInp').val(-1) : $('#readStatusInp').val(value);
			});
  			//checkbox的全选事件
			$('#selectAllInp').on('click',function(){
				var checkStatus = $(this).prop('checked');
				 $('input[name=singleCheckInp_'+ page.data.mailType +']').each(function(){
					this.checked = checkStatus;
					if(checkStatus){
						$(this).prev().addClass('hasActive');
						$('#hasReadBtn').removeClass('noSelA').addClass('hasSelA').attr('canClickFlag','true');
						$('#delBtn').removeClass('noSelA').addClass('hasSelA').attr('canClickFlag','true');
					}else{
	            		$(this).prev().removeClass('hasActive');
	            		$('#hasReadBtn').removeClass('hasSelA').addClass('noSelA').attr('canClickFlag','false');
	            		$('#delBtn').removeClass('hasSelA').addClass('noSelA').attr('canClickFlag','false');
	            	}
				});
				if(checkStatus){
            		$(this).prev().addClass('hasActive');
            	}else{
            		$(this).prev().removeClass('hasActive');
            		$('#mailIdInp').val('');
            	}
			});
			//单个选
			function singleCheck(){
				$('input[name=singleCheckInp_'+ page.data.mailType +']').each(function(){
					$(this).click(function(){
						var checkStatus = $(this).prop('checked'),
							totalLen = $('input[name=singleCheckInp_'+ page.data.mailType +']').length,
	    					hasCheckedLen = $('input[name=singleCheckInp_'+ page.data.mailType +']:checked').length;
		    			if(checkStatus){
							$(this).prev().addClass('hasActive');
							$('#hasReadBtn').removeClass('noSelA').addClass('hasSelA').attr('canClickFlag','true');
							$('#delBtn').removeClass('noSelA').addClass('hasSelA').attr('canClickFlag','true');
						}else{
							$(this).prev().removeClass('hasActive');
						}
		    			if(hasCheckedLen == totalLen){
		    				$('#selectAllInp').prop('checked',true).prev().addClass('hasActive');
		    			}else{
		    				$('#selectAllInp').prop('checked',false).prev().removeClass('hasActive');
		    			}
						if(hasCheckedLen == 0){
	            			page.checkAllInpInit();
						}
					});
				});
			}
 			//标记已读
			$('#hasReadBtn').on('click',function(){
				if($(this).attr('canClickFlag') == 'true'){
					mailIdArray.length = 0;
					$('input[name=singleCheckInp_'+ page.data.mailType +']').each(function(i){
						var checkStatus = $('input[name=singleCheckInp_'+ page.data.mailType +']').eq(i).prop('checked');
						if(checkStatus){
							mailIdArray.push($('input[name=singleCheckInp_'+ page.data.mailType +']').eq(i).val());
						}
					});
					$('#mailIdInp').val(mailIdArray.join(','));
					layer.load('1');
					$.ajax({
						type:'post',
				        async:false,
				        dataType:'json',
				        data : {mailId : $('#mailIdInp').val()},
				        url:'mail.do?action=updateMailStatus',
				        success:function (json){
				        	layer.closeAll('loading');
				        	if(json['result'] == 'success'){
				        		layer.msg('标记已读成功',{icon:1,time:1000},function(){
	   			        			page.loadEmailList(page.data.opts,page.data.mailType);
	   			        			page.initLayPage();
				        			page.checkAllInpInit();
				        			page.getNoReadStatusNum();
				        		});
				        	}
				        }
					});
				}
			});
			//删除邮件
			$('#delBtn').on('click',function(){
				if($(this).attr('canClickFlag') == 'true'){
					mailIdArray.length = 0;
					$('input[name=singleCheckInp_'+ page.data.mailType +']').each(function(i){
						var checkStatus = $('input[name=singleCheckInp_'+ page.data.mailType +']').eq(i).prop('checked');
						if(checkStatus){
							mailIdArray.push($('input[name=singleCheckInp_'+ page.data.mailType +']').eq(i).val());
						}
					});
					$('#mailIdInp').val(mailIdArray.join(','));
					layer.confirm('确定要删除当前邮件吗？', {
						title:'提示',
						skin: 'layui-layer-molv',
						btn: ['确定','取消'] //按钮
					},function(){
						layer.load('1');
						$.ajax({
							type:'post',
					        async:false,
					        dataType:'json',
					        data : {mailId : $('#mailIdInp').val()},
					        url:'mail.do?action=delMail',
					        success:function (json){
					        	layer.closeAll('loading');
					        	if(json['result'] == 'success'){
					        		layer.msg('删除成功',{icon:1,time:1000},function(){
		   			        			page.loadEmailList(page.data.opts,page.data.mailType);
		   			        			page.initLayPage();
		   			        			page.checkAllInpInit();
		   			        			page.getNoReadStatusNum();
					        		});
					        	}
					        }
						});
					});
				}
			});
  			//获取每个mailType对应邮件的条数
  			function getEmailCount(opts,mailType){
  				var count = 0;
				if(opts == 'initLoad'){
					var field = {mailType:mailType,mailTitle:'',readStatus:-1};
				}else if(opts == 'queryLoad'){
					var mailType = $('#mailTypeInp').val(),
						mailTitle = $('#emailTitInp').val() == '' ? '' : escape($.trim($('#emailTitInp').val())),
						readStatus = $('#readStatusInp').val(),
						field = {mailType:mailType,mailTitle:mailTitle,readStatus:readStatus};
				}
  				$.ajax({
					type:'post',
			        async:false,
			        dataType:'json',
			        data : field,
			        url:'mail.do?action=getMailCount',
			        success:function (json){
			        	count = json['result'];
			        }
				});
  				return count;
  			}
  			$(function(){
  				page.init();
  			});
  		});
  	</script>
  </body>
</html>
