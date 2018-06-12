<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<form action="/login/uploadHeadPortrait?id=112233" method="post"enctype="multipart/form-data">

    本地目录1：<input  type="file" name="uploadFile">
	<br>
	name:<input type="text " name="name">
	<br>
	age:<input type="text " name="age">
<input type="submit" value="上传头像"/>

</form>
</body>
</html>