<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="style/searchList.css"/>
<!-- 히든 -->
<div>
	<input type="hidden">
	<c:if test="${not empty wishes}">
		<c:forEach items="${wishes }" var="wishe">
			<p>${wishe }</p>
		</c:forEach>
	</c:if>
</div>
<!-- 히든 -->

<section>
	<div class="contaier">
		<div class="page_top">
			<h2>숙소 검색 결과</h2>
			<p>총 <span id="search_cnt_prt"></span>의 숙소가 검색되었습니다.
		</div>
		<form class="search_filter">
			<p>검색 결과</p>
			<ul class="filterList">
				<li class="search_filter_room_price_container">	
					<h6>가격</h6>
					<div class="search_filter_room_capacity_wrap">
						<!-- 슬라이드바 넣을 자리 -->
					</div>
				</li>			
				<li class="search_filter_room_capacity_container">
					<h6>적정 인원</h6>
					<div class="search_filter_room_capacity_wrap">
						<label for="search_filter_room_capacity2">2인실</label>
						<input type="checkbox" id="search_filter_room_capacity2" value="2">
					</div>
					<div class="search_filter_room_capacity_wrap">
						<label for="search_filter_room_capacity4">4인실</label>					
						<input type="checkbox" id="search_filter_room_capacity4" value="4">
					</div>
					<div class="search_filter_room_capacity_wrap">
						<label for="search_filter_room_capacity2">4인 이상</label>					
						<input type="checkbox" id="search_filter_room_capacityover4" value="5">
					</div>
				</li>
				<li class="search_filter_acc_facility_container">
					<h6>부대시설</h6>
					<div class="search_filter_acc_facility_container">
						<input type="text" id="search_filter_acc_facility" placeholder="검색어를 입력하세요.">
					</div>
				</li>
			</ul>
		</form>
		<div class="search_btn_wrap">
			<button class="now_selected" onclick="">인기 많은순</button>
			<button onclick="">가격 낮은순</button>
			<button onclick="">평점 높은순</button>
		</div>
		<div class="search_wrap">
			<c:set var="cnt_item" value="0"></c:set>
			<c:set var="wishes" value="${wishes }"></c:set>
			<c:forEach var="sa_arr" items="${sa_arr }" >
				<div class="search_acc_card">
					<div class="acc_card_left">
						<img alt="${sa_arr.acc_name }전경 이미지" src="${sa_arr.acc_img }">
					</div>
					<div class="acc_card_middle">
						<ul class="acc_card_right_top">
							<li class="acc_type">${sa_arr.acc_type }</li>
							<li class="acc_name">${sa_arr.acc_name }</li>
							<li class="city_name">${sa_arr.city_name }</li>
							<li class="avg_rating">${sa_arr.avg_rating } <span>(${sa_arr.cnt_reviews }명)</span></li>
							
							<li class="wish_btn">
								<!-- wishList 색칠할지 검사받고 칠하기 -->
								<button type="button" onclick="${empty sessionScope.user_email ? 'pleasJoin();' : 'location.href=\'wishListAddcon.do?acc_code=' += sa_arr.acc_code += '\''}"></button> <!-- 찜 추가 버튼 -->
							</li>
								<li class="acc_facility">
									<div>
									<%-- <c:forTokens items="" delims=""> --%>
										<span>부대시설</span><!-- 이 자리에 추후 forTokens 으로 받아온 부대시설 추가 -->	
										<span>부대시설</span><!-- 이 자리에 추후 forTokens 으로 받아온 부대시설 추가 -->	
										<span>부대시설</span><!-- 이 자리에 추후 forTokens 으로 받아온 부대시설 추가 -->	
									<%-- </c:forTokens>	 --%>
									</div>
								</li>
						</ul>
						<ul class="acc_card_right">
							<li class="room_price">${sa_arr.room_price }</li>
							<li class="info_link_btn"><button type="button" onclick="location.href='accInfoProc.do?acc_code=${sa_arr.acc_code}&book_checkin_date=${book_checkin_date }&book_checkout_date=${book_checkout_date }'">객실 보기</button></li> <!-- 객실 보기 버튼 -->
						</ul>
					</div>
				</div>
				<input type="hidden" value="${cnt_item =  cnt_item+1}">
			</c:forEach>
		</div>
		<input type="hidden" value="${cnt_item}" id="search_cnt">
	</div>
</section>
<script type="text/javascript" defer="defer">
	let search_cnt = document.getElementById("search_cnt");
	let search_cnt_prt = document.getElementById("search_cnt_prt");
	
	search_cnt_prt.innerText = search_cnt.value + "개";
	
	function pleasJoin(){
		alert("찜 기능은 로그인 후 이용 가능합니다");
		location.href = "index.jsp?section=user/loginForm.jsp";
	}
</script>