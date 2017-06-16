<!DOCTYPE html>
<#assign basePath=request.contextPath />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>小希希小哈哈</title>
    <link href="${basePath}/css/bootstrap.css" rel="stylesheet" />
    <link href="${basePath}/css/font-awesome.css" rel="stylesheet" />
    <link href="${basePath}/css/custom-styles.css" rel="stylesheet" />
    <link href="${basePath}/css/bootstrap-table.css" rel="stylesheet" />
    <link href="${basePath}/css/select2.min.css" rel="stylesheet" />
    <link href="${basePath}/css/index.css" rel="stylesheet" />
    <link href="${basePath}/js/layer/skin/default/layer.css" rel="stylesheet" />
</head>
<body>
    <div id="user_main_query" class="animated fadeInRight jmenu-contant">
        <form id="user_main_form" style="padding-top: 15px">
            <table class="table table-bordered table-clear-margin">
                <tr style="background-color:#f3f3f4;">
                    <td class="col-xs-3">
                        <span class="list-form-left">编号:</span>
                        <input class="form-control list-form-right" type="text" id="id" name="id" />
                    </td>
                    <td class="col-xs-3">
                        <span class="list-form-left">用户名</span>
                        <input class="form-control list-form-right" type="text" id="userName" name="userName" />
                    </td>
                    <td class="col-xs-3">
                        <span class="list-form-left">姓名</span>
                        <input class="form-control list-form-right" type="text" id="name" name="name" />
                    </td>
                    <td class="col-xs-3 form-inline">
                        <span class="list-form-left">城市</span>
                        <select style="width:290px" class="select2 select2-container select2-container--default select2-container--focus" id="city" name="city">
                            <option value=""></option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
        <div class="row" style="padding: 15px 15px">
            <div class="col-lg-12">
                <div class="form-group text-center">
                    <div class="btn btn-info" id="user_main_reset">重置</div>
                    <div class="btn btn-success" id="user_main_search">查询</div>
                    <div class="btn btn-primary" id="user_main_add">添加</div>
                    <div class="btn btn-warning" id="user_main_update">修改</div>
                    <div class="btn btn-danger" id="user_main_del">删除</div>
                </div>
            </div>
        </div>
    </div>
    <div id="user_main_div">
        <table id="user_table"></table>
    </div>
</body>
<script src="${basePath}/js/jquery-1.9.1.min.js"></script>
<script src="${basePath}/js/bootstrap.min.js"></script>
<script src="${basePath}/js/bootstrap-table.js"></script>
<script src="${basePath}/js/bootstrap-table-zh-CN.js"></script>
<script src="${basePath}/js/select2.min.js"></script>
<script src="${basePath}/js/layer/layer.js"></script>
<script type="text/javascript">


    $(function(){
        $('#user_main_query #user_main_search').bind('click',function(){
            var params ={};
            $.each($("#user_main_query #user_main_form").serializeArray(),function(){
                params[this.name]=this.value;
            })
            $('#user_main_div #user_table').bootstrapTable('refresh',{"url":"${basePath}/user/list"});
        });

        $('#user_main_query #user_main_reset').bind('click',function(){
            $("#user_main_query #id").val("");
            $("#user_main_query #userName").val("");
            $("#user_main_query #name").val("");
            $("#user_main_query #city").val("");
        });

        $("#user_main_query #user_main_add").bind('click',function(){

        });

        $("#user_main_query #user_main_del").bind('click',function(){
            var selects = $('#user_main_div #user_table').bootstrapTable('getSelections');
            if(selects.length>0){
                layer.confirm('纳尼？',{icon: 1, title:'提示'}, function(index){

                });
            }else{
                layer.msg('不开心。。', {icon: 5});
            }

        });

        $("#user_main_query #city").select2({
            ajax: {
                url: "${basePath}/city/citySelect2",
                dataType: 'json',
                delay: 250,
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

        $('#user_main_div #user_table').bootstrapTable({
            url: "${basePath}/user/list",
            dataType:"json",
            queryParams: function (params) {
                $.each($("#user_main_query #user_main_form").serializeArray(),function(){
                    params[this.name]=this.value;
                })
                return params;
            },
            showColumns: true,
            pagination: true,
            sidePagination: 'server',
            pageNumber: 1,
            pageList: [ 10, 50, 100, 500],
            search: false,
            showRefresh:false,
            columns: [
                {
                    field: 'state',
                    checkbox: true,
                    width:30
                },
                {
                    field: 'id',
                    title: '规则ID',
                    visible: true,
                    sortable: true
                },
                {
                    field: 'name',
                    title: '名称',
                    visible: true
                },
                {
                    field: 'userName',
                    title: '用户名',
                    visible: true
                },
                {
                    field: 'sex',
                    title: '性别',
                    visible: true
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    visible: true,
                    sortable: true,
                    formatter: function(value, row, index){
                        var date = new Date(value);
                        var Y = date.getFullYear() + '-';
                        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                        var D = date.getDate() + ' ';
                        var h = date.getHours() + ':';
                        var m = date.getMinutes() + ':';
                        var s = date.getSeconds();
                        return Y+M+D+h+m+s;
                    }
                },
                {
                    field: 'cityName',
                    title: '城市',
                    visible: true
                },
                {
                    field: 'card',
                    title: '证件',
                    visible: true,
                    formatter:function(value,row,index){
                        return row.cardTypeName+":"+value;
                    }
                },
                {
                    field: 'email',
                    title: '邮箱',
                    visible: true
                },
                {
                    field: 'tel',
                    title: '电话',
                    visible: true
                },
                {
                    field: 'customerTypeName',
                    title: '游客类型',
                    visible: true
                }
            ]
        })
    })
</script>
</html>
