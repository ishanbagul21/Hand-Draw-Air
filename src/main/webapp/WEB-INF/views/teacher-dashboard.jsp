<%@ page contentType="text/html;charset=UTF-8" %><!doctype html><html><head><title>Teacher Dashboard</title><jsp:include page="common/header.jspf"/></head><body>
<jsp:include page="common/sidebar.jspf"/>
<div class="content-wrapper"><h1>Teacher Dashboard</h1><hr>
<div class="row g-3">
<div class="col-md-4"><div class="stat-card p-3">Total Courses<br><b>${stats.courses}</b></div></div>
<div class="col-md-4"><div class="stat-card p-3">Total Classes<br><b>${stats.classes}</b></div></div>
<div class="col-md-4"><div class="stat-card p-3">Question Paper List<br><b>${stats.papers}</b></div></div>
</div></div></body></html>
