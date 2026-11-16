<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="style/myBooking.css"/>
<div class="myBookingList">
	<h3>예약확인</h3>
	<c:forEach var = "arr" items="${mblist}">
		<table class="myBooking_table">
			<%-- 왼쪽 숙소 이미지 + 숙소명 / 숙소타입  --%>
			<tbody>
				<tr>
					<td>
						<img src="${arr.acc_img }" alt="${arr.acc_name}"/>
					</td>
				</tr>
				<tr>
					<th class="label">숙소명</th>
					<td class="data acc_name">${arr.acc_name }
					<span class="accType">(${arr.acc_type})</span></td>
				</tr>
			</tbody>
			<%-- 오른쪽 예매 정보 --%>
			<tbody>
				<tr>
					<th class="label">예약번호</th>
					<td class="data acc_name">${arr.book_number }</td>
				</tr>
				<tr>
					<th class="label">예약자</th>
					<td class="data book_name">${arr.book_name }</td>
				</tr>
				
				<tr>
					<th class="label">숙소주소</th>
					<td class="data acc_address">${arr.acc_address }</td>
				</tr>
				<tr>
					<th class="label">객실명</th>
					<td class="data room_name">${arr.room_name }</td>
				</tr>
				<tr>
					<th class="label">금액</th>
					<td class="data room_price">${arr.room_price * arr. }</td>
				</tr>
				<tr>
					<th class="label">예약일</th>
					<td class="data book_date">${arr.book_date }</td>
				</tr>
				<tr>
					<th class="label">상태</th>
					<%-- 예약이 취소된 경우 --%>
					<c:choose>
						<c:when test="${arr.book_chk_status ==1 }"><span class="book_cancel">예약취소</span></c:when>
						<c:when test="${arr.book_chk_status ==2 }"><span class="book_expire">만료</span></c:when>
						<c:otherwise><span class="book_ing">예약중</span></c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<th class="label">예약번호</th>
					<td class="data acc_name">${arr.book_number }</td>
				</tr>
			
			</tbody>
			
		</table>
	
	</c:forEach>

</div>