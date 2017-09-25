<html>
<#assign basePath=request.contextPath />
<head>
    <title>管理员</title>
    <script src="${basePath}/jquery-3.1.1.js"></script>
    <script src="${basePath}/bootstrap/js/bootstrap.js"></script>
    <link href="${basePath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link href="${basePath}/bootstrap/css/bootstrap-theme.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">文章列表</div>
        <div class="panel-body">
            <p><a class="btn btn-primary btn-sm" href="${basePath}/article/toAdd.html" role="button">写博客</a></p>
        </div>

        <table class="table">
            <tr class="info">
                <td>编号</td>
                <td>标题</td>
                <td>摘要</td>
                <td style="text-align: center" colspan="2">操作</td>
            </tr>
            <#if page??>
            <#if page.rows??>
                <#list page.rows as article>
                    <tr>
                        <td>${article.articleId}</td>
                        <td>${article.title}</td>
                        <td>${article.summary?html}</td>
                        <td><a href="${basePath}/article/toUpdate/${article.articleId}.html">修改</a></td>
                        <td><a href="${basePath}/article/delete/${article.articleId}.html">删除</a></td>
                    </tr>
                </#list>
            </#if>
            </#if>
        </table>
    </div>
</div>
</body>
</html>
