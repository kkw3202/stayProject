<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 비회원 로그인 == 예약번호와 전화번호를 입력받고, 해당하는 예약 하나만 조회가능 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/guestLogin.css">

<div class="guest-login-page">
    <div class="login-shell">
        <!-- 왼쪽 비주얼 영역 (로그인 페이지와 동일한 디자인) -->
        <div class="login-visual">
            <div class="brand">
                <strong>⌂ 머물:多</strong>
                <small>전국 주요 10대 명소 숙소 예약 플랫폼</small>
            </div>
            <div class="visual-copy">비회원도<br>편리하게<br>예약을 확인하세요.</div>
        </div>

        <!-- 오른쪽 콘텐츠 영역 (비회원 조회 폼) -->
        <div class="login-content">
            <h2>비회원 예약조회</h2>
            <p class="login-description">예약번호와 전화번호를 입력하시면 예약 내역을 확인하실 수 있습니다.</p>

            <form action="guestInfoProc.do" method="post" class="login-form">
                <div class="field">
                    <label for="book_number">예약번호</label>
                    <span class="field-icon" aria-hidden="true">🎫</span>
                    <input type="text" id="book_number" name="book_number" placeholder="예약번호를 입력하세요"/>
                </div>
                <div class="field">
                    <label for="guest_phone">전화번호</label>
                    <span class="field-icon" aria-hidden="true">📞</span>
                    <input type="tel" id="guest_phone" name="guest_phone" placeholder="전화번호를 입력하세요"/>
                </div>
                <button type="submit" class="primary-button">조회하기</button>
            </form>
            <p class="contact_admin">예약번호를 잊어버리신 경우, 관리자에게 문의 바랍니다. (<span class="contact_admin tel">☎ 1899-0933</span>)</p>
            
        </div>
    </div>
</div>