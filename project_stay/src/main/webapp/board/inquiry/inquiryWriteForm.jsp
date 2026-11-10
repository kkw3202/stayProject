<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/inquiryWrite.css"/>
<%-- 1:1게시판 글쓰기 --%>
<div class="boardwrap">
	<h3>1:1게시글 작성하기</h3>

	<form action="inquiryWriteProcCon.do" method="post" name="inquiryWriteForm">
		<table class="inquiryWriteTable">
			<tr>
				<th class="label">글제목</th>
				<td class="data">
					<input type="text" name = "inq_title" class="inq_title"/>
				</td>
			</tr>
			<tr>
				<th class="label">글내용</th>
				<td class="data">
					<textarea name="inq_content"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input class="btn" type="button" onclick="inquiryBoardWriteException()" value="등록하기"/>
					<input class="btn" type = "button" onclick="location.href='inquiryListProc.do'" value="취소하기"/>
				</td>
			</tr>
		</table>
	</form>
</div>

<script>
    function inquiryBoardWriteException(){
        let f= document.inquiryWriteForm;
        if(f.inq_title.value.trim()==""){
            alert("글제목을 입력하세요");
            f.inq_title.focus();
            return;
        }
        if(f.inq_content.value.trim()==""){
            alert("글내용을 입력하세요");
            f.inq_content.focus();
            return;
        }
        alert("1:1문의가 등록되었습니다!");
        f.submit();
    }
</script>

