package control.board.inquiry;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Inquiry.InquiryDAO;
import model.Inquiry.InquiryDTO;


@WebServlet("/inquiryWriteProcCon.do")
public class inquiryWriteProcCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);
		
//		String user_name = (String)session.getAttribute("user_name");
		String user_email = (String)session.getAttribute("user_email");
		
//		System.out.println(user_name);
		System.out.println(user_email);
		
		InquiryDAO idao = new InquiryDAO();
		int user_code = idao.getInquiryUserCode(user_email);
		System.out.println(user_code);
		
		if(user_email==null) {
			response.setContentType("text/html: charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α����� �ʿ��մϴ�.')");
			out.println("location.href='index.jsp?section=user/loginForm.jsp'");
			out.println("</script>");
		}
		
		InquiryDTO idto = new InquiryDTO();
		idto.setInq_title(request.getParameter("inq_title"));
		idto.setInq_content(request.getParameter("inq_content"));
		
		idao.insertInquiryBoard(idto, user_code);
		
		response.sendRedirect("inquiryListProc.do");
	}
	
	

}
