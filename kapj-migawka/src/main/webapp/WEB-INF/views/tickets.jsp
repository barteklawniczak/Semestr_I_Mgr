<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tickets in system</title>
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
                <th>Ticket Type</th>
                <th>Price</th>
                <th>Start Date</th>
                <th>Duration</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ticket" items="${tickets}">
                <tr>
                    <td><c:out value="${ticket.card.user.username}"/></td>
                    <td><c:out value="${ticket.ticketType.description}"/></td>
                    <td><c:out value="${ticket.price}"/></td>
                    <td><c:out value="${ticket.startDate}"/></td>
                    <td><c:out value="${ticket.ticketType.duration} days"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
