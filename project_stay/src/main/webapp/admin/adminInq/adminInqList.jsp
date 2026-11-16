<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/style/adminInq.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-inq-wrap">

    <!-- 1. 페이지 제목 -->
    <h2>1:1 문의관리</h2>

    <!-- 2. 처리 결과 메시지 -->
    <c:if test="${not empty inqMsg}">
        <div class="inq-message">
            <c:out value="${inqMsg}" />
        </div>
    </c:if>

    <!-- 3. 문의 현황 -->
    <div class="inq-summary">

        <div class="inq-summary-item">
            <span>정상 문의</span>
            <strong>
                <fmt:formatNumber value="${activeCount}" />
            </strong>
        </div>

        <div class="inq-summary-item">
            <span>미답변</span>
            <strong>
                <fmt:formatNumber value="${waitingCount}" />
            </strong>
        </div>

        <div class="inq-summary-item">
            <span>답변완료</span>
            <strong>
                <fmt:formatNumber value="${answeredCount}" />
            </strong>
        </div>

    </div>

    <!-- 4. 검색 및 필터 -->
    <form class="inq-search-form"
          action="${pageContext.request.contextPath}/AdminInqListProc.do"
          method="get">

        <div class="inq-search-row">

            <!-- 검색 종류 -->
            <select name="searchType">
                <option value="title"
                    ${searchType == 'title' ? 'selected' : ''}>
                    제목
                </option>
                <option value="content"
                    ${searchType == 'content' ? 'selected' : ''}>
                    내용
                </option>
                <option value="userCode"
                    ${searchType == 'userCode' ? 'selected' : ''}>
                    회원코드
                </option>
            </select>

            <!-- 검색어 -->
            <input type="text"
                   name="keyword"
                   value="<c:out value='${keyword}' />"
                   placeholder="검색어를 입력하세요">

            <!-- 답변 상태 -->
            <select name="status">
                <option value=""
                    ${empty status ? 'selected' : ''}>
                    답변상태 전체
                </option>
                <option value="0"
                    ${status == 0 ? 'selected' : ''}>
                    미답변
                </option>
                <option value="1"
                    ${status == 1 ? 'selected' : ''}>
                    답변완료
                </option>
            </select>

            <!-- 삭제 상태 -->
            <select name="deleted">
                <option value="0"
                    ${deletedFilter == '0' ? 'selected' : ''}>
                    정상 문의
                </option>
                <option value="1"
                    ${deletedFilter == '1' ? 'selected' : ''}>
                    삭제처리 문의
                </option>
                <option value="all"
                    ${deletedFilter == 'all' ? 'selected' : ''}>
                    전체
                </option>
            </select>

            <button type="submit" class="inq-btn inq-btn-primary">
                검색
            </button>

            <a href="${pageContext.request.contextPath}/AdminInqListProc.do"
               class="inq-btn inq-btn-reset">
                초기화
            </a>

        </div>
    </form>

    <!-- 5. 검색 결과 -->
    <div class="inq-list-header">
        <strong>
            검색 결과
            <fmt:formatNumber value="${total}" />건
        </strong>
    </div>

    <!-- 6. 문의 목록 -->
    <div class="inq-table-scroll">

        <table class="inq-table">

            <thead>
                <tr>
                    <th>문의번호</th>
                    <th>제목</th>
                    <th>회원코드</th>
                    <th>작성일시</th>
                    <th>답변상태</th>
                    <th>삭제상태</th>
                    <th>상세보기</th>
                </tr>
            </thead>

            <tbody>

                <c:choose>

                    <c:when test="${empty inqList}">
                        <tr>
                            <td colspan="7" class="inq-empty">
                                조회된 문의가 없습니다.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>

                        <c:forEach var="inq" items="${inqList}">

                            <tr>

                                <td>
                                    <c:out value="${inq.inqCode}" />
                                </td>

                                <td class="inq-title">
                                    <a href="${pageContext.request.contextPath}/AdminInqInfoProc.do?inq_code=${inq.inqCode}">
                                        <c:out value="${inq.inqTitle}" />
                                    </a>
                                </td>

                                <td>
                                    <c:out value="${inq.userCode}" />
                                </td>

                                <td>
                                    <fmt:formatDate
                                        value="${inq.createdDatetime}"
                                        pattern="yyyy-MM-dd HH:mm" />
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${inq.chkStatus == 0}">
                                            <span class="inq-status inq-status-waiting">
                                                미답변
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="inq-status inq-status-answered">
                                                답변완료
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${inq.deleted == 0}">
                                            <span class="inq-status inq-status-active">
                                                정상
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="inq-status inq-status-deleted">
                                                삭제처리
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <a class="inq-btn inq-btn-detail"
                                       href="${pageContext.request.contextPath}/AdminInqInfoProc.do?inq_code=${inq.inqCode}">
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

    <!-- 7. 페이징 : 페이지 번호 10개 단위 -->
    <c:if test="${pages > 0}">

        <c:set var="blockSize" value="10" />

        <fmt:parseNumber
            var="blockIndex"
            value="${(pageNo - 1) / blockSize}"
            integerOnly="true" />

        <c:set var="startPage"
               value="${blockIndex * blockSize + 1}" />

        <c:set var="endPage"
               value="${startPage + blockSize - 1}" />

        <c:if test="${endPage > pages}">
            <c:set var="endPage" value="${pages}" />
        </c:if>

        <!-- 검색 조건 유지 -->
        <c:url var="pageUrl" value="/AdminInqListProc.do">
            <c:param name="searchType" value="${searchType}" />
            <c:param name="keyword" value="${keyword}" />
            <c:param name="status" value="${status}" />
            <c:param name="deleted" value="${deletedFilter}" />
        </c:url>

        <div class="inq-pagination">

            <!-- 처음 -->
            <c:if test="${pageNo > 1}">
                <a href="${pageUrl}&amp;page=1">처음</a>
            </c:if>

            <!-- 이전 10페이지 블록 -->
            <c:if test="${startPage > 1}">
                <a href="${pageUrl}&amp;page=${startPage - 1}">
                    이전
                </a>
            </c:if>

            <!-- 페이지 번호 -->
            <c:forEach var="p" begin="${startPage}" end="${endPage}">

                <c:choose>

                    <c:when test="${p == pageNo}">
                        <span class="active">${p}</span>
                    </c:when>

                    <c:otherwise>
                        <a href="${pageUrl}&amp;page=${p}">
                            ${p}
                        </a>
                    </c:otherwise>

                </c:choose>

            </c:forEach>

            <!-- 다음 10페이지 블록 -->
            <c:if test="${endPage < pages}">
                <a href="${pageUrl}&amp;page=${endPage + 1}">
                    다음
                </a>
            </c:if>

            <!-- 마지막 -->
            <c:if test="${pageNo < pages}">
                <a href="${pageUrl}&amp;page=${pages}">
                    마지막
                </a>
            </c:if>

        </div>

    </c:if>

</div>
