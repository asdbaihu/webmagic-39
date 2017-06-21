<script>
    $(function(){
        $("#userAddForm #city").select2({
            ajax: {
                url: "${basePath}/webmagic/city/citySelect2",
                dataType: 'json',
                delay: 250,
                cache: false,
                data: function (term) {
                    //alert(JSON.stringify(term))
                    return { search: term.term };
                },
                processResults: function (data) {
                    //alert(JSON.stringify(data))
                    return { results: data };
                }
            },
            formatNoMatches: function() {
                return "没有选项";
            },
            escapeMarkup: function (markup) { return markup; },
            minimumResultsForSearch:0,
            placeholder: "请输入",
            placeholderOption: 'first',
            allowClear: true
        });
    })

    function addUserInfoSubmit(parentIndex){
        var o=loadMsg();
        $.ajax({
            type:"post",
            dataType:"json",
            data:$("#userAddForm").serializeArray(),
            url:"/webmagic/user/save",
            success:function(data){
                closeMsg(o);
                if(data.status=='success'){
                    closeMsg(parentIndex);
                    layer.msg(data.message, {icon: 1});
                    $('#user_main_div #user_table').bootstrapTable("refresh");
                }else if(data.status=='error'){
                    layer.msg(data.message, {icon: 1});
                }
            },error:function(){
                closeMsg(o);
                layer.msg('系统错误请联系管理员!', {icon: 1});
            }
        })
    }
</script>
<div style="width:600px;padding:15px;">
    <form class="form-horizontal" id="userAddForm" autocomplete="off">
        <div class="form-group">
            <label class="col-xs-3 control-label">账户</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="账户" name="userName" id="userName" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">密码</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" onfocus="this.type='password'" autocomplete="off" placeholder="密码" name="password" id="password" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="姓名" name="name" id="name" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">城市</label>
            <div class="col-xs-5">
                <select class="form-control select2 select2-container select2-container--default select2-container--focus" name="city" id="city">
                    <option value=""></option>
                </select>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">证件类型</label>
            <div class="col-xs-5">
                <select class="form-control" name="cardType" id="cardType">
                    <option value=""></option>
                    <#list cardTypes as cardType>
                        <option value="${cardType}">${cardType.description}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">证件号</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="证件号" name="card" id="card" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">邮箱</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="邮箱" name="email" id="email" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">电话</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="电话" name="tel" id="tel" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">旅客类型</label>
            <div class="col-xs-5">
                <select class="form-control" name="customerType" id="customerType">
                    <option value=""></option>
                    <#list customerTypes as customerType>
                        <option value="${customerType}">${customerType.description}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="备注" name="remark" id="remark" value="">
            </div>
        </div>
    </form>
</div>
