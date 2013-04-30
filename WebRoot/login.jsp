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
		body { height: 100%; width: 100% }
		#MainDiv {
			width: 500px;
			height: 300px;
			position: absolute;
			top: 50%; left: 50%;
			margin: -150px 0 0 -250px;
			border: 1px solid #ccc;
			background-color: #fefefe;
			z-index: 100;
			
			background-image: url("img/bg-login01.png");
			background-repeat: repeat-x;
		}
		
		#MainDiv ul { position: absolute; top: 50%; left: 50%; margin: -80px 0 0 -120px; height: 160px; width: 240px; }
		#MainDiv ul li {height: 53px; line-height: 53px;}
		#MainDiv ul li .DefButton { margin-left: 10px; width: 80px; }
	</style>
	<script type="text/javascript">
		$(function(){
			var msg = '${msg }';
			var paramMsg = '${param.msg }';
			showMsg(msg == '' ? paramMsg : msg);
			
		});
	</script>
  </head>
  
  <body onload="document.loginForm.account.focus();">
  	 <form name="loginForm" action="servlet/LoginAndOutServlet" method="post" >
  	 <input type="hidden" name="opertype" value="0" />
	 <div id="MainDiv">
		<ul>
			<li>用户名&nbsp;&nbsp;<input class="DefInputText" style="height: 24px; margin-left: 1px;" type="text" name="account" /></li>
			<li>密&nbsp;&nbsp;码&nbsp;&nbsp;<input class="DefInputText" style="height: 24px;" type="password" name="passwd" /></li>
			<li>
				<button style="margin-left: 40px; " class="DefButton" type="submit" >登录</button>
				<button class="DefButton" type="reset" >重置</button>
			</li>
		</ul>
	</div>
	</form>
  </body>
</html>
