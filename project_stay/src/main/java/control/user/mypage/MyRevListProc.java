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
import model.User.MyBookingDAO;
import model.User.MyBookingDTO;

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
		
		// rdao 생성하기
		ReviewDAO rdao = new ReviewDAO();
		
		// 한 화면에 보여줄 글의 개수 (3개)
		pdto.setPageSize(3);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 할 블록 개수 [1][2][3][4][5]
		int pageBlock=5;
		
		// pageNum 파라미터 받아오기 (처음 : null / 이후부터는 페이징 번호클릭으로 받아옴)
		String pageNum = request.getParameter("pageNum");
		
		// 전체 글의 개수 저장
		int count = 0;
		
		String user_name = rdao.getUser_name(user_email);
		
		// count 값 구하기
		count = rdao.getMyreviewCount(user_name); // 하단 전체 글 개수
		
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
		
		// 리뷰 작성안된 목록 배열 
		ArrayList<ReviewDTO> notList;
		notList = rdao.getUnwrittenMyReviewList(user_email);
		
		// 리뷰 작성안된 목록 총 갯수 구하는 함수
		int notList_cnt = rdao.countUnwrittenTotalReview(user_email);
		
		// JSP로 넘길 자료 세팅하기
		request.setAttribute("rlist", rlist);
		request.setAttribute("notList", notList);
		request.setAttribute("notList_cnt", notList_cnt);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		// mypage 안에서 필요한 jsp 이름 지정
		request.setAttribute("mypageSection", "myReview");
		
		// 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
		
	}
}