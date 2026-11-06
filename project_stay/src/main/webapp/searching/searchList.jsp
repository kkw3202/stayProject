<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section>
	<div class="contaier">
		<div class="search_wrap">
			<c:forEach var="sa_arr" items="${sa_arr }" >
				<div class="search_acc_card">
					<div class="acc_card_left">
						<img alt="${sa_arr.acc_name }전경 이미지" src="${sa_arr.acc_img }">
					</div>
					<div class="acc_card_right">
						<ul class="acc_card_right_top">
							<li class="acc_type">${sa_arr.acc_type }</li>
							<li class="acc_name">${sa_arr.acc_name }</li>
							<li class="city_name">${sa_arr.city_name }</li>
							<li class="avg_rating">${sa_arr.avg_rating }</li>
							<li class="cnt_reviews">${sa_arr.cnt_reviews }</li>
							<li class="wish_btn">
								<button type="button" onclick="#.do?acc_code=${sa_arr.acc_code}"></button>
							</li>				
						</ul>
						<ul class="acc_card_right_bottom">
							<li class="room_price">${sa_arr.room_price }</li>
						</ul>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>