<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
 <style>
   #main {
    margin: 0;
    width: 350px;
    background-color: #fff;
    padding: 28px 22px 30px;
    border-radius: 18px;
    box-shadow: 0 10px 28px rgba(0, 0, 0, 0.18);
    position: absolute;
    top: 68px;
    left: 260px;
    z-index: 9999;
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Noto Sans KR", sans-serif;
}

#main .head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 44px;
    font-size: 17px;
    font-weight: 600;
    color: #222;
    margin-bottom: 16px;
    padding: 0 6px;
}

#main .head button {
    border: 1px solid #ddd;
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background-color: #fff;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.2s ease;
    color: #555;
    font-size: 15px;
}

#main .head button:hover {
    background-color: #f5f5f5;
    border-color: #bbb;
}

table {
    border-collapse: collapse;
    border: none;
    width: 100%;
}

table span,
table td,
table th {
    color: #333 !important;
    font-size: 14px;
}

table .sunday,
table .sunday span {
    color: #e53935 !important;
}

table .saterday,
table .saterday span {
    color: #1e88e5 !important;
}

table .beforDay {
    color: #bdbdbd !important;
}

td {
    border: none;
    text-align: center;
    width: 44px;
    height: 42px;
    vertical-align: middle;
    padding: 1px;
    cursor: pointer;
    transition: background-color 0.15s ease;
}

/* 모든 날짜 공통 호버 */
td:hover {
    background-color: #e8f5e9;
    border-radius: 50%;
}

td:hover span {
    background-color: transparent;
}

span {
    font-weight: 500;
    display: inline-block;
    width: 34px;
    height: 34px;
    line-height: 34px;
    border-radius: 50%;
    transition: background-color 0.15s ease;
}

.noShow{
	display: none;
}
</style>
	<header id="header">
		<!-- 좌측 상단 -->
		<div class="left_top">
			<a href="#">
				<img alt="로고이미지" src="image/logo3.png">
			</a>
		</div>

		<!-- 우측 상단 -->
		<nav class="right_top">

			<c:choose>
				<c:when test="${empty sessionScope.user_email}"> <!-- 칼럼 명 변경후 재확인 필요. -->
					<a href="index.jsp?section=showbook.jsp">비회원 예약조회</a>
					<a href="index.jsp?section=join.jsp">회원가입</a>
					<a href="index.jsp?section=login.jsp">로그인</a>
				</c:when>
				<c:otherwise>
					<a style="display: none;"></a>
					<a href="index.jsp?section=mypage.jsp">마이페이지</a>
					<a href="#">로그아웃</a> <!-- 로그아웃 기능으로 매핑 필요 -->
				</c:otherwise>
			</c:choose>
					<a href="inquiryListProc.do">1:1 문의</a>

		</nav>

		<!-- 하단 -->
		<div class="bottom">
			<!-- <img alt="배너" src="image/bana1.jpg" class="bana"> -->
			<!-- 검색창 css용 묶음 -->
			<div class="search_wrap">
				<form action="searchingListProc.do" method="get" name="sform"> <!-- 검색 기능으로 매핑 필요 -->
					<!-- 지역, 추후 여력 되면 select 말고, 팝업해서 보기 좋게 바꾸기 -->
					<div class="city">
					<img alt="핀 이미지" src="image/ping.svg" class="search_bar_img">
						<select name="city_name"> <!-- 칼럼 명 변경후 재확인 필요. -->
							<option selected="selected" disabled="disabled" value="">지역 선택</option>
							<option value="서울">서울</option>
							<option value="부산">부산</option>
							<option value="제주">제주</option>
							<option value="인천">인천</option>
							<option value="평창">평창</option>
							<option value="안동">안동</option>
							<option value="여수">여수</option>
							<option value="경주">경주</option>
							<option value="제천">제천</option>
							<option value="태안">태안</option>
						</select>
					</div>
					<!-- 숙박 기간 -->
					<div class="date" onclick="show_calendar()">
						<img alt="달력이미지" src="image/cal.svg" class="search_bar_img">
						<!-- 나중에 달력 붙이기 -->
						<p>일정 선택</p> <!-- 달력에서 선택한 날 띄우기 -->
						<div class="saveSelWrap"></div>
					</div>
					<div id="main" class="noShow"></div>
					<!-- 숙소 유형 -->
					<div class="type">
						<img alt="호텔" src="image/bilding.svg" class="search_bar_img">
						<select name="acc_type"> <!-- 칼럼 명 변경후 재확인 필요. -->
							<option selected="selected" disabled="disabled" value="">숙소 유형 선택</option>
							<option value="호텔">호텔</option>
							<option value="모텔">모텔</option>
							<option value="펜션">펜션</option>
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
	<script type="text/javascript">
		function form_submit(){
			let f = document.sform;
			f.submit();
		}
	</script>
</header>