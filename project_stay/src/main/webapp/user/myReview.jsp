<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="style/mypageReview.css"/>

<div class="myReviewWrap">
	<h3>내 리뷰 리스트</h3>
	<%-- 작성한 리뷰가 없는 경우 --%>
	<c:if test="${empty rlist }">
		<div class="no_review">
			<p>작성한 리뷰가 아직 없습니다.</p>
		</div>
	</c:if>
	<%-- 작성한 리뷰가 있는 경우 --%>
	<c:if test="${not empty rlist }">
		<c:forEach var="arr" items="${rlist}">
			<table class="review_table">
				<%-- 왼쪽 : 숙소 이미지  --%>
				<tbody class="left_box">
					<tr>
						<td class="acc_img box">
							<img src="${arr.acc_img }" alt = "${arr.acc_name }"/> 
						</td>
					</tr>
				</tbody>
				<%-- 오른쪽 : 숙소명 등 정보들  --%>
				<tbody class="right_box">
					<tr>
						<th class="label">숙소명</th>
						<td class="acc_title">${arr.acc_name } <span class="acc_type">${arr.acc_type }</span></td>
					</tr>
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
								<c:when test="${arr.rev_rating==5}">
									<span class="star">★★★★★</span>
								</c:when>
								<c:when test="${arr.rev_rating==4}">
									<span class="star">★★★★☆</span>
								</c:when>
								<c:when test="${arr.rev_rating==3}">
									<span class="star">★★★☆☆</span>
								</c:when>
								<c:when test="${arr.rev_rating==2}">
									<span class="star">★★☆☆☆</span>
								</c:when>
								<c:otherwise>
									<span class="star">★☆☆☆☆</span>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th class="label">리뷰내용</th>
						<td class="content_box">${arr.rev_content }</td>
					</tr>
					<tr>
						<td colspan="2" class="btn_area">
							<%-- 리뷰수정하기/삭제하기 : 작성자에게만 수정하기삭제하기 버튼 보여야함 --%>
							<input type="button" class="btn_update" onclick="updateReview(${arr.rev_code})" value="수정하기"/>
							<input type="button" class="btn_delete" onclick="deleteReview(${arr.rev_code})" value="삭제하기"/>
						</td>
					</tr>
				</tbody>
			</table>
		</c:forEach>
	</c:if>


	<%-- 페이징 부분(마찬가지로, 리뷰가 있는 경우에만 페이지 보이기 --%>
	<c:if test="${not empty rlist}">
		<div class="paging">
			<c:if test = "${startPage > pageBlock }">
				<input type="button" onclick="pageBtn1(${startPage},${pageBlock})" value="이전"/>
			</c:if>
			
			<%-- 페이징 번호 --%>
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<input type="button" onclick="pageBtn2(${i})" value="${i }"/>
			</c:forEach>
			
			<%-- 페이징 번호 --%>
			<c:if test = "${endPage < pageCount}">
				<input type="button" onclick="pageBtn3(${startPage},${pageBlock})" value="다음"/>
			</c:if>
		</div>	
	</c:if>

</div>


<script>
	// 삭제하기 함수(본인 글만 삭제 가능) (소프트삭제)
	function deleteReview(rev_code){
		if(confirm("해당 리뷰 삭제하시겠습니까?")){
			// 해당 리뷰번호와 함께, 삭제하는 서블릿으로 넘기기
			location.href="MyRevDeleteCon.do?rev_code="+rev_code;
		}
	}
	// 내가 쓴 리뷰 수정하는 함수
	function updateReview(rev_code){
		location.href="MyRevModProc.do?rev_code="+rev_code;
	}
	
	function pageBtn1(startPage,pageBlock){
		let f = document.acc_choice;
		location.href="MyRevListProc.do?pageNum="+ (startPage-pageBlock);
	}
	function pageBtn2(i){
		let f = document.acc_choice;
		location.href="MyRevListProc.do?pageNum="+ i;
	}
	function pageBtn3(startPage,pageBlock){
		let f = document.acc_choice;
		location.href="MyRevListProc.do?pageNum="+ (startPage+pageBlock);
	}
	
</script>
