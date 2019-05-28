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
            <c:set var="reports" scope="request" value="${contractReportDAO.getContractReport(param.storingFrom, param.storingUntil, param.employee)}" />

            <c:choose>
                <c:when test="${reports.size() == 0}">
                    <div class="alert alert-warning" role="alert">
                        Invalid entered data or no contracts match your specified data
                    </div>
                </c:when>

                <c:otherwise>
                    <table class="table">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col">Storing From</th>
                            <th scope="col">Storing Until</th>
                            <th scope="col">Client</th>
                            <th scope="col">Bill &euro;</th>
                            <th scope="col">Excise &euro;</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${reports}" var="report">
                                <tr>
                                    <td colspan="5" style="text-align: center; background-color: lightgray">${report.getStatus()}</td>
                                </tr>

                                <c:forEach begin="0" end="${fn:length(report.getFullName()) - 1}" var="loop">
                                    <tr>
                                        <td>${report.getStoringFrom()[loop]}</td>
                                        <td>${report.getStoringUntil()[loop]}</td>
                                        <td>${report.getFullName()[loop]}</td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${report.getBills()[loop] != null}">
                                                    ${report.getBills()[loop]}
                                                </c:when>

                                                <c:otherwise>
                                                    -
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${report.getExcises()[loop] != null}">
                                                    ${report.getExcises()[loop]}
                                                </c:when>

                                                <c:otherwise>
                                                    -
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>

                                <tr>
                                    <td colspan="3" style="text-align: right">Group total:</td>
                                    <td>${report.getGroupBill()}</td>
                                    <td>${report.getGroupExcise()}</td>
                                </tr>
                            </c:forEach>

                            <tr>
                                <td colspan="3" style="text-align: right">Total:</td>
                                <td>${reports.get(0).getTotalBill()}</td>
                                <td>${reports.get(0).getTotalExcise()}</td>
                            </tr>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${param.display == 'false'}">
            <h4 style="text-align: center; padding: 16px">Enter information for the report</h4>

            <form action="${pageContext.request.contextPath}/?form=contract&action=report&display=true" method="get">
                <div class="form_controls">
                    <div>
                        <input type="text" class="form-control" name="form" value="contract" style="display: none"/>
                        <input type="text" class="form-control" name="action" value="report" style="display: none"/>
                        <input type="text" class="form-control" name="display" value="true" style="display: none"/>
                    </div>

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
