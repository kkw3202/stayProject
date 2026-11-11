<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="mypageInfoMod">
	<form action = "mypageModProcCon.do" method="post" name = "modForm">
		<table class="modTable">
			<tr>
				<th class="label">이름</th>
				<td class="data">${udto.user_name }</td>
			</tr>
			<tr>
				<th class="label">전화번호</th>
				<td class="data">${udto.user_phone }</td>
			</tr>
			<tr>
				<th class="label">이메일</th>
				<td class="data">${udto.user_email }</td>
			</tr>
			<tr>
				<th class="label">생년월일</th>
				<td class="data">${udto.user_birth }</td>
			</tr>
			<tr>
				<th class="label">성별</th>
				<td class="data">${udto.user_gender }</td>
			</tr>
			<tr>
				<th class="label"><span class="mod_label">기존 비밀번호</span></th>
				<td class="data">
					<input type="password" name="password" placeholder="기존 비밀번호를 입력해주세요"/>
				</td>
			</tr>
			<tr>
				<th class="label"><span class="mod_label">변경할 비밀번호</span></th>
				<td class="data">
					<input type="password" name="new_password" placeholder="영문과 숫자로 20자 이내로 입력해주세요"/>
				</td>
			</tr>
			<tr>
				<th class="label"><span class="mod_label">변경할 비밀번호<br/>다시입력</span></th>
				<td class="data">
					<input type="password" name="new_password_chk" placeholder="영문과 숫자로 20자 이내로 입력해주세요"/>
				</td>
			</tr>
			<tr>
				<th class="label"><span class="mod_label">보안질문에 대한<br/>답변 변경하기</span></th>
				<td class="data">
					<select name="security_question">
						<option value="">질문을 선택하세요</option>
						<option value="1">가장 기억에 남는 여행지는?</option>
						<option value="2">다시 태어나면 되고 싶은 것은?</option>
						<option value="3">가장 좋아하는 음식은?</option>
						<option value="4">태어난 도시는?</option>
					</select>
					<input type="text" name="passkey" placeholder="${udto.user_passkey }"/>
				</td>
			</tr>
			<tr>
				<td class="btn" colspan="2">
					<input type="button" value="수정하기" onclick="chkForm()"/>
					<input type="button" value="취소하기" onclick="cancelForm()"/>
				</td>
			</tr>
		</table>
	</form>
</div>

<script>
	// 취소하기 함수
	function cancelForm(){
		if (confirm("수정을 취소하시겠습니까?")){
			location.href="mypageInfoProc.do";
		}
	}
	// 수정하기 버튼 : 예외처리 후 전송 W
	function chkForm(){
		let f = document.modForm;
		
		// 
		
		// 기존 비밀번호가 일치하지 않으면 리턴
		if(f.password.value.trim()!="${udto.user_password}"){
			alert("기존 비밀번호와 일치하지 않습니다.");
			f.password.focus();
			return;
		}
		// 새비밀번호와 기존 비밀번호 값이 같으면 리턴
		if(f.new_password.value.trim() == "${udto.user_password}"){
			alert("기존 비밀번호와 같은 번호입니다. 다른 번호로 변경해주세요");
			// 새비밀번호랑 체크 새비밀번호 클리어
			f.new_password.value="";
			f.new_password.value="";
			
			f.new_password.focus();
			return;
		}
		
		// 새비밀번호와 새비밀번호확인 값이 다르면 리턴
		if(f.new_password.value.trim()!=f.new_password_chk.value.trim()){
			alert("비밀번호를 다시 입력해주세요.");
			//새비밀번호랑 체크 새비밀번호 클리어
			f.new_password.value="";
			f.new_password_chk.value="";
			f.new_password.focus();
			return;
		}
		
		if(f.passkey.value.trim()==""){
			alert("보안질문에 대한 답변 작성부탁드립니다");
			f.passkey.focus();
			return;
		}
		if(confirm("수정을 하시겠습니까?")){
			f.submit;
		}
	}
	

</script>