<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 1108 추가분 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="style/acc_info.css">
<link rel="stylesheet" href="style/acc_info_modal_legacy.css">

<section>
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
				<p class="avg_rating">${pdto.prt_avg_rating }</p>
			</div>
			<div class="right">
				<button type="button" onclick="${empty sessionScope.user_email ? 'pleasJoin();' : 'location.href=\'wishListAddcon.do?acc_code=' += pdto.acc_code += '\''}"  class="wish_btn"></button> <!-- 찜 추가 버튼 -->
				<c:set var="low" value="999999999999"/>
				<c:forEach items="${rdto_arr }" var="rdto" varStatus="stat">
					<c:if test="${rdto.room_price<low}">
						<c:set var="low" value="${rdto.room_price }"/>
						<c:set var="lowIndex" value="${stat.count-1 }"/>
					</c:if>
				</c:forEach>
				<p class="low_price"><span class="per_oneday">하루 머물 가격</span>${rdto_arr[lowIndex].one_day_prt_room_price}원</p>
			</div>
		</div>
		<div class="room_list">
			<h5>객실선택</h5>
			<%-- <c:if test="${rdto_arr[0].room_code =! 0}"> --%>
				<c:forEach var="rdto" items="${rdto_arr }" varStatus="status">
					<form class="room_card" action="bookingProc.do" method="get"> <!-- 결재 화면으로 이동하게 해야함. -->
						<div class="room_img1">
							<img alt="방 대표 이미지" src="${rdto.room_img1 }">
						</div>
						<h6>${rdto.room_name }</h6>
						<p class="room_modal" onclick="show_modal(${status.count-1})">상세정보 ></p> <!-- 클릭 시 숙소 상세 모달 창 띄워야함! -->
						<div class="detail">
							<div class="left">
								<p>입실 : ${pdto.acc_checkinTime }, 퇴실 : ${pdto.acc_checkoutTime }</p>
							</div>
							<div class="right">
								<span class="per_oneday">${diffDays }박 ${diffDays+1 }일</span>
								<p>${rdto.prt_room_price }원</p>
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
					<div class="modal_background" onclick="add_noShow(${status.count-1})">
						<div class="modal_roomInfo noShow">
							<button type="button" onclick="add_noShow(${status.count-1})">X</button> 
							<div class="room_name">
								<h5>${rdto.room_name }</h5>
							</div>
							<div class="room_images_wrap">
								<div class="big_room_images"><img alt="큰 이미지" src="${rdto.room_img1}"></div>
								<div class="room_images">
									<c:forEach begin="1" end="4" var="i">
										<c:set var="imgKey" value="room_img${i}"/>
										<img alt="객실 이미지" src="${rdto[imgKey]}" class="room_image">
									</c:forEach>
								</div>
							</div>
							<div class="about_room">
								<h6>객실 정보</h6>
								<span class="info_title">숙박 </span><span>| 체크인 ${prt_book_checkin_date }<br/>| 체크아웃 ${prt_book_checkout_date }</span>
								<span class="info_title">숙박 인수 </span><span>| ${rdto.room_capacity }명</span>
								<span class="info_title">하루 머무는 가격 </span><span>| ${rdto.prt_room_price }원</span>
							</div>
							
							<br/>
							
							<div class="additionalInfo_room">
								<h6>편의시설</h6>
								<span class="info_title">편의시설 </span><span>| ${rdto.room_amenity }</span>
	
								<h6>취소 및 환불 규정</h6>
								<span class="info_title">무료 취소</span><span>| 체크인 기준 3일 전 18:00까지: 100% 환불</span>
								<span class="info_title">환불 불가 </span><span>| 체크인 당일 23:59까지 및 노쇼 시: 환불불가</span>
							</div>
						</div>
					</div>
				</c:forEach>

		</div>
		<%-- 리뷰 --%>
		<div class="review_wrap">
			<jsp:include page="/accReviewListProc.do?acc_code=${pdto.acc_code}"/>
		</div>
	</div>
	
	<script type="text/javascript" defer="defer">
		let wish_btn = document.querySelector(".wish_btn");
		if(${is_wish==true}) wish_btn.classList.add("wished");
		
		let modal_roomInfo = document.querySelectorAll(".modal_roomInfo");
		let modal_background = document.querySelectorAll(".modal_background");
		function show_modal(num){	
			modal_focusImgChange(num);
			let show_back = modal_background[num];
			show_back.classList.add("is-open");
			let show_room = modal_roomInfo[num];
			show_room.classList.remove("noShow");
		}
		
		function add_noShow(num){
			let show_back = modal_background[num];
			show_back.classList.remove("is-open");
			let show_room = modal_roomInfo[num];
			show_room.classList.add("noShow");
		}
		
		function pleasJoin(){
			alert("로그인 후 이용 가능합니다");
			location.href="index.jsp?section=user/loginForm.jsp";
		}
		
		function modal_focusImgChange(num){
			let room_image_arr = document.querySelectorAll(".room_image");
			let big_room_images = document.querySelectorAll(".big_room_images img");
			room_image_arr.forEach((item)=>{
				item.addEventListener('mouseenter', ()=>{
					big_room_images[num].src = item.src;
				});
			});
		}
		
		
	

	</script>
</section>