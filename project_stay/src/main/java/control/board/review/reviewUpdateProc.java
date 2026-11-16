package control.board.review;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Review.ReviewDAO;
import model.Review.ReviewDTO;

@WebServlet("/reviewUpdateProc.do")
public class reviewUpdateProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		
		//  리뷰게시판에서 리뷰 코드 가져오기 
		String code = request.getParameter("rev_code"); 
		int rev_code = 0; 
		if(code!=null || !code.isEmpty()) {
			rev_code = Integer.parseInt(code);
		}
		request.setAttribute("rev_code", rev_code);
		
		// rdao 인스턴스 / rdto 가져오기
		ReviewDAO rdao = new ReviewDAO();
		ReviewDTO rdto = rdao.getReviewOneBoard(rev_code);
		// 숙소명 가져오기
		// String acc_name = rdao.getAcc_name(rev_code);
		// String acc_name = rdto.getAcc_name();
		
		// System.out.println("acc_name"+acc_name);
		
		request.setAttribute("rdto", rdto);
		// request.setAttribute("acc_name", acc_name);
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/review/reviewUpdateForm.jsp");
		rd.forward(request, response);
	}
}
