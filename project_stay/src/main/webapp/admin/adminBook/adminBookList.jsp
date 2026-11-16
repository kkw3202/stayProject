<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="style/adminBook.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-book-container">

    <h2>예약 관리</h2>

    <%-- 1. 수정 및 삭제 처리 결과 메시지 --%>
    <c:if test="${not empty bookMsg}">
        <script>
            alert("${bookMsg}");
        </script>
    </c:if>

    <%-- 2. 예약 상태별 현황 --%>
    <div class="admin-book-summary">

        <div class="summary-item">
            <span>예약중</span>
            <strong>${reservedCount}</strong>
        </div>

        <div class="summary-item">
            <span>예약만료</span>
            <strong>${expiredCount}</strong>
        </div>

        <div class="summary-item">
            <span>예약취소</span>
            <strong>${canceledCount}</strong>
        </div>

        <%-- [임시 정책] 상태값 4 : 관리자 소프트 삭제 --%>
        <div class="summary-item">
            <span>삭제처리</span>
            <strong>${deletedCount}</strong>
        </div>

    </div>

    <%-- 3. 예약 검색 및 상태 필터 --%>
    <form action="${pageContext.request.contextPath}/AdminBookListProc.do"
          method="get" class="admin-book-search">

        <select name="status">
            <option value="" ${empty param.status ? 'selected' : ''}>전체 상태</option>
            <option value="3" ${param.status eq '3' ? 'selected' : ''}>예약중</option>
            <option value="2" ${param.status eq '2' ? 'selected' : ''}>예약만료</option>
            <option value="1" ${param.status eq '1' ? 'selected' : ''}>예약취소</option>
            <option value="4" ${param.status eq '4' ? 'selected' : ''}>삭제처리</option>
        </select>

        <select name="searchType">
            <option value="name" ${searchType eq 'name' ? 'selected' : ''}>예약자명</option>
            <option value="number" ${searchType eq 'number' ? 'selected' : ''}>예약번호</option>
        </select>

        <input type="text" name="keyword" value="<c:out value='${keyword}'/>"
               placeholder="검색어를 입력하세요">

        <button type="submit">검색</button>
        <a href="${pageContext.request.contextPath}/AdminBookListProc.do"
           class="admin-book-reset">초기화</a>
    </form>
    <!-- 검색 결과 건수 -->
    <p class="admin-book-count" align="right"> 총 ${total }건</p>
    <%-- 4. 예약 목록 --%>
    <div class="admin-book-table-wrap">

        <table class="admin-book-table">
            <thead>
                <tr>
                    <th>예약코드</th>
                    <th>예약번호</th>
                    <th>예약자명</th>
                    <th>회원구분</th>
                    <th>입실일</th>
                    <th>퇴실일</th>
                    <th>예약일</th>
                    <th>예약상태</th>
                    <th>관리</th>
                </tr>
            </thead>

            <tbody>
                <c:choose>
                    <c:when test="${empty books}">
                        <tr>
                            <td colspan="9" class="admin-book-empty">
                                조회된 예약 내역이 없습니다.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="book" items="${books}">
                            <tr>
                                <td>${book.bookCode}</td>
                                <td><c:out value="${book.bookNumber}"/></td>
                                <td><c:out value="${book.bookName}"/></td>

                                <td>
                                    <c:choose>
                                        <c:when test="${book.isMember eq 1}">회원</c:when>
                                        <c:otherwise>비회원</c:otherwise>
                                    </c:choose>
                                </td>

                                <td>${book.checkinDate}</td>
                                <td>${book.checkoutDate}</td>
                                <td>${book.bookDate}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${book.status eq 1}">
                                            <span class="book-status canceled">예약취소</span>
                                        </c:when>
                                        <c:when test="${book.status eq 2}">
                                            <span class="book-status expired">예약만료</span>
                                        </c:when>
                                        <c:when test="${book.status eq 3}">
                                            <span class="book-status reserved">예약중</span>
                                        </c:when>
                                        <c:when test="${book.status eq 4}">
                                            <span class="book-status deleted">삭제처리</span>
                                        </c:when>
                                        <c:otherwise>상태확인</c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <a class="admin-book-detail"
                                       href="${pageContext.request.contextPath}/AdminBookInfoProc.do?book_code=${book.bookCode}">
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

    
    <%-- 5. 페이징 처리 : 10페이지 단위 --%>

    <c:set var="pageBlock" value="10"/>
	<c:set var="startPage" value="${pageNo - ((pageNo - 1) mod pageBlock)}"/>
    <c:set var="endPage" value="${startPage + pageBlock - 1}"/>

    <c:if test="${endPage > pages}">
        <c:set var="endPage" value="${pages}"/>
    </c:if>

    <div class="admin-book-pagination">

        <c:if test="${pages > 0}">

            <%-- 처음 페이지 --%>
            <c:url var="firstUrl" value="/AdminBookListProc.do">
                <c:param name="page" value="1"/>
                <c:param name="searchType" value="${searchType}"/>
                <c:param name="keyword" value="${keyword}"/>
                <c:if test="${not empty status}">
                    <c:param name="status" value="${status}"/>
                </c:if>
            </c:url>

            <a href="${firstUrl}">처음</a>

            <%-- 이전 10페이지 묶음 --%>
            <c:if test="${startPage > 1}">
                <c:url var="prevUrl" value="/AdminBookListProc.do">
                    <c:param name="page" value="${startPage - 1}"/>
                    <c:param name="searchType" value="${searchType}"/>
                    <c:param name="keyword" value="${keyword}"/>
                    <c:if test="${not empty status}">
                        <c:param name="status" value="${status}"/>
                    </c:if>
                </c:url>

                <a href="${prevUrl}">이전</a>
            </c:if>

            <%-- 현재 묶음의 페이지 번호 --%>
            <c:forEach var="p" begin="${startPage}" end="${endPage}">

                <c:url var="pageUrl" value="/AdminBookListProc.do">
                    <c:param name="page" value="${p}"/>
                    <c:param name="searchType" value="${searchType}"/>
                    <c:param name="keyword" value="${keyword}"/>
                    <c:if test="${not empty status}">
                        <c:param name="status" value="${status}"/>
                    </c:if>
                </c:url>

                <a href="${pageUrl}" class="${p eq pageNo ? 'active' : ''}">
                    ${p}
                </a>

            </c:forEach>

            <%-- 다음 10페이지 묶음 --%>
            <c:if test="${endPage < pages}">
                <c:url var="nextUrl" value="/AdminBookListProc.do">
                    <c:param name="page" value="${endPage + 1}"/>
                    <c:param name="searchType" value="${searchType}"/>
                    <c:param name="keyword" value="${keyword}"/>
                    <c:if test="${not empty status}">
                        <c:param name="status" value="${status}"/>
                    </c:if>
                </c:url>

                <a href="${nextUrl}">다음</a>
            </c:if>

            <%-- 마지막 페이지 --%>
            <c:url var="lastUrl" value="/AdminBookListProc.do">
                <c:param name="page" value="${pages}"/>
                <c:param name="searchType" value="${searchType}"/>
                <c:param name="keyword" value="${keyword}"/>
                <c:if test="${not empty status}">
                    <c:param name="status" value="${status}"/>
                </c:if>
            </c:url>

            <a href="${lastUrl}">마지막</a>

        </c:if>

    </div>


</div>
