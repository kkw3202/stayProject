<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.UUID" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/style/adminCustomer.css?v=4" />
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/none_header_bottom.js"
        defer></script>

<%
    if (session.getAttribute("adminCustomerCsrf") == null) {
        session.setAttribute("adminCustomerCsrf",
                UUID.randomUUID().toString());
    }

    String flash = (String) session.getAttribute("adminCustomerFlash");

    if (flash != null) {
        request.setAttribute("adminFlash", flash);
        session.removeAttribute("adminCustomerFlash");
    }
%>

<section class="customer-wrap">

    <h2>회원 상세정보</h2>

    <%-- ★ 수정 : 상세정보 너비 제한 클래스 추가 --%>
    <div class="customer-detail customer-detail-narrow">

        <div>
            <strong>회원번호</strong>
            <span>${member.code}</span>
        </div>

        <div>
            <strong>이름</strong>
            <span><c:out value="${member.name}"/></span>
        </div>

        <div>
            <strong>이메일</strong>
            <span><c:out value="${member.email}"/></span>
        </div>

        <div>
            <strong>전화번호</strong>
            <span>
                ${fn:substring(member.phone, 0, 3)}-${fn:substring(member.phone, 3, 7)}-${fn:substring(member.phone, 7, 11)}
            </span>
        </div>

        <div>
            <strong>생년월일</strong>
            <span><c:out value="${member.birth}"/></span>
        </div>

        <div>
            <strong>성별</strong>
            <span>${member.gender eq 1 ? '남' : '여'}</span>
        </div>

        <div>
            <strong>회원상태</strong>
            <span>${member.deleted eq 0 ? '정상' : '탈퇴'}</span>
        </div>

    </div>

    <div class="customer-bottom">

        <a class="customer-btn secondary"
           href="${pageContext.request.contextPath}/AdminCustomerListProc.do">
            목록
        </a>

        <c:if test="${member.deleted eq 0}">

            <a class="customer-btn"
               href="${pageContext.request.contextPath}/admincustomerInfoModProc.do?user_code=${member.code}">
                수정
            </a>

            <form action="${pageContext.request.contextPath}/admincustomerInfoDeleteCon.do"
                  method="post"
                  onsubmit="return confirm('해당 회원을 탈퇴 처리하시겠습니까?');">

                <input type="hidden"
                       name="csrf"
                       value="${sessionScope.adminCustomerCsrf}"/>

                <input type="hidden"
                       name="user_code"
                       value="${member.code}"/>

                <button type="submit" class="customer-danger">
                    삭제
                </button>

            </form>

        </c:if>

    </div>

</section>

<c:if test="${not empty adminFlash}">
    <span id="adminCustomerFlashMessage" hidden><c:out value="${adminFlash}"/></span>
    <script>
        window.addEventListener("load", function() {
            setTimeout(function() {
                const messageElement =
                    document.getElementById("adminCustomerFlashMessage");

                if (messageElement) {
                    alert(messageElement.textContent);
                }
            }, 100);
        });
    </script>
</c:if>
