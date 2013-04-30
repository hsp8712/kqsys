<%@ page language="java" contentType="text/html;charset=utf-8" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>组管理-考勤系统</title>
    
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
	<script type="text/javascript" src="scripts/page.js"></script>
	<style type="text/css">
		.setMem li {float: left;}
		select {width: 130px; }
	</style>
	<script type="text/javascript">
		$(function(){
			showMsg('${msg }');
			$(".tableClass").attr("cellspacing", "1");
		});
		
		function addMem(){
			var userId = document.getElementsByName("nomemUsers")[0].value;
			window.location.href="<%=basePath%>servlet/TeamServlet?opertype=mem_add&id=${team.id }&userId=" + userId;
		}
		
		function removeMem(){
			var userId = document.getElementsByName("memUsers")[0].value;
			window.location.href="<%=basePath%>servlet/TeamServlet?opertype=mem_remove&id=${team.id }&userId=" + userId;
		}
	</script>
  </head>
  
  <body>
  	<div class="main_wrap">
  		<input type="hidden" name="teamId" value="${team.id }" />
		<table class="tableClass" cellspacing="1" >
  			<tr>
  				<th class="title">组</th>
  				<td>${team.teamName}</td>
  			</tr>
  			<tr>
  				<th class="title">
  					成员分配
  				</th>
  				<td>
	  				<ul class="setMem">
	  					<li>
	  						<br/>未非配用户：<br/><br/>
	  						<select multiple="multiple" name="nomemUsers" size="10" >
	  							<c:forEach var="user" items="${nomemUsers }">
	  							<option value="${user.id }" >
	  								${user.empno }-${user.name }
	  							</option>
	  							</c:forEach>
	  						</select>
	  					</li>
	  					<li>
	  						<br/><br/><br/><br/><br/>
	  						<a href="javascript: addMem()" 
								class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
							<br/><br/>
							<a href="javascript: removeMem()" 
								class="easyui-linkbutton" data-options="iconCls:'icon-remove'">移除</a>
	  					</li>
	  					<li>
	  						<br/>组成员用户：<br/><br/>
	  						<select multiple="multiple" name="memUsers" size="10">
	  							<c:forEach var="user" items="${memUsers }">
	  							<option value="${user.id }" >
	  								${user.empno }-${user.name }
	  							</option>
	  							</c:forEach>
	  						</select>
	  					</li>
	  				</ul>
  				</td>
  			</tr>
  		</table>	  	
  	</div>
  </body>
</html>
