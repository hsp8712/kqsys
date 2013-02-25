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
	</script>
  </head>
  
  <body>
  	<a href="servlet/DailyRecordServlet?opertype=export" class="easyui-linkbutton" style="margin:5px auto 0 5px;" data-options="iconCls:'icon-add'">导出</a>
  	<form name="inputForm" action="servlet/DailyRecordServlet?opertype=query" method="post"></form>
  	<H:table name="page" tableClass="tableClass" var="dailyRecord" formName="inputForm" showPageBottom="true">
  		<H:tablefield title="日期" >${dailyRecord.record_date }</H:tablefield>
  		<H:tablefield title="工号" >${dailyRecord.empno }</H:tablefield>
  		<H:tablefield title="姓名" >${dailyRecord.name }</H:tablefield>
		<H:tablefield title="首次打卡时间" >${dailyRecord.first_time }</H:tablefield>
		<H:tablefield title="最后打卡时间" >${dailyRecord.last_time }</H:tablefield>
		<H:tablefield title="加班开始时间" >${dailyRecord.over_time }</H:tablefield>
		<H:tablefield title="加班时长" >${dailyRecord.over_time_hour }</H:tablefield>
  	</H:table>
  </body>
</html>
