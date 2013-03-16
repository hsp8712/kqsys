<%@ page language="java" contentType="text/html;charset=utf-8" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="H" uri="/HPageTable"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<script type="text/javascript" src="scripts/page.js"></script>
	
	<script type="text/javascript">
		$(function(){
			showMsg('${msg }');
			$(".tableClass").attr("cellspacing", "1");
		});
		
		function formSub() {
			document.inputForm.submit();
		}
		function formExport() {
			document.inputForm.action="servlet/CheckinRecordServlet?opertype=export";
			document.inputForm.submit();
			document.inputForm.action="servlet/CheckinRecordServlet?opertype=query";
		}
	</script>
  </head>
  
  <body>
  	<form name="inputForm" action="servlet/CheckinRecordServlet?opertype=query" method="post">
  		<span style="margin:5px auto 2px 5px; height: 24px;">年月<input style="height: 24px;" size="10" type="text" name="month" value="${param.month }">（格式：YYYYMM）</span>
  		<span style="margin:5px auto 2px 5px; height: 24px;">姓名<input style="height: 24px;" size="10" type="text" name="name" value="${param.name }"></span>
  		<a href="javascript: formSub();" class="easyui-linkbutton" style="margin:5px auto 0 5px;" data-options="iconCls:'icon-search'">查询</a>
	  	<a href="javascript: formExport();" class="easyui-linkbutton" style="margin:5px auto 0 5px;" data-options="iconCls:'icon-add'">导出</a>
  	</form>
  	<H:table name="page" tableClass="tableClass" var="record" formName="inputForm" showPageBottom="true">
  		<H:tablefield title="姓名" >${record.name }</H:tablefield>
  		<H:tablefield title="日期时间" >${record.check_time }</H:tablefield>
  	</H:table>
  </body>
</html>