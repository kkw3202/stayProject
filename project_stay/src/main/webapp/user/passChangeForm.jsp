<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <script type="text/javascript" src="js/none_header_bottom.js" defer></script> -->
<div class="pass-change-wrap">
    <h2>비밀번호 찾기</h2>

    <c:choose>
        <%-- 1단계: 회원정보 확인 --%>
        <c:when test="${not passVerified}">
            <form action="passChangeCon.do" method="post">
                <input type="hidden" name="mode" value="check">
                <table>
                    <tr>
                        <th>이메일</th>
                        <td>
                            <input type="email" name="email" maxlength="30" required>
                        </td>
                    </tr>
                    <tr>
                        <th>전화번호</th>
                        <td>
                            <input type="text" name="phone" maxlength="11" placeholder="숫자만 입력" required>
                        </td>
                    </tr>
                    <tr>
                        <th>비밀번호 패스키</th>
                        <td>
                            <input type="text" name="passkey" maxlength="20" required>
                        </td>
                    </tr>
                </table>
                <div class="btn-wrap">
                    <button type="submit">본인 확인</button>
                    <button type="button" onclick="cancelPassChange()">취소</button>
                </div>
            </form>
        </c:when>

        <%-- 2단계: 새로운 비밀번호 입력 --%>
        <c:otherwise>
            <h3>새 비밀번호 설정</h3>
            <form action="passChangeCon.do" method="post"
                  onsubmit="return checkPassword()">
                <input type="hidden" name="mode" value="change">
                <table>
                    <tr>
                        <th>새 비밀번호</th>
                        <td>
                            <input type="password" name="newPassword" id="newPassword" maxlength="20" required>
                        </td>
                    </tr>
                    <tr>
                        <th>비밀번호 확인</th>
                        <td>
                            <input type="password" name="confirmPassword" id="confirmPassword" maxlength="20" required>
                        </td>
                    </tr>
                </table>
                <div class="btn-wrap">
                    <button type="submit">비밀번호 변경</button>
                    <button type="button" onclick="cancelPassChange()">취소</button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<script>
// 새로운 비밀번호 입력 확인
	function checkPassword() {
        const pw = document.getElementById("newPassword").value;
        const pwChk = document.getElementById("confirmPassword").value;
        if (pw !== pwChk) {
            alert("비밀번호가 일치하지 않습니다.");
            return false;
        }
        return true;
    }
    
 // 비밀번호 변경 취소 확인
    function cancelPassChange() {
        if (confirm("정말 비밀번호 변경을 취소하시겠습니까?")) {
            location.href = "index.jsp";
        }
    }
</script>

<%-- 서버 처리 결과 알림 --%>
<c:if test="${not empty msg}">
    <script>
        window.addEventListener("load", function() {
            setTimeout(function() {
                alert("${msg}");
                <c:if test="${moveMain}">
                    location.href = "index.jsp";
                </c:if>
            }, 100);
        });
    </script>
</c:if>
