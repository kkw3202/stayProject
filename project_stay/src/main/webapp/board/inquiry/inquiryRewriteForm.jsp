<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%-- 관리자 답변다는 폼 --%>
<div class="boardWrap">
	<form action = "inquiryRewriteProcCon.do" method="post" name="inquiryRewriteForm">
		<%-- inq_code, ref, 유저코드, 문의글 제목도 숨겨서 넘김 --%>
		<input type="hidden" name="inq_code" value="${idto.inq_code }"/>
		<input type = "hidden" name="inq_ref" value="${idto.inq_ref }"/>
		<input type = "hidden" name="re_inq_title" value="└Re:${idto.inq_title }"/>
		<input type="hidden" name="user_code" value="${idto.user_code}">
		
		<table class="rewriteTable">
			<tr>
				<th class="label">문의글 제목</th>
				<td class="date">${idto.inq_title }</td>
			</tr>
			<tr>
				<th class="label">문의자</th>
				<td class="date">${idto.user_name }</td>
			</tr>
			<tr>
				<th class="label">문의글 작성일시</th>
				<td class="date">${fn:substring(idto.inq_created_datetime, 0, 10)}</td>
			</tr>
			<tr>
				<th class="label">문의한 내용</th>
				<td class="date">${idto.inq_content }</td>
			</tr>
			<tr>
				<th class="label">답변하기</th>
				<td class="date">
					<textarea name="inq_content"></textarea>
				</td>
			</tr>
			<tr>
				<td class="btn" colspan="2">
					<input type = "button" onclick="formChk()" value="등록하기"/>
					<input type = "button" value="취소하기" onclick="cancelRewrite()"/>
				</td>
			</tr>
		</table>	
	</form>
</div>

<script>
	// 취소하기 함수
	function cancelRewrite(){
		history.go(-1);
				
	}
	// formChk 함수 (예외처리)
	function formChk(){
		let f = document.inquiryRewriteForm;
		if(f.inq_content.value.trim() == ""){
			alert("아직 답변글을 달지 않았습니다.");
			f.inq_content.focus();
			return;
		}
		
		f.submit();
	}
</script>
