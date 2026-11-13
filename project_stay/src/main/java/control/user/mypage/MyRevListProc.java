package control.user.mypage;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Handler.pagingHandler.pagingHandlerDAO;
import model.Handler.pagingHandler.pagingHandlerDTO;
import model.Review.ReviewDAO;
import model.Review.ReviewDTO;

@WebServlet("/MyRevListProc.do")
public class MyRevListProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// 페이징 핸들러 및 자료가져오기
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		pagingHandlerDAO pdao = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		
		// rdao 가져오기
		ReviewDAO rdao = new ReviewDAO();
		
		// 한 화면에 보여질 행의 개수 (7개)
		pdto.setPageSize(7);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 하고 싶은 개수 [1][2][3][4][5]
		int pageBlock=5;
		
		// pageNum 정보 가져오기 (최초 : null / 이후에는 페이징 번호별로 가져오기)
		String pageNum = request.getParameter("pageNum");
		
		// 리뷰 개수 변수 설정
		int count = 0;
		
		String user_name = rdao.getUser_name(user_email);
		
		// count 개수 설정
		count = rdao.getMyreviewCount(user_name); // 일단 전체 리뷰 개수
		
		// --------------------------------------------
		// 페이징 부분
		pdto = pdao.getPaging(pageNum, count, pageSize, pageBlock);
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();
		
		// 배열
		ArrayList<ReviewDTO> rlist;
		rlist = rdao.getMyreviewBoard(startRow, pageSize, user_name);
		
		// JSP에 넘길 자료 포장하기
		request.setAttribute("rlist", rlist);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		// mypage 안쪽에 넣을 jsp 이름 포장
		request.setAttribute("mypageSection", "myReview");
		
		// 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
		
	}
}
