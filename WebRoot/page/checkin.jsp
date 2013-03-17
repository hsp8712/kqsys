<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	
	<style type="text/css">
		.title {width: 120px;}
	</style>
	
	<script type="text/javascript" src="jquery-easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="scripts/common.js"></script>
	
	<script type="text/javascript">
		$(function(){
			showMsg('${msg }');
		});
		
		function clock() {
			millisec += 1000;
			myDate.setTime(millisec);
			$("#now").text(myDate.toLocaleString());
		}
		
		var millisec;
		var myDate = new Date();
		$(function(){
			$.ajax({
				url:'servlet/CheckinServlet?opertype=server_time&random=' + Math.random(), 
				success:function(data) {
							millisec = new Number(data);
						},
				async: false
			});
			
			var int=self.setInterval("clock()",1000);
		});
	</script>
	<style type="text/css">
		.tableClass ul li {float: left; vertical-align: middle; margin-left:5px; height: 20px; line-height: 20px;}
	</style>
  </head>
  
  <body onload="document.form0.validateCode.focus();">
  	<form action="servlet/CheckinServlet?opertype=checkin" name="form0" method="post" >
  		<table class="tableClass" cellspacing="1" >
  			<tr>
  				<td class="title">姓名</td>
  				<td>${CURRENT_USER.name }</td>
  			</tr>
  			<tr>
  				<td class="title">所属组</td>
  				<td>${CURRENT_USER.team.teamName }</td>
  			</tr>
  			<tr>
  				<td class="title">当前时间</td>
  				<td id="now"></td>
  			</tr>
  			<tr>
  				<td class="title">验证码</td>
  				<td>
  					<ul>
  						<li><input type="text" name="validateCode" style="height: 20px;" class="DefInputText" size="5" /></li>
  						<li><img src="image.jsp" /></li>
  					</ul>
  				</td>
  			</tr>
  			<tr>
  				<td colspan="2"><input type="submit" value="打卡" /></td>
  			</tr>
  		</table>
  	</form>
  </body>
</html>
