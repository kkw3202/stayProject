package control.user.mypage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.UserDAO;
import model.User.UserDTO;

// 내 정보 관리하는 폼 (mypage 처음으로 들어올시 / 내 정보 버튼 눌렀을시 실행)
@WebServlet("/mypageInfoProc.do")
public class mypageInfoProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);
		UserDTO udto = new UserDTO();
		UserDAO udao = new UserDAO();
		
		String loginEmail = (String)session.getAttribute("user_email");
		
		if(session != null) {
			udto = udao.getUserInfo(loginEmail);
		}
		
		// 세션 만료 시 로그인 페이지로 이동
		if (loginEmail == null) {
			response.sendRedirect("loginProc.do");
			return;
		}
		
		// 핸드폰번호 010-0000-0000 처리하기
		String phone = udto.getUser_phone();
		if(phone != null && phone.length() == 11) {
			phone = phone.substring(0,3) + "-" + phone.substring(3,7) + "-" + phone.substring(7,11);
		}
		// 세팅하기
		request.setAttribute("udto", udto);
		request.setAttribute("phone_number", phone);
		// 포워딩
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
	
	}

}