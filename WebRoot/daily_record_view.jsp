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
	
	<link rel="stylesheet" type="text/css" href="jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="jquery-easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="scripts/common.js"></script>
	<script type="text/javascript">
		$(function(){
			showMsg('${msg }');
			$(".tableClass").attr("cellspacing", "1");
		});
	</script>
  </head>
  
  <body>
  	<H:table name="page" tableClass="tableClass" var="dailyRecord" formName="inputForm" showPageBottom="true">
  		<H:tablefield title="日期" ><fmt:formatDate value="${dailyRecord.date }"  pattern="yyyy-MM-dd" /></H:tablefield>
		<H:tablefield title="首次打卡时间" ><fmt:formatDate value="${dailyRecord.firstTime }"  pattern="yyyy-MM-dd HH:mm:ss" /></H:tablefield>
		<H:tablefield title="最后打卡时间" ><fmt:formatDate value="${dailyRecord.lastTime }"  pattern="yyyy-MM-dd HH:mm:ss" /></H:tablefield>
		<H:tablefield title="加班开始时间" ><fmt:formatDate value="${dailyRecord.overTime }"  pattern="yyyy-MM-dd HH:mm:ss" /></H:tablefield>
		<H:tablefield title="加班时长" >${dailyRecord.overTimeHour }</H:tablefield>
  	</H:table>
  </body>
</html>
