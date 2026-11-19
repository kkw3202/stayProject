package control.user.mypage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Handler.dateHandler.DateHandlerDAO;
import model.Handler.pagingHandler.pagingHandlerDAO;
import model.Handler.pagingHandler.pagingHandlerDTO;
import model.User.MyBookingDAO;
import model.User.MyBookingDTO;

@WebServlet("/myBookingProc.do")
public class myBookingProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			reqPro(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			reqPro(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		// 세션에서 회원 아이디 얻고 mbdao 인스턴스
		HttpSession session = request.getSession(false);
		String user_email = (String)session.getAttribute("user_email");
		
		// 세션 만료 시 로그인 페이지로 이동
		if (user_email == null) {
			response.sendRedirect("loginProc.do");
			return;
		}
		
		MyBookingDAO mbdao = new MyBookingDAO();
		
		// 핸들러 페이징 pdao / pdto 인스턴스 
		pagingHandlerDAO pdao = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		
		// 한 화면에 보여줄 글의 개수 (3개)
		pdto.setPageSize(3);
		int pageSize = pdto.getPageSize();
		// 한 화면에 페이징 할 블록 개수 [1][2][3]
		int pageBlock = 3;
		
		// pageNum 과 status 파라미터 받아오기
		String pageNum = request.getParameter("pageNum");
		String status = request.getParameter("status");
		if (status == null || status.trim().equals("")) {
			status = "all";
		}
		
		// 탭별로 총 예약 건수 (탭에 상관없이 항상 전체 카운트를 유지)
		int totalAllCount = mbdao.countAllMyBooking(user_email);
		int countCancelBooking = mbdao.countCancelBooking(user_email);
		int countOverBooking = mbdao.countOverBooking(user_email);
		int countReservedBooking = mbdao.countReservedBooking(user_email);
		
		// 현재 선택된 탭 번호에 맞는 페이징 count 구하기
		int count = 0;
		if ("all".equals(status)) {
			count = totalAllCount;
		} else {
			count = mbdao.countBookingByStatus(user_email, Integer.parseInt(status));
		}
		
		// 페이징 부분
		pdto = pdao.getPaging(pageNum, count, pageSize, pageBlock);
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();
		
		// 상태별 예약 목록 조회
		ArrayList<MyBookingDTO> mblist;
		if ("all".equals(status)) {
			mblist = mbdao.getMyBookingList(startRow, pageSize, user_email);
		} else {
			mblist = mbdao.getMyBookingListByStatus(startRow, pageSize, user_email, Integer.parseInt(status));
		}
		
		DateHandlerDAO dhdao = new DateHandlerDAO();
		
		// 숫자 DecimalFormat 설정
		DecimalFormat df = new DecimalFormat("#,###");
		// 날짜 SimpleDateFormat 설정
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sf02 = new SimpleDateFormat("yyyy년MM월");
		
		// 예약 목록 리스트를 돌면서 숙박기간(숙박일수) 및 총 금액 계산
		if (mblist != null) {
			for (MyBookingDTO dto : mblist) {
				String inDate = dto.getBook_checkin_date();
				String outDate = dto.getBook_checkout_date();
				
				if (inDate != null && outDate != null && inDate.length() >= 10 && outDate.length() >= 10) {
					LocalDate start = LocalDate.parse(inDate.substring(0, 10));
					LocalDate end = LocalDate.parse(outDate.substring(0, 10));
					
					int date_of_stay = (int) ChronoUnit.DAYS.between(start, end);
					if (date_of_stay < 0) date_of_stay = 0;
					
					dto.setDate_of_stay(date_of_stay);
					dto.setTotal_price(date_of_stay * dto.getRoom_price());
				}
			}
		}
		
		// 세팅단계
		request.setAttribute("mblist", mblist);
		request.setAttribute("status", status);
		request.setAttribute("totalAllCount", totalAllCount);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("countCancelBooking", countCancelBooking);
		request.setAttribute("countOverBooking", countOverBooking);
		request.setAttribute("countReservedBooking", countReservedBooking);
		
		// mypage 안에서 필요한 jsp 이름 지정
		request.setAttribute("mypageSection", "myBooking");
		
		// rd로 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/mypage.jsp");
		rd.forward(request, response);
	}
}