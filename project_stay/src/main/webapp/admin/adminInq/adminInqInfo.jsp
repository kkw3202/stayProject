<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/style/adminInq.css">
<script type="text/javascript" src="js/none_header_bottom.js" defer></script>
<div class="admin-inq-wrap">

    <!-- 1. 페이지 제목 -->
    <h2>1:1 문의 상세정보</h2>

    <!-- 2. 처리 결과 메시지 -->
    <c:if test="${not empty inqMsg}">
        <div class="inq-message">
            <c:out value="${inqMsg}" />
        </div>
    </c:if>

    <!-- 3. 원본 문의 정보 -->
    <div class="inq-info-card">

        <div class="inq-info-title">
            <h3>문의 내용</h3>

            <c:choose>
                <c:when test="${inq.chkStatus == 0}">
                    <span class="inq-status inq-status-waiting">
                        미답변
                    </span>
                </c:when>
                <c:otherwise>
                    <span class="inq-status inq-status-answered">
                        답변완료
                    </span>
                </c:otherwise>
            </c:choose>
        </div>

        <table class="inq-info-table">
            <tbody>
                <tr>
                    <th>문의번호</th>
                    <td><c:out value="${inq.inqCode}" /></td>
                </tr>

                <tr>
                    <th>문의 제목</th>
                    <td><c:out value="${inq.inqTitle}" /></td>
                </tr>

                <tr>
                    <th>작성 회원코드</th>
                    <td><c:out value="${inq.userCode}" /></td>
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
                    <th>참조번호</th>
                    <td><c:out value="${inq.inqRef}" /></td>
                </tr>

                <tr>
                    <th>삭제상태</th>
                    <td>
                        <c:choose>
                            <c:when test="${inq.deleted == 0}">
                                <span class="inq-status inq-status-active">
                                    정상
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="inq-status inq-status-deleted">
                                    삭제처리
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

                <tr>
                    <th>문의 내용</th>
                    <td class="inq-content">
                        <c:out value="${inq.inqContent}" />
                    </td>
                </tr>
            </tbody>
        </table>

    </div>

    <!-- 4. 관리자 답변 정보 -->
    <div class="inq-info-card">

        <div class="inq-info-title">
            <h3>관리자 답변</h3>
        </div>

        <c:choose>

            <c:when test="${not empty reply}">

                <table class="inq-info-table">
                    <tbody>
                        <tr>
                            <th>답변번호</th>
                            <td><c:out value="${reply.inqCode}" /></td>
                        </tr>

                        <tr>
                            <th>답변 제목</th>
                            <td><c:out value="${reply.inqTitle}" /></td>
                        </tr>

                        <tr>
                            <th>답변일시</th>
                            <td>
                                <fmt:formatDate
                                    value="${reply.createdDatetime}"
                                    pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>

                        <tr>
                            <th>답변 내용</th>
                            <td class="inq-content">
                                <c:out value="${reply.inqContent}" />
                            </td>
                        </tr>
                    </tbody>
                </table>

            </c:when>

            <c:otherwise>
                <div class="inq-info-notice">
                    등록된 관리자 답변이 없습니다.
                </div>
            </c:otherwise>

        </c:choose>

    </div>

    <!-- 5. 삭제된 문의 안내 -->
    <c:if test="${inq.deleted == 1}">
        <div class="inq-info-notice">
            삭제 처리된 문의입니다.
            답변 등록 및 추가 삭제는 할 수 없습니다.
        </div>
    </c:if>

    <!-- 6. 하단 버튼 -->
    <div class="inq-info-buttons">

        <!-- 목록으로 -->
        <a href="${pageContext.request.contextPath}/AdminInqListProc.do"
           class="inq-btn inq-btn-reset">
            목록으로
        </a>

        <!-- 답변 작성 -->
        <c:if test="${canReply}">
            <a href="${pageContext.request.contextPath}/AdminInqReplyProc.do?inq_code=${inq.inqCode}"
               class="inq-btn inq-btn-primary">
                답변 작성
            </a>
        </c:if>

        <!-- 문의 삭제 -->
        <c:if test="${inq.deleted == 0}">

            <form class="inq-delete-form"
                  action="${pageContext.request.contextPath}/AdminInqDeleteCon.do"
                  method="post"
                  onsubmit="return confirm('문의와 연결된 답변을 함께 삭제 처리하시겠습니까?');">

                <input type="hidden"
                       name="inq_code"
                       value="${inq.inqCode}">

                <input type="hidden"
                       name="csrfToken"
                       value="${deleteCsrfToken}">

                <button type="submit"
                        class="inq-btn inq-btn-danger">
                    문의 삭제
                </button>

            </form>

        </c:if>

    </div>

</div>
