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
		
		// 리뷰작성하기 폼에서 정보가져오기
		int acc_code = Integer.parseInt(request.getParameter("acc_code"));
		String acc_name = request.getParameter("acc_name");
		int rev_rating = Integer.parseInt(request.getParameter("rev_rating"));
		String rev_content = request.getParameter("rev_content");
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		String user_name = request.getParameter("user_name");
		
		// rdao 인스턴스 / rdto 인스턴스 및
		ReviewDAO rdao = new ReviewDAO();
		ReviewDTO rdto = new ReviewDTO();
		
		int user_code = rdao.getUserCode(user_email);
		
		// rdto에 포장하기
		rdto.setAcc_code(acc_code);
		rdto.setUser_code(user_code);
		rdto.setRev_rating(rev_rating);
		rdto.setRev_content(rev_content);
		
		// 인서트하기
		rdao.insertReview(rdto);
		
		// 페이지 넘기기 (기존에 선택한 숙소로 돌아가기)
		response.sendRedirect("reviewListProc.do?searchType="+acc_code);
		
	}
}
