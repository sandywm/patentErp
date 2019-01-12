layui.define(["layer","form","jquery"],function(exports){
    var form = layui.form,
    $ = layui.jquery,
    Address = function(){};

    Address.prototype.provinces = function() {
        //加载省数据
        var proHtml = '',that = this;
        $.get("/plugins/frame/json/address.json", function (data) {
            for (var i = 0; i < data.length; i++) {
            	proHtml += '<option value="' + data[i].code + '">' + data[i].name + '</option>';
            }
            //初始化省数据
            $("select[name=cpyProvSel]").append(proHtml);
            form.render();
            form.on('select(province)', function (proData) {
            	$("#provVal").val("");
                var value = proData.value;
                if (value > 0) {
                    //that.citys(data[$(this).index() - 1].childs);
                	that.citys(data[$(this).index()].childs);
                } else {
                    $("select[name=cpyCitySel]").attr("disabled", "disabled");
                }
                var options=$("select[name=cpyProvSel] option:selected");
                $("#provInp").val(options.text());//不管有没有设置 选择select的时候，将当前option值给隐藏变量
                $("#cityInp").val("");
            });
            if($("#provInp").val() != "" && $("#provInp").val() != undefined){ //表示已经保存了省、市到数据库
            	//根据当前的省的值拿取其对应市区的值
            	$("select[name=cpyProvSel] option:first").html('请选择省');
            	var hasDataCityHtml = '<option value="">请选择市</option>';
            	console.log($('#cityInp').val())
            	for (var i = 0; i < data.length; i++) {
            		if($("#provInp").val() == data[i].name){
            			$("select[name=cpyProvSel] option[value='"+ data[i].code +"']").attr("selected","selected");
            			for(var j=0;j<data[i].childs.length;j++){
            				if($('#cityInp').val() == data[i].childs[j].name){
            					hasDataCityHtml += '<option value="' + data[i].childs[j].code + '" selected>' + data[i].childs[j].name + '</option>';
            				}else{
            					hasDataCityHtml += '<option value="' + data[i].childs[j].code + '">' + data[i].childs[j].name + '</option>';
            				}
            			}
            		}
            	}
            	$("select[name=cpyCitySel]").html(hasDataCityHtml);
            	
            	form.render('select');
            	form.on('select(city)',function(cityData){
            		var options=$("select[name=cpyCitySel] option:selected");
                    $("#cityInp").val(options.text());
            	});
            }
        });
    };

    //加载市数据
    Address.prototype.citys = function(citys) {
        var cityHtml = '<option value="">请选择市</option>',that = this;
        for (var i = 0; i < citys.length; i++) {
        	cityHtml += '<option value="' + citys[i].code + '">' + citys[i].name + '</option>';
        }
        $("select[name=cpyCitySel]").html(cityHtml).removeAttr("disabled");
        form.render();
        form.on('select(city)', function (cityData) {
            var value = cityData.value;
            if (value > 0) {
                that.areas(citys[$(this).index() - 1].childs);
            } else {
                $("select[name=area]").attr("disabled", "disabled");
            }
            var options=$("select[name=cpyCitySel] option:selected");
            //console.log(options.text() + "--通过省加载市")
            $("#cityInp").val(options.text());
        });
    };

    //加载县/区数据
    Address.prototype.areas = function(areas) {
        var areaHtml = '<option value="">请选择县/区</option>';
        for (var i = 0; i < areas.length; i++) {
            areaHtml += '<option value="' + areas[i].code + '">' + areas[i].name + '</option>';
        }
        $("select[name=area]").html(areaHtml).removeAttr("disabled");
        form.render();
    };

    var address = new Address();
    exports("address",function(){
        address.provinces();
    });
});