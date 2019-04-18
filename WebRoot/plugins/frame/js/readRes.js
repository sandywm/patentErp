/**
 * @Description: 批量读取通知书/缴费单据渲染结构
 * @author: hlf
 */
layui.define(['jquery','table','common'],function(exports){
	var $ = layui.jquery,table = layui.table,common = layui.common;
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
    			$('.commonResCon').html(strHtml);
        		$('.commonResCon li:odd').addClass('oddColor');
    		}else if(opts == 'batchImp_tzs'){
    			table.render({
    				elem : '#readResTab_tzs',
					height: 'full-200',
					data : readInfo,
					page : false,
					even : true,
					cellMinWidth:120,
					cols : [[
					     {field : '', title: '序号',type:'numbers', width:50, align:'center'},
					     {field : 'ajTitle', title: '案件名称', width:240, align:'center'},
					     {field : 'ajNoGf', title: '专利号', width:180, align:'center'},
					     {field : 'tzsName', title: '通知书名称', width:240, align:'center'},
					     {field : 'readResultChi', title: '读取结果', align:'center',templet:function(d){
					    	 if(d.result == 'success'){
					    		 return '<span class="readStaSuc">'+ d.readResultChi +'</span>';
					    	 }else{
					    		 return '<span class="readStaAlarm">'+ d.readResultChi +'</span>';
					    	 }
					     }},
					     {field : '', title: '操作', width:120, align:'center',templet:function(d){
					    	 if(d.result != 'uploadExist'){
					    		 return '<a class="viewTzs" href="javascript:void(0)" lay-event="viewTzsImg" tzsId="'+ d.tzsId +'">查看</a>';
					    	 }
					     }},
					]],
					done : function(){
						$('#isHasReadInp').val(1);//用这个隐藏变量来判断当前读取是否是第一次读取 1：不是第一次 0 第一次
					}
    			});
    		}
    		parent.hasReadFlag = true;
    	}
    };
	table.on('tool(readResTab_tzs)',function(obj){
		if(obj.event == 'viewTzsImg'){
			var tzsId = $(this).attr('tzsId');
			common.getTzsPath(tzsId,parent.parent);
		}
	});
    //输出接口
    exports('readRes', obj);
});
