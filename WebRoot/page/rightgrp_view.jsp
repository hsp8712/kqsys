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
		
		function del(id, teamName) {
			if(window.confirm("确定删除\"" + teamName + "\"")) {
				window.location.href="<%=basePath%>servlet/RightgrpServlet?opertype=delete&id=" + id;
			}
		}
		
		function memView(id) {
			window.location.href="<%=basePath%>servlet/RightgrpServlet?opertype=mem_view&id=" + id;
		}
	</script>
  </head>
  
  <body>
  	<div class="main_wrap">
  		<a href="servlet/RightgrpServlet?opertype=add_view" class="easyui-linkbutton" style="margin:5px auto 0 5px;" data-options="iconCls:'icon-add'">新增</a>
	  	<form name="inputForm" action="servlet/RightgrpServlet?opertype=view" method="post">
	  	<H:table name="page" tableClass="tableClass" var="rightgrp" formName="inputForm" showPageBottom="true">
	  		<H:tablefield title="权限组名" >${rightgrp.rightgrp_name }</H:tablefield>
			<H:tablefield title="操作" cssClass="operate" >
				<a href="javascript: del('${rightgrp.id }','${rightgrp.rightgrp_name }')"  >删除</a>&nbsp;|
				<a href="javascript: memView('${rightgrp.id }')" >权限管理</a>
			</H:tablefield>
	  	</H:table>
	  	</form>
  	</div>
  </body>
</html>
