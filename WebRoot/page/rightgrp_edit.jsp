<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>权限组-考勤系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="styles/reset.css">
	<link rel="stylesheet" type="text/css" href="styles/common.css">
	
	<link rel="stylesheet" type="text/css" href="jquery-easyui/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="jquery-easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="scripts/common.js"></script>
	<script type="text/javascript">
		$(function(){
			showMsg('${msg }');
		});
		
	</script>
  </head>
  
  <body>
  	<form action="servlet/RightgrpServlet?opertype=save" method="post" >
  		<input type="hidden" name="id" value="${rightgrp.id }" />
  		<table class="tableClass" cellspacing="1" >
  			<tr>
  				<th class="title">操作</th>
  				<td>${empty rightgrp.id ? '新增' : '修改'}</td>
  			</tr>
  			<tr>
  				<th class="title">权限组名</th>
  				<td><input type="text" class="DefInputText" name="rightgrpName" value="${rightgrp.rightgrp_name }" /></td>
  			</tr>
  			<tr>
  				<td colspan="2"><input type="submit" class="DefButton" value="保存" /></td>
  			</tr>
  		</table>
  	</form>
  </body>
</html>
