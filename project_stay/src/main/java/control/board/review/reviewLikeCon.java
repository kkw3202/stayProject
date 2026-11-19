package control.board.review;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Review.ReviewDAO;
import model.Review.ReviewDTO;

@WebServlet("/reviewLikeCon.do")
public class reviewLikeCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);
		String user_email = (String)session.getAttribute("user_email");
		
		// 세션 만료 시 로그인 페이지로 이동
		if (user_email == null) {
			response.sendRedirect("loginProc.do");
			return;
		}
		
		// 파라미터 값 가져오기
		int rev_code = Integer.parseInt(request.getParameter("rev_code"));
		String tmp= request.getParameter("acc_code");
		int acc_code=0;
		if(tmp == null || tmp.equals("0")||tmp.isEmpty()) {
			acc_code= 0;
		}else {
			acc_code=Integer.parseInt(tmp);
		}
		
		System.out.println("rev_code"+rev_code);
		// rdao 인스턴스 후 실행
		ReviewDAO rdao = new ReviewDAO();
		rdao.updateReviewLike(rev_code);
		// 업데이트 후 리다이렉트 (체크인/체크아웃 날짜 받기 + 날짜가 없을 경우 기본값)
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