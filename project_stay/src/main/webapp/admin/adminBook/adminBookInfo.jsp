<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style/adminBook.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-book-container">

    <h2>예약 상세정보</h2>

    <%-- 1. 예약 기본정보 --%>
    <div class="admin-book-info">

        <table class="admin-book-info-table">
            <tr>
                <th>예약코드</th>
                <td>${book.bookCode}</td>
                <th>예약번호</th>
                <td><c:out value="${book.bookNumber}"/></td>
            </tr>

            <tr>
                <th>예약자명</th>
                <td><c:out value="${book.bookName}"/></td>
                <th>회원구분</th>
                <td>
                    <c:choose>
                        <c:when test="${book.isMember eq 1}">회원</c:when>
                        <c:otherwise>비회원</c:otherwise>
                    </c:choose>
                </td>
            </tr>

            <tr>
                <th>회원코드</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty book.userCode}">${book.userCode}</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <th>비회원코드</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty book.guestCode}">${book.guestCode}</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
            </tr>

            <tr>
                <th>객실코드</th>
                <td>${book.roomCode}</td>
                <th>예약일</th>
                <td>${book.bookDate}</td>
            </tr>

            <tr>
                <th>입실일</th>
                <td>${book.checkinDate}</td>
                <th>퇴실일</th>
                <td>${book.checkoutDate}</td>
            </tr>

            <tr>
                <th>예약취소일</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty book.cancelDate}">
                            ${book.cancelDate}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>

                <th>예약상태</th>
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
            </tr>
        </table>

    </div>

    <%-- 2. 예약 관리 버튼 --%>
    <div class="admin-book-buttons">

        <a href="${pageContext.request.contextPath}/AdminBookListProc.do"
           class="admin-book-btn list-btn">
            목록으로
        </a>

        <%--
            [임시 정책 - 추후 회의 결과에 따라 변경]

            book_chk_status = 4 : 관리자 소프트 삭제

            삭제된 예약은 상세 조회만 허용한다.
            수정 및 중복 삭제 버튼은 표시하지 않는다.
        --%>
        <c:if test="${book.status ne 4}">

            <%-- 예약 수정 화면 이동 --%>
            <a href="${pageContext.request.contextPath}/AdminBookInfoModProc.do?book_code=${book.bookCode}"
               class="admin-book-btn edit-btn">
                예약 수정
            </a>

            <%-- 예약 소프트 삭제 요청 --%>
            <form action="${pageContext.request.contextPath}/AdminBookInfoDeleteCon.do"
                  method="post"
                  onsubmit="return confirm('해당 예약을 삭제 처리하시겠습니까?');">

                <input type="hidden" name="book_code" value="${book.bookCode}">
                <input type="hidden" name="csrfToken" value="${deleteCsrfToken}">

                <button type="submit" class="admin-book-btn delete-btn">
                    예약 삭제
                </button>
            </form>

        </c:if>

    </div>

</div>
