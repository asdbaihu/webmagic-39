<html>
<head>
    <title>${displayName}</title>
</head>
<body>
<#include  "views/comm/top.ftl">
<div class="container">
    <#if articles??>
    <#list articles as article>
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">${article.title}</h3>
            </div>
            <div class="panel-body">
                <h4>${article.summary}</h4>
                <p><a class="btn btn-primary btn-lg" href="${basePath}/article/detail?id=${article.id}" role="button">阅读全文</a></p>
            </div>
        </div>
    </#list>
    </#if>
</div>
</body>
</html>
