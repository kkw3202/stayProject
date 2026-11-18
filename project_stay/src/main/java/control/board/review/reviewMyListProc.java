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
		
		// 세션값 가져오기
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		// 핸들러 인스턴스 및 httpSession을하여 user_email 가져오기
		pagingHandlerDAO pado = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		
		// 한 화면에 보여줄 글의 개수 (10개)
		pdto.setPageSize(10);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 할 블록 개수 [1][2][3][4][5]
		int pageBlock=5;
		
		// pageNum 파라미터 받아오기 (처음: null / 이후부터는 페이징 번호클릭해서 받아옴)
		String pageNum = request.getParameter("pageNum");
		
		// 숙소코드 카테고리용
		String searchType = request.getParameter("searchType");
		int acc_code = 0; 
		// 전체 레코드 개수 변수
		int count = 0;
		
		
		// rdao 생성하기
		ReviewDAO rdao = new ReviewDAO();
		// 회원의 이름 찾는 메서드 호출
		String user_name = rdao.getUser_name(user_email);
		
		// 내글 게시글 총 개수 메서드 가져오기
		count = rdao.getMyreviewCount(user_name);
		
		// 페이징 부분
		pdto = pado.getPaging(pageNum, count, pageSize, pageBlock);
		
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();

		// 내글 게시글 목록 가져오는 메서드 가져오기
		ArrayList<ReviewDTO> rlist = rdao.getMyreviewBoard(startRow, pageSize, user_name);
		
		// 콤보박스로서 특정 숙소 선택시 AccommodationDTO에서 해당숙소의 정보 가져오기
		ArrayList<AccommodationDTO> alist = rdao.getAccList();
		
		// JSP로 넘길 자료들
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
		// searchType을 acc_code 정수형태로 넘겨주기
		request.setAttribute("searchType", acc_code);
		System.out.println(searchType);
		
		// MylistProc에서 넘어온 요청인지 확인하는 구분을 위해 myList 값을 생성하여 넘기기
		String myList = "myList";
		request.setAttribute("listType", myList);
		
		// 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/review/reviewList.jsp");
		rd.forward(request, response);
	}

}