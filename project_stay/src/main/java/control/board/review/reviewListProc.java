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

@WebServlet("/reviewListProc.do")
public class reviewListProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// 핸들러 인스턴스 및 httpSession선언하여 user_email 가져오기
		pagingHandlerDAO pado = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		HttpSession session = request.getSession();
		String user_email= (String)session.getAttribute("user_email");
		ReviewDAO rdao = new ReviewDAO();
		
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
		// count 개수 설정
		if(searchType==null || searchType.equals("none")|| searchType.equals("0")) {  // 콤보박스에서 선택이 안된 경우
			count = rdao.countTotalReview();  // 전체 리뷰개수 
		}else {  // 콤보박스에서 숙소 하나 선택한 경우
			acc_code = Integer.parseInt(searchType);
			count = rdao.countAccReview(acc_code);  // 해당 숙소의 리뷰개수
		}
		
		// 페이징 부분
		pdto = pado.getPaging(pageNum, count, pageSize, pageBlock);
		
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();
		
		ArrayList<ReviewDTO> rlist;
		if(searchType==null || searchType.equals("none")|| searchType.equals("0")) {
			rlist = rdao.getReviewAllBoard(startRow, pageSize);
		}else {
			rlist = rdao.getReviewAccBoard(startRow, pageSize, acc_code);
		}
		
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
		
		System.out.println("acc_code: " + acc_code);
		System.out.println("count: " + count);
		
		// 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/review/reviewList.jsp");
		rd.forward(request, response);
	}
}
