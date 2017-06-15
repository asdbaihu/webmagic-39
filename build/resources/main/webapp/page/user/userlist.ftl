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
</head>
<body>
    <div id="user_main_query">
        <form id="user_main_form" style="padding-top: 15px">
            <div class="row">
                <div class="col-lg-3">
                    <div class="form-group form-inline">
                        <label>编号:</label>
                        <input class="form-control" type="text" id="id" name="id" />
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="form-group form-inline">
                        <label>用户名</label>
                        <input class="form-control" type="text" id="userName" name="userName" />
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="form-group form-inline">
                        <label>姓名</label>
                        <input class="form-control" type="text" id="name" name="name" />
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="form-group form-inline">
                        <label>城市</label>
                        <input class="form-control" type="text" id="city" name="city" />
                    </div>
                </div>
            </div>
        </form>
        <div class="row">
            <div class="col-lg-12">
                <div class="form-group text-center">
                    <div class="btn btn-primary" id="user_main_search">重置</div>
                    <div class="btn btn-primary" id="user_main_reset">查询</div>
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
<script type="text/javascript">

    function user_list_search(){

    }

    $(function(){
        $('#user_main_query #user_main_reset').bind('click',function(){
            alert("qqq");
            $("#user_main_query #id").val("");
            $("#user_main_query #userName").val("");
            $("#user_main_query #name").val("");
            $("#user_main_query #city").val("");
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
