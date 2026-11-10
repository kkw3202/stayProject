<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<section>
	<div class="contaier">
		<form action="paymentProc.do" method="get">
			<div class="left">
				<div class="reserved">
					<div class="nameWrap">
						<label for="book_name">예약자 이름</label>
						<input type="text" name="book_name" id="book_name">
					</div>
					<div class="phoneWrap">
						<label for="phone">예약자 번호</label>
						<input type="text" name="phone" id="phone" placeholder="010xxxxxxxx, -없이 입력">
					</div>
				</div>
				<div class="pay_btn_wrap">
					<!-- 결제 타입 선택 버튼 추가 -->
				</div>
			</div>
			<div class="right">
				<div class="room_info">
					<div class="room_img1">
						<img alt="객실 대표 이미지" src="${room_img1 }">
					</div>
					<div class="info_name_wrap">
						<p class="info_name">객실</p>
						<p class="info_name">일정</p>
						<p class="info_name">인원</p>
						<p class="info_name">추가정보</p>
					</div>
					<div class="info_wrap">
						<p class="info">${room_name }</p>
						<p class="info">${book_checkin_date } ${acc_checkinTime } ~<br/>${book_checkout_date } ${acc_checkoutTime }</p>
						<p class="info">${room_capacity }인실</p>
						<p class="info">${room_infos }</p>
					</div>
				</div>
				<div class="pay_wrap">
					<h6>결제 정보</h6>
					<p class="info_name">객실 가격(1박당)</p>
					<p class="info">${room_price }</p>
					<p class="info_name">총 결제 금액</p>
					<p class="info">${totalPrice }</p>
				</div>
				<div class="agreement_wrap">
					<!-- p태그로 동의 내용 작성 예정 -->
					<input type="checkbox" id="allAgree">
					<label for="allAgree">약관 전체 동의</label>
				</div>
				<div class="submit_btn_wrap">
					<button type="submit">${totalPrice }원 결제하기</button>
				</div>
			</div>
			<div class="hidden_wrap">
				<input type="hidden" value="${book_checkin_date }" name="book_checkin_date">
				<input type="hidden" value="${book_checkout_date }" name="book_checkout_date">
				<input type="hidden" value="${room_code }" name="room_code">
			</div>
		</form>
	</div>
</section>