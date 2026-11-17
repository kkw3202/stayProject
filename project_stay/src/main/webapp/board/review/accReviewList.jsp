<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="style/accReviewList.css"/>

<div class="accReviewList" id="accReviewSection">
	<div class="review_header">
		<h3>리뷰 (${revCount}건 )</h3>
		<%-- 1. 현재 숙소 예약자 + 퇴실일 경과 + 미작성자인 경우에만 리뷰작성 버튼 노출 --%>
		<c:if test="${canWrite}">
			<div class="writeBox">
				<input type="button" class="write_btn" value="리뷰작성" onclick="toggleInlineReviewForm()"/>
			</div>
		</c:if>
	</div>
	<%-- 페이지 이동 없이 바로 아래에 펼쳐지는 인라인 리뷰 작성 폼 (처음엔 숨김) --%>
	<%-- 인라인 리뷰 작성 폼은 review_header 바깥(바로 아래)에 배치 --%>
	<c:if test="${canWrite}">
		<div id="inlineReviewFormBox" style="display: none; margin: 15px 0; padding: 18px; border: 1px solid #d9e5f7; border-radius: 12px; background: #f8fbff;">
			<form action="reviewWriteProcCon.do" method="post" name="rform">
				<input type="hidden" name="acc_code" value="${acc_code}"/>
				<table class="inline_write_table" style="width: 100%;">
					<tr>
						<th class="label" style="width: 100px;">평점</th>
						<td class="data">
							<select name="rev_rating">
								<option value="5">★★★★★ (5점)</option>
								<option value="4">★★★★☆ (4점)</option>
								<option value="3">★★★☆☆ (3점)</option>
								<option value="2">★★☆☆☆ (2점)</option>
								<option value="1">★☆☆☆☆ (1점)</option>
							</select>
						</td>
					</tr>
					<tr>
						<th class="label">리뷰내용</th>
						<td class="data">
							<textarea class="rev_content" name="rev_content" rows="4" style="width: 100%;" placeholder="숙소 이용 후기를 남겨주세요."></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: right; padding-top: 10px;">
							<input type="button" class="btns" onclick="chkInlineForm()" value="등록하기"/>
							<input type="button" class="btns" onclick="toggleInlineReviewForm()" value="취소"/>
						</td>
					</tr>
				</table>
			</form>
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
					<td class="data">${fn:substring(arr.user_name, 0, 1)}**</td>
					<td class="data">${arr.rev_created_datetime }</td>
					<td class="data">
						<c:choose>
							<c:when test="${arr.rev_rating ==5}"><span class="star">★★★★★</span></c:when>
							<c:when test="${arr.rev_rating ==4}"><span class="star">★★★★☆</span></c:when>
							<c:when test="${arr.rev_rating ==3}"><span class="star">★★★☆☆</span></c:when>
							<c:when test="${arr.rev_rating ==2}"><span class="star">★★☆☆☆</span></c:when>
							<c:otherwise><span class="star">★☆☆☆☆</span></c:otherwise>
						</c:choose>
					</td>
					<td colspan = "2" >
						<%-- 좋아요 박스 (알약 형태 통합) --%>
						<c:choose>
							<%-- 로그인 상태이고 본인 글이 아닌 경우: 클릭 가능한 알약 버튼 --%>
							<c:when test="${not empty sessionScope.user_email and sessionScope.user_email ne arr.user_email}">
								<button type="button" class="like_pill_btn" onclick="chkLike(${arr.rev_code}, ${arr.acc_code})">
									<span class="like_badge">👍</span>
									<span class="like_text">좋아요</span>
									<strong class="like_count">${arr.rev_like}</strong>
								</button>
							</c:when>
							<%-- 비로그인이거나 본인 글인 경우: 클릭 안 되는 알약 배지 --%>
							<c:otherwise>
								<div class="like_pill_badge">
									<span class="like_badge">👍</span>
									<span class="like_text">좋아요</span>
									<strong class="like_count">${arr.rev_like}</strong>
								</div>
							</c:otherwise>
						</c:choose>
						<%-- 본인이 쓴 리뷰에는 삭제하기 / 수정하기 버튼 보임 --%>
						<c:if test="${not empty sessionScope.user_email and sessionScope.user_email eq arr.user_email}">
							<input type="button" class="btns" onclick="deleteReview(${arr.rev_code},${arr.acc_code})" value="삭제하기"/>
							<input type="button" class="btns" onclick="modReview(${arr.rev_code})" value="수정하기"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="data">
						<%-- 평소 보이는 리뷰 본문 --%>
						<div id="revView_${arr.rev_code}">${arr.rev_content }</div>

						<%-- 본인 글일 때만 생성되는 인라인 수정 폼 (처음엔 숨김) --%>
						<c:if test="${not empty sessionScope.user_email and sessionScope.user_email eq arr.user_email}">
							<div id="revEdit_${arr.rev_code}" class="inline_edit_box" style="display: none;">
								<form action="reviewUpdateProcCon.do" method="post" onsubmit="return chkEditForm(this);">
									<input type="hidden" name="rev_code" value="${arr.rev_code}"/>
									<input type="hidden" name="acc_code" value="${arr.acc_code}"/>
									<input type="hidden" name="book_checkin_date" value="${book_checkin_date}"/>
									<input type="hidden" name="book_checkout_date" value="${book_checkout_date}"/>
									
									<div class="edit_top_row">
										<select name="rev_rating">
											<option value="5" ${arr.rev_rating == 5 ? 'selected' : ''}>★★★★★ (5점)</option>
											<option value="4" ${arr.rev_rating == 4 ? 'selected' : ''}>★★★★☆ (4점)</option>
											<option value="3" ${arr.rev_rating == 3 ? 'selected' : ''}>★★★☆☆ (3점)</option>
											<option value="2" ${arr.rev_rating == 2 ? 'selected' : ''}>★★☆☆☆ (2점)</option>
											<option value="1" ${arr.rev_rating == 1 ? 'selected' : ''}>★☆☆☆☆ (1점)</option>
										</select>
									</div>
									<textarea name="rev_content" rows="3">${arr.rev_content}</textarea>
									<div class="edit_btn_row">
										<input type="submit" class="btns save_btn" value="수정완료"/>
										<input type="button" class="btns" onclick="toggleEditReview(${arr.rev_code})" value="취소"/>
									</div>
								</form>
							</div>
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
	//좋아요 버튼 함수
	function chkLike(rev_code, acc_code){
		location.href = "reviewLikeCon.do?rev_code=" + rev_code 
					  + "&acc_code=" + acc_code
					  + "&book_checkin_date=${book_checkin_date}"
					  + "&book_checkout_date=${book_checkout_date}";
	}
	// 본인 리뷰 삭제 함수
	function deleteReview(rev_code, acc_code){
		if(confirm("해당 리뷰를 삭제하시겠습니까?")){
			location.href = "reviewDeleteCon.do?rev_code=" + rev_code 
						  + "&acc_code=" + acc_code
						  + "&book_checkin_date=${book_checkin_date}"
						  + "&book_checkout_date=${book_checkout_date}";
		}
	}
	// 인라인 리뷰 수정 폼 열기/닫기
	function toggleEditReview(rev_code) {
		let viewBox = document.getElementById("revView_" + rev_code);
		let editBox = document.getElementById("revEdit_" + rev_code);
		
		if (editBox.style.display === "none") {
			viewBox.style.display = "none";
			editBox.style.display = "block";
		} else {
			viewBox.style.display = "block";
			editBox.style.display = "none";
		}
	}

	// 수정하기 버튼 클릭 시 호출
	function modReview(rev_code) {
		toggleEditReview(rev_code);
	}

	// 수정 폼 유효성 검사
	function chkEditForm(form) {
		if (form.rev_content.value.trim() === "") {
			alert("리뷰 내용을 입력하세요.");
			form.rev_content.focus();
			return false;
		}
		return confirm("리뷰를 수정하시겠습니까?");
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
	
	// 인라인 리뷰 작성폼 열기/닫기
	function toggleInlineReviewForm() {
		let formBox = document.getElementById("inlineReviewFormBox");
		if (formBox.style.display === "none") {
			formBox.style.display = "block";
		} else {
			formBox.style.display = "none";
		}
	}

	// 인라인 폼 유효성 검사 및 제출
	function chkInlineForm() {
		let f = document.rform;
		if (f.rev_content.value.trim() === "") {
			alert("리뷰 내용을 입력하세요.");
			f.rev_content.focus();
			return;
		}
		if (confirm("리뷰를 등록하시겠습니까?")) {
			f.submit();
		}
	}

</script>
