<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改密碼-考勤系统</title>
    
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
	<script type="text/javascript">
		$(function(){
			showMsg('${msg }');
		});
		
		function valPassword(formObj) {
			var password = formObj.password;
			var newPassword = formObj.newPassword;
			var newPassword1 = formObj.newPassword1;
			
			if(password.value == null || password.value == ''
				|| newPassword1.value == null || newPassword1.value == ''
				|| newPassword.value == null || newPassword.value == '') {
				alert("<原密码>、<新密码>、<确认新密码>均不能为空！");
				return false;
			}
			
			var reg = /^[0-9a-zA-Z]{6,12}$/;
			if(!reg.test(password.value)) {
				alert("<原密码>格式错误！");
				password.focus();
				return false;
			}
			if(!reg.test(newPassword.value)) {
				alert("<新密码>格式错误！");
				newPassword.focus();
				return false;
			}
			if(!reg.test(newPassword1.value)) {
				alert("<确认新密码>格式错误！");
				newPassword1.focus();
				return false;
			}
			if(newPassword.value != newPassword1.value) {
				alert("<确认新密码>与<新密码>不匹配！");
				newPassword1.focus();
				return false;
			}
			
			return true;
			
		}
	</script>
  </head>
  
  <body>
  	<form action="servlet/UserPasswordServlet?opertype=mod_password_save" method="post" onsubmit="return valPassword(this);" >
  		<table class="tableClass" cellspacing="1" >
  			<tr>
  				<th class="title">原密码</th>
  				<td><input type="password" name="password" class="DefInputText" />(6-12位数字或字母)</td>
  			</tr>
  			<tr>
  				<th class="title">新密码</th>
  				<td><input type="password" name="newPassword" class="DefInputText" />(6-12位数字或字母)</td>
  			</tr>
  			<tr>
  				<th class="title">确认新密码</th>
  				<td><input type="password" name="newPassword1" class="DefInputText" />(6-12位数字或字母)</td>
  			</tr>
  			<tr>
  				<td colspan="2"><button class="DefButton" type="submit" >保存</button></td>
  			</tr>
  		</table>
  	</form>
  </body>
</html>
