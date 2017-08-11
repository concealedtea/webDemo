<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/resources/image/favicon.ico" rel="bookmark" type="image/x-icon" />
        <link href="/resources/image/favicon.ico" rel="icon" type="image/x-icon" />
        <link href="/resources/image/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <title>登录</title>
        <link href="/resources/css/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="/resources/css/fontawesome-4.3/css/font-awesome.css"  rel="stylesheet" type="text/css"  />
        <link href="/resources/css/pagemodule.css" rel="stylesheet" type="text/css" />
        <link href="/resources/css/pagelogin.css" rel="stylesheet" type="text/css" />
    </head>

</head>
<body class="reg-login">
<div class="reg-container">
    <div class="reg-block">
        <div class="reg-block-header">
            <h2>Welcome</h2>
        </div>
        <form  id="form_Login">
            <div class="input-group margin-bottom-20 parsley-err-absolute">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <input type="text" class="form-control" id="txt_username"  placeholder="账户名" required="">
            </div>
            <div class="input-group margin-bottom-20 parsley-err-absolute">
                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <input  type="password" class="form-control" id="txt_password" placeholder="密码" required="">
            </div>
        </form>
        <hr>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <button class="btn btn-block btn-lg btn-default waves-effect waves-light" id="btn_Login" type="button">登录</button>
            </div>
        </div>
    </div>
    <!--End Reg Block-->
</div>
<%--<div class="backstretch">
    <img src="/resources/image/banner.jpg">
</div>--%>
<script src="/resources/js/jquery-1.11.3.js"></script>
<script src="/resources/js/bootstrap.js"></script>
<script src="/resources/js/waves/waves.js"></script>
<script src="/resources/js/jquery.parsley/parsley.js"></script>
<script src="/resources/js/jquery.notify/notify.js"></script>
<script src="/resources/js/jquery.cl.extend/ext.common.js"></script>
<script src="/resources/js/jquery.cl.extend/ext.ajax.js"></script>
<script src="/resources/js/jquery.cl.extend/ext.parsley.js"></script>
<script src="/resources/js/jquery.cl.extend/ext.notify.js"></script>
<script type="text/javascript">
    $(function () {
        var $tempLogo = {
            init: function () {
                $("#form_Login").ExtParsley();
            },
            checkParsleyFrom: function () {
                return  $("#form_Login").ExtParsley("validate");
            },
            save:function(){
                if(this.checkParsleyFrom()){
                    var username= $.trim($("#txt_username").val());
                    var password= $.trim($("#txt_password").val());
                    var tempjson={userName:username,password:password};
                    $.ExtAjax.initPost({
                        disableid:"#btn_Login",
                        loadinfo:{place:"body"},
                        isloading:true,
                        url: '/entry.shtml',
                        data: tempjson,
                        contentType: "application/x-www-form-urlencoded",
                        successcallback: function (res) {
                            if (res.success) {
                                window.location.href = res.data;
                            } else {
                                $.ExtNotify.error(res.msg)
                            }
                        }
                    });
                }
            }
        };
        $tempLogo.init();
        $(document).on("keydown",function (event) {
            if(event.keyCode == 13) $tempLogo.save();
        });
        $("#btn_Login").on("click",function () {
            $tempLogo.save();
        });
        if (window.history && window.history.pushState) {
            $(window).on('popstate', function () {
                var hashLocation = location.hash;
                var hashSplit = hashLocation.split("#!/");
                var hashName = hashSplit[1];
                if (hashName !== '') {
                    var hash = window.location.hash;
                    if (hash === '') {
                        window.history.pushState('logout', null, 'logout.shtml');
                    }
                }
            });
            window.history.pushState('logout', null, 'logout.shtml');
        }
    });
</script>
</body>
</html>