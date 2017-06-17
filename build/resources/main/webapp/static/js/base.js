function loadMsg(){
    return layer.load(0, {shade: [0.5,'#000']});
}

function closeMsg(index){
    layer.close(index);
}

function openWindows(title,url,data,yesFunction,disableBtn){
    var loadIndex = loadMsg();
    $.ajax({
        type:"post",
        url:url,
        data:data,
        dataType:"html",
        traditional:true,
        success:function(result){
            var autoWidth=$(result).eq(2).outerWidth()+"px";
            if(autoWidth=="0px"){
                autoWidth=[];
            }
            if(disableBtn){
                layer.open({
                    type:1,
                    title:title,
                    content:result,
                    area: autoWidth,
                    shift:Math.floor(Math.random()*6 + 1),
                    success: function(layero, index){
                        var con=$(layero).find(".layui-layer-content");
                        con.css("height",con.height()+"px");
                    }
                });
            }else{
                layer.open({
                    type:1,
                    title:title,
                    content:result,
                    area: autoWidth,
                    shift:Math.floor(Math.random()*6 + 1),
                    btn: [' 提交', ' 取消'],
                    btn1: function(index, layero){
                        yesFunction(index,layero);
                    },
                    btn2: function(index){
                        layer.close(index);
                    },
                    success: function(layero, index){
                        $(".layui-layer-btn0").attr("class", "layui-layer-btn0 fa fa-save");
                        $(".layui-layer-btn1").attr("class", "layui-layer-btn1 fa fa-angle-left");
                        var con=$(layero).find(".layui-layer-content");
                        con.css("height",con.height()+"px");
                    }
                });
            }
        },error:function(){
            closeMsg(loadIndex);
            layer.msg('系统异常,请重试', {shade: [0.5,'#000']});
        }
    })
}