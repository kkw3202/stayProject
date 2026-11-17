<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/style/adminBook.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>

<div class="admin-book-container">

    <h2>예약 정보 수정</h2>

    <%-- 1. 예약 수정 입력 폼 --%>

    <%-- ★ 수정 : 수정 완료 클릭 시 confirm 창 표시 --%>
    <form action="${pageContext.request.contextPath}/AdminBookInfoModProcCon.do"
          method="post"
          class="admin-book-mod-form"
          onsubmit="return confirm('예약정보를 수정하시겠습니까?');">

        <%-- 수정 대상 예약코드 및 CSRF 토큰 --%>
        <input type="hidden" name="book_code" value="${book.bookCode}">
        <input type="hidden" name="csrfToken" value="${csrfToken}">

        <table class="admin-book-info-table">

            <tr>
                <th>예약코드</th>
                <td>${book.bookCode}</td>

                <th>예약번호</th>
                <td>
                    <c:out value="${book.bookNumber}"/>
                </td>
            </tr>

            <tr>
                <th>예약자명</th>
                <td colspan="3">
                    <input type="text"
                           name="book_name"
                           value="<c:out value='${book.bookName}'/>"
                           maxlength="20"
                           required
                           class="admin-book-input">
                </td>
            </tr>

            <tr>
                <th>회원구분</th>
                <td>
                    <c:choose>
                        <c:when test="${book.isMember eq 1}">
                            회원
                        </c:when>
                        <c:otherwise>
                            비회원
                        </c:otherwise>
                    </c:choose>
                </td>

                <th>객실코드</th>
                <td>${book.roomCode}</td>
            </tr>

            <tr>
                <th>입실일</th>
                <td>
                    <%-- 객실 중복 예약 검사 구현 전까지 날짜 변경 제한 --%>
                    <input type="date"
                           value="${book.checkinDate}"
                           class="admin-book-input"
                           disabled>

                    <input type="hidden"
                           name="book_checkin_date"
                           value="${book.checkinDate}">
                </td>

                <th>퇴실일</th>
                <td>
                    <%-- 객실 중복 예약 검사 구현 전까지 날짜 변경 제한 --%>
                    <input type="date"
                           value="${book.checkoutDate}"
                           class="admin-book-input"
                           disabled>

                    <input type="hidden"
                           name="book_checkout_date"
                           value="${book.checkoutDate}">
                </td>
            </tr>

            <tr>
                <th>예약일</th>
                <td>${book.bookDate}</td>

                <th>예약상태</th>
                <td>
                    <c:choose>
                        <c:when test="${book.status eq 1}">
                            예약취소
                        </c:when>
                        <c:when test="${book.status eq 2}">
                            예약만료
                        </c:when>
                        <c:when test="${book.status eq 3}">
                            예약중
                        </c:when>
                        <c:otherwise>
                            상태확인
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>

        </table>

        <%-- 2. 수정 및 취소 버튼 --%>
        <div class="admin-book-buttons">

            <%-- ★ 수정 : 클릭하면 현재 페이지에서 confirm 실행 --%>
            <button type="submit"
                    class="admin-book-btn edit-btn">
                수정 완료
            </button>

            <a href="${pageContext.request.contextPath}/AdminBookInfoProc.do?book_code=${book.bookCode}"
               class="admin-book-btn list-btn">
                수정 취소
            </a>

        </div>

    </form>

</div>
