package control.board.review;

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
import javax.servlet.http.HttpSession;

import model.Review.ReviewDAO;

@WebServlet("/reviewWriteProc.do")
public class reviewWriteProc extends HttpServlet {
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
		HttpSession session = request.getSession();
		String user_email= (String)session.getAttribute("user_email");
		ReviewDAO rdao = new ReviewDAO();
		
		// 숙소코드 카져오기
		int acc_code = 0; 
		String acc_name = "";
		acc_code = Integer.parseInt(request.getParameter("acc_code"));
		acc_name = rdao.getAccName(acc_code); 
		String acc_img = request.getParameter("acc_img");
		// 회원명 가져오기 (히든으로 정보저장할 예정)
		String user_name = rdao.getUser_name(user_email);
		
		
		request.setAttribute("acc_code", acc_code);
		request.setAttribute("acc_name", acc_name);
		request.setAttribute("user_name", user_name);
		request.setAttribute("acc_img", acc_img);
		System.out.println("---------------------------------------------------------");
		System.out.println("acc_code"+acc_code);
		System.out.println("acc_name"+acc_name);
		
		// 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/review/reviewWriteForm.jsp");
		rd.forward(request, response);
	}

}
