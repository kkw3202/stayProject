<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="style/reviewList.css"/>

<div class="boardWrap">
	
	<%-- 콤보박스 (숙소 선택하면 해당 숙소만 나오기) --%>
	<%-- form으로 날리는 방식으로 진행 --%>
	<form action="reviewListProc.do" method="get" name = "acc_choice">
		<div class="choice_label">숙소명</div>
		<div class="choice_data">
			<select class="searchType" onchange="search(this.form)" name="searchType">
				<option value= "0" ${searchType== 0 ? 'selected' : ''} >숙소명</option>
				<c:forEach var="acc" items="${alist }" >
					<option value="${acc.acc_code}" ${searchType==acc.acc_code?'selected':''}>${acc.acc_name }</option>
				</c:forEach>
			</select>
		</div>
	</form>
	
	<%--  --%>
	
	<table class="review_table">
		<tr>
			<%-- 로그인 하지 않거나, 관리자인 경우에는 내가쓴리뷰 안보이게 처리 --%>
			<%-- 위치이동가능 : 리뷰작성하기 / 내가쓴리뷰  --%>
			<c:if test="${not empty sessionScope.user_email and sessionScope.user_email ne 'admin@naver.com'}">
				<td class="btn" colspan="2">
					<input type="button" onclick="goWriteForm()" value="리뷰작성"/>
					<input type = "button" onclick = "" value = "내가쓴리뷰"/>
				</td>
			</c:if>
		</tr>
		<c:forEach var="arr" items="${rlist}">
			<tr>
				<th class="label">숙소명</th>
				<td class="data">${arr.acc_name }</td>
			</tr>
			<tr>
				<%-- 첫글자만 나오게 처리 --%>
				<th class="label">작성자</th>
				<td class="data">${fn:substring(arr.user_name, 0, 1)}**</td>
			</tr>
			<tr>
				<th class="label">작성시간</th>
				<td class="data">${arr.rev_created_datetime }</td>
			</tr>
			<tr>
				<th class="label">평점</th>
				<td class="data">${arr.rev_rating }</td>
			</tr>
			<tr>
				<th class="label">리뷰내용</th>
				<td class="data">${arr.rev_content }</td>
			</tr>
			<tr>
				<td colspan="2">
					<%-- 좋아요 부분 --%>
					<%-- 리뷰를 보고, 다른 사람들이 리뷰에 대해서, 도움이 되는 경우 추천 버튼 --%>
					<div class="like">
						<span class="like_badge">👍</span>좋아요 ${arr.rev_like }
						<%-- 로그인되어 있고, 내가 쓴 글이 아닌 경우에만 추천하기 버튼 가능 --%>
						<c:if test="${not empty sessionScope.user_email and sessionScope.user_email ne arr.user_email }">
							<input type="button" class="btn_delete_like" onclick="chkLike(${arr.rev_code})" value="추천하기"/>
						</c:if>
					</div>
					
					<%-- 리뷰삭제하기 : 작성자에게만 삭제하기 버튼 보여야함 --%>
					<c:if test="${not empty sessionScope.user_email and sessionScope.user_email eq arr.user_email }">
						<input type="button" class="btn_delete_like" onclick="deleteReview(${arr.rev_code})" value="삭제하기"/>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>


	<%-- 페이징 부분 --%>
	<div class="paging">
		<c:if test = "${startPage > pageBlock }">
			<a href="reviewListProc.do?pageNum=${startPage-pageBlock}">[이전]</a>
		</c:if>
		
		<%-- 페이징 번호 --%>
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<a href="reviewListProc.do?pageNum=${i}">${i}</a>
		</c:forEach>
		
		<%-- 페이징 번호 --%>
		<c:if test = "${endPage < pageCount}">
			<a href = "reviewListProc.do?pageNum=${startPage+pageBlock}">[다음]</a>
		</c:if>
	</div>	

</div>

<script>
	// 좋아요 수량증가
	function chkLike(rev_code){
		alert("해당 리뷰 추천하겠습니다!");
		// 해당 리뷰번호와 함께, 좋아요 계산하는 서블릿으로 넘기기
		location.href="reviewLikeCon.do?rev_code="+rev_code;
	}
	
	// 삭제하기 함수(본인 글만 삭제 가능) (소프트삭제)
	function deleteReview(rev_code){
		alert("해당 리뷰 삭제하겠습니다!");
		// 해당 리뷰번호와 함께, 삭제하는 서블릿으로 넘기기
		location.href="reviewDeleteCon.do?rev_code="+rev_code;
	}
	// 숙소명 onchange() 함수 제출하기
	function search(f){
		f.submit();
	}
	// 리뷰작성하러가는 함수
	function goWriteForm(){
		// 테이블 위 콤보박스 폼(acc_choice)에서, searchType 정보 가져오기 
		let acc_code = document.acc_choice.searchType.value;
		location.href="reviewWriteProc.do?searchType="+acc_code;
	}
	
</script>