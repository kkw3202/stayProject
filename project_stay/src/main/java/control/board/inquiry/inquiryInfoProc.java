package control.board.inquiry;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Inquiry.InquiryDAO;
import model.Inquiry.InquiryDTO;

@WebServlet("/inquiryInfoProc.do")
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
		
		// 객체선언
		InquiryDAO idao = new InquiryDAO();
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		
		int inq_code = Integer.parseInt(request.getParameter("inq_code"));
		// idao에서 작성자 찾아오는 메소드 
		String writerEmail = idao.getInquiryWriterEmail(inq_code);
		System.out.println(inq_code);
		System.out.println(writerEmail);
		if(user_email.equals(writerEmail)||user_email.equals("admin@naver.com")) {
			// idao에서 하나의 게시글 불러오는 메소드실행하여, idto에 담기
			InquiryDTO idto = idao.getInquiryOneBoard(inq_code);
			System.out.println(inq_code);
			
			// RequestDispatcher로 보내는 단계
			request.setAttribute("idto", idto);
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/inquiry/inquiryInfo.jsp");
			rd.forward(request, response);
		}else if(user_email==null || !user_email.equals(writerEmail)){
			// 작성자와 로그인한 사람이 일치하지 않으면, 메시지창 띄우고, 이전페이지로 이동
			// 작성자와 로그인 회원의 값이 다르면, 읽을 수 없다
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('작성자만 확인이 가능합니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.flush();
			return;
			
		}
		
		
		
		
	}
}
