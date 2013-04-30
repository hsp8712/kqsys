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
	
	<link rel="stylesheet" type="text/css" href="jquery-easyui/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="jquery-easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="jquery-easyui/jquery.easyui.min.js"></script>
	<style type="text/css">
		#menu_ul {width: 100%; margin: 1px;}
		#menu_ul li {line-height: 30px; height: 30px; text-align: center; }
		#menu_ul li button {border: 1px solid #444; background-color: #444; color: #fff; width: 100%; margin: 0 0 1px 0; line-height: 25px; cursor: pointer; }
		#menu_ul li button:HOVER { background-color: #777; }
	</style>
	<script type="text/javascript">
		
		// Turn to the menu window
		function turnTo(link, name){
			document.getElementById('main_frame').src=link;
		}
	</script>
  </head>
  
  <body>
  	<div id="main_wrap">
  		<div class="easyui-layout" style="width:1000px;height:100%; text-align:left; margin: 0 auto; ">
			<div data-options="region:'north',split:true" style="width:100%; height:90px;overflow:hidden; background-color: #444; color: #0df; ">
				<div style="width: 100%; height: 30px; text-align: right; margin-top: 10px; color: #fff;">
					您好！${CURRENT_USER.empno }-${CURRENT_USER.name }&nbsp;|&nbsp;<a style="color: #fff;" href="servlet/LoginAndOutServlet?opertype=1">退出</a>&nbsp;&nbsp;
				</div>
				<h2 style="font-family: '微软雅黑'; margin: 0 0 10px 20px; ">考勤系统</h2>
			</div>
			<div data-options="region:'south',split:true" style=" width:100%; height:50px; background-color: #444; overflow:hidden;"></div>
			<div data-options="region:'west',split:true" style="width:150px; overflow:hidden; background-color: #ededed;">
				<ul id="menu_ul" >
					<c:forEach items="${CURRENT_USER_RIGHTS }" var="right"  >
					<li><button onclick="turnTo('${right.rightLink }','${right.rightName }');">${right.rightName }</button></li>
					</c:forEach>
				</ul>
			</div>
			<div id="center" data-options="region:'center',split:true" style="background:#fafafa;overflow:hidden; ">
				<iframe id="main_frame" width="100%" height="100%" src="page/checkin.jsp" frameborder="0" scrolling="no" />
			</div>
		</div>
		
		
		

  	</div>
  </body>
</html>
