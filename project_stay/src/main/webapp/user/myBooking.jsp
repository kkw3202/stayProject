<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="style/myBooking.css"/>
<div class="myBookingList">
	<h3>예약확인</h3>
	<%-- 대시보드 현황판 --%>
	<div class="dashboard">
		<button type="button" class="dash_btn ${status eq 'all' or empty status ? 'active' : ''}" onclick="filterBooking('all')">
			<span class="dash_label">전체예약</span>
			<strong class="dash_num">${totalAllCount}개</strong>
		</button>
		<button type="button" class="dash_btn ${status eq '3' ? 'active' : ''}" onclick="filterBooking('3')">
			<span class="dash_label">예약중</span>
			<strong class="dash_num">${countReservedBooking}개</strong>
		</button>
		<button type="button" class="dash_btn ${status eq '2' ? 'active' : ''}" onclick="filterBooking('2')">
			<span class="dash_label">기간만료</span>
			<strong class="dash_num">${countOverBooking}개</strong>
		</button>
		<button type="button" class="dash_btn ${status eq '1' ? 'active' : ''}" onclick="filterBooking('1')">
			<span class="dash_label">예약취소</span>
			<strong class="dash_num">${countCancelBooking}개</strong>
		</button>
	</div>
	
	<c:forEach var = "arr" items="${mblist}">
		<%-- 예약취소인 경우에, 예매 정보 회색 처리 위해 클래스 부여 --%>
		<c:choose>
			<c:when test="${arr.book_chk_status ==1 }">
				<c:set var="statusClass" value="cancel_ticket" />
			</c:when>
			<c:when test="${arr.book_chk_status ==2 }">
				<c:set var="statusClass" value="over_ticket"/>
			</c:when>
			<c:otherwise>
				<c:set var="statusClass" value="live_ticket"/>
			</c:otherwise>
	 	</c:choose>
	
		<%-- 왼쪽 숙소 이미지 + 숙소명 / 숙소타입  --%>
		<div class="leftBox ${statusClass}">
			<div class="imageBox">
				<%-- 이미지 누르면, 해당 숙소페이지로 이동하기 --%>
				<c:set var="inDate" value="${fn:replace(fn:substring(arr.book_checkin_date, 0, 10), '-', '')}" />
				<c:set var="outDate" value="${fn:replace(fn:substring(arr.book_checkout_date, 0, 10), '-', '')}" />
				<a href="accInfoProc.do?acc_code=${arr.acc_code}&book_checkin_date=${inDate}&book_checkout_date=${outDate}">
					<img src="${arr.acc_img }" alt="${arr.acc_name}"/>
				</a>
				
			</div>
			<%-- 그림 밑에 비치 --%>
			<div class="accName">
				<p class="data acc_name">${arr.acc_name}
				<span class="accType">(${arr.acc_type})</span></p>
			</div>
			<div class="accNumber">
				<span class="acc_number_label">예약번호</span>
				<span class="acc_number_data">${arr.book_number }</span>
			</div>
			
		</div>
		<%-- 오른쪽 예매 정보 --%>
		<table class="myBooking_table ${statusClass}">
			<tr>
				<th class="label">예약자</th>
				<td class="data book_name">
					<div class="booker_row">
						<span>${arr.book_name }</span>
						<c:if test="${arr.book_chk_status == 3}">
							<div class="cancel_btn_block">
								<input type="button" value="예매 취소하기" onclick="cancelBooking('${arr.book_number}')" class="cancel_btn"/>
							</div>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<th class="label">숙소주소</th>
				<td class="data acc_address">${arr.acc_address }</td>
			</tr>
			<tr>
				<th class="label">객실명</th>
				<td class="data room_name">${arr.room_name }</td>
			</tr>
			<tr>
				<th class="label">예매일(결제일)</th>
				<td class="data book_date">${arr.book_date }</td>
			</tr>
			<tr>
				<th class="label">숙박기간</th>
				<td class="data book_checkin_date">${arr.book_checkin_date } ~ ${arr.book_checkout_date } (${arr.date_of_stay }박${arr.date_of_stay+1 }일)</td>
			</tr>
			<tr>
				<th class="label">금액</th>
				<td class="data total_price">
					<fmt:formatNumber value="${arr.total_price }" pattern="#,###"/>원
				</td>
			</tr>
			<tr>
				<th class="label">상태</th>
				<td class="data"> 
					<%-- 예약이 취소된 경우 --%>
					<c:choose>
						<c:when test="${arr.book_chk_status ==1 }"><span class="book_cancel">예약취소</span></c:when>
						<c:when test="${arr.book_chk_status ==2 }"><span class="book_expire">기간만료</span></c:when>
						<c:otherwise><span class="book_ing">예약중</span></c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</c:forEach>
	
	<%-- 페이징 부분(마찬가지로, 리뷰가 있는 경우에만 페이지 보이기 --%>
	<c:if test="${not empty mblist}">
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
	// 예매취소하는 버튼 함수
	function cancelBooking(book_number){
		if(confirm("해당 예매내역을 취소하시겠습니까?")){
			// 예매 취소 실행하기
			location.href="MyBookingDeleteCon.do?book_number=" + book_number;
		}
	}
	// 대시보드 상태 필터 이동
	function filterBooking(status) {
		location.href = "myBookingProc.do?status=" + status;
	}

	function pageBtn1(startPage, pageBlock) {
		location.href = "myBookingProc.do?pageNum=" + (startPage - pageBlock) + "&status=${status}";
	}
	function pageBtn2(i) {
		location.href = "myBookingProc.do?pageNum=" + i + "&status=${status}";
	}
	function pageBtn3(startPage, pageBlock) {
		location.href = "myBookingProc.do?pageNum=" + (startPage + pageBlock) + "&status=${status}";
	}
</script>