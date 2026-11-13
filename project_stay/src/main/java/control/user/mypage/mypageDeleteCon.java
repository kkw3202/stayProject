package control.user.mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.UserDAO;

@WebServlet("/mypageDeleteCon.do")
public class mypageDeleteCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// 정보가져오기
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		// udao 인스턴스
		UserDAO udao = new UserDAO();
		
		// 소프트 삭제 실행
		udao.deleteMember(user_email);
		
		// 로그아웃 시키고, 돌려보내기
		session.invalidate();
		response.sendRedirect("index.jsp");
		
	}
}
