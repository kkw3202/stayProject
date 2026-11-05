package control.board.inquiry;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Handler.pagingHandler.pagingHandlerDAO;
import model.Handler.pagingHandler.pagingHandlerDTO;
import model.Inquiry.InquiryDAO;
import model.Inquiry.InquiryDTO;


// 1:1 게시판 그리는 서블릿
@WebServlet("/inquiryListProc.do")
public class inquiryListProc extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		
		// 인스턴스화 (페이징 / Inquiry)
		pagingHandlerDAO pdao = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO(); 
		InquiryDAO idao = new InquiryDAO(); 
		
		int count = 0;
		
		// 한 화면에 보여질 행의 개수 (10개)
        pdto.setPageSize(10);
        int pageSize = pdto.getPageSize();
		// 한 화면에 페이징하고 싶은 개수 [1][2][3][4][5]
		int pageBlock=5;
		
		// inquiryList에서 입력한 정보 가져오기
		String pageNum = request.getParameter("pageNum");
		String keyWord = request.getParameter("keyWord");
		String searchType = request.getParameter("searchType");
		
		System.out.println(keyWord);
		// 검색어가 없는 경우(전체목록보기) / 검색어가 있는 경우 
		if(keyWord=="" || keyWord==null) {
			// count에 전체 게시글 수 대입하기 (idao에서 메소드 실행)
			count = idao.getInquiryAllCount();
		}else {
			// count에 전체 게시글 수 대입하기 (idao에서 메소드 실행)
			count = idao.getInquirySearchCount();
			System.out.println("검색결과1");
		}
		
		pdto = pdao.getPaging(pageNum, count, pageSize, pageBlock);
		
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();
		
		ArrayList<InquiryDTO> ilist;
		// 검색어가 없는 경우(전체목록보기) / 검색어가 있는 경우 
		if(keyWord=="" || keyWord==null) {
			ilist = idao.getInquiryAllBoard(startRow, pageSize);
		}else {
			ilist = idao.getInquirySearchBoard(startRow, pageSize, searchType, keyWord);
			System.out.println("검색결과2");
		}
		
		// 최신글 10개를 가져오는 DAO 메소드를 이용해서 리턴
		number = count - startRow;  // 위와 동일함
		
		System.out.println(number);
		System.out.println(count);
		
		// --------------------------------------------------------------------
		request.setAttribute("ilist", ilist);
		//  그릴 변수 넘기기
		// 페이징을 그림그릴 변수 넘기기
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		// InquiryList.jsp 으로 보내기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/inquiry/inquiryList.jsp");
		rd.forward(request, response);
	}

}
