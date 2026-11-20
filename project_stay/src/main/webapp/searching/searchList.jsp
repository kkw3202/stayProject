<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="style/searchList.css"/>
<!-- 히든 -->
<div>
	<c:if test="${not empty wishes}">
		<c:forEach items="${wishes }" var="wish">
			<input type="hidden" value="${wish }" class="wishList">
		</c:forEach>
	</c:if>
</div>
<!-- 히든 -->

<section>
	<div class="contaier">
	<c:if test="${not empty sa_arr }">
		<div class="page_top">
			<h2>숙소 검색 결과</h2>
			<p>총 <span id="search_cnt_prt"></span>의 숙소가 검색되었습니다.
		</div>
		<div class="search_filter" >
			<p>검색 결과</p>
			<ul class="filterList">
				<li class="search_filter_room_price_container">	
					<h6>가격</h6>
					<div class="search_filter_room_capacity_wrap">
						<ul>
							<li>~</li>
						</ul>
					</div>
				</li>			
				<li class="search_filter_room_capacity_container">
					<h6>적정 인원</h6>
					<div class="search_filter_room_capacity_wrap">
						<label for="search_filter_room_capacity2">2인~</label>
						<input type="radio" id="search_filter_room_capacity2" value="2" name="filter_room_capacity" onclick="filtering_room_capacity()" class="filter_room_capacity">
					</div>
					<div class="search_filter_room_capacity_wrap">
						<label for="search_filter_room_capacity4">4인~</label>					
						<input type="radio" id="search_filter_room_capacity4" value="4" name="filter_room_capacity" onclick="filtering_room_capacity()" class="filter_room_capacity">
					</div>
					<div class="search_filter_room_capacity_wrap">
						<label for="search_filter_room_capacity6">6인~</label>					
						<input type="radio" id="search_filter_room_capacity6" value="6" name="filter_room_capacity" onclick="filtering_room_capacity()" class="filter_room_capacity">
					</div>
				</li>
				<li class="search_filter_acc_facility_container">
					<h6>부대시설</h6>
					<div class="search_filter_acc_facility_container">
						<input type="text" id="search_filter_acc_facility" placeholder="검색어를 입력하세요.">
						<button type="button" onclick="filtering_acc_facility()">검색</button>
					</div>
				</li>
			</ul>
		</div>
		<div class="search_btn_wrap">
			<button class="search_btn ${orderBy == 'most' || empty orderBy ? 'now_selected' : '' }" onclick="location.href='searchingListProc.do?orderBy=most&selectedDays=${book_checkin_date}&selectedDays=${book_checkout_date }'">인기 많은순</button> <!-- most -->
			<button class="search_btn ${orderBy == 'LPrice' ? 'now_selected' : '' }" onclick="location.href='searchingListProc.do?orderBy=LPrice&selectedDays=${book_checkin_date}&selectedDays=${book_checkout_date }'">가격 낮은순</button> <!-- LPrice -->
			<button class="search_btn ${orderBy == 'HRating' ? 'now_selected' : '' }" onclick="location.href='searchingListProc.do?orderBy=HRating&selectedDays=${book_checkin_date}&selectedDays=${book_checkout_date }'">평점 높은순</button> <!-- HRating -->
		</div>
		<div class="search_wrap">
			<c:set var="cnt_item" value="0"></c:set>
			<c:set var="wishes" value="${wishes }"></c:set>
			<c:forEach var="sa_arr" items="${sa_arr }" >
				<div class="search_acc_card">
					<input type="hidden" value="${sa_arr.min_room_capacity }" name="min_room_capacity">
					<input type="hidden" value="${sa_arr.max_room_capacity }" name="max_room_capacity">
					<input type="hidden" value="${sa_arr.acc_facility }" name="acc_facility">
					<input type="hidden" value="${sa_arr.prt_room_price }" name="room_price">	
					<div class="acc_card_left">
						<img alt="${sa_arr.acc_name }전경 이미지" src="${sa_arr.acc_img }">
					</div>
					<div class="acc_card_middle">
						<ul class="acc_card_right_top">
							<li class="acc_type">${sa_arr.acc_type }</li>
							<li class="acc_name">${sa_arr.acc_name }</li>
							<li class="city_name">${sa_arr.city_name }</li>
							<li class="avg_rating">${sa_arr.prt_avg_rating } <span>(${sa_arr.cnt_reviews }명)</span></li>
							
							<li class="wish_btn">
								<input type="hidden" value="${sa_arr.acc_code}" class="num_cnt">
								<button type="button" onclick="${empty sessionScope.user_email ? 'pleasJoin();' : 'location.href=\'wishListAddcon.do?acc_code=' += sa_arr.acc_code += '\''}"></button> <!-- 찜 추가 버튼 -->
							</li>
								<li class="acc_facility">
									<div>
									<c:forTokens items="${sa_arr.acc_facility }" delims="/" var="facility">
										<span>${facility }</span>
									</c:forTokens>
									</div>
								</li>
						</ul>
						<ul class="acc_card_right">
							<li class="room_price">${sa_arr.prt_room_price }</li>
							<li class="info_link_btn"><button type="button" onclick="location.href='accInfoProc.do?acc_code=${sa_arr.acc_code}&book_checkin_date=${book_checkin_date }&book_checkout_date=${book_checkout_date }'">객실 보기</button></li> <!-- 객실 보기 버튼 -->
						</ul>
					</div>
				</div>
				<input type="hidden" value="${cnt_item =  cnt_item+1}">
			</c:forEach>
		</div>
		</c:if>
		<c:if test="${empty sa_arr }">
			<div class="not_to_search">
				<p>검색 결과가 없습니다.</p>
			</div>
		</c:if>
		<input type="hidden" value="${cnt_item}" id="search_cnt">
	</div>
</section>
<script type="text/javascript" defer="defer">
	let wishList = document.querySelectorAll(".wishList");
	let num_cnts = document.querySelectorAll(".wish_btn .num_cnt");
	let wish_btns = document.querySelectorAll(".wish_btn button");

	num_cnts.forEach((item, index) => {
		wishList.forEach((is_equle) =>{
			if(item.value == is_equle.value){
				wish_btns[index].classList.add("wished");
				
				return;
			}
		});
	});



	let search_cnt = document.getElementById("search_cnt");
	let search_cnt_prt = document.getElementById("search_cnt_prt");
	search_cnt_prt.innerText = search_cnt.value + "개";
	
	function pleasJoin(){
		alert("찜 기능은 로그인 후 이용 가능합니다");
		location.href = "index.jsp?section=user/loginForm.jsp";
	}
	
	function filtering_room_capacity(){
		let cards = document.querySelectorAll(".search_acc_card");
		let targetCapacitys = document.querySelector("input[name='filter_room_capacity']:checked");
		let targetCapacity = targetCapacitys ? targetCapacitys.value : "";
		
		cards.forEach((item)=> {
			let max = item.querySelector('input[name="max_room_capacity"]');
			let min = item.querySelector('input[name="min_room_capacity"]');
			
			if(targetCapacity > max.value){
				item.classList.add("noShow");
				
			}
		});
	}
	function filtering_acc_facility(){
		let filter_acc_facility = document.getElementById("search_filter_acc_facility");
		let cards = document.querySelectorAll(".search_acc_card");
		let facility = filter_acc_facility.value;
		
		
		
		cards.forEach((item)=> {
			let one_facility = item.querySelector('input[name="acc_facility"]');
			
			if(!one_facility.value.includes(facility)){  //  여기 에러남. 위의 값 못받아오는듯 함!
				item.classList.add("noShow");
			}
		});
	}
</script>