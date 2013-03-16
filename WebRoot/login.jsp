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
			showMsg('${msg }');
		});
	</script>
  </head>
  
  <body>
    <div id="main_wrap">
    	<div id="main_center_wrap">
	    	<div style="height:150px;">
	    	</div>
	    	<div style="border: 1px solid #00aaee; height: 300px; text-align: center; ">
	    		<div style="margin: 100 auto 60 auto; width: 240px; text-align: left;">
				    <form action="servlet/LoginAndOutServlet" method="post" >
				    	<input type="hidden" name="opertype" value="0" />
				    	<table>
				    		<tr>
				    			<td>用户名</td>
				    			<td><input class="DefInputCss" type="text" name="account" /></td>
				    		</tr>
				    		<tr>
				    			<td>密&nbsp;&nbsp;码</td>
				    			<td><input class="DefInputCss" type="password" name="passwd" /></td>
				    		</tr>
				    		<tr>
				    			<td colspan="2"><input type="submit" value="登录" /></td>
				    		</tr>
				    	</table>
					</form>
				</div>
			</div>
		</div>
	</div>
  </body>
</html>
