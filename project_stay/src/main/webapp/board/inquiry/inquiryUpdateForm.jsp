<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- 1:1게시글 작성했던 글 수정하기(업데이트) --%>
<div class="boardwrap">
	<h3>1:1게시글 수정하기</h3>
	<form action="inquiryUpdateProcCon.do" method="post" name="inquiryUpdateForm">
		<%-- 글번호는 숨겨서 넘기기 --%>
		<input type = "hidden" name="inq_code" value="${idto.inq_code }"/>
		<table class="inquiryUpdateTable">
			<tr>
				<th class="label">글제목</th>
				<td class="data">
					<input type="text" name = "inq_title" value="${idto.inq_title }"/>
				</td>
			</tr>
			<tr>
				<th class="label">작성자</th>
				<td class="data">${idto.user_name}</td>
			</tr>
			<tr>
				<th class="label">작성일시</th>
				<td class="data">${idto.inq_created_datetime}</td>
			</tr>
			<tr>
				<th class="label">글내용</th>
				<td class="data">
					<input type = "text" name = "inq_content" value="${idto.inq_content }"/>
				</td>
			</tr>
			<tr>
				<th class="label">글변상태</th>
				<%-- 답변상태 inq_chk_status --%>
				<c:choose>	
					<c:when test="${idto.inq_chk_status == 0}">
						<td>
	       					 <span class="status wait">답변대기중</span>
	       				</td>
					</c:when>
					<c:otherwise>
						<td>
	       					 <span class="status done">답변완료</span>
	       				</td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td colspan="2">
					<input class="btn" type="button" onclick="inquiryBoardUpdateException()" value="수정하기"/>
					<input class="btn" type = "button" onclick="inquiryCancelUpdate()" value="취소하기"/>
				</td>
			</tr>
		</table>
	
	</form>
</div>

<script>
	// 취소하기 버튼 누르면, 전 페이지 이동
	function inquiryCancelUpdate(){
		if(confirm("1:1 문의 수정을 중단하시겠습니까?")){
			history.go(-1);
		}
	}
	// 예외처리하기
	function inquiryBoardUpdateException(){
		let f = document.inquiryUpdateForm;
		if(f.inq_title.value.trim() == ""){
			alert("글제목을 입력하세요");
			f.inq_title.focus();
			return;
		}
		if(f.inq_content.value.trim() == ""){
			alert("글내용을 입력하세요");
			f.inq_content.focus();
			return;
		}
		if(confirm("위 내용대로 1:1문의 수정하시겠습니까?")){
			f.submit();
		}
	}

</script>
    