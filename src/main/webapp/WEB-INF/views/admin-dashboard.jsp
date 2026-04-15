<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html><head><title>Admin Dashboard</title><jsp:include page="common/header.jspf"/></head><body>
<jsp:include page="common/sidebar.jspf"/>
<div class="content-wrapper">
<h1>Welcome to Automatic Question Paper Generator</h1><hr>
<div class="row g-3">
<div class="col-md-3"><div class="stat-card p-3">Total Courses<br><b>${stats.courses}</b></div></div>
<div class="col-md-3"><div class="stat-card p-3">Total Classes<br><b>${stats.classes}</b></div></div>
<div class="col-md-3"><div class="stat-card p-3">Question Paper List<br><b>${stats.papers}</b></div></div>
<div class="col-md-3"><div class="stat-card p-3">Registered Users<br><b>${stats.users}</b></div></div>
</div></div></body></html>
