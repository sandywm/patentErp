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
    		//succ fail alarm-> 之前导入过
    		if(opts == 'batchImp_fee'){
    			var readStaCHN = '';
    			for(var i=0;i<readInfo.length;i++){
    				if(readInfo[i].readStatus == 'succ'){
    					readStaCHN = '<span class="readStaSuc">[读取成功]</span>';
    				}else if(readInfo[i].readStatus == 'fail'){
    					readStaCHN = '<span class="readStaFail">[读取失败]</span>';
    				}else if(readInfo[i].readStatus == 'alarm'){
    					readStaCHN = '<span class="readStaAlarm">[重复导入]</span>';
    				}
        			strHtml += '<li>';
        			strHtml += '<p class="oneWid_fee">'+ readInfo[i].fileName +'<p>';
        			strHtml += '<p class="towWid_fee">'+ readStaCHN + readInfo[i].readInfo +'<p>';
        			strHtml += '</li>';
        		}
    		}
    		$('.commonResCon').html(strHtml);
    		$('.commonResCon li:odd').addClass('oddColor');
    		parent.hasReadFlag = true;
    	}
    };
    //输出接口
    exports('readRes', obj);
});
