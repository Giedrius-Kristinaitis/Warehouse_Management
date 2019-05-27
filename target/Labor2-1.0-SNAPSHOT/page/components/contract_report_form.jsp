<div>
    <c:if test="${not empty param.error}">
        <c:choose>
            <c:when test="${param.error == 'invalid'}">
                <div class="alert alert-danger" role="alert">
                    Please enter a valid date
                </div>
            </c:when>

            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    An error has occurred and the report could not be generated
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <jsp:useBean id="contractReportDAO" class="com.warehouse.dao.ContractDAO"/>

    <c:choose>
        <c:when test="${param.display == 'true'}">
            <% contractReportDAO.getContractReport("2016-01-02", "2020-02-02", "2 a b"); %>
        </c:when>

        <c:when test="${param.display == 'false'}">
            <h4 style="text-align: center; padding: 16px">Enter information for the report</h4>

            <form action="${pageContext.request.contextPath}/?form=contract&action=report&display=true" method="get">
                <div class="form_controls">
                    <div class="form-group">
                        <label for="storingFrom">Storing From (yyyy-mm-dd)</label>

                        <input type="text" class="form-control" name="storingFrom" id="storingFrom" placeholder="Storing From"/>
                    </div>

                    <div class="form-group">
                        <label for="storingUntil">Storing Until (yyyy-mm-dd)</label>

                        <input type="text" class="form-control" name="storingUntil" id="storingUntil" placeholder="Storing Until"/>
                    </div>

                    <div class="form-group">
                        <label for="employee">Assigned Employee</label>

                        <select class="form-control" name="employee" id="employee">
                            <c:forEach items="${contractReportDAO.getEmployees()}" var="employee">
                                <option>${employee}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-dark mb-2">Generate Report</button>
                </div>
            </form>
        </c:when>
    </c:choose>
</div>
