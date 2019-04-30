<div>
    <c:if test="${not empty param.error}">
        <c:choose>
            <c:when test="${param.error == 'invalid'}">
                <div class="alert alert-danger" role="alert">
                    Invalid employee information, try again
                </div>
            </c:when>

            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    An error has occurred and the employee could not be saved
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:choose>
        <c:when test="${param.action == 'add'}">
            <h4 class="list_title">Add new employee</h4>
        </c:when>

        <c:when test="${param.action == 'edit'}">
            <h4 class="list_title">Edit employee's information</h4>
        </c:when>
    </c:choose>

    <jsp:useBean id="employeeEditDao" class="com.warehouse.dao.EmployeeDAO"/>

    <c:if test="${not empty param.id}">
        <c:set var="employee" value="${employeeEditDao.getByNumber(param.id)}" />
    </c:if>

    <form action="${pageContext.request.contextPath}/api/employee/save" method="post">
        <div class="form_controls">
            <div class="form-group">
                <label for="number">Time-card Number</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <input type="text" class="form-control" name="number" id="number" placeholder="Time-card Number" value="${employee.getTimeCardNumber()}"/>
                        <input style="display: none" type="text" class="form-control" name="oldNumber" id="oldNumber" placeholder="Time-card Number" value="${employee.getTimeCardNumber()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="number" id="number" placeholder="Time-card Number"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="firstName">First Name</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <input type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name" value="${employee.getFirstName()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Last Name" value="${employee.getLastName()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Last Name"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="authority">Authority</label>

                <select class="form-control" name="authority" id="authority">
                    <c:forEach items="${employeeEditDao.getAuthorities()}" var="authority">
                        <option ${param.action == 'edit' && employee.getAuthority() == authority ? 'selected' : ''}>${authority}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="workplace">Workplace</label>
                <select class="form-control" name="workplace" id="workplace">
                    <c:forEach items="${employeeEditDao.getWorkplaces()}" var="workplace">
                        <option ${param.action == 'edit' && employee.getWorkplace() == workplace ? 'selected' : ''}>${workplace}</option>
                    </c:forEach>
                </select>
            </div>

            <c:if test="${param.action == 'edit'}">
                <input style="display: none" type="text" id="update" name="update" value="true"/>
            </c:if>

            <button type="submit" class="btn btn-dark mb-2">Save</button>
        </div>
    </form>
</div>