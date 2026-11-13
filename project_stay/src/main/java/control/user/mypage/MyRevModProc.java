package control.user.mypage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Review.ReviewDAO;
import model.Review.ReviewDTO;

@WebServlet("/MyRevModProc.do")
public class MyRevModProc extends HttpServlet {
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
		
		// rdao 인스턴스 및 해당 글만 가져오는 함수 실행
		ReviewDAO rdao = new ReviewDAO();
		ReviewDTO rdto = rdao.getReviewOneBoard(rev_code);
		
		// 정보들 포장하기
		request.setAttribute("rdto", rdto);
		
		// mypage 안쪽에 넣을 jsp 이름 포장
		request.setAttribute("mypageSection", "myReviewMod");
		
		// 페이지 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
	}
	

}
