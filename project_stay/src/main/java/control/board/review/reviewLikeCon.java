package control.board.review;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		// 정보가져오기
		int rev_code = Integer.parseInt(request.getParameter("rev_code"));
		String tmp= request.getParameter("acc_code");
		int acc_code=0;
		if(tmp == null || tmp.equals("0")||tmp.isEmpty()) {
			acc_code= 0;
		}else {
			acc_code=Integer.parseInt(tmp);
		}
		
		System.out.println("rev_code"+rev_code);
		// rdao 인스턴스 및 실행
		ReviewDAO rdao = new ReviewDAO();
		rdao.updateReviewLike(rev_code);
		//System.out.println("rev_code"+rev_code);
		// 업데이트 후 복귀
		response.sendRedirect("reviewListProc.do?searchType="+acc_code);
	}

}
