package control.user.mypage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Handler.pagingHandler.pagingHandlerDAO;
import model.Handler.pagingHandler.pagingHandlerDTO;
import model.WishList.WishListDAO;
import model.WishList.WishListDTO;
@WebServlet("/myWishProc.do")
public class myWishProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		
		// 핸들러 인스턴스 및 user_email 가져오기 + wdao 인스턴스
		HttpSession session = request.getSession(false);
		String user_email = (String)session.getAttribute("user_email");
		
		// 세션 만료 시 처리
		if(user_email == null) {
			response.sendRedirect("loginProc.do");
			return;
		}
		
		pagingHandlerDAO pdao = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		WishListDAO wdao = new WishListDAO();
		
		// 한 화면에 보여줄 글의 개수 (3개)
		pdto.setPageSize(3);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 할 블록 개수 [1][2][3]
		int pageBlock = 3;
		
		// pageNum 파라미터가져오기 (처음화면 null 일때)
		String pageNum = request.getParameter("pageNum");
		
		// count(전체 글의 개수)
		int count = 0;
		count = wdao.countMyWishlist(user_email);
		
		// 페이징 부분
		pdto = pdao.getPaging(pageNum, count, pageSize, pageBlock);
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();
		
		// 날짜 세팅을 위한 설정
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		
		
		/* Date nextDay = new Date(); */
		Date nextDay = cal.getTime();
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String defaultCheckinDate = sd.format(today);
		String defaultCheckoutDate = sd.format(nextDay);
		
		request.setAttribute("defaultCheckinDate", defaultCheckinDate);
		request.setAttribute("defaultCheckoutDate", defaultCheckoutDate);
		
		
		// 위 찜 리스트 관리하는 함수 불러오기
		ArrayList<WishListDTO> wlist;
		wlist = wdao.myWishList(user_email, startRow, pageSize);
		
		// jsp로 넘길 자료로 세팅하기
		request.setAttribute("wlist", wlist);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		// mypage 안에서 필요한 섹션명 설정하기
		request.setAttribute("mypageSection", "myWish");
		
		// 포워딩
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
	}

	
}