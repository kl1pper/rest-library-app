<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ошибка доступа</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css"/>
    <style>
        .vertical-center {
            min-height: 100%;
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
    </style>
</head>
<body>
<div class="jumbotron vertical-center">
    <div class="container">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <h2>Доступ запрещен</h2>
                <a href="<c:url value='/'/>" class="btn btn-info" role="button">Вернуться на главную</a>
            </div>
            <div class="col-md-3"></div>
        </div>
    </div>
</div>
</body>
</html>
