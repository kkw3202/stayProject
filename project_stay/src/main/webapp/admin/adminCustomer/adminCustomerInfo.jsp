
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.UUID" %>
<link rel="stylesheet" href="style/adminCustomer.css" />
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
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
	
	    <div class="customer-detail">
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
	            <span><c:out value="${member.phone}"/></span>
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
	           href="customerListProc.do">목록</a>
	
	        <c:if test="${member.deleted eq 0}">
	            <a class="customer-btn"
	               href="admincustomerInfoModProc.do?user_code=${member.code}">
	                수정
	            </a>
	
	            <form action="admincustomerInfoDeleteCon.do"
	                  method="post"
	                  onsubmit="return confirm('해당 회원을 탈퇴 처리하시겠습니까?');">
	
	                <input type="hidden" name="csrf"
	                       value="${sessionScope.adminCustomerCsrf}"/>
	                <input type="hidden" name="user_code"
	                       value="${member.code}"/>
	
	                <button type="submit" class="customer-danger">
	                    삭제
	                </button>
	            </form>
	        </c:if>
	    </div>
	</section>
	
	<c:if test="${not empty adminFlash}">
	    <script>
	        window.addEventListener("load", function() {
	            setTimeout(function() {
	                alert("${adminFlash}");
	            }, 100);
	        });
	    </script>
	</c:if>
