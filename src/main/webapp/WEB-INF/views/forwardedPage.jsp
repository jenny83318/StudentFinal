<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head> 
<link rel='stylesheet' href="<c:url value='/css/styles.css' />"  type="text/css" />
<meta charset="UTF-8">
<title>Forward / Redirect</title>
</head>
<body>
存放在請求物件內的屬性物件(modelData0): ${modelData0} <br>
<hr>
Request URL: ${url0} <br>
<hr>
Request URI: ${uri0} <br>
<hr>
存放在請求物件內的屬性物件(modelData1): ${modelData1} <br>
${uri1} <br>
</body>
</html>
