<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="style/booking.css">
<section>
	<div class="contaier">
		<form action="paymentProc.do" method="get" name="pform">
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
					<ul>
						<c:forTokens items="신용카드, 현장결제" delims=", " varStatus="status" var="item">
							<li></li>
						</c:forTokens>
					</ul>
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
					<div class="agreement_info">
						<h6>미성년자 이용</h6>
						<p>미성년자는 법정대리인 동의 없이 이용할 수 없어요. 숙소에 따라 가족관계증명서 등 증빙 서류를 요청할 수 있어요.</p>
						<h6>신분증 확인</h6>
						<p>숙소에 따라 체크인 시 신분증 확인을 요청할 수 있으니 미리 준비해 주세요.</p>
						<h6>현장 결제</h6>
						<p>기준 인원을 초과하면 추가 인원 요금은 숙소에서 현장 결제로 진행돼요.</p>
					</div>
					<!-- p태그로 동의 내용 작성 예정 -->
					<div class="check_btn_wrap">
						<input type="checkbox" id="allAgree" >
						<label for="allAgree">약관 전체 동의</label>
					</div>
				</div>
				<div class="submit_btn_wrap">
					<button type="button" onclick="submit_form()">${totalPrice }원 결제하기</button>
				</div>
			</div>
			<div class="hidden_wrap">
				<input type="hidden" value="${book_checkin_date }" name="book_checkin_date">
				<input type="hidden" value="${book_checkout_date }" name="book_checkout_date">
				<input type="hidden" value="${room_code }" name="room_code">
			</div>
		</form>
	</div>
	<script type="text/javascript" defer="defer">
		function submit_form(){
			let allAgree = document.getElementById("allAgree");
			let book_name = document.getElementById("book_name");
			let phone = document.getElementById("phone");
			let f = document.pform;
			
			if(book_name.value.trim()==""){
				alert("필수 입력사항을 입력해 주세요");
				book_name.focus();
				return;
			}
			if(phone.value.trim()==""){
				alert("필수 입력사항을 입력해 주세요");
				phone.focus();
				return;
			}
			if(!allAgree.checked){
				alert("약관을 읽고, 동의해주세요");
				return;
			}
			f.submit();
		}
	
		
	</script>
</section>