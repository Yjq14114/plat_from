<%--
  Created by IntelliJ IDEA.
  User: yangjq
  Date: 2016/9/14
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/demo.css">
    <script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
    <title>header</title>
</head>
<body>
</body>
</html>
