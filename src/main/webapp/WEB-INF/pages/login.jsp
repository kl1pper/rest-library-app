<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Страница авторизации</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login.css"/>
</head>
<body>
<div class="container">
    <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="alert alert-danger">
                    <label id="error">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</label>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    </c:if>

    <form class="form-login" action="<c:url value='/j_spring_security_check' />" method="POST">
        <h3 class="form-login-heading">Введите логин и пароль</h3>
        <input id="j_username" name="j_username" type="text" class="form-control" placeholder="Логин">

        <input id="j_password" name="j_password" type="password" class="form-control" placeholder="Пароль">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
    </form>

</div>
</body>
</html>
