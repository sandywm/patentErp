var errorTypeFlag = false;
layui.define(['element','jquery','upload'],function(exports){
	var element = layui.element,
	upload = layui.upload;
	var obj = {
		uploadFiles : function(url,maxNumber,fileType){
			//console.log(url)
			var imageListView = $('#upLoadFileList')
			 ,alreadyUploadFiles={}//记录已经上传成功的文件相对路径（后台返回）
			//,maxNumber=5//这里设置自己允许最大上传数
			,uploadListIns=upload.render({
				  elem : '#selFileBtn'
				  ,url: url//这里设置自己的上传接口
				  ,accept: 'file'
				  ,exts : fileType
				  ,multiple: true
				  ,auto: false
				  ,size:10240
				  ,number: maxNumber
				  ,bindAction: '#upListAction' 
				 	,xhr:xhrOnProgress
				 	,progress: function(index,value){//上传进度回调 value进度值
				 	    element.progress('image'+index, value+'%');//设置页面进度条
				 	}
				  ,errorMsg: function(content){
				  	layer.msg(content, {icon: 2, shift: 6});
				  }
				  ,choose: function(obj){
				  	var that = this;
				  	//检查是否已经成功上传了maxNumber个图片
				  	//获取上传成功的文件：
				  	var alreadyUploadFilesLength = Object.keys(alreadyUploadFiles).length;
				  	if(alreadyUploadFilesLength>=maxNumber){
				  		that.errorMsg('同时最多只能上传的数量为：'+maxNumber + "个");
				  		return false;
				  	}
				      //读取本地文件
				      obj.preview(function(index, file, result){
				      	var files = that.files = obj.pushFile(); //将每次选择的文件追加到文件队列
				      	var fileNames = file.name.split('.')[0]; 
						var fileType = file.name.substr(file.name.lastIndexOf('.'));
						var size = file.size;
				       var alreadyChooseFileLength = 0;
				       if(that.files){//已经选择，未上传的文件
				       	$.each(that.files, function(){
				       		alreadyChooseFileLength++;
				       	});
				       }
				       if((alreadyUploadFilesLength + alreadyChooseFileLength) > maxNumber){
				       	that.errorMsg('同时最多只能上传的数量为：'+maxNumber);
				       	delete that.files[index];	
				       	delete files[index];
				       	uploadListIns.config.elem.next()[0].value = '';
				   		return false;
				       }
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
				       var tr = $(['<tr id="upload-'+ index +'">',
						'<td>'+ file.name +'</td>',
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
				     	//删除
				        tr.find('.deleteBtn_sel').on('click', function(){
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
				  	});
				  }
				  ,done: function(res, index, upload){
				    if(res.msg == 'success'){ //上传成功
				      var tr = imageListView.find('tr#upload-'+ index)
				      ,tds = tr.children();
				      tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
				      tds.eq(4).find('.uploadInpHid').attr('name','hasUpSuccInp');
				      tds.eq(4).find('.uploadInpHid').val(res.data[0].src);
				      alreadyUploadFiles[index]=res.data && res.data[0];//缓存已上传的文件
				      $('#upListAction').hide();
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
				    tds.eq(2).html('<span style="color: #ff5722;">上传失败</span>');
				    tds.eq(3).find('.layui-progress-bar').css('background-color','#ff5722');
				    tds.eq(4).find('.reloadBtn_sel').removeClass('layui-hide'); //显示重传
				  }
			});
		}
	};
	//输出接口
    exports('upLoadFiles', obj);
});