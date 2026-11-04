package control.user.join;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User.UserDAO;
import model.User.UserDTO;

@WebServlet("/joinProc.do")
public class joinProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		UserDAO udao = new UserDAO();
		UserDTO udto = new UserDTO();
		
		String email = request.getParameter("email");
		String emailChk = udao.getEmail(email);
		String pass = request.getParameter("password");
		String passChk = udao.getPass(pass);
		
		udto.setUser_email(email);
		udto.setUser_password(pass);
		udto.setUser_name(request.getParameter("name"));
		udto.setUser_phone(request.getParameter("phone"));
		udto.setUser_birth(request.getParameter("birth"));
		udto.setUser_gender(Integer.parseInt(request.getParameter("gender")));
		udto.setUser_chk_del(Integer.parseInt(request.getParameter("gender")));
		udto.setUser_passkey(request.getParameter("passkey"));
		
		String link ="";
		
		if(email.equals("admin@naver.com")){
		request.setAttribute("msgEmail", "관리자님, 오늘도 반갑습니다!");
		link = "#";
		}else if(email.equals(emailChk)) {
			request.setAttribute("msgEmail", "이미 존재하는 이메일 입니다");
			link = "/user/joinEmailErr.jsp";
		}else if(pass.equals(passChk)) {
			udao.insertUser(udto);
			link = "index.jsp";
		}else {
			request.setAttribute("msgPass", "비밀번호를 확인하세요");
			link = "/user/joinPassErr.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(link);
		
		if(link.equals("index.jsp")) {
			rd.forward(request, response);
		}else {
			response.sendRedirect(link);
		}
	}
}
