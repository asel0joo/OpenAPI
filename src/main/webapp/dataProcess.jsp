<%@page import="kr.co.wifiinfo.Services"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	
<title> dataProcess </title>
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
</head>
<body>
	
 	<!-- 위치 history에 값 저장 -->
	<%  
		String lat = request.getParameter("LAT");
		String lnt = request.getParameter("LNT");
		Services service = new Services();
		
		System.out.println(lat +"  " + lnt);
		
		service.insertHistory(lat, lnt); 
		
	%>

	<!-- 이전 페이지로 돌아가기 -->
	<script type="text/javascript">

		var lat = getParameter("LAT");
		var lnt = getParameter("LNT");
		var url = 'index.jsp?LAT=' + lat + '&LNT= ' + lnt;
		location.replace(url);
	
		
		function getParameter(name) {
		    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		        results = regex.exec(location.search);
		    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
		}
	</script>
</body>
</html>