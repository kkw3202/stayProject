package control.user.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Review.ReviewDAO;

@WebServlet("/MyRevDeleteCon.do")
public class MyRevDeleteCon extends HttpServlet {

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
		
		// rdao 인스턴스 및 리뷰코드 가져오기
		ReviewDAO rdao = new ReviewDAO();
		
		// 소프트 삭제 후 페이지 넘기기
		rdao.deleteReview(rev_code);
		
		response.sendRedirect("MyRevListProc.do");
	}
}
