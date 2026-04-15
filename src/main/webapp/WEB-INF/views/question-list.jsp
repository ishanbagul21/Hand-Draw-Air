<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="card mt-4"><div class="card-body">
<h3>Question List Page</h3>
<table class="table table-bordered"><thead><tr><th>Question</th><th>Mark</th><th>Action</th></tr></thead><tbody>
<c:forEach items="${questions}" var="q"><tr><td>${q.questionText}</td><td>${q.marks}</td><td><button class="btn btn-sm btn-primary">Edit</button> <button class="btn btn-sm btn-danger">Delete</button></td></tr></c:forEach>
<c:if test="${empty questions}"><tr><td colspan="3">No questions yet</td></tr></c:if>
</tbody></table>
</div></div>
