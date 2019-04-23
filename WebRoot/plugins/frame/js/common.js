/**
 * @Description: 基础配置
 * @author: hlf
 */
var tzsImg = [];
//自定义模块
layui.define(['rate'],function(exports){
	var $ = layui.jquery,rate = layui.rate;
    var obj = {
    	data : {
    		lcIdDet : 0 //任务详情中的lcId
    	},
    	getId : function(id){
    		return document.getElementById(id);
    	},
    	//通用验证码更换	
        vercode : function(){        	
        	var obj = this.getId("sessCode");
			obj.src = "authImg?code="+Math.random()+100;
		},
		//获取用户基本信息
		getUserBasicInfo : function(opts){
			$.ajax({
				type:"post",
		        dataType:"json",
		        url:"user.do?action=getUserDetail",
		        success:function (json){
		        	layer.closeAll("loading");
		        	if(json['result'] == 'success'){
		        		if(opts == 'mine'){
		        			$('#userName').html(json.name);
		        		}else if(opts == 'myParent'){
		        			parent.$('#userName').html(json.name);
		        		}
			        	sessionStorage.setItem('userInfo',JSON.stringify(json));
		        	}
		        }
			});
		},
		//获取权限
		getPermission : function(actModName,lcNameEng,zlId){
			var permissionFlag = false;
			$.ajax({
				type:"post",
		        async:false,
		        dataType:"json",
		        data : {actNameEng : actModName,lcNameEng:lcNameEng,zlId:zlId},
		        url:"/zlm.do?action=getAbilityFlag",
		        success:function (json){
		        	if(json["result"] == "ability"){
		        		permissionFlag = true;
		        	}else if(json["result"] == "noAbility"){
		        		permissionFlag = false;
		        	}else if(json["result"]  == "error"){
		        		permissionFlag = false;
		        	}
		        }
			});
			return permissionFlag;
		},
		//通用下载附件方法
		downFiles : function(downFilePath,downFileType){
			layer.load('1');
			var url = '';
			if(downFileType == 0){//下载通用附件
				url = '/zlm.do?action=downFile';
			}else if(downFileType == 1){//下载导出费用记录/发票
				url = '/zlm.do?action=downFile_1';
			}else if(downFileType == 2){//任务流程中 通知书类型的下载
				url = '/zlm.do?action=zipDownLoad';
			}
			var form = $("<form>");   //定义一个form表单
			form.attr('style', 'display:none'); //在form表单中添加查询参数
			form.attr('target', '');
			form.attr('method', 'post');
			form.attr('action', url);
			var input1 = $('<input>');
			input1.attr('type', 'hidden');
			input1.attr('name', 'fileUrl');
			input1.attr('value', escape(downFilePath));
			$('body').append(form);  //将表单放置在web中 
			form.append(input1);   //将查询参数控件提交到表单上
		  	form.submit();
		  	layer.closeAll('loading');
		},
		//下载客户确认函通用方法
		downCusQrhZipFiles : function(downFileType){
			layer.load('1');
			if(downFileType == 0){//任务详情里面的下载客户确认函 只传lcId
				var field = {lcId : this.data.lcIdDet};
			}else{//客户确认函模块进来 带有其他参数 ajTitle ajNo cusId ...
				
			}
			$.ajax({
				type:'post',
		        dataType:'json',
		        data : field,
		        url:'/zlm.do?action=createBatchCusQrh',
		        success:function (json){
		        	if(json['result'] == 'success'){
		        		layer.closeAll('loading');
		        		var form = $("<form>");   //定义一个form表单
		    			form.attr('style', 'display:none'); //在form表单中添加查询参数
		    			form.attr('target', '');
		    			form.attr('method', 'post');
		    			form.attr('action', '/zlm.do?action=downZipFile');
		    			var input1 = $('<input>');
		    			input1.attr('type', 'hidden');
		    			input1.attr('name', 'zipPath');
		    			input1.attr('value', escape(json.zipPath));
		    			$('body').append(form);  //将表单放置在web中 
		    			form.append(input1);   //将查询参数控件提交到表单上
		    		  	form.submit();
		        	}else if(json['result'] == 'noAbility'){
		        		layer.msg('抱歉，您暂无权限下载客户确认函', {icon:5,anim:6,time:2000});
		        	}else if(json['result'] == 'noInfo'){
		        		layer.msg('暂无客户确认函', {icon:5,anim:6,time:1500});
		        	}
		        	
		        }
			});
		},
		//导出费用清单 客户清单 费用清单
		commonExportFun : function(url,name,value){
			layer.load('1');
		    var form = $("<form>");   //定义一个form表单
			form.attr('style', 'display:none;'); //在form表单中添加查询参数
			form.attr('target', '');
			form.attr('method', 'post');
			form.attr('action', url);
			var input1 = $('<input>');
			input1.attr('type', 'text');
			input1.attr('name', name);
			input1.attr('value', value);
			$('body').append(form);  //将表单放置在web中 
			form.append(input1);   //将查询参数控件提交到表单上
		  	form.submit();
			layer.closeAll('loading');
		},
		//星星只读
		rateFunReadOnly : function(elem,value){
			rate.render({
			    elem: '#' + elem
			    ,value : value
			    ,length:3
			    ,readonly:true
			    ,text: true
			    ,theme: '#FF8000'
			    ,setText: function(value){ //自定义文本的回调
			      var arrs = {
			        '1': '简单'
			        ,'2': '困难'
			        ,'3': '复杂'
			      };
			      this.span.text(arrs[value] || '');
			    }
			});
		},
		//字符串转数组
		switchToArray : function(tmpArray){
			var strHtml = '';
			for(var i=0;i<tmpArray.length;i++){
				strHtml += '<span class="blockSpan">'+ tmpArray[i] +'</span>';
			}
			return strHtml;
		},
		//获取通知书个数 
		getTzsPath : function(tzsId,parType){
			layer.load('1');
			var _this = this;
			$.ajax({
				type:'post',
		        dataType:'json',
		        data : {tzsId:tzsId},
		        url:'/zlm.do?action=getTzsJpgInfo',
		        success:function (json){
		        	layer.closeAll('loading');
		        	if(json['result'] == 'success'){
		        		//查看通知书图片
		        		if(json.imgFilePath != ''){
		        			tzsImg = json.imgFilePath.split(',');
		        			_this.viewTzsImg(parType);
		        		}else{
		        			layer.msg('暂无通知书!', {icon:5,anim:6,time:1500});
		        		}
		        	}else if(json['result'] == 'noInfo'){
		        		layer.msg('暂无通知书！', {icon:5,anim:6,time:1500});
		        	}
		        }
			});
		},
		//查看通知书
		viewTzsImg : function(parType){
			var str = '';
			for(var i=0,iLen=tzsImg.length;i<iLen;i++){
				str += '<div class="swiper-slide">';
				str += '<div class="loadingBars"></div>';
				str += '<img class="tzsImg" data-src="/Module/uploadFile/'+ tzsImg[i] +'" src="" style="display:none;"/>';
				str += '</div>';
			}
			parType.$('.tzsLayer').show();
			parType.$('.swiperBox').show();
			parType.$('#swiperWrap').html(str);
			parType.swiperTzs();
			
			var imgs = parType.$(".tzsImg"), Img = [];
	        imgs.each(function (i, v) {
	        	var originSrc = $(v).attr('data-src');
	            Img[i] = new Image();
	            Img[i].src = originSrc;
	            Img[i].onload = function(i) {
	            	$(v).show().attr('src',originSrc);
	            	$('.loadingBars').eq(i).hide();
	            };
	        });
		}
    };
    //输出接口
    exports('common', obj);
});
