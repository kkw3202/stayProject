<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/mypageWish.css"/>

<div class="boardWrap">
	<h3>내 찜 목록</h3>
	<c:choose>
		<%-- 찜 리스트가 없는 경우 --%>
		<c:when test="${empty wlist }">
			<span class="noList">찜한 목록이 없습니다.</span>
		</c:when>
		<c:otherwise>
			<%-- 반복문으로 게시판 형식으로 그리기 --%>
			<c:forEach var = "arr" items="${wlist }">
				<table class="myWishList">
					<%-- 왼쪽 : 이미지 / 하트 모양 (누르면 찜목록 해제가능) --%>
					<tbody class="left">
						<tr>
							<td colspan = "2">
								<img src="${arr.acc_img }" alt="${arr.acc_name }"/>
								
							</td>
							<td>
								<button class="badge_group" onclick="cancelWish(${arr.wish_code})">
									<span class="wish_badge" >♥</span>
								</button>
							</td>
						</tr>
					</tbody>
					<%-- 오른쪽 :  숙소명 / 유형 / 숙소주소 / 부대시설 / 입실~퇴실시간  --%>
					<tbody class="right">
						<tr>
							<th class="label">숙소명</th>
							<td class="data">${arr.acc_name }</td>
						</tr>
						<tr>
							<th class="label">유형</th>
							<td class="data">${arr.acc_type }</td>
						</tr>
						<tr>
							<th class="label">주소</th>
							<td class="data">${arr.acc_address }</td>
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
							<td class="data">${arr.acc_checkinTime } ~ ${arr.acc_checkoutTime } </td>
						</tr>
					
					</tbody>
					
				</table>
			</c:forEach>
			
		</c:otherwise>
	</c:choose>
	
	<%-- 페이징 부분 (역시, 찜한 목록이 있는 경우에만 보이기 --%>
	<c:if test ="${not empty wlist }" >
		<div class="paging">
		<c:if test = "${startPage > pageBlock }">
			<input type="button" onclick="pageBtn1(${startPage},${pageBlock})" value="이전"/>
		</c:if>
		
		<%-- 페이징 번호 --%>
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<%-- 현재 페이지에는 클래스 이름 넣어, 표시하기 --%>
			<c:choose>
				<c:when test="${i == currentPage}">
					<input class="currentPage_btn" type="button" onclick="pageBtn2(${i})" value="${i }"/>
				</c:when>
				<c:otherwise>
					<input type="button" onclick="pageBtn2(${i})" value="${i }"/>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		
		<%-- 페이징 번호 --%>
		<c:if test = "${endPage < pageCount}">
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
	
	// 페이징 번호 관련 함수
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
    