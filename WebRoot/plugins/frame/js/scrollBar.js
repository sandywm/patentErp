/**
 * @Description: 基础配置
 * @author: hlf
 */
//自定义模块
layui.define(function(exports){
    var obj = {
		getId : function(id){
			return document.getElementById(id);
		},
		//模拟滚动条
		scrollBar : function(objContainer,objContent,objScrollBox,objScrollBar,wheelNum){
			var containerParent = this.getId(objContainer); //内容的父级容器
			var content = this.getId(objContent);  //内容部分
			var scrollBox = this.getId(objScrollBox); //滚动条的父级容器
			var scrollBar = this.getId(objScrollBar);//滚动条
			
			var scale = 0;
			var scrollBarHeight=0;
			var maxTop=0;
			var listMaxTop=0;
			var t=0;
			//滚动条比例
			scale = containerParent.clientHeight / content.scrollHeight;
			if(scale > 1){
				scale = 1;
			}
			scrollBarHeight = scale*scrollBox.scrollHeight;
			maxTop = scrollBox.scrollHeight-scrollBarHeight;
			listMaxTop = containerParent.clientHeight-content.scrollHeight;
			scrollBar.style.height=scrollBarHeight+'px';
			if(scale==1){
				scrollBox.style.display="none";
			}
			//fnScroll(); 函数是控制滚动条的高度变化的函数，具体代码
			function fnScroll(){
					
					if( t < 0 ) t = 0;
					if(t > maxTop) t = maxTop;
				
					var scale = t / maxTop;
					
					scrollBar.style.top = t + 'px';
					content.style.top=-scale*(content.offsetHeight-containerParent.clientHeight)+'px';	
			};
			//滚动条拖动
			scrollBar.onmousedown = function(ev){
					
					var ev = ev || event;
					
					var disY = ev.clientY - this.offsetTop;
					
					document.onmousemove = function(ev){
						
						var ev = ev || event;
						
						t = ev.clientY - disY;
									
						fnScroll();	
						
					};
					
					document.onmouseup = function(){
						document.onmouseup = document.onmousemove = null;
					};
					
					return false;	
			};	
			//mouseScroll   //因为 mousewheel 事件 和 DOMMouseScroll 事件下记录鼠标滚轮信息的事件对象不一样，上下滚动的正负值也不一样
			function mouseScroll(ev){
				
				var ev = ev || event;
				var fx = ev.wheelDelta || ev.detail; //变量记录滚轮信息
				var bDown = true;
					
				if( ev.detail ){
					bDown = fx > 0 ? true : false;
				}else{
					bDown = fx > 0 ? false : true;
				}
				
				if( bDown ){ // bDown 这个变量来记录是上还是下  如果向上滚动，bDown 的值为 false, 向下 则为 true;

					t += wheelNum;
				}else{
					t -= wheelNum;
				}
				
				fnScroll();
				
				if( ev.preventDefault ){
					ev.preventDefault();
				}
				
				return false;
			};
			
			//鼠标滚轮效果。鼠标滚轮在 ie / chrome / firefox 下是有兼容问题的。ie 和 chrome 下用的是 mousewheel 事件，而ff下用的 是 DOMMouseScroll 并且 DOMMouseScroll 是只能通过 addEventListener 函数来监听实现的
			containerParent.onmousewheel = mouseScroll;
			
			if(containerParent.addEventListener){
				containerParent.addEventListener('DOMMouseScroll',mouseScroll,false);
			}
		}
    };
    //输出接口
    exports('scrollBar', obj);
});
