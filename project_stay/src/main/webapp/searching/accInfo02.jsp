<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 1108 추가분 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="styleSheet" href="style/acc_info.css"/>
<section class="">
	<div class="contaier">
		<c:set var="pdto" value="${pdto }"/>
		<c:set var="rdto_arr" value="${rdto_arr }"/>
		<div class="top_image_wrap" onclick="image_modal_open"> <!-- 클릭시 이미지 전체 모아보기 모달 열리게 스크립트 -->
			<div class="acc_img" >
				<img alt="전경 이미지" src="${pdto.acc_img}">
			</div>
			<div class="room_img">
				<img alt="상세 이미지1" src="${rdto_arr[0].room_img1}">
				<img alt="상세 이미지2" src="${rdto_arr[0].room_img2}">
				<img alt="상세 이미지3" src="${rdto_arr[0].room_img3}">
				<img alt="상세 이미지4" src="${rdto_arr[0].room_img4}">
			</div>
		</div>
		<div class="about_acc">
			<div class="left">
				<p class="acc_type">${pdto.acc_type }</p>
				<p class="acc_name">${pdto.acc_name }</p>
				<p class="acc_address">${pdto.acc_address }</p>
				<p class="avg_rating">${pdto.avg_rating }</p>
			</div>
			<div class="right">
				<button type="button" onclick="location.href='wishListAddcon.do?acc_code=${sa_arr.acc_code}'" ></button> <!-- 찜 추가 버튼 -->
				<c:set var="low" value="999999999999"/>
				<c:forEach items="${rdto_arr }" var="rdto">
					<c:if test="${rdto.room_price<low}">
						<c:set var="low" value="${rdto.room_price }"/>
					</c:if>
				</c:forEach>
				<p class="low_price"><span class="per_oneday">1박</span>${low }원</p>
			</div>
		</div>
		<div class="room_list">
			<h5>객실선택</h5>
			<c:if test="${rdto_arr[0].room_code =! 0}">
				<c:forEach var="rdto" items="${rdto_arr }">
					<form class="room_card" action="bookingProc.java" method="get"> <!-- 결재 화면으로 이동하게 해야함. -->
						<div class="room_img1">
							<img alt="방 대표 이미지" src="${rdto.room_img1 }">
						</div>
						<h6>${rdto.room_name }</h6>
						<p class="room_modal" onclick="">상세정보 ></p> <!-- 클릭 시 숙소 상세 모달 창 띄워야함! -->
						<div class="detail">
								<p>입실 : ${pdto.acc_checkinTime }, 퇴실 : ${pdto.acc_checkoutTime }</p>
							</div>
							<div class="right">
								<p>${diffDays * rdto.room_price }원</p>
								<button type="submit">객실 예약하기</button>
							</div>
						</div>
						<div class="about">
							<p class="what">객실 정보</p>
							<p class="info">인원 기준 : ${rdto.room_capacity }인</p>
						</div>
						<input type="hidden" value="${rdto.room_code }" name="room_code">
						<input type="hidden" value="${pdto.acc_checkinTime }" name="acc_checkinTime">
						<input type="hidden" value="${pdto.acc_checkoutTime }" name="acc_checkoutTime">
						<input type="hidden" value="${diffDays * rdto.room_price }" name="totalPrice">
						<input type="hidden" value="${pdto.acc_code }" name="acc_code">
						<input type="hidden" value="${rdto.room_img1 }" name="room_img1">
						<input type="hidden" value="${rdto.room_name }" name="room_name">
						<input type="hidden" value="${book_checkin_date }" name="book_checkin_date">
						<input type="hidden" value="${book_checkout_date }" name="book_checkout_date">
						<input type="hidden" value="${rdto.room_capacity }" name="room_capacity">
						<input type="hidden" value="${rdto.room_info }" name="room_info">
						<input type="hidden" value="${rdto.room_price }" name="room_price">
						
					</form>
				</c:forEach>
			</c:if>
		</div>
	</div>
</section>