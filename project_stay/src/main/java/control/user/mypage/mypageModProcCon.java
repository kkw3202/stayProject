package control.user.mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.UserDAO;


@WebServlet("/mypageModProcCon.do")
public class mypageModProcCon extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		System.out.println("--------------------------");
		// 아이디 가져오기
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		// 다른 정보들 가져오기
		String user_name = request.getParameter("user_name");
		String user_phone = request.getParameter("user_phone");
		int user_gender = Integer.parseInt(request.getParameter("user_gender"));
		String user_birth = request.getParameter("user_birth");
		String new_password = request.getParameter("new_password");
		String passkey = request.getParameter("passkey");
		
		// udao 인스턴스 및 업데이트 하기
		UserDAO udao = new UserDAO();
		udao.updateUserMod(user_name, user_phone, user_gender, user_birth, new_password, passkey, user_email);
		
		// 바로 돌려보내기
		response.sendRedirect("mypageInfoProc.do");
	}
}
