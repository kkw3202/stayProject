<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="style/join_style_v2.css">

<div class="join-page">
  <div class="join-layout">
    <aside class="join-visual">
      <div class="join-brand">⌂ 머물:多<small>전국 주요 명소 숙소 예약 플랫폼</small></div>
      <p class="join-message">지금,<br>머물:多와 함께<br>특별한 여행을<br>시작하세요.</p>
    </aside>
    <div class="join-form-area">
      <h3>회원가입</h3>
      <p class="join-subtitle">간단한 정보로 머물:多의 다양한 서비스를 이용하세요.</p>
      <form action="joinProc.do" method="post" name="jfrm">
        <table class="join-table">
          <tr><th>이름 *</th><td><input type="text" name="name" placeholder="이름을 입력하세요"></td></tr>
          <tr><th>비밀번호 *</th><td><input type="password" name="password" placeholder="영문과 숫자로 20자 이내"></td></tr>
          <tr><th>보안질문 *</th><td><div class="join-security"><select name="security_question"><option value="">질문을 선택하세요</option><option value="1">가장 기억에 남는 여행지는?</option><option value="2">다시 태어나면 되고 싶은 것은?</option><option value="3">가장 좋아하는 음식은?</option><option value="4">태어난 도시는?</option></select><input type="text" name="passkey" placeholder="보안질문의 답변을 입력하세요"></div></td></tr>
          <tr><th>전화번호 *</th><td><input type="tel" name="phone" placeholder="숫자만 입력하세요"></td></tr>
          <tr><th>이메일 *</th><td><input type="email" name="email" placeholder="이메일을 입력하세요"></td></tr>
          <tr><th>생년월일 *</th><td><input type="text" name="birth" placeholder="예) 2026-01-01"></td></tr>
          <tr><th>성별 *</th><td><div class="join-gender"><label><input type="radio" name="gender" value="1">남성</label><label><input type="radio" name="gender" value="2">여성</label><label><input type="radio" name="gender" value="3">기타</label></div></td></tr>
          <tr><td colspan="2" class="join-actions"><input class="join-submit" type="button" value="회원가입" onclick="checkForm()"><input class="join-cancel" type="button" value="취소" onclick="checkCancel()"></td></tr>
        </table>
      </form>
    </div>
  </div>
</div>
<script>
function checkForm() {
  let f = document.jfrm;
  if (f.name.value.trim() == "") { alert("이름을 입력하세요!"); f.name.focus(); return; }
  if (f.password.value == "") { alert("비밀번호를 입력하세요!"); f.password.focus(); return; }
  if (f.security_question.value == "") { alert("보안질문을 선택하세요!"); f.security_question.focus(); return; }
  if (f.passkey.value.trim() == "") { alert("보안질문 답변을 입력하세요!"); f.passkey.focus(); return; }
  if (f.phone.value.trim() == "") { alert("전화번호를 입력하세요!"); f.phone.focus(); return; }
  if (f.email.value.trim() == "") { alert("이메일을 입력하세요!"); f.email.focus(); return; }
  if (f.birth.value.trim() == "") { alert("생년월일을 입력하세요!"); f.birth.focus(); return; }
  if (!f.querySelector('input[name="gender"]:checked')) { alert("성별을 선택하세요!"); return; }
  f.submit();
}
function checkCancel() {
  if (confirm("회원가입을 취소하시겠습니까?")) { location.href="index.jsp"; }
}
</script>
