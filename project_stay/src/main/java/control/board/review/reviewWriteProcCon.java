package control.board.review;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Review.ReviewDAO;
import model.Review.ReviewDTO;

@WebServlet("/reviewWriteProcCon.do")
public class reviewWriteProcCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// 리뷰작성하기 위해서 파라미터값 가져오기
		int acc_code = Integer.parseInt(request.getParameter("acc_code"));
		String acc_name = request.getParameter("acc_name");
		int rev_rating = Integer.parseInt(request.getParameter("rev_rating"));
		String rev_content = request.getParameter("rev_content");
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		String user_name = request.getParameter("user_name");
		
		// rdao 인스턴스 / rdto 인스턴스 생성
		ReviewDAO rdao = new ReviewDAO();
		ReviewDTO rdto = new ReviewDTO();
		
		int user_code = rdao.getUserCode(user_email);
		
		// rdto에 세팅하기
		rdto.setAcc_code(acc_code);
		rdto.setUser_code(user_code);
		rdto.setRev_rating(rev_rating);
		rdto.setRev_content(rev_content);
		
		// 날짜 세팅을 위한 설정
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		
		
		/* Date nextDay = new Date(); */
		Date nextDay = cal.getTime();
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String defaultCheckinDate = sd.format(today);
		String defaultCheckoutDate = sd.format(nextDay);
		
		request.setAttribute("defaultCheckinDate", defaultCheckinDate);
		request.setAttribute("defaultCheckoutDate", defaultCheckoutDate);
		
		
		// 인서트하기
		rdao.insertReview(rdto);
		
		// 넘기기
		response.sendRedirect("accInfoProc.do?acc_code=" + acc_code 
				+ "&book_checkin_date=" + defaultCheckinDate 
				+ "&book_checkout_date=" + defaultCheckoutDate 
				+ "#accReviewSection");
	}
}