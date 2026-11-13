<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="style/mypageMod.css"/>
<div class="mypageInfoMod">
	<h3>회원정보 수정</h3>
	<form action = "mypageModProcCon.do" method="post" name = "modForm">
		<table class="modTable">
			<tbody>
				<tr>
					<th class="label">이름</th>
					<td class="data">
						<input type="text" name="user_name" value="${udto.user_name }"/>
					</td>
				</tr>
				<%-- (기존 비밀번호를 변경할때 입력하는 단계 = 1단계) --%>
				<tr>
					<th class="label"><span class="mod_label">기존 비밀번호</span></th>
					<td class="data">
						<div class="currentPass_group">
							<input type="password" name="password" placeholder="비밀번호 변경시에만 입력해주세요"/>
							<input type="button" class="btn" value="비밀번호 변경하기" id="btnPw" onclick="chkCurrentPw()"/>
						</div>
					</td>
				</tr>
			</tbody>
			
			<%-- (2단계 : 숨겨져잇다가(noShow css) 기존 비밀번호 통과한 경우, 나옴 --%>
			<tbody id="step2" class="noShow">
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
			</tbody>
			
			<%-- 나머지 정보들 변경하는 곳  --%>
			<tbody>
				<tr>
					<th class="label">보안질문 답변</th>
					<td class="data">
						<div class="security_group">
							<select name="security_question">
								<option value="none">질문을 선택하세요</option>
								<option value="1">가장 기억에 남는 여행지는?</option>
								<option value="2">다시 태어나면 되고 싶은 것은?</option>
								<option value="3">가장 좋아하는 음식은?</option>
								<option value="4">태어난 도시는?</option>
							</select>
							<input type="text" name="passkey" value="${udto.user_passkey }"/>
						</div>
					</td>
				</tr>
				<tr>
					<th class="label">전화번호</th>
					<td class="data">
						<p class="phone_group">숫자만 입력해주세요. (예:01012345678)</p>
						<input type="text" name="user_phone" value="${udto.user_phone }"/>
					</td>
				</tr>
				<tr>
					<th class="label">이메일</th>
					<td class="email_group">${udto.user_email }</td>
				</tr>
				<tr>
					<th class="label">생년월일</th>
					<td class="data">
						<input type="text" name="user_birth" value="${udto.user_birth }"/>
					</td>
				</tr>
				<tr>
					<th class="label">성별</th>
					<td class="data">
						<div class="gender_group">
							<label>
								<input type="radio" name="user_gender" value="1" ${udto.user_gender == 1 ? 'checked':''}/>남성
							</label>
							<label>
								<input type="radio" name="user_gender" value="2" ${udto.user_gender == 2 ? 'checked':''}/>여성
							</label>
							<label>
								<input type="radio" name="user_gender" value="3" ${udto.user_gender == 3 ? 'checked':''}/>기타
							</label>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="bottom_area">
						<input type="button" class="btn" value="수정하기" onclick="chkForm()"/>
						<input type="button" class="btn" value="취소하기" onclick="cancelForm()"/>
					</td>
				</tr>
			</tbody>
			
		</table>
	</form>
</div>

<script>
	// 비밀번호 바꿀지 확인 변수
	let isChk = false;
	// 1단계 : 현재비밀번호 확인단계
	function chkCurrentPw(){
		let f=document.modForm;
		if(f.password.value.trim()!="${udto.user_password}"){
			alert("기존 비밀번호와 일치하지 않습니다.");
			// 비밀번호 초기화
			f.password.value="";
			f.password.focus();
			return;
		}
		// 확인 메시지
		alert("비밀번호가 확인되었습니다. 변경할 정보를 입력해주세요.");
		f.password.readOnly = true; // 기존 비밀번호 수정불가
		// 비번 변경변수 true로 전환
		isChk=true;
		
		// 버튼 비활성화
		let btnPw = document.getElementById("btnPw");
		btnPw.value="인증완료";
		// noShow클래스 제거하여, 2단계 보이기
		document.getElementById("step2").className = "";
	}
	
	// 2단계 : 수정하기 버튼 : 예외처리 후 전송
	function chkForm(){
		let f = document.modForm;
		// 회원 이름을 입력하지 않았을때, 리턴
		if(f.user_name.value.trim()==""){
			alert("회원이름을 입력하지 않았습니다");
			f.user_name.focus();
			return;
		}
		
		// 회원 연락처를 입력하지 않았을때, 리턴
		if(f.user_phone.value.trim()==""){
			alert("연락처를 입력하지 않았습니다");
			f.user_phone.focus();
			return;
		}
		
		// 회원 연락처가 숫자가 아니거나, 11자리가 아닌 경우, 리턴
		if(f.user_phone.value.length != 11){
			alert("연락처 11자리를 정확히 입력해주세요. (예: 01012345678)");
			f.user_phone.value == "";
			f.user_phone.focus();
			return;
		}
		
		// 생년월일을 입력하지 않았을때, 리턴
		if(f.user_birth.value.trim()==""){
			alert("생년월일을 입력하지 않았습니다");
			f.user_birth.focus();
			return;
		}
		
		// 보안질문을 선택하지 않았을때, 리턴
		if(f.security_question.value=="none"||f.security_question.value==""){
			alert("보안질문을 선택해주세요");
			f.security_question.focus();
			return;
		}
		// 보안질문 답변하지 않았을때, 리턴
		if(f.passkey.value.trim()==""){
			alert("보안질문에 대한 답변 작성부탁드립니다");
			f.passkey.focus();
			return;
		}
		
		if(isChk){
			// 새비밀번호 공백일 경우 리턴
			if(f.new_password.value.trim()==""){
				alert("새 비밀번호를 입력해주세요");
				f.new_password.focus();
				return;
			}
			
			// 새비밀번호와 기존 비밀번호 값이 같으면 리턴
			if(f.new_password.value.trim() == "${udto.user_password}"){
				alert("기존 비밀번호와 같은 번호입니다. 다른 번호로 변경해주세요");
				// 새비밀번호랑 체크 새비밀번호 클리어
				f.new_password.value="";
				f.new_password_chk.value="";
				
				f.new_password.focus();
				return;
			}
			
			// 새비밀번호와 "새비밀번호확인" 값이 다르면 리턴
			if(f.new_password.value.trim()!=f.new_password_chk.value.trim()){
				alert("비밀번호를 다시 입력해주세요.");
				//새비밀번호랑 체크 새비밀번호 클리어
				f.new_password.value="";
				f.new_password_chk.value="";
				f.new_password.focus();
				return;
			}
		}else{ // isChk()가 false인 경우에는, 새 비밀번호에, 기존 비밀번호 넣기
			f.new_password.value = "${udto.user_password}";
		}
		
		if(confirm("수정을 하시겠습니까?")){
			f.submit();
		}
	}
	
	// 취소하기 함수
	function cancelForm(){
		if (confirm("수정을 취소하시겠습니까?")){
			location.href="mypageInfoProc.do";
		}
	}
	

</script>