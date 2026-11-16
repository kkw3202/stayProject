package control.board.inquiry;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Inquiry.InquiryDAO;
import model.Inquiry.InquiryDTO;

@WebServlet("/inquiryRewriteProc.do")
public class inquiryRewriteProc extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// 상세보기에서 inq_code 가져오기
		int inq_code = Integer.parseInt(request.getParameter("inq_code"));
		
		// idao 인스턴스 / idto 인스턴스
		InquiryDAO idao = new InquiryDAO();
		InquiryDTO idto = idao.getInquiryOneBoard(inq_code);
				
		request.setAttribute("idto", idto);
		
		// 폼으로 정보보내기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/inquiry/inquiryRewriteForm.jsp");
		rd.forward(request, response);
	}
}
