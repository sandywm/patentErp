/**
 * @Description: 批量读取通知书/缴费单据渲染结构
 * @author: hlf
 */
layui.define(['jquery'],function(exports){
	var $ = layui.jquery;
    var obj = {
    	renderReadRes : function(opts,readInfo){
    		var strHtml = '';
    		$('.hasSelWords').html('');
    		$('#upLoadFileList').html('');
    		$('.importCon').hide();
    		$('.readResWrap').show();
    		//$('#selFileBtn').hide();
    		if(opts == 'batchImp_fee'){
    			for(var i=0;i<readInfo.length;i++){
        			strHtml += '<li>';
        			strHtml += '<p class="oneWid_fee">'+ readInfo[i].fileName +'<p>';
        			strHtml += '<p class="towWid_fee">'+ readInfo[i].readInfo +'<p>';
        			strHtml += '</li>';
        		}
    		}
    		$('.commonResCon').html(strHtml);
    		$('.commonResCon li:odd').addClass('oddColor');
    	}
    };
    //输出接口
    exports('readRes', obj);
});
