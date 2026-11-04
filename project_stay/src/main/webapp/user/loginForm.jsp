<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 하기</title>
</head>
<body>
<!-- email주소가 admin@naver.com으로 로그인 시 관리자  -->
	<div class="container">
		<form action="loginProc.do" method="post" name="lfrm">
			<table border="1">
				<tr>
					<td colspan="2"><h3>로그인</h3></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<input type="email" name="email" placeholder="이메일을 입력하세요">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="text" name="password" placeholder="비밀번호를 입력하세요">
					</td>
				</tr>
				<tr>
					<td>
						<input type="button" value="로그인" onclick="doLogin()">
						<input type="button" value="비밀번호 찾기" onclick="findPassword()">
					</td>
				</tr>
			</table> 		
		</form>
	</div>
	
</body>
</html>