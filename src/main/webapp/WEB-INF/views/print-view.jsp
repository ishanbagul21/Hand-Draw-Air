<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html><head><title>Print View</title><link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet"></head><body>
<div class="content-wrapper" style="margin-left:0"><div class="paper-shell"><h3 class="text-center">Print View - Question Paper</h3>
<c:forEach items="${paperQuestions}" var="q"><p><b>Q:</b> ${q.text}</p></c:forEach>
<button class="no-print" onclick="window.print()">Print</button>
</div></div></body></html>
