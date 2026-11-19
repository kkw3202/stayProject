package control.booking; 

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/bookingProc.do")
public class bookingProc extends HttpServlet {
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
		
		int room_code = Integer.parseInt(request.getParameter("room_code"));
		String acc_checkinTime = request.getParameter("acc_checkinTime");
		String book_checkout_date = request.getParameter("book_checkout_date");
		int get_totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
		String acc_code = request.getParameter("acc_code");
		String room_img1 = request.getParameter("room_img1");
		String room_name = request.getParameter("room_name");
		String book_checkin_date = request.getParameter("book_checkin_date");
		String acc_checkoutTime = request.getParameter("acc_checkoutTime");
		String room_capacity = request.getParameter("room_capacity");
		String room_info = request.getParameter("room_info");
		int get_room_price = Integer.parseInt(request.getParameter("room_price"));
	
		HttpSession session = request.getSession(false);
		
		DecimalFormat de = new DecimalFormat("#,##0");
		String totalPrice = de.format(get_totalPrice);
		String room_price = de.format(get_room_price);
		
		if(session != null) {
			String user_email = (String) session.getAttribute("user_email");
			// user_email을 세션에서 자동으로 불러오기 처리
		}
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
		
		String prt_book_checkin_date = sd2.format(sd.parse(book_checkin_date));
		String prt_book_checkout_date = sd2.format(sd.parse(book_checkout_date));
		
		request.setAttribute("room_code", room_code);
		
		String prt_acc_checkinTime = acc_checkinTime.substring(0,5);
		String prt_acc_checkoutTime = acc_checkoutTime.substring(0,5);
		
		request.setAttribute("acc_checkinTime", prt_acc_checkinTime);
		request.setAttribute("acc_checkoutTime", prt_acc_checkoutTime);
		request.setAttribute("totalPrice", totalPrice);
		request.setAttribute("acc_code", acc_code);
		request.setAttribute("room_img1", room_img1);
		request.setAttribute("room_name", room_name);
		request.setAttribute("book_checkin_date", book_checkin_date);
		request.setAttribute("book_checkout_date", book_checkout_date);
		request.setAttribute("room_capacity", room_capacity);
		request.setAttribute("room_info", room_info);
		request.setAttribute("room_price", room_price);
		request.setAttribute("prt_book_checkin_date", prt_book_checkin_date);
		request.setAttribute("prt_book_checkout_date", prt_book_checkout_date);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=booking/booking.jsp");
		rd.forward(request, response);
	}
}