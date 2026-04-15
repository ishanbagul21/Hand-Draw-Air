<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html><html><head><title>Question Paper Details</title><jsp:include page="common/header.jspf"/></head><body>
<jsp:include page="common/sidebar.jspf"/>
<div class="content-wrapper">
<div class="paper-shell">
<div class="d-flex justify-content-between"><h4>Question Paper Details</h4><div><button class="btn btn-primary btn-sm">Edit</button> <button class="btn btn-danger btn-sm">Delete</button></div></div><hr>
<p><b>Class:</b> BSIT - Test102</p><p><b>Question Paper Title:</b> Sample Exam 2</p>
<div class="my-3"><button class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#generateModal">Generate Question Paper</button></div>
<jsp:include page="generate-modal.jsp"/>
<jsp:include page="question-list.jsp"/>
</div></div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
