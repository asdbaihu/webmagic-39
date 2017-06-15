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
    <div id="wrapper">
        <nav class="navbar navbar-default top-navbar" role="navigation">
            <div class="navbar-header">
                <a class="navbar-brand" href="javascript:void(0)">我的火车票</a>
            </div>
            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-envelope fa-fw"></i>邮件
                    </a>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-tasks fa-fw"></i>任务
                    </a>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-user fa-fw"></i>退出
                    </a>
                </li>
            </ul>
        </nav>
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li>
                        <a class="active-menu" href="javascript:void(0)"><i class="fa fa-dashboard"></i>系统管理</a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="/webmagic/user/toList.html" target="page-wrapper">用户管理</a>
                            </li>
                            <li>
                                <a href="#" target="page-wrapper">国家管理</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#" target="page-wrapper"><i class="fa fa-desktop"></i>车票查询</a>
                    </li>
                    <li>
                        <a href="#" target="page-wrapper"><i class="fa fa-qrcode"></i>任务管理</a>
                    </li>
                    <li>
                        <a href="#" target="page-wrapper"><i class="fa fa-table"></i>数据统计</a>
                    </li>
                    <li>
                        <a href="#" target="page-wrapper"><i class="fa fa-edit"></i>财务管理</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div id="page-wrapper" style="height:1000px;">
            <iframe name="page-wrapper" id="iframe" marginwidth=10 marginheight=10 frameborder=no width="100%" scrolling="no"  src="/webmagic/user/toList.html"></iframe>
        </div>
    </div>
    <script src="${basePath}/js/jquery-1.9.1.min.js"></script>
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/bootstrap-table.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#wrapper #iframe").load(function () {
                var mainheight = $(this).contents().find("body").height();
                $(this).height(mainheight);
            });
        });

    </script>
</body>
</html>