<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="style/mypageReview.css"/>

<div class="myReviewWrap">
	<h3>내 리뷰 보기 <span class="reviewCount">(총 리뷰 : ${count }개)</span></h3> 
	
	<%-- 작성한 리뷰가 없는 경우 --%>
	<c:if test="${empty rlist }">
		<div class="no_review">
			<p>작성한 리뷰가 아직 없습니다.</p>
		</div>
	</c:if>
	<%-- 내 예약리스트 중, 아직 리뷰 안달린 리뷰 상단에 먼저 올리기--%>
	<%-- 단, 퇴실날짜부터만 작성 가능함 --%>
	<c:if test="${not empty notList }">
		<div class="unwritten_wrap">
			<%-- 아래 글자 클릭시, 리뷰 작성하지 않는 리스트 펼침 --%>
			<p class="notList_title" onclick="toggleUnwrittenList(this)">
				<span>아직 작성하지 않은 리뷰가 있습니다.</span>
				<span class="unwritten_reviewCount">(총 ${notList_cnt }개)</span>
				<span class="toggle_arrow">▼</span>
			</p>
			<%-- 처음에는 숨겨져 있는 부분(작성하지 않은 리뷰 리스트) --%>
			<div id="unwrittenListBox" class="unwritten_list_box noShow">
				<c:forEach var="arr02" items="${notList }">
					<c:set var="inDate" value="${fn:replace(fn:substring(arr02.book_checkin_date, 0, 10), '-', '')}" />
					<c:set var="outDate" value="${fn:replace(fn:substring(arr02.book_checkout_date, 0, 10), '-', '')}" />
					
					<div class="unwritten_review_list">
						<a href="accInfoProc.do?acc_code=${arr02.acc_code}&book_checkin_date=${inDate}&book_checkout_date=${outDate}#accReviewSection">
							<span class="unwritten_acc_name">${arr02.acc_name }</span>
							<span class="unwritten_date">
								${fn:substring(arr02.book_checkin_date, 0, 10)} ~ ${fn:substring(arr02.book_checkout_date, 0, 10)}
							</span>
							<span class="btn_go_write">리뷰 쓰러가기 &gt;</span>
						</a>
					</div>
				</c:forEach>	
			</div>
		</div>
	</c:if>
	
	<%-- 작성한 리뷰가 있는 경우 --%>
	<c:if test="${not empty rlist }">
		<c:forEach var="arr" items="${rlist}">
			<div class="review_card">
				<%-- 왼쪽 : 숙소 이미지 + 숙소명/타입 + 좋아요 --%>
				<div class="left_box">
					<div class="imageBox">
						<a href="javascript:void(0);" onclick="goAccDetail(${arr.acc_code})">
							<img src="${arr.acc_img }" alt="${arr.acc_name}"/>
						</a>
					</div>
					<div class="acc_info_bottom">
						<p class="acc_title">${arr.acc_name } <span class="acc_type">${arr.acc_type }</span></p>
						<div class="rev_like_box">
							<span class="like_icon">👍</span>
							<span class="like_label">받은 좋아요</span>
							<strong class="like_num">${arr.rev_like}</strong>
						</div>
					</div>
				</div>

				<%-- 오른쪽 : 작성자 / 작성시간 / 평점 / 리뷰내용 --%>
				<table class="right_box">
					<tr>
						<th class="label">작성자</th>
						<td class="data">
							<div class="writer_row">
								<span>${arr.user_name }</span>
								<div class="btn_area">
									<input type="button" class="btn_update" onclick="updateReview(${arr.rev_code})" value="수정하기"/>
									<input type="button" class="btn_delete" onclick="deleteReview(${arr.rev_code})" value="삭제하기"/>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th class="label">작성시간</th>
						<td class="data">${arr.rev_created_datetime }</td>
					</tr>
					<tr>
						<th class="label">평점</th>
						<td class="data">
							<c:choose>
								<c:when test="${arr.rev_rating==5}"><span class="star">★★★★★</span></c:when>
								<c:when test="${arr.rev_rating==4}"><span class="star">★★★★☆</span></c:when>
								<c:when test="${arr.rev_rating==3}"><span class="star">★★★☆☆</span></c:when>
								<c:when test="${arr.rev_rating==2}"><span class="star">★★☆☆☆</span></c:when>
								<c:otherwise><span class="star">★☆☆☆☆</span></c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th class="label">리뷰내용</th>
						<td class="data">
							<div class="content_box">${arr.rev_content }</div>
						</td>
					</tr>
				</table>
			</div>
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
				<c:choose>
					<c:when test="${i == currentPage or (empty currentPage and i == 1)}">
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
	// 아직 쓰지 않은 리뷰 펼치는 함수
	function toggleUnwrittenList(titleEl){
		let listBox = document.getElementById("unwrittenListBox");
		let arrow = titleEl.querySelector(".toggle_arrow");
		
		listBox.classList.toggle("noShow");
		if(listBox.classList.contains("noShow")){
			arrow.textContent = "▼";
		} else {
			arrow.textContent = "▲";
		}
	}
	// 숙소 이미지 클릭 시 리뷰 섹션 이동
	function goAccDetail(acc_code) {
		let today = new Date();
		let tomorrow = new Date(today);
		tomorrow.setDate(today.getDate() + 1);

		function formatDate(d) {
			let y = d.getFullYear();
			let m = String(d.getMonth() + 1).padStart(2, '0');
			let day = String(d.getDate()).padStart(2, '0');
			return y + m + day;
		}

		let inDate = formatDate(today);
		let outDate = formatDate(tomorrow);

		location.href = "accInfoProc.do?acc_code=" + acc_code
				+ "&book_checkin_date=" + inDate
				+ "&book_checkout_date=" + outDate
				+ "#accReviewSection";
	}

</script>
