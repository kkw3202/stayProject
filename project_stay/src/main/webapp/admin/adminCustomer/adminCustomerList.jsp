
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
	    <h3>회원관리</h3>
	    <form class="customer-search" action="AdminCustomerListProc.do" method="get">
	        <select name="searchType">
	            <option value="email" ${searchType eq 'email' ? 'selected' : ''}>이메일</option>
	            <option value="name" ${searchType eq 'name' ? 'selected' : ''}>이름</option>
	            <option value="phone" ${searchType eq 'phone' ? 'selected' : ''}>전화번호</option>
	        </select>
	        <input name="keyWord" maxlength="100" placeholder="검색" value="${fn:escapeXml(keyWord)}" />
	        <button type="submit">검색</button>
	        <a class="customer-btn secondary" href="AdminCustomerListProc.do">전체보기</a>
	    </form>
	
	    <p class="customer-count">총 ${total}명</p>
	
	    <div class="customer-scroll">
	        <table class="customer-table">
	            <thead>
	                <tr>
	                    <th>회원번호</th>
	                    <th>이름</th>
	                    <th>이메일</th>
	                    <th>전화번호</th>
	                    <th>상태</th>
	                    <th>관리</th>
	                </tr>
	            </thead>
	            <tbody>
	                <c:forEach var="m" items="${members}">
	                    <tr>
	                        <td>${m.code}</td>
	                        <td><c:out value="${m.name}"/></td>
	                        <td><c:out value="${m.email}"/></td>
	                        <td><c:out value="${m.phone}"/></td>
	
	                        <td>
	                            <span class="customer-status ${m.deleted eq 0 ? 'active' : 'inactive'}">
	                                ${m.deleted eq 0 ? '정상' : '탈퇴'}
	                            </span>
	                        </td>
	
	                        <td class="customer-actions">
	                            <a href="admincustomerInfoProc.do?user_code=${m.code}">
	                                조회
	                            </a>
	
	                            <c:if test="${m.deleted eq 0}">
	                                <a href="admincustomerInfoModProc.do?user_code=${m.code}">
	                                    수정
	                                </a>
	
	                                <form action="admincustomerInfoDeleteCon.do"
	                                      method="post"
	                                      onsubmit="return confirm('해당 회원을 탈퇴 처리하시겠습니까?');">
	
	                                    <input type="hidden" name="csrf"
	                                           value="${sessionScope.adminCustomerCsrf}"/>
	                                    <input type="hidden" name="user_code"
	                                           value="${m.code}"/>
	
	                                    <button type="submit"
	                                            class="customer-danger">
	                                        삭제
	                                    </button>
	                                </form>
	                            </c:if>
	                        </td>
	                    </tr>
	                </c:forEach>
	
	                <c:if test="${empty members}">
	                    <tr>
	                        <td colspan="6">검색 결과가 없습니다.</td>
	                    </tr>
	                </c:if>
	            </tbody>
	        </table>
	    </div>
	    <%-- 페이징 처리 : 10페이지 단위 --%>
	    <c:set var="pageBlock" value="10"/>
	    <c:set var="startPage" value="${pageNo - ((pageNo - 1) mod pageBlock)}"/>
	    <c:set var="endPage" value="${startPage + pageBlock - 1}"/>
	
	    <c:if test="${endPage > pages}">
	        <c:set var="endPage" value="${pages}"/>
	    </c:if>
	
	    <nav class="customer-pages">
	
	        <c:if test="${pages > 0}">
	
	            <%-- 처음 페이지 --%>
	            <c:url var="firstUrl" value="AdminCustomerListProc.do">
	                <c:param name="page" value="1"/>
	                <c:param name="searchType" value="${searchType}"/>
	                <c:param name="keyWord" value="${keyWord}"/>
	            </c:url>
	
	            <a href="${firstUrl}">처음</a>
	
	            <%-- 이전 10페이지 묶음 --%>
	            <c:if test="${startPage > 1}">
	                <c:url var="prevUrl" value="AdminCustomerListProc.do">
	                    <c:param name="page" value="${startPage - 1}"/>
	                    <c:param name="searchType" value="${searchType}"/>
	                    <c:param name="keyWord" value="${keyWord}"/>
	                </c:url>
	
	                <a href="${prevUrl}">이전</a>
	            </c:if>
	
	            <%-- 현재 묶음의 페이지 번호 --%>
	            <c:forEach var="p" begin="${startPage}" end="${endPage}">
	
	                <c:url var="pageUrl" value="AdminCustomerListProc.do">
	                    <c:param name="page" value="${p}"/>
	                    <c:param name="searchType" value="${searchType}"/>
	                    <c:param name="keyWord" value="${keyWord}"/>
	                </c:url>
	
	                <a class="${p eq pageNo ? 'selected' : ''}"
	                   href="${pageUrl}">${p}</a>
	
	            </c:forEach>
	
	            <%-- 다음 10페이지 묶음 --%>
	            <c:if test="${endPage < pages}">
	                <c:url var="nextUrl" value="AdminCustomerListProc.do">
	                    <c:param name="page" value="${endPage + 1}"/>
	                    <c:param name="searchType" value="${searchType}"/>
	                    <c:param name="keyWord" value="${keyWord}"/>
	                </c:url>
	
	                <a href="${nextUrl}">다음</a>
	            </c:if>
	
	            <%-- 마지막 페이지 --%>
	            <c:url var="lastUrl" value="AdminCustomerListProc.do">
	                <c:param name="page" value="${pages}"/>
	                <c:param name="searchType" value="${searchType}"/>
	                <c:param name="keyWord" value="${keyWord}"/>
	            </c:url>
	
	            <a href="${lastUrl}">마지막</a>
	
	        </c:if>
	
	    </nav>

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
