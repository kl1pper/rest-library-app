<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Сообщения</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/book.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.0.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/transparency.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.bootpag.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/book.js"></script>

    <script>
        $(document).ready(function () {
            Book.load('${pageContext.request.contextPath}');
        })
    </script>
</head>
<body style="padding-top: 70px;">
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <span class="navbar-brand">Список книг</span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="buttons-panel">
        <sec:authorize access="hasRole('ADMIN')">
            <button type="button" class="btn btn-success" onclick="Book.prepareCreate()"><span
                    class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
            </button>
        </sec:authorize>
        <div class="btn-group pull-right">
            <button type="button" class="btn btn-primary" onclick="Book.prepareSearch()">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Поиск
            </button>
            <button type="button" class="btn btn-primary clear-search-button" onclick="Book.clearSearch()"
                    data-toggle="tooltip" title="Сбросить поисковые фильтры">
                <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span>
            </button>
        </div>

    </div>
    <table class="table table-striped" id="books-table">
        <thead>
        <th>Автор</th>
        <th>Название</th>
        <th>Год</th>
        <th></th>
        <sec:authorize access="hasRole('ADMIN')">
            <th></th>
            <th></th>
        </sec:authorize>
        </thead>
        <tbody id="books">
        <tr>
            <td class="author"></td>
            <td class="title"></td>
            <td class="publishedYear"></td>
            <td>
                <a href="#" class="viewLink" onclick="">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </a>
            </td>
            <sec:authorize access="hasRole('ADMIN')">
                <td>
                    <a href="#" class="editLink" onclick="">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    </a>
                </td>
                <td>
                    <a href="#" class="deleteLink" onclick="">
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>
                </td>
            </sec:authorize>
        </tr>
        </tbody>
    </table>
    <div id="paginator" class="pull-right"></div>
</div>

<div class="modal fade" id="book-view">
    <div class="modal-dialog book">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Автор</label>

                        <div class="col-sm-10">
                            <p class="form-control-static author"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Год</label>

                        <div class="col-sm-10">
                            <p class="form-control-static publishedYear"></p>
                        </div>
                    </div>
                    <blockquote class="description"></blockquote>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="book-edit">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title modalTitle"></h4>
            </div>
            <div class="modal-body book">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="author">Автор</label>

                        <div class="col-sm-10">
                            <input class="form-control" id="author" name="author"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="title">Название</label>

                        <div class="col-sm-10">
                            <input class="form-control" id="title" name="title"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="publishedYear">Год</label>

                        <div class="col-sm-10">
                            <input class="form-control" id="publishedYear" name="publishedYear"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="description">Описание</label>

                        <div class="col-sm-10">
                            <textarea class="form-control" id="description" name="description"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                <button type="button" class="btn btn-primary" id="confirmButton" onclick=""></button>
            </div>
        </div>
    </div>
</div>

<sec:authorize access="hasRole('ADMIN')">
    <div class="modal fade" id="book-delete">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body message">
                    Вы действительно хотите удалить книгу
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Нет</button>
                    <button type="button" class="btn btn-danger" id="deleteButton" onclick="">Да</button>
                </div>

            </div>
        </div>
    </div>
</sec:authorize>


</body>
</html>
