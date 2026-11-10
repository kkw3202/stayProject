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
            <!-- 추천숙소 첫번째 행 -->
            <div class="rec_row">
            	<!-- 첫번째 카드 -->
            	<div class="rec_card">
            		<a href="accInfoProc.do?acc_code=1&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
		                <!-- 이미지 박스-->
		                <div class="imgbox">
		                    <img src="image/hotel/1.jpg" alt="추천숙소1"/>
		                    <!-- 이미지 안에 배지(평점) -->
		                    <div class="badgeLike_box">
		                        <span class="badge_like">★4.9</span>
		                    </div>
		                </div>
		                <!-- 숙소구분(호텔/모텔/펜션) -->
		                <p class="acc_cate">호텔</p>
		                <p class="acc_name">서울 한강뷰 호텔</p>
		                <p class="acc_city">서울</p>
            		</a>
            	</div>
            	<!-- 두번째 카드 -->
            	<div class="rec_card">
            		<a href="accInfoProc.do?acc_code=2&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
		                <!-- 이미지 박스-->
		                <div class="imgbox">
		                    <img src="image/hotel/2.jpg" alt="추천숙소2"/>
		                    <!-- 이미지 안에 배지(평점) -->
		                    <div class="badgeLike_box">
		                        <span class="badge_like">★4.9</span>
		                    </div>
		                </div>
		                <!-- 숙소구분(호텔/모텔/펜션) -->
		                <p class="acc_cate">호텔</p>
		                <p class="acc_name">서울 시티스테이 호텔</p>
		                <p class="acc_city">서울</p>
            		</a>
            	</div>
            	<!-- 세번째 카드 -->
            	<div class="rec_card">
            		<a href="accInfoProc.do?acc_code=3&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
		                <!-- 이미지 박스-->
		                <div class="imgbox">
		                    <img src="image/motel/3.jpg" alt="추천숙소3"/>
		                    <!-- 이미지 안에 배지(평점) -->
		                    <div class="badgeLike_box">
		                        <span class="badge_like">★4.8</span>
		                    </div>
		                </div>
		                <!-- 숙소구분(호텔/모텔/펜션) -->
		                <p class="acc_cate">모텔</p>
		                <p class="acc_name">서울 달빛 모텔</p>
		                <p class="acc_city">서울</p>
            		</a>
            	</div>
            	<!-- 네번째 카드 -->
            	<div class="rec_card">
            		<a href="accInfoProc.do?acc_code=4&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
		                <!-- 이미지 박스-->
		                <div class="imgbox">
		                    <img src="image/hotel/3.jpg" alt="추천숙소4"/>
		                    <!-- 이미지 안에 배지(평점) -->
		                    <div class="badgeLike_box">
		                        <span class="badge_like">★4.8</span>
		                    </div>
		                </div>
		                <!-- 숙소구분(호텔/모텔/펜션) -->
		                <p class="acc_cate">호텔</p>
		                <p class="acc_name">부산 오션브리즈 호텔</p>
		                <p class="acc_city">부산</p>
            		</a>
            	</div>
            </div>
            <!-- 추천숙소 두번째 행 -->
            <div class="rec_row">
            	<!-- 다섯번째 카드 -->
            	<div class="rec_card">
            		<a href="accInfoProc.do?acc_code=5&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
		                <!-- 이미지 박스-->
		                <div class="imgbox">
		                    <img src="image/hotel/4.jpg" alt="추천숙소5"/>
		                    <!-- 이미지 안에 배지(평점) -->
		                    <div class="badgeLike_box">
		                        <span class="badge_like">★4.7</span>
		                    </div>
		                </div>
		                <!-- 숙소구분(호텔/모텔/펜션) -->
		                <p class="acc_cate">호텔</p>
		                <p class="acc_name">부산 마린스카이 호텔</p>
		                <p class="acc_city">부산</p>
            		</a>
            	</div>
            	<!-- 여섯번째 카드 -->
            	<div class="rec_card">
            		<a href="accInfoProc.do?acc_code=6&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
		                <!-- 이미지 박스-->
		                <div class="imgbox">
		                    <img src="image/hotel/2.jpg" alt="추천숙소6"/>
		                    <!-- 이미지 안에 배지(평점) -->
		                    <div class="badgeLike_box">
		                        <span class="badge_like">★4.6</span>
		                    </div>
		                </div>
		                <!-- 숙소구분(호텔/모텔/펜션) -->
		                <p class="acc_cate">모텔</p>
		                <p class="acc_name">부산 블루나잇 모텔</p>
		                <p class="acc_city">부산</p>
            		</a>
            	</div>
            	<!-- 일곱번째 카드 -->
            	<div class="rec_card">
            		<a href="accInfoProc.do?acc_code=7&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
		                <!-- 이미지 박스-->
		                <div class="imgbox">
		                    <img src="image/hotel/5.jpg" alt="추천숙소7"/>
		                    <!-- 이미지 안에 배지(평점) -->
		                    <div class="badgeLike_box">
		                        <span class="badge_like">★4.5</span>
		                    </div>
		                </div>
		                <!-- 숙소구분(호텔/모텔/펜션) -->
		                <p class="acc_cate">호텔</p>
		                <p class="acc_name">제주 오름가든 호텔</p>
		                <p class="acc_city">제주도</p>
            		</a>
            	</div>
            	<!-- 여덟번째 카드 -->
            	<div class="rec_card">
            		<a href="accInfoProc.do?acc_code=8&book_checkin_date=${defaultCheckinDate }&book_checkout_date=${defaultCheckoutDate }">
		                <!-- 이미지 박스-->
		                <div class="imgbox">
		                    <img src="image/pension/1.jpg" alt="추천숙소8"/>
		                    <!-- 이미지 안에 배지(평점) -->
		                    <div class="badgeLike_box">
		                        <span class="badge_like">★4.4</span>
		                    </div>
		                </div>
		                <!-- 숙소구분(호텔/모텔/펜션) -->
		                <p class="acc_cate">펜션</p>
		                <p class="acc_name">제주 바람결 펜션</p>
		                <p class="acc_city">제주도</p>
            		</a>
            	</div>
            </div>
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
	                <a href="">
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
	                        <p class="rev_title">${indto.rev_content}</p>
	                        <p class="rev_content">
	                            체크인하고 커튼을 걷는 순간 탁 트인 여수 바다가 한눈에 들어와서 정말 감동이었습니다. 침구류도 5성급 못지않게 폭신하고 청결해서 꿀잠 잤어요. 밤에는 파도 소리 들으며 테라스에서 맥주 한잔했는데 올해 최고의 여행이었습니다!
	                        </p>
	                    </div>
	                </a>
	            </div>
			</c:forEach>
		</div>
    </div>
</section>