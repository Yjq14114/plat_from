<%--
  Created by IntelliJ IDEA.
  User: yangjq
  Date: 2016/10/14
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header.jsp"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        $(function () {
            initTreeData();
        });
        function initTreeData(){
            $('#tree').tree({
                url:'demo01.ashx',
                checkbox:true,
                onClick:function(node){
                    alert(node.text);
                },
                onContextMenu: function(e, node){
                    e.preventDefault();
                    $('#tree').tree('select', node.target);
                    $('#mm').menu('show',{
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            });
        }

    </script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
        <h3>后台管理系统</h3>
    </div>
    <div data-options="region:'west',split:true,title:'West'" style="width:180px;padding:10px;">
            <ul id="tree">

            </ul>
            <div id="mm" class="easyui-menu"  style=" width:120px;">

            </div>
    </div>
    <div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>
    <div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
    <div data-options="region:'center',title:'Center'"></div>
</body>
</html>
