<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome to MigawkaApp</title>
</head>
<body>

<div class="container">
    <div class="jumbotron">
        <h1>Welcome ${pageContext.request.userPrincipal.name}</h1>
        <br><br><hr><br>
        <p class="lead">Please select on navbar an action that you would perform</p>
    </div>
</div>


</body>
</html>
