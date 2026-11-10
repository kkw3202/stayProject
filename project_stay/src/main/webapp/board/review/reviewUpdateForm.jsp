<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri= "http://java.sun.com/jsp/jstl/functions"%>

<div class="boardWrap">
	<form action="reviewUpdateProcCon.do" method="post" name="reviewUpdateForm">
		<input type ="hidden" value="${rev_code }" name = "rev_code"/>
		<table>
			<tr>
				<th class="label">숙소명</th>
				<td class="data">
					<input type="text" value="${rdto.acc_name }" name = "acc_name" readonly/>
				</td>
			</tr>
			<tr>
				<th class="label">평점</th>
				<td class="data">
					<select name = "rev_rating">
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
		let f = document.reviewUpdateForm;
		if(f.rev_rating.value== null){
			alert("평점을 선택하세요");
			return;
		}
		if(f.rev_content.value.trim()==""){
			alert("리뷰내용을 입력해주세요");
			f.rev_content.focus();
			return;
		}
		alert("수정완료하였습니다!");
		f.submit();
	} 
	// 취소하기 버튼
	function cancelform(){
		alert("업데이트 취소하겠습니다!");
		history.go(-1);
	}
</script>