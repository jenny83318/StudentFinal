<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>Forward / Redirect</title>
<link rel='stylesheet' href="<c:url value='/css/styles.css' />"  type="text/css" />
</head>
<body>
存放在請求物件內的屬性物件(modelData2): ${modelData2} <br>
原始的請求: ${uri2}<br>
<hr>
存放在redirectAttributes物件內的屬性物件(modelData3): ${modelData3} <br>
原始的請求: ${uri3}<br>
</body>
</html>
