<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Check your migawka card</title>
</head>
<body>

<div class="container">
    <div class="jumbotron">
        <c:if test="${message != null}">
            <div class="alert alert-info">
                <strong>${message}</strong>
            </div>
        </c:if>
        <c:if test="${notExist != null}">
            <h1>${notExist}</h1>
            <br><br><hr><br>
            <p class="lead">If you want to create one please click on the button below</p>
            <br>
            <form method="POST" action="${contextPath}/card" >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Create your card</button>
            </form>
        </c:if>
        <c:if test="${card != null}">
            <h1>Your card: ID = ${card.id}</h1>
            <br><hr><br>
            <h3>Your bought tickets:</h3>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Ticket Type</th>
                    <th>Purchase Date</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Price</th>
                    <th>Half Price</th>
                    <th>Duration</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ticket" items="${tickets}">
                    <tr>
                        <td><c:out value="${ticket.ticketType.description}"/></td>
                        <td><c:out value="${ticket.purchaseDate}"/></td>
                        <td><c:out value="${ticket.startDate}"/></td>
                        <td><c:out value="${ticket.endDate}"/></td>
                        <td><c:out value="${ticket.price}"/></td>
                        <td><c:out value="${ticket.halfPrice}"/></td>
                        <td><c:out value="${ticket.ticketType.duration} days"/></td>
                        <td><a class="btn-group">
                            <a class="btn btn-info" href="${contextPath}/card/ticket/bill?id=${ticket.id}">Get bill</a>
                        </a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

    </div>
</div>


</body>
</html>
