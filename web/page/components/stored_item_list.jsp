<c:if test="${empty param.page}">
    <c:redirect url="/?form=${param.form}&page=1"/>
</c:if>

<c:choose>
    <c:when test="${not empty param.page}">
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <h4 class="list_title">Stored Items</h4>
                </div>

                <div class="col-sm">
                    <a class="my_button btn btn-dark" href="${pageContext.request.contextPath}/?form=stored_item&action=add" role="button">New Item</a>
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${param.success == 'add'}">
                <div class="alert alert-success" role="alert">
                    Item successfully inserted
                </div>
            </c:when>

            <c:when test="${param.success == 'edit'}">
                <div class="alert alert-success" role="alert">
                    Item successfully updated
                </div>
            </c:when>

            <c:when test="${param.success == 'delete'}">
                <div class="alert alert-success" role="alert">
                    Item successfully deleted
                </div>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${param.error == 'failed_deletion'}">
                <div class="alert alert-danger" role="alert">
                    An error has occurred when trying to delete the item
                </div>
            </c:when>
        </c:choose>

        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Description</th>
                <th scope="col">Dimensions (m)</th>
                <th scope="col">Weight (kg)</th>
                <th scope="col">Count</th>
                <th scope="col">Owner</th>
                <th scope="col">Storage Address</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="itemDao" class="com.warehouse.dao.StoredItemDAO"/>

            <%@ page import="com.warehouse.Constants" %>

            <c:set var="count" value="${itemDao.count()}" />
            <c:set var="end" value="${count / Constants.PAGE_SIZE + (count % Constants.PAGE_SIZE == 0 ? 0 : 1)}" />

            <c:if test="${param.page <= 0 || param.page > end}">
                <c:redirect url="/?form=${param.form}&page=1" />
            </c:if>

            <c:forEach items="${itemDao.getPage(param.page)}" var="item">
                <tr>
                    <td>${item.getName()}</td>
                    <td>${item.getDescription()}</td>
                    <td>${item.getDimensions()}</td>
                    <td>${item.getWeight()}</td>
                    <td>${item.getCount()}</td>
                    <td>${item.getOwner().getFirstName()} ${item.getOwner().getLastName()}</td>
                    <td>${item.getStorageAddress()}</td>

                    <td>
                        <a class="list_item_button btn btn-dark" href="${pageContext.request.contextPath}/?form=stored_item&action=edit" role="button">Edit</a>
                    </td>

                    <td>
                        <a class="list_item_button btn btn-dark" href="${pageContext.request.contextPath}/api/item/delete?id=${item.getId()}" role="button">Delete</a>
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
