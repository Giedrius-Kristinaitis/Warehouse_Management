<div>
    <c:if test="${not empty param.error}">
        <c:choose>
            <c:when test="${param.error == 'invalid'}">
                <div class="alert alert-danger" role="alert">
                    Invalid item information, try again
                </div>
            </c:when>

            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    An error has occurred and the item could not be saved
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <h4 class="list_title">Edit item</h4>

    <form>
        <div class="form_controls">
            <div class="form-group">
                <label for="type">Item</label>
                <select class="form-control" id="type">
                    <option></option>
                </select>
            </div>

            <div class="form-group">
                <label for="count">Count</label>
                <input type="text" class="form-control" id="count" placeholder="Count"/>
            </div>

            <div class="form-group">
                <label for="owner">Owner</label>
                <select class="form-control" id="owner">
                    <option></option>
                </select>
            </div>

            <div class="form-group">
                <label for="storageArea">Storage Address</label>
                <select class="form-control" id="storageArea">
                    <option></option>
                </select>
            </div>

            <button type="submit" class="list_item_button btn btn-dark mb-2">Save</button>
        </div>
    </form>
</div>
