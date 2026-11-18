<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>

<%-- ★ 수정 : CSS 경로에 contextPath 적용 --%>
<link rel="stylesheet" href="style/passChangeForm.css">

<%-- ★ 수정 : 로그인 페이지와 동일한 카드형 레이아웃 --%>
<section class="pass-change-page">

    <div class="pass-change-card">

        <h2>비밀번호 찾기</h2>

        <c:choose>

            <%-- =====================================
                 1단계 : 회원정보 확인
            ===================================== --%>
            <c:when test="${not passVerified}">

                <p class="pass-description">
                    가입 시 등록한 이메일, 전화번호 및
                    비밀번호 패스키를 입력해 주세요.
                </p>

                <form action="${pageContext.request.contextPath}/passChangeCon.do"
                      method="post">

                    <input type="hidden"
                           name="mode"
                           value="check">

                    <%-- 이메일 --%>
                    <div class="pass-field">

                        <label for="pass-email">
                            이메일
                        </label>

                        <input type="email"
                               id="pass-email"
                               name="email"
                               maxlength="30"
                               placeholder="이메일을 입력하세요"
                               autocomplete="email"
                               required>

                    </div>

                    <%-- 전화번호 --%>
                    <div class="pass-field">

                        <label for="pass-phone">
                            전화번호
                        </label>

                        <input type="tel"
                               id="pass-phone"
                               name="phone"
                               maxlength="11"
                               placeholder="숫자만 입력하세요"
                               inputmode="numeric"
                               required>

                    </div>

                    <%-- 비밀번호 패스키 --%>
                    <div class="pass-field">

                        <label for="pass-key">
                            비밀번호 패스키
                        </label>

                        <input type="text"
                               id="pass-key"
                               name="passkey"
                               maxlength="20"
                               placeholder="비밀번호 패스키를 입력하세요"
                               required>

                    </div>

                    <%-- 버튼 --%>
                    <div class="pass-buttons">

                        <button type="submit"
                                class="pass-submit">
                            본인 확인
                        </button>

                        <button type="button"
                                class="pass-cancel"
                                onclick="cancelPassChange()">
                            취소
                        </button>

                    </div>

                </form>

            </c:when>

            <%-- =====================================
                 2단계 : 새로운 비밀번호 설정
            ===================================== --%>
            <c:otherwise>

                <p class="pass-description">
                    본인 확인이 완료되었습니다.
                    새로운 비밀번호를 입력해 주세요.
                </p>

                <form action="${pageContext.request.contextPath}/passChangeCon.do"
                      method="post"
                      onsubmit="return checkPassword()">

                    <input type="hidden"
                           name="mode"
                           value="change">

                    <%-- 새 비밀번호 --%>
                    <div class="pass-field">

                        <label for="newPassword">
                            새 비밀번호
                        </label>

                        <input type="password"
                               id="newPassword"
                               name="newPassword"
                               maxlength="20"
                               placeholder="새 비밀번호를 입력하세요"
                               autocomplete="new-password"
                               required>

                    </div>

                    <%-- 비밀번호 확인 --%>
                    <div class="pass-field">

                        <label for="confirmPassword">
                            비밀번호 확인
                        </label>

                        <input type="password"
                               id="confirmPassword"
                               name="confirmPassword"
                               maxlength="20"
                               placeholder="새 비밀번호를 다시 입력하세요"
                               autocomplete="new-password"
                               required>

                    </div>

                    <%-- 버튼 --%>
                    <div class="pass-buttons">

                        <button type="submit"
                                class="pass-submit">
                            비밀번호 변경
                        </button>

                        <button type="button"
                                class="pass-cancel"
                                onclick="cancelPassChange()">
                            취소
                        </button>

                    </div>

                </form>

            </c:otherwise>

        </c:choose>

    </div>

</section>

<script type="text/javascript">

    // 기존 기능 유지 : 새 비밀번호 일치 여부 확인
    function checkPassword() {

        const pw =
            document.getElementById("newPassword").value;

        const pwChk =
            document.getElementById("confirmPassword").value;

        if (pw !== pwChk) {

            alert("비밀번호가 일치하지 않습니다.");

            return false;
        }

        return true;
    }

    // 기존 기능 유지 : 비밀번호 변경 취소 확인
    function cancelPassChange() {

        if (confirm("정말 비밀번호 변경을 취소하시겠습니까?")) {

            location.href =
                "${pageContext.request.contextPath}/index.jsp";
        }
    }

</script>

<%-- ★ 수정 : 서버 메시지를 HTML에 안전하게 출력 --%>
<c:if test="${not empty msg}">

    <span id="passChangeMessage" hidden><c:out value="${msg}"/></span>

    <script type="text/javascript">

        window.addEventListener("load", function() {

            setTimeout(function() {

                const messageElement =
                    document.getElementById("passChangeMessage");

                if (messageElement) {
                    alert(messageElement.textContent);
                }

                <c:if test="${moveMain}">
                    location.href =
                        "${pageContext.request.contextPath}/index.jsp";
                </c:if>

            }, 100);

        });

    </script>

</c:if>
