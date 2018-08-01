<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html> 
<html>
  <head>
    <title>专利管理系统--用户注册</title>
    <link href="/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <link href="/plugins/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="/plugins/pace/pace-theme-flash.min.css" rel="stylesheet" type="text/css"/>
	<script src="/plugins/pace/pace.min.js"></script>
  </head>
  <body>
  	<div class="layui-form">
		<div class="layui-input-inline">
			<select name="province" lay-filter="province" class="province">
				<option value="">请选择省</option>
			</select>
		</div>
		<div class="layui-input-inline">
			<select name="city" lay-filter="city" disabled>
				<option value="">请选择市</option>
			</select>
		</div>
		<div class="layui-input-inline">
			<select name="area" lay-filter="area" disabled>
				<option value="">请选择县/区</option>
			</select>
		</div>
	</div>
	
  	<script src="/plugins/layui/layui.js"></script>
  	<script type="text/javascript">
  		layui.config({
  			 base: '/plugins/frame/js/'  
  		});
  		layui.use(['address','form','jquery'],function(){
  			var	address = layui.address(),
  				form = layui.form
  				$ = layui.jquery;
  		});
  		
  	</script>
  </body>
</html>
	