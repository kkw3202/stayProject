package control.guest;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Booking.BookingDAO;
import model.Handler.dateHandler.DateHandlerDAO;
import model.common.BookResultDTO;

@WebServlet("/guestInfoProc.do")
public class guestInfoProc extends HttpServlet {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		
		String book_number = request.getParameter("book_number");
		String guest_phone = request.getParameter("guest_phone");
		
		BookingDAO bdao = new BookingDAO();
		BookResultDTO brdto = bdao.get_one_BookResult(book_number, guest_phone);
		
		// 길이가 0이면 다시 확인받기. 있다면 띄워주기.
		if(brdto.getBook_number()==null) {
			String msg = "예약번호나 전화번호를 확인해주세요.";
			
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\" defer=\"defer\">");
			out.println("alert('"+msg+"');");
			out.println("location.href='index.jsp?section=guest/guestLogin.jsp';");
			out.println("</script>");
			return;
		}
		
		
		
		DateHandlerDAO dhdao = new DateHandlerDAO();
		
		ArrayList<String> days = dhdao.getAllDay(brdto.getBook_checkin_date(), brdto.getBook_checkout_date());
		
		
		int date_of_stay = days.size();
		int total_price = date_of_stay * (brdto.getRoom_price());
		
		DecimalFormat de = new DecimalFormat("#,##0");
		
		brdto.setDate_of_stay(date_of_stay);
		brdto.setPrt_total_price(de.format(total_price));
		brdto.setPrt_room_price(de.format(brdto.getRoom_price()));
		request.setAttribute("brdto", brdto);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=guest/guestInfo.jsp");
		rd.forward(request, response);
	}

}
