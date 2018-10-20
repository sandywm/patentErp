/**
 * @Description: 基础配置
 * @author: hlf
 */
//自定义模块
layui.define(function(exports){
	//var $ = layui.jquery;
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
		}
    };
    //输出接口
    exports('common', obj);
});
