<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html><head><title>Login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"></head>
<body class="bg-light d-flex align-items-center" style="min-height:100vh"><div class="container"><div class="row justify-content-center"><div class="col-md-5">
<div class="card shadow"><div class="card-header bg-primary text-white">Login</div><div class="card-body">
<form method="post" action="${pageContext.request.contextPath}/login">
<input class="form-control mb-3" name="email" type="email" placeholder="Email" required>
<input class="form-control mb-3" name="password" type="password" placeholder="Password" required>
<select class="form-select mb-3" name="role"><option value="admin">Admin</option><option value="teacher">Teacher</option><option value="student">Student</option></select>
<button class="btn btn-primary w-100">Sign in</button>
<c:if test="${not empty error}"><div class="text-danger mt-2">${error}</div></c:if>
</form></div></div></div></div></div></body></html>
