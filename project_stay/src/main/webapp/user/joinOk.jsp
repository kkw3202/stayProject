<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	alert("${sessionScope.msg}");
	location.href="${pageContext.request.contextPath}/index.jsp";

</script>

<%
    session.removeAttribute("msg");
%>
