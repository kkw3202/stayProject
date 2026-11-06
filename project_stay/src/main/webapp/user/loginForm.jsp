<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 하기</title>
<link rel="stylesheet" href="style/pages/user/loginForm.css">
</head>
<body>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- index.jsp에서 include하는 화면이므로 html/head/body 태그를 중복 작성하지 않습니다. --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/login_style.css">
<section>
<div class="login-page">
    <div class="login-shell">
        <div class="login-visual">
            <div class="brand">
                <strong>⌂ 머물:多</strong>
                <small>전국 주요 10대 명소 숙소 예약 플랫폼</small>
            </div>
            <div class="visual-copy">다시,<br>머물:多에서<br>여행을 이어가세요.</div>
        </div>
>>>>>>> cfe108a81794e298bd6e8f59f45f74686d0b9bb0

        <div class="login-content">
            <h2>로그인</h2>
            <p class="login-description">회원 로그인 후, 예약·찜·리뷰 등 다양한 서비스를 이용할 수 있습니다.</p>

            <form action="loginProc.do" method="post" name="lfrm" class="login-form">
                <div class="field">
                    <label for="loginEmail">이메일</label>
                    <span class="field-icon" aria-hidden="true">✉</span>
                    <input type="email" id="loginEmail" name="email" placeholder="이메일을 입력하세요">
                </div>
                <div class="field">
                    <label for="loginPassword">비밀번호</label>
                    <span class="field-icon" aria-hidden="true">▣</span>
                    <input type="password" id="loginPassword" name="password" placeholder="비밀번호를 입력하세요">
                </div>
                <div class="login-options">
                    <label class="remember"><input type="checkbox" name="rememberEmail"> 이메일 기억하기</label>
                    <button type="button" class="link-button" onclick="findPassword()">비밀번호 찾기</button>
                </div>
                <button type="button" class="primary-button" onclick="chkLogin()">로그인</button>
                <div class="or-divider">또는</div>
                <button type="button" class="secondary-button" onclick="location.href='index.jsp?section=user/joinForm.jsp'">회원가입</button>
            </form>
        </div>
    </div>
</div>
</section>
<script>
function chkLogin() {
    var form = document.lfrm;
    if (form.email.value.trim() == '') {
        alert('이메일을 입력하세요');
        form.email.focus();
        return;
    }
    if (form.password.value == '') {
        alert('비밀번호를 입력하세요');
        form.password.focus();
        return;
    }
    form.submit();
}
function findPassword() {
    location.href = 'findPasswordProc.do';
}
</script>
