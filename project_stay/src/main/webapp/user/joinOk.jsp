<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
<<<<<<< HEAD
	alert("${sessionScope.msg}");
	location.href="${pageContext.request.contextPath}/index.jsp";

=======
	alert("${msg}");
	location.href="/index.jsp";
>>>>>>> main
</script>

<%
    session.removeAttribute("msg");
%>
