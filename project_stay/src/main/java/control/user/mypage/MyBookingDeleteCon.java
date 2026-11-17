package control.user.mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User.MyBookingDAO;

@WebServlet("/MyBookingDeleteCon.do")
public class MyBookingDeleteCon extends HttpServlet {
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
		MyBookingDAO mbdao = new MyBookingDAO();
		String book_number = request.getParameter("book_number");
		
		// 업데이트 실행하기
		mbdao.deleteMyBooking(book_number);
		
		// 페이지 리턴하기
		response.sendRedirect("myBookingProc.do");
	}
}
