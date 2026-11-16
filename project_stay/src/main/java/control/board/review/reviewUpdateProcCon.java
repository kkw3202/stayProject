package control.board.review;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Review.ReviewDAO;

@WebServlet("/reviewUpdateProcCon.do")
public class reviewUpdateProcCon extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩하기
		request.setCharacterEncoding("utf-8");
		
		// 정보가져오기
		String tmp = request.getParameter("rev_code");
		
		int rev_code = Integer.parseInt(tmp);
		int rev_rating = Integer.parseInt(request.getParameter("rev_rating"));
		String rev_content = request.getParameter("rev_content");
		
		System.out.println("tmp"+tmp);
		System.out.println("rev_code"+rev_code);
		System.out.println("rev_rating"+rev_rating);
		System.out.println("rev_content"+rev_content);
		
		// rdao 인스턴스 + 업데이트 실시
		ReviewDAO rdao = new ReviewDAO();
		rdao.updateReview(rev_code, rev_rating, rev_content);
		
		RequestDispatcher rd = request.getRequestDispatcher("reviewListProc.do");
		rd.forward(request, response);
		
	}
	
}
