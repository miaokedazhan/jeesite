<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>版本管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/mobile/dmApk/">版本列表</a></li>
		<li class="active"><a href="${ctx}/mobile/dmApk/form?id=${dmApk.id}">版本<shiro:hasPermission name="mobile:dmApk:edit">${not empty dmApk.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="mobile:dmApk:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dmApk" action="${ctx}/mobile/dmApk/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">包名：</label>
			<div class="controls">
				<form:input path="packagename" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台：</label>
			<div class="controls">
                <form:select id="os" name="os"
                             path="os" class="input-medium">
                    <form:options items="${fns:getDictList('MOBILE_SERSION')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本：</label>
			<div class="controls">
				<form:input path="version" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件：</label>
			<div class="controls">
				<form:hidden id="url" path="url" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:ckfinder input="url" type="files" uploadPath="/mobile/dmApk" selectMultiple="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="mobile:dmApk:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>