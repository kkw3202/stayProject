<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/mypage.css"/>

<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<section>

	<div class="mypage_container">
		<div class="mypage_menu_wrap">
			<a href="mypageInfoProc.do">회원정보</a>
			<a href="myBookingProc.do">예약 확인</a>
			<a href="myWishProc.do">찜목록</a>
			<a href="#">내 리뷰 보기</a> <%-- jsp 만들고 매핑하기! --%>
		</div>
		<%-- mypageSection 값 없이 마이페이지로 오면 나타남 --%>
		<c:if test="${empty mypageSection}"> 
			<div class="userInfo_wrap">
				<h3>내 정보</h3>
					<table class="myinfo_table">
						<tr>
							<th class="label">이름</th>
							<td class="data">${udto.user_name }</td>
						</tr>
						<tr>
						<tr>
							<th class="label">전화번호</th>
							<td class="data">${phone_number}</td>
						</tr>
						<tr>
							<th class="label">이메일</th>
							<td class="data">${udto.user_email }</td>
						</tr>
						<tr>
							<th class="label">생년월일</th>
							<td class="data">${udto.user_birth }</td>
						</tr>
						<tr>
							<th class="label">성별</th>
							<td class="data">	
								<c:if test="${udto.user_gender == 1}">남성</c:if>
								<c:if test="${udto.user_gender == 2}">여성</c:if>
								<c:if test="${udto.user_gender == 3}">기타</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="button" value="수정" onclick="location.href='mypageModProc.do'"/>
								<input type="button" value="탈퇴"  onclick="location.href='mypageDeleteCon.do'"/>
							</td>
						</tr>
					</table>
			</div>
		</c:if>
		<c:if test="${not empty mypageSection }">
			<jsp:include page="${mypageSection }.jsp"></jsp:include>
		</c:if>
	</div>
</section>