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
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// HttpSession 인스턴스화
		HttpSession session = request.getSession(false);
		
//		String user_name = (String)session.getAttribute("user_name");
		String user_email = (String)session.getAttribute("user_email");
		
//		System.out.println(user_name);
		System.out.println(user_email);
		
		// InquiryDAO 인스턴스
		InquiryDAO idao = new InquiryDAO();
		// user_code 가져오는 메소드 사용 
		int user_code = idao.getInquiryUserCode(user_email);
		System.out.println(user_code);
		
		// 로그인이 안된 상태인 경우 : 알림창을 띄우고, 로그인 페이지로 이동
		// 삽입, 삭제, 수정 => 처리하는 서블릿에서 넘길때, sendRedirect()로 넘김
		if(user_email==null) {
			response.setContentType("text/html: charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요합니다.')");
			out.println("location.href='index.jsp?section=user/loginForm.jsp'");
			out.println("</script>");
		}
		
		// InquiryDTO 인스턴스 및 로그인된 상태인경우=> inquiryDTO에 자료 인서트 하기
		InquiryDTO idto = new InquiryDTO();
		idto.setInq_title(request.getParameter("inq_title"));
		idto.setInq_content(request.getParameter("inq_content"));
		
		idao.insertInquiryBoard(idto, user_code);
		
		// 페이지 넘김
		response.sendRedirect("inquiryListProc.do");
	}
	
	

}
