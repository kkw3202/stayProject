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
		// 기존 이메일과 같은 이메일일 경우
		
		System.out.println("email:"+email+"emailChk:"+emailChk);
		if(email.equals(emailChk)) {
			request.setAttribute("msg", "이미 존재하는 이메일 입니다");
			link = "user/joinErr.jsp";
		}else {
			udao.insertUser(udto);
			request.setAttribute("msg", "회원가입을 축하합니다.");
			link = "user/joinOk.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(link);
		
		if(link.equals("index.jsp")) {
			rd.forward(request, response);
		}else {
			response.sendRedirect(link);
		}
	}
}
