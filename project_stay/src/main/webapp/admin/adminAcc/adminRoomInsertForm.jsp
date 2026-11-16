<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style/adminRoom.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-room-wrap">

    <h2>신규 객실 등록</h2>

    <!-- 1. 등록 결과 메시지 -->
    <c:if test="${not empty roomMsg}">
        <div class="room-message">
            <c:out value="${roomMsg}"/>
        </div>
    </c:if>

    <!-- 2. 소속 숙소정보 -->
    <div class="room-parent-info">

        <strong>등록할 숙소:</strong>
        <c:out value="${acc.accName}"/>

        (숙소코드: ${acc.accCode})

        <p class="room-field-note">
            신규 객실은 현재 선택된 숙소에 등록됩니다.
            등록 후 소속 숙소는 변경할 수 없습니다.
        </p>

    </div>

    <!-- 3. 객실 등록 폼 -->
    <form action="${pageContext.request.contextPath}/AdminRoomInsertCon.do"
          method="post"
          class="room-insert-form"
          onsubmit="return confirm('신규 객실을 등록하시겠습니까?');">

        <!-- 4. 숙소코드 및 CSRF 토큰 -->
        <input type="hidden"
               name="acc_code"
               value="${acc.accCode}">

        <input type="hidden"
               name="csrfToken"
               value="${csrfToken}">

        <table class="room-info-table">
            <tbody>

                <!-- 5. 소속 숙소 -->
                <tr>
                    <th>소속 숙소</th>
                    <td>
                        <c:out value="${acc.accName}"/>
                        <span class="room-field-note">
                            숙소코드: ${acc.accCode}
                        </span>
                    </td>
                </tr>

                <!-- 6. 객실명 -->
                <tr>
                    <th>
                        <label for="room_name">객실명 *</label>
                    </th>
                    <td>
                        <input type="text"
                               id="room_name"
                               name="room_name"
                               maxlength="30"
                               placeholder="예) 디럭스 더블"
                               required>
                    </td>
                </tr>

                <!-- 7. 객실가격 -->
                <tr>
                    <th>
                        <label for="room_price">객실가격 *</label>
                    </th>
                    <td>
                        <input type="number"
                               id="room_price"
                               name="room_price"
                               min="0"
                               max="2147483647"
                               step="1"
                               placeholder="예) 120000"
                               required>

                        <span class="room-field-note">원</span>
                    </td>
                </tr>

                <!-- 8. 객실 수 -->
                <tr>
                    <th>
                        <label for="room_count">객실 수 *</label>
                    </th>
                    <td>
                        <input type="number"
                               id="room_count"
                               name="room_count"
                               min="0"
                               max="127"
                               step="1"
                               value="1"
                               required>

                        <p class="room-field-note">
                            동일한 객실 유형의 보유 수량을 입력해 주세요.
                        </p>
                    </td>
                </tr>

                <!-- 9. 수용인원 -->
                <tr>
                    <th>
                        <label for="room_capacity">수용인원 *</label>
                    </th>
                    <td>
                        <input type="number"
                               id="room_capacity"
                               name="room_capacity"
                               min="1"
                               max="127"
                               step="1"
                               value="2"
                               required>

                        <span class="room-field-note">명</span>
                    </td>
                </tr>

                <!-- 10. 객실정보 -->
                <tr>
                    <th>
                        <label for="room_info">객실정보</label>
                    </th>
                    <td>
                        <input type="text"
                               id="room_info"
                               name="room_info"
                               maxlength="50"
                               placeholder="예) 더블침대 1개, 금연 객실">

                        <p class="room-field-note">
                            최대 50자까지 입력할 수 있습니다.
                        </p>
                    </td>
                </tr>

                <!-- 11. 객실 편의시설 -->
                <tr>
                    <th>
                        <label for="room_amenity">객실 편의시설</label>
                    </th>
                    <td>
                        <input type="text"
                               id="room_amenity"
                               name="room_amenity"
                               maxlength="50"
                               placeholder="예) 와이파이/TV/에어컨/냉장고">

                        <p class="room-field-note">
                            여러 편의시설은 / 기호로 구분해 주세요.
                        </p>
                    </td>
                </tr>

                <!-- 12. 객실 이미지 경로 1 -->
                <tr>
                    <th>
                        <label for="room_img1">이미지 경로 1 *</label>
                    </th>
                    <td>
                        <input type="text"
                               id="room_img1"
                               name="room_img1"
                               maxlength="40"
                               placeholder="image/hotel/img1/1.jpg"
                               required>
                    </td>
                </tr>

                <!-- 13. 객실 이미지 경로 2 -->
                <tr>
                    <th>
                        <label for="room_img2">이미지 경로 2 *</label>
                    </th>
                    <td>
                        <input type="text"
                               id="room_img2"
                               name="room_img2"
                               maxlength="40"
                               placeholder="image/hotel/img2/1.jpg"
                               required>
                    </td>
                </tr>

                <!-- 14. 객실 이미지 경로 3 -->
                <tr>
                    <th>
                        <label for="room_img3">이미지 경로 3 *</label>
                    </th>
                    <td>
                        <input type="text"
                               id="room_img3"
                               name="room_img3"
                               maxlength="40"
                               placeholder="image/hotel/img3/1.jpg"
                               required>
                    </td>
                </tr>

                <!-- 15. 객실 이미지 경로 4 -->
                <tr>
                    <th>
                        <label for="room_img4">이미지 경로 4 *</label>
                    </th>
                    <td>
                        <input type="text"
                               id="room_img4"
                               name="room_img4"
                               maxlength="40"
                               placeholder="image/hotel/img4/1.jpg"
                               required>
                    </td>
                </tr>

                <!-- 16. 관리상태 -->
                <tr>
                    <th>관리상태</th>
                    <td>
                        정상

                        <span class="room-field-note">
                            신규 객실은 정상 상태로 등록됩니다.
                        </span>
                    </td>
                </tr>

            </tbody>
        </table>

        <!-- 17. 이미지 경로 안내 -->
        <p class="room-field-note">
            ※ 이미지 파일은 업로드되지 않습니다.
            서버에 존재하는 이미지의 상대경로를 입력해 주세요.
            숙소 유형에 따라 hotel, motel, pension 경로를 사용합니다.
        </p>

        <!-- 18. 등록 및 취소 버튼 -->
        <div class="room-info-buttons">

            <c:url var="roomListUrl" value="/AdminRoomListProc.do">
                <c:param name="acc_code" value="${acc.accCode}"/>
            </c:url>

            <a href="${roomListUrl}"
               class="room-btn room-btn-reset">
                취소
            </a>

            <button type="submit"
                    class="room-btn room-btn-primary">
                객실 등록
            </button>

        </div>

    </form>

</div>
