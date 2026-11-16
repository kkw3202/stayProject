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

// 내 정보 그리는 곳 (mypage 처음에 진입시 / 내 정보 버튼 누르면 실행)
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
		
		// 휴대전화 010-0000-0000 처리하기
		String phone = udto.getUser_phone();
		if(phone != null && phone.length() == 11) {
			phone = phone.substring(0,3) + "-" + phone.substring(3,7) + "-" + phone.substring(7,11);
		}
		// 포장하기
		request.setAttribute("udto", udto);
		request.setAttribute("phone_number", phone);
		// 보내기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
	
	}

}
