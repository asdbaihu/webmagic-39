<html>
<#assign basePath=request.contextPath />
<head>
    <title>请登录</title>
    <script src="${basePath}/jquery-3.1.1.js"></script>
    <script src="${basePath}/bootstrap/js/bootstrap.js"></script>
    <link href="${basePath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link href="${basePath}/bootstrap/css/bootstrap-theme.css" rel="stylesheet"/>
    <link href="${basePath}/signin.css" rel="stylesheet"/>
</head>
<body>
<div class="container">

    <form class="form-signin" action="" method="post">
        <h2 class="form-signin-heading">请登录</h2>
        <div></div>
        <label for="inputEmail" class="sr-only">请输入用户名</label>
        <input type="text" id="inputEmail" name="username" value="123" class="form-control" placeholder="用户名" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" value="123" name="password" class="form-control" placeholder="密码" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>

</div>
</body>
</html>
