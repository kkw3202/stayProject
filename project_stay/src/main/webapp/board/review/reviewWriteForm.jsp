<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<div class="boardWrap">
	<form action = "reviewWriteProcCon.do" method="post" name="rform">
		<%-- 회원명 숨겨서 보내기 --%>
		<input type="hidden" value="${user_name }" name="user_name"/>
		<input type ="hidden" value="${acc_code }" name="acc_code"/>
		<table>
			<%-- 왼쪽에는 숙소 이미지 --%>
			<%-- <tbody class="img_box">
				<tr>
					<td>
						<img src="${acc_img }" alt="${acc_name }"/>
					</td>
				</tr>
			</tbody> --%>
		
			<%-- 숙소명 --%>
			<tr>
				<th class="label">숙소명</th>
				<td class="data">${acc_name }</td>
			</tr>	
			<tr>
				<th class="label">평점<br/>(5점 만점)</th>
				<td class="data">
					<select name="rev_rating">
						<option value="5">5점</option>
						<option value="4">4점</option>
						<option value="3">3점</option>
						<option value="2">2점</option>
						<option value="1">1점</option>
					</select>
				</td>
			</tr>		
			<tr>
				<th class="label">리뷰내용</th>
				<td class="data">
					<textarea class="rev_content" name="rev_content"></textarea>
				</td>
			</tr>		
			<tr>
				<td class="btn" colspan="2">
					<input type="button" onclick="chkForm()" value="등록하기"/>
					<input type="button" onclick="cancelReview()" value="취소하기"/>
				</td>
			</tr>		
		</table>
	</form>
</div>
<script>
	// 취소하기 함수
	function cancelReview(){
		if(confirm("작성을 취소하시겠습니까?")){
			history.go(-1);
		}
	}
	
	// 폼 예외처리하기
	function chkForm(){
		let f = document.rform;
		if(f.acc_code.value==0||f.acc_code.value==null){
			alert("숙소명을 입력하세요");
			f.acc_code.focus();
			return;
		}
		if(f.rev_rating.value=="0"|| f.rev_rating.value==null || f.rev_rating.value=="none"){
			alert("평점을 선택하세요");
			return;
		}
		if(f.rev_content.value.trim()==""){
			alert("리뷰내용을 입력하세요");
			f.rev_content.focus();
			return;
		}
		if(confirm("리뷰 등록하시겠습니까?")){
			f.submit();
		}
	}
</script>