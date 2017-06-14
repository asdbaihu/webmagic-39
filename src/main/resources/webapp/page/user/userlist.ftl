<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
        $(function () {
            $("#dataShow").bootstrapTable({
                url: "TradHandler.ashx?request=getTradList",
                sortName: "rkey",//排序列
                striped: true,//條紋行
                sidePagination: "server",//服务器分页
                //showRefresh: true,//刷新功能
                //search: true,//搜索功能
                clickToSelect: true,//选择行即选择checkbox
                singleSelect: true,//仅允许单选
                //searchOnEnterKey: true,//ENTER键搜索
                pagination: true,//启用分页
                escape: true,//过滤危险字符
                queryParams: getParams,//携带参数
                pageCount: 10,//每页行数
                pageIndex: 0,//其实页
                method: "get",//请求格式
                //toolbar: "#toolBar",
                onPageChange: function (number, size) {
                    currPageIndex = number;
                    currLimit = size
                },
                onLoadSuccess: function ()
                {
                    $("#searchBtn").button('reset');
                }
            });
    </script>

</head>
<body>



</body>
</html>
