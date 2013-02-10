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
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
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
  	<form action="servlet/UserServlet?opertype=save" method="post" >
  		<input type="hidden" name="id" value="${user.id }" />
  		<table class="tableClass" cellspacing="1" >
  			<tr>
  				<td>操作</td>
  				<td>${empty user.id ? '新增' : '修改'}用户</td>
  			</tr>
  			<tr>
  				<td>姓名</td>
  				<td><input type="text" name="name" value="${user.name }" /></td>
  			</tr>
  			<tr>
  				<td>工号</td>
  				<td>
  					<input type="text" name="empno" value="${user.empno }" />
  				</td>
  			</tr>
  			<tr>
  				<td>账号</td>
  				<td>
  					<input type="text" name="account" value="${user.account }" />
  				</td>
  			</tr>
  			<tr>
  				<td>所属组</td>
  				<td>
  					<c:choose>
  						<c:when test="${empty user.id }">
  							<select name="team">
	  						<option value="" >-- 请选择 --</option>
	  						<c:forEach var="team" items="${teams }">
	  							<option value="${team.id }" ${team.id eq user.team_id ? 'selected' : '' } >
	  								${team.team_name }
	  							</option>
	  						</c:forEach>
	  						</select>
  						</c:when>
  						<c:otherwise>
  							<input name="team" type="hidden" value="${user.team_id }" />
  							${user.team_name }
  						</c:otherwise>
  					</c:choose>
  				</td>
  			</tr>
  			<tr>
  				<td>所属组</td>
  				<td>
  					<select name="rightgrp">
	  				<c:forEach var="rightgrp" items="${rightgrps }">
	  					<option value="${rightgrp.id }" ${rightgrp.id eq user.rightgrp_id ? 'selected' : '' } >
	  						${rightgrp.rightgrp_name }
	  					</option>
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
