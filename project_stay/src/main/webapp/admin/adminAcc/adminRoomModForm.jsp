<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style/adminRoom.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-room-wrap">

    <h2>객실정보 수정</h2>

    <!-- 1. 수정 결과 메시지 -->
    <c:if test="${not empty roomMsg}">
        <div class="room-message">
            <c:out value="${roomMsg}"/>
        </div>
    </c:if>

    <!-- 2. 소속 숙소정보 -->
    <div class="room-parent-info">
        <strong>소속 숙소:</strong>
        <c:out value="${room.accName}"/>
        (숙소코드: ${room.accCode})
    </div>

    <!-- 3. 객실 수정 폼 -->
    <form action="${pageContext.request.contextPath}/AdminRoomInfoModProcCon.do"
          method="post"
          class="room-mod-form"
          onsubmit="return confirm('객실 정보를 수정하시겠습니까?');">

        <!-- 4. 객실코드 및 CSRF 토큰 -->
        <input type="hidden" name="room_code" value="${room.roomCode}">
        <input type="hidden" name="csrfToken" value="${csrfToken}">

        <table class="room-info-table">
            <tbody>

                <!-- 5. 객실코드 -->
                <tr>
                    <th>객실코드</th>
                    <td>
                        ${room.roomCode}
                        <span class="room-field-note">
                            객실코드는 수정할 수 없습니다.
                        </span>
                    </td>
                </tr>

                <!-- 6. 소속 숙소 -->
                <tr>
                    <th>소속 숙소</th>
                    <td>
                        <c:out value="${room.accName}"/>
                        <span class="room-field-note">
                            소속 숙소는 변경할 수 없습니다.
                        </span>
                    </td>
                </tr>

                <!-- 7. 객실명 -->
                <tr>
                    <th><label for="room_name">객실명</label></th>
                    <td>
                        <input type="text"
                               id="room_name"
                               name="room_name"
                               value="<c:out value='${room.roomName}'/>"
                               maxlength="30"
                               required>
                    </td>
                </tr>

                <!-- 8. 객실가격 -->
                <tr>
                    <th><label for="room_price">객실가격</label></th>
                    <td>
                        <input type="number"
                               id="room_price"
                               name="room_price"
                               value="${room.roomPrice}"
                               min="0"
                               max="2147483647"
                               required>
                        <span class="room-field-note">원</span>
                    </td>
                </tr>

                <!-- 9. 객실 수 -->
                <tr>
                    <th><label for="room_count">객실 수</label></th>
                    <td>
                        <input type="number"
                               id="room_count"
                               name="room_count"
                               value="${room.roomCount}"
                               min="0"
                               max="127"
                               required>
                    </td>
                </tr>

                <!-- 10. 수용인원 -->
                <tr>
                    <th><label for="room_capacity">수용인원</label></th>
                    <td>
                        <input type="number"
                               id="room_capacity"
                               name="room_capacity"
                               value="${room.roomCapacity}"
                               min="1"
                               max="127"
                               required>
                        <span class="room-field-note">명</span>
                    </td>
                </tr>

                <!-- 11. 객실정보 -->
                <tr>
                    <th><label for="room_info">객실정보</label></th>
                    <td>
                        <input type="text"
                               id="room_info"
                               name="room_info"
                               value="<c:out value='${room.roomInfo}'/>"
                               maxlength="50">

                        <p class="room-field-note">
                            객실에 대한 간단한 설명을 입력해 주세요.
                        </p>
                    </td>
                </tr>

                <!-- 12. 객실 편의시설 -->
                <tr>
                    <th><label for="room_amenity">객실 편의시설</label></th>
                    <td>
                        <input type="text"
                               id="room_amenity"
                               name="room_amenity"
                               value="<c:out value='${room.roomAmenity}'/>"
                               maxlength="50">

                        <p class="room-field-note">
                            여러 편의시설은 / 기호로 구분해 주세요.
                        </p>
                    </td>
                </tr>

                <!-- 13. 객실 이미지 경로 1 -->
                <tr>
                    <th><label for="room_img1">이미지 경로 1</label></th>
                    <td>
                        <input type="text"
                               id="room_img1"
                               name="room_img1"
                               value="<c:out value='${room.roomImg1}'/>"
                               maxlength="40"
                               required>
                    </td>
                </tr>

                <!-- 14. 객실 이미지 경로 2 -->
                <tr>
                    <th><label for="room_img2">이미지 경로 2</label></th>
                    <td>
                        <input type="text"
                               id="room_img2"
                               name="room_img2"
                               value="<c:out value='${room.roomImg2}'/>"
                               maxlength="40"
                               required>
                    </td>
                </tr>

                <!-- 15. 객실 이미지 경로 3 -->
                <tr>
                    <th><label for="room_img3">이미지 경로 3</label></th>
                    <td>
                        <input type="text"
                               id="room_img3"
                               name="room_img3"
                               value="<c:out value='${room.roomImg3}'/>"
                               maxlength="40"
                               required>
                    </td>
                </tr>

                <!-- 16. 객실 이미지 경로 4 -->
                <tr>
                    <th><label for="room_img4">이미지 경로 4</label></th>
                    <td>
                        <input type="text"
                               id="room_img4"
                               name="room_img4"
                               value="<c:out value='${room.roomImg4}'/>"
                               maxlength="40"
                               required>
                    </td>
                </tr>

                <!-- 17. 관리상태 -->
                <tr>
                    <th>관리상태</th>
                    <td>
                        정상
                        <span class="room-field-note">
                            이 화면에서는 관리상태를 변경할 수 없습니다.
                        </span>
                    </td>
                </tr>

            </tbody>
        </table>

        <!-- 18. 수정 및 취소 버튼 -->
        <div class="room-info-buttons">

            <c:url var="infoUrl" value="/AdminRoomInfoProc.do">
                <c:param name="room_code" value="${room.roomCode}"/>
            </c:url>

            <a href="${infoUrl}" class="room-btn room-btn-reset">
                취소
            </a>

            <button type="submit" class="room-btn room-btn-primary">
                수정 완료
            </button>

        </div>

    </form>

</div>
