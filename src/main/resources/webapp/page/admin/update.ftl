<html>
<#assign basePath=request.contextPath />
<head>
    <title>写博客</title>
    <script src="${basePath}/jquery-3.1.1.js"></script>
    <script src="${basePath}/editormd/editormd.js"></script>
    <script src="${basePath}/bootstrap/js/bootstrap.js"></script>
    <link href="${basePath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link href="${basePath}/bootstrap/css/bootstrap-theme.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${basePath}/editormd/css/editormd.css"/>
    <link rel="shortcut icon" href="${basePath}/editormd/images/favicon.ico" type="image/x-icon"/>
</head>
<body>
<div class="container">
    <form method="post" action="${basePath}/article/update.html">
        <br>
        <br>
        <div class="row">
            <div class="col-lg-6">
                <div class="input-group">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false" id="categoryBtn">
                            <#list categories as category>
                            <#if article.categoryId==category.id>
                                ${category.displayName}
                            </#if>
                            </#list>

                        </button>
                        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false" id="categoryBtn">${article.category}
                        </button>
                        <ul class="dropdown-menu">
                            <#list categories as category>
                                <li>
                                    <a onclick="selectCategory('${category.id}','${category.displayName}');">${category.displayName}</a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                    <input name="categoryId" id="cateoryInput" type="hidden" value="${article.categoryId}">
                    <input name="id" type="hidden" value="${article.id}">
                    <input type="text" class="form-control" placeholder="标题" name="title" value="${article.title}">
                </div>
            </div>
        </div>
</div>
<br>
<div id="layout" style="width: 100%;height: 100%">
    <div id="test-editormd">
            <textarea style="display: none" name="content">${article.content}</textarea>
    </div>
</div>

<script type="text/javascript">
    var testEditor;
    $(function () {
        testEditor = editormd("test-editormd", {
            width: "100%",
            height: 640,
            syncScrolling: "single",
            path: "${basePath}/editormd/lib/",
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: ""
        });
    });
</script>
<div class="row">
    <div class="col-md-6 col-md-offset-6">
        <p>
            <input type="submit" class="btn btn-primary btn-lg" role="button" value="发表">
        </p>
    </div>
    </form>
</div>
<script>
    function selectCategory(name, displayName) {
        $("#categoryBtn").html(displayName);
        $("#cateoryInput").val(name);
    }
</script>
</body>
</html>
