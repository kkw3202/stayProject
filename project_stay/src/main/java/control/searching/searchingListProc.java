package control.searching;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.place.placeDAO;
import model.place.placeDTO;

@WebServlet("/searchingListProc.do")
public class searchingListProc extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		placeDAO pdao = new placeDAO();
		placeDTO pdto = new placeDTO();
		
		String[] selectedDays = request.getParameterValues("selectedDays");
		String city_name = request.getParameter("city_name");
		String acc_type = request.getParameter("acc_type");
		String room_name = request.getParameter("room_name"); // 일부만 검색해도 받아와야함!
		String book_checkin_date;
		String book_checkout_date;

		boolean is_day_right = Integer.parseInt(selectedDays[0]) < Integer.parseInt(selectedDays[1]) ? true : false;
		
		if(is_day_right) {
			book_checkin_date = selectedDays[0];
			book_checkout_date = selectedDays[1];
		}else {
			book_checkin_date = selectedDays[1];
			book_checkout_date = selectedDays[0];
		}
		
			
		pdao.do_searching(city_name, acc_type, room_name, book_checkin_date, book_checkout_date);
		
		
		
		
		
	}
}
