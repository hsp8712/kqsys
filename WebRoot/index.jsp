<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页-考勤系统</title>
    
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
	<style type="text/css">
		#menu_ul {width: 100%; }
		#menu_ul li {line-height: 40px; height: 45px; text-align: center;}
		#menu_ul li button {border: 1px solid #bbccff; width: 90%; margin: 0 auto; line-height: 25px; cursor: pointer; }
	</style>
  </head>
  
  <body>
  	<div id="main_wrap">
  		<div class="easyui-layout" style="width:900px;height:100%; text-align:left; margin: 0 auto; ">
			<div data-options="region:'north'" style="height:90px;overflow:hidden;padding:10px">
				<div style="width: 100%; height: 15px; text-align: right;">
					您好！${CURRENT_USER.empno }-${CURRENT_USER.name }&nbsp;|&nbsp;<a href="servlet/LoginAndOutServlet?opertype=1">退出</a>
				</div>
				<h2>考勤系统</h2>
			</div>
			<div data-options="region:'south',split:true" style="height:50px;background:#fafafa;"></div>
			<div data-options="region:'west',split:true" title="菜单" style="width:150px;">
				<div style="width: 100%; height:30px;"></div>
				<ul id="menu_ul" >
					<c:forEach items="${CURRENT_USER_RIGHTS }" var="right"  >
					<li><button onclick="document.getElementById('main_frame').src='${right.rightLink }'">${right.rightName }</button></li>
					</c:forEach>
				</ul>
			</div>
			<div data-options="region:'center',title:'主功能窗口'" style="background:#fafafa;overflow:hidden">
				<iframe id="main_frame" width="100%" height="100%" src="checkin.jsp" frameborder="0"  />
			</div>
		</div>

  	</div>
  </body>
</html>
