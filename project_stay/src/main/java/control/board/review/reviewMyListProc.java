package control.board.review;

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
import model.place.Accommodation.AccommodationDTO;

@WebServlet("/reviewMyListProc.do")
public class reviewMyListProc extends HttpServlet {
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
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		// 핸들러 인스턴스 및 httpSession선언하여 user_email 가져오기
		pagingHandlerDAO pado = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		
		// 한 화면에 보여질 행의 개수 (10개)
		pdto.setPageSize(10);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 하고 싶은 개수 [1][2][3][4][5]
		int pageBlock=5;
		
		// pageNum 정보 가져오기 (최초: null / 이후에는 페이징 번호별로 가져오기)
		String pageNum = request.getParameter("pageNum");
		
		// 숙소코드 카져오기
		String searchType = request.getParameter("searchType");
		int acc_code = 0; 
		// 리뷰 개수 변수 설정
		int count = 0;
		
		
		// rdao 실행하기
		ReviewDAO rdao = new ReviewDAO();
		// 회원명 찾는 메소드 실행
		String user_name = rdao.getUser_name(user_email);
		
		// 내가쓴 게시글 숫자 세는 메소드 가져오기
		count = rdao.getMyreviewCount(user_name);
		
		// 페이징 부분
		pdto = pado.getPaging(pageNum, count, pageSize, pageBlock);
		
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();

		// 내가쓴 게시글 숫자 계산하는 메소드 가져오기
		ArrayList<ReviewDTO> rlist = rdao.getMyreviewBoard(startRow, pageSize, user_name);
		
		// 콤보박스에서 특정 숙소 선택시 AccommodationDTO에서 해당숙소 목록 가져오기
		ArrayList<AccommodationDTO> alist = rdao.getAccList();
		
		// JSP에 넘길 자료들
		request.setAttribute("rlist", rlist);
		request.setAttribute("alist", alist);
		
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		// searchType에 acc_code 숫자형태로 넘겨주기
		request.setAttribute("searchType", acc_code);
		System.out.println(searchType);
		
		// 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/review/reviewList.jsp");
		rd.forward(request, response);
	}

}
