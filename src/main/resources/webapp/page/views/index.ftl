<!DOCTYPE html>
<#assign basePath=request.contextPath />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>我的博客</title>
    <link href="${basePath}/mycss.css" rel="stylesheet"/>
</head>
<body>
<#include  "views/comm/top.ftl">
<#if articles??>
    <#list articles as article>
    <div class="row">
        <div class="container">
            <div class="jumbotron">
                <h3>${article.title}</h3>
                <span class="summary">${article.summary?html}</span><br><br>
                <p><a class="btn btn-primary btn-lg" href="${basePath}/article/detail/${article.articleId?c}.html" role="button">阅读全文</a></p>
            </div>
        </div>
    </div>
    </#list>
</#if>
</body>
</html>
