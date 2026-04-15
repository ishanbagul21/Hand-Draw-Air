<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal fade" id="generateModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
<form method="post" action="${pageContext.request.contextPath}/teacher/paper/generate">
<div class="modal-header"><h5 class="modal-title">Generate Paper Question Form</h5></div>
<div class="modal-body">
<input type="hidden" name="classId" value="1"><input type="hidden" name="title" value="AI Generated Exam">
<label>Easy Questions</label><input class="form-control mb-2" type="number" name="easyCount" value="2">
<label>Medium Questions</label><input class="form-control mb-2" type="number" name="mediumCount" value="2">
<label>Hard Questions</label><input class="form-control mb-2" type="number" name="hardCount" value="1">
</div><div class="modal-footer"><button class="btn btn-primary">Save</button></div></form>
</div></div></div>
