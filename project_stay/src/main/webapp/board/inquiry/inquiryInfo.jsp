<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/inquiryInfo.css"/>
<%-- 1:1게시판 상세보기 --%>
<div class="boardwrap">
	<h3>1:1게시글 상세보기</h3>
	<table>
		<tr>
			<td colspan="2">
				<%-- 관리자로 접속시 : 답글쓰기만 보여야함. --%>
                <c:if test = "${sessionScope.email eq 'admin@naver.com'}">
	                <input type="button" value="답글쓰기" onclick="location.href='inquiryRewriteProc.do?inq_code=${idto.inq_code}&inq_ref=${idto.inq_ref}&inq_re_step=${idto.inq_re_step}'">
                </c:if>
                <%-- 관리자가 아니면서 일반 회원인 경우만 수정/삭제하기 버튼 보이기 --%>
                <c:if test = "${sessionScope.email ne 'admin@naver.com' && not empty sessionScope.email}">
                	<input type="button" onclick="location.href='inquiryUpdateProc.do?inq_code=${idto.inq_code}'" value="수정하기"/>
					<input type = "button" onclick="location.href='inquiryDeleteProc.do?inq_code=${idto.inq_code}'" value="삭제하기"/>
                </c:if>
                <%-- 아무나 다 볼 수 있는 목록보기는 if문 하지 않는다. --%>
                <input type="button" value="목록보기" onclick="location.href='inquiryListProc.do'">
			
				<input type="button" onclick="location.href='inquiryUpdateProc.do'" value="수정하기"/>
				<input type = "button" onclick="location.href='inquiryDeleteProc.do'" value="삭제하기"/>
			</td>
		</tr>
		<tr>
			<th>글제목</th>
			<td>${idto.inq_title }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${idto.user_name }</td>
		</tr>
		<tr>
			<th>작성일시</th>
			<td>${idto.inq_created_datetime }</td>
		</tr>
		<tr>
			<th>글내용</th>
			<td>${idto.inq_content }</td>
		</tr>
		<tr>
			<th>답변상태</th>
			<c:choose>	
				<c:when test="${ilist.inq_chk_status == 0}">
					<td>답변대기중</td>
				</c:when>
				<c:otherwise>
					<td>답변완료</td>
				</c:otherwise>
			</c:choose>
		</tr>
		
	</table>
</div>
