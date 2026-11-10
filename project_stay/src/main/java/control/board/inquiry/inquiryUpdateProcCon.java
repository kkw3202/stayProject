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

@WebServlet("/inquiryUpdateProcCon.do")
public class inquiryUpdateProcCon extends HttpServlet {
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		// 수정하는 폼에서 정보들 가져오기
		int inq_code = Integer.parseInt(request.getParameter("inq_code"));
		String inq_title = request.getParameter("inq_title");
		String inq_content = request.getParameter("inq_content");
		
		// idao 선언 + 업데이트 실행하기
		InquiryDAO idao = new InquiryDAO();
		idao.updateInquiry(inq_code, inq_title, inq_content);
		// 페이지 넘김
		// response.sendRedirect("inquiryInfoProc.do");
		RequestDispatcher rd = request.getRequestDispatcher("inquiryInfoProc.do");
		rd.forward(request, response);
		
	}

}
