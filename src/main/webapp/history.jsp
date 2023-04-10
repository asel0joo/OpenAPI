<%@page import="kr.co.wifiinfo.Dto.HistoryDto"%>
<%@page import="kr.co.wifiinfo.Services"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	
	<title>위치 히스토리 정보</title>
	<!-- jQuery CDN -->
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<!-- 부트스트랩 실행 코드 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	<h1>위치 히스토리 정보</h1>
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

	<p>
		<div>
			<button type="button" class="btn btn-warning" onclick="location.href='/WifiInfo/index.jsp'">홈</button>
			<button type="button" class="btn btn-warning" onclick="location.href='/WifiInfo/history.jsp'">위치 히스토리 목록</button>
			<button type="button" class="btn btn-warning" onclick="location.href='/WifiInfo/load_wifi.jsp'">Open API 와이파이 정보 가져오기</button>
		</div>
	</P>

	<%
		Services service = new Services();
		List<HistoryDto> itemList = service.historyList();
	%>
	<p>
	<table class="table">
	    <thead>
	        <tr>
	            <th >ID</th>  
	            <th >X좌표</th>
	            <th >Y좌표</th>
	            <th >조회일자</th>
	            <th >비고</th>
	        </tr>
	    </thead>
	    <tbody>
				<%
			        Services Service = new Services();
			        if (request.getParameter("id") != null) {
			            Integer delId = Integer.valueOf(request.getParameter("id"));
			            Service.deleteHistory(delId);
			        }
			        List<HistoryDto> historys = Service.historyList();
			        for (HistoryDto dto : historys) {
			    %>
					<tr>
						<td> <%=dto.getId()%> </td>	
						<td> <%=dto.getLat()%> </td>
						<td> <%=dto.getLnt()%> </td>
						<td> <%=dto.getDate() %> </td>
						<td>
							<!-- 위치 히스토리 목록 삭제하기 -->  
							<button type="button" onclick="location.href='history.jsp?id=<%=dto.getId()%>'">삭제</button>
						</td>
					</tr>
				<%
				}
				%>
		</tbody>
	</table>
	</p>
</body>
</html>