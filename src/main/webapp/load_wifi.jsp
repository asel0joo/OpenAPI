<%@page import="kr.co.wifiinfo.Services"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
<title>Load_wifi</title>
</head>
<style>
	h1, div{
	text-align : center;
	}
</style>
<body>
	<%
		Services service = new Services();
	%>
	
	<h1><%=service.insertAll() %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
	
	<div><a href = "http://localhost:8080/WifiInfo/index.jsp"> 홈 으로 가기</a></div>
</body>
</html>