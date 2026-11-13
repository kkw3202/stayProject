package control.user.mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Review.ReviewDAO;

@WebServlet("/MyRevModProcCon.do")
public class MyRevModProcCon extends HttpServlet {
	
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
		int rev_code = Integer.parseInt(request.getParameter("rev_code"));
		int rev_rating = Integer.parseInt(request.getParameter("rev_rating"));
		String rev_content = request.getParameter("rev_content");
		
		// rdao 인스턴스 및 업데이트 실행
		ReviewDAO rdao = new ReviewDAO();
		rdao.updateReview(rev_code, rev_rating, rev_content);
		
		// 페이지 넘기기
		response.sendRedirect("MyRevListProc.do");
	}

}
