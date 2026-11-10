package control.user.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.UserDAO;


@WebServlet("/loginProc.do")
public class loginProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		UserDAO udao = new UserDAO();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean loginAdminchk = udao.loginAdminCheck(email, password);
		boolean loginchk = udao.loginCheck(email, password);
		// admin 로그인시
		if("admin@naver.com".equals(email)) {
			if(loginAdminchk) {
				request.setAttribute("msg", "관리자님, 환영합니다!");
				HttpSession session = request.getSession();
				session.setAttribute("user_email", email);
				session.setMaxInactiveInterval(60*240);
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/loginOk.jsp");
				rd.forward(request, response);
			}else {
				request.setAttribute("msg", "로그인 실패.");
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/loginErr.jsp");
				rd.forward(request, response);
			}
			return;
		}
		if(loginchk) {
			request.setAttribute("msg", "로그인 되었습니다.");
			// 로그인에 성공했으므로, 세션에 id 저장해야함.
			HttpSession session = request.getSession();
			// session.setAttribute("작명", 값);
			// session.getAttribute("작명");
			session.setAttribute("user_email", email);
			session.getAttribute("user_email");
			// session 유지 시간 30분 => ()안의 값 : 초
			session.setMaxInactiveInterval(60*30);
			
			// 이전 페이지 말고, 아예 다른 페이지 이동 시에는 sendRedirect 사용
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/loginOk.jsp");
			rd.forward(request, response);
			
		}else {
			request.setAttribute("msg", "로그인 실패.");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/loginErr.jsp");
			rd.forward(request, response);
		}
	}

}