<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="style/mypageWish.css"/>

<div class="boardWrap" >
	<h3>내 찜 목록 <span class="wishCount">(총 : ${count }개)</span></h3>  
	
	<c:choose>
		<%-- 찜 리스트가 없는 경우 --%>
		<c:when test="${empty wlist }">
			<div class="noList">
				<p>찜한 목록이 없습니다.</p>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="arr" items="${wlist }">
				<div class="wish_card">
					<%-- 왼쪽 : 이미지 + 우측 상단 하트(찜 해제) --%>
					<div class="left_box">
						<div class="imageBox">
							<a href="accInfoProc.do?acc_code=${arr.acc_code}&book_checkin_date=${defaultCheckinDate}&book_checkout_date=${defaultCheckoutDate}">
								<img src="${arr.acc_img }" alt="${arr.acc_name }"/>
							</a>
							<button type="button" class="badge_group" title="찜 해제" onclick="cancelWish(${arr.wish_code})">
								<span class="wish_badge">♥</span>
							</button>
						</div>
					</div>

					<%-- 오른쪽 : 숙소명 등 정보들 --%>
					<table class="right_box">
						<tr>
							<th class="label">숙소명</th>
							<td class="data">
								<div class="title_row">
									<span class="acc_name_text">${arr.acc_name }</span>
									<input type="button"
										onclick="location.href='accInfoProc.do?acc_code=${arr.acc_code}&book_checkin_date=${defaultCheckinDate}&book_checkout_date=${defaultCheckoutDate}'"
										class="goAcc_btn" value="예매하기"/>
								</div>
							</td>
						</tr>
						<tr>
							<th class="label">유형</th>
							<td class="data"><span class="acc_type_badge">${arr.acc_type }</span></td>
						</tr>
						<tr>
							<th class="label">숙소주소</th>
							<td class="data">${arr.acc_address }</td>
						</tr>
						<tr>
							<th class="label">부대시설</th>
							<td class="data">${arr.acc_facility }</td>
						</tr>
						<tr>
							<th class="label">입실~퇴실</th>
							<td class="data">${arr.acc_checkinTime } ~ ${arr.acc_checkoutTime }</td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<%-- 페이징 부분 --%>
	<c:if test="${not empty wlist }">
		<div class="paging">
			<c:if test="${startPage > pageBlock }">
				<input type="button" onclick="pageBtn1(${startPage},${pageBlock})" value="이전"/>
			</c:if>
			
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<c:choose>
					<c:when test="${i == currentPage or (empty currentPage and i == 1)}">
						<input class="currentPage_btn" type="button" onclick="pageBtn2(${i})" value="${i }"/>
					</c:when>
					<c:otherwise>
						<input type="button" onclick="pageBtn2(${i})" value="${i }"/>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			<c:if test="${endPage < pageCount}">
				<input type="button" onclick="pageBtn3(${startPage},${pageBlock})" value="다음"/>
			</c:if>
		</div>	
	</c:if>
</div>

<script>
	function cancelWish(wish_code){
		if(confirm("해당 숙소를 찜목록에서 삭제하시겠습니까?")){
			location.href="myWishDeleteCon.do?wish_code="+wish_code;
		}
	}
	
	function pageBtn1(startPage,pageBlock){
		location.href="myWishProc.do?pageNum="+ (startPage-pageBlock);
	}
	function pageBtn2(i){
		location.href="myWishProc.do?pageNum="+ i;
	}
	function pageBtn3(startPage,pageBlock){
		location.href="myWishProc.do?pageNum="+ (startPage+pageBlock);
	}
</script>