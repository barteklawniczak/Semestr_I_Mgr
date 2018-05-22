<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Log in with your account</title>
</head>

<body>

<div class="container">
    <div class="jumbotron">
        <form method="POST" action="${contextPath}/login" class="form-signin">
            <c:if test="${message != null}">
                <div class="alert alert-info">
                    <strong>${message}</strong>
                </div>
            </c:if>
            <h2 class="form-heading">Log in</h2>
            <div class="form-group ${error != null ? 'has-error' : ''}">
                <br>
                <label>Type your username:</label>
                <input name="username" type="text" class="form-control" placeholder="Username" autofocus/>
                <label>Type your password:</label>
                <input name="password" type="password" class="form-control" placeholder="Password"/>
                <br>
                <c:if test="${error != null}">
                    <div class="alert alert-danger">
                        <strong>${error}</strong>
                    </div>
                </c:if>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
                <a class="btn btn-lg btn-info btn-block text-center" href="registration">Create an account</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
