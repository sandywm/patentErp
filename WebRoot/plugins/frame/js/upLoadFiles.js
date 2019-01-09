/**
 * @Description: 上传通用
 * @author: hlf
 */
var errorTypeFlag = false;//判断当前上传的文件类型是否是正确的
layui.define(['element','jquery','upload','form','readRes'],function(exports){
	var element = layui.element,
	upload = layui.upload,form = layui.form,
	readRes = layui.readRes;
	var obj = {
		data : {
			globalOpts : '',
    		indexLayer : '<div class="indexLayer"><div class="loadingWrap"></div></div>',
    		upSuccTips : '<p class="upTipsTxt">上传成功，<span id="countNum_up"></span>秒后开始自动读取</p>',
    		readingTips : '<div class="spinner"></div><p>正在读取中<span class="dotting"></span></p><p>请勿刷新页面</p>',
    		readSuccTips : '<i class="iconfont layui-extend-succ readSucc"></i><p class="succTxt">读取成功，<span id="countNum"></span>秒后关闭</p>'
		},
		switchZlTypeCHN : function(zlTypeEng){
			var zlTypeCHN = '';
			if(zlTypeEng == 'fm'){
				zlTypeCHN = '发明';
			}else if(zlTypeEng == 'wg'){
				zlTypeCHN = '外观';
			}else if(zlTypeEng == 'syxx'){
				zlTypeCHN = '实用新型';
			}else if(zlTypeEng == 'fmxx'){
				zlTypeCHN = '<select class="selZlType" lay-filter="selZlTypeTxt"><option value="">请指派专利类型</option><option value="fm">发明</option> <option value="xx">新型</option></select><input class="zlTypeInpTarg" type="hidden"/>';
			}
			return zlTypeCHN;
		},
		uploadFiles : function(url,maxNumber,fileType,opts){
			this.data.globalOpts = opts;
			var imageListView = $('#upLoadFileList')
			,_this = this
			 //,alreadyUploadFiles={}//记录已经上传成功的文件相对路径（后台返回）
			,uploadListIns=upload.render({
				  elem : '#selFileBtn'
				  ,url: url//这里设置自己的上传接口
				  ,accept: 'images'
				  ,exts : fileType
				  ,multiple: true
				  ,auto: false
				  ,number: maxNumber
				  ,bindAction: '#upListAction' 
				 	,xhr:xhrOnProgress
				 	,progress: function(index,value){//上传进度回调 value进度值
				 	    element.progress('image'+index, value+'%');//设置页面进度条
				 	}
				  ,errorMsg: function(content){
				  	layer.msg(content, {icon: 2, shift: 6});
				  }
				  ,before : function(obj){
					  var that = this;
					  //假如用户分了两次或者多次上传若个干文件，那么，用已经上传的文件和当前已经选中的文件的数量相加和maxNum做比较
					  $('#upListAction').hide();
					  var hasUpDoneLen = $('.hasUpDone').length;
					  var noUpDoneLen = $('.noUpDone').length;
					  if((hasUpDoneLen + noUpDoneLen) > maxNumber){
						  that.errorMsg('最多只能上传'+ maxNumber +'个文件');
						  return false;
					  }else{
						  $('.deleteBtn_sel').hide();
					  }
				  }
				  ,choose: function(obj){
				  	  var that = this,zlTypeTxt='';
				      //读取本地文件
				  	  if($('.commonResCon li').length > 0){//通知书/缴费单据批量导入第二次
				  		$('.importCon').show();
				  		$('.readResWrap').hide();
				  		$('.commonResCon').html('');
				  	  }
				  	  if(opts == 'zlTaskOpts'){
				  		zlTypeTxt = parent.globalTaskOpts.zlType;
				  	  }else{
				  		zlTypeTxt = _this.switchZlTypeCHN($('#zlTypeInp').val());
				  	  }
				      obj.preview(function(index, file, result){
				      	var files = that.files = obj.pushFile(); //将每次选择的文件追加到文件队列
				      	var fileNames = file.name.split('.')[0]; 
						var fileType = file.name.substr(file.name.lastIndexOf('.'));
						var size = file.size;
				        if(fileNames.length > 50){
							layer.msg('上传的文件名不能超过50个字符',{icon:5,anim:6,time:1000});
		    				return;
						}
						if(fileType == '.jpg' || fileType == '.png' || fileType == '.bmp' || fileType == '.gif' || fileType == '.jpeg'){
			    			//如果是图片，单个图片不能大于5M
			    			if(size > (5 * 1024 * 1024)){
			    				layer.msg('上传的图片文件不能大于5M',{icon:5,anim:6,time:1000});
			    				return;
			    			}
			    		}else{
			    			//如果是其他文件类类型，单个文件不能大于10M
			    			if(size > (10 * 1024 * 1024)){
			    				layer.msg('上传的文件不能大于10M',{icon:5,anim:6,time:1000});
			    				return;
			    			}
			    		}
				       var tr = $(['<tr class="hasSelTr noUpDone" id="upload-'+ index +'">',
						'<td>'+ file.name +'</td>',
						opts == 'batchImp_tzs' || opts == 'batchImp_fee' ? '' : '<td><div class="zlTypeTxt layui-form">'+ zlTypeTxt +'</div></td>',
						'<td>'+ (file.size/1014).toFixed(1) +'kb</td>',
						'<td>等待上传</td>',
						'<td style="width:120px;">',
				     	'<div class="layui-progress layui-progress-big layui-progress-radius-fix" lay-showpercent="true" lay-filter="image'+index+'">',
				     	  '<div class="layui-progress-bar progressBarBg" lay-percent="0%">',
				     	    '<span class="layui-progress-text">0%</span>',
				     	  '</div>',
				     	'</div>',
				     	'</td>',
						'<td>',
						'<button class="layui-btn layui-btn-xs reloadBtn reloadBtn_sel layui-hide">重传</button>',
						'<input class="uploadInpHid" name="notUpInp" type="hidden"/>',
						'<button class="layui-btn layui-btn-xs layui-btn-danger deleteBtn deleteBtn_sel">删除</button>',
						'</td>',
						'</tr>'].join(''));
					    var noUpDoneLen = $('.noUpDone').length;
					    var hasUpDoneLen = $('.hasUpDone').length;
				        if(opts == 'batchImp_tzs' || opts == 'batchImp_fee'){
				        	$('.hasSelWords').html('已选择' + ((noUpDoneLen+1) + hasUpDoneLen) + '个文件,' + (noUpDoneLen+1) + '个未上传，' + hasUpDoneLen + "个已上传");
				        }
				     	//删除
				        tr.find('.deleteBtn_sel').on('click', function(){
				        	var noUpDoneLen = $('.noUpDone').length,hasUpDoneLen = $('.hasUpDone').length;
				        	if(opts == 'batchImp_tzs' || opts == 'batchImp_fee'){
				        		$('.hasSelWords').html('已选择' + ((noUpDoneLen-1) + hasUpDoneLen) + '个文件,' + (noUpDoneLen-1) + '个未上传，' + hasUpDoneLen + "个已上传");
					    	    if(noUpDoneLen == 1 && hasUpDoneLen == 0){//一个都未上传
					    	    	$('.hasSelWords').html('');
					    	    }else if(noUpDoneLen == 1 && hasUpDoneLen != 0){//一部分上传 然后重新选择了一些文件但并没有上传
					    	    	$('#upListAction').hide();
					    	    	$('.hasSelWords').html('已选择' + ((noUpDoneLen-1) + hasUpDoneLen) + '个文件,' + (noUpDoneLen-1) + '个未上传，' + hasUpDoneLen + "个已上传");
					    	    }
					        }
				        	if(noUpDoneLen != 0 && hasUpDoneLen != 0 && hasUpDoneLen < maxNumber){
				        		if((noUpDoneLen+hasUpDoneLen) == (maxNumber+1)){
				        			$('#upListAction').show();
				        		}
				        	}
				        	delete files[index]; //删除对应的文件
				            tr.remove();				            
				            uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
				        	if($('.reloadBtn_sel').length == 0){
				        		$('#upListAction').hide();
				        		if($('.deleteBtn_edit').length == 0){
				        			$('#fujianInp').val('');
				        		}
				        	}
				        });
				       //单个重传
				       tr.find('.reloadBtn_sel').on('click', function(){
				         obj.upload(index, file);
				       });
				       imageListView.append(tr);
				       $('#upListAction').show();
				       form.render();
				  	});
				     if(opts != 'batchImp_tzs' || opts != 'zlTaskOpts' || opts == 'batchImp_fee'){
				    	 form.on('select(selZlTypeTxt)', function(data){
				    		 var value = data.value,parent = $(this).parent().parent().parent(),topParent = $(this).parents('tr');
				    		 parent.find('.zlTypeInpTarg').val(value);
				    		 if(value == 'fm'){
				    			 topParent.find('.uploadInpHid').removeClass('xxPathInp').addClass('fmPathInp');
				    		 }else if(value == 'xx'){
				    			 topParent.find('.uploadInpHid').removeClass('fmPathInp').addClass('xxPathInp');
				    		 }else{
				    			 topParent.find('.uploadInpHid').removeClass('fmPathInp xxPathInp');
				    		 }
				    	 });
				     } 
				  }
				  ,done: function(res, index, upload){
					layer.closeAll('loading');
				    if(res.msg == 'success'){ //上传成功
				      var tr = imageListView.find('tr#upload-'+ index)
				      ,tds = tr.children(),noUpDoneLen = $('.noUpDone').length,hasUpDoneLen = $('.hasUpDone').length;
				      tr.removeClass('noUpDone').addClass("hasUpDone");
				      //alreadyUploadFiles[index]=res.data && res.data[0];//缓存已上传的文件
				      $('#upListAction').hide();
				      if(opts == 'batchImp_tzs' || opts == 'batchImp_fee'){//批量导入通知书 上传成功后隐藏/移除 删除按钮
				    	  tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
					      tds.eq(4).find('.uploadInpHid').attr('name','hasUpSuccInp');
					      tds.eq(4).find('.uploadInpHid').val(res.data[0].src);
						  $('.hasSelWords').html('已选择' + (noUpDoneLen + hasUpDoneLen) + '个文件, '+ (noUpDoneLen-1) +'个未上传，' + (hasUpDoneLen+1) + "已上传");
						  if((noUpDoneLen + hasUpDoneLen) == (hasUpDoneLen+1)){
							  //执行读取通知书 调出遮罩层	
							  parent.parent.$('body').append(_this.data.indexLayer);
							  parent.parent.$('body').find('.loadingWrap').html(_this.data.upSuccTips);
							  _this.showTime(3,parent.parent.$('body').find('#countNum_up'),opts,'',false);
						  }
				      }else{
				    	  tds.eq(3).html('<span style="color: #5FB878;">上传成功</span>');
					      tds.eq(5).find('.uploadInpHid').attr('name','hasUpSuccInp');
					      tds.eq(5).find('.uploadInpHid').val(res.data[0].src);
				      }
				      return delete this.files[index]; //删除文件队列已经上传成功的文件
				    }else if(res.msg == 'suffixError'){
						layer.msg('上传异常或选中文件不包括支持的格式！请稍后重试',{icon:5,anim:6,time:1500});
						errorTypeFlag = true;
					}
				    this.error(index, upload);
				  }
				  ,error: function(index, upload){
				    var tr = imageListView.find('tr#upload-'+ index)
				    ,tds = tr.children();	
				    if(opts == 'batchImp_tzs' || opts == 'batchImp_fee'){
				    	tds.eq(2).html('<span style="color: #ff5722;">上传失败</span>');
					    tds.eq(3).find('.layui-progress-bar').css('background-color','#ff5722');
					    tds.eq(4).find('.reloadBtn_sel').removeClass('layui-hide'); //显示重传
				    }else{
				    	tds.eq(3).html('<span style="color: #ff5722;">上传失败</span>');
					    tds.eq(4).find('.layui-progress-bar').css('background-color','#ff5722');
					    tds.eq(5).find('.reloadBtn_sel').removeClass('layui-hide'); //显示重传
				    }
				  }
			});
		},
		//倒计时
		showTime : function(count,obj,opts,readInfo,isReadSucFlag){
			var _this = this,tmpUrl = '',filePath = '',tmpUpSuccPath=[];
			$(obj).html(count);
			if(count == 0){
				if(isReadSucFlag){//批量读取成功
					parent.parent.$('body').find('.indexLayer').remove();
					readRes.renderReadRes(opts,readInfo);//渲染批量读取结果
				}else{//上传成功，倒计时开始批量读取
					parent.parent.$('body').find('.loadingWrap').html(_this.data.readingTips);
					$('input[name="hasUpSuccInp"]').each(function(i){
						tmpUpSuccPath.push($('input[name="hasUpSuccInp"]').eq(i).val());
					});
					if(opts == 'batchImp_tzs'){//读取通知书
						tmpUrl = '/zlm.do?action=dealTzsDetail';
					}else if(opts == 'batchImp_fee'){//读取导入费用单据
						tmpUrl = '/fee.do?action=dealYjFeeExcel';
					}
					filePath = tmpUpSuccPath.join(',');
					_this.readImportFile(tmpUrl,filePath);
				}
			}else{
				count -= 1;
				setTimeout(function () {
					_this.showTime(count,obj,opts,readInfo,isReadSucFlag);
				}, 1000);
			}
		},
		//批量读取通知书/费用单据
		readImportFile : function(tmpUrl,filePath){
			var _this = this;
			$.ajax({
				type:"post",
				dataType:"json",
				async:true,
				data:{filePath:filePath},
				url:tmpUrl,
				success:function(json){
					console.log(json)
					if(json['result'] == 'success'){
						//console.log(json.readInfo)
						parent.parent.$('body').find('.loadingWrap').html(_this.data.readSuccTips);
						_this.showTime(3,parent.parent.$('body').find('#countNum'),_this.data.globalOpts,json.readInfo,true);
					}else if(json['result'] == 'error'){
						layer.msg('批量读取异常，请稍后重试',{icon:5,anim:6,time:1500});
					}
				}
			});
		}
	};
	//输出接口
    exports('upLoadFiles', obj);
});