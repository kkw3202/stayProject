<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*"%>
<%@ page import = "DBPKG.Util" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/pages/board/inquiry/inquiryList.css">

<%-- 1:1게시판 --%>
<section>
<div class="boardwrap">
	<h3>1:1게시판</h3>
	<%-- 검색기능  --%>
	<form action="inquiryListProc.do" method="get" class="searchbox">
		<%-- 검색키워드 --%>
		<select class="inq_search_type" name = "searchType">
			<option value = "inq_title" ${searchType=='inq_title'?'selected':'' }>제목</option>
			<option value = "user_name" ${searchType=='user_name'?'selected':'' }>작성자</option>
			<option value = "inq_created_datetime" ${searchType=='inq_created_datetime'?'selected':'' }>작성시기</option>
		</select>
		<%-- 검색창 --%>
		<input type="text" placeholder="검색" name="keyWord"/>
		<%-- 아래는 버튼들 --%>
		<%-- 검색버튼 --%>
		<input type = "submit" value="검색"/>  
		<%-- 전체보기 버튼은, 회원/비회원 모두 가능 --%>
		<input type = "button" value="전체보기" onclick="location.href='inquiryListProc.do'" class="btn_search"/>
		<%-- 로그인한 사람만 글쓰기/내가 쓴 글보기 버튼 보임 --%>
		<c:if test="${not empty sessionScope.email }">
			<input type="button" onclick="location.href='index.jsp?section=board/inquiry/inquiryInfo.jsp'" value = "글쓰기"/>
			<%-- 관리자가 아니면서 일반회원인 경우에만 내가 쓴 글보기 --%>
               <c:if test = "${sessionScope.email ne 'admin@naver.com' && not empty sessionScope.email}">
               <%-- <input type="button" onclick="location.href='inquiryUpdateProc.do?inq_code=${idto.inq_code}'" value="수정하기"/> --%>
			   <input type="button" class="btn_all" onclick="location.href='inquiryListProc.do'" value = "내가 쓴 글보기"/>
               </c:if>
		</c:if>
	</form>
	
	<%-- 게시판 테이블  --%>
	<table width="700" border="1">
		<tr>
			<th>순번</th>
			<th>글제목</th>
			<th>작성자</th>
			<th>작성일시</th>
			<th>답변상태</th>
		</tr>
		
		<c:set var="number" value = "${number}"/>
		<c:forEach var="ilist" items="${ilist}">
			<tr>
				<td>${number}</td>
				<td>
					<c:if test = "${ilist.inq_re_step==1 }">
						<c:forEach var="i" begin="1" end = "5">
							&nbsp;
						</c:forEach>
					</c:if>
					<a href="inquiryInfoProc.do?inq_code=${ilist.inq_code}">${ilist.inq_title}</a>
				</td>
				<%-- <td>
					<a href="">${ilist.inq_title}</a>
				</td> --%>
				<%-- 작성자 user_name --%>
				<td>${ilist.user_name}</td>
				<%-- 작성일시 inq_created_datetime --%>
				<td>${ilist.inq_created_datetime}</td>
				<%-- 답변상태 inq_chk_status --%>
				<c:choose>	
					<c:when test="${ilist.inq_chk_status == 0}">
						<td>답변대기중</td>
					</c:when>
					<c:otherwise>
						<td>답변완료</td>
					</c:otherwise>
				</c:choose>
			</tr>
			<c:set var="number" value="${number-1}"/>
		</c:forEach>
	</table>
	
	<%-- 페이징 부분 --%>
	<div class="paging">
		<c:if test = "${startPage > pageBlock }">
			<a href="inquiryListProc.do?pageNum=${startPage-pageBlock}">[이전]</a>
		</c:if>
		
		<%-- 페이징 번호 --%>
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<a href="inquiryListProc.do?pageNum=${i}">[${i}]</a>
		</c:forEach>
		
		<%-- 페이징 번호 --%>
		<c:if test = "endPage < pageCount">
			<a href = "inquiryListProc.do?pageNum=${startPage+pageBlock}">[다음]</a>
		</c:if>
	</div>	

</div>
</section>

<script>
	// 게시판 글쓰기 버튼 관련한 스크립트

</script>

    
    