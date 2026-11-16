<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="style/loginForm.css"/>

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
						<input type="password" name="password" placeholder="비밀번호를 입력하세요">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="로그인" onclick="chkLogin()">
						<input type="button" value="비밀번호 찾기" onclick="findPassword()">
					</td>
				</tr>
			</table> 
		</form>
	</div>
	<script type="text/javascript">
		function chkLogin(){
			var form = document.lfrm;
			if(!form.email.value){
				alert("이메일을 입력하세요");
				form.email.focus();
				return;
			}
			if(!form.password.value){
				alert("비밀번호를 입력하세요");
				form.password.focus();
				return;
			}
			form.submit();
		}
		
		function findPassword(){
			location.href="findPasswordProc.do"
		}
	
	</script>	

