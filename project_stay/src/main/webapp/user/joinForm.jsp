<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입하기</title>
<link rel="stylesheet" href="style/pages/user/joinForm.css">
</head>
<body>
	<div class="container">
		<h3>회원가입</h3>
		<form action="joinProc.do" method="post" name="jfrm">
			<table border="1">
				<tr>
					<th>이름</th>
					<td>
						<input type="text" name="name" placeholder="이름을 입력하세요">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" name="password" placeholder="영문과 숫자로 20자 이내의 비밀번호를 입력하세요">
					</td>
				</tr>
				<tr>
					<th>보안질문</th>
					<td>
						<select>
							<option value="">선택</option>
							<option value="1">가장 기억에 남는 여행지는?</option>
							<option value="2">다시 태어나면 되고 싶은 것은?</option>
							<option value="3">가장 좋아하는 음식은?</option>
							<option value="4">태어난 도시는?</option>
						</select>
						<input type="text" name="passkey">
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<input type=tel" name="phone" placeholder="숫자만 입력하세요">
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<input type="email" name="email" placeholder="이메일을 입력하세요">
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						<input type="text" name="birth" placeholder="예)2026-01-01">
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="gender" value="1">남성
						<input type="radio" name="gender" value="2">여성
						<input type="radio" name="gender" value="3">기타
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="회원가입" onclick="checkForm()">
						<input type="button" value="취소"  onclick="checkCancel()">
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 입력오류에 대한 예외처리 -->
	<script type="text/javascript">
		function checkForm(){
			let f = document.jfrm;
			
			if(f.name.value == ""){
				alert("아이디를 입력하세요!");
				f.user_name.focus();
				return;
			}
			if(f.password.value == ""){
				alert("비밀번호를 입력하세요!");
				f.user_password.focus();
				return;
			}
			if(f.passkey.value == ""){
				alert("보안질문을 입력하세요!");
				f.user_passkey.focus();
				return;
			}
			if(f.phone.value == ""){
				alert("전화번호를 입력하세요!");
				f.user_phone.focus();
				return;
			}
			if(f.email.value == ""){
				alert("이메일을 입력하세요!");
				f.user_email.focus();
				return;
			}
			if(f.birth.value == ""){
				alert("생년월일을 입력하세요!");
				f.user_birth.focus();
				return;
			}
			if(f.gender.value == ""){
				alert("성별을 선택하세요!");
				f.user_gender.focus();
				return;
			}
			
			alert("가입을 축하드립니다~!")
			f.submit();
		}
	/* 회원가입 취소 시 */	
		function checkCancel(){
			alert("회원가입을 취소합니다.")
			location.href="index.jsp";
		}
	</script>
	
</body>
</html>