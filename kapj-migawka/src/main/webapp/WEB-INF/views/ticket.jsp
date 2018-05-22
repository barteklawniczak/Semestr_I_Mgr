<%@ taglib prefix="botDetect" uri="https://captcha.com/java/jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Buy your ticket</title>
</head>
<body>
<div class="container">
    <div class="jumbotron">

        <form:form method="POST" modelAttribute="ticketForm" class="form-signin" action="ticket">
            <h2><spring:message code="label.ticket.title" /></h2><br>
            <spring:bind path="ticketType">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="ticketType"><spring:message code="label.ticketType" /></form:label>
                    <form:select path="ticketType">
                        <form:options items="${ticketTypes}" itemValue="id" itemLabel="id" />
                    </form:select>
                    <form:errors path="ticketType" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <spring:bind path="halfPrice">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="halfPrice"><spring:message code="label.halfPrice" /></form:label>
                    <form:checkbox path="halfPrice"/>
                    <form:errors path="halfPrice" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <spring:bind path="startDate">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="startDate"><spring:message code="label.startDate" /></form:label>
                    <form:input type="date" path="startDate" class="form-control"/>
                    <form:errors path="startDate" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">
                <spring:message code="label.submit" />
            </button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

        <br><br><br>
        <h3>Available ticket types:</h3><br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Id</th>
                <th>Description</th>
                <th>Price</th>
                <th>Duration (In Days)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ticketType" items="${ticketTypes}">
                <tr>
                    <td><c:out value="${ticketType.id}"/></td>
                    <td><c:out value="${ticketType.description}"/></td>
                    <td><c:out value="${ticketType.price}"/></td>
                    <td><c:out value="${ticketType.duration}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
