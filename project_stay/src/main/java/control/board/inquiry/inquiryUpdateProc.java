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

// 1:1게시판 글 수정을 처리하는 서블릿
@WebServlet("/inquiryUpdateProc.do")
public class inquiryUpdateProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);
		String user_email = (String)session.getAttribute("user_email");
		
		// 세션 만료 시 로그인 페이지로 이동
		if (user_email == null) {
			response.sendRedirect("loginProc.do");
			return;
		}
		
		// 1:1게시판에서 글 번호 inq_code 파라미터로 가져오기
		int inq_code = Integer.parseInt(request.getParameter("inq_code"));
		
		// idao / idto 인스턴스
		InquiryDAO idao = new InquiryDAO();
		InquiryDTO idto = idao.getInquiryOneBoard(inq_code);
		
		request.setAttribute("idto", idto);
		// inquiryUpdateForm.jsp 쪽으로 포워드
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/inquiry/inquiryUpdateForm.jsp");
		rd.forward(request, response);
	}

}