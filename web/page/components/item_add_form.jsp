<div>
    <c:if test="${not empty param.error}">
        <c:choose>
            <c:when test="${param.error == 'invalid'}">
                <div class="alert alert-danger" role="alert">
                    Invalid information, try again
                </div>
            </c:when>

            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    An error has occurred and the item could not be saved
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <h4 class="list_title">Add new item</h4>

    <jsp:useBean id="itemEditDao" class="com.warehouse.dao.StoredItemDAO"/>

    <form action="${pageContext.request.contextPath}/api/item/save" method="post">
        <div class="form_controls">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" name="name" id="name" placeholder="Name"/>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <input type="text" class="form-control" name="description" id="description" placeholder="Description"/>
            </div>

            <div class="form-group">
                <label for="weight">Weight (kg)</label>
                <input type="text" class="form-control" name="weight" id="weight" placeholder="Weight"/>
            </div>

            <div class="form-group">
                <label for="width">Width (m)</label>
                <input type="text" class="form-control" name="width" id="width" placeholder="Width"/>
            </div>

            <div class="form-group">
                <label for="length">Length (m)</label>
                <input type="text" class="form-control" name="length" id="length" placeholder="Length"/>
            </div>

            <div class="form-group">
                <label for="height">Height (m)</label>
                <input type="text" class="form-control" name="height" id="height" placeholder="Height"/>
            </div>

            <div class="form-group">
                <label for="category">Category</label>

                <select class="form-control" name="category" id="category">
                    <c:forEach items="${itemEditDao.getCategories()}" var="category">
                        <option>${category}</option>
                    </c:forEach>
                </select>
            </div>

            <div id="dynamic-content" style="display: none;"></div>

            <div id="addStoredItem" class="btn btn-dark" role="button">Add Stored Item</div>

            <br />

            <script src="${pageContext.request.contextPath}/js/add_stored_item.js"></script>

            <br />

            <button type="submit" class="list_item_button btn btn-dark mb-2">Save</button>
        </div>
    </form>
</div>
