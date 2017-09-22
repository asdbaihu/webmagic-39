<#assign basePath=request.contextPath />
<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav" id="nv1">
    <#if categoryList??>
        <#list categoryList as category>
            <li id="firstPage"><a href="${basePath}/article/column?id=${category.id}">${category.displayName}</a></li>
        </#list>
    </#if>
    </ul>
    <form class="navbar-form navbar-right">
        <div class="input-group">
            <input type="text" class="form-control" placeholder="搜索">
            <span class="input-group-btn">
                <button class="btn btn-default" type="button">Go!</button>
            </span>
        </div>
    </form>
</div>