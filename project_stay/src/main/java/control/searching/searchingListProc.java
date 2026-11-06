package control.searching;

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
import javax.servlet.http.HttpSessionContext;

import model.common.search_accDTO;
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
		search_accDTO sadto = new search_accDTO();
		
		ArrayList<search_accDTO> sa_arr = new ArrayList<search_accDTO>();
		
		String[] selectedDays = request.getParameterValues("selectedDays");
		String city_name = request.getParameter("city_name");
		String acc_type = request.getParameter("acc_type");
		String acc_name = request.getParameter("acc_name"); // 일부만 검색해도 받아와야함!
		String book_checkin_date=null;
		String book_checkout_date=null;
		
		HttpSession session = request.getSession(false);
		
		session.setAttribute("city_name", city_name);
		session.setAttribute("acc_type", acc_type);
		session.setAttribute("acc_name", acc_name);
		session.setAttribute("book_checkin_date", book_checkin_date);
		session.setAttribute("book_checkout_date", book_checkout_date);
		
		
		if(request.getParameterValues("selectedDays") != null) {
			if(selectedDays.length != 0) {
				boolean is_day_right = Integer.parseInt(selectedDays[0]) < Integer.parseInt(selectedDays[1]) ? true : false;
				
				
				if(is_day_right) {
					book_checkin_date = selectedDays[0];
					book_checkout_date = selectedDays[1];
				}else {
					book_checkin_date = selectedDays[1];
					book_checkout_date = selectedDays[0];
				}
			}
		}
		
		try {
			sa_arr = pdao.do_searching(city_name, acc_type, acc_name, book_checkin_date, book_checkout_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("sa_arr", sa_arr);
		
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=searching/searchList.jsp");
		rd.forward(request, response);
		
	}
}
