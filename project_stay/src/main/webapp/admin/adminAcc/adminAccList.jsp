<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/style/adminAcc.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-acc-wrap">

    <h2>숙소관리</h2>

    <!-- 1. 처리 결과 메시지 -->
    <c:if test="${not empty accMsg}">
        <div class="acc-message">
            <c:out value="${accMsg}"/>
        </div>
    </c:if>

    <!-- 2. 숙소 현황 -->
    <div class="acc-summary">
        <span>정상 숙소 <strong>${activeCount}</strong>건</span>
        <span>삭제처리 숙소 <strong>${deletedCount}</strong>건</span>
    </div>

    <!-- 3. 검색 및 상태 필터 -->
    <form action="${pageContext.request.contextPath}/AdminAccListProc.do"
          method="get" class="acc-search-form">

        <select name="searchType">
            <option value="name" ${searchType == 'name' ? 'selected' : ''}>숙소명</option>
            <option value="type" ${searchType == 'type' ? 'selected' : ''}>숙소유형</option>
            <option value="address" ${searchType == 'address' ? 'selected' : ''}>주소</option>
        </select>

        <input type="text" name="keyword"
               value="<c:out value='${keyword}'/>"
               placeholder="검색어를 입력하세요">

        <select name="status">
            <option value="" ${empty param.status ? 'selected' : ''}>정상 숙소 전체</option>
            <option value="0" ${param.status == '0' ? 'selected' : ''}>정상</option>
            <option value="1" ${param.status == '1' ? 'selected' : ''}>삭제처리</option>
        </select>

        <button type="submit" class="acc-btn acc-btn-search">검색</button>

        <a href="${pageContext.request.contextPath}/AdminAccListProc.do"
           class="acc-btn acc-btn-reset">초기화</a>
    </form>

    <!-- 4. 목록 상단 -->
    <div class="acc-list-header">
        <p>검색 결과 : 총 <strong>${total}</strong>건</p>

        <a href="${pageContext.request.contextPath}/AdminAccInsertProc.do"
           class="acc-btn acc-btn-primary">+ 신규 숙소 등록</a>
    </div>

    <!-- 5. 숙소 목록 -->
    <div class="acc-table-scroll">
        <table class="acc-table">
            <thead>
                <tr>
                    <th>숙소코드</th>
                    <th>숙소명</th>
                    <th>유형</th>
                    <th>지역코드</th>
                    <th>주소</th>
                    <th>입실시간</th>
                    <th>퇴실시간</th>
                    <th>상태</th>
                    <th>관리</th>
                </tr>
            </thead>

            <tbody>
                <c:choose>
                    <c:when test="${empty accList}">
                        <tr>
                            <td colspan="9" class="acc-empty">
                                조회된 숙소가 없습니다.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="acc" items="${accList}">
                            <tr>
                                <td>${acc.accCode}</td>

                                <td class="acc-name">
                                    <c:out value="${acc.accName}"/>
                                </td>

                                <td><c:out value="${acc.accType}"/></td>

                                <td>${acc.cityCode}</td>

                                <td class="acc-address">
                                    <c:out value="${acc.accAddress}"/>
                                </td>

                                <td>${acc.checkinTime}</td>
                                <td>${acc.checkoutTime}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${acc.deleted == 0}">
                                            <span class="acc-status acc-status-active">정상</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="acc-status acc-status-deleted">삭제처리</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:url var="infoUrl" value="/AdminAccInfoProc.do">
                                        <c:param name="acc_code" value="${acc.accCode}"/>
                                    </c:url>

                                    <a href="${infoUrl}" class="acc-btn acc-btn-detail">
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

    <!-- 6. 검색 조건을 유지하는 페이지 이동 URL -->
    <c:url var="firstUrl" value="/AdminAccListProc.do">
        <c:param name="page" value="1"/>
        <c:param name="searchType" value="${searchType}"/>
        <c:param name="keyword" value="${keyword}"/>
        <c:if test="${not empty param.status}">
            <c:param name="status" value="${param.status}"/>
        </c:if>
    </c:url>

    <!-- 7. 10페이지 단위 페이징 -->
    <c:if test="${pages > 0}">

        <c:set var="pageBlock" value="10"/>
        <c:set var="startPage" value="${pageNo - ((pageNo - 1) mod pageBlock)}"/>
        <c:set var="endPage" value="${startPage + pageBlock - 1}"/>

        <c:if test="${endPage > pages}">
            <c:set var="endPage" value="${pages}"/>
        </c:if>

        <div class="acc-pagination">

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
                    <c:url var="prevUrl" value="/AdminAccListProc.do">
                        <c:param name="page" value="${startPage - 1}"/>
                        <c:param name="searchType" value="${searchType}"/>
                        <c:param name="keyword" value="${keyword}"/>
                        <c:if test="${not empty param.status}">
                            <c:param name="status" value="${param.status}"/>
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

                <c:url var="pageUrl" value="/AdminAccListProc.do">
                    <c:param name="page" value="${p}"/>
                    <c:param name="searchType" value="${searchType}"/>
                    <c:param name="keyword" value="${keyword}"/>
                    <c:if test="${not empty param.status}">
                        <c:param name="status" value="${param.status}"/>
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
                    <c:url var="nextUrl" value="/AdminAccListProc.do">
                        <c:param name="page" value="${endPage + 1}"/>
                        <c:param name="searchType" value="${searchType}"/>
                        <c:param name="keyword" value="${keyword}"/>
                        <c:if test="${not empty param.status}">
                            <c:param name="status" value="${param.status}"/>
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
                    <c:url var="lastUrl" value="/AdminAccListProc.do">
                        <c:param name="page" value="${pages}"/>
                        <c:param name="searchType" value="${searchType}"/>
                        <c:param name="keyword" value="${keyword}"/>
                        <c:if test="${not empty param.status}">
                            <c:param name="status" value="${param.status}"/>
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
