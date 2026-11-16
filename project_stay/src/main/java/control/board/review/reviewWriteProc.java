package control.board.review;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Review.ReviewDAO;
import model.place.Accommodation.AccommodationDTO;

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
		String searchType = request.getParameter("searchType");
		int acc_code = 0; 
		String acc_name = "";
		
		// 숙소를 선택하고 들어온 경우 - 해당 숙소에서 바로 리뷰 작성할때, 
		if (searchType != null && !searchType.equals("none") && !searchType.equals("0") && !searchType.trim().isEmpty()) {
			acc_code = Integer.parseInt(searchType);
			// 선택된 숙소 이름 idao에서 메소드 실행하여 가져오기
			acc_name = rdao.getAccName(acc_code); 
		}
		
		// 숙소선택안하고 타고 들어온경우, 숙소 목록 드롭다운으로 제공하기
		ArrayList<AccommodationDTO> alist = rdao.getAccList();
		request.setAttribute("alist", alist);
		
		// 회원명 가져오기 (히든으로 정보저장할 예정)
		String user_name = rdao.getUser_name(user_email);
		
		request.setAttribute("acc_code", acc_code);
		request.setAttribute("acc_name", acc_name);
		request.setAttribute("user_name", user_name);
		System.out.println("---------------------------------------------------------");
		System.out.println("acc_code"+acc_code);
		System.out.println("acc_name"+acc_name);
		
		// 넘기기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/review/reviewWriteForm.jsp");
		rd.forward(request, response);
	}

}
