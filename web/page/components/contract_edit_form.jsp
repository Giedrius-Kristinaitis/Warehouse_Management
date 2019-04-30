<div>
    <c:if test="${not empty param.error}">
        <c:choose>
            <c:when test="${param.error == 'invalid'}">
                <div class="alert alert-danger" role="alert">
                    Invalid contract information, try again
                </div>
            </c:when>

            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    An error has occurred and the contract could not be saved
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:choose>
        <c:when test="${param.action == 'add'}">
            <h4 class="list_title">Add new contract</h4>
        </c:when>

        <c:when test="${param.action == 'edit'}">
            <h4 class="list_title">Edit contract</h4>
        </c:when>
    </c:choose>

    <jsp:useBean id="contractEditDao" class="com.warehouse.dao.ContractDAO"/>

    <c:if test="${not empty param.id}">
        <c:set var="contract" value="${contractEditDao.getById(param.id)}" />
    </c:if>

    <form action="${pageContext.request.contextPath}/api/contract/save" method="post">
        <div class="form_controls">
            <div class="form-group">
                <label for="date">Date (yyyy-mm-dd)</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <fmt:formatDate value="${contract.getDate()}" var="formattedDate"
                                        type="date" pattern="yyyy-MM-dd" />

                        <input type="text" class="form-control" name="date" id="date" placeholder="Date" value="${formattedDate}"/>
                        <input style="display: none;" type="text" class="form-control" name="oldId" id="oldId" placeholder="Date" value="${contract.getId()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="date" id="date" placeholder="Date"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="storingFrom">Storing From (yyyy-mm-dd)</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <fmt:formatDate value="${contract.getStoringFrom()}" var="formattedDate"
                                        type="date" pattern="yyyy-MM-dd" />

                        <input type="text" class="form-control" name="storingFrom" id="storingFrom" placeholder="Storing From" value="${formattedDate}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="storingFrom" id="storingFrom" placeholder="Storing From"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="storingUntil">Storing Until (yyyy-mm-dd)</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <fmt:formatDate value="${contract.getStoringUntil()}" var="formattedDate"
                                        type="date" pattern="yyyy-MM-dd" />

                        <input type="text" class="form-control" name="storingUntil" id="storingUntil" placeholder="Storing Until" value="${formattedDate}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="storingUntil" id="storingUntil" placeholder="Storing Until"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="rentedArea">Rented Area (m<sup>3</sup>)</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <input type="text" class="form-control" name="rentedArea" id="rentedArea" placeholder="Rented Area" value="${contract.getRentedArea()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="rentedArea" id="rentedArea" placeholder="Rented Area"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="status">Status</label>

                <select class="form-control" name="status" id="status">
                    <c:forEach items="${contractEditDao.getStatuses()}" var="status">
                        <option ${param.action == 'edit' && employee.getStatus() == status ? 'selected' : ''}>${status}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="client">Client</label>

                <select class="form-control" name="client" id="client">
                    <c:forEach items="${contractEditDao.getClients()}" var="client">
                        <option>${client}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="employee">Employee</label>

                <select class="form-control" name="employee" id="employee">
                    <c:forEach items="${contractEditDao.getEmployees()}" var="employee">
                        <option>${employee}</option>
                    </c:forEach>
                </select>
            </div>

            <div id="dynamic-content" style="display: none;"></div>

            <div id="addBill" class="list_item_button btn btn-dark" role="button">Add Bill</div>

            <br />

            <script src="${pageContext.request.contextPath}/js/add_bill.js"></script>

            <br />

            <c:if test="${param.action == 'edit'}">
                <input style="display: none" type="text" id="update" name="update" value="true"/>
            </c:if>

            <button type="submit" class="list_item_button btn btn-dark mb-2">Save</button>
        </div>
    </form>
</div>
