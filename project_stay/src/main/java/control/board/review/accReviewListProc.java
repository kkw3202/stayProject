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

import model.Booking.BookingDAO;
import model.Handler.pagingHandler.pagingHandlerDAO;
import model.Handler.pagingHandler.pagingHandlerDTO;
import model.Review.ReviewDAO;
import model.Review.ReviewDTO;
import model.User.MyBookingDAO;
import model.User.MyBookingDTO;


@WebServlet("/accReviewListProc.do")
public class accReviewListProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		System.out.println("-----------------------------------------------------");
		
		// 세션값 가져오기
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		pagingHandlerDAO pado = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		ReviewDAO rdao = new ReviewDAO();
		
		// 예약 관련 DAO
		BookingDAO bdao = new BookingDAO();
		
		// 한 화면에 보여줄 글의 개수 (5개)
		pdto.setPageSize(5);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 할 블록 개수 [1][2][3][4][5]
		int pageBlock=5;
		
		// pageNum 파라미터 받아오기 (처음: null / 이후부터는 페이징 번호 클릭해서 받아옴)
		String pageNum = request.getParameter("pageNum");
		int acc_code = Integer.parseInt(request.getParameter("acc_code"));
		// 전체 레코드 개수 변수
		int count = 0;
		count = rdao.countAccReview(acc_code);  // 해당 숙소의 리뷰개수
		
		// 페이징 부분
		pdto = pado.getPaging(pageNum, count, pageSize, pageBlock);
		
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();
		
		// 로그인 한 사용자가 숙소에 대해 작성 가능한 상태인지 확인 
		boolean canWrite = rdao.canWriteReview(user_email, acc_code);
		
		ArrayList<ReviewDTO> rlist = rdao.getReviewAccBoard(startRow, pageSize, acc_code);
		
		// 세팅하기
		request.setAttribute("rlist", rlist);
		request.setAttribute("canWrite", canWrite); // notList 처리를 위한 boolean 값
		request.setAttribute("acc_code", acc_code); // 리뷰 작성 페이지로 갈 수 있도록 넘김
		
		// 세팅하기
		request.setAttribute("rlist", rlist);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("revCount", count);  // 전체 레코드 수 넘김
		
		System.out.println("-----------------------------------------------------");
		
		// 포워딩하기
		RequestDispatcher rd = request.getRequestDispatcher("/board/review/accReviewList.jsp");
		rd.include(request, response);
		
	}

}