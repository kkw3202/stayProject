<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
 <link rel="stylesheet" href="style/mumulda_calendar.css">
	<header id="header">
		<!-- 좌측 상단 -->
		<div class="left_top">
			<a href="index.jsp">
				<img alt="로고이미지" src="image/logo3.png">
			</a>
		</div>

		<!-- 우측 상단 -->
		<nav class="right_top">

			<c:choose>
				
				<c:when test="${not empty sessionScope.user_email and sessionScope.user_email != 'admin@naver.com'}">
					<a href="mypageInfoProc.do">마이페이지</a>
					<a href="logoutProc.do">로그아웃</a>
					<a href="inquiryListProc.do">1:1 문의</a>	
				</c:when>
				
				<c:when test="${not empty sessionScope.user_email and sessionScope.user_email eq 'admin@naver.com'}">
					
					<a href="customerListProc.do">회원관리</a>
					<a href="AdminBookListProc.do">예약관리</a>
					<a href="AdminAccListProc.do">숙소관리</a>
					<a href="#">1:1문의 관리</a>  <%-- 추후 jsp 파일 만들고 매핑하기 --%>
					<span>[관리자]</span> <%-- 추후 css 붙이고 태그 바꾸기 --%>
					<a href="logoutProc.do">로그아웃</a>
				</c:when>
				
				<c:otherwise>
					<a href="index.jsp?section=showbook.jsp">비회원 예약조회</a>
					<a href="index.jsp?section=user/joinForm.jsp">회원가입</a>
					<a href="index.jsp?section=user/loginForm.jsp">로그인</a>
					<a href="inquiryListProc.do">1:1 문의</a>
				</c:otherwise>
			</c:choose>
			
			

		</nav>

		<!-- 하단 -->
		<div class="bottom" id="header_bottom">
			<!-- <img alt="배너" src="image/bana1.jpg" class="bana"> -->
			<!-- 검색창 css용 묶음 -->
			<div class="search_wrap">
				<form action="searchingListProc.do" method="get" name="sform"> <!-- 검색 기능으로 매핑 필요 -->
					<!-- 지역, 추후 여력 되면 select 말고, 팝업해서 보기 좋게 바꾸기 -->
					<div class="city">
					<img alt="핀 이미지" src="image/ping.svg" class="search_bar_img">
						<select name="city_name"> <!-- 칼럼 명 변경후 재확인 필요. -->
							<option ${city_name eq null ? "selected='selected'" : "" } disabled="disabled" value="">지역 선택</option>
							<option ${city_name eq "서울" ? "selected='selected'" : "" } value="서울">서울</option>
							<option ${city_name eq "부산" ? "selected='selected'" : "" } value="부산">부산</option>
							<option ${city_name eq "제주" ? "selected='selected'" : "" } value="제주">제주</option>
							<option ${city_name eq "인천" ? "selected='selected'" : "" } value="인천">인천</option>
							<option ${city_name eq "평창" ? "selected='selected'" : "" } value="평창">평창</option>
							<option ${city_name eq "안동" ? "selected='selected'" : "" } value="안동">안동</option>
							<option ${city_name eq "여수" ? "selected='selected'" : "" } value="여수">여수</option>
							<option ${city_name eq "경주" ? "selected='selected'" : "" } value="경주">경주</option>
							<option ${city_name eq "제천" ? "selected='selected'" : "" } value="제천">제천</option>
							<option ${city_name eq "태안" ? "selected='selected'" : "" } value="태안">태안</option>
						</select>
					</div>
					<!-- 숙박 기간 -->
					<div class="date" onclick="show_calendar()">
						<img alt="달력이미지" src="image/cal.svg" class="search_bar_img">
						<!-- 나중에 달력 붙이기 -->
						<p id="day_show">일정 선택</p> <!-- 달력에서 선택한 날 띄우기 -->
						<div class="saveSelWrap">
							<div class="saveSel1 hidden_saveSel">
								<c:if test="${book_checkin_date != null && is_index != true}">
									<input type="hidden" value="${book_checkin_date }" name="selectedDays" class="saveSel">
								</c:if>
							</div>
							<div class="saveSel2 hidden_saveSel">
								<c:if test="${book_checkout_date != null && is_index != true}">
									<input type="hidden" value="${book_checkout_date }" name="selectedDays" class="saveSel">
								</c:if>
							</div>
							<c:if test="${sessionScope.book_checkin_date  }"></c:if>
						</div>
					</div>
					<div id="main" class="noShow"></div>
					<!-- 숙소 유형 -->
					<div class="type">
						<img alt="호텔" src="image/bilding.svg" class="search_bar_img">
						<select name="acc_type"> <!-- 칼럼 명 변경후 재확인 필요. -->
							<option ${acc_type eq null ? "selected='selected'" : "" } disabled="disabled" value="">숙소 유형 선택</option>
							<option ${acc_type eq "호텔" ? "selected='selected'" : "" } value="호텔">호텔</option>
							<option ${acc_type eq "모텔" ? "selected='selected'" : "" } value="모텔">모텔</option>
							<option ${acc_type eq "펜션" ? "selected='selected'" : "" } value="펜션">펜션</option>
						</select>
					</div>
					<!-- 숙소명 검색어 입력창 -->
					<div class="search_bar">
						<input type="text" name="acc_name" placeholder="숙소명 입력(선택)"/>
					</div>
					<!-- 검색 버튼 -->
					<div class="btn" onclick="form_submit()">
						<img alt="돋보기_검색" src="image/search.svg">
					</div>
				</form>
			</div>
		</div>
		
	
	<script type="text/javascript" src="js/cala.js" defer></script>
	<script type="text/javascript" defer>
		let day_show = document.getElementById("day_show");
		let prt_book_checkin_date = formatDate("${book_checkin_date}");
		let prt_book_checkout_date = formatDate("${book_checkout_date}");
		
		if(${book_checkin_date != null && is_index != true}) day_show.innerText = prt_book_checkin_date + " - " + prt_book_checkout_date;
		
		function formatDate(date) {
	        let year = date.substring(0, 4);
	        let month = date.substring(4, 6);
	        let day = date.substring(6, 8);
	        return year + "." + month + "." + day;
		}
	</script>
	<script type="text/javascript">
		function form_submit(){
			let f = document.sform;
			let saveSel = document.querySelectorAll(".saveSel");
			
			if(saveSel.length < 2){
				alert("날짜를 선택해주세요");
				return;
			}
			f.submit();
		}
	</script>
</header>