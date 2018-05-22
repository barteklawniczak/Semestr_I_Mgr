<%@ taglib prefix="botDetect" uri="https://captcha.com/java/jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit user ${editUserForm.username}</title>
</head>
<body>
<div class="container">
    <div class="jumbotron">

        <form:form method="POST" modelAttribute="editUserForm" class="form-signin" action="edit?id=${editUserForm.id}">

            <c:if test="${message != null}">
                <div class="alert alert-info">
                    <strong>${message}</strong>
                </div>
            </c:if>

            <h2><spring:message code="label.editUser.title" /></h2><br>
            <spring:bind path="username">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="username"><spring:message code="label.username" /></form:label>
                    <form:input type="text" path="username" class="form-control" placeholder="" autofocus="true"/>
                    <form:errors path="username" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="name"><spring:message code="label.name" /></form:label>
                    <form:input type="text" path="name" class="form-control" placeholder=""/>
                    <form:errors path="name" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <spring:bind path="surname">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="surname"><spring:message code="label.surname" /></form:label>
                    <form:input type="text" path="surname" class="form-control" placeholder=""/>
                    <form:errors path="surname" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="email"><spring:message code="label.email" /></form:label>
                    <form:input type="text" path="email" class="form-control" placeholder=""/>
                    <form:errors path="email" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <spring:bind path="birthDate">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="birthDate"><spring:message code="label.birthDate" /></form:label>
                    <form:input type="date" path="birthDate" class="form-control"/>
                    <form:errors path="birthDate" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <form:input path="id" type="hidden" />

            <br><br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">
                <spring:message code="label.submit" />
            </button>
        </form:form>
    </div>
</div>
</body>
</html>
