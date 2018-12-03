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
		$(this).remove();
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
			$('input[name=myParSqr_'+ objSqrId +']').parent().remove();
			//删除后还应该检测下当前下是否还存在lxr 假如不存在 应该将数组清空
			if($('#lxrBox p').length == 0){
				lxrIdArray.length = 0;
				lxrIdNum.length = 0;
			}
		}
	}
	function judgeFmrLen(fmrLen,objSqrId){
		if(fmrLen > 0){
			$('input[name=myParSqr_'+ objSqrId +']').parent().remove();
			if($('#fmrBox p').length == 0){
				fmrIdArray.length = 0;
				fmrIdNum.length = 0;
			}
		}
	}
	function judgeJslxrLen(jsLxrLen,objSqrId){
		if(jsLxrLen > 0){
			$('input[name=myParSqr_'+ objSqrId +']').parent().remove();
			if($('#techLxrBox p').length == 0){
				jsLxrIdArray.length = 0;
				jsLxrIdNum.length = 0;
			}
		}
	}
}