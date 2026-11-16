package control.board.inquiry;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Inquiry.InquiryDAO;
import model.Inquiry.InquiryDTO;

@WebServlet("/inquiryRewriteProcCon.do")
public class inquiryRewriteProcCon extends HttpServlet {
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
		int inq_code = Integer.parseInt(request.getParameter("inq_code"));
		int inq_ref = Integer.parseInt(request.getParameter("inq_ref"));  
		String re_inq_title = request.getParameter("re_inq_title");  // 관리자가 답변한 글 제목(히든으로 받은값)
		String inq_content = request.getParameter("inq_content");  // 관리자가 답변한 내용
		
		// idao 인스턴스
		InquiryDAO idao = new InquiryDAO();
		InquiryDTO idto = new InquiryDTO();
		
		// 게시판에 작성자는 관리자 표시
		// 실제 작성자는 원래 작성자 유저코드로 등록
		int user_code = Integer.parseInt(request.getParameter("user_code")); 
		
		// idto에 정보 채우기
		idto.setInq_title(re_inq_title);
		idto.setInq_content(inq_content);
		idto.setInq_ref(inq_ref);
		idto.setUser_code(user_code);

		// 답변달기 메소드 실행
		idao.rewriteInquiry(idto, inq_code);
		
		// 보내기
		RequestDispatcher rd = request.getRequestDispatcher("inquiryListProc.do");
		rd.forward(request, response);
	}

}
