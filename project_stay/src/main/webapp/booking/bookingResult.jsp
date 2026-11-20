<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="styleSheet" href="style/bookResult.css">
<section class="bookresult_page">
	<c:set var="brdto" value="${brdto }"/>
	<div class="container">
		<div class="bookresult_card">
			<div class="about_book_wrap">
				<div class="about_book_infoName_wrap">
					<p class="about_book_infoName book_number">예약 번호</p>
					<p class="about_book_infoName book_name">예약자</p>
					<p class="about_book_infoName book_checkin_date">입실 예정일</p>
					<p class="about_book_infoName book_checkout_date">퇴실 예정일</p>
					<p class="about_book_infoName acc_name">숙소명</p>
					<p class="about_book_infoName room_name">객실</p>
				</div>
				<div class="about_book_info_wrap">
					<p class="about_book_info book_number">${brdto.book_number }</p>
					<p class="about_book_info book_name">${brdto.book_name }</p>
					<p class="about_book_info book_checkin_date">${brdto.prt_book_checkin_date }</p>
					<p class="about_book_info book_checkout_date">${brdto.prt_book_checkout_date }</p>
					<p class="about_book_info acc_name">${brdto.acc_name }</p>
					<p class="about_book_info room_name">${brdto.room_name }</p>
				</div>
			</div>
			<div class="about_price_wrap">
				<div class="about_price_infoName_wrap">
					<p class="about_price_infoName book_number">1박 당 가격</p>
					<p class="about_price_infoName book_name">머물 일 수</p>
					<p class="about_price_infoName book_checkin_date">총 결제 금액</p>
				</div>
				<div class="about_price_info_wrap">
					<p class="about_price_info book_number">${brdto.prt_room_price }원</p>
					<p class="about_price_info book_name">${brdto.date_of_stay }박 ${brdto.date_of_stay+1 }일</p>
					<p class="about_price_info book_checkin_date">${brdto.prt_total_price }원</p>
				</div>
			</div>
			<c:if test="${empty sessionScope.user_email }">
			<div class="notice_wrap">
				<p class="notice">※ 예약번호를 메모해두세요. 조회 및 취소는 화면 상단의 비회원 예약조회에서 진행되며, 예약번호가 필요합니다.</p>
			</div>
			</c:if>
		</div>
		
	</div>
</section>