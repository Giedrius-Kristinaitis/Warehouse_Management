<header>
    <nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
        <span id="brand" class="navbar-brand">Warehouse</span>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link ${param.form == 'employee' ? 'active' : ''}" href="${pageContext.request.contextPath}/?form=employee">Employees</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.form == 'client' ? 'active' : ''}" href="${pageContext.request.contextPath}/?form=client">Clients</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.form == 'contract' ? 'active' : ''}" href="${pageContext.request.contextPath}/?form=contract">Contracts</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.form == 'stored_item' ? 'active' : ''}" href="${pageContext.request.contextPath}/?form=stored_item">Stored Items</a>
                </li>
            </ul>
        </div>
    </nav>
</header>
