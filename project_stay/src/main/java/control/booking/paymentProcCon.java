package control.booking;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Booking.BookingDAO;
import model.Handler.User_change;
import model.Handler.dateHandler.DateHandlerDAO;
import model.common.BookResultDTO;

@WebServlet("/paymentProcCon.do")
public class paymentProcCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			reqPro(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			reqPro(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
		request.setCharacterEncoding("UTF-8");
		
		String book_number = request.getParameter("book_number");
		
		BookingDAO bdao = new BookingDAO();
		BookResultDTO brdto = bdao.get_one_BookResult(book_number);
		
		DateHandlerDAO dhdao = new DateHandlerDAO();
		
		ArrayList<String> days = dhdao.getAllDay(brdto.getBook_checkin_date(), brdto.getBook_checkout_date());
		
		
		int date_of_stay = days.size();
		int total_price = date_of_stay * (brdto.getRoom_price());
		
		brdto.setBook_number(book_number);
		brdto.setDate_of_stay(date_of_stay);
		brdto.setTotal_price(total_price);
		request.setAttribute("brdto", brdto);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=booking/bookingResult.jsp");
		rd.forward(request, response);
	}

}
