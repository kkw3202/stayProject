package control.board.review;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Review.ReviewDAO;

@WebServlet("/reviewDeleteCon.do")
public class reviewDeleteCon extends HttpServlet {
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
		String tmp = request.getParameter("acc_code");
		int acc_code=0;
		if(tmp == null || tmp.equals("0")|| tmp.trim().isEmpty()) {
			acc_code = 0;
		}else {
			acc_code = Integer.parseInt(tmp);
		}
		
		// rdao 인스턴스 및 삭제처리하기 실행 (소프트삭제)
		ReviewDAO rdao = new ReviewDAO();
		rdao.deleteReview(rev_code);
		// 소프트삭제후, 원래 보고 있던 숙소 상세페이지 리뷰 영역으로 바로 이동
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
