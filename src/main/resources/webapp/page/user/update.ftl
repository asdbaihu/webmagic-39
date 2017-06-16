<script>
    $(function(){
        $("#userUpdateForm #city").select2({
            ajax: {
                url: "${basePath}/city/citySelect2",
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
            escapeMarkup: function (markup) { return markup; },
            placeholder: "请输入",
            placeholderOption: 'first',
            allowClear: true
        });
    })

    function updateUserInfoSubmit(parentIndex){
        var o=loadMsg();
        $.ajax({
            type:"post",
            dataType:"json",
            data:$("#userUpdateForm").serializeArray(),
            url:"/user/update",
            success:function(data){
                closeMsg(o);
                if(data.status=="00"){
                    closeMsg(parentIndex);
                    onlyMsg('用户修改成功');
                    $('#user_main_div #user_table').bootstrapTable("refresh");
                }else{
                    alertMsg(data.status,0)
                }
            },error:function(){
                closeMsg(o);
                alertMsg("系统异常,请重试",0)
            }
        })
    }
</script>

<div style="width:600px;padding:15px;">
    <form class="form-horizontal" id="userUpdateForm" autocomplete="off">
        <input type="hidden" value="${user.id}" name="id">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="姓名" name="name" id="name" value="${user.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">城市</label>
            <div class="col-xs-5">
                <select class="form-control" name="city" id="city">
                    <option value="${user.city}">${user.cityName}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">证件类型</label>
            <div class="col-xs-5">
                <select class="form-control" name="cardType" id="cardType">
                    <option value=""></option>
                    [#list cardTypes as cardType]
                        <option value="${cardType}" [#if cardType==user.cardType]selected[/#if]>${cardType.description}</option>
                    [/#list]
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">证件号</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="证件号" name="card" id="card" value="${user.card}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">邮箱</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="邮箱" name="email" id="email" value="${user.email}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">电话</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="电话" name="tel" id="tel" value="${user.tel}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">旅客类型</label>
            <select class="form-control" name="customerType" id="customerType">
                <option value=""></option>
            [#list customerTypes as customerType]
                <option value="${customerType}" [#if customerType==user.customerType]selected[/#if]>${customerType.description}</option>
            [/#list]
            </select>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" autocomplete="off" placeholder="备注" name="remark" id="remark" value="${user.remark}">
            </div>
        </div>
    </form>
</div>