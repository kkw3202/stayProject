<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="style/adminRoom.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-room-wrap">

    <h2>객실관리</h2>

    <!-- 1. 처리 결과 메시지 -->
    <c:if test="${not empty roomMsg}">
        <div class="room-message">
            <c:out value="${roomMsg}"/>
        </div>
    </c:if>

    <!-- 2. 특정 숙소의 객실 목록인 경우 -->
    <c:if test="${not empty acc}">
        <div class="room-parent-info">
            <strong>관리 숙소:</strong>
            <c:out value="${acc.accName}"/>
            (숙소코드: ${acc.accCode})

            <c:if test="${acc.deleted == 1}">
                <span class="room-status room-status-deleted">
                    숙소 삭제처리
                </span>
            </c:if>
        </div>
    </c:if>

    <!-- 3. 객실 상태별 현황 -->
    <div class="room-summary">
        <span>
            정상 객실
            <strong>${activeCount}</strong>건
        </span>

        <span>
            삭제처리 객실
            <strong>${deletedCount}</strong>건
        </span>
    </div>

    <!-- 4. 검색 및 상태 필터 -->
    <form action="${pageContext.request.contextPath}/AdminRoomListProc.do"
          method="get"
          class="room-search-form">

        <!-- 특정 숙소의 객실만 조회하도록 숙소코드 유지 -->
        <c:if test="${not empty accCode}">
            <input type="hidden" name="acc_code" value="${accCode}">
        </c:if>

        <select name="searchType">
            <option value="roomName"
                ${searchType == 'roomName' ? 'selected' : ''}>
                객실명
            </option>

            <option value="accName"
                ${searchType == 'accName' ? 'selected' : ''}>
                숙소명
            </option>
        </select>

        <input type="text"
               name="keyword"
               value="<c:out value='${keyword}'/>"
               placeholder="검색어를 입력하세요">

        <select name="status">
            <option value="" ${empty status ? 'selected' : ''}>
                정상 객실 전체
            </option>

            <option value="0" ${status == 0 ? 'selected' : ''}>
                정상
            </option>

            <option value="1" ${status == 1 ? 'selected' : ''}>
                삭제처리
            </option>
        </select>

        <button type="submit" class="room-btn room-btn-primary">
            검색
        </button>

        <c:url var="resetUrl" value="/AdminRoomListProc.do">
            <c:if test="${not empty accCode}">
                <c:param name="acc_code" value="${accCode}"/>
            </c:if>
        </c:url>

        <a href="${resetUrl}" class="room-btn room-btn-reset">
            초기화
        </a>
    </form>

    <!-- 5. 목록 상단 -->
    <div class="room-list-header">

        <p>
            검색 결과 총 <strong>${total}</strong>건
        </p>

        <div class="room-list-actions">

            <!-- 특정 숙소에 속하지 않은 전체 객실 목록 -->
            <c:if test="${empty accCode}">
                <a href="${pageContext.request.contextPath}/AdminAccListProc.do"
                   class="room-btn room-btn-reset">
                    숙소 목록
                </a>
            </c:if>

            <!-- 특정 숙소의 객실 목록 -->
            <c:if test="${not empty accCode}">

                <c:url var="accInfoUrl" value="/AdminAccInfoProc.do">
                    <c:param name="acc_code" value="${accCode}"/>
                </c:url>

                <a href="${accInfoUrl}" class="room-btn room-btn-reset">
                    숙소 상세정보
                </a>

                <!-- 정상 숙소에만 신규 객실 등록 허용 -->
                <c:if test="${acc.deleted == 0}">

                    <c:url var="insertUrl" value="/AdminRoomInsertProc.do">
                        <c:param name="acc_code" value="${accCode}"/>
                    </c:url>

                    <a href="${insertUrl}" class="room-btn room-btn-primary">
                        신규 객실 등록
                    </a>

                </c:if>

            </c:if>

        </div>
    </div>

    <!-- 6. 객실 목록 테이블 -->
    <div class="room-table-scroll">
        <table class="room-table">

            <thead>
                <tr>
                    <th>객실코드</th>
                    <th>숙소명</th>
                    <th>객실명</th>
                    <th>가격</th>
                    <th>객실 수</th>
                    <th>수용인원</th>
                    <th>객실상태</th>
                    <th>숙소상태</th>
                    <th>상세보기</th>
                </tr>
            </thead>

            <tbody>
                <c:choose>

                    <c:when test="${empty roomList}">
                        <tr>
                            <td colspan="9" class="room-empty">
                                조회된 객실이 없습니다.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>

                        <c:forEach var="room" items="${roomList}">
                            <tr>

                                <td>${room.roomCode}</td>

                                <td>
                                    <c:out value="${room.accName}"/>
                                </td>

                                <td class="room-name">
                                    <c:out value="${room.roomName}"/>
                                </td>

                                <td>
                                    <fmt:formatNumber
                                        value="${room.roomPrice}"
                                        pattern="#,##0"/>원
                                </td>

                                <td>${room.roomCount}</td>

                                <td>${room.roomCapacity}명</td>

                                <td>
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
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${room.accDeleted == 0}">
                                            정상
                                        </c:when>
                                        <c:otherwise>
                                            삭제처리
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:url var="roomInfoUrl" value="/AdminRoomInfoProc.do">
                                        <c:param name="room_code" value="${room.roomCode}"/>
                                    </c:url>

                                    <a href="${roomInfoUrl}"
                                       class="room-btn room-btn-detail">
                                        상세보기
                                    </a>
                                </td>

                            </tr>
                        </c:forEach>

                    </c:otherwise>

                </c:choose>
            </tbody>

        </table>
    </div>

    <!-- 7. 페이징: 10페이지 단위 -->
    <c:if test="${pages > 0}">

        <c:set var="pageBlock" value="10"/>

        <c:set var="startPage"
               value="${pageNo - ((pageNo - 1) mod pageBlock)}"/>

        <c:set var="endPage"
               value="${startPage + pageBlock - 1}"/>

        <c:if test="${endPage > pages}">
            <c:set var="endPage" value="${pages}"/>
        </c:if>

        <!-- 페이지 이동 URL 생성용 기본 조건 -->
        <c:url var="firstUrl" value="/AdminRoomListProc.do">
            <c:param name="page" value="1"/>
            <c:param name="searchType" value="${searchType}"/>
            <c:param name="keyword" value="${keyword}"/>
            <c:param name="status" value="${status}"/>
            <c:if test="${not empty accCode}">
                <c:param name="acc_code" value="${accCode}"/>
            </c:if>
        </c:url>

        <div class="room-pagination">

            <!-- 처음 -->
            <c:choose>
                <c:when test="${pageNo > 1}">
                    <a href="${firstUrl}">처음</a>
                </c:when>
                <c:otherwise>
                    <span class="disabled">처음</span>
                </c:otherwise>
            </c:choose>

            <!-- 이전 10페이지 묶음 -->
            <c:choose>
                <c:when test="${startPage > 1}">

                    <c:url var="prevUrl" value="/AdminRoomListProc.do">
                        <c:param name="page" value="${startPage - 1}"/>
                        <c:param name="searchType" value="${searchType}"/>
                        <c:param name="keyword" value="${keyword}"/>
                        <c:param name="status" value="${status}"/>
                        <c:if test="${not empty accCode}">
                            <c:param name="acc_code" value="${accCode}"/>
                        </c:if>
                    </c:url>

                    <a href="${prevUrl}">이전</a>

                </c:when>
                <c:otherwise>
                    <span class="disabled">이전</span>
                </c:otherwise>
            </c:choose>

            <!-- 페이지 번호 -->
            <c:forEach var="p" begin="${startPage}" end="${endPage}">

                <c:url var="pageUrl" value="/AdminRoomListProc.do">
                    <c:param name="page" value="${p}"/>
                    <c:param name="searchType" value="${searchType}"/>
                    <c:param name="keyword" value="${keyword}"/>
                    <c:param name="status" value="${status}"/>
                    <c:if test="${not empty accCode}">
                        <c:param name="acc_code" value="${accCode}"/>
                    </c:if>
                </c:url>

                <c:choose>
                    <c:when test="${p == pageNo}">
                        <span class="active">${p}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageUrl}">${p}</a>
                    </c:otherwise>
                </c:choose>

            </c:forEach>

            <!-- 다음 10페이지 묶음 -->
            <c:choose>
                <c:when test="${endPage < pages}">

                    <c:url var="nextUrl" value="/AdminRoomListProc.do">
                        <c:param name="page" value="${endPage + 1}"/>
                        <c:param name="searchType" value="${searchType}"/>
                        <c:param name="keyword" value="${keyword}"/>
                        <c:param name="status" value="${status}"/>
                        <c:if test="${not empty accCode}">
                            <c:param name="acc_code" value="${accCode}"/>
                        </c:if>
                    </c:url>

                    <a href="${nextUrl}">다음</a>

                </c:when>
                <c:otherwise>
                    <span class="disabled">다음</span>
                </c:otherwise>
            </c:choose>

            <!-- 마지막 -->
            <c:choose>
                <c:when test="${pageNo < pages}">

                    <c:url var="lastUrl" value="/AdminRoomListProc.do">
                        <c:param name="page" value="${pages}"/>
                        <c:param name="searchType" value="${searchType}"/>
                        <c:param name="keyword" value="${keyword}"/>
                        <c:param name="status" value="${status}"/>
                        <c:if test="${not empty accCode}">
                            <c:param name="acc_code" value="${accCode}"/>
                        </c:if>
                    </c:url>

                    <a href="${lastUrl}">마지막</a>

                </c:when>
                <c:otherwise>
                    <span class="disabled">마지막</span>
                </c:otherwise>
            </c:choose>

        </div>

    </c:if>

</div>
