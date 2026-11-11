<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*"%>
<%@ page import = "DBPKG.Util" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style/inquiryList.css"/>
<section>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="style/inquiryList.css"/>
<%-- 1:1게시판     --%>
<div class="boardwrap">
	<h3>1:1게시판</h3>
	<%-- 리뷰게시판 잠깐 사용할 부분(월요일에 삭제예정) --%>
	<input type="button" onclick="location.href='reviewListProc.do'" value="리뷰리스트(임시버튼)"/>
	<%-- 검색기능  --%>
	<form action="inquiryListProc.do?searchType=${searchType }, keyWord=${keyWord}" method="get" class="searchbox" name="listForm">
		<%-- 검색키워드 --%>
		<select class="inq_search_type" name = "searchType">
			<option value = "inq_title" ${searchType=='inq_title'?'selected':'' }>제목</option>
			<option value = "user_name" ${searchType=='user_name'?'selected':'' }>작성자</option>
			<option value = "inq_created_datetime" ${searchType=='inq_created_datetime'?'selected':'' }>작성시기</option>
		</select>
		<%-- 검색창 --%>
		<input type="text" placeholder="검색" name="keyWord" value="${keyWord}"/>
		<%-- 아래는 버튼들 --%>
		<%-- 검색버튼 --%>
		<input type = "submit" value="검색" />  
		<%-- 전체보기 버튼은, 회원/비회원 모두 가능 --%>
		<input type = "button" value="전체보기" onclick="location.href='inquiryListProc.do'" class="btn_search"/>
		<%-- 관리자가 아니면서 일반회원인 경우에만 글쓰기/내가 쓴 글보기 버튼 보임 --%>
		<c:if test = "${sessionScope.user_email ne 'admin@naver.com' && not empty sessionScope.user_email}">
			<input type="button" onclick="location.href='index.jsp?section=board/inquiry/inquiryWriteForm.jsp'" value = "글쓰기"/>
			<%--  내가 쓴 글보기 --%>
            <%-- <input type="button" onclick="location.href='inquiryUpdateProc.do?inq_code=${idto.inq_code}'" value="수정하기"/> --%>
		    <input type="button" class="btn_all" onclick="location.href='inquiryListProc.do?searchType=${'user_name'}'" value = "내가 쓴 글보기"/>

		</c:if>
	</form>
	
	<%-- 게시판 테이블  --%>
	<div class="table-scroll">
	<table class="inquiry-table">
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
					<%-- [로그인을 안한 경우] / [작성자와 로그인한 사람이 다른 경우&&관리자가 아닌 경우] => "작성자만 확인가능" 메시지 --%>
					<%-- 작성자와 로그인한자가 일치한 경우 => inquiryInfoProc.do 로 이동 및 정보넘기기 --%>
					<c:choose>
						<%-- 1. 로그인 안 한 경우 => 경고문 + 로그인 페이지로 이동 --%>
						<c:when test="${empty sessionScope.user_email}">
							<a href="javascript:alert('로그인이 필요합니다.'); location.href='index.jsp?section=user/loginForm.jsp'">${ilist.inq_title}</a>
						</c:when>
				
						<%-- 2. 관리자이거나 글 작성자 본인인 경우 --%>
					    <c:when test="${sessionScope.user_email eq 'admin@naver.com' or sessionScope.user_email eq ilist.user_email}">
					        <a href="inquiryInfoProc.do?inq_code=${ilist.inq_code}">${ilist.inq_title}</a>
					    </c:when>
						
						<%-- 5. 로그인했지만 본인 글이 아닌 경우 --%>
						<c:otherwise>
							<a href="javascript:alert('작성자만 확인이 가능합니다.');">${ilist.inq_title}</a>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:if test="${ilist.inq_re_step == 1}">
						<span class="admin_name">관리자</span>
					</c:if>
					<c:if test="${ilist.inq_re_step == 0}">
						${fn:substring(ilist.user_name, 0, 1)}**
					</c:if>
				</td>
				<td>
					${fn:substring(ilist.inq_created_datetime, 0, 10)}
				</td>
				<%-- 답변상태 inq_chk_status --%>
				<c:choose>	
					<c:when test="${ilist.inq_chk_status == 0}">
						<td>
           					 <span class="status wait">답변대기중</span>
        				</td>
					</c:when>
					<c:otherwise>
						<td>
           					 <span class="status done">답변완료</span>
        				</td>
					</c:otherwise>
				</c:choose>
			</tr>
			<c:set var="number" value="${number-1}"/>
		</c:forEach>
	</table>
	</div>
	
	<%-- 페이징 부분 --%>
	<div class="paging">
		<c:if test = "${startPage > pageBlock }">
			<input type="button" onclick="pageBtn1(${startPage},${pageBlock})" value="이전"/>
		</c:if>
		
		<%-- 페이징 번호 --%>
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<input type="button" onclick="pageBtn2(${i})" value="${i }"/>
		</c:forEach>
		
		<%-- 페이징 번호 --%>
		<c:if test = "${endPage < pageCount}">
			<input type="button" onclick="pageBtn3(${startPage},${pageBlock})" value="[다음]"/>
		</c:if>
	</div>	

</div>
</section>

<script>
	function pageBtn1(startPage,pageBlock){
		let f = document.listForm;
		let tmp = f.searchType.value;
		let num = f.keyWord.value;
		location.href="inquiryListProc.do?pageNum="+ (startPage-pageBlock)+ "&searchType="+ tmp + "&keyWord="+num;
	}
	function pageBtn2(i){
		let f = document.listForm;
		let tmp = f.searchType.value;
		let num = f.keyWord.value;
		location.href="inquiryListProc.do?pageNum="+ i+ "&searchType="+ tmp + "&keyWord="+num;
		
	}
	function pageBtn3(startPage,pageBlock){
		let f = document.listForm;
		let tmp = f.searchType.value;
		let num = f.keyWord.value;
		location.href="inquiryListProc.do?pageNum="+ (startPage+pageBlock)+ "&searchType="+ tmp + "&keyWord="+num;
	}
	
</script>
    
    
    