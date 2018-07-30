/**
 * @Description: 基础配置
 * @author: hlf
 */
//自定义模块
layui.define(['jquery'], function(exports){
    var $ = layui.jquery;
    var obj = {
        hello : function(str){
        	alert("hello" + str);
        }
    };
    //输出接口
    exports('common', obj);
});
