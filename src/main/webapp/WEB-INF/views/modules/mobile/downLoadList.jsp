<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
    <meta name="author" content="">

    <link rel="stylesheet" href="${ctxStatic}/downLoad/iconfont.css">
    <link rel="stylesheet" href="${ctxStatic}/downLoad/global.css">
    <link rel="stylesheet" href="${ctxStatic}/downLoad/download/flexslider.css">
    <link href="${ctxStatic}/downLoad/bootstrap.css" rel="stylesheet">

    <title>woohoo-智能云笔记本</title>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body class="ui-mobile-viewport ui-overlay-a">


<div class="container zhirenxiu-box">


    <div class="row mt20">
        <div class="col-sm-12">
            <div class="xiu-content box-shadow">
                <table id="contentTable" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>版本号</th>
                        <th>文件</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${dmApks}" var="dmApk">
                        <tr>
                            <td>
                                    ${dmApk.version}
                            </td>
                            <td>
                                <a href="${dmApk.url}">下载</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <p align="center">

                    深圳市我好数码科技有限公司

                </p>
            </div>
        </div>
    </div>
</div>


</div>
</body>
</html>