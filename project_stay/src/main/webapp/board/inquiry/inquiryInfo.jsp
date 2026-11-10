<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri ="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="style/inquiryInfo.css"/>


<%-- 1:1게시판 상세보기 --%>
<div class="boardwrap">
	<h3>1:1게시글 상세보기</h3>
	<table class= "infoTable">

		<tr>
			<td colspan="2">
				<%-- 관리자로 접속시 : 답글쓰기만 보여야함.(이미 답변이 달린 글은 다시 답변 못함) --%>
                <c:if test = "${sessionScope.user_email eq 'admin@naver.com' and idto.inq_re_step == 0 and idto.inq_chk_status==0}">
	                <input class= "btn" type="button" value="답글쓰기" onclick="location.href='inquiryRewriteProc.do?inq_code=${idto.inq_code}&inq_ref=${idto.inq_ref}&inq_re_step=${idto.inq_re_step}'">
                </c:if>
                <%-- 관리자가 아니면서 일반 회원인 경우만 수정/삭제하기 버튼 보이기 + re_step==0인 경우에만 수정/삭제하기 보이기 --%>
                <c:if test = "${sessionScope.user_email ne 'admin@naver.com' && not empty sessionScope.user_email && idto.inq_re_step==0}">
                	<%-- 단, 1. 답변대기중인 경우(inq_chk_status==0) 인경우 => 수정가능 --%>
                	<c:choose>
	                	<c:when test="${idto.inq_chk_status == 0}">
		                	<input class= "btn" type="button" onclick="location.href='inquiryUpdateProc.do?inq_code=${idto.inq_code}'" value="수정하기"/>
	                	</c:when>
	                	<c:otherwise>
	                		<input class= "btn" type = "button" onclick="alert('관리자가 답변을 작성한 문의글은, 수정할 수 없습니다.')" value="수정하기"/>
	                	</c:otherwise>
                	</c:choose>
                	<%-- 관리자가 답변을 단 상태이면, 삭제불가 --%>
                	<c:choose>
						<%-- 1. 답변대기중인 경우(inq_chk_status==0) 인경우 => 삭제가능 --%>
						<c:when test="${idto.inq_chk_status == 0}">
							<input class= "btn" type = "button" onclick="deleteChk(${idto.inq_code})" value="삭제하기"/>
						</c:when>
				
						<%-- 2. 답변이 완료된 상태인 경우 => 삭제불가 메시지 --%>
						<c:otherwise>
							<input class= "btn" type = "button" onclick="alert('관리자가 답변을 작성한 문의글은, 삭제할 수 없습니다.')" value="삭제하기"/>
						</c:otherwise>
					</c:choose>
                	
                </c:if>
                <%-- 아무나 다 볼 수 있는 목록보기는 if문 하지 않는다. --%>
                <input class= "btn" type="button" value="목록보기" onclick="location.href='inquiryListProc.do'">
			</td>
		</tr>
		<tr>
			<th class="thinfo">글제목</th>
			<td>${idto.inq_title }</td>
		</tr>
		<tr>
			<th class="thinfo">작성자</th>
			<td>
				<%-- 답글인 경우에는, 작성자에 "관리자"라고 표시 --%>
				<c:if test = "${idto.inq_re_step ==1 }">
					관리자
				</c:if>
				<c:if test= "${idto.inq_re_step==0 }">
					${idto.user_name }
				</c:if>
				<%-- 답글인 경우에는, 작성자에 "관리자"라고 표시 --%>
			</td>
		</tr>
		<tr>
			<th class="thinfo">작성일시</th>
			<td>${fn:substring(idto.inq_created_datetime, 0, 10)}</td>
		</tr>
		<tr>
			<th class="thinfo">글내용</th>
			<td>${idto.inq_content }</td>
		</tr>
		<tr>
			<th class="thinfo">답변상태</th>
			<c:choose>
				<c:when test="${idto.inq_chk_status == 0}">
					<td>답변대기중</td>
				</c:when>
				<c:otherwise>
					<td>답변완료</td>
				</c:otherwise>
			</c:choose>
		</tr>
		
	</table>
</div>

</section>


<script>
	// 삭제처리 함수 (소프트 삭제)
	function deleteChk(inq_code){
		alert("문의글을 삭제하겠습니다.");
		location.href="inquiryDeleteCon.do?inq_code="+inq_code;
	}

</script>

