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

// 내 정보 수정하는 폼 그리기
@WebServlet("/mypageModProc.do")
public class mypageModProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// 세션값 가져오기
		HttpSession session = request.getSession(false);
		String user_email = (String)session.getAttribute("user_email");
		
		// udao / udto 인스턴스
		UserDAO udao = new UserDAO();
		UserDTO udto = new UserDTO();
		
		if(session != null) {
			udto = udao.getUserInfo(user_email);
		}
		
		if (user_email == null) {
			response.sendRedirect("loginProc.do");
			return;
		}
		
		// 세팅하기
		request.setAttribute("udto", udto);
		
		// mypage 안에서 필요한 jsp 이름 지정
		request.setAttribute("mypageSection", "mypageMod");
		
		// 포워딩하기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
	}
	
}