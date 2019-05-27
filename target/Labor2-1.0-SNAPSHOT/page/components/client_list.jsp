<c:if test="${empty param.page}">
    <c:redirect url="/?form=${param.form}&page=1"/>
</c:if>

<c:choose>
    <c:when test="${not empty param.page}">
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <h4 class="list_title">Clients</h4>
                </div>

                <div class="col-sm">
                    <a class="my_button btn btn-dark" href="${pageContext.request.contextPath}/?form=client&action=add" role="button">New Client</a>
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${param.success == 'add'}">
                <div class="alert alert-success" role="alert">
                    Client successfully inserted
                </div>
            </c:when>

            <c:when test="${param.success == 'edit'}">
                <div class="alert alert-success" role="alert">
                    Client successfully updated
                </div>
            </c:when>

            <c:when test="${param.success == 'delete'}">
                <div class="alert alert-success" role="alert">
                    Client successfully deleted
                </div>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${param.error == 'failed_deletion'}">
                <div class="alert alert-danger" role="alert">
                    An error has occurred when trying to delete the client
                </div>
            </c:when>
        </c:choose>

        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Personal Code</th>
                <th scope="col">First Name</th>
                <th scope="col">Last Name</th>
                <th scope="col">Phone</th>
                <th scope="col">Email</th>
                <th scope="col">Birth Date</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="clientDao" class="com.warehouse.dao.ClientDAO"/>

            <%@ page import="com.warehouse.Constants" %>

            <c:set var="count" value="${clientDao.count()}" />
            <c:set var="end" value="${count / Constants.PAGE_SIZE + (count % Constants.PAGE_SIZE == 0 ? 0 : 1)}" />

            <c:if test="${param.page <= 0 || param.page > end}">
                <c:redirect url="/?form=${param.form}&page=1" />
            </c:if>

            <c:forEach items="${clientDao.getPage(param.page)}" var="client">
                <tr>
                    <td>${client.getPersonalCode()}</td>
                    <td>${client.getFirstName()}</td>
                    <td>${client.getLastName()}</td>
                    <td>${client.getPhone()}</td>
                    <td>${client.getEmail()}</td>
                    <td>
                        <fmt:formatDate value="${client.getBirthDate()}" var="formattedDate"
                                        type="date" pattern="yyyy-MM-dd" />
                        ${formattedDate}
                    </td>

                    <td>
                        <a class="list_item_button btn btn-dark" href="${pageContext.request.contextPath}/?form=client&action=edit&id=${client.getPersonalCode()}" role="button">Edit</a>
                    </td>

                    <td>
                        <a class="list_item_button btn btn-dark" href="${pageContext.request.contextPath}/api/client/delete?code=${client.getPersonalCode()}" role="button">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="center">
            <nav aria-label="...">
                <ul class="pagination pg-black">
                    <li class="page-item disabled">
                        <span class="page-link">Pages</span>
                    </li>

                    <c:forEach begin="1" end="${end}" varStatus="loop">
                        <li class="page-item ${param.page == loop.index ? 'active' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/?form=${param.form}&page=${loop.index}">${loop.index}</a></li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </c:when>

    <c:otherwise>
        <h4 class="list_title">Please provide the page number</h4>
    </c:otherwise>
</c:choose>

