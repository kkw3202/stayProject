package control.board.inquiry;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Inquiry.InquiryDTO;


public class inquiryInfoProc extends HttpServlet {
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		// 1:1 게시판 회원이름 / 글번호 / 작성일자 / 글그룹 / 글스탭/ 답변상태체크키 가져오기
		
		// 세션 인스턴스화
		HttpSession session = request.getSession();
		String user_name = (String)session.getAttribute("user_name");
		
		InquiryDTO idto  = new InquiryDTO();
		//idto.setInq_code(request.getParameter("inq_code"));
		
		// 
		
		
		
	
	}

}
