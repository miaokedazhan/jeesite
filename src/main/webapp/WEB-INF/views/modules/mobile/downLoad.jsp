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
                <div>


                    <div style="width: 50%; height: 200px;float:left;padding:70px;border-right:1px solid #000"
                         align="center"><img style="width: 20px; height: 30px;" src="${ctxStatic}/downLoad/a.png"></div>
                    <div style="width: 50%; height: 200px;float:right;padding:70px;" align="center"><img
                            style="width: 20px; height: 30px;" src="${ctxStatic}/downLoad/i.png"></div>
                </div>
                <div>
                    <div style="width: 50%; height: 100px;float:left;margin-top:20px;" align="center"><a id="Android"
                                                                                                         href="${dmApk.url}"
                                                                                                         url="${dmApk.url}">安卓下载</a>
                    </div>
                    <div style="width: 50%; height: 100px;float:right;margin-top:20px;" align="center"><a
                            href="https://itunes.apple.com/cn/app/%E6%99%BA%E8%83%BD%E4%BA%91%E7%AC%94%E8%AE%B0%E6%9C%AC/id1318529507?mt=8">苹果下载</a>
                    </div>

                </div>

                <hr>
                <p style="color:red">
                    注意：请点击微信右上角,选择在浏览器中打开下载
                </p>
                <p><img style="width: 100%; height: auto;display:none;" src="${ctxStatic}/downLoad/005.jpg"></p>
                <p align="center">

                    深圳市我好数码科技有限公司

                </p>
                <p align="center">

                    <a href="${pageContext.request.contextPath}/version/getApkList">获取更多版本</a>

                </p>
            </div>
        </div>
    </div>
</div>


</div>
</body>
</html>