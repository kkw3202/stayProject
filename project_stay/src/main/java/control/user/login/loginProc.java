package control.user.login;

import java.io.IOException;
import java.io.PrintWriter;

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
       response.setContentType("text/html; charset=UTF-8");
		
		UserDAO udao = new UserDAO();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean loginAdminchk = udao.loginAdminCheck(email, password);
		boolean loginchk = udao.loginCheck(email, password);
		
		PrintWriter out = response.getWriter();
		if("admin@naver.com".equals(email)) {
			if(loginAdminchk) {
				HttpSession session = request.getSession();
				session.setAttribute("user_email", email);
				session.setMaxInactiveInterval(60*240);
				
				request.setAttribute("msg", "관리자님 환영합니다");
				request.setAttribute("loginSuccess", true);
				
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/loginForm.jsp");
				rd.forward(request, response);
				
			}else {
				request.setAttribute("msg", "로그인 실패");
				request.setAttribute("loginSuccess", false);
				
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/loginForm.jsp");
				rd.forward(request, response);
			}
			return;
		}
		if(loginchk) {
			HttpSession session = request.getSession();
			session.setAttribute("user_email", email);
			session.getAttribute("user_email");
			session.setMaxInactiveInterval(60*30);
			
			request.setAttribute("msg", "로그인 성공");
			request.setAttribute("loginSuccess", true);
			
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/loginForm.jsp");
			rd.forward(request, response);
			
		}else {
			request.setAttribute("msg", "로그인 실패");
			request.setAttribute("loginSuccess", false);
			
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/loginForm.jsp");
			rd.forward(request, response);
		}
	}

}