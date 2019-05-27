<c:if test="${empty param.page}">
    <c:redirect url="/?form=${param.form}&page=1"/>
</c:if>

<c:choose>
    <c:when test="${not empty param.page}">
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <h4 class="list_title">Contracts</h4>
                </div>

                <div class="col-sm">
                    <a class="my_button btn btn-dark" href="${pageContext.request.contextPath}/?form=contract&action=add" role="button">New Contract</a>
                </div>

                <div class="col-sm">
                    <a class="my_button btn btn-dark" href="${pageContext.request.contextPath}/?form=contract&action=report&display=false" role="button">New Report</a>
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${param.success == 'add'}">
                <div class="alert alert-success" role="alert">
                    Contract successfully inserted
                </div>
            </c:when>

            <c:when test="${param.success == 'edit'}">
                <div class="alert alert-success" role="alert">
                    Contract successfully updated
                </div>
            </c:when>

            <c:when test="${param.success == 'delete'}">
                <div class="alert alert-success" role="alert">
                    Contract successfully deleted
                </div>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${param.error == 'failed_deletion'}">
                <div class="alert alert-danger" role="alert">
                    An error has occurred when trying to delete the contract
                </div>
            </c:when>
        </c:choose>

        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Date</th>
                <th scope="col">Storing From</th>
                <th scope="col">Storing Until</th>
                <th scope="col">Rented Area (m<sup>3</sup>)</th>
                <th scope="col">Status</th>
                <th scope="col">Client</th>
                <th scope="col">Employee</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="contractDao" class="com.warehouse.dao.ContractDAO"/>

            <%@ page import="com.warehouse.Constants" %>

            <c:set var="count" value="${contractDao.count()}" />
            <c:set var="end" value="${count / Constants.PAGE_SIZE + (count % Constants.PAGE_SIZE == 0 ? 0 : 1)}" />

            <c:if test="${param.page <= 0 || param.page > end}">
                <c:redirect url="/?form=${param.form}&page=1" />
            </c:if>

            <c:forEach items="${contractDao.getPage(param.page)}" var="contract">
                <tr>
                    <td>
                        <fmt:formatDate value="${contract.getDate()}" var="formattedDate"
                                        type="date" pattern="yyyy-MM-dd" />
                            ${formattedDate}
                    </td>
                    <td>
                        <fmt:formatDate value="${contract.getStoringFrom()}" var="formattedDate"
                                        type="date" pattern="yyyy-MM-dd" />
                            ${formattedDate}
                    </td>
                    <td>
                        <fmt:formatDate value="${contract.getStoringUntil()}" var="formattedDate"
                                        type="date" pattern="yyyy-MM-dd" />
                            ${formattedDate}
                    </td>

                    <td>${contract.getRentedArea()}</td>
                    <td>${contract.getStatus()}</td>
                    <td>${contract.getClient().getFirstName()} ${contract.getClient().getLastName()}</td>
                    <td>${contract.getEmployee().getFirstName()} ${contract.getEmployee().getLastName()}</td>

                    <td>
                        <a class="list_item_button btn btn-dark" href="${pageContext.request.contextPath}/?form=contract&action=edit&id=${contract.getId()}" role="button">Edit</a>
                    </td>

                    <td>
                        <a class="list_item_button btn btn-dark" href="${pageContext.request.contextPath}/api/contract/delete?id=${contract.getId()}" role="button">Delete</a>
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
