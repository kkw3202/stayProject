package control.board.inquiry;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		System.out.println("--------------");
		// 정보 가져오기
		String pageNum = request.getParameter("pageNum");  
		String keyWord = request.getParameter("keyWord");
		String searchType = request.getParameter("searchType");
		
		// 인스턴스화 (페이징 / Inquiry)
		pagingHandlerDAO pdao = new pagingHandlerDAO();
		pagingHandlerDTO pdto = new pagingHandlerDTO(); 
		InquiryDAO idao = new InquiryDAO(); 
		HttpSession session = request.getSession();
		String user_email = (String)session.getAttribute("user_email");
		
		int count = 0;
		
		// 한 화면에 보여질 행의 개수 (10개)
        pdto.setPageSize(10);
        int pageSize = pdto.getPageSize();
		// 한 화면에 페이징하고 싶은 개수 [1][2][3][4][5]
		int pageBlock=5;
		
		
		
		String user_name = "";
		
		// 검색창에 공백만 치거나 그냥 검색 눌렀을 때 null로 처리
		if(keyWord != null) {
		    keyWord = keyWord.trim();
		    if(keyWord.equals("")) {
		        keyWord = null;
		    }
		}
		
		//boolean isSearch;
		
		
		// 내가쓴 글보기 버튼 누른 경우 (검색어가 입력없음)
		//if(searchType!=null && keyWord == null && searchType.equals("user_name")) {
			//isSearch=false;
		// 검색어에 입력한 경우
		//}else if(searchType != null && keyWord != null) {
			//isSearch=false;
		// 처음에 진입하거나, 전체보기 버튼 누른경우
		//}else {
			//isSeach = true;
		//}
		
		// 전체 게시글 수 파악방법
		// 내가쓴 글보기 버튼 누른 경우 (검색어가 입력없음)
		if(searchType!=null && keyWord == null && searchType.equals("user_name")) {
			// user_name 가져오는 메소드 실행하여, keyWord에 대입하기
			keyWord = idao.getInquiryUserName(user_email);
			count = idao.getInquirySearchCount(searchType, keyWord);
			System.out.println("count" + count);
			System.out.println("1번 경우");
		// 검색창에 검색어를 입력하고 검색버튼을 누른경우
		}else if(searchType != null && keyWord != null) {
			// count에 전체 게시글 수 대입하기 (idao에서 메소드 실행)
			count = idao.getInquirySearchCount(searchType, keyWord);
			System.out.println("count" + count);
			System.out.println("2번 경우");
		// 처음에 진입하거나, 전체보기 버튼 누른경우
		}else {
			// count에 전체 게시글 수 대입하기 (idao에서 메소드 실행)
			count = idao.getInquiryAllCount();
			System.out.println("count" + count);
			System.out.println("3번 경우");
		}
		
		
		
		pdto = pdao.getPaging(pageNum, count, pageSize, pageBlock);
		
		int currentPage = pdto.getCurrentPage();
		int startRow = pdto.getStartRow();
		int number = pdto.getNumber();
		int pageCount = pdto.getPageCount();
		int startPage = pdto.getStartPage();
		int endPage = pdto.getEndPage();
		
		
		ArrayList<InquiryDTO> ilist;
		// 내가쓴것만 본 경우 / 검색어가 없는 경우(전체목록보기) / 검색어가 있는 경우 
		if(searchType!=null && keyWord == null && searchType.equals("user_name")) {
			ilist= idao.getInquiryMylistBoard(startRow, pageSize, user_name);
		}else if(keyWord=="" || keyWord==null) {
			ilist = idao.getInquiryAllBoard(startRow, pageSize);
		}else {
			ilist = idao.getInquirySearchBoard(startRow, pageSize, searchType, keyWord);
		}
		
		// 최신글 10개를 가져오는 DAO 메소드를 이용해서 리턴
		number = count - startRow;  // 위와 동일함
		
		// --------------------------------------------------------------------
		request.setAttribute("ilist", ilist);
		//  그릴 변수 넘기기
		// 페이징을 그림그릴 변수 넘기기
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("searchType", searchType);
		request.setAttribute("keyWord", keyWord);
		
		System.out.println("startPage"+startPage);
		System.out.println("pageBlock"+pageBlock);
		System.out.println("endPage"+endPage);
		System.out.println("pageNum"+currentPage);
		
		// InquiryList.jsp 으로 보내기
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/inquiry/inquiryList.jsp");
		rd.forward(request, response);
	}

}
