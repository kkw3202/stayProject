package control.admin.adminBook;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminBookDAO;
import model.User.AdminBookDAO.Book;


@WebServlet("/AdminBookListProc.do")
public class AdminBookListProc extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	// 한 페이지에 표시할 예약 건수
	private static final int PAGE_SIZE = 10;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // 1. 관리자 로그인 및 권한 확인
        HttpSession session = request.getSession(false);
        if (session == null || !"admin@naver.com".equals(session.getAttribute("user_email"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다." );
            return;
        }
        // 2. 검색 조건 가져오기
        // type : name(예약자명), number(예약번호)
        String searchType = request.getParameter("searchType");
        String keyword = request.getParameter("keyword");

        if (!"number".equals(searchType)) {
            searchType = "name";
        }

        if (keyword == null) {
            keyword = "";
        }

        keyword = keyword.trim();

        // 3. 예약 상태 필터
        // null : 관리자 소프트 삭제(4)를 제외한 전체
        // 1 : 예약취소
        // 2 : 예약만료
        // 3 : 예약중
        // 4 : 관리자 소프트 삭제(임시 정책)
        Integer status = null;

        String statusParam = request.getParameter("status");

        if (statusParam != null &&
            !statusParam.trim().isEmpty()) {

            try {
                int value = Integer.parseInt(statusParam);

                if (value >= 1 && value <= 4) {
                    status = value;
                }
            } catch (NumberFormatException e) {
                status = null;
            }
        }

        // 4. 현재 페이지 번호
        int page = 1;

        try {
            page = Integer.parseInt(
                request.getParameter("page")
            );

            if (page < 1) {
                page = 1;
            }

        } catch (NumberFormatException e) {
            page = 1;
        }

        // 5. DAO를 이용한 예약 목록 조회
        AdminBookDAO dao = new AdminBookDAO();

        int total = dao.count(
            searchType, keyword, status
        );

        // 전체 페이지 수
        int pages = Math.max(
            1,
            (int) Math.ceil((double) total / PAGE_SIZE)
        );

        // 요청한 페이지가 마지막 페이지보다 크면 보정
        if (page > pages) {
            page = pages;
        }

        List<Book> books = dao.list(
            searchType,
            keyword,
            status,
            page,
            PAGE_SIZE
        );

        // 6. 예약 상태별 건수
        int reservedCount = dao.countByStatus(
            AdminBookDAO.STATUS_RESERVED
        );

        int expiredCount = dao.countByStatus(
            AdminBookDAO.STATUS_EXPIRED
        );

        int canceledCount = dao.countByStatus(
            AdminBookDAO.STATUS_CANCEL
        );

        int deletedCount = dao.countByStatus(
            AdminBookDAO.STATUS_ADMIN_DELETED
        );

        // 7. JSP에 전달할 데이터 저장
        request.setAttribute("books", books);
        request.setAttribute("total", total);
        request.setAttribute("pages", pages);
        request.setAttribute("pageNo", page);

        request.setAttribute("searchType", searchType);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);

        request.setAttribute("reservedCount", reservedCount);
        request.setAttribute("expiredCount", expiredCount);
        request.setAttribute("canceledCount", canceledCount);
        request.setAttribute("deletedCount", deletedCount);

        // 8. 삭제·수정 결과 메시지
        String bookMsg = (String) session.getAttribute("bookMsg");

        if (bookMsg != null) {
            request.setAttribute("bookMsg", bookMsg);
            session.removeAttribute("bookMsg");
        }

        // 9. 예약 목록 JSP로 이동
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=admin/adminBook/adminBookList.jsp" );

        rd.forward(request, response);
    }

}
