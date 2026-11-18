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


@WebServlet("/inquiryDeleteCon.do")
public class inquiryDeleteCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// inq_code 파라미터로 받아오기
		int inq_code = Integer.parseInt(request.getParameter("inq_code"));
		
		// idao 인스턴스 생성
		InquiryDAO idao = new InquiryDAO();
		
		// 데이터베이스에서 삭제하기
		idao.deleteBoard(inq_code);
		
		// 리다이렉트 (포워드)
		RequestDispatcher rd = request.getRequestDispatcher("inquiryListProc.do");
		rd.forward(request, response);
	}

}