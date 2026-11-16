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
		System.out.println("acc_code" + acc_code);
		
		// 소프트삭제후, 바로 이동 (원래 선택햇던 숙소로 돌아가기)
		response.sendRedirect("reviewListProc.do?searchType="+acc_code);
	}
}
