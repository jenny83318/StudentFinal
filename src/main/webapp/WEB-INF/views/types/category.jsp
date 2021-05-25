<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel='stylesheet' href="<c:url value='/css/styles.css' />"  type="text/css" />
</head>
<body>
<div style="text-align:center" >
<h1>請依照類別來挑選產品：</h1>
<c:forEach var='category' items='${categoryList}' >
	<a href='products/${category}'>${category}</a><br>
</c:forEach>
</div>
</body>
</html>