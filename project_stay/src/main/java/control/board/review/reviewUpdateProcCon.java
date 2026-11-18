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
		
		// 수정 후 원래 보고 있던 숙소 상세페이지 리뷰 위치(#accReviewSection)로 복귀
		int acc_code = Integer.parseInt(request.getParameter("acc_code"));
		String book_checkin_date = request.getParameter("book_checkin_date");
		String book_checkout_date = request.getParameter("book_checkout_date");

		if (book_checkin_date == null || book_checkin_date.isEmpty()) {
			java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyyMMdd");
			java.util.Calendar cal = java.util.Calendar.getInstance();
			book_checkin_date = sd.format(cal.getTime());
			cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
			book_checkout_date = sd.format(cal.getTime());
		}

		response.sendRedirect("accInfoProc.do?acc_code=" + acc_code
				+ "&book_checkin_date=" + book_checkin_date
				+ "&book_checkout_date=" + book_checkout_date
				+ "#accReviewSection");
	}
	
}
