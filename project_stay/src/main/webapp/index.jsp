<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/index.css"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>머물 수 있는 곳이 많은 머물:多</title>
<link rel="stylesheet" href="style/index.css">


</head>

<body>
	<div class="container">
		<jsp:include page="header.jsp" />
		<c:set var="sectionMain" value="index_sectionProc.do"></c:set>
		<c:set var="section" value="${param.section }"></c:set>
		<c:choose>
			<c:when test="${not empty section != ''  }">
				<jsp:include page="${section }" />
			</c:when>
			<c:otherwise>
				<jsp:include page="${sectionMain}" />
			</c:otherwise>
		</c:choose>
		
		<jsp:include page="footer.jsp" />
	</div>
	
</body>
</html>