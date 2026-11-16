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
	    <h2>회원정보 수정</h2>
	
	    <form class="customer-edit"
	          action="admincustomerInfoModProcCon.do"
	          method="post">
	
	        <input type="hidden" name="csrf"
	               value="${sessionScope.adminCustomerCsrf}"/>
	
	        <input type="hidden" name="user_code"
	               value="${member.code}"/>
	
	        <label>
	            이메일 (변경 불가)
	            <input value="${fn:escapeXml(member.email)}" readonly />
	        </label>
	
	        <label>
	            이름
	            <input name="user_name"
	                   value="${fn:escapeXml(member.name)}"
	                   maxlength="20" required />
	        </label>
	
	        <label>
	            전화번호
	            <input name="user_phone"
	                   value="${fn:escapeXml(member.phone)}"
	                   maxlength="11"
	                   pattern="[0-9]{8,11}" required />
	        </label>
	
	        <label>
	            생년월일
	            <input type="date" name="user_birth"
	                   value="${member.birth}" required />
	        </label>
	
	        <label>
	            성별
	            <select name="user_gender">
	                <option value="1"
	                    ${member.gender eq 1 ? 'selected' : ''}>남</option>
	                <option value="0"
	                    ${member.gender eq 0 ? 'selected' : ''}>여</option>
	            </select>
	        </label>
	
	        <label>
	            비밀번호 패스키
	            <input name="user_passkey"
	                   value="${fn:escapeXml(member.passkey)}"
	                   maxlength="20" required />
	        </label>
	
	        <div class="customer-bottom">
	            <button type="submit">저장</button>
	
	            <a class="customer-btn secondary"
	               href="admincustomerInfoProc.do?user_code=${member.code}">
	                취소
	            </a>
	        </div>
	    </form>
	</section>
