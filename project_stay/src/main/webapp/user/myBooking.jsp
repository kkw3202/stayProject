<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="style/myBooking.css"/>
<div class="myBookingList">
	<h3>예약확인</h3>
	<c:forEach var = "arr" items="${mblist}">
		<table class="myBooking_table">
			<%-- 왼쪽 숙소 이미지  --%>
			<tbody>
				<tr>
					<td>
						<img src="${arr.acc_img }" alt="${arr.acc_name}"/>
					</td>
				</tr>
			</tbody>
			<%-- 오른쪽 예매 정보 --%>
			<tbody>
				<tr>
					<th class="label">숙소명</th>
					<td class="data acc_name">${arr.acc_name }</td>
				</tr>
			</tbody>
		</table>
	
	</c:forEach>

</div>