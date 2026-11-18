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


// 1:1 게시판 리스트 처리
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
		// 파라미터 받아오기
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
		
		// 한 화면에 보여줄 게시글 개수 (10개)
        pdto.setPageSize(10);
        int pageSize = pdto.getPageSize();
		// 한 화면에 페이징처리할 블록 개수 [1][2][3][4][5]
		int pageBlock=5;
		
		
		
		String user_name = "";
		
		// 검색창에 공백만 치거나 그냥 검색 버튼을 누를 때 null로 처리
		if(keyWord != null) {
		    keyWord = keyWord.trim();
		    if(keyWord.equals("")) {
		        keyWord = null;
		    }
		}
		
		//boolean isSearch;
		
		
		// 마이페이지 내글보기 버튼 클릭 시 (검색어가 입력안됐음)
		//if(searchType!=null && keyWord == null && searchType.equals("user_name")) {
			//isSearch=false;
		// 검색어에 입력한 경우
		//}else if(searchType != null && keyWord != null) {
			//isSearch=false;
		// 처음에 접속하거나, 전체목록 버튼 클릭시
		//}else {
			//isSeach = true;
		//}
		
		// 전체 게시글 수 구하기문
		// 마이페이지 내글보기 버튼 클릭 시 (검색어가 입력안됐음)
		if(searchType!=null && keyWord == null && searchType.equals("user_name")) {
			// user_name 파라미터를 메서드에 전달하여, keyWord로 활용하기
			keyWord = idao.getInquiryUserName(user_email);
			count = idao.getInquirySearchCount(searchType, keyWord);
			System.out.println("count" + count);
			System.out.println("1번 경우");
		// 검색창에 검색어를 입력하고 검색버튼을 눌렀을 때
		}else if(searchType != null && keyWord != null) {
			// count에 전체 게시글 수 계산하기 (idao에서 메서드 호출)
			count = idao.getInquirySearchCount(searchType, keyWord);
			System.out.println("count" + count);
			System.out.println("2번 경우");
		// 처음에 접속하거나, 전체목록 버튼 클릭시
		}else {
			// count에 전체 게시글 수 계산하기 (idao에서 메서드 호출)
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
		// 마이페이지로 올 경우 / 검색어가 없을 경우(전체목록보기) / 검색어가 있는 경우 
		if(searchType!=null && keyWord == null && searchType.equals("user_name")) {
			ilist= idao.getInquiryMylistBoard(startRow, pageSize, user_name);
		}else if(keyWord=="" || keyWord==null) {
			ilist = idao.getInquiryAllBoard(startRow, pageSize);
		}else {
			ilist = idao.getInquirySearchBoard(startRow, pageSize, searchType, keyWord);
		}
		
		// 최신글 10개씩 출력하기 위해 DAO 메서드를 이용해서 출력
		number = count - startRow;  // 출력 번호
		
		// --------------------------------------------------------------------
		request.setAttribute("ilist", ilist);
		//  파라미터 값 넘김
		// 페이징용 파라미터 값 넘김
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
		
		// InquiryList.jsp 쪽으로 포워드
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=board/inquiry/inquiryList.jsp");
		rd.forward(request, response);
	}

}