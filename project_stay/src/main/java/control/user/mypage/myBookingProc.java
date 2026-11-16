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
import model.User.MyBookingDAO;
import model.User.MyBookingDTO;

@WebServlet("/myBookingProc.do")
public class myBookingProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		// 세션으로 회원 아이디 및 mbdao 인스턴스
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		MyBookingDAO mbdao = new MyBookingDAO();
		
		// 핸들러 페이징pdao / pdto 인스턴스 
		pagingHandlerDAO pdao = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		
		// 한 화면에 보여질 행의 개수 (8개)
		pdto.setPageSize(8);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 하고 싶은 개수 [1][2][3]
		int pageBlock = 3;
		
		// pageNum 정보 가져오기 (처음에는 null값)
		String pageNum = request.getParameter("pageNum");
		
		// 리뷰 총 개수 변수 
		int count = 0;
		count = mbdao.countAllMyBooking(user_email);
		
		// 페이징 부분
		pdto = pdao.getPaging(pageNum, count, pageSize, pageBlock);
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();
		
		// 배열선언
		ArrayList<MyBookingDTO> mblist;
		mblist = mbdao.getMyBookingList(startRow, pageSize, user_email);
		// 포장단계
		request.setAttribute("mblist", mblist);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		// mypage 안쪽에 넣을 jsp 이름 포장
		request.setAttribute("mypageSection", "myBooking");
		
		// rd로 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
		
	}

}
