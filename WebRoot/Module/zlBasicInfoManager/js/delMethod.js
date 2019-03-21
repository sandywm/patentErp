//公共删除已添加的方法（技术领域、申请人）
function delHasAddMethod(obj,opts){
	var tmpArray = [];
	$('.'+ obj).on('click',function(){
		var objId = $(this).attr('id'),lxrLen = $('#lxrBox p').length,fmrLen = $('#fmrBox p').length,jsLxrLen = $('#techLxrBox p').length;
		var objSqrId = '';
		if(opts == 'delFieldOpt'){
			tmpArray = fieldArray;
		}else if(opts == 'delSqrOpt'){
			tmpArray = sqrArray;
			objSqrId = $(this).attr('id').split('_')[1];
		}else if(opts == 'delLxrOpt'){
			tmpArray = lxrIdArray;
		}else if(opts == 'delFmrOpt'){
			tmpArray = fmrIdArray;
		}else if(opts == 'delJsLxrOpt'){
			tmpArray = jsLxrIdArray;
		}
		for(var i=0;i<tmpArray.length;i++){
			if(objId == tmpArray[i]){
				tmpArray.splice(i,1);
				if(opts == 'delSqrOpt'){
					sqrIdArray.splice(i,1);
					sqrTxtArray.splice(i,1);
				}
			}
		}
		$(this).parent().remove();	
		if(opts == 'delSqrOpt'){
			judgeLxrLen(lxrLen,objSqrId);
			judgeFmrLen(fmrLen,objSqrId);
			judgeJslxrLen(jsLxrLen,objSqrId);
		}else if(opts == 'delLxrOpt'){
			judgeLxrLen(lxrLen,objSqrId);
		}else if(opts == 'delFmrOpt'){
			judgeFmrLen(fmrLen,objSqrId);
		}else if(opts == 'delJsLxrOpt'){
			judgeJslxrLen(jsLxrLen,objSqrId);
		}
	});
	function judgeLxrLen(lxrLen,objSqrId){
		if(lxrLen > 0){
			$('input[name=myParSqr_'+ objSqrId +']').parent().parent().remove();
			//删除后还应该检测下当前下是否还存在lxr 假如不存在 应该将数组清空
			if($('#lxrBox p').length == 0){
				lxrIdArray.length = 0;
				lxrIdNum.length = 0;
			}
		}
	}
	function judgeFmrLen(fmrLen,objSqrId){
		if(fmrLen > 0){
			$('input[name=myParSqr_'+ objSqrId +']').parent().parent().remove();
			if($('#fmrBox p').length == 0){
				fmrIdArray.length = 0;
				fmrIdNum.length = 0;
			}
		}
	}
	function judgeJslxrLen(jsLxrLen,objSqrId){
		if(jsLxrLen > 0){
			$('input[name=myParSqr_'+ objSqrId +']').parent().parent().remove();
			if($('#techLxrBox p').length == 0){
				jsLxrIdArray.length = 0;
				jsLxrIdNum.length = 0;
			}
		}
	}
}
//不同申请人对应其下联系人 发明人 技术联系人 显示对应申请人色块
function commonAddColorCls(){
	if($('.delLxrBtn').length > 0){
		$('#lxrBox').find('p').each(function(i){
			for(var j=0;j<sqrIdArray.length;j++){
				if($('#lxrBox').find('p').eq(i).attr('sqrIds') == sqrIdArray[j]){
					var clsColor = $('#sqrId_' + sqrIdArray[j]).attr('clscolor');
					$('#lxrBox').find('p').eq(i).addClass(clsColor);
				}
			}
		});
	}
	if($('#fmrBox').find('p').length > 0){
		$('#fmrBox').find('p').each(function(i){
			for(var j=0;j<sqrIdArray.length;j++){
				if($('#fmrBox').find('p').eq(i).attr('sqrIds') == sqrIdArray[j]){
					var clsColor = $('#sqrId_' + sqrIdArray[j]).attr('clscolor');
					$('#fmrBox').find('p').eq(i).addClass(clsColor);
				}
			}
		});
	}
	$('#techLxrBox').find('p').each(function(i){
		for(var j=0;j<sqrIdArray.length;j++){
			if($('#techLxrBox').find('p').eq(i).attr('sqrIds') == sqrIdArray[j]){
				var clsColor = $('#sqrId_' + sqrIdArray[j]).attr('clscolor');
				$('#techLxrBox').find('p').eq(i).addClass(clsColor);
			}
		}
	});
}
var detailOpts = '',cusIdDet=0,lxrIdDet=0,fmrIdDet=0;
//查看申请人 联系人 发明人 基本信息公共方法
function viewDetInfo_cus(){
	//查看申请人信息公共方法
	$('.getSqrDetailBtn').on('click',function(){
		var cusId = $(this).attr('cusId'),cusName=$(this).attr('cusName');
		var title = '申请人[<span class="detColor">' + cusName + '</span>]的详情',wid='550px',hei='300px';
		cusIdDet = cusId;
		detailOpts = 'cusDetInfo';
		commonOpenLayer(title,wid,hei);
	});
}
function viewDetInfo_lxr(){
	//查看联系人基本信息公共方法
	$('.getLxrInfoBtn').on('click',function(){
		var lxrId = $(this).attr('lxrId'),lxrName = $(this).attr('lxrName'),wid='450px',hei='200px';
		var title = '事务联系人[<span class="detColor">' + lxrName + '</span>]的详情';
		lxrIdDet = lxrId;
		detailOpts = 'lxrDetInfo'; 
		commonOpenLayer(title,wid,hei);
	});
}
function viewDetInfo_fmr(){
	//查看发明人基本信息公共方法
	$('.getFmrInfoBtn').on('click',function(){
		var fmrId = $(this).attr('fmrId'),fmrName = $(this).attr('fmrName'),wid='450px',hei='250px';
		var title = '发明人[<span class="detColor">' + fmrName + '</span>]的详情';
		fmrIdDet = fmrId;
		detailOpts = 'fmrDetInfo'; 
		commonOpenLayer(title,wid,hei);
	});
}
function viewDetInfo_jslxr(){
	$('.getJsLxrInfoBtn').on('click',function(){
		var fmrId = $(this).attr('fmrId'),fmrName = $(this).attr('fmrName'),wid='450px',hei='250px';
		var title = '技术联系人[<span class="detColor">' + fmrName + '</span>]的详情';
		fmrIdDet = fmrId;
		detailOpts = 'JsLxrDetInfo'; 
		commonOpenLayer(title,wid,hei);
	});
}
function commonOpenLayer(title,wid,hei){
	layer.open({
		title:title,
		type: 2,
	  	area: [wid, hei],
	  	fixed: true, //不固定
	  	maxmin: false,
	  	shadeClose :true,
	  	content: '/Module/zlBasicInfoManager/jsp/viewDetailInfo.html'
	});	
}