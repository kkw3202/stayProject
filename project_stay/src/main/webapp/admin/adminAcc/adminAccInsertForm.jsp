<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style/adminAcc.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-acc-wrap">

    <h2>신규 숙소 등록</h2>

    <!-- 1. 숙소 등록 안내 -->
    <p class="acc-field-note">
        숙소 정보를 입력한 후 등록 버튼을 눌러 주세요.
        숙소코드는 자동으로 부여됩니다.
    </p>

    <!-- 2. 숙소 등록 폼 -->
    <form action="${pageContext.request.contextPath}/AdminAccInsertCon.do"
          method="post"
          class="acc-insert-form"
          onsubmit="return confirm('신규 숙소를 등록하시겠습니까?');">

        <!-- 3. 등록용 CSRF 토큰 -->
        <input type="hidden" name="csrfToken" value="${csrfToken}">

        <table class="acc-info-table">
            <tbody>

                <!-- 4. 지역코드 -->
                <tr>
                    <th><label for="city_code">지역코드</label></th>
                    <td>
                        <input type="number"
                               id="city_code"
                               name="city_code"
                               min="1"
                               max="127"
                               placeholder="지역코드 입력"
                               required>
                    </td>
                </tr>

                <!-- 5. 숙소유형 -->
                <tr>
                    <th><label for="acc_type">숙소유형</label></th>
                    <td>
                        <select id="acc_type" name="acc_type" required>
                            <option value="">숙소유형 선택</option>
                            <option value="호텔">호텔</option>
                            <option value="모텔">모텔</option>
                            <option value="펜션">펜션</option>
                        </select>
                    </td>
                </tr>

                <!-- 6. 숙소명 -->
                <tr>
                    <th><label for="acc_name">숙소명</label></th>
                    <td>
                        <input type="text"
                               id="acc_name"
                               name="acc_name"
                               maxlength="30"
                               placeholder="숙소명을 입력하세요"
                               required>
                    </td>
                </tr>

                <!-- 7. 주소 -->
                <tr>
                    <th><label for="acc_address">주소</label></th>
                    <td>
                        <input type="text"
                               id="acc_address"
                               name="acc_address"
                               maxlength="35"
                               placeholder="숙소 주소를 입력하세요"
                               required>
                    </td>
                </tr>

                <!-- 8. 편의시설 -->
                <tr>
                    <th><label for="acc_facility">편의시설</label></th>
                    <td>
                        <input type="text"
                               id="acc_facility"
                               name="acc_facility"
                               maxlength="50"
                               placeholder="예: 주차/와이파이/수영장"
                               required>

                        <p class="acc-field-note">
                            편의시설은 / 기호로 구분해 주세요.
                        </p>
                    </td>
                </tr>

                <!-- 9. 입실시간 -->
                <tr>
                    <th><label for="acc_checkinTime">입실시간</label></th>
                    <td>
                        <input type="time"
                               id="acc_checkinTime"
                               name="acc_checkinTime"
                               value="15:00"
                               required>
                    </td>
                </tr>

                <!-- 10. 퇴실시간 -->
                <tr>
                    <th><label for="acc_checkoutTime">퇴실시간</label></th>
                    <td>
                        <input type="time"
                               id="acc_checkoutTime"
                               name="acc_checkoutTime"
                               value="11:00"
                               required>
                    </td>
                </tr>

                <!-- 11. 숙소 대표 이미지 경로 -->
                <tr>
                    <th><label for="acc_img">대표 이미지 경로</label></th>
                    <td>
                        <input type="text"
                               id="acc_img"
                               name="acc_img"
                               maxlength="20"
                               placeholder="image/hotel/1.jpg"
                               required>

                        <p class="acc-field-note">
                            프로젝트 내부 이미지 경로를 입력해 주세요.
                            예: image/hotel/1.jpg
                        </p>
                    </td>
                </tr>

                <!-- 12. 관리상태 -->
                <tr>
                    <th>관리상태</th>
                    <td>
                        정상
                        <span class="acc-field-note">
                            신규 숙소는 정상 상태로 등록됩니다.
                        </span>
                    </td>
                </tr>

            </tbody>
        </table>

        <!-- 13. 등록 및 취소 버튼 -->
        <div class="acc-info-buttons">

            <a href="${pageContext.request.contextPath}/AdminAccListProc.do"
               class="acc-btn acc-btn-reset">
                취소
            </a>

            <button type="submit" class="acc-btn acc-btn-primary">
                숙소 등록
            </button>

        </div>

    </form>

</div>
