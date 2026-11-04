<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 1:1게시판 상세보기 --%>
<div class="boardwrap">
	<h3>1:1게시글 상세보기</h3>
	<table width="700" border = "1">
		<tr>
			<th>글제목</th>
			<td>
				<input type="text" name = "inq_title"/>
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>
				<input type="text" name = "user_name" readonly/>
			</td>
		</tr>
		<tr>
			<th>글내용</th>
			<td>
				<input type="text" name = "inq_content"/>
			</td>
		</tr>
		<tr>
			<td colspna="2">
				<input type="button" onclick="location.href='inquiryUpdateProc.do'" value="수정하기"/>
				<input type = "button" onclick="location.href='inquiryDeleteProc.do'" value="삭제하기"/>
			</td>
		</tr>
	</table>
</div>
