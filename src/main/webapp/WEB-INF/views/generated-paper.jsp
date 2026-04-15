<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html><head><title>Generated Paper</title><jsp:include page="common/header.jspf"/></head><body>
<jsp:include page="common/sidebar.jspf"/>
<div class="content-wrapper"><div class="paper-shell">
<div class="d-flex justify-content-between"><h4>Generated Question Paper</h4><a class="btn btn-outline-dark no-print" href="${pageContext.request.contextPath}/teacher/paper/print?paperId=${param.paperId}">Print</a></div><hr>
<h3 class="text-center">Sample Exam</h3>
<h4>A. Single Answer</h4>
<c:forEach items="${paperQuestions}" var="q"><c:if test="${q.type=='single'}"><div><p>${q.text}</p><div class="row"><c:forEach items="${q.options}" var="op"><div class="col-md-6"><input type="radio"> ${op.optionText}</div></c:forEach></div></div></c:if></c:forEach>
<h4 class="mt-3">B. Multiple Answer</h4>
<c:forEach items="${paperQuestions}" var="q"><c:if test="${q.type=='multiple'}"><div><p>${q.text}</p><div class="row"><c:forEach items="${q.options}" var="op"><div class="col-md-6"><input type="checkbox"> ${op.optionText}</div></c:forEach></div></div></c:if></c:forEach>
<h4 class="mt-3">C. Text Answer</h4>
<c:forEach items="${paperQuestions}" var="q"><c:if test="${q.type=='text'}"><div><p>${q.text}</p><textarea class="form-control"></textarea></div></c:if></c:forEach>
</div></div></body></html>
