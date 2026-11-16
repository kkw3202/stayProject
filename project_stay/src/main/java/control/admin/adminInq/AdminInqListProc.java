package control.admin.adminInq;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminInqDAO;
import model.User.AdminInqDAO.Inq;

@WebServlet("/AdminInqListProc.do")
public class AdminInqListProc extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 1. 한 페이지에 표시할 문의 수
    private static final int PAGE_SIZE = 10;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 2. 관리자 로그인 및 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null || !"admin@naver.com".equals(session.getAttribute("user_email"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다.");
            return;
        }

        // 3. 검색 종류 및 검색어
        String searchType = request.getParameter("searchType");
        String keyword = request.getParameter("keyword");

        if (!"title".equals(searchType)
                && !"content".equals(searchType)
                && !"userCode".equals(searchType)) {
            searchType = "title";
        }

        if (keyword == null) {
            keyword = "";
        }

        keyword = keyword.trim();

        // 4. 답변 상태 필터
        // null : 전체 / 0 : 미답변 / 1 : 답변완료
        Integer status = null;
        String statusParam = request.getParameter("status");

        if ("0".equals(statusParam)) {
            status = AdminInqDAO.STATUS_WAITING;
        } else if ("1".equals(statusParam)) {
            status = AdminInqDAO.STATUS_ANSWERED;
        }

        // 5. 삭제 상태 필터
        // 기본값 0 : 정상 문의만 표시
        // 1 : 삭제처리 문의 / all : 전체
        Integer deleted = AdminInqDAO.STATUS_ACTIVE;
        String deletedParam = request.getParameter("deleted");

        if ("1".equals(deletedParam)) {
            deleted = AdminInqDAO.STATUS_DELETED;
        } else if ("all".equals(deletedParam)) {
            deleted = null;
        }

        // 6. 현재 페이지 번호
        int pageNo = 1;

        try {
            String pageParam = request.getParameter("page");

            if (pageParam != null) {
                pageNo = Integer.parseInt(pageParam);
            }

            if (pageNo < 1) {
                pageNo = 1;
            }

        } catch (NumberFormatException e) {
            pageNo = 1;
        }

        // 7. DAO 생성 및 전체 건수 조회
        AdminInqDAO dao = new AdminInqDAO();

        int total = dao.count(
                searchType,
                keyword,
                status,
                deleted
        );

        // 8. 전체 페이지 수 계산
        int pages = (int) Math.ceil((double) total / PAGE_SIZE);

        // 요청 페이지가 마지막 페이지보다 크면 조정
        if (pages > 0 && pageNo > pages) {
            pageNo = pages;
        }

        // 9. 문의 목록 조회
        List<Inq> inqList = dao.list(
                searchType,
                keyword,
                status,
                deleted,
                pageNo,
                PAGE_SIZE
        );

        // 10. 정상 문의 기준 답변 상태별 건수
        int waitingCount = dao.countByStatus(
                AdminInqDAO.STATUS_WAITING
        );

        int answeredCount = dao.countByStatus(
                AdminInqDAO.STATUS_ANSWERED
        );

        int activeCount = waitingCount + answeredCount;

        // 11. JSP에 전달할 데이터
        request.setAttribute("inqList", inqList);

        request.setAttribute("total", total);
        request.setAttribute("pages", pages);
        request.setAttribute("pageNo", pageNo);

        request.setAttribute("searchType", searchType);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);
        request.setAttribute("deleted", deleted);
        request.setAttribute("deletedFilter",
                deleted == null ? "all" : String.valueOf(deleted));

        request.setAttribute("waitingCount", waitingCount);
        request.setAttribute("answeredCount", answeredCount);
        request.setAttribute("activeCount", activeCount);

        // 12. 등록·삭제 결과 메시지 전달
        String inqMsg = (String) session.getAttribute("inqMsg");

        if (inqMsg != null) {
            request.setAttribute("inqMsg", inqMsg);
            session.removeAttribute("inqMsg");
        }

        // 13. 관리자 문의 목록 JSP 호출
        RequestDispatcher rd = request.getRequestDispatcher(
                "/index.jsp?section=admin/adminInq/adminInqList.jsp"
        );

        rd.forward(request, response);
    }
}
