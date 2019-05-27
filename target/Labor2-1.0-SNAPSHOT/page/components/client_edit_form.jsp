<div>
    <c:if test="${not empty param.error}">
        <c:choose>
            <c:when test="${param.error == 'invalid'}">
                <div class="alert alert-danger" role="alert">
                    Invalid client information, try again
                </div>
            </c:when>

            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    An error has occurred and the client could not be saved
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:choose>
        <c:when test="${param.action == 'add'}">
            <h4 class="list_title">Add new client</h4>
        </c:when>

        <c:when test="${param.action == 'edit'}">
            <h4 class="list_title">Edit client's information</h4>
        </c:when>
    </c:choose>

    <c:if test="${not empty param.id}">
        <jsp:useBean id="clientEditDao" class="com.warehouse.dao.ClientDAO"/>
        <c:set var="client" value="${clientEditDao.getByPersonalCode(param.id)}" />
    </c:if>

    <form action="${pageContext.request.contextPath}/api/client/save" method="post">
        <div class="form_controls">
            <div class="form-group">
                <label for="personalCode">Personal Code</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <input type="text" class="form-control" name="personalCode" id="personalCode" placeholder="Personal Code" value="${client.getPersonalCode()}"/>
                        <input type="text" style="display: none" class="form-control" name="oldPersonalCode" id="oldPersonalCode" value="${client.getPersonalCode()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="personalCode" id="personalCode" placeholder="Personal Code"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="firstName">First Name</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <input type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name" value="${client.getFirstName()}"/>
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
                        <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Last Name" value="${client.getLastName()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Last Name"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="phone">Phone</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="Phone Number" value="${client.getPhone()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="Phone Number"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="email">Email</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <input type="text" class="form-control" name="email" id="email" placeholder="Email Address" value="${client.getEmail()}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="email" id="email" placeholder="Email Address"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="birthDate">Birth Date (yyyy-mm-dd)</label>

                <c:choose>
                    <c:when test="${param.action == 'edit'}">
                        <fmt:formatDate value="${client.getBirthDate()}" var="formattedDate"
                                        type="date" pattern="yyyy-MM-dd" />

                        <input type="text" class="form-control" name="birthDate" id="birthDate" placeholder="Birth Date" value="${formattedDate}"/>
                    </c:when>

                    <c:otherwise>
                        <input type="text" class="form-control" name="birthDate" id="birthDate" placeholder="Birth Date"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${param.action == 'edit'}">
                <input style="display: none" type="text" id="update" name="update" value="true"/>
            </c:if>

            <button type="submit" class="btn btn-dark mb-2">Save</button>
        </div>
    </form>
</div>
