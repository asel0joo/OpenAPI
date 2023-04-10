<%@page import="kr.co.wifiinfo.Dto.WifiKMDto"%>
<%@page import="kr.co.wifiinfo.Dto.PublicWifiDto"%>
<%@page import="java.util.PriorityQueue"%>
<%@page import="kr.co.wifiinfo.Services"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
<title>index</title>
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
<!-- 부트스트랩 실행 코드 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<style>
	td, th {
	  text-align: center;
	  border:solid 0.1px ;
	  padding : 10px;
	}
	table{
		width : 100%;
		border:solid 0.1px #000;
		border-collapse : collapse;
	}
	thead{
	    background-color: #ffc107;
	    color: #000000;
	}
</style>

<body>
	<h2>와이파이 정보 구하기</h2>
	<button type="button" class="btn btn-warning" onclick="location.href='/WifiInfo/index.jsp'">홈</button>
	<button type="button" class="btn btn-warning" onclick="location.href='/WifiInfo/history.jsp'">위치 히스토리 목록</button>
	<button type="button" class="btn btn-warning" onclick="location.href='/WifiInfo/load_wifi.jsp'">Open API 와이파이 정보 가져오기</button>
	
	<p>
		<div>
			<!-- 위도, 경도 값 dataProcess.jsp로 보내기 -->
			<form method="get" action="dataProcess.jsp">
			 LAT : 
			<input type="text" id="LAT" name="LAT" required minlength="0" maxlength="15" size="15", value = "0.0">
			, LNT :  
			<input type="text" id="LNT" name="LNT" required minlength="0" maxlength="15" size="15", value = "0.0">
			
			<input type="button" onclick="clickBtn()" value= "내 위치 가져오기">
			
			<input type="submit" value="근처 wifi 정보 보기" onclick = "btn()">			
			</form>	
		</div>	
	</p>
	
	<p>
	<table class="table">
	    <thead>
	        <tr>
	            <th>거리(Km)</th>  
	            <th>관리번호</th>
	            <th>자치구</th>
	            <th>와이파이명</th>
	            <th>도로명주소</th>
	         	<th>상세주소</th>
	         	<th>설치위치(층)</th>
	         	<th>설치유형</th>
	         	<th>설치기</th>
	         	<th>서비스구분</th>
	         	<th>망종류</th>
	         	<th>설치년도</th>
	         	<th>실내외구분</th>
	         	<th>WIFI접속환경</th>
	         	<th>X좌표</th>
	         	<th>Y좌표</th>
	         	<th>작업일자</th>	         	
	        </tr>
	   </thead>
	   
	    <%
	   	    String lat = request.getParameter("LAT") ;
	   	    		String lnt = request.getParameter("LNT") ;
	   	    		
	   	    		System.out.println(lat);
	   	    		System.out.println(lnt);
	   	    		
	   	    	   	if (lat == null && lnt == null){
	   	    %>
	   	<tbody>
	        <tr>
	            <td colspan="17">위치 정보를 입력한 후에 조회해 주세요</td>
	        </tr>
	    </tbody>
	   	<%
	   	} else {
	   	%>
	   		
	   		<tbody>
			<%
			Services service = new Services(); 
				PriorityQueue<WifiKMDto> wifiList = service.wifiList(lat, lnt);
				   		
					while (!wifiList.isEmpty()){
						WifiKMDto nearWifi = wifiList.poll();
						PublicWifiDto wifi = nearWifi.getItem();
			%>
					
				<tr>
		 		 	<td><%=nearWifi.getKilo()%> </td>
					<td> <%=wifi.getX_SWIFI_MGR_NO()%> </td>
					<td> <%=wifi.getX_SWIFI_WRDOFC() %> </td>
					<td> <%=wifi.getX_SWIFI_MAIN_NM() %> </td>
					<td><%=wifi.getX_SWIFI_ADRES1() %></td>
					<td><%=wifi.getX_SWIFI_ADRES2() %></td>
					<td><%=wifi.getX_SWIFI_INSTL_FLOOR() %></td>
					<td><%=wifi.getX_SWIFI_INSTL_TY() %></td>
					<td><%=wifi.getX_SWIFI_INSTL_MBY() %></td>
					<td><%=wifi.getX_SWIFI_SVC_SE() %></td>
					<td><%=wifi.getX_SWIFI_CMCWR() %></td>
					<td><%=wifi.getX_SWIFI_CNSTC_YEAR() %></td>
					<td><%=wifi.getX_SWIFI_INOUT_DOOR() %></td>
					<td><%=wifi.getX_SWIFI_REMARS3() %></td>
					<td><%=wifi.getLAT() %></td>
					<td><%=wifi.getLNT() %></td>
					<td><%=wifi.getWORK_DTTM() %></td>
				</tr>	
				
					<% 
				}
	   	}
					%>
	
	    </tbody>
	    
	</table>
	</p>
	
</body>
<script>
	//내 위치 가져오기
	function clickBtn(){
	    window.navigator.geolocation.getCurrentPosition( function(position){ //OK
	        var lat= position.coords.latitude;
	        var lnt= position.coords.longitude;
	        
	        LAT.value = lat;
	       	LNT.value = lnt;
	    }); 
	}
</script>
</html>