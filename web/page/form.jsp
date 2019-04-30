<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Warehouse</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-grid.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body>
    <div>
        <!-- include the header -->
        <%@ include file="components/header.jsp" %>

        <div class="content">
            <!-- include the form content -->
            <c:choose>
                <c:when test="${param.form == 'employee'}">
                    <c:choose>
                        <c:when test="${param.action == 'edit' || param.action == 'add'}">
                            <%@ include file="components/employee_edit_form.jsp" %>
                        </c:when>

                        <c:otherwise>
                            <%@ include file="components/employee_list.jsp" %>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:when test="${param.form == 'client'}">
                    <c:choose>
                        <c:when test="${param.action == 'edit' || param.action == 'add'}">
                            <%@ include file="components/client_edit_form.jsp" %>
                        </c:when>

                        <c:otherwise>
                            <%@ include file="components/client_list.jsp" %>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:when test="${param.form == 'contract'}">
                    <c:choose>
                        <c:when test="${param.action == 'edit' || param.action == 'add'}">
                            <%@ include file="components/contract_edit_form.jsp" %>
                        </c:when>

                        <c:otherwise>
                            <%@ include file="components/contract_list.jsp" %>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:when test="${param.form == 'stored_item'}">
                    <c:choose>
                        <c:when test="${param.action == 'edit'}">
                            <%@ include file="components/item_edit_form.jsp" %>
                        </c:when>

                        <c:when test="${param.action == 'add'}">
                            <%@ include file="components/item_add_form.jsp" %>
                        </c:when>

                        <c:otherwise>
                            <%@ include file="components/stored_item_list.jsp" %>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:otherwise>
                    <c:redirect url="/?form=employee&page=1"/>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- include the footer (optional) -->
    </div>

    <script src="${pageContext.request.contextPath}/js/jquery-3.4.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
