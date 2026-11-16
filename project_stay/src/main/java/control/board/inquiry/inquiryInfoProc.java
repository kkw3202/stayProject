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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		// 1:1 게시판 회원이름 / 글번호 / 작성일자 / 글그룹 / 글스탭/ 답변상태체크키 가져오기

		// 객체선언
		InquiryDAO idao = new InquiryDAO();
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		
		int inq_code = Integer.parseInt(request.getParameter("inq_code"));
		// idao에서 작성자 찾아오는 메소드
		String writerEmail = idao.getInquiryWriterEmail(inq_code);
		System.out.println(inq_code);
		System.out.println(writerEmail);

		// 관리자이거나 / 로그인한사람과 작성자가 일치하는 경우, 상세보기 inquiryInfo.jsp로 보냄
		InquiryDTO idto = idao.getInquiryOneBoard(inq_code);
		request.setAttribute("idto", idto);
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/inquiry/inquiryInfo.jsp");
		rd.forward(request, response);

	}
}
