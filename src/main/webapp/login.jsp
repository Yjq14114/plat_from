<%--
  Created by IntelliJ IDEA.
  User: yangjq
  Date: 2016/9/14
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header.jsp"%>
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        div#login {position: absolute;width:400px;height:200px;left:50%;top:50%;
            margin-left:-200px;margin-top:-200px;border:1px solid rgba(0, 0, 255, 0)
        }
    </style>
    <script type="text/javascript">
        function login() {
            var username = $("#username").val();
            var password = $("#password").val();
            if (username == '' || password == '') {
                alert("请填写账号密码");
            } else {
                $.post("<%=basePath%>/login/validate", {data: '{account:"' + username + '",password:"' + password + '"}'}, function (data) {
                    var code = data.code;
                    if(code==1){
                        window.location.href="jsp/home.jsp";
                    }else if(code==2){
                        alert("密码错误");
                    }else if(code==3){
                        alert("账号不存在");
                    }
                });
            }
        }

    </script>
</head>
<body>
<div id = "login">
    <div style="text-align: center">
        <h1 style="color: #1f637b">后台管理系统</h1>
    </div>
    <div style="margin:20px 0;"></div>
    <div class="easyui-panel" style="width:400px;padding:50px 60px">
        <div style="margin-bottom:20px">
            <input id="username" class="easyui-textbox" prompt="Username" iconWidth="28" style="width:100%;height:34px;padding:10px;">
        </div>
        <div style="margin-bottom:20px">
            <input id="password" class="easyui-passwordbox" prompt="Password" iconWidth="28" style="width:100%;height:34px;padding:10px">
        </div>
        <div style="text-align: right">
            <a href="#" class="easyui-linkbutton" style="width:100%;height:34px;padding:10px" onclick=login()>登录</a>
        </div>
        <div style="text-align: right">
            <label><input type="checkbox" name="rember_me"checked value="true">记住我</label>
        </div>
    </div>
</div>
</body>
</html>
