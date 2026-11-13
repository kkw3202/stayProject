<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/mypageReviewMod.css"/>

<div class="myReviewModWrap">
	<form action = "MyRevModProcCon.do" method="post" name="modForm">
		<%-- hidden으로 리뷰코드 넘기기 --%>
		<input type="hidden" value="${rdto.rev_code }" name="rev_code" />
		<table class="review_mod_table">
			<tr>
				<th class="label">숙소명</th>
				<td class="data">${rdto.acc_name }</td>
			</tr>
			<tr>
				<th class="label">평점</th>
				<td class="data">
					<select name = "rev_rating">
						<option value="5" ${rdto.rev_rating == 5? 'selected' : ''}>5점</option>
						<option value="4" ${rdto.rev_rating == 4? 'selected' : ''}>4점</option>
						<option value="3" ${rdto.rev_rating == 3? 'selected' : ''}>3점</option>
						<option value="2" ${rdto.rev_rating== 2? 'selected' : '' }>2점</option>
						<option value="1" ${rdto.rev_rating == 1? 'selected' : '' }>1점</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="label">리뷰내용</th>
				<td class="data">
					<input type="text" value="${rdto.rev_content }" name = "rev_content"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input class="btn" type="button" value="수정등록" onclick="chkForm()"/>
					<input class="btn" type = "button" onclick="cancelform()" value="취소하기"/>
				</td>
			</tr>
		</table>
	</form>
</div>

<script>
	function chkForm(){
		let f = document.modForm;
		if(f.rev_rating.value== null){
			alert("평점을 선택하세요");
			return;
		}
		if(f.rev_content.value.trim()==""){
			alert("리뷰내용을 입력해주세요");
			f.rev_content.focus();
			return;
		}
		if(confirm("위 내용대로 수정하시겠습니까?")){
			f.submit();
		}
	} 
	// 취소하기 버튼
	function cancelform(){
		if(confirm("업데이트 취소하시겠습니까?")){
			history.go(-1);
		}
	}
</script>