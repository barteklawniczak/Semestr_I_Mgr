<%@ taglib prefix="botDetect" uri="https://captcha.com/java/jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register new account</title>
</head>
<body>
<div class="container">
    <div class="jumbotron">
        <spring:message code="label.language" /><a href="?lang=en">English</a> | <a href="?lang=pl">Polski</a>

        <form:form method="POST" modelAttribute="userForm" class="form-signin" action="registration">
            <h2><spring:message code="label.registration.title" /></h2><br>
            <spring:bind path="username">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="username"><spring:message code="label.username" /></form:label>
                    <form:input type="text" path="username" class="form-control" placeholder="" autofocus="true"/>
                    <form:errors path="username" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="password"><spring:message code="label.password" /></form:label>
                    <form:input type="password" path="password" class="form-control" placeholder=""/>
                    <form:errors path="password" cssStyle="color: red"/>
                </div>
            </spring:bind>

            <spring:bind path="passwordConfirm">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="passwordConfirm"><spring:message code="label.confirmPassword" /></form:label>
                    <form:input type="password" path="passwordConfirm" class="form-control" placeholder=""/>
                    <form:errors path="passwordConfirm" cssStyle="color: red"/>
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

            <label for="captchaCode"><spring:message code="label.retype.captcha" /></label>
            <botDetect:captcha id="exampleCaptcha" userInputID="captchaCode"/>
            <input id="captchaCode" type="text" name="captchaCode" />
            <br>
            <c:if test="${invalidCaptcha == 'error.captcha'}">
                <a class="color-red"><spring:message code="error.captcha" /></a>
            </c:if>

            <br><br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">
                <spring:message code="label.submit" />
            </button>
            <a class="btn btn-lg btn-info btn-block" type="button" href="login">
                <spring:message code="label.ifAccount.login" />
            </a>
        </form:form>
    </div>
</div>
</body>
</html>
