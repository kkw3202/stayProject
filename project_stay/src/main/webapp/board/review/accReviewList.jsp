<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="accReviewList" id="accReviewSection">
	<h3>리뷰 (${revCount}건 )</h3>
	<%-- 리뷰작성하기 버튼(로그인 한 사람 중, 예약이 된 사람중에서 퇴실예정일 날부터 리뷰 작성 가능 --%>
	<c:if test="${not empty sessionScope.user_email and not empty notList }">
		<div class="writeBox">
			<input type="button" class="write_btn" value="리뷰작성" onclick="goWriteForm(${notList[0].acc_code})"/>
		</div>
	</c:if>
	
	<%-- 리뷰가 없을때 => 등록된 리뷰가 없습니다. --%>
	<c:if test="${empty rlist }">
		<span class="no_review">아직 등록된 리뷰가 없습니다.</span>
	</c:if>	
	<%-- 리뷰가 있는 경우 --%>
	<c:if test="${not empty rlist }">
		<table class="review_table">
			<c:forEach var="arr" items="${rlist}">
				<tr>
					<th class="label">작성자</th>
					<td class="data">${arr.user_name }</td>
				</tr>
				<tr>
					<th class="label">작성시간</th>
					<td class="data">${arr.rev_created_datetime }</td>
				</tr>
				<tr>
					<th class="label">평점</th>
					<td class="data">
						<c:choose>
							<c:when test="${arr.rev_rating ==5}"><span class="star">★★★★★</span></c:when>
							<c:when test="${arr.rev_rating ==4}"><span class="star">★★★★☆</span></c:when>
							<c:when test="${arr.rev_rating ==3}"><span class="star">★★★☆☆</span></c:when>
							<c:when test="${arr.rev_rating ==2}"><span class="star">★★☆☆☆</span></c:when>
							<c:otherwise><span class="star">★☆☆☆☆</span></c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th class="label">리뷰내용</th>
					<td class="data">${arr.rev_content }</td>
				</tr>
				<tr>
					<td colspan = "2" >
						<%-- 좋아요 박스 --%>
						<div class="like">
							<span class="like_badge">👍</span>좋아요 ${arr.rev_like}
							<%-- 본인이 쓴 글이 아닌 경우에만, 추천하기 버튼 보임 --%>
							<c:if test="${not empty sessionScope.user_email and sessionScope.user_email ne arr.user_email }">
								<input type="button" class="btns" onclick="chkLike(${arr.rev_code}, ${arr.acc_code})" value="추천하기"/>
							</c:if>
						</div>
						
						<%-- 본인이 쓴 리뷰에는, 삭제하기 / 수정하기 버튼 보임 --%>
						<c:if test="${not empty sessionScope.user_email and sessionScope.user_email eq arr.user_email }">
							<input type="button" class="btns" onclick="deleteReview(${arr.rev_code},${arr.acc_code})" value="삭제하기"/>				
							<input type="button" class="btns" onclick="modReview(${arr.rev_code})" value="수정하기"/>				
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<%-- 페이징  --%>
		<%-- 페이징 부분 --%>
		<div class="paging">
			<c:if test = "${startPage > pageBlock }">
				<input type="button" onclick="pageBtn1(${startPage},${pageBlock})" value="이전"/>
			</c:if>
			
			<%-- 페이징 번호 --%>
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<%-- 현재 페이지에는 클래스 이름 넣어, 표시하기 --%>
				<c:choose>
					<c:when test="${i == currentPage}">
						<input class="currentPage_btn" type="button" onclick="pageBtn2(${i})" value="${i }"/>
					</c:when>
					<c:otherwise>
						<input type="button" onclick="pageBtn2(${i})" value="${i }"/>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			<%-- 페이징 번호 --%>
			<c:if test = "${endPage < pageCount}">
				<input type="button" onclick="pageBtn3(${startPage},${pageBlock})" value="다음"/>
			</c:if>
		</div>	
		
		
	</c:if>

</div>

<script>
	// 좋아요 버튼 함수
	function chkLike(rev_code, acc_code){
		location.href="reviewLikeCon.do?rev_code="+rev_code+"&acc_code="+acc_code;
	}
	// 본인 리뷰 삭제 함수
	function deleteReview(rev_code, acc_code){
		if(confirm("해당 리뷰를 삭제하시겠습니까?")){
			location.href="reviewDeleteCon.do?rev_code="+rev_code+"&acc_code="+acc_code;
		}
	}
	// 본인 리뷰 수정 함수
	function modReview(rev_code){
		location.href="MyRevModProc.do?rev_code="+rev_code;
	}

	// 페이징 함수
	function pageBtn1(startPage, pageBlock){
		location.href="accInfoProc.do?acc_code=${pdto.acc_code}&book_checkin_date=${book_checkin_date}"
				+ "&book_checkout_date=${book_checkout_date}&pageNum=" + (startPage - pageBlock) + "#accReviewSection";
	}
	function pageBtn2(i){
		location.href="accInfoProc.do?acc_code=${pdto.acc_code}&book_checkin_date=${book_checkin_date}"
				+ "&book_checkout_date=${book_checkout_date}&pageNum=" + i + "#accReviewSection";
	}
	function pageBtn3(startPage, pageBlock){
		location.href="accInfoProc.do?acc_code=${pdto.acc_code}&book_checkin_date=${book_checkin_date}"
				+ "&book_checkout_date=${book_checkout_date}&pageNum=" + (startPage + pageBlock) + "#accReviewSection";
	}
	
	// 리뷰 작성하는 폼으로 이동하는 메소드
	function goWriteForm(acc_code){
		location.href="reviewWriteProc.do?acc_code="+acc_code;	
	}


</script>
