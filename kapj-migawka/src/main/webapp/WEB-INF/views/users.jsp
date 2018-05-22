<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Users in system</title>
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
                <th>Username</th>
                <th>Email</th>
                <th>Registration date</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.registrationDate}"/></td>
                    <td><a class="btn-group">
                        <a class="btn btn-info" href="${contextPath}/admin/users/edit?id=${user.id}">Edit</a>
                        <c:if test="${employee == null}">
                            <a class="btn btn-danger" href="${contextPath}/admin/users/delete?id=${user.id}"
                               onClick="return confirm('Are you sure to delete ${user.username}?')">Delete</a>
                        </c:if>
                        <c:if test="${employee != null}">
                            <a class="btn btn-danger" href="${contextPath}/admin/employees/delete?id=${user.id}"
                               onClick="return confirm('Are you sure to delete ${user.username}?')">Delete</a>
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
