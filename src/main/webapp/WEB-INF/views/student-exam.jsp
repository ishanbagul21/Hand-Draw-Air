<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html><head><title>Student Exam</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"></head><body class="bg-light">
<div class="container py-4"><div class="card"><div class="card-header d-flex justify-content-between"><span>Start Exam</span><span id="timer">30:00</span></div><div class="card-body">
<form method="post"><input type="hidden" name="paperId" value="${paperId}">
<c:forEach items="${paperQuestions}" var="q" varStatus="loop"><div class="mb-3"><p>${loop.index+1}. ${q.text}</p>
<c:forEach items="${q.options}" var="op"><div><input type="checkbox" name="q_${q.id}" value="${op.id}"> ${op.optionText}</div></c:forEach>
<c:if test="${q.type=='text'}"><textarea class="form-control" name="q_${q.id}_text"></textarea></c:if>
</div></c:forEach><button class="btn btn-primary">Submit</button></form>
</div></div></div>
<script>
let sec=1800;setInterval(()=>{sec--;const m=String(Math.floor(sec/60)).padStart(2,'0');const s=String(sec%60).padStart(2,'0');document.getElementById('timer').innerText=`${m}:${s}`;if(sec<=0)document.forms[0].submit();},1000);
</script></body></html>
