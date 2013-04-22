<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录-考勤系统</title>
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
	
	<style type="text/css">
		#main_wrap { width: 100%; text-align: center; }
		#main_wrap div {text-align: left;}
		#main_center_wrap {margin: 0 auto; width: 500px;}
		#main_center_wrap div {width: 100%; }
	</style>
	<script type="text/javascript">
		$(function(){
			var msg = '${msg }';
			var paramMsg = '${param.msg }';
			showMsg(msg == '' ? paramMsg : msg);
		});
	</script>
	<style type="text/css">
		ul { margin: 90px auto auto auto; width: 300px; text-align: left; }
		ul li {margin: 10px; height: 25px; line-height: 26px;}
	</style>
  </head>
  
  <body onload="document.loginForm.account.focus();">
    <div id="main_wrap">
    	<div id="main_center_wrap">
	    	<div style="height:100px; "></div>
	    	<div style="height:20px; ">考勤系统v1.0</div>
	    	<div style="border: 1px solid #ccc; height: 300px; text-align: center; background-color: #efefef;">
				 <form name="loginForm" action="servlet/LoginAndOutServlet" method="post" >
				 	<input type="hidden" name="opertype" value="0" />
				  	<ul>
				  		<li>用户名&nbsp;&nbsp;<input class="DefInputText" style="height: 24px; margin-left: 1px;" type="text" name="account" /></li>
				   		<li>密&nbsp;&nbsp;码&nbsp;&nbsp;<input class="DefInputText" style="height: 24px;" type="password" name="passwd" /></li>
				   		<li>
				   			<input type="submit" value="登录" style="width: 35%; margin-left: 5px; border: 1px solid #ccc;" />
				   			<input type="reset" value="重置" style="width: 35%; margin-left: 5px; border: 1px solid #999;" />
				   		</li>
				   	</ul>
				</form>
			</div>
		</div>
	</div>
  </body>
</html>
