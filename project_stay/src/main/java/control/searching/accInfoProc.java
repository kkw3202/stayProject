package control.searching;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		request.setAttribute("book_checkin_date", book_checkin_date);
		request.setAttribute("book_checkout_date", book_checkout_date);
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		

		// 스트레스땜에 도무지 대가리가 안굴러가서 일단 ai한테 짜달라 한거. 나중에 읽어보고 수정해야함
		
	    Calendar cal1 = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
	    
	    cal1.setTime(sd.parse(book_checkin_date));
	    cal2.setTime(sd.parse(book_checkout_date));

	    int day1 = cal1.get(Calendar.DAY_OF_YEAR);
	    int day2 = cal2.get(Calendar.DAY_OF_YEAR);
	    
	    int diffDays = day2 - day1;
	    request.setAttribute("diffDays", diffDays);
		// 여까지 ================================================================
	    
		AccomodationDAO adao = new AccomodationDAO();
		placeDTO pdto = new placeDTO();
		RoomDAO rdao = new RoomDAO();
		ArrayList<RoomDTO> rdto_arr = new ArrayList<>();
		
		pdto = adao.getOne_accInfo(acc_code);
		rdto_arr = rdao.getAllRoom_acc(book_checkin_date,book_checkout_date,acc_code);
		
		request.setAttribute("pdto", pdto);
		request.setAttribute("rdto_arr", rdto_arr);

		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=searching/accInfo.jsp");
		rd.forward(request, response);
	}
}
