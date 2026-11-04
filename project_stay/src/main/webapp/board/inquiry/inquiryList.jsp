<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*"%>
<%@ page import = "DBPKG.Util" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 1:1게시판 --%>
<div class="boardwrap">
	<h3>1:1게시판</h3>
	<table width="700" border="1">
		<tr colspan="5" align="center">
			<select class="inq_search">
				<option value = "">제목</option>
				<option value = "">작성자</option>
				<option value = "">답변상태</option>
			</select>
			<input type="text" placeholder="검색" />
			<input type = "button" value="검색"/>
		</tr>
		<tr>
			<th>순번</th>
			<th>글제목</th>
			<th>작성자</th>
			<th>작성일시</th>
			<th>답변상태</th>
		</tr>
		
		<c:set var="number" value = "${number}"/>
		<c:forEach var="ilist" items="${ilist}">
			<tr>
				<td>${number}</td>
				<%-- 글제목 inq_title --%>
				<td>${ilist.inq_title}</td>
				<%-- 작성자 user_name --%>
				<td>${ilist.user_name}</td>
				<%-- 작성일시 inq_created_datetime --%>
				<td>${ilist.inq_created_datetime}</td>
				<%-- 답변상태 inq_chk_status --%>
				<c:choose>	
					<c:when test="${ilist.inq_chk_status == 0}">
						<td>답변대기중</td>
					</c:when>
					<c:otherwise>
						<td>답변완료</td>
					</c:otherwise>
				</c:choose>
			</tr>
			<c:set var="number" value="${number-1}"/>
		</c:forEach>
	
		<tr colspan="5" align="left">
			<td colspan="5">
				<input type="button" onclick="location.href='index.jsp?section=board/inquiry/inquiryInfo.jsp'" value = "글쓰기"/>
				<input type="button" onclick="" value = "내가 쓴 글보기"/>
			</td>
		</tr>
	</table>
	
	<%-- 페이징 부분 --%>
	<div class="paging">
		<c:if test = "${startPage > pageBlock }">
			<a href="inquiryListProc.do?pageNum=${startPage-pageBlock}">[이전]</a>
		</c:if>
		
		<%-- 페이징 번호 --%>
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<a href="inquiryListProc.do?pageNum=${i}">[${i}]</a>
		</c:forEach>
		
		<%-- 페이징 번호 --%>
		<c:if test = "endPage < pageCount">
			<a href = "inquiryListProc.do?pageNum=${startPage+pageBlock}">[다음]</a>
		</c:if>
	</div>	

</div>
<script>
	// 게시판 글쓰기 버튼 관련한 스크립트

</script>

    
    