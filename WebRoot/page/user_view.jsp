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
		.operate{ width: 80px; }
	</style>
	<script type="text/javascript">
		$(function(){
			showMsg('${msg }');
			$(".tableClass").attr("cellspacing", "1");
		});
		
		function edit(id) {
			window.location.href="<%=basePath%>servlet/UserServlet?opertype=mod_view&id=" + id;
		}
		
		function del(id, userName) {
			if(window.confirm("确定删除\"" + userName + "\"")) {
				window.location.href="<%=basePath%>servlet/UserServlet?opertype=delete&id=" + id;
			}
		}
	</script>
  </head>
  
  <body>
  	<div class="main_wrap">
  		<a href="servlet/UserServlet?opertype=add_view" class="easyui-linkbutton" style="margin:5px auto 0 5px;" data-options="iconCls:'icon-add'">新增</a>
	  	<form name="inputForm" action="servlet/UserServlet?opertype=view" method="post">
	  	<H:table name="page" tableClass="tableClass" var="user" formName="inputForm" showPageBottom="true">
	  		<H:tablefield title="工号" >${user.empno }</H:tablefield>
			<H:tablefield title="姓名" >${user.name }</H:tablefield>
			<H:tablefield title="账号" >${user.account }</H:tablefield>
			<H:tablefield title="所属组" >${user.team_name }</H:tablefield>
			<H:tablefield title="操作" cssClass="operate" >
				<a href="javascript: edit('${user.id }')" >编辑</a>&nbsp;|
				<a href="javascript: del('${user.id }','${user.empno }-${user.name }')" >删除</a>
			</H:tablefield>
	  	</H:table>
	  	</form>
  	</div>
  </body>
</html>
