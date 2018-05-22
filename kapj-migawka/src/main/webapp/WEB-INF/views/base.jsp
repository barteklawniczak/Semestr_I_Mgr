<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="description" content="Migawka Application - simple project">
    <meta name="author" content="Bartłomiej Ławniczak">
    <link href="../../resources/css/migawka.css" rel="stylesheet">
    <link href="../../resources/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="app-wrapper">
    <nav class="navbar navbar-default bg-white">
        <div class="container-fluid bg-white">
            <div class="navbar-header">
                <a class="navbar-brand" href="${contextPath}/home">MigawkaApplication</a>
            </div>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <form id="logoutForm" method="POST" action="${contextPath}/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <div style="float: right" class="btn-group">
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <a class="btn btn-lg" href="${contextPath}/ticket">Buy a ticket</a>
                        <a class="btn btn-lg" href="${contextPath}/card">Check your card</a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_EMPLOYEE')">
                        <a class="btn btn-lg" href="${contextPath}/employee/tickets">Check tickets</a>
                        <a class="btn btn-lg" href="${contextPath}/employee/cards">Check cards</a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a class="btn btn-lg" href="${contextPath}/admin/employees/add">Add employee</a>
                        <a class="btn btn-lg" href="${contextPath}/admin/employees">List employees</a>
                        <a class="btn btn-lg" href="${contextPath}/admin/users">List users</a>
                    </sec:authorize>
                    <a class="btn btn-lg" onclick="document.forms['logoutForm'].submit()">Logout</a></li>
                </div>
            </c:if>
        </div>
    </nav>
</div>
</body>