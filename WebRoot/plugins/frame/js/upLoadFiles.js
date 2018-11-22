;(function(){
	var uploadListIns,url= '';
    //定义构造函数
    var Upfile = function(ele,opt){
    	var upLoadFileList = $('#upLoadFileList');
        this.defaults = {
            elem:$(ele) //绑定元素
            //,url:'/upload.do?action=uploadFile&ajId=0'    //上传接口
            ,url:url
            ,method:'post'
        	,accept: 'file'
			,exts : 'doc|docx|wps|xls|xlsx|txt|pdf|pptx|ppt|zip|rar|dwg|eml|jpg|png|bmp|gif|jpeg|vsd|vsdx'
			,multiple: true
			,auto: false
			,number:5
			,bindAction : '#upListAction'
			,before : function(obj){
				layer.load();
			}
			,choose : function(obj){
				var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
				//读取本地文件
				obj.preview(function(index,file,result){
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
		    		var tr = $(['<tr id="upload-'+ index +'">',
		    			'<td>'+ file.name +'</td>',
		    			'<td>'+ (file.size/1014).toFixed(1) +'kb</td>',
		    			'<td>等待上传</td>',
		    			'<td>',
		    			'<button class="layui-btn layui-btn-xs reloadBtn reloadBtn_sel layui-hide">重传</button>',
		    			'<input class="uploadInpHid" name="notUpInp" type="hidden"/>',
		    			'<button class="layui-btn layui-btn-xs layui-btn-danger deleteBtn deleteBtn_sel">删除</button>',
		    			'</td>',
		    			'</tr>'].join(''));
		    		//单个重传
		    		tr.find('.reloadBtn_sel').on('click', function(){
			            obj.upload(index, file);
			        });
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
			        upLoadFileList.append(tr);
		        	$('#upListAction').show();
				});
			}
			,done : function(res, index, upload){
				layer.closeAll('loading');
				if(res.msg == 'success'){//上传成功
					var tr = upLoadFileList.find('tr#upload-'+ index),
						tds = tr.children();
					tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
					//tds.eq(3).html(''); //清空操作uploadInpHid
		         	tds.eq(3).find('.uploadInpHid').attr('name','hasUpSuccInp');
		         	tds.eq(3).find('.uploadInpHid').val(res.data[0].src);
		         	$('#upListAction').hide();
		         	return delete this.files[index]; //删除文件队列已经上传成功的文件
				}else if(res.msg == 'suffixError'){
					layer.msg('上传异常，请稍后重试',{icon:5,anim:6,time:1000});
				}
				this.error(index, upload);
			}
			,error : function(index, upload){
				layer.closeAll('loading'); //关闭loading
				var tr = upLoadFileList.find('tr#upload-'+ index),
					tds = tr.children();
				tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
		     	tds.eq(3).find('.reloadBtn_sel').removeClass('layui-hide'); //显示重传
			}
        }
        this.options = $.extend({}, this.defaults ,opt);
    };
     
    //定义方法
    Upfile.prototype = {
        init:function(){
            var _this = this;
            return layui.use('upload',function(){
                var upload = layui.upload;  
                //执行实例
                uploadListIns = upload.render(_this.options);
            });
        }
    };
     
    //在插件中使用对象
    $.fn.upfile = function(options){
    	url = options;
        var upfile = new Upfile(this,options);
        return upfile.init();
    };
})();