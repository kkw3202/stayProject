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
				<th class="formTitle">글제목</th>
				<td>
					<input type="text" name = "inq_title" class="inq_title"/>
				</td>
			</tr>
			<%-- <tr>
				<th>작성자 메일주소</th>
				작성자 이름을 바로 불러오려면,
				로그인 서블릿에서 
				// 로그인 성공 시 서블릿에서 이렇게 담아둔 경우
				session.setAttribute("user", userDTO);
				${sessionScope.user.user_name}
				${sessionScope.user.user_email}
				// 로그인 서블릿에서 회원명도 개별로 담아둔 경우:
				session.setAttribute("user_email", email);
				session.setAttribute("user_name", userName);
				session.setAttribute("user_code", userCode);
				${sessionScope.user_name}
				
				<td>
					${sessionScope.user_email}
				</td>
			</tr> --%>
			<tr>
				<th class="formContent">글내용</th>
				<td>
					<input type="text" name = "inq_content" class="inq_content"/>
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
        let inqWriteForm = document.inquiryWriteForm;
        let title = document.getElementsByClassName("inq_title")[0];
        let content = document.getElementsByClassName("inq_content")[0];
        if(!title.value){
            alert("글제목을 입력하세요");
            title.focus();
            return;
        }
        if(!content.value){
            alert("글내용을 입력하세요");
            content.focus();
            return;
        }
        alert("1:1문의가 등록되었습니다.");
        inqWriteForm.submit();
    }
</script>