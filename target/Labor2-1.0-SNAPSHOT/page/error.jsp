<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <c:choose>
        <c:when test="${not empty param.status}">
            <c:choose>
                <c:when test="${param.status==404}">
                    <h1>Error 404: Not Found</h1>

                    <p>Unfortunately, the resource you are trying to access does not exist on this server :(</p>
                    <p>Here's what you can do:</p>

                    <ul>
                        <li>Make sure the URL is correct</li>
                        <li>Go to another resource</li>
                        <li><a href="${pageContext.request.contextPath}/form">Go to the home page</a></li>
                    </ul>
                </c:when>

                <c:otherwise>
                    <h3>Status ${param.status} is not recognized, stop trying to mess with the error page</h3>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:otherwise>
            <h3>Status not supplied, stop trying to mess with the error page</h3>
        </c:otherwise>
    </c:choose>
</body>
</html>