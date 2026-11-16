<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<link rel="stylesheet" href="style/adminAcc.css">

<div class="admin-acc-wrap">

    <h2>숙소 상세정보</h2>
     <!-- 등록 및 수정 결과 메시지 표시 (추가) -->
    <c:if test="${not empty accMsg}">
        <div class="acc-message">
            <c:out value="${accMsg}"/>
        </div>
    </c:if>
    <!-- 1. 숙소 기본정보 -->
    <div class="acc-info-card">

        <div class="acc-info-title">
            <h3><c:out value="${acc.accName}"/></h3>

            <c:choose>
                <c:when test="${acc.deleted == 0}">
                    <span class="acc-status acc-status-active">정상</span>
                </c:when>
                <c:otherwise>
                    <span class="acc-status acc-status-deleted">삭제처리</span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- 2. 숙소 대표 이미지 -->
        <div class="acc-info-image">
            <c:choose>
                <c:when test="${not empty acc.accImg}">
                    <c:url var="accImageUrl" value="/${acc.accImg}"/>
                    <img src="${accImageUrl}" alt="숙소 대표 이미지">
                </c:when>
                <c:otherwise>
                    <div class="acc-no-image">등록된 이미지가 없습니다.</div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- 3. 숙소 상세정보 테이블 -->
        <table class="acc-info-table">
            <tbody>
                <tr>
                    <th>숙소코드</th>
                    <td>${acc.accCode}</td>

                    <th>지역코드</th>
                    <td>${acc.cityCode}</td>
                </tr>

                <tr>
                    <th>숙소명</th>
                    <td colspan="3">
                        <c:out value="${acc.accName}"/>
                    </td>
                </tr>

                <tr>
                    <th>숙소유형</th>
                    <td><c:out value="${acc.accType}"/></td>

                    <th>관리상태</th>
                    <td>
                        <c:choose>
                            <c:when test="${acc.deleted == 0}">정상</c:when>
                            <c:otherwise>삭제처리</c:otherwise>
                        </c:choose>
                    </td>
                </tr>

                <tr>
                    <th>주소</th>
                    <td colspan="3">
                        <c:out value="${acc.accAddress}"/>
                    </td>
                </tr>

                <tr>
                    <th>편의시설</th>
                    <td colspan="3">
                        <c:out value="${acc.accFacility}"/>
                    </td>
                </tr>

                <tr>
                    <th>입실시간</th>
                    <td>
                        <fmt:formatDate value="${acc.checkinTime}" pattern="HH:mm"/>
                    </td>

                    <th>퇴실시간</th>
                    <td>
                        <fmt:formatDate value="${acc.checkoutTime}" pattern="HH:mm"/>
                    </td>
                </tr>

                <tr>
                    <th>대표 이미지 경로</th>
                    <td colspan="3">
                        <c:out value="${acc.accImg}"/>
                    </td>
                </tr>
            </tbody>
        </table>

    </div>

    <!-- 4. 관리 버튼 -->
    <div class="acc-info-buttons">

        <a href="${pageContext.request.contextPath}/AdminAccListProc.do"
           class="acc-btn acc-btn-reset">
            목록으로
        </a>

        <!-- 해당 숙소의 객실관리 목록 -->
        <c:url var="roomListUrl" value="/AdminRoomListProc.do">
            <c:param name="acc_code" value="${acc.accCode}"/>
        </c:url>

        <a href="${roomListUrl}" class="acc-btn acc-btn-primary">
            객실관리
        </a>

        <!-- 정상 숙소에 대해서만 수정 및 삭제 허용 -->
        <c:if test="${acc.deleted == 0}">

            <c:url var="modUrl" value="/AdminAccInfoModProc.do">
                <c:param name="acc_code" value="${acc.accCode}"/>
            </c:url>

            <a href="${modUrl}" class="acc-btn acc-btn-primary">
                숙소정보 수정
            </a>

            <!-- 소프트 삭제 -->
            <form action="${pageContext.request.contextPath}/AdminAccInfoDeleteCon.do"
                  method="post"
                  class="acc-delete-form"
                  onsubmit="return confirm('해당 숙소를 삭제 처리하시겠습니까?');">

                <input type="hidden" name="acc_code" value="${acc.accCode}">
                <input type="hidden" name="csrfToken" value="${deleteCsrfToken}">

                <button type="submit" class="acc-btn acc-btn-danger">
                    숙소 삭제
                </button>
            </form>

        </c:if>

    </div>

    <!-- 5. 삭제된 숙소 안내 -->
    <c:if test="${acc.deleted == 1}">
        <p class="acc-info-notice">
            ※ 이 숙소는 관리자에 의해 삭제 처리되었습니다.
            연결된 객실 및 기존 예약 기록은 유지됩니다.
        </p>
    </c:if>

</div>
