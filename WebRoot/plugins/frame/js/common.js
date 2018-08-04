/**
 * @Description: 基础配置
 * @author: hlf
 */
//自定义模块
layui.define(['jquery'], function(exports){
    var $ = layui.jquery;
    var obj = {
    	//通用验证码更换	
        vercode : function(){
        	var obj = document.getElementById("sessCode");
			obj.src = "authImg?code="+Math.random()+100;
		}
    };
    //输出接口
    exports('common', obj);
});
