<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section>
	<c:set var="df_Checkin" value="${defaultCheckinDate }"/>
	<c:set var="df_Checkout" value="${defaultCheckoutDate }"/>
     <!-- 섹션(상) : 추천숙소(평점순) -> 클릭하면 "해당숙소" 페이지 이동 -->
    <div class="section_top">
        <p>추천 숙소</p>
        <div class="group recommend">
            <div class="rec_row">
            	<c:set value="${recommend_codes }" var="accCode"/>
            	<c:set value="${rec_idto_arr }" var="rec_idto_arr"/>
            	<c:forTokens items="${accCode }" delims="," varStatus="index_num" var="current_code">
            		<div class="rec_card">
	            		<a href="accInfoProc.do?acc_code=${current_code }&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
			                <!-- 이미지 박스-->
			                <div class="imgbox">
			                    <img src="${rec_idto_arr[index_num.count-1].acc_img }" alt="추천숙소${index_num.count-1}"/>
			                    <!-- 이미지 안에 배지(평점) -->
			                    <div class="badgeLike_box">
			                        <span class="badge_like">${rec_idto_arr[index_num.count-1].prt_avg_rev_rating }</span>
			                    </div>
			                </div>
			                <!-- 숙소구분(호텔/모텔/펜션) -->
			                <p class="acc_cate">${rec_idto_arr[index_num.count-1].acc_type }</p> <!-- 타입 -->
			                <p class="acc_name">${rec_idto_arr[index_num.count-1].acc_name }</p>
			                <p class="acc_city">${rec_idto_arr[index_num.count-1].city_name }</p>
	            		</a>
	            	</div>
	            	<c:if test="${index_num.count == 4 }">
				        </div>
				        <div class="rec_row">
	            	</c:if>
            	</c:forTokens>
            </div>
    	</div>

    <!-- 섹션(중) : 이달의 여행지 -> 클릭하면 "지역숙소 검색" 페이지 이동 -->
    <div class="section_middle">
        <p>이달의 여행지</p>
        <div class="monthRegion">
       		<!-- 첫번째 카드(지역) -->
       		<div class="month_card">
       			<a href="searchingListProc.do?city_name=서울&selectedDays=${defaultCheckinDate }&selectedDays=${defaultCheckoutDate }">
       				<img src="image/city/1.jpg" alt="이달의 지역1"/>
       			</a>
       		</div>
       		<!-- 두번째 카드(지역) -->
       		<div class="month_card">
       			<a href="searchingListProc.do?city_name=부산&selectedDays=${defaultCheckinDate }&selectedDays=${defaultCheckoutDate }">
       				<img src="image/city/2.jpg" alt="이달의 지역2"/>
       			</a>
       		</div>
       		<!-- 세번째 카드(지역) -->
			<div class="month_card">
       			<a href="searchingListProc.do?city_name=제주&selectedDays=${defaultCheckinDate }&selectedDays=${defaultCheckoutDate }">
       				<img src="image/city/3.jpg" alt="이달의 지역3"/>
       			</a>
       		</div>       		
       		<!-- 네번째 카드(지역) -->
        	<div class="month_card">
       			<a href="searchingListProc.do?city_name=인천&selectedDays=${defaultCheckinDate }&selectedDays=${defaultCheckoutDate }">
       				<img src="image/city/4.jpg" alt="이달의 지역4"/>
       			</a>
       		</div>
        	<!-- 다섯번째 카드(지역) -->
        	<div class="month_card">
       			<a href="searchingListProc.do?city_name=평창&selectedDays=${defaultCheckinDate }&selectedDays=${defaultCheckoutDate }">
       				<img src="image/city/5.jpg" alt="이달의 지역5"/>
       			</a>
       		</div>
        	<!-- 여섯째 카드(지역) -->
        	<div class="month_card">
       			<a href="searchingListProc.do?city_name=안동&selectedDays=${defaultCheckinDate }&selectedDays=${defaultCheckoutDate }">
       				<img src="image/city/6.jpg" alt="이달의 지역6"/>
       			</a>
       		</div>
        </div>
    </div>

    <!-- 섹션(하) : 랜덤 리뷰 -> 클릭하면 "해당숙소" 페이지 이동 -->
    <!-- 섹션(하) : 랜덤 리뷰 -> 클릭하면 "해당숙소" 페이지 이동 -->
    <div class="section_bottom">
        <p>실시간 <span class="hot">Hot</span> review</p>
        <!-- 리뷰 첫번째 행 -->
	        <div class="rev_row">
	        <c:forEach items="${indto_arr}" var="indto">
	            <!-- 첫번째 카드 -->
	            <div class="rev_card">
	                <a href="accInfoProc.do?acc_code=${indto.acc_code }&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
	                    <!-- 왼쪽 : 사진 + 숙소명 + 평점/리뷰수 -->
	                    <div class="rev_left">
	                        <div class="rev_imgbox">
	                            <img src="${indto.acc_img }" alt="리뷰 이미지"/>
	                        </div>
	                        <p class="rev_acc_name">${indto.acc_name }</p>
	                        <div class="badgereview_box">
	                            <span class="badge_review">${indto.avg_rev_rating }</span>
	                            <span class="rev_count">리뷰 ${indto.count_rev_rating }개</span>
	                        </div>
	                    </div>
	                    <!-- 오른쪽 : 리뷰 제목 + 리뷰 상세내용 -->
	                    <div class="rev_right">
	                        <p class="rev_title">${indto.city_name}</p>
	                        <p class="rev_content">${indto.rev_content}</p>
	                        <div class="rev_right_bottom">
		                        <p class="user_name">${indto.user_name}</p>
		                        <p class="date">${indto.rev_created_datetime}</p>
		                    </div>
	                    </div>
	                </a>
	            </div>
			</c:forEach>
		</div>
    </div>
</section>