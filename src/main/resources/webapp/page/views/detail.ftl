<html>
<#assign basePath=request.contextPath />
<head>
    <title>详情</title>
    <script type="text/javascript" src="${basePath}/jquery-3.1.1.js" language="javascript"></script>
</head>
<body>
<#include  "views/comm/top.ftl">
<div class="container">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title">${article.title}</h3>
        </div>
        <div id="content" class="panel-body">
            ${article.content}
        </div>
    </div>
</div>
</body>
</html>