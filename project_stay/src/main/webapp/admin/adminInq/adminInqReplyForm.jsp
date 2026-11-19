<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/style/adminInq.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-inq-wrap">

    <!-- 1. 페이지 제목 -->
    <h2>1:1 문의 답변 작성</h2>

    <!-- 2. 처리 결과 메시지 -->
    <c:if test="${not empty inqMsg}">
        <div class="inq-message">
            <c:out value="${inqMsg}" />
        </div>
    </c:if>

    <!-- 3. 원본 문의 확인 -->
    <div class="inq-info-card">

        <div class="inq-info-title">
            <h3>원본 문의</h3>
            <span class="inq-status inq-status-waiting">
                미답변
            </span>
        </div>

        <table class="inq-info-table">
            <tbody>
                <tr>
                    <th>문의번호</th>
                    <td>
                        <c:out value="${inq.inqCode}" />
                    </td>
                </tr>

                <tr>
                    <th>문의 제목</th>
                    <td>
                        <c:out value="${inq.inqTitle}" />
                    </td>
                </tr>

                <tr>
                    <th>회원코드</th>
                    <td>
                        <c:out value="${inq.userCode}" />
                    </td>
                </tr>

                <tr>
                    <th>작성일시</th>
                    <td>
                        <fmt:formatDate
                            value="${inq.createdDatetime}"
                            pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                </tr>

				<tr>
					<th>문의 내용</th>
					<td class="inq-content"><c:out value="${inq.inqContent}" /></td>
				</tr>
			</tbody>
        </table>

    </div>

    <!-- 4. 관리자 답변 작성 -->
    <div class="inq-info-card">

        <div class="inq-info-title">
            <h3>관리자 답변 입력</h3>
        </div>

        <form class="inq-reply-form"
              action="${pageContext.request.contextPath}/AdminInqReplyCon.do"
              method="post"
              onsubmit="return validateInqReply(this);">

            <!-- 5. 원본 문의번호 -->
            <input type="hidden"
                   name="inq_code"
                   value="${inq.inqCode}">

            <!-- 6. CSRF 토큰 -->
            <input type="hidden"
                   name="csrfToken"
                   value="${csrfToken}">

            <div class="inq-form-field">
                <label for="reply_title">답변 제목</label>

                <input type="text"
                       id="reply_title"
                       name="reply_title"
                       maxlength="50"
                       required
                       value="[답변] <c:out value='${inq.inqTitle}' />"
                       placeholder="답변 제목을 입력하세요">

                <p class="inq-field-note">
                    답변 제목은 최대 50자까지 입력할 수 있습니다.
                </p>
            </div>

            <div class="inq-form-field">
                <label for="reply_content">답변 내용</label>

                <textarea id="reply_content"
                          name="reply_content"
                          rows="12"
                          required
                          placeholder="문의에 대한 답변을 입력하세요"></textarea>

                <p class="inq-field-note">
                    답변 등록 후 문의 상태가 답변완료로 변경됩니다.
                </p>
            </div>

            <!-- 7. 하단 버튼 -->
            <div class="inq-info-buttons">

                <a href="${pageContext.request.contextPath}/AdminInqInfoProc.do?inq_code=${inq.inqCode}"
                   class="inq-btn inq-btn-reset">
                    취소
                </a>

                <button type="submit"
                        class="inq-btn inq-btn-primary">
                    답변 등록
                </button>

            </div>

        </form>

    </div>

</div>

<!-- 8. 답변 입력 검증 -->
<script>
function validateInqReply(form) {

    const title = form.reply_title.value.trim();
    const content = form.reply_content.value.trim();

    if (title.length === 0 || title.length > 50) {
        alert("답변 제목은 1~50자로 입력해 주세요.");
        form.reply_title.focus();
        return false;
    }

    if (content.length === 0) {
        alert("답변 내용을 입력해 주세요.");
        form.reply_content.focus();
        return false;
    }

    return confirm("관리자 답변을 등록하시겠습니까?");
}
</script>
