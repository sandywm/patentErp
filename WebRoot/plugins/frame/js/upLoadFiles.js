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
		switchZlAjNewOrOld : function(zlAjType){
			var zlAjTypeStr = '';
			zlAjTypeStr += '<select class="selAjType" lay-filter="selAjTypeFilter"><option value="">请选择附件类型</option>';
			zlAjTypeStr += '<option value="js">技术底稿</option><option value="ht">合同文件</option>';
			if(zlAjType == 'old'){//旧案
				zlAjTypeStr += '<option value="dg">定稿文件</option>';
			}
			zlAjTypeStr += '</select><input class="ajFjTypeInp" type="hidden"/>';
			return zlAjTypeStr;
		},
		//专利任务-专利补正下增加附件类型
		addZlTaskBzType : function(){
			var addBzTypeStr = '';
			addBzTypeStr += '<select class="selBzType" lay-filter="selBzTypeFilter"><option value="">请选择附件类型</option>';
			addBzTypeStr += '<option value="sq">申请表</option><option value="df">答复文件</option><option value="th">替换文件</option><option value="zm">证明文件</option>';
			addBzTypeStr += '</select><input class="bzTypeInp" type="hidden"/><input class="rowBzTypeInp" type="hidden"/>';
			return addBzTypeStr;
		},
		uploadFiles : function(url,maxNumber,fileType,opts){
			//addEditZlOpts batchImp_tzs zlTaskOpts zlTaskOpts_bz batchImp_fee
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
					  var that = this,isFmxxTmpFlag = true;;
					  //假如用户分了两次或者多次上传若个干文件，那么，用已经上传的文件和当前已经选中的文件的数量相加和maxNum做比较
					  $('#upListAction').hide();
					  var hasUpDoneLen = $('.hasUpDone').length;
					  var noUpDoneLen = $('.noUpDone').length;
					  if((hasUpDoneLen + noUpDoneLen) > maxNumber){
						  that.errorMsg('最多只能上传'+ maxNumber +'个文件');
						  return false;
					  }else{
						  if(opts == 'zlTaskOpts' || opts == 'zlTaskOpts_bz'){
							  $('.deleteBtn_sel').show();
						  }else{
							  $('.deleteBtn_sel').hide();
						  }
					  }
				  }
				  ,choose: function(obj){
				  	  var that = this,zlTypeTxt='',zlAjTypeStr='',zlBzTypeStr = '';
				      //读取本地文件
				  	  if($('.commonResCon li').length > 0 && opts == 'batchImp_fee'){//通知书/缴费单据批量导入第二次
				  		$('.importCon').show();
				  		$('.readResWrap').hide();
				  		$('.commonResCon').html('');
				  	  }else if(opts == 'batchImp_tzs' && $('#isHasReadInp').val() == 1){
				  		  $('.importCon').show();
				  		  $('.readResWrap').hide();
				  	  }
				  	  if(opts == 'addEditZlOpts'){//添加附件类型 专利添加编辑&&任务这块的专利补正需要
				  		zlAjTypeStr = _this.switchZlAjNewOrOld($('#anjianType').val());
				  	  }else if(opts == 'zlTaskOpts_bz'){//专利补正 添加附件类型 
				  		zlBzTypeStr = _this.addZlTaskBzType();
				  	  }
				  	  if(opts == 'zlTaskOpts' || opts == 'zlTaskOpts_bz'){//从去完成任务里面进来
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
						'<td style="max-width:260px;">'+ file.name +'</td>',
						opts == 'addEditZlOpts' ? '<td><div style="max-width:130px;" class="ajFjTypeTxt layui-form">'+ zlAjTypeStr +'</div></td>' : '',
						opts == 'zlTaskOpts_bz' ? '<td style="max-width:110px;"><div class="zlTypeTxt layui-form">'+ zlBzTypeStr +'</div></td>' : '',
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
				     if(opts == 'addEditZlOpts'){
				    	 form.on('select(selAjTypeFilter)', function(data){//选择附件类型
				    		 var value = data.value,tmpAjTypeVal = $('#anjianType').val(),tmpZlTypeVal = $('#zlTypeInp').val(),parent = $(this).parent().parent().parent();
				    		 var parentNextEle = $(this).parents('tr').find('.zlTypeTxt'),topParent = $(this).parents('tr');
				    		 parent.find('.ajFjTypeInp').val(value);
				    		 if(value == ''){
				    			 topParent.find('.uploadInpHid').removeClass('ajHtPathInp ajJsPathInp ajDgPathInp fmPathInp xxPathInp xxPathInp_dg fmPathInp_dg');
				    		 }else if(value == 'ht'){//当附件类型是合同文件/*并且专利类型是发明+新型时无需指派专利类型*/
				    			 parentNextEle.html('<p class="noZlTypeTxt">无需指派专利类型</p>');
				    			 topParent.find('.uploadInpHid').removeClass('ajJsPathInp ajDgPathInp fmPathInp xxPathInp xxPathInp_dg fmPathInp_dg').addClass('ajHtPathInp');
				    		 }else{
				    			 //必须指定了专利类型才能重新创建
				    			 if(tmpZlTypeVal != ''){
				    				 var zlTypeTxt = _this.switchZlTypeCHN(tmpZlTypeVal);
					    			 parentNextEle.html(zlTypeTxt);
				    				 form.render();
				    				 if(value == 'js'){//技术底稿
				    					 topParent.find('.uploadInpHid').removeClass('ajHtPathInp ajDgPathInp xxPathInp_dg fmPathInp_dg').addClass('ajJsPathInp');
					    			 }else{//定稿文件(旧案)
					    				 topParent.find('.uploadInpHid').removeClass('ajHtPathInp ajJsPathInp xxPathInp fmPathInp').addClass('ajDgPathInp');
					    			 } 
				    				 
				    			 }else{
				    				 parentNextEle.html('');
				    				 if(value == 'js'){
				    					 topParent.find('.uploadInpHid').removeClass('ajHtPathInp ajDgPathInp').addClass('ajJsPathInp');
				    				 }else if(value == 'dg'){
				    					 topParent.find('.uploadInpHid').removeClass('ajHtPathInp ajJsPathInp').addClass('ajDgPathInp');
				    				 }else if(value == ''){
				    					 topParent.find('.uploadInpHid').removeClass('ajHtPathInp ajJsPathInp ajDgPathInp');
				    				 }
				    			 }
				    		 }
				    	 });
				    	 form.on('select(selZlTypeTxt)', function(data){//根据专利类型发明+新型进行专利指派
				    		 var value = data.value,parent = $(this).parent().parent().parent(),topParent = $(this).parents('tr');
				    		 parent.find('.zlTypeInpTarg').val(value);
				    		 var prevAjTypeInp = $(this).parents('tr').find('.ajFjTypeInp');
				    		 if(prevAjTypeInp.val() != 'dg'){
				    			 if(value == 'fm'){
					    			 topParent.find('.uploadInpHid').removeClass('xxPathInp').addClass('fmPathInp');
					    		 }else if(value == 'xx'){
					    			 topParent.find('.uploadInpHid').removeClass('fmPathInp').addClass('xxPathInp');
					    		 }else{
					    			 topParent.find('.uploadInpHid').removeClass('fmPathInp xxPathInp');
					    		 } 
				    		 }else{//旧案下定稿文件
				    			 if(value == 'fm'){
					    			 topParent.find('.uploadInpHid').removeClass('xxPathInp_dg').addClass('fmPathInp_dg');
					    		 }else if(value == 'xx'){
					    			 topParent.find('.uploadInpHid').removeClass('fmPathInp_dg').addClass('xxPathInp_dg');
					    		 }else{
					    			 topParent.find('.uploadInpHid').removeClass('fmPathInp_dg xxPathInp_dg');
					    		 } 
				    		 }
				    	 });
				     }else if(opts == 'zlTaskOpts_bz'){//专利补正 && 补正修改
				    	 form.on('select(selBzTypeFilter)',function(data){
				    		var value = data.value,parent = $(this).parent().parent().parent(),topParent = $(this).parents('tr');
				    		parent.find('.bzTypeInp').val(value);
				    		var str1 = 'dfPath thPath zmPath',str2 = 'sqPath thPath zmPath',str3 = 'sqPath dfPath zmPath',str4 = 'sqPath dfPath thPath';
				    		if(value == 'sq'){//申请表
				    			topParent.find('.uploadInpHid').addClass('sqPath').removeClass(str1);
				    		}else if(value == 'df'){//答复文件
				    			topParent.find('.uploadInpHid').addClass('dfPath').removeClass(str2);
				    		}else if(value == 'th'){//替换文件
				    			topParent.find('.uploadInpHid').addClass('thPath').removeClass(str3);
				    		}else if(value == 'zm'){//证明文件
				    			topParent.find('.uploadInpHid').addClass('zmPath').removeClass(str4);
				    		}
				    		parent.find('.rowBzTypeInp').val(topParent.find('.uploadInpHid').val() + ':' + value);
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
				      }else if(opts == 'addEditZlOpts' || opts == 'zlTaskOpts_bz'){//增加 编辑专利下增加附件类型 专利补正、补正修改 任务
				    	  tds.eq(4).html('<span style="color: #5FB878;">上传成功</span>');
					      tds.eq(6).find('.uploadInpHid').attr('name','hasUpSuccInp');
					      tds.eq(6).find('.uploadInpHid').val(res.data[0].src);
					      if(opts == 'zlTaskOpts_bz'){
					    	  var eqPrevZlBzInpVal = tds.eq(1).find('.rowBzTypeInp').prev('.bzTypeInp').val();
					    	  tds.eq(1).find('.rowBzTypeInp').val(res.data[0].src + ':' + eqPrevZlBzInpVal);
					      }
				      }else if(opts == 'zlTaskOpts'){//除了专利补正 补正修改之外的所有任务流程
				    	  tds.eq(3).html('<span style="color: #5FB878;">上传成功</span>');
					      tds.eq(5).find('.uploadInpHid').attr('name','hasUpSuccInp');
					      tds.eq(5).find('.uploadInpHid').val(res.data[0].src);
				      }
				      return delete this.files[index]; //删除文件队列已经上传成功的文件
				    }else if(res.msg == 'suffixError'){
						layer.msg('上传异常或选中文件不包括支持的格式！请稍后重试',{icon:5,anim:6,time:1500});
						errorTypeFlag = true;
					}else if(res.msg == 'noAbility'){
						layer.msg('抱歉，您暂无权限上传文件',{icon:5,anim:6,time:1500});
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
				    }else if(opts == 'addEditZlOpts' || opts == 'zlTaskOpts_bz'){
				    	tds.eq(4).html('<span style="color: #ff5722;">上传失败</span>');
					    tds.eq(5).find('.layui-progress-bar').css('background-color','#ff5722');
					    tds.eq(6).find('.reloadBtn_sel').removeClass('layui-hide'); //显示重传
				    }else if(opts == 'zlTaskOpts' || opts == ''){//''表示个人/公司身份下发布任务
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
					if(json['result'] == 'success'){
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