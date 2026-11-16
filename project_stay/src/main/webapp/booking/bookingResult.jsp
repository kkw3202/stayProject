<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section>
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
					<p class="about_book_info book_checkin_date">${brdto.book_checkin_date }</p>
					<p class="about_book_info book_checkout_date">${brdto.book_checkout_date }</p>
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
					<p class="about_price_info book_number">${brdto.room_price }</p>
					<p class="about_price_info book_name">${brdto.date_of_stay }</p>
					<p class="about_price_info book_checkin_date">${brdto.total_price }</p>
				</div>
			</div>
		</div>
	</div>
</section>