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
    
    <title>打卡-考勤系统</title>
    
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
  	<form action="servlet/TeamServlet" method="post" >
  		<table class="tableClass" cellspacing="1" >
  			<tr>
  				<td>组名</td>
  				<td><input type="text" name="teamName" /></td>
  			</tr>
  			<tr>
  				<td>描述</td>
  				<td>
  					<textarea name="description" rows="4" cols="30"></textarea>
  				</td>
  			</tr>
  			<tr>
  				<td>管理员</td>
  				<td>
  					<select>
  						<option value="" >-- 请选择 --</option>
  						<c:forEach var="user" items="${noTeamUsers }">
  							<option value="${user.id }" >${user.name }</option>
  						</c:forEach>
  					</select>
  				</td>
  			</tr>
  			<tr>
  				<td colspan="2"><input type="submit" value="保存" /></td>
  			</tr>
  		</table>
  	</form>
  </body>
</html>
