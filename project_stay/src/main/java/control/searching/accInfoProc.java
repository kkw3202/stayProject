package control.searching;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.WishList.WishListDAO;
import model.common.search_accDTO;
import model.place.placeDTO;
import model.place.Accomodation.AccomodationDAO;
import model.place.Room.RoomDAO;
import model.place.Room.RoomDTO;

@WebServlet("/accInfoProc.do")
public class accInfoProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
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

		
		int acc_code = Integer.parseInt(request.getParameter("acc_code"));
		String book_checkin_date = request.getParameter("book_checkin_date");
		String book_checkout_date = request.getParameter("book_checkout_date");
		
		HttpSession session = request.getSession(false);
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
		
		String prt_book_checkin_date = sd2.format(sd.parse(book_checkin_date));
		String prt_book_checkout_date = sd2.format(sd.parse(book_checkout_date));
		

		request.setAttribute("book_checkin_date", book_checkin_date);
		request.setAttribute("prt_book_checkin_date", prt_book_checkin_date);
		request.setAttribute("book_checkout_date", book_checkout_date);
		request.setAttribute("prt_book_checkout_date", prt_book_checkout_date);
		// 프론트에서 날짜를 안가져오거나 안넘어오는 경우를 대비해 ai가 날짜를 임시로 넘김. 나중에 읽어볼때 수정해야함
		
	    Calendar cal1 = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
	    
	    cal1.setTime(sd.parse(book_checkin_date));
	    cal2.setTime(sd.parse(book_checkout_date));

	    int day1 = cal1.get(Calendar.DAY_OF_YEAR);
	    int day2 = cal2.get(Calendar.DAY_OF_YEAR);
	    
	    int diffDays = day2 - day1;
	    request.setAttribute("diffDays", diffDays);
		// 숙박일수 ================================================================
	    
		AccomodationDAO adao = new AccomodationDAO();
		placeDTO pdto = new placeDTO();
		RoomDAO rdao = new RoomDAO();
		ArrayList<RoomDTO> rdto_arr = new ArrayList<>();
		
		pdto = adao.getOne_accInfo(acc_code);
		rdto_arr = rdao.getAllRoom_acc(book_checkin_date,book_checkout_date,acc_code);
		
		request.setAttribute("pdto", pdto);
		
		DecimalFormat de = new DecimalFormat("#,##0");
		
		for(RoomDTO rp : rdto_arr) {
			int price = rp.getRoom_price();
			int total = price * diffDays;
			rp.setPrt_room_price(de.format(total));
			rp.setOne_day_prt_room_price(de.format(price));
		}
		
		if(session != null) {
			String user_email = (String) session.getAttribute("user_email");
			if(user_email != null) {
				WishListDAO wdao = new WishListDAO();
				ArrayList<Integer> wishes = wdao.getWishList(user_email);
				boolean is_wish = false;

				for(int i = 0; i<wishes.size(); i++) {
					if(acc_code == wishes.get(i)) {
						is_wish = true;
						break;
					}
				}
				
				request.setAttribute("is_wish", is_wish);
			}
		}

		
		request.setAttribute("rdto_arr", rdto_arr);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=searching/accInfo.jsp");
		rd.forward(request, response);
	}
}