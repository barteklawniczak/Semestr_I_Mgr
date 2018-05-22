<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cards in system</title>
</head>
<body>

<div class="container">
    <div class="jumbotron">
        <c:if test="${message != null}">
            <div class="alert alert-info">
                <strong>${message}</strong>
            </div>
        </c:if>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Id</th>
                <th>User</th>
                <th>Is enabled</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="card" items="${cards}">
                <tr>
                    <td><c:out value="${card.id}"/></td>
                    <td><c:out value="${card.user.username}"/></td>
                    <td><c:out value="${card.enabled}"/></td>
                    <td><a class="btn-group">
                        <c:if test="${card.enabled == false}">
                            <a class="btn btn-info" href="${contextPath}/employee/cards/enable?id=${card.id}">Enable</a>
                        </c:if>
                        <c:if test="${card.enabled == true}">
                            <button class="btn" disabled>Enable</button>
                        </c:if>
                    </a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
