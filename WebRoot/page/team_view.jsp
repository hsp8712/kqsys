<%@ page language="java" contentType="text/html;charset=utf-8" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="H" uri="/HPageTable"%>
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
		.operate{ width: 150px; }
	</style>
	<script type="text/javascript">
		$(function(){
			showMsg('${msg }');
			$(".tableClass").attr("cellspacing", "1");
		});
		
		function edit(id) {
			window.location.href="<%=basePath%>servlet/TeamServlet?opertype=mod_view&id=" + id;
		}
		
		function del(id, teamName) {
			if(window.confirm("确定删除\"" + teamName + "\"")) {
				window.location.href="<%=basePath%>servlet/TeamServlet?opertype=delete&id=" + id;
			}
		}
		
		function memView(id) {
			window.location.href="<%=basePath%>servlet/TeamServlet?opertype=mem_view&id=" + id;
		}
	</script>
  </head>
  
  <body>
  	<div class="main_wrap">
  		<button onclick="location.href='servlet/TeamServlet?opertype=add_view'" type="button" class="DefButton">新增</button>
	  	<form name="inputForm" action="servlet/TeamServlet?opertype=view" method="post">
	  	<H:table name="page" tableClass="tableClass" var="team" formName="inputForm" showPageBottom="true">
	  		<H:tablefield title="组名" >${team.teamName }</H:tablefield>
			<H:tablefield title="组管理员" >${team.manager.name }</H:tablefield>
			<H:tablefield title="描述" >${team.description }</H:tablefield>
			<H:tablefield title="操作" cssClass="operate" >
				<a href="javascript: edit('${team.id }')" >编辑</a>&nbsp;|
				<a href="javascript: del('${team.id }','${team.teamName }')" >删除</a>&nbsp;|
				<a href="javascript: memView('${team.id }')" >成员管理</a>
			</H:tablefield>
	  	</H:table>
	  	</form>
  	</div>
  </body>
</html>
