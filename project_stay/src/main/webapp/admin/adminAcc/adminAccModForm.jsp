<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<link rel="stylesheet" href="style/adminAcc.css">

<div class="admin-acc-wrap">

    <h2>숙소 정보 수정</h2>

    <!-- 1. 숙소 수정 폼 -->
    <form action="${pageContext.request.contextPath}/AdminAccInfoModProcCon.do"
          method="post"
          class="acc-mod-form"
          onsubmit="return confirm('숙소 정보를 수정하시겠습니까?');">

        <!-- 2. 숙소코드 및 CSRF 토큰 -->
        <input type="hidden" name="acc_code" value="${acc.accCode}">
        <input type="hidden" name="csrfToken" value="${csrfToken}">

        <table class="acc-info-table">
            <tbody>

                <!-- 3. 숙소코드 -->
                <tr>
                    <th>숙소코드</th>
                    <td>
                        <c:out value="${acc.accCode}"/>
                        <span class="acc-field-note">자동 부여된 번호로 수정할 수 없습니다.</span>
                    </td>
                </tr>

                <!-- 4. 지역코드 -->
                <tr>
                    <th><label for="city_code">지역코드</label></th>
                    <td>
                        <input type="number"
                               id="city_code"
                               name="city_code"
                               value="${acc.cityCode}"
                               min="1"
                               max="127"
                               required>
                    </td>
                </tr>

                <!-- 5. 숙소유형 -->
                <tr>
                    <th><label for="acc_type">숙소유형</label></th>
                    <td>
                        <select id="acc_type" name="acc_type" required>
                            <option value="호텔" ${acc.accType == '호텔' ? 'selected' : ''}>호텔</option>
                            <option value="모텔" ${acc.accType == '모텔' ? 'selected' : ''}>모텔</option>
                            <option value="펜션" ${acc.accType == '펜션' ? 'selected' : ''}>펜션</option>
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
                               value="<c:out value='${acc.accName}'/>"
                               maxlength="30"
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
                               value="<c:out value='${acc.accAddress}'/>"
                               maxlength="35"
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
                               value="<c:out value='${acc.accFacility}'/>"
                               maxlength="50"
                               required>
                        <p class="acc-field-note">
                            여러 편의시설은 / 기호로 구분해 주세요.
                        </p>
                    </td>
                </tr>

                <!-- 9. 입실시간 -->
                <tr>
                    <th><label for="acc_checkinTime">입실시간</label></th>
                    <td>
                        <fmt:formatDate value="${acc.checkinTime}"
                                        pattern="HH:mm"
                                        var="checkinValue"/>

                        <input type="time"
                               id="acc_checkinTime"
                               name="acc_checkinTime"
                               value="${checkinValue}"
                               required>
                    </td>
                </tr>

                <!-- 10. 퇴실시간 -->
                <tr>
                    <th><label for="acc_checkoutTime">퇴실시간</label></th>
                    <td>
                        <fmt:formatDate value="${acc.checkoutTime}"
                                        pattern="HH:mm"
                                        var="checkoutValue"/>

                        <input type="time"
                               id="acc_checkoutTime"
                               name="acc_checkoutTime"
                               value="${checkoutValue}"
                               required>
                    </td>
                </tr>

                <!-- 11. 숙소 대표 이미지 -->
                <tr>
                    <th><label for="acc_img">대표 이미지 경로</label></th>
                    <td>
                        <input type="text"
                               id="acc_img"
                               name="acc_img"
                               value="<c:out value='${acc.accImg}'/>"
                               maxlength="20"
                               required>

                        <p class="acc-field-note">
                            예: image/hotel/1.jpg
                        </p>
                    </td>
                </tr>

                <!-- 12. 숙소 관리상태 -->
                <tr>
                    <th>관리상태</th>
                    <td>
                        <c:choose>
                            <c:when test="${acc.deleted == 0}">
                                정상
                            </c:when>
                            <c:otherwise>
                                삭제처리
                            </c:otherwise>
                        </c:choose>
                        <span class="acc-field-note">
                            관리상태는 이 화면에서 변경할 수 없습니다.
                        </span>
                    </td>
                </tr>

            </tbody>
        </table>

        <!-- 13. 수정 및 취소 버튼 -->
        <div class="acc-info-buttons">

            <c:url var="infoUrl" value="/AdminAccInfoProc.do">
                <c:param name="acc_code" value="${acc.accCode}"/>
            </c:url>

            <a href="${infoUrl}" class="acc-btn acc-btn-reset">
                취소
            </a>

            <button type="submit" class="acc-btn acc-btn-primary">
                수정 완료
            </button>

        </div>

    </form>

</div>
