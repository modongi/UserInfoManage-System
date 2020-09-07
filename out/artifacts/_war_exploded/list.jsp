<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>

    <style type="text/css">
        td, th {
            text-align: center;
        }

        .R-btn {
            float: right;
            margin: 5px;
        }

        .L-from {
            float: left;

        }
    </style>

    <script type="text/javascript">
        function deleteUser(id) {
            //弹出提示框，安全提示用户
            if (confirm("您确定要删除吗？")) {
                //访问路径
                location.href = "${pageContext.request.contextPath}/DelUserServlet?id=" + id;
            }
        }

        //页面加载完成后
        window.onload = function () {
            //给删除选中按钮添加单击事件
            document.getElementById("delSelected").onclick = function () {
                //弹出提示框，安全提示用户
                if (confirm("您确定要删除选中的条目吗？")) {
                    var flag = false;
                    //判断是否有选中条目，防止空异常
                    var Cbs = document.getElementsByName("uid");
                    for (var i = 0; i < Cbs.length; i++) {
                        if (Cbs[i].checked) {
                            //有一个条目选中了
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        //表单提交方法
                        document.getElementById("form").submit();
                    }
                }
            };

            //实现选中表头的复选框则所有的复选框全选
            //1.获取表头的复选框Cb
            document.getElementById("firstCb").onclick = function () {
                //2.获取下边列表中所有的Cb
                var Cbs = document.getElementsByName("uid");
                //3.遍历
                for (var i = 0; i < Cbs.length; i++) {
                    //4.设置这些Cbs[i]的checked状态 == firstCb.checked
                    Cbs[i].checked = this.checked;
                }
            }
        }
    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>

    <%--内联表单--%>
    <div class="L-from">
        <form class="form-inline" action="${pageContext.request.contextPath}/FindUserByPageServlet" method="post">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" class="form-control" name="name" value="${condition.name[0]}" id="exampleInputName2">
            </div>
            <div class="form-group">
                <label for="exampleInputName3">籍贯</label>
                <input type="text" class="form-control" name="address" value="${condition.address[0]}" id="exampleInputName3">
            </div>
            <div class="form-group">
                <label for="exampleInputEmail2">邮箱</label>
                <input type="text" class="form-control" name="email" value="${condition.email[0]}" id="exampleInputEmail2">
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>

    <div class="R-btn">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
        <a class="btn btn-primary" href="javascript:void(0)" id="delSelected">删除选中</a>
    </div>

    <%--
        实现多项选中：最简单的方式，浏览器表单form默认支持复选框选中提交的方式。
        所以只需要把复选框嵌套在一个表单里面，表单提交的是复选框的value属性值.
    --%>
    <form action="${pageContext.request.contextPath}/DelSelectedServlet" method="post" id="form">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">

                <th><input type="checkbox" id="firstCb"></th>
                <th>编号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>籍贯</th>
                <th>QQ</th>
                <th>邮箱</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${pb.list}" var="user" varStatus="s">
                <tr>
                    <td><input type="checkbox" name="uid" value="${user.id}"></td>
                    <td>${s.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <td>
                        <a class="btn btn-default btn-sm"
                           href="${pageContext.request.contextPath}/FindUserServlet?id=${user.id}">修改</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id});">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>

    <%--分页工具条--%>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <%--代码嵌套--%>
                <c:if test="${pb.currentPage == 1}">
                    <li class="disabled">
                </c:if>

                <c:if test="${pb.currentPage != 1}">
                    <li>
                </c:if>
                    <%--优化：上一页、下一页，如果当前页码数处于边界页码，让上一页或下一页处于禁用状态--%>
                    <a href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${pb.currentPage - 1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <%--分页工具条，页码总数--%>
                <c:forEach begin="1" end="${pb.totalPage}" var="i">

                    <%--优化：页码点击后显示激活状态--%>
                    <c:if test="${pb.currentPage == i}">
                        <%--<li class="active"><a href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${i}&rows=5">${i}</a></li>--%>
                        <%--复杂条件查询优化：分页查询访问资源加上查询条件，原因换页后查询条件丢失。--%>
                        <li class="active"><a href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>
                </c:forEach>

                <%--代码嵌套--%>
                <c:if test="${pb.currentPage == pb.totalPage}">
                    <li class="disabled">
                </c:if>

                <c:if test="${pb.currentPage != pb.totalPage}">
                    <li>
                </c:if>
                    <a href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${pb.currentPage + 1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>

                <span style="font-size: 25px; margin-left: 5px">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>

            </ul>
        </nav>
    </div>

</div>
</body>
</html>
