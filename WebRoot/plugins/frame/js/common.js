/**
 * @Description: 基础配置
 * @author: hlf
 */
//自定义模块
layui.define(function(exports){
	var $ = layui.jquery;
    var obj = {
    	//通用验证码更换	
        vercode : function(){        	
        	var obj = this.getId("sessCode");
			obj.src = "authImg?code="+Math.random()+100;
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
		downFiles : function(downFilePath){
			layer.load('1');
			var form = $("<form>");   //定义一个form表单
			form.attr('style', 'display:none'); //在form表单中添加查询参数
			form.attr('target', '');
			form.attr('method', 'post');
			form.attr('action', "/zlm.do?action=downFile");
			var input1 = $('<input>');
			input1.attr('type', 'hidden');
			input1.attr('name', 'fileUrl');
			input1.attr('value', escape(downFilePath));
			$('body').append(form);  //将表单放置在web中 
			form.append(input1);   //将查询参数控件提交到表单上
		  	form.submit();
		  	layer.closeAll('loading');
		}
    };
    //输出接口
    exports('common', obj);
});
