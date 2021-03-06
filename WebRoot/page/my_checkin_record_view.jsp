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
	<link rel="stylesheet" type="text/css" href="datepicker/pikaday.css">
	
	<link rel="stylesheet" type="text/css" href="jquery-easyui/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui/themes/icon.css">
	
	<script type="text/javascript" src="datepicker/moment.min.js" ></script>
	
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
			document.inputForm.action="servlet/MyCheckinRecordServlet?opertype=export_my";
			document.inputForm.submit();
			document.inputForm.action="servlet/MyCheckinRecordServlet?opertype=query_my";
		}
		
	</script>
  </head>
  
  <body>
  	<form name="inputForm" action="servlet/MyCheckinRecordServlet?opertype=query_my" method="post">
  		<ul class="HeadToolUl">
  			<li>日期范围</li>
  			<li>
  				<input size="10" type="text" readonly="readonly" id="startDate" class="DatepickerText" name="startDate" value="${param.startDate }">
  			</li>
  			<li>至</li>
  			<li>
  				<input size="10" type="text" readonly="readonly" id="endDate" class="DatepickerText" name="endDate" value="${param.endDate }">
  			</li>
  			<li><button class="DefButton" type="button" onclick="formSub()" >查询</button></li>
  			<li><button class="DefButton" type="button" onclick="formExport()" >导出</button></li>
  		</ul>
  	</form>
  	<H:table name="page" tableClass="tableClass" var="record" formName="inputForm" showPageBottom="true">
  		<H:tablefield title="姓名" >${record.name }</H:tablefield>
  		<H:tablefield title="打卡IP" >${record.check_ip }</H:tablefield>
  		<H:tablefield title="日期时间" >${record.check_time }</H:tablefield>
  	</H:table>
  
  <script type="text/javascript" src="datepicker/pikaday.js" ></script>
  <script type="text/javascript">
  	
  	var startDateObj = document.getElementById('startDate');
  	var endDateObj = document.getElementById('endDate');
	var startDatePicker = new Pikaday({
	    field: startDateObj,
	    format:'YYYY-MM-DD',
	    defaultDate:new Date(),
	    setDefaultDate: (startDateObj.value==null||startDateObj.value=='')?true:false,
	    firstDay: 0,
	    yearRange: [2000,2020]
	});
	var endDatePicker = new Pikaday({
	    field: endDateObj,
	    format:'YYYY-MM-DD',
	    defaultDate:new Date(),
	    setDefaultDate:(endDateObj.value==null||endDateObj.value=='')?true:false,
	    firstDay: 0,
	    yearRange: [2000,2020]
	});
  </script>
  </body>
</html>
