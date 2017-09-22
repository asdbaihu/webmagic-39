<#assign basePath=request.contextPath />
<script src="${basePath}/jquery-3.1.1.js"></script>
<script src="${basePath}/bootstrap/js/bootstrap.js"></script>
<link href="${basePath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
<link href="${basePath}/bootstrap/css/bootstrap-theme.css" rel="stylesheet"/>
<div class="row">
    <nav class="navbar navbar-inverse">
        <div class="container" id="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <#--<iframe class="collapse navbar-collapse" src="${basePath}/category/"></iframe>-->
        </div>
    </nav>
</div>


<script type="text/javascript">
    $(function(){
        $.post("${basePath}/category/",function(data){
            $("#container").append(data);
        });
    });

</script>
<script src="${basePath}/snow/snow.js"></script>
<style type="text/css">.snow-container{position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:100001;}</style>
<div class="snow-container"></div>