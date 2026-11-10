<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/mypage.css"/>
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<section>

	<div class="container">
		<div class="mypage_menu_wrap">
			<a href="mypageInfoProc.do">회원정보</a>
			<a href="myBookingProc.do">예약 확인</a>
			<a href="myWishProc.do">찜목록</a>
			<a href="#">내 리뷰 보기</a> <!-- jsp 만들고 매핑하기! -->
		</div>
		<!-- mypageSection 값 없이 마이페이지로 오면 나타남 -->
		<c:if test="${empty mypageSection}"> 
			<div class="userInfo_wrap">
				<h3>내 정보</h3>
				<form action="mypageInfoProc.do" method="post" name="mfrm">
					<table border="1">
						<tr>
							<th>이름</th>
							<td>
							</td>
						</tr>
						<tr>
						<tr>
							<th>전화번호</th>
							<td>
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
							</td>
						</tr>
						<tr>
							<th>생년월일</th>
							<td>
							</td>
						</tr>
						<tr>
							<th>성별</th>
							<td>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="button" value="수정" onclick="">
								<input type="button" value="탈퇴"  onclick="">
						</tr>
					</table>
				</form>
			</div>
		</c:if>
		<c:if test="${not empty mypageSection }">
			<jsp:include page="${mypageSection }.jsp"></jsp:include>
		</c:if>
	</div>
</section>