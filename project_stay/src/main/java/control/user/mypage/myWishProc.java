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
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		// 세션 만료 예외처리
		if(user_email == null) {
			response.sendRedirect("loginProc.do");
			return;
		}
		
		pagingHandlerDAO pdao = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		WishListDAO wdao = new WishListDAO();
		
		// 한 화면에 보여질 행의 개수 (5개)
		pdto.setPageSize(5);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 하고 싶은 개수 [1][2][3]
		int pageBlock = 3;
		
		// pageNum 정보가져오기 (처음화면 null 값)
		String pageNum = request.getParameter("pageNum");
		
		// count(전체 개수 설정)
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
		
		// 내 찜 목록 그리는 함수 불러오기
		ArrayList<WishListDTO> wlist;
		wlist = wdao.myWishList(user_email, startRow, pageSize);
		
		// jsp에 넘길 자료들 포장하기
		request.setAttribute("wlist", wlist);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		// mypage 안쪽에 넣을 페이지 포장하기
		request.setAttribute("mypageSection", "myWish");
		
		// 보내기
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
	}

	
}
