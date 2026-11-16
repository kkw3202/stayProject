package control.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.common.IndexDAO;
import model.common.IndexDTO;

@WebServlet("/index_sectionProc.do")
public class index_sectionProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		boolean is_index = true;
		
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		
		
		/* Date nextDay = new Date(); */
		Date nextDay = cal.getTime();

		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String defaultCheckinDate = sd.format(today);
		String defaultCheckoutDate = sd.format(nextDay);
		
		request.setAttribute("defaultCheckinDate", defaultCheckinDate);
		request.setAttribute("defaultCheckoutDate", defaultCheckoutDate);
		
		IndexDAO indao = new IndexDAO();
		
		ArrayList<IndexDTO> indto_arr = indao.getIndex_section();
		
		request.setAttribute("indto_arr", indto_arr);
		request.setAttribute("is_index", is_index);
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=section.jsp");
		rd.forward(request, response);
	}

}
