<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<link rel="stylesheet" href="style/adminRoom.css">

<div class="admin-room-wrap">

    <h2>객실 상세정보</h2>

    <!-- 1. 등록 및 수정 결과 메시지 -->
    <c:if test="${not empty roomMsg}">
        <div class="room-message">
            <c:out value="${roomMsg}"/>
        </div>
    </c:if>

    <!-- 2. 객실 기본정보 -->
    <div class="room-info-card">

        <div class="room-info-title">

            <h3><c:out value="${room.roomName}"/></h3>

            <c:choose>
                <c:when test="${room.deleted == 0}">
                    <span class="room-status room-status-active">
                        정상
                    </span>
                </c:when>
                <c:otherwise>
                    <span class="room-status room-status-deleted">
                        삭제처리
                    </span>
                </c:otherwise>
            </c:choose>

        </div>

        <!-- 3. 소속 숙소정보 -->
        <div class="room-parent-info">

            <strong>소속 숙소:</strong>

            <c:url var="accInfoUrl" value="/AdminAccInfoProc.do">
                <c:param name="acc_code" value="${room.accCode}"/>
            </c:url>

            <a href="${accInfoUrl}">
                <c:out value="${room.accName}"/>
            </a>

            (숙소코드: ${room.accCode})

            <c:if test="${room.accDeleted == 1}">
                <span class="room-status room-status-deleted">
                    숙소 삭제처리
                </span>
            </c:if>

        </div>

        <!-- 4. 객실 이미지 4장 -->
        <div class="room-info-images">

            <c:if test="${not empty room.roomImg1}">
                <c:url var="roomImageUrl1" value="/${room.roomImg1}"/>
                <img src="${roomImageUrl1}" alt="객실 이미지 1">
            </c:if>

            <c:if test="${not empty room.roomImg2}">
                <c:url var="roomImageUrl2" value="/${room.roomImg2}"/>
                <img src="${roomImageUrl2}" alt="객실 이미지 2">
            </c:if>

            <c:if test="${not empty room.roomImg3}">
                <c:url var="roomImageUrl3" value="/${room.roomImg3}"/>
                <img src="${roomImageUrl3}" alt="객실 이미지 3">
            </c:if>

            <c:if test="${not empty room.roomImg4}">
                <c:url var="roomImageUrl4" value="/${room.roomImg4}"/>
                <img src="${roomImageUrl4}" alt="객실 이미지 4">
            </c:if>

        </div>

        <!-- 5. 객실 상세정보 테이블 -->
        <table class="room-info-table">
            <tbody>

                <tr>
                    <th>객실코드</th>
                    <td>${room.roomCode}</td>

                    <th>숙소코드</th>
                    <td>${room.accCode}</td>
                </tr>

                <tr>
                    <th>객실명</th>
                    <td colspan="3">
                        <c:out value="${room.roomName}"/>
                    </td>
                </tr>

                <tr>
                    <th>소속 숙소명</th>
                    <td colspan="3">
                        <c:out value="${room.accName}"/>
                    </td>
                </tr>

                <tr>
                    <th>객실가격</th>
                    <td>
                        <fmt:formatNumber
                            value="${room.roomPrice}"
                            pattern="#,##0"/>원
                    </td>

                    <th>객실 수</th>
                    <td>${room.roomCount}</td>
                </tr>

                <tr>
                    <th>수용인원</th>
                    <td>${room.roomCapacity}명</td>

                    <th>객실상태</th>
                    <td>
                        <c:choose>
                            <c:when test="${room.deleted == 0}">정상</c:when>
                            <c:otherwise>삭제처리</c:otherwise>
                        </c:choose>
                    </td>
                </tr>

                <tr>
                    <th>객실정보</th>
                    <td colspan="3">
                        <c:out value="${room.roomInfo}"/>
                    </td>
                </tr>

                <tr>
                    <th>객실 편의시설</th>
                    <td colspan="3">
                        <c:out value="${room.roomAmenity}"/>
                    </td>
                </tr>

                <tr>
                    <th>이미지 경로 1</th>
                    <td colspan="3">
                        <c:out value="${room.roomImg1}"/>
                    </td>
                </tr>

                <tr>
                    <th>이미지 경로 2</th>
                    <td colspan="3">
                        <c:out value="${room.roomImg2}"/>
                    </td>
                </tr>

                <tr>
                    <th>이미지 경로 3</th>
                    <td colspan="3">
                        <c:out value="${room.roomImg3}"/>
                    </td>
                </tr>

                <tr>
                    <th>이미지 경로 4</th>
                    <td colspan="3">
                        <c:out value="${room.roomImg4}"/>
                    </td>
                </tr>

            </tbody>
        </table>

    </div>

    <!-- 6. 관리 버튼 -->
    <div class="room-info-buttons">

        <!-- 해당 숙소의 객실 목록 -->
        <c:url var="roomListUrl" value="/AdminRoomListProc.do">
            <c:param name="acc_code" value="${room.accCode}"/>
        </c:url>

        <a href="${roomListUrl}" class="room-btn room-btn-reset">
            객실 목록
        </a>

        <!-- 소속 숙소 상세정보 -->
        <a href="${accInfoUrl}" class="room-btn room-btn-reset">
            숙소 상세정보
        </a>

        <!-- 정상 객실 및 정상 숙소에서만 수정·삭제 허용 -->
        <c:if test="${room.deleted == 0 && room.accDeleted == 0}">

            <c:url var="modUrl" value="/AdminRoomInfoModProc.do">
                <c:param name="room_code" value="${room.roomCode}"/>
            </c:url>

            <a href="${modUrl}" class="room-btn room-btn-primary">
                객실정보 수정
            </a>

            <!-- 7. 객실 소프트 삭제 -->
            <form action="${pageContext.request.contextPath}/AdminRoomInfoDeleteCon.do"
                  method="post"
                  class="room-delete-form"
                  onsubmit="return confirm('해당 객실을 삭제 처리하시겠습니까?');">

                <input type="hidden"
                       name="room_code"
                       value="${room.roomCode}">

                <input type="hidden"
                       name="csrfToken"
                       value="${deleteCsrfToken}">

                <button type="submit" class="room-btn room-btn-danger">
                    객실 삭제
                </button>

            </form>

        </c:if>

    </div>

    <!-- 8. 삭제 상태 안내 -->
    <c:if test="${room.deleted == 1}">
        <p class="room-info-notice">
            ※ 이 객실은 관리자에 의해 삭제 처리되었습니다.
            기존 예약 기록은 유지됩니다.
        </p>
    </c:if>

    <c:if test="${room.accDeleted == 1}">
        <p class="room-info-notice">
            ※ 소속 숙소가 삭제 처리되어 객실 수정 및 삭제가 제한됩니다.
        </p>
    </c:if>

</div>
