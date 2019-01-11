/**
 * @Description: 基础配置
 * @author: hlf
 */
//自定义模块
layui.define(['rate'],function(exports){
	var $ = layui.jquery,rate = layui.rate;
    var obj = {
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
		}
    };
    //输出接口
    exports('common', obj);
});
