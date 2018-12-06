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
		},
		//增加专利时代理费用填写格式
		judgeAgentFee : function(obj){
			if($('#'+obj).val().length==1){
				$('#'+obj).val($('#'+obj).val().replace(/[^1-9]/g,''));
			}else{
				$('#'+obj).val($('#'+obj).val().replace(/\D/g,''));
			}
		},
		judgeAgentFee_num : function(obj,isRightFlag,zlId){
			/*if($('#'+obj).val() == ''){
				layer.msg('专利代理费用不能为空', {icon:5,anim:6,time:1500});
				return;
			}else */if($('#'+obj).val() < 100 && $('#'+obj).val() != ''){
				layer.msg('专利代理费用最少不能低于100元', {icon:5,anim:6,time:1200});
				$('#'+obj).val('');
				return;
			}else if($('#'+obj).val() > 99999){
				layer.msg('专利代理费用最高不能超过99999', {icon:5,anim:6,time:1200});
				$('#'+obj).val('');
				return;
			}else{
				if(isRightFlag && $('#'+obj).val() != ''){//当是编辑的时候需要检测代理费用是否合理
					this.judgeIsRight_agentFee(zlId,$('#'+obj).val());
				}
			}
		},
		//检测当前输入代理费是否合理
		judgeIsRight_agentFee : function(zlId,dlFee){
			layer.load('1');
			$.ajax({
				type:"post",
		        async:false,
		        dataType:"json",
		        data : {zlId :zlId,dlFee:dlFee},
		        url:"/zlm.do?action=checkInpDlFee",
		        success:function (json){
		        	layer.closeAll('loading');
		        	if(json["result"] == 0){
		        	}else if(json["result"] == 1){
		        		layer.msg('客户已交完代理费，不能进行修改', {icon:5,anim:6,time:1200});
		        		return;
		        	}else if(json["result"]  == 2){
		        		layer.msg('代理费不能小于客户已交费用', {icon:5,anim:6,time:1200});
		        		return;
		        	}else if(json["result"]  == 3){
		        		layer.msg('代理费必须是100-100000的正整数', {icon:5,anim:6,time:1200});
		        		return;
		        	}
		        }
			});
		},
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
		}
    };
    //输出接口
    exports('common', obj);
});
