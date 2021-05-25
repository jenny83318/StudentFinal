<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel='stylesheet' href="<c:url value='/css/styles.css' />"  type="text/css" />
<title>Insert title here</title>
</head>
<body>

<img src="<c:url value='/image/kitty.jpg' />" >

	<h1 style="text-align: center">MVC Exercise</h1>
	<hr>
	<table border="1" style="margin: 0px auto;">
		<tr height="52" bgcolor="lightblue" align="center">
			<td width="350"><p align="left" /> <a href='welcome'>Hello
					Spring MVC</a><BR></td>
			<td width="350"><p align="left" /> <a href='products'>查詢產品資料</a><BR>
			</td>
		</tr>

		<tr height="52" bgcolor="lightblue" align="center">
			<td width="350"><p align="left" /> 
				<a href='products'>查詢所有產品資料</a><BR>
			</td>
			<td width="350"><p align="left" /> 
				<a href='update/stock'>更新多筆產品的庫存數量</a><BR>
			</td>
		</tr>
		
		<tr height="52" bgcolor="lightblue" align="center">
			<td width="350"><p align="left" /> 
				<a href='queryByCategory'>分類查詢</a><BR>
			</td>
			<td width="350"><p align="left" /> 
	 			<a href="<c:url value='/products/add' />">新增產品資料</a><BR>
	        </td>
	        
		</tr>
		<tr height="52" bgcolor="lightblue" align="center">
			<td width="350"><p align="left" /> 
				<a href='forwardDemo'>RedirectView:	forwardDemo</a><br>
			</td>
			<td width="350"><p align="left" /> 
				<a href='redirectDemo'>RedirectView: redirectDemo</a><br>
			</td>
		</tr>
	</table>

</body>
</html>