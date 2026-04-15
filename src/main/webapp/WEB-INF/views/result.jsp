<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html><head><title>Result</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"></head><body>
<div class="container py-5"><div class="card p-4"><h2>Your Score: ${score}</h2><h4>AI Suggestions</h4><ul><c:forEach items="${suggestions}" var="s"><li>${s}</li></c:forEach></ul></div></div>
</body></html>
