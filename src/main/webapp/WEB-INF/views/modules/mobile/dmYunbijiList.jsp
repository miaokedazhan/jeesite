<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>云笔记数据管理</title>
    <meta name="decorator" content="default"/>
    <style>
        .max {
            width: 100%;
            height: auto;
        }

        .min {
            width: 100px;
            height: auto;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function () {
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/mobile/dmYunbiji/">云笔记数据列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="dmYunbiji" action="${ctx}/mobile/dmYunbiji/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>昵称</th>
        <th>用户</th>
        <th>名称</th>
        <th>大小</th>
        <th>文件</th>
        <th>创建时间</th>
        <shiro:hasPermission name="mobile:dmYunbiji:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="dmYunbiji">
        <tr>
            <td>
                    ${dmYunbiji.name.nickname}
            </td>
            <td>
                    ${dmYunbiji.name.phoneNumber}
            </td>
            <td>
                    ${dmYunbiji.bijiName}
            </td>
            <td>
                    ${dmYunbiji.bijiSize}
            </td>
            <td>
                <img src="${dmYunbiji.bijiImage}"
                     style="width: 50px;height: 50px;padding: 5px">
            </td>
            <td>
                <fmt:formatDate value="${dmYunbiji.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <shiro:hasPermission name="mobile:dmYunbiji:edit">
                <td>
                    <a href="${ctx}/mobile/dmYunbiji/delete?id=${dmYunbiji.id}"
                       onclick="return confirmx('确认要删除该云笔记数据吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>