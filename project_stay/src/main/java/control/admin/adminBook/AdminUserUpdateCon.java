package control.admin.adminBook;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.UserDAO;

@WebServlet("/AdminUserUpdateCon.do")
public class AdminUserUpdateCon extends HttpServlet {
       
    
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
        HttpSession session = request.getSession(false);

        if (session == null || !"admin@naver.com".equals(session.getAttribute("user_email"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다.");
            return;
        }
        
        // udao 선언
        UserDAO udao = new UserDAO();
        
        // 업데이트 실행 (전 회원 상태값 체크 - > 기간 만료된 경우 book_chk_status 를 3->2 로 바꿈)
        udao.updateAllBookingStatus();
        
        // 돌려 보내기
        response.sendRedirect("AdminBookListProc.do");
        
	}

}