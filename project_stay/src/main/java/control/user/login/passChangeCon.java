package control.user.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.UserDAO;

@WebServlet("/passChangeCon.do")
public class passChangeCon extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		UserDAO udao = new UserDAO();

		String mode = request.getParameter("mode");

		// 현재 요청의 결과를 로그인 화면과 같은 방식으로 전달
		String msg = "";
		boolean moveMain = false;

		// 이미 5회 실패한 경우
		Integer failCount = (Integer) session.getAttribute("passFailCount");

		if (failCount == null) {
			failCount = 0;
		}

		if (failCount >= 5) {
			msg = "인증 횟수를 초과하였습니다. 관리자에게 문의하세요.";
			moveMain = true;

		// 1단계: 이메일, 전화번호, 패스키 확인
		} else if ("check".equals(mode)) {

			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String passkey = request.getParameter("passkey");
			// 이전 인증정보 초기화
			session.removeAttribute("passChangeUserCode");

			if (email == null || phone == null || passkey == null
					|| email.trim().isEmpty()
					|| phone.trim().isEmpty()
					|| passkey.trim().isEmpty()) {
				msg = "모든 정보를 입력해 주세요.";
			} else {
				int userCode =
						udao.passChangeCheck(email, phone, passkey);
				if (userCode > 0) {
					// 회원정보 일치
					session.setAttribute("passChangeUserCode", userCode);
					session.removeAttribute("passFailCount");

					request.setAttribute("passVerified", true);
					msg = "본인 확인이 완료되었습니다.";
				} else {
					// 회원정보 불일치
					failCount++;
					session.setAttribute("passFailCount", failCount);
					if (failCount >= 5) {
						msg = "5회 불일치했습니다. 관리자에게 문의하세요.";
						moveMain = true;
					} else {
						msg = "정보가 일치하지 않습니다. 남은 횟수: "
								+ (5 - failCount) + "회";
					}
				}
			}

		// 2단계: 새로운 비밀번호 저장
		} else if ("change".equals(mode)) {
			Integer userCode = (Integer)
					session.getAttribute("passChangeUserCode");
			String newPassword = request.getParameter("newPassword");
			String confirmPassword =
					request.getParameter("confirmPassword");
			if (userCode == null) {
				msg = "본인 확인을 먼저 실행해 주세요.";
			} else if (newPassword == null
					|| confirmPassword == null
					|| newPassword.isEmpty()
					|| confirmPassword.isEmpty()) {
				msg = "새 비밀번호를 모두 입력해 주세요.";
				request.setAttribute("passVerified", true);
			} else if (!newPassword.equals(confirmPassword)) {
				msg = "비밀번호가 일치하지 않습니다.";
				request.setAttribute("passVerified", true);
			} else if (newPassword.length() > 20) {
				msg = "비밀번호는 20자 이내로 입력해 주세요.";
				request.setAttribute("passVerified", true);
			} else {
				boolean result =
						udao.passChangeUpdate(userCode, newPassword);
				if (result) {
					session.removeAttribute("passChangeUserCode");
					session.removeAttribute("passFailCount");
					msg = "비밀번호가 변경되었습니다.";
					moveMain = true;
				} else {
					msg = "비밀번호 변경에 실패했습니다.";
					request.setAttribute("passVerified", true);
				}
			}
		} else {
			msg = "잘못된 요청입니다.";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("moveMain", moveMain);

		request.getRequestDispatcher(
			"/index.jsp?section=user/passChangeForm.jsp"
		).forward(request, response);
	}

}