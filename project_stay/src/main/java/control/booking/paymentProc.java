package control.booking;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Booking.BookingDAO;
import model.Handler.User_change;


@WebServlet("/paymentProc")
public class paymentProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		int user_code = 0;
		
		String book_checkin_date = request.getParameter("book_checkin_date");
		String book_checkout_date = request.getParameter("book_checkout_date");
		int room_code = Integer.parseInt(request.getParameter("room_code"));
		String book_name = request.getParameter("book_name");
		
		if(session!=null) {
			String user_email = (String) session.getAttribute("user_email");
					
			User_change uc = new User_change();
			user_code = uc.email_to_code(user_email); 
		}
		
		BookingDAO bdao = new BookingDAO();
		bdao.doBooking(user_code, book_checkin_date, book_checkout_date, room_code, book_name);
		
		response.sendRedirect("index.jsp");
		
	}
	
	

}
