<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
   href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>發生例外</title>
<link rel='stylesheet' href='css/styles.css'  type="text/css" />
</head>
<body>
   <section>
      <div class="jumbotron">
         <div class="container">
            <h2>查無此項產品的資料: ${invalidBookId}</h2>
         </div>
      </div>
   </section>
   <section >
      <div class="container">
           <p>${url}</p>
           <p>${exception}</p>
           <p>${message}</p>
      </div>
      <div class="container">
           <p>
             <a href="<spring:url value='/products' />"  class='btn btn-primary' >
             <span class='glyphicon-left-hand glyphicon'></span>回產品列表</a></p>
           <p>${exceptiom}</p>
      </div>
   </section>
</body>
</html>
